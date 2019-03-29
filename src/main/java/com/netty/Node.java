package com.netty;

/**
 * spring容器创建流程，以及初始化过程
 * 
 * 1.refresh方法 2.prepareRefresh()刷新前的预处理； 1）、记录当前时间，以及一些状态（启动停止状态），
 * 2）、initPropertySource()c初始化一些属性设置（空的，方法留给子类自定义一些个性化的属性） 3）、getEnvironment().validateRequiredProperties();进行属性的校验。
 * 4）、earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>()保存容器中的一些早期的事件
 * 
 * 3.obtainFreshBeanFactory（）获取bean工厂 1）、refreshBeanFactory();刷新【创建】bean工厂 a.在GenericApplicationContext类 beanFactory =
 * new DefaultListableBeanFactory();创建beanFactory对象 b.设置id 2)、
 * getBeanFactory();返回在GenericApplicationContext创建的beanFactory对象 3）、将创建的beanFactory【DefaultListableBeanFactory】返回
 * 
 * 4.prepareBeanFactory(beanFactory);beanFactory 的与准备工作，进行一些设置 1）、设置beanFactory的类加载器，支持的表达式解析器等...
 * 2）、添加部分的BeanPostProcessor【ApplicationContextAwareProcessor（作用就是bean初始化之后检查有没有实现ApplicationContextAware的类）】、
 * 3）、设置忽略的自动装配的接口【EnvironmentAware...】
 * 4）、注册可以解析的自动装配（在任何组件中可以自动注入的【BeanFactory，ResourceLoader，ApplicationEventPublisher（时间派发器），ApplicationContext】）
 * 5）、添加BeanPostProcessor（bean初始化前后的后置处理器）【ApplicationContextAwareProcessor】
 * 6）、给beanFactory中注册一些能用的组件【比如ConfigurableEnvironment类型的environment、 还有系统属性Map<String, Object> systemProperties、
 * 还有系统的环境变量信息Map<String, Object> systemEnvironment】（可以直接aotoware用）
 * 5.postProcessBeanFactory(beanFactory);beanFactory准备完成的后置处理工作（这个方法也是空的留给子类重写这个方法，在beanFactory创建并与准备完成来进行进一步的设置）
 * ======================以上是beanFactory的创建和与准备工作================================================
 * 
 * 6.invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors的方法（限制性子类接口）
 * BeanFactoryPostProcessors:beanFactory的后置处理器，在beanFactory标准初始化之后执行的。
 * 两个接口BeanFactoryPostProcessor以及其子类BeanDefinitionRegistryPostProcessor 1）、执行BeanFactoryPostProcessors方法
 * 先执行BeanDefinitionRegistryPostProcessor a.获取所有的BeanDefinitionRegistryPostProcessor
 * b.看优先级排序，先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor、
 * postProcessor.postProcessBeanDefinitionRegistry(registry);
 * c.在执行实现了Ordered顺序（不是优先级）的BeanDefinitionRegistryPostProcessor d.最后执行没有实现任何优先级或者顺序的BeanDefinitionRegistryPostProcessor
 * 
 * 在执行BeanFactoryPostProcessors的方法 a.获取所有的BeanFactoryPostProcessor组件
 * b.看优先级排序，先执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor、 postProcessor.postProcessBeanFactory(beanFactory)
 * c.在执行实现了Ordered顺序（不是优先级）的BeanFactoryPostProcessor postProcessor.postProcessBeanFactory(beanFactory)
 * d.最后执行没有实现任何优先级或者顺序的BeanFactoryPostProcessor postProcessor.postProcessBeanFactory(beanFactory)
 * 7.registerBeanPostProcessors(beanFactory);注册BeanPostProcessor（bean的后置处理器）【拦截bean创建过程】
 * BeanPostProcessor子接口【DestructionAwareBeanPostProcessor， InstantiationAwareBeanPostProcessor，
 * SmartInstantiationAwareBeanPostProcessor， MergedBeanDefinitionPostProcessor（会被记录到internalPostProcessors）】
 * 不同的BeanPostProcessor执行时机是不一样的 1）、获取所有的BeanPostProcessor，后置处理器可以通过PriorityOrdered和Ordered接口来指定优先级
 * 2）、先注册PriorityOrdered优先级的BeanPostProcessor
 * 注册：把每一个BeanPostProcessor后置处理器添加到BeanFactory中(beanFactory.addBeanPostProcessor) 3)、再注册实现了Ordered接口的 4）、再注册没有实现任何优先级的
 * 5）、最终注册MergedBeanDefinitionPostProcessor，也就是这个internalPostProcessors 6）、最后还注册一个beanFactory.addBeanPostProcessor(new
 * ApplicationListenerDetector(applicationContext)); 作用：在bean创建完成后检查是不是ApplicationListener，
 * 如果是：applicationContext.addApplicationListener((ApplicationListener<?>) bean);添加到容器中
 * 8.initMessageSource();初始化MessageSource组件（国际化功能、消息绑定、消息解析）； 1）、获取beanFactory 2）、看容器中有没有id为messageSource的组件
 * 如果有id为messageSource类型为MessageSource 如果没有：创建一个DelegatingMessageSource messageSource:一般用来取出国际化配置文件中某个key的值，能按照区域信息获取
 * 3）、把创建好的messageSource注册到容器中，以后获取国际化配置文件的时候可以自动注入MessageSource组件，调用 MessageSource的String getMessage(String code,
 * Object[] args, Locale locale) throws NoSuchMessageException; beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME,
 * this.messageSource); 9.initApplicationEventMulticaster();初始化时间派发器 1）、获取bean工厂getBeanFactory();
 * 2）、如果自己配置了时间派发器则获取applicationEventMulticaster组件 3）、如果自己没配创建一个简单的时间派发器 new
 * SimpleApplicationEventMulticaster(beanFactory);用它来帮我们派发事件 4）、将创建的组件租车到容器中，其他组件可以直接注入使用
 * beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
 * 10.onRefresh();留给子容器（子类） 1）、子类重写这个方法，在容器刷新的时候，可以在自定义逻辑
 * 11.registerListeners();给容器中将所有项目中的监听器所有的applicationListener注册到容器 1）、从容器中拿到所有的 ApplicationListeners组件
 * 2）、将每个监听器添加到时间派发器中 getApplicationEventMulticaster().addApplicationListenerBean(lisName); 3）、派发之前步骤产生的事件
 * 12.finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean
 * 1）、beanFactory.preInstantiateSingletons();初始化剩下的单实例bean a.获取容器中的所有的bean依次进行初始化和创建对象 b.获取bean的定义信息RootBeanDefinition
 * c.判断bean不是抽象的的，是单实例的，是懒加载的!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()
 * ①、判断是不是factoryBean-->是否是实现FactoryBean（工厂bean）接口的bean ②、不是工厂bean->用getBean(beanName);创建对象
 * getBean(beanName)步骤--->就是我们字节写的ioc容器.getBean 调用doGetBean(name, null, null, false);
 * 先获取缓存中的保存的单实例bean，如果能获取到，说明bean之前被创建过了（所有创建过得bean都会被缓存起来） 缓存：private final Map<String, Object> singletonObjects = new
 * ConcurrentHashMap<String, Object>(64); singletonObjects：缓存所有单实例bean;key：beanName,value:bean实例对象 缓存中拿不到，开始bean创建对象的流程：
 * 先标记bean已经被创建markBeanAsCreated(beanName); 获取bean的定义信息 获取当前bean依赖的其他bean，如果有，还是按照getBean()的方式把里来的bean创建出来
 * 启动单实例bean的创建流程 1）、createBean(beanName, mbd, args); 2）、Object bean = resolveBeforeInstantiation(beanName, mbd);
 * 这一步是让beanPostProcessort先拦截返回代理对象（这块是【InstantiationAwareBeanPostProcessor】这个提前执行） 先来触发bean =
 * postProcessBeforeInstantiation(beanClass, beanName); 如果bean!=null 再触发postProcessAfterInitialization(result,
 * beanName); 3）、如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象调用 4）、doCreateBean(beanName, mbd, args);
 * 1）、创建bean实例createBeanInstance(beanName, mbd, args); 利用工厂方法或者对象的构造器来创建bean实例
 * 2）、bean实例创建完成在调用applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
 * 【MergedBeanDefinitionPostProcessors】 调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition(mbd,
 * beanType, beanName); 3）、对bean属性赋值populateBean(beanName, mbd, instanceWrapper); ======赋值之前：====
 * 1）、拿到【InstantiationAwareBeanPostProcessor】后置处理器执行 postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)方法
 * 2）、还是拿到【InstantiationAwareBeanPostProcessor】后置处理器执行 postProcessPropertyValues(pvs, filteredPds,
 * bw.getWrappedInstance(), beanName); ======赋值：==== 3）、应用bean属性的值applyPropertyValues(beanName, mbd, bw,
 * pvs);为属性利用setter()进行赋值 4）、【bean初始化】initializeBean(beanName, exposedObject, mbd);
 * 1）、【先执行aware接口的方法】invokeAwareMethods(beanName, bean);执行xxxAware接口的方法
 * xxxAware:BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
 * 2)、【在执行后置处理器初始化之前的方法】applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * BeanPostProcessor.postProcessBeforeInitialization(result, beanName); d.所有的bean通过getBean创建完成后
 * 检查所有的bean是否是SmartInitializingSingleton接口的 如果是：执行smartSingleton.afterSingletonsInstantiated();
 * 13.finishRefresh();完成Beanfactory初始工作，IOC容器创建完成 1）、initLifecycleProcessor();初始化和生命周期有关的后置处理器
 * 允许我们写一个LifecycleProcessor的实现类，默认从容器中找是否有【LifecycleProcessor】lifecycleProcessor组件，如果没有创建一个默认的生命周期组件 new
 * DefaultLifecycleProcessor();加入到容器中，可以自动注入 作用：在BeanFactory的生命周期进行 void onRefresh(); void onClose();
 * 2）、getLifecycleProcessor().onRefresh(); 拿到前边创建的生命周期处理器（beanFactory生命周期的）进行回调onRefresh() 3)、publishEvent(new
 * ContextRefreshedEvent(this));发布容器刷新完成事件 4）、LiveBeansView.registerApplicationContext(this);
 * 
 * ====================================总结================================== 1.spring容器在启动的时候，先保存注册进来的bean定义信息
 * 如何注册bean定义信息： 1）、xml注册bean,<bean> 2）、使用注解注册bean比如@Service、@Component、@Bean... 2.spring容器在合适的时机去创建bean 合适的时机：
 * 1）、用到这个bean的时候，利用getbean()方法创建，创建好保存在容器中 2）、同一创建剩下所有bean的时候finishBeanFactoryInitialization(beanFactory); 3.后置处理器：
 * 每一个bean创建完成，都会使用各种后置处理器处理，来增强bean的功能 比如AutowiredAnnotationBeanPostProcessor：处理自动注入功能
 * AnnotationAwareAspectJAutoProxyCreator：为bean创建代理对象 xxx... 增强功能注解 AsyncAnnotationBeanPostProcessor ... 4.事件驱动模型
 * ApplicationListener：事件监听 ApplicationEventPublisher:事件派发
 * 
 * @author: zhaojq
 * @date: 2019年3月28日
 * @version: V1.0
 */
public class Node {
	public int	value;
	public Node	next;
	static Node	head;

	public Node(int data) {
		this.value = data;
	}
}