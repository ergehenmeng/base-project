## 移动端接口调用
* 基础必填请求头字段:
    * `Channel` 设备渠道:`IOS`,`ANDROID`
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

* post请求接口默认`application/json`格式 
* get请求接口默认`x-www-form-urlencoded`格式
* 上传文件`multipart/form-data`格式(不需要进行加解密操作)


### 其他注意事项
* 一个账户只能登陆一台设备
* `swagger`访问地址`http://host:ip/swagger/index.html`
* `RequestMapping`标示的方法返回前台数据时,可以为任意对象(此时swagger中不会显示RespBody的层级),最终会由`EncryptRespBodyAdviceHandler`包装为`RespBody`对象,如果不想返回前台`RespBody`格式的对象可在方法上添加`@SkipWrapper`,注意如果添加该注解,加密自动实现(相当于添加`@SkipEncrypt`)
* 表`black_roster` 为ip黑名单 可限制某些用户访问
* 默认所有的接口均需要登录才能访问,如果某个接口不需要登陆校验则方法上添加`@SkipAccess`
* post请求接口默认做json格式数据绑定,如果不需要自动绑定则方法上添加`@SkipDataBinder`
* 所以接口默认均支持android和ios访问,如果不想某类设备访问,方法上添加`@ClientType`
* 所有接口的请求响应日志均会记录到日志文件中,如果不想记录则添加`SkipLogger`
* 后台获取用户id方法`ApiHolder.getUserId()`,同理获取其他相关属性也可以通过该类,也可在`@RequestMapping`所在的方法上声明一个RequestMessage对象,该对象会自动被注入
* 涉及分页查询必须继承`PagingQuery`类(内部会校验页容量等信息)
* 分页响应对象最好为`RespBody<Paging<T>>`,做统一的处理
* 禁止使用枚举ordinal()或name()作为参数进行传递,必须显式声明
* 默认post请求有频率限制,具体在`SubmitFrequencyLimitInterceptor`声明
