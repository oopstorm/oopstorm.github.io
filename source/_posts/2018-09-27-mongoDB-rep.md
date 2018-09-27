---
layout: pages
title: mongoDB4.0副本集讲解及配置
date: 2018.09.27
tags: mongoDB
---
# 1.副本集的作用
- 复制提供了冗余并增加了数据可用性。在不同的数据库服务器上有多个数据副本，复制提供了对单个数据库服务器丢失的容错能力。
- 副本集可以支持客户端读取，增加了mongo的读能力，且可以为灾难恢复和报告等提供特殊的副本。

简单的理解可以将mongo的副本集比作主从。
官方解释：

![image.png](https://upload-images.jianshu.io/upload_images/10783308-7fab294f95aee860.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 2.副本集架构描述

- 副本集仅有一个主节点，其它均为次节点,次节点可为多个，主节点可写可读，次节点仅可读，次节点会根据oplog同步主的数据，如下图:

![image.png](https://upload-images.jianshu.io/upload_images/10783308-6ffb58d3bff8429d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/10783308-3c38e84cd6f0d24e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 当一个主节点挂掉了以后，次节点会进行选举，选举出一个新的主节点[(关于副本集的选举)](https://docs.mongodb.com/manual/core/replica-set-elections/)
![image.png](https://upload-images.jianshu.io/upload_images/10783308-8b1a111aeebcf960.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 读操作，默认是读主节点，但是客户端可以通过设置读偏好来决定，可以对副本集进行读取[(关于读偏好的设置和说明)](https://docs.mongodb.com/manual/core/read-preference/)

- 事务，mongo4.0以后支持了副本集多文档事务，如果使用副本集事务必须将读偏好设置为primary,一次事务操作必须要路由到同一个节点。

- Change Streams，mongo3.6以后提供了changeStreams，可以用来订阅集合上的数据变更，不用像以前一样使用oplog去监听变更了，降低了复杂度和风险。[(change streams详细说明)](https://docs.mongodb.com/manual/changeStreams/)

 # 3.副本集的配置
- 准备工作  我们将37017,37018,37019做为一组副本集 
- 首先要安装mongodb
```  shell
    mkdir /mongodb/bin
    cd  /mongodb
　  wget http://downloads.mongodb.org/linux/mongodb-linux-x86_64-rhel70-4.0.2.tgz
    tar xf  mongodb-linux-x86_64-rhel70-4.0.2.tgz
    cd mongodb-linux-x86_64-rhel70-4.0.2.tgz/bin/
    cp * /mongodb/bin
```
- 构建目录

``` shell
for  i in 37017,37018,37019
    do 
      mkdir -p /mongodb/$i/conf  
      mkdir -p /mongodb/$i/data  
      mkdir -p /mongodb/$i/log
done 
```
- 编辑配置文件
``` shell
cat >>/mongodb/37017/conf/mongod.conf<<'EOF'
systemLog:
  destination: file
  path: /mongodb/37017/log/mongodb.log
  logAppend: true
storage:
  journal:
    enabled: true
  dbPath: /mongodb/37017/data
  directoryPerDB: true
  wiredTiger:
    engineConfig:
      directoryForIndexes: true
    collectionConfig:
      blockCompressor: zlib
    indexConfig:
      prefixCompression: true
processManagement:
  fork: true
net:
  port: 37017
replication:
  oplogSizeMB: 2048
  replSetName: rep1
EOF
```
- 复制配置文件
``` shell
for i in 37018 37019
  do  
   \cp  /mongodb/37017/conf/mongod.conf  /mongodb/$i/conf/
done
```
- 修改配置文件
``` shell
for i in 37018 37019
  do 
    sed  -i  "s#37017#$i#g" /mongodb/$i/conf/mongod.conf
done
```

- 启动服务
``` shell
for i in 37018 37019
  do  
    mongod -f /mongodb/$i/conf/mongod.conf  --bind_ip_all
done
```
- 配置副本集
``` shell
./mongo --port 37017
config = {_id: 'rep1', members: [
                          {_id: 0, host: 'ip:37017'},
                          {_id: 1, host: 'ip:38018'},
                          {_id: 2, host: 'ip:39019'}]
          }
rs.initiate(config)
```
完毕，欢迎大家评论与指正。

参考文档
[1]https://docs.mongodb.com/manual/replication
[2]https://www.cnblogs.com/clsn/p/8214345.html








