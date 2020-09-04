
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify` varchar(10) NOT NULL DEFAULT '' COMMENT '版本类型 ANDROID IOS',
  `version` varchar(10) NOT NULL COMMENT '版本号:1.2.8 范围(0.0.01~99.99.99)',
  `version_no` int(10) DEFAULT NULL COMMENT '数字格式化后的版本号',
  `force_update` bit(1) DEFAULT b'0' COMMENT '是否强制更新 0:否 1:是',
  `url` varchar(500) DEFAULT NULL COMMENT '下载地址,android为实际下载地址,ios是跳转到app_store',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注信息:版本更新的东西或解决的问题',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '上架状态 0:待上架 1:已上架',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP版本管理表';

-- ----------------------------
-- Table structure for audit_config
-- ----------------------------
DROP TABLE IF EXISTS `audit_config`;
CREATE TABLE `audit_config` (
  `id` int(10) NOT NULL COMMENT '主键',
  `audit_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核类型',
  `role_type` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色类型',
  `step` tinyint(2) DEFAULT '1' COMMENT '审批时的步骤(1,2,3)',
  `rejection_policy` bigint(1) unsigned DEFAULT '1' COMMENT '拒绝策略 1:结束流程 2:退回上一步 3:退回第一步(不开启)',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='审核流程配置表';

