package org.originit.hand.factory.support;

public class PropertyValue {

    private String name;

    private Object value;

    public static PropertyValue create(String name, Object value) {
        final PropertyValue propertyValue = new PropertyValue();
        propertyValue.setName(name);
        propertyValue.setValue(value);
        return propertyValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
