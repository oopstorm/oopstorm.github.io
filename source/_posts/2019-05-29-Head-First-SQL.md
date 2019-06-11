---
layout: pages
title: Head First SQL
date: 2019.05.29
tags: bookworms
---

## Head First SQL | [8.4](https://book.douban.com/subject/2334295/)

* 一、数据和表：保存所有东西的地方
* 数据库：保存表和其它相关sql结构的内容
* 表：数据库中 包含数据的结构，有行和列组成
* 大写和下划线有助于编写SQL
* CREATE DATABASE gregs_list；
* CREATE TABLE my_contacts(last_name VARCHAR(30) NOT NULL, first_name VARCHAR(20) NOT NULL  DEFAULT 'anna');
* DROP TABLE my_contacts；
* INSERT INTO your_table（column_name1,column_name2,...）VALUES ('value1', 'value2',...)；
* SELECT * FROM my_contacts;
* 二、select 语句：去的精美包装里的数据
* 单引号是特殊字符：当自己单独使用时，加上反斜线转义
* 例：insert into my_contacts(location) values ('Grover\'s Mill');
      或者insert into my_contacts(location) values ('Grover''s Mill');

* SELECT  * FROM my_contacts WHERE first_name = 'Anne';
* SELECT drink_name,main FROM easy_drink WHERE main = 'soda';
* 综合查询：SELECT location FROM doughut_ratings WHERE type = 'plain glazed' AND rating = 10；
* 比较运算符（"=" 与 “<>” 正好相反 等于和不等）：
         SELECT drink_name FROM easy_drinks WHERE main = 'soda' AND amount > 1;
* 或者：SELECT drink_info FROM easy_drinks WHERE main = 'orange juice' OR main = 'apple juice';
* 不可直接选择null：SELECT drink_name FROM drink_info WHERE calories IS NULL;
* LIKE通配符: SELECT * FROM my_contacts WHERE location LIKE '%CA';   -以CA开头
           或者：SELECT * FROM my_contacts WHERE location LIKE '_im';    -Jim，Gim都可匹配
* between(等于大于等于或小于等于)：SELECT drink_name FROM drink_info WHERE calories BETWEEN 30 AND 60;
* IN 列值匹配几何中的任何值即返回该行和该列：
         SELECT  date_name FROM black_book WHERE  rating IN('innovative','fabulous', 'delightful');
* NOT IN （查询结果集不在值的集合内），可与BETWEEN...AND , 和LIKE一起使用，注意：NOT紧跟在WHERE后边，当与AND、OR一起使用时，直接放在AND、OR后面
* 三、Delete和Update：改变是件好事
  DELETE 不能删除单一列中的值或表中某一列的所有值、慎用（先使用select语句确定）；
  UPDATE  可以改变单一列或多列的值，交与WHERE决定、SET子句加入colum = value组，以逗号分隔、取代了DELETE和INSERT、与数学运算符一起使用；
* DELETE FROM clown_info WHERE activities = 'dancing';
* UPDATE  dought_ratings SET type='glazde' where type='plain glazed';
* 四、聪明的表设计：为什么要规范化
* 使用数据的方式将影响设置表的方式
  * 为什么要规范化：
      1、让数据具有原子性（表示在同一列中不会存储多个类型相同的数据；不会用多个列存储类型相同的数据）；
      2、主键是表中的某个列，它可以让每一条记录成为唯一的；
            - 主键不为NULL、插入新纪录时必须指定主键值、主键必须简洁、主键值不可被修改
  * 规范化表的优点：
      规范化表中没有重复数据，可以减小数据的大小；
      因为查找的数据较少，你的查询会更快速；
* CREATE TABLE my_contacts （contact_id INT NOT NULL AUTO_INCREMENT,last_name varchar(30) default NULL,.... PRIMARY KEY(contact_id)）
* SHOW CREATE TABLE
* 五、Alter ：改写历史
* ALTER可以调整列的顺序：FIRST、LAST、BEFORE cloumn_name、AFTER cloumn_name、THIRD...
* 使用RENMAE改变表名
* 想同时改变列的名称和类型用 AILTER 搭配 CHANGE
* 只想改变数据类型时 AILTER 搭配 MODIFY
* 指定的顺序在表中添加列 ALTER 搭配 ADD
* 表中删除列  ALTER 搭配 DROP
* DROP CLOUMN 从表中删除列
* 加字段：ALTER TABLE my_contacts ADD COLUMN contact_id INT NOT NULL ANTO_INCREMENT FIRST, ADD PRIMARY KEY(contact_id);
* 修改表名：ALTER TABLE projekts RENAME TO project_list;
* 修改列名：ALTER TABLE project_list CHANGE COLUMN descriptionfproj proj_desc VARCHAR(100);
* 将number改为proj_id，设置成AUTO_INCREMENT,并设为主键：
   AlTER TABLE project_list CHANGE CLOUMN number proj_id INT NOT NULL AUTO_INCREMENT,ADD PRIMARY KEY(proj_id);
