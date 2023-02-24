/**
 * 事件循环
 * Created by MoncozGC on 2023/2/24
 */

var events = require('events');

var eventEmitter = new events.EventEmitter();

var connectHandler = function connected() {
    console.log("连接成功");

    // 触发 data_received事件
    eventEmitter.emit("data_received");
};

eventEmitter.on("connection", connectHandler);

// 使用匿名函数绑定 data_received事件
eventEmitter.on("data_received", function () {
    console.log("数据连接成功...")
});

// 触发connection事件
eventEmitter.emit("connection");

console.log("程序执行完毕.")