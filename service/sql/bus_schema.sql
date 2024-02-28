DROP TABLE IF EXISTS `item_tag`;
CREATE TABLE item_tag
(
    id          VARCHAR(20) NOT NULL COMMENT '主键',
    pid         VARCHAR(20) DEFAULT '0' COMMENT '父节点id',
    title       VARCHAR(20) COMMENT '标签名称',
    icon        VARCHAR(200) COMMENT '标签图标',
    sort        INT(10) COMMENT '排序',
    create_time DATETIME    DEFAULT NOW() COMMENT '创建时间',
    update_time DATETIME    DEFAULT NOW() ON UPDATE NOW() COMMENT '创建时间',
    deleted     BIT(1)      DEFAULT 0 COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (id)
) COMMENT '零售标签';

DROP TABLE IF EXISTS `homestay`;
CREATE TABLE `homestay`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(50)    DEFAULT NULL COMMENT '民宿名称',
    `level`          tinyint(1)     DEFAULT NULL COMMENT '星级 5:五星级 4:四星级 3:三星级 0: 其他',
    `state`          tinyint(1)     default 0 COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区',
    `detail_address` varchar(100)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `intro`          varchar(100)   DEFAULT NULL COMMENT '简介信息',
    `cover_url`      varchar(100)   DEFAULT NULL COMMENT '封面图片',
    `introduce`      longtext COMMENT '详细介绍',
    `phone`          varchar(20)    DEFAULT NULL COMMENT '联系电话',
    `key_service`    varchar(200)   DEFAULT NULL COMMENT '特色服务',
    `score`          decimal(2, 1)  DEFAULT NULL COMMENT '分数',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `tag`            varchar(200)   DEFAULT NULL COMMENT '标签',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '民宿所属商家',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='民宿信息表';

DROP TABLE IF EXISTS `homestay_room`;
CREATE TABLE `homestay_room`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(50)   DEFAULT NULL COMMENT '房型名称',
    `merchant_id`     bigint(20)    DEFAULT NULL COMMENT '所属商家',
    `homestay_id`     bigint(20)    DEFAULT NULL COMMENT '民宿id',
    `state`           tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `confirmType`     tinyint(1)    DEFAULT '1' COMMENT '订单确认方式: 1: 自动确认 2:手动确认',
    `recommend`       bit(1)        DEFAULT b'0' COMMENT '是否为推荐房型 1:是 0:不是',
    `room`            tinyint(1)    DEFAULT '1' COMMENT '几室',
    `hall`            tinyint(1)    DEFAULT '0' COMMENT '几厅',
    `kitchen`         tinyint(1)    DEFAULT '0' COMMENT '几厨',
    `washroom`        tinyint(1)    DEFAULT '1' COMMENT '卫生间数',
    `dimension`       smallint(3)   DEFAULT NULL COMMENT '面积',
    `resident`        tinyint(2)    DEFAULT '2' COMMENT '居住人数',
    `bed`             tinyint(1)    DEFAULT '1' COMMENT '床数',
    `room_type`       tinyint(1)    DEFAULT NULL COMMENT '房型类型 1:整租 2:单间 3:合租',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `infrastructure`  varchar(200)  DEFAULT NULL COMMENT '基础设施',
    `introduce`       longtext COMMENT '详细介绍',
    `refund_type`     tinyint(1)    DEFAULT '2' COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe` varchar(200)  DEFAULT NULL COMMENT '退款描述',
    `create_date`     date          DEFAULT null COMMENT '创建日期',
    `create_time`     datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房型信息表';

DROP TABLE IF EXISTS `homestay_room_config`;
CREATE TABLE `homestay_room_config`
(
    `id`               bigint(20) NOT NULL COMMENT '主键',
    `state`            bit(1)      DEFAULT b'1' COMMENT '状态 false:不可预定 true:可预定',
    `homestay_room_id` bigint(20)  DEFAULT NULL COMMENT '房型id',
    `homestay_id`      bigint(20)  DEFAULT NULL COMMENT '民宿id(冗余字段)',
    `config_date`      date        DEFAULT NULL COMMENT '日期',
    `line_price`       int(10)     DEFAULT NULL COMMENT '划线机',
    `sale_price`       int(10)     DEFAULT NULL COMMENT '销售价',
    `stock`            smallint(4) DEFAULT NULL COMMENT '剩余库存',
    `sale_num`         smallint(4) DEFAULT '0' COMMENT '已预订数量',
    `create_time`      datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `date_index` (`homestay_room_id`, `config_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房间价格配置表';

DROP TABLE IF EXISTS `line`;
CREATE TABLE `line`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `travel_agency_id`  bigint(20)    DEFAULT NULL COMMENT '所属旅行社id',
    `merchant_id`       bigint(20)    DEFAULT NULL COMMENT '所属商家',
    `title`             varchar(50)   DEFAULT NULL COMMENT '线路名称',
    `state`             tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:强制下架',
    `hot_sell`          bit(1)        DEFAULT b'0' COMMENT '是否为热销商品 true:是 false:不是',
    `start_province_id` bigint(20)    DEFAULT NULL COMMENT '出发省份id',
    `start_city_id`     bigint(20)    DEFAULT NULL COMMENT '出发城市id',
    `cover_url`         varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `sale_num`          int(10)       DEFAULT '0' COMMENT '销售量',
    `score`             decimal(2, 1) DEFAULT NULL COMMENT '分数',
    `total_num`         int(10)       DEFAULT '0' COMMENT '总销量=实际销售+虚拟销量',
    `duration`          tinyint(2)    DEFAULT NULL COMMENT '几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游',
    `advance_day`       tinyint(2)    DEFAULT NULL COMMENT '提前天数',
    `refund_type`       tinyint(1)    DEFAULT '2' COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe`   varchar(200)  DEFAULT NULL COMMENT '退款描述',
    `introduce`         longtext COMMENT '商品介绍',
    `create_date`       date          DEFAULT null COMMENT '创建日期',
    `create_time`       datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路商品信息表';

DROP TABLE IF EXISTS `line_config`;
CREATE TABLE `line_config`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `line_id`     bigint(20)  DEFAULT NULL COMMENT '线路商品id',
    `config_date` date        DEFAULT NULL COMMENT '配置日期',
    `state`       bit(1)      DEFAULT b'0' COMMENT '状态 false:不可预定 true:可预定',
    `stock`       smallint(4) DEFAULT '0' COMMENT '总库存',
    `line_price`  int(10)     DEFAULT '0' COMMENT '划线价',
    `sale_price`  int(10)     DEFAULT '0' COMMENT '销售价格',
    `sale_num`    int(10)     DEFAULT '0' COMMENT '销售数量',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `date_index` (`line_id`, `config_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路商品配置表';

