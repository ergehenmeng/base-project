# 项目代码规范化重构方案

## 一、项目概述

**项目名称**: base-project  
**技术栈**: Spring Boot 3.2.12 + MyBatis-Plus + RabbitMQ + Redis + MySQL  
**当前模块**: 
- `parent` (根 POM)
- `service` (核心业务服务层)
- `webapp-server` (移动端 API 服务)
- `manage-server` (管理后台 API 服务)
- `i18n` (国际化模块)

**目标**: 在不改变任何业务逻辑的前提下，通过多模块拆分和包路径调整，使代码结构更加规范、清晰，便于后期扩展和维护。

---

## 二、现状分析

### 2.1 当前模块结构

```
base-project/
├── pom.xml (parent)
├── service/              # 核心业务模块（包含所有业务逻辑）
├── webapp-server/        # 移动端 Web 服务
├── manage-server/        # 管理后台 Web 服务
└── i18n/                 # 国际化模块
```

### 2.2 当前 service 模块包结构问题

`service` 模块目前是一个"大杂烩"，包含了：
- **基础设施层**: configuration, utils, constants, exception, enums, annotation
- **数据访问层**: mapper, model
- **业务服务层**: service (business/operate/sys), common, cache, lock, manager
- **数据传输层**: dto, vo, convertor, excel
- **消息队列**: mq (listener, service)
- **支付模块**: pay (dto, enums, service, vo)
- **处理器**: handler (chain, email, mysql)
- **事件**: event

**主要问题**:
1. **模块职责不清晰**: service 模块既包含基础设施，又包含业务逻辑，还包含数据访问
2. **包层级混乱**: 
   - `common` 包放置了太多通用服务（AlarmService, CommonService, EmailService, FileService 等）
   - `configuration` 包过于庞大，包含各种配置类
   - `dto` 和 `vo` 分散在不同业务子包中，但命名不统一
3. **扩展性差**: 如果要添加新的业务模块（如订单模块、商品模块），需要在 service 中多个包下添加文件
4. **依赖关系不清晰**: 所有代码在一个模块中，无法清晰区分基础设施依赖和业务依赖
5. **配置类引用了具体实现**: `WebMvcConfig`、`OssConfig`、`SmsConfig`、`TokenConfig` 等配置类在 `configuration/` 中直接引用 `common/impl/` 下的具体实现类，违反了依赖倒置原则

### 2.3 当前包路径示例

```
com.eghm
├── annotation/          # 自定义注解
├── cache/               # 缓存服务
├── common/              # 通用服务（过于宽泛）
│   ├── impl/
│   │   ├── AbstractAlarmService.java
│   │   ├── AliOssFileServiceImpl.java
│   │   ├── CommonServiceImpl.java
│   │   ├── EmailServiceImpl.java
│   │   └── ...
│   ├── AlarmService.java
│   ├── CommonService.java
│   └── ...
├── configuration/       # 配置类（过于庞大，且部分引用具体实现）
│   ├── authentication/
│   ├── config/
│   ├── data/
│   ├── encoder/
│   ├── gson/
│   ├── interceptor/
│   ├── jackson/
│   ├── log/
│   ├── rabbit/
│   ├── ratelimit/
│   ├── task/
│   ├── template/
│   ├── version/
│   └── *.java (20+ 配置类，其中 OssConfig/SmsConfig/TokenConfig 引用了具体实现)
├── constants/           # 常量
├── convertor/           # 转换器
├── dto/                 # 数据传输对象
│   ├── business/
│   ├── ext/
│   ├── operate/
│   ├── sys/
│   └── wechat/
├── enums/               # 枚举
├── event/               # 事件
├── excel/               # Excel 处理
├── exception/           # 异常
├── handler/             # 处理器
│   ├── chain/
│   ├── email/
│   └── mysql/
├── lock/                # 分布式锁
├── manager/             # 管理器
├── mapper/              # MyBatis Mapper
├── model/               # 实体类
├── mq/                  # 消息队列
│   ├── listener/
│   └── service/
├── pay/                 # 支付模块（相对独立）
│   ├── dto/
│   ├── enums/
│   ├── service/
│   └── vo/
├── service/             # 业务服务
│   ├── business/
│   ├── operate/
│   └── sys/
├── utils/               # 工具类
└── vo/                  # 视图对象
    ├── business/
    ├── login/
    ├── operate/
    ├── sys/
    └── wechat/
```

### 2.4 关键依赖关系分析（代码审查发现）

经过对实际代码的审查，发现以下关键依赖关系：

**① DTO/VO 被服务层引用**：`service/` 下的接口和实现都大量引用了 `dto/` 和 `vo/` 下的类。例如：
- `MemberService.java` 导入了 `dto.business.member.*`、`dto.sys.login.*`、`dto.sys.register.*`
- `CommonServiceImpl.java` 导入了 `vo.sys.ext.SysAreaVO`
- `LoginServiceImpl.java` 导入了 `vo.business.member.LoginDeviceVO`

**② 配置类引用了具体实现**：`configuration/` 下有多个工厂配置类直接引用了 `common/impl/` 下的具体类：
- `WebMvcConfig.java` 导入了 `DefaultAlarmServiceImpl`、`DingTalkAlarmServiceImpl`、`WeChatAlarmServiceImpl`、`FeiShuAlarmServiceImpl`
- `OssConfig.java` 导入了 `AliOssFileServiceImpl`、`SysConfigApi`、`SystemFileServiceImpl`
- `SmsConfig.java` 导入了 `AliSmsServiceImpl`、`DefaultSmsServiceImpl`、`TencentSmsServiceImpl`
- `TokenConfig.java` 导入了 `JwtUserTokenServiceImpl`、`RedisUserTokenServiceImpl`

---

## 三、重构方案

### 3.1 多模块拆分策略

将当前 `service` 模块拆分为以下子模块：

```
base-project/
├── pom.xml (parent)
├── eghm-common/              # 通用基础模块（注解、常量、枚举、异常、工具类、通用DTO）
├── eghm-dao/                 # 数据访问模块（mapper + model + MySQL类型处理器）
├── eghm-service-api/         # 服务接口模块（所有 Service 接口 + DTO + VO + 支付定义）
├── eghm-service-impl/        # 服务实现模块（所有 Service 实现 + 工厂配置类 + 处理器）
├── eghm-web-common/          # Web 通用模块（Web 层公共配置、拦截器、转换器、过滤器）
├── webapp-server/            # 移动端 Web 服务（保持不变）
├── manage-server/            # 管理后台 Web 服务（保持不变）
└── i18n/                     # 国际化模块（保持不变）
```

**模块依赖关系**:
```
eghm-common (基础)
    ↑
eghm-dao (数据访问)
    ↑
eghm-service-api (服务接口 + DTO/VO)
    ↑
eghm-service-impl (服务实现 + 工厂配置)
    ↑
eghm-web-common (Web 通用)
    ↑
webapp-server / manage-server (应用层)
```

### 3.2 各模块职责与包结构

