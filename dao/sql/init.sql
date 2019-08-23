/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50628
Source Host           : 127.0.0.1:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2019-08-20 16:36:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_feedback
-- ----------------------------
DROP TABLE IF EXISTS `app_feedback`;
CREATE TABLE `app_feedback` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `state` tinyint(1) unsigned DEFAULT NULL COMMENT '状态: 0:待解决 1:已解决',
  `version` char(50) DEFAULT NULL COMMENT '软件版本',
  `system_version` char(50) DEFAULT NULL COMMENT '系统版本',
  `content` varchar(200) DEFAULT NULL COMMENT '反馈内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_status` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP用户反馈信息表';

-- ----------------------------
-- Records of app_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify` char(20) DEFAULT NULL COMMENT '版本类型 IOS,ANDROID',
  `version` char(10) DEFAULT NULL COMMENT '版本号:1.2.8',
  `force_update` bit(1) DEFAULT b'0' COMMENT '是否强制更新 0:否 1:是',
  `url` varchar(500) DEFAULT NULL COMMENT '下载地址,android为实际下载地址,ios是跳转到app_store',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注信息:版本更新的东西或解决的问题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP版本管理表';

-- ----------------------------
-- Records of app_version
-- ----------------------------

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图类型:由system_dict的banner_classify维护(不同模块的轮播均在该表中维护)',
  `client_type` tinyint(1) unsigned DEFAULT '0' COMMENT '客户端类型 0:PC 1:APP',
  `img_url` varchar(200) NOT NULL COMMENT '轮播图片地址',
  `turn_url` varchar(200) DEFAULT NULL COMMENT '轮播图点击后跳转的URL',
  `sort` tinyint(2) unsigned DEFAULT NULL COMMENT '轮播图顺序(大<->小) 最大的在最前面',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始展示时间(可在指定时间后开始展示)',
  `end_time` datetime DEFAULT NULL COMMENT '取消展示的时间(只在某个时间段展示)',
  `click` bit(1) DEFAULT b'1' COMMENT '是否可点击 0:否 1:可以',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type_client_type_index` (`classify`,`client_type`) USING BTREE COMMENT '组合索引',
  KEY `type_index` (`classify`) USING BTREE,
  KEY `client_type_index` (`classify`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='轮播图维护表';

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for help_instruction
-- ----------------------------
DROP TABLE IF EXISTS `help_instruction`;
CREATE TABLE `help_instruction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify` tinyint(2) DEFAULT NULL COMMENT '帮助分类取system_dict表中help_classify字段',
  `state` tinyint(1) DEFAULT '1' COMMENT '状态 0:不显示 1:显示',
  `ask` char(50) DEFAULT NULL COMMENT '问',
  `answer` varchar(500) DEFAULT NULL COMMENT '答 支持',
  `sort` tinyint(4) DEFAULT '0' COMMENT '排序(小<->大)',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:不删除(正常) 1:已删除',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助说明信息表';

-- ----------------------------
-- Records of help_instruction
-- ----------------------------

-- ----------------------------
-- Table structure for image_log
-- ----------------------------
DROP TABLE IF EXISTS `image_log`;
CREATE TABLE `image_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(50) DEFAULT NULL COMMENT '图片名称',
  `classify` tinyint(3) unsigned DEFAULT NULL COMMENT '图片分类 数据字典image_classify',
  `url` varchar(200) DEFAULT NULL COMMENT '文件存放地址',
  `size` bigint(15) unsigned DEFAULT NULL COMMENT '文件大小',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='图片上传记录';

-- ----------------------------
-- Records of image_log
-- ----------------------------

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS `message_template`;
CREATE TABLE `message_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(50) DEFAULT NULL COMMENT '消息名称',
  `nid` char(50) DEFAULT NULL COMMENT '消息nid',
  `state` bit(1) DEFAULT b'1' COMMENT '状态 0:关闭 1:开启',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '消息类型',
  `content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `tag` char(50) DEFAULT NULL COMMENT '后置处理标示符(消息推送跳转页面)',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板表';

-- ----------------------------
-- Records of message_template
-- ----------------------------

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sms_type` char(20) DEFAULT NULL COMMENT '短信分类',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `content` varchar(100) DEFAULT NULL COMMENT '短信内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `state` tinyint(1) unsigned DEFAULT NULL COMMENT '发送状态 0:发送中 1:已发送 2:发送失败',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信日志记录表';

-- ----------------------------
-- Records of sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_address
-- ----------------------------
DROP TABLE IF EXISTS `system_address`;
CREATE TABLE `system_address` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `nid` char(12) DEFAULT NULL COMMENT '区域代码',
  `pid` char(12) DEFAULT '0' COMMENT '父级区域代码',
  `zip_code` char(12) DEFAULT NULL COMMENT '邮编',
  `mark` char(1) DEFAULT NULL COMMENT '标示符-首字母',
  `classify` tinyint(1) unsigned DEFAULT NULL COMMENT '分类 省份1级 市2级 县3级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3574 DEFAULT CHARSET=utf8mb4 COMMENT='省市县代码表';

-- ----------------------------
-- Records of system_address
-- ----------------------------
INSERT INTO `system_address` VALUES ('1', '北京', '110000', '0', null, 'B', '1');
INSERT INTO `system_address` VALUES ('2', '市辖区', '110100', '110000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3', '东城区', '110101', '110100', '100000', 'D', '3');
INSERT INTO `system_address` VALUES ('4', '西城区', '110102', '110100', '100000', 'X', '3');
INSERT INTO `system_address` VALUES ('5', '崇文区', '110103', '110100', '100000', 'C', '3');
INSERT INTO `system_address` VALUES ('6', '宣武区', '110104', '110100', '100000', 'X', '3');
INSERT INTO `system_address` VALUES ('7', '朝阳区', '110105', '110100', '100000', 'C', '3');
INSERT INTO `system_address` VALUES ('8', '丰台区', '110106', '110100', '100000', 'F', '3');
INSERT INTO `system_address` VALUES ('9', '石景山区', '110107', '110100', '100000', 'S', '3');
INSERT INTO `system_address` VALUES ('10', '海淀区', '110108', '110100', '100000', 'H', '3');
INSERT INTO `system_address` VALUES ('11', '门头沟区', '110109', '110100', '102300', 'M', '3');
INSERT INTO `system_address` VALUES ('12', '房山区', '110111', '110100', '102400', 'F', '3');
INSERT INTO `system_address` VALUES ('13', '通州区', '110112', '110100', '101100', 'T', '3');
INSERT INTO `system_address` VALUES ('14', '顺义区', '110113', '110100', '101300', 'S', '3');
INSERT INTO `system_address` VALUES ('15', '昌平区', '110114', '110100', '102200', 'C', '3');
INSERT INTO `system_address` VALUES ('16', '大兴区', '110115', '110100', '102600', 'D', '3');
INSERT INTO `system_address` VALUES ('17', '怀柔区', '110116', '110100', '101400', 'H', '3');
INSERT INTO `system_address` VALUES ('18', '平谷区', '110117', '110100', '101200', 'P', '3');
INSERT INTO `system_address` VALUES ('19', '密云县', '110228', '110100', '101500', 'M', '3');
INSERT INTO `system_address` VALUES ('20', '延庆县', '110229', '110100', '102100', 'Y', '3');
INSERT INTO `system_address` VALUES ('21', '天津', '120000', '0', null, 'T', '1');
INSERT INTO `system_address` VALUES ('22', '市辖区', '120100', '120000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('23', '和平区', '120101', '120100', '300000', 'H', '3');
INSERT INTO `system_address` VALUES ('24', '河东区', '120102', '120100', '300000', 'H', '3');
INSERT INTO `system_address` VALUES ('25', '河西区', '120103', '120100', '300000', 'H', '3');
INSERT INTO `system_address` VALUES ('26', '南开区', '120104', '120100', '300000', 'N', '3');
INSERT INTO `system_address` VALUES ('27', '河北区', '120105', '120100', '300000', 'H', '3');
INSERT INTO `system_address` VALUES ('28', '红桥区', '120106', '120100', '300000', 'H', '3');
INSERT INTO `system_address` VALUES ('29', '塘沽区', '120107', '120100', '300450', 'T', '3');
INSERT INTO `system_address` VALUES ('30', '汉沽区', '120108', '120100', '300480', 'H', '3');
INSERT INTO `system_address` VALUES ('31', '大港区', '120109', '120100', '300000', 'D', '3');
INSERT INTO `system_address` VALUES ('32', '东丽区', '120110', '120100', '300000', 'D', '3');
INSERT INTO `system_address` VALUES ('33', '西青区', '120111', '120100', '300000', 'X', '3');
INSERT INTO `system_address` VALUES ('34', '津南区', '120112', '120100', '300000', 'J', '3');
INSERT INTO `system_address` VALUES ('35', '北辰区', '120113', '120100', '300000', 'B', '3');
INSERT INTO `system_address` VALUES ('36', '武清区', '120114', '120100', '301700', 'W', '3');
INSERT INTO `system_address` VALUES ('37', '宝坻区', '120115', '120100', '301800', 'B', '3');
INSERT INTO `system_address` VALUES ('38', '滨海新区', '120116', '120100', '300000', 'B', '3');
INSERT INTO `system_address` VALUES ('39', '宁河县', '120221', '120100', '301500', 'N', '3');
INSERT INTO `system_address` VALUES ('40', '静海县', '120223', '120100', '301600', 'J', '3');
INSERT INTO `system_address` VALUES ('41', '蓟县', '120225', '120100', '301900', 'J', '3');
INSERT INTO `system_address` VALUES ('42', '河北', '130000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('43', '石家庄市', '130100', '130000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('44', '长安区', '130102', '130100', '050000', 'Z', '3');
INSERT INTO `system_address` VALUES ('45', '桥东区', '130103', '130100', '050000', 'Q', '3');
INSERT INTO `system_address` VALUES ('46', '桥西区', '130104', '130100', '050000', 'Q', '3');
INSERT INTO `system_address` VALUES ('47', '新华区', '130105', '130100', '050000', 'X', '3');
INSERT INTO `system_address` VALUES ('48', '井陉矿区', '130107', '130100', '050100', 'J', '3');
INSERT INTO `system_address` VALUES ('49', '裕华区', '130108', '130100', '050000', 'Y', '3');
INSERT INTO `system_address` VALUES ('50', '井陉县', '130121', '130100', '050300', 'J', '3');
INSERT INTO `system_address` VALUES ('51', '正定县', '130123', '130100', '050800', 'Z', '3');
INSERT INTO `system_address` VALUES ('52', '栾城区', '130124', '130100', '051430', 'L', '3');
INSERT INTO `system_address` VALUES ('53', '行唐县', '130125', '130100', '050600', 'X', '3');
INSERT INTO `system_address` VALUES ('54', '灵寿县', '130126', '130100', '050500', 'L', '3');
INSERT INTO `system_address` VALUES ('55', '高邑县', '130127', '130100', '051330', 'G', '3');
INSERT INTO `system_address` VALUES ('56', '深泽县', '130128', '130100', '052500', 'S', '3');
INSERT INTO `system_address` VALUES ('57', '赞皇县', '130129', '130100', '051230', 'Z', '3');
INSERT INTO `system_address` VALUES ('58', '无极县', '130130', '130100', '052460', 'W', '3');
INSERT INTO `system_address` VALUES ('59', '平山县', '130131', '130100', '050400', 'P', '3');
INSERT INTO `system_address` VALUES ('60', '元氏县', '130132', '130100', '051130', 'Y', '3');
INSERT INTO `system_address` VALUES ('61', '赵县', '130133', '130100', '051530', 'Z', '3');
INSERT INTO `system_address` VALUES ('62', '辛集市', '130181', '130100', '052360', 'X', '3');
INSERT INTO `system_address` VALUES ('63', '藁城区', '130182', '130100', '052160', 'G', '3');
INSERT INTO `system_address` VALUES ('64', '晋州市', '130183', '130100', '052200', 'J', '3');
INSERT INTO `system_address` VALUES ('65', '新乐市', '130184', '130100', '050700', 'X', '3');
INSERT INTO `system_address` VALUES ('66', '鹿泉区', '130185', '130100', '050200', 'L', '3');
INSERT INTO `system_address` VALUES ('67', '唐山市', '130200', '130000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('68', '路南区', '130202', '130200', '063000', 'L', '3');
INSERT INTO `system_address` VALUES ('69', '路北区', '130203', '130200', '063000', 'L', '3');
INSERT INTO `system_address` VALUES ('70', '古冶区', '130204', '130200', '063100', 'G', '3');
INSERT INTO `system_address` VALUES ('71', '开平区', '130205', '130200', '063000', 'K', '3');
INSERT INTO `system_address` VALUES ('72', '丰南区', '130207', '130200', '063300', 'F', '3');
INSERT INTO `system_address` VALUES ('73', '丰润区', '130208', '130200', '063000', 'F', '3');
INSERT INTO `system_address` VALUES ('74', '滦县', '130223', '130200', '063700', 'L', '3');
INSERT INTO `system_address` VALUES ('75', '滦南县', '130224', '130200', '063500', 'L', '3');
INSERT INTO `system_address` VALUES ('76', '乐亭县', '130225', '130200', '063600', 'L', '3');
INSERT INTO `system_address` VALUES ('77', '迁西县', '130227', '130200', '064300', 'Q', '3');
INSERT INTO `system_address` VALUES ('78', '玉田县', '130229', '130200', '064100', 'Y', '3');
INSERT INTO `system_address` VALUES ('79', '曹妃甸区', '130230', '130200', '063000', 'C', '3');
INSERT INTO `system_address` VALUES ('80', '遵化市', '130281', '130200', '064200', 'Z', '3');
INSERT INTO `system_address` VALUES ('81', '迁安市', '130283', '130200', '064400', 'Q', '3');
INSERT INTO `system_address` VALUES ('82', '唐海县', '130299', '130200', '063200', 'T', '3');
INSERT INTO `system_address` VALUES ('83', '秦皇岛市', '130300', '130000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('84', '海港区', '130302', '130300', '066000', 'H', '3');
INSERT INTO `system_address` VALUES ('85', '山海关区', '130303', '130300', '066200', 'S', '3');
INSERT INTO `system_address` VALUES ('86', '北戴河区', '130304', '130300', '066100', 'B', '3');
INSERT INTO `system_address` VALUES ('87', '青龙满族自治县', '130321', '130300', '066500', 'Q', '3');
INSERT INTO `system_address` VALUES ('88', '昌黎县', '130322', '130300', '066600', 'C', '3');
INSERT INTO `system_address` VALUES ('89', '抚宁县', '130323', '130300', '066300', 'F', '3');
INSERT INTO `system_address` VALUES ('90', '卢龙县', '130324', '130300', '066400', 'L', '3');
INSERT INTO `system_address` VALUES ('91', '经济技术开发区', '130399', '130300', '066000', 'J', '3');
INSERT INTO `system_address` VALUES ('92', '邯郸市', '130400', '130000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('93', '邯山区', '130402', '130400', '056000', 'H', '3');
INSERT INTO `system_address` VALUES ('94', '丛台区', '130403', '130400', '056000', 'C', '3');
INSERT INTO `system_address` VALUES ('95', '复兴区', '130404', '130400', '056000', 'F', '3');
INSERT INTO `system_address` VALUES ('96', '峰峰矿区', '130406', '130400', '056200', 'F', '3');
INSERT INTO `system_address` VALUES ('97', '邯郸县', '130421', '130400', '056000', 'H', '3');
INSERT INTO `system_address` VALUES ('98', '临漳县', '130423', '130400', '056600', 'L', '3');
INSERT INTO `system_address` VALUES ('99', '成安县', '130424', '130400', '056700', 'C', '3');
INSERT INTO `system_address` VALUES ('100', '大名县', '130425', '130400', '056900', 'D', '3');
INSERT INTO `system_address` VALUES ('101', '涉县', '130426', '130400', '056400', 'S', '3');
INSERT INTO `system_address` VALUES ('102', '磁县', '130427', '130400', '056500', 'C', '3');
INSERT INTO `system_address` VALUES ('103', '肥乡县', '130428', '130400', '057550', 'F', '3');
INSERT INTO `system_address` VALUES ('104', '永年县', '130429', '130400', '057150', 'Y', '3');
INSERT INTO `system_address` VALUES ('105', '邱县', '130430', '130400', '057450', 'Q', '3');
INSERT INTO `system_address` VALUES ('106', '鸡泽县', '130431', '130400', '057350', 'J', '3');
INSERT INTO `system_address` VALUES ('107', '广平县', '130432', '130400', '057650', 'G', '3');
INSERT INTO `system_address` VALUES ('108', '馆陶县', '130433', '130400', '057750', 'G', '3');
INSERT INTO `system_address` VALUES ('109', '魏县', '130434', '130400', '056800', 'W', '3');
INSERT INTO `system_address` VALUES ('110', '曲周县', '130435', '130400', '057250', 'Q', '3');
INSERT INTO `system_address` VALUES ('111', '武安市', '130481', '130400', '056300', 'W', '3');
INSERT INTO `system_address` VALUES ('112', '邢台市', '130500', '130000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('113', '桥东区', '130502', '130500', '054000', 'Q', '3');
INSERT INTO `system_address` VALUES ('114', '桥西区', '130503', '130500', '054000', 'Q', '3');
INSERT INTO `system_address` VALUES ('115', '邢台县', '130521', '130500', '054000', 'X', '3');
INSERT INTO `system_address` VALUES ('116', '临城县', '130522', '130500', '054300', 'L', '3');
INSERT INTO `system_address` VALUES ('117', '内丘县', '130523', '130500', '054200', 'N', '3');
INSERT INTO `system_address` VALUES ('118', '柏乡县', '130524', '130500', '055450', 'B', '3');
INSERT INTO `system_address` VALUES ('119', '隆尧县', '130525', '130500', '055350', 'L', '3');
INSERT INTO `system_address` VALUES ('120', '任县', '130526', '130500', '055150', 'R', '3');
INSERT INTO `system_address` VALUES ('121', '南和县', '130527', '130500', '054000', 'N', '3');
INSERT INTO `system_address` VALUES ('122', '宁晋县', '130528', '130500', '055550', 'N', '3');
INSERT INTO `system_address` VALUES ('123', '巨鹿县', '130529', '130500', '055250', 'J', '3');
INSERT INTO `system_address` VALUES ('124', '新河县', '130530', '130500', '051730', 'X', '3');
INSERT INTO `system_address` VALUES ('125', '广宗县', '130531', '130500', '054600', 'G', '3');
INSERT INTO `system_address` VALUES ('126', '平乡县', '130532', '130500', '054500', 'P', '3');
INSERT INTO `system_address` VALUES ('127', '威县', '130533', '130500', '054700', 'W', '3');
INSERT INTO `system_address` VALUES ('128', '清河县', '130534', '130500', '054800', 'Q', '3');
INSERT INTO `system_address` VALUES ('129', '临西县', '130535', '130500', '054900', 'L', '3');
INSERT INTO `system_address` VALUES ('130', '南宫市', '130581', '130500', '051800', 'N', '3');
INSERT INTO `system_address` VALUES ('131', '沙河市', '130582', '130500', '054100', 'S', '3');
INSERT INTO `system_address` VALUES ('132', '保定市', '130600', '130000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('133', '新市区', '130602', '130600', '071000', 'X', '3');
INSERT INTO `system_address` VALUES ('134', '北市区', '130603', '130600', '071000', 'B', '3');
INSERT INTO `system_address` VALUES ('135', '南市区', '130604', '130600', '071000', 'N', '3');
INSERT INTO `system_address` VALUES ('136', '满城区', '130621', '130600', '072150', 'M', '3');
INSERT INTO `system_address` VALUES ('137', '清苑区', '130622', '130600', '071100', 'Q', '3');
INSERT INTO `system_address` VALUES ('138', '涞水县', '130623', '130600', '074100', 'L', '3');
INSERT INTO `system_address` VALUES ('139', '阜平县', '130624', '130600', '073200', 'F', '3');
INSERT INTO `system_address` VALUES ('140', '徐水区', '130625', '130600', '072550', 'X', '3');
INSERT INTO `system_address` VALUES ('141', '定兴县', '130626', '130600', '072650', 'D', '3');
INSERT INTO `system_address` VALUES ('142', '唐县', '130627', '130600', '072350', 'T', '3');
INSERT INTO `system_address` VALUES ('143', '高阳县', '130628', '130600', '071500', 'G', '3');
INSERT INTO `system_address` VALUES ('144', '容城县', '130629', '130600', '071700', 'R', '3');
INSERT INTO `system_address` VALUES ('145', '涞源县', '130630', '130600', '074300', 'L', '3');
INSERT INTO `system_address` VALUES ('146', '望都县', '130631', '130600', '072450', 'W', '3');
INSERT INTO `system_address` VALUES ('147', '安新县', '130632', '130600', '071600', 'A', '3');
INSERT INTO `system_address` VALUES ('148', '易县', '130633', '130600', '074200', 'Y', '3');
INSERT INTO `system_address` VALUES ('149', '曲阳县', '130634', '130600', '073100', 'Q', '3');
INSERT INTO `system_address` VALUES ('150', '蠡县', '130635', '130600', '071400', 'L', '3');
INSERT INTO `system_address` VALUES ('151', '顺平县', '130636', '130600', '072250', 'S', '3');
INSERT INTO `system_address` VALUES ('152', '博野县', '130637', '130600', '071300', 'B', '3');
INSERT INTO `system_address` VALUES ('153', '雄县', '130638', '130600', '071800', 'X', '3');
INSERT INTO `system_address` VALUES ('154', '涿州市', '130681', '130600', '072750', 'Z', '3');
INSERT INTO `system_address` VALUES ('155', '定州市', '130682', '130600', '073000', 'D', '3');
INSERT INTO `system_address` VALUES ('156', '安国市', '130683', '130600', '071200', 'A', '3');
INSERT INTO `system_address` VALUES ('157', '高碑店市', '130684', '130600', '074000', 'G', '3');
INSERT INTO `system_address` VALUES ('158', '高开区', '130698', '130600', '071700', 'G', '3');
INSERT INTO `system_address` VALUES ('159', '张家口市', '130700', '130000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('160', '桥东区', '130702', '130700', '075000', 'Q', '3');
INSERT INTO `system_address` VALUES ('161', '桥西区', '130703', '130700', '075000', 'Q', '3');
INSERT INTO `system_address` VALUES ('162', '宣化区', '130705', '130700', '075000', 'X', '3');
INSERT INTO `system_address` VALUES ('163', '下花园区', '130706', '130700', '075399', 'X', '3');
INSERT INTO `system_address` VALUES ('164', '宣化县', '130721', '130700', '075100', 'X', '3');
INSERT INTO `system_address` VALUES ('165', '张北县', '130722', '130700', '076450', 'Z', '3');
INSERT INTO `system_address` VALUES ('166', '康保县', '130723', '130700', '076650', 'K', '3');
INSERT INTO `system_address` VALUES ('167', '沽源县', '130724', '130700', '076550', 'G', '3');
INSERT INTO `system_address` VALUES ('168', '尚义县', '130725', '130700', '076750', 'S', '3');
INSERT INTO `system_address` VALUES ('169', '蔚县', '130726', '130700', '075700', 'Y', '3');
INSERT INTO `system_address` VALUES ('170', '阳原县', '130727', '130700', '075800', 'Y', '3');
INSERT INTO `system_address` VALUES ('171', '怀安县', '130728', '130700', '076150', 'H', '3');
INSERT INTO `system_address` VALUES ('172', '万全县', '130729', '130700', '076250', 'W', '3');
INSERT INTO `system_address` VALUES ('173', '怀来县', '130730', '130700', '075400', 'H', '3');
INSERT INTO `system_address` VALUES ('174', '涿鹿县', '130731', '130700', '075600', 'Z', '3');
INSERT INTO `system_address` VALUES ('175', '赤城县', '130732', '130700', '075500', 'C', '3');
INSERT INTO `system_address` VALUES ('176', '崇礼县', '130733', '130700', '076350', 'C', '3');
INSERT INTO `system_address` VALUES ('177', '承德市', '130800', '130000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('178', '双桥区', '130802', '130800', '067000', 'S', '3');
INSERT INTO `system_address` VALUES ('179', '双滦区', '130803', '130800', '067000', 'S', '3');
INSERT INTO `system_address` VALUES ('180', '鹰手营子矿区', '130804', '130800', '067200', 'Y', '3');
INSERT INTO `system_address` VALUES ('181', '承德县', '130821', '130800', '067400', 'C', '3');
INSERT INTO `system_address` VALUES ('182', '兴隆县', '130822', '130800', '067300', 'X', '3');
INSERT INTO `system_address` VALUES ('183', '平泉县', '130823', '130800', '067500', 'P', '3');
INSERT INTO `system_address` VALUES ('184', '滦平县', '130824', '130800', '068250', 'L', '3');
INSERT INTO `system_address` VALUES ('185', '隆化县', '130825', '130800', '068150', 'L', '3');
INSERT INTO `system_address` VALUES ('186', '丰宁满族自治县', '130826', '130800', '068350', 'F', '3');
INSERT INTO `system_address` VALUES ('187', '宽城满族自治县', '130827', '130800', '067600', 'K', '3');
INSERT INTO `system_address` VALUES ('188', '围场满族蒙古族自治县', '130828', '130800', '068450', 'W', '3');
INSERT INTO `system_address` VALUES ('189', '沧州市', '130900', '130000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('190', '新华区', '130902', '130900', '061000', 'X', '3');
INSERT INTO `system_address` VALUES ('191', '运河区', '130903', '130900', '061000', 'Y', '3');
INSERT INTO `system_address` VALUES ('192', '沧县', '130921', '130900', '061000', 'C', '3');
INSERT INTO `system_address` VALUES ('193', '青县', '130922', '130900', '062650', 'Q', '3');
INSERT INTO `system_address` VALUES ('194', '东光县', '130923', '130900', '061600', 'D', '3');
INSERT INTO `system_address` VALUES ('195', '海兴县', '130924', '130900', '061200', 'H', '3');
INSERT INTO `system_address` VALUES ('196', '盐山县', '130925', '130900', '061300', 'Y', '3');
INSERT INTO `system_address` VALUES ('197', '肃宁县', '130926', '130900', '062350', 'S', '3');
INSERT INTO `system_address` VALUES ('198', '南皮县', '130927', '130900', '061500', 'N', '3');
INSERT INTO `system_address` VALUES ('199', '吴桥县', '130928', '130900', '061800', 'W', '3');
INSERT INTO `system_address` VALUES ('200', '献县', '130929', '130900', '062250', 'X', '3');
INSERT INTO `system_address` VALUES ('201', '孟村回族自治县', '130930', '130900', '061400', 'M', '3');
INSERT INTO `system_address` VALUES ('202', '泊头市', '130981', '130900', '062150', 'B', '3');
INSERT INTO `system_address` VALUES ('203', '任丘市', '130982', '130900', '062550', 'R', '3');
INSERT INTO `system_address` VALUES ('204', '黄骅市', '130983', '130900', '061100', 'H', '3');
INSERT INTO `system_address` VALUES ('205', '河间市', '130984', '130900', '062450', 'H', '3');
INSERT INTO `system_address` VALUES ('206', '廊坊市', '131000', '130000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('207', '安次区', '131002', '131000', '065000', 'A', '3');
INSERT INTO `system_address` VALUES ('208', '广阳区', '131003', '131000', '065000', 'G', '3');
INSERT INTO `system_address` VALUES ('209', '固安县', '131022', '131000', '065500', 'G', '3');
INSERT INTO `system_address` VALUES ('210', '永清县', '131023', '131000', '065600', 'Y', '3');
INSERT INTO `system_address` VALUES ('211', '香河县', '131024', '131000', '065400', 'X', '3');
INSERT INTO `system_address` VALUES ('212', '大城县', '131025', '131000', '065900', 'D', '3');
INSERT INTO `system_address` VALUES ('213', '文安县', '131026', '131000', '065800', 'W', '3');
INSERT INTO `system_address` VALUES ('214', '大厂回族自治县', '131028', '131000', '065300', 'D', '3');
INSERT INTO `system_address` VALUES ('215', '开发区', '131051', '131000', '065000', 'K', '3');
INSERT INTO `system_address` VALUES ('216', '燕郊经济技术开发区', '131052', '131000', '065000', 'Y', '3');
INSERT INTO `system_address` VALUES ('217', '霸州市', '131081', '131000', '065700', 'B', '3');
INSERT INTO `system_address` VALUES ('218', '三河市', '131082', '131000', '065201', 'S', '3');
INSERT INTO `system_address` VALUES ('219', '廊坊经济技术开发区', '131099', '131000', '065000', 'L', '3');
INSERT INTO `system_address` VALUES ('220', '衡水市', '131100', '130000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('221', '桃城区', '131102', '131100', '053000', 'T', '3');
INSERT INTO `system_address` VALUES ('222', '枣强县', '131121', '131100', '053100', 'Z', '3');
INSERT INTO `system_address` VALUES ('223', '武邑县', '131122', '131100', '053400', 'W', '3');
INSERT INTO `system_address` VALUES ('224', '武强县', '131123', '131100', '053300', 'W', '3');
INSERT INTO `system_address` VALUES ('225', '饶阳县', '131124', '131100', '053900', 'R', '3');
INSERT INTO `system_address` VALUES ('226', '安平县', '131125', '131100', '053600', 'A', '3');
INSERT INTO `system_address` VALUES ('227', '故城县', '131126', '131100', '253800', 'G', '3');
INSERT INTO `system_address` VALUES ('228', '景县', '131127', '131100', '053500', 'J', '3');
INSERT INTO `system_address` VALUES ('229', '阜城县', '131128', '131100', '053700', 'F', '3');
INSERT INTO `system_address` VALUES ('230', '冀州市', '131181', '131100', '053200', 'J', '3');
INSERT INTO `system_address` VALUES ('231', '深州市', '131182', '131100', '053800', 'S', '3');
INSERT INTO `system_address` VALUES ('232', '山西', '140000', '0', null, 'S', '1');
INSERT INTO `system_address` VALUES ('233', '太原市', '140100', '140000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('234', '小店区', '140105', '140100', '030000', 'X', '3');
INSERT INTO `system_address` VALUES ('235', '迎泽区', '140106', '140100', '030000', 'Y', '3');
INSERT INTO `system_address` VALUES ('236', '杏花岭区', '140107', '140100', '030000', 'X', '3');
INSERT INTO `system_address` VALUES ('237', '尖草坪区', '140108', '140100', '030000', 'J', '3');
INSERT INTO `system_address` VALUES ('238', '万柏林区', '140109', '140100', '030000', 'W', '3');
INSERT INTO `system_address` VALUES ('239', '晋源区', '140110', '140100', '030000', 'J', '3');
INSERT INTO `system_address` VALUES ('240', '清徐县', '140121', '140100', '030400', 'Q', '3');
INSERT INTO `system_address` VALUES ('241', '阳曲县', '140122', '140100', '030100', 'Y', '3');
INSERT INTO `system_address` VALUES ('242', '娄烦县', '140123', '140100', '030300', 'L', '3');
INSERT INTO `system_address` VALUES ('243', '古交市', '140181', '140100', '030200', 'G', '3');
INSERT INTO `system_address` VALUES ('244', '大同市', '140200', '140000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('245', '城区', '140202', '140200', '037000', 'C', '3');
INSERT INTO `system_address` VALUES ('246', '矿区', '140203', '140200', '037000', 'K', '3');
INSERT INTO `system_address` VALUES ('247', '南郊区', '140211', '140200', '037000', 'N', '3');
INSERT INTO `system_address` VALUES ('248', '新荣区', '140212', '140200', '037000', 'X', '3');
INSERT INTO `system_address` VALUES ('249', '阳高县', '140221', '140200', '038100', 'Y', '3');
INSERT INTO `system_address` VALUES ('250', '天镇县', '140222', '140200', '038200', 'T', '3');
INSERT INTO `system_address` VALUES ('251', '广灵县', '140223', '140200', '037500', 'G', '3');
INSERT INTO `system_address` VALUES ('252', '灵丘县', '140224', '140200', '034400', 'L', '3');
INSERT INTO `system_address` VALUES ('253', '浑源县', '140225', '140200', '037400', 'H', '3');
INSERT INTO `system_address` VALUES ('254', '左云县', '140226', '140200', '037100', 'Z', '3');
INSERT INTO `system_address` VALUES ('255', '大同县', '140227', '140200', '037300', 'D', '3');
INSERT INTO `system_address` VALUES ('256', '阳泉市', '140300', '140000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('257', '城区', '140302', '140300', '045000', 'C', '3');
INSERT INTO `system_address` VALUES ('258', '矿区', '140303', '140300', '045000', 'K', '3');
INSERT INTO `system_address` VALUES ('259', '郊区', '140311', '140300', '045000', 'J', '3');
INSERT INTO `system_address` VALUES ('260', '平定县', '140321', '140300', '045200', 'P', '3');
INSERT INTO `system_address` VALUES ('261', '盂县', '140322', '140300', '045100', 'Y', '3');
INSERT INTO `system_address` VALUES ('262', '长治市', '140400', '140000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('263', '长治县', '140421', '140400', '047100', 'Z', '3');
INSERT INTO `system_address` VALUES ('264', '襄垣县', '140423', '140400', '046200', 'X', '3');
INSERT INTO `system_address` VALUES ('265', '屯留县', '140424', '140400', '046100', 'T', '3');
INSERT INTO `system_address` VALUES ('266', '平顺县', '140425', '140400', '047400', 'P', '3');
INSERT INTO `system_address` VALUES ('267', '黎城县', '140426', '140400', '047600', 'L', '3');
INSERT INTO `system_address` VALUES ('268', '壶关县', '140427', '140400', '047300', 'H', '3');
INSERT INTO `system_address` VALUES ('269', '长子县', '140428', '140400', '046600', 'Z', '3');
INSERT INTO `system_address` VALUES ('270', '武乡县', '140429', '140400', '046400', 'W', '3');
INSERT INTO `system_address` VALUES ('271', '沁县', '140430', '140400', '046400', 'Q', '3');
INSERT INTO `system_address` VALUES ('272', '沁源县', '140431', '140400', '046500', 'Q', '3');
INSERT INTO `system_address` VALUES ('273', '潞城市', '140481', '140400', '047500', 'L', '3');
INSERT INTO `system_address` VALUES ('274', '城区', '140482', '140400', '046400', 'C', '3');
INSERT INTO `system_address` VALUES ('275', '郊区', '140483', '140400', '046400', 'J', '3');
INSERT INTO `system_address` VALUES ('276', '高新区', '140484', '140400', '046400', 'G', '3');
INSERT INTO `system_address` VALUES ('277', '晋城市', '140500', '140000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('278', '城区', '140502', '140500', '048000', 'C', '3');
INSERT INTO `system_address` VALUES ('279', '沁水县', '140521', '140500', '048200', 'Q', '3');
INSERT INTO `system_address` VALUES ('280', '阳城县', '140522', '140500', '048100', 'Y', '3');
INSERT INTO `system_address` VALUES ('281', '陵川县', '140524', '140500', '048300', 'L', '3');
INSERT INTO `system_address` VALUES ('282', '泽州县', '140525', '140500', '048000', 'Z', '3');
INSERT INTO `system_address` VALUES ('283', '高平市', '140581', '140500', '048400', 'G', '3');
INSERT INTO `system_address` VALUES ('284', '朔州市', '140600', '140000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('285', '朔城区', '140602', '140600', '036002', 'S', '3');
INSERT INTO `system_address` VALUES ('286', '平鲁区', '140603', '140600', '036800', 'P', '3');
INSERT INTO `system_address` VALUES ('287', '山阴县', '140621', '140600', '036000', 'S', '3');
INSERT INTO `system_address` VALUES ('288', '应县', '140622', '140600', '037600', 'Y', '3');
INSERT INTO `system_address` VALUES ('289', '右玉县', '140623', '140600', '037200', 'Y', '3');
INSERT INTO `system_address` VALUES ('290', '怀仁县', '140624', '140600', '038300', 'H', '3');
INSERT INTO `system_address` VALUES ('291', '晋中市', '140700', '140000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('292', '榆次区', '140702', '140700', '030600', 'Y', '3');
INSERT INTO `system_address` VALUES ('293', '榆社县', '140721', '140700', '031800', 'Y', '3');
INSERT INTO `system_address` VALUES ('294', '左权县', '140722', '140700', '032600', 'Z', '3');
INSERT INTO `system_address` VALUES ('295', '和顺县', '140723', '140700', '032700', 'H', '3');
INSERT INTO `system_address` VALUES ('296', '昔阳县', '140724', '140700', '045300', 'X', '3');
INSERT INTO `system_address` VALUES ('297', '寿阳县', '140725', '140700', '045400', 'S', '3');
INSERT INTO `system_address` VALUES ('298', '太谷县', '140726', '140700', '030800', 'T', '3');
INSERT INTO `system_address` VALUES ('299', '祁县', '140727', '140700', '030900', 'Q', '3');
INSERT INTO `system_address` VALUES ('300', '平遥县', '140728', '140700', '031100', 'P', '3');
INSERT INTO `system_address` VALUES ('301', '灵石县', '140729', '140700', '031300', 'L', '3');
INSERT INTO `system_address` VALUES ('302', '介休市', '140781', '140700', '032000', 'J', '3');
INSERT INTO `system_address` VALUES ('303', '运城市', '140800', '140000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('304', '盐湖区', '140802', '140800', '044000', 'Y', '3');
INSERT INTO `system_address` VALUES ('305', '风陵渡经济开发区', '140803', '140800', '044000', 'F', '3');
INSERT INTO `system_address` VALUES ('306', '临猗县', '140821', '140800', '044100', 'L', '3');
INSERT INTO `system_address` VALUES ('307', '万荣县', '140822', '140800', '044200', 'W', '3');
INSERT INTO `system_address` VALUES ('308', '闻喜县', '140823', '140800', '043800', 'W', '3');
INSERT INTO `system_address` VALUES ('309', '稷山县', '140824', '140800', '043200', 'J', '3');
INSERT INTO `system_address` VALUES ('310', '新绛县', '140825', '140800', '043100', 'X', '3');
INSERT INTO `system_address` VALUES ('311', '绛县', '140826', '140800', '043600', 'J', '3');
INSERT INTO `system_address` VALUES ('312', '垣曲县', '140827', '140800', '043700', 'Y', '3');
INSERT INTO `system_address` VALUES ('313', '夏县', '140828', '140800', '044400', 'X', '3');
INSERT INTO `system_address` VALUES ('314', '平陆县', '140829', '140800', '044300', 'P', '3');
INSERT INTO `system_address` VALUES ('315', '芮城县', '140830', '140800', '044600', 'R', '3');
INSERT INTO `system_address` VALUES ('316', '永济市', '140881', '140800', '044500', 'Y', '3');
INSERT INTO `system_address` VALUES ('317', '河津市', '140882', '140800', '043300', 'H', '3');
INSERT INTO `system_address` VALUES ('318', '忻州市', '140900', '140000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('319', '忻府区', '140902', '140900', '034000', 'X', '3');
INSERT INTO `system_address` VALUES ('320', '定襄县', '140921', '140900', '035400', 'D', '3');
INSERT INTO `system_address` VALUES ('321', '五台县', '140922', '140900', '035500', 'W', '3');
INSERT INTO `system_address` VALUES ('322', '代县', '140923', '140900', '034200', 'D', '3');
INSERT INTO `system_address` VALUES ('323', '繁峙县', '140924', '140900', '034300', 'F', '3');
INSERT INTO `system_address` VALUES ('324', '宁武县', '140925', '140900', '036000', 'N', '3');
INSERT INTO `system_address` VALUES ('325', '静乐县', '140926', '140900', '035100', 'J', '3');
INSERT INTO `system_address` VALUES ('326', '神池县', '140927', '140900', '036100', 'S', '3');
INSERT INTO `system_address` VALUES ('327', '五寨县', '140928', '140900', '036200', 'W', '3');
INSERT INTO `system_address` VALUES ('328', '岢岚县', '140929', '140900', '036300', 'K', '3');
INSERT INTO `system_address` VALUES ('329', '河曲县', '140930', '140900', '036500', 'H', '3');
INSERT INTO `system_address` VALUES ('330', '保德县', '140931', '140900', '036600', 'B', '3');
INSERT INTO `system_address` VALUES ('331', '偏关县', '140932', '140900', '036400', 'P', '3');
INSERT INTO `system_address` VALUES ('332', '原平市', '140981', '140900', '034100', 'Y', '3');
INSERT INTO `system_address` VALUES ('333', '临汾市', '141000', '140000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('334', '尧都区', '141002', '141000', '041000', 'Y', '3');
INSERT INTO `system_address` VALUES ('335', '曲沃县', '141021', '141000', '043400', 'Q', '3');
INSERT INTO `system_address` VALUES ('336', '翼城县', '141022', '141000', '043500', 'Y', '3');
INSERT INTO `system_address` VALUES ('337', '襄汾县', '141023', '141000', '041500', 'X', '3');
INSERT INTO `system_address` VALUES ('338', '洪洞县', '141024', '141000', '031600', 'H', '3');
INSERT INTO `system_address` VALUES ('339', '古县', '141025', '141000', '042400', 'G', '3');
INSERT INTO `system_address` VALUES ('340', '安泽县', '141026', '141000', '042500', 'A', '3');
INSERT INTO `system_address` VALUES ('341', '浮山县', '141027', '141000', '041000', 'F', '3');
INSERT INTO `system_address` VALUES ('342', '吉县', '141028', '141000', '042200', 'J', '3');
INSERT INTO `system_address` VALUES ('343', '乡宁县', '141029', '141000', '042100', 'X', '3');
INSERT INTO `system_address` VALUES ('344', '大宁县', '141030', '141000', '042300', 'D', '3');
INSERT INTO `system_address` VALUES ('345', '隰县', '141031', '141000', '041300', 'X', '3');
INSERT INTO `system_address` VALUES ('346', '永和县', '141032', '141000', '041400', 'Y', '3');
INSERT INTO `system_address` VALUES ('347', '蒲县', '141033', '141000', '041200', 'P', '3');
INSERT INTO `system_address` VALUES ('348', '汾西县', '141034', '141000', '031500', 'F', '3');
INSERT INTO `system_address` VALUES ('349', '侯马市', '141081', '141000', '043000', 'H', '3');
INSERT INTO `system_address` VALUES ('350', '霍州市', '141082', '141000', '031400', 'H', '3');
INSERT INTO `system_address` VALUES ('351', '吕梁市', '141100', '140000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('352', '离石区', '141102', '141100', '033000', 'L', '3');
INSERT INTO `system_address` VALUES ('353', '文水县', '141121', '141100', '032100', 'W', '3');
INSERT INTO `system_address` VALUES ('354', '交城县', '141122', '141100', '030500', 'J', '3');
INSERT INTO `system_address` VALUES ('355', '兴县', '141123', '141100', '033600', 'X', '3');
INSERT INTO `system_address` VALUES ('356', '临县', '141124', '141100', '033200', 'L', '3');
INSERT INTO `system_address` VALUES ('357', '柳林县', '141125', '141100', '033300', 'L', '3');
INSERT INTO `system_address` VALUES ('358', '石楼县', '141126', '141100', '032500', 'S', '3');
INSERT INTO `system_address` VALUES ('359', '岚县', '141127', '141100', '035200', 'L', '3');
INSERT INTO `system_address` VALUES ('360', '方山县', '141128', '141100', '033100', 'F', '3');
INSERT INTO `system_address` VALUES ('361', '中阳县', '141129', '141100', '033400', 'Z', '3');
INSERT INTO `system_address` VALUES ('362', '交口县', '141130', '141100', '032400', 'J', '3');
INSERT INTO `system_address` VALUES ('363', '孝义市', '141181', '141100', '032300', 'X', '3');
INSERT INTO `system_address` VALUES ('364', '汾阳市', '141182', '141100', '032200', 'F', '3');
INSERT INTO `system_address` VALUES ('365', '内蒙古', '150000', '0', null, 'N', '1');
INSERT INTO `system_address` VALUES ('366', '呼和浩特市', '150100', '150000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('367', '新城区', '150102', '150100', '010000', 'X', '3');
INSERT INTO `system_address` VALUES ('368', '回民区', '150103', '150100', '010000', 'H', '3');
INSERT INTO `system_address` VALUES ('369', '玉泉区', '150104', '150100', '010000', 'Y', '3');
INSERT INTO `system_address` VALUES ('370', '赛罕区', '150105', '150100', '010000', 'S', '3');
INSERT INTO `system_address` VALUES ('371', '土默特左旗', '150121', '150100', '010100', 'T', '3');
INSERT INTO `system_address` VALUES ('372', '托克托县', '150122', '150100', '010200', 'T', '3');
INSERT INTO `system_address` VALUES ('373', '和林格尔县', '150123', '150100', '011500', 'H', '3');
INSERT INTO `system_address` VALUES ('374', '清水河县', '150124', '150100', '011600', 'Q', '3');
INSERT INTO `system_address` VALUES ('375', '武川县', '150125', '150100', '011700', 'W', '3');
INSERT INTO `system_address` VALUES ('376', '包头市', '150200', '150000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('377', '东河区', '150202', '150200', '014000', 'D', '3');
INSERT INTO `system_address` VALUES ('378', '昆都仑区', '150203', '150200', '014010', 'K', '3');
INSERT INTO `system_address` VALUES ('379', '青山区', '150204', '150200', '014000', 'Q', '3');
INSERT INTO `system_address` VALUES ('380', '石拐区', '150205', '150200', '014070', 'S', '3');
INSERT INTO `system_address` VALUES ('381', '白云鄂博矿区', '150206', '150200', '014080', 'B', '3');
INSERT INTO `system_address` VALUES ('382', '九原区', '150207', '150200', '014060', 'J', '3');
INSERT INTO `system_address` VALUES ('383', '土默特右旗', '150221', '150200', '014100', 'T', '3');
INSERT INTO `system_address` VALUES ('384', '固阳县', '150222', '150200', '014200', 'G', '3');
INSERT INTO `system_address` VALUES ('385', '达尔罕茂明安联合旗', '150223', '150200', '014500', 'D', '3');
INSERT INTO `system_address` VALUES ('386', '乌海市', '150300', '150000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('387', '海勃湾区', '150302', '150300', '016000', 'H', '3');
INSERT INTO `system_address` VALUES ('388', '海南区', '150303', '150300', '016000', 'H', '3');
INSERT INTO `system_address` VALUES ('389', '乌达区', '150304', '150300', '016000', 'W', '3');
INSERT INTO `system_address` VALUES ('390', '赤峰市', '150400', '150000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('391', '红山区', '150402', '150400', '024000', 'H', '3');
INSERT INTO `system_address` VALUES ('392', '元宝山区', '150403', '150400', '024000', 'Y', '3');
INSERT INTO `system_address` VALUES ('393', '松山区', '150404', '150400', '024000', 'S', '3');
INSERT INTO `system_address` VALUES ('394', '阿鲁科尔沁旗', '150421', '150400', '025550', 'A', '3');
INSERT INTO `system_address` VALUES ('395', '巴林左旗', '150422', '150400', '025450', 'B', '3');
INSERT INTO `system_address` VALUES ('396', '巴林右旗', '150423', '150400', '025150', 'B', '3');
INSERT INTO `system_address` VALUES ('397', '林西县', '150424', '150400', '025250', 'L', '3');
INSERT INTO `system_address` VALUES ('398', '克什克腾旗', '150425', '150400', '025350', 'K', '3');
INSERT INTO `system_address` VALUES ('399', '翁牛特旗', '150426', '150400', '024500', 'W', '3');
INSERT INTO `system_address` VALUES ('400', '喀喇沁旗', '150428', '150400', '024400', 'K', '3');
INSERT INTO `system_address` VALUES ('401', '宁城县', '150429', '150400', '024200', 'N', '3');
INSERT INTO `system_address` VALUES ('402', '敖汉旗', '150430', '150400', '024300', 'A', '3');
INSERT INTO `system_address` VALUES ('403', '通辽市', '150500', '150000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('404', '科尔沁区', '150502', '150500', '028000', 'K', '3');
INSERT INTO `system_address` VALUES ('405', '科尔沁左翼中旗', '150521', '150500', '029300', 'K', '3');
INSERT INTO `system_address` VALUES ('406', '科尔沁左翼后旗', '150522', '150500', '028100', 'K', '3');
INSERT INTO `system_address` VALUES ('407', '开鲁县', '150523', '150500', '028400', 'K', '3');
INSERT INTO `system_address` VALUES ('408', '库伦旗', '150524', '150500', '028200', 'K', '3');
INSERT INTO `system_address` VALUES ('409', '奈曼旗', '150525', '150500', '028300', 'N', '3');
INSERT INTO `system_address` VALUES ('410', '扎鲁特旗', '150526', '150500', '029100', 'Z', '3');
INSERT INTO `system_address` VALUES ('411', '霍林郭勒市', '150581', '150500', '029200', 'H', '3');
INSERT INTO `system_address` VALUES ('412', '鄂尔多斯市', '150600', '150000', null, 'E', '2');
INSERT INTO `system_address` VALUES ('413', '东胜区', '150602', '150600', '017000', 'D', '3');
INSERT INTO `system_address` VALUES ('414', '达拉特旗', '150621', '150600', '014300', 'D', '3');
INSERT INTO `system_address` VALUES ('415', '准格尔旗', '150622', '150600', '017100', 'Z', '3');
INSERT INTO `system_address` VALUES ('416', '鄂托克前旗', '150623', '150600', '016200	', 'E', '3');
INSERT INTO `system_address` VALUES ('417', '鄂托克旗', '150624', '150600', '016100', 'E', '3');
INSERT INTO `system_address` VALUES ('418', '杭锦旗', '150625', '150600', '017400', 'H', '3');
INSERT INTO `system_address` VALUES ('419', '乌审旗', '150626', '150600', '017300', 'W', '3');
INSERT INTO `system_address` VALUES ('420', '伊金霍洛旗', '150627', '150600', '017200', 'Y', '3');
INSERT INTO `system_address` VALUES ('421', '呼伦贝尔市', '150700', '150000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('422', '海拉尔区', '150702', '150700', '021000', 'H', '3');
INSERT INTO `system_address` VALUES ('423', '扎赉诺尔区', '150703', '150700', '162750', 'Z', '3');
INSERT INTO `system_address` VALUES ('424', '阿荣旗', '150721', '150700', '162750	', 'A', '3');
INSERT INTO `system_address` VALUES ('425', '莫力达瓦达斡尔族自治旗', '150722', '150700', '162850', 'M', '3');
INSERT INTO `system_address` VALUES ('426', '鄂伦春自治旗', '150723', '150700', '022450', 'E', '3');
INSERT INTO `system_address` VALUES ('427', '鄂温克族自治旗', '150724', '150700', '021100', 'E', '3');
INSERT INTO `system_address` VALUES ('428', '陈巴尔虎旗', '150725', '150700', '021500', 'C', '3');
INSERT INTO `system_address` VALUES ('429', '新巴尔虎左旗', '150726', '150700', '021200', 'X', '3');
INSERT INTO `system_address` VALUES ('430', '新巴尔虎右旗', '150727', '150700', '021300', 'X', '3');
INSERT INTO `system_address` VALUES ('431', '满洲里市', '150781', '150700', '021400', 'M', '3');
INSERT INTO `system_address` VALUES ('432', '牙克石市', '150782', '150700', '022150', 'Y', '3');
INSERT INTO `system_address` VALUES ('433', '扎兰屯市', '150783', '150700', '162650', 'Z', '3');
INSERT INTO `system_address` VALUES ('434', '额尔古纳市', '150784', '150700', '022250', 'E', '3');
INSERT INTO `system_address` VALUES ('435', '根河市', '150785', '150700', '022350', 'G', '3');
INSERT INTO `system_address` VALUES ('436', '巴彦淖尔市', '150800', '150000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('437', '临河区', '150802', '150800', '015000', 'L', '3');
INSERT INTO `system_address` VALUES ('438', '五原县', '150821', '150800', '015100', 'W', '3');
INSERT INTO `system_address` VALUES ('439', '磴口县', '150822', '150800', '015200', 'D', '3');
INSERT INTO `system_address` VALUES ('440', '乌拉特前旗', '150823', '150800', '014400', 'W', '3');
INSERT INTO `system_address` VALUES ('441', '乌拉特中旗', '150824', '150800', '015300', 'W', '3');
INSERT INTO `system_address` VALUES ('442', '乌拉特后旗', '150825', '150800', '015500', 'W', '3');
INSERT INTO `system_address` VALUES ('443', '杭锦后旗', '150826', '150800', '015400', 'H', '3');
INSERT INTO `system_address` VALUES ('444', '乌兰察布市', '150900', '150000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('445', '集宁区', '150902', '150900', '012000', 'J', '3');
INSERT INTO `system_address` VALUES ('446', '卓资县', '150921', '150900', '012300', 'Z', '3');
INSERT INTO `system_address` VALUES ('447', '化德县', '150922', '150900', '013350', 'H', '3');
INSERT INTO `system_address` VALUES ('448', '商都县', '150923', '150900', '013400', 'S', '3');
INSERT INTO `system_address` VALUES ('449', '兴和县', '150924', '150900', '013650', 'X', '3');
INSERT INTO `system_address` VALUES ('450', '凉城县', '150925', '150900', '013750', 'L', '3');
INSERT INTO `system_address` VALUES ('451', '察哈尔右翼前旗', '150926', '150900', '012200', 'C', '3');
INSERT INTO `system_address` VALUES ('452', '察哈尔右翼中旗', '150927', '150900', '013500', 'C', '3');
INSERT INTO `system_address` VALUES ('453', '察哈尔右翼后旗', '150928', '150900', '012400', 'C', '3');
INSERT INTO `system_address` VALUES ('454', '四子王旗', '150929', '150900', '011800', 'S', '3');
INSERT INTO `system_address` VALUES ('455', '丰镇市', '150981', '150900', '012100', 'F', '3');
INSERT INTO `system_address` VALUES ('456', '兴安盟', '152200', '150000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('457', '乌兰浩特市', '152201', '152200', '137400', 'W', '3');
INSERT INTO `system_address` VALUES ('458', '阿尔山市', '152202', '152200', '137400', 'A', '3');
INSERT INTO `system_address` VALUES ('459', '科尔沁右翼前旗', '152221', '152200', '137400', 'K', '3');
INSERT INTO `system_address` VALUES ('460', '科尔沁右翼中旗', '152222', '152200', '137400', 'K', '3');
INSERT INTO `system_address` VALUES ('461', '扎赉特旗', '152223', '152200', '137600', 'Z', '3');
INSERT INTO `system_address` VALUES ('462', '突泉县', '152224', '152200', '137500', 'T', '3');
INSERT INTO `system_address` VALUES ('463', '锡林郭勒盟', '152500', '150000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('464', '二连浩特市', '152501', '152500', '012600', 'E', '3');
INSERT INTO `system_address` VALUES ('465', '锡林浩特市', '152502', '152500', '026000', 'X', '3');
INSERT INTO `system_address` VALUES ('466', '阿巴嘎旗', '152522', '152500', '011400', 'A', '3');
INSERT INTO `system_address` VALUES ('467', '苏尼特左旗', '152523', '152500', '011300', 'S', '3');
INSERT INTO `system_address` VALUES ('468', '苏尼特右旗', '152524', '152500', '011200', 'S', '3');
INSERT INTO `system_address` VALUES ('469', '东乌珠穆沁旗', '152525', '152500', '026300', 'D', '3');
INSERT INTO `system_address` VALUES ('470', '西乌珠穆沁旗', '152526', '152500', '026200', 'X', '3');
INSERT INTO `system_address` VALUES ('471', '太仆寺旗', '152527', '152500', '027000', 'T', '3');
INSERT INTO `system_address` VALUES ('472', '镶黄旗', '152528', '152500', '013250', 'X', '3');
INSERT INTO `system_address` VALUES ('473', '正镶白旗', '152529', '152500', '013800', 'Z', '3');
INSERT INTO `system_address` VALUES ('474', '正蓝旗', '152530', '152500', '027200', 'Z', '3');
INSERT INTO `system_address` VALUES ('475', '多伦县', '152531', '152500', '027300', 'D', '3');
INSERT INTO `system_address` VALUES ('476', '阿拉善盟', '152900', '150000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('477', '阿拉善左旗', '152921', '152900', '750300', 'A', '3');
INSERT INTO `system_address` VALUES ('478', '阿拉善右旗', '152922', '152900', '737300', 'A', '3');
INSERT INTO `system_address` VALUES ('479', '额济纳旗', '152923', '152900', '735400', 'E', '3');
INSERT INTO `system_address` VALUES ('480', '辽宁', '210000', '0', null, 'L', '1');
INSERT INTO `system_address` VALUES ('481', '沈阳市', '210100', '210000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('482', '和平区', '210102', '210100', '110000', 'H', '3');
INSERT INTO `system_address` VALUES ('483', '沈河区', '210103', '210100', '110000', 'S', '3');
INSERT INTO `system_address` VALUES ('484', '大东区', '210104', '210100', '110041', 'D', '3');
INSERT INTO `system_address` VALUES ('485', '皇姑区', '210105', '210100', '110000', 'H', '3');
INSERT INTO `system_address` VALUES ('486', '铁西区', '210106', '210100', '110020', 'T', '3');
INSERT INTO `system_address` VALUES ('487', '苏家屯区', '210111', '210100', '110100', 'S', '3');
INSERT INTO `system_address` VALUES ('488', '东陵区', '210112', '210100', '110000', 'D', '3');
INSERT INTO `system_address` VALUES ('489', '新城子区', '210113', '210100', '110000', 'X', '3');
INSERT INTO `system_address` VALUES ('490', '于洪区', '210114', '210100', '110000', 'Y', '3');
INSERT INTO `system_address` VALUES ('491', '辽中县', '210122', '210100', '110200', 'L', '3');
INSERT INTO `system_address` VALUES ('492', '康平县', '210123', '210100', '110500', 'K', '3');
INSERT INTO `system_address` VALUES ('493', '法库县', '210124', '210100', '110400', 'F', '3');
INSERT INTO `system_address` VALUES ('494', '新民市', '210181', '210100', '110300', 'X', '3');
INSERT INTO `system_address` VALUES ('495', '浑南区', '210182', '210100', '110000', 'H', '3');
INSERT INTO `system_address` VALUES ('496', '张士开发区', '210183', '210100', '110000', 'Z', '3');
INSERT INTO `system_address` VALUES ('497', '沈北新区', '210184', '210100', '110000', 'S', '3');
INSERT INTO `system_address` VALUES ('498', '大连市', '210200', '210000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('499', '中山区', '210202', '210200', '116000', 'Z', '3');
INSERT INTO `system_address` VALUES ('500', '西岗区', '210203', '210200', '116000', 'X', '3');
INSERT INTO `system_address` VALUES ('501', '沙河口区', '210204', '210200', '116000', 'S', '3');
INSERT INTO `system_address` VALUES ('502', '甘井子区', '210211', '210200', '116000', 'G', '3');
INSERT INTO `system_address` VALUES ('503', '旅顺口区', '210212', '210200', '116000', 'L', '3');
INSERT INTO `system_address` VALUES ('504', '金州区', '210213', '210200', '116000', 'J', '3');
INSERT INTO `system_address` VALUES ('505', '大连经济技术开发区', '210214', '210200', '116000', 'D', '3');
INSERT INTO `system_address` VALUES ('506', '长海县', '210224', '210200', '116500', 'Z', '3');
INSERT INTO `system_address` VALUES ('507', '开发区', '210251', '210200', '116000', 'K', '3');
INSERT INTO `system_address` VALUES ('508', '瓦房店市', '210281', '210200', '116300', 'W', '3');
INSERT INTO `system_address` VALUES ('509', '普兰店市', '210282', '210200', '116200', 'P', '3');
INSERT INTO `system_address` VALUES ('510', '庄河市', '210283', '210200', '116400', 'Z', '3');
INSERT INTO `system_address` VALUES ('511', '岭前区', '210297', '210200', '116000', 'L', '3');
INSERT INTO `system_address` VALUES ('512', '鞍山市', '210300', '210000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('513', '铁东区', '210302', '210300', '114000', 'T', '3');
INSERT INTO `system_address` VALUES ('514', '铁西区', '210303', '210300', '114000', 'T', '3');
INSERT INTO `system_address` VALUES ('515', '立山区', '210304', '210300', '114000', 'L', '3');
INSERT INTO `system_address` VALUES ('516', '千山区', '210311', '210300', '114000', 'Q', '3');
INSERT INTO `system_address` VALUES ('517', '台安县', '210321', '210300', '114100', 'T', '3');
INSERT INTO `system_address` VALUES ('518', '岫岩满族自治县', '210323', '210300', '114300', 'X', '3');
INSERT INTO `system_address` VALUES ('519', '高新区', '210351', '210300', '114000', 'G', '3');
INSERT INTO `system_address` VALUES ('520', '海城市', '210381', '210300', '114200', 'H', '3');
INSERT INTO `system_address` VALUES ('521', '抚顺市', '210400', '210000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('522', '新抚区', '210402', '210400', '113000', 'X', '3');
INSERT INTO `system_address` VALUES ('523', '东洲区', '210403', '210400', '113000', 'D', '3');
INSERT INTO `system_address` VALUES ('524', '望花区', '210404', '210400', '113000', 'W', '3');
INSERT INTO `system_address` VALUES ('525', '顺城区', '210411', '210400', '113000', 'S', '3');
INSERT INTO `system_address` VALUES ('526', '抚顺县', '210421', '210400', '113100', 'F', '3');
INSERT INTO `system_address` VALUES ('527', '新宾满族自治县', '210422', '210400', '113200', 'X', '3');
INSERT INTO `system_address` VALUES ('528', '清原满族自治县', '210423', '210400', '113300', 'Q', '3');
INSERT INTO `system_address` VALUES ('529', '本溪市', '210500', '210000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('530', '平山区', '210502', '210500', '117000', 'P', '3');
INSERT INTO `system_address` VALUES ('531', '溪湖区', '210503', '210500', '117000', 'X', '3');
INSERT INTO `system_address` VALUES ('532', '明山区', '210504', '210500', '117000', 'M', '3');
INSERT INTO `system_address` VALUES ('533', '南芬区', '210505', '210500', '117000', 'N', '3');
INSERT INTO `system_address` VALUES ('534', '本溪满族自治县', '210521', '210500', '117100', 'B', '3');
INSERT INTO `system_address` VALUES ('535', '桓仁满族自治县', '210522', '210500', '117200', 'H', '3');
INSERT INTO `system_address` VALUES ('536', '丹东市', '210600', '210000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('537', '元宝区', '210602', '210600', '118000', 'Y', '3');
INSERT INTO `system_address` VALUES ('538', '振兴区', '210603', '210600', '118000', 'Z', '3');
INSERT INTO `system_address` VALUES ('539', '振安区', '210604', '210600', '118000', 'Z', '3');
INSERT INTO `system_address` VALUES ('540', '宽甸满族自治县', '210624', '210600', '118200', 'K', '3');
INSERT INTO `system_address` VALUES ('541', '东港市', '210681', '210600', '118300', 'D', '3');
INSERT INTO `system_address` VALUES ('542', '凤城市', '210682', '210600', '118100', 'F', '3');
INSERT INTO `system_address` VALUES ('543', '锦州市', '210700', '210000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('544', '古塔区', '210702', '210700', '121000', 'G', '3');
INSERT INTO `system_address` VALUES ('545', '凌河区', '210703', '210700', '121000', 'L', '3');
INSERT INTO `system_address` VALUES ('546', '太和区', '210711', '210700', '121000', 'T', '3');
INSERT INTO `system_address` VALUES ('547', '黑山县', '210726', '210700', '121400', 'H', '3');
INSERT INTO `system_address` VALUES ('548', '义县', '210727', '210700', '121100', 'Y', '3');
INSERT INTO `system_address` VALUES ('549', '凌海市', '210781', '210700', '121200', 'L', '3');
INSERT INTO `system_address` VALUES ('550', '北镇市', '210782', '210700', '121300', 'B', '3');
INSERT INTO `system_address` VALUES ('551', '营口市', '210800', '210000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('552', '站前区', '210802', '210800', '115000', 'Z', '3');
INSERT INTO `system_address` VALUES ('553', '西市区', '210803', '210800', '115000', 'X', '3');
INSERT INTO `system_address` VALUES ('554', '鲅鱼圈区', '210804', '210800', '115000', 'B', '3');
INSERT INTO `system_address` VALUES ('555', '老边区', '210811', '210800', '115000', 'L', '3');
INSERT INTO `system_address` VALUES ('556', '盖州市', '210881', '210800', '115200', 'G', '3');
INSERT INTO `system_address` VALUES ('557', '大石桥市', '210882', '210800', '115100', 'D', '3');
INSERT INTO `system_address` VALUES ('558', '阜新市', '210900', '210000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('559', '海州区', '210902', '210900', '123000', 'H', '3');
INSERT INTO `system_address` VALUES ('560', '新邱区', '210903', '210900', '123000', 'X', '3');
INSERT INTO `system_address` VALUES ('561', '太平区', '210904', '210900', '123000', 'T', '3');
INSERT INTO `system_address` VALUES ('562', '清河门区', '210905', '210900', '123000', 'Q', '3');
INSERT INTO `system_address` VALUES ('563', '细河区', '210911', '210900', '123000', 'X', '3');
INSERT INTO `system_address` VALUES ('564', '阜新蒙古族自治县', '210921', '210900', '123100', 'F', '3');
INSERT INTO `system_address` VALUES ('565', '彰武县', '210922', '210900', '123200', 'Z', '3');
INSERT INTO `system_address` VALUES ('566', '辽阳市', '211000', '210000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('567', '白塔区', '211002', '211000', '111000', 'B', '3');
INSERT INTO `system_address` VALUES ('568', '文圣区', '211003', '211000', '111000', 'W', '3');
INSERT INTO `system_address` VALUES ('569', '宏伟区', '211004', '211000', '111000', 'H', '3');
INSERT INTO `system_address` VALUES ('570', '弓长岭区', '211005', '211000', '111000', 'G', '3');
INSERT INTO `system_address` VALUES ('571', '太子河区', '211011', '211000', '111000', 'T', '3');
INSERT INTO `system_address` VALUES ('572', '辽阳县', '211021', '211000', '111200', 'L', '3');
INSERT INTO `system_address` VALUES ('573', '灯塔市', '211081', '211000', '111300', 'D', '3');
INSERT INTO `system_address` VALUES ('574', '盘锦市', '211100', '210000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('575', '双台子区', '211102', '211100', '124000', 'S', '3');
INSERT INTO `system_address` VALUES ('576', '兴隆台区', '211103', '211100', '124000', 'X', '3');
INSERT INTO `system_address` VALUES ('577', '大洼县', '211121', '211100', '124200', 'D', '3');
INSERT INTO `system_address` VALUES ('578', '盘山县', '211122', '211100', '124100', 'P', '3');
INSERT INTO `system_address` VALUES ('579', '铁岭市', '211200', '210000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('580', '银州区', '211202', '211200', '112000', 'Y', '3');
INSERT INTO `system_address` VALUES ('581', '清河区', '211204', '211200', '112000', 'Q', '3');
INSERT INTO `system_address` VALUES ('582', '铁岭县', '211221', '211200', '112600', 'T', '3');
INSERT INTO `system_address` VALUES ('583', '西丰县', '211223', '211200', '112400', 'X', '3');
INSERT INTO `system_address` VALUES ('584', '昌图县', '211224', '211200', '112500', 'C', '3');
INSERT INTO `system_address` VALUES ('585', '调兵山市', '211281', '211200', '112700', 'D', '3');
INSERT INTO `system_address` VALUES ('586', '开原市', '211282', '211200', '112300', 'K', '3');
INSERT INTO `system_address` VALUES ('587', '朝阳市', '211300', '210000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('588', '双塔区', '211302', '211300', '122000', 'S', '3');
INSERT INTO `system_address` VALUES ('589', '龙城区', '211303', '211300', '122000', 'L', '3');
INSERT INTO `system_address` VALUES ('590', '朝阳县', '211321', '211300', '122000', 'C', '3');
INSERT INTO `system_address` VALUES ('591', '建平县', '211322', '211300', '122400', 'J', '3');
INSERT INTO `system_address` VALUES ('592', '喀喇沁左翼蒙古族自治县', '211324', '211300', '122300', 'K', '3');
INSERT INTO `system_address` VALUES ('593', '北票市', '211381', '211300', '122100', 'B', '3');
INSERT INTO `system_address` VALUES ('594', '凌源市', '211382', '211300', '122500', 'L', '3');
INSERT INTO `system_address` VALUES ('595', '葫芦岛市', '211400', '210000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('596', '连山区', '211402', '211400', '125000', 'L', '3');
INSERT INTO `system_address` VALUES ('597', '龙港区', '211403', '211400', '125000', 'L', '3');
INSERT INTO `system_address` VALUES ('598', '南票区', '211404', '211400', '125027', 'N', '3');
INSERT INTO `system_address` VALUES ('599', '绥中县', '211421', '211400', '125200', 'S', '3');
INSERT INTO `system_address` VALUES ('600', '建昌县', '211422', '211400', '125300', 'J', '3');
INSERT INTO `system_address` VALUES ('601', '兴城市', '211481', '211400', '125100', 'X', '3');
INSERT INTO `system_address` VALUES ('602', '吉林', '220000', '0', null, 'J', '1');
INSERT INTO `system_address` VALUES ('603', '长春市', '220100', '220000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('604', '南关区', '220102', '220100', '130000', 'N', '3');
INSERT INTO `system_address` VALUES ('605', '宽城区', '220103', '220100', '130000', 'K', '3');
INSERT INTO `system_address` VALUES ('606', '朝阳区', '220104', '220100', '130012', 'C', '3');
INSERT INTO `system_address` VALUES ('607', '二道区', '220105', '220100', '130000', 'E', '3');
INSERT INTO `system_address` VALUES ('608', '绿园区', '220106', '220100', '130000', 'L', '3');
INSERT INTO `system_address` VALUES ('609', '双阳区', '220112', '220100', '130600', 'S', '3');
INSERT INTO `system_address` VALUES ('610', '高新北区', '220117', '220100', '130000', 'G', '3');
INSERT INTO `system_address` VALUES ('611', '高新区', '220119', '220100', '130000', 'G', '3');
INSERT INTO `system_address` VALUES ('612', '农安县', '220122', '220100', '130200', 'N', '3');
INSERT INTO `system_address` VALUES ('613', '九台区', '220181', '220100', '130500', 'J', '3');
INSERT INTO `system_address` VALUES ('614', '榆树市', '220182', '220100', '130400', 'Y', '3');
INSERT INTO `system_address` VALUES ('615', '德惠市', '220183', '220100', '130300', 'D', '3');
INSERT INTO `system_address` VALUES ('616', '高新技术产业开发区', '220184', '220100', '130000', 'G', '3');
INSERT INTO `system_address` VALUES ('617', '汽车产业开发区', '220185', '220100', '130000', 'Q', '3');
INSERT INTO `system_address` VALUES ('618', '经济技术开发区', '220186', '220100', '130000', 'J', '3');
INSERT INTO `system_address` VALUES ('619', '净月旅游开发区', '220187', '220100', '130000', 'J', '3');
INSERT INTO `system_address` VALUES ('620', '吉林市', '220200', '220000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('621', '昌邑区', '220202', '220200', '132000', 'C', '3');
INSERT INTO `system_address` VALUES ('622', '龙潭区', '220203', '220200', '132000', 'L', '3');
INSERT INTO `system_address` VALUES ('623', '船营区', '220204', '220200', '132000', 'C', '3');
INSERT INTO `system_address` VALUES ('624', '丰满区', '220211', '220200', '132000', 'F', '3');
INSERT INTO `system_address` VALUES ('625', '永吉县', '220221', '220200', '132100', 'Y', '3');
INSERT INTO `system_address` VALUES ('626', '蛟河市', '220281', '220200', '132500', 'J', '3');
INSERT INTO `system_address` VALUES ('627', '桦甸市', '220282', '220200', '132400', 'H', '3');
INSERT INTO `system_address` VALUES ('628', '舒兰市', '220283', '220200', '132600', 'S', '3');
INSERT INTO `system_address` VALUES ('629', '磐石市', '220284', '220200', '132300', 'P', '3');
INSERT INTO `system_address` VALUES ('630', '四平市', '220300', '220000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('631', '铁西区', '220302', '220300', '136000', 'T', '3');
INSERT INTO `system_address` VALUES ('632', '铁东区', '220303', '220300', '136000', 'T', '3');
INSERT INTO `system_address` VALUES ('633', '梨树县', '220322', '220300', '136500', 'L', '3');
INSERT INTO `system_address` VALUES ('634', '伊通满族自治县', '220323', '220300', '130700', 'Y', '3');
INSERT INTO `system_address` VALUES ('635', '公主岭市', '220381', '220300', '136100', 'G', '3');
INSERT INTO `system_address` VALUES ('636', '双辽市', '220382', '220300', '136400', 'S', '3');
INSERT INTO `system_address` VALUES ('637', '辽源市', '220400', '220000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('638', '龙山区', '220402', '220400', '136200', 'L', '3');
INSERT INTO `system_address` VALUES ('639', '西安区', '220403', '220400', '136200', 'X', '3');
INSERT INTO `system_address` VALUES ('640', '东丰县', '220421', '220400', '136300', 'D', '3');
INSERT INTO `system_address` VALUES ('641', '东辽县', '220422', '220400', '136600', 'D', '3');
INSERT INTO `system_address` VALUES ('642', '通化市', '220500', '220000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('643', '东昌区', '220502', '220500', '134000', 'D', '3');
INSERT INTO `system_address` VALUES ('644', '二道江区', '220503', '220500', '134000', 'E', '3');
INSERT INTO `system_address` VALUES ('645', '通化县', '220521', '220500', '134100', 'T', '3');
INSERT INTO `system_address` VALUES ('646', '辉南县', '220523', '220500', '135100', 'H', '3');
INSERT INTO `system_address` VALUES ('647', '柳河县', '220524', '220500', '135300', 'L', '3');
INSERT INTO `system_address` VALUES ('648', '梅河口市', '220581', '220500', '135000', 'M', '3');
INSERT INTO `system_address` VALUES ('649', '集安市', '220582', '220500', '134200', 'J', '3');
INSERT INTO `system_address` VALUES ('650', '白山市', '220600', '220000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('651', '浑江区', '220602', '220600', '134300', 'H', '3');
INSERT INTO `system_address` VALUES ('652', '八道江区', '220603', '220600', '134300', 'B', '3');
INSERT INTO `system_address` VALUES ('653', '抚松县', '220621', '220600', '134500', 'F', '3');
INSERT INTO `system_address` VALUES ('654', '靖宇县', '220622', '220600', '135200', 'J', '3');
INSERT INTO `system_address` VALUES ('655', '长白朝鲜族自治县', '220623', '220600', '134400', 'Z', '3');
INSERT INTO `system_address` VALUES ('656', '江源区', '220625', '220600', '134700', 'J', '3');
INSERT INTO `system_address` VALUES ('657', '临江市', '220681', '220600', '134600', 'L', '3');
INSERT INTO `system_address` VALUES ('658', '松原市', '220700', '220000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('659', '宁江区', '220702', '220700', '138000', 'N', '3');
INSERT INTO `system_address` VALUES ('660', '前郭尔罗斯蒙古族自治县', '220721', '220700', '131100', 'Q', '3');
INSERT INTO `system_address` VALUES ('661', '长岭县', '220722', '220700', '131500', 'Z', '3');
INSERT INTO `system_address` VALUES ('662', '乾安县', '220723', '220700', '138000', 'Q', '3');
INSERT INTO `system_address` VALUES ('663', '扶余市', '220781', '220700', '131200', 'F', '3');
INSERT INTO `system_address` VALUES ('664', '白城市', '220800', '220000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('665', '洮北区', '220802', '220800', '137000', 'T', '3');
INSERT INTO `system_address` VALUES ('666', '镇赉县', '220821', '220800', '137300', 'Z', '3');
INSERT INTO `system_address` VALUES ('667', '通榆县', '220822', '220800', '137200', 'T', '3');
INSERT INTO `system_address` VALUES ('668', '洮南市', '220881', '220800', '137100', 'T', '3');
INSERT INTO `system_address` VALUES ('669', '大安市', '220882', '220800', '131300', 'D', '3');
INSERT INTO `system_address` VALUES ('670', '延边朝鲜族自治州', '222400', '220000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('671', '延吉市', '222401', '222400', '133000', 'Y', '3');
INSERT INTO `system_address` VALUES ('672', '图们市', '222402', '222400', '133100', 'T', '3');
INSERT INTO `system_address` VALUES ('673', '敦化市', '222403', '222400', '133700', 'D', '3');
INSERT INTO `system_address` VALUES ('674', '珲春市', '222404', '222400', '133300', 'H', '3');
INSERT INTO `system_address` VALUES ('675', '龙井市', '222405', '222400', '133400', 'L', '3');
INSERT INTO `system_address` VALUES ('676', '和龙市', '222406', '222400', '133500', 'H', '3');
INSERT INTO `system_address` VALUES ('677', '汪清县', '222424', '222400', '133200', 'W', '3');
INSERT INTO `system_address` VALUES ('678', '安图县', '222426', '222400', '133600', 'A', '3');
INSERT INTO `system_address` VALUES ('679', '黑龙江', '230000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('680', '哈尔滨市', '230100', '230000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('681', '道里区', '230102', '230100', '150000', 'D', '3');
INSERT INTO `system_address` VALUES ('682', '南岗区', '230103', '230100', '150000', 'N', '3');
INSERT INTO `system_address` VALUES ('683', '道外区', '230104', '230100', '150000', 'D', '3');
INSERT INTO `system_address` VALUES ('684', '香坊区', '230106', '230100', '150000', 'X', '3');
INSERT INTO `system_address` VALUES ('685', '动力区', '230107', '230100', '150040', 'D', '3');
INSERT INTO `system_address` VALUES ('686', '平房区', '230108', '230100', '150000', 'P', '3');
INSERT INTO `system_address` VALUES ('687', '松北区', '230109', '230100', '150000', 'S', '3');
INSERT INTO `system_address` VALUES ('688', '香坊区', '230110', '230100', '150036', 'X', '3');
INSERT INTO `system_address` VALUES ('689', '呼兰区', '230111', '230100', '150500', 'H', '3');
INSERT INTO `system_address` VALUES ('690', '依兰县', '230123', '230100', '154800', 'Y', '3');
INSERT INTO `system_address` VALUES ('691', '方正县', '230124', '230100', '150800', 'F', '3');
INSERT INTO `system_address` VALUES ('692', '宾县', '230125', '230100', '150400', 'B', '3');
INSERT INTO `system_address` VALUES ('693', '巴彦县', '230126', '230100', '151800', 'B', '3');
INSERT INTO `system_address` VALUES ('694', '木兰县', '230127', '230100', '151900', 'M', '3');
INSERT INTO `system_address` VALUES ('695', '通河县', '230128', '230100', '150900', 'T', '3');
INSERT INTO `system_address` VALUES ('696', '延寿县', '230129', '230100', '150700', 'Y', '3');
INSERT INTO `system_address` VALUES ('697', '阿城区', '230181', '230100', '150000', 'A', '3');
INSERT INTO `system_address` VALUES ('698', '双城区', '230182', '230100', '150100', 'S', '3');
INSERT INTO `system_address` VALUES ('699', '尚志市', '230183', '230100', '150600', 'S', '3');
INSERT INTO `system_address` VALUES ('700', '五常市', '230184', '230100', '150200', 'W', '3');
INSERT INTO `system_address` VALUES ('701', '阿城市', '230185', '230100', '150300', 'A', '3');
INSERT INTO `system_address` VALUES ('702', '齐齐哈尔市', '230200', '230000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('703', '龙沙区', '230202', '230200', '161000', 'L', '3');
INSERT INTO `system_address` VALUES ('704', '建华区', '230203', '230200', '161000', 'J', '3');
INSERT INTO `system_address` VALUES ('705', '铁锋区', '230204', '230200', '161000', 'T', '3');
INSERT INTO `system_address` VALUES ('706', '昂昂溪区', '230205', '230200', '161000', 'A', '3');
INSERT INTO `system_address` VALUES ('707', '富拉尔基区', '230206', '230200', '161000', 'F', '3');
INSERT INTO `system_address` VALUES ('708', '碾子山区', '230207', '230200', '161000', 'N', '3');
INSERT INTO `system_address` VALUES ('709', '梅里斯达斡尔族区', '230208', '230200', '161000', 'M', '3');
INSERT INTO `system_address` VALUES ('710', '龙江县', '230221', '230200', '161100', 'L', '3');
INSERT INTO `system_address` VALUES ('711', '依安县', '230223', '230200', '161500', 'Y', '3');
INSERT INTO `system_address` VALUES ('712', '泰来县', '230224', '230200', '162400', 'T', '3');
INSERT INTO `system_address` VALUES ('713', '甘南县', '230225', '230200', '162100', 'G', '3');
INSERT INTO `system_address` VALUES ('714', '富裕县', '230227', '230200', '161200', 'F', '3');
INSERT INTO `system_address` VALUES ('715', '克山县', '230229', '230200', '161600', 'K', '3');
INSERT INTO `system_address` VALUES ('716', '克东县', '230230', '230200', '164800', 'K', '3');
INSERT INTO `system_address` VALUES ('717', '拜泉县', '230231', '230200', '164700', 'B', '3');
INSERT INTO `system_address` VALUES ('718', '讷河市', '230281', '230200', '161300', 'N', '3');
INSERT INTO `system_address` VALUES ('719', '鸡西市', '230300', '230000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('720', '鸡冠区', '230302', '230300', '158100', 'J', '3');
INSERT INTO `system_address` VALUES ('721', '恒山区', '230303', '230300', '158100', 'H', '3');
INSERT INTO `system_address` VALUES ('722', '滴道区', '230304', '230300', '158100', 'D', '3');
INSERT INTO `system_address` VALUES ('723', '梨树区', '230305', '230300', '158100', 'L', '3');
INSERT INTO `system_address` VALUES ('724', '城子河区', '230306', '230300', '158100', 'C', '3');
INSERT INTO `system_address` VALUES ('725', '麻山区', '230307', '230300', '158100', 'M', '3');
INSERT INTO `system_address` VALUES ('726', '鸡东县', '230321', '230300', '158200', 'J', '3');
INSERT INTO `system_address` VALUES ('727', '虎林市', '230381', '230300', '158400', 'H', '3');
INSERT INTO `system_address` VALUES ('728', '密山市', '230382', '230300', '158300', 'M', '3');
INSERT INTO `system_address` VALUES ('729', '鹤岗市', '230400', '230000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('730', '向阳区', '230402', '230400', '154100', 'X', '3');
INSERT INTO `system_address` VALUES ('731', '工农区', '230403', '230400', '154100', 'G', '3');
INSERT INTO `system_address` VALUES ('732', '南山区', '230404', '230400', '154100', 'N', '3');
INSERT INTO `system_address` VALUES ('733', '兴安区', '230405', '230400', '154100', 'X', '3');
INSERT INTO `system_address` VALUES ('734', '东山区', '230406', '230400', '154100', 'D', '3');
INSERT INTO `system_address` VALUES ('735', '兴山区', '230407', '230400', '154100', 'X', '3');
INSERT INTO `system_address` VALUES ('736', '萝北县', '230421', '230400', '154200', 'L', '3');
INSERT INTO `system_address` VALUES ('737', '绥滨县', '230422', '230400', '156200', 'S', '3');
INSERT INTO `system_address` VALUES ('738', '双鸭山市', '230500', '230000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('739', '尖山区', '230502', '230500', '155100', 'J', '3');
INSERT INTO `system_address` VALUES ('740', '岭东区', '230503', '230500', '155100', 'L', '3');
INSERT INTO `system_address` VALUES ('741', '四方台区', '230505', '230500', '155100', 'S', '3');
INSERT INTO `system_address` VALUES ('742', '宝山区', '230506', '230500', '155100', 'B', '3');
INSERT INTO `system_address` VALUES ('743', '集贤县', '230521', '230500', '155900', 'J', '3');
INSERT INTO `system_address` VALUES ('744', '友谊县', '230522', '230500', '155800', 'Y', '3');
INSERT INTO `system_address` VALUES ('745', '宝清县', '230523', '230500', '155600', 'B', '3');
INSERT INTO `system_address` VALUES ('746', '饶河县', '230524', '230500', '155700', 'R', '3');
INSERT INTO `system_address` VALUES ('747', '大庆市', '230600', '230000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('748', '萨尔图区', '230602', '230600', '163000', 'S', '3');
INSERT INTO `system_address` VALUES ('749', '龙凤区', '230603', '230600', '163000', 'L', '3');
INSERT INTO `system_address` VALUES ('750', '让胡路区', '230604', '230600', '163000', 'R', '3');
INSERT INTO `system_address` VALUES ('751', '红岗区', '230605', '230600', '163000', 'H', '3');
INSERT INTO `system_address` VALUES ('752', '大同区', '230606', '230600', '163000', 'D', '3');
INSERT INTO `system_address` VALUES ('753', '肇州县', '230621', '230600', '166400', 'Z', '3');
INSERT INTO `system_address` VALUES ('754', '肇源县', '230622', '230600', '166500', 'Z', '3');
INSERT INTO `system_address` VALUES ('755', '林甸县', '230623', '230600', '166300	', 'L', '3');
INSERT INTO `system_address` VALUES ('756', '杜尔伯特蒙古族自治县', '230624', '230600', '166200', 'D', '3');
INSERT INTO `system_address` VALUES ('757', '伊春市', '230700', '230000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('758', '伊春区', '230702', '230700', '153000', 'Y', '3');
INSERT INTO `system_address` VALUES ('759', '南岔区', '230703', '230700', '153000', 'N', '3');
INSERT INTO `system_address` VALUES ('760', '友好区', '230704', '230700', '153000', 'Y', '3');
INSERT INTO `system_address` VALUES ('761', '西林区', '230705', '230700', '153000', 'X', '3');
INSERT INTO `system_address` VALUES ('762', '翠峦区', '230706', '230700', '153000', 'C', '3');
INSERT INTO `system_address` VALUES ('763', '新青区', '230707', '230700', '153036', 'X', '3');
INSERT INTO `system_address` VALUES ('764', '美溪区', '230708', '230700', '153000', 'M', '3');
INSERT INTO `system_address` VALUES ('765', '金山屯区', '230709', '230700', '153000', 'J', '3');
INSERT INTO `system_address` VALUES ('766', '五营区', '230710', '230700', '153000', 'W', '3');
INSERT INTO `system_address` VALUES ('767', '乌马河区', '230711', '230700', '153000', 'W', '3');
INSERT INTO `system_address` VALUES ('768', '汤旺河区', '230712', '230700', '153000', 'T', '3');
INSERT INTO `system_address` VALUES ('769', '带岭区', '230713', '230700', '153000', 'D', '3');
INSERT INTO `system_address` VALUES ('770', '乌伊岭区', '230714', '230700', '153000', 'W', '3');
INSERT INTO `system_address` VALUES ('771', '红星区', '230715', '230700', '153000', 'H', '3');
INSERT INTO `system_address` VALUES ('772', '上甘岭区', '230716', '230700', '153000', 'S', '3');
INSERT INTO `system_address` VALUES ('773', '嘉荫县', '230722', '230700', '153200', 'J', '3');
INSERT INTO `system_address` VALUES ('774', '铁力市', '230781', '230700', '152500', 'T', '3');
INSERT INTO `system_address` VALUES ('775', '佳木斯市', '230800', '230000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('776', '永红区', '230802', '230800', '154004', 'Y', '3');
INSERT INTO `system_address` VALUES ('777', '向阳区', '230803', '230800', '154000', 'X', '3');
INSERT INTO `system_address` VALUES ('778', '前进区', '230804', '230800', '154002', 'Q', '3');
INSERT INTO `system_address` VALUES ('779', '东风区', '230805', '230800', '154000', 'D', '3');
INSERT INTO `system_address` VALUES ('780', '郊区', '230811', '230800', '154000', 'J', '3');
INSERT INTO `system_address` VALUES ('781', '桦南县', '230822', '230800', '154400', 'H', '3');
INSERT INTO `system_address` VALUES ('782', '桦川县', '230826', '230800', '154300', 'H', '3');
INSERT INTO `system_address` VALUES ('783', '汤原县', '230828', '230800', '154700', 'T', '3');
INSERT INTO `system_address` VALUES ('784', '抚远县', '230833', '230800', '156500', 'F', '3');
INSERT INTO `system_address` VALUES ('785', '同江市', '230881', '230800', '156400', 'T', '3');
INSERT INTO `system_address` VALUES ('786', '富锦市', '230882', '230800', '156100', 'F', '3');
INSERT INTO `system_address` VALUES ('787', '七台河市', '230900', '230000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('788', '新兴区', '230902', '230900', '154600', 'X', '3');
INSERT INTO `system_address` VALUES ('789', '桃山区', '230903', '230900', '154600', 'T', '3');
INSERT INTO `system_address` VALUES ('790', '茄子河区', '230904', '230900', '154600', 'Q', '3');
INSERT INTO `system_address` VALUES ('791', '勃利县', '230921', '230900', '154500', 'B', '3');
INSERT INTO `system_address` VALUES ('792', '牡丹江市', '231000', '230000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('793', '东安区', '231002', '231000', '157000', 'D', '3');
INSERT INTO `system_address` VALUES ('794', '阳明区', '231003', '231000', '157000', 'Y', '3');
INSERT INTO `system_address` VALUES ('795', '爱民区', '231004', '231000', '157000', 'A', '3');
INSERT INTO `system_address` VALUES ('796', '西安区', '231005', '231000', '157000', 'X', '3');
INSERT INTO `system_address` VALUES ('797', '东宁县', '231024', '231000', '157200', 'D', '3');
INSERT INTO `system_address` VALUES ('798', '林口县', '231025', '231000', '157600', 'L', '3');
INSERT INTO `system_address` VALUES ('799', '绥芬河市', '231081', '231000', '157300', 'S', '3');
INSERT INTO `system_address` VALUES ('800', '海林市', '231083', '231000', '157100', 'H', '3');
INSERT INTO `system_address` VALUES ('801', '宁安市', '231084', '231000', '157400', 'N', '3');
INSERT INTO `system_address` VALUES ('802', '穆棱市', '231085', '231000', '157500', 'M', '3');
INSERT INTO `system_address` VALUES ('803', '黑河市', '231100', '230000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('804', '爱辉区', '231102', '231100', '164300', 'A', '3');
INSERT INTO `system_address` VALUES ('805', '嫩江县', '231121', '231100', '161400', 'N', '3');
INSERT INTO `system_address` VALUES ('806', '逊克县', '231123', '231100', '164400', 'X', '3');
INSERT INTO `system_address` VALUES ('807', '孙吴县', '231124', '231100', '164200', 'S', '3');
INSERT INTO `system_address` VALUES ('808', '北安市', '231181', '231100', '161400', 'B', '3');
INSERT INTO `system_address` VALUES ('809', '五大连池市', '231182', '231100', '164100', 'W', '3');
INSERT INTO `system_address` VALUES ('810', '绥化市', '231200', '230000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('811', '北林区', '231202', '231200', '152000', 'B', '3');
INSERT INTO `system_address` VALUES ('812', '望奎县', '231221', '231200', '152100', 'W', '3');
INSERT INTO `system_address` VALUES ('813', '兰西县', '231222', '231200', '151500', 'L', '3');
INSERT INTO `system_address` VALUES ('814', '青冈县', '231223', '231200', '151600', 'Q', '3');
INSERT INTO `system_address` VALUES ('815', '庆安县', '231224', '231200', '152400', 'Q', '3');
INSERT INTO `system_address` VALUES ('816', '明水县', '231225', '231200', '151700', 'M', '3');
INSERT INTO `system_address` VALUES ('817', '绥棱县', '231226', '231200', '152200', 'S', '3');
INSERT INTO `system_address` VALUES ('818', '安达市', '231281', '231200', '151400', 'A', '3');
INSERT INTO `system_address` VALUES ('819', '肇东市', '231282', '231200', '151100', 'Z', '3');
INSERT INTO `system_address` VALUES ('820', '海伦市', '231283', '231200', '152300', 'H', '3');
INSERT INTO `system_address` VALUES ('821', '大兴安岭地区', '232700', '230000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('822', '松岭区', '232702', '232700', '165010', 'S', '3');
INSERT INTO `system_address` VALUES ('823', '新林区', '232703', '232700', '165000', 'X', '3');
INSERT INTO `system_address` VALUES ('824', '呼中区', '232704', '232700', '165036', 'H', '3');
INSERT INTO `system_address` VALUES ('825', '呼玛县', '232721', '232700', '165100', 'H', '3');
INSERT INTO `system_address` VALUES ('826', '塔河县', '232722', '232700', '165200', 'T', '3');
INSERT INTO `system_address` VALUES ('827', '漠河县', '232723', '232700', '165300', 'M', '3');
INSERT INTO `system_address` VALUES ('828', '加格达奇区', '232724', '232700', '165000', 'J', '3');
INSERT INTO `system_address` VALUES ('829', '上海', '310000', '0', null, 'S', '1');
INSERT INTO `system_address` VALUES ('830', '市辖区', '310100', '310000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('831', '黄浦区', '310101', '310100', '200001', 'H', '3');
INSERT INTO `system_address` VALUES ('832', '卢湾区', '310103', '310100', '200020', 'L', '3');
INSERT INTO `system_address` VALUES ('833', '徐汇区', '310104', '310100', '200030', 'X', '3');
INSERT INTO `system_address` VALUES ('834', '长宁区', '310105', '310100', '200050', 'Z', '3');
INSERT INTO `system_address` VALUES ('835', '静安区', '310106', '310100', '200040', 'J', '3');
INSERT INTO `system_address` VALUES ('836', '普陀区', '310107', '310100', '200333', 'P', '3');
INSERT INTO `system_address` VALUES ('837', '闸北区', '310108', '310100', '200070', 'Z', '3');
INSERT INTO `system_address` VALUES ('838', '虹口区', '310109', '310100', '200080', 'H', '3');
INSERT INTO `system_address` VALUES ('839', '杨浦区', '310110', '310100', '200082', 'Y', '3');
INSERT INTO `system_address` VALUES ('840', '闵行区', '310112', '310100', '201100', 'M', '3');
INSERT INTO `system_address` VALUES ('841', '宝山区', '310113', '310100', '201900', 'B', '3');
INSERT INTO `system_address` VALUES ('842', '嘉定区', '310114', '310100', '201800', 'J', '3');
INSERT INTO `system_address` VALUES ('843', '浦东新区', '310115', '310100', '200120', 'P', '3');
INSERT INTO `system_address` VALUES ('844', '金山区', '310116', '310100', '200540', 'J', '3');
INSERT INTO `system_address` VALUES ('845', '松江区', '310117', '310100', '201600', 'S', '3');
INSERT INTO `system_address` VALUES ('846', '青浦区', '310118', '310100', '201700', 'Q', '3');
INSERT INTO `system_address` VALUES ('847', '南汇区', '310119', '310100', '201300', 'N', '3');
INSERT INTO `system_address` VALUES ('848', '奉贤区', '310120', '310100', '201499', 'F', '3');
INSERT INTO `system_address` VALUES ('849', '川沙区', '310152', '310100', '200000', 'C', '3');
INSERT INTO `system_address` VALUES ('850', '崇明县', '310230', '310100', '202150', 'C', '3');
INSERT INTO `system_address` VALUES ('851', '江苏', '320000', '0', null, 'J', '1');
INSERT INTO `system_address` VALUES ('852', '南京市', '320100', '320000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('853', '玄武区', '320102', '320100', '210000', 'X', '3');
INSERT INTO `system_address` VALUES ('854', '白下区', '320103', '320100', '210000', 'B', '3');
INSERT INTO `system_address` VALUES ('855', '秦淮区', '320104', '320100', '210000', 'Q', '3');
INSERT INTO `system_address` VALUES ('856', '建邺区', '320105', '320100', '210000', 'J', '3');
INSERT INTO `system_address` VALUES ('857', '鼓楼区', '320106', '320100', '210000', 'G', '3');
INSERT INTO `system_address` VALUES ('858', '下关区', '320107', '320100', '210000', 'X', '3');
INSERT INTO `system_address` VALUES ('859', '浦口区', '320111', '320100', '210000', 'P', '3');
INSERT INTO `system_address` VALUES ('860', '栖霞区', '320113', '320100', '210000', 'Q', '3');
INSERT INTO `system_address` VALUES ('861', '雨花台区', '320114', '320100', '210000', 'Y', '3');
INSERT INTO `system_address` VALUES ('862', '江宁区', '320115', '320100', '211100', 'J', '3');
INSERT INTO `system_address` VALUES ('863', '六合区', '320116', '320100', '211500', 'L', '3');
INSERT INTO `system_address` VALUES ('864', '溧水区', '320124', '320100', '211200', 'L', '3');
INSERT INTO `system_address` VALUES ('865', '高淳区', '320125', '320100', '211300', 'G', '3');
INSERT INTO `system_address` VALUES ('866', '无锡市', '320200', '320000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('867', '崇安区', '320202', '320200', '214000', 'C', '3');
INSERT INTO `system_address` VALUES ('868', '南长区', '320203', '320200', '214000', 'N', '3');
INSERT INTO `system_address` VALUES ('869', '北塘区', '320204', '320200', '214000', 'B', '3');
INSERT INTO `system_address` VALUES ('870', '锡山区', '320205', '320200', '214000', 'X', '3');
INSERT INTO `system_address` VALUES ('871', '惠山区', '320206', '320200', '214100', 'H', '3');
INSERT INTO `system_address` VALUES ('872', '滨湖区', '320211', '320200', '214100', 'B', '3');
INSERT INTO `system_address` VALUES ('873', '梁溪区', '320213', '320200', '214000', 'L', '3');
INSERT INTO `system_address` VALUES ('874', '新吴区', '320214', '320200', '214028', 'X', '3');
INSERT INTO `system_address` VALUES ('875', '江阴市', '320281', '320200', '214400', 'J', '3');
INSERT INTO `system_address` VALUES ('876', '宜兴市', '320282', '320200', '214200', 'Y', '3');
INSERT INTO `system_address` VALUES ('877', '新区', '320296', '320200', '214000', 'X', '3');
INSERT INTO `system_address` VALUES ('878', '徐州市', '320300', '320000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('879', '鼓楼区', '320302', '320300', '221000', 'G', '3');
INSERT INTO `system_address` VALUES ('880', '云龙区', '320303', '320300', '221000', 'Y', '3');
INSERT INTO `system_address` VALUES ('881', '九里区', '320304', '320300', '221140', 'J', '3');
INSERT INTO `system_address` VALUES ('882', '贾汪区', '320305', '320300', '221000', 'J', '3');
INSERT INTO `system_address` VALUES ('883', '泉山区', '320311', '320300', '221000', 'Q', '3');
INSERT INTO `system_address` VALUES ('884', '丰县', '320321', '320300', '221700', 'F', '3');
INSERT INTO `system_address` VALUES ('885', '沛县', '320322', '320300', '221600', 'P', '3');
INSERT INTO `system_address` VALUES ('886', '铜山区', '320323', '320300', '221100', 'T', '3');
INSERT INTO `system_address` VALUES ('887', '睢宁县', '320324', '320300', '221200', 'S', '3');
INSERT INTO `system_address` VALUES ('888', '新沂市', '320381', '320300', '221400', 'X', '3');
INSERT INTO `system_address` VALUES ('889', '邳州市', '320382', '320300', '221300', 'P', '3');
INSERT INTO `system_address` VALUES ('890', '常州市', '320400', '320000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('891', '天宁区', '320402', '320400', '213000', 'T', '3');
INSERT INTO `system_address` VALUES ('892', '钟楼区', '320404', '320400', '213000', 'Z', '3');
INSERT INTO `system_address` VALUES ('893', '戚墅堰区', '320405', '320400', '213000', 'Q', '3');
INSERT INTO `system_address` VALUES ('894', '新北区', '320411', '320400', '213001', 'X', '3');
INSERT INTO `system_address` VALUES ('895', '武进区', '320412', '320400', '213100', 'W', '3');
INSERT INTO `system_address` VALUES ('896', '溧阳市', '320481', '320400', '213300', 'L', '3');
INSERT INTO `system_address` VALUES ('897', '金坛区', '320482', '320400', '213200', 'J', '3');
INSERT INTO `system_address` VALUES ('898', '苏州市', '320500', '320000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('899', '沧浪区', '320502', '320500', '215000', 'C', '3');
INSERT INTO `system_address` VALUES ('900', '平江区', '320503', '320500', '215000', 'P', '3');
INSERT INTO `system_address` VALUES ('901', '金阊区', '320504', '320500', '215000', 'J', '3');
INSERT INTO `system_address` VALUES ('902', '虎丘区', '320505', '320500', '215004', 'H', '3');
INSERT INTO `system_address` VALUES ('903', '吴中区', '320506', '320500', '215100', 'W', '3');
INSERT INTO `system_address` VALUES ('904', '相城区', '320507', '320500', '215100', 'X', '3');
INSERT INTO `system_address` VALUES ('905', '姑苏区', '320508', '320500', '215008', 'G', '3');
INSERT INTO `system_address` VALUES ('906', '新区', '320510', '320500', '215000', 'X', '3');
INSERT INTO `system_address` VALUES ('907', '常熟市', '320581', '320500', '215500', 'C', '3');
INSERT INTO `system_address` VALUES ('908', '张家港市', '320582', '320500', '215600', 'Z', '3');
INSERT INTO `system_address` VALUES ('909', '昆山市', '320583', '320500', '215300', 'K', '3');
INSERT INTO `system_address` VALUES ('910', '吴江区', '320584', '320500', '215200', 'W', '3');
INSERT INTO `system_address` VALUES ('911', '太仓市', '320585', '320500', '215400', 'T', '3');
INSERT INTO `system_address` VALUES ('912', '园区', '320595', '320500', '215000', 'Y', '3');
INSERT INTO `system_address` VALUES ('913', '南通市', '320600', '320000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('914', '崇川区', '320602', '320600', '226000', 'C', '3');
INSERT INTO `system_address` VALUES ('915', '港闸区', '320611', '320600', '226000', 'G', '3');
INSERT INTO `system_address` VALUES ('916', '通州区', '320612', '320600', '226000', 'T', '3');
INSERT INTO `system_address` VALUES ('917', '开发区', '320613', '320600', '226000', 'K', '3');
INSERT INTO `system_address` VALUES ('918', '海安县', '320621', '320600', '226600', 'H', '3');
INSERT INTO `system_address` VALUES ('919', '如东县', '320623', '320600', '226400', 'R', '3');
INSERT INTO `system_address` VALUES ('920', '启东市', '320681', '320600', '226200', 'Q', '3');
INSERT INTO `system_address` VALUES ('921', '如皋市', '320682', '320600', '226500', 'R', '3');
INSERT INTO `system_address` VALUES ('922', '通州市', '320683', '320600', '226300', 'T', '3');
INSERT INTO `system_address` VALUES ('923', '海门市', '320684', '320600', '226100', 'H', '3');
INSERT INTO `system_address` VALUES ('924', '开发区', '320693', '320600', '226000', 'K', '3');
INSERT INTO `system_address` VALUES ('925', '连云港市', '320700', '320000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('926', '连云区', '320703', '320700', '222000', 'L', '3');
INSERT INTO `system_address` VALUES ('927', '新浦区', '320705', '320700', '222000', 'X', '3');
INSERT INTO `system_address` VALUES ('928', '海州区', '320706', '320700', '222000', 'H', '3');
INSERT INTO `system_address` VALUES ('929', '赣榆区', '320721', '320700', '222100', 'G', '3');
INSERT INTO `system_address` VALUES ('930', '东海县', '320722', '320700', '222300', 'D', '3');
INSERT INTO `system_address` VALUES ('931', '灌云县', '320723', '320700', '222200', 'G', '3');
INSERT INTO `system_address` VALUES ('932', '灌南县', '320724', '320700', '223500', 'G', '3');
INSERT INTO `system_address` VALUES ('933', '淮安市', '320800', '320000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('934', '清河区', '320802', '320800', '223001', 'Q', '3');
INSERT INTO `system_address` VALUES ('935', '淮安区', '320803', '320800', '223200', 'H', '3');
INSERT INTO `system_address` VALUES ('936', '淮阴区', '320804', '320800', '223300', 'H', '3');
INSERT INTO `system_address` VALUES ('937', '开发区', '320809', '320800', '223001', 'K', '3');
INSERT INTO `system_address` VALUES ('938', '清浦区', '320811', '320800', '223001', 'Q', '3');
INSERT INTO `system_address` VALUES ('939', '涟水县', '320826', '320800', '223400', 'L', '3');
INSERT INTO `system_address` VALUES ('940', '洪泽县', '320829', '320800', '223100', 'H', '3');
INSERT INTO `system_address` VALUES ('941', '盱眙县', '320830', '320800', '211700', 'X', '3');
INSERT INTO `system_address` VALUES ('942', '金湖县', '320831', '320800', '211600', 'J', '3');
INSERT INTO `system_address` VALUES ('943', '盐城市', '320900', '320000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('944', '亭湖区', '320902', '320900', '224000', 'T', '3');
INSERT INTO `system_address` VALUES ('945', '盐都区', '320903', '320900', '224000', 'Y', '3');
INSERT INTO `system_address` VALUES ('946', '响水县', '320921', '320900', '224600', 'X', '3');
INSERT INTO `system_address` VALUES ('947', '滨海县', '320922', '320900', '224500', 'B', '3');
INSERT INTO `system_address` VALUES ('948', '阜宁县', '320923', '320900', '224400', 'F', '3');
INSERT INTO `system_address` VALUES ('949', '射阳县', '320924', '320900', '224300', 'S', '3');
INSERT INTO `system_address` VALUES ('950', '建湖县', '320925', '320900', '224700', 'J', '3');
INSERT INTO `system_address` VALUES ('951', '东台市', '320981', '320900', '224200', 'D', '3');
INSERT INTO `system_address` VALUES ('952', '大丰市', '320982', '320900', '224100', 'D', '3');
INSERT INTO `system_address` VALUES ('953', '扬州市', '321000', '320000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('954', '广陵区', '321002', '321000', '225000', 'G', '3');
INSERT INTO `system_address` VALUES ('955', '邗江区', '321003', '321000', '225100', 'H', '3');
INSERT INTO `system_address` VALUES ('956', '维扬区', '321011', '321000', '225000', 'W', '3');
INSERT INTO `system_address` VALUES ('957', '宝应县', '321023', '321000', '225800', 'B', '3');
INSERT INTO `system_address` VALUES ('958', '仪征市', '321081', '321000', '211400', 'Y', '3');
INSERT INTO `system_address` VALUES ('959', '高邮市', '321084', '321000', '225600', 'G', '3');
INSERT INTO `system_address` VALUES ('960', '江都区', '321088', '321000', '225200', 'J', '3');
INSERT INTO `system_address` VALUES ('961', '经济开发区', '321092', '321000', '225000', 'J', '3');
INSERT INTO `system_address` VALUES ('962', '镇江市', '321100', '320000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('963', '京口区', '321102', '321100', '212000', 'J', '3');
INSERT INTO `system_address` VALUES ('964', '润州区', '321111', '321100', '212000', 'R', '3');
INSERT INTO `system_address` VALUES ('965', '丹徒区', '321112', '321100', '212100', 'D', '3');
INSERT INTO `system_address` VALUES ('966', '丹阳市', '321181', '321100', '212300', 'D', '3');
INSERT INTO `system_address` VALUES ('967', '扬中市', '321182', '321100', '212200', 'Y', '3');
INSERT INTO `system_address` VALUES ('968', '句容市', '321183', '321100', '212400', 'J', '3');
INSERT INTO `system_address` VALUES ('969', '泰州市', '321200', '320000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('970', '海陵区', '321202', '321200', '225300', 'H', '3');
INSERT INTO `system_address` VALUES ('971', '高港区', '321203', '321200', '225300', 'G', '3');
INSERT INTO `system_address` VALUES ('972', '兴化市', '321281', '321200', '225700', 'X', '3');
INSERT INTO `system_address` VALUES ('973', '靖江市', '321282', '321200', '214500', 'J', '3');
INSERT INTO `system_address` VALUES ('974', '泰兴市', '321283', '321200', '225400', 'T', '3');
INSERT INTO `system_address` VALUES ('975', '姜堰区', '321284', '321200', '225500', 'J', '3');
INSERT INTO `system_address` VALUES ('976', '宿迁市', '321300', '320000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('977', '宿城区', '321302', '321300', '223800', 'S', '3');
INSERT INTO `system_address` VALUES ('978', '宿豫区', '321311', '321300', '223800', 'S', '3');
INSERT INTO `system_address` VALUES ('979', '沭阳县', '321322', '321300', '223600', 'S', '3');
INSERT INTO `system_address` VALUES ('980', '泗阳县', '321323', '321300', '223700', 'S', '3');
INSERT INTO `system_address` VALUES ('981', '泗洪县', '321324', '321300', '223900', 'S', '3');
INSERT INTO `system_address` VALUES ('982', '浙江', '330000', '0', null, 'Z', '1');
INSERT INTO `system_address` VALUES ('983', '杭州市', '330100', '330000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('984', '上城区', '330102', '330100', '310000', 'S', '3');
INSERT INTO `system_address` VALUES ('985', '下城区', '330103', '330100', '310006', 'X', '3');
INSERT INTO `system_address` VALUES ('986', '江干区', '330104', '330100', '310000', 'J', '3');
INSERT INTO `system_address` VALUES ('987', '拱墅区', '330105', '330100', '310011', 'G', '3');
INSERT INTO `system_address` VALUES ('988', '西湖区', '330106', '330100', '310000', 'X', '3');
INSERT INTO `system_address` VALUES ('989', '滨江区', '330108', '330100', '310000', 'B', '3');
INSERT INTO `system_address` VALUES ('990', '萧山区', '330109', '330100', '311200', 'X', '3');
INSERT INTO `system_address` VALUES ('991', '余杭区', '330110', '330100', '311100', 'Y', '3');
INSERT INTO `system_address` VALUES ('992', '桐庐县', '330122', '330100', '311500', 'T', '3');
INSERT INTO `system_address` VALUES ('993', '淳安县', '330127', '330100', '311700', 'C', '3');
INSERT INTO `system_address` VALUES ('994', '建德市', '330182', '330100', '311600', 'J', '3');
INSERT INTO `system_address` VALUES ('995', '富阳区', '330183', '330100', '311400', 'F', '3');
INSERT INTO `system_address` VALUES ('996', '临安市', '330185', '330100', '311300', 'L', '3');
INSERT INTO `system_address` VALUES ('997', '宁波市', '330200', '330000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('998', '海曙区', '330203', '330200', '315000', 'H', '3');
INSERT INTO `system_address` VALUES ('999', '江东区', '330204', '330200', '315000', 'J', '3');
INSERT INTO `system_address` VALUES ('1000', '江北区', '330205', '330200', '315000', 'J', '3');
INSERT INTO `system_address` VALUES ('1001', '北仑区', '330206', '330200', '315800', 'B', '3');
INSERT INTO `system_address` VALUES ('1002', '镇海区', '330211', '330200', '315200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1003', '鄞州区', '330212', '330200', '315100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1004', '象山县', '330225', '330200', '315700', 'X', '3');
INSERT INTO `system_address` VALUES ('1005', '宁海县', '330226', '330200', '315600', 'N', '3');
INSERT INTO `system_address` VALUES ('1006', '余姚市', '330281', '330200', '315400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1007', '慈溪市', '330282', '330200', '315300', 'C', '3');
INSERT INTO `system_address` VALUES ('1008', '奉化市', '330283', '330200', '315500', 'F', '3');
INSERT INTO `system_address` VALUES ('1009', '高新区', '330299', '330200', '315000', 'G', '3');
INSERT INTO `system_address` VALUES ('1010', '温州市', '330300', '330000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('1011', '鹿城区', '330302', '330300', '325000', 'L', '3');
INSERT INTO `system_address` VALUES ('1012', '龙湾区', '330303', '330300', '325000', 'L', '3');
INSERT INTO `system_address` VALUES ('1013', '瓯海区', '330304', '330300', '325000', 'O', '3');
INSERT INTO `system_address` VALUES ('1014', '洞头县', '330322', '330300', '325700', 'D', '3');
INSERT INTO `system_address` VALUES ('1015', '永嘉县', '330324', '330300', '325100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1016', '平阳县', '330326', '330300', '325400', 'P', '3');
INSERT INTO `system_address` VALUES ('1017', '苍南县', '330327', '330300', '325800', 'C', '3');
INSERT INTO `system_address` VALUES ('1018', '文成县', '330328', '330300', '325300', 'W', '3');
INSERT INTO `system_address` VALUES ('1019', '泰顺县', '330329', '330300', '325500', 'T', '3');
INSERT INTO `system_address` VALUES ('1020', '瑞安市', '330381', '330300', '325200', 'R', '3');
INSERT INTO `system_address` VALUES ('1021', '乐清市', '330382', '330300', '325600', 'L', '3');
INSERT INTO `system_address` VALUES ('1022', '嘉兴市', '330400', '330000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1023', '南湖区', '330402', '330400', '314001', 'N', '3');
INSERT INTO `system_address` VALUES ('1024', '秀洲区', '330411', '330400', '314000', 'X', '3');
INSERT INTO `system_address` VALUES ('1025', '嘉善县', '330421', '330400', '314100', 'J', '3');
INSERT INTO `system_address` VALUES ('1026', '海盐县', '330424', '330400', '314300', 'H', '3');
INSERT INTO `system_address` VALUES ('1027', '海宁市', '330481', '330400', '314400', 'H', '3');
INSERT INTO `system_address` VALUES ('1028', '平湖市', '330482', '330400', '314200', 'P', '3');
INSERT INTO `system_address` VALUES ('1029', '桐乡市', '330483', '330400', '314500', 'T', '3');
INSERT INTO `system_address` VALUES ('1030', '湖州市', '330500', '330000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1031', '吴兴区', '330502', '330500', '313000', 'W', '3');
INSERT INTO `system_address` VALUES ('1032', '南浔区', '330503', '330500', '313000', 'N', '3');
INSERT INTO `system_address` VALUES ('1033', '德清县', '330521', '330500', '313200', 'D', '3');
INSERT INTO `system_address` VALUES ('1034', '长兴县', '330522', '330500', '313100', 'Z', '3');
INSERT INTO `system_address` VALUES ('1035', '安吉县', '330523', '330500', '313300', 'A', '3');
INSERT INTO `system_address` VALUES ('1036', '绍兴市', '330600', '330000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1037', '越城区', '330602', '330600', '312000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1038', '柯桥区', '330621', '330600', '312030', 'K', '3');
INSERT INTO `system_address` VALUES ('1039', '新昌县', '330624', '330600', '312500', 'X', '3');
INSERT INTO `system_address` VALUES ('1040', '诸暨市', '330681', '330600', '311800', 'Z', '3');
INSERT INTO `system_address` VALUES ('1041', '上虞区', '330682', '330600', '312300', 'S', '3');
INSERT INTO `system_address` VALUES ('1042', '嵊州市', '330683', '330600', '312400', 'S', '3');
INSERT INTO `system_address` VALUES ('1043', '金华市', '330700', '330000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1044', '婺城区', '330702', '330700', '321000', 'W', '3');
INSERT INTO `system_address` VALUES ('1045', '金东区', '330703', '330700', '321000', 'J', '3');
INSERT INTO `system_address` VALUES ('1046', '武义县', '330723', '330700', '321200', 'W', '3');
INSERT INTO `system_address` VALUES ('1047', '浦江县', '330726', '330700', '322200', 'P', '3');
INSERT INTO `system_address` VALUES ('1048', '磐安县', '330727', '330700', '322300', 'P', '3');
INSERT INTO `system_address` VALUES ('1049', '兰溪市', '330781', '330700', '321100', 'L', '3');
INSERT INTO `system_address` VALUES ('1050', '义乌市', '330782', '330700', '322000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1051', '东阳市', '330783', '330700', '322100', 'D', '3');
INSERT INTO `system_address` VALUES ('1052', '永康市', '330784', '330700', '321300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1053', '衢州市', '330800', '330000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('1054', '柯城区', '330802', '330800', '324000', 'K', '3');
INSERT INTO `system_address` VALUES ('1055', '衢江区', '330803', '330800', '324000', 'Q', '3');
INSERT INTO `system_address` VALUES ('1056', '常山县', '330822', '330800', '324200', 'C', '3');
INSERT INTO `system_address` VALUES ('1057', '开化县', '330824', '330800', '324300', 'K', '3');
INSERT INTO `system_address` VALUES ('1058', '龙游县', '330825', '330800', '324400', 'L', '3');
INSERT INTO `system_address` VALUES ('1059', '江山市', '330881', '330800', '324100', 'J', '3');
INSERT INTO `system_address` VALUES ('1060', '舟山市', '330900', '330000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1061', '定海区', '330902', '330900', '316000', 'D', '3');
INSERT INTO `system_address` VALUES ('1062', '普陀区', '330903', '330900', '316100', 'P', '3');
INSERT INTO `system_address` VALUES ('1063', '岱山县', '330921', '330900', '316200', 'D', '3');
INSERT INTO `system_address` VALUES ('1064', '嵊泗县', '330922', '330900', '202450', 'S', '3');
INSERT INTO `system_address` VALUES ('1065', '台州市', '331000', '330000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('1066', '椒江区', '331002', '331000', '317700', 'J', '3');
INSERT INTO `system_address` VALUES ('1067', '黄岩区', '331003', '331000', '318020', 'H', '3');
INSERT INTO `system_address` VALUES ('1068', '路桥区', '331004', '331000', '318000', 'L', '3');
INSERT INTO `system_address` VALUES ('1069', '玉环县', '331021', '331000', '317600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1070', '三门县', '331022', '331000', '317100', 'S', '3');
INSERT INTO `system_address` VALUES ('1071', '天台县', '331023', '331000', '317200', 'T', '3');
INSERT INTO `system_address` VALUES ('1072', '仙居县', '331024', '331000', '317300', 'X', '3');
INSERT INTO `system_address` VALUES ('1073', '温岭市', '331081', '331000', '317500', 'W', '3');
INSERT INTO `system_address` VALUES ('1074', '临海市', '331082', '331000', '317000', 'L', '3');
INSERT INTO `system_address` VALUES ('1075', '丽水市', '331100', '330000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1076', '莲都区', '331102', '331100', '323000', 'L', '3');
INSERT INTO `system_address` VALUES ('1077', '青田县', '331121', '331100', '323900', 'Q', '3');
INSERT INTO `system_address` VALUES ('1078', '缙云县', '331122', '331100', '321400', 'J', '3');
INSERT INTO `system_address` VALUES ('1079', '遂昌县', '331123', '331100', '323300', 'S', '3');
INSERT INTO `system_address` VALUES ('1080', '松阳县', '331124', '331100', '323400', 'S', '3');
INSERT INTO `system_address` VALUES ('1081', '云和县', '331125', '331100', '323600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1082', '庆元县', '331126', '331100', '323800', 'Q', '3');
INSERT INTO `system_address` VALUES ('1083', '景宁畲族自治县', '331127', '331100', '323500', 'J', '3');
INSERT INTO `system_address` VALUES ('1084', '龙泉市', '331181', '331100', '323700', 'L', '3');
INSERT INTO `system_address` VALUES ('1085', '安徽', '340000', '0', null, 'A', '1');
INSERT INTO `system_address` VALUES ('1086', '合肥市', '340100', '340000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1087', '瑶海区', '340102', '340100', '230000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1088', '庐阳区', '340103', '340100', '230000', 'L', '3');
INSERT INTO `system_address` VALUES ('1089', '蜀山区', '340104', '340100', '230000', 'S', '3');
INSERT INTO `system_address` VALUES ('1090', '包河区', '340111', '340100', '230000', 'B', '3');
INSERT INTO `system_address` VALUES ('1091', '长丰县', '340121', '340100', '231100', 'Z', '3');
INSERT INTO `system_address` VALUES ('1092', '肥东县', '340122', '340100', '230000', 'F', '3');
INSERT INTO `system_address` VALUES ('1093', '肥西县', '340123', '340100', '231200', 'F', '3');
INSERT INTO `system_address` VALUES ('1094', '高新区', '340151', '340100', '230000', 'G', '3');
INSERT INTO `system_address` VALUES ('1095', '中区', '340191', '340100', '230000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1096', '合肥经济技术开发区', '340198', '340100', '230000', 'H', '3');
INSERT INTO `system_address` VALUES ('1097', '合肥市政务新区', '340199', '340100', '230000', 'H', '3');
INSERT INTO `system_address` VALUES ('1098', '巢湖市', '341400', '340100', '238000', 'C', '3');
INSERT INTO `system_address` VALUES ('1099', '居巢区', '341402', '340100', '238000', 'J', '3');
INSERT INTO `system_address` VALUES ('1100', '庐江县', '341421', '340100', '231500', 'L', '3');
INSERT INTO `system_address` VALUES ('1101', '芜湖市', '340200', '340000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('1102', '镜湖区', '340202', '340200', '241000', 'J', '3');
INSERT INTO `system_address` VALUES ('1103', '弋江区', '340203', '340200', '241000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1104', '鸠江区', '340207', '340200', '241000', 'J', '3');
INSERT INTO `system_address` VALUES ('1105', '三山区', '340208', '340200', '241000', 'S', '3');
INSERT INTO `system_address` VALUES ('1106', '芜湖县', '340221', '340200', '241100', 'W', '3');
INSERT INTO `system_address` VALUES ('1107', '繁昌县', '340222', '340200', '241200', 'F', '3');
INSERT INTO `system_address` VALUES ('1108', '南陵县', '340223', '340200', '242400', 'N', '3');
INSERT INTO `system_address` VALUES ('1109', '无为县', '341422', '340200', '238300', 'W', '3');
INSERT INTO `system_address` VALUES ('1110', '蚌埠市', '340300', '340000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('1111', '龙子湖区', '340302', '340300', '233000', 'L', '3');
INSERT INTO `system_address` VALUES ('1112', '蚌山区', '340303', '340300', '233000', 'B', '3');
INSERT INTO `system_address` VALUES ('1113', '禹会区', '340304', '340300', '233000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1114', '淮上区', '340311', '340300', '233000', 'H', '3');
INSERT INTO `system_address` VALUES ('1115', '怀远县', '340321', '340300', '233400', 'H', '3');
INSERT INTO `system_address` VALUES ('1116', '五河县', '340322', '340300', '233000', 'W', '3');
INSERT INTO `system_address` VALUES ('1117', '固镇县', '340323', '340300', '233700', 'G', '3');
INSERT INTO `system_address` VALUES ('1118', '淮南市', '340400', '340000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1119', '大通区', '340402', '340400', '232000', 'D', '3');
INSERT INTO `system_address` VALUES ('1120', '田家庵区', '340403', '340400', '232000', 'T', '3');
INSERT INTO `system_address` VALUES ('1121', '谢家集区', '340404', '340400', '232000', 'X', '3');
INSERT INTO `system_address` VALUES ('1122', '八公山区', '340405', '340400', '232000', 'B', '3');
INSERT INTO `system_address` VALUES ('1123', '潘集区', '340406', '340400', '232000', 'P', '3');
INSERT INTO `system_address` VALUES ('1124', '凤台县', '340421', '340400', '232100', 'F', '3');
INSERT INTO `system_address` VALUES ('1125', '寿县', '341521', '340400', '232200', 'S', '3');
INSERT INTO `system_address` VALUES ('1126', '马鞍山市', '340500', '340000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('1127', '金家庄区', '340502', '340500', '243000', 'J', '3');
INSERT INTO `system_address` VALUES ('1128', '花山区', '340503', '340500', '243000', 'H', '3');
INSERT INTO `system_address` VALUES ('1129', '雨山区', '340504', '340500', '243000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1130', '博望区', '340506', '340500', '243131', 'B', '3');
INSERT INTO `system_address` VALUES ('1131', '当涂县', '340521', '340500', '243100', 'D', '3');
INSERT INTO `system_address` VALUES ('1132', '含山县', '341423', '340500', '238100', 'H', '3');
INSERT INTO `system_address` VALUES ('1133', '和县', '341424', '340500', '238200', 'H', '3');
INSERT INTO `system_address` VALUES ('1134', '淮北市', '340600', '340000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1135', '杜集区', '340602', '340600', '235000', 'D', '3');
INSERT INTO `system_address` VALUES ('1136', '相山区', '340603', '340600', '235000', 'X', '3');
INSERT INTO `system_address` VALUES ('1137', '烈山区', '340604', '340600', '235000', 'L', '3');
INSERT INTO `system_address` VALUES ('1138', '濉溪县', '340621', '340600', '235100', 'S', '3');
INSERT INTO `system_address` VALUES ('1139', '铜陵市', '340700', '340000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('1140', '铜官山区', '340702', '340700', '244000', 'T', '3');
INSERT INTO `system_address` VALUES ('1141', '狮子山区', '340703', '340700', '244000', 'S', '3');
INSERT INTO `system_address` VALUES ('1142', '郊区', '340711', '340700', '244000', 'J', '3');
INSERT INTO `system_address` VALUES ('1143', '义安区', '340721', '340700', '244100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1144', '枞阳县', '340823', '340700', '246700', 'Z', '3');
INSERT INTO `system_address` VALUES ('1145', '安庆市', '340800', '340000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('1146', '迎江区', '340802', '340800', '246000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1147', '大观区', '340803', '340800', '246000', 'D', '3');
INSERT INTO `system_address` VALUES ('1148', '宜秀区', '340811', '340800', '246003', 'Y', '3');
INSERT INTO `system_address` VALUES ('1149', '怀宁县', '340822', '340800', '246100', 'H', '3');
INSERT INTO `system_address` VALUES ('1150', '枞阳县', '340823', '340800', '246700', 'Z', '3');
INSERT INTO `system_address` VALUES ('1151', '潜山县', '340824', '340800', '246300', 'Q', '3');
INSERT INTO `system_address` VALUES ('1152', '太湖县', '340825', '340800', '246400', 'T', '3');
INSERT INTO `system_address` VALUES ('1153', '宿松县', '340826', '340800', '246500', 'S', '3');
INSERT INTO `system_address` VALUES ('1154', '望江县', '340827', '340800', '246200', 'W', '3');
INSERT INTO `system_address` VALUES ('1155', '岳西县', '340828', '340800', '246600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1156', '桐城市', '340881', '340800', '231400', 'T', '3');
INSERT INTO `system_address` VALUES ('1157', '黄山市', '341000', '340000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1158', '屯溪区', '341002', '341000', '245000', 'T', '3');
INSERT INTO `system_address` VALUES ('1159', '黄山区', '341003', '341000', '242700', 'H', '3');
INSERT INTO `system_address` VALUES ('1160', '徽州区', '341004', '341000', '245061', 'H', '3');
INSERT INTO `system_address` VALUES ('1161', '歙县', '341021', '341000', '245200', 'S', '3');
INSERT INTO `system_address` VALUES ('1162', '休宁县', '341022', '341000', '245400', 'X', '3');
INSERT INTO `system_address` VALUES ('1163', '黟县', '341023', '341000', '245500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1164', '祁门县', '341024', '341000', '245600', 'Q', '3');
INSERT INTO `system_address` VALUES ('1165', '滁州市', '341100', '340000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('1166', '琅琊区', '341102', '341100', '239000', 'L', '3');
INSERT INTO `system_address` VALUES ('1167', '南谯区', '341103', '341100', '239000', 'N', '3');
INSERT INTO `system_address` VALUES ('1168', '来安县', '341122', '341100', '239200', 'L', '3');
INSERT INTO `system_address` VALUES ('1169', '全椒县', '341124', '341100', '239500', 'Q', '3');
INSERT INTO `system_address` VALUES ('1170', '定远县', '341125', '341100', '233200', 'D', '3');
INSERT INTO `system_address` VALUES ('1171', '凤阳县', '341126', '341100', '233100', 'F', '3');
INSERT INTO `system_address` VALUES ('1172', '天长市', '341181', '341100', '239300', 'T', '3');
INSERT INTO `system_address` VALUES ('1173', '明光市', '341182', '341100', '239400', 'M', '3');
INSERT INTO `system_address` VALUES ('1174', '阜阳市', '341200', '340000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('1175', '颍州区', '341202', '341200', '236000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1176', '颍东区', '341203', '341200', '236000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1177', '颍泉区', '341204', '341200', '236000	', 'Y', '3');
INSERT INTO `system_address` VALUES ('1178', '临泉县', '341221', '341200', '236400', 'L', '3');
INSERT INTO `system_address` VALUES ('1179', '太和县', '341222', '341200', '236600', 'T', '3');
INSERT INTO `system_address` VALUES ('1180', '阜南县', '341225', '341200', '236300', 'F', '3');
INSERT INTO `system_address` VALUES ('1181', '颍上县', '341226', '341200', '236200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1182', '界首市', '341282', '341200', '236500', 'J', '3');
INSERT INTO `system_address` VALUES ('1183', '宿州市', '341300', '340000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1184', '埇桥区', '341302', '341300', '234000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1185', '砀山县', '341321', '341300', '235300', 'D', '3');
INSERT INTO `system_address` VALUES ('1186', '萧县', '341322', '341300', '235200', 'X', '3');
INSERT INTO `system_address` VALUES ('1187', '灵璧县', '341323', '341300', '234200', 'L', '3');
INSERT INTO `system_address` VALUES ('1188', '泗县', '341324', '341300', '234300', 'S', '3');
INSERT INTO `system_address` VALUES ('1189', '六安市', '341500', '340000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1190', '金安区', '341502', '341500', '237000', 'J', '3');
INSERT INTO `system_address` VALUES ('1191', '裕安区', '341503', '341500', '237000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1192', '霍邱县', '341522', '341500', '237400', 'H', '3');
INSERT INTO `system_address` VALUES ('1193', '舒城县', '341523', '341500', '231300', 'S', '3');
INSERT INTO `system_address` VALUES ('1194', '金寨县', '341524', '341500', '237300', 'J', '3');
INSERT INTO `system_address` VALUES ('1195', '霍山县', '341525', '341500', '237200', 'H', '3');
INSERT INTO `system_address` VALUES ('1196', '叶集区', '341526', '341500', '237431', 'Y', '3');
INSERT INTO `system_address` VALUES ('1197', '亳州市', '341600', '340000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('1198', '谯城区', '341602', '341600', '236800', 'Q', '3');
INSERT INTO `system_address` VALUES ('1199', '涡阳县', '341621', '341600', '233600', 'W', '3');
INSERT INTO `system_address` VALUES ('1200', '蒙城县', '341622', '341600', '233500', 'M', '3');
INSERT INTO `system_address` VALUES ('1201', '利辛县', '341623', '341600', '236700', 'L', '3');
INSERT INTO `system_address` VALUES ('1202', '池州市', '341700', '340000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('1203', '贵池区', '341702', '341700', '247100', 'G', '3');
INSERT INTO `system_address` VALUES ('1204', '东至县', '341721', '341700', '247200', 'D', '3');
INSERT INTO `system_address` VALUES ('1205', '石台县', '341722', '341700', '245100', 'S', '3');
INSERT INTO `system_address` VALUES ('1206', '青阳县', '341723', '341700', '242800', 'Q', '3');
INSERT INTO `system_address` VALUES ('1207', '宣城市', '341800', '340000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1208', '宣州区', '341802', '341800', '242000', 'X', '3');
INSERT INTO `system_address` VALUES ('1209', '郎溪县', '341821', '341800', '242100', 'L', '3');
INSERT INTO `system_address` VALUES ('1210', '广德县', '341822', '341800', '242200', 'G', '3');
INSERT INTO `system_address` VALUES ('1211', '泾县', '341823', '341800', '242500', 'J', '3');
INSERT INTO `system_address` VALUES ('1212', '绩溪县', '341824', '341800', '245300', 'J', '3');
INSERT INTO `system_address` VALUES ('1213', '旌德县', '341825', '341800', '242600', 'J', '3');
INSERT INTO `system_address` VALUES ('1214', '宁国市', '341881', '341800', '242300', 'N', '3');
INSERT INTO `system_address` VALUES ('1215', '福建', '350000', '0', null, 'F', '1');
INSERT INTO `system_address` VALUES ('1216', '福州市', '350100', '350000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('1217', '鼓楼区', '350102', '350100', '350000', 'G', '3');
INSERT INTO `system_address` VALUES ('1218', '台江区', '350103', '350100', '350000', 'T', '3');
INSERT INTO `system_address` VALUES ('1219', '仓山区', '350104', '350100', '350000', 'C', '3');
INSERT INTO `system_address` VALUES ('1220', '马尾区', '350105', '350100', '350000', 'M', '3');
INSERT INTO `system_address` VALUES ('1221', '晋安区', '350111', '350100', '350000', 'J', '3');
INSERT INTO `system_address` VALUES ('1222', '闽侯县', '350121', '350100', '350100', 'M', '3');
INSERT INTO `system_address` VALUES ('1223', '连江县', '350122', '350100', '350500', 'L', '3');
INSERT INTO `system_address` VALUES ('1224', '罗源县', '350123', '350100', '350600', 'L', '3');
INSERT INTO `system_address` VALUES ('1225', '闽清县', '350124', '350100', '350800', 'M', '3');
INSERT INTO `system_address` VALUES ('1226', '永泰县', '350125', '350100', '350700', 'Y', '3');
INSERT INTO `system_address` VALUES ('1227', '平潭县', '350128', '350100', '350400', 'P', '3');
INSERT INTO `system_address` VALUES ('1228', '福清市', '350181', '350100', '350300', 'F', '3');
INSERT INTO `system_address` VALUES ('1229', '长乐市', '350182', '350100', '350200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1230', '厦门市', '350200', '350000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1231', '思明区', '350203', '350200', '361001', 'S', '3');
INSERT INTO `system_address` VALUES ('1232', '海沧区', '350205', '350200', '361000', 'H', '3');
INSERT INTO `system_address` VALUES ('1233', '湖里区', '350206', '350200', '361000', 'H', '3');
INSERT INTO `system_address` VALUES ('1234', '集美区', '350211', '350200', '361000', 'J', '3');
INSERT INTO `system_address` VALUES ('1235', '同安区', '350212', '350200', '361100', 'T', '3');
INSERT INTO `system_address` VALUES ('1236', '翔安区', '350213', '350200', '361100', 'X', '3');
INSERT INTO `system_address` VALUES ('1237', '莆田市', '350300', '350000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('1238', '城厢区', '350302', '350300', '351100', 'C', '3');
INSERT INTO `system_address` VALUES ('1239', '涵江区', '350303', '350300', '351100', 'H', '3');
INSERT INTO `system_address` VALUES ('1240', '荔城区', '350304', '350300', '351100', 'L', '3');
INSERT INTO `system_address` VALUES ('1241', '秀屿区', '350305', '350300', '351100', 'X', '3');
INSERT INTO `system_address` VALUES ('1242', '仙游县', '350322', '350300', '351200', 'X', '3');
INSERT INTO `system_address` VALUES ('1243', '三明市', '350400', '350000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1244', '梅列区', '350402', '350400', '365000', 'M', '3');
INSERT INTO `system_address` VALUES ('1245', '三元区', '350403', '350400', '365000', 'S', '3');
INSERT INTO `system_address` VALUES ('1246', '明溪县', '350421', '350400', '365200', 'M', '3');
INSERT INTO `system_address` VALUES ('1247', '清流县', '350423', '350400', '365300', 'Q', '3');
INSERT INTO `system_address` VALUES ('1248', '宁化县', '350424', '350400', '365400', 'N', '3');
INSERT INTO `system_address` VALUES ('1249', '大田县', '350425', '350400', '366100', 'D', '3');
INSERT INTO `system_address` VALUES ('1250', '尤溪县', '350426', '350400', '365100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1251', '沙县', '350427', '350400', '365500', 'S', '3');
INSERT INTO `system_address` VALUES ('1252', '将乐县', '350428', '350400', '353300', 'J', '3');
INSERT INTO `system_address` VALUES ('1253', '泰宁县', '350429', '350400', '354400', 'T', '3');
INSERT INTO `system_address` VALUES ('1254', '建宁县', '350430', '350400', '354500', 'J', '3');
INSERT INTO `system_address` VALUES ('1255', '永安市', '350481', '350400', '366000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1256', '泉州市', '350500', '350000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('1257', '鲤城区', '350502', '350500', '362000', 'L', '3');
INSERT INTO `system_address` VALUES ('1258', '丰泽区', '350503', '350500', '362000', 'F', '3');
INSERT INTO `system_address` VALUES ('1259', '洛江区', '350504', '350500', '362000', 'L', '3');
INSERT INTO `system_address` VALUES ('1260', '泉港区', '350505', '350500', '362800', 'Q', '3');
INSERT INTO `system_address` VALUES ('1261', '惠安县', '350521', '350500', '362100', 'H', '3');
INSERT INTO `system_address` VALUES ('1262', '安溪县', '350524', '350500', '362400', 'A', '3');
INSERT INTO `system_address` VALUES ('1263', '永春县', '350525', '350500', '362600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1264', '德化县', '350526', '350500', '362500', 'D', '3');
INSERT INTO `system_address` VALUES ('1265', '金门县', '350527', '350500', '362000', 'J', '3');
INSERT INTO `system_address` VALUES ('1266', '石狮市', '350581', '350500', '362700', 'S', '3');
INSERT INTO `system_address` VALUES ('1267', '晋江市', '350582', '350500', '362200', 'J', '3');
INSERT INTO `system_address` VALUES ('1268', '南安市', '350583', '350500', '362300', 'N', '3');
INSERT INTO `system_address` VALUES ('1269', '漳州市', '350600', '350000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1270', '芗城区', '350602', '350600', '363000', 'X', '3');
INSERT INTO `system_address` VALUES ('1271', '龙文区', '350603', '350600', '363000', 'L', '3');
INSERT INTO `system_address` VALUES ('1272', '云霄县', '350622', '350600', '363300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1273', '漳浦县', '350623', '350600', '363200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1274', '诏安县', '350624', '350600', '363500', 'Z', '3');
INSERT INTO `system_address` VALUES ('1275', '长泰县', '350625', '350600', '363900', 'Z', '3');
INSERT INTO `system_address` VALUES ('1276', '东山县', '350626', '350600', '363400', 'D', '3');
INSERT INTO `system_address` VALUES ('1277', '南靖县', '350627', '350600', '363600', 'N', '3');
INSERT INTO `system_address` VALUES ('1278', '平和县', '350628', '350600', '363700', 'P', '3');
INSERT INTO `system_address` VALUES ('1279', '华安县', '350629', '350600', '363800', 'H', '3');
INSERT INTO `system_address` VALUES ('1280', '龙海市', '350681', '350600', '363100', 'L', '3');
INSERT INTO `system_address` VALUES ('1281', '南平市', '350700', '350000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('1282', '延平区', '350702', '350700', '353000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1283', '顺昌县', '350721', '350700', '353200', 'S', '3');
INSERT INTO `system_address` VALUES ('1284', '浦城县', '350722', '350700', '353400', 'P', '3');
INSERT INTO `system_address` VALUES ('1285', '光泽县', '350723', '350700', '354100', 'G', '3');
INSERT INTO `system_address` VALUES ('1286', '松溪县', '350724', '350700', '353500', 'S', '3');
INSERT INTO `system_address` VALUES ('1287', '政和县', '350725', '350700', '353600', 'Z', '3');
INSERT INTO `system_address` VALUES ('1288', '邵武市', '350781', '350700', '354000', 'S', '3');
INSERT INTO `system_address` VALUES ('1289', '武夷山市', '350782', '350700', '354300', 'W', '3');
INSERT INTO `system_address` VALUES ('1290', '建瓯市', '350783', '350700', '353100', 'J', '3');
INSERT INTO `system_address` VALUES ('1291', '建阳区', '350784', '350700', '354200	', 'J', '3');
INSERT INTO `system_address` VALUES ('1292', '龙岩市', '350800', '350000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1293', '新罗区', '350802', '350800', '364000', 'X', '3');
INSERT INTO `system_address` VALUES ('1294', '长汀县', '350821', '350800', '366300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1295', '永定区', '350822', '350800', '364100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1296', '上杭县', '350823', '350800', '364200', 'S', '3');
INSERT INTO `system_address` VALUES ('1297', '武平县', '350824', '350800', '364300', 'W', '3');
INSERT INTO `system_address` VALUES ('1298', '连城县', '350825', '350800', '366200', 'L', '3');
INSERT INTO `system_address` VALUES ('1299', '漳平市', '350881', '350800', '364400', 'Z', '3');
INSERT INTO `system_address` VALUES ('1300', '宁德市', '350900', '350000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('1301', '蕉城区', '350902', '350900', '352000', 'J', '3');
INSERT INTO `system_address` VALUES ('1302', '霞浦县', '350921', '350900', '355100', 'X', '3');
INSERT INTO `system_address` VALUES ('1303', '古田县', '350922', '350900', '352200', 'G', '3');
INSERT INTO `system_address` VALUES ('1304', '屏南县', '350923', '350900', '352300', 'P', '3');
INSERT INTO `system_address` VALUES ('1305', '寿宁县', '350924', '350900', '355500', 'S', '3');
INSERT INTO `system_address` VALUES ('1306', '周宁县', '350925', '350900', '355400', 'Z', '3');
INSERT INTO `system_address` VALUES ('1307', '柘荣县', '350926', '350900', '355300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1308', '福安市', '350981', '350900', '355000', 'F', '3');
INSERT INTO `system_address` VALUES ('1309', '福鼎市', '350982', '350900', '355200', 'F', '3');
INSERT INTO `system_address` VALUES ('1310', '江西', '360000', '0', null, 'J', '1');
INSERT INTO `system_address` VALUES ('1311', '南昌市', '360100', '360000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('1312', '东湖区', '360102', '360100', '330000', 'D', '3');
INSERT INTO `system_address` VALUES ('1313', '西湖区', '360103', '360100', '330000', 'X', '3');
INSERT INTO `system_address` VALUES ('1314', '青云谱区', '360104', '360100', '330000', 'Q', '3');
INSERT INTO `system_address` VALUES ('1315', '湾里区', '360105', '360100', '330000', 'W', '3');
INSERT INTO `system_address` VALUES ('1316', '青山湖区', '360111', '360100', '330000', 'Q', '3');
INSERT INTO `system_address` VALUES ('1317', '南昌县', '360121', '360100', '330200', 'N', '3');
INSERT INTO `system_address` VALUES ('1318', '新建县', '360122', '360100', '330100', 'X', '3');
INSERT INTO `system_address` VALUES ('1319', '安义县', '360123', '360100', '330500', 'A', '3');
INSERT INTO `system_address` VALUES ('1320', '进贤县', '360124', '360100', '331700', 'J', '3');
INSERT INTO `system_address` VALUES ('1321', '红谷滩新区', '360125', '360100', '330000', 'H', '3');
INSERT INTO `system_address` VALUES ('1322', '经济技术开发区', '360126', '360100', '330000', 'J', '3');
INSERT INTO `system_address` VALUES ('1323', '昌北区', '360127', '360100', '330000', 'C', '3');
INSERT INTO `system_address` VALUES ('1324', '景德镇市', '360200', '360000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1325', '昌江区', '360202', '360200', '333000', 'C', '3');
INSERT INTO `system_address` VALUES ('1326', '珠山区', '360203', '360200', '333000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1327', '浮梁县', '360222', '360200', '333400', 'F', '3');
INSERT INTO `system_address` VALUES ('1328', '乐平市', '360281', '360200', '333300', 'L', '3');
INSERT INTO `system_address` VALUES ('1329', '萍乡市', '360300', '360000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('1330', '安源区', '360302', '360300', '337000', 'A', '3');
INSERT INTO `system_address` VALUES ('1331', '湘东区', '360313', '360300', '337000', 'X', '3');
INSERT INTO `system_address` VALUES ('1332', '莲花县', '360321', '360300', '337100', 'L', '3');
INSERT INTO `system_address` VALUES ('1333', '上栗县', '360322', '360300', '337009', 'S', '3');
INSERT INTO `system_address` VALUES ('1334', '芦溪县', '360323', '360300', '337000', 'L', '3');
INSERT INTO `system_address` VALUES ('1335', '九江市', '360400', '360000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1336', '庐山区', '360402', '360400', '332900', 'L', '3');
INSERT INTO `system_address` VALUES ('1337', '浔阳区', '360403', '360400', '332000', 'X', '3');
INSERT INTO `system_address` VALUES ('1338', '九江县', '360421', '360400', '332100', 'J', '3');
INSERT INTO `system_address` VALUES ('1339', '武宁县', '360423', '360400', '332300', 'W', '3');
INSERT INTO `system_address` VALUES ('1340', '修水县', '360424', '360400', '332400', 'X', '3');
INSERT INTO `system_address` VALUES ('1341', '永修县', '360425', '360400', '330300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1342', '德安县', '360426', '360400', '330400', 'D', '3');
INSERT INTO `system_address` VALUES ('1343', '星子县', '360427', '360400', '332800', 'X', '3');
INSERT INTO `system_address` VALUES ('1344', '都昌县', '360428', '360400', '332600', 'D', '3');
INSERT INTO `system_address` VALUES ('1345', '湖口县', '360429', '360400', '332500', 'H', '3');
INSERT INTO `system_address` VALUES ('1346', '彭泽县', '360430', '360400', '332700', 'P', '3');
INSERT INTO `system_address` VALUES ('1347', '瑞昌市', '360481', '360400', '332200', 'R', '3');
INSERT INTO `system_address` VALUES ('1348', '共青城市', '360483', '360400', '332020', 'G', '3');
INSERT INTO `system_address` VALUES ('1349', '新余市', '360500', '360000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1350', '渝水区', '360502', '360500', '338000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1351', '分宜县', '360521', '360500', '336600', 'F', '3');
INSERT INTO `system_address` VALUES ('1352', '鹰潭市', '360600', '360000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1353', '月湖区', '360602', '360600', '335000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1354', '余江县', '360622', '360600', '335200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1355', '贵溪市', '360681', '360600', '335400', 'G', '3');
INSERT INTO `system_address` VALUES ('1356', '赣州市', '360700', '360000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('1357', '章贡区', '360702', '360700', '341000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1358', '信丰县', '360704', '360700', '341600', 'X', '3');
INSERT INTO `system_address` VALUES ('1359', '赣县区', '360722', '360700', '341100', 'G', '3');
INSERT INTO `system_address` VALUES ('1360', '大余县', '360723', '360700', '341500', 'D', '3');
INSERT INTO `system_address` VALUES ('1361', '上犹县', '360724', '360700', '341200', 'S', '3');
INSERT INTO `system_address` VALUES ('1362', '崇义县', '360725', '360700', '341300', 'C', '3');
INSERT INTO `system_address` VALUES ('1363', '安远县', '360726', '360700', '342100', 'A', '3');
INSERT INTO `system_address` VALUES ('1364', '龙南县', '360727', '360700', '341700', 'L', '3');
INSERT INTO `system_address` VALUES ('1365', '定南县', '360728', '360700', '341900', 'D', '3');
INSERT INTO `system_address` VALUES ('1366', '全南县', '360729', '360700', '341800', 'Q', '3');
INSERT INTO `system_address` VALUES ('1367', '宁都县', '360730', '360700', '342800', 'N', '3');
INSERT INTO `system_address` VALUES ('1368', '于都县', '360731', '360700', '342300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1369', '兴国县', '360732', '360700', '342400', 'X', '3');
INSERT INTO `system_address` VALUES ('1370', '会昌县', '360733', '360700', '342600', 'H', '3');
INSERT INTO `system_address` VALUES ('1371', '寻乌县', '360734', '360700', '342200', 'X', '3');
INSERT INTO `system_address` VALUES ('1372', '石城县', '360735', '360700', '342700', 'S', '3');
INSERT INTO `system_address` VALUES ('1373', '黄金区', '360751', '360700', '341000', 'H', '3');
INSERT INTO `system_address` VALUES ('1374', '瑞金市', '360781', '360700', '342500', 'R', '3');
INSERT INTO `system_address` VALUES ('1375', '南康区', '360782', '360700', '341400', 'N', '3');
INSERT INTO `system_address` VALUES ('1376', '吉安市', '360800', '360000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1377', '吉州区', '360802', '360800', '343000', 'J', '3');
INSERT INTO `system_address` VALUES ('1378', '青原区', '360803', '360800', '343000', 'Q', '3');
INSERT INTO `system_address` VALUES ('1379', '吉安县', '360821', '360800', '343100', 'J', '3');
INSERT INTO `system_address` VALUES ('1380', '吉水县', '360822', '360800', '331600', 'J', '3');
INSERT INTO `system_address` VALUES ('1381', '峡江县', '360823', '360800', '331400', 'X', '3');
INSERT INTO `system_address` VALUES ('1382', '新干县', '360824', '360800', '331300', 'X', '3');
INSERT INTO `system_address` VALUES ('1383', '永丰县', '360825', '360800', '331500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1384', '泰和县', '360826', '360800', '343700', 'T', '3');
INSERT INTO `system_address` VALUES ('1385', '遂川县', '360827', '360800', '343900', 'S', '3');
INSERT INTO `system_address` VALUES ('1386', '万安县', '360828', '360800', '343800', 'W', '3');
INSERT INTO `system_address` VALUES ('1387', '安福县', '360829', '360800', '343200', 'A', '3');
INSERT INTO `system_address` VALUES ('1388', '永新县', '360830', '360800', '343400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1389', '井冈山市', '360881', '360800', '343600', 'J', '3');
INSERT INTO `system_address` VALUES ('1390', '宜春市', '360900', '360000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1391', '袁州区', '360902', '360900', '336000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1392', '奉新县', '360921', '360900', '330700', 'F', '3');
INSERT INTO `system_address` VALUES ('1393', '万载县', '360922', '360900', '336100', 'W', '3');
INSERT INTO `system_address` VALUES ('1394', '上高县', '360923', '360900', '336400', 'S', '3');
INSERT INTO `system_address` VALUES ('1395', '宜丰县', '360924', '360900', '336300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1396', '靖安县', '360925', '360900', '330600', 'J', '3');
INSERT INTO `system_address` VALUES ('1397', '铜鼓县', '360926', '360900', '336200', 'T', '3');
INSERT INTO `system_address` VALUES ('1398', '丰城市', '360981', '360900', '331100', 'F', '3');
INSERT INTO `system_address` VALUES ('1399', '樟树市', '360982', '360900', '331200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1400', '高安市', '360983', '360900', '330800', 'G', '3');
INSERT INTO `system_address` VALUES ('1401', '抚州市', '361000', '360000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('1402', '临川区', '361002', '361000', '344100', 'L', '3');
INSERT INTO `system_address` VALUES ('1403', '南城县', '361021', '361000', '344700', 'N', '3');
INSERT INTO `system_address` VALUES ('1404', '黎川县', '361022', '361000', '344600', 'L', '3');
INSERT INTO `system_address` VALUES ('1405', '南丰县', '361023', '361000', '344500', 'N', '3');
INSERT INTO `system_address` VALUES ('1406', '崇仁县', '361024', '361000', '344200', 'C', '3');
INSERT INTO `system_address` VALUES ('1407', '乐安县', '361025', '361000', '344300', 'L', '3');
INSERT INTO `system_address` VALUES ('1408', '宜黄县', '361026', '361000', '344400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1409', '金溪县', '361027', '361000', '344800', 'J', '3');
INSERT INTO `system_address` VALUES ('1410', '资溪县', '361028', '361000', '335300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1411', '东乡县', '361029', '361000', '331800', 'D', '3');
INSERT INTO `system_address` VALUES ('1412', '广昌县', '361030', '361000', '344900', 'G', '3');
INSERT INTO `system_address` VALUES ('1413', '上饶市', '361100', '360000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1414', '信州区', '361102', '361100', '334000', 'X', '3');
INSERT INTO `system_address` VALUES ('1415', '上饶县', '361121', '361100', '334100', 'S', '3');
INSERT INTO `system_address` VALUES ('1416', '广丰县', '361122', '361100', '334600', 'G', '3');
INSERT INTO `system_address` VALUES ('1417', '玉山县', '361123', '361100', '334700', 'Y', '3');
INSERT INTO `system_address` VALUES ('1418', '铅山县', '361124', '361100', '334500', 'Q', '3');
INSERT INTO `system_address` VALUES ('1419', '横峰县', '361125', '361100', '334300', 'H', '3');
INSERT INTO `system_address` VALUES ('1420', '弋阳县', '361126', '361100', '334400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1421', '余干县', '361127', '361100', '335100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1422', '鄱阳县', '361128', '361100', '333100', 'P', '3');
INSERT INTO `system_address` VALUES ('1423', '万年县', '361129', '361100', '335500', 'W', '3');
INSERT INTO `system_address` VALUES ('1424', '婺源县', '361130', '361100', '333200', 'W', '3');
INSERT INTO `system_address` VALUES ('1425', '德兴市', '361181', '361100', '334200', 'D', '3');
INSERT INTO `system_address` VALUES ('1426', '山东', '370000', '0', null, 'S', '1');
INSERT INTO `system_address` VALUES ('1427', '济南市', '370100', '370000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1428', '高新区', '250101', '370100', '250000', 'G', '3');
INSERT INTO `system_address` VALUES ('1429', '历下区', '370102', '370100', '250000', 'L', '3');
INSERT INTO `system_address` VALUES ('1430', '市中区', '370103', '370100', '250000', 'S', '3');
INSERT INTO `system_address` VALUES ('1431', '槐荫区', '370104', '370100', '250000', 'H', '3');
INSERT INTO `system_address` VALUES ('1432', '天桥区', '370105', '370100', '250000', 'T', '3');
INSERT INTO `system_address` VALUES ('1433', '历城区', '370112', '370100', '250100', 'L', '3');
INSERT INTO `system_address` VALUES ('1434', '长清区', '370113', '370100', '250300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1435', '平阴县', '370124', '370100', '250400', 'P', '3');
INSERT INTO `system_address` VALUES ('1436', '济阳县', '370125', '370100', '251400', 'J', '3');
INSERT INTO `system_address` VALUES ('1437', '商河县', '370126', '370100', '251600', 'S', '3');
INSERT INTO `system_address` VALUES ('1438', '章丘市', '370181', '370100', '250200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1439', '青岛市', '370200', '370000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('1440', '市南区', '370202', '370200', '266000', 'S', '3');
INSERT INTO `system_address` VALUES ('1441', '市北区', '370203', '370200', '266000', 'S', '3');
INSERT INTO `system_address` VALUES ('1442', '西海岸新区', '370204', '370200', '266000', 'X', '3');
INSERT INTO `system_address` VALUES ('1443', '四方区', '370205', '370200', '266000', 'S', '3');
INSERT INTO `system_address` VALUES ('1444', '黄岛区', '370211', '370200', '266000', 'H', '3');
INSERT INTO `system_address` VALUES ('1445', '崂山区', '370212', '370200', '266100', 'L', '3');
INSERT INTO `system_address` VALUES ('1446', '李沧区', '370213', '370200', '266000', 'L', '3');
INSERT INTO `system_address` VALUES ('1447', '城阳区', '370214', '370200', '266000', 'C', '3');
INSERT INTO `system_address` VALUES ('1448', '开发区', '370251', '370200', '266000', 'K', '3');
INSERT INTO `system_address` VALUES ('1449', '胶州市', '370281', '370200', '266300', 'J', '3');
INSERT INTO `system_address` VALUES ('1450', '即墨市', '370282', '370200', '266200', 'J', '3');
INSERT INTO `system_address` VALUES ('1451', '平度市', '370283', '370200', '266700', 'P', '3');
INSERT INTO `system_address` VALUES ('1452', '胶南市', '370284', '370200', '266400', 'J', '3');
INSERT INTO `system_address` VALUES ('1453', '莱西市', '370285', '370200', '266600', 'L', '3');
INSERT INTO `system_address` VALUES ('1454', '淄博市', '370300', '370000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1455', '淄川区', '370302', '370300', '255100', 'Z', '3');
INSERT INTO `system_address` VALUES ('1456', '张店区', '370303', '370300', '255000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1457', '博山区', '370304', '370300', '255200', 'B', '3');
INSERT INTO `system_address` VALUES ('1458', '临淄区', '370305', '370300', '255400', 'L', '3');
INSERT INTO `system_address` VALUES ('1459', '周村区', '370306', '370300', '255300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1460', '桓台县', '370321', '370300', '256400', 'H', '3');
INSERT INTO `system_address` VALUES ('1461', '高青县', '370322', '370300', '256300', 'G', '3');
INSERT INTO `system_address` VALUES ('1462', '沂源县', '370323', '370300', '256100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1463', '枣庄市', '370400', '370000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1464', '市中区', '370402', '370400', '277000', 'S', '3');
INSERT INTO `system_address` VALUES ('1465', '薛城区', '370403', '370400', '277000', 'X', '3');
INSERT INTO `system_address` VALUES ('1466', '峄城区', '370404', '370400', '277300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1467', '台儿庄区', '370405', '370400', '277400', 'T', '3');
INSERT INTO `system_address` VALUES ('1468', '山亭区', '370406', '370400', '277200', 'S', '3');
INSERT INTO `system_address` VALUES ('1469', '滕州市', '370481', '370400', '277500', 'T', '3');
INSERT INTO `system_address` VALUES ('1470', '东营市', '370500', '370000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('1471', '东营区', '370502', '370500', '257100', 'D', '3');
INSERT INTO `system_address` VALUES ('1472', '河口区', '370503', '370500', '257200', 'H', '3');
INSERT INTO `system_address` VALUES ('1473', '垦利县', '370521', '370500', '257500', 'K', '3');
INSERT INTO `system_address` VALUES ('1474', '利津县', '370522', '370500', '257400', 'L', '3');
INSERT INTO `system_address` VALUES ('1475', '广饶县', '370523', '370500', '257300', 'G', '3');
INSERT INTO `system_address` VALUES ('1476', '西城区', '370589', '370500', '257000', 'X', '3');
INSERT INTO `system_address` VALUES ('1477', '东城区', '370590', '370500', '257000', 'D', '3');
INSERT INTO `system_address` VALUES ('1478', '烟台市', '370600', '370000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1479', '芝罘区', '370602', '370600', '264000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1480', '福山区', '370611', '370600', '265500', 'F', '3');
INSERT INTO `system_address` VALUES ('1481', '牟平区', '370612', '370600', '264100', 'M', '3');
INSERT INTO `system_address` VALUES ('1482', '莱山区', '370613', '370600', '264000', 'L', '3');
INSERT INTO `system_address` VALUES ('1483', '长岛县', '370634', '370600', '265800', 'Z', '3');
INSERT INTO `system_address` VALUES ('1484', '龙口市', '370681', '370600', '265700', 'L', '3');
INSERT INTO `system_address` VALUES ('1485', '莱阳市', '370682', '370600', '265200', 'L', '3');
INSERT INTO `system_address` VALUES ('1486', '莱州市', '370683', '370600', '261400', 'L', '3');
INSERT INTO `system_address` VALUES ('1487', '蓬莱市', '370684', '370600', '265600', 'P', '3');
INSERT INTO `system_address` VALUES ('1488', '招远市', '370685', '370600', '265400', 'Z', '3');
INSERT INTO `system_address` VALUES ('1489', '栖霞市', '370686', '370600', '265300', 'Q', '3');
INSERT INTO `system_address` VALUES ('1490', '海阳市', '370687', '370600', '265100', 'H', '3');
INSERT INTO `system_address` VALUES ('1491', '潍坊市', '370700', '370000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('1492', '潍城区', '370702', '370700', '261000', 'W', '3');
INSERT INTO `system_address` VALUES ('1493', '寒亭区', '370703', '370700', '261100', 'H', '3');
INSERT INTO `system_address` VALUES ('1494', '坊子区', '370704', '370700', '261200', 'F', '3');
INSERT INTO `system_address` VALUES ('1495', '奎文区', '370705', '370700', '261000', 'K', '3');
INSERT INTO `system_address` VALUES ('1496', '滨海经济技术开发区', '370706', '370700', '261000', 'B', '3');
INSERT INTO `system_address` VALUES ('1497', '临朐县', '370724', '370700', '262600', 'L', '3');
INSERT INTO `system_address` VALUES ('1498', '昌乐县', '370725', '370700', '262400', 'C', '3');
INSERT INTO `system_address` VALUES ('1499', '开发区', '370751', '370700', '261000', 'K', '3');
INSERT INTO `system_address` VALUES ('1500', '青州市', '370781', '370700', '262500', 'Q', '3');
INSERT INTO `system_address` VALUES ('1501', '诸城市', '370782', '370700', '262200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1502', '寿光市', '370783', '370700', '262700', 'S', '3');
INSERT INTO `system_address` VALUES ('1503', '安丘市', '370784', '370700', '262100', 'A', '3');
INSERT INTO `system_address` VALUES ('1504', '高密市', '370785', '370700', '261500', 'G', '3');
INSERT INTO `system_address` VALUES ('1505', '昌邑市', '370786', '370700', '261300', 'C', '3');
INSERT INTO `system_address` VALUES ('1506', '济宁市', '370800', '370000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1507', '市中区', '370802', '370800', '272000', 'S', '3');
INSERT INTO `system_address` VALUES ('1508', '任城区', '370811', '370800', '272000', 'R', '3');
INSERT INTO `system_address` VALUES ('1509', '微山县', '370826', '370800', '277600', 'W', '3');
INSERT INTO `system_address` VALUES ('1510', '鱼台县', '370827', '370800', '272300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1511', '金乡县', '370828', '370800', '272200', 'J', '3');
INSERT INTO `system_address` VALUES ('1512', '嘉祥县', '370829', '370800', '272400', 'J', '3');
INSERT INTO `system_address` VALUES ('1513', '汶上县', '370830', '370800', '272500', 'W', '3');
INSERT INTO `system_address` VALUES ('1514', '泗水县', '370831', '370800', '273200', 'S', '3');
INSERT INTO `system_address` VALUES ('1515', '梁山县', '370832', '370800', '272600', 'L', '3');
INSERT INTO `system_address` VALUES ('1516', '曲阜市', '370881', '370800', '273100', 'Q', '3');
INSERT INTO `system_address` VALUES ('1517', '兖州区', '370882', '370800', '272100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1518', '邹城市', '370883', '370800', '273500', 'Z', '3');
INSERT INTO `system_address` VALUES ('1519', '泰安市', '370900', '370000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('1520', '泰山区', '370902', '370900', '271000', 'T', '3');
INSERT INTO `system_address` VALUES ('1521', '岱岳区', '370903', '370900', '271000', 'D', '3');
INSERT INTO `system_address` VALUES ('1522', '宁阳县', '370921', '370900', '271400', 'N', '3');
INSERT INTO `system_address` VALUES ('1523', '东平县', '370923', '370900', '271500', 'D', '3');
INSERT INTO `system_address` VALUES ('1524', '新泰市', '370982', '370900', '271200', 'X', '3');
INSERT INTO `system_address` VALUES ('1525', '肥城市', '370983', '370900', '271600', 'F', '3');
INSERT INTO `system_address` VALUES ('1526', '威海市', '371000', '370000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('1527', '环翠区', '371002', '371000', '264200', 'H', '3');
INSERT INTO `system_address` VALUES ('1528', '文登区', '371081', '371000', '264400', 'W', '3');
INSERT INTO `system_address` VALUES ('1529', '荣成市', '371082', '371000', '264300', 'R', '3');
INSERT INTO `system_address` VALUES ('1530', '乳山市', '371083', '371000', '264500', 'R', '3');
INSERT INTO `system_address` VALUES ('1531', '日照市', '371100', '370000', null, 'R', '2');
INSERT INTO `system_address` VALUES ('1532', '东港区', '371102', '371100', '276800', 'D', '3');
INSERT INTO `system_address` VALUES ('1533', '岚山区', '371103', '371100', '276800', 'L', '3');
INSERT INTO `system_address` VALUES ('1534', '五莲县', '371121', '371100', '262300', 'W', '3');
INSERT INTO `system_address` VALUES ('1535', '莒县', '371122', '371100', '276500', 'J', '3');
INSERT INTO `system_address` VALUES ('1536', '莱芜市', '371200', '370000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1537', '莱城区', '371202', '371200', '271100', 'L', '3');
INSERT INTO `system_address` VALUES ('1538', '钢城区', '371203', '371200', '271100', 'G', '3');
INSERT INTO `system_address` VALUES ('1539', '临沂市', '371300', '370000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1540', '兰山区', '371302', '371300', '276000', 'L', '3');
INSERT INTO `system_address` VALUES ('1541', '罗庄区', '371311', '371300', '276000', 'L', '3');
INSERT INTO `system_address` VALUES ('1542', '河东区', '371312', '371300', '276000', 'H', '3');
INSERT INTO `system_address` VALUES ('1543', '沂南县', '371321', '371300', '276300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1544', '郯城县', '371322', '371300', '276100', 'T', '3');
INSERT INTO `system_address` VALUES ('1545', '沂水县', '371323', '371300', '276400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1546', '兰陵县', '371324', '371300', '277700', 'L', '3');
INSERT INTO `system_address` VALUES ('1547', '费县', '371325', '371300', '273400', 'F', '3');
INSERT INTO `system_address` VALUES ('1548', '平邑县', '371326', '371300', '273300', 'P', '3');
INSERT INTO `system_address` VALUES ('1549', '莒南县', '371327', '371300', '276600', 'J', '3');
INSERT INTO `system_address` VALUES ('1550', '蒙阴县', '371328', '371300', '276200', 'M', '3');
INSERT INTO `system_address` VALUES ('1551', '临沭县', '371329', '371300', '276700', 'L', '3');
INSERT INTO `system_address` VALUES ('1552', '德州市', '371400', '370000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('1553', '德城区', '371402', '371400', '253000', 'D', '3');
INSERT INTO `system_address` VALUES ('1554', '陵城区', '371421', '371400', '253500', 'L', '3');
INSERT INTO `system_address` VALUES ('1555', '宁津县', '371422', '371400', '253400', 'N', '3');
INSERT INTO `system_address` VALUES ('1556', '庆云县', '371423', '371400', '253700', 'Q', '3');
INSERT INTO `system_address` VALUES ('1557', '临邑县', '371424', '371400', '251500', 'L', '3');
INSERT INTO `system_address` VALUES ('1558', '齐河县', '371425', '371400', '251100', 'Q', '3');
INSERT INTO `system_address` VALUES ('1559', '平原县', '371426', '371400', '253100', 'P', '3');
INSERT INTO `system_address` VALUES ('1560', '夏津县', '371427', '371400', '253200', 'X', '3');
INSERT INTO `system_address` VALUES ('1561', '武城县', '371428', '371400', '253300', 'W', '3');
INSERT INTO `system_address` VALUES ('1562', '开发区', '371451', '371400', '253000', 'K', '3');
INSERT INTO `system_address` VALUES ('1563', '乐陵市', '371481', '371400', '253600', 'L', '3');
INSERT INTO `system_address` VALUES ('1564', '禹城市', '371482', '371400', '251200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1565', '聊城市', '371500', '370000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1566', '东昌府区', '371502', '371500', '252000', 'D', '3');
INSERT INTO `system_address` VALUES ('1567', '阳谷县', '371521', '371500', '252300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1568', '莘县', '371522', '371500', '252400', 'X', '3');
INSERT INTO `system_address` VALUES ('1569', '茌平县', '371523', '371500', '252100', 'C', '3');
INSERT INTO `system_address` VALUES ('1570', '东阿县', '371524', '371500', '252200', 'D', '3');
INSERT INTO `system_address` VALUES ('1571', '冠县', '371525', '371500', '252500', 'G', '3');
INSERT INTO `system_address` VALUES ('1572', '高唐县', '371526', '371500', '252800', 'G', '3');
INSERT INTO `system_address` VALUES ('1573', '临清市', '371581', '371500', '252600', 'L', '3');
INSERT INTO `system_address` VALUES ('1574', '滨州市', '371600', '370000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('1575', '滨城区', '371602', '371600', '256600', 'B', '3');
INSERT INTO `system_address` VALUES ('1576', '惠民县', '371621', '371600', '251700', 'H', '3');
INSERT INTO `system_address` VALUES ('1577', '阳信县', '371622', '371600', '251800', 'Y', '3');
INSERT INTO `system_address` VALUES ('1578', '无棣县', '371623', '371600', '251900', 'W', '3');
INSERT INTO `system_address` VALUES ('1579', '沾化区', '371624', '371600', '256800', 'Z', '3');
INSERT INTO `system_address` VALUES ('1580', '博兴县', '371625', '371600', '256500', 'B', '3');
INSERT INTO `system_address` VALUES ('1581', '邹平县', '371626', '371600', '256200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1582', '菏泽市', '371700', '370000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1583', '牡丹区', '371702', '371700', '274000', 'M', '3');
INSERT INTO `system_address` VALUES ('1584', '曹县', '371721', '371700', '274400', 'C', '3');
INSERT INTO `system_address` VALUES ('1585', '单县', '371722', '371700', '274300', 'D', '3');
INSERT INTO `system_address` VALUES ('1586', '成武县', '371723', '371700', '274200', 'C', '3');
INSERT INTO `system_address` VALUES ('1587', '巨野县', '371724', '371700', '274900', 'J', '3');
INSERT INTO `system_address` VALUES ('1588', '郓城县', '371725', '371700', '274700', 'Y', '3');
INSERT INTO `system_address` VALUES ('1589', '鄄城县', '371726', '371700', '274600', 'J', '3');
INSERT INTO `system_address` VALUES ('1590', '定陶县', '371727', '371700', '274100', 'D', '3');
INSERT INTO `system_address` VALUES ('1591', '东明县', '371728', '371700', '274500', 'D', '3');
INSERT INTO `system_address` VALUES ('1592', '河南', '410000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('1593', '郑州市', '410100', '410000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1594', '中原区', '410102', '410100', '450000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1595', '二七区', '410103', '410100', '450000', 'E', '3');
INSERT INTO `system_address` VALUES ('1596', '管城回族区', '410104', '410100', '450000', 'G', '3');
INSERT INTO `system_address` VALUES ('1597', '金水区', '410105', '410100', '450000', 'J', '3');
INSERT INTO `system_address` VALUES ('1598', '上街区', '410106', '410100', '450000', 'S', '3');
INSERT INTO `system_address` VALUES ('1599', '惠济区', '410108', '410100', '450000', 'H', '3');
INSERT INTO `system_address` VALUES ('1600', '中牟县', '410122', '410100', '451450', 'Z', '3');
INSERT INTO `system_address` VALUES ('1601', '巩义市', '410181', '410100', '451200', 'G', '3');
INSERT INTO `system_address` VALUES ('1602', '荥阳市', '410182', '410100', '450100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1603', '新密市', '410183', '410100', '452370', 'X', '3');
INSERT INTO `system_address` VALUES ('1604', '新郑市', '410184', '410100', '451100', 'X', '3');
INSERT INTO `system_address` VALUES ('1605', '登封市', '410185', '410100', '452470', 'D', '3');
INSERT INTO `system_address` VALUES ('1606', '郑东新区', '410186', '410100', '450046', 'Z', '3');
INSERT INTO `system_address` VALUES ('1607', '高新区', '410187', '410100', '450000', 'G', '3');
INSERT INTO `system_address` VALUES ('1608', '经济开发区', '410193', '410100', '450000', 'J', '3');
INSERT INTO `system_address` VALUES ('1609', '开封市', '410200', '410000', null, 'K', '2');
INSERT INTO `system_address` VALUES ('1610', '龙亭区', '410202', '410200', '475000', 'L', '3');
INSERT INTO `system_address` VALUES ('1611', '顺河回族区', '410203', '410200', '475000', 'S', '3');
INSERT INTO `system_address` VALUES ('1612', '鼓楼区', '410204', '410200', '475000', 'G', '3');
INSERT INTO `system_address` VALUES ('1613', '禹王台区', '410205', '410200', '475000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1614', '金明区', '410211', '410200', '475000', 'J', '3');
INSERT INTO `system_address` VALUES ('1615', '杞县', '410221', '410200', '475200', 'Q', '3');
INSERT INTO `system_address` VALUES ('1616', '通许县', '410222', '410200', '475400', 'T', '3');
INSERT INTO `system_address` VALUES ('1617', '尉氏县', '410223', '410200', '475500', 'W', '3');
INSERT INTO `system_address` VALUES ('1618', '祥符区', '410224', '410200', '475100', 'X', '3');
INSERT INTO `system_address` VALUES ('1619', '兰考县', '410225', '410200', '475300', 'L', '3');
INSERT INTO `system_address` VALUES ('1620', '洛阳市', '410300', '410000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1621', '老城区', '410302', '410300', '471000', 'L', '3');
INSERT INTO `system_address` VALUES ('1622', '西工区', '410303', '410300', '471000', 'X', '3');
INSERT INTO `system_address` VALUES ('1623', '瀍河回族区', '410304', '410300', '471000', 'C', '3');
INSERT INTO `system_address` VALUES ('1624', '涧西区', '410305', '410300', '471000', 'J', '3');
INSERT INTO `system_address` VALUES ('1625', '吉利区', '410306', '410300', '471000', 'J', '3');
INSERT INTO `system_address` VALUES ('1626', '洛龙区', '410307', '410300', '471000', 'L', '3');
INSERT INTO `system_address` VALUES ('1627', '孟津县', '410322', '410300', '471100', 'M', '3');
INSERT INTO `system_address` VALUES ('1628', '新安县', '410323', '410300', '471800', 'X', '3');
INSERT INTO `system_address` VALUES ('1629', '栾川县', '410324', '410300', '471500', 'L', '3');
INSERT INTO `system_address` VALUES ('1630', '嵩县', '410325', '410300', '471400', 'S', '3');
INSERT INTO `system_address` VALUES ('1631', '汝阳县', '410326', '410300', '471200', 'R', '3');
INSERT INTO `system_address` VALUES ('1632', '宜阳县', '410327', '410300', '471600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1633', '洛宁县', '410328', '410300', '471700', 'L', '3');
INSERT INTO `system_address` VALUES ('1634', '伊川县', '410329', '410300', '471300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1635', '偃师市', '410381', '410300', '471900', 'Y', '3');
INSERT INTO `system_address` VALUES ('1636', '平顶山市', '410400', '410000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('1637', '新华区', '410402', '410400', '467000', 'X', '3');
INSERT INTO `system_address` VALUES ('1638', '卫东区', '410403', '410400', '467000', 'W', '3');
INSERT INTO `system_address` VALUES ('1639', '石龙区', '410404', '410400', '467000', 'S', '3');
INSERT INTO `system_address` VALUES ('1640', '湛河区', '410411', '410400', '467000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1641', '宝丰县', '410421', '410400', '467400', 'B', '3');
INSERT INTO `system_address` VALUES ('1642', '叶县', '410422', '410400', '467200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1643', '鲁山县', '410423', '410400', '467300', 'L', '3');
INSERT INTO `system_address` VALUES ('1644', '郏县', '410425', '410400', '467100', 'J', '3');
INSERT INTO `system_address` VALUES ('1645', '舞钢市', '410481', '410400', '462500', 'W', '3');
INSERT INTO `system_address` VALUES ('1646', '汝州市', '410482', '410400', '467599', 'R', '3');
INSERT INTO `system_address` VALUES ('1647', '安阳市', '410500', '410000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('1648', '文峰区', '410502', '410500', '455000', 'W', '3');
INSERT INTO `system_address` VALUES ('1649', '北关区', '410503', '410500', '455000', 'B', '3');
INSERT INTO `system_address` VALUES ('1650', '殷都区', '410505', '410500', '455000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1651', '龙安区', '410506', '410500', '455000', 'L', '3');
INSERT INTO `system_address` VALUES ('1652', '安阳县', '410522', '410500', '455100', 'A', '3');
INSERT INTO `system_address` VALUES ('1653', '汤阴县', '410523', '410500', '456150', 'T', '3');
INSERT INTO `system_address` VALUES ('1654', '滑县', '410526', '410500', '456400', 'H', '3');
INSERT INTO `system_address` VALUES ('1655', '内黄县', '410527', '410500', '456300', 'N', '3');
INSERT INTO `system_address` VALUES ('1656', '林州市', '410581', '410500', '456500', 'L', '3');
INSERT INTO `system_address` VALUES ('1657', '鹤壁市', '410600', '410000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1658', '鹤山区', '410602', '410600', '458000', 'H', '3');
INSERT INTO `system_address` VALUES ('1659', '山城区', '410603', '410600', '458000', 'S', '3');
INSERT INTO `system_address` VALUES ('1660', '淇滨区', '410611', '410600', '458030', 'Q', '3');
INSERT INTO `system_address` VALUES ('1661', '浚县', '410621', '410600', '456250', 'J', '3');
INSERT INTO `system_address` VALUES ('1662', '淇县', '410622', '410600', '456750', 'Q', '3');
INSERT INTO `system_address` VALUES ('1663', '新乡市', '410700', '410000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1664', '红旗区', '410702', '410700', '453000', 'H', '3');
INSERT INTO `system_address` VALUES ('1665', '卫滨区', '410703', '410700', '453000', 'W', '3');
INSERT INTO `system_address` VALUES ('1666', '凤泉区', '410704', '410700', '453011', 'F', '3');
INSERT INTO `system_address` VALUES ('1667', '牧野区', '410711', '410700', '453002', 'M', '3');
INSERT INTO `system_address` VALUES ('1668', '新乡县', '410721', '410700', '453700', 'X', '3');
INSERT INTO `system_address` VALUES ('1669', '获嘉县', '410724', '410700', '453800', 'H', '3');
INSERT INTO `system_address` VALUES ('1670', '原阳县', '410725', '410700', '453500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1671', '延津县', '410726', '410700', '453200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1672', '封丘县', '410727', '410700', '453300', 'F', '3');
INSERT INTO `system_address` VALUES ('1673', '长垣县', '410728', '410700', '453400', 'Z', '3');
INSERT INTO `system_address` VALUES ('1674', '卫辉市', '410781', '410700', '453100', 'W', '3');
INSERT INTO `system_address` VALUES ('1675', '辉县市', '410782', '410700', '453600', 'H', '3');
INSERT INTO `system_address` VALUES ('1676', '焦作市', '410800', '410000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1677', '解放区', '410802', '410800', '454150', 'J', '3');
INSERT INTO `system_address` VALUES ('1678', '中站区', '410803', '410800', '454150', 'Z', '3');
INSERT INTO `system_address` VALUES ('1679', '马村区', '410804', '410800', '454150', 'M', '3');
INSERT INTO `system_address` VALUES ('1680', '山阳区', '410811', '410800', '454150', 'S', '3');
INSERT INTO `system_address` VALUES ('1681', '修武县', '410821', '410800', '454350', 'X', '3');
INSERT INTO `system_address` VALUES ('1682', '博爱县', '410822', '410800', '454450', 'B', '3');
INSERT INTO `system_address` VALUES ('1683', '武陟县', '410823', '410800', '454950', 'W', '3');
INSERT INTO `system_address` VALUES ('1684', '温县', '410825', '410800', '454850', 'W', '3');
INSERT INTO `system_address` VALUES ('1685', '沁阳市', '410882', '410800', '454550', 'Q', '3');
INSERT INTO `system_address` VALUES ('1686', '孟州市', '410883', '410800', '454750', 'M', '3');
INSERT INTO `system_address` VALUES ('1687', '濮阳市', '410900', '410000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('1688', '华龙区', '410902', '410900', '457001', 'H', '3');
INSERT INTO `system_address` VALUES ('1689', '清丰县', '410922', '410900', '457300', 'Q', '3');
INSERT INTO `system_address` VALUES ('1690', '南乐县', '410923', '410900', '457400', 'N', '3');
INSERT INTO `system_address` VALUES ('1691', '范县', '410926', '410900', '457500', 'F', '3');
INSERT INTO `system_address` VALUES ('1692', '台前县', '410927', '410900', '457600', 'T', '3');
INSERT INTO `system_address` VALUES ('1693', '濮阳县', '410928', '410900', '457100', 'P', '3');
INSERT INTO `system_address` VALUES ('1694', '许昌市', '411000', '410000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1695', '魏都区', '411002', '411000', '461000', 'W', '3');
INSERT INTO `system_address` VALUES ('1696', '许昌县', '411023', '411000', '461100', 'X', '3');
INSERT INTO `system_address` VALUES ('1697', '鄢陵县', '411024', '411000', '461200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1698', '襄城县', '411025', '411000', '452670', 'X', '3');
INSERT INTO `system_address` VALUES ('1699', '禹州市', '411081', '411000', '461670', 'Y', '3');
INSERT INTO `system_address` VALUES ('1700', '长葛市', '411082', '411000', '461500', 'Z', '3');
INSERT INTO `system_address` VALUES ('1701', '漯河市', '411100', '410000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('1702', '源汇区', '411102', '411100', '462000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1703', '郾城区', '411103', '411100', '462300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1704', '召陵区', '411104', '411100', '462300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1705', '舞阳县', '411121', '411100', '462400', 'W', '3');
INSERT INTO `system_address` VALUES ('1706', '临颍县', '411122', '411100', '462600', 'L', '3');
INSERT INTO `system_address` VALUES ('1707', '三门峡市', '411200', '410000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1708', '湖滨区', '411202', '411200', '472000', 'H', '3');
INSERT INTO `system_address` VALUES ('1709', '渑池县', '411221', '411200', '472400', 'M', '3');
INSERT INTO `system_address` VALUES ('1710', '陕州区', '411222', '411200', '472100', 'S', '3');
INSERT INTO `system_address` VALUES ('1711', '卢氏县', '411224', '411200', '472200', 'L', '3');
INSERT INTO `system_address` VALUES ('1712', '义马市', '411281', '411200', '472300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1713', '灵宝市', '411282', '411200', '472500', 'L', '3');
INSERT INTO `system_address` VALUES ('1714', '南阳市', '411300', '410000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('1715', '宛城区', '411302', '411300', '473000', 'W', '3');
INSERT INTO `system_address` VALUES ('1716', '卧龙区', '411303', '411300', '473000', 'W', '3');
INSERT INTO `system_address` VALUES ('1717', '南召县', '411321', '411300', '474650', 'N', '3');
INSERT INTO `system_address` VALUES ('1718', '方城县', '411322', '411300', '473200', 'F', '3');
INSERT INTO `system_address` VALUES ('1719', '西峡县', '411323', '411300', '474550', 'X', '3');
INSERT INTO `system_address` VALUES ('1720', '镇平县', '411324', '411300', '474250', 'Z', '3');
INSERT INTO `system_address` VALUES ('1721', '内乡县', '411325', '411300', '474350', 'N', '3');
INSERT INTO `system_address` VALUES ('1722', '淅川县', '411326', '411300', '474450', 'X', '3');
INSERT INTO `system_address` VALUES ('1723', '社旗县', '411327', '411300', '473300', 'S', '3');
INSERT INTO `system_address` VALUES ('1724', '唐河县', '411328', '411300', '473400', 'T', '3');
INSERT INTO `system_address` VALUES ('1725', '新野县', '411329', '411300', '473500', 'X', '3');
INSERT INTO `system_address` VALUES ('1726', '桐柏县', '411330', '411300', '474750', 'T', '3');
INSERT INTO `system_address` VALUES ('1727', '邓州市', '411381', '411300', '474150', 'D', '3');
INSERT INTO `system_address` VALUES ('1728', '商丘市', '411400', '410000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1729', '梁园区', '411402', '411400', '476000', 'L', '3');
INSERT INTO `system_address` VALUES ('1730', '睢阳区', '411403', '411400', '476000', 'S', '3');
INSERT INTO `system_address` VALUES ('1731', '民权县', '411421', '411400', '476800', 'M', '3');
INSERT INTO `system_address` VALUES ('1732', '睢县', '411422', '411400', '476900', 'S', '3');
INSERT INTO `system_address` VALUES ('1733', '宁陵县', '411423', '411400', '476700', 'N', '3');
INSERT INTO `system_address` VALUES ('1734', '柘城县', '411424', '411400', '476200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1735', '虞城县', '411425', '411400', '476300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1736', '夏邑县', '411426', '411400', '476400', 'X', '3');
INSERT INTO `system_address` VALUES ('1737', '永城市', '411481', '411400', '476600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1738', '信阳市', '411500', '410000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1739', '浉河区', '411502', '411500', '464000', 'S', '3');
INSERT INTO `system_address` VALUES ('1740', '平桥区', '411503', '411500', '464000', 'P', '3');
INSERT INTO `system_address` VALUES ('1741', '罗山县', '411521', '411500', '464200', 'L', '3');
INSERT INTO `system_address` VALUES ('1742', '光山县', '411522', '411500', '465450', 'G', '3');
INSERT INTO `system_address` VALUES ('1743', '新县', '411523', '411500', '465550', 'X', '3');
INSERT INTO `system_address` VALUES ('1744', '商城县', '411524', '411500', '465350', 'S', '3');
INSERT INTO `system_address` VALUES ('1745', '固始县', '411525', '411500', '465200', 'G', '3');
INSERT INTO `system_address` VALUES ('1746', '潢川县', '411526', '411500', '465150', 'H', '3');
INSERT INTO `system_address` VALUES ('1747', '淮滨县', '411527', '411500', '464400', 'H', '3');
INSERT INTO `system_address` VALUES ('1748', '息县', '411528', '411500', '464300', 'X', '3');
INSERT INTO `system_address` VALUES ('1749', '周口市', '411600', '410000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1750', '川汇区', '411602', '411600', '466000', 'C', '3');
INSERT INTO `system_address` VALUES ('1751', '扶沟县', '411621', '411600', '461300', 'F', '3');
INSERT INTO `system_address` VALUES ('1752', '西华县', '411622', '411600', '466600', 'X', '3');
INSERT INTO `system_address` VALUES ('1753', '商水县', '411623', '411600', '466100', 'S', '3');
INSERT INTO `system_address` VALUES ('1754', '沈丘县', '411624', '411600', '466300', 'S', '3');
INSERT INTO `system_address` VALUES ('1755', '郸城县', '411625', '411600', '477150', 'D', '3');
INSERT INTO `system_address` VALUES ('1756', '淮阳县', '411626', '411600', '466700', 'H', '3');
INSERT INTO `system_address` VALUES ('1757', '太康县', '411627', '411600', '475400', 'T', '3');
INSERT INTO `system_address` VALUES ('1758', '鹿邑县', '411628', '411600', '477200', 'L', '3');
INSERT INTO `system_address` VALUES ('1759', '项城市', '411681', '411600', '466200', 'X', '3');
INSERT INTO `system_address` VALUES ('1760', '驻马店市', '411700', '410000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1761', '驿城区', '411702', '411700', '463000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1762', '西平县', '411721', '411700', '463600', 'X', '3');
INSERT INTO `system_address` VALUES ('1763', '上蔡县', '411722', '411700', '463800', 'S', '3');
INSERT INTO `system_address` VALUES ('1764', '平舆县', '411723', '411700', '463400', 'P', '3');
INSERT INTO `system_address` VALUES ('1765', '正阳县', '411724', '411700', '463900', 'Z', '3');
INSERT INTO `system_address` VALUES ('1766', '确山县', '411725', '411700', '463200', 'Q', '3');
INSERT INTO `system_address` VALUES ('1767', '泌阳县', '411726', '411700', '463700', 'M', '3');
INSERT INTO `system_address` VALUES ('1768', '汝南县', '411727', '411700', '463300', 'R', '3');
INSERT INTO `system_address` VALUES ('1769', '遂平县', '411728', '411700', '463100', 'S', '3');
INSERT INTO `system_address` VALUES ('1770', '新蔡县', '411729', '411700', '463500', 'X', '3');
INSERT INTO `system_address` VALUES ('1771', '省直辖县', '419000', '410000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1772', '湖北', '420000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('1773', '武汉市', '420100', '420000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('1774', '江岸区', '420102', '420100', '430014', 'J', '3');
INSERT INTO `system_address` VALUES ('1775', '江汉区', '420103', '420100', '430000', 'J', '3');
INSERT INTO `system_address` VALUES ('1776', '硚口区', '420104', '420100', '430000', 'Q', '3');
INSERT INTO `system_address` VALUES ('1777', '汉阳区', '420105', '420100', '430050', 'H', '3');
INSERT INTO `system_address` VALUES ('1778', '武昌区', '420106', '420100', '430000', 'W', '3');
INSERT INTO `system_address` VALUES ('1779', '青山区', '420107', '420100', '430080', 'Q', '3');
INSERT INTO `system_address` VALUES ('1780', '洪山区', '420111', '420100', '430070', 'H', '3');
INSERT INTO `system_address` VALUES ('1781', '东西湖区', '420112', '420100', '430040', 'D', '3');
INSERT INTO `system_address` VALUES ('1782', '汉南区', '420113', '420100', '430090', 'H', '3');
INSERT INTO `system_address` VALUES ('1783', '蔡甸区', '420114', '420100', '430100', 'C', '3');
INSERT INTO `system_address` VALUES ('1784', '江夏区', '420115', '420100', '430200', 'J', '3');
INSERT INTO `system_address` VALUES ('1785', '黄陂区', '420116', '420100', '432200', 'H', '3');
INSERT INTO `system_address` VALUES ('1786', '新洲区', '420117', '420100', '431400', 'X', '3');
INSERT INTO `system_address` VALUES ('1787', '武汉经济技术开发区', '420199', '420100', '430000', 'W', '3');
INSERT INTO `system_address` VALUES ('1788', '黄石市', '420200', '420000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1789', '黄石港区', '420202', '420200', '435000', 'H', '3');
INSERT INTO `system_address` VALUES ('1790', '西塞山区', '420203', '420200', '435000', 'X', '3');
INSERT INTO `system_address` VALUES ('1791', '下陆区', '420204', '420200', '435000', 'X', '3');
INSERT INTO `system_address` VALUES ('1792', '铁山区', '420205', '420200', '435000', 'T', '3');
INSERT INTO `system_address` VALUES ('1793', '阳新县', '420222', '420200', '435200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1794', '大冶市', '420281', '420200', '435100', 'D', '3');
INSERT INTO `system_address` VALUES ('1795', '十堰市', '420300', '420000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1796', '茅箭区', '420302', '420300', '442000', 'M', '3');
INSERT INTO `system_address` VALUES ('1797', '张湾区', '420303', '420300', '442000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1798', '郧阳区', '420321', '420300', '442500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1799', '郧西县', '420322', '420300', '442600', 'Y', '3');
INSERT INTO `system_address` VALUES ('1800', '竹山县', '420323', '420300', '442200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1801', '竹溪县', '420324', '420300', '442300', 'Z', '3');
INSERT INTO `system_address` VALUES ('1802', '房县', '420325', '420300', '442100', 'F', '3');
INSERT INTO `system_address` VALUES ('1803', '丹江口市', '420381', '420300', '442700', 'D', '3');
INSERT INTO `system_address` VALUES ('1804', '城区', '420382', '420300', '442000', 'C', '3');
INSERT INTO `system_address` VALUES ('1805', '宜昌市', '420500', '420000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1806', '西陵区', '420502', '420500', '443000', 'X', '3');
INSERT INTO `system_address` VALUES ('1807', '伍家岗区', '420503', '420500', '443000', 'W', '3');
INSERT INTO `system_address` VALUES ('1808', '点军区', '420504', '420500', '443000', 'D', '3');
INSERT INTO `system_address` VALUES ('1809', '猇亭区', '420505', '420500', '443007', 'Y', '3');
INSERT INTO `system_address` VALUES ('1810', '夷陵区', '420506', '420500', '443100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1811', '远安县', '420525', '420500', '444200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1812', '兴山县', '420526', '420500', '443700', 'X', '3');
INSERT INTO `system_address` VALUES ('1813', '秭归县', '420527', '420500', '443600', 'Z', '3');
INSERT INTO `system_address` VALUES ('1814', '长阳土家族自治县', '420528', '420500', '443500', 'Z', '3');
INSERT INTO `system_address` VALUES ('1815', '五峰土家族自治县', '420529', '420500', '443400', 'W', '3');
INSERT INTO `system_address` VALUES ('1816', '葛洲坝区', '420551', '420500', '443003', 'G', '3');
INSERT INTO `system_address` VALUES ('1817', '开发区', '420552', '420500', '443000', 'K', '3');
INSERT INTO `system_address` VALUES ('1818', '宜都市', '420581', '420500', '443000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1819', '当阳市', '420582', '420500', '444100', 'D', '3');
INSERT INTO `system_address` VALUES ('1820', '枝江市', '420583', '420500', '443200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1821', '襄阳市', '420600', '420000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1822', '襄城区', '420602', '420600', '441000', 'X', '3');
INSERT INTO `system_address` VALUES ('1823', '樊城区', '420606', '420600', '441000', 'F', '3');
INSERT INTO `system_address` VALUES ('1824', '襄州区', '420607', '420600', '441100', 'X', '3');
INSERT INTO `system_address` VALUES ('1825', '南漳县', '420624', '420600', '441500', 'N', '3');
INSERT INTO `system_address` VALUES ('1826', '谷城县', '420625', '420600', '441700', 'G', '3');
INSERT INTO `system_address` VALUES ('1827', '保康县', '420626', '420600', '441600', 'B', '3');
INSERT INTO `system_address` VALUES ('1828', '老河口市', '420682', '420600', '441800', 'L', '3');
INSERT INTO `system_address` VALUES ('1829', '枣阳市', '420683', '420600', '441200', 'Z', '3');
INSERT INTO `system_address` VALUES ('1830', '宜城市', '420684', '420600', '441400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1831', '鄂州市', '420700', '420000', null, 'E', '2');
INSERT INTO `system_address` VALUES ('1832', '梁子湖区', '420702', '420700', '436000', 'L', '3');
INSERT INTO `system_address` VALUES ('1833', '华容区', '420703', '420700', '436000', 'H', '3');
INSERT INTO `system_address` VALUES ('1834', '鄂城区', '420704', '420700', '436000', 'E', '3');
INSERT INTO `system_address` VALUES ('1835', '荆门市', '420800', '420000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1836', '东宝区', '420802', '420800', '448000', 'D', '3');
INSERT INTO `system_address` VALUES ('1837', '掇刀区', '420804', '420800', '448000', 'D', '3');
INSERT INTO `system_address` VALUES ('1838', '京山县', '420821', '420800', '431800', 'J', '3');
INSERT INTO `system_address` VALUES ('1839', '沙洋县', '420822', '420800', '448200', 'S', '3');
INSERT INTO `system_address` VALUES ('1840', '钟祥市', '420881', '420800', '431900', 'Z', '3');
INSERT INTO `system_address` VALUES ('1841', '孝感市', '420900', '420000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1842', '孝南区', '420902', '420900', '432100', 'X', '3');
INSERT INTO `system_address` VALUES ('1843', '孝昌县', '420921', '420900', '432900', 'X', '3');
INSERT INTO `system_address` VALUES ('1844', '大悟县', '420922', '420900', '432800', 'D', '3');
INSERT INTO `system_address` VALUES ('1845', '云梦县', '420923', '420900', '432500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1846', '应城市', '420981', '420900', '432400', 'Y', '3');
INSERT INTO `system_address` VALUES ('1847', '安陆市', '420982', '420900', '432600', 'A', '3');
INSERT INTO `system_address` VALUES ('1848', '汉川市', '420984', '420900', '432300', 'H', '3');
INSERT INTO `system_address` VALUES ('1849', '荆州市', '421000', '420000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('1850', '沙市区', '421002', '421000', '434000', 'S', '3');
INSERT INTO `system_address` VALUES ('1851', '荆州区', '421003', '421000', '434020', 'J', '3');
INSERT INTO `system_address` VALUES ('1852', '公安县', '421022', '421000', '434300', 'G', '3');
INSERT INTO `system_address` VALUES ('1853', '监利县', '421023', '421000', '433300', 'J', '3');
INSERT INTO `system_address` VALUES ('1854', '江陵县', '421024', '421000', '434100', 'J', '3');
INSERT INTO `system_address` VALUES ('1855', '石首市', '421081', '421000', '434400', 'S', '3');
INSERT INTO `system_address` VALUES ('1856', '洪湖市', '421083', '421000', '433200', 'H', '3');
INSERT INTO `system_address` VALUES ('1857', '松滋市', '421087', '421000', '434200', 'S', '3');
INSERT INTO `system_address` VALUES ('1858', '黄冈市', '421100', '420000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1859', '黄州区', '421102', '421100', '438000', 'H', '3');
INSERT INTO `system_address` VALUES ('1860', '团风县', '421121', '421100', '438000', 'T', '3');
INSERT INTO `system_address` VALUES ('1861', '红安县', '421122', '421100', '438400', 'H', '3');
INSERT INTO `system_address` VALUES ('1862', '罗田县', '421123', '421100', '438600', 'L', '3');
INSERT INTO `system_address` VALUES ('1863', '英山县', '421124', '421100', '438700', 'Y', '3');
INSERT INTO `system_address` VALUES ('1864', '浠水县', '421125', '421100', '438200', 'X', '3');
INSERT INTO `system_address` VALUES ('1865', '蕲春县', '421126', '421100', '435300', 'Q', '3');
INSERT INTO `system_address` VALUES ('1866', '黄梅县', '421127', '421100', '435500', 'H', '3');
INSERT INTO `system_address` VALUES ('1867', '麻城市', '421181', '421100', '438300', 'M', '3');
INSERT INTO `system_address` VALUES ('1868', '武穴市', '421182', '421100', '435400', 'W', '3');
INSERT INTO `system_address` VALUES ('1869', '咸宁市', '421200', '420000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1870', '咸安区', '421202', '421200', '437000', 'X', '3');
INSERT INTO `system_address` VALUES ('1871', '嘉鱼县', '421221', '421200', '437200', 'J', '3');
INSERT INTO `system_address` VALUES ('1872', '通城县', '421222', '421200', '437400', 'T', '3');
INSERT INTO `system_address` VALUES ('1873', '崇阳县', '421223', '421200', '437500', 'C', '3');
INSERT INTO `system_address` VALUES ('1874', '通山县', '421224', '421200', '437600', 'T', '3');
INSERT INTO `system_address` VALUES ('1875', '赤壁市', '421281', '421200', '437300', 'C', '3');
INSERT INTO `system_address` VALUES ('1876', '温泉城区', '421282', '421200', '437000', 'W', '3');
INSERT INTO `system_address` VALUES ('1877', '随州市', '421300', '420000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1878', '曾都区', '421303', '421300', '441300', 'C', '3');
INSERT INTO `system_address` VALUES ('1879', '随县', '421321', '421300', '431500', 'S', '3');
INSERT INTO `system_address` VALUES ('1880', '广水市', '421381', '421300', '432700', 'G', '3');
INSERT INTO `system_address` VALUES ('1881', '恩施土家族苗族自治州', '422800', '420000', null, 'E', '2');
INSERT INTO `system_address` VALUES ('1882', '恩施市', '422801', '422800', '445000', 'E', '3');
INSERT INTO `system_address` VALUES ('1883', '利川市', '422802', '422800', '445400', 'L', '3');
INSERT INTO `system_address` VALUES ('1884', '建始县', '422822', '422800', '445300', 'J', '3');
INSERT INTO `system_address` VALUES ('1885', '巴东县', '422823', '422800', '444300', 'B', '3');
INSERT INTO `system_address` VALUES ('1886', '宣恩县', '422825', '422800', '445500', 'X', '3');
INSERT INTO `system_address` VALUES ('1887', '咸丰县', '422826', '422800', '445600', 'X', '3');
INSERT INTO `system_address` VALUES ('1888', '来凤县', '422827', '422800', '445700', 'L', '3');
INSERT INTO `system_address` VALUES ('1889', '鹤峰县', '422828', '422800', '445800', 'H', '3');
INSERT INTO `system_address` VALUES ('1890', '仙桃市', '422900', '429000', '433000', 'X', '3');
INSERT INTO `system_address` VALUES ('1891', '潜江市', '429005', '429000', '433100', 'Q', '3');
INSERT INTO `system_address` VALUES ('1892', '天门市', '429006', '429000', '431700', 'T', '3');
INSERT INTO `system_address` VALUES ('1893', '神农架林区', '429021', '429000', '442400', 'S', '3');
INSERT INTO `system_address` VALUES ('1894', '湖南', '430000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('1895', '长沙市', '430100', '430000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1896', '芙蓉区', '430102', '430100', '410000', 'F', '3');
INSERT INTO `system_address` VALUES ('1897', '天心区', '430103', '430100', '410000', 'T', '3');
INSERT INTO `system_address` VALUES ('1898', '岳麓区', '430104', '430100', '410000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1899', '开福区', '430105', '430100', '410000', 'K', '3');
INSERT INTO `system_address` VALUES ('1900', '雨花区', '430111', '430100', '410000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1901', '长沙县', '430121', '430100', '410100', 'Z', '3');
INSERT INTO `system_address` VALUES ('1902', '望城区', '430122', '430100', '410200', 'W', '3');
INSERT INTO `system_address` VALUES ('1903', '宁乡县', '430124', '430100', '410600', 'N', '3');
INSERT INTO `system_address` VALUES ('1904', '浏阳市', '430181', '430100', '410300', 'L', '3');
INSERT INTO `system_address` VALUES ('1905', '株洲市', '430200', '430000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1906', '荷塘区', '430202', '430200', '412000', 'H', '3');
INSERT INTO `system_address` VALUES ('1907', '芦淞区', '430203', '430200', '412000', 'L', '3');
INSERT INTO `system_address` VALUES ('1908', '石峰区', '430204', '430200', '412000', 'S', '3');
INSERT INTO `system_address` VALUES ('1909', '天元区', '430211', '430200', '412000', 'T', '3');
INSERT INTO `system_address` VALUES ('1910', '株洲县', '430221', '430200', '412000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1911', '攸县', '430223', '430200', '412300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1912', '茶陵县', '430224', '430200', '412400', 'C', '3');
INSERT INTO `system_address` VALUES ('1913', '炎陵县', '430225', '430200', '412500', 'Y', '3');
INSERT INTO `system_address` VALUES ('1914', '醴陵市', '430281', '430200', '412200', 'L', '3');
INSERT INTO `system_address` VALUES ('1915', '湘潭市', '430300', '430000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('1916', '雨湖区', '430302', '430300', '411100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1917', '岳塘区', '430304', '430300', '411100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1918', '湘潭县', '430321', '430300', '411200', 'X', '3');
INSERT INTO `system_address` VALUES ('1919', '湘乡市', '430381', '430300', '411400', 'X', '3');
INSERT INTO `system_address` VALUES ('1920', '韶山市', '430382', '430300', '411300', 'S', '3');
INSERT INTO `system_address` VALUES ('1921', '衡阳市', '430400', '430000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('1922', '珠晖区', '430405', '430400', '421000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1923', '雁峰区', '430406', '430400', '421000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1924', '石鼓区', '430407', '430400', '421000', 'S', '3');
INSERT INTO `system_address` VALUES ('1925', '蒸湘区', '430408', '430400', '421000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1926', '南岳区', '430412', '430400', '421000', 'N', '3');
INSERT INTO `system_address` VALUES ('1927', '衡阳县', '430421', '430400', '421200', 'H', '3');
INSERT INTO `system_address` VALUES ('1928', '衡南县', '430422', '430400', '421100', 'H', '3');
INSERT INTO `system_address` VALUES ('1929', '衡山县', '430423', '430400', '421300', 'H', '3');
INSERT INTO `system_address` VALUES ('1930', '衡东县', '430424', '430400', '421400', 'H', '3');
INSERT INTO `system_address` VALUES ('1931', '祁东县', '430426', '430400', '421600', 'Q', '3');
INSERT INTO `system_address` VALUES ('1932', '耒阳市', '430481', '430400', '421800', 'L', '3');
INSERT INTO `system_address` VALUES ('1933', '常宁市', '430482', '430400', '421500', 'C', '3');
INSERT INTO `system_address` VALUES ('1934', '邵阳市', '430500', '430000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('1935', '双清区', '430502', '430500', '422000	', 'S', '3');
INSERT INTO `system_address` VALUES ('1936', '大祥区', '430503', '430500', '422000	', 'D', '3');
INSERT INTO `system_address` VALUES ('1937', '北塔区', '430511', '430500', '422000	', 'B', '3');
INSERT INTO `system_address` VALUES ('1938', '邵东县', '430521', '430500', '422800', 'S', '3');
INSERT INTO `system_address` VALUES ('1939', '新邵县', '430522', '430500', '422900', 'X', '3');
INSERT INTO `system_address` VALUES ('1940', '邵阳县', '430523', '430500', '422100', 'S', '3');
INSERT INTO `system_address` VALUES ('1941', '隆回县', '430524', '430500', '422200', 'L', '3');
INSERT INTO `system_address` VALUES ('1942', '洞口县', '430525', '430500', '422300', 'D', '3');
INSERT INTO `system_address` VALUES ('1943', '绥宁县', '430527', '430500', '422600', 'S', '3');
INSERT INTO `system_address` VALUES ('1944', '新宁县', '430528', '430500', '422700', 'X', '3');
INSERT INTO `system_address` VALUES ('1945', '城步苗族自治县', '430529', '430500', '422500', 'C', '3');
INSERT INTO `system_address` VALUES ('1946', '武冈市', '430581', '430500', '422400', 'W', '3');
INSERT INTO `system_address` VALUES ('1947', '岳阳市', '430600', '430000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1948', '岳阳楼区', '430602', '430600', '414000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1949', '云溪区', '430603', '430600', '414000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1950', '君山区', '430611', '430600', '414000', 'J', '3');
INSERT INTO `system_address` VALUES ('1951', '岳阳县', '430621', '430600', '414000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1952', '华容县', '430623', '430600', '414200', 'H', '3');
INSERT INTO `system_address` VALUES ('1953', '湘阴县', '430624', '430600', '410500', 'X', '3');
INSERT INTO `system_address` VALUES ('1954', '平江县', '430626', '430600', '410400', 'P', '3');
INSERT INTO `system_address` VALUES ('1955', '汨罗市', '430681', '430600', '414400', 'M', '3');
INSERT INTO `system_address` VALUES ('1956', '临湘市', '430682', '430600', '414300', 'L', '3');
INSERT INTO `system_address` VALUES ('1957', '常德市', '430700', '430000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('1958', '武陵区', '430702', '430700', '415000', 'W', '3');
INSERT INTO `system_address` VALUES ('1959', '鼎城区', '430703', '430700', '415100', 'D', '3');
INSERT INTO `system_address` VALUES ('1960', '安乡县', '430721', '430700', '415600', 'A', '3');
INSERT INTO `system_address` VALUES ('1961', '汉寿县', '430722', '430700', '415900', 'H', '3');
INSERT INTO `system_address` VALUES ('1962', '澧县', '430723', '430700', '415500', 'L', '3');
INSERT INTO `system_address` VALUES ('1963', '临澧县', '430724', '430700', '415200', 'L', '3');
INSERT INTO `system_address` VALUES ('1964', '桃源县', '430725', '430700', '415700', 'T', '3');
INSERT INTO `system_address` VALUES ('1965', '石门县', '430726', '430700', '415300', 'S', '3');
INSERT INTO `system_address` VALUES ('1966', '津市市', '430781', '430700', '415400', 'J', '3');
INSERT INTO `system_address` VALUES ('1967', '张家界市', '430800', '430000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('1968', '永定区', '430802', '430800', '427000', 'Y', '3');
INSERT INTO `system_address` VALUES ('1969', '武陵源区', '430811', '430800', '427400', 'W', '3');
INSERT INTO `system_address` VALUES ('1970', '慈利县', '430821', '430800', '427200', 'C', '3');
INSERT INTO `system_address` VALUES ('1971', '桑植县', '430822', '430800', '427100', 'S', '3');
INSERT INTO `system_address` VALUES ('1972', '益阳市', '430900', '430000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1973', '资阳区', '430902', '430900', '413000', 'Z', '3');
INSERT INTO `system_address` VALUES ('1974', '赫山区', '430903', '430900', '413000', 'H', '3');
INSERT INTO `system_address` VALUES ('1975', '南县', '430921', '430900', '413200', 'N', '3');
INSERT INTO `system_address` VALUES ('1976', '桃江县', '430922', '430900', '413400', 'T', '3');
INSERT INTO `system_address` VALUES ('1977', '安化县', '430923', '430900', '413500', 'A', '3');
INSERT INTO `system_address` VALUES ('1978', '沅江市', '430981', '430900', '413100', 'Y', '3');
INSERT INTO `system_address` VALUES ('1979', '郴州市', '431000', '430000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('1980', '北湖区', '431002', '431000', '423000', 'B', '3');
INSERT INTO `system_address` VALUES ('1981', '苏仙区', '431003', '431000', '423000', 'S', '3');
INSERT INTO `system_address` VALUES ('1982', '桂阳县', '431021', '431000', '424400', 'G', '3');
INSERT INTO `system_address` VALUES ('1983', '宜章县', '431022', '431000', '424200', 'Y', '3');
INSERT INTO `system_address` VALUES ('1984', '永兴县', '431023', '431000', '423300', 'Y', '3');
INSERT INTO `system_address` VALUES ('1985', '嘉禾县', '431024', '431000', '424500', 'J', '3');
INSERT INTO `system_address` VALUES ('1986', '临武县', '431025', '431000', '424300', 'L', '3');
INSERT INTO `system_address` VALUES ('1987', '汝城县', '431026', '431000', '424100', 'R', '3');
INSERT INTO `system_address` VALUES ('1988', '桂东县', '431027', '431000', '423500', 'G', '3');
INSERT INTO `system_address` VALUES ('1989', '安仁县', '431028', '431000', '423600', 'A', '3');
INSERT INTO `system_address` VALUES ('1990', '资兴市', '431081', '431000', '423400', 'Z', '3');
INSERT INTO `system_address` VALUES ('1991', '永州市', '431100', '430000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('1992', '零陵区', '431102', '431100', '425100', 'L', '3');
INSERT INTO `system_address` VALUES ('1993', '冷水滩区', '431103', '431100', '425000', 'L', '3');
INSERT INTO `system_address` VALUES ('1994', '祁阳县', '431121', '431100', '426100', 'Q', '3');
INSERT INTO `system_address` VALUES ('1995', '东安县', '431122', '431100', '425900', 'D', '3');
INSERT INTO `system_address` VALUES ('1996', '双牌县', '431123', '431100', '425200', 'S', '3');
INSERT INTO `system_address` VALUES ('1997', '道县', '431124', '431100', '425300', 'D', '3');
INSERT INTO `system_address` VALUES ('1998', '江永县', '431125', '431100', '425400', 'J', '3');
INSERT INTO `system_address` VALUES ('1999', '宁远县', '431126', '431100', '425600', 'N', '3');
INSERT INTO `system_address` VALUES ('2000', '蓝山县', '431127', '431100', '425800', 'L', '3');
INSERT INTO `system_address` VALUES ('2001', '新田县', '431128', '431100', '425700', 'X', '3');
INSERT INTO `system_address` VALUES ('2002', '江华瑶族自治县', '431129', '431100', '425500', 'J', '3');
INSERT INTO `system_address` VALUES ('2003', '怀化市', '431200', '430000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2004', '鹤城区', '431202', '431200', '418000', 'H', '3');
INSERT INTO `system_address` VALUES ('2005', '中方县', '431221', '431200', '418000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2006', '沅陵县', '431222', '431200', '419600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2007', '辰溪县', '431223', '431200', '419500', 'C', '3');
INSERT INTO `system_address` VALUES ('2008', '溆浦县', '431224', '431200', '419300', 'X', '3');
INSERT INTO `system_address` VALUES ('2009', '会同县', '431225', '431200', '418300', 'H', '3');
INSERT INTO `system_address` VALUES ('2010', '麻阳苗族自治县', '431226', '431200', '419400', 'M', '3');
INSERT INTO `system_address` VALUES ('2011', '新晃侗族自治县', '431227', '431200', '419200', 'X', '3');
INSERT INTO `system_address` VALUES ('2012', '芷江侗族自治县', '431228', '431200', '419100', 'Z', '3');
INSERT INTO `system_address` VALUES ('2013', '靖州苗族侗族自治县', '431229', '431200', '418400', 'J', '3');
INSERT INTO `system_address` VALUES ('2014', '通道侗族自治县', '431230', '431200', '418500	', 'T', '3');
INSERT INTO `system_address` VALUES ('2015', '洪江市', '431281', '431200', '418200', 'H', '3');
INSERT INTO `system_address` VALUES ('2016', '娄底市', '431300', '430000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2017', '娄星区', '431302', '431300', '417000', 'L', '3');
INSERT INTO `system_address` VALUES ('2018', '双峰县', '431321', '431300', '417700', 'S', '3');
INSERT INTO `system_address` VALUES ('2019', '新化县', '431322', '431300', '417600', 'X', '3');
INSERT INTO `system_address` VALUES ('2020', '冷水江市', '431381', '431300', '417500', 'L', '3');
INSERT INTO `system_address` VALUES ('2021', '涟源市', '431382', '431300', '417100', 'L', '3');
INSERT INTO `system_address` VALUES ('2022', '湘西土家族苗族自治州', '433100', '430000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('2023', '吉首市', '433101', '433100', '416000', 'J', '3');
INSERT INTO `system_address` VALUES ('2024', '泸溪县', '433122', '433100', '416100', 'L', '3');
INSERT INTO `system_address` VALUES ('2025', '凤凰县', '433123', '433100', '416200', 'F', '3');
INSERT INTO `system_address` VALUES ('2026', '花垣县', '433124', '433100', '416400', 'H', '3');
INSERT INTO `system_address` VALUES ('2027', '保靖县', '433125', '433100', '416500', 'B', '3');
INSERT INTO `system_address` VALUES ('2028', '古丈县', '433126', '433100', '416300', 'G', '3');
INSERT INTO `system_address` VALUES ('2029', '永顺县', '433127', '433100', '416700', 'Y', '3');
INSERT INTO `system_address` VALUES ('2030', '龙山县', '433130', '433100', '416800', 'L', '3');
INSERT INTO `system_address` VALUES ('2031', '广东', '440000', '0', null, 'G', '1');
INSERT INTO `system_address` VALUES ('2032', '广州市', '440100', '440000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2033', '荔湾区', '440103', '440100', '510000', 'L', '3');
INSERT INTO `system_address` VALUES ('2034', '越秀区', '440104', '440100', '510000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2035', '海珠区', '440105', '440100', '510000', 'H', '3');
INSERT INTO `system_address` VALUES ('2036', '天河区', '440106', '440100', '510000', 'T', '3');
INSERT INTO `system_address` VALUES ('2037', '白云区', '440111', '440100', '510000', 'B', '3');
INSERT INTO `system_address` VALUES ('2038', '黄埔区', '440112', '440100', '510700', 'H', '3');
INSERT INTO `system_address` VALUES ('2039', '番禺区', '440113', '440100', '511400', 'F', '3');
INSERT INTO `system_address` VALUES ('2040', '花都区', '440114', '440100', '510800', 'H', '3');
INSERT INTO `system_address` VALUES ('2041', '南沙区', '440115', '440100', '511400', 'N', '3');
INSERT INTO `system_address` VALUES ('2042', '萝岗区', '440116', '440100', '510000', 'L', '3');
INSERT INTO `system_address` VALUES ('2043', '增城区', '440118', '440100', '511300', 'Z', '3');
INSERT INTO `system_address` VALUES ('2044', '从化区', '440184', '440100', '510900', 'C', '3');
INSERT INTO `system_address` VALUES ('2045', '东山区', '440188', '440100', '510080', 'D', '3');
INSERT INTO `system_address` VALUES ('2046', '韶关市', '440200', '440000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2047', '武江区', '440203', '440200', '512000', 'W', '3');
INSERT INTO `system_address` VALUES ('2048', '浈江区', '440204', '440200', '512000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2049', '曲江区', '440205', '440200', '512100', 'Q', '3');
INSERT INTO `system_address` VALUES ('2050', '始兴县', '440222', '440200', '512500', 'S', '3');
INSERT INTO `system_address` VALUES ('2051', '仁化县', '440224', '440200', '512300', 'R', '3');
INSERT INTO `system_address` VALUES ('2052', '翁源县', '440229', '440200', '512600', 'W', '3');
INSERT INTO `system_address` VALUES ('2053', '乳源瑶族自治县', '440232', '440200', '512600', 'R', '3');
INSERT INTO `system_address` VALUES ('2054', '新丰县', '440233', '440200', '511100', 'X', '3');
INSERT INTO `system_address` VALUES ('2055', '乐昌市', '440281', '440200', '512200', 'L', '3');
INSERT INTO `system_address` VALUES ('2056', '南雄市', '440282', '440200', '512400', 'N', '3');
INSERT INTO `system_address` VALUES ('2057', '深圳市', '440300', '440000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2058', '罗湖区', '440303', '440300', '518000', 'L', '3');
INSERT INTO `system_address` VALUES ('2059', '福田区', '440304', '440300', '518000', 'F', '3');
INSERT INTO `system_address` VALUES ('2060', '南山区', '440305', '440300', '518000', 'N', '3');
INSERT INTO `system_address` VALUES ('2061', '宝安区', '440306', '440300', '518100', 'B', '3');
INSERT INTO `system_address` VALUES ('2062', '龙岗区', '440307', '440300', '518100', 'L', '3');
INSERT INTO `system_address` VALUES ('2063', '盐田区', '440308', '440300', '518000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2064', '光明新区', '440320', '440300', '518000', 'G', '3');
INSERT INTO `system_address` VALUES ('2065', '坪山新区', '440321', '440300', '518118', 'P', '3');
INSERT INTO `system_address` VALUES ('2066', '大鹏新区', '440322', '440300', '518116', 'D', '3');
INSERT INTO `system_address` VALUES ('2067', '龙华新区', '440323', '440300', '518110', 'L', '3');
INSERT INTO `system_address` VALUES ('2068', '珠海市', '440400', '440000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2069', '香洲区', '440402', '440400', '519000', 'X', '3');
INSERT INTO `system_address` VALUES ('2070', '斗门区', '440403', '440400', '519100', 'D', '3');
INSERT INTO `system_address` VALUES ('2071', '金湾区', '440404', '440400', '519090', 'J', '3');
INSERT INTO `system_address` VALUES ('2072', '金唐区', '440486', '440400', '519000', 'J', '3');
INSERT INTO `system_address` VALUES ('2073', '南湾区', '440487', '440400', '519000', 'N', '3');
INSERT INTO `system_address` VALUES ('2074', '汕头市', '440500', '440000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2075', '龙湖区', '440507', '440500', '515000', 'L', '3');
INSERT INTO `system_address` VALUES ('2076', '金平区', '440511', '440500', '515000', 'J', '3');
INSERT INTO `system_address` VALUES ('2077', '濠江区', '440512', '440500', '515000', 'H', '3');
INSERT INTO `system_address` VALUES ('2078', '潮阳区', '440513', '440500', '515100', 'C', '3');
INSERT INTO `system_address` VALUES ('2079', '潮南区', '440514', '440500', '515100', 'C', '3');
INSERT INTO `system_address` VALUES ('2080', '澄海区', '440515', '440500', '515800', 'C', '3');
INSERT INTO `system_address` VALUES ('2081', '南澳县', '440523', '440500', '515900', 'N', '3');
INSERT INTO `system_address` VALUES ('2082', '佛山市', '440600', '440000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('2083', '禅城区', '440604', '440600', '528000', 'S', '3');
INSERT INTO `system_address` VALUES ('2084', '南海区', '440605', '440600', '528200', 'N', '3');
INSERT INTO `system_address` VALUES ('2085', '顺德区', '440606', '440600', '528300', 'S', '3');
INSERT INTO `system_address` VALUES ('2086', '三水区', '440607', '440600', '528100', 'S', '3');
INSERT INTO `system_address` VALUES ('2087', '高明区', '440608', '440600', '528500', 'G', '3');
INSERT INTO `system_address` VALUES ('2088', '江门市', '440700', '440000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('2089', '蓬江区', '440703', '440700', '529000', 'P', '3');
INSERT INTO `system_address` VALUES ('2090', '江海区', '440704', '440700', '529000', 'J', '3');
INSERT INTO `system_address` VALUES ('2091', '新会区', '440705', '440700', '529100', 'X', '3');
INSERT INTO `system_address` VALUES ('2092', '台山市', '440781', '440700', '529200', 'T', '3');
INSERT INTO `system_address` VALUES ('2093', '开平市', '440783', '440700', '529300', 'K', '3');
INSERT INTO `system_address` VALUES ('2094', '鹤山市', '440784', '440700', '529700', 'H', '3');
INSERT INTO `system_address` VALUES ('2095', '恩平市', '440785', '440700', '529400', 'E', '3');
INSERT INTO `system_address` VALUES ('2096', '湛江市', '440800', '440000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2097', '赤坎区', '440802', '440800', '524033', 'C', '3');
INSERT INTO `system_address` VALUES ('2098', '霞山区', '440803', '440800', '524000', 'X', '3');
INSERT INTO `system_address` VALUES ('2099', '坡头区', '440804', '440800', '524000', 'P', '3');
INSERT INTO `system_address` VALUES ('2100', '麻章区', '440811', '440800', '524000', 'M', '3');
INSERT INTO `system_address` VALUES ('2101', '遂溪县', '440823', '440800', '524300', 'S', '3');
INSERT INTO `system_address` VALUES ('2102', '徐闻县', '440825', '440800', '524100', 'X', '3');
INSERT INTO `system_address` VALUES ('2103', '廉江市', '440881', '440800', '524400', 'L', '3');
INSERT INTO `system_address` VALUES ('2104', '雷州市', '440882', '440800', '524200', 'L', '3');
INSERT INTO `system_address` VALUES ('2105', '吴川市', '440883', '440800', '524500', 'W', '3');
INSERT INTO `system_address` VALUES ('2106', '茂名市', '440900', '440000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('2107', '茂南区', '440902', '440900', '525000', 'M', '3');
INSERT INTO `system_address` VALUES ('2108', '茂港区', '440903', '440900', '525000', 'M', '3');
INSERT INTO `system_address` VALUES ('2109', '电白区', '440923', '440900', '525400', 'D', '3');
INSERT INTO `system_address` VALUES ('2110', '高州市', '440981', '440900', '525200', 'G', '3');
INSERT INTO `system_address` VALUES ('2111', '化州市', '440982', '440900', '525100', 'H', '3');
INSERT INTO `system_address` VALUES ('2112', '信宜市', '440983', '440900', '525300', 'X', '3');
INSERT INTO `system_address` VALUES ('2113', '肇庆市', '441200', '440000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2114', '端州区', '441202', '441200', '526000', 'D', '3');
INSERT INTO `system_address` VALUES ('2115', '鼎湖区', '441203', '441200', '526000', 'D', '3');
INSERT INTO `system_address` VALUES ('2116', '广宁县', '441223', '441200', '526300', 'G', '3');
INSERT INTO `system_address` VALUES ('2117', '怀集县', '441224', '441200', '526400', 'H', '3');
INSERT INTO `system_address` VALUES ('2118', '封开县', '441225', '441200', '526500', 'F', '3');
INSERT INTO `system_address` VALUES ('2119', '德庆县', '441226', '441200', '526600', 'D', '3');
INSERT INTO `system_address` VALUES ('2120', '高要区', '441283', '441200', '526100', 'G', '3');
INSERT INTO `system_address` VALUES ('2121', '四会市', '441284', '441200', '526200', 'S', '3');
INSERT INTO `system_address` VALUES ('2122', '惠州市', '441300', '440000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2123', '惠城区', '441302', '441300', '516000', 'H', '3');
INSERT INTO `system_address` VALUES ('2124', '惠阳区', '441303', '441300', '516200', 'H', '3');
INSERT INTO `system_address` VALUES ('2125', '博罗县', '441322', '441300', '516100', 'B', '3');
INSERT INTO `system_address` VALUES ('2126', '惠东县', '441323', '441300', '516300', 'H', '3');
INSERT INTO `system_address` VALUES ('2127', '龙门县', '441324', '441300', '516800', 'L', '3');
INSERT INTO `system_address` VALUES ('2128', '大亚湾区', '441325', '441300', '516000', 'D', '3');
INSERT INTO `system_address` VALUES ('2129', '梅州市', '441400', '440000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('2130', '梅江区', '441402', '441400', '514000', 'M', '3');
INSERT INTO `system_address` VALUES ('2131', '梅县', '441421', '441400', '514700', 'M', '3');
INSERT INTO `system_address` VALUES ('2132', '大埔县', '441422', '441400', '514200', 'D', '3');
INSERT INTO `system_address` VALUES ('2133', '丰顺县', '441423', '441400', '514300', 'F', '3');
INSERT INTO `system_address` VALUES ('2134', '五华县', '441424', '441400', '514400', 'W', '3');
INSERT INTO `system_address` VALUES ('2135', '平远县', '441426', '441400', '514600', 'P', '3');
INSERT INTO `system_address` VALUES ('2136', '蕉岭县', '441427', '441400', '514100', 'J', '3');
INSERT INTO `system_address` VALUES ('2137', '兴宁市', '441481', '441400', '514500', 'X', '3');
INSERT INTO `system_address` VALUES ('2138', '汕尾市', '441500', '440000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2139', '城区', '441502', '441500', '516600', 'C', '3');
INSERT INTO `system_address` VALUES ('2140', '海丰县', '441521', '441500', '516400', 'H', '3');
INSERT INTO `system_address` VALUES ('2141', '陆河县', '441523', '441500', '516700', 'L', '3');
INSERT INTO `system_address` VALUES ('2142', '陆丰市', '441581', '441500', '516500', 'L', '3');
INSERT INTO `system_address` VALUES ('2143', '河源市', '441600', '440000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2144', '源城区', '441602', '441600', '517000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2145', '紫金县', '441621', '441600', '517400', 'Z', '3');
INSERT INTO `system_address` VALUES ('2146', '龙川县', '441622', '441600', '517300', 'L', '3');
INSERT INTO `system_address` VALUES ('2147', '连平县', '441623', '441600', '517100', 'L', '3');
INSERT INTO `system_address` VALUES ('2148', '和平县', '441624', '441600', '517200', 'H', '3');
INSERT INTO `system_address` VALUES ('2149', '东源县', '441625', '441600', '517500', 'D', '3');
INSERT INTO `system_address` VALUES ('2150', '阳江市', '441700', '440000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2151', '江城区', '441702', '441700', '529500', 'J', '3');
INSERT INTO `system_address` VALUES ('2152', '阳西县', '441721', '441700', '529800', 'Y', '3');
INSERT INTO `system_address` VALUES ('2153', '阳东区', '441723', '441700', '529900', 'Y', '3');
INSERT INTO `system_address` VALUES ('2154', '阳春市', '441781', '441700', '529600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2155', '清远市', '441800', '440000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2156', '清城区', '441802', '441800', '511500', 'Q', '3');
INSERT INTO `system_address` VALUES ('2157', '佛冈县', '441821', '441800', '511600', 'F', '3');
INSERT INTO `system_address` VALUES ('2158', '阳山县', '441823', '441800', '513100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2159', '连山壮族瑶族自治县', '441825', '441800', '513200', 'L', '3');
INSERT INTO `system_address` VALUES ('2160', '连南瑶族自治县', '441826', '441800', '513300', 'L', '3');
INSERT INTO `system_address` VALUES ('2161', '清新区', '441827', '441800', '511800', 'Q', '3');
INSERT INTO `system_address` VALUES ('2162', '英德市', '441881', '441800', '513000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2163', '连州市', '441882', '441800', '513400', 'L', '3');
INSERT INTO `system_address` VALUES ('2164', '东莞市', '441900', '440000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2165', '中山市', '442000', '440000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2166', '中山火炬开发区', '442002', '442000', '528437', 'Z', '3');
INSERT INTO `system_address` VALUES ('2167', '石岐区', '442003', '442000', '528400', 'S', '3');
INSERT INTO `system_address` VALUES ('2169', '潮州市', '445100', '440000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('2170', '湘桥区', '445102', '445100', '521000', 'X', '3');
INSERT INTO `system_address` VALUES ('2171', '潮安区', '445121', '445100', '515600', 'C', '3');
INSERT INTO `system_address` VALUES ('2172', '饶平县', '445122', '445100', '515700', 'R', '3');
INSERT INTO `system_address` VALUES ('2173', '枫溪区', '445185', '445100', '521000', 'F', '3');
INSERT INTO `system_address` VALUES ('2174', '揭阳市', '445200', '440000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('2175', '榕城区', '445202', '445200', '522000', 'R', '3');
INSERT INTO `system_address` VALUES ('2176', '揭东区', '445221', '445200', '515500', 'J', '3');
INSERT INTO `system_address` VALUES ('2177', '揭西县', '445222', '445200', '515400', 'J', '3');
INSERT INTO `system_address` VALUES ('2178', '惠来县', '445224', '445200', '515200', 'H', '3');
INSERT INTO `system_address` VALUES ('2179', '普宁市', '445281', '445200', '515300', 'P', '3');
INSERT INTO `system_address` VALUES ('2180', '东山区', '445284', '445200', '510080', 'D', '3');
INSERT INTO `system_address` VALUES ('2181', '云浮市', '445300', '440000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2182', '云城区', '445302', '445300', '527300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2183', '新兴县', '445321', '445300', '527400', 'X', '3');
INSERT INTO `system_address` VALUES ('2184', '郁南县', '445322', '445300', '527100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2185', '云安区', '445323', '445300', '527500', 'Y', '3');
INSERT INTO `system_address` VALUES ('2186', '罗定市', '445381', '445300', '527200', 'L', '3');
INSERT INTO `system_address` VALUES ('2187', '广西', '450000', '0', null, 'G', '1');
INSERT INTO `system_address` VALUES ('2188', '南宁市', '450100', '450000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('2189', '兴宁区', '450102', '450100', '530000', 'X', '3');
INSERT INTO `system_address` VALUES ('2190', '青秀区', '450103', '450100', '530000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2191', '江南区', '450105', '450100', '530000', 'J', '3');
INSERT INTO `system_address` VALUES ('2192', '西乡塘区', '450107', '450100', '530000', 'X', '3');
INSERT INTO `system_address` VALUES ('2193', '良庆区', '450108', '450100', '530200', 'L', '3');
INSERT INTO `system_address` VALUES ('2194', '邕宁区', '450109', '450100', '530200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2195', '武鸣区', '450110', '450100', '530100', 'W', '3');
INSERT INTO `system_address` VALUES ('2196', '隆安县', '450123', '450100', '532700', 'L', '3');
INSERT INTO `system_address` VALUES ('2197', '马山县', '450124', '450100', '530600', 'M', '3');
INSERT INTO `system_address` VALUES ('2198', '上林县', '450125', '450100', '530500', 'S', '3');
INSERT INTO `system_address` VALUES ('2199', '宾阳县', '450126', '450100', '530400', 'B', '3');
INSERT INTO `system_address` VALUES ('2200', '横县', '450127', '450100', '530300', 'H', '3');
INSERT INTO `system_address` VALUES ('2201', '柳州市', '450200', '450000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2202', '城中区', '450202', '450200', '545000', 'C', '3');
INSERT INTO `system_address` VALUES ('2203', '鱼峰区', '450203', '450200', '545000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2204', '柳南区', '450204', '450200', '545000', 'L', '3');
INSERT INTO `system_address` VALUES ('2205', '柳北区', '450205', '450200', '545000', 'L', '3');
INSERT INTO `system_address` VALUES ('2206', '柳江县', '450221', '450200', '545100', 'L', '3');
INSERT INTO `system_address` VALUES ('2207', '柳城县', '450222', '450200', '545200', 'L', '3');
INSERT INTO `system_address` VALUES ('2208', '鹿寨县', '450223', '450200', '545600', 'L', '3');
INSERT INTO `system_address` VALUES ('2209', '融安县', '450224', '450200', '545400', 'R', '3');
INSERT INTO `system_address` VALUES ('2210', '融水苗族自治县', '450225', '450200', '545300', 'R', '3');
INSERT INTO `system_address` VALUES ('2211', '三江侗族自治县', '450226', '450200', '545500', 'S', '3');
INSERT INTO `system_address` VALUES ('2212', '桂林市', '450300', '450000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2213', '秀峰区', '450302', '450300', '541000', 'X', '3');
INSERT INTO `system_address` VALUES ('2214', '叠彩区', '450303', '450300', '541000', 'D', '3');
INSERT INTO `system_address` VALUES ('2215', '象山区', '450304', '450300', '541000', 'X', '3');
INSERT INTO `system_address` VALUES ('2216', '七星区', '450305', '450300', '541000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2217', '雁山区', '450311', '450300', '541000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2218', '阳朔县', '450321', '450300', '541900', 'Y', '3');
INSERT INTO `system_address` VALUES ('2219', '临桂区', '450322', '450300', '541100', 'L', '3');
INSERT INTO `system_address` VALUES ('2220', '灵川县', '450323', '450300', '541200', 'L', '3');
INSERT INTO `system_address` VALUES ('2221', '全州县', '450324', '450300', '541500', 'Q', '3');
INSERT INTO `system_address` VALUES ('2222', '兴安县', '450325', '450300', '541300', 'X', '3');
INSERT INTO `system_address` VALUES ('2223', '永福县', '450326', '450300', '541899', 'Y', '3');
INSERT INTO `system_address` VALUES ('2224', '灌阳县', '450327', '450300', '541600', 'G', '3');
INSERT INTO `system_address` VALUES ('2225', '龙胜各族自治县', '450328', '450300', '541700', 'L', '3');
INSERT INTO `system_address` VALUES ('2226', '资源县', '450329', '450300', '541400', 'Z', '3');
INSERT INTO `system_address` VALUES ('2227', '平乐县', '450330', '450300', '542400', 'P', '3');
INSERT INTO `system_address` VALUES ('2228', '荔浦县', '450331', '450300', '546600', 'L', '3');
INSERT INTO `system_address` VALUES ('2229', '恭城瑶族自治县', '450332', '450300', '542500', 'G', '3');
INSERT INTO `system_address` VALUES ('2230', '梧州市', '450400', '450000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('2231', '万秀区', '450403', '450400', '543000', 'W', '3');
INSERT INTO `system_address` VALUES ('2232', '蝶山区', '450404', '450400', '543000', 'D', '3');
INSERT INTO `system_address` VALUES ('2233', '长洲区', '450405', '450400', '543000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2234', '龙圩区', '450406', '450400', '543004', 'L', '3');
INSERT INTO `system_address` VALUES ('2235', '苍梧县', '450421', '450400', '543100', 'C', '3');
INSERT INTO `system_address` VALUES ('2236', '藤县', '450422', '450400', '543300', 'T', '3');
INSERT INTO `system_address` VALUES ('2237', '蒙山县', '450423', '450400', '546700', 'M', '3');
INSERT INTO `system_address` VALUES ('2238', '岑溪市', '450481', '450400', '543200', 'C', '3');
INSERT INTO `system_address` VALUES ('2239', '北海市', '450500', '450000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2240', '海城区', '450502', '450500', '536000', 'H', '3');
INSERT INTO `system_address` VALUES ('2241', '银海区', '450503', '450500', '536000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2242', '铁山港区', '450512', '450500', '536000', 'T', '3');
INSERT INTO `system_address` VALUES ('2243', '合浦县', '450521', '450500', '536100', 'H', '3');
INSERT INTO `system_address` VALUES ('2244', '防城港市', '450600', '450000', null, 'F', '2');
INSERT INTO `system_address` VALUES ('2245', '港口区', '450602', '450600', '538000', 'G', '3');
INSERT INTO `system_address` VALUES ('2246', '防城区', '450603', '450600', '538000', 'F', '3');
INSERT INTO `system_address` VALUES ('2247', '上思县', '450621', '450600', '535500', 'S', '3');
INSERT INTO `system_address` VALUES ('2248', '东兴市', '450681', '450600', '538100', 'D', '3');
INSERT INTO `system_address` VALUES ('2249', '钦州市', '450700', '450000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2250', '钦南区', '450702', '450700', '535000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2251', '钦北区', '450703', '450700', '535000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2252', '灵山县', '450721', '450700', '535400', 'L', '3');
INSERT INTO `system_address` VALUES ('2253', '浦北县', '450722', '450700', '535300', 'P', '3');
INSERT INTO `system_address` VALUES ('2254', '贵港市', '450800', '450000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2255', '港北区', '450802', '450800', '537100', 'G', '3');
INSERT INTO `system_address` VALUES ('2256', '港南区', '450803', '450800', '537100', 'G', '3');
INSERT INTO `system_address` VALUES ('2257', '覃塘区', '450804', '450800', '537100', 'T', '3');
INSERT INTO `system_address` VALUES ('2258', '平南县', '450821', '450800', '537300', 'P', '3');
INSERT INTO `system_address` VALUES ('2259', '桂平市', '450881', '450800', '537200', 'G', '3');
INSERT INTO `system_address` VALUES ('2260', '玉林市', '450900', '450000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2261', '玉州区', '450902', '450900', '537000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2262', '福绵区', '450903', '450900', '537000', 'F', '3');
INSERT INTO `system_address` VALUES ('2263', '容县', '450921', '450900', '537500', 'R', '3');
INSERT INTO `system_address` VALUES ('2264', '陆川县', '450922', '450900', '537700', 'L', '3');
INSERT INTO `system_address` VALUES ('2265', '博白县', '450923', '450900', '537600', 'B', '3');
INSERT INTO `system_address` VALUES ('2266', '兴业县', '450924', '450900', '537800', 'X', '3');
INSERT INTO `system_address` VALUES ('2267', '北流市', '450981', '450900', '537400', 'B', '3');
INSERT INTO `system_address` VALUES ('2268', '百色市', '451000', '450000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2269', '右江区', '451002', '451000', '533000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2270', '田阳县', '451021', '451000', '533600', 'T', '3');
INSERT INTO `system_address` VALUES ('2271', '田东县', '451022', '451000', '531500', 'T', '3');
INSERT INTO `system_address` VALUES ('2272', '平果县', '451023', '451000', '531400', 'P', '3');
INSERT INTO `system_address` VALUES ('2273', '德保县', '451024', '451000', '533700', 'D', '3');
INSERT INTO `system_address` VALUES ('2274', '靖西县', '451025', '451000', '533800', 'J', '3');
INSERT INTO `system_address` VALUES ('2275', '那坡县', '451026', '451000', '533900', 'N', '3');
INSERT INTO `system_address` VALUES ('2276', '凌云县', '451027', '451000', '533100', 'L', '3');
INSERT INTO `system_address` VALUES ('2277', '乐业县', '451028', '451000', '533200', 'L', '3');
INSERT INTO `system_address` VALUES ('2278', '田林县', '451029', '451000', '533300', 'T', '3');
INSERT INTO `system_address` VALUES ('2279', '西林县', '451030', '451000', '533500', 'X', '3');
INSERT INTO `system_address` VALUES ('2280', '隆林各族自治县', '451031', '451000', '533400', 'L', '3');
INSERT INTO `system_address` VALUES ('2281', '贺州市', '451100', '450000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2282', '八步区', '451102', '451100', '542800', 'B', '3');
INSERT INTO `system_address` VALUES ('2283', '平桂管理区', '451119', '451100', '542827', 'P', '3');
INSERT INTO `system_address` VALUES ('2284', '昭平县', '451121', '451100', '546800', 'Z', '3');
INSERT INTO `system_address` VALUES ('2285', '钟山县', '451122', '451100', '542600', 'Z', '3');
INSERT INTO `system_address` VALUES ('2286', '富川瑶族自治县', '451123', '451100', '542700', 'F', '3');
INSERT INTO `system_address` VALUES ('2287', '河池市', '451200', '450000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2288', '金城江区', '451202', '451200', '547000', 'J', '3');
INSERT INTO `system_address` VALUES ('2289', '南丹县', '451221', '451200', '547200', 'N', '3');
INSERT INTO `system_address` VALUES ('2290', '天峨县', '451222', '451200', '547300', 'T', '3');
INSERT INTO `system_address` VALUES ('2291', '凤山县', '451223', '451200', '547600', 'F', '3');
INSERT INTO `system_address` VALUES ('2292', '东兰县', '451224', '451200', '547400', 'D', '3');
INSERT INTO `system_address` VALUES ('2293', '罗城仫佬族自治县', '451225', '451200', '546400', 'L', '3');
INSERT INTO `system_address` VALUES ('2294', '环江毛南族自治县', '451226', '451200', '547100', 'H', '3');
INSERT INTO `system_address` VALUES ('2295', '巴马瑶族自治县', '451227', '451200', '547500', 'B', '3');
INSERT INTO `system_address` VALUES ('2296', '都安瑶族自治县', '451228', '451200', '530700', 'D', '3');
INSERT INTO `system_address` VALUES ('2297', '大化瑶族自治县', '451229', '451200', '530800', 'D', '3');
INSERT INTO `system_address` VALUES ('2298', '宜州市', '451281', '451200', '546300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2299', '来宾市', '451300', '450000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2300', '兴宾区', '451302', '451300', '546100', 'X', '3');
INSERT INTO `system_address` VALUES ('2301', '忻城县', '451321', '451300', '546200', 'X', '3');
INSERT INTO `system_address` VALUES ('2302', '象州县', '451322', '451300', '545800', 'X', '3');
INSERT INTO `system_address` VALUES ('2303', '武宣县', '451323', '451300', '545900', 'W', '3');
INSERT INTO `system_address` VALUES ('2304', '金秀瑶族自治县', '451324', '451300', '545700', 'J', '3');
INSERT INTO `system_address` VALUES ('2305', '合山市', '451381', '451300', '546500', 'H', '3');
INSERT INTO `system_address` VALUES ('2306', '崇左市', '451400', '450000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('2307', '江州区', '451402', '451400', '532200', 'J', '3');
INSERT INTO `system_address` VALUES ('2308', '扶绥县', '451421', '451400', '532100', 'F', '3');
INSERT INTO `system_address` VALUES ('2309', '宁明县', '451422', '451400', '532500', 'N', '3');
INSERT INTO `system_address` VALUES ('2310', '龙州县', '451423', '451400', '532400', 'L', '3');
INSERT INTO `system_address` VALUES ('2311', '大新县', '451424', '451400', '532300', 'D', '3');
INSERT INTO `system_address` VALUES ('2312', '天等县', '451425', '451400', '532800', 'T', '3');
INSERT INTO `system_address` VALUES ('2313', '凭祥市', '451481', '451400', '532600', 'P', '3');
INSERT INTO `system_address` VALUES ('2314', '海南', '460000', '0', null, 'H', '1');
INSERT INTO `system_address` VALUES ('2315', '海口市', '460100', '460000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2316', '秀英区', '460105', '460100', '570100', 'X', '3');
INSERT INTO `system_address` VALUES ('2317', '龙华区', '460106', '460100', '570100', 'L', '3');
INSERT INTO `system_address` VALUES ('2318', '琼山区', '460107', '460100', '571100', 'Q', '3');
INSERT INTO `system_address` VALUES ('2319', '美兰区', '460108', '460100', '570100', 'M', '3');
INSERT INTO `system_address` VALUES ('2320', '三亚市', '460200', '460000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2321', '三亚市', '460201', '460200', '572000', 'S', '3');
INSERT INTO `system_address` VALUES ('2326', '五指山市', '469001', '469000', '572200', 'W', '3');
INSERT INTO `system_address` VALUES ('2327', '琼海市', '469002', '469000', '571400', 'Q', '3');
INSERT INTO `system_address` VALUES ('2328', '儋州市', '469003', '460000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2329', '文昌市', '469005', '469000', '571300', 'W', '3');
INSERT INTO `system_address` VALUES ('2330', '万宁市', '469006', '469000', '571500', 'W', '3');
INSERT INTO `system_address` VALUES ('2331', '东方市', '469007', '469000', '572600', 'D', '3');
INSERT INTO `system_address` VALUES ('2332', '定安县', '469025', '469000', '571200', 'D', '3');
INSERT INTO `system_address` VALUES ('2333', '屯昌县', '469026', '469000', '571600', 'T', '3');
INSERT INTO `system_address` VALUES ('2334', '澄迈县', '469027', '469000', '571900', 'C', '3');
INSERT INTO `system_address` VALUES ('2335', '临高县', '469028', '469000', '571800', 'L', '3');
INSERT INTO `system_address` VALUES ('2336', '昌江黎族自治县', '469031', '469000', '572700', 'C', '3');
INSERT INTO `system_address` VALUES ('2337', '乐东黎族自治县', '469033', '469000', '572500', 'L', '3');
INSERT INTO `system_address` VALUES ('2338', '陵水黎族自治县', '469034', '469000', '572400', 'L', '3');
INSERT INTO `system_address` VALUES ('2339', '保亭黎族苗族自治县', '469035', '469000', '572300', 'B', '3');
INSERT INTO `system_address` VALUES ('2340', '琼中黎族苗族自治县', '469036', '469000', '572900', 'Q', '3');
INSERT INTO `system_address` VALUES ('2341', '重庆', '500000', '0', null, 'Z', '1');
INSERT INTO `system_address` VALUES ('2342', '重庆市', '500100', '500000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2343', '万州区', '500101', '500100', '404100', 'W', '3');
INSERT INTO `system_address` VALUES ('2344', '涪陵区', '500102', '500100', '408000', 'F', '3');
INSERT INTO `system_address` VALUES ('2345', '渝中区', '500103', '500100', '400000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2346', '大渡口区', '500104', '500100', '400000', 'D', '3');
INSERT INTO `system_address` VALUES ('2347', '江北区', '500105', '500100', '400000', 'J', '3');
INSERT INTO `system_address` VALUES ('2348', '沙坪坝区', '500106', '500100', '400000', 'S', '3');
INSERT INTO `system_address` VALUES ('2349', '九龙坡区', '500107', '500100', '400000', 'J', '3');
INSERT INTO `system_address` VALUES ('2350', '南岸区', '500108', '500100', '400000', 'N', '3');
INSERT INTO `system_address` VALUES ('2351', '北碚区', '500109', '500100', '400700', 'B', '3');
INSERT INTO `system_address` VALUES ('2352', '万盛区', '500110', '500100', '400800', 'W', '3');
INSERT INTO `system_address` VALUES ('2353', '双桥区', '500111', '500100', '400900', 'S', '3');
INSERT INTO `system_address` VALUES ('2354', '渝北区', '500112', '500100', '401120', 'Y', '3');
INSERT INTO `system_address` VALUES ('2355', '巴南区', '500113', '500100', '401320', 'B', '3');
INSERT INTO `system_address` VALUES ('2356', '黔江区', '500114', '500100', '409000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2357', '长寿区', '500115', '500100', '401220', 'Z', '3');
INSERT INTO `system_address` VALUES ('2358', '北部新区', '500199', '500100', '400000', 'B', '3');
INSERT INTO `system_address` VALUES ('2359', '两江新区', '500200', '500100', '401147', 'L', '3');
INSERT INTO `system_address` VALUES ('2360', '綦江区', '500222', '500100', '401420', 'Q', '3');
INSERT INTO `system_address` VALUES ('2361', '潼南区', '500223', '500100', '402660', 'T', '3');
INSERT INTO `system_address` VALUES ('2362', '铜梁区', '500224', '500100', '402560', 'T', '3');
INSERT INTO `system_address` VALUES ('2363', '大足区', '500225', '500100', '402360', 'D', '3');
INSERT INTO `system_address` VALUES ('2364', '荣昌区', '500226', '500100', '402460', 'R', '3');
INSERT INTO `system_address` VALUES ('2365', '璧山区', '500227', '500100', '402700', 'B', '3');
INSERT INTO `system_address` VALUES ('2366', '梁平区', '500228', '500100', '405200', 'L', '3');
INSERT INTO `system_address` VALUES ('2367', '城口县', '500229', '500200', '405900', 'C', '3');
INSERT INTO `system_address` VALUES ('2368', '丰都县', '500230', '500200', '408200', 'F', '3');
INSERT INTO `system_address` VALUES ('2369', '垫江县', '500231', '500200', '408300', 'D', '3');
INSERT INTO `system_address` VALUES ('2370', '武隆县', '500232', '500100', '408500', 'W', '3');
INSERT INTO `system_address` VALUES ('2371', '忠县', '500233', '500200', '404300', 'Z', '3');
INSERT INTO `system_address` VALUES ('2372', '开县', '500234', '500100', '405400', 'K', '3');
INSERT INTO `system_address` VALUES ('2373', '云阳县', '500235', '500200', '404500', 'Y', '3');
INSERT INTO `system_address` VALUES ('2374', '奉节县', '500236', '500200', '404600', 'F', '3');
INSERT INTO `system_address` VALUES ('2375', '巫山县', '500237', '500200', '404700', 'W', '3');
INSERT INTO `system_address` VALUES ('2376', '巫溪县', '500238', '500200', '405800', 'W', '3');
INSERT INTO `system_address` VALUES ('2377', '石柱土家族自治县', '500240', '500200', '409100', 'S', '3');
INSERT INTO `system_address` VALUES ('2378', '秀山土家族苗族自治县', '500241', '500200', '409900', 'X', '3');
INSERT INTO `system_address` VALUES ('2379', '酉阳土家族苗族自治县', '500242', '500200', '409800', 'Y', '3');
INSERT INTO `system_address` VALUES ('2380', '彭水苗族土家族自治县', '500243', '500200', '409600', 'P', '3');
INSERT INTO `system_address` VALUES ('2381', '江津区', '500381', '500100', '402260', 'J', '3');
INSERT INTO `system_address` VALUES ('2382', '合川区', '500382', '500100', '401520', 'H', '3');
INSERT INTO `system_address` VALUES ('2383', '永川区', '500383', '500100', '402160', 'Y', '3');
INSERT INTO `system_address` VALUES ('2384', '南川区', '500384', '500100', '408400', 'N', '3');
INSERT INTO `system_address` VALUES ('2385', '四川', '510000', '0', null, 'S', '1');
INSERT INTO `system_address` VALUES ('2386', '成都市', '510100', '510000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('2387', '锦江区', '510104', '510100', '610000', 'J', '3');
INSERT INTO `system_address` VALUES ('2388', '青羊区', '510105', '510100', '610000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2389', '金牛区', '510106', '510100', '610000', 'J', '3');
INSERT INTO `system_address` VALUES ('2390', '武侯区', '510107', '510100', '610000', 'W', '3');
INSERT INTO `system_address` VALUES ('2391', '成华区', '510108', '510100', '610000', 'C', '3');
INSERT INTO `system_address` VALUES ('2392', '龙泉驿区', '510112', '510100', '610100', 'L', '3');
INSERT INTO `system_address` VALUES ('2393', '青白江区', '510113', '510100', '610300', 'Q', '3');
INSERT INTO `system_address` VALUES ('2394', '新都区', '510114', '510100', '610500', 'X', '3');
INSERT INTO `system_address` VALUES ('2395', '温江区', '510115', '510100', '611130', 'W', '3');
INSERT INTO `system_address` VALUES ('2396', '天府新区', '510116', '510100', '610000', 'T', '3');
INSERT INTO `system_address` VALUES ('2397', '高新区', '510117', '510100', '610000', 'G', '3');
INSERT INTO `system_address` VALUES ('2398', '金堂县', '510121', '510100', '610400', 'J', '3');
INSERT INTO `system_address` VALUES ('2399', '双流县', '510122', '510100', '610200', 'S', '3');
INSERT INTO `system_address` VALUES ('2400', '郫县', '510124', '510100', '611730', 'P', '3');
INSERT INTO `system_address` VALUES ('2401', '大邑县', '510129', '510100', '611300', 'D', '3');
INSERT INTO `system_address` VALUES ('2402', '蒲江县', '510131', '510100', '611600', 'P', '3');
INSERT INTO `system_address` VALUES ('2403', '新津县', '510132', '510100', '611400', 'X', '3');
INSERT INTO `system_address` VALUES ('2404', '都江堰市', '510181', '510100', '611800', 'D', '3');
INSERT INTO `system_address` VALUES ('2405', '彭州市', '510182', '510100', '611900', 'P', '3');
INSERT INTO `system_address` VALUES ('2406', '邛崃市', '510183', '510100', '611500', 'Q', '3');
INSERT INTO `system_address` VALUES ('2407', '崇州市', '510184', '510100', '611200', 'C', '3');
INSERT INTO `system_address` VALUES ('2408', '自贡市', '510300', '510000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2409', '自流井区', '510302', '510300', '643000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2410', '贡井区', '510303', '510300', '643020', 'G', '3');
INSERT INTO `system_address` VALUES ('2411', '大安区', '510304', '510300', '643010', 'D', '3');
INSERT INTO `system_address` VALUES ('2412', '沿滩区', '510311', '510300', '643030', 'Y', '3');
INSERT INTO `system_address` VALUES ('2413', '荣县', '510321', '510300', '643100', 'R', '3');
INSERT INTO `system_address` VALUES ('2414', '富顺县', '510322', '510300', '643200', 'F', '3');
INSERT INTO `system_address` VALUES ('2415', '攀枝花市', '510400', '510000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('2416', '东区', '510402', '510400', '617000', 'D', '3');
INSERT INTO `system_address` VALUES ('2417', '西区', '510403', '510400', '617000', 'X', '3');
INSERT INTO `system_address` VALUES ('2418', '仁和区', '510411', '510400', '617000', 'R', '3');
INSERT INTO `system_address` VALUES ('2419', '米易县', '510421', '510400', '617200', 'M', '3');
INSERT INTO `system_address` VALUES ('2420', '盐边县', '510422', '510400', '617100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2421', '泸州市', '510500', '510000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2422', '江阳区', '510502', '510500', '646000', 'J', '3');
INSERT INTO `system_address` VALUES ('2423', '纳溪区', '510503', '510500', '646300', 'N', '3');
INSERT INTO `system_address` VALUES ('2424', '龙马潭区', '510504', '510500', '646000', 'L', '3');
INSERT INTO `system_address` VALUES ('2425', '泸县', '510521', '510500', '646100', 'L', '3');
INSERT INTO `system_address` VALUES ('2426', '合江县', '510522', '510500', '646200', 'H', '3');
INSERT INTO `system_address` VALUES ('2427', '叙永县', '510524', '510500', '646400', 'X', '3');
INSERT INTO `system_address` VALUES ('2428', '古蔺县', '510525', '510500', '646500', 'G', '3');
INSERT INTO `system_address` VALUES ('2429', '德阳市', '510600', '510000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2430', '旌阳区', '510603', '510600', '618000', 'J', '3');
INSERT INTO `system_address` VALUES ('2431', '中江县', '510623', '510600', '618100', 'Z', '3');
INSERT INTO `system_address` VALUES ('2432', '罗江县', '510626', '510600', '618500', 'L', '3');
INSERT INTO `system_address` VALUES ('2433', '广汉市', '510681', '510600', '618300', 'G', '3');
INSERT INTO `system_address` VALUES ('2434', '什邡市', '510682', '510600', '618400', 'S', '3');
INSERT INTO `system_address` VALUES ('2435', '绵竹市', '510683', '510600', '618200', 'M', '3');
INSERT INTO `system_address` VALUES ('2436', '绵阳市', '510700', '510000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('2437', '涪城区', '510703', '510700', '621000', 'F', '3');
INSERT INTO `system_address` VALUES ('2438', '游仙区', '510704', '510700', '621000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2439', '三台县', '510722', '510700', '621100', 'S', '3');
INSERT INTO `system_address` VALUES ('2440', '盐亭县', '510723', '510700', '621600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2441', '安县', '510724', '510700', '622650', 'A', '3');
INSERT INTO `system_address` VALUES ('2442', '梓潼县', '510725', '510700', '622150', 'Z', '3');
INSERT INTO `system_address` VALUES ('2443', '北川羌族自治县', '510726', '510700', '622750', 'B', '3');
INSERT INTO `system_address` VALUES ('2444', '平武县', '510727', '510700', '622550', 'P', '3');
INSERT INTO `system_address` VALUES ('2445', '高新区', '510751', '510700', '621000', 'G', '3');
INSERT INTO `system_address` VALUES ('2446', '江油市', '510781', '510700', '621700', 'J', '3');
INSERT INTO `system_address` VALUES ('2447', '广元市', '510800', '510000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2448', '利州区', '510802', '510800', '628000', 'L', '3');
INSERT INTO `system_address` VALUES ('2449', '昭化区', '510811', '510800', '628000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2450', '朝天区', '510812', '510800', '628000', 'C', '3');
INSERT INTO `system_address` VALUES ('2451', '旺苍县', '510821', '510800', '628200', 'W', '3');
INSERT INTO `system_address` VALUES ('2452', '青川县', '510822', '510800', '628100', 'Q', '3');
INSERT INTO `system_address` VALUES ('2453', '剑阁县', '510823', '510800', '628300', 'J', '3');
INSERT INTO `system_address` VALUES ('2454', '苍溪县', '510824', '510800', '628400', 'C', '3');
INSERT INTO `system_address` VALUES ('2455', '遂宁市', '510900', '510000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2456', '船山区', '510903', '510900', '629000', 'C', '3');
INSERT INTO `system_address` VALUES ('2457', '安居区', '510904', '510900', '629000', 'A', '3');
INSERT INTO `system_address` VALUES ('2458', '蓬溪县', '510921', '510900', '629100', 'P', '3');
INSERT INTO `system_address` VALUES ('2459', '射洪县', '510922', '510900', '629200', 'S', '3');
INSERT INTO `system_address` VALUES ('2460', '大英县', '510923', '510900', '629300', 'D', '3');
INSERT INTO `system_address` VALUES ('2461', '内江市', '511000', '510000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('2462', '市中区', '511002', '511000', '641000', 'S', '3');
INSERT INTO `system_address` VALUES ('2463', '东兴区', '511011', '511000', '641100', 'D', '3');
INSERT INTO `system_address` VALUES ('2464', '威远县', '511024', '511000', '642450', 'W', '3');
INSERT INTO `system_address` VALUES ('2465', '资中县', '511025', '511000', '641200', 'Z', '3');
INSERT INTO `system_address` VALUES ('2466', '隆昌县', '511028', '511000', '642150', 'L', '3');
INSERT INTO `system_address` VALUES ('2467', '乐山市', '511100', '510000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2468', '市中区', '511102', '511100', '614000', 'S', '3');
INSERT INTO `system_address` VALUES ('2469', '沙湾区', '511111', '511100', '614900', 'S', '3');
INSERT INTO `system_address` VALUES ('2470', '五通桥区', '511112', '511100', '614800', 'W', '3');
INSERT INTO `system_address` VALUES ('2471', '金口河区', '511113', '511100', '614700', 'J', '3');
INSERT INTO `system_address` VALUES ('2472', '犍为县', '511123', '511100', '614400', 'J', '3');
INSERT INTO `system_address` VALUES ('2473', '井研县', '511124', '511100', '613100', 'J', '3');
INSERT INTO `system_address` VALUES ('2474', '夹江县', '511126', '511100', '614100', 'J', '3');
INSERT INTO `system_address` VALUES ('2475', '沐川县', '511129', '511100', '614500', 'M', '3');
INSERT INTO `system_address` VALUES ('2476', '峨边彝族自治县', '511132', '511100', '614300', 'E', '3');
INSERT INTO `system_address` VALUES ('2477', '马边彝族自治县', '511133', '511100', '614600', 'M', '3');
INSERT INTO `system_address` VALUES ('2478', '峨眉山市', '511181', '511100', '614200', 'E', '3');
INSERT INTO `system_address` VALUES ('2479', '南充市', '511300', '510000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('2480', '顺庆区', '511302', '511300', '637000', 'S', '3');
INSERT INTO `system_address` VALUES ('2481', '高坪区', '511303', '511300', '637100', 'G', '3');
INSERT INTO `system_address` VALUES ('2482', '嘉陵区', '511304', '511300', '637500', 'J', '3');
INSERT INTO `system_address` VALUES ('2483', '南部县', '511321', '511300', '637300', 'N', '3');
INSERT INTO `system_address` VALUES ('2484', '营山县', '511322', '511300', '637700', 'Y', '3');
INSERT INTO `system_address` VALUES ('2485', '蓬安县', '511323', '511300', '637800', 'P', '3');
INSERT INTO `system_address` VALUES ('2486', '仪陇县', '511324', '511300', '637600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2487', '西充县', '511325', '511300', '637200', 'X', '3');
INSERT INTO `system_address` VALUES ('2488', '阆中市', '511381', '511300', '637400', 'L', '3');
INSERT INTO `system_address` VALUES ('2489', '眉山市', '511400', '510000', null, 'M', '2');
INSERT INTO `system_address` VALUES ('2490', '东坡区', '511402', '511400', '620000', 'D', '3');
INSERT INTO `system_address` VALUES ('2491', '仁寿县', '511421', '511400', '620500', 'R', '3');
INSERT INTO `system_address` VALUES ('2492', '彭山区', '511422', '511400', '620800', 'P', '3');
INSERT INTO `system_address` VALUES ('2493', '洪雅县', '511423', '511400', '620300', 'H', '3');
INSERT INTO `system_address` VALUES ('2494', '丹棱县', '511424', '511400', '620200', 'D', '3');
INSERT INTO `system_address` VALUES ('2495', '青神县', '511425', '511400', '620400', 'Q', '3');
INSERT INTO `system_address` VALUES ('2496', '宜宾市', '511500', '510000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2497', '翠屏区', '511502', '511500', '644000', 'C', '3');
INSERT INTO `system_address` VALUES ('2498', '宜宾县', '511521', '511500', '644600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2499', '南溪区', '511522', '511500', '644100', 'N', '3');
INSERT INTO `system_address` VALUES ('2500', '江安县', '511523', '511500', '644200', 'J', '3');
INSERT INTO `system_address` VALUES ('2501', '长宁县', '511524', '511500', '644300', 'Z', '3');
INSERT INTO `system_address` VALUES ('2502', '高县', '511525', '511500', '645150', 'G', '3');
INSERT INTO `system_address` VALUES ('2503', '珙县', '511526', '511500', '644500', 'G', '3');
INSERT INTO `system_address` VALUES ('2504', '筠连县', '511527', '511500', '645250', 'Y', '3');
INSERT INTO `system_address` VALUES ('2505', '兴文县', '511528', '511500', '644400', 'X', '3');
INSERT INTO `system_address` VALUES ('2506', '屏山县', '511529', '511500', '645350', 'P', '3');
INSERT INTO `system_address` VALUES ('2507', '广安市', '511600', '510000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2508', '广安区', '511602', '511600', '638550', 'G', '3');
INSERT INTO `system_address` VALUES ('2509', '前锋区', '511603', '511600', '638019', 'Q', '3');
INSERT INTO `system_address` VALUES ('2510', '岳池县', '511621', '511600', '638300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2511', '武胜县', '511622', '511600', '638400', 'W', '3');
INSERT INTO `system_address` VALUES ('2512', '邻水县', '511623', '511600', '638500', 'L', '3');
INSERT INTO `system_address` VALUES ('2513', '华蓥市', '511681', '511600', '409800', 'H', '3');
INSERT INTO `system_address` VALUES ('2514', '市辖区', '511682', '511600', '638500', 'S', '3');
INSERT INTO `system_address` VALUES ('2515', '达州市', '511700', '510000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2516', '通川区', '511702', '511700', '635000', 'T', '3');
INSERT INTO `system_address` VALUES ('2517', '达川区', '511721', '511700', '635000', 'D', '3');
INSERT INTO `system_address` VALUES ('2518', '宣汉县', '511722', '511700', '636150', 'X', '3');
INSERT INTO `system_address` VALUES ('2519', '开江县', '511723', '511700', '636250', 'K', '3');
INSERT INTO `system_address` VALUES ('2520', '大竹县', '511724', '511700', '635100', 'D', '3');
INSERT INTO `system_address` VALUES ('2521', '渠县', '511725', '511700', '635200', 'Q', '3');
INSERT INTO `system_address` VALUES ('2522', '万源市', '511781', '511700', '636350', 'W', '3');
INSERT INTO `system_address` VALUES ('2523', '雅安市', '511800', '510000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2524', '雨城区', '511802', '511800', '625000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2525', '名山区', '511821', '511800', '625100', 'M', '3');
INSERT INTO `system_address` VALUES ('2526', '荥经县', '511822', '511800', '625200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2527', '汉源县', '511823', '511800', '625300', 'H', '3');
INSERT INTO `system_address` VALUES ('2528', '石棉县', '511824', '511800', '625400', 'S', '3');
INSERT INTO `system_address` VALUES ('2529', '天全县', '511825', '511800', '625500', 'T', '3');
INSERT INTO `system_address` VALUES ('2530', '芦山县', '511826', '511800', '625600', 'L', '3');
INSERT INTO `system_address` VALUES ('2531', '宝兴县', '511827', '511800', '625700', 'B', '3');
INSERT INTO `system_address` VALUES ('2532', '巴中市', '511900', '510000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2533', '巴州区', '511902', '511900', '636600', 'B', '3');
INSERT INTO `system_address` VALUES ('2534', '恩阳区', '511903', '511900', '636063', 'E', '3');
INSERT INTO `system_address` VALUES ('2535', '通江县', '511921', '511900', '636700', 'T', '3');
INSERT INTO `system_address` VALUES ('2536', '南江县', '511922', '511900', '635600', 'N', '3');
INSERT INTO `system_address` VALUES ('2537', '平昌县', '511923', '511900', '636400', 'P', '3');
INSERT INTO `system_address` VALUES ('2538', '资阳市', '512000', '510000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2539', '雁江区', '512002', '512000', '641300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2540', '安岳县', '512021', '512000', '642350', 'A', '3');
INSERT INTO `system_address` VALUES ('2541', '乐至县', '512022', '512000', '641500', 'L', '3');
INSERT INTO `system_address` VALUES ('2542', '简阳市', '512081', '512000', '641400', 'J', '3');
INSERT INTO `system_address` VALUES ('2543', '阿坝藏族羌族自治州', '513200', '510000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('2544', '汶川县', '513221', '513200', '623000', 'W', '3');
INSERT INTO `system_address` VALUES ('2545', '理县', '513222', '513200', '623100', 'L', '3');
INSERT INTO `system_address` VALUES ('2546', '茂县', '513223', '513200', '623200', 'M', '3');
INSERT INTO `system_address` VALUES ('2547', '松潘县', '513224', '513200', '623300', 'S', '3');
INSERT INTO `system_address` VALUES ('2548', '九寨沟县', '513225', '513200', '623400', 'J', '3');
INSERT INTO `system_address` VALUES ('2549', '金川县', '513226', '513200', '624100', 'J', '3');
INSERT INTO `system_address` VALUES ('2550', '小金县', '513227', '513200', '624200', 'X', '3');
INSERT INTO `system_address` VALUES ('2551', '黑水县', '513228', '513200', '623500', 'H', '3');
INSERT INTO `system_address` VALUES ('2552', '马尔康县', '513229', '513200', '624000', 'M', '3');
INSERT INTO `system_address` VALUES ('2553', '壤塘县', '513230', '513200', '624300', 'R', '3');
INSERT INTO `system_address` VALUES ('2554', '阿坝县', '513231', '513200', '624600', 'A', '3');
INSERT INTO `system_address` VALUES ('2555', '若尔盖县', '513232', '513200', '624500', 'R', '3');
INSERT INTO `system_address` VALUES ('2556', '红原县', '513233', '513200', '624400', 'H', '3');
INSERT INTO `system_address` VALUES ('2557', '甘孜藏族自治州', '513300', '510000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2558', '康定区', '513321', '513300', '626000', 'K', '3');
INSERT INTO `system_address` VALUES ('2559', '泸定县', '513322', '513300', '626100', 'L', '3');
INSERT INTO `system_address` VALUES ('2560', '丹巴县', '513323', '513300', '626300', 'D', '3');
INSERT INTO `system_address` VALUES ('2561', '九龙县', '513324', '513300', '616200', 'J', '3');
INSERT INTO `system_address` VALUES ('2562', '雅江县', '513325', '513300', '627450', 'Y', '3');
INSERT INTO `system_address` VALUES ('2563', '道孚县', '513326', '513300', '626400', 'D', '3');
INSERT INTO `system_address` VALUES ('2564', '炉霍县', '513327', '513300', '626500', 'L', '3');
INSERT INTO `system_address` VALUES ('2565', '甘孜县', '513328', '513300', '626700', 'G', '3');
INSERT INTO `system_address` VALUES ('2566', '新龙县', '513329', '513300', '626800', 'X', '3');
INSERT INTO `system_address` VALUES ('2567', '德格县', '513330', '513300', '627250', 'D', '3');
INSERT INTO `system_address` VALUES ('2568', '白玉县', '513331', '513300', '627150', 'B', '3');
INSERT INTO `system_address` VALUES ('2569', '石渠县', '513332', '513300', '627350', 'S', '3');
INSERT INTO `system_address` VALUES ('2570', '色达县', '513333', '513300', '626600', 'S', '3');
INSERT INTO `system_address` VALUES ('2571', '理塘县', '513334', '513300', '624300', 'L', '3');
INSERT INTO `system_address` VALUES ('2572', '巴塘县', '513335', '513300', '627650', 'B', '3');
INSERT INTO `system_address` VALUES ('2573', '乡城县', '513336', '513300', '627850', 'X', '3');
INSERT INTO `system_address` VALUES ('2574', '稻城县', '513337', '513300', '627750', 'D', '3');
INSERT INTO `system_address` VALUES ('2575', '得荣县', '513338', '513300', '627950', 'D', '3');
INSERT INTO `system_address` VALUES ('2576', '凉山彝族自治州', '513400', '510000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2577', '西昌市', '513401', '513400', '615000', 'X', '3');
INSERT INTO `system_address` VALUES ('2578', '木里藏族自治县', '513422', '513400', '615800', 'M', '3');
INSERT INTO `system_address` VALUES ('2579', '盐源县', '513423', '513400', '615700', 'Y', '3');
INSERT INTO `system_address` VALUES ('2580', '德昌县', '513424', '513400', '615500', 'D', '3');
INSERT INTO `system_address` VALUES ('2581', '会理县', '513425', '513400', '615100', 'H', '3');
INSERT INTO `system_address` VALUES ('2582', '会东县', '513426', '513400', '615200', 'H', '3');
INSERT INTO `system_address` VALUES ('2583', '宁南县', '513427', '513400', '615400', 'N', '3');
INSERT INTO `system_address` VALUES ('2584', '普格县', '513428', '513400', '615300', 'P', '3');
INSERT INTO `system_address` VALUES ('2585', '布拖县', '513429', '513400', '615350', 'B', '3');
INSERT INTO `system_address` VALUES ('2586', '金阳县', '513430', '513400', '616250', 'J', '3');
INSERT INTO `system_address` VALUES ('2587', '昭觉县', '513431', '513400', '616150', 'Z', '3');
INSERT INTO `system_address` VALUES ('2588', '喜德县', '513432', '513400', '616750', 'X', '3');
INSERT INTO `system_address` VALUES ('2589', '冕宁县', '513433', '513400', '615600', 'M', '3');
INSERT INTO `system_address` VALUES ('2590', '越西县', '513434', '513400', '616650', 'Y', '3');
INSERT INTO `system_address` VALUES ('2591', '甘洛县', '513435', '513400', '616850', 'G', '3');
INSERT INTO `system_address` VALUES ('2592', '美姑县', '513436', '513400', '616450', 'M', '3');
INSERT INTO `system_address` VALUES ('2593', '雷波县', '513437', '513400', '616550', 'L', '3');
INSERT INTO `system_address` VALUES ('2594', '贵州', '520000', '0', null, 'G', '1');
INSERT INTO `system_address` VALUES ('2595', '贵阳市', '520100', '520000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('2596', '铜仁市', '520102', '520100', '554300', 'T', '3');
INSERT INTO `system_address` VALUES ('2597', '云岩区', '520103', '520100', '550000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2598', '花溪区', '520111', '520100', '550000', 'H', '3');
INSERT INTO `system_address` VALUES ('2599', '乌当区', '520112', '520100', '550000', 'W', '3');
INSERT INTO `system_address` VALUES ('2600', '白云区', '520113', '520100', '550000', 'B', '3');
INSERT INTO `system_address` VALUES ('2601', '小河区', '520114', '520100', '550000', 'X', '3');
INSERT INTO `system_address` VALUES ('2602', '开阳县', '520121', '520100', '550300', 'K', '3');
INSERT INTO `system_address` VALUES ('2603', '息烽县', '520122', '520100', '551100', 'X', '3');
INSERT INTO `system_address` VALUES ('2604', '修文县', '520123', '520100', '550200', 'X', '3');
INSERT INTO `system_address` VALUES ('2605', '观山湖区', '520151', '520100', '550081', 'G', '3');
INSERT INTO `system_address` VALUES ('2606', '清镇市', '520181', '520100', '551400', 'Q', '3');
INSERT INTO `system_address` VALUES ('2607', '六盘水市', '520200', '520000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2608', '钟山区', '520201', '520200', '553000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2609', '六枝特区', '520203', '520200', '553400', 'L', '3');
INSERT INTO `system_address` VALUES ('2610', '水城县', '520221', '520200', '553600', 'S', '3');
INSERT INTO `system_address` VALUES ('2611', '盘县', '520222', '520200', '553537', 'P', '3');
INSERT INTO `system_address` VALUES ('2612', '遵义市', '520300', '520000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2613', '红花岗区', '520302', '520300', '563000', 'H', '3');
INSERT INTO `system_address` VALUES ('2614', '汇川区', '520303', '520300', '563000', 'H', '3');
INSERT INTO `system_address` VALUES ('2615', '遵义县', '520321', '520300', '563100', 'Z', '3');
INSERT INTO `system_address` VALUES ('2616', '桐梓县', '520322', '520300', '563200', 'T', '3');
INSERT INTO `system_address` VALUES ('2617', '绥阳县', '520323', '520300', '563300', 'S', '3');
INSERT INTO `system_address` VALUES ('2618', '正安县', '520324', '520300', '563400', 'Z', '3');
INSERT INTO `system_address` VALUES ('2619', '道真仡佬族苗族自治县', '520325', '520300', '563500', 'D', '3');
INSERT INTO `system_address` VALUES ('2620', '务川仡佬族苗族自治县', '520326', '520300', '564300', 'W', '3');
INSERT INTO `system_address` VALUES ('2621', '凤冈县', '520327', '520300', '564200', 'F', '3');
INSERT INTO `system_address` VALUES ('2622', '湄潭县', '520328', '520300', '564100', 'M', '3');
INSERT INTO `system_address` VALUES ('2623', '余庆县', '520329', '520300', '564400', 'Y', '3');
INSERT INTO `system_address` VALUES ('2624', '习水县', '520330', '520300', '564600', 'X', '3');
INSERT INTO `system_address` VALUES ('2625', '赤水市', '520381', '520300', '564700', 'C', '3');
INSERT INTO `system_address` VALUES ('2626', '仁怀市', '520382', '520300', '564500', 'R', '3');
INSERT INTO `system_address` VALUES ('2627', '安顺市', '520400', '520000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('2628', '西秀区', '520402', '520400', '561000', 'X', '3');
INSERT INTO `system_address` VALUES ('2629', '平坝区', '520421', '520400', '561100', 'P', '3');
INSERT INTO `system_address` VALUES ('2630', '普定县', '520422', '520400', '562100', 'P', '3');
INSERT INTO `system_address` VALUES ('2631', '镇宁布依族苗族自治县', '520423', '520400', '561200', 'Z', '3');
INSERT INTO `system_address` VALUES ('2632', '关岭布依族苗族自治县', '520424', '520400', '561300', 'G', '3');
INSERT INTO `system_address` VALUES ('2633', '紫云苗族布依族自治县', '520425', '520400', '550800', 'Z', '3');
INSERT INTO `system_address` VALUES ('2634', '铜仁市', '522200', '520000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('2635', '碧江区', '522201', '522200', '554300', 'B', '3');
INSERT INTO `system_address` VALUES ('2636', '江口县', '522222', '522200', '554400', 'J', '3');
INSERT INTO `system_address` VALUES ('2637', '玉屏侗族自治县', '522223', '522200', '554000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2638', '石阡县', '522224', '522200', '555100', 'S', '3');
INSERT INTO `system_address` VALUES ('2639', '思南县', '522225', '522200', '565100', 'S', '3');
INSERT INTO `system_address` VALUES ('2640', '印江土家族苗族自治县', '522226', '522200', '555200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2641', '德江县', '522227', '522200', '565200', 'D', '3');
INSERT INTO `system_address` VALUES ('2642', '沿河土家族自治县', '522228', '522200', '565300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2643', '松桃苗族自治县', '522229', '522200', '554100', 'S', '3');
INSERT INTO `system_address` VALUES ('2644', '万山区', '522230', '522200', '554200', 'W', '3');
INSERT INTO `system_address` VALUES ('2645', '黔西南布依族苗族自治州', '522300', '520000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2646', '兴义市', '522301', '522300', '562400', 'X', '3');
INSERT INTO `system_address` VALUES ('2647', '兴仁县', '522322', '522300', '562300', 'X', '3');
INSERT INTO `system_address` VALUES ('2648', '普安县', '522323', '522300', '561500', 'P', '3');
INSERT INTO `system_address` VALUES ('2649', '晴隆县', '522324', '522300', '561400', 'Q', '3');
INSERT INTO `system_address` VALUES ('2650', '贞丰县', '522325', '522300', '562200', 'Z', '3');
INSERT INTO `system_address` VALUES ('2651', '望谟县', '522326', '522300', '552300', 'W', '3');
INSERT INTO `system_address` VALUES ('2652', '册亨县', '522327', '522300', '552200', 'C', '3');
INSERT INTO `system_address` VALUES ('2653', '安龙县', '522328', '522300', '552400', 'A', '3');
INSERT INTO `system_address` VALUES ('2654', '毕节市', '522400', '520000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2655', '七星关区', '520502', '522400', '551700', 'Q', '3');
INSERT INTO `system_address` VALUES ('2656', '大方县', '520521', '522400', '551600', 'D', '3');
INSERT INTO `system_address` VALUES ('2657', '黔西县', '520522', '522400', '551500', 'Q', '3');
INSERT INTO `system_address` VALUES ('2658', '金沙县', '520523', '522400', '551800', 'J', '3');
INSERT INTO `system_address` VALUES ('2659', '织金县', '520524', '522400', '552100', 'Z', '3');
INSERT INTO `system_address` VALUES ('2660', '纳雍县', '520525', '522400', '553300', 'N', '3');
INSERT INTO `system_address` VALUES ('2661', '威宁彝族回族苗族自治县', '520526', '522400', '553100', 'W', '3');
INSERT INTO `system_address` VALUES ('2662', '赫章县', '520527', '522400', '553200', 'H', '3');
INSERT INTO `system_address` VALUES ('2663', '黔东南苗族侗族自治州', '522600', '520000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2664', '凯里市', '522601', '522600', '556000', 'K', '3');
INSERT INTO `system_address` VALUES ('2665', '黄平县', '522622', '522600', '556100', 'H', '3');
INSERT INTO `system_address` VALUES ('2666', '施秉县', '522623', '522600', '556200', 'S', '3');
INSERT INTO `system_address` VALUES ('2667', '三穗县', '522624', '522600', '556500', 'S', '3');
INSERT INTO `system_address` VALUES ('2668', '镇远县', '522625', '522600', '557700', 'Z', '3');
INSERT INTO `system_address` VALUES ('2669', '岑巩县', '522626', '522600', '557800', 'C', '3');
INSERT INTO `system_address` VALUES ('2670', '天柱县', '522627', '522600', '556600', 'T', '3');
INSERT INTO `system_address` VALUES ('2671', '锦屏县', '522628', '522600', '556700', 'J', '3');
INSERT INTO `system_address` VALUES ('2672', '剑河县', '522629', '522600', '556400', 'J', '3');
INSERT INTO `system_address` VALUES ('2673', '台江县', '522630', '522600', '556300', 'T', '3');
INSERT INTO `system_address` VALUES ('2674', '黎平县', '522631', '522600', '557300', 'L', '3');
INSERT INTO `system_address` VALUES ('2675', '榕江县', '522632', '522600', '557200', 'R', '3');
INSERT INTO `system_address` VALUES ('2676', '从江县', '522633', '522600', '557400', 'C', '3');
INSERT INTO `system_address` VALUES ('2677', '雷山县', '522634', '522600', '557100', 'L', '3');
INSERT INTO `system_address` VALUES ('2678', '麻江县', '522635', '522600', '557600', 'M', '3');
INSERT INTO `system_address` VALUES ('2679', '丹寨县', '522636', '522600', '557500', 'D', '3');
INSERT INTO `system_address` VALUES ('2680', '黔南布依族苗族自治州', '522700', '520000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2681', '都匀市', '522701', '522700', '558000', 'D', '3');
INSERT INTO `system_address` VALUES ('2682', '福泉市', '522702', '522700', '550500', 'F', '3');
INSERT INTO `system_address` VALUES ('2683', '荔波县', '522722', '522700', '558400', 'L', '3');
INSERT INTO `system_address` VALUES ('2684', '贵定县', '522723', '522700', '551300', 'G', '3');
INSERT INTO `system_address` VALUES ('2685', '瓮安县', '522725', '522700', '550400', 'W', '3');
INSERT INTO `system_address` VALUES ('2686', '独山县', '522726', '522700', '558200', 'D', '3');
INSERT INTO `system_address` VALUES ('2687', '平塘县', '522727', '522700', '558300', 'P', '3');
INSERT INTO `system_address` VALUES ('2688', '罗甸县', '522728', '522700', '550100', 'L', '3');
INSERT INTO `system_address` VALUES ('2689', '长顺县', '522729', '522700', '550700', 'Z', '3');
INSERT INTO `system_address` VALUES ('2690', '龙里县', '522730', '522700', '551200', 'L', '3');
INSERT INTO `system_address` VALUES ('2691', '惠水县', '522731', '522700', '550600', 'H', '3');
INSERT INTO `system_address` VALUES ('2692', '三都水族自治县', '522732', '522700', '558100', 'S', '3');
INSERT INTO `system_address` VALUES ('2693', '云南', '530000', '0', null, 'Y', '1');
INSERT INTO `system_address` VALUES ('2694', '昆明市', '530100', '530000', null, 'K', '2');
INSERT INTO `system_address` VALUES ('2695', '五华区', '530102', '530100', '650000', 'W', '3');
INSERT INTO `system_address` VALUES ('2696', '盘龙区', '530103', '530100', '650000', 'P', '3');
INSERT INTO `system_address` VALUES ('2697', '官渡区', '530111', '530100', '650200', 'G', '3');
INSERT INTO `system_address` VALUES ('2698', '西山区', '530112', '530100', '650100', 'X', '3');
INSERT INTO `system_address` VALUES ('2699', '东川区', '530113', '530100', '654100', 'D', '3');
INSERT INTO `system_address` VALUES ('2700', '呈贡区', '530114', '530100', '650500', 'C', '3');
INSERT INTO `system_address` VALUES ('2701', '晋宁县', '530122', '530100', '650600', 'J', '3');
INSERT INTO `system_address` VALUES ('2702', '富民县', '530124', '530100', '650400', 'F', '3');
INSERT INTO `system_address` VALUES ('2703', '宜良县', '530125', '530100', '652100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2704', '石林彝族自治县', '530126', '530100', '652200', 'S', '3');
INSERT INTO `system_address` VALUES ('2705', '嵩明县', '530127', '530100', '651700', 'S', '3');
INSERT INTO `system_address` VALUES ('2706', '禄劝彝族苗族自治县', '530128', '530100', '651500', 'L', '3');
INSERT INTO `system_address` VALUES ('2707', '寻甸回族彝族自治县', '530129', '530100', '655200', 'X', '3');
INSERT INTO `system_address` VALUES ('2708', '安宁市', '530181', '530100', '650300', 'A', '3');
INSERT INTO `system_address` VALUES ('2709', '曲靖市', '530300', '530000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('2710', '麒麟区', '530302', '530300', '655000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2711', '马龙县', '530321', '530300', '655100', 'M', '3');
INSERT INTO `system_address` VALUES ('2712', '陆良县', '530322', '530300', '655699', 'L', '3');
INSERT INTO `system_address` VALUES ('2713', '师宗县', '530323', '530300', '655700', 'S', '3');
INSERT INTO `system_address` VALUES ('2714', '罗平县', '530324', '530300', '655800', 'L', '3');
INSERT INTO `system_address` VALUES ('2715', '富源县', '530325', '530300', '655500', 'F', '3');
INSERT INTO `system_address` VALUES ('2716', '会泽县', '530326', '530300', '654200', 'H', '3');
INSERT INTO `system_address` VALUES ('2717', '沾益县', '530328', '530300', '655331', 'Z', '3');
INSERT INTO `system_address` VALUES ('2718', '宣威市', '530381', '530300', '655400', 'X', '3');
INSERT INTO `system_address` VALUES ('2719', '玉溪市', '530400', '530000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2720', '红塔区', '530402', '530400', '653100', 'H', '3');
INSERT INTO `system_address` VALUES ('2721', '江川县', '530421', '530400', '652600', 'J', '3');
INSERT INTO `system_address` VALUES ('2722', '澄江县', '530422', '530400', '652500', 'C', '3');
INSERT INTO `system_address` VALUES ('2723', '通海县', '530423', '530400', '652700', 'T', '3');
INSERT INTO `system_address` VALUES ('2724', '华宁县', '530424', '530400', '652800', 'H', '3');
INSERT INTO `system_address` VALUES ('2725', '易门县', '530425', '530400', '651100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2726', '峨山彝族自治县', '530426', '530400', '653200', 'E', '3');
INSERT INTO `system_address` VALUES ('2727', '新平彝族傣族自治县', '530427', '530400', '653400', 'X', '3');
INSERT INTO `system_address` VALUES ('2728', '元江哈尼族彝族傣族自治县', '530428', '530400', '653300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2729', '保山市', '530500', '530000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2730', '隆阳区', '530502', '530500', '678000', 'L', '3');
INSERT INTO `system_address` VALUES ('2731', '施甸县', '530521', '530500', '678200', 'S', '3');
INSERT INTO `system_address` VALUES ('2732', '腾冲县', '530522', '530500', '679100', 'T', '3');
INSERT INTO `system_address` VALUES ('2733', '龙陵县', '530523', '530500', '678300', 'L', '3');
INSERT INTO `system_address` VALUES ('2734', '昌宁县', '530524', '530500', '678100', 'C', '3');
INSERT INTO `system_address` VALUES ('2735', '昭通市', '530600', '530000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('2736', '昭阳区', '530602', '530600', '657000', 'Z', '3');
INSERT INTO `system_address` VALUES ('2737', '鲁甸县', '530621', '530600', '657100', 'L', '3');
INSERT INTO `system_address` VALUES ('2738', '巧家县', '530622', '530600', '654600', 'Q', '3');
INSERT INTO `system_address` VALUES ('2739', '盐津县', '530623', '530600', '657500', 'Y', '3');
INSERT INTO `system_address` VALUES ('2740', '大关县', '530624', '530600', '657400', 'D', '3');
INSERT INTO `system_address` VALUES ('2741', '永善县', '530625', '530600', '657300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2742', '绥江县', '530626', '530600', '657700', 'S', '3');
INSERT INTO `system_address` VALUES ('2743', '镇雄县', '530627', '530600', '657200', 'Z', '3');
INSERT INTO `system_address` VALUES ('2744', '彝良县', '530628', '530600', '657600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2745', '威信县', '530629', '530600', '657900', 'W', '3');
INSERT INTO `system_address` VALUES ('2746', '水富县', '530630', '530600', '657800', 'S', '3');
INSERT INTO `system_address` VALUES ('2747', '丽江市', '530700', '530000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2748', '古城区', '530702', '530700', '674100', 'G', '3');
INSERT INTO `system_address` VALUES ('2749', '玉龙纳西族自治县', '530721', '530700', '674100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2750', '永胜县', '530722', '530700', '674200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2751', '华坪县', '530723', '530700', '674800', 'H', '3');
INSERT INTO `system_address` VALUES ('2752', '宁蒗彝族自治县', '530724', '530700', '674300', 'N', '3');
INSERT INTO `system_address` VALUES ('2753', '普洱市', '530800', '530000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('2754', '思茅区', '530802', '530800', '665000', 'S', '3');
INSERT INTO `system_address` VALUES ('2755', '宁洱哈尼族彝族自治县', '530821', '530800', '665000', 'N', '3');
INSERT INTO `system_address` VALUES ('2756', '墨江哈尼族自治县', '530822', '530800', '654800', 'M', '3');
INSERT INTO `system_address` VALUES ('2757', '景东彝族自治县', '530823', '530800', '676200', 'J', '3');
INSERT INTO `system_address` VALUES ('2758', '景谷傣族彝族自治县', '530824', '530800', '666400', 'J', '3');
INSERT INTO `system_address` VALUES ('2759', '镇沅彝族哈尼族拉祜族自治县', '530825', '530800', '666500', 'Z', '3');
INSERT INTO `system_address` VALUES ('2760', '江城哈尼族彝族自治县', '530826', '530800', '665900', 'J', '3');
INSERT INTO `system_address` VALUES ('2761', '孟连傣族拉祜族佤族自治县', '530827', '530800', '665800', 'M', '3');
INSERT INTO `system_address` VALUES ('2762', '澜沧拉祜族自治县', '530828', '530800', '665600', 'L', '3');
INSERT INTO `system_address` VALUES ('2763', '西盟佤族自治县', '530829', '530800', '665700', 'X', '3');
INSERT INTO `system_address` VALUES ('2764', '临沧市', '530900', '530000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2765', '临翔区', '530902', '530900', '677000', 'L', '3');
INSERT INTO `system_address` VALUES ('2766', '凤庆县', '530921', '530900', '675900', 'F', '3');
INSERT INTO `system_address` VALUES ('2767', '云县', '530922', '530900', '675800', 'Y', '3');
INSERT INTO `system_address` VALUES ('2768', '永德县', '530923', '530900', '677600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2769', '镇康县', '530924', '530900', '677700', 'Z', '3');
INSERT INTO `system_address` VALUES ('2770', '双江拉祜族佤族布朗族傣族自治县', '530925', '530900', '677300', 'S', '3');
INSERT INTO `system_address` VALUES ('2771', '耿马傣族佤族自治县', '530926', '530900', '677500', 'G', '3');
INSERT INTO `system_address` VALUES ('2772', '沧源佤族自治县', '530927', '530900', '677400', 'C', '3');
INSERT INTO `system_address` VALUES ('2773', '楚雄彝族自治州', '532300', '530000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('2774', '楚雄市', '532301', '532300', '675000', 'C', '3');
INSERT INTO `system_address` VALUES ('2775', '双柏县', '532322', '532300', '675100', 'S', '3');
INSERT INTO `system_address` VALUES ('2776', '牟定县', '532323', '532300', '675500', 'M', '3');
INSERT INTO `system_address` VALUES ('2777', '南华县', '532324', '532300', '675200', 'N', '3');
INSERT INTO `system_address` VALUES ('2778', '姚安县', '532325', '532300', '675300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2779', '大姚县', '532326', '532300', '675400', 'D', '3');
INSERT INTO `system_address` VALUES ('2780', '永仁县', '532327', '532300', '651400', 'Y', '3');
INSERT INTO `system_address` VALUES ('2781', '元谋县', '532328', '532300', '651300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2782', '武定县', '532329', '532300', '651600', 'W', '3');
INSERT INTO `system_address` VALUES ('2783', '禄丰县', '532331', '532300', '651200', 'L', '3');
INSERT INTO `system_address` VALUES ('2784', '红河哈尼族彝族自治州', '532500', '530000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2785', '个旧市', '532501', '532500', '661000', 'G', '3');
INSERT INTO `system_address` VALUES ('2786', '开远市', '532502', '532500', '661600', 'K', '3');
INSERT INTO `system_address` VALUES ('2787', '蒙自市', '532522', '532500', '661100', 'M', '3');
INSERT INTO `system_address` VALUES ('2788', '屏边苗族自治县', '532523', '532500', '661200', 'P', '3');
INSERT INTO `system_address` VALUES ('2789', '建水县', '532524', '532500', '654300', 'J', '3');
INSERT INTO `system_address` VALUES ('2790', '石屏县', '532525', '532500', '662200', 'S', '3');
INSERT INTO `system_address` VALUES ('2791', '弥勒市', '532526', '532500', '652300', 'M', '3');
INSERT INTO `system_address` VALUES ('2792', '泸西县', '532527', '532500', '652400', 'L', '3');
INSERT INTO `system_address` VALUES ('2793', '元阳县', '532528', '532500', '662400', 'Y', '3');
INSERT INTO `system_address` VALUES ('2794', '红河县', '532529', '532500', '654400', 'H', '3');
INSERT INTO `system_address` VALUES ('2795', '金平苗族瑶族傣族自治县', '532530', '532500', '661500', 'J', '3');
INSERT INTO `system_address` VALUES ('2796', '绿春县', '532531', '532500', '662500', 'L', '3');
INSERT INTO `system_address` VALUES ('2797', '河口瑶族自治县', '532532', '532500', '661300', 'H', '3');
INSERT INTO `system_address` VALUES ('2798', '文山壮族苗族自治州', '532600', '530000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('2799', '文山市', '532621', '532600', '663000', 'W', '3');
INSERT INTO `system_address` VALUES ('2800', '砚山县', '532622', '532600', '663100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2801', '西畴县', '532623', '532600', '663500', 'X', '3');
INSERT INTO `system_address` VALUES ('2802', '麻栗坡县', '532624', '532600', '663600', 'M', '3');
INSERT INTO `system_address` VALUES ('2803', '马关县', '532625', '532600', '663700', 'M', '3');
INSERT INTO `system_address` VALUES ('2804', '丘北县', '532626', '532600', '663200', 'Q', '3');
INSERT INTO `system_address` VALUES ('2805', '广南县', '532627', '532600', '663300', 'G', '3');
INSERT INTO `system_address` VALUES ('2806', '富宁县', '532628', '532600', '663400', 'F', '3');
INSERT INTO `system_address` VALUES ('2807', '西双版纳傣族自治州', '532800', '530000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('2808', '景洪市', '532801', '532800', '666100', 'J', '3');
INSERT INTO `system_address` VALUES ('2809', '勐海县', '532822', '532800', '666200', 'M', '3');
INSERT INTO `system_address` VALUES ('2810', '勐腊县', '532823', '532800', '666300', 'M', '3');
INSERT INTO `system_address` VALUES ('2811', '大理白族自治州', '532900', '530000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2812', '大理市', '532901', '532900', '671000', 'D', '3');
INSERT INTO `system_address` VALUES ('2813', '漾濞彝族自治县', '532922', '532900', '672500', 'Y', '3');
INSERT INTO `system_address` VALUES ('2814', '祥云县', '532923', '532900', '672100', 'X', '3');
INSERT INTO `system_address` VALUES ('2815', '宾川县', '532924', '532900', '671600', 'B', '3');
INSERT INTO `system_address` VALUES ('2816', '弥渡县', '532925', '532900', '675600', 'M', '3');
INSERT INTO `system_address` VALUES ('2817', '南涧彝族自治县', '532926', '532900', '675700', 'N', '3');
INSERT INTO `system_address` VALUES ('2818', '巍山彝族回族自治县', '532927', '532900', '672400', 'W', '3');
INSERT INTO `system_address` VALUES ('2819', '永平县', '532928', '532900', '672600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2820', '云龙县', '532929', '532900', '672700', 'Y', '3');
INSERT INTO `system_address` VALUES ('2821', '洱源县', '532930', '532900', '671200', 'E', '3');
INSERT INTO `system_address` VALUES ('2822', '剑川县', '532931', '532900', '671300', 'J', '3');
INSERT INTO `system_address` VALUES ('2823', '鹤庆县', '532932', '532900', '671500', 'H', '3');
INSERT INTO `system_address` VALUES ('2824', '德宏傣族景颇族自治州', '533100', '530000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2825', '瑞丽市', '533102', '533100', '678600', 'R', '3');
INSERT INTO `system_address` VALUES ('2826', '芒市', '533103', '533100', '678400', 'M', '3');
INSERT INTO `system_address` VALUES ('2827', '梁河县', '533122', '533100', '679200', 'L', '3');
INSERT INTO `system_address` VALUES ('2828', '盈江县', '533123', '533100', '679300', 'Y', '3');
INSERT INTO `system_address` VALUES ('2829', '陇川县', '533124', '533100', '678700', 'L', '3');
INSERT INTO `system_address` VALUES ('2830', '怒江傈僳族自治州', '533300', '530000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('2831', '泸水县', '533321', '533300', '673200', 'L', '3');
INSERT INTO `system_address` VALUES ('2832', '福贡县', '533323', '533300', '673400', 'F', '3');
INSERT INTO `system_address` VALUES ('2833', '贡山独龙族怒族自治县', '533324', '533300', '673500', 'G', '3');
INSERT INTO `system_address` VALUES ('2834', '兰坪白族普米族自治县', '533325', '533300', '671400', 'L', '3');
INSERT INTO `system_address` VALUES ('2835', '迪庆藏族自治州', '533400', '530000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('2836', '香格里拉市', '533421', '533400', '674400', 'X', '3');
INSERT INTO `system_address` VALUES ('2837', '德钦县', '533422', '533400', '674500', 'D', '3');
INSERT INTO `system_address` VALUES ('2838', '维西傈僳族自治县', '533423', '533400', '674600', 'W', '3');
INSERT INTO `system_address` VALUES ('2839', '西藏', '540000', '0', null, 'X', '1');
INSERT INTO `system_address` VALUES ('2840', '拉萨市', '540100', '540000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2841', '城关区', '540102', '540100', '850000', 'C', '3');
INSERT INTO `system_address` VALUES ('2842', '林周县', '540121', '540100', '851600', 'L', '3');
INSERT INTO `system_address` VALUES ('2843', '当雄县', '540122', '540100', '851500', 'D', '3');
INSERT INTO `system_address` VALUES ('2844', '尼木县', '540123', '540100', '851600', 'N', '3');
INSERT INTO `system_address` VALUES ('2845', '曲水县', '540124', '540100', '850600', 'Q', '3');
INSERT INTO `system_address` VALUES ('2846', '堆龙德庆县', '540125', '540100', '851400', 'D', '3');
INSERT INTO `system_address` VALUES ('2847', '达孜县', '540126', '540100', '850100', 'D', '3');
INSERT INTO `system_address` VALUES ('2848', '墨竹工卡县', '540127', '540100', '850200', 'M', '3');
INSERT INTO `system_address` VALUES ('2849', '昌都市', '542100', '540000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('2850', '卡若区', '542121', '542100', '854000', 'K', '3');
INSERT INTO `system_address` VALUES ('2851', '江达县', '542122', '542100', '854100', 'J', '3');
INSERT INTO `system_address` VALUES ('2852', '贡觉县', '542123', '542100', '854200', 'G', '3');
INSERT INTO `system_address` VALUES ('2853', '类乌齐县', '542124', '542100', '855600', 'L', '3');
INSERT INTO `system_address` VALUES ('2854', '丁青县', '542125', '542100', '855700', 'D', '3');
INSERT INTO `system_address` VALUES ('2855', '察雅县', '542126', '542100', '854300', 'C', '3');
INSERT INTO `system_address` VALUES ('2856', '八宿县', '542127', '542100', '854600', 'B', '3');
INSERT INTO `system_address` VALUES ('2857', '左贡县', '542128', '542100', '854400', 'Z', '3');
INSERT INTO `system_address` VALUES ('2858', '芒康县', '542129', '542100', '854500', 'M', '3');
INSERT INTO `system_address` VALUES ('2859', '洛隆县', '542132', '542100', '855400', 'L', '3');
INSERT INTO `system_address` VALUES ('2860', '边坝县', '542133', '542100', '855500', 'B', '3');
INSERT INTO `system_address` VALUES ('2861', '山南地区', '542200', '540000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('2862', '乃东县', '542221', '542200', '856100', 'N', '3');
INSERT INTO `system_address` VALUES ('2863', '扎囊县', '542222', '542200', '850800', 'Z', '3');
INSERT INTO `system_address` VALUES ('2864', '贡嘎县', '542223', '542200', '850700', 'G', '3');
INSERT INTO `system_address` VALUES ('2865', '桑日县', '542224', '542200', '856200', 'S', '3');
INSERT INTO `system_address` VALUES ('2866', '琼结县', '542225', '542200', '856800', 'Q', '3');
INSERT INTO `system_address` VALUES ('2867', '曲松县', '542226', '542200', '856300', 'Q', '3');
INSERT INTO `system_address` VALUES ('2868', '措美县', '542227', '542200', '856900', 'C', '3');
INSERT INTO `system_address` VALUES ('2869', '洛扎县', '542228', '542200', '851200', 'L', '3');
INSERT INTO `system_address` VALUES ('2870', '加查县', '542229', '542200', '856400', 'J', '3');
INSERT INTO `system_address` VALUES ('2871', '隆子县', '542231', '542200', '856600', 'L', '3');
INSERT INTO `system_address` VALUES ('2872', '错那县', '542232', '542200', '856700', 'C', '3');
INSERT INTO `system_address` VALUES ('2873', '浪卡子县', '542233', '542200', '851100', 'L', '3');
INSERT INTO `system_address` VALUES ('2874', '日喀则市', '542300', '540000', null, 'R', '2');
INSERT INTO `system_address` VALUES ('2875', '桑珠孜区', '542301', '542300', '857000', 'S', '3');
INSERT INTO `system_address` VALUES ('2876', '南木林县', '542322', '542300', '857100', 'N', '3');
INSERT INTO `system_address` VALUES ('2877', '江孜县', '542323', '542300', '858100', 'J', '3');
INSERT INTO `system_address` VALUES ('2878', '定日县', '542324', '542300', '858200', 'D', '3');
INSERT INTO `system_address` VALUES ('2879', '萨迦县', '542325', '542300', '857800', 'S', '3');
INSERT INTO `system_address` VALUES ('2880', '拉孜县', '542326', '542300', '858100', 'L', '3');
INSERT INTO `system_address` VALUES ('2881', '昂仁县', '542327', '542300', '858500', 'A', '3');
INSERT INTO `system_address` VALUES ('2882', '谢通门县', '542328', '542300', '858900', 'X', '3');
INSERT INTO `system_address` VALUES ('2883', '白朗县', '542329', '542300', '857300', 'B', '3');
INSERT INTO `system_address` VALUES ('2884', '仁布县', '542330', '542300', '857200', 'R', '3');
INSERT INTO `system_address` VALUES ('2885', '康马县', '542331', '542300', '857500', 'K', '3');
INSERT INTO `system_address` VALUES ('2886', '定结县', '542332', '542300', '857900', 'D', '3');
INSERT INTO `system_address` VALUES ('2887', '仲巴县', '542333', '542300', '858800', 'Z', '3');
INSERT INTO `system_address` VALUES ('2888', '亚东县', '542334', '542300', '857600', 'Y', '3');
INSERT INTO `system_address` VALUES ('2889', '吉隆县', '542335', '542300', '858700', 'J', '3');
INSERT INTO `system_address` VALUES ('2890', '聂拉木县', '542336', '542300', '858300', 'N', '3');
INSERT INTO `system_address` VALUES ('2891', '萨嘎县', '542337', '542300', '858600', 'S', '3');
INSERT INTO `system_address` VALUES ('2892', '岗巴县', '542338', '542300', '857700', 'G', '3');
INSERT INTO `system_address` VALUES ('2893', '那曲地区', '542400', '540000', null, 'N', '2');
INSERT INTO `system_address` VALUES ('2894', '那曲县', '542421', '542400', '852000', 'N', '3');
INSERT INTO `system_address` VALUES ('2895', '嘉黎县', '542422', '542400', '852400', 'J', '3');
INSERT INTO `system_address` VALUES ('2896', '比如县', '542423', '542400', '852300', 'B', '3');
INSERT INTO `system_address` VALUES ('2897', '聂荣县', '542424', '542400', '853500', 'N', '3');
INSERT INTO `system_address` VALUES ('2898', '安多县', '542425', '542400', '853400', 'A', '3');
INSERT INTO `system_address` VALUES ('2899', '申扎县', '542426', '542400', '853100', 'S', '3');
INSERT INTO `system_address` VALUES ('2900', '索县', '542427', '542400', '852200', 'S', '3');
INSERT INTO `system_address` VALUES ('2901', '班戈县', '542428', '542400', '852500', 'B', '3');
INSERT INTO `system_address` VALUES ('2902', '巴青县', '542429', '542400', '852100', 'B', '3');
INSERT INTO `system_address` VALUES ('2903', '尼玛县', '542430', '542400', '853200', 'N', '3');
INSERT INTO `system_address` VALUES ('2904', '双湖县', '542432', '542400', '853300', 'S', '3');
INSERT INTO `system_address` VALUES ('2905', '阿里地区', '542500', '540000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('2906', '普兰县', '542521', '542500', '859500', 'P', '3');
INSERT INTO `system_address` VALUES ('2907', '札达县', '542522', '542500', '859600', 'Z', '3');
INSERT INTO `system_address` VALUES ('2908', '噶尔县', '542523', '542500', '859000', 'G', '3');
INSERT INTO `system_address` VALUES ('2909', '日土县', '542524', '542500', '859700', 'R', '3');
INSERT INTO `system_address` VALUES ('2910', '革吉县', '542525', '542500', '859100', 'G', '3');
INSERT INTO `system_address` VALUES ('2911', '改则县', '542526', '542500', '859200', 'G', '3');
INSERT INTO `system_address` VALUES ('2912', '措勤县', '542527', '542500', '859300', 'C', '3');
INSERT INTO `system_address` VALUES ('2913', '林芝市', '542600', '540000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('2914', '巴宜区', '542621', '542600', '860000', 'B', '3');
INSERT INTO `system_address` VALUES ('2915', '工布江达县', '542622', '542600', '860200', 'G', '3');
INSERT INTO `system_address` VALUES ('2916', '米林县', '542623', '542600', '860500', 'M', '3');
INSERT INTO `system_address` VALUES ('2917', '墨脱县', '542624', '542600', '860700', 'M', '3');
INSERT INTO `system_address` VALUES ('2918', '波密县', '542625', '542600', '860300', 'B', '3');
INSERT INTO `system_address` VALUES ('2919', '察隅县', '542626', '542600', '860600', 'C', '3');
INSERT INTO `system_address` VALUES ('2920', '朗县', '542627', '542600', '860400', 'L', '3');
INSERT INTO `system_address` VALUES ('2921', '陕西', '610000', '0', null, 'S', '1');
INSERT INTO `system_address` VALUES ('2922', '西安市', '610100', '610000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('2923', '新城区', '610102', '610100', '710000', 'X', '3');
INSERT INTO `system_address` VALUES ('2924', '碑林区', '610103', '610100', '710000', 'B', '3');
INSERT INTO `system_address` VALUES ('2925', '莲湖区', '610104', '610100', '710000', 'L', '3');
INSERT INTO `system_address` VALUES ('2926', '灞桥区', '610111', '610100', '710000', 'B', '3');
INSERT INTO `system_address` VALUES ('2927', '未央区', '610112', '610100', '710000', 'W', '3');
INSERT INTO `system_address` VALUES ('2928', '雁塔区', '610113', '610100', '710000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2929', '阎良区', '610114', '610100', '710000', 'Y', '3');
INSERT INTO `system_address` VALUES ('2930', '临潼区', '610115', '610100', '710600', 'L', '3');
INSERT INTO `system_address` VALUES ('2931', '长安区', '610116', '610100', '710100', 'Z', '3');
INSERT INTO `system_address` VALUES ('2932', '高陵区', '610117', '610100', '710200', 'G', '3');
INSERT INTO `system_address` VALUES ('2933', '蓝田县', '610122', '610100', '710500', 'L', '3');
INSERT INTO `system_address` VALUES ('2934', '周至县', '610124', '610100', '710400', 'Z', '3');
INSERT INTO `system_address` VALUES ('2935', '户县', '610125', '610100', '710300', 'H', '3');
INSERT INTO `system_address` VALUES ('2936', '高陵区', '610126', '610100', '710200', 'G', '3');
INSERT INTO `system_address` VALUES ('2937', '曲江新区', '610199', '610100', '710000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2938', '铜川市', '610200', '610000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('2939', '王益区', '610202', '610200', '727000', 'W', '3');
INSERT INTO `system_address` VALUES ('2940', '印台区', '610203', '610200', '727007', 'Y', '3');
INSERT INTO `system_address` VALUES ('2941', '耀州区', '610204', '610200', '727100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2942', '宜君县', '610222', '610200', '727200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2943', '宝鸡市', '610300', '610000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('2944', '渭滨区', '610302', '610300', '721000', 'W', '3');
INSERT INTO `system_address` VALUES ('2945', '金台区', '610303', '610300', '721000', 'J', '3');
INSERT INTO `system_address` VALUES ('2946', '陈仓区', '610304', '610300', '721300', 'C', '3');
INSERT INTO `system_address` VALUES ('2947', '凤翔县', '610322', '610300', '721400', 'F', '3');
INSERT INTO `system_address` VALUES ('2948', '岐山县', '610323', '610300', '722400', 'Q', '3');
INSERT INTO `system_address` VALUES ('2949', '扶风县', '610324', '610300', '722200', 'F', '3');
INSERT INTO `system_address` VALUES ('2950', '眉县', '610326', '610300', '722300', 'M', '3');
INSERT INTO `system_address` VALUES ('2951', '陇县', '610327', '610300', '721200', 'L', '3');
INSERT INTO `system_address` VALUES ('2952', '千阳县', '610328', '610300', '721100', 'Q', '3');
INSERT INTO `system_address` VALUES ('2953', '麟游县', '610329', '610300', '721500', 'L', '3');
INSERT INTO `system_address` VALUES ('2954', '凤县', '610330', '610300', '721700', 'F', '3');
INSERT INTO `system_address` VALUES ('2955', '太白县', '610331', '610300', '721600', 'T', '3');
INSERT INTO `system_address` VALUES ('2956', '咸阳市', '610400', '610000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('2957', '秦都区', '610402', '610400', '712000', 'Q', '3');
INSERT INTO `system_address` VALUES ('2958', '杨陵区', '610403', '610400', '712100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2959', '渭城区', '610404', '610400', '712000', 'W', '3');
INSERT INTO `system_address` VALUES ('2960', '三原县', '610422', '610400', '713800', 'S', '3');
INSERT INTO `system_address` VALUES ('2961', '泾阳县', '610423', '610400', '713700', 'J', '3');
INSERT INTO `system_address` VALUES ('2962', '乾县', '610424', '610400', '713300', 'Q', '3');
INSERT INTO `system_address` VALUES ('2963', '礼泉县', '610425', '610400', '713200', 'L', '3');
INSERT INTO `system_address` VALUES ('2964', '永寿县', '610426', '610400', '713400', 'Y', '3');
INSERT INTO `system_address` VALUES ('2965', '彬县', '610427', '610400', '713500', 'B', '3');
INSERT INTO `system_address` VALUES ('2966', '长武县', '610428', '610400', '713600', 'Z', '3');
INSERT INTO `system_address` VALUES ('2967', '旬邑县', '610429', '610400', '711300', 'X', '3');
INSERT INTO `system_address` VALUES ('2968', '淳化县', '610430', '610400', '711200', 'C', '3');
INSERT INTO `system_address` VALUES ('2969', '武功县', '610431', '610400', '712200', 'W', '3');
INSERT INTO `system_address` VALUES ('2970', '兴平市', '610481', '610400', '713100', 'X', '3');
INSERT INTO `system_address` VALUES ('2971', '渭南市', '610500', '610000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('2972', '临渭区', '610502', '610500', '714000', 'L', '3');
INSERT INTO `system_address` VALUES ('2973', '华县', '610521', '610500', '714100', 'H', '3');
INSERT INTO `system_address` VALUES ('2974', '潼关县', '610522', '610500', '714300', 'T', '3');
INSERT INTO `system_address` VALUES ('2975', '大荔县', '610523', '610500', '715100', 'D', '3');
INSERT INTO `system_address` VALUES ('2976', '合阳县', '610524', '610500', '715300', 'H', '3');
INSERT INTO `system_address` VALUES ('2977', '澄城县', '610525', '610500', '715200', 'C', '3');
INSERT INTO `system_address` VALUES ('2978', '蒲城县', '610526', '610500', '715500', 'P', '3');
INSERT INTO `system_address` VALUES ('2979', '白水县', '610527', '610500', '715600', 'B', '3');
INSERT INTO `system_address` VALUES ('2980', '富平县', '610528', '610500', '711700', 'F', '3');
INSERT INTO `system_address` VALUES ('2981', '韩城市', '610581', '610500', '715400', 'H', '3');
INSERT INTO `system_address` VALUES ('2982', '华阴市', '610582', '610500', '714200', 'H', '3');
INSERT INTO `system_address` VALUES ('2983', '延安市', '610600', '610000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('2984', '宝塔区', '610602', '610600', '716000', 'B', '3');
INSERT INTO `system_address` VALUES ('2985', '延长县', '610621', '610600', '717100', 'Y', '3');
INSERT INTO `system_address` VALUES ('2986', '延川县', '610622', '610600', '717200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2987', '子长县', '610623', '610600', '717300', 'Z', '3');
INSERT INTO `system_address` VALUES ('2988', '安塞县', '610624', '610600', '717400', 'A', '3');
INSERT INTO `system_address` VALUES ('2989', '志丹县', '610625', '610600', '717500', 'Z', '3');
INSERT INTO `system_address` VALUES ('2990', '吴起县', '610626', '610600', '717600', 'W', '3');
INSERT INTO `system_address` VALUES ('2991', '甘泉县', '610627', '610600', '716100', 'G', '3');
INSERT INTO `system_address` VALUES ('2992', '富县', '610628', '610600', '727500', 'F', '3');
INSERT INTO `system_address` VALUES ('2993', '洛川县', '610629', '610600', '727400', 'L', '3');
INSERT INTO `system_address` VALUES ('2994', '宜川县', '610630', '610600', '716200', 'Y', '3');
INSERT INTO `system_address` VALUES ('2995', '黄龙县', '610631', '610600', '715700', 'H', '3');
INSERT INTO `system_address` VALUES ('2996', '黄陵县', '610632', '610600', '727300', 'H', '3');
INSERT INTO `system_address` VALUES ('2997', '汉中市', '610700', '610000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('2998', '汉台区', '610702', '610700', '723000', 'H', '3');
INSERT INTO `system_address` VALUES ('2999', '南郑县', '610721', '610700', '723100', 'N', '3');
INSERT INTO `system_address` VALUES ('3000', '城固县', '610722', '610700', '723200', 'C', '3');
INSERT INTO `system_address` VALUES ('3001', '洋县', '610723', '610700', '723300', 'Y', '3');
INSERT INTO `system_address` VALUES ('3002', '西乡县', '610724', '610700', '723500', 'X', '3');
INSERT INTO `system_address` VALUES ('3003', '勉县', '610725', '610700', '724200', 'M', '3');
INSERT INTO `system_address` VALUES ('3004', '宁强县', '610726', '610700', '724400', 'N', '3');
INSERT INTO `system_address` VALUES ('3005', '略阳县', '610727', '610700', '724300', 'L', '3');
INSERT INTO `system_address` VALUES ('3006', '镇巴县', '610728', '610700', '723600', 'Z', '3');
INSERT INTO `system_address` VALUES ('3007', '留坝县', '610729', '610700', '724100', 'L', '3');
INSERT INTO `system_address` VALUES ('3008', '佛坪县', '610730', '610700', '723400', 'F', '3');
INSERT INTO `system_address` VALUES ('3009', '榆林市', '610800', '610000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('3010', '榆阳区', '610802', '610800', '719000', 'Y', '3');
INSERT INTO `system_address` VALUES ('3011', '神木县', '610821', '610800', '719300', 'S', '3');
INSERT INTO `system_address` VALUES ('3012', '府谷县', '610822', '610800', '719400', 'F', '3');
INSERT INTO `system_address` VALUES ('3013', '横山县', '610823', '610800', '719200', 'H', '3');
INSERT INTO `system_address` VALUES ('3014', '靖边县', '610824', '610800', '718500', 'J', '3');
INSERT INTO `system_address` VALUES ('3015', '定边县', '610825', '610800', '718600', 'D', '3');
INSERT INTO `system_address` VALUES ('3016', '绥德县', '610826', '610800', '718000', 'S', '3');
INSERT INTO `system_address` VALUES ('3017', '米脂县', '610827', '610800', '718100', 'M', '3');
INSERT INTO `system_address` VALUES ('3018', '佳县', '610828', '610800', '719200', 'J', '3');
INSERT INTO `system_address` VALUES ('3019', '吴堡县', '610829', '610800', '718200', 'W', '3');
INSERT INTO `system_address` VALUES ('3020', '清涧县', '610830', '610800', '718300', 'Q', '3');
INSERT INTO `system_address` VALUES ('3021', '子洲县', '610831', '610800', '718400', 'Z', '3');
INSERT INTO `system_address` VALUES ('3022', '安康市', '610900', '610000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('3023', '汉滨区', '610902', '610900', '725000', 'H', '3');
INSERT INTO `system_address` VALUES ('3024', '汉阴县', '610921', '610900', '725100', 'H', '3');
INSERT INTO `system_address` VALUES ('3025', '石泉县', '610922', '610900', '725200', 'S', '3');
INSERT INTO `system_address` VALUES ('3026', '宁陕县', '610923', '610900', '711600', 'N', '3');
INSERT INTO `system_address` VALUES ('3027', '紫阳县', '610924', '610900', '725300', 'Z', '3');
INSERT INTO `system_address` VALUES ('3028', '岚皋县', '610925', '610900', '725400', 'L', '3');
INSERT INTO `system_address` VALUES ('3029', '平利县', '610926', '610900', '725500', 'P', '3');
INSERT INTO `system_address` VALUES ('3030', '镇坪县', '610927', '610900', '725600', 'Z', '3');
INSERT INTO `system_address` VALUES ('3031', '旬阳县', '610928', '610900', '725700', 'X', '3');
INSERT INTO `system_address` VALUES ('3032', '白河县', '610929', '610900', '725800', 'B', '3');
INSERT INTO `system_address` VALUES ('3033', '商洛市', '611000', '610000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3034', '商州区', '611002', '611000', '726000', 'S', '3');
INSERT INTO `system_address` VALUES ('3035', '洛南县', '611021', '611000', '726100', 'L', '3');
INSERT INTO `system_address` VALUES ('3036', '丹凤县', '611022', '611000', '726200', 'D', '3');
INSERT INTO `system_address` VALUES ('3037', '商南县', '611023', '611000', '726300', 'S', '3');
INSERT INTO `system_address` VALUES ('3038', '山阳县', '611024', '611000', '726400', 'S', '3');
INSERT INTO `system_address` VALUES ('3039', '镇安县', '611025', '611000', '711500', 'Z', '3');
INSERT INTO `system_address` VALUES ('3040', '柞水县', '611026', '611000', '711400', 'Z', '3');
INSERT INTO `system_address` VALUES ('3041', '甘肃', '620000', '0', null, 'G', '1');
INSERT INTO `system_address` VALUES ('3042', '兰州市', '620100', '620000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('3043', '城关区', '620102', '620100', '730030', 'C', '3');
INSERT INTO `system_address` VALUES ('3044', '七里河区', '620103', '620100', '730050', 'Q', '3');
INSERT INTO `system_address` VALUES ('3045', '西固区', '620104', '620100', '730060', 'X', '3');
INSERT INTO `system_address` VALUES ('3046', '安宁区', '620105', '620100', '730070', 'A', '3');
INSERT INTO `system_address` VALUES ('3047', '红古区', '620111', '620100', '730080', 'H', '3');
INSERT INTO `system_address` VALUES ('3048', '永登县', '620121', '620100', '730300', 'Y', '3');
INSERT INTO `system_address` VALUES ('3049', '皋兰县', '620122', '620100', '730200', 'G', '3');
INSERT INTO `system_address` VALUES ('3050', '榆中县', '620123', '620100', '730100', 'Y', '3');
INSERT INTO `system_address` VALUES ('3051', '兰州新区', '1000665', '620100', '730000', 'L', '3');
INSERT INTO `system_address` VALUES ('3052', '嘉峪关市', '620200', '620000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('3053', '金昌市', '620300', '620000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('3054', '金川区', '620302', '620300', '737100', 'J', '3');
INSERT INTO `system_address` VALUES ('3055', '永昌县', '620321', '620300', '737200', 'Y', '3');
INSERT INTO `system_address` VALUES ('3056', '白银市', '620400', '620000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('3057', '白银区', '620402', '620400', '730900', 'B', '3');
INSERT INTO `system_address` VALUES ('3058', '平川区', '620403', '620400', '730900', 'P', '3');
INSERT INTO `system_address` VALUES ('3059', '靖远县', '620421', '620400', '730600', 'J', '3');
INSERT INTO `system_address` VALUES ('3060', '会宁县', '620422', '620400', '730700', 'H', '3');
INSERT INTO `system_address` VALUES ('3061', '景泰县', '620423', '620400', '730400', 'J', '3');
INSERT INTO `system_address` VALUES ('3062', '天水市', '620500', '620000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3063', '秦州区', '620502', '620500', '741000', 'Q', '3');
INSERT INTO `system_address` VALUES ('3064', '麦积区', '620503', '620500', '741020', 'M', '3');
INSERT INTO `system_address` VALUES ('3065', '清水县', '620521', '620500', '741400', 'Q', '3');
INSERT INTO `system_address` VALUES ('3066', '秦安县', '620522', '620500', '741600', 'Q', '3');
INSERT INTO `system_address` VALUES ('3067', '甘谷县', '620523', '620500', '741200', 'G', '3');
INSERT INTO `system_address` VALUES ('3068', '武山县', '620524', '620500', '741300', 'W', '3');
INSERT INTO `system_address` VALUES ('3069', '张家川回族自治县', '620525', '620500', '741500', 'Z', '3');
INSERT INTO `system_address` VALUES ('3070', '武威市', '620600', '620000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('3071', '凉州区', '620602', '620600', '733000', 'L', '3');
INSERT INTO `system_address` VALUES ('3072', '民勤县', '620621', '620600', '733300', 'M', '3');
INSERT INTO `system_address` VALUES ('3073', '古浪县', '620622', '620600', '733100', 'G', '3');
INSERT INTO `system_address` VALUES ('3074', '天祝藏族自治县', '620623', '620600', '733200', 'T', '3');
INSERT INTO `system_address` VALUES ('3075', '张掖市', '620700', '620000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('3076', '甘州区', '620702', '620700', '734000', 'G', '3');
INSERT INTO `system_address` VALUES ('3077', '肃南裕固族自治县', '620721', '620700', '734400', 'S', '3');
INSERT INTO `system_address` VALUES ('3078', '民乐县', '620722', '620700', '734500', 'M', '3');
INSERT INTO `system_address` VALUES ('3079', '临泽县', '620723', '620700', '734200', 'L', '3');
INSERT INTO `system_address` VALUES ('3080', '高台县', '620724', '620700', '734300', 'G', '3');
INSERT INTO `system_address` VALUES ('3081', '山丹县', '620725', '620700', '734100', 'S', '3');
INSERT INTO `system_address` VALUES ('3082', '平凉市', '620800', '620000', null, 'P', '2');
INSERT INTO `system_address` VALUES ('3083', '崆峒区', '620802', '620800', '744000', 'K', '3');
INSERT INTO `system_address` VALUES ('3084', '泾川县', '620821', '620800', '744300', 'J', '3');
INSERT INTO `system_address` VALUES ('3085', '灵台县', '620822', '620800', '744400', 'L', '3');
INSERT INTO `system_address` VALUES ('3086', '崇信县', '620823', '620800', '744200', 'C', '3');
INSERT INTO `system_address` VALUES ('3087', '华亭县', '620824', '620800', '744100', 'H', '3');
INSERT INTO `system_address` VALUES ('3088', '庄浪县', '620825', '620800', '744600', 'Z', '3');
INSERT INTO `system_address` VALUES ('3089', '静宁县', '620826', '620800', '743400', 'J', '3');
INSERT INTO `system_address` VALUES ('3090', '酒泉市', '620900', '620000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('3091', '肃州区', '620902', '620900', '735000', 'S', '3');
INSERT INTO `system_address` VALUES ('3092', '金塔县', '620921', '620900', '735300', 'J', '3');
INSERT INTO `system_address` VALUES ('3093', '瓜州县', '620922', '620900', '736100', 'G', '3');
INSERT INTO `system_address` VALUES ('3094', '肃北蒙古族自治县', '620923', '620900', '736300', 'S', '3');
INSERT INTO `system_address` VALUES ('3095', '阿克塞哈萨克族自治县', '620924', '620900', '736400', 'A', '3');
INSERT INTO `system_address` VALUES ('3096', '玉门市', '620981', '620900', '735200', 'Y', '3');
INSERT INTO `system_address` VALUES ('3097', '敦煌市', '620982', '620900', '736200', 'D', '3');
INSERT INTO `system_address` VALUES ('3098', '庆阳市', '621000', '620000', null, 'Q', '2');
INSERT INTO `system_address` VALUES ('3099', '西峰区', '621002', '621000', '745000', 'X', '3');
INSERT INTO `system_address` VALUES ('3100', '庆城县', '621021', '621000', '745100', 'Q', '3');
INSERT INTO `system_address` VALUES ('3101', '环县', '621022', '621000', '745700', 'H', '3');
INSERT INTO `system_address` VALUES ('3102', '华池县', '621023', '621000', '745600', 'H', '3');
INSERT INTO `system_address` VALUES ('3103', '合水县', '621024', '621000', '745400', 'H', '3');
INSERT INTO `system_address` VALUES ('3104', '正宁县', '621025', '621000', '745300', 'Z', '3');
INSERT INTO `system_address` VALUES ('3105', '宁县', '621026', '621000', '745200', 'N', '3');
INSERT INTO `system_address` VALUES ('3106', '镇原县', '621027', '621000', '744500', 'Z', '3');
INSERT INTO `system_address` VALUES ('3107', '定西市', '621100', '620000', null, 'D', '2');
INSERT INTO `system_address` VALUES ('3108', '安定区', '621102', '621100', '744300', 'A', '3');
INSERT INTO `system_address` VALUES ('3109', '通渭县', '621121', '621100', '743300', 'T', '3');
INSERT INTO `system_address` VALUES ('3110', '陇西县', '621122', '621100', '748100', 'L', '3');
INSERT INTO `system_address` VALUES ('3111', '渭源县', '621123', '621100', '748200', 'W', '3');
INSERT INTO `system_address` VALUES ('3112', '临洮县', '621124', '621100', '730500', 'L', '3');
INSERT INTO `system_address` VALUES ('3113', '漳县', '621125', '621100', '748300', 'Z', '3');
INSERT INTO `system_address` VALUES ('3114', '岷县', '621126', '621100', '748400', 'M', '3');
INSERT INTO `system_address` VALUES ('3115', '陇南市', '621200', '620000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('3116', '武都区', '621202', '621200', '746000', 'W', '3');
INSERT INTO `system_address` VALUES ('3117', '成县', '621221', '621200', '742500', 'C', '3');
INSERT INTO `system_address` VALUES ('3118', '文县', '621222', '621200', '746400', 'W', '3');
INSERT INTO `system_address` VALUES ('3119', '宕昌县', '621223', '621200', '748500', 'D', '3');
INSERT INTO `system_address` VALUES ('3120', '康县', '621224', '621200', '746500', 'K', '3');
INSERT INTO `system_address` VALUES ('3121', '西和县', '621225', '621200', '742100', 'X', '3');
INSERT INTO `system_address` VALUES ('3122', '礼县', '621226', '621200', '742200', 'L', '3');
INSERT INTO `system_address` VALUES ('3123', '徽县', '621227', '621200', '742300', 'H', '3');
INSERT INTO `system_address` VALUES ('3124', '两当县', '621228', '621200', '742400', 'L', '3');
INSERT INTO `system_address` VALUES ('3125', '临夏回族自治州', '622900', '620000', null, 'L', '2');
INSERT INTO `system_address` VALUES ('3126', '临夏市', '622901', '622900', '731100', 'L', '3');
INSERT INTO `system_address` VALUES ('3127', '临夏县', '622921', '622900', '731800', 'L', '3');
INSERT INTO `system_address` VALUES ('3128', '康乐县', '622922', '622900', '731500', 'K', '3');
INSERT INTO `system_address` VALUES ('3129', '永靖县', '622923', '622900', '731600', 'Y', '3');
INSERT INTO `system_address` VALUES ('3130', '广河县', '622924', '622900', '731300', 'G', '3');
INSERT INTO `system_address` VALUES ('3131', '和政县', '622925', '622900', '731200', 'H', '3');
INSERT INTO `system_address` VALUES ('3132', '东乡族自治县', '622926', '622900', '731400', 'D', '3');
INSERT INTO `system_address` VALUES ('3133', '积石山保安族东乡族撒拉族自治县', '622927', '622900', '731700', 'J', '3');
INSERT INTO `system_address` VALUES ('3134', '甘南藏族自治州', '623000', '620000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('3135', '合作市', '623001', '623000', '747000', 'H', '3');
INSERT INTO `system_address` VALUES ('3136', '临潭县', '623021', '623000', '747500', 'L', '3');
INSERT INTO `system_address` VALUES ('3137', '卓尼县', '623022', '623000', '747600', 'Z', '3');
INSERT INTO `system_address` VALUES ('3138', '舟曲县', '623023', '623000', '746300', 'Z', '3');
INSERT INTO `system_address` VALUES ('3139', '迭部县', '623024', '623000', '747400', 'D', '3');
INSERT INTO `system_address` VALUES ('3140', '玛曲县', '623025', '623000', '747300', 'M', '3');
INSERT INTO `system_address` VALUES ('3141', '碌曲县', '623026', '623000', '747200', 'L', '3');
INSERT INTO `system_address` VALUES ('3142', '夏河县', '623027', '623000', '747100', 'X', '3');
INSERT INTO `system_address` VALUES ('3143', '青海', '630000', '0', null, 'Q', '1');
INSERT INTO `system_address` VALUES ('3144', '西宁市', '630100', '630000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('3145', '城东区', '630102', '630100', '810000', 'C', '3');
INSERT INTO `system_address` VALUES ('3146', '城中区', '630103', '630100', '810000', 'C', '3');
INSERT INTO `system_address` VALUES ('3147', '城西区', '630104', '630100', '810000', 'C', '3');
INSERT INTO `system_address` VALUES ('3148', '城北区', '630105', '630100', '810000', 'C', '3');
INSERT INTO `system_address` VALUES ('3149', '大通回族土族自治县', '630121', '630100', '810100', 'D', '3');
INSERT INTO `system_address` VALUES ('3150', '湟中县', '630122', '630100', '811600', 'H', '3');
INSERT INTO `system_address` VALUES ('3151', '湟源县', '630123', '630100', '812100', 'H', '3');
INSERT INTO `system_address` VALUES ('3152', '海东市', '632100', '630000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3153', '平安区', '632121', '632100', '810600', 'P', '3');
INSERT INTO `system_address` VALUES ('3154', '民和回族土族自治县', '632122', '632100', '810800', 'M', '3');
INSERT INTO `system_address` VALUES ('3155', '乐都区', '632123', '632100', '810700', 'L', '3');
INSERT INTO `system_address` VALUES ('3156', '互助土族自治县', '632126', '632100', '810500', 'H', '3');
INSERT INTO `system_address` VALUES ('3157', '化隆回族自治县', '632127', '632100', '810900', 'H', '3');
INSERT INTO `system_address` VALUES ('3158', '循化撒拉族自治县', '632128', '632100', '811100', 'X', '3');
INSERT INTO `system_address` VALUES ('3159', '海北藏族自治州', '632200', '630000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3160', '门源回族自治县', '632221', '632200', '810300', 'M', '3');
INSERT INTO `system_address` VALUES ('3161', '祁连县', '632222', '632200', '810400', 'Q', '3');
INSERT INTO `system_address` VALUES ('3162', '海晏县', '632223', '632200', '812200', 'H', '3');
INSERT INTO `system_address` VALUES ('3163', '刚察县', '632224', '632200', '812300', 'G', '3');
INSERT INTO `system_address` VALUES ('3164', '黄南藏族自治州', '632300', '630000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3165', '同仁县', '632321', '632300', '811300', 'T', '3');
INSERT INTO `system_address` VALUES ('3166', '尖扎县', '632322', '632300', '811200', 'J', '3');
INSERT INTO `system_address` VALUES ('3167', '泽库县', '632323', '632300', '811400', 'Z', '3');
INSERT INTO `system_address` VALUES ('3168', '河南蒙古族自治县', '632324', '632300', '811500', 'H', '3');
INSERT INTO `system_address` VALUES ('3169', '海南藏族自治州', '632500', '630000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3170', '共和县', '632521', '632500', '813000', 'G', '3');
INSERT INTO `system_address` VALUES ('3171', '同德县', '632522', '632500', '813200', 'T', '3');
INSERT INTO `system_address` VALUES ('3172', '贵德县', '632523', '632500', '811700', 'G', '3');
INSERT INTO `system_address` VALUES ('3173', '兴海县', '632524', '632500', '813300', 'X', '3');
INSERT INTO `system_address` VALUES ('3174', '贵南县', '632525', '632500', '813100', 'G', '3');
INSERT INTO `system_address` VALUES ('3175', '果洛藏族自治州', '632600', '630000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('3176', '玛沁县', '632621', '632600', '814000', 'M', '3');
INSERT INTO `system_address` VALUES ('3177', '班玛县', '632622', '632600', '814300', 'B', '3');
INSERT INTO `system_address` VALUES ('3178', '甘德县', '632623', '632600', '814100', 'G', '3');
INSERT INTO `system_address` VALUES ('3179', '达日县', '632624', '632600', '814200', 'D', '3');
INSERT INTO `system_address` VALUES ('3180', '久治县', '632625', '632600', '624700', 'J', '3');
INSERT INTO `system_address` VALUES ('3181', '玛多县', '632626', '632600', '813500', 'M', '3');
INSERT INTO `system_address` VALUES ('3182', '玉树藏族自治州', '632700', '630000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('3183', '玉树市', '632721', '632700', '815000', 'Y', '3');
INSERT INTO `system_address` VALUES ('3184', '杂多县', '632722', '632700', '815300', 'Z', '3');
INSERT INTO `system_address` VALUES ('3185', '称多县', '632723', '632700', '815100', 'C', '3');
INSERT INTO `system_address` VALUES ('3186', '治多县', '632724', '632700', '815400', 'Z', '3');
INSERT INTO `system_address` VALUES ('3187', '囊谦县', '632725', '632700', '815200', 'N', '3');
INSERT INTO `system_address` VALUES ('3188', '曲麻莱县', '632726', '632700', '815500', 'Q', '3');
INSERT INTO `system_address` VALUES ('3189', '海西蒙古族藏族自治州', '632800', '630000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3190', '格尔木市', '632801', '632800', '816000', 'G', '3');
INSERT INTO `system_address` VALUES ('3191', '德令哈市', '632802', '632800', '817000', 'D', '3');
INSERT INTO `system_address` VALUES ('3192', '乌兰县', '632821', '632800', '817100', 'W', '3');
INSERT INTO `system_address` VALUES ('3193', '都兰县', '632822', '632800', '816100', 'D', '3');
INSERT INTO `system_address` VALUES ('3194', '天峻县', '632823', '632800', '817200', 'T', '3');
INSERT INTO `system_address` VALUES ('3195', '宁夏', '640000', '0', null, 'N', '1');
INSERT INTO `system_address` VALUES ('3196', '银川市', '640100', '640000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('3197', '兴庆区', '640104', '640100', '750000', 'X', '3');
INSERT INTO `system_address` VALUES ('3198', '西夏区', '640105', '640100', '750000', 'X', '3');
INSERT INTO `system_address` VALUES ('3199', '金凤区', '640106', '640100', '750000', 'J', '3');
INSERT INTO `system_address` VALUES ('3200', '永宁县', '640121', '640100', '750100', 'Y', '3');
INSERT INTO `system_address` VALUES ('3201', '贺兰县', '640122', '640100', '750200', 'H', '3');
INSERT INTO `system_address` VALUES ('3202', '灵武市', '640181', '640100', '751400', 'L', '3');
INSERT INTO `system_address` VALUES ('3203', '石嘴山市', '640200', '640000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3204', '大武口区', '640202', '640200', '753000', 'D', '3');
INSERT INTO `system_address` VALUES ('3205', '惠农区', '640205', '640200', '753600', 'H', '3');
INSERT INTO `system_address` VALUES ('3206', '平罗县', '640221', '640200', '753400', 'P', '3');
INSERT INTO `system_address` VALUES ('3207', '吴忠市', '640300', '640000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('3208', '利通区', '640302', '640300', '751100', 'L', '3');
INSERT INTO `system_address` VALUES ('3209', '红寺堡区', '640303', '640300', '751900', 'H', '3');
INSERT INTO `system_address` VALUES ('3210', '盐池县', '640323', '640300', '751500', 'Y', '3');
INSERT INTO `system_address` VALUES ('3211', '同心县', '640324', '640300', '751300', 'T', '3');
INSERT INTO `system_address` VALUES ('3212', '青铜峡市', '640381', '640300', '751600', 'Q', '3');
INSERT INTO `system_address` VALUES ('3213', '固原市', '640400', '640000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('3214', '原州区', '640402', '640400', '756000', 'Y', '3');
INSERT INTO `system_address` VALUES ('3215', '西吉县', '640422', '640400', '756200', 'X', '3');
INSERT INTO `system_address` VALUES ('3216', '隆德县', '640423', '640400', '756300', 'L', '3');
INSERT INTO `system_address` VALUES ('3217', '泾源县', '640424', '640400', '756400', 'J', '3');
INSERT INTO `system_address` VALUES ('3218', '彭阳县', '640425', '640400', '756500', 'P', '3');
INSERT INTO `system_address` VALUES ('3219', '中卫市', '640500', '640000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('3220', '沙坡头区', '640502', '640500', '755000', 'S', '3');
INSERT INTO `system_address` VALUES ('3221', '中宁县', '640521', '640500', '755100', 'Z', '3');
INSERT INTO `system_address` VALUES ('3222', '海原县', '640522', '640500', '755200', 'H', '3');
INSERT INTO `system_address` VALUES ('3223', '新疆', '650000', '0', null, 'X', '1');
INSERT INTO `system_address` VALUES ('3224', '乌鲁木齐市', '650100', '650000', null, 'W', '2');
INSERT INTO `system_address` VALUES ('3225', '天山区', '650102', '650100', '830000', 'T', '3');
INSERT INTO `system_address` VALUES ('3226', '沙依巴克区', '650103', '650100', '830000', 'S', '3');
INSERT INTO `system_address` VALUES ('3227', '新市区', '650104', '650100', '830000', 'X', '3');
INSERT INTO `system_address` VALUES ('3228', '水磨沟区', '650105', '650100', '830017', 'S', '3');
INSERT INTO `system_address` VALUES ('3229', '头屯河区', '650106', '650100', '830000', 'T', '3');
INSERT INTO `system_address` VALUES ('3230', '达坂城区', '650107', '650100', '830039', 'D', '3');
INSERT INTO `system_address` VALUES ('3231', '米东区', '650109', '650100', '831400', 'M', '3');
INSERT INTO `system_address` VALUES ('3232', '乌鲁木齐县', '650121', '650100', '830000', 'W', '3');
INSERT INTO `system_address` VALUES ('3233', '克拉玛依市', '650200', '650000', null, 'K', '2');
INSERT INTO `system_address` VALUES ('3234', '独山子区', '650202', '650200', '838600', 'D', '3');
INSERT INTO `system_address` VALUES ('3235', '克拉玛依区', '650203', '650200', '834000', 'K', '3');
INSERT INTO `system_address` VALUES ('3236', '白碱滩区', '650204', '650200', '834000', 'B', '3');
INSERT INTO `system_address` VALUES ('3237', '乌尔禾区', '650205', '650200', '834000', 'W', '3');
INSERT INTO `system_address` VALUES ('3238', '吐鲁番市', '652100', '650000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3239', '高昌区', '652101', '652100', '838000', 'G', '3');
INSERT INTO `system_address` VALUES ('3240', '鄯善县', '652122', '652100', '838200', 'S', '3');
INSERT INTO `system_address` VALUES ('3241', '托克逊县', '652123', '652100', '838100', 'T', '3');
INSERT INTO `system_address` VALUES ('3242', '哈密地区', '652200', '650000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3243', '哈密市', '652201', '652200', '839000', 'H', '3');
INSERT INTO `system_address` VALUES ('3244', '巴里坤哈萨克自治县', '652222', '652200', '839200', 'B', '3');
INSERT INTO `system_address` VALUES ('3245', '伊吾县', '652223', '652200', '839300', 'Y', '3');
INSERT INTO `system_address` VALUES ('3246', '昌吉回族自治州', '652300', '650000', null, 'C', '2');
INSERT INTO `system_address` VALUES ('3247', '昌吉市', '652301', '652300', '831100', 'C', '3');
INSERT INTO `system_address` VALUES ('3248', '阜康市', '652302', '652300', '831500', 'F', '3');
INSERT INTO `system_address` VALUES ('3249', '呼图壁县', '652323', '652300', '831200', 'H', '3');
INSERT INTO `system_address` VALUES ('3250', '玛纳斯县', '652324', '652300', '832200', 'M', '3');
INSERT INTO `system_address` VALUES ('3251', '奇台县', '652325', '652300', '831800', 'Q', '3');
INSERT INTO `system_address` VALUES ('3252', '吉木萨尔县', '652327', '652300', '831700', 'J', '3');
INSERT INTO `system_address` VALUES ('3253', '木垒哈萨克自治县', '652328', '652300', '831900', 'M', '3');
INSERT INTO `system_address` VALUES ('3254', '博尔塔拉蒙古自治州', '652700', '650000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('3255', '博乐市', '652701', '652700', '833400', 'B', '3');
INSERT INTO `system_address` VALUES ('3256', '阿拉山口市', '652702', '652700', '833418', 'A', '3');
INSERT INTO `system_address` VALUES ('3257', '精河县', '652722', '652700', '833300', 'J', '3');
INSERT INTO `system_address` VALUES ('3258', '温泉县', '652723', '652700', '833500', 'W', '3');
INSERT INTO `system_address` VALUES ('3259', '巴音郭楞蒙古自治州', '652800', '650000', null, 'B', '2');
INSERT INTO `system_address` VALUES ('3260', '库尔勒市', '652801', '652800', '841000', 'K', '3');
INSERT INTO `system_address` VALUES ('3261', '轮台县', '652822', '652800', '841600', 'L', '3');
INSERT INTO `system_address` VALUES ('3262', '尉犁县', '652823', '652800', '841500', 'W', '3');
INSERT INTO `system_address` VALUES ('3263', '若羌县', '652824', '652800', '841800', 'R', '3');
INSERT INTO `system_address` VALUES ('3264', '且末县', '652825', '652800', '841900', 'Q', '3');
INSERT INTO `system_address` VALUES ('3265', '焉耆回族自治县', '652826', '652800', '841100', 'Y', '3');
INSERT INTO `system_address` VALUES ('3266', '和静县', '652827', '652800', '841300', 'H', '3');
INSERT INTO `system_address` VALUES ('3267', '和硕县', '652828', '652800', '841200', 'H', '3');
INSERT INTO `system_address` VALUES ('3268', '博湖县', '652829', '652800', '841400', 'B', '3');
INSERT INTO `system_address` VALUES ('3269', '阿克苏地区', '652900', '650000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('3270', '阿克苏市', '652901', '652900', '843000', 'A', '3');
INSERT INTO `system_address` VALUES ('3271', '温宿县', '652922', '652900', '843100', 'W', '3');
INSERT INTO `system_address` VALUES ('3272', '库车县', '652923', '652900', '842000', 'K', '3');
INSERT INTO `system_address` VALUES ('3273', '沙雅县', '652924', '652900', '842200', 'S', '3');
INSERT INTO `system_address` VALUES ('3274', '新和县', '652925', '652900', '842100', 'X', '3');
INSERT INTO `system_address` VALUES ('3275', '拜城县', '652926', '652900', '842300', 'B', '3');
INSERT INTO `system_address` VALUES ('3276', '乌什县', '652927', '652900', '843400', 'W', '3');
INSERT INTO `system_address` VALUES ('3277', '阿瓦提县', '652928', '652900', '843200', 'A', '3');
INSERT INTO `system_address` VALUES ('3278', '柯坪县', '652929', '652900', '843600', 'K', '3');
INSERT INTO `system_address` VALUES ('3279', '克孜勒苏柯尔克孜自治州', '653000', '650000', null, 'K', '2');
INSERT INTO `system_address` VALUES ('3280', '阿图什市', '653001', '653000', '845350', 'A', '3');
INSERT INTO `system_address` VALUES ('3281', '阿克陶县', '653022', '653000', '845550', 'A', '3');
INSERT INTO `system_address` VALUES ('3282', '阿合奇县', '653023', '653000', '843500', 'A', '3');
INSERT INTO `system_address` VALUES ('3283', '乌恰县', '653024', '653000', '845450', 'W', '3');
INSERT INTO `system_address` VALUES ('3284', '喀什地区', '653100', '650000', null, 'K', '2');
INSERT INTO `system_address` VALUES ('3285', '喀什市', '653101', '653100', '844000', 'K', '3');
INSERT INTO `system_address` VALUES ('3286', '疏附县', '653121', '653100', '844100', 'S', '3');
INSERT INTO `system_address` VALUES ('3287', '疏勒县', '653122', '653100', '844200', 'S', '3');
INSERT INTO `system_address` VALUES ('3288', '英吉沙县', '653123', '653100', '844500', 'Y', '3');
INSERT INTO `system_address` VALUES ('3289', '泽普县', '653124', '653100', '844800', 'Z', '3');
INSERT INTO `system_address` VALUES ('3290', '莎车县', '653125', '653100', '844710', 'S', '3');
INSERT INTO `system_address` VALUES ('3291', '叶城县', '653126', '653100', '844900', 'Y', '3');
INSERT INTO `system_address` VALUES ('3292', '麦盖提县', '653127', '653100', '844600', 'M', '3');
INSERT INTO `system_address` VALUES ('3293', '岳普湖县', '653128', '653100', '844400', 'Y', '3');
INSERT INTO `system_address` VALUES ('3294', '伽师县', '653129', '653100', '844300', 'J', '3');
INSERT INTO `system_address` VALUES ('3295', '巴楚县', '653130', '653100', '843800', 'B', '3');
INSERT INTO `system_address` VALUES ('3296', '塔什库尔干塔吉克自治县', '653131', '653100', '845250', 'T', '3');
INSERT INTO `system_address` VALUES ('3297', '和田地区', '653200', '650000', null, 'H', '2');
INSERT INTO `system_address` VALUES ('3298', '和田市', '653201', '653200', '848000', 'H', '3');
INSERT INTO `system_address` VALUES ('3299', '和田县', '653221', '653200', '848000', 'H', '3');
INSERT INTO `system_address` VALUES ('3300', '墨玉县', '653222', '653200', '848100', 'M', '3');
INSERT INTO `system_address` VALUES ('3301', '皮山县', '653223', '653200', '845150', 'P', '3');
INSERT INTO `system_address` VALUES ('3302', '洛浦县', '653224', '653200', '848200', 'L', '3');
INSERT INTO `system_address` VALUES ('3303', '策勒县', '653225', '653200', '848300', 'C', '3');
INSERT INTO `system_address` VALUES ('3304', '于田县', '653226', '653200', '848400', 'Y', '3');
INSERT INTO `system_address` VALUES ('3305', '民丰县', '653227', '653200', '848500', 'M', '3');
INSERT INTO `system_address` VALUES ('3306', '伊犁哈萨克自治州', '654000', '650000', null, 'Y', '2');
INSERT INTO `system_address` VALUES ('3307', '伊宁市', '654002', '654000', '835000', 'Y', '3');
INSERT INTO `system_address` VALUES ('3308', '奎屯市', '654003', '654000', '833200', 'K', '3');
INSERT INTO `system_address` VALUES ('3309', '霍尔果斯市', '654004', '654000', '835000', 'H', '3');
INSERT INTO `system_address` VALUES ('3310', '伊宁县', '654021', '654000', '835100', 'Y', '3');
INSERT INTO `system_address` VALUES ('3311', '察布查尔锡伯自治县', '654022', '654000', '835300', 'C', '3');
INSERT INTO `system_address` VALUES ('3312', '霍城县', '654023', '654000', '835200', 'H', '3');
INSERT INTO `system_address` VALUES ('3313', '巩留县', '654024', '654000', '835400', 'G', '3');
INSERT INTO `system_address` VALUES ('3314', '新源县', '654025', '654000', '835800', 'X', '3');
INSERT INTO `system_address` VALUES ('3315', '昭苏县', '654026', '654000', '835600', 'Z', '3');
INSERT INTO `system_address` VALUES ('3316', '特克斯县', '654027', '654000', '835500', 'T', '3');
INSERT INTO `system_address` VALUES ('3317', '尼勒克县', '654028', '654000', '835700', 'N', '3');
INSERT INTO `system_address` VALUES ('3318', '塔城地区', '654200', '650000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3319', '塔城市', '654201', '654200', '834300', 'T', '3');
INSERT INTO `system_address` VALUES ('3320', '乌苏市', '654202', '654200', '833300', 'W', '3');
INSERT INTO `system_address` VALUES ('3321', '额敏县', '654221', '654200', '834600', 'E', '3');
INSERT INTO `system_address` VALUES ('3322', '沙湾县', '654223', '654200', '832100', 'S', '3');
INSERT INTO `system_address` VALUES ('3323', '托里县', '654224', '654200', '834500', 'T', '3');
INSERT INTO `system_address` VALUES ('3324', '裕民县', '654225', '654200', '834800', 'Y', '3');
INSERT INTO `system_address` VALUES ('3325', '和布克赛尔蒙古自治县', '654226', '654200', '834400', 'H', '3');
INSERT INTO `system_address` VALUES ('3326', '阿勒泰地区', '654300', '650000', null, 'A', '2');
INSERT INTO `system_address` VALUES ('3327', '阿勒泰市', '654301', '654300', '836500', 'A', '3');
INSERT INTO `system_address` VALUES ('3328', '布尔津县', '654321', '654300', '836600', 'B', '3');
INSERT INTO `system_address` VALUES ('3329', '富蕴县', '654322', '654300', '836100', 'F', '3');
INSERT INTO `system_address` VALUES ('3330', '福海县', '654323', '654300', '836400', 'F', '3');
INSERT INTO `system_address` VALUES ('3331', '哈巴河县', '654324', '654300', '836700', 'H', '3');
INSERT INTO `system_address` VALUES ('3332', '青河县', '654325', '654300', '836200', 'Q', '3');
INSERT INTO `system_address` VALUES ('3333', '吉木乃县', '654326', '654300', '836800', 'J', '3');
INSERT INTO `system_address` VALUES ('3334', '石河子市', '659001', '659000', '832000', 'S', '3');
INSERT INTO `system_address` VALUES ('3335', '阿拉尔市', '659002', '659000', '843300', 'A', '3');
INSERT INTO `system_address` VALUES ('3336', '图木舒克市', '659003', '659000', '844000', 'T', '3');
INSERT INTO `system_address` VALUES ('3337', '五家渠市', '659004', '659000', '831300', 'W', '3');
INSERT INTO `system_address` VALUES ('3338', '北屯市', '659005', '659000', '836000', 'B', '3');
INSERT INTO `system_address` VALUES ('3339', '铁门关市', '659006', '659000', '841007', 'T', '3');
INSERT INTO `system_address` VALUES ('3340', '可克达拉市', '659008', '659000', '835213', 'K', '3');
INSERT INTO `system_address` VALUES ('3341', '台湾', '710000', '0', null, 'T', '1');
INSERT INTO `system_address` VALUES ('3342', '香港', '810000', '0', null, 'X', '1');
INSERT INTO `system_address` VALUES ('3343', '中西區', '810101', '810100', '999077', 'Z', '3');
INSERT INTO `system_address` VALUES ('3344', '灣仔區', '810102', '810100', '999077', 'W', '3');
INSERT INTO `system_address` VALUES ('3345', '東區', '810103', '810100', '999077', 'D', '3');
INSERT INTO `system_address` VALUES ('3346', '南區', '810104', '810100', '999077', 'N', '3');
INSERT INTO `system_address` VALUES ('3347', '油尖旺區', '810105', '810100', '999077', 'Y', '3');
INSERT INTO `system_address` VALUES ('3348', '深水埗區', '810106', '810100', '999077', 'S', '3');
INSERT INTO `system_address` VALUES ('3349', '九龍城區', '810107', '810100', '999077', 'J', '3');
INSERT INTO `system_address` VALUES ('3350', '黃大仙區', '810108', '810100', '999077', 'H', '3');
INSERT INTO `system_address` VALUES ('3351', '觀塘區', '810109', '810100', '999077', 'G', '3');
INSERT INTO `system_address` VALUES ('3352', '荃灣區', '810110', '810100', '999077', 'Q', '3');
INSERT INTO `system_address` VALUES ('3353', '屯門區', '810111', '810100', '999077', 'T', '3');
INSERT INTO `system_address` VALUES ('3354', '元朗區', '810112', '810100', '999077', 'Y', '3');
INSERT INTO `system_address` VALUES ('3355', '北區', '810113', '810100', '999077', 'B', '3');
INSERT INTO `system_address` VALUES ('3356', '大埔區', '810114', '810100', '999077', 'D', '3');
INSERT INTO `system_address` VALUES ('3357', '西貢區', '810115', '810100', '999077', 'X', '3');
INSERT INTO `system_address` VALUES ('3358', '沙田區', '810116', '810100', '999077', 'S', '3');
INSERT INTO `system_address` VALUES ('3359', '葵青區', '810117', '810100', '999077', 'K', '3');
INSERT INTO `system_address` VALUES ('3360', '離島區', '810118', '810100', '999077', 'L', '3');
INSERT INTO `system_address` VALUES ('3361', '澳门', '820000', '0', null, 'A', '1');
INSERT INTO `system_address` VALUES ('3362', '花地瑪堂區', '820101', '820100', '999078', 'H', '3');
INSERT INTO `system_address` VALUES ('3363', '花王堂區', '820102', '820100', '999078', 'H', '3');
INSERT INTO `system_address` VALUES ('3364', '望德堂區', '820103', '820100', '999078', 'W', '3');
INSERT INTO `system_address` VALUES ('3365', '大堂區', '820104', '820100', '999078', 'D', '3');
INSERT INTO `system_address` VALUES ('3366', '風順堂區', '820105', '820100', '999078', 'F', '3');
INSERT INTO `system_address` VALUES ('3367', '嘉模堂區', '820106', '820100', '999078', 'J', '3');
INSERT INTO `system_address` VALUES ('3368', '路氹填海區', '820107', '820100', '999078', 'L', '3');
INSERT INTO `system_address` VALUES ('3369', '聖方濟各堂區', '820108', '820100', '999078', 'S', '3');
INSERT INTO `system_address` VALUES ('3370', '济源市', '419001', '419000', '454650', 'J', '3');
INSERT INTO `system_address` VALUES ('3371', '省直辖县', '429000', '420000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3372', '市区', '441901', '441900', '523000', 'S', '3');
INSERT INTO `system_address` VALUES ('3373', '市辖区', '620201', '620200', '735100', 'S', '3');
INSERT INTO `system_address` VALUES ('3374', '三沙市', '460300', '460000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3375', '省直辖县', '469000', '460000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3376', '白沙黎族自治县', '469025', '469000', '572800', 'B', '3');
INSERT INTO `system_address` VALUES ('3377', '市辖区', '500100', '500000', null, 'S', '2');
INSERT INTO `system_address` VALUES ('3378', '县', '500200', '500000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('3379', '台北市', '710100', '710000', '000100', 'T', '2');
INSERT INTO `system_address` VALUES ('3380', '新北市', '710200', '710000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('3381', '桃园市', '710300', '710000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3382', '台中市', '710400', '710000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3383', '台南市', '710500', '710000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3384', '高雄市', '710600', '710000', null, 'G', '2');
INSERT INTO `system_address` VALUES ('3385', '基隆市', '710700', '710000', '', 'J', '2');
INSERT INTO `system_address` VALUES ('3386', '新竹市', '710800', '710000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('3387', '嘉义市', '710900', '710000', null, 'J', '2');
INSERT INTO `system_address` VALUES ('3388', '县', '711000', '710000', null, 'X', '2');
INSERT INTO `system_address` VALUES ('3389', '新竹县', '711010', '711000', '000300', 'X', '3');
INSERT INTO `system_address` VALUES ('3390', '苗栗县', '711020', '711000', '000037', 'M', '3');
INSERT INTO `system_address` VALUES ('3391', '彰化县', '711030', '711000', '000500', 'Z', '3');
INSERT INTO `system_address` VALUES ('3392', '南投县', '711040', '711000', '000540', 'N', '3');
INSERT INTO `system_address` VALUES ('3393', '云林县', '711050', '711000', '000640', 'Y', '3');
INSERT INTO `system_address` VALUES ('3394', '嘉义县', '711060', '711000', '000600', 'J', '3');
INSERT INTO `system_address` VALUES ('3395', '屏东县', '711070', '711000', '000900', 'P', '3');
INSERT INTO `system_address` VALUES ('3396', '宜兰县', '711080', '711000', '000260', 'Y', '3');
INSERT INTO `system_address` VALUES ('3397', '花莲县', '711090', '711000', '097047', 'H', '3');
INSERT INTO `system_address` VALUES ('3398', '台东县', '711100', '711000', '000950', 'T', '3');
INSERT INTO `system_address` VALUES ('3399', '澎湖县', '711110', '711000', '000880', 'P', '3');
INSERT INTO `system_address` VALUES ('3400', '金门县', '711120', '711000', '000890', 'J', '3');
INSERT INTO `system_address` VALUES ('3402', '中正区', '710101', '710100', '000100', 'Z', '3');
INSERT INTO `system_address` VALUES ('3403', '大同区', '710102', '710100', '000103', 'D', '3');
INSERT INTO `system_address` VALUES ('3404', '中山区', '710103', '710100', '000104', 'Z', '3');
INSERT INTO `system_address` VALUES ('3405', '万华区', '710104', '710100', '000108', 'W', '3');
INSERT INTO `system_address` VALUES ('3406', '信义区', '710105', '710100', '000110', 'X', '3');
INSERT INTO `system_address` VALUES ('3407', '松山区', '710106', '710100', '000105', 'S', '3');
INSERT INTO `system_address` VALUES ('3408', '大安区', '710107', '710100', '000106', 'D', '3');
INSERT INTO `system_address` VALUES ('3409', '南港区', '710108', '710100', '000115', 'N', '3');
INSERT INTO `system_address` VALUES ('3410', '北投区', '710109', '710100', '000112', 'B', '3');
INSERT INTO `system_address` VALUES ('3411', '内湖区', '710110', '710100', '000114', 'N', '3');
INSERT INTO `system_address` VALUES ('3412', '士林区', '710111', '710100', '000111', 'S', '3');
INSERT INTO `system_address` VALUES ('3413', '文山区', '710112', '710100', '000116', 'W', '3');
INSERT INTO `system_address` VALUES ('3414', '板桥区', '710201', '710200', '000220', 'B', '3');
INSERT INTO `system_address` VALUES ('3415', '土城区', '710202', '710200', '000236', 'T', '3');
INSERT INTO `system_address` VALUES ('3416', '新庄区', '710203', '710200', '000242', 'X', '3');
INSERT INTO `system_address` VALUES ('3417', '新店区', '710204', '710200', '000231', 'X', '3');
INSERT INTO `system_address` VALUES ('3418', '深坑区', '710205', '710200', '000222', 'S', '3');
INSERT INTO `system_address` VALUES ('3419', '石碇区', '710206', '710200', '000223', 'S', '3');
INSERT INTO `system_address` VALUES ('3420', '坪林区', '710207', '710200', '000232', 'P', '3');
INSERT INTO `system_address` VALUES ('3421', '乌来区', '710208', '710200', '000233', 'W', '3');
INSERT INTO `system_address` VALUES ('3422', '五股区', '710209', '710200', '000248', 'W', '3');
INSERT INTO `system_address` VALUES ('3423', '八里区', '710210', '710200', '000249', 'B', '3');
INSERT INTO `system_address` VALUES ('3424', '林口区', '710211', '710200', '000244', 'L', '3');
INSERT INTO `system_address` VALUES ('3425', '淡水区', '710212', '710200', '025170', 'D', '3');
INSERT INTO `system_address` VALUES ('3426', '中和区', '710213', '710200', '023545', 'Z', '3');
INSERT INTO `system_address` VALUES ('3427', '永和区', '710214', '710200', '000234', 'Y', '3');
INSERT INTO `system_address` VALUES ('3428', '三重区', '710215', '710200', '000241', 'S', '3');
INSERT INTO `system_address` VALUES ('3429', '芦洲区', '710216', '710200', '000247', 'L', '3');
INSERT INTO `system_address` VALUES ('3430', '泰山区', '710217', '710200', '000243', 'T', '3');
INSERT INTO `system_address` VALUES ('3431', '树林区', '710218', '710200', '000238', 'L', '3');
INSERT INTO `system_address` VALUES ('3432', '莺歌区', '710219', '710200', '000239', 'Y', '3');
INSERT INTO `system_address` VALUES ('3433', '三峡区', '710220', '710200', '000237', 'S', '3');
INSERT INTO `system_address` VALUES ('3434', '汐止区', '710221', '710200', '000221', 'X', '3');
INSERT INTO `system_address` VALUES ('3435', '金山区', '710222', '710200', '020841', 'J', '3');
INSERT INTO `system_address` VALUES ('3436', '万里区', '710223', '710200', '000207', 'W', '3');
INSERT INTO `system_address` VALUES ('3437', '三芝区', '710224', '710200', '000252', 'S', '3');
INSERT INTO `system_address` VALUES ('3438', '石门区', '710225', '710200', '000253', 'S', '3');
INSERT INTO `system_address` VALUES ('3439', '瑞芳区', '710226', '710200', '000224', 'R', '3');
INSERT INTO `system_address` VALUES ('3440', '贡寮区', '710227', '710200', '000228', 'G', '3');
INSERT INTO `system_address` VALUES ('3441', '双溪区', '710228', '710200', '000227', 'S', '3');
INSERT INTO `system_address` VALUES ('3442', '平溪区', '710229', '710200', '000226', 'P', '3');
INSERT INTO `system_address` VALUES ('3443', '桃园区', '710301', '710300', '000330', 'T', '3');
INSERT INTO `system_address` VALUES ('3444', '中坜区', '710302', '710300', '000320', 'Z', '3');
INSERT INTO `system_address` VALUES ('3445', '平镇区', '710303', '710300', '000324', 'P', '3');
INSERT INTO `system_address` VALUES ('3446', '八德区', '710304', '710300', '000334', 'B', '3');
INSERT INTO `system_address` VALUES ('3447', '杨梅区', '710305', '710300', '000326', 'Y', '3');
INSERT INTO `system_address` VALUES ('3448', '芦竹区', '710306', '710300', '000338', 'L', '3');
INSERT INTO `system_address` VALUES ('3449', '大溪区', '710307', '710300', '000335', 'D', '3');
INSERT INTO `system_address` VALUES ('3450', '龙潭区', '710308', '710300', '000325', 'Z', '3');
INSERT INTO `system_address` VALUES ('3451', '龟山区', '710309', '710300', '000333', 'G', '3');
INSERT INTO `system_address` VALUES ('3452', '大园区', '710310', '710300', '000337', 'D', '3');
INSERT INTO `system_address` VALUES ('3453', '观音区', '710311', '710300', '000328', 'G', '3');
INSERT INTO `system_address` VALUES ('3454', '新屋区', '710312', '710300', '000327', 'X', '3');
INSERT INTO `system_address` VALUES ('3455', '复兴区', '710313', '710300', '000336', 'F', '3');
INSERT INTO `system_address` VALUES ('3456', '中区', '710401', '710400', '000400', 'Z', '3');
INSERT INTO `system_address` VALUES ('3457', '东区', '710402', '710400', '000401', 'D', '3');
INSERT INTO `system_address` VALUES ('3458', '西区', '710403', '710400', '000403', 'X', '3');
INSERT INTO `system_address` VALUES ('3459', '南区', '710404', '710400', '000402', 'N', '3');
INSERT INTO `system_address` VALUES ('3460', '北区', '710405', '710400', '000404', 'B', '3');
INSERT INTO `system_address` VALUES ('3461', '西屯区', '710406', '710400', '000407', 'X', '3');
INSERT INTO `system_address` VALUES ('3462', '南屯区', '710407', '710400', '000408', 'N', '3');
INSERT INTO `system_address` VALUES ('3463', '北屯区', '710408', '710400', '000406', 'B', '3');
INSERT INTO `system_address` VALUES ('3464', '丰原区', '710409', '710400', '000420', 'F', '3');
INSERT INTO `system_address` VALUES ('3465', '大里区', '710410', '710400', '000412', 'D', '3');
INSERT INTO `system_address` VALUES ('3466', '太平区', '710411', '710400', '000411', 'T', '3');
INSERT INTO `system_address` VALUES ('3467', '东势区', '710412', '710400', '000423', 'D', '3');
INSERT INTO `system_address` VALUES ('3468', '大甲区\r\n', '710413', '710400', '000437', 'D', '3');
INSERT INTO `system_address` VALUES ('3469', '清水区', '710414', '710400', '000436', 'Q', '3');
INSERT INTO `system_address` VALUES ('3470', '沙鹿区', '710415', '710400', '000433', 'S', '3');
INSERT INTO `system_address` VALUES ('3471', '梧栖区', '710416', '710400', '000435', 'W', '3');
INSERT INTO `system_address` VALUES ('3472', '后里区', '710417', '710400', '000421', 'H', '3');
INSERT INTO `system_address` VALUES ('3473', '神冈区', '710418', '710400', '000429', 'S', '3');
INSERT INTO `system_address` VALUES ('3474', '潭子区', '710419', '710400', '000427', 'T', '3');
INSERT INTO `system_address` VALUES ('3475', '大雅区', '710420', '710400', '000428', 'D', '3');
INSERT INTO `system_address` VALUES ('3476', '新社区', '710421', '710400', '000426', 'X', '3');
INSERT INTO `system_address` VALUES ('3477', '石冈区', '710422', '710400', '000422', 'S', '3');
INSERT INTO `system_address` VALUES ('3478', '外埔区', '710423', '710400', '000438', 'W', '3');
INSERT INTO `system_address` VALUES ('3479', '大安区', '710424', '710400', '000439', 'D', '3');
INSERT INTO `system_address` VALUES ('3480', '乌日区', '710425', '710400', '000414', 'W', '3');
INSERT INTO `system_address` VALUES ('3481', '大肚区', '710426', '710400', '000432', 'D', '3');
INSERT INTO `system_address` VALUES ('3482', '龙井区', '710427', '710400', '000434', 'L', '3');
INSERT INTO `system_address` VALUES ('3483', '雾峰区', '710428', '710400', '000413', 'W', '3');
INSERT INTO `system_address` VALUES ('3484', '和平区', '710429', '710400', '000424', 'H', '3');
INSERT INTO `system_address` VALUES ('3485', '中西区', '710501', '710500', '000700', 'Z', '3');
INSERT INTO `system_address` VALUES ('3486', '东区', '710502', '710500', '000701', 'D', '3');
INSERT INTO `system_address` VALUES ('3487', '南区', '710503', '710500', '000702', 'N', '3');
INSERT INTO `system_address` VALUES ('3488', '北区', '710504', '710500', '000704', 'N', '3');
INSERT INTO `system_address` VALUES ('3489', '安平区', '710505', '710500', '000708', 'A', '3');
INSERT INTO `system_address` VALUES ('3490', '安南区', '710506', '710500', '000709', 'A', '3');
INSERT INTO `system_address` VALUES ('3491', '永康区', '710507', '710500', '000710', 'Y', '3');
INSERT INTO `system_address` VALUES ('3492', '归仁区', '710508', '710500', '000711', 'G', '3');
INSERT INTO `system_address` VALUES ('3493', '新化区', '710509', '710500', '000712', 'X', '3');
INSERT INTO `system_address` VALUES ('3494', '左镇区', '710510', '710500', '000713', 'Z', '3');
INSERT INTO `system_address` VALUES ('3495', '玉井区', '710511', '710500', '000714', 'Y', '3');
INSERT INTO `system_address` VALUES ('3496', '楠西区', '710512', '710500', '000715', 'N', '3');
INSERT INTO `system_address` VALUES ('3497', '南化区', '710513', '710500', '000716', 'N', '3');
INSERT INTO `system_address` VALUES ('3498', '仁德区', '710514', '710500', '000717', 'R', '3');
INSERT INTO `system_address` VALUES ('3499', '关庙区', '710515', '710500', '000718', 'G', '3');
INSERT INTO `system_address` VALUES ('3500', '龙崎区', '710516', '710500', '000719', 'L', '3');
INSERT INTO `system_address` VALUES ('3501', '官田区', '710517', '710500', '000720', 'G', '3');
INSERT INTO `system_address` VALUES ('3502', '麻豆区', '710518', '710500', '000721', 'M', '3');
INSERT INTO `system_address` VALUES ('3503', '佳里区', '710519', '710500', '000722', 'J', '3');
INSERT INTO `system_address` VALUES ('3504', '西港区', '710520', '710500', '000723', 'X', '3');
INSERT INTO `system_address` VALUES ('3505', '七股区', '710521', '710500', '000724', 'Q', '3');
INSERT INTO `system_address` VALUES ('3506', '将军区', '710522', '710500', '000725', 'J', '3');
INSERT INTO `system_address` VALUES ('3507', '学甲区', '710523', '710500', '000726', 'X', '3');
INSERT INTO `system_address` VALUES ('3508', '北门区', '710524', '710500', '000727', 'B', '3');
INSERT INTO `system_address` VALUES ('3509', '新营区', '710525', '710500', '000730', 'X', '3');
INSERT INTO `system_address` VALUES ('3510', '后壁区', '710526', '710500', '000731', 'H', '3');
INSERT INTO `system_address` VALUES ('3511', '白河区', '710527', '710500', '000732', 'B', '3');
INSERT INTO `system_address` VALUES ('3512', '东山区', '710528', '710500', '000733', 'D', '3');
INSERT INTO `system_address` VALUES ('3513', '六甲区', '710529', '710500', '000734', 'L', '3');
INSERT INTO `system_address` VALUES ('3514', '下营区', '710530', '710500', '000735', 'X', '3');
INSERT INTO `system_address` VALUES ('3515', '柳营区', '710531', '710500', '000736', 'L', '3');
INSERT INTO `system_address` VALUES ('3516', '盐水区', '710532', '710500', '000737', 'Y', '3');
INSERT INTO `system_address` VALUES ('3517', '善化区', '710533', '710500', '000741', 'S', '3');
INSERT INTO `system_address` VALUES ('3518', '大内区', '710534', '710500', '000742', 'D', '3');
INSERT INTO `system_address` VALUES ('3519', '山上区', '710535', '710500', '000743', 's', '3');
INSERT INTO `system_address` VALUES ('3520', '新市区', '710536', '710500', '000744', 'X', '3');
INSERT INTO `system_address` VALUES ('3521', '安定区', '710537', '710500', '000745', 'A', '3');
INSERT INTO `system_address` VALUES ('3522', '楠梓区', '710601', '710600', '000811', 'N', '3');
INSERT INTO `system_address` VALUES ('3523', '左营区', '710602', '710600', '000813', 'Z', '3');
INSERT INTO `system_address` VALUES ('3524', '鼓山区', '710603', '710600', '000804', 'G', '3');
INSERT INTO `system_address` VALUES ('3525', '三民区', '710604', '710600', '000807', 'S', '3');
INSERT INTO `system_address` VALUES ('3526', '盐埕区', '710605', '710600', '000803', 'Y', '3');
INSERT INTO `system_address` VALUES ('3527', '前金区', '710606', '710600', '000801', 'Q', '3');
INSERT INTO `system_address` VALUES ('3528', '新兴区', '710607', '710600', '000800', 'X', '3');
INSERT INTO `system_address` VALUES ('3529', '苓雅区', '710608', '710600', '000802', 'L', '3');
INSERT INTO `system_address` VALUES ('3530', '前镇区', '710609', '710600', '000806', 'Q', '3');
INSERT INTO `system_address` VALUES ('3531', '旗津区', '710610', '710600', '000805', 'Q', '3');
INSERT INTO `system_address` VALUES ('3532', '小港区', '710611', '710600', '000812', 'X', '3');
INSERT INTO `system_address` VALUES ('3533', '凤山区', '710612', '710600', '000830', 'F', '3');
INSERT INTO `system_address` VALUES ('3534', '大寮区', '710613', '710600', '000831', 'D', '3');
INSERT INTO `system_address` VALUES ('3535', '鸟松区', '710614', '710600', '000833', 'N', '3');
INSERT INTO `system_address` VALUES ('3536', '林园区', '710615', '710600', '000832', 'L', '3');
INSERT INTO `system_address` VALUES ('3537', '仁武区', '710616', '710600', '000814', 'W', '3');
INSERT INTO `system_address` VALUES ('3538', '大树区', '710617', '710600', '000840', 'D', '3');
INSERT INTO `system_address` VALUES ('3539', '大社区', '710618', '710600', '000815', 'D', '3');
INSERT INTO `system_address` VALUES ('3540', '冈山区', '710619', '710600', '000820', 'G', '3');
INSERT INTO `system_address` VALUES ('3541', '路竹区', '710620', '710600', '000821', 'L', '3');
INSERT INTO `system_address` VALUES ('3542', '桥头区', '710621', '710600', '000825', 'Q', '3');
INSERT INTO `system_address` VALUES ('3543', '梓官区', '710622', '710600', '000826', 'X', '3');
INSERT INTO `system_address` VALUES ('3544', '弥陀区', '710623', '710600', '000827', 'M', '3');
INSERT INTO `system_address` VALUES ('3545', '永安区', '710624', '710600', '000828', 'Y', '3');
INSERT INTO `system_address` VALUES ('3546', '燕巢区', '710625', '710600', '000824', 'Y', '3');
INSERT INTO `system_address` VALUES ('3547', '阿莲区', '710626', '710600', '000822', 'A', '3');
INSERT INTO `system_address` VALUES ('3548', '茄萣区', '710627', '710600', '000852', 'Q', '3');
INSERT INTO `system_address` VALUES ('3549', '湖内区', '710628', '710600', '000829', 'H', '3');
INSERT INTO `system_address` VALUES ('3550', '旗山区', '710629', '710600', '000842', 'H', '3');
INSERT INTO `system_address` VALUES ('3551', '美浓区', '710630', '710600', '000843', 'M', '3');
INSERT INTO `system_address` VALUES ('3552', '内门区', '710631', '710600', '000845', 'N', '3');
INSERT INTO `system_address` VALUES ('3553', '杉林区', '710632', '710600', '000846', 'S', '3');
INSERT INTO `system_address` VALUES ('3554', '甲仙区', '710633', '710600', '000847', 'J', '3');
INSERT INTO `system_address` VALUES ('3555', '六龟区', '710634', '710600', '000844', 'L', '3');
INSERT INTO `system_address` VALUES ('3556', '茂林区', '710635', '710600', '000851', 'M', '3');
INSERT INTO `system_address` VALUES ('3557', '桃源区', '710636', '710600', '000848', 'T', '3');
INSERT INTO `system_address` VALUES ('3558', '那玛夏区', '710637', '710600', '000849', 'N', '3');
INSERT INTO `system_address` VALUES ('3559', '中正区', '710701', '710700', '000202', 'Z', '3');
INSERT INTO `system_address` VALUES ('3560', '七堵区', '710702', '710700', '000206', 'Q', '3');
INSERT INTO `system_address` VALUES ('3561', '暖暖区', '710703', '710700', '000205', 'N', '3');
INSERT INTO `system_address` VALUES ('3562', '仁爱区', '710704', '710700', '000200', 'R', '3');
INSERT INTO `system_address` VALUES ('3563', '中山区', '710705', '710700', '000203', 'Z', '3');
INSERT INTO `system_address` VALUES ('3564', '安乐区', '710706', '710700', '000204', 'A', '3');
INSERT INTO `system_address` VALUES ('3565', '信义区', '710707', '710700', '000201', 'X', '3');
INSERT INTO `system_address` VALUES ('3566', '东区', '710801', '710800', '000300', 'D', '3');
INSERT INTO `system_address` VALUES ('3567', '北区', '710802', '710800', '000300', 'B', '3');
INSERT INTO `system_address` VALUES ('3568', '香山区', '710803', '710800', '000300', 'X', '3');
INSERT INTO `system_address` VALUES ('3569', '东区', '710901', '710900', '000600', 'D', '3');
INSERT INTO `system_address` VALUES ('3570', '西区', '710902', '710900', '000600', 'X', '3');
INSERT INTO `system_address` VALUES ('3571', '自治直辖县', '659000', '650000', null, 'Z', '2');
INSERT INTO `system_address` VALUES ('3572', '特别行政区', '810100', '810000', null, 'T', '2');
INSERT INTO `system_address` VALUES ('3573', '特别行政区', '820100', '820000', null, 'T', '2');

-- ----------------------------
-- Table structure for system_cache
-- ----------------------------
DROP TABLE IF EXISTS `system_cache`;
CREATE TABLE `system_cache` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(50) DEFAULT NULL COMMENT '缓存名称',
  `cache_name` char(50) DEFAULT NULL COMMENT '缓存名称 必须与CacheConstant中保持一致',
  `state` tinyint(3) unsigned DEFAULT '0' COMMENT '缓存更新状态 0:未更新 1:更新成功 2:更新失败',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='缓存信息管理表';

-- ----------------------------
-- Records of system_cache
-- ----------------------------
INSERT INTO `system_cache` VALUES ('1', '系统参数缓存', 'system_config', '1', '2019-08-09 14:11:53', '全局系统参数缓存(查询缓存)');
INSERT INTO `system_cache` VALUES ('2', '数据字典缓存', 'system_dict', '1', '2019-08-09 14:11:53', '全局数据字典缓存(查询缓存)');
INSERT INTO `system_cache` VALUES ('4', '用户登陆token缓存', 'access_token', '1', '2019-08-09 14:11:53', '登陆信息(保存缓存)');
INSERT INTO `system_cache` VALUES ('6', '异步结果缓存', 'async_response', '1', '2019-08-09 14:11:53', '异步信息(保存缓存)');

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` char(50) NOT NULL COMMENT '参数标示符',
  `title` char(50) DEFAULT NULL COMMENT '参数名称',
  `content` varchar(500) NOT NULL COMMENT '参数值',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '参数类型,见system_dict表nid=config_classify',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定状态(禁止编辑) 0:未锁定,1:锁定',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `reserve_content` varchar(500) DEFAULT NULL COMMENT '备用值,如果不在有效期内自动启用备用值',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `nid_index` (`nid`) USING BTREE,
  KEY `type_index` (`classify`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数配置信息表';

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('1', 'application_name', '后台系统名称', '后台管理系统', '1', '\0', '2019-01-17 00:00:00', '2019-02-19 00:00:00', '阿斯顿', '', '2018-01-12 10:01:04', '2019-01-22 17:37:12');
INSERT INTO `system_config` VALUES ('2', 'enterprise_name', '企业名称', '二哥很猛', '1', '\0', null, null, null, null, '2018-02-08 14:38:59', null);
INSERT INTO `system_config` VALUES ('3', 'enterprise_address', '企业地址', '浙江省杭州市余杭区中泰街道338号', '1', '\0', null, null, null, null, '2018-02-08 14:40:01', null);
INSERT INTO `system_config` VALUES ('4', 'enterprise_phone', '企业电话', '0571-00000000', '1', '\0', null, null, null, null, '2018-02-08 14:40:46', null);
INSERT INTO `system_config` VALUES ('5', 'enterprise_email', '企业邮箱', '664956140@qq.com', '1', '\0', null, null, null, null, '2018-02-08 14:41:22', null);
INSERT INTO `system_config` VALUES ('6', 'ios_latest_version', 'ios最新版本号', '1.2.3', '1', '\0', null, null, null, '最新版本号,格式必须为x.x.x', '2018-09-28 10:50:03', null);
INSERT INTO `system_config` VALUES ('7', 'android_latest_version', 'android最新版本', '1.2.3', '1', '\0', null, null, null, '最新版本号,格式必须为x.x.x', '2018-09-28 10:50:41', null);
INSERT INTO `system_config` VALUES ('11', 'system_domain', '前台系统域名', 'http://www.eghm.top', '1', '\0', null, null, null, '前台提供服务的域名', '2018-11-25 21:02:17', null);
INSERT INTO `system_config` VALUES ('12', 'system_ip', '前台系统IP', 'http://127.0.0.1:8080', '1', '\0', null, null, null, '前台提供服务的ip', '2018-11-25 21:03:13', null);
INSERT INTO `system_config` VALUES ('13', 'manage_domain', '后台系统域名', 'http://www.baidu.com', '1', '\0', null, null, null, null, '2018-11-29 16:41:04', null);
INSERT INTO `system_config` VALUES ('14', 'operation_log_switch', '操作日志开关', '0', '1', '\0', null, null, null, '操作日志开关 0:不开启操作日志 1:开启操作日志', '2019-01-17 16:50:54', null);
INSERT INTO `system_config` VALUES ('15', 'env', '系统环境', '2', '2', '\0', null, null, '', '1 生产 2 开发 3  测试', '2019-01-22 17:23:19', null);
INSERT INTO `system_config` VALUES ('16', 'timestamp_deviation', '客户端与服务端时间容错值', '30000', '1', '\0', null, null, null, '单位:毫秒', '2019-07-10 16:50:31', '2019-07-10 16:52:43');
INSERT INTO `system_config` VALUES ('17', 'send_from', '系统邮件发件人', '664956140@qq.com', '2', '\0', null, null, null, null, '2019-07-10 16:53:01', '2019-07-10 16:53:14');
INSERT INTO `system_config` VALUES ('18', 'token_expire', 'token过期时间', '864000', '2', '\0', null, null, null, '单位秒', '2019-08-13 15:18:33', '2019-08-13 15:18:33');
INSERT INTO `system_config` VALUES ('19', 'sso_open', '是否开启单设备单点登录', '1', '2', '\0', null, null, null, '0:不开启 1:开启', '2019-08-13 15:45:39', '2019-08-13 15:45:39');
INSERT INTO `system_config` VALUES ('20', 'nick_name_prefix', '默认昵称前缀', 'eghm_', '1', '\0', null, null, null, '昵称为空时会自动生成以此为前缀的昵称', '2019-08-19 16:06:04', '2019-08-19 16:06:04');

-- ----------------------------
-- Table structure for system_department
-- ----------------------------
DROP TABLE IF EXISTS `system_department`;
CREATE TABLE `system_department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(50) DEFAULT NULL COMMENT '部门名称',
  `code` char(128) DEFAULT NULL COMMENT '部门编号',
  `parent_code` char(64) DEFAULT NULL COMMENT '父级编号',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `operator_name` char(20) DEFAULT NULL COMMENT '操作人姓名',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- ----------------------------
-- Records of system_department
-- ----------------------------

-- ----------------------------
-- Table structure for system_dict
-- ----------------------------
DROP TABLE IF EXISTS `system_dict`;
CREATE TABLE `system_dict` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(50) DEFAULT NULL COMMENT '字典中文名称',
  `nid` char(50) DEFAULT NULL COMMENT '数据字典nid(英文名称)',
  `hidden_value` tinyint(2) unsigned DEFAULT NULL COMMENT '数据字典隐藏值 1~∞',
  `show_value` char(50) DEFAULT NULL COMMENT '显示值',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
  `locked` bit(1) DEFAULT b'0' COMMENT '锁定状态(禁止编辑):0:未锁定 1:锁定',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='系统数据字典表';

-- ----------------------------
-- Records of system_dict
-- ----------------------------
INSERT INTO `system_dict` VALUES ('1', '图片分类', 'image_classify', '1', 'pc首页', '\0', '', '2018-11-27 17:14:49', null, null);
INSERT INTO `system_dict` VALUES ('2', '图片分类', 'image_classify', '2', 'app首页', '\0', '', '2018-11-27 17:15:33', null, null);
INSERT INTO `system_dict` VALUES ('3', '图片分类', 'image_classify', '3', 'h5首页', '\0', '', '2018-11-27 17:15:55', null, null);
INSERT INTO `system_dict` VALUES ('4', '系统参数分类', 'config_classify', '1', '业务参数', '\0', '', '2019-01-11 11:02:39', '2019-01-15 10:11:36', '');
INSERT INTO `system_dict` VALUES ('5', '系统参数分类', 'config_classify', '2', '系统参数', '\0', '', '2019-01-11 11:03:00', '2019-01-15 10:11:57', '是东方闪电2131');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(20) NOT NULL COMMENT '菜单名称',
  `nid` char(50) NOT NULL COMMENT '菜单标示符 唯一',
  `pid` int(10) unsigned NOT NULL COMMENT '父节点ID,一级菜单默认为0',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单地址',
  `sub_url` varchar(500) DEFAULT NULL COMMENT '权限拦截路径',
  `classify` tinyint(1) unsigned DEFAULT '1' COMMENT '菜单分类 0:左侧菜单 1: 按钮菜单',
  `sort` int(3) DEFAULT '0' COMMENT '排序规则 小的排在前面',
  `deleted` bit(1) DEFAULT b'0' COMMENT '状态:0:正常,1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `nid_unique_index` (`nid`,`deleted`) USING BTREE,
  KEY `pid_index` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1017 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1001', '系统管理', 'systemManage', '0', null, null, '0', '0', '\0', null, '2018-01-25 16:13:54', null);
INSERT INTO `system_menu` VALUES ('1004', '菜单管理', 'menuManage', '1001', '/public/system/menu/manage_page', '/system/menu/list_page', '0', '1', '\0', '', '2018-01-25 16:14:01', '2019-08-08 13:51:17');
INSERT INTO `system_menu` VALUES ('1007', '系统参数', 'systemParamter', '1001', '/public/system/config/manage_page', null, '0', '2', '\0', null, '2018-01-25 16:14:31', '2019-08-08 13:51:07');
INSERT INTO `system_menu` VALUES ('1008', '用户管理', 'systemUser', '1001', '/public/system/operator/manage_page', null, '0', '3', '\0', null, '2018-01-25 16:14:40', '2019-08-08 13:51:10');
INSERT INTO `system_menu` VALUES ('1009', '角色管理', 'roleManage', '1001', '/public/system/role/manage_page', null, '0', '4', '\0', null, '2018-01-25 16:14:56', '2019-08-08 13:52:32');
INSERT INTO `system_menu` VALUES ('1010', '图片管理', 'imageManage', '1001', '/public/system/image/manage_page', null, '0', '5', '\0', null, '2018-11-28 17:02:36', '2019-08-08 13:52:28');
INSERT INTO `system_menu` VALUES ('1011', '数据字典', 'dictManage', '1001', '/public/system/dict/manage_pagess', null, '0', '6', '\0', null, '2019-01-11 17:51:31', '2019-08-08 13:52:24');
INSERT INTO `system_menu` VALUES ('1012', '缓存管理', 'cacheManage', '1001', '/public/system/cache/manage_page', '', '0', '7', '\0', null, '2019-01-14 15:27:58', '2019-08-08 13:51:23');
INSERT INTO `system_menu` VALUES ('1013', '操作日志', 'operationManage', '1001', '/public/system/operation/manage_page', null, '0', '8', '\0', null, '2019-01-16 14:31:01', '2019-08-08 13:52:19');
INSERT INTO `system_menu` VALUES ('1014', '部门管理', 'departmentManage', '1001', '/public/system/department/manage_page', null, '0', '9', '\0', null, '2019-01-17 18:03:54', '2019-08-08 13:51:37');
INSERT INTO `system_menu` VALUES ('1015', '新增', 'menuManageQuery', '1004', '/public/system/menu/add_page', '/system/menu/add', '1', '2', '\0', '按钮权限', '2019-01-22 14:16:11', '2019-08-08 13:51:31');
INSERT INTO `system_menu` VALUES ('1016', '基础', 'menuManageBase', '1004', '', '', '1', '1', '\0', '基础按钮', '2019-01-22 14:19:01', '2019-01-22 14:19:29');

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` char(100) NOT NULL COMMENT '公告标题',
  `classify` tinyint(2) unsigned DEFAULT NULL COMMENT '公告类型(数据字典表notice_classify)',
  `content` text COMMENT '公告内容(富文本)',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常 1:删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告信息表';

-- ----------------------------
-- Records of system_notice
-- ----------------------------

-- ----------------------------
-- Table structure for system_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operation_log`;
CREATE TABLE `system_operation_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(200) DEFAULT NULL COMMENT '请求地址',
  `operator_id` int(10) DEFAULT NULL COMMENT '操作人',
  `operator_name` char(50) DEFAULT NULL COMMENT '操作人姓名',
  `request` varchar(1000) DEFAULT NULL COMMENT '请求参数',
  `response` text COMMENT '响应参数',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `ip` char(64) DEFAULT NULL COMMENT '访问ip',
  `business_time` bigint(12) unsigned DEFAULT NULL COMMENT '业务耗时',
  `classify` tinyint(1) unsigned DEFAULT NULL COMMENT '操作日志分类,参考:MethodType',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1923 DEFAULT CHARSET=utf8mb4 COMMENT='后台操作记录';

-- ----------------------------
-- Records of system_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_operator
-- ----------------------------
DROP TABLE IF EXISTS `system_operator`;
CREATE TABLE `system_operator` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operator_name` char(20) NOT NULL COMMENT '用户名称',
  `mobile` char(11) NOT NULL COMMENT '手机号码(登陆账户)',
  `state` tinyint(1) unsigned DEFAULT '1' COMMENT '用户状态:0:锁定,1:正常',
  `pwd` varchar(256) DEFAULT NULL COMMENT '登陆密码MD5',
  `init_pwd` varchar(256) DEFAULT NULL COMMENT '初始密码',
  `department` char(64) DEFAULT NULL COMMENT '所属部门',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态 0:正常,1:已删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `name_index` (`operator_name`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE,
  KEY `status_index` (`state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='管理后台用户表';

-- ----------------------------
-- Records of system_operator
-- ----------------------------
INSERT INTO `system_operator` VALUES ('1', '超管', '13000000000', '1', '$2a$10$ztSM0sQT.mqMOZBXxjYCK.IOHiKtCvUdKei/drs0qmm081omlcvC6', '$2a$10$5r2rvlqCSSwOHRvoBxQNkecRVKOqcIFF3NY3.FHnrTdtTp7Fmh2omy', '0', '\0', '2018-01-26 10:38:20', '2019-07-19 15:41:56', '');

-- ----------------------------
-- Table structure for system_operator_role
-- ----------------------------
DROP TABLE IF EXISTS `system_operator_role`;
CREATE TABLE `system_operator_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operator_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_index` (`operator_id`) USING BTREE,
  KEY `role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='角色与用户关系表';

-- ----------------------------
-- Records of system_operator_role
-- ----------------------------
INSERT INTO `system_operator_role` VALUES ('5', '1', '1');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` char(10) DEFAULT NULL COMMENT '角色名称',
  `role_type` char(20) DEFAULT NULL COMMENT '角色类型',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除状态:0:正常,1:已删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_name_index` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', '超级管理员', 'administrator', '2018-01-29 13:45:49', '2019-01-15 15:30:07', '\0', '');

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(10) unsigned DEFAULT NULL COMMENT '角色Id',
  `menu_id` int(10) unsigned DEFAULT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id_index` (`role_id`) USING BTREE,
  KEY `menu_id_index` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单关系表';

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES ('215', '1', '1001');
INSERT INTO `system_role_menu` VALUES ('216', '1', '1004');
INSERT INTO `system_role_menu` VALUES ('217', '1', '1016');
INSERT INTO `system_role_menu` VALUES ('218', '1', '1015');
INSERT INTO `system_role_menu` VALUES ('219', '1', '1007');
INSERT INTO `system_role_menu` VALUES ('220', '1', '1008');
INSERT INTO `system_role_menu` VALUES ('221', '1', '1009');
INSERT INTO `system_role_menu` VALUES ('222', '1', '1010');
INSERT INTO `system_role_menu` VALUES ('223', '1', '1011');
INSERT INTO `system_role_menu` VALUES ('224', '1', '1012');
INSERT INTO `system_role_menu` VALUES ('225', '1', '1013');
INSERT INTO `system_role_menu` VALUES ('226', '1', '1014');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` char(20) DEFAULT '' COMMENT '昵称',
  `mobile` char(11) NOT NULL COMMENT '手机号码',
  `email` char(50) DEFAULT NULL COMMENT '电子邮箱',
  `pwd` char(128) NOT NULL COMMENT '登陆密码',
  `state` bit(1) DEFAULT b'1' COMMENT '状态 0:注销  1正常 ',
  `channel` tinyint(1) DEFAULT '0' COMMENT '注册渠道 ',
  `register_ip` char(32) DEFAULT NULL COMMENT '注册地址',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE,
  KEY `email_index` (`email`) USING BTREE,
  KEY `status_index` (`state`) USING BTREE,
  KEY `channel_index` (`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='前台用户基本信息表';

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_ext
-- ----------------------------
DROP TABLE IF EXISTS `user_ext`;
CREATE TABLE `user_ext` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '投资人用户ID',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `real_name` char(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` char(128) DEFAULT NULL COMMENT '身份证号码(前10位加密[18位身份证],前8位加密[15位身份证])',
  `birthday` char(8) DEFAULT NULL COMMENT '生日yyyyMMdd',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id_index` (`user_id`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户扩展信息表';

-- ----------------------------
-- Records of user_ext
-- ----------------------------

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `channel` tinyint(1) unsigned DEFAULT NULL COMMENT '登陆渠道',
  `ip` char(32) DEFAULT NULL COMMENT '登陆ip',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
  `device_type` tinyint(1) unsigned DEFAULT NULL COMMENT '设备类型',
  `software_version` char(12) DEFAULT NULL COMMENT '软件版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登陆日志信息';

