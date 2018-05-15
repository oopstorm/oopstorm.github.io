---
layout: pages
title: Using Liquibase with Gradle in Spring Project
date: 2018.05.15
tags: [Liquibase, Spring, Gradle]
---


Liquibase 是什么
---------------

引用 [Liquibase 官网](http://www.liquibase.org/index.html) 的一张图片

![Source Control for Your Database](http://www.liquibase.org/custom_images/home_tagline.png)

类似的工具还有 [Flyway](https://flywaydb.org/)


为什么选择 Liquibase
------------------

[Flyway 官网](https://flywaydb.org/) 上有一个同类工具特性的对比，详见 `Feature Comparison` 部分或下图：

![Feature Comparison](./2018-05-15-liquibase-with-gradle/Feature-Comparison.png)

看图的话，Flyway 完胜，不过 **等等！看完下面内容再做决定**：

* [Liquibase vs Flyway which one to use?](https://stackoverflow.com/questions/37385823/liquibase-vs-flyway-which-one-to-use)
* [Tool-based Database Refactoring: Flyway vs. Liquibase](https://reflectoring.io/database-refactoring-flyway-vs-liquibase/)
* [Java世界最棒的DB Migration Tool](http://ju.outofmemory.cn/entry/85903)

一句话总结一下：

```
面向 SQL，选择 Flyway
不面向 SQL，选择 Liquibase
```




版本
---

* [liquibase v3.6.1](https://github.com/liquibase/liquibase/tree/liquibase-parent-3.6.1)
* [liquibase-gradle-plugin v1.2.4](https://github.com/liquibase/liquibase-gradle-plugin/tree/acf7a693563471f83fd26b9e15365ab98011d804)

Change Log Template
-------------------

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                                       http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.6.xsd">
    <changeSet author="alphahinex" id="20180515-1">
      ...
    </changeSet>
</databaseChangeLog>
```

### 新增数据

1. 在数据库中执行 insert 语句
1. 修改 build.gradle 中配置的 liquibase.runList，将其值改为 'genDevData'
1. `./gradlew generateChangelog`
> 需确保 db/dev-data.xml 不存在，否则会报错
1. 从 `db/dev-data.xml` 中找到新增数据的 changeSet，并放至相应模块的 change log 文件中

参考资料
-------

1. [Liquibase](https://github.com/liquibase/liquibase)
1. [Liquibase Documentation](http://www.liquibase.org/documentation/index.html)
1. [liquibase-gradle-plugin](https://github.com/liquibase/liquibase-gradle-plugin)
1. [Why not use HBM2DDL?](https://github.com/liquibase/liquibase-hibernate/wiki#why-not-use-hbm2ddl)
