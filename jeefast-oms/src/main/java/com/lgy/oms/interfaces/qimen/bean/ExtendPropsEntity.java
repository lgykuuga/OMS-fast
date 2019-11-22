package com.lgy.oms.interfaces.qimen.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;


/**
 * 扩展属性
 *
 * @author LGy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "extendProps")
public class ExtendPropsEntity {


    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
