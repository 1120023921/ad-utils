package cn.doublehh.adutils.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡昊
 * Description: 组织单位
 * Date: 2019/4/14
 * Time: 23:52
 * Create: DoubleH
 */
public class OU {

    private String name;
    private String ou;
    private List<OU> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public List<OU> getChildren() {
        return children;
    }

    public void setChildren(List<OU> children) {
        this.children = children;
    }
}
