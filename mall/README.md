# mall-learning

![img.png](img.png)
[mall整合Spring官网](https://www.macrozheng.com/mall/architect/mall_arch_01.html#mysql%E6%95%B0%E6%8D%AE%E5%BA%93%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA)

## 整合redis实现缓存功能

## 整合Kafka实现订阅功能

1. 启动SpringBoot
2. 服务器启动消费者, /ykyy/kafka/bin/kafka-topics.sh --bootstrap-server 192.168.0.46:9092 --topic test --from-beginning

## 热部署配置

1. 引入pom依赖
   ```xml
   <!--配置热部署-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-devtools</artifactId>
       <optional>true</optional>
   </dependency>
   ```

2. 配置文件增加配置
   ```properties
   # 热部署是否开启
   spring.devtools.restart.enabled=true
   # 设置重启目录
   spring.devtools.restart.additional-paths=src/main/java
   # 设置忽略目录
   spring.devtools.restart.exclude=static/**
   ```

3. idea修改配置
   - 进入设置Settings -> Build,xx -> Compiler -> 勾选 Build project automatically
   - 进入设置Settings -> Advanced Settings -> 勾选Allow auto-make to start even if developed application is currently running

## 问题记录

### 实体类不要首字母大写

[参考链接](https://blog.csdn.net/qq_24155097/article/details/107034023)

- Spring默认的命名方式, 首字母转小写, 练习大写字母转成小写
- 建议参数类型都: 小写字母, 以下划线分隔. eg: spring_boot

> 实体类参数大写, 可能会导致接收不到参数数据. 导致空指针异常
