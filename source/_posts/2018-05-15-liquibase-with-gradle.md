---
layout: pages
title: Using Liquibase with Gradle in Spring Project
date: 2018.05.15
tags: [Gradle, Spring, Liquibase]
---

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
1. 从 `db/dev-data.xml` 中找到新增数据的 changeSet，并放至相应模块的 changelog 文件中

参考资料
-------

1. [liquibase](https://github.com/liquibase/liquibase)
1. [liquibase-gradle-plugin](https://github.com/liquibase/liquibase-gradle-plugin)
