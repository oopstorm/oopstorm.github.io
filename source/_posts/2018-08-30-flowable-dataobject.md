---
layout: pages
title: Flowable DataObject的使用(flowable自定义流程标题)
date: 2018.08.30
tags: workflow
---
# 1.DataObject可以做什么
在流程定义的时候可以设置数据对象，数据对象的名称会成为流程变量的key，flowable还提供了一个扩展属性value来为数据对象赋值。也就是说在流程定义阶段我们就可以在全局定义一些流程变量[（官网解释）](https://www.flowable.org/docs/userguide/index.html#dataobjects)。一个比较简单的使用场景就是自定义流程标题，下面我们将以自定义流程标题为例子，讲解DataObject的使用方式。
定义方法如下图：
图1:
![image.png](https://upload-images.jianshu.io/upload_images/10783308-ad7461120d081339.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
图2：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-59e92f29a281642d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 2.flowable自定义流程标题的思路
首先定义一个DataObject为流程标题模板，然后在流程发起时去解析模板，将流程变量和提交的表单数据作为参数替换至模板生成需要的流程标题。

#3.具体的实现方式
①定义dataObject，name为processTitle如图2

②在流程发起的时候获取配置的模板(伪代码需要自己根据业务实现一下具体需求，重要的两处已经贴在下面了，一个是如何在流程发起的时候获取模板，一个是模板的解析)
```java
//在流程发起前通过流程定义获得DataObject集合
List<ValuedDataObject> datas = repositoryService.getBpmnModel(processDefinition.getId()).getMainProcess().getDataObjects();
//流程标题配置模板
String processTitleTmpl="";
 for (ValuedDataObject data : datas) {
     if (“processTitle”.equals(data.getName())) {
                processTitleTmpl = (String) data.getValue();
      }
 }
//globalVariables表单数据+初始化流程变量 自行获取一下
//processTitle就是根据配置的模板和传递的参数做了替换后的自定义的流程标题了
String processTitle=resolveTmpl(defaultTitleTmpl, globalVariables)
    //正则匹配模板${}内的内容并根据map中的数据取值做替换
    public static String resolveTmpl(String tmpl, Map<String, Object> variables) {
        String re = "(?<=\\$\\{).*?(?=\\})";
        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(tmpl);
        String message = tmpl;
        while (m.find()) {
            String key = m.group();
            if (StringUtil.isEmpty(key)) {
                continue;
            }
            String value = (String) variables.get(key);
            message = message.replaceAll("\\$\\{" + key + "\\}", StringUtil.isEmpty(value) ? "" : value);
        }
        return message;
    }
```

如有未描述清楚的地方欢迎大家留言，欢迎大家积极评论与指正。
