1. 设置MANNIFEST.MF,通过pom来管理

```
Manifest-Version: 1.0
Agent-Class: loaded.LoadedAgent
Created-By: 1.6.0_29

```

2. add tools.jar to your classpath,在启动脚本中配置
    目标程序用：java -noverify -XX:-UseSplitVerifier 该特性在jdk 5 6 中非强制的，jdk8中已经rmoved了
    https://stackoverflow.com/questions/31566672/java-8-what-is-the-equivalent-of-usesplitverifier
    
  
3. 目的：(进行中)
  做一些常用的监控如：方法执行统计，答应执行的sql语句，方法中参数监控等
####附：agent jar中manifest的属性
```
Premain-Class: 当在VM启动时，在命令行中指定代理jar时，必须在manifest中设置Premain-Class属性，值为代理类全类名，并且该代理类必须提供premain方法。否则JVM会异常终止。
Agent-Class: 当在VM启动之后，动态添加代理jar包时，代理jar包中manifest必须设置Agent-Class属性，值为代理类全类名，并且该代理类必须提供agentmain方法，否则无法启动该代理。
Boot-Class-Path: Bootstrap class loader加载类时的搜索路径，可选。
Can-Redefine-Classes: true/false；标示代理类是否能够重定义类。可选。
Can-Retransform-Classes: true/false；标示代理类是否能够转换类定义。可选。
Can-Set-Native-Prefix::true/false；标示代理类是否需要本地方法前缀，可选。
```



