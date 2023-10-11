### 一个基于SpringBoot的基础框架版,包含后台管理系统母版和移动端母版
### 完善直至转行
> 集百度之所长+强迫症思维模式而迭代出来的项目  

#### 运行环境及技术
* java1.8+
* redis3.2+
* mysql5.7+
* spring boot 2.7.8
* spring security(废弃)
* mybatis
* freemarker
* nginx

## 管理后台

### 约定
* 所有后端接口一律强制采用 `@GetMapping` 或 `@PostMapping`
* 默认 `POST` 请求会记录操作日志, 如果不需要记录则在 `@PostMapping` 同方法上添加 `@SkipLogger` 注解
* `GET` 默认采用表单或链接直接携带参数方式, `POST` 使用json方式进行请求

### 定时任务相关
* 开启定时任务添加`@EnableTask`注解即可(建议使用)
    * 支持数据库配置cron定时,spring原生注解定时,单次执行的定时
    * 在 `job_task` 表中 `bean_name` 字段为要执行定时任务的bean的名称 `cron_expression` 为cron表达式 `bean_method` 为方法名 `args` 方法入参
    * 定时任务配置更新后需要 *手动刷新配置* 才能重新生效
    * 只执行一次的定时任务可通过 `TaskConfiguration.addTask()` 实现
    * demo例子`TestJobService` `OnceJobService`
    * 注意: bean方法定义时必须包含一个字符串类型的入参, 且方法必须是 `public`, 强烈建议方法上添加 `@ScheduledTask` 注解作为定时任务标注一下
* `@Scheduled`(不推荐使用) 定时任务不受 `@EnableTask` 影响
    
### 其他
* 刷新缓存由缓存管理模块统一管理,后台需要开发其他缓存刷新功能,则在`ClearCacheService`,`SystemCacheService`中按指定格式修改
* 异步任务可采用原生`@Async` + `AsyncConfigurer`实现异步或者MQ
* `KeyGenerator` 订单号生成类
* `CacheProxyService` 缓存代理类(为了防止同一个类中调用本类其他带有 `@Cacheable` , `@CachePut` 注解的方法无效问题而额外增加的类)
* `EmailService` 简单发送邮件的工具类,配合 `HtmlTemplate` 类可实现发送html的邮件(样式图片必须定义在模板文件中),模板路径放在 `spring.freemarker.template-loader-path` 即可, `spring.freemarker.settings.template_update_delay` 默认刷新间隔
* `FileService` 文件上传工具类
* `PushService` 极光推送工具类(未测试)
* `SmsService` 短信发送工具类(未接入)
* `HandlerChain` 精简版责任链工具类(有点鸡肋,没想到适用的场景),通过实现 `Handler` 接口并标注 `HandlerMark` 注解
* 后台用户新增 默认密码手机号码后6位
* `ExcelUtil` 工具类是针对 `easyexcel` 的二次封装, 如果导出的excel有枚举, 且可以通过枚举做类型转换, 则可以在枚举字段上添加 `@ExcelProperty(converter = EnumExcelConverter.class)`, 且枚举需要返回前端的字段上添加 `@ExcelValue` 注解, 具体可参考 `MenuExportVO` 类

## 移动端

### 约定
* 基础必填请求头字段:
    * `Channel` 设备渠道: `IOS` , `ANDROID`
    * `Version` app版本号
    * `Os-Version` 系统版本
    * `Device-Brand` 设备厂商
    * `Device-Model` 设备型号
    * `Serial-Number` 设备唯一序列号
* 登陆后额外请求头字段:
    * `Token` 登陆成功时,由后台返回给前台
    * `Refresh-Token` 刷新token,由后台传递给前台
* 响应结果 code=200为成功,其他均有异常或错误

```json
{
  "code": 200, 
  "msg": "success",
  "data": "xxx" 
}
```

* post请求接口默认 `application/json` 格式
* get请求接口默认 `x-www-form-urlencoded` 格式
* 上传文件 `multipart/form-data` 格式(不需要进行加解密操作)
* **涉及分页列表,不建议返回总条数**


### 其他注意事项
* 一个账户只能登陆一台设备
* `swagger` 地址会在服务启动时打印出来地址,访问地址 `http://host:ip/doc.html`
* 表 `black_roster` 为ip黑名单 可限制某些用户访问
* 默认所有的接口不登录即可访问,如果某个接口需要登陆校验则方法上添加 `@AccessToken`
* 所以接口默认均支持android和ios访问,如果不想某类设备访问,方法上添加 `@ClientType`
* 所有接口的请求响应日志均会记录到日志文件中,如果不想记录则添加 `@SkipLogger`
* 除配置文件中约定路径,其他任何路径均会有权限校验, 如果特殊接口不需要权限校验可通过 `@SkipPerm` 屏蔽
* 后台获取用户id方法 `ApiHolder.getMemberId()` , 同理获取其他相关属性也可以通过该类,也可在 `@RequestMapping` 所在的方法上声明一个RequestMessage对象,该对象会自动被注入
* 涉及分页查询必须继承 `PagingQuery` 类(内部会校验页容量等信息)
* 分页响应对象最好为 `RespBody<Paging<T>>` ,做统一的处理
* 禁止使用枚举ordinal()或name()作为参数进行传递,必须显式声明
* 全局默认post请求有频率限制,具体在 `SubmitIntervalInterceptor` 声明, 可在配置文件中调整, 同时可通过 `@SubmitInterval` 调整个别接口的频率
* `ProductType` 商品类型枚举, 提供常用方法, 例如: 生成订单编号 `generateOrderNo()`, 生成交易单号 `generateSerialNo` 


## 其他开发说明
* `CacheProxyService` 缓存代理层 增加该类的原因: 由于@Cacheable等注解是基于动态代理实现的, 在同一个类中调用另一个方法则换成不会生效, 因此统一归集到该类中, 即:所有使用SpringCache注解的方法都建议维护到该类中
* `CacheService` 缓存类封装, 建议所有手动设置查询的缓存走该接口, 方便后期维护
* `SystemProperties` 所有本地化配置参数必须强制在该类中定义,方便统一维护, 禁止使用 `@Value`,
* 如果返回前端的数据中有枚举, 需要指定字段作为返回值时, 使用 `@JsonValue` 注解
* POST请求, 如果后端采用枚举作为参数接受时, 使用 `@JsonCreator` 注解
* GET请求, 如果后端采用枚举作为参数接收时, 枚举类型需要继承 `EnumBinder` 接口 (注意该接口默认前端传递是数字且非下标映射), 具体例子查看 `webapp/item/couponScope` 接口