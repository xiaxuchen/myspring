package org.originit.hand.factory.support.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.*;
import org.originit.hand.io.Resource;
import org.originit.hand.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxc
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            // 判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }
            // 解析标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            final String scope = bean.getAttribute("scope");
            final String initMethod = bean.getAttribute("init-method");
            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            // 定义 Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz, beanName);
            beanDefinition.setScope(scope);
            beanDefinition.setInitMethod(initMethod);
            // 读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                final Node item = bean.getChildNodes().item(j);
                if (!(item instanceof Element)) {
                    continue;
                }
                final String nodeName = item.getNodeName();
                if ("property".equals(nodeName)) {
                    resolveProperties(item, beanDefinition);
                }
                if ("constructor".equals(nodeName)) {
                    resolveConstructProps(item, beanDefinition);
                }
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void resolveProperties(Node item,BeanDefinition beanDefinition) {
        // 解析标签：property
        final PropertyValue propertyValue = resolveAsPropertyValue(item);
        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
    }

    private void resolveConstructProps(Node item,BeanDefinition beanDefinition) {
        List<Object> args = new ArrayList<>();
        // 解析标签：constructor
        for (int i = 0; i < item.getChildNodes().getLength(); i++) {
            final Node child = item.getChildNodes().item(i);
            if (!(child instanceof Element)) {
                continue;
            }
            if ("arg".equals(child.getNodeName())) {
                args.add(resolveRefOrValue(child));
            }
        }
        beanDefinition.setConstructArgs(new ConstructArgs(args));
    }

    private PropertyValue resolveAsPropertyValue(Node item) {
        // 解析标签：property
        Element property = (Element) item;
        String attrName = property.getAttribute("name");
        Object value = resolveRefOrValue(property);
        // 创建属性信息
        return PropertyValue.create(attrName, value);
    }

    private Object resolveRefOrValue(Node item) {
        Element property = (Element) item;
        String attrValue = property.getAttribute("value");
        String attrRef = property.getAttribute("ref");
        return StrUtil.isNotEmpty(attrRef) ? BeanReference.refer(attrRef) : attrValue;
    }
}
