---
layout: pages
title: 什么时候用Mongo
date: 2018.10.29
tags: mongo
---
这为什么会用mongo？这还用得着mysql?这你怎么用redis！！是我承认，上述问题每个月都会在我的内心中纠结那么一两次，于是我痛下决心决定给自己定下一个标准，什么时候用什么比较好。
# 1.读写间的区别
①Redis
redis的数据是存放在服务器内存的，当内存满了以后就需要扩容，采用redis分布式方案，为了防止redis数据丢失，可以调整redis配置，按照一定的策略将数据持久化到硬盘

②MongoDB
Mongo同时使用了内存和硬盘，采用[MMAP](https://baike.baidu.com/item/mmap/1322217?fr=aladdin)机制对文件进行读写,可以简单理解为将一部分映射到内存，然后再从内存查

③Mysql
mysql毫无疑问是存在硬盘的，就是这么硬

# 2.查找数据的区别

①Redis
redis采用键值对方式存数据，有key值的时候直接查非常快

②MongoDB和Mysql
都支持Id(主键)和索引，当用id和索引查的时候效率高，没有id和索引的时候效率低，对于mysql索引可以看看《高性能Mysql》里面讲的很详细。

# 3.该如何选择呢

①Redis
读写速度快，但是内存成本高，所以一般读写频率比较高且查找简单（Redis是key-value存储）的场景比较适合redis，比如用户的身份验证信息。

②MongoDB
- 大尺寸，低价值
- 高伸缩性：多服务器组成的集群数据库
- 存储地理坐标：划重点，MongoDB的地理坐标查询功能非常强大，这个之前笔者也没了解过，稍后我们在下一段试试这个强大的地理坐标查询
- 不适用于高度事务和复杂SQL：虽然高版本mongo已经支持了事务，但是用起来还是没有传统关系型来的自在，所以遇到高度事务的场景还是不要给自己找麻烦了，用mysql。复杂查询也是，mongoDB虽然支持查询语句，但是跟mysql比查询的能力还是不够强大(可能是我没用的很明白，不过欢迎在留言处进行反驳)。

③Mysql
MongoDB不适用的场景就用mysql，没错就是这样。

# 4.MongoDB的地址查询是什么，我已经等不及了
①输入沈阳地点的坐标[百度坐标拾取器](https://map.baidu.com/)大家也可以拾取自己家乡的坐标来玩
``` java
db.location.insert({"name":"市府广场","coordinate":{"longitude":123.440708,"latitude":41.810372}})
db.location.insert({"name":"八一公园","coordinate":{"longitude":123.431399,"latitude":41.807845}})
db.location.insert({"name":"华府天地","coordinate":{"longitude":123.441639,"latitude":41.817177}})
db.location.insert({"name":"张士灯具城","coordinate":{"longitude":123.294777,"latitude":41.770025}})
```
②MongoDB的原生地理索引支持两种，一个是平面的一个是球面的
- 平面索引
``` java
db.location.ensureIndex({'coordinate':'2d'})
```
- 球面索引
``` java
db.location.ensureIndex({'coordinate':'2dsphere'})
```
显而易见,追求精度的话使用球面查最准，如果距离跨度不大用平面查就可以。

③查询一波
``` java
db.location.find({
    "coordinate":{
      //当前点坐标
     "$near":[123.441363,41.82645],
      //如果指定的点是GeoJSON，则以米为单位指定距离;如果指定的点是遗留坐标对，则以弧度为单位指定距离
     "$maxDistance":0.1
   }
})
```
得出的结果是没有张士灯具城，[坐标查询文档](https://docs.mongodb.com/manual/reference/command/geoNear/#dbcmd.geoNear)

好了感慨一下mongo的强大，然后总结一下，从上述几点我们能很明显的看出来，有事务和复杂查询需求的我们应该用关系型，除此之外用mongo，调用非常频繁且查询简单的我们用redis。
ok,就说这些，欢迎大家指出文章的不足，已经我认识的不足，如果对你有帮助请点赞，谢谢（PHP是世界最好的语言没有之一）
