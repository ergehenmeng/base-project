# DDD架构重构计划

## 背景

项目采用Maven多模块DDD分层架构（common、domain、application、infrastructure、web-core、manage-server、webapp-server）。经过审查发现4个问题需要修复。

## 任务概览

| # | 任务 | 复杂度 | 状态 |
|---|------|--------|------|
| 1 | BaseEntity包名调整 | 低 | 待执行 |
| 2 | 贫血实体充血化 | 高 | 待执行 |
| 3 | Repository保持现状 | - | 跳过 |
| 4 | 清理application层SDK依赖 | 低 | 待执行 |

**执行顺序**：任务1 → 任务4 → 任务2（任务2依赖任务1完成）

---

## 任务1：BaseEntity包名调整

### 现状
- BaseEntity定义在domain模块中：`domain/src/main/java/com/eghm/common/model/BaseEntity.java`
- 包名为 `com.eghm.common.model`，但实际属于domain层
- 所有继承BaseEntity的实体类import语句为 `import com.eghm.common.model.BaseEntity`

### 目标
将包名改为 `com.eghm.model`，使其更符合domain层的定位。

### 实施步骤

1. **创建新目录并移动文件**
   - 创建 `domain/src/main/java/com/eghm/model/` 目录
   - 移动 `BaseEntity.java` 到新目录
   - 修改包声明为 `package com.eghm.model`

2. **批量更新所有继承类的import语句**

   需要修改的实体类（约24个，按领域分组）：

   **operate领域**：News, AuthConfig, AppVersion, CommentReport, SysNotice, SensitiveWord, NewsConfig, ImageLog, HelpCenter, FeedbackLog, Banner

   **business领域**：Member, MemberCollect, MemberNoticeLog, MemberNotice, MemberInviteLog

   **sys领域**：SysDept, SysUser, BlackRoster, SysRole, SysDictItem, SysDict

   **pay领域**：PayRequestLog, PayNotifyLog

   统一修改：
   ```java
   // 修改前
   import com.eghm.common.model.BaseEntity;
   // 修改后
   import com.eghm.model.BaseEntity;
   ```

3. **检查其他模块引用**
   - 搜索 `import com.eghm.common.model.BaseEntity` 的所有文件
   - 更新application、infrastructure等模块中的引用

4. **编译验证**
   ```bash
   mvn clean compile -DskipTests
   ```

### 风险
- **低风险**：主要是import语句的批量替换，确保不遗漏即可

---

## 任务2：贫血实体充血化

### 现状分析

经过详细检查，实体分为三类：

**A类：已是充血模型（无需改造）**
- Member：initializeRegistration, assertCanLogin, bindEmail, changeEmail, changePassword, changeScore等
- SysUser：initializeSystemUser, changePassword, resetPassword, assertCanPasswordLogin等

**B类：已有部分业务方法（需丰富）**
- Comment：create(), canBeReportedBy(), increaseReportNum()
- SysDept：assignCode(), recordOperator()
- SysMenu：assignIdentity(), changeCode(), assertCanCreateChild(), isRoot()
- SysRole：assertDeletable(), changeProfile()
- SysConfig：assertEditable(), changeContent()
- BlackRoster：assertRangeValid(), toCacheValues()
- AuthConfig：initialize(), resetSecret()（需确认）

**C类：完全贫血（需添加业务方法）**
- operate领域：News, AppVersion, CommentReport, SysNotice, SensitiveWord, NewsConfig, ImageLog, HelpCenter, FeedbackLog, EmailTemplate, Banner, NoticeTemplate
- business领域：MemberCollect, MemberScoreLog, MemberNoticeLog, MemberNotice, MemberInviteLog, LoginLog, LoginDevice
- sys领域：SysDictItem, SysDict, SysUserRole, SysTaskLog, SysTask, SmsLog, WebappLog, ManageLog, SysDeptData, SysCache, SysArea, Family
- pay领域：PayRequestLog, PayNotifyLog

### 改造原则

参考Member和SysUser的模式，充血模型应包含：
1. **初始化方法**：`initializeXxx()` - 设置初始状态
2. **状态变更方法**：`changeXxx()` / `enable()` / `disable()` - 修改实体状态
3. **断言方法**：`assertXxx()` - 业务规则校验，失败抛出BusinessException
4. **查询方法**：`isXxx()` / `canXxx()` - 状态查询
5. **工厂方法**：创建关联实体

### 具体改造方案

#### 2.1 operate领域（14个实体）

