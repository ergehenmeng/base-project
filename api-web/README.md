## 移动端接口调用
* 基础必填请求头字段:
    * `Channel` 设备渠道:`IOS`,`ANDROID`
    * `Version` app版本号
    * `Os-Version` 系统版本
    * `Device-Brand` 设备厂商
    * `Device-Model` 设备型号
* 登陆后额外请求头字段:
    * `Access-Token` 登陆成功时,由后台返回给前台
    * `Refresh-Token` 刷新token,由后台传递给前台
* 默认登陆后的请求响应均加密(`DecryptReqBodyAdviceHandler`,`EncryptRespBodyAdviceHandler`)
    * 请求加密是对所有json参数进行加密 AES.encrypt(jsonBody,secret)
    * 响应数据加密是对data参数进行加密

```json
{
  "code": 200,
  "msg": "success",
  "data": "加密信息" 
}
```

* 如非特殊要求全部采用post请求
* 普通接口`application/json`格式 
* 上传文件`multipart/form-data`格式(不需要进行加解密操作)
* 图片采用get请求


### 其他注意事项
* 一个账户只能登陆一台设备
* `swagger`访问地址`http://host:ip/swagger/index.html`
* `RequestMapping`标示的方法返回前台数据时,可以为任意对象,最终会由`EncryptRespBodyAdviceHandler`包装为`RespBody`对象,如果不想返回前台`RespBody`格式的对象可在方法上添加`@SkipWrapper`,注意如果添加该注解,加密自动实现(相当于添加`@SkipEncrypt`)
* 表`black_roster` 为ip黑名单 可限制某些用户访问
* 默认所有的接口均需要登录才能访问,如果某个接口不需要登陆校验则方法上添加`@SkipAccess`
* 所有接口的请求参数均为映射为对象,如果不需要映射则方法上添加`@SkipDataBinder`
* 需要登陆才能请求的接口均会进行加密解密处理,如果不想进行加解密操作,方法上添加`@SkipEncrypt`或`@SkipDecrypt`
* 所以接口默认均支持android和ios访问,如果不想某类设备访问,方法上添加`@ClientType`
* 所有接口的请求响应日志均会记录到日志文件中,如果不想记录则添加`SkipLogger`(内部采用gson格式化对象)
* 后台获取用户id方法`RequestThreadLocal.getUserId()`,同理获取其他相关属性也可以通过该类,也可在`RequestMapping`所在的方法上声明一个RequestMessage对象,该对象会自动被注入

