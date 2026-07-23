# API Version Spring Boot Starter

基于 Spring MVC 自定义 `RequestCondition` 实现的 API 版本路由 Starter。

客户端通过请求携带版本号，服务端使用 `@ApiVersion` 为同一个 HTTP 接口声明多个版本实现。Starter 会从所有兼容的实现中选择版本最高的一个，同时支持接口废弃提示、拦截路径配置以及自定义版本解析和比较策略。

## 功能特性

- 支持在 Controller 类型或处理方法上声明 API 版本。
- 同一路径存在多个版本实现时，自动选择不高于客户端版本的最高版本。
- 默认从可配置的 HTTP 请求头读取客户端版本。
- 支持配置拦截路径、排除路径和拦截器顺序。
- 支持通过响应头提示客户端接口已经废弃。
- `ApiVersionResolver` 和 `ApiVersionComparator` 均可由应用自行替换。
- 使用 Spring Boot 自动配置，引入依赖后即可生效。
- 不依赖项目内的 `base-core`、`base-web` 等基础模块。

## 环境要求

- Java 17 或更高版本。
- Maven 3.6.3 或更高版本。
- Spring Boot 3.x。
- Spring MVC Servlet Web 应用。

当前项目基线为 Java 17 和 Spring Boot 3.2.12。

## 引入依赖

模块 Maven 坐标：

```xml
<dependency>
    <groupId>com.eghm</groupId>
    <artifactId>api-version-spring-boot-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

Starter 会传递引入 `spring-boot-starter-web`。如果应用已经使用 Spring MVC，无需增加其他依赖。

## 快速开始

### 声明接口版本

下面两个方法拥有相同的 HTTP 方法和路径，但分别从 `1.0.0` 和 `2.0.0` 开始提供服务：

```java
package com.example.web;

import com.eghm.apiversion.annotation.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @ApiVersion("1.0.0")
    @GetMapping
    public String profileV1() {
        return "profile-v1";
    }

    @ApiVersion("2.0.0")
    @GetMapping
    public String profileV2() {
        return "profile-v2";
    }
}
```

### 发起请求

默认使用 `Version` 请求头传递客户端版本：

```bash
curl -H "Version: 1.5.0" http://localhost:8080/api/profile
```

响应来自 `profileV1()`。

```bash
curl -H "Version: 2.1.0" http://localhost:8080/api/profile
```

响应来自 `profileV2()`。

## 版本匹配规则

假设同一个接口声明了 `1.0.0`、`2.0.0` 和 `2.5.0` 三个版本：

| 客户端版本 | 匹配结果 | 说明 |
| --- | --- | --- |
| `1.0.0` | `1.0.0` | 精确匹配 |
| `1.8.0` | `1.0.0` | 选择不高于客户端版本的最高实现 |
| `2.0.0` | `2.0.0` | 精确匹配 |
| `2.8.0` | `2.5.0` | 多个版本兼容时选择最高版本 |
| `0.9.0` | 不匹配 | 没有不高于客户端版本的实现 |
| 未携带版本 | 不匹配 | 版本解析器返回空值 |
| 非法版本 | 不匹配 | 默认比较器抛出格式异常并记录警告日志 |

如果请求没有匹配的其他 Controller 映射，Spring MVC 通常返回 `404`。

默认比较器面向 `x.y.z` 数字版本，每段最多两位，推荐使用 `1.0.0` 至 `99.99.99` 格式。版本中的 `v` 或 `V` 字符会被忽略，例如 `v2.1.0` 可以正常参与比较。

> 同一 HTTP 方法和路径不建议混合使用“带 `@ApiVersion`”与“不带 `@ApiVersion`”的处理方法，以免无版本条件的映射影响预期路由。

## 在 Controller 类型上声明版本

`@ApiVersion` 也可以标注在 Controller 类型上，该类型中的处理方法默认继承该版本：

```java
@RestController
@RequestMapping("/api/orders")
@ApiVersion("2.0.0")
public class OrderController {

    @GetMapping
    public String list() {
        return "order-v2";
    }

