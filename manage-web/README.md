###开发说明
* 页面跳转采用get请求,ajax请求采用post
* `@Mark`注解在Controller类方法上,记录日志专用


###定时任务相关
* 开启数据库配置定时任务在`application.properties`中添加`application.job=true`
    * 在`job_task`表中`bean`字段为要执行定时任务的bean的名称
    * `cron_expression`为cron表达式,如需简单定时任务可使用`@Scheduled`
    * 相应的bean定任务必须实现`com.fanyin.configuration.job.Task`接口
    * 定时任务配置更新后需要手动刷新配置才能重新生效
    * 数据库定时任务支持日志查询
    * demo例子`TestJobService`
> 如定时任务报错,可能因为服务还没完全启动,SpringContextUtil没有设置ApplicationContext属性

###权限说明

* 按钮权限,添加@auth标签 nid为system_menu表中定义的按钮唯一标示符
```ftl

<@auth nid='menuManageAdd'>
    str += '<a href="javascript:void(0);" onclick="$.fn.treeGridOptions.editFun('+row.id+',addTitle,winWidth,winHeight,addUrl);"> 添加</a>';
</@auth>

``` 


