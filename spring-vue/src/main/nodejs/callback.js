/**
 * 回调函数的使用
 * Created by MoncozGC on 2023/2/24
 */
var fs = require("fs");


// 回调函数第一个参数是错误类型, 第二个参数是执行异步操作的函数
fs.readFile("callback.txt", function (err, data) {
    //  如果执行报错则会直接在此处输出返回
    if (err) return console.error(err);
    console.log(data.toString())
});
console.log("程序执行结束")