* 数据类型修改，可能会丢失数据：
* ALTER TABLE project_list MODIFY COLUMN proj_desc VARCHAR(120);
* 删除一列：ALTER TABLE project_list DROP COLUMN start_date;
* 字符串函数（文本以及有char或varchar类型的列中存储的值）
* RIGHT()和LEFT()从列中选出指定量的字符
   * 例：SELECT RIGHT(location，2)FROM my_contacts；
* 字符串函数能选出文本列的部分内容  
   * 例：SELECT SUBSTRING_INDEX(location，',',1)FROM my_contacts；(含义：查找location列中第一个带逗号的值)
   * 例：SELECT SUBSTRING(your_string, start_position,length)；(含义：截取部分your_string字符串，截取的起始位置为start_position，截取的长度由length指定)
   * 例：SELECT UPPER(your_string)和LOWER(your_string)；(含义：分别将整组字符串改成大写或小写)
   * 例：SELECT REVERSE(your_string)；(含义：反转字符串的字符排序)
   * 例：SELECT LTRIM(your_string)和RTRIM(your_string)；(含义：返回清除多余空格后字符串，分别清除字符左侧和右侧的多余空格
   * 例：SELECT LENGTH(your_string);(含义：返回字符串的字符数量)
* UPDATE
   * 例：UPDATE my_contact SET colum_name= newvalue
   * 例：UPDATE my_contact SET state= RIGHT(location，2)  截取location列的最后两个字符
* 六、select进阶：以新视角看你的数据
* 指定列按条件更新：
        UPDATE my_table SET new_column=
        WHEN cloumn1 = somevalue1 THEN newvalue1
        WHEN column2 = somevalue2 THEN newvalue2
        ELSE newvalue3
        END；
* ORDER BY 根据指定的列，按字母顺序排列查询结果
         * 例: Select title,category from movie_table where title like 'A%' AND  category = 'family'；
* GROUP BY 根据列，把记录分成多个组
* ASC, DESC, SUM, AVG,  MIN, MAX ，COUNT(计算天数), DISTINGCT(特殊的值)
    * 例：SELECT first_name, SUM(sales) from cookie_sales GROUP BY first_name,ORDER BY SUM(sales) DESC 
      --- 查询cookie_sales表中，first_name 值分组，按照sales的和倒序排序；
      SELECT DISTINCT sales_date FROM cookie_sales ORDER BY sale_date;
      SELECT COUNT(DISTINCT sales_date) FROM cookie_sales
* LIMIT：明确指定返回记录的数量，以及从哪一条记录开始返回
* 七、多张表的数据设计：拓展你的表
* SUBSTRING_INDEX
   * 例：SELECT * from my_contacts where gender='F' AND SUBSTRING_INDEX(interest, ',', 1) = 'animals'; --- 只有第一项兴趣为animals的女性才会出现在查询结果
   P323
* 八、联接与多张表的操作：不能单独存在
* 一列的多个值，拆分成多列：
* UPDATE my_contacts SET interest1 = SUBSTRING _INDEX(interests, ',', 1);
* 说明(interests:需要截取数据的列名; ',':需要找的分隔字符，本处为逗号；1：...查找第一个逗号)
* UPDATE my_contacts SET interest = SUBSTR(interests, LENGTH(interest1)+2);
* 说明：把interests列的值改变为这个查询指定的任何内容，但要去除interst1 列存储的值，有逗号与空格（P381）
* 同时进行CREATE、SELECT、INSERT
* AS 会引用某个查询的结果来安插到另一个表中，会创建一列，并采用与select的查询结果相同的列名与数据类型；
* 例如：
* 创建表的同时设置主键并利用select填入数据
* CREATE TABLE profession(
  id INT(11) NOT NULL AUTO _INCREMENTT PRIMARY KEY,
 profession varchar(20)
) AS 
SELECT profession FROM my _contacts GROUP BY profession 
ORDER BY profession；
* 列的别名、表的别名(联接需要)
* 联接：
  * 交叉连接（属于内联接的一种）：返回一张表的每一行与另一张表的每一行所有可能得搭配结果；
   * SELECT t.boy,b.boy FROM toys AS t CROSS JOIN boys AS b;  ---CROSS JOIN返回两张表的每一行相乘的结果；
  * 内联接：任何使用条件结合来自两张表的记录的联接；
   * SELECT somecolumns FROM table1 INNER JOIN table2 ON somecondition；
  * 相等联接（内联接的一种）：返回相等的行；
   * SELECT boys.boy,toys.toy FROM boys INNER JOIN toys ON boys.toy_id = toys.toy_id ORDER BY boys.boy ;
  * 不等联接（内联接的一种）：返回不相等的行；
   * SELECT boys.boy,toys.toy FROM boys INNER JOIN toys ON boys.toy_id <> toys.toy_id ORDER BY boys.boy ;
  * 自然联接：不使用"on"，子句的内联接，只有在联接的两张表中有同名列时才能顺利运作；
  * SELECT boys.boy,toys.toy FROM boys NATURAL JOIN toys;

* 九、子查询：查询中的查询
* 子查询有助于避免数据重复，让查询更加动态灵活
* 子查询：只不过是查询里的查询；
   * SELECT some_column,another_column FROM table WHERE column = (SELECT column FROM table);
  * 子查询也可以用自然内联接命令完成；
   * SELELCT jc.salary FROM my_contacts mc NATURAL JOIN job_current jc WHERE email = 'andy.com';
* 非关联子查询：如果子查询可以独立运行且不会引用外层查询的任何结果，称为非关联子查询
   * OUTER query： 外
* 层查询会较晚处理，它查询的结果取决于内层查询的值
   * INNER query: 内层查询可以单独运行，联接会先处理这个部分
  * 非关联子查询使用IN或NOT IN来检查子查询返回的值是否为集合的成员之一
  * 例: SELECT mc.first8_name,mc.last_name,mc.phone,jc.title,
     * FROM job_current AS jc NATURAL JOIN my_contacts AS mc
     * WHERE jc.title IN ( SELECT title FROM job_listing); 
  * 说明： IN 根据子查询返回的整个结果集来评估jc.title 每一行的值
* 关联子查询：如果子查询依赖于外层查询，称为关联子查询
  * 搭配 EXIST 与 NOT EXIST 的关联子查询：找出所有外层查询结果事都存在于关联表里的记录
  * 例：SELECT mc.first_name firsstname,mc.last_name lastname, mc.email email 
    FROM mys_contact mc WHERE NOT EXIIST
    (SELECT * FROM job_current jc WHERE 
    mc.contact_id = jc.contact_id)； 

* 十、外连接、自连接与联合：新策略
* 外联接 
   * LEAFT OUTER JOIN (左外联接)：接收左表的所有行，并用这些行与右表中的行进行匹配；
   * 例：SELECT g.girl,t.toy FROM girls g    --- girls 位于LEAFT OUTER JOIN前，是左表
      LEAFT OUTER JOIN toys t         --- toys 是右表
      ON g.toy_id = t.toy_id;
  * RIGHT OUTER JOIN (右外联接)：会根据左表评估右表
   * 例： 同上
  * 同一个表可以同时作为外联接的左右表；
* 外键：自引用外键是个处于其他目的而用于同一张表的主键；
* 自联接：能把单一表当成两张具有完全相同的信息的表来进行查询；
  * 例：SELECT c1.name,c2.name AS boss
    FROM clown_info c1 
    INNER JOIN clown_info c2
    ON c1.boss_id = c2.id;
* UNION:联合 --- 数据不会重复
   * 例：SELECT title FROM job_current 
      UNION 
     SELECT title FROM job_desired
      UNION 
     SELECT title FROM job_listings；
    * 联合的规则：每个select 语句中列的数量必须一致、包含的表达式和统计函数必相同、语句顺序不重要，不会改变结果、SQL默认会清楚联合的结果中的重复值、列的数据类型* 必须相同或者可以相互转换、若想看到重复数据使用UNION ALL运算符；
   * 联合创建表：
     CREATE TABLE my_union AS 
     select title FROM job_current UNION
     select title FROM job_desired
     UNION select title FROM job_listings；
* INTERSECT(交集)与 EXCEPT(差集)
   * 例：SELECT title FROM job_current 
      INTERSECT                 --- 或者EXCEPT
      SELECT title FROM job_desired;

* 子查询和联接的区别：
       * 子联接例：
     SELECT mc.first_name,mc.last_name,mc.phone，jc.title
     FROM job_current AS jc NATURAL JOIN my_contacts AS mc
     WHERE jc.title IN(SELECT title FROM job_listings);
   * 内联接例：
     SELECT mc.first_name,mc.last_name,mc.phone，jc.title
     FROM job_current AS jc NATURAL JOIN my_contacts AS mc
     INNER JOIN job_listings j1
     ON jc.title = j1.title;

* 十一、约束、视图与事务：人多手杂，数据库受不了
* 检查约束：check---列约束
   * check检查约束限定允许插入某个列的值，与where 子句都使用相同的条件表达式
   * 所有运算符-AND,OR,IN,NOT,BETWEEN 都可以用check,但是不能使用子查询；
   * 例：ALTER TABLE my_contacts ADD CONTRAINT CHECK gender IN('M', 'F');
* 视图：创建后，查看会呈现出表格视图---称为虚拟表，不会一直保存在数据库中
  * 创建视图:
    CTREAT VIEW web_designers AS SELECT mc.first_name,mc.last_name,mc.phone,mc.email
    FROM my_contacts mc
    NATURAL JOIN job_desired jd  ---也可以使用INNER JOIN ON mc.contact_id= jd.contact_id
    WHERE jd.title = 'Web Designer';
  * 查看视图：SELECT * FROM web_designers；
  * CHECK OPTION的视图：检查每个进行INSERT、DELETE的查询，他根据视图中的WHERE子句来判断这些查询是否执行
  * 视图使用完毕后：DROP VIEW web_designers；
  * 视图优点：
   * 视图把复杂查询简化为一个命令、及时一直改变数据结构也不会破坏依赖表的应用程序、创建视图可以隐藏读者无需看到的信息
* 事务：
  * 在事务过程中，如果所有步骤无法不受干扰地完成，则不改完成任何单一的步骤；
  * ACID: 原子性、一致性、隔离性、持久性
  * SQL帮助管理事务：
    START TRANSACTION;
    COMMIT;  --- COMMIT 之前数据库不会发生任何改变
    ROLLBACK;  --- 回滚
  * 存储引擎：BOB或InnoDB 
   * 例：若要改变存储引擎：ALTER TABLE your_table TYPE = InnoDB;
  
* 十二、安全性：保护你的资产
* 给数据库设置密码
  * MYSQL:  SET PASSWORD FOR 'root@localhost'= PASSWORD('b4dc10wnz');
  * ORACLE: alter user root identified by new-password;
* 添加新用户   ---elsie
     CREATE USER elsie IDENTIFIED BY  'cl3bsye2ewb';
* 赋予权限     ---elsie 行使表clown_info的权限
     GRANT SELECT ON clown_info TO elsie;
* 撤销权限 
     REVOKE SELECT ON clown_info FROM elsie;
* 撤销，但不触及权限
    REVOKE GRANT OPTION ON DELETE ON chores FROM happy,sleepy;
  * 注意：A授予B，B授予C权限
  * CASCADE 表示权限的撤销具有连锁反应，包括目标在内的被授权人的权限都被撤销
    REVOKE DELETE ON chores FROM sleepy CASCADE;
  * RESTRICT 两方的权限被保留，返回错误信息
    REVOKE DELETE ON chores FROM sleepy RESTRICT;
* 设定角色
   CREATE ROLE data_entry;
* 给角色授权
   GRANT SELECT,INSERT ON some_table TO data_entry；
* 指定用户角色
   GRANT data_entry TO doc; --- data_entry 角色名称替换了表名与权限
* 卸除角色
   DROP ROLE data_entry;
* 加上WITH ADMIN OPTION的角色
   GRANT data_entry TO doc WITH ADMIN OPTION; --- WITH ADMIN OPTION 让有角色的用户把同一个角色授予其他人
* 撤销角色采用上边的CASADE、RESTRICT

* 附录：
