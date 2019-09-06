###定时任务相关
* 开启数据库配置定时任务添加`@EnableScheduling`后需要额外在`application.properties`中添加`application.job=true`
    * 在`job_task`表中`bean`字段为要执行定时任务的bean的名称
    * `cron_expression`为cron表达式,如需简单定时任务可使用`@Scheduled`
    * 相应的bean定任务必须实现`com.fanyin.configuration.job.Task`接口
    * 定时任务配置更新后需要手动刷新配置才能重新生效
    * 数据库定时任务支持日志查询
    * demo例子`TestJobService`
> 如定时任务报错,可能因为服务还没完全启动,SpringContextUtil没有设置ApplicationContext属性