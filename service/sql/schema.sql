/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50729
Source Host           : 127.0.0.1:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-02-04 19:06:30
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `classify`     varchar(10)         NOT NULL DEFAULT '' COMMENT '版本类型 ANDROID IOS',
    `version`      varchar(10)         NOT NULL COMMENT '版本号:1.2.8 范围(0.0.01~99.99.99)',
    `version_no`   int(10)                      DEFAULT NULL COMMENT '数字格式化后的版本号',
    `force_update` bit(1)                       DEFAULT b'0' COMMENT '是否强制更新 0:否 1:是',
    `url`          varchar(500)                 DEFAULT NULL COMMENT '下载地址,android为实际下载地址,ios是跳转到app_store',
    `create_time`  datetime                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `remark`       varchar(500)                 DEFAULT NULL COMMENT '备注信息:版本更新的东西或解决的问题',
    `deleted`      bit(1)                       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `update_time`  datetime                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `state`        tinyint(1) unsigned          DEFAULT '0' COMMENT '上架状态 0:待上架 1:已上架',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='APP版本管理表';

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)         DEFAULT NULL COMMENT '标题信息',
    `classify`    tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图类型:由system_dict的banner_classify维护(不同模块的轮播均在该表中维护)',
    `client_type` varchar(20)         DEFAULT 'PC' COMMENT '客户端类型 PC ANDROID IOS H5',
    `img_url`     varchar(200)        NOT NULL COMMENT '轮播图片地址',
    `jump_url`    varchar(200)        DEFAULT NULL COMMENT '轮播图点击后跳转的URL',
    `sort`        tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图顺序(大<->小) 最大的在最前面',
    `start_time`  datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '开始展示时间(可在指定时间后开始展示)',
    `end_time`    datetime            DEFAULT NULL COMMENT '取消展示的时间(只在某个时间段展示)',
    `click`       bit(1)              DEFAULT b'1' COMMENT '是否可点击 0:否 1:可以',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)              DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `remark`      varchar(200)        DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `type_client_type_index` (`classify`, `client_type`) USING BTREE COMMENT '组合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='轮播图表';

-- ----------------------------
-- Table structure for black_roster
-- ----------------------------
DROP TABLE IF EXISTS `black_roster`;
CREATE TABLE `black_roster`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `start_ip`    bigint(20) DEFAULT NULL COMMENT '访问ip',
    `end_ip`      bigint(20) DEFAULT NULL COMMENT '访问ip,长整型',
    `deleted`     bit(1)     DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    `create_time` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='访问黑名单';

-- ----------------------------
-- Table structure for email_template
-- ----------------------------
DROP TABLE IF EXISTS `email_template`;
CREATE TABLE `email_template`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nid`         varchar(30)  DEFAULT NULL COMMENT '模板唯一编码',
    `title`       varchar(50)  DEFAULT NULL COMMENT '模板标题',
    `content`     text COMMENT '模板内容',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    KEY `idx_nid` (`nid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4 COMMENT ='邮件模板';

