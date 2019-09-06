* 定时任务最好放在manage-web项目中,使用注解定时任务添加`@EnableScheduling`注解即可
* 使用数据库配置事务需要额外在`application.properties`中添加`application.job=true`
    * 在`job_task`表中`bean`字段为要执行定时任务的bean的名称
    * `cron_expression`为cron表达式,如需简单定时任务可使用`@Scheduled`
    * 相应的bean定任务必须实现`com.fanyin.configuration.job.Task`接口
    * 定时任务配置更新后需要手动刷新配置才能重新生效
    * 数据库定时任务支持日志查询
    * demo例子`TestJobService`