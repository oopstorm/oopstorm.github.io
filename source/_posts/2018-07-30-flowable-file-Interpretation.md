---
layout: pages
title: Flowable中的几个重要字段
date: 2018.07.30
tags: workflow
---
# 1.什么是[Flowable](https://www.flowable.org/documentation.html)

大名鼎鼎的Activiti大家都有所了解，其实Flowable是Acitiviti原班人马因为种种原因，另起的一个项目，声称是无成本从Activiti迁移到Flowable，

我们公司也进行了迁移，感觉成本还是有点但是不大，都是些包名和方法名的小改动。

## 2.都有哪几个重要字段

在对流程引擎有一个基础性的了解后，如果要深入的使用流程引擎，首先要了解其中的几个关键字段。

①procInstId 流程实例Id

②taskId 任务Id

③executionId 执行Id

④processDefinitionId 流程定义Id

⑤processDefinitionKey 流程定义Key

⑥processDefinitionName 流程定义名称

# 3.这几个Id都代表了什么在流程中是如何使用的

④⑤⑥这三个字段是属于流程定义级别的一个流程定义的Key对应多个流程定义Id，流程定义Id是对流程定义Key+版本的描述。

我们真正发起一个流程的时候一般会通过flowable提供的接口runtimeService中的startProcessInstanceBy**去发起，通常是用流程定义Id或流程定义Key去发起，这块大家可以试一下，flowable的api还是比较友好的。

- 当启动了一个流程后，就会得到一个流程实例，在流程执行的过程中一个流程对应一个流程实例Id，（子流程会另起一个流程实例），一个流程实例Id会对应多个executionId， executionId是当流程流转到分支节点(包含网关等)或子流程时原executionId会拆分成多个executionId。

- 对于执行Id我们可能还需要更详细的说明一下，因为执行id本身并无太大的实际意义,BPMN2.0规范是这么描述执行id的（为了方便大家读我就截图了，有需要的话可以直接下载[BPMN2.0规范](https://www.omg.org/spec/BPMN/2.0/)）

![image](http://upload-images.jianshu.io/upload_images/10783308-8d49137a5354f44e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image](http://upload-images.jianshu.io/upload_images/10783308-b955697b3a128c36?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 大概的意思是BPMN定义了一个token的概念，token是用来标识流程执行的（很容易对应到flowable的执行id），然后当遇到网关或者子流程时这个token会合并或拆分。

- taskId就是每一个任务的id。

- 总结一下，如果形象的拿procInstId，executionId，taskId做一个比喻的话，procInstId为一个面，executionId为在面上的一条线，taskId为在线上的一个点。

暂时就写这些，下一篇会介绍flowable的skip和流程变量的作用域。欢迎大家积极评论与指正。