-- ----------------------------
-- Table structure for exception_log
-- ----------------------------
DROP TABLE IF EXISTS `webapp_log`;
CREATE TABLE `webapp_log`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`     bigint(20)    DEFAULT NULL COMMENT '用户id',
    `url`           varchar(50)   DEFAULT NULL COMMENT '访问链接',
    `request_param` varchar(2000) DEFAULT NULL COMMENT '请求参数(json)',
    `error_msg`     text COMMENT '错误日志',
    `version`       varchar(20)   DEFAULT NULL COMMENT '版本号',
    `channel`       varchar(20)   DEFAULT NULL COMMENT '客户端类型 ANDROID,IOS,PC,H5',
    `os_version`    varchar(20)   DEFAULT NULL COMMENT '客户端平台版本号 ios: 10.4.1,android:8.1.0',
    `device_brand`  varchar(50)   DEFAULT NULL COMMENT '设备厂商',
    `device_model`  varchar(50)   DEFAULT NULL COMMENT '设备型号',
    `serial_number` varchar(50)   DEFAULT NULL COMMENT '设备唯一编号',
    `ip`            varchar(40)   DEFAULT NULL COMMENT '访问ip',
    `trace_id`      varchar(40)   DEFAULT NULL COMMENT '访问traceId',
    `elapsed_time`  bigint(20)    DEFAULT NULL COMMENT '耗时',
    `create_time`   datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `url_index` (`url`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='移动端操作记录';

-- ----------------------------
-- Table structure for feedback_log
-- ----------------------------
DROP TABLE IF EXISTS `feedback_log`;
CREATE TABLE `feedback_log`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`      bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
    `classify`       tinyint(1)          DEFAULT NULL COMMENT '反馈类型分类',
    `state`          bit(1)              DEFAULT b'0' COMMENT '状态: 0:待解决 1:已解决',
    `version`        varchar(50)         DEFAULT NULL COMMENT '软件版本',
    `system_version` varchar(50)         DEFAULT NULL COMMENT '系统版本',
    `content`        varchar(200)        DEFAULT NULL COMMENT '反馈内容',
    `create_time`    datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
    `update_time`    datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)              DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `image_url`      varchar(500)        DEFAULT NULL COMMENT '图片地址',
    `device_brand`   varchar(50)         DEFAULT NULL COMMENT '设备厂商',
    `device_model`   varchar(50)         DEFAULT NULL COMMENT '设备型号',
    `user_id`        bigint(20) unsigned DEFAULT NULL COMMENT '处理人id',
    `user_name`      varchar(20)         DEFAULT NULL COMMENT '处理人姓名',
    `remark`         varchar(200)        DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `index_status` (`state`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='APP用户反馈信息表';

-- ----------------------------
-- Table structure for help_center
-- ----------------------------
DROP TABLE IF EXISTS `help_center`;
CREATE TABLE `help_center`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `classify`    tinyint(2) unsigned DEFAULT NULL COMMENT '帮助分类取system_dict表中help_classify字段',
    `state`       tinyint(1) unsigned DEFAULT '1' COMMENT '状态 0:不显示 1:显示',
    `ask`         varchar(100)        DEFAULT NULL COMMENT '问',
    `answer`      text                DEFAULT NULL COMMENT '答',
    `sort`        tinyint(4)          DEFAULT '0' COMMENT '排序(小<->大)',
    `deleted`     bit(1)              DEFAULT b'0' COMMENT '删除状态 0:不删除(正常) 1:已删除',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='帮助说明信息表';

-- ----------------------------
-- Table structure for image_log
-- ----------------------------
DROP TABLE IF EXISTS `image_log`;
CREATE TABLE `image_log`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)         DEFAULT NULL COMMENT '图片名称',
    `classify`    tinyint(3) unsigned DEFAULT NULL COMMENT '图片分类 数据字典image_classify',
    `path`        varchar(200)        DEFAULT NULL COMMENT '文件存放地址',
    `size`        bigint(15) unsigned DEFAULT NULL COMMENT '文件大小',
    `remark`      varchar(200)        DEFAULT NULL COMMENT '备注信息',
    `deleted`     bit(1)              DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='图片上传记录';

