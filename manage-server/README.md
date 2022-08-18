### 开发说明
* 所有后端接口一律强制采用 `@GetMapping` 或 `@PostMapping` 
* 默认 `POST` 请求会记录操作日志, 如果不需要记录则在`PostMapping`同方法上添加 `SkipLogger` 注解
* `GET` 默认采用表单或链接直接携带参数方式, `POST` 使用json方式进行请求 

### 定时任务相关
* 开启定时任务添加`@EnableTask`注解即可
    * 支持数据库配置cron定时,spring原生注解定时,单次执行的定时
    * 在 `job_task` 表中 `bean_name` 字段为要执行定时任务的bean的名称 `cron_expression` 为cron表达式 `bean_method` 为方法名 `args` 方法入参
    * 如需简单定时任务可使用`@Scheduled`
    * 定时任务配置更新后需要 *手动刷新配置* 才能重新生效
    * 只执行一次的定时任务可通过 `TaskConfiguration.addTask()` 实现
    * demo例子`TestJobService` `OnceJobService`
    * 注意: bean方法定义时必须包含一个字符串类型的入参, 且方法必须是`public`, 强烈建议方法上添加 `@ScheduledTask` 注解作为定时任务标注一下


### 其他说明
* 刷新缓存由缓存管理模块统一管理,后台需要开发其他缓存刷新功能,则在`ClearCacheService`,`SystemCacheService`中按指定格式修改
* 异步任务可采用原生`@Async` + `AsyncConfigurer`实现异步+额外记录,也可自定义实现`AbstractTask`或`AbstractAsyncTask`类(增加额外一些日志记录等),统一定义在`TaskHandler`类型调用, `AbstractAsyncTask`接口主要用于获取异步执行的结果,参数类必须实现`AsyncKey`,在自身实现逻辑中可通过`cacheService#cacheAsyncResponse`来缓存结果
* `KeyGenerator` 订单号生成类
* `CacheProxyService` 缓存代理类(为了防止同一个类中调用本类其他带有`@Cacheable`,`@CachePut`注解的方法无效问题而额外增加的类)
* `EmailService` 简单发送邮件的工具类,配合`HtmlTemplate`类可实现发送html的邮件(样式图片必须定义在模板文件中),模板路径放在`spring.freemarker.template-loader-path`即可, `spring.freemarker.settings.template_update_delay` 默认刷新间隔
* `FileService` 文件上传工具类
* `PushService` 极光推送工具类(未测试)
* `SmsService` 短信发送工具类(未接入)
* `HandlerExecute` 精简版责任链工具类(有点鸡肋,没想到适用的场景),通过实现`Handler`接口并标注`HandlerMark`注解
* 后台用户新增 默认密码手机号码后6位