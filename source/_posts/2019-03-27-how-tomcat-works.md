---
layout: pages
title: How Tomcat Works
date: 2019.03.27
tags: bookworms
---

## How Tomcat Works | [8.4](https://book.douban.com/subject/10426640/)

* 可以使用 Facade 模式，将接口的实现类包装起来，通过 Facade，仅暴露接口中的方法，而不将实现类自己的方法暴露出去。避免了了解实现类结构时，直接将实例从接口强制转换回实现类，并调用实现类中定义的（非接口提供）的方法。使用 Facade 模式是比通过 protected 修饰符限制类访问权限更优雅的方法
* 连接器是干什么用的？等待 HTTP 请求，并创建请求和响应对象
* Catalina 一共有四种不同的容器：
  1. Engine：表示整个 Catalina 的 servlet 引擎
  2. Host：表示一个有拥有数个上下文的虚拟机
  3. Context：表示一个 Web 应用，一个 context 包含一个或多个 wrapper
  4. Wrapper：表示一个独立的 servlet
* 一个容器的流水线有一个基本的阀门，可以通过编辑 server.xml 来添加阀门。每一个阀门像一个过滤器。基本阀门总是在最后才被调用
* 一个 servlet 容器需要一个定制的加载器，而不是简单的使用系统的加载器，否则 servlet 就可以进入 java 虚拟机 classpath 环境下面的任何类库，这会带来安全隐患。servlet 只允许访问 WEB-INF 目录及其子目录下面的类以及部署在 WEB-INF/lib 目录下的类库
* tomcat 需要一个自己的加载器的另一个原因是它需要支持在 WEB-INF/classes 或者 WEB-INF/lib 目录被改变的时候会重新加载
* servlet 2.3 和 2.4 中定义的 javax.servlet.SingleThreadModel 接口，可以保证实现了此接口的 servlet 不会有两个线程同时使用其 service 方法。servlet 容器可以保证同步进入一个 servlet 的一个实例，或维持的 servlet 实例池和处理每个新请求。该接口并不能避免同步而产生的问题，如访问静态类变量或该 servlet 以外的类或变量。因为这个接口使程序员产生虚假安全感，认为它是线程安全的，所以已在 servlet 2.4 中废弃
* 为了节省资源，tomcat 中所有的后台过程都分享同一个线程。如果一个组件或者容器需要定期的来执行操作，它需要做的是将这些代码写入到 backgroundProcess 方法即可
* 如果需要在一个 tomcat 部署中部署多个上下文，需要使用一个主机。理论上，当只有一个上下文容器的时候不需要主机。但是实践中，一个 tomcat 部署往往需要一个主机
* Catalina 类用来启动和停止一个服务器对象并且解析 Tomcat 配置文件，即 server.xml。Bootstrap 类创建一个 Catalina 的实例并调用它的 process 方法。理论上，这两个类可以合成有一个类。但是，为了支持 Tomcat 的多模式启动，提供了多个引导类。例如前述的 Bootstrop 类是将 Tomcat 作为一个独立的程序运行，而 org.apache.catalina.startup.BootstrapService 则是将 Tomcat 作为一个 Windows NT 系统的服务来运行
* 1>&2 将 stdout 上的错误信息显示到 stderr 上，而 2>&1 将 stderr 的输出显示到 stdout 上
* 可以通过特殊接口 ContainerServlet 来创建一个可以访问 Catalina 内部类的 Servlet。Manager 应用可以通过 Deployer 来管理部署的应用
