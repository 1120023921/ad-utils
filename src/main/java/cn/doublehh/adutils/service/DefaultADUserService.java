package cn.doublehh.adutils.service;

import cn.doublehh.adutils.enums.ObjectClassEnum;
import cn.doublehh.adutils.exception.AuthenticateException;
import cn.doublehh.adutils.exception.UnknownAccountException;
import cn.doublehh.adutils.model.OU;
import cn.doublehh.adutils.model.ADUser;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 胡昊
 * Description: AD操作工具类
 * Date: 2019/4/14
 * Time: 20:55
 * Create: DoubleH
 */
public class DefaultADUserService implements ADUserService {

    private static String adminName;
    private static String adminPassword;
    private static String ldapURL;
    private static String[] returnedAtts;
    private static String domain;

    public DefaultADUserService(String adminName, String adminPassword, String ldapURL, String[] returnedAtts, String domain) {
        DefaultADUserService.adminName = adminName;
        DefaultADUserService.adminPassword = adminPassword;
        DefaultADUserService.ldapURL = ldapURL;
        DefaultADUserService.returnedAtts = returnedAtts;
        DefaultADUserService.domain = domain;
    }

    /**
     * 获取AD连接
     *
     * @return AD连接
     */
    private LdapContext getLdapContext() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//LDAP访问安全级别："none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        try {
            return new InitialLdapContext(env, null);
        } catch (NamingException e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] AD域服务连接认证失败", e);
        }
    }

    /**
     * 关闭AD连接
     *
     * @param dc AD连接
     */
    private void close(LdapContext dc) {
        if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
                throw new RuntimeException("ADUserUtils [ADUserUtils] AD域服务连接关闭失败", e);
            }
        }
    }

    @Override
    public void add(ADUser adUser) {
        LdapContext dc = getLdapContext();
        try {
            Attributes attrs = new BasicAttributes(true);
            addAttrs(attrs, adUser);
            dc.createSubcontext(adUser.getDistinguishedName(), attrs);
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 新增AD域用户失败", e);
        } finally {
            close(dc);
        }
    }

    private void addAttrs(Attributes attrs, ADUser adUser) {
        Class adUserClass = adUser.getClass();
        Field[] fields = adUserClass.getDeclaredFields();
        Field[] superFields = adUserClass.getSuperclass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!StringUtils.isEmpty(field.get(adUser))) {
                    attrs.put(field.getName(), field.get(adUser));
                }
            }
            for (Field field : superFields) {
                field.setAccessible(true);
                if (!StringUtils.isEmpty(field.get(adUser))) {
                    attrs.put(field.getName(), field.get(adUser));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("ADUserUtils [addAttrs] 属性访问权限不足", e);
        }
    }

    /**
     * 删除AD域用户
     *
     * @param dn 路径
     */
    @Override
    public void delete(String dn) {
        LdapContext dc = getLdapContext();
        try {
            dc.destroySubcontext(dn);
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 删除AD域用户失败:" + dn, e);
        } finally {
            close(dc);
        }
    }

    /**
     * 更新AD用户
     *
     * @param dn         路径
     * @param key        属性名
     * @param val        属性值
     * @param updateMode 更新模式
     * @return 更新结果
     */
    @Override
    public boolean update(String dn, String key, String val, int updateMode) {
        LdapContext dc = getLdapContext();
        try {
            ModificationItem[] mods = new ModificationItem[1];
            // 修改属性
            Attribute attr = new BasicAttribute(key, val);
            if (LdapContext.ADD_ATTRIBUTE == updateMode) {
                mods[0] = new ModificationItem(LdapContext.ADD_ATTRIBUTE, attr);//新增属性
            } else if (LdapContext.REMOVE_ATTRIBUTE == updateMode) {
                mods[0] = new ModificationItem(LdapContext.REMOVE_ATTRIBUTE, attr);//删除属性
            } else if (LdapContext.REPLACE_ATTRIBUTE == updateMode) {
                mods[0] = new ModificationItem(LdapContext.REPLACE_ATTRIBUTE, attr);//覆盖属性
            } else {
                throw new IllegalArgumentException("ADUserUtils [ADUserUtils] 非法更新模式");
            }
            dc.modifyAttributes(dn, mods);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 修改AD域用户属性失败", e);
        } finally {
            close(dc);
        }
    }

    /**
     * 获取指定域下的所有用户
     *
     * @param searchBase 节点路径
     * @return 用户列表
     */
    @Override
    public <T extends ADUser> List<T> searchADUser(String searchBase, Class<T> clazz) {
        LdapContext dc = getLdapContext();
        List<T> result = new ArrayList<>();
        try {
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "objectClass=user";
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = dc.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = answer.next();
                Attributes attributes = sr.getAttributes();
                result.add(attrToObject(attributes, clazz));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 获取指定节点下的所有用户失败", e);
        } finally {
            close(dc);
        }
    }

    private <T extends ADUser> T attrToObject(Attributes attributes, Class<T> clazz) {
        try {
            T adUser = clazz.newInstance();
            NamingEnumeration<? extends Attribute> all = attributes.getAll();
            while (all.hasMore()) {
                Attribute attr = all.next();
                String id = attr.getID();
                try {
                    Field field = clazz.getDeclaredField(id);
                    field.setAccessible(true);
                    field.set(adUser, attr.get());
                } catch (NoSuchFieldException e) {
                    try {
                        Field field = clazz.getSuperclass().getDeclaredField(id);
                        field.setAccessible(true);
                        field.set(adUser, attr.get());
                    } catch (NoSuchFieldException e1) {
                    }
                }
            }
            return adUser;
        } catch (InstantiationException | IllegalAccessException | NamingException e) {
            throw new RuntimeException("ADUserUtils [attrToObject] 新建实例失败", e);
        }
    }

    /**
     * 指定搜索节点搜索指定域用户
     *
     * @param searchBase   节点
     * @param searchFilter 过滤条件
     * @return AD用户
     */
    @Override
    public <T extends ADUser> T searchBySearchFilter(String searchBase, String searchFilter, Class<T> clazz) {
        LdapContext dc = getLdapContext();
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(returnedAtts);
        try {
            NamingEnumeration<SearchResult> answer = dc.search(searchBase, searchFilter, searchCtls);
            if (answer.hasMore()) {
                SearchResult sr = answer.next();
                Attributes attributes = sr.getAttributes();
                return attrToObject(attributes, clazz);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 指定搜索节点搜索指定域用户失败", e);
        } finally {
            close(dc);
        }
    }

    @Override
    public ADUser searchBySearchFilter(String searchBase, String searchFilter) {
        return searchBySearchFilter(searchBase, searchFilter, ADUser.class);
    }

    @Override
    public <T extends ADUser> List<T> listBySearchFilter(String searchBase, String searchFilter, Class<T> clazz) {
        LdapContext dc = getLdapContext();
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(returnedAtts);
        List<T> result = new ArrayList<>();
        try {
            NamingEnumeration<SearchResult> answer = dc.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMore()) {
                SearchResult sr = answer.next();
                Attributes attributes = sr.getAttributes();
                result.add(attrToObject(attributes, clazz));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 指定搜索节点搜索指定域用户失败", e);
        } finally {
            close(dc);
        }
    }

    @Override
    public List<ADUser> listBySearchFilter(String searchBase, String searchFilter) {
        return listBySearchFilter(searchBase, searchFilter, ADUser.class);
    }

    /**
     * 获取组织架构树形结构
     *
     * @param searchBase 初始域
     * @param ou         根域节点
     */
    @Override
    public void getStruct(String searchBase, OU ou) {
        searchBase = "OU=" + ou.getOu() + "," + searchBase;
        LdapContext dc = getLdapContext();
        try {
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            String searchFilter = "objectClass=" + ObjectClassEnum.ORGANIZATIONALUNIT.getObjectClass();
            NamingEnumeration<SearchResult> answer = dc.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = answer.next();
                OU ouInfo = new OU();
                Attributes attributes = sr.getAttributes();
                Attribute ouTmp = attributes.get("ou");
                Attribute name = attributes.get("name");
                if (null != ouTmp) {
                    ouInfo.setOu((String) ouTmp.get());
                    ouInfo.setName((String) name.get());
                    ou.getChildren().add(ouInfo);
                    getStruct(searchBase, ouInfo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("ADUserUtils [ADUserUtils] 获取指定节点下的所有用户失败", e);
        } finally {
            close(dc);
        }
    }

    @Override
    public <T extends ADUser> T authenricate(String searchBase, String username, String password, Class<T> clazz) {
        final String searchFilter = "(|(userPrincipalName=" + username + ")" +
                "(sAMAccountName=" + username + ")(mail=" + username + ")(displayName=" + username + "))";
        T user = searchBySearchFilter(searchBase, searchFilter, clazz);
        if (null == user) {
            throw new UnknownAccountException("LDAP Connection: FAILED login with " + username);
        }
        LdapContext dc = getLdapContext();
        try {
            dc.addToEnvironment(Context.SECURITY_PRINCIPAL, domain != null && !username.toLowerCase().endsWith(domain) ? username + "@" + domain : username);
            dc.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            dc.reconnect(null);
            return user;
        } catch (NamingException e) {
            e.printStackTrace();
            throw new AuthenticateException("LDAP Connection: FAILED login with " + username);
        }
    }

    @Override
    public ADUser authenricate(String searchBase, String username, String password) {
        return authenricate(searchBase, username, password, ADUser.class);
    }
}
