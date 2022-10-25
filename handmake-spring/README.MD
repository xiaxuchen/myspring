### 手写spring遇到的问题

### 一、 资源加载
通过定义Resource接口，来处理不同的资源来源的加载，如本地文件、classpath、远程http。
而通过ResourceLoader来屏蔽掉底层的不同资源加载的细节。这里其实使用的是工厂模式，根据传入的location
来生产不同的Resource。
### 二、 构造器对比
由于java方法的参数会被擦除，所以不能通过参数名进行匹配，而是通过参数位置与类型匹配。
SpringMvc是如何获得参数名称的呢？其是通过ASM技术操纵字节码，从局部变量表中获取到变量名。
但这个实现比较复杂，我们也可以通过SpringMvc提供的工具类来使用该功能
```
public class Main {

    public String testArgName(String name,Integer age){
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Main.class.getMethod("testArgName", String.class, Integer.class);
        int parameterCount = method.getParameterCount();
        Parameter[] parameters = method.getParameters();

        // 打印输出：
        System.out.println("方法参数总数：" + parameterCount);
        Arrays.stream(parameters).forEach(p -> System.out.println(p.getType() + "----" + p.getName()));

        MethodParameter nameParameter = new MethodParameter(method, 0);
        MethodParameter ageParameter = new MethodParameter(method, 1);
        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        nameParameter.initParameterNameDiscovery(parameterNameDiscoverer);
        ageParameter.initParameterNameDiscovery(parameterNameDiscoverer);
        System.out.println(nameParameter.getParameterType() + "----" + nameParameter.getParameterName());
        System.out.println(ageParameter.getParameterType() + "----" + ageParameter.getParameterName());
    }
}

```
### 三、 一些设计理解
1. 通过接口定义需要的功能，在接口或抽象类扩展额外支持该接口的方法，并让子类进行实现。
2. 通过抽象类的继承来将不同的公共部分分离在不同的抽象中，一个抽象类除了可以实现接口外，还可以继承抽象类
3. 代码中仍然需要一些硬编码，如对特殊类型的处理，但可以将特殊类型定义为接口，然后封装对应接口的处理。
### 四、 ApplicationContext
其是作为构建BeanFactory的门面对外暴露的。当我们装配BeanFactory时，需要很多复杂的操作，通过ApplicationContext将这些需要交互的类
调用集成封装，使得用户更简单使用。