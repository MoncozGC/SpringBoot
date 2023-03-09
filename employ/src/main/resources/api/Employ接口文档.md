# 接口文档
> API使用工具文档: https://www.macrozheng.com/tool/swagger_postman.html#%E6%9C%89%E4%BD%95%E7%BC%BA%E7%82%B9
## 多线程调用

```http request
GET http://localhost:8071/test
Accept: application/json
```
- 执行成功后查看控制台输出
```text
线程Test-Async-3执行异步任务27
线程Test-Async-11执行异步任务29
线程Test-Async-7执行异步任务26
线程Test-Async-7执行异步任务30
线程Test-Async-6执行异步任务31
线程Test-Async-6执行异步任务32
```