#### 3.2.1 eghm-common (通用基础模块)

**职责**: 提供项目通用的基础设施代码，不依赖任何业务模块

**包路径**: `com.eghm.common`

**包结构**:
```
com.eghm.common
├── annotation/              # 自定义注解
│   ├── ApiVersion.java
│   ├── Assign.java
│   ├── CronMark.java
│   ├── DateFormatter.java
│   ├── Desensitization.java
│   ├── EnableSchedulingTask.java
│   ├── ExcelDesc.java
│   ├── ExcelDict.java
│   ├── ExcelSpinner.java
│   ├── JsonDesc.java
│   ├── RateLimiter.java
│   ├── SkipLogger.java
│   ├── SkipPerm.java
│   └── YuanToCentFormat.java
├── constants/               # 常量定义
│   ├── ApplicationHeader.java
│   ├── CacheConstant.java
│   ├── CommonConstant.java
│   ├── ConfigConstant.java
│   ├── DictConstant.java
│   ├── LockConstant.java
│   ├── QueueConstant.java
│   └── WeChatConstant.java
├── enums/                   # 通用枚举
│   ├── AlarmType.java
│   ├── Channel.java
│   ├── CollectType.java
│   ├── DataType.java
│   ├── DirectionType.java
│   ├── DisplayState.java
│   ├── EmailType.java
│   ├── Env.java
│   ├── ErrorCode.java
│   ├── ExchangeQueue.java
│   ├── ExchangeType.java
│   ├── FeedbackType.java
│   ├── FieldType.java
│   ├── Gender.java
│   ├── LoginType.java
│   ├── MemberState.java
│   ├── MessageType.java
│   ├── ObjectType.java
│   ├── ReportType.java
│   ├── RoleType.java
│   ├── ScoreType.java
│   ├── SelectType.java
│   ├── SmsChannel.java
│   ├── TemplateType.java
│   ├── TokenType.java
│   ├── UploadType.java
│   ├── UserState.java
│   ├── UserType.java
│   └── WeChatVersion.java
├── exception/               # 异常定义
│   ├── AliPayException.java
│   ├── BusinessException.java
│   ├── DataException.java
│   ├── ParameterException.java
│   ├── SystemException.java
│   └── WeChatPayException.java
├── utils/                   # 工具类
│   ├── BeanValidator.java
│   ├── CacheUtil.java
│   ├── DataUtil.java
│   ├── DateUtil.java
│   ├── DecimalUtil.java
│   ├── EasyExcelUtil.java
│   ├── EncUtil.java
│   ├── ExceptionUtil.java
│   ├── FileUtil.java
│   ├── ImageUtil.java
│   ├── IpUtil.java
│   ├── LoggerUtil.java
│   ├── MusicUtil.java
│   ├── MybatisUtil.java
│   ├── RateLimiterUtil.java
│   ├── RedEnvelopeUtil.java
│   ├── RegExpUtil.java
│   ├── ResourceUtil.java
│   ├── StopWatch.java
│   ├── StringUtil.java
│   ├── TimingWheelUtil.java
│   ├── TotpUtil.java
│   ├── TransactionUtil.java
│   ├── TreeUtil.java
│   ├── ValidationUtil.java
│   ├── VersionUtil.java
│   └── WebUtil.java
├── dto/                     # 通用 DTO（被所有层使用）
│   ├── ext/                 # 扩展 DTO
│   │   ├── AbstractDateComparator.java
│   │   ├── AbstractDatePagingComparator.java
│   │   ├── ActionRecord.java
│   │   ├── AlarmMsg.java
│   │   ├── AsyncKey.java
│   │   ├── CheckBox.java
│   │   ├── ExcelStyle.java
│   │   ├── FeiShuMsg.java
│   │   ├── FilePath.java
│   │   ├── LocalDateCompare.java
│   │   ├── LoginRecord.java
│   │   ├── MemberRegister.java
│   │   ├── MemberToken.java
│   │   ├── PageData.java
│   │   ├── PagingQuery.java
│   │   ├── RequestMessage.java
│   │   ├── RespBody.java
│   │   ├── SendNotice.java
│   │   ├── UserToken.java
│   │   └── VerifyEmailCode.java
│   ├── IdDTO.java
│   ├── IdRequest.java
│   ├── SortByDTO.java
│   └── StateRequest.java
└── event/                   # 通用事件
    └── PermissionRefreshEvent.java
```

#### 3.2.2 eghm-dao (数据访问模块)

**职责**: 数据访问层，包含实体类和 MyBatis Mapper

**包路径**: `com.eghm.dao`

**依赖**: `eghm-common`

**包结构**:
```
com.eghm.dao
├── model/                   # 实体类
│   ├── AppVersion.java
│   ├── AuthConfig.java
│   ├── Banner.java
│   ├── BaseEntity.java
│   ├── BlackRoster.java
│   ├── Comment.java
│   ├── CommentReport.java
│   ├── EmailTemplate.java
│   ├── Family.java
│   ├── FeedbackLog.java
│   ├── HelpCenter.java
│   ├── ImageLog.java
│   ├── LoginDevice.java
│   ├── LoginLog.java
│   ├── ManageLog.java
│   ├── Member.java
│   ├── MemberCollect.java
│   ├── MemberInviteLog.java
│   ├── MemberNotice.java
│   ├── MemberNoticeLog.java
│   ├── MemberScoreLog.java
│   ├── News.java
│   ├── NewsConfig.java
│   ├── NoticeTemplate.java
│   ├── PayNotifyLog.java
│   ├── PayRequestLog.java
│   ├── SensitiveWord.java
│   ├── SmsLog.java
│   ├── SysArea.java
│   ├── SysCache.java
│   ├── SysConfig.java
│   ├── SysDept.java
│   ├── SysDeptData.java
│   ├── SysDict.java
│   ├── SysDictItem.java
│   ├── SysMenu.java
│   ├── SysNotice.java
│   ├── SysRole.java
│   ├── SysTask.java
│   ├── SysTaskLog.java
│   ├── SysUser.java
│   ├── SysUserRole.java
│   └── WebappLog.java
├── mapper/                  # MyBatis Mapper 接口
│   ├── AppVersionMapper.java
│   ├── AuthConfigMapper.java
│   ├── BannerMapper.java
│   ├── BlackRosterMapper.java
│   ├── CommentMapper.java
│   ├── CommentReportMapper.java
│   ├── EmailTemplateMapper.java
│   ├── FamilyMapper.java
│   ├── FeedbackLogMapper.java
│   ├── HelpCenterMapper.java
│   ├── ImageLogMapper.java
│   ├── LoginDeviceMapper.java
│   ├── LoginLogMapper.java
│   ├── ManageLogMapper.java
│   ├── MemberCollectMapper.java
│   ├── MemberInviteLogMapper.java
│   ├── MemberMapper.java
│   ├── MemberNoticeLogMapper.java
│   ├── MemberNoticeMapper.java
│   ├── MemberScoreLogMapper.java
│   ├── NewsConfigMapper.java
│   ├── NewsMapper.java
│   ├── NoticeTemplateMapper.java
│   ├── PayNotifyLogMapper.java
│   ├── PayRequestLogMapper.java
│   ├── SensitiveWordMapper.java
│   ├── SmsLogMapper.java
│   ├── SysAreaMapper.java
│   ├── SysCacheMapper.java
│   ├── SysConfigMapper.java
│   ├── SysDeptDataMapper.java
│   ├── SysDeptMapper.java
│   ├── SysDictItemMapper.java
│   ├── SysDictMapper.java
│   ├── SysMenuMapper.java
│   ├── SysNoticeMapper.java
│   ├── SysRoleMapper.java
│   ├── SysTaskLogMapper.java
│   ├── SysTaskMapper.java
│   ├── SysUserMapper.java
│   ├── SysUserRoleMapper.java
│   └── WebappLogMapper.java
└── handler/                 # MyBatis 类型处理器
    └── mysql/
        └── LikeTypeHandler.java
```

