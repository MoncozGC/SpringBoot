# WebSocket

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