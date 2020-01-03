### 开发说明
* 页面跳转采用get请求,ajax请求采用post
* `@Mark`注解在Controller类方法上,记录日志专用


### 定时任务相关
* 开启数据库配置定时任务在`application.properties`中添加`application.job=true`
    * 在`job_task`表中`bean`字段为要执行定时任务的bean的名称
    * `cron_expression`为cron表达式,如需简单定时任务可使用`@Scheduled`
    * 相应的bean定任务必须实现`com.eghm.configuration.job.Task`接口
    * 定时任务配置更新后需要手动刷新配置才能重新生效
    * demo例子`TestJobService`
> 如定时任务报错,可能因为服务还没完全启动,SpringContextUtil没有设置ApplicationContext属性

### 权限说明

* 按钮权限,添加@auth标签 nid为system_menu表中定义的按钮唯一标示符
```ftl

<@auth nid='menuManageAdd'>
    str += '<a href="javascript:void(0);" onclick="$.fn.treeGridOptions.editFun('+row.id+',addTitle,winWidth,winHeight,addUrl);"> 添加</a>';
</@auth>

``` 
### 其他说明
* 刷新缓存由缓存管理模块统一管理,后台需要开发其他缓存刷新功能,则在`ClearCacheService`,`SystemCacheService`中按指定格式修改
* 异步任务可采用原生`@Async` + `AsyncConfigurer`实现异步+额外记录,也可自定义实现`AbstractTask`类(增加额外一些日志记录等),统一定义在`TaskHandler`类型调用
* `KeyGenerator` 订单号生成类
* `CacheProxyService` 缓存代理类(为了防止同一个类中调用本类其他带有`@Cacheable`,`@CachePut`注解的方法无效问题而额外增加的类)
* `EmailService` 简单发送邮件的工具类,配合`HtmlTemplate`类可实现发送html的邮件(样式图片必须定义在模板文件中),模板路径放在`spring.freemarker.template-loader-path`即可, `spring.freemarker.settings.template_update_delay` 默认刷新间隔
* `FileService` 文件上传工具类
* `PushService` 极光推送工具类(未测试)
* `SmsService` 短信发送工具类(未接入)
* `HandlerExecute` 精简版责任链工具类(有点鸡肋,没想到适用的场景)
* 后台用户新增 默认手机号码后6位