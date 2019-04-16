package cn.doublehh.adutils.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 胡昊
 * Description: AD连接属性
 * Date: 2019/4/15
 * Time: 10:39
 * Create: DoubleH
 */
@ConfigurationProperties(prefix = "ldap")
public class ADServiceProperties {

    private String adminName;
    private String adminPassword;
    private String ldapURL;
    private String returnedAtts;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getLdapURL() {
        return ldapURL;
    }

    public void setLdapURL(String ldapURL) {
        this.ldapURL = ldapURL;
    }

    public String getReturnedAtts() {
        return returnedAtts;
    }

    public void setReturnedAtts(String returnedAtts) {
        this.returnedAtts = returnedAtts;
    }
}
