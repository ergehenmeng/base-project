### 一个基于SpringBoot的基础框架版(完善直至转行)

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
* 所有Bean都使用构造方法注入(防止循环依赖)

### 定时任务相关

* 开启定时任务添加`@EnableTask`注解即可(建议使用)
    * 支持数据库配置cron定时,spring原生注解定时,单次执行的定时
    * 在 `sys_task` 表中 `bean_name` 字段为要执行定时任务的bean的名称 `cron_expression` 为cron表达式 `bean_method`
      为方法名 `args` 方法入参
    * 定时任务配置更新后需要 *手动刷新配置* 才能重新生效
    * 只执行一次的定时任务可通过 `SysTaskRegistrar.addTask()` 实现
    * demo例子`TestJobService` `OnceJobService`
    * 注意: bean方法定义时必须包含一个字符串类型的入参, 且方法必须是 `public`, 强烈建议方法上添加 `@CronTask`
      注解作为定时任务标注一下
* `@Scheduled`(不推荐使用) 定时任务不受 `@EnableTask` 影响

### 其他

* 刷新缓存由缓存管理模块统一管理,后台需要开发其他缓存刷新功能,则在`ClearCacheService`中按指定格式修改
* 异步任务可采用原生`@Async` + `AsyncConfigurer`实现异步或者MQ
* `CacheProxyService` 缓存代理类(为了防止同一个类中调用本类其他带有 `@Cacheable` , `@CachePut`
  注解的方法无效问题而额外增加的类)
* `EmailService` 简单发送邮件的工具类,配合 `HtmlTemplate` 类可实现发送html的邮件(样式图片必须定义在模板文件中)
  ,模板路径放在 `spring.freemarker.template-loader-path` 即可, `spring.freemarker.settings.template_update_delay` 默认刷新间隔
* `FileService` 文件上传工具类
* `SmsService` 短信发送工具类(未接入)
* `HandlerChain` 精简版责任链工具类(有点鸡肋,没想到适用的场景),通过实现 `Handler` 接口并标注 `HandlerMark` 注解
* 后台用户新增 默认密码手机号码后8位
* `EasyExcelUtil` 工具类是针对 `easyexcel` 的二次封装, 如果导出的excel有枚举, 且可以通过枚举做类型转换,
  则可以在枚举字段上添加 `@ExcelProperty(converter = EnumExcelConverter.class)`,
  且枚举需要返回前端的字段上添加 `@ExcelDesc` 注解, 具体可参考 `MenuExportVO` 类

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
* 默认所有的接口不登录即可访问,如果某个接口需要登陆校验则方法上添加 `@AccessToken`
* 所有接口的请求响应日志均会记录到日志文件中,如果不想记录则添加 `@SkipLogger`
* 除配置文件中约定路径,其他任何路径均会有权限校验, 如果特殊接口不需要权限校验可通过 `@SkipPerm` 屏蔽
* 后台获取用户id方法 `ApiHolder.getMemberId()` , 同理获取其他相关属性也可以通过该类,也可在 `@RequestMapping`
  所在的方法上声明一个RequestMessage对象,该对象会自动被注入
* 涉及分页查询必须继承 `PagingQuery` 类(内部会校验页容量等信息)
* 分页响应对象最好为 `RespBody<Paging<T>>` ,做统一的处理
* 禁止使用枚举ordinal()或name()作为参数进行传递,必须显式声明
* 全局默认post请求有频率限制,具体在 `SubmitIntervalInterceptor` 声明, 可在配置文件中调整

### 关于项目加密
* 请参考 `manage-server` 项目中的pom.xml, 使用 `classfinal-maven-plugin` 插件进行加密
* 参考 `classfinal-maven-plugin` 插件文档 https://gitee.com/roseboy/classfinal
* 绑定机器码 `java -jar classfinal-fatjar.jar -C` 在指定机器上生成机器码, 同时会在命令所属目录下生成 `classfinal-code.txt`, 内部就包含code, 将该code复制到pom.xml中的configuration.code节点上打包
* 默认启动方式: `java -javaagent:manage-server-encrypted.jar -jar manage-server-encrypted.jar`, 如果要绑定机器码则在后面需要添加 `-code`

## 其他开发说明
* 管理后台验证码有 `MathCaptchaProducer` `TextCaptchaProducer` 两种方式, 默认为 `MathCaptchaProducer`, 可在`WebMvcConfig#captcha`调整配置
* 管理后台支持账号密码登陆, 短信验证码登陆, 账号密码+短信双认证登录, 扫码登录(未接入)
* 数据库涉及金额的字段, 统一使用 `INT` 类型, 避免浮点数精度丢失
    * POST请求时: 后台可通过 `@JsonDeserialize(using = YuanToCentDecoder.class)` 自动转为分,
      同样后端的分也可以通过 `@JsonSerialize(using = CentToYuanEncoder.class)` 自动转为元返回给前端
    * GET请求时: 前端传递金额格式为元,后端通过 `@YuanToCentFormat` 注解自动转为分.