**需要迁移的 Mapper XML 文件**:
- 原路径: `service/src/main/resources/mapper/*.xml`
- 新路径: `eghm-dao/src/main/resources/mapper/*.xml`

#### 3.2.3 eghm-service-api (服务接口模块)

**职责**: 定义所有业务服务接口 + DTO + VO + 支付定义，不包含实现

**包路径**: `com.eghm.service.api`

**依赖**: `eghm-common` + `eghm-dao`

**说明**: 将 DTO 和 VO 放在此模块是因为它们被服务接口和实现层共同引用。例如 `MemberService` 接口的方法签名使用了 `MemberDTO`、`AccountLoginDTO` 等，`CommonServiceImpl` 使用了 `SysAreaVO`。将 DTO/VO 放在 service-api 中使得服务契约（接口 + 入参 + 出参）集中在同一模块。

**包结构**:
```
com.eghm.service.api
├── common/                  # 通用服务接口
│   ├── AlarmService.java
│   ├── CommonService.java
│   ├── EmailService.java
│   ├── FileService.java
│   ├── GeoService.java
│   ├── JsonService.java
│   ├── MemberTokenService.java
│   ├── SendSmsService.java
│   ├── SmsService.java
│   └── UserTokenService.java
├── cache/                   # 缓存服务接口
│   ├── CacheProxyService.java
│   ├── CacheService.java
│   ├── ClearCacheService.java
│   └── SysCacheService.java
├── lock/                    # 分布式锁接口
│   └── RedisLock.java
├── manager/                 # 管理器接口
│   └── LoginCacheManager.java
├── mq/                      # 消息队列服务接口
│   └── service/
│       └── MessageService.java
├── business/                # 业务服务接口
│   ├── LoginService.java
│   ├── MemberCollectService.java
│   ├── MemberInviteLogService.java
│   ├── MemberNoticeService.java
│   ├── MemberScoreLogService.java
│   └── MemberService.java
├── operate/                 # 运营服务接口
│   ├── AppVersionService.java
│   ├── AuthConfigService.java
│   ├── BannerService.java
│   ├── CommentReportService.java
│   ├── CommentService.java
│   ├── EmailTemplateService.java
│   ├── FeedbackService.java
│   ├── HelpCenterService.java
│   ├── ImageLogService.java
│   ├── NewsConfigService.java
│   ├── NewsService.java
│   ├── NoticeTemplateService.java
│   ├── SensitiveWordService.java
│   └── SysNoticeService.java
├── sys/                     # 系统服务接口
│   ├── BlackRosterService.java
│   ├── FamilyService.java
│   ├── ManageLogService.java
│   ├── SmsLogService.java
│   ├── SysAreaService.java
│   ├── SysConfigService.java
│   ├── SysDeptDataService.java
│   ├── SysDeptService.java
│   ├── SysDictService.java
│   ├── SysMenuService.java
│   ├── SysRoleService.java
│   ├── SysTaskLogService.java
│   ├── SysTaskService.java
│   ├── SysUserService.java
│   └── WebappLogService.java
├── wechat/                  # 微信服务接口
│   ├── WeChatMiniService.java
│   └── WeChatMpService.java
├── pay/                     # 支付服务接口
│   ├── service/
│   │   ├── AggregatePayService.java
│   │   ├── CreatePayService.java
│   │   ├── PayNotifyLogService.java
│   │   ├── PayRequestLogService.java
│   │   └── PayService.java
├── dto/                     # 业务 DTO（服务契约的一部分）
│   ├── business/
│   │   ├── collect/
│   │   │   ├── CollectDTO.java
│   │   │   └── CollectQueryDTO.java
│   │   ├── member/
│   │   │   ├── BindEmailDTO.java
│   │   │   ├── ChangeEmailDTO.java
│   │   │   ├── LoginLogQueryRequest.java
│   │   │   ├── MemberDTO.java
│   │   │   ├── MemberQueryRequest.java
│   │   │   ├── MemberScoreQueryDTO.java
│   │   │   ├── MemberScoreQueryRequest.java
│   │   │   ├── ScoreUpdateRequest.java
│   │   │   ├── SendEmailAuthCodeDTO.java
│   │   │   ├── SendNotifyRequest.java
│   │   │   └── SendSmsRequest.java
│   │   ├── news/
│   │   │   ├── config/
│   │   │   │   ├── NewsConfigAddRequest.java
│   │   │   │   └── NewsConfigEditRequest.java
│   │   │   ├── NewsAddRequest.java
│   │   │   ├── NewsEditRequest.java
│   │   │   └── NewsQueryRequest.java
│   │   ├── pay/
│   │   │   └── PayLogQueryRequest.java
│   │   └── statistics/
│   │       ├── CollectRequest.java
│   │       └── DateRequest.java
│   ├── operate/
│   │   ├── auth/
│   │   │   ├── AuthConfigAddRequest.java
│   │   │   └── AuthConfigEditRequest.java
│   │   ├── banner/
│   │   │   ├── BannerAddRequest.java
│   │   │   ├── BannerEditRequest.java
│   │   │   └── BannerQueryRequest.java
│   │   ├── comment/
│   │   │   ├── CommentDTO.java
│   │   │   ├── CommentQueryDTO.java
│   │   │   ├── CommentQueryRequest.java
│   │   │   ├── CommentReportDTO.java
│   │   │   └── CommentReportQueryRequest.java
│   │   ├── email/
│   │   │   ├── EmailTemplateRequest.java
│   │   │   └── SendEmail.java
│   │   ├── feedback/
│   │   │   ├── FeedbackAddDTO.java
│   │   │   ├── FeedbackDisposeRequest.java
│   │   │   └── FeedbackQueryRequest.java
│   │   ├── help/
│   │   │   ├── HelpAddRequest.java
│   │   │   ├── HelpEditRequest.java
│   │   │   ├── HelpQueryDTO.java
│   │   │   └── HelpQueryRequest.java
│   │   ├── image/
│   │   │   ├── ImageAddRequest.java
│   │   │   ├── ImageEditRequest.java
│   │   │   └── ImageQueryRequest.java
│   │   ├── notice/
│   │   │   ├── NoticeAddRequest.java
│   │   │   ├── NoticeEditRequest.java
│   │   │   └── NoticeQueryRequest.java
│   │   ├── roster/
│   │   │   └── BlackRosterAddRequest.java
│   │   ├── sensitive/
│   │   │   └── KeywordDTO.java
│   │   ├── template/
│   │   │   └── NoticeTemplateRequest.java
│   │   └── version/
│   │       ├── VersionAddRequest.java
│   │       ├── VersionEditRequest.java
│   │       └── VersionQueryRequest.java
│   ├── sys/
│   │   ├── cache/
│   │   │   └── DeleteRequest.java
│   │   ├── config/
│   │   │   ├── ConfigEditRequest.java
│   │   │   └── ConfigQueryRequest.java
│   │   ├── dept/
│   │   │   ├── DeptAddRequest.java
│   │   │   └── DeptEditRequest.java
│   │   ├── dict/
│   │   │   ├── DictAddRequest.java
│   │   │   ├── DictEditRequest.java
│   │   │   ├── DictItemAddRequest.java
│   │   │   ├── DictItemEditRequest.java
│   │   │   └── DictQueryRequest.java
│   │   ├── family/
│   │   │   ├── FamilyAddRequest.java
│   │   │   └── FamilyEditRequest.java
│   │   ├── log/
│   │   │   ├── ManageQueryRequest.java
│   │   │   ├── SmsLogQueryRequest.java
│   │   │   └── WebappQueryRequest.java
│   │   ├── login/
│   │   │   ├── AccountLoginDTO.java
│   │   │   ├── DoubleCheckDTO.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── SendSmsDTO.java
│   │   │   ├── SetPasswordDTO.java
│   │   │   ├── SmsLoginDTO.java
│   │   │   ├── SmsLoginRequest.java
│   │   │   ├── SmsVerifyRequest.java
│   │   │   ├── TotpBindRequest.java
│   │   │   ├── TotpCheckRequest.java
│   │   │   └── VerifySmsDTO.java
│   │   ├── menu/
│   │   │   ├── MenuAddRequest.java
│   │   │   ├── MenuEditRequest.java
│   │   │   └── MenuQueryRequest.java
│   │   ├── register/
│   │   │   ├── AccountRegisterDTO.java
│   │   │   ├── MobileRegisterDTO.java
│   │   │   └── RegisterSmsDTO.java
│   │   ├── role/
│   │   │   ├── RoleAddRequest.java
│   │   │   ├── RoleAuthRequest.java
│   │   │   └── RoleEditRequest.java
│   │   ├── task/
│   │   │   ├── TaskEditRequest.java
│   │   │   ├── TaskLogQueryRequest.java
│   │   │   ├── TaskQueryRequest.java
│   │   │   └── TaskRunRequest.java
│   │   └── user/
│   │       ├── CheckPwdRequest.java
│   │       ├── PasswordEditRequest.java
│   │       ├── UserAddRequest.java
│   │       ├── UserEditRequest.java
│   │       ├── UserProfileRequest.java
│   │       └── UserQueryRequest.java
│   └── wechat/
│       ├── JsTicketDTO.java
│       ├── LinkUrlRequest.java
│       ├── MaLoginDTO.java
│       ├── MaOpenLoginDTO.java
│       ├── MpLoginDTO.java
│       ├── QrCodeRequest.java
│       └── ShortUrlRequest.java
├── vo/                      # 视图对象（服务契约的返回类型）
│   ├── business/
│   │   ├── collect/
│   │   │   └── MemberCollectVO.java
│   │   ├── member/
│   │   │   ├── LoginDeviceVO.java
│   │   │   ├── MemberInviteVO.java
│   │   │   ├── MemberNoticeVO.java
│   │   │   ├── MemberResponse.java
│   │   │   ├── MemberScoreVO.java
│   │   │   ├── MemberVO.java
│   │   │   └── SignInVO.java
│   │   ├── news/
│   │   │   ├── NewsConfigResponse.java
│   │   │   ├── NewsDetailVO.java
│   │   │   ├── NewsResponse.java
│   │   │   └── NewsVO.java
│   │   └── statistics/
│   │       ├── CollectStatisticsVO.java
│   │       ├── MemberRegisterVO.java
│   │       ├── MemberStatisticsVO.java
│   │       └── PieDataVO.java
│   ├── login/
│   │   ├── LoginMenuResponse.java
│   │   ├── LoginResponse.java
│   │   ├── LoginTokenVO.java
│   │   ├── QrcodeLoginResponse.java
│   │   └── TotpLoginResponse.java
│   ├── operate/
│   │   ├── auth/
│   │   │   ├── AuthConfigResponse.java
│   │   │   └── AuthConfigVO.java
│   │   ├── banner/
│   │   │   ├── BannerResponse.java
│   │   │   └── BannerVO.java
│   │   ├── comment/
│   │   │   ├── CommentReportResponse.java
│   │   │   ├── CommentResponse.java
│   │   │   ├── CommentSecondVO.java
│   │   │   └── CommentVO.java
│   │   ├── feedback/
│   │   │   └── FeedbackResponse.java
│   │   ├── help/
│   │   │   ├── HelpCenterVO.java
│   │   │   ├── HelpDetailResponse.java
│   │   │   └── HelpResponse.java
│   │   ├── log/
│   │   │   ├── ImageLogResponse.java
│   │   │   ├── ManageLogResponse.java
│   │   │   ├── PayNotifyLogResponse.java
│   │   │   ├── PayRequestLogResponse.java
│   │   │   ├── SmsLogResponse.java
│   │   │   ├── SysTaskLogResponse.java
│   │   │   └── WebappLogResponse.java
│   │   ├── notice/
│   │   │   ├── NoticeDetailVO.java
│   │   │   ├── NoticeResponse.java
│   │   │   ├── NoticeTopVO.java
│   │   │   └── NoticeVO.java
│   │   ├── task/
│   │   │   └── SysTaskResponse.java
│   │   └── version/
│   │       ├── AppVersionResponse.java
│   │       └── AppVersionVO.java
│   ├── sys/
│   │   ├── dept/
│   │   │   └── SysDeptResponse.java
│   │   ├── dict/
│   │   │   ├── BaseDictResponse.java
│   │   │   ├── BaseItemVO.java
│   │   │   ├── DictItemResponse.java
│   │   │   └── DictResponse.java
│   │   ├── ext/
│   │   │   ├── SysAreaVO.java
│   │   │   ├── SysConfigResponse.java
│   │   │   ├── SysDeptResponse.java
│   │   │   └── SysRoleResponse.java
│   │   ├── family/
│   │   │   └── FamilyResponse.java
│   │   ├── menu/
│   │   │   ├── MenuFullResponse.java
│   │   │   ├── MenuResponse.java
│   │   │   └── MenuTreeResponse.java
│   │   └── user/
│   │       ├── UserDetailResponse.java
│   │       └── UserResponse.java
│   └── wechat/
│       └── JsTicketVO.java
└── pay/                     # 支付相关类型定义（属于服务契约）
    ├── dto/
    │   ├── PrepayDTO.java
    │   └── RefundDTO.java
    ├── enums/
    │   ├── PayChannel.java
    │   ├── RefundChannel.java
    │   ├── RefundStatus.java
    │   ├── StepType.java
    │   ├── TradeState.java
    │   └── TradeType.java
    └── vo/
        ├── PayOrderVO.java
        ├── PrepayVO.java
        └── RefundVO.java
```

