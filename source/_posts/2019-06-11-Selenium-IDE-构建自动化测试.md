---
layout: pages
title: Selenium IDE 构建自动化测试
date: 2019.06.11
tags: Selenium IDE 自动化测试
---

参考 

[seleniumIDE 官方网站](https://www.seleniumhq.org/selenium-ide/)  
[selenium 仓库](https://github.com/SeleniumHQ/selenium)

### 安装

> selenium IDE 是一个脚本录制工具, 可在 [Chrome](https://chrome.google.com/webstore/detail/selenium-ide/mooikfkahbdckldjjndioackbalphokd) 或者 [Firefox](https://addons.mozilla.org/en-US/firefox/addon/selenium-ide/) 进行安装使用

### selenium side runner

> 这是一个可以使用命令行执行 selenium IDE 录制脚本的工具, 可以将脚本通过命令行在任何浏览器进行执行。

* 安装

   > 通过 `npm` 进行安装 `selenium-side-runner`
   ```
   npm install -g selenium-side-runner
   ```

* 安装浏览器驱动

   > npm 提供了各大常用浏览器的驱动, 需安装之后才可以使用该浏览器进行执行自动化脚本
    * Chrome
      ```
      npm install -g chromedriver
      ```
    * Edge
      ```
      npm install -g edgedriver
      ```
    * Firefox
      ```
      npm install -g geckodriver
      ```
    * Internet Explorer
      ```
      npm install -g iedriver
      ```
    * Safari

      参考[Testing with WebDriver in Safari](https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari#2957277)

* 执行录制脚本

   ```
   selenium-side-runner *.side
   ```
   > 默认使用 Chrome 进行执行脚本

   > 指定浏览器
   ```
   selenium-side-runner -c "browserName=chrome"
   selenium-side-runner -c "browserName='internet explorer'"
   selenium-side-runner -c "browserName=edge"
   selenium-side-runner -c "browserName=firefox"
   selenium-side-runner -c "browserName=safari"
   ```
   > 在 Selenium Grid2 执行 (-w 指定并行数)
   ```
   selenium-side-runner --server http://localhost:4444/wd/hub -c "browserName=chrome" -w 4
   ```

* 导出测试结果

   ```
   selenium-side-runner *.side --output-directory=result --output-format=junit
   ```
   > output-format 有 `junit` 和 `jest`, `junit` 导出为 `xml`, `jest` 导出为 `json`

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <testsuites name="jest tests" tests="1" failures="1" time="14.59">
   <testsuite name="Default Suite" errors="0" failures="1" skipped="0" timestamp="2019-06-11T04:43:28" time="14.074" tests="1">
      <testcase classname="Default Suite meeting" name="Default Suite meeting" time="8.426">
         <failure>Error: expect(received).toHaveText(expected)

   Expected value to be (using Object.is):
   &quot;方明222&quot;
   Received:
   &quot;方明&quot;
      at Object.args [as toHaveText] (D:\Program Files\nodejs\node_modules\selenium-side-runner\node_modules\expect\build\index.js:202:20)
      at Object.toHaveText [as meeting] (C:\Users\Administrator\Desktop\selenium\side-suite-meeting\commons.js:34:70)
      at process._tickCallback (internal/process/next_tick.js:68:7)</failure>
      </testcase>
   </testsuite>
   </testsuites>
   ```
   ```
   {
      "numFailedTestSuites": 0,
      "numFailedTests": 0,
      "numPassedTestSuites": 1,
      "numPassedTests": 1,
      "numPendingTestSuites": 0,
      "numPendingTests": 0,
      "numRuntimeErrorTestSuites": 0,
      "numTodoTests": 0,
      "numTotalTestSuites": 1,
      "numTotalTests": 1,
      "openHandles": [],
      "snapshot": {
         "added": 0,
         "didUpdate": false,
         "failure": false,
         "filesAdded": 0,
         "filesRemoved": 0,
         "filesUnmatched": 0,
         "filesUpdated": 0,
         "matched": 0,
         "total": 0,
         "unchecked": 0,
         "uncheckedKeysByFile": [],
         "unmatched": 0,
         "updated": 0
      },
      "startTime": 1560227191641,
      "success": true,
      "testResults": [{
         "assertionResults": [{
            "ancestorTitles": ["Default Suite"],
            "failureMessages": [],
            "fullName": "Default Suite meeting",
            "location": null,
            "status": "passed",
            "title": "meeting"
         }],
         "endTime": 1560227202652,
         "message": "",
         "name": "C:\\Users\\Administrator\\Desktop\\selenium\\side-suite-meeting\\DefaultSuite.test.js",
         "startTime": 1560227191800,
         "status": "passed",
         "summary": ""
      }],
      "wasInterrupted": false
   }
   ```


