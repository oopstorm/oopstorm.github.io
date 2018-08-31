---
layout: pages
title:git gradle持续集成按需构建
date: 2018.08.31
tags: CI
---
如题，本文将详细对git+gradle持续集成如何按需构建做一个详细说明

# 1.为什么要持续集成
随着微服务的日渐成熟，软件的架构模块化已经成为主流，在模块化服务化的架构下拆分的思路大行其道，但是拆开容易合起来却并不是那么容易，如何让拆开的模块，服务更容易的合起来，合起来更加的稳定，那持续集成将是不可或缺的环节。

# 2.为什么要按需构建
随着项目的日渐庞大，单元测试不断增多，那么持续集成的速度将会是团队效率的一个重要指标
我们的持续集成步骤如下图：
![image.png](https://upload-images.jianshu.io/upload_images/10783308-855d1c46db414f93.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从图中不难看出，要想代码被打成jar包最终上传需要经历①②③步，且当审查有问题的时候有可能多次经历①，那么①步的构建速度将会很大程度上影响整个持续集成周期的速度。我们原来是①与③相同均将所有模块都check一遍，可是问题出来了，即使是用了gradle的parallel(并行构建)由于服务器性能瓶颈，构建速度依然提不上去（500个单元测试大概需要15分钟左右）。那么何不按需构建，只构建修改的模块，在③步的时候再去做一次统一构建。

# 3.如何按需构建
①首先获取git的本次提交的所有文件列表
```shell
   filePathStrs=`git diff --name-only HEAD develop`
   # 变更列表转数组
   filePathAttr=(${filePathStrs// / })
```
②一般模块化的代码应该可以通过文件路径解析出project名称
我们的文件路径：subprojects/modelName/businessName/src/**
modelName是模块名 
businessName为业务名  非必须
若有业务名则业务名为project名称否则modelName为project名称
```shell
# 模块名称String拼接
models=''
for var in ${filePathAttr[@]}
do
   if [[ $var =~ subprojects[\s\S]* ]] && [[ $var =~ [\s\S]*src ]];then
       subFilePath=`echo $var|grep -P '(?<=subprojects\/).*?(?=\/src)' -o`
       subFilePathAttr=(${subFilePath//// })
       subFilePathAttrLength=${#subFilePathAttr[@]}
       modelName=${subFilePathAttr[subFilePathAttrLength-1]}
       models=$models' '$modelName
   elif [[ $var =~ [\s\S]*\.gradle ]];then
       subFilePath=`echo $var|grep -P '.*?(?=\.gradle)' -o`
       subFilePathAttr=(${subFilePath//// })
       subFilePathAttrLength=${#subFilePathAttr[@]}
       modelName=${subFilePathAttr[subFilePathAttrLength-1]}
       models=$models' '$modelName
   fi
done
modelsAttr=(${models// / })
# 模块名称数组并去重
modelsAttr=($(awk -vRS=' ' '!a[$1]++' <<< ${modelsAttr[@]}))
```
获取到变更的项目名称后需要与当前的项目做一个对比 因为有可能全部模块均被删除掉
```shell
projectsStr=`gradle projects`
projects=`echo $projectsStr|grep -P "(?<=\'\:).*?(?=\')" -o`
needBuildModel=''
for project in ${projects[@]}
do 
  for model in ${modelsAttr[@]}
  do      
    if [[ $project == $model ]];then
     needBuildModel=$needBuildModel' '$project
    fi   
  done  
done
#获取与gradle中匹配的project集合
needBuildModelAttr=(${needBuildModel// / })
```
③我们已经拿到了gradle中匹配的project集合 那剩下的事就是gradle命令了
```shell
commandStr=''
for needBuild in ${needBuildModelAttr[@]}
do
   commandStr=$commandStr' '$needBuild:check
done  
#所有变更模块进行一次check
./gradlew $commandStr --scan --daemon --parallel
```
这样我们就完成了按需构建的脚本，将脚本配在持续集成工具上就ok了，还有一个思路是用git提供的webhook(钩子)去获取变更列表，但是这种方案可能调用持续集成工具(jenkins,teamcity)的时候有点麻烦所以就没有探索，如果大家有思路欢迎留言，最后欢迎大家积极评论与指正。


