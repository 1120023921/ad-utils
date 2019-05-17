package cn.doublehh.adutils.model;

import java.util.List;

/**
 * @author 胡昊
 * Description: AD用户信息
 * Date: 2019/4/14
 * Time: 21:13
 * Create: DoubleH
 */
public class ADUser {

    private List<String> objectClass;
    /**
     * 名
     */
    private String givenName;
    /**
     * 姓
     */
    private String sn;
    /**
     * 公共名称
     */
    private String cn;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 省份
     */
    private String st;
    /**
     * 市/县
     */
    private String l;
    /**
     * 部门
     */
    private String department;
    /**
     * 邮箱
     */
    private String emailAddress;
    /**
     * 传真
     */
    private String facsimiletelephonenumber;
    /**
     * 手机
     */
    private String mobile;
    /**
     * UPN
     */
    private String sAMAccountName;
    /**
     * 用户登录名
     */
    private String userPrincipalName;
    /**
     * 密码
     */
    private String unicodePwd;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 职务
     */
    private String title;
    /**
     * 办公室
     */
    private String physicalDeliveryOfficeName;
    /**
     * 邮件
     */
    private String mail;
    /**
     * 目录位置
     */
    private String distinguishedName;

    private String tel;

    private String ipPhone;

    private List<String> member;

    private List<String> menberOf;

    public List<String> getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(List<String> objectClass) {
        this.objectClass = objectClass;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFacsimiletelephonenumber() {
        return facsimiletelephonenumber;
    }

    public void setFacsimiletelephonenumber(String facsimiletelephonenumber) {
        this.facsimiletelephonenumber = facsimiletelephonenumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getUnicodePwd() {
        return unicodePwd;
    }

    public void setUnicodePwd(String unicodePwd) {
        this.unicodePwd = unicodePwd;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhysicalDeliveryOfficeName() {
        return physicalDeliveryOfficeName;
    }

    public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
        this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public List<String> getMenberOf() {
        return menberOf;
    }

    public void setMenberOf(List<String> menberOf) {
        this.menberOf = menberOf;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIpPhone() {
        return ipPhone;
    }

    public void setIpPhone(String ipPhone) {
        this.ipPhone = ipPhone;
    }
}
