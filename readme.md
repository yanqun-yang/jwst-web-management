# jwst-web-management
---
## 接口


---
## 知识点

### 事务

#### 事务属性-回滚rollbackFor

&emsp;默认情况下，只有出现RuntimeException才回归异常。rollbackFor属性用于控制出现何种异常类型，回滚事务。

#### 事务属性-传播行为propagation

- 事务传播行为：指的就是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行事务控制
- REQUIRED：大部分情况下都是用该传播行为即可。
- REQUIRES_NEW：当我们不希望事务之间相互影响时，可以使用该传播行为。比如：下订单前需要记录日志，不论订单保存成功与
  否，都需要保证日志记录能够记录成功
### AOP
#### AOP核心概念（底层：动态代理，生成动态代理对象Override方法）
- 连接点：JoinPoint，可以被AOP控制的方法（暗含方法执行时的相关信息）
- 通知：Advice，指哪些重复的逻辑，也就是共性功能（最终体现为一个方法）
- 切入点：Pointcut，匹配连接点的条件，通知仅会在切入点方法执行时被应用
- 切面：Aspect，描述通知与切入点的对应关系（通知+切入点）
- 目标对象：Target，通知所应用的对象
#### 通知类型
- @Around：环绕通知，此注解标注的通知方法在目标方法前、后都被执行
- @Before：前置通知，此注解标注的通知方法在目标方法前被执行
- @After：后置通知，此注解标注的通知方法在目标方法后被执行，无论是否有异常都会执行
- @AfterReturning：返回后通知，此注解标注的通知方法在目标方法后被执行，有异常不会执行
- @AfterThrowing：异常后通知，此注解标注的通知方法发生异常后执行
#### @Pointcut
&emsp;该注解的作用是将公共的切点表达式抽取出来，需要用到时引用该切点表达式即可。
### 获取当前登录用户ID
&emsp;获取request对象，从请求头中获取到jwt令牌，解析令牌获取出当前用户的id。
### 配置优先级
&emsp;命令行参数args(--xxx=xxx) ＞ java系统属性options(-Dxxx=xxx) ＞
application.properties ＞ application.yml ＞ application.yaml
### Bean
#### Bean的获取
- 根据name获取bean: `Object getBean(String name)`
- 根据类型获取bean: `<T>T getBean(Class<T> requiredType)`
- 根据name获取bean(带类型转换): `<T> T getBean(String name，Class<T> requiredType)`
#### Bean的作用域
&emsp;Spring支持五种作用域，后三种在web环境才生效

| 作用域 | 说明                     |
|-----|------------------------|
|   singleton  | 容器内同名称的bean只有一个实例（单例）（默认） |
|   prototype   | 每次使用该bean时会创建新的实例（非单例） |
|   request  | 每个请求范围内会创建新的实例|
|   session  | 每个会话范围内会创建新的实例|
|   application  | 每个应用范围内会创建新的实例|

可以通过@Scope注解来进行配置作用域: `@Scope("prototyke”)`
- 默认singleton的bean在容器启动时被创建，可以使用**@Lazy**注解来延迟初始化（延退到第一次使用时）
- prototype的bean，每一次使用该bean的时候都会创建一个新的实例。
- 实际开发当中，绝大部分的Bean是单例的，也就是说绝大部分Bean不需要配置scope属性。
#### @component及衍生注解与@Bean注解使用场景？
- 项目中自定义的，使用@component及其行生注解
- 项目中引入第三方的，使用@Bean注解
### ※SpringBoot原理
#### 介绍
- 起步依赖：原理就是maven的依赖传递
- 自动配置：springBoot的自动配置就是当spring容器启动后，
一些配置类、bean对象就自动存入到了IOC容器中，不需要我们手动去声明，
从而简化了开发，省去了繁项的配置操作
#### 自动配置原理
- 方案一：@ComponentScan组件扫描
- 方案二：@Import导入。使用@Import导入的类会被Spring加载到IOC容器中，导入形式主要有以下几种： 
  1. 导入普通类
  2. 导入配置类 
  3. 导入ImportSelector接口实现类 
  4. @Enablexxxx注解，封装@Import注解