-- ----------------------------
-- Table structure for audit_record
-- ----------------------------
DROP TABLE IF EXISTS `audit_record`;
CREATE TABLE `audit_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `audit_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审批单号(在整个审批流程中不变)',
  `state` tinyint(1) DEFAULT NULL COMMENT '审核状态 0:待审核 1:审批通过 2:审批拒绝',
  `opinion` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审批意见',
  `title` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核信息标题',
  `apply_id` int(11) DEFAULT NULL COMMENT '关联该审批的申请id',
  `apply_operator_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `apply_operator_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '申请人姓名',
  `role_type` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核人角色类型',
  `audit_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核类型',
  `step` tinyint(2) DEFAULT NULL COMMENT '当前审核的步骤',
  `audit_operator_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `audit_operator_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id_state` (`audit_operator_id`,`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='审核记录表';

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '标题信息',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图类型:由system_dict的banner_classify维护(不同模块的轮播均在该表中维护)',
  `client_type` varchar(20) DEFAULT 'PC' COMMENT '客户端类型 PC ANDROID IOS H5',
  `img_url` varchar(200) NOT NULL COMMENT '轮播图片地址',
  `turn_url` varchar(200) DEFAULT NULL COMMENT '轮播图点击后跳转的URL',
  `sort` tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图顺序(大<->小) 最大的在最前面',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始展示时间(可在指定时间后开始展示)',
  `end_time` datetime DEFAULT NULL COMMENT '取消展示的时间(只在某个时间段展示)',
  `click` bit(1) DEFAULT b'1' COMMENT '是否可点击 0:否 1:可以',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type_client_type_index` (`classify`,`client_type`) USING BTREE COMMENT '组合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图维护表';

-- ----------------------------
-- Table structure for black_roster
-- ----------------------------
DROP TABLE IF EXISTS `black_roster`;
CREATE TABLE `black_roster` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` bigint(20) DEFAULT NULL COMMENT '访问ip',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
  `end_time` datetime DEFAULT NULL COMMENT '黑名单截止时间',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问黑名单';

-- ----------------------------
-- Table structure for exception_log
-- ----------------------------
DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(50) DEFAULT NULL COMMENT '访问链接',
  `request_params` varchar(1000) DEFAULT NULL COMMENT '请求参数(json)',
  `error_msg` text COMMENT '错误日志',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `url_index` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统异常记录表';

-- ----------------------------
-- Table structure for feedback_log
-- ----------------------------
DROP TABLE IF EXISTS `feedback_log`;
CREATE TABLE `feedback_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `classify` tinyint(1) DEFAULT NULL COMMENT '反馈类型分类',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '状态: 0:待解决 1:已解决',
  `version` varchar(50) DEFAULT NULL COMMENT '软件版本',
  `system_version` varchar(50) DEFAULT NULL COMMENT '系统版本',
  `content` varchar(200) DEFAULT NULL COMMENT '反馈内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `device_brand` varchar(50) DEFAULT NULL COMMENT '设备厂商',
  `device_model` varchar(50) DEFAULT NULL COMMENT '设备型号',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '处理人id',
  `operator_name` varchar(20) DEFAULT NULL COMMENT '处理人姓名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_status` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP用户反馈信息表';

-- ----------------------------
-- Table structure for help_center
-- ----------------------------
DROP TABLE IF EXISTS `help_center`;
CREATE TABLE `help_center` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '帮助分类取system_dict表中help_classify字段',
  `state` tinyint(1) unsigned DEFAULT '1' COMMENT '状态 0:不显示 1:显示',
  `ask` varchar(50) DEFAULT NULL COMMENT '问',
  `answer` varchar(500) DEFAULT NULL COMMENT '答 支持',
  `sort` tinyint(4) DEFAULT '0' COMMENT '排序(小<->大)',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:不删除(正常) 1:已删除',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助说明信息表';

-- ----------------------------
-- Table structure for image_log
-- ----------------------------
DROP TABLE IF EXISTS `image_log`;
CREATE TABLE `image_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '图片名称',
  `classify` tinyint(3) unsigned DEFAULT NULL COMMENT '图片分类 数据字典image_classify',
  `url` varchar(200) DEFAULT NULL COMMENT '文件存放地址',
  `size` bigint(15) unsigned DEFAULT NULL COMMENT '文件大小',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片上传记录';

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `channel` varchar(10) DEFAULT NULL COMMENT '登陆渠道',
  `ip` bigint(20) DEFAULT '0' COMMENT '登陆ip',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
  `device_brand` varchar(30) DEFAULT NULL COMMENT '设备厂商',
  `device_model` varchar(50) DEFAULT NULL COMMENT '设备型号',
  `software_version` varchar(12) DEFAULT NULL COMMENT '软件版本',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '设备唯一编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登陆日志信息';

-- ----------------------------
-- Table structure for push_template
-- ----------------------------
DROP TABLE IF EXISTS `push_template`;
CREATE TABLE `push_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '消息名称',
  `nid` varchar(50) DEFAULT NULL COMMENT '消息nid',
  `state` tinyint(1) DEFAULT '1' COMMENT '状态 0:关闭 1:开启',
  `content` varchar(200) DEFAULT NULL COMMENT '消息内容',
  `tag` varchar(50) DEFAULT NULL COMMENT '标签(消息推送跳转页面,与移动端约定好)',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推送消息模板表';

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sms_type` varchar(20) DEFAULT NULL COMMENT '短信分类',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `content` varchar(100) DEFAULT NULL COMMENT '短信内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '发送状态 0:发送中 1:已发送 2:发送失败',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信日志记录表';

-- ----------------------------
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` varchar(20) DEFAULT NULL COMMENT '短信模板nid即短信类型',
  `content` varchar(120) DEFAULT NULL COMMENT '短信内容',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='短信模板类型';

-- ----------------------------
-- Table structure for sys_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_address`;
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int(10) unsigned NOT NULL COMMENT '区域代码 唯一',
  `title` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `pid` int(10) DEFAULT '0' COMMENT '父级区域代码',
  `zip_code` char(6) DEFAULT NULL COMMENT '邮编',
  `mark` char(1) DEFAULT NULL COMMENT '标示符-首字母',
  `classify` tinyint(1) unsigned DEFAULT NULL COMMENT '分类 省份1级 市2级 县3级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省市县代码表';

-- ----------------------------
-- Table structure for sys_cache
-- ----------------------------
DROP TABLE IF EXISTS `sys_cache`;
CREATE TABLE `sys_cache` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '缓存名称',
  `cache_name` varchar(50) DEFAULT NULL COMMENT '缓存名称 必须与CacheConstant中保持一致',
  `state` tinyint(3) unsigned DEFAULT '0' COMMENT '缓存更新状态 0:未更新 1:更新成功 2:更新失败',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='缓存信息管理表';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` varchar(50) NOT NULL COMMENT '参数标示符',
  `title` varchar(50) DEFAULT NULL COMMENT '参数名称',
  `content` varchar(500) NOT NULL COMMENT '参数值',
  `classify` tinyint(2) unsigned DEFAULT '1' COMMENT '参数类型,见system_dict表nid=config_classify',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定状态(禁止编辑) 0:未锁定,1:锁定',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `nid_index` (`nid`) USING BTREE,
  KEY `type_index` (`classify`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数配置信息表';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `code` varchar(128) DEFAULT NULL COMMENT '部门编号',
  `parent_code` varchar(128) DEFAULT '0' COMMENT '父级编号',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `operator_name` varchar(20) DEFAULT NULL COMMENT '操作人姓名',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '字典中文名称',
  `nid` varchar(50) DEFAULT NULL COMMENT '数据字典nid(英文名称)',
  `hidden_value` tinyint(2) unsigned DEFAULT NULL COMMENT '数据字典隐藏值 1~∞',
  `show_value` varchar(50) DEFAULT NULL COMMENT '显示值',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定状态(禁止编辑):0:未锁定 1:锁定',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统数据字典表';

-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `calendar` date DEFAULT NULL COMMENT '日期',
  `date_month` char(7) DEFAULT NULL COMMENT '月份 yyyy-MM',
  `weekday` tinyint(1) unsigned DEFAULT NULL COMMENT '星期几',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '是否为节假日 0:否 1:是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统节假日表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(20) NOT NULL COMMENT '菜单名称',
  `nid` varchar(50) NOT NULL COMMENT '菜单标示符 唯一',
  `pid` int(10) unsigned NOT NULL COMMENT '父节点ID,一级菜单默认为0',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单地址',
  `sub_url` varchar(500) DEFAULT NULL COMMENT '权限拦截路径',
  `grade` tinyint(1) unsigned DEFAULT '1' COMMENT '菜单级别 1:一级菜单(导航) 2:二级菜单(导航) 3:三级菜单(按钮)',
  `sort` int(3) DEFAULT '0' COMMENT '排序规则 小的排在前面',
  `deleted` bit(1) DEFAULT b'0' COMMENT '状态:0:正常,1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `nid_unique_index` (`nid`,`deleted`) USING BTREE,
  KEY `pid_index` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1080 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) NOT NULL COMMENT '公告标题',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '公告类型(数据字典表notice_classify)',
  `content` text COMMENT '公告内容(富文本)',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '是否发布 0:未发布 1:已发布',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常 1:删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告信息表';

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(200) DEFAULT NULL COMMENT '请求地址',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作人',
  `operator_name` varchar(50) DEFAULT NULL COMMENT '操作人姓名',
  `request` varchar(1000) DEFAULT NULL COMMENT '请求参数',
  `response` text COMMENT '响应参数',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `ip` bigint(20) DEFAULT NULL COMMENT '访问ip',
  `business_time` bigint(12) unsigned DEFAULT NULL COMMENT '业务耗时',
  `classify` tinyint(1) unsigned DEFAULT NULL COMMENT '操作日志分类,参考:MethodType',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台操作记录';

-- ----------------------------
-- Table structure for sys_operator
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operator_name` varchar(20) NOT NULL COMMENT '用户名称',
  `mobile` char(11) NOT NULL COMMENT '手机号码(登陆账户)',
  `state` tinyint(1) unsigned DEFAULT '1' COMMENT '用户状态:0:锁定,1:正常',
  `pwd` varchar(256) DEFAULT NULL COMMENT '登陆密码MD5',
  `init_pwd` varchar(256) DEFAULT NULL COMMENT '初始密码',
  `dept_code` varchar(20) DEFAULT NULL COMMENT '所属部门编号',
  `permission_type` tinyint(1) DEFAULT '1' COMMENT '数据权限类型 1:自己的权限 2:自己部门权限 3:自己部门及子部门 4: 自定义权限 5 全部',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `name_index` (`operator_name`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE,
  KEY `status_index` (`state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='管理后台用户表';

-- ----------------------------
-- Table structure for sys_operator_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_dept`;
CREATE TABLE `sys_operator_dept` (
  `id` int(10) NOT NULL,
  `operator_id` int(11) DEFAULT NULL COMMENT '用户id',
  `dept_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门编号',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户与部门数据权限关联表(自定义数据权限)';

-- ----------------------------
-- Table structure for sys_operator_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_role`;
CREATE TABLE `sys_operator_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operator_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_index` (`operator_id`) USING BTREE,
  KEY `role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='角色与用户关系表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(10) DEFAULT NULL COMMENT '角色名称',
  `role_type` varchar(20) DEFAULT NULL COMMENT '角色类型',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态:0:正常,1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_name_index` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色Id',
  `menu_id` int(10) unsigned NOT NULL COMMENT '菜单Id',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id_index` (`role_id`) USING BTREE,
  KEY `menu_id_index` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=975 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单关系表';

-- ----------------------------
-- Table structure for tag_view
-- ----------------------------
DROP TABLE IF EXISTS `tag_view`;
CREATE TABLE `tag_view` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '页面名称',
  `tag` varchar(50) DEFAULT NULL COMMENT 'view唯一标示符',
  `url` varchar(1000) DEFAULT NULL COMMENT 'view页面涉及到的接口,逗号分隔',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='移动端页面映射表';

-- ----------------------------
-- Table structure for task_config
-- ----------------------------
DROP TABLE IF EXISTS `task_config`;
CREATE TABLE `task_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '定时任务名称',
  `nid` varchar(20) DEFAULT NULL COMMENT '定时任务nid',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'bean名称 必须实现Task接口',
  `cron_expression` char(50) DEFAULT NULL COMMENT 'cron表达式',
  `state` tinyint(1) unsigned DEFAULT '1' COMMENT '状态 0:关闭 1:开启',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid_unique_index` (`nid`) USING BTREE COMMENT 'nid必须唯一'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务配置表';

-- ----------------------------
-- Table structure for task_log
-- ----------------------------
DROP TABLE IF EXISTS `task_log`;
CREATE TABLE `task_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` char(50) DEFAULT NULL COMMENT '任务nid',
  `bean_name` char(50) DEFAULT NULL COMMENT '定时任务bean名称',
  `state` bit(1) DEFAULT b'1' COMMENT '执行结果 0:失败 1:成功',
  `start_time` datetime DEFAULT NULL COMMENT '开始执行时间',
  `elapsed_time` bigint(20) DEFAULT '0' COMMENT '总耗时',
  `error_msg` text COMMENT '执行错误时的信息',
  `ip` char(50) DEFAULT NULL COMMENT '执行任务的机器ip',
  PRIMARY KEY (`id`),
  KEY `nid_index` (`nid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务执行日志';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号码',
  `open_id` varchar(64) DEFAULT NULL COMMENT '微信小程序openId',
  `nick_name` varchar(20) DEFAULT '' COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `pwd` varchar(256) DEFAULT NULL COMMENT '登陆密码',
  `state` bit(1) DEFAULT b'1' COMMENT '状态 0:冻结 1:正常 ',
  `channel` tinyint(1) unsigned DEFAULT '0' COMMENT '注册渠道',
  `register_ip` bigint(20) DEFAULT NULL COMMENT '注册地址',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像路径',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE,
  KEY `email_index` (`email`) USING BTREE,
  KEY `status_index` (`state`) USING BTREE,
  KEY `channel_index` (`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='前台用户基本信息表';

-- ----------------------------
-- Table structure for user_ext
-- ----------------------------
DROP TABLE IF EXISTS `user_ext`;
CREATE TABLE `user_ext` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(256) DEFAULT NULL COMMENT '身份证号码(前10位加密[18位身份证],前8位加密[15位身份证])',
  `birthday` char(8) DEFAULT NULL COMMENT '生日yyyyMMdd',
  `sex` tinyint(1) unsigned DEFAULT NULL COMMENT '性别 0:未知 1:男 2:女',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id_index` (`user_id`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户扩展信息表';

CREATE TABLE `email_template` (
  `id` int(10) NOT NULL COMMENT '主键',
  `nid` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板唯一编码',
  `title` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板标题',
  `content` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板内容',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `idx_nid` (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='邮件模板';
