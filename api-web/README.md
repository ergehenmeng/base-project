## 移动端接口调用
* 基础必填请求头字段:
    * `Channel` 设备渠道:`IOS`,`ANDROID`
    * `Version` app版本号
    * `Os-Version` 系统版本
    * `Device-Brand` 设备厂商
    * `Device-Model` 设备型号
* 登陆后额外请求头字段:
    * `Access-Token` 登陆成功时,由后台返回给前台
* 验签请求头字段(需登录后):
    * `Signature` 签名信息 MD5(signKey + Base64(json) + Timestamp) signKey由后台动态生成
    * `Timestamp` 时间戳(毫秒值)
* 如非特殊要求全部采用post请求
* 普通接口`application/json`格式 
* 上传文件`multipart/form-data`格式
* 图片采用get请求


### 其他注意事项
* `swagger`访问地址`http://host:ip/swagger/index.html`
* `RequestMapping`标示的方法返回前台数据时,可以为任意对象,最终会由`RespBodyAdviceHandler`包装为`RespBody`对象,如果不想返回前`台RespBody`格式的对象可在方法上添加`@SkipWrapper`
* 系统参数`single_client_login` 用来开启或关闭单客户端最多一个人登陆的校验
* 表`black_roster` 为ip黑名单 可限制某些用户访问
* 默认所有的接口均需要登录才能访问,如果某个接口不需要登陆校验则方法上添加`@SkipAccess`
* 所有接口的请求参数均为映射为对象,如果不需要映射则方法上添加`@SkipDataBinder`
* 所以接口默认均支持android和ios访问,如果不想某类设备访问,方法上添加`@ClientType`
* 后台获取用户id方法`RequestThreadLocal.getUser()`,同理获取其他相关属性也可以通过该类,也可在`RequestMapping`所在的方法上声明一个RequestMessage对象,该对象会自动被注入

