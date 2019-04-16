package cn.doublehh.adutils.enums;

/**
 * @author 胡昊
 * Description:
 * Date: 2019/4/16
 * Time: 13:57
 * Create: DoubleH
 */
public enum ObjectClassEnum {
    USER("user"), ORGANIZATIONALUNIT("organizationalUnit");

    private String objectClass;

    ObjectClassEnum(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }}
