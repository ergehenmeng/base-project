##接口调用
* 请求头字段:

###其他注意事项
* `swagger`访问地址`http://host:ip/swagger/index.html`
* `RequestMapping`返回json时可以为任意对象,最终会由`RespBodyAdviceHandler`包装为`RespBody`对象
* 系统参数`single_client_login` 用来开启或关闭单客户端最多一个人登陆的校验
* 表`black_roster` 为ip黑名单 可限制某些用户访问