#### 3.2.4 eghm-service-impl (服务实现模块)

**职责**: 实现所有业务服务接口 + 工厂配置类 + 处理器

**包路径**: `com.eghm.service.impl`

**依赖**: `eghm-service-api`

**重要变更**: 将原来 `configuration/` 下的工厂配置类（`OssConfig`、`SmsConfig`、`TokenConfig`）以及 `WebMvcConfig` 中的 `alarmService()` Bean 方法移至此模块，因为这些配置类创建了 `common/impl/` 下的具体实现 Bean，属于服务实现层的职责。

**包结构**:
```
com.eghm.service.impl
├── common/                  # 通用服务实现
│   └── impl/
│       ├── AbstractAlarmService.java
│       ├── AliOssFileServiceImpl.java
│       ├── AliSmsServiceImpl.java
│       ├── CommonServiceImpl.java
│       ├── DefaultAlarmServiceImpl.java
│       ├── DefaultSmsServiceImpl.java
│       ├── DingTalkAlarmServiceImpl.java
│       ├── EmailServiceImpl.java
│       ├── FeiShuAlarmServiceImpl.java
│       ├── GeoServiceImpl.java
│       ├── JsonServiceImpl.java
│       ├── JwtUserTokenServiceImpl.java
│       ├── MemberTokenServiceImpl.java
│       ├── RedisUserTokenServiceImpl.java
│       ├── SmsServiceImpl.java
│       ├── SysConfigApi.java
│       ├── SystemFileServiceImpl.java
│       ├── TencentSmsServiceImpl.java
│       └── WeChatAlarmServiceImpl.java
├── cache/                   # 缓存服务实现
│   └── impl/
│       ├── CacheProxyServiceImpl.java
│       ├── CacheServiceImpl.java
│       ├── ClearCacheServiceImpl.java
│       └── SysCacheServiceImpl.java
├── lock/                    # 分布式锁实现
│   └── impl/
│       └── RedisLockImpl.java
├── manager/                 # 管理器实现
│   └── LoginCacheManagerImpl.java (如有)
├── mq/                      # 消息队列服务实现
│   └── service/
│       └── impl/
│           └── MessageServiceImpl.java
├── handler/                 # 处理器（业务逻辑处理器）
│   ├── chain/
│   │   ├── annotation/
│   │   │   └── HandlerMark.java
│   │   ├── enums/
│   │   │   └── HandlerEnum.java
│   │   ├── impl/
│   │   │   └── InviteRegisterHandler.java
│   │   ├── Handler.java
│   │   ├── HandlerChain.java
│   │   ├── HandlerInvoker.java
│   │   └── MessageData.java
│   └── email/
│       ├── service/
│       │   ├── BindEmailEmailHandler.java
│       │   └── ChangeEmailEmailHandler.java
│       ├── AuthCodeEmailHandler.java
│       └── BaseEmailHandler.java
├── business/                # 业务服务实现
│   └── impl/
│       ├── LoginServiceImpl.java
│       ├── MemberCollectServiceImpl.java
│       ├── MemberInviteLogServiceImpl.java
│       ├── MemberNoticeServiceImpl.java
│       ├── MemberScoreLogServiceImpl.java
│       └── MemberServiceImpl.java
├── operate/                 # 运营服务实现
│   └── impl/
│       ├── AppVersionServiceImpl.java
│       ├── AuthConfigServiceImpl.java
│       ├── BannerServiceImpl.java
│       ├── CommentReportServiceImpl.java
│       ├── CommentServiceImpl.java
│       ├── EmailTemplateServiceImpl.java
│       ├── FeedbackServiceImpl.java
│       ├── HelpCenterServiceImpl.java
│       ├── ImageLogServiceImpl.java
│       ├── NewsConfigServiceImpl.java
│       ├── NewsServiceImpl.java
│       ├── NoticeTemplateServiceImpl.java
│       ├── SensitiveWordServiceImpl.java
│       └── SysNoticeServiceImpl.java
├── sys/                     # 系统服务实现
│   └── impl/
│       ├── BlackRosterServiceImpl.java
│       ├── FamilyServiceImpl.java
│       ├── ManageLogServiceImpl.java
│       ├── SmsLogServiceImpl.java
│       ├── SysAreaServiceImpl.java
│       ├── SysConfigServiceImpl.java
│       ├── SysDeptDataServiceImpl.java
│       ├── SysDeptServiceImpl.java
│       ├── SysDictServiceImpl.java
│       ├── SysMenuServiceImpl.java
│       ├── SysRoleServiceImpl.java
│       ├── SysTaskLogServiceImpl.java
│       ├── SysTaskServiceImpl.java
│       ├── SysUserServiceImpl.java
│       └── WebappLogServiceImpl.java
├── wechat/                  # 微信服务实现
│   └── impl/
│       ├── WeChatMiniServiceImpl.java
│       └── WeChatMpServiceImpl.java
├── pay/                     # 支付服务实现
│   └── service/
│       └── impl/
│           ├── AggregatePayServiceImpl.java
│           ├── AliAppCreatePayServiceImpl.java
│           ├── AliJsApiCreatePayServiceImpl.java
│           ├── AliPayServiceImpl.java
│           ├── AliQrCodeCreatePayServiceImpl.java
│           ├── PayNotifyLogServiceImpl.java
│           ├── PayRequestLogServiceImpl.java
│           └── WechatPayServiceImpl.java
└── config/                  # 【新增】工厂配置类（原 configuration/ 下引用具体实现的配置）
    ├── ServiceOssConfig.java    # 原 OssConfig，创建 FileService Bean
    ├── ServiceSmsConfig.java    # 原 SmsConfig，创建 SendSmsService Bean
    ├── ServiceTokenConfig.java  # 原 TokenConfig，创建 UserTokenService Bean
    └── ServiceAlarmConfig.java  # 【新增】从 WebMvcConfig 中拆分出的 alarmService Bean
```