#### ※@SpringBootApplication
&emsp;该注解标识在SpringBoot工程引导类上，是SpringBoot中最最最重要的注解。该注解由三个部分组成：
- @SpringBootConfiguration：该注解与@Configuration注解作用相同，用来声明当前也是一个配置类。
- @ComponentScan：组件扫描，默认扫描当前引导类所在包及其子包。
- @EnableAutoConfiguration：SpringBoot实现自动化配置的核心注解。
#### ※@EnableAutoConfiguration
&emsp;底层封装一个@Import(AutoConfigurationImportSelector.class)，这个实现类就实现了ImportSeleor
接口中的Sting [] = selectImports(...)方法，返回值是要导入到SpringBoot的IOC容器中的类的全类名
该方法中加载了两个文件，一个是spring.factories，一个是**xxx.AutoConfiguration.imports**。<br>
&emsp;**xxx.AutoConfiguration.imports**这个文件中定义了配置类的全类名，在配置类中就可以通过@Bean注解
来声明一个一个的Bean对象，SpringBoot项目启动的时候会自动加载这个配置文件的配置类(spring.factories
是早期配置文件，也会兼容加载)，然后将配置类信息封装到String数组当中，最后通过@Import注解将这些配置类
加载到Spring的IOC容器当中交给IOC容器进行管理。<br>
&emsp;这些Bean对象上面会加一些@ConditionalXXX注解（如@ConditionalOnMissingBean），这些注解的作用就是按条件装配，
当满足一定的条件之后才会把Bean注册到Spring的IOC容器当中。
#### @Conditional
&emsp;作用：按照一定的条件进行判断，在满足给定条件后才会注册对应的bean对象到Spring IOC容器中。<br>
&emsp;作用位置：方法/类<br>
@Conditional本身是一个父注解，派生出大量的子注解：
- @ConditionalOnClass：判断环境中是否有对应字节码文件，才注册bean到IOC容器。
- @ConditionalOnMissingBean：判断环境中没有对应的bean（类型或名称），才注册bean到IOC容器。
- @ConditionOnProperty：判断配置文件中有对应属性和值，才注册bean到IOC容器。
### Web后端开发总结
&emsp;Web后端开发现在基本上都是基于标准的三层架构进行开发的，在三层架构当中：
- **Controller**控制层负责接收请求，响应数据
- **service**业务层负责具体的业务逻辑的处理
- **DAO**数据访问层，也叫持久层，就是用来处理数据访问操作的，来完成数据库当中数据的增删改查操作<br><br>
  &emsp;前端发起请求，首先会到达**Controller**，它不进行逻辑处理，而是直接调用**service**，**service**进行逻辑处理，
**service**再调用**DAO**完成数据访问操作。<br>
&emsp;若执行具体的业务处理之前，需要去做一些通用的业务处理，比如要进行统一的登录校验/字符编码等，可以借助JavaWeb三大组件之一
的**过滤器Filter**或者是spring当中提供的**拦截器intercepter**来实现。<br>
&emsp;为了实现三层架构层与层之间的解耦，学习了spring框架当中的第一大核心**AOC控制反转**与**DI依赖注入**：
- AOC(Inversion of Control)控制反转：指的是将对象创建的控制权，由应用程序自身交给外部容器，这个容器就是**IOC容器**或**spring容器**，
声明为spring容器当中的Bean对象
- DI(Dependency Injection)依赖注入：容器为程序提供运行时所需要的资源<br>
&emsp;还有**AOP**面向切面编程、spring当中的**事务管理**、**全局异常处理器**以及传统会话技术**cookie**、**session**，
新会话跟踪的解决方案，**GWT**令牌阿里云**OSS**对象存储服务，通过**Mybatis**这个持久层框架来操作数据库。<br>
技术归属的框架:
- JavaWeb：**过滤器Filter**、**cookie**、**session**
- 现在常见的一些解决方案：**GWT令牌**、**阿里云OSS**
- Spring framework：**IOC控制反转**、**DI依赖注入**、**AOP面向切面编程**、**事务管理**、**全局异常处理**、**拦截器**
- **Mybatis**：持久层的框架，用来操作数据库的
- SpringBoot：简化Spring开发的
- SpringMVC：Spring框架对**Web程序开**发提供了非常好的支持，像**全局异常处理器**、
**拦截器**都是Spring框架当中Web开发模块所提供的功能，**SpringMVC**并不是一个单独的框架，是Spring框架的一部分，
用来简化原始的Servlet程序的开发的，在**Controller**当中**接收请求**、**响应数据**的这部分功能都是**SpringMVC**当中提供的功能
- SSM：**SpringMVC** + **Spring framework** + **Mybatis**
- SpringBoot：直接基于**传统的SSM框架**进行整合开发是比较繁琐的，效率也是比较低的，现在的企业项目开发基本上都是直接基于
**SpringBoot**进行项目开发的
















