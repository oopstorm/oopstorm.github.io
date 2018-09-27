---
layout: pages
title: Flowable消息边界事件
date: 2018.09.28
tags: workflow
---
# 1.消息边界事件可以做什么
可以插件式的为流程添加节点，说起来有点抽象我们看例子。

# 2.如何设置一个消息边界事件
- ①首先在Message definitions中定义消息，如下图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-8be98c7de4bd9391.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/10783308-6d992f759308ea7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- ②然后编辑流程
设置消息边界，设置的时候就可以选择①定义好的消息了（此处有很多人会疑惑消息边界事件的引用如何使用，其实是配合①的定义来使用）定义后的流程如图所示：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-8b9a5a126812d58a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[附件：messageEventboundary.bpmn20.xml](https://github.com/jianlinz/open/blob/master/messageEventboundary.bpmn20.xml)

# 3.如何使用一个消息边界事件
``` java
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

  ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("messageEventboundary");
        //启动流程 到达任务1
        List<Task> tasks = taskService.createTaskQuery().list();
        assert tasks.size() == 1;
        assert "任务1".equals(tasks.get(0).getName());

        //触发边界事件
        Execution execution = runtimeService.createExecutionQuery().messageEventSubscriptionName("边界测试").singleResult();
        runtimeService.messageEventReceived("边界测试", execution.getId());

        //到达边界事件
        List<Task> eventTasks = taskService.createTaskQuery().list();
        assert eventTasks.size() == 1;
        assert "边界事件".equals(eventTasks.get(0).getName());

        //完成边界事件
        taskService.complete(eventTasks.get(0).getId());

        assert null != historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult().getEndTime();
```
关于消息边界事件的使用应该就是这些，感觉并没有吃透flowable的边界事件的用途，接下来的博客会对边界事件的其它形式（异常边界事件，定时边界事件等）做一个跟深入的研究,也欢迎大家留言探讨。
