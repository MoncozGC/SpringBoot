# WebSocket

使用WebSocket, 实现前后端通信交互、订阅服务。

在WebSocket之前的实现方式：

1. 轮询，客户端定期去服务端获取最新数据。这样无法保证数据的时效性
2. 长链接(long poll)，和轮询相似，但是为阻塞模式的轮询，客户端请求新的数据request, 服务器会阻塞请求，直到有新数据后才返回response给客户端；然后客户端再重复此过程。

> 以上两种方式的特点，不断的建立HTTP连接，然后发送request请求，之后等待服务器处理。体现了服务端的被动性，同时也消耗网络带宽和服务器资源。

WebSocket的特点

1. 通过第一次HTTP Request建立连接之后，后续的数据交换都不用在重新发送起HTTP Request，节省了带宽资源；
2. WebSocket的连接时双向通信的连接，在同一个TCP连接上，既可以发送也可以接收；
3. 具体多路复用的功能，即几个不同的URL可以复用同一个WebSocket连接。类似于TCP连接，但是因为引用了HTTP协议的概念，所以叫WebSocket。

## 项目结构

- chat/moncozgc/chatweb
  - [实现简单的聊天室](https://gitee.com/make-a-dream/chat-demo)
  - 基于WebSocket、SpringBoot、Vue
  - 注: 对应前端代码在 resource/chatweb中.
- 项目启动方式:
  - 前端依赖 npm install
  - 前端启动 npm run dev
  - 后台jar包 maven管理, 执行 ChatApplication.java方法

- com/moncozgc/websocket
  - 实现广播、点对点发送信息
  - 基于WebSocket、STOMP协议、SpringBoot、原生js
  - 注: 对应前端代码在【包含广播及点对点的网页】 resource/static/{privateExample.html、publicExample.html}
- 项目测试方式
  - 【包含广播及点对点的网页访问】: http://localhost:9908/{privateExample、publicExample}.html

```http request
### 广播发送请求
POST http://localhost:9908/pushToAll
Accept: application/json, text/plain, */*
content-Type: application/json;charset=UTF-8
Cache-Control: no-cache

{
  "content":"这是一条信息"
}

### 点对点发送请求
POST http://localhost:9908/pushToOne
Accept: application/json, text/plain, */*
content-Type: application/json;charset=UTF-8
Cache-Control: no-cache

{
  "to": 19901,
  "content":"这是一条信息"
}
```

### 状态码

```text
1000 连接正常关闭
1001 端点离线，例如服务器down，或者浏览器已经离开此页面
1002 端点因为协议错误而中断连接
1003 端点因为受到不能接受的数据类型而中断连接
1004 保留
1005 保留, 用于提示应用未收到连接关闭的状态码
1006 端点异常关闭
1007 端点收到的数据帧类型不一致而导致连接关闭
1008 数据违例而关闭连接
1009 收到的消息数据太大而关闭连接
1010 客户端因为服务器未协商扩展而关闭
1011 服务器因为遭遇异常而关闭连接
1015 TLS握手失败关闭连接
```