DROP TABLE IF EXISTS `line_day_config`;
CREATE TABLE `line_day_config`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `line_id`      bigint(20)  DEFAULT NULL COMMENT '线路商品id',
    `route_index`  tinyint(2)  DEFAULT '1' COMMENT '行程排序(第几天)',
    `start_point`  varchar(30) DEFAULT NULL COMMENT '出发地点',
    `end_point`    varchar(30) DEFAULT NULL COMMENT '结束地点',
    `traffic_type` tinyint(1)  DEFAULT NULL COMMENT '交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他',
    `repast`       tinyint(2)  DEFAULT '0' COMMENT '包含就餐 1:早餐 2:午餐 4:晚餐',
    `depict`       longtext COMMENT '详细描述信息',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `line_index` (`line_id`, `route_index`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路每日行程配置信息';

DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(50)    DEFAULT NULL COMMENT '商家名称',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `hot_sell`       bit(1)         DEFAULT b'0' COMMENT '是否为热销商品 true:是 false:不是',
    `logo_url`       varchar(200)   DEFAULT NULL COMMENT '商家logo',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '商家封面',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '营业时间',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(50)    DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `phone`          varchar(20)    DEFAULT NULL COMMENT '商家热线',
    `score`          decimal(2, 1)  DEFAULT NULL COMMENT '分数',
    `introduce`      longtext COMMENT '商家介绍',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮商家信息表';

DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `restaurant_id` bigint(20)    DEFAULT NULL COMMENT '餐饮商家id',
    `merchant_id`   bigint(20)    DEFAULT NULL COMMENT '所属商家',
    `title`         varchar(50)   DEFAULT NULL COMMENT '商品名称',
    `state`         tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `hot_sell`      bit(1)        DEFAULT b'0' COMMENT '是否为热销商品 true:是 false:不是',
    `cover_url`     varchar(100)  DEFAULT NULL COMMENT '封面图片',
    `line_price`    int(10)       DEFAULT NULL COMMENT '划线价',
    `sale_price`    int(10)       DEFAULT NULL COMMENT '销售价',
    `stock`         int(10)       DEFAULT NULL COMMENT '剩余库存',
    `sale_num`      int(10)       DEFAULT '0' COMMENT '销售数量',
    `total_num`     int(10)       DEFAULT '0' COMMENT '总销量=虚拟销量+真实销量',
    `score`         decimal(2, 1) DEFAULT NULL COMMENT '商品评分',
    `depict`        varchar(200)  DEFAULT NULL COMMENT '购买说明',
    `quota`         smallint(10)  DEFAULT '1' COMMENT '单次限购数量',
    `valid_days`    smallint(3)   DEFAULT NULL COMMENT '有效期购买之日起(与开始日期和截止日期互斥)',
    `effect_date`   date          DEFAULT NULL COMMENT '使用开始日期(包含) yyyy-MM-dd',
    `expire_date`   date          DEFAULT NULL COMMENT '使用截止日期(包含) yyyy-MM-dd',
    `effect_time`   varchar(10)   DEFAULT NULL COMMENT '使用开始时间 HH:mm',
    `expire_time`   varchar(10)   DEFAULT NULL COMMENT '使用截止时间 HH:mm',
    `introduce`     longtext COMMENT '详细介绍',
    `create_date`   date          DEFAULT null COMMENT '创建日期',
    `create_time`   datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮代金券';

DROP TABLE IF EXISTS `scenic`;
CREATE TABLE `scenic`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `scenic_name`    varchar(50)    DEFAULT NULL COMMENT '景区名称',
    `level`          tinyint(1)     DEFAULT NULL COMMENT '景区等级 5: 5A 4: 4A 3:3A 0:其他',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '景区营业时间',
    `phone`          varchar(20)    DEFAULT NULL COMMENT '景区电话',
    `tag`            varchar(300)   DEFAULT NULL COMMENT '景区标签,逗号分隔',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '景区所属商家(为空则表示系统自营)',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `sort`           smallint(3)    DEFAULT '999' COMMENT '景区排序(小<->大)',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(100)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `min_price`      int(10)        DEFAULT '0' COMMENT '景区最低票价',
    `max_price`      int(10)        DEFAULT '0' COMMENT '景区最高票价',
    `score`          decimal(2, 1)  DEFAULT NULL COMMENT '景区评分',
    `depict`         varchar(100)   DEFAULT NULL COMMENT '景区描述信息',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '景区图片',
    `introduce`      longtext COMMENT '景区详细介绍信息',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='景区信息表';


DROP TABLE IF EXISTS `scenic_ticket`;
CREATE TABLE `scenic_ticket`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `scenic_id`         bigint(20)          DEFAULT NULL COMMENT '门票所属景区',
    `merchant_id`       bigint(20)          DEFAULT NULL COMMENT '所属商家',
    `title`             varchar(50)         DEFAULT NULL COMMENT '门票名称',
    `state`             tinyint(1)          DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `category`          tinyint(2)          DEFAULT NULL COMMENT '门票种类 1: 成人票 2: 老人票 3:儿童票',
    `hot_sell`          bit(1)              DEFAULT b'0' COMMENT '是否为热销商品 true:是 false:不是',
    `cover_url`         varchar(500)        DEFAULT NULL COMMENT '门票封面图',
    `line_price`        int(10)             DEFAULT NULL COMMENT '划线价',
    `sale_price`        int(10)             DEFAULT '0' COMMENT '销售价',
    `advance_day`       tinyint(2) unsigned DEFAULT NULL COMMENT '提前多少天购票',
    `quota`             tinyint(2)          DEFAULT '1' COMMENT '单次最大购买数量',
    `start_date`        date                DEFAULT NULL COMMENT '开始预订时间',
    `end_date`          date                DEFAULT NULL COMMENT '截止预订时间',
    `stock`             int(10)             DEFAULT '0' COMMENT '剩余库存',
    `sale_num`          int(10)             DEFAULT '0' COMMENT '真实销售数量',
    `total_num`         int(10)             DEFAULT '0' COMMENT '总销量=实际销量+虚拟销量',
    `introduce`         longtext COMMENT '门票介绍',
    `verification_type` tinyint(2)          DEFAULT NULL COMMENT '核销方式 1:手动核销 2:自动核销 (凌晨自动核销)',
    `real_buy`          bit(1)              DEFAULT b'0' COMMENT '是否实名购票 0:不实名 1:实名',
    `create_date`       date                DEFAULT null COMMENT '创建日期',
    `create_time`       datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间(订单创建时间)',
    `update_time`       datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)              DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='景区门票信息表';


DROP TABLE IF EXISTS `ticket_order`;
CREATE TABLE `ticket_order`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `scenic_id`         bigint(20)   DEFAULT NULL COMMENT '门票所属景区id(冗余字段)',
    `scenic_name`       varchar(50)  DEFAULT NULL COMMENT '景区名称(冗余字段)',
    `order_no`          varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `line_price`        int(10)      DEFAULT NULL DEFAULT NULL COMMENT '划线价',
    `category`          tinyint(2)   DEFAULT NULL COMMENT '门票种类 1: 成人票 2: 老人票 3:儿童票',
    `visit_date`        date         DEFAULT NULL COMMENT '预计游玩日期',
    `verification_type` tinyint(2)   DEFAULT NULL COMMENT '核销方式 1:手动核销 2:自动核销 (凌晨自动核销)',
    `real_buy`          bit(1)       DEFAULT b'0' COMMENT '是否实名购票 0:不实名 1:实名',
    `introduce`         longtext COMMENT '门票介绍',
    `ticket_id`         bigint(20)   DEFAULT NULL COMMENT '门票id',
    `use_time`          datetime     DEFAULT NULL COMMENT '使用时间',
    `create_time`       datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           varchar(255) DEFAULT '0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='门票订单表';

DROP TABLE IF EXISTS `item_store`;
CREATE TABLE `item_store`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `title`          varchar(50)    DEFAULT NULL COMMENT '店铺名称',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户id',
    `recommend`      bit(1)         DEFAULT b'0' COMMENT '是否为推荐店铺 1:是 0:不是',
    `logo_url`       varchar(200)   DEFAULT NULL COMMENT '店铺logo',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '封面图',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '线下营业时间',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `score`          decimal(2, 1)  DEFAULT NULL COMMENT '评分',
    `telephone`      varchar(20)    DEFAULT NULL COMMENT '商家电话',
    `introduce`      longtext COMMENT '商家介绍',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='店铺信息表';

DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant`
(
    `id`                    bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `merchant_name`         varchar(30)   DEFAULT NULL COMMENT '商家名称',
    `mobile`                varchar(20)   DEFAULT NULL COMMENT '联系人电话(登录手机号)',
    `platform_service_rate` decimal(4, 2) DEFAULT NULL COMMENT ',单位:%',
    `enterprise_type`       tinyint(2)    DEFAULT NULL COMMENT '企业类型 1:个体工商户 2:企业',
    `credit_code`           varchar(30)   DEFAULT NULL COMMENT '社会统一信用代码',
    `business_license_url`  varchar(500)  DEFAULT NULL COMMENT '营业执照图片',
    `legal_name`            varchar(20)   DEFAULT NULL COMMENT '法人姓名',
    `legal_id_card`         varchar(20)   DEFAULT NULL COMMENT '法人身份证',
    `legal_url`             varchar(500)  DEFAULT NULL COMMENT '法人身份证图片',
    `open_id`               varchar(30)   DEFAULT NULL COMMENT 'openId',
    `auth_mobile`           varchar(20)   DEFAULT NULL COMMENT '微信授权手机号',
    `type`                  smallint(4)   DEFAULT NULL COMMENT '商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路 32:场馆',
    `user_id`               bigint(20) NOT NULL COMMENT '商户关联系统用户ID',
    `province_id`           bigint(20)    DEFAULT NULL COMMENT '省份id',
    `city_id`               bigint(20)    DEFAULT NULL COMMENT '城市id',
    `county_id`             bigint(20)    DEFAULT NULL COMMENT '区县id',
    `detail_address`        varchar(100)  DEFAULT NULL COMMENT '详细地址',
    `create_time`           datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`           datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`               bit(1)        DEFAULT b'0' COMMENT '是否为删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4 COMMENT ='商家信息表';

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(30)  DEFAULT NULL COMMENT '优惠券名称',
    `merchant_id`     bigint(20)   DEFAULT NULL COMMENT '商户id',
    `state`           tinyint(1)   DEFAULT '0' COMMENT '状态 0:未启用 1:启用',
    `stock`           smallint(4)  DEFAULT '1' COMMENT '库存',
    `receive_num`     smallint(6)  DEFAULT '0' COMMENT '已领取数量',
    `use_num`         smallint(6)  DEFAULT '0' COMMENT '已使用数量',
    `max_limit`       smallint(2)  DEFAULT '1' COMMENT '单人领取限制',
    `mode`            tinyint(1)   DEFAULT '1' COMMENT '领取方式 1:页面领取 2: 手动发放',
    `coupon_type`     tinyint(1)   DEFAULT '1' COMMENT '优惠券类型 1: 抵扣券 2: 折扣券',
    `use_scope`       tinyint(1)   DEFAULT '1' COMMENT '使用范围  1:店铺通用(只针对零售) 2:指定商品',
    `store_id`        bigint(20) COMMENT '零售店铺id',
    `discount_value`  smallint(3) COMMENT '折扣比例 0-100',
    `deduction_value` smallint(6) COMMENT '抵扣金额 单位:分',
    `use_threshold`   smallint(6)  DEFAULT '0' COMMENT '使用门槛 0:不限制 大于0表示限制启用金额 单位:分',
    `start_time`      datetime     DEFAULT NULL COMMENT '发放开始时间',
    `end_time`        datetime     DEFAULT NULL COMMENT '发放截止时间',
    `use_start_time`  datetime     DEFAULT NULL COMMENT '可以使用的开始时间',
    `use_end_time`    datetime     DEFAULT NULL COMMENT '可以使用的截止时间',
    `instruction`     varchar(200) DEFAULT NULL COMMENT '使用说明',
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券配置表';


DROP TABLE IF EXISTS `coupon_scope`;
CREATE TABLE `coupon_scope`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `coupon_id`    bigint(20)  DEFAULT NULL COMMENT '优惠券id',
    `product_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
    `product_id`   bigint(20)  DEFAULT NULL COMMENT '商品id',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券商品关联表';


