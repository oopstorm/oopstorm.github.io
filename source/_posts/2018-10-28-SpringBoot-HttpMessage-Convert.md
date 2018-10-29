---
layout: pages
title: SpringBoot定义HttpMessageConverter
date: 2018.10.28
tags: SpringBoot
---
# 1.HttpMessageConverter的作用
其实通过名字就可以大概猜个七七八八，就是SpringMvc在接或传Http消息的一个转换器，通俗的理解一下。
转换流程如下图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-75ece7398e6d2aed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 2.如何定义HttpMessageConverter
[Spring官网](https://docs.spring.io/spring/docs/5.1.2.RELEASE/spring-framework-reference/web.html#mvc-config-message-converters)给出两种定义方式
①xml方式
![image.png](https://upload-images.jianshu.io/upload_images/10783308-471f5686e9586033.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
②configuration 方式
![image.png](https://upload-images.jianshu.io/upload_images/10783308-7003874933f4dab9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 3.@EnableWebMvc
在用configuration 方式自定义HttpMessageConverter看到了官方的例子，说需要添加一个@EnableWebMvc的注解，那究竟要不要添加呢，这个注解是做什么的呢，笔者在这个注解上耽误了一些时间，下面我将娓娓道来。
①@EnableWebMvc注解的作用
上图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-6828b12f392e4599.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
EnableWebMvc引入了DelegatingWebMvcConfiguration，DelegatingWebMvcConfiguration继承了WebMvcConfigurationSupport，ok不要怕，记住WebMvcConfigurationSupport就够了，记住WebMvcConfigurationSupport,记住WebMvcConfigurationSupport重要的事说三遍。
简单说EnableWebMvc的作用就是实例化了一个WebMvcConfigurationSupport

②如果不加EnableWebMvc，谁会去实例化WebMvcConfigurationSupport
请看WebMvcAutoConfiguration
![image.png](https://upload-images.jianshu.io/upload_images/10783308-1dcab337c3499656.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
ok,不用往下看就知道了WebMvcAutoConfiguration肯定实例化WebMvcConfigurationSupport，就因为红线那句话，当缺少WebMvcConfigurationSupport的bean的时候，这个自动配置才会执行

那么看到这里大家是不是已经知道答案了，当你的模块使用了@EnableWebMvc，那你将失去springBoot的自动配置，那如果使用@EnableWebMvc会发生什么呢？
看下图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-2db62041d544232f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
又见老大哥，在重要的事情强调三遍的时候我们知道老大哥继承了WebMvcConfigurationSupport，然后如图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-cd5a4d72bce84799.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
没错老大哥把所有散落在外的实现了WebMvcConfigurer接口的bean都加载进来了，为啥要加载，肯定是为了覆盖默认配置呀，所以说如果不使用@EnableWebMvc，那么只要实现了WebMvcConfigurer，你的配置会覆盖SpringBoot的默认配置，如果使用了@EnableWebMvc那么这个世界只有你的配置。

总结：本文是根据官方文档和对于源码的简单分析得出的结论，进行了一部分验证，如果大家有什么问题欢迎提出，交流，如果没有问题且你从文章中得到了些许帮助，那么伸出你的小手点个赞就是对笔者最大的鼓励，谢谢大家读完！欢迎提出意见







