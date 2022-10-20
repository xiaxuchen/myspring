package org.originit.hand.factory.support;

public class BeanReference {

    private String referName;

    /**
     * 相关bean名称
     * @param referBeanName 引用bean的名称
     */
    public static BeanReference refer(String referBeanName) {
        final BeanReference beanReference = new BeanReference();
        beanReference.setReferName(referBeanName);
        return beanReference;
    }

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }
}