* `DateFormatter` 日期格式化注解与 `DateTimeFormat` 类似, 支持 `LocalDate` `LocalDateTime` `LocalTime`类型, 在GET请求时,
  前端传递日期格式为范围  `yyyy-MM-dd` 时, 例如: 查询 `2023-11-11` 到 `2023-11-11`的日期, 实际上查询的是 `2023-11-11`
  到 `2023-11-11 23:59:59`,因此需要在前端传递 `2023-11-11`, 后端则会自动转换为 `2023-11-11` 到 `2023-11-12`
* `LongToIpEncoder` `IpToLongDecoder` 前后端IP转换工具类
* `CacheProxyService` 缓存代理层 增加该类的原因: 由于@Cacheable等注解是基于动态代理实现的, 在同一个类中调用另一个方法则换成不会生效,
  因此统一归集到该类中, 即:所有使用SpringCache注解的方法都建议维护到该类中
* `CacheService` 缓存类封装, 建议所有手动设置查询的缓存走该接口, 方便后期维护
* `SystemProperties` 所有本地化配置参数必须强制在该类中定义,方便统一维护, 禁止使用 `@Value`,
* 如果返回前端的数据中有枚举, 需要指定字段作为返回值时, 使用 `@JsonValue` 注解
* POST请求, 如果后端采用枚举作为参数接受时, 使用 `@JsonCreator` 注解
* GET请求, 如果后端采用枚举作为参数接收时, 枚举类型需要继承 `EnumBinder` 接口, 如果希望使用swagger在线调试,
  需要重写 `toString` (`value` + `:` + `desc`) (注意该接口默认前端传递是数字且非下标映射).
* `StopWatch` 打印某段程序耗时的工具
* `TransactionUtil` 事务工具类,防止耗时业务影响事务持有时间(第三方请求等)
* `LoggerUtil` 规范日志打印
* `BeanValidator` 校验某个pojo是否满足其注解要求 (`NotEmpty`, `NotNull` 等)
* `DataUtil` 对象copy工具类
* `@ExcelDict` 导出Excel时, 如果数据库保存的字段为数据字典,则可以通过该自动自动转换为格式化好的文本 (前提:
  数据字典必须已定义)
* `XssEncoder` xss过滤工具, 防止xss攻击,使用方式: 在需要过滤xss的字段上添加 `@JsonDeserialize(using = XssEncoder.class)`
* `com.eghm.validation.annotation` 包有自定义校验注解, 可根据实际场景使用. **注意:** `@DateCompare` 日期比较, 需要 pojo
  继承 `DateComparator` 或者 `DatePagingComparator` (一个带分页,一个不带分页), 或者在 pojo定义以便于实现特殊提示语.
* `@WordChecker` 是敏感词校验注解
* `@Desensitization` 脱敏注解,添加到需要进行脱敏的字段上, 例如: `@Desensitization(FieldType.MOBILE_PHONE)`
* 签名功能, 目前支持 `MD5` `RSA` 两种, 在管理后台 `授权管理` 增加第三方商户信息, 同时将生成的 `appKey` 和 `appSecret`
  给第三方, 第三方在请求接口时,需要在请求头带上 `appKey` 、 `signature` `timestmap` 三个字段. 目前 `@SignCheck`
  只支持 `POST` 请求, 可在 `SignCheckInterceptor` 拦截器中进行二次扩展:
    * `MD5` **`signature`=MD5(appSecret=`appSecret`&data=(Base64(`requestBody`))&timestamp=`timestamp`)**
      其中 `requestBody` 是 post请求体中的数据, `timestamp` 是当前时间(毫秒). 注意:即使请求体为空也需要带上 `data` 字段
    * `RSA` **`signature`=SHA256withRSA(data=Base64(`requestBody`)&timestamp=`timestamp`)** 其中 `requestBody` 是
      post请求体中的数据, `timestamp` 是当前时间(毫秒). `appSecret` 是 `RSA` 的私钥, 采用 `SHA256withRSA` 方式进行签名
* 注意: `TransactionConfig`中定义的事务管理器作用在 `com.eghm.service` 包, 因此在该包不要定义非事务相关的业务(例如第三方接口调用, 工具类等), 否则可能会导致事务异常.
* 管理后台集成了Websocket, 采用stomp协议, 支持前端实时接收消息, 具体可参考 `WebSocketController`, 后台主动发送消息可注入 `SimpMessagingTemplate` 发送, 注意:需要移动端先订阅消息才可收到消息 
* 移动端服务和管理后台服务是独立的, 双方之间如需通信, 可通过MQ, 注意: 消息消费端定义的位置
* `com.eghm.convertor` 包下有各种转换器, 主要是用于序列化,反序列化,Excel导出格式化转换器, 请根据实际场景使用