#### 3.2.5 eghm-web-common (Web 通用模块)

**职责**: Web 层公共配置、拦截器、转换器、过滤器。**不依赖 eghm-service-impl**，只依赖 eghm-service-api。

**包路径**: `com.eghm.web.common`

**依赖**: `eghm-service-api`（不依赖 eghm-service-impl）

**重要变更**: `WebMvcConfig` 中的 `alarmService()` Bean 方法已移出，改为只依赖 `AlarmService` 接口（由 eghm-service-impl 中的 `ServiceAlarmConfig` 提供 Bean）。

**包结构**:
```
com.eghm.web.common
├── configuration/           # Web 配置
│   ├── filter/              # 过滤器基类
│   │   ├── AbstractIgnoreFilter.java
│   │   └── ByteHttpRequestFilter.java
│   ├── interceptor/         # 拦截器基类
│   │   └── InterceptorAdapter.java
│   ├── authentication/      # 认证相关
│   │   ├── ApiHolder.java
│   │   └── SecurityHolder.java
│   ├── encoder/             # 编码器
│   │   ├── BcryptEncoder.java
│   │   └── Encoder.java
│   ├── gson/                # Gson 适配
│   │   ├── LocalDateAdapter.java
│   │   └── LocalDateTimeAdapter.java
│   ├── jackson/             # Jackson 适配
│   │   ├── DesensitizationAnnotationInterceptor.java
│   │   └── DesensitizationSerializer.java
│   ├── log/                 # 日志追踪
│   │   ├── LogTraceFilter.java
│   │   └── LogTraceHolder.java
│   ├── rabbit/              # RabbitMQ 配置
│   │   ├── RabbitConfig.java
│   │   └── RabbitInitConfig.java
│   ├── ratelimit/           # 限流
│   │   └── RateLimitAspect.java
│   ├── task/                # 定时任务基础设施
│   │   ├── config/
│   │   │   ├── CronScheduleBean.java
│   │   │   ├── CronTaskWrapper.java
│   │   │   ├── Invoker.java
│   │   │   ├── OnceScheduleBean.java
│   │   │   ├── ScheduleBean.java
│   │   │   ├── ScheduledLockAspect.java
│   │   │   ├── SchedulingConfig.java
│   │   │   └── TaskRegistrar.java
│   │   └── job/
│   │       ├── OnceJobService.java
│   │       └── TestJobService.java
│   ├── template/            # 模板引擎
│   │   ├── FreemarkerTemplate.java
│   │   └── TemplateEngine.java
│   ├── version/             # API 版本
│   │   ├── ApiVersionCondition.java
│   │   └── ApiVersionRequestMappingHandlerMapping.java
│   ├── config/              # 配置管理
│   │   ├── impl/
│   │   │   └── CaptchaTypeHandler.java
│   │   ├── ConfigHandler.java
│   │   └── ConfigRegistry.java
│   ├── data/                # 数据权限
│   │   └── permission/
│   │       ├── DataScope.java
│   │       ├── DataScopeAspect.java
│   │       └── DataScopeInterceptor.java
│   ├── AliPayConfig.java
│   ├── ApplicationProperties.java
│   ├── ExcelConfig.java
│   ├── ExecutorConfig.java
│   ├── MathCaptchaProducer.java
│   ├── MybatisConfig.java
│   ├── RandomDissolveGimpy.java
│   ├── RedisConfig.java
│   ├── SqlFormatter.java
│   ├── TextCaptchaProducer.java
│   ├── TransactionConfig.java
│   ├── WeChatConfig.java
│   └── WebMvcConfig.java         # 移除了 alarmService() Bean 方法
├── convertor/               # DTO/VO 转换器（Jackson 序列化器等）
│   ├── excel/               # Excel 转换器
│   │   ├── AreaConverter.java
│   │   ├── BooleanExcelConverter.java
│   │   ├── CentToYuanConverter.java
│   │   ├── DesensitizationConverter.java
│   │   ├── DictConverter.java
│   │   ├── EnumExcelConverter.java
│   │   └── ImageConverter.java
│   ├── BigDecimalOmitSerializer.java
│   ├── CentToYuanOmitSerializer.java
│   ├── CentToYuanSerializer.java
│   ├── DateAnnotationFormatterBinderFactory.java
│   ├── DateAnnotationFormatterParser.java
│   ├── DateParseSerializer.java
│   ├── EnumBinderConverterFactory.java
│   ├── EnumDescSerializer.java
│   ├── IpToLongDeserializer.java
│   ├── JoinerDeserializer.java
│   ├── LongToIpSerializer.java
│   ├── NumberParseSerializer.java
│   ├── RsaPasswordDeserializer.java
│   ├── SplitterIntJsonSerializer.java
│   ├── SplitterJsonSerializer.java
│   ├── XssDeserializer.java
│   ├── YuanToCentAnnotationFormatterBinderFactory.java
│   └── YuanToCentDeserializer.java
├── excel/                   # Excel 处理
│   ├── DynamicSpinner.java
│   └── ExcelSpinnerResolver.java
└── mq/                      # 消息队列监听器基类
    └── listener/
        └── AbstractListenerHandler.java
```

