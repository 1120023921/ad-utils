package cn.doublehh.adutils.service;

import cn.doublehh.adutils.model.ADUser;
import cn.doublehh.adutils.model.OU;

import java.util.List;

/**
 * @author 胡昊
 * Description:
 * Date: 2019/4/15
 * Time: 11:07
 * Create: DoubleH
 */
public interface ADUserService {
    void add(ADUser adUser);

    void delete(String dn);

    boolean update(String dn, String key, String val, int updateMode);

    <T extends ADUser> List<T> searchADUser(String searchBase, Class<T> clazz);

    <T extends ADUser> T searchByUserName(String searchBase, String userName, Class<T> clazz);

    void getStruct(String searchBase, OU ou);
}