DROP TABLE IF EXISTS `member_coupon`;
CREATE TABLE `member_coupon`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `coupon_id`    bigint(20) DEFAULT NULL COMMENT '优惠券id',
    `member_id`    bigint(20) DEFAULT NULL COMMENT '用户id',
    `state`        tinyint(1) DEFAULT '0' COMMENT '使用状态 0:未使用 1:已使用 2:已过期',
    `receive_time` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    `use_time`     datetime   DEFAULT NULL COMMENT '使用时间',
    `create_time`  datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)     DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户优惠券表';

DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(20)  DEFAULT NULL COMMENT '活动名称',
    `now_date`    date         DEFAULT NULL COMMENT '日期',
    `start_time`  char(5)      DEFAULT NULL COMMENT '开始时间HH:mm',
    `end_time`    char(5)      DEFAULT NULL COMMENT '结束时间HH:mm',
    `address`     varchar(100) DEFAULT NULL COMMENT '活动地点',
    `cover_url`   varchar(500) DEFAULT NULL COMMENT '活动封面图片',
    `introduce`   longtext COMMENT '活动详细介绍',
    `scenic_id`   bigint(20) COMMENT '活动关联的景区',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='活动信息表';

DROP TABLE IF EXISTS `pay_notify_log`;
CREATE TABLE `pay_notify_log`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `pay_channel` varchar(30) DEFAULT NULL COMMENT '交易方式 WECHAT:微信 ALI_PAY:支付宝',
    `notify_id`   varchar(50) DEFAULT NULL COMMENT '异步通知唯一ID',
    `step_type`   varchar(30) DEFAULT NULL COMMENT '通知类型 PAY: 支付异步通知 REFUND:退款异步通知',
    `trade_no`    varchar(30) DEFAULT NULL COMMENT '交易流水号',
    `refund_no`   varchar(30) DEFAULT NULL COMMENT '退款流水号',
    `params`      text COMMENT '退款通知原始参数',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `notify_id_unique` (`pay_channel`, `notify_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='支付或退款异步通知记录表';

DROP TABLE IF EXISTS `pay_request_log`;
CREATE TABLE `pay_request_log`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `pay_channel`   varchar(30) DEFAULT NULL COMMENT '交易方式 WECHAT:微信 ALI_PAY:支付宝',
    `order_no`      varchar(50) DEFAULT NULL COMMENT '订单编号',
    `step_type`     VARCHAR(20) DEFAULT NULL COMMENT '请求类型 PAY: 支付 REFUND:退款',
    `trade_no`      varchar(30) DEFAULT NULL COMMENT '交易流水号',
    `refund_no`     varchar(30) DEFAULT NULL COMMENT '退款流水号',
    `request_body`  text COMMENT '请求参数',
    `response_body` TEXT comment '响应参数',
    `create_time`   datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `order_idx` (`order_no`, `pay_channel`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='支付或退款请求记录表';

DROP TABLE IF EXISTS `order_visitor`;
CREATE TABLE `order_visitor`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `product_type` varchar(30) DEFAULT NULL COMMENT '商品类型',
    `order_no`     varchar(50) DEFAULT NULL COMMENT '订单编号',
    `member_name`  varchar(20) DEFAULT NULL COMMENT '游客姓名',
    `id_card`      varchar(20) DEFAULT NULL COMMENT '身份证号码',
    `state`        tinyint(1)  DEFAULT '0' COMMENT '状态 0: 初始化(待支付) 1: 已支付,待使用 2:已使用 3:退款中 4:已退款',
    `refund_id`    bigint(20)  DEFAULT NULL COMMENT '关联的退款记录id',
    `verify_id`    bigint(20)  DEFAULT NULL COMMENT '关联的核销记录id',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单游客信息表';

DROP TABLE IF EXISTS `order_visitor_refund`;
CREATE TABLE `order_visitor_refund`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `order_no`    varchar(50) DEFAULT NULL COMMENT '订单编号',
    `visitor_id`  bigint(20)  DEFAULT NULL COMMENT '游客id',
    `refund_id`   bigint(20)  DEFAULT NULL COMMENT '退款记录id',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='游客退款记录关联表';


DROP TABLE IF EXISTS `order_refund_log`;
CREATE TABLE `order_refund_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`      varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `merchant_id`   bigint(20) NOT NULL COMMENT '订单所属商户id',
    `member_id`     bigint(20)   DEFAULT NULL COMMENT '退款人id',
    `refund_no`     varchar(50)  DEFAULT NULL COMMENT '退款流水号',
    `num`           smallint(2)  DEFAULT '1' COMMENT '退款数量',
    `item_order_id` bigint(20)   DEFAULT NULL COMMENT '零售退款订单id',
    `apply_amount`  int(10)      DEFAULT '0' COMMENT '申请退款金额(含运费)',
    `refund_amount` int(10)      DEFAULT '0' COMMENT '实际退款金额(总退款金额)',
    `express_fee`   int(10)      DEFAULT '0' COMMENT '退款运费',
    `reason`        varchar(200) DEFAULT NULL COMMENT '退款原因',
    `apply_type`    tinyint(1)   DEFAULT '1' COMMENT '申请方式 1:仅退款 2:退货退款',
    `state`         tinyint(1)   DEFAULT '0' COMMENT '退款状态 0:退款中 1:退款成功 2:退款失败',
    `audit_state`   tinyint(1)   DEFAULT '0' COMMENT '审核状态 0:待审核 1:审核通过 2:审核拒绝',
    `audit_user_id` bigint(20)   DEFAULT NULL COMMENT '审核人',
    `audit_time`    datetime     DEFAULT NULL COMMENT '退款审核时间',
    `audit_remark`  varchar(255) DEFAULT NULL COMMENT '审批意见或建议',
    `apply_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '退款申请时间',
    `express_code`  varchar(50)  DEFAULT NULL COMMENT '物流公司编号(退货退款)',
    `express_no`    varchar(30)  DEFAULT NULL COMMENT '物流单号(退货退款)',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单退款记录表';

DROP TABLE IF EXISTS `verify_log`;
CREATE TABLE `verify_log`
(
    `id`          bigint(20)   DEFAULT NULL COMMENT '主键',
    `merchant_id` bigint(20)   DEFAULT NULL COMMENT '商户id',
    `order_no`    varchar(50)  DEFAULT NULL COMMENT '订单编号',
    `user_id`     bigint(20)   DEFAULT NULL COMMENT '核销人id',
    `num`         tinyint(2)   DEFAULT '1' COMMENT '核销数量',
    `remark`      varchar(100) DEFAULT NULL COMMENT '核销备注',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '核销时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单核销记录表';

DROP TABLE IF EXISTS `homestay_order`;
CREATE TABLE `homestay_order`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `order_no`      varchar(30)   DEFAULT NULL COMMENT '订单编号',
    `title`         varchar(30)   DEFAULT NULL COMMENT '房型名称(冗余字段)',
    `homestay_id`   bigint(20)    DEFAULT NULL COMMENT '酒店id(冗余字段)',
    `room_id`       bigint(20)    DEFAULT NULL COMMENT '房型id',
    `member_id`     bigint(20)    DEFAULT NULL COMMENT '会员ID(冗余字段)',
    `confirm_state` tinyint(1)    DEFAULT 0 COMMENT '确认状态 0:待确认 1:确认有房 2:确认无房',
    `start_date`    date          DEFAULT NULL COMMENT '入住开始时间(含)',
    `end_date`      date          DEFAULT NULL COMMENT '入住结束时间(含)',
    `room`          tinyint(1)    DEFAULT '1' COMMENT '几室',
    `hall`          tinyint(1)    DEFAULT '0' COMMENT '几厅',
    `kitchen`       tinyint(1)    DEFAULT '0' COMMENT '几厨',
    `washroom`      tinyint(1)    DEFAULT '1' COMMENT '卫生间数',
    `dimension`     smallint(3)   DEFAULT NULL COMMENT '面积',
    `resident`      tinyint(2)    DEFAULT '2' COMMENT '居住人数',
    `bed`           tinyint(1)    DEFAULT '1' COMMENT '床数',
    `room_type`     tinyint(1)    DEFAULT NULL COMMENT '房型类型 1:整租 2:单间 3:合租',
    `cover_url`     varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `introduce`     longtext COMMENT '详细介绍',
    `remark`        varchar(200)  DEFAULT NULL COMMENT '确认备注信息',
    `create_time`   datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       varchar(255)  DEFAULT '0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='民宿订单表';

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`     bigint(20) NOT NULL COMMENT '订单所属商户id',
    `title`           varchar(200) DEFAULT NULL COMMENT '商品名称',
    `cover_url`       varchar(200) DEFAULT NULL COMMENT '商品封面图(第一张)',
    `member_id`       bigint(20)   DEFAULT NULL COMMENT '用户id',
    `limit_id`        bigint(20)   DEFAULT NULL comment '限时购活动id',
    `booking_no`      varchar(30)  DEFAULT NULL COMMENT '拼团单号',
    `booking_id`      bigint(20) comment '拼团活动id(冗余)',
    `booking_state`   tinyint(1)   default 0 comment '拼团状态 0:待成团 1:拼团成功 2:拼团失败',
    `multiple`        bit(1)       default b'0' comment '是否为多订单,普通商品且购物车购买才可能是多订单,即一个订单对应多个商品',
    `delivery_type`   tinyint(1)   DEFAULT '0' COMMENT '交付方式 0:无须发货 1:门店自提 2:快递包邮',
    `pay_type`        varchar(30)  DEFAULT NULL COMMENT '支付方式',
    `order_no`        varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `store_id`        bigint(20)   DEFAULT NULL COMMENT '商品所属门店id',
    `trade_no`        varchar(50)  DEFAULT NULL COMMENT '商户支付流水号',
    `price`           int(10)      DEFAULT NULL COMMENT '单价',
    `num`             smallint(3)  DEFAULT '1' COMMENT '数量',
    `product_type`    varchar(30)  DEFAULT NULL COMMENT '商品类型',
    `refund_type`     tinyint(1)   DEFAULT '2' COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe` varchar(200) DEFAULT NULL COMMENT '退款描述信息',
    `state`           tinyint(2)   DEFAULT '0' COMMENT '订单状态 -1:初始状态 0:待支付 1:支付中 2:支付成功,待使用 3:已核销 4:待发货 5:待收货 6:退款中 7:待评价 8:订单完成 9:已关闭 10:支付异常 11:退款异常',
    `refund_state`    tinyint(1)   DEFAULT '0' COMMENT '退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功',
    `close_type`      tinyint(1)   DEFAULT NULL COMMENT '关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成',
    `discount_amount` int(10)      DEFAULT '0' COMMENT '总优惠金额',
    `express_amount`  int(10)      DEFAULT '0' COMMENT '总快递费',
    `pay_amount`      int(10)      DEFAULT '0' COMMENT '总付款金额=单价*数量+总快递费-总优惠金额',
    `refund_amount`   int(10)      DEFAULT '0' COMMENT '已退款金额',
    `cd_key_amount`   int(10)      DEFAULT '0' COMMENT 'cd_key金额',
    `cd_key`          varchar(20)  DEFAULT NULL COMMENT 'cd_key',
    `coupon_id`       bigint(20)   DEFAULT NULL COMMENT '优惠券id',
    `pay_time`        datetime     DEFAULT NULL COMMENT '订单支付时间',
    `complete_time`   datetime     DEFAULT NULL COMMENT '订单完成时间',
    `evaluate_state`  bit(1)       default b'0' COMMENT '是否已评价',
    `settle_state`    bit(1)       default b'0' COMMENT '是否已结算',
    `nick_name`       varchar(20)  DEFAULT NULL COMMENT '昵称',
    `mobile`          varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `province_id`     bigint(20)   DEFAULT NULL COMMENT '省份id',
    `city_id`         bigint(20)   DEFAULT NULL COMMENT '城市id',
    `county_id`       bigint(20)   DEFAULT NULL COMMENT '县区id',
    `detail_address`  varchar(100) DEFAULT NULL COMMENT '详细地址',
    `close_time`      datetime     DEFAULT NULL COMMENT '订单关闭时间',
    `verify_no`       varchar(50)  DEFAULT NULL COMMENT '核销码',
    `remark`          varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_date`     date         DEFAULT NULL COMMENT '创建日期',
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `merchant_idx` (`merchant_id`),
    KEY `member_idx` (`member_id`, `order_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单表';

DROP TABLE IF EXISTS `voucher_order`;
CREATE TABLE `voucher_order`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `restaurant_id` bigint(20)   DEFAULT NULL COMMENT '餐饮商家id(冗余)',
    `title`         varchar(30)  DEFAULT NULL COMMENT '餐饮券名称(冗余)',
    `voucher_id`    bigint(20)   DEFAULT NULL COMMENT '餐饮券id',
    `member_id`     bigint(20)   DEFAULT NULL COMMENT '会员ID(冗余)',
    `order_no`      varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `cover_url`     varchar(100) DEFAULT NULL COMMENT '封面图片',
    `line_price`    int(10)      DEFAULT NULL COMMENT '划线价',
    `sale_price`    int(10)      DEFAULT NULL COMMENT '销售价',
    `depict`        varchar(200) DEFAULT NULL COMMENT '购买说明',
    `quota`         int(10)      DEFAULT '1' COMMENT '限购数量',
    `use_num`       int(10)      DEFAULT '0' COMMENT '已使用数量',
    `valid_days`    smallint(3)  DEFAULT NULL COMMENT '有效期购买之日起',
    `effect_date`   date         DEFAULT NULL COMMENT '生效时间(包含)',
    `expire_date`   date         DEFAULT NULL COMMENT '失效日期(包含)',
    `effect_time`   varchar(10)  DEFAULT NULL COMMENT '使用开始时间',
    `expire_time`   varchar(10)  DEFAULT NULL COMMENT '使用截止时间',
    `introduce`     longtext COMMENT '详细介绍',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮券订单表';

DROP TABLE IF EXISTS `homestay_order_snapshot`;
CREATE TABLE `homestay_order_snapshot`
(
    `id`               bigint(20) NOT NULL COMMENT '主键',
    `homestay_room_id` bigint(20)  DEFAULT NULL COMMENT '房型id',
    `order_no`         varchar(30) DEFAULT NULL COMMENT '订单编号',
    `sale_price`       int(10)     DEFAULT '0' COMMENT '销售价',
    `line_price`       int(10)     DEFAULT '0' COMMENT '划线价',
    `config_date`      date        DEFAULT NULL COMMENT '日期',
    `create_time`      datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房态快照表';

DROP TABLE IF EXISTS `line_order`;
CREATE TABLE `line_order`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `title`             varchar(50)   default null comment '线路名称',
    `member_id`         bigint(20)    default null comment '订单所属用户',
    `line_id`           bigint(20)    DEFAULT NULL COMMENT '线路id',
    `line_config_id`    bigint(20)    DEFAULT NULL COMMENT '线路配置id',
    `order_no`          varchar(30)   DEFAULT NULL COMMENT '订单编号',
    `visit_date`        date          DEFAULT NULL COMMENT '游玩日期',
    `travel_agency_id`  bigint(20)    DEFAULT NULL COMMENT '所属旅行社id',
    `start_province_id` bigint(20)    DEFAULT NULL COMMENT '出发省份id',
    `start_city_id`     bigint(20)    DEFAULT NULL COMMENT '出发城市id',
    `cover_url`         varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `duration`          tinyint(2)    DEFAULT NULL COMMENT '几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游',
    `advance_day`       tinyint(2)    DEFAULT NULL COMMENT '提前天数',
    `introduce`         longtext COMMENT '商品介绍',
    `line_price`        int(10)       DEFAULT '0' COMMENT '划线价',
    `sale_price`        int(10)       DEFAULT '0' COMMENT '销售价',
    `create_time`       datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路订单表';

DROP TABLE IF EXISTS `line_order_snapshot`;
CREATE TABLE `line_order_snapshot`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `line_id`      bigint(20)  DEFAULT NULL COMMENT '线路商品id',
    `order_no`     varchar(30) DEFAULT NULL COMMENT '订单编号',
    `route_index`  tinyint(2)  DEFAULT '1' COMMENT '行程排序(第几天)',
    `start_point`  varchar(30) DEFAULT NULL COMMENT '出发地点',
    `end_point`    varchar(30) DEFAULT NULL COMMENT '结束地点',
    `traffic_type` tinyint(1)  DEFAULT NULL COMMENT '交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他',
    `repast`       tinyint(2)  DEFAULT '0' COMMENT '包含就餐 1:早餐 2:午餐 4:晚餐',
    `depict`       longtext COMMENT '详细描述信息',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路订单每日行程配置快照表';

DROP TABLE IF EXISTS `item_order`;
CREATE TABLE `item_order`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `item_id`        bigint(20) COMMENT '商品id',
    `title`          varchar(50) comment '商品名称',
    `order_no`       varchar(30) COMMENT '订单编号',
    `member_id`      bigint(20) COMMENT '订单所属用户ID(冗余)',
    `num`            smallint(4) COMMENT '订单数量',
    `refund_num`     smallint(4)   default '0' COMMENT '已退款数量(含退款中)',
    `refund_state`   tinyint(1)    DEFAULT '0' COMMENT '退款状态 0: 初始状态 1:已退款 2:部分退款',
    `delivery_state` tinyint(1)    DEFAULT '0' COMMENT '配送状态 0: 初始 1:待发货 2:待收货 3:已收货',
    `store_id`       bigint(20)    DEFAULT NULL COMMENT '商品所属店铺',
    `depict`         varchar(50)   DEFAULT NULL COMMENT '商品描述信息',
    `cover_url`      varchar(1000) DEFAULT NULL COMMENT '封面图',
    `sku_title`      varchar(20)   DEFAULT NULL COMMENT '规格名称',
    `spec_id`        varchar(50)   DEFAULT NULL COMMENT 'specId',
    `sku_id`         bigint(20) COMMENT 'skuId',
    `sku_cover_url`  varchar(200) COMMENT '封面图',
    `line_price`     int(10)       DEFAULT NULL COMMENT '划线价',
    `sale_price`     int(10)       DEFAULT NULL COMMENT '售卖价',
    `cost_price`     int(10)       DEFAULT '0' COMMENT '成本价',
    `express_fee`    int(10)       DEFAULT '0' COMMENT '快递费',
    `purchase_notes` varchar(200)  DEFAULT NULL COMMENT '购买须知',
    `quota`          smallint(3)   DEFAULT '1' COMMENT '限购数量',
    `delivery_type`  tinyint(1)    DEFAULT NULL COMMENT '交付方式 0:无需发货 1:门店自提 2:快递包邮',
    `introduce`      longtext COMMENT '商品介绍信息',
    `create_time`    datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品订单表';

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `member_id`   bigint(20)  DEFAULT NULL COMMENT '用户id',
    `store_id`    bigint(20)  DEFAULT NULL COMMENT '店铺id',
    `merchant_id` bigint(20)  DEFAULT NULL COMMENT '商户ID',
    `item_id`     bigint(20)  DEFAULT NULL COMMENT '商品id',
    `sku_id`      bigint(20)  DEFAULT NULL COMMENT '商品规格id',
    `sale_price`  int(10)     DEFAULT NULL COMMENT '商品售价(冗余)',
    `quantity`    smallint(3) DEFAULT '1' COMMENT '数量',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `member_id_index` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='购物车表';

DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `store_id`        bigint(20)    DEFAULT NULL COMMENT '所属特产店',
    `merchant_id`     bigint(20)    DEFAULT NULL COMMENT '所属商家',
    `state`           tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `recommend`       bit(1)        DEFAULT b'0' COMMENT '是否为推荐商品 true:是 false:否',
    `multi_spec`      bit(1)        DEFAULT b'0' COMMENT '是否为多规格商品 true:是 false:不是',
    `hot_sell`        bit(1)        DEFAULT b'0' COMMENT '是否为热销商品 true:是 false:不是',
    `booking_id`      bigint(20)    DEFAULT null COMMENT '拼团活动id',
    `limit_id`        bigint(20)    DEFAULT null comment '限时购活动id',
    `sort`            smallint(4)   DEFAULT '999' COMMENT '商品排序 越小越排在前面',
    `title`           varchar(30)   DEFAULT NULL COMMENT '商品名称',
    `tag_id`          varchar(30)   DEFAULT NULL COMMENT '标签id',
    `depict`          varchar(50)   DEFAULT NULL COMMENT '商品描述信息',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图',
    `purchase_notes`  varchar(200)  DEFAULT NULL COMMENT '购买须知',
    `quota`           smallint(3)   DEFAULT '1' COMMENT '限购数量',
    `delivery_type`   tinyint(1)    DEFAULT NULL COMMENT '交付方式 0:无须发货 1:门店自提 2:快递包邮',
    `refund_type`     tinyint(1)    DEFAULT '2' COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe` varchar(200)  DEFAULT NULL COMMENT '退款描述信息',
    `min_price`       int(10)       DEFAULT NULL COMMENT '最低价格',
    `max_price`       int(10)       DEFAULT NULL COMMENT '最高价格',
    `score`           decimal(2, 1) DEFAULT NULL COMMENT '分数',
    `sale_num`        int(10)       DEFAULT '0' COMMENT '销售数量(所有规格销售总量)',
    `total_num`       int(10)       DEFAULT '0' COMMENT '总销售量=实际销售+虚拟销量',
    `introduce`       longtext COMMENT '商品介绍信息',
    `create_date`     date          DEFAULT null COMMENT '创建日期',
    `create_time`     datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `tag_idx` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='零售商品信息';

DROP TABLE IF EXISTS `item_sku`;
CREATE TABLE `item_sku`
(
    `id`                 bigint(20) NOT NULL COMMENT '主键',
    `item_id`            bigint(20)     DEFAULT NULL COMMENT '零售id',
    `primary_spec_value` varchar(30)    DEFAULT NULL COMMENT '一级规格名',
    `second_spec_value`  varchar(30)    DEFAULT NULL COMMENT '二级规格名',
    `spec_id`            varchar(100)   DEFAULT NULL COMMENT '规格id,多个逗号分隔',
    `cost_price`         int(10)        DEFAULT NULL COMMENT '成本价',
    `line_price`         int(10)        DEFAULT NULL COMMENT '划线价',
    `sale_price`         int(10)        DEFAULT NULL COMMENT '销售价格',
    `stock`              smallint(4)    DEFAULT '0' COMMENT '库存',
    `virtual_num`        smallint(4)    DEFAULT '0' COMMENT '虚拟销量',
    `sale_num`           int(11)        DEFAULT '0' COMMENT '销售量',
    `weight`             decimal(10, 2) DEFAULT NULL COMMENT '重量kg',
    `sku_pic`            varchar(255)   DEFAULT NULL COMMENT 'sku图片(优先级最高)',
    `create_time`        datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`            bit(1)         DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品sku表';

DROP TABLE IF EXISTS `item_spec`;
CREATE TABLE `item_spec`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `item_id`     bigint(20)   DEFAULT NULL COMMENT '商品id',
    `spec_name`   varchar(30)  DEFAULT NULL COMMENT '规格名',
    `spec_value`  varchar(30)  DEFAULT NULL COMMENT '规格名称',
    `spec_pic`    varchar(255) DEFAULT NULL COMMENT '规格图片(一级规格必填), 优先级比sku_pic低',
    `sort`        smallint(4)  DEFAULT NULL COMMENT '排序',
    `level`       tinyint(1)   DEFAULT NULL COMMENT '标签级别 一级标签 二级标签',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品规格配置表';

DROP TABLE IF EXISTS `lottery`;
CREATE TABLE `lottery`
(
    `id`            bigint(20)    DEFAULT NULL COMMENT '主键',
    `title`         varchar(30)   DEFAULT NULL COMMENT '活动名称',
    `merchant_id`   bigint(20)    DEFAULT NULL COMMENT '商户id',
    `store_id`      bigint(20)    DEFAULT NULL COMMENT '店铺id',
    `banner_url`    varchar(200)  DEFAULT NULL COMMENT 'banner图',
    `start_time`    datetime      DEFAULT NULL COMMENT '开始时间',
    `end_time`      datetime      DEFAULT NULL COMMENT '结束时间',
    `state`         tinyint(1)    DEFAULT '0' COMMENT '活动状态  0:未开始 1:进行中 2:已结束',
    `lottery_day`   smallint(4)   DEFAULT '1' COMMENT '单日抽奖次数限制',
    `lottery_total` smallint(4)   DEFAULT '1' COMMENT '总抽奖次数限制',
    `win_num`       smallint(4)   DEFAULT '1' COMMENT '中奖次数限制',
    `cover_url`     varchar(255)  DEFAULT NULL COMMENT '抽奖页面封面图',
    `sub_title`     varchar(20)   DEFAULT NULL COMMENT '抽奖标题',
    `rule`          varchar(1000) DEFAULT NULL COMMENT '抽奖规则',
    `create_time`   datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='抽奖活动表';

DROP TABLE IF EXISTS `lottery_prize`;
CREATE TABLE `lottery_prize`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `merchant_id` bigint(20)   DEFAULT NULL COMMENT '商户id',
    `lottery_id`  bigint(20)   DEFAULT NULL COMMENT '抽奖活动id',
    `prize_name`  varchar(20)  DEFAULT NULL COMMENT '奖品名称',
    `prize_type`  tinyint(1)   DEFAULT NULL COMMENT '奖品类型',
    `num`         int(10)      DEFAULT 1 COMMENT '单次中奖发放数量',
    `total_num`   int(10)      DEFAULT NULL COMMENT '奖品总数量',
    `win_num`     int(10)      DEFAULT 0 COMMENT '已抽中数量',
    `cover_url`   varchar(255) DEFAULT NULL COMMENT '奖品图片',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='奖品信息表';

DROP TABLE IF EXISTS `lottery_config`;
CREATE TABLE `lottery_config`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `merchant_id` bigint(20)  DEFAULT NULL COMMENT '商户id',
    `lottery_id`  bigint(20)  DEFAULT NULL COMMENT '抽奖活动id',
    `prize_id`    bigint(20)  DEFAULT NULL COMMENT '奖品id',
    `prize_type`  tinyint(2)  DEFAULT NULL COMMENT '中奖商品类型',
    `location`    tinyint(1)  DEFAULT '8' COMMENT '中奖位置 1-8',
    `start_range` smallint(4) DEFAULT NULL COMMENT '中奖权重开始范围',
    `end_range`   smallint(4) DEFAULT NULL COMMENT '中奖权重截止范围',
    `weight`      smallint(4) DEFAULT NULL COMMENT '中奖权重',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='抽奖位置配置表';

DROP TABLE IF EXISTS `lottery_log`;
CREATE TABLE `lottery_log`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `lottery_id`  bigint(20) DEFAULT NULL COMMENT '抽奖活动id',
    `member_id`   bigint(20) DEFAULT NULL COMMENT '用户id',
    `location`    tinyint(1) DEFAULT NULL COMMENT '抽奖位置',
    `winning`     bit(1)     DEFAULT b'0' COMMENT '是否中奖 0:未中奖 1:中奖',
    `prize_id`    bigint(20) DEFAULT NULL COMMENT '中奖奖品id',
    `create_time` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '抽奖时间',
    `update_time` datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)     DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `lottery_member_idx` (`lottery_id`, `member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='抽奖记录表';

DROP TABLE IF EXISTS `offline_refund_log`;
CREATE TABLE offline_refund_log
(
    id            BIGINT(20) NOT NULL COMMENT '主键',
    order_no      VARCHAR(50) COMMENT '订单编号',
    refund_amount INT(10) COMMENT '退款金额',
    note          VARCHAR(1000) COMMENT '线下退款关联信息(json)',
    certificate   VARCHAR(200) COMMENT '退款凭证(转账记录)',
    remark        VARCHAR(200) COMMENT '备注信息',
    user_id       BIGINT(20) COMMENT '用户id',
    create_time   DATETIME DEFAULT NOW() COMMENT '创建时间',
    update_time   DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    deleted       BIT(1)   DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '线下退款记录表';

DROP TABLE IF EXISTS `travel_agency`;
CREATE TABLE `travel_agency`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(50)    DEFAULT NULL COMMENT '旅行社名称',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户',
    `logo_url`       varchar(100)   DEFAULT NULL COMMENT '店铺logo',
    `state`          tinyint(2)     DEFAULT 0 COMMENT '状态 0:待上架 1:已上架 2:平台下架',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `depict`         varchar(200)   DEFAULT NULL COMMENT '旅行社描述信息',
    `cover_url`      varchar(500)   DEFAULT NULL COMMENT '旅行社图片',
    `introduce`      longtext COMMENT '旅行社详细介绍信息',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='旅行社信息表';

DROP TABLE IF EXISTS `express_template_region`;
CREATE TABLE `express_template_region`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `express_id`      bigint(20)     DEFAULT NULL COMMENT '模板id',
    `first_part`      decimal(10, 2) DEFAULT NULL COMMENT '首件或首重',
    `first_price`     int(10)        DEFAULT NULL COMMENT '首件或首重的价格',
    `next_part`       decimal(10, 2) DEFAULT NULL COMMENT '续重或续件',
    `next_unit_price` int(10)        DEFAULT NULL COMMENT '续重或续件的单价',
    `region_code`     text COMMENT '区域编号(逗号分隔)',
    `region_name`     text COMMENT '区域名称(逗号分隔)',
    `create_time`     datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_time`     datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted`         bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `express_idx` (`express_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='快递模板区域';

DROP TABLE IF EXISTS `express_template`;
CREATE TABLE `express_template`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `merchant_id` bigint(20)  DEFAULT NULL COMMENT '模板所属商户',
    `title`       varchar(30) DEFAULT NULL COMMENT '模板名称',
    `state`       tinyint(1)  DEFAULT '1' COMMENT '状态 0:禁用 1:启用',
    `charge_mode` tinyint(1)  DEFAULT NULL COMMENT '计费方式 1:按件数 2:按重量',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `merchant_idx` (`merchant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='快递模板表';

DROP TABLE IF EXISTS `order_evaluation`;
CREATE TABLE `order_evaluation`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `store_id`        bigint(20)   DEFAULT NULL COMMENT '订单所属店铺id',
    `order_id`        bigint(20)   DEFAULT NULL COMMENT '订单子表id',
    `order_no`        varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `product_type`    tinyint(2)   DEFAULT NULL COMMENT '商品类型',
    `product_id`      bigint(20)   DEFAULT NULL COMMENT '商品',
    `product_title`   varchar(50)  DEFAULT NULL COMMENT '商品名称',
    `sku_title`       varchar(30)  DEFAULT NULL COMMENT '规格名称',
    `product_cover`   varchar(200) DEFAULT NULL COMMENT '商品图片',
    `score`           tinyint(1)   DEFAULT '5' COMMENT '综合评分1-5分',
    `logistics_score` tinyint(1)   DEFAULT '5' COMMENT '物流评审1-5分',
    `store_score`     tinyint(1)   DEFAULT '5' COMMENT '店铺评审1-5分',
    `comment`         varchar(200) DEFAULT NULL COMMENT '评论',
    `comment_pic`     varchar(500) DEFAULT NULL COMMENT '评论图片',
    `state`           tinyint(1)   DEFAULT '0' COMMENT '审核状态 0:待审核 1:审核通过 2:审核失败',
    `system_evaluate` bit(1)       DEFAULT b'0' COMMENT '是否默认评价 0:不是默认 1:是默认评价',
    `member_id`       bigint(20)   DEFAULT NULL COMMENT '用户id',
    `anonymity`       bit(1)       DEFAULT b'0' COMMENT '是否匿名评论 0:非匿名1:匿名',
    `audit_remark`    varchar(100) DEFAULT NULL COMMENT '审核拒绝原因',
    `user_id`         bigint(20)   DEFAULT NULL COMMENT '审核人',
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除1:已删除',
    PRIMARY KEY (`id`),
    KEY `product_idx` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单评价';

DROP TABLE IF EXISTS `merchant_user`;
CREATE TABLE `merchant_user`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `nick_name`   varchar(10)  DEFAULT NULL COMMENT '昵称',
    `mobile`      varchar(11)  DEFAULT NULL COMMENT '手机号',
    `merchant_id` bigint(20)   DEFAULT NULL COMMENT '所属商户id',
    `user_id`     bigint(20)   DEFAULT NULL COMMENT '关联的系统用户ID',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='商户用户表';

DROP TABLE IF EXISTS `item_express`;
CREATE TABLE `item_express`
(
    `id`           bigint(20)  DEFAULT NULL COMMENT '主键',
    `order_no`     varchar(30) DEFAULT NULL COMMENT '订单号',
    `express_code` varchar(20) DEFAULT NULL COMMENT '物流公司code',
    `express_no`   varchar(30) DEFAULT NULL COMMENT '快递单号',
    `content`      text COMMENT '物流信息(json)',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='快递信息表';

DROP TABLE IF EXISTS item_order_express;
CREATE TABLE `item_order_express`
(
    `id`            bigint(20)  DEFAULT NULL COMMENT '主键',
    `item_order_id` varchar(30) DEFAULT NULL COMMENT '零售订单id',
    `order_no`      varchar(30) DEFAULT NULL COMMENT '订单号(冗余)',
    `express_id`    bigint(20) comment '快递信息id',
    `create_time`   datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单与快递关联表';

DROP TABLE IF EXISTS news;
CREATE TABLE `news`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(30)   DEFAULT NULL COMMENT '资讯标题',
    `code`        varchar(20)   DEFAULT NULL COMMENT '资讯编码',
    `depict`      varchar(200)  DEFAULT NULL COMMENT '一句话描述信息',
    `image`       varchar(1000) DEFAULT NULL COMMENT '图集',
    `content`     longtext COMMENT '详细信息',
    `video`       varchar(200)  DEFAULT NULL COMMENT '视频',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `sort`        smallint(6)   DEFAULT '999' COMMENT '排序',
    `like_num`    int(10)       DEFAULT '0' COMMENT '点赞数',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资讯信息表';

DROP TABLE IF EXISTS news_config;
CREATE TABLE `news_config`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(30) DEFAULT NULL COMMENT '分类标题',
    `code`            varchar(20) DEFAULT NULL COMMENT '资讯编码',
    `include_title`   bit(1)      DEFAULT b'1' COMMENT '是否包含标题',
    `include_depict`  bit(1)      DEFAULT b'0' COMMENT '是否包含描述信息',
    `include_image`   bit(1)      DEFAULT b'0' COMMENT '是否包含图集',
    `include_content` bit(1)      DEFAULT b'1' COMMENT '是否包含详细信息',
    `include_video`   bit(1)      DEFAULT b'0' COMMENT '是否包含视频',
    `create_time`     datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资讯配置';

DROP TABLE IF EXISTS express;
create table express
(
    id           bigint(20) not null comment '主键',
    express_code varchar(50) default null comment '快递编码',
    express_name varchar(50) default null comment '快递公司名称',
    create_time  datetime    default now() comment '创建时间',
    update_time  datetime    default now() on update now() comment '更新时间',
    deleted      bit(1)      default false comment '删除状态 0:未删除 1:已删除',
    primary key (id)
) comment '快递公司';

DROP TABLE IF EXISTS member_collect;
create table member_collect
(
    id           bigint(20) not null comment '主键',
    member_id    bigint(20) comment '会员id',
    collect_id   bigint(20) comment '收藏id',
    collect_type tinyint(2) comment '收藏对象类型',
    state        tinyint(1) default 1 comment '0:取消收藏, 1:加入收藏',
    create_date  date       default null comment '创建日期',
    create_time  datetime   default CURRENT_TIMESTAMP comment '创建时间',
    update_time  datetime   default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit(1)     default 0 comment '删除状态 0:未删除 1:已删除',
    primary key (id)
) comment '会员收藏记录表';

DROP TABLE IF EXISTS comment;
CREATE TABLE `comment`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `member_id`   bigint(20)   DEFAULT NULL COMMENT '用户ID',
    `object_id`   bigint(20)   DEFAULT NULL COMMENT '评论对象ID',
    `object_type` tinyint(2)   DEFAULT NULL COMMENT '评论对象类型',
    `like_num`    int(10)      DEFAULT '0' COMMENT '点赞数量',
    `report_num`  int(10)      DEFAULT '0' COMMENT '被举报次数',
    `reply_num`   int(10)      DEFAULT '0' COMMENT '被评论次数',
    `state`       bit(1)       DEFAULT b'1' COMMENT '状态 0:已屏蔽 1:正常',
    `top_state`   tinyint(2)   DEFAULT b'0' COMMENT '置顶状态 0:未置顶 1:置顶',
    `reply_id`    bigint(20)   DEFAULT NULL COMMENT '回复id',
    `pid`         bigint(20)   DEFAULT NULL COMMENT '父评论id',
    `content`     varchar(200) DEFAULT NULL COMMENT '评论信息',
    `grade`       varchar(200) DEFAULT 1 COMMENT '评论级别 1:一级评论 2:二级评论',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论记录表';

DROP TABLE IF EXISTS account;
create table account
(
    id              bigint(20) not null comment '主键',
    merchant_id     bigint(20) comment '商户id',
    amount          int(10)  default 0 comment '可提现金额',
    pay_freeze      int(10)  default 0 comment '支付冻结金额',
    withdraw_freeze int(10)  default 0 comment '提现冻结金额',
    version         int(10)  default 1 comment '版本号',
    create_time     datetime default CURRENT_TIMESTAMP comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit(1)   default 0 comment '删除状态 0:未删除 1:已删除',
    primary key (id)
) comment '商户资金账户信息表';

DROP TABLE IF EXISTS account_log;
create table account_log
(
    id             bigint(20) not null comment '主键',
    merchant_id    bigint(20) comment '商户id',
    account_type   tinyint(2) comment '资金变动类型',
    amount         int(10)  default 0 comment '变动金额',
    direction      tinyint  default 0 comment '1:收入 2:支出',
    surplus_amount int(10)  default 0 comment '变动后的余额(可提现金额+支付冻结金额)',
    trade_no       varchar(30) comment '关联的交易单号',
    remark         varchar(200) comment '备注信息',
    create_time    datetime default CURRENT_TIMESTAMP comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        bit(1)   default 0 comment '删除状态 0:未删除 1:已删除',
    primary key (id)
) comment '商户资金变动明细表';

DROP TABLE IF EXISTS withdraw_log;
CREATE TABLE `withdraw_log`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`   bigint(20)   DEFAULT NULL COMMENT '商户id',
    `state`         tinyint(2)   DEFAULT '0' COMMENT '0:提现中 1:提现成功 2:提现失败',
    `withdraw_way`  tinyint(1)   DEFAULT '0' COMMENT '1:手动提现 2:自动提现',
    `amount`        int(10)      DEFAULT '0' COMMENT '提现金额',
    `fee`           int(10)      DEFAULT '0' COMMENT '提现手续费',
    `refund_no`     varchar(50)  DEFAULT NULL COMMENT '提现流水号',
    `out_refund_no` varchar(50)  DEFAULT NULL COMMENT '第三方流水号',
    `payment_time`  datetime     DEFAULT NULL COMMENT '到账时间',
    `real_name`     varchar(20)  DEFAULT NULL COMMENT '银行卡所属用户姓名',
    `bank_type`     varchar(20)  DEFAULT NULL COMMENT '银行卡类型',
    `bank_num`      varchar(30)  DEFAULT NULL COMMENT '银行卡号',
    `remark`        varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商户提现记录';

DROP TABLE IF EXISTS comment_report;
CREATE TABLE `comment_report`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `member_id`   bigint(20)   DEFAULT NULL COMMENT '举报用户ID',
    `comment_id`  bigint(20)   DEFAULT NULL COMMENT '评价ID',
    `object_id`   bigint(20)   DEFAULT NULL COMMENT '评论对象ID',
    `object_type` bigint(20)   DEFAULT NULL COMMENT '评论对象类型',
    `content`     varchar(200) DEFAULT NULL COMMENT '举报内容',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论举报记录表';

DROP TABLE IF EXISTS member_visit_log;
CREATE TABLE `member_visit_log`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `member_id`   bigint(20)  DEFAULT NULL COMMENT '用户ID',
    `open_id`     varchar(30) DEFAULT NULL COMMENT 'openId',
    `channel`     varchar(20) DEFAULT NULL COMMENT '访问渠道',
    `create_date` date        DEFAULT NULL COMMENT '访问日期',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `visit_type`  tinyint(2)  DEFAULT NULL COMMENT '页面类型',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户浏览记录表';

DROP TABLE IF EXISTS group_booking;
CREATE TABLE `group_booking`
(
    `id`                  bigint(20) NOT NULL COMMENT '主键',
    `title`               varchar(20) DEFAULT NULL COMMENT '活动名称',
    `item_id`             bigint(20)  DEFAULT NULL COMMENT '零售id',
    `merchant_id`         bigint(20)  DEFAULT NULL COMMENT '商户id',
    `start_time`          datetime    DEFAULT NULL COMMENT '开始时间',
    `end_time`            datetime    DEFAULT NULL COMMENT '截止时间',
    `num`                 tinyint(2)  DEFAULT NULL COMMENT '拼团人数',
    `sku_value`           text        DEFAULT NULL COMMENT '拼团优惠配置json',
    `max_discount_amount` int(10)     DEFAULT 0 COMMENT '最大优惠金额',
    `expire_time`         int(10)     DEFAULT '86400' COMMENT '拼团有效期,单位:分钟',
    `create_time`         datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)      DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='拼团活动表';

DROP TABLE IF EXISTS item_group_order;
CREATE TABLE `item_group_order`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `booking_no`  varchar(20) DEFAULT NULL COMMENT '拼团活动编号',
    `order_no`    varchar(20) DEFAULT NULL COMMENT '订单号',
    `item_id`     bigint(20)  DEFAULT NULL COMMENT '零售id',
    `member_id`   bigint(20)  DEFAULT NULL COMMENT '会员id',
    `booking_id`  bigint(20)  DEFAULT NULL COMMENT '拼团活动id',
    `starter`     bit(1)      DEFAULT b'0' COMMENT '是否为拼团发起者',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='拼团订单表';

DROP TABLE IF EXISTS limit_purchase;
CREATE TABLE `limit_purchase`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`  bigint(20)  DEFAULT NULL COMMENT '商户id',
    `title`        varchar(20) DEFAULT NULL COMMENT '活动名称',
    `start_time`   datetime    DEFAULT NULL COMMENT '开始时间',
    `end_time`     datetime    DEFAULT NULL COMMENT '结束时间',
    `advance_hour` tinyint(2)  DEFAULT NULL COMMENT '提前预告小时',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)      DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='限时购活动表';

DROP TABLE IF EXISTS limit_purchase_item;
CREATE TABLE `limit_purchase_item`
(
    `id`                  bigint(20) NOT NULL COMMENT '主键',
    `limit_purchase_id`   bigint(20) DEFAULT NULL COMMENT '限时购活动id',
    `start_time`          datetime   DEFAULT NULL COMMENT '开始时间',
    `end_time`            datetime   DEFAULT NULL COMMENT '结束时间',
    `item_id`             bigint(20) DEFAULT NULL COMMENT '零售id',
    `merchant_id`         bigint(20) DEFAULT NULL COMMENT '商户id',
    `sku_value`           text COMMENT '优惠配置json',
    `max_discount_amount` int(10)    DEFAULT '0' COMMENT '最大优惠金额',
    `advance_time`        datetime   DEFAULT NULL COMMENT '开始预告时间',
    `create_time`         datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '开始预告时间',
    `update_time`         datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)     DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='限时购商品表';

DROP TABLE IF EXISTS venue;
CREATE TABLE `venue`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(30)    DEFAULT NULL COMMENT '场馆名称',
    `venue_type`     tinyint(2)     DEFAULT NULL COMMENT '场馆类型',
    `cover_url`      varchar(200)   DEFAULT NULL COMMENT '场馆封面图',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:强制下架',
    `open_time`      varchar(20)    DEFAULT NULL COMMENT '营业时间',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `telephone`      varchar(20)    DEFAULT NULL COMMENT '商家电话',
    `introduce`      longtext COMMENT '场馆详细信息',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='场馆信息';

DROP TABLE IF EXISTS venue_site;
CREATE TABLE `venue_site`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(30)  DEFAULT NULL COMMENT '场地名称',
    `cover_url`   varchar(200) DEFAULT NULL COMMENT '场地图片',
    `venue_id`    bigint(20)   DEFAULT NULL COMMENT '所属场馆',
    `sort`        smallint(6)  DEFAULT '999' COMMENT '排序',
    `merchant_id` bigint(20)   DEFAULT NULL COMMENT '所属商户',
    `state`       tinyint(1)   DEFAULT '0' COMMENT '状态 0:待上架 1:已上架 2:强制下架',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='场地信息表';

DROP TABLE IF EXISTS venue_site_price;
CREATE TABLE `venue_site_price`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `venue_id`      bigint(20) DEFAULT NULL COMMENT '所属场馆id',
    `venue_site_id` bigint(20) DEFAULT NULL COMMENT '场地id',
    `merchant_id`   bigint(20) DEFAULT NULL COMMENT '所属商户id',
    `start_time`    time       DEFAULT NULL COMMENT '开始时间',
    `end_time`      time       DEFAULT NULL COMMENT '结束时间',
    `now_date`      date       DEFAULT NULL COMMENT '当前日期',
    `now_week`      tinyint(1) DEFAULT NULL COMMENT '星期几 1-7',
    `stock`         tinyint(1) DEFAULT '1' COMMENT '库存(默认1)',
    `price`         int(10)    DEFAULT '0' COMMENT '价格',
    `state`         bit(1)     DEFAULT b'1' COMMENT '是否可预定 0:不可预定 1:可预定',
    `create_time`   datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='场馆场次价格信息表';

DROP TABLE IF EXISTS venue_order;
CREATE TABLE `venue_order`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `order_no`       varchar(30)    default null comment '订单号',
    `venue_id`       bigint(20)     DEFAULT NULL COMMENT '场馆id',
    `venue_site_id`  bigint(20)     DEFAULT NULL COMMENT '场地id',
    `member_id`      bigint(20)     DEFAULT NULL COMMENT '下单人',
    `title`          varchar(30)    DEFAULT NULL COMMENT '场馆名称',
    `site_title`     varchar(30)    DEFAULT NULL COMMENT '场地名称',
    `venue_type`     tinyint(2)     DEFAULT NULL COMMENT '场馆类型',
    `cover_url`      varchar(200)   DEFAULT NULL COMMENT '场馆封面图',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户',
    `open_time`      varchar(20)    DEFAULT NULL COMMENT '营业时间',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `telephone`      varchar(20)    DEFAULT NULL COMMENT '商家电话',
    `visit_date`     date comment '预约日期',
    `time_phase`     text comment '预约的时间段及价格(json)',
    `introduce`      longtext COMMENT '场馆详细信息',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='场馆预约订单表';

DROP TABLE IF EXISTS redeem_code;
CREATE TABLE `redeem_code`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(20) DEFAULT NULL COMMENT 'cd名称',
    `start_time`  datetime    DEFAULT NULL COMMENT '有效开始时间',
    `end_time`    datetime    DEFAULT NULL COMMENT '有效截止时间',
    `amount`      int(10)     default NULL COMMENT '兑换码金额',
    `num`         smallint(4) comment '发放数量',
    `state`       tinyint(1) comment '状态 0:待发放 1:已发放',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT ='兑换码配置表';

DROP TABLE IF EXISTS redeem_code_grant;
create table redeem_code_grant
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `redeem_code_id` bigint(20) comment '发放表id',
    `title`          varchar(20) DEFAULT NULL COMMENT 'cd名称',
    `cd_key`         varchar(20) comment 'cd_key',
    `use_time`       datetime comment '使用时间',
    `member_id`      bigint(20) comment '用户id',
    `state`          tinyint(1) comment '使用状态 0:待使用 1:已使用 2:已过期',
    `amount`         int(10)     default NULL COMMENT '兑换码金额',
    `start_time`     datetime    DEFAULT NULL COMMENT '有效开始时间',
    `end_time`       datetime    DEFAULT NULL COMMENT '有效截止时间',
    `create_time`    datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)      DEFAULT b'0' COMMENT '是否删除 1:已删除 0:未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT ='兑换码发放表';

DROP TABLE IF EXISTS score_account;
CREATE TABLE `score_account`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`     bigint(20) DEFAULT NULL COMMENT '商户id',
    `amount`          int(10)    DEFAULT '0' COMMENT '可用积分',
    `pay_freeze`      int(10)    DEFAULT '0' COMMENT '支付冻结金额',
    `withdraw_freeze` int(10)    DEFAULT '0' COMMENT '提现冻结金额',
    `version`         int(10)    DEFAULT '1' COMMENT '版本号',
    `create_time`     datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)     DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT ='商户积分账户表';


DROP TABLE IF EXISTS score_account_log;
CREATE TABLE `score_account_log`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`    bigint(20)   DEFAULT NULL COMMENT '商户id',
    `charge_type`    tinyint(2)   DEFAULT NULL COMMENT '积分变动类型',
    `amount`         int(10)      DEFAULT '0' COMMENT '变动积分',
    `direction`      tinyint(4)   DEFAULT '0' COMMENT '1:收入 2:支出',
    `surplus_amount` int(10)      DEFAULT '0' COMMENT '变动后的积分',
    `trade_no`       varchar(30)  DEFAULT NULL COMMENT '关联的交易单号(订单号或者提现单号)',
    `remark`         varchar(200) DEFAULT NULL COMMENT '备注信息',
    `create_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT ='商户积分变动明细表';


DROP TABLE IF EXISTS redeem_code_scope;
CREATE TABLE redeem_code_scope
(
    id             BIGINT(20) NOT NULL COMMENT '主键',
    redeem_code_id BIGINT(20) COMMENT '配置id',
    store_id       BIGINT(20) COMMENT '店铺id',
    product_type   VARCHAR(20) COMMENT '店铺类型',
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) COMMENT '兑换码使用范围表';

DROP TABLE IF EXISTS scan_recharge_log;
CREATE TABLE `scan_recharge_log`
(
    `id`          BIGINT(20) NOT NULL COMMENT '主键',
    `merchant_id` BIGINT(20)  DEFAULT NULL COMMENT '商户id',
    `amount`      INT(10)     DEFAULT '0' COMMENT '付款金额',
    `state`       TINYINT(1) COMMENT '付款状态 0:待支付 1:已支付 2:已过期',
    `qr_code`     VARCHAR(500) COMMENT '二维码url',
    `trade_type`  VARCHAR(20) COMMENT '付款方式',
    `trade_no`    VARCHAR(30) DEFAULT NULL COMMENT '本地交易单号',
    `pay_time`    DATETIME    DEFAULT NULL COMMENT '支付时间',
    `create_time` DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     BIT(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT ='扫码支付记录表';

DROP TABLE IF EXISTS account_freeze_log;
CREATE TABLE `account_freeze_log`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `merchant_id`   bigint(20)   DEFAULT NULL COMMENT '商户id',
    `state`         tinyint(1)   default '1' comment '状态 1:冻结中 2:已解冻',
    `amount`        int(10)      DEFAULT '0' COMMENT '冻结金额',
    `change_type`   tinyint(1)   default '1' comment '状态变更原因 1:支付冻结, 2:退款解冻 3:订单完成解冻',
    `order_no`      varchar(30)  DEFAULT NULL comment '订单编号',
    `remark`        varchar(200) DEFAULT NULL COMMENT '备注信息',
    `unfreeze_time` datetime     DEFAULT NULL COMMENT '解冻时间',
    `create_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商户资金冻结记录表';