-- ----------------------------
-- Table structure for login_device
-- ----------------------------
DROP TABLE IF EXISTS `login_device`;
CREATE TABLE `login_device`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`     bigint(20)   DEFAULT NULL COMMENT '用户id',
    `ip`            bigint(20)   DEFAULT NULL COMMENT '登录ip',
    `serial_number` varchar(128) DEFAULT NULL COMMENT '设备唯一序列号',
    `device_model`  varchar(50)  DEFAULT NULL COMMENT '设备型号',
    `login_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次登陆的时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_complex` (`member_id`, `serial_number`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='登陆设备管理表';

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`        bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
    `channel`          varchar(10)         DEFAULT NULL COMMENT '登陆渠道',
    `ip`               bigint(20)          DEFAULT '0' COMMENT '登陆ip',
    `create_time`      datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
    `device_brand`     varchar(30)         DEFAULT NULL COMMENT '设备厂商',
    `device_model`     varchar(50)         DEFAULT NULL COMMENT '设备型号',
    `software_version` varchar(12)         DEFAULT NULL COMMENT '软件版本',
    `serial_number`    varchar(64)         DEFAULT NULL COMMENT '设备唯一编号',
    `deleted`          bit(1)              DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_member_serial` (`member_id`, `serial_number`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户登陆日志信息';

-- ----------------------------
-- Table structure for notice_template
-- ----------------------------
DROP TABLE IF EXISTS `notice_template`;
CREATE TABLE `notice_template`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        varchar(30)  DEFAULT NULL COMMENT '消息模板code',
    `title`       varchar(50)  DEFAULT NULL COMMENT '消息标题',
    `content`     varchar(500) DEFAULT NULL COMMENT '模板内容消息',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='站内信消息模板';

-- ----------------------------
-- Table structure for push_template
-- ----------------------------
DROP TABLE IF EXISTS `push_template`;
CREATE TABLE `push_template`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)  DEFAULT NULL COMMENT '消息名称',
    `nid`         varchar(50)  DEFAULT NULL COMMENT '消息nid',
    `state`       tinyint(1)   DEFAULT '1' COMMENT '状态 0:关闭 1:开启',
    `content`     varchar(200) DEFAULT NULL COMMENT '消息内容',
    `tag`         varchar(50)  DEFAULT NULL COMMENT '标签(消息推送跳转页面,与移动端约定好)',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推送消息模板表';

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `sms_type`    varchar(20)         DEFAULT NULL COMMENT '短信分类',
    `mobile`      char(11)            DEFAULT NULL COMMENT '手机号',
    `content`     varchar(100)        DEFAULT NULL COMMENT '短信内容',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    `state`       tinyint(1) unsigned DEFAULT '0' COMMENT '发送状态 0:发送中 1:已发送 2:发送失败',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `mobile_index` (`mobile`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信日志记录表';

-- ----------------------------
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nid`         varchar(20)  DEFAULT NULL COMMENT '短信模板nid即短信类型',
    `content`     varchar(120) DEFAULT NULL COMMENT '短信内容',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信模板类型';

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`
(
    `id`       bigint(20) unsigned NOT NULL COMMENT '区域代码 唯一',
    `title`    varchar(50)         DEFAULT NULL COMMENT '区域名称',
    `pid`      bigint(20)          DEFAULT '0' COMMENT '父级区域代码',
    `zip_code` char(6)             DEFAULT NULL COMMENT '邮编',
    `mark`     char(1)             DEFAULT NULL COMMENT '标示符-首字母',
    `classify` tinyint(1) unsigned DEFAULT NULL COMMENT '分类 省份1级 市2级 县3级',
    PRIMARY KEY (`id`),
    KEY `idx_pid` (`pid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='省市县代码表';

-- ----------------------------
-- Table structure for sys_cache
-- ----------------------------
DROP TABLE IF EXISTS `sys_cache`;
CREATE TABLE `sys_cache`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)         DEFAULT NULL COMMENT '缓存名称',
    `cache_name`  varchar(50)         DEFAULT NULL COMMENT '缓存名称 必须与CacheConstant中保持一致',
    `state`       tinyint(3) unsigned DEFAULT '0' COMMENT '缓存更新状态 0:未更新 1:更新成功 2:更新失败',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      varchar(200)        DEFAULT NULL COMMENT '备注说明',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4 COMMENT ='缓存信息管理表';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nid`         varchar(50)         NOT NULL COMMENT '参数标示符',
    `title`       varchar(50)  DEFAULT NULL COMMENT '参数名称',
    `content`     varchar(500)        NOT NULL COMMENT '参数值',
    `locked`      bit(1)       DEFAULT b'0' COMMENT '锁定状态(禁止编辑) 0:未锁定,1:锁定',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `nid_index` (`nid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统参数配置信息表';

-- ----------------------------
-- Table structure for sys_data_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dept`;
CREATE TABLE `sys_data_dept`
(
    `id`        bigint(20) NOT NULL COMMENT '主键',
    `user_id`   bigint(20)  DEFAULT NULL COMMENT '用户id',
    `dept_code` varchar(20) DEFAULT NULL COMMENT '部门编号',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户与部门数据权限关联表(自定义数据权限)';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)         DEFAULT NULL COMMENT '部门名称',
    `code`        varchar(128)        DEFAULT NULL COMMENT '部门编号',
    `parent_code` varchar(128)        DEFAULT '0' COMMENT '父级编号',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)              DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `remark`      varchar(200)        DEFAULT NULL COMMENT '备注信息',
    `user_id`     bigint(20) unsigned DEFAULT NULL COMMENT '操作人id',
    `user_name`   varchar(20)         DEFAULT NULL COMMENT '操作人姓名',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code_index` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门信息表';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(50)  DEFAULT NULL COMMENT '字典中文名称',
    `nid`         varchar(50)  DEFAULT NULL COMMENT '字典编码',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
    `locked`      bit(1)       DEFAULT b'0' COMMENT '锁定状态(禁止编辑):0:未锁定 1:锁定',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据字典表';

DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict_item`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nid`          bigint(50)          DEFAULT NULL COMMENT '字典编号',
    `hidden_value` tinyint(2) unsigned DEFAULT NULL COMMENT '数据字典隐藏值 1~∞',
    `show_value`   varchar(50)         DEFAULT NULL COMMENT '显示值',
    `deleted`      bit(1)              DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
    `create_time`  datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据字典选项表';


-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `calendar`   date                DEFAULT NULL COMMENT '日期',
    `date_month` char(7)             DEFAULT NULL COMMENT '月份 yyyy-MM',
    `weekday`    tinyint(1) unsigned DEFAULT NULL COMMENT '星期几',
    `state`      tinyint(1) unsigned DEFAULT '0' COMMENT '是否为节假日 0:否 1:是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统节假日表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`            varchar(20) NOT NULL COMMENT '主键',
    `title`         varchar(20) NOT NULL COMMENT '菜单名称',
    `code`          varchar(50) NOT NULL COMMENT '菜单标示符(自动生成)',
    `pid`           varchar(20) NOT NULL COMMENT '父节点ID,一级菜单默认为0',
    `path`          varchar(200)        DEFAULT NULL COMMENT '菜单地址',
    `sub_path`      varchar(500)        DEFAULT NULL COMMENT '权限拦截路径',
    `grade`         tinyint(1) unsigned DEFAULT '1' COMMENT '菜单级别 1:导航菜单 2:按钮菜单',
    `sort`          int(10)             DEFAULT '0' COMMENT '排序规则 小的排在前面',
    `state`         bit(1)              DEFAULT b'1' COMMENT '状态: 1:启用 0:禁用',
    `remark`        varchar(200)        DEFAULT NULL COMMENT '备注信息',
    `display_state` tinyint(1)          DEFAULT NULL COMMENT '显示状态 1:商户显示(该菜单或按钮只对商户开放) 2:系统显示(该菜单或按钮只对系统人员开放)  3:全部显示(该菜单或按钮对商户和系统人员都开放)',
    `create_time`   datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `pid_index` (`pid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1080
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统菜单表';

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `manage_log`;
CREATE TABLE `manage_log`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `url`           varchar(200)        DEFAULT NULL COMMENT '请求地址',
    `user_id`       bigint(20) unsigned DEFAULT NULL COMMENT '操作人',
    `user_name`     varchar(50)         DEFAULT NULL COMMENT '操作人姓名',
    `request`       varchar(1000)       DEFAULT NULL COMMENT '请求参数',
    `response`      longtext COMMENT '响应参数',
    `create_time`   datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `ip`            varchar(20)         DEFAULT NULL COMMENT '访问ip',
    `business_time` bigint(12) unsigned DEFAULT NULL COMMENT '业务耗时',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理后台操作记录';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nick_name`   varchar(20)         NOT NULL COMMENT '用户名称',
    `mobile`      char(11)            NOT NULL COMMENT '手机号码(登陆账户)',
    `user_type`   tinyint(2)          DEFAULT 1 COMMENT '用户类型 1:系统用户 2: 商户管理员 3:商户普通用户',
    `data_type`   tinyint(2)          DEFAULT 1 COMMENT '数据权限(1:本人,2:本部门,4:本部门及子部门 8:全部 16:自定义),只针对系统用户',
    `state`       tinyint(1) unsigned DEFAULT '1' COMMENT '用户状态:0:锁定,1:正常',
    `pwd`         varchar(256)        DEFAULT NULL COMMENT '登陆密码MD5',
    `init_pwd`    varchar(256)        DEFAULT NULL COMMENT '初始密码',
    `dept_code`   varchar(20)         DEFAULT NULL COMMENT '所属部门编号',
    `deleted`     bit(1)              DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      varchar(200)        DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `name_index` (`nick_name`) USING BTREE,
    KEY `mobile_index` (`mobile`) USING BTREE,
    KEY `status_index` (`state`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理后台用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_role_idx` (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色与用户关系表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_name`   varchar(10)  DEFAULT NULL COMMENT '角色名称',
    `role_type`   varchar(20)  DEFAULT 'common' COMMENT '角色类型',
    `merchant_id` bigint(20)   DEFAULT NULL COMMENT '角色所属商户',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态:0:正常,1:已删除',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `role_name_index` (`role_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色Id',
    `menu_id`     varchar(20)         NOT NULL COMMENT '菜单Id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `r_m_idx` (`role_id`, `menu_id`),
    KEY `role_id_index` (`role_id`) USING BTREE,
    KEY `menu_id_index` (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 156
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色与菜单关系表';


-- ----------------------------
-- Table structure for tag_view
-- ----------------------------
DROP TABLE IF EXISTS `tag_view`;
CREATE TABLE `tag_view`
(
    `id`     bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `title`  varchar(50)   DEFAULT NULL COMMENT '页面名称',
    `tag`    varchar(50)   DEFAULT NULL COMMENT 'view唯一标示符',
    `url`    varchar(1000) DEFAULT NULL COMMENT 'view页面涉及到的接口,逗号分隔',
    `remark` varchar(200)  DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='移动端页面映射表';

-- ----------------------------
-- Table structure for task_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`           varchar(50)                  DEFAULT NULL COMMENT '定时任务名称',
    `bean_name`       varchar(200)                 DEFAULT NULL COMMENT 'bean名称 必须实现Task接口',
    `method_name`     varchar(50)                  DEFAULT NULL COMMENT '方法名',
    `args`            varchar(300)                 DEFAULT NULL COMMENT '方法入参',
    `cron_expression` varchar(50)                  DEFAULT NULL COMMENT 'cron表达式',
    `alarm_email`     varchar(30)                  DEFAULT NULL COMMENT '错误报警邮箱',
    `state`           tinyint(1) unsigned          DEFAULT '1' COMMENT '状态 0:关闭 1:开启',
    `lock_time`       bigint(20) unsigned NOT NULL DEFAULT 30000 COMMENT '锁定时间(多副本时防止并发),单位:毫秒',
    `update_time`     datetime                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`          varchar(255)                 DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_index` (`bean_name`, `method_name`) USING BTREE COMMENT 'nid必须唯一'
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务配置表';



-- ----------------------------
-- Table structure for task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_log`;
CREATE TABLE `sys_task_log`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bean_name`    varchar(50)  DEFAULT NULL COMMENT '定时任务bean名称',
    `method_name`  varchar(30)  DEFAULT NULL COMMENT '方法名',
    `args`         varchar(300) DEFAULT NULL COMMENT '方法参数',
    `state`        bit(1)       DEFAULT b'1' COMMENT '执行结果 0:失败 1:成功',
    `start_time`   datetime     DEFAULT NULL COMMENT '开始执行时间',
    `elapsed_time` bigint(20)   DEFAULT '0' COMMENT '总耗时',
    `error_msg`    text COMMENT '执行错误时的信息',
    `ip`           varchar(50)  DEFAULT NULL COMMENT '执行任务的机器ip',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务执行日志';


-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `avatar`      varchar(200)                                          DEFAULT NULL COMMENT '头像路径',
    `mobile`      char(11)                                              DEFAULT NULL COMMENT '手机号码',
    `mp_open_id`  varchar(64)                                           DEFAULT NULL COMMENT '微信公众号openId',
    `ma_open_id`  varchar(64)                                           DEFAULT NULL COMMENT '微信小程序openId',
    `union_id`    varchar(64)                                           DEFAULT NULL COMMENT '微信unionId',
    `nick_name`   varchar(40)                                           DEFAULT '' COMMENT '昵称',
    `email`       varchar(50)                                           DEFAULT NULL COMMENT '电子邮箱',
    `pwd`         varchar(256)                                          DEFAULT NULL COMMENT '登陆密码',
    `state`       bit(1)                                                DEFAULT b'1' COMMENT '状态 0:冻结 1:正常 ',
    `real_name`   varchar(20)                                           DEFAULT NULL COMMENT '真实姓名',
    `id_card`     varchar(256)                                          DEFAULT NULL COMMENT '身份证号码',
    `birthday`    char(8)                                               DEFAULT NULL COMMENT '生日 yyyyMMdd',
    `invite_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邀请码',
    `sex`         tinyint(1)                                            DEFAULT '2' COMMENT '性别 0:女性 1:男 2:未知',
    `score`       int(10)                                               DEFAULT '0' COMMENT '积分',
    `channel`     varchar(20)                                           DEFAULT NULL COMMENT '注册渠道',
    `register_ip` bigint(20)                                            DEFAULT NULL COMMENT '注册地址',
    `create_time` datetime                                              DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `update_time` datetime                                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `mobile_index` (`mobile`) USING BTREE,
    KEY `email_index` (`email`) USING BTREE,
    KEY `channel_index` (`channel`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='前台用户基本信息表';

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `member_address`;
CREATE TABLE `member_address`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`      bigint(20)   DEFAULT NULL COMMENT '用户id',
    `nick_name`      varchar(10)  DEFAULT NULL COMMENT '昵称',
    `mobile`         varchar(11)  DEFAULT NULL COMMENT '手机号码',
    `state`          tinyint(1)   DEFAULT '0' COMMENT '状态 0: 普通地址  1:默认地址',
    `province_id`    bigint(20)   DEFAULT NULL COMMENT '省份id',
    `province_name`  varchar(50)  DEFAULT NULL COMMENT '省份名称',
    `city_id`        bigint(20)   DEFAULT NULL COMMENT '城市id',
    `city_name`      varchar(50)  DEFAULT NULL COMMENT '城市名称',
    `county_id`      bigint(20)   DEFAULT NULL COMMENT '县区id',
    `county_name`    varchar(50)  DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(255) DEFAULT NULL COMMENT '详细地址',
    `deleted`        bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `create_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户收货地址表';

-- ----------------------------
-- Table structure for member_invite_log
-- ----------------------------
DROP TABLE IF EXISTS `member_invite_log`;
CREATE TABLE `member_invite_log`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`        bigint(20) DEFAULT NULL COMMENT '用户id',
    `invite_member_id` bigint(20) DEFAULT NULL COMMENT '被邀请人id',
    `create_time`      datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户邀请记录表';

-- ----------------------------
-- Table structure for member_notice
-- ----------------------------
DROP TABLE IF EXISTS `member_notice`;
CREATE TABLE `member_notice`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`   bigint(20)   DEFAULT NULL COMMENT '用户id',
    `title`       varchar(50)  DEFAULT NULL COMMENT '消息标题',
    `content`     varchar(500) DEFAULT NULL COMMENT '站内信内容',
    `classify`    varchar(30)  DEFAULT NULL COMMENT '站内信分类',
    `is_read`     bit(1)       DEFAULT b'0' COMMENT '状态 0:未读 1:已读',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户站内信';

-- ----------------------------
-- Table structure for member_score_log
-- ----------------------------
DROP TABLE IF EXISTS `member_score_log`;
CREATE TABLE `member_score_log`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_id`   bigint(20)   DEFAULT NULL COMMENT '用户id',
    `score`       int(10)      DEFAULT '0' COMMENT '本次收入或支出的积分数',
    `type`        tinyint(2)   DEFAULT NULL COMMENT '积分收入或支出分类',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户积分日志表(不清零)';

DROP TABLE IF EXISTS `pay_bank`;
CREATE TABLE `pay_bank`
(
    `id`          int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bank_type`   varchar(20)  DEFAULT NULL COMMENT '银行类型',
    `bank_name`   varchar(100) DEFAULT NULL COMMENT '银行名称',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 253
  DEFAULT CHARSET = utf8mb4 COMMENT ='微信支付银行卡类型表';

DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(30) DEFAULT NULL COMMENT '公告标题',
    `classify`    tinyint(2)  DEFAULT NULL COMMENT '公告分类',
    `content`     text COMMENT '公告内容',
    `state`       bit(1)      DEFAULT b'0' COMMENT '是否发布 0:未发布 1:已发布',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统公告';

DROP TABLE IF EXISTS `sensitive_word`;
CREATE TABLE `sensitive_word`
(
    `id`          varchar(64) NOT NULL COMMENT '主键',
    `keyword`     varchar(200)         DEFAULT NULL COMMENT '敏感字',
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      NOT NULL DEFAULT b'0' COMMENT '删除状态: 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='敏感词库';