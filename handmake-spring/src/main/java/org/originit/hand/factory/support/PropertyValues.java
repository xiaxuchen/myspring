package org.originit.hand.factory.support;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private List<PropertyValue> propertyValues = new ArrayList<>();

    /**
     * 添加属性
     * @param propertyValue 属性
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    /**
     * 获取所有的属性
     */
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

}