**News（资讯）**
```java
// 已有：assertCommentSupport()
// 新增：
public void publish() { this.state = true; }
public void unpublish() { this.state = false; }
public void increasePraiseNum() { this.praiseNum = this.praiseNum == null ? 1 : this.praiseNum + 1; }
public boolean isPublished() { return Boolean.TRUE.equals(this.state); }
```

**Comment（评论）**
```java
// 已有：create(), canBeReportedBy(), increaseReportNum()
// 新增：
public void shield() { this.state = false; }
public void unshield() { this.state = true; }
public void top() { this.topState = 1; }
public void untop() { this.topState = 0; }
public void increasePraiseNum() { this.praiseNum = this.praiseNum == null ? 1 : this.praiseNum + 1; }
public void increaseReplyNum() { this.replyNum = this.replyNum == null ? 1 : this.replyNum + 1; }
public boolean isShielded() { return Boolean.FALSE.equals(this.state); }
```

**Banner（轮播图）**
```java
public void enable() { this.state = true; }
public void disable() { this.state = false; }
public boolean isEffective(LocalDateTime now) {
    if (startTime != null && now.isBefore(startTime)) return false;
    if (endTime != null && now.isAfter(endTime)) return false;
    return Boolean.TRUE.equals(this.state);
}
```

**SysNotice（系统通知）**
```java
public void publish() { this.state = true; }
public void unpublish() { this.state = false; }
public boolean isPublished() { return Boolean.TRUE.equals(this.state); }
```

**HelpCenter（帮助中心）**
```java
public void enable() { this.state = true; }
public void disable() { this.state = false; }
public boolean isEnabled() { return Boolean.TRUE.equals(this.state); }
```

**CommentReport（评论举报）**
```java
public void process() { this.state = 1; }
public void ignore() { this.state = 2; }
public boolean isPending() { return Integer.valueOf(0).equals(this.state); }
```

**FeedbackLog（反馈日志）**
```java
public void reply() { this.state = 1; }
public boolean isPending() { return Integer.valueOf(0).equals(this.state); }
```

**其他operate实体**：AppVersion, SensitiveWord, NewsConfig, ImageLog, EmailTemplate, NoticeTemplate
- 添加基础的 `initialize()` 方法和状态管理方法

#### 2.2 business领域（7个实体）

**MemberCollect（会员收藏）**
```java
public void initialize(Long memberId, Long objectId, Integer objectType) {
    this.memberId = memberId;
    this.objectId = objectId;
    this.objectType = objectType;
}
public void cancel() { this.state = false; }
public boolean isCollected() { return Boolean.TRUE.equals(this.state); }
```

**MemberScoreLog（积分日志）**
```java
public void initialize(Long memberId, Integer score, Integer surplusScore, Integer type, String remark) {
    this.memberId = memberId;
    this.score = score;
    this.surplusScore = surplusScore;
    this.type = type;
    this.remark = remark;
}
```

**MemberNotice（会员通知）**
```java
public void markRead() { this.readState = true; }
public boolean isRead() { return Boolean.TRUE.equals(this.readState); }
```

**MemberNoticeLog, MemberInviteLog, LoginLog, LoginDevice**
- 添加 `initialize()` 方法

#### 2.3 sys领域（16个实体）

**SysDept（部门）**
```java
// 已有：assignCode(), recordOperator()
// 新增：
public void changeName(String name) { this.title = name; }
public boolean isRoot() { return "0".equals(this.parentCode); }
```

**SysMenu（菜单）**
```java
// 已有：assignIdentity(), changeCode(), assertCanCreateChild(), isRoot()
// 新增：
public void enable() { this.state = true; }
public void disable() { this.state = false; }
public boolean isButton() { return Integer.valueOf(2).equals(this.grade); }
```

**SysRole（角色）**
```java
// 已有：assertDeletable(), changeProfile()
// 新增：
public void initialize(String roleName, RoleType roleType) {
    this.roleName = roleName;
    this.roleType = roleType;
}
```

**SysDict, SysDictItem**
```java
public void enable() { this.state = true; }
public void disable() { this.state = false; }
```

**其他sys实体**：SysUserRole, SysTaskLog, SysTask, SmsLog, WebappLog, ManageLog, SysDeptData, SysCache, SysArea, Family
- 添加基础的 `initialize()` 方法和状态管理方法

#### 2.4 pay领域（2个实体）

**PayRequestLog, PayNotifyLog**
```java
public void initialize(String orderNo, ...) { ... }
public void markSuccess() { this.state = 1; }
public void markFailed() { this.state = 2; }
```

### 实施步骤