#### 3.2.6 webapp-server / manage-server (应用层)

**职责**: 各自独立的 Spring Boot 启动入口 + 控制器 + 专属拦截器/过滤器/配置

**当前包路径**: `com.eghm.web`（保持不变）

**依赖**: `eghm-web-common` + `eghm-service-impl`

**需要调整的注解**:
- `@MapperScan("com.eghm.mapper")` → `@MapperScan("com.eghm.dao.mapper")`
- `@ComponentScan("com.eghm")` → 保持不变（基包路径仍为 `com.eghm`）

### 3.3 模块依赖关系图

```
┌─────────────────┐
│   i18n          │  (独立模块，不依赖其他业务模块)
└─────────────────┘

┌─────────────────┐
│  eghm-common    │  (基础模块，无业务依赖)
└─────────────────┘
         ↑
┌─────────────────┐
│   eghm-dao      │  (依赖 eghm-common)
└─────────────────┘
         ↑
┌─────────────────┐
│eghm-service-api │  (依赖 eghm-common, eghm-dao)
│  + DTO/VO       │
└─────────────────┘
         ↑
┌─────────────────┐
│eghm-service-impl│  (依赖 eghm-service-api)
│  + 工厂配置      │
└─────────────────┘
         ↑
┌─────────────────┐
│ eghm-web-common │  (依赖 eghm-service-api，不依赖 eghm-service-impl)
│  + WebMvcConfig  │
└─────────────────┘
         ↑
┌─────────────────────────────────┐
│  webapp-server / manage-server  │  (依赖 eghm-web-common + eghm-service-impl)
└─────────────────────────────────┘
```

### 3.4 关键设计决策说明

**决策 1: DTO/VO 放在 eghm-service-api 而非 eghm-web-common**

原计划将 DTO/VO 放在 eghm-web-common，但经代码审查发现：
- 服务接口（如 `MemberService`）的方法签名直接使用了 DTO 作为参数
- 服务实现（如 `CommonServiceImpl`）使用了 VO 作为返回值
- 若 DTO/VO 在 web-common，则 service-impl 需要依赖 web-common，造成循环依赖
- 将 DTO/VO 放在 service-api 中，使服务契约（接口 + 入参 + 出参）集中在同一模块，符合"接口与契约同行"原则

**决策 2: 工厂配置类（OssConfig/SmsConfig/TokenConfig）移至 eghm-service-impl**

