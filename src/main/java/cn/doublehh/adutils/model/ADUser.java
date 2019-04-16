package cn.doublehh.adutils.model;

import lombok.Data;

/**
 * @author 胡昊
 * Description: AD用户信息
 * Date: 2019/4/14
 * Time: 21:13
 * Create: DoubleH
 */
@Data
public class ADUser {

    private String objectClass;
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
}
