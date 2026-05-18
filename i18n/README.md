## 国际化支持
### 功能描述
#### 配置文件
```yml
i18n:
  # 是否启用国际化模块 (默认true, 设为false则完全禁用) 例如: `X-Language: zh-CN`
  enabled: true
  # 请求头名称 (用于传递语言标识)
  header-name: X-Language 
```
#### 使用说明
* 后端必须实现`I18nMessageProvider`接口，用于提供国际化消息的具体实现(自行实现数据字典或缓存等)
* 后端返回前端对象必须实现 `RespBodyProvider` 接口，用于错误信息的国际化处理

#### 参数异常示例：

```java
import com.eghm.i18n.annotation.Translation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class User {

  @NotBlank(message = "{user.username.notblank}")
  @Size(min = 2, max = 10, message = "{user.username.size}")
  private String name;

  @Min(value = 18, message = "{age.gt}")
  private Integer age;

  @Translation(value = "user_status")
  private Integer status;
}

```
#### I18nMessageProvider请求和响应：

* name.notblank = "姓名不能为空" | Username must not be blank
* user.username.size = "姓名长度必须在{min}到{max}个字符之间" | Username length must be between {min} and {max} characters
* age.gt = "年龄必须大于{value}" | Age must be greater than {value}
* 注意: User作为请求参数时,需要添加 @Validated(推荐) @Valid校验注解,并根据校验结果返回国际化错误信息
  * 建议数据字典key=validator统一维护校验错误信息
* 注意: User作为返回值时, @Translation会生效, user_status为数据字典key, 1 = "正常" | Normal
  * 建议数据字典key按功能模块进行维护区分

#### 业务异常示例：

> 如果业务异常,需要在业务层抛出异常,异常信息会自动被国际化处理 是根据返回前端的code != 200 进行国际化处理
  * 建议数据字典key=error_code统一维护业务错误信息
  * 错误码作为异常信息的key, 例如: 40001 = "用户名已存在" | Username already exists