原计划将全部 configuration/ 放在 eghm-web-common，但经代码审查发现：
- `OssConfig` 导入 `AliOssFileServiceImpl`、`SysConfigApi`、`SystemFileServiceImpl`
- `SmsConfig` 导入 `AliSmsServiceImpl`、`DefaultSmsServiceImpl`、`TencentSmsServiceImpl`
- `TokenConfig` 导入 `JwtUserTokenServiceImpl`、`RedisUserTokenServiceImpl`
- 这些类创建具体实现 Bean，属于服务实现层职责，应放在 service-impl 中

**决策 3: WebMvcConfig 中 alarmService() Bean 拆分**

- `WebMvcConfig` 保留在 `eghm-web-common`（它是 Web 层公共配置基类）
- 将其中的 `alarmService()` Bean 方法提取到 `eghm-service-impl` 的 `ServiceAlarmConfig` 中
- `WebMvcConfig` 只依赖 `AlarmService` 接口（来自 service-api），不再依赖具体实现

---

## 四、实施步骤

### 阶段一：创建新模块结构

1. **修改根 pom.xml**
   - 添加新模块声明：`eghm-common`, `eghm-dao`, `eghm-service-api`, `eghm-service-impl`, `eghm-web-common`
   - 移除旧的 `service` 模块
   - 在 `dependencyManagement` 中添加新模块的版本管理

2. **创建各模块 pom.xml**
   - 为每个新模块创建 `pom.xml`，配置正确的依赖关系
   - 确保依赖传递正确

### 阶段二：迁移代码（从底层到高层）

1. **迁移到 eghm-common**
   - `annotation/` → `com.eghm.common.annotation`
   - `constants/` → `com.eghm.common.constants`
   - `enums/` → `com.eghm.common.enums`
   - `exception/` → `com.eghm.common.exception`
   - `utils/` → `com.eghm.common.utils`
   - `dto/ext/` 中的通用 DTO → `com.eghm.common.dto.ext`
   - `dto/IdDTO.java`, `IdRequest.java`, `SortByDTO.java`, `StateRequest.java` → `com.eghm.common.dto`
   - `event/` → `com.eghm.common.event`

2. **迁移到 eghm-dao**
   - `model/` → `com.eghm.dao.model`
   - `mapper/` → `com.eghm.dao.mapper`
   - `handler/mysql/` → `com.eghm.dao.handler.mysql`
   - `resources/mapper/*.xml` → `eghm-dao/src/main/resources/mapper/*.xml`

3. **迁移到 eghm-service-api**
   - `common/` 接口 → `com.eghm.service.api.common`
   - `cache/` 接口 → `com.eghm.service.api.cache`
   - `lock/` 接口 → `com.eghm.service.api.lock`
   - `manager/` 接口 → `com.eghm.service.api.manager`
   - `mq/service/` 接口 → `com.eghm.service.api.mq.service`
   - `service/business/` 接口 → `com.eghm.service.api.business`
   - `service/operate/` 接口 → `com.eghm.service.api.operate`
   - `service/sys/` 接口 → `com.eghm.service.api.sys`
   - `wechat/` 接口 → `com.eghm.service.api.wechat`
   - `pay/service/` 接口 → `com.eghm.service.api.pay.service`
   - `dto/`（业务 DTO）→ `com.eghm.service.api.dto`
   - `vo/` → `com.eghm.service.api.vo`
   - `pay/dto/`, `pay/enums/`, `pay/vo/` → `com.eghm.service.api.pay`

4. **迁移到 eghm-service-impl**
   - `common/impl/` → `com.eghm.service.impl.common.impl`
   - `cache/impl/` → `com.eghm.service.impl.cache.impl`
   - `lock/impl/` → `com.eghm.service.impl.lock.impl`
   - `mq/service/impl/` → `com.eghm.service.impl.mq.service.impl`
   - `handler/` → `com.eghm.service.impl.handler`
   - `service/business/impl/` → `com.eghm.service.impl.business.impl`
   - `service/operate/impl/` → `com.eghm.service.impl.operate.impl`
   - `service/sys/impl/` → `com.eghm.service.impl.sys.impl`
   - `wechat/impl/` → `com.eghm.service.impl.wechat.impl`
   - `pay/service/impl/` → `com.eghm.service.impl.pay.service.impl`
   - `configuration/OssConfig.java` → `com.eghm.service.impl.config.ServiceOssConfig.java`
   - `configuration/SmsConfig.java` → `com.eghm.service.impl.config.ServiceSmsConfig.java`
   - `configuration/TokenConfig.java` → `com.eghm.service.impl.config.ServiceTokenConfig.java`
   - 新增 `com.eghm.service.impl.config.ServiceAlarmConfig.java`（从 WebMvcConfig 拆分）

5. **迁移到 eghm-web-common**
   - `configuration/`（排除 OssConfig, SmsConfig, TokenConfig）→ `com.eghm.web.common.configuration`
   - `convertor/` → `com.eghm.web.common.convertor`
   - `excel/` → `com.eghm.web.common.excel`
   - `mq/listener/AbstractListenerHandler.java` → `com.eghm.web.common.mq.listener`

### 阶段三：更新引用

1. **批量更新 import 语句**
   - 使用 IDE 重构功能或脚本批量更新所有 Java 文件中的 import 语句
   - 确保所有引用都指向新的包路径

2. **更新 Spring 配置**
   - 更新 `@MapperScan("com.eghm.mapper")` → `@MapperScan("com.eghm.dao.mapper")`
   - `@ComponentScan("com.eghm")` 保持不变（基包路径仍为 `com.eghm`）
   - 更新 `@SpringBootApplication(scanBasePackages = "com.eghm")` 保持不变

3. **更新 MyBatis XML 配置**
   - 更新 XML mapper 文件中的 namespace（如 `com.eghm.foundation.data.infrastructure.mapper.SysUserMapper` → `com.eghm.dao.mapper.SysUserMapper`）

4. **更新 webapp-server 和 manage-server 的 pom.xml**
   - 将 `service` 依赖替换为 `eghm-web-common` + `eghm-service-impl`

### 阶段四：验证与测试

1. **编译验证**: `mvn clean compile` 确保所有模块编译通过
2. **单元测试**: 运行所有单元测试，确保业务逻辑未改变
3. **集成测试**: 启动 `webapp-server` 和 `manage-server`，验证功能正常

---

## 五、包路径变更对照表

