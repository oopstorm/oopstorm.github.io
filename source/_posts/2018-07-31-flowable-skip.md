---
layout: pages
title: Flowable SKIP
date: 2018.07.31
tags: workflow
---
1.skip表达式的作用
在流程编辑器中大家应该会注意到一个 skip表达式的输入框，flowable设计这个skip的含义就是当表达式满足true的话那直接跳过当前节点。

2.使用skip的注意事项
①需要注意是跳过，而不是自动办结，也就是说skip的task是不会有经办人的。

②还有一个需要注意的地方是flowable的skip节点不会在task刚结束的时候就实例化，如果两个连续的skip节点，在节点1中写了前处理添加一些流程变量，但是在skip2节点是拿不到的，只有task停住了（例如到了一个人工节点）之前的skip节点的信息才会被实例化，流程变量才会被拿到。

③要让skip生效需要在流程启动的时候配置一个流程变量_FLOWABLE_SKIP_EXPRESSION_ENABLED=true
[FLOWABLE单元测试地址](https://github.com/flowable/flowable-engine/blob/master/modules/flowable-engine/src/test/java/org/flowable/examples/bpmn/usertask/SkipExpressionUserTaskTest.java)

3.我们用在了什么场景
我们的使用场景是一个请假流程，需要部门负责人-部门副总-总经理审批，但是有时候会有人身兼多职，这样的话同样的内容审批好几次就很烦了，于是我们提供了一个计算上一个待办的经办人和当前经办人是否为同一人的方法，在skip表达式中进行调用，这样的话就免除了同一个人审批多次的烦恼。

暂时就写这些，下一篇会介绍flowable的消息节点或者是子流程。欢迎大家积极评论与指正。