    @ApiVersion("3.0.0")
    @GetMapping("/detail")
    public String detail() {
        return "order-detail-v3";
    }
}
```

方法上的 `@ApiVersion` 会覆盖类型上的版本声明。

## 接口废弃提示

通过 `deprecated` 和 `deprecatedMessage` 标识已经废弃但仍然可访问的接口版本：

```java
@ApiVersion(
        value = "1.0.0",
        deprecated = true,
        deprecatedMessage = "该版本将在 2026-12-31 下线，请升级到 2.0.0"
)
@GetMapping("/api/example")
public String deprecatedApi() {
    return "legacy-response";
}
```

命中该方法后，默认响应头为：

```text
Api-Deprecated: true
Api-Deprecated-Message: 该版本将在 2026-12-31 下线，请升级到 2.0.0
```

废弃标识只向客户端传递接口生命周期信息，不会阻止请求执行。`deprecatedMessage` 为空时，只返回废弃标识响应头。

## 配置项

配置前缀为 `application.api-version`。

| 配置项 | 类型 | 默认值 | 说明 |
| --- | --- | --- | --- |
| `enabled` | `boolean` | `true` | 是否启用 API 版本自动配置 |
| `header-name` | `String` | `Version` | 默认解析器读取的客户端版本请求头 |
| `deprecated-header-name` | `String` | `Api-Deprecated` | 接口废弃标识响应头名称 |
| `deprecated-message-header-name` | `String` | `Api-Deprecated-Message` | 接口废弃消息响应头名称 |
| `path-patterns` | `List<String>` | `/**` | 注册废弃提示拦截器的包含路径 |
| `exclude-path-patterns` | `List<String>` | 空列表 | 注册废弃提示拦截器的排除路径 |
| `order` | `int` | `-2147483648` | 拦截器执行顺序，数值越小优先级越高 |

完整配置示例：

```yaml
application:
  api-version:
    enabled: true
    header-name: Version
    deprecated-header-name: Api-Deprecated
    deprecated-message-header-name: Api-Deprecated-Message
    path-patterns:
      - "/api/**"
    exclude-path-patterns:
      - "/api/public/**"
      - "/api/health"
    order: -2147483648
```

需要关闭 Starter 时：

```yaml
application:
  api-version:
    enabled: false
```

## 扩展版本来源

默认的 `HeaderApiVersionResolver` 从 `header-name` 指定的请求头读取版本。注册自定义 `ApiVersionResolver` Bean 后，自动配置不会再创建默认解析器。

例如，从查询参数 `apiVersion` 中读取版本：

```java
package com.example.config;

import com.eghm.apiversion.spi.ApiVersionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiVersionConfiguration {

    @Bean
    public ApiVersionResolver queryParameterApiVersionResolver() {
        return request -> request.getParameter("apiVersion");
    }
}
```

请求示例：

```text
GET /api/profile?apiVersion=2.0.0
```

解析器也可以从 URL 路径、JWT、媒体类型或其他请求信息中获取版本。未携带版本时应返回 `null` 或空字符串。

## 扩展版本比较规则

默认的 `NumericApiVersionComparator` 适用于三段式数字版本。注册自定义 `ApiVersionComparator` Bean 后，可以支持语义化版本、日期版本或业务自定义版本。

```java
package com.example.config;

import com.eghm.apiversion.spi.ApiVersionComparator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ApiVersionComparatorConfiguration {

    @Bean
    public ApiVersionComparator apiVersionComparator() {
        return (left, right) -> Arrays.compare(parse(left), parse(right));
    }

    private int[] parse(String version) {
        return Arrays.stream(version.replaceFirst("^[vV]", "").split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
```

比较器必须遵循以下约定：

- 左侧版本小于右侧版本时返回负数。
- 两个版本相等时返回 `0`。
- 左侧版本大于右侧版本时返回正数。
- 无法解析版本时建议抛出 `NumberFormatException`。

## 自动配置覆盖规则

Starter 默认注册以下组件：

| Bean 类型 | 默认实现 | 覆盖方式 |
| --- | --- | --- |
| `ApiVersionResolver` | `HeaderApiVersionResolver` | 注册同类型 Bean |
| `ApiVersionComparator` | `NumericApiVersionComparator` | 注册同类型 Bean |
| `ApiVersionInterceptor` | `ApiVersionInterceptor` | 注册同类型 Bean |
| `WebMvcRegistrations` | 创建 `ApiVersionRequestMappingHandlerMapping` | 应用中不存在其他 `WebMvcRegistrations` 时注册 |

如果应用已经声明了自己的 `WebMvcRegistrations`，Starter 不会再次注册该 Bean。此时若仍需版本路由，需要由应用自己的 `WebMvcRegistrations` 返回 `ApiVersionRequestMappingHandlerMapping`，或合并相应能力。

## 工作原理

请求处理过程如下：

1. 自动配置使用 `ApiVersionRequestMappingHandlerMapping` 替换默认的请求映射处理器。
2. HandlerMapping 将 Controller 或方法上的 `@ApiVersion` 转换为 `ApiVersionCondition`。
3. `ApiVersionResolver` 从当前请求解析客户端版本。
4. `ApiVersionComparator` 判断客户端版本是否兼容接口声明版本。
5. 多个条件均匹配时，Spring MVC 选择声明版本最高的方法。
6. 方法被调用前，`ApiVersionInterceptor` 检查废弃标记并写入响应头。

## 包结构

```text
com.eghm.apiversion
├── annotation      @ApiVersion 注解
├── autoconfigure   Spring Boot 自动配置
├── condition       Spring MVC 版本匹配条件
├── config          Starter 配置属性
├── interceptor     接口废弃提示拦截器
├── mvc             版本化 HandlerMapping
├── spi             解析器和比较器扩展接口
└── support         默认扩展实现
```

自动配置入口声明在：

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## 构建与验证

在项目根目录使用 Java 17 和 Maven 3.6.3 以上版本执行：

```bash
mvn -pl :api-version-spring-boot-starter -am test
```

## 常见问题

### 请求携带版本后仍然返回 404

依次检查：

1. Controller 或方法是否声明了 `@ApiVersion`。
2. 请求头名称是否与 `application.api-version.header-name` 一致。
3. 客户端版本是否大于或等于接口声明版本。
4. 版本号是否符合当前 `ApiVersionComparator` 支持的格式。
5. 应用是否注册了自定义 `WebMvcRegistrations`，导致 Starter 的 HandlerMapping 未被创建。

### 修改 `path-patterns` 后版本路由没有变化

`path-patterns` 和 `exclude-path-patterns` 控制的是废弃提示拦截器范围，不控制 `@ApiVersion` 的路由匹配范围。版本路由由 `ApiVersionRequestMappingHandlerMapping` 统一处理。

### 如何保持没有版本头的旧客户端可用

当前默认策略中，缺少版本的请求不会匹配带 `@ApiVersion` 的接口。可以注册自定义 `ApiVersionResolver`，在请求未携带版本时返回约定的兼容版本：

```java
@Bean
public ApiVersionResolver backwardCompatibleApiVersionResolver() {
    return request -> {
        String version = request.getHeader("Version");
        return version == null || version.isBlank() ? "1.0.0" : version;
    };
}
```

### 非法版本会产生什么结果

默认比较器解析失败时会抛出 `NumberFormatException`。版本条件会记录警告日志，并将当前版本化接口视为不匹配，不会直接向客户端抛出该异常。