| 原始路径 | 新路径 | 目标模块 |
|---------|-------|---------|
| `com.eghm.annotation` | `com.eghm.common.annotation` | eghm-common |
| `com.eghm.constants` | `com.eghm.common.constants` | eghm-common |
| `com.eghm.enums` | `com.eghm.common.enums` | eghm-common |
| `com.eghm.exception` | `com.eghm.common.exception` | eghm-common |
| `com.eghm.utils` | `com.eghm.common.utils` | eghm-common |
| `com.eghm.dto.ext` | `com.eghm.common.dto.ext` | eghm-common |
| `com.eghm.event` | `com.eghm.common.event` | eghm-common |
| `com.eghm.model` | `com.eghm.dao.model` | eghm-dao |
| `com.eghm.mapper` | `com.eghm.dao.mapper` | eghm-dao |
| `com.eghm.handler.mysql` | `com.eghm.dao.handler.mysql` | eghm-dao |
| `com.eghm.common.*` (接口) | `com.eghm.service.api.common` | eghm-service-api |
| `com.eghm.cache.*` (接口) | `com.eghm.service.api.cache` | eghm-service-api |
| `com.eghm.lock.*` (接口) | `com.eghm.service.api.lock` | eghm-service-api |
| `com.eghm.service.*` (接口) | `com.eghm.service.api.*` | eghm-service-api |
| `com.eghm.wechat.*` (接口) | `com.eghm.service.api.wechat` | eghm-service-api |
| `com.eghm.dto` (业务) | `com.eghm.service.api.dto` | eghm-service-api |
| `com.eghm.vo` | `com.eghm.service.api.vo` | eghm-service-api |
| `com.eghm.pay.dto` | `com.eghm.service.api.pay.dto` | eghm-service-api |
| `com.eghm.pay.enums` | `com.eghm.service.api.pay.enums` | eghm-service-api |
| `com.eghm.pay.vo` | `com.eghm.service.api.pay.vo` | eghm-service-api |
| `com.eghm.common.impl` | `com.eghm.service.impl.common.impl` | eghm-service-impl |
| `com.eghm.cache.impl` | `com.eghm.service.impl.cache.impl` | eghm-service-impl |
| `com.eghm.lock.impl` | `com.eghm.service.impl.lock.impl` | eghm-service-impl |
| `com.eghm.service.*.impl` | `com.eghm.service.impl.*.impl` | eghm-service-impl |
| `com.eghm.wechat.impl` | `com.eghm.service.impl.wechat.impl` | eghm-service-impl |
| `com.eghm.pay.service.impl` | `com.eghm.service.impl.pay.service.impl` | eghm-service-impl |
| `com.eghm.handler` (chain/email) | `com.eghm.service.impl.handler` | eghm-service-impl |
| `com.eghm.integration.storage.config.OssConfig` | `com.eghm.service.impl.config.ServiceOssConfig` | eghm-service-impl |
| `com.eghm.integration.messaging.config.SmsConfig` | `com.eghm.service.impl.config.ServiceSmsConfig` | eghm-service-impl |
| `com.eghm.platform.iam.config.TokenConfig` | `com.eghm.service.impl.config.ServiceTokenConfig` | eghm-service-impl |
| `com.eghm.configuration` (其余) | `com.eghm.web.common.configuration` | eghm-web-common |
| `com.eghm.convertor` | `com.eghm.web.common.convertor` | eghm-web-common |
| `com.eghm.excel` | `com.eghm.web.common.excel` | eghm-web-common |
| `com.eghm.mq.listener` | `com.eghm.web.common.mq.listener` | eghm-web-common |

---

## 六、示例：ServiceAlarmConfig.java（新增文件）

```java
package com.eghm.service.impl.config;

import com.eghm.common.enums.AlarmType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.api.common.AlarmService;
import com.eghm.service.api.common.JsonService;
import com.eghm.service.impl.common.impl.DefaultAlarmServiceImpl;
import com.eghm.service.impl.common.impl.DingTalkAlarmServiceImpl;
import com.eghm.service.impl.common.impl.FeiShuAlarmServiceImpl;
import com.eghm.service.impl.common.impl.WeChatAlarmServiceImpl;
import com.eghm.web.common.configuration.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.eghm.common.utils.StringUtil.isBlank;

@Configuration
@AllArgsConstructor
public class ServiceAlarmConfig {

    private final JsonService jsonService;
    private final ApplicationProperties applicationProperties;

    @Bean
    public AlarmService alarmService() {
        ApplicationProperties.Alarm alarm = applicationProperties.getAlarm();
        if (alarm.getType() == AlarmType.DEFAULT) {
            return new DefaultAlarmServiceImpl();
        }
        if (isBlank(alarm.getWebHook())) {
            throw new BusinessException(ErrorCode.WEB_HOOK_NULL);
        }
        if (alarm.getType() == AlarmType.DING_TALK) {
            return new DingTalkAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarm.getType() == AlarmType.FEI_SHU) {
            return new FeiShuAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarm.getType() == AlarmType.ENTERPRISE_WECHAT) {
            return new WeChatAlarmServiceImpl(jsonService, applicationProperties);
        }
        return new DefaultAlarmServiceImpl();
    }
}
```

---

## 七、风险与注意事项

### 7.1 潜在风险

1. **包路径变更导致的大量 import 修改**
   - 需要使用 IDE 重构功能（IntelliJ IDEA 的 Move Class / Refactor）或脚本批量处理
   - 建议分模块逐步迁移，避免一次性大规模变更

2. **Spring Bean 扫描**
   - `@ComponentScan("com.eghm")` 基包路径不变，Bean 扫描不受影响
   - `@MapperScan("com.eghm.mapper")` 必须更新为 `@MapperScan("com.eghm.dao.mapper")`
   - 确保 `@Service`, `@Component`, `@Repository` 注解的类都能被正确扫描

3. **MyBatis Mapper XML namespace**
   - XML mapper 文件中的 namespace 需要从 `com.eghm.mapper.XxxMapper` 更新为 `com.eghm.dao.mapper.XxxMapper`

4. **ApplicationProperties 依赖**
   - `ApplicationProperties` 在 `eghm-web-common` 中，但被 `eghm-service-impl` 的工厂配置类引用
   - 需确保 `eghm-service-impl` 依赖 `eghm-web-common`（当前依赖链已包含此关系）

### 7.2 注意事项

1. **保持业务逻辑不变**: 仅做包路径和模块结构调整，不修改任何业务代码逻辑
2. **依赖管理**: 确保模块间依赖关系清晰，避免循环依赖
3. **测试覆盖**: 每个阶段完成后都要进行编译和测试
4. **版本控制**: 建议在独立的 Git 分支上进行重构，每次迁移一个模块后提交一次

---

## 八、预期收益

1. **模块职责清晰**: 每个模块有明确的职责边界，便于理解和维护
2. **依赖关系明确**: 通过模块依赖关系可以清晰看出代码层次
3. **扩展性增强**: 新增业务模块时，只需在对应模块中添加代码
4. **复用性提升**: 通用代码集中在 common 模块，便于跨项目复用
5. **团队协作优化**: 不同团队可以专注于不同模块，减少代码冲突
6. **构建效率提升**: 修改某个模块时，只需编译相关模块
7. **依赖倒置**: 工厂配置类放入 service-impl，WebMvcConfig 不再直接引用具体实现，符合依赖倒置原则

---

**文档生成时间**: 2026-07-15  
**适用项目**: base-project  
**重构目标**: 代码规范化 + 多模块拆分 + 包路径优化