1. **按领域分批改造**
   - 第1批：operate领域（14个实体）
   - 第2批：business领域（7个实体）
   - 第3批：sys领域（16个实体）
   - 第4批：pay领域（2个实体）

2. **每个实体的改造流程**
   - 分析实体在application层的Service使用方式
   - 识别业务行为，设计方法签名
   - 在实体类中添加方法实现
   - 重构application层Service，调用实体方法
   - 编译验证

3. **编译验证**
   ```bash
   mvn clean compile -DskipTests
   ```

### 风险
- **中风险**：业务规则理解不准确，重构Service时可能引入bug
- **缓解措施**：分批重构，每批验证；仔细分析现有Service代码

---

## 任务3：Repository保持现状

无需改动，跳过。

---

## 任务4：清理application层SDK依赖

### 现状分析

**好消息**：经过详细检查，application层的接口已经完全使用自定义DTO，没有引用任何SDK类型！

- WeChatMiniService：所有方法参数和返回值都是String、byte[]等基础类型
- WeChatMpService：使用自定义DTO（MpUserInfo, JsTicketSignature, MpAccessToken）
- PayService：使用自定义DTO（PrepayDTO, PayNotifyMessage, RefundDTO等）
- SendSmsService、EmailService、FileService：都使用自定义DTO

**唯一引用**：ApplicationProperties中的WxMp、WxMa、WxPay是内部类，不是SDK类

### 目标
从application的pom.xml中移除所有SDK依赖。

### 实施步骤

1. **从application/pom.xml中移除以下依赖**
   ```xml
   <!-- 移除微信SDK -->
   <dependency>
       <groupId>com.github.binarywang</groupId>
       <artifactId>weixin-java-miniapp</artifactId>
   </dependency>
   <dependency>
       <groupId>com.github.binarywang</groupId>
       <artifactId>weixin-java-mp</artifactId>
   </dependency>
   <dependency>
       <groupId>com.github.binarywang</groupId>
       <artifactId>weixin-java-pay</artifactId>
   </dependency>

   <!-- 移除支付宝SDK -->
   <dependency>
       <groupId>com.alipay.sdk</groupId>
       <artifactId>alipay-sdk-java</artifactId>
   </dependency>

   <!-- 移除阿里云SDK -->
   <dependency>
       <groupId>com.aliyun</groupId>
       <artifactId>dysmsapi20170525</artifactId>
   </dependency>
   <dependency>
       <groupId>com.aliyun</groupId>
       <artifactId>tea</artifactId>
   </dependency>
   <dependency>
       <groupId>com.aliyun.oss</groupId>
       <artifactId>aliyun-sdk-oss</artifactId>
   </dependency>

   <!-- 移除腾讯云SDK -->
   <dependency>
       <groupId>com.tencentcloudapi</groupId>
       <artifactId>tencentcloud-sdk-java-sms</artifactId>
   </dependency>
   ```

2. **确保infrastructure/pom.xml包含所有SDK依赖**（已包含，无需修改）

3. **编译验证**
   ```bash
   mvn clean compile -DskipTests
   ```

### 风险
- **低风险**：接口已完全隔离SDK类型，只需移除依赖即可

---

## 验证计划

### 编译验证
每个任务完成后执行：
```bash
mvn clean compile -DskipTests
```

### 功能验证
1. 启动应用，验证核心功能正常
2. 测试会员注册、登录、积分变更等核心流程
3. 测试支付流程（如有）
4. 测试微信授权登录（如有）

### 代码审查
- 检查充血模型的方法命名是否体现业务语义
- 检查业务规则是否正确封装在实体中
- 检查application层是否正确调用实体方法

---

## 关键文件清单

### 任务1：BaseEntity包名调整
- `domain/src/main/java/com/eghm/model/BaseEntity.java`（移动后）
- 24个继承BaseEntity的实体类（修改import）

### 任务2：贫血实体充血化
- 40个实体类（domain模块）
- 对应的Service实现类（application模块）

### 任务4：清理SDK依赖
- `application/pom.xml`（移除SDK依赖）

---

## 时间估算

| 任务 | 预计时间 |
|------|----------|
| 任务1：BaseEntity包名调整 | 0.5天 |
| 任务4：清理SDK依赖 | 0.5天 |
| 任务2：贫血实体充血化 | 3-5天 |
| **总计** | **4-6天** |

---

## 注意事项

1. **分批提交**：每完成一个任务就提交一次，便于回滚
2. **编译验证**：每个任务完成后必须编译验证
3. **代码审查**：充血模型的方法设计需要仔细审查
4. **测试覆盖**：建议为核心实体的新方法编写单元测试
