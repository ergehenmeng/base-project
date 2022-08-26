CREATE TABLE `homestay`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(50)    DEFAULT NULL COMMENT '民宿名称',
    `level`          tinyint(1)     DEFAULT NULL COMMENT '星级 5:五星级 4:四星级 3:三星级 0: 其他',
    `state`          tinyint(1)     DEFAULT NULL COMMENT '状态 0:待上架  1:已上架',
    `audit_state`    tinyint(1)     DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区',
    `detail_address` varchar(100)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `describe`       varchar(100)   DEFAULT NULL COMMENT '描述信息',
    `cover_url`      varchar(100)   DEFAULT NULL COMMENT '封面图片',
    `introduce`      longtext COMMENT '详细介绍',
    `phone`          varchar(20)    DEFAULT NULL COMMENT '联系电话',
    `infrastructure` varchar(200)   DEFAULT NULL COMMENT '基础设施',
    `bathroom`       varchar(200)   DEFAULT NULL COMMENT '卫浴设施',
    `kitchen`        varchar(200)   DEFAULT NULL COMMENT '厨房设施',
    `key_service`    varchar(200)   DEFAULT NULL COMMENT '特色服务',
    `add_time`       datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    `tags`           varchar(200)   DEFAULT NULL COMMENT '标签',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '民宿所属商家',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='民宿信息表';

CREATE TABLE `homestay_room`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(50)   DEFAULT NULL COMMENT '房型名称',
    `homestay_id`     bigint(20)    DEFAULT NULL COMMENT '民宿id',
    `state`           tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:已上架',
    `audit_state`     tinyint(1)    DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `room`            tinyint(1)    DEFAULT '1' COMMENT '几室',
    `hall`            tinyint(1)    DEFAULT '0' COMMENT '几厅',
    `kitchen`         tinyint(1)    DEFAULT '0' COMMENT '几厨',
    `washroom`        tinyint(1)    DEFAULT '1' COMMENT '卫生间数',
    `dimension`       smallint(3)   DEFAULT NULL COMMENT '面积',
    `resident`        tinyint(2)    DEFAULT '2' COMMENT '居住人数',
    `bed`             tinyint(1)    DEFAULT '1' COMMENT '床数',
    `room_type`       tinyint(1)    DEFAULT NULL COMMENT '房型类型 1:整租 2:单间 3:合租',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `introduce`       longtext COMMENT '详细介绍',
    `support_refund`  bit(1)        DEFAULT b'0' COMMENT '是否支持退款 0:不支持 1:支持',
    `refund_describe` varchar(200)  DEFAULT NULL COMMENT '退款描述',
    `add_time`        datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房型信息表';


CREATE TABLE `homestay_room_config`
(
    `id`               bigint(20) NOT NULL COMMENT '主键',
    `state`            tinyint(1)  DEFAULT '1' COMMENT '状态 0:可用 1:不可用',
    `homestay_room_id` bigint(20)  DEFAULT NULL COMMENT '房型id',
    `config_date`      date        DEFAULT NULL COMMENT '日期',
    `line_price`       int(10)     DEFAULT NULL COMMENT '划线机',
    `sale_price`       int(10)     DEFAULT NULL COMMENT '销售价',
    `stock`            smallint(4) DEFAULT NULL COMMENT '剩余库存',
    `sale_num`         smallint(4) DEFAULT '0' COMMENT '已预订数量',
    `add_time`         datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`      datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房间价格配置表';

CREATE TABLE `line`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `travel_agency_id`  bigint(20)  DEFAULT NULL COMMENT '所属旅行社id',
    `title`             varchar(50)   DEFAULT NULL COMMENT '线路名称',
    `state`             tinyint(1)    DEFAULT '0' COMMENT '状态 0:待上架 1:待审核 2:已上架',
    `start_province_id` bigint(20)    DEFAULT NULL COMMENT '出发省份id',
    `start_city_id`     bigint(20)    DEFAULT NULL COMMENT '出发城市id',
    `cover_url`         varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `sale_num`          int(10)       DEFAULT '0' COMMENT '销售量',
    `total_num`         int(10)       DEFAULT '0' COMMENT '总销量=实际销售+虚拟销量',
    `duration`          tinyint(2)    DEFAULT NULL COMMENT '几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游',
    `advance_day`       tinyint(2)    DEFAULT NULL COMMENT '提前天数',
    `support_refund`    tinyint(1)    DEFAULT NULL COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe`   varchar(200)  DEFAULT NULL COMMENT '退款描述',
    `introduce`         longtext COMMENT '商品介绍',
    `add_time`          datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `create_time`       datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路商品信息表';

CREATE TABLE `line_config`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `line_id`     bigint(20)  DEFAULT NULL COMMENT '线路商品id',
    `config_date` date        DEFAULT NULL COMMENT '配置日期',
    `stock`       smallint(4) DEFAULT '0' COMMENT '总库存',
    `sale_price`  int(10)     DEFAULT '0' COMMENT '销售价格',
    `sale_num`    int(10)     DEFAULT '0' COMMENT '销售数量',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路商品配置表';

CREATE TABLE `line_day_config`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `line_id`      bigint(20)  DEFAULT NULL COMMENT '线路商品id',
    `route_index`  tinyint(2)  DEFAULT '1' COMMENT '行程排序(第几天)',
    `start_point`  varchar(30) DEFAULT NULL COMMENT '出发地点',
    `end_point`    varchar(30) DEFAULT NULL COMMENT '结束地点',
    `traffic_type` tinyint(1)  DEFAULT NULL COMMENT '交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他',
    `repast`       tinyint(2)  DEFAULT '0' COMMENT '包含就餐 1:早餐 2:午餐 4:晚餐',
    `describe`     longtext COMMENT '详细描述信息',
    `add_time`     datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='线路日配置信息';



CREATE TABLE `restaurant`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(50)    DEFAULT NULL COMMENT '商家名称',
    `merchant_id` bigint(20)     DEFAULT NULL COMMENT '所属商户',
    `state`       tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架',
    `audit_state` tinyint(1)     DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `cover_url`   varchar(1000)  DEFAULT NULL COMMENT '商家封面',
    `open_time`   varchar(100)   DEFAULT NULL COMMENT '营业时间',
    `province_id` bigint(20)     DEFAULT NULL COMMENT '省份',
    `city_id`     bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`   bigint(20)     DEFAULT NULL COMMENT '县区id',
    `longitude`   decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`    decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `phone`       varchar(20)    DEFAULT NULL COMMENT '商家热线',
    `introduce`   longtext COMMENT '商家介绍',
    `add_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮商家信息表';

CREATE TABLE `restaurant_voucher`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `restaurant_id`   bigint(20)   DEFAULT NULL COMMENT '餐饮商家id',
    `title`           varchar(50)  DEFAULT NULL COMMENT '商品名称',
    `state`           tinyint(1)   DEFAULT '0' COMMENT '状态 0:待上架 1:已上架',
    `audit_state`     tinyint(1)   DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `cover_url`       varchar(100) DEFAULT NULL COMMENT '封面图片',
    `line_price`      int(10)      DEFAULT NULL COMMENT '划线价',
    `sale_price`      int(10)      DEFAULT NULL COMMENT '销售价',
    `stock`           int(10)      DEFAULT NULL COMMENT '剩余库存',
    `sale_num`        int(10)      DEFAULT '0' COMMENT '销售数量',
    `total_num`       int(10)      DEFAULT '0' COMMENT '总销量=虚拟销量+真实销量',
    `describe`        varchar(200) DEFAULT NULL COMMENT '购买说明',
    `quota`           smallint(10) DEFAULT '1' COMMENT '单次限购数量',
    `valid_days`      smallint(3)  DEFAULT NULL COMMENT '有效期购买之日起(与开始日期和截止日期互斥)',
    `effect_date`     date         DEFAULT NULL COMMENT '使用开始日期(包含) yyyy-MM-dd',
    `expire_date`     date         DEFAULT NULL COMMENT '使用截止日期(包含) yyyy-MM-dd',
    `effect_time`     varchar(10)  DEFAULT NULL COMMENT '使用开始时间 HH:mm',
    `expire_time`     varchar(10)  DEFAULT NULL COMMENT '使用截止时间 HH:mm',
    `support_refund`  bit(1)       DEFAULT b'1' COMMENT '是否支持退款 0:不支持 1:支持',
    `refund_describe` varchar(200) DEFAULT NULL COMMENT '退款描述信息',
    `introduce`       longtext COMMENT '详细介绍',
    `add_time`        datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮代金券';

CREATE TABLE `scenic`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',

    `scenic_name`    varchar(50)    DEFAULT NULL COMMENT '景区名称',
    `level`          tinyint(1)     DEFAULT NULL COMMENT '景区等级 5: 5A 4: 4A 3:3A 0:其他',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '景区营业时间',
    `tag`            varchar(300)   DEFAULT NULL COMMENT '景区标签,逗号分隔',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '景区所属商家(为空则表示系统自营)',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:已上架',
    `audit_state`    tinyint(1)     DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `sort`           smallint(3)    DEFAULT '999' COMMENT '景区排序(小<->大)',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省份id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(100)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `describe`       varchar(100)   DEFAULT NULL COMMENT '景区描述信息',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '景区图片',
    `introduce`      longtext COMMENT '景区详细介绍信息',
    `add_time`       datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    `min_price`      int(10)        DEFAULT '0' COMMENT '景区最低票价',
    `max_price`      int(10)        DEFAULT '0' COMMENT '景区最高票价',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='景区信息表';

CREATE TABLE `scenic_ticket`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `scenic_id`         bigint(20)          DEFAULT NULL COMMENT '门票所属景区',
    `title`             varchar(50)         DEFAULT NULL COMMENT '门票名称',
    `state`             tinyint(1)          DEFAULT '0' COMMENT '景区状态 0:待上架 1:已上架',
    `audit_state`       tinyint(1)          DEFAULT NULL COMMENT '审核状态 0:初始  1:未上架 2:已上架',
    `category`          tinyint(2)          DEFAULT NULL COMMENT '门票种类 1: 成人票 2: 老人票 3:儿童票',
    `cover_url`         varchar(500)        DEFAULT NULL COMMENT '门票封面图',
    `line_price`        int(10)             DEFAULT '0' COMMENT '划线价',
    `sale_price`        int(10)             DEFAULT '0' COMMENT '销售价',
    `cost_price`        int(10)             DEFAULT '0' COMMENT '成本价',
    `advance_day`       tinyint(2) unsigned DEFAULT NULL COMMENT '提前多少天购票',
    `quota`             tinyint(2)          DEFAULT '1' COMMENT '单次最大购买数量',
    `start_date`        date                DEFAULT NULL COMMENT '开始预订时间',
    `end_date`          date                DEFAULT NULL COMMENT '截止预订时间',
    `stock`             int(10)             DEFAULT '0' COMMENT '剩余库存',
    `sale_num`          int(10)             DEFAULT '0' COMMENT '真实销售数量',
    `total_num`         int(10)             DEFAULT '0' COMMENT '总销量=实际销量+虚拟销量',
    `introduce`         longtext COMMENT '景区介绍',
    `valid_days`        smallint(3)         DEFAULT NULL COMMENT '有效期购买之日起',
    `effective_date`    date                DEFAULT NULL COMMENT '生效日期(包含)',
    `expire_date`       date                DEFAULT NULL COMMENT '失效日期(包含)',
    `use_scope`         smallint(3)         DEFAULT NULL COMMENT '使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日',
    `verification_type` tinyint(2)          DEFAULT NULL COMMENT '核销方式 1:手动核销 2:自动核销 (凌晨自动核销)',
    `support_refund`    bit(1)              DEFAULT b'1' COMMENT '是否支持退款 1:支持 0:不支持',
    `refund_describe`   varchar(200)        DEFAULT NULL COMMENT '退款描述',
    `real_buy`          bit(1)              DEFAULT b'0' COMMENT '是否实名购票 0:不实名 1:实名',
    `close_time`        datetime            DEFAULT NULL COMMENT '订单关闭时间',
    `pay_time`          datetime            DEFAULT NULL COMMENT '订单支付时间',
    `complete_time`     datetime            DEFAULT NULL COMMENT '订单完成时间',
    `use_time`          datetime            DEFAULT NULL COMMENT '门票使用时间',
    `add_time`          datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间(订单创建时间)',
    `update_time`       datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)              DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='景区门票信息表';

CREATE TABLE `product`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `store_id`        bigint(20)    DEFAULT NULL COMMENT '所属特产店',
    `state`           tinyint(1)    DEFAULT '0' COMMENT '商品状态 0:待上架 1:待审核 2:已上架',
    `audit_state`     tinyint(1)    DEFAULT NULL COMMENT '平台状态 0:初始 1:待审核 2:已上架',
    `title`           varchar(30)   DEFAULT NULL COMMENT '商品名称',
    `describe`        varchar(50)   DEFAULT NULL COMMENT '商品描述信息',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图',
    `purchase_notes`  varchar(200)  DEFAULT NULL COMMENT '购买须知',
    `quota`           smallint(3)   DEFAULT '1' COMMENT '限购数量',
    `delivery_method` tinyint(1)    DEFAULT NULL COMMENT '交付方式 1:门店自提 2:快递包邮',
    `support_refund`  bit(1)        DEFAULT b'0' COMMENT '是否支持退款 true:支持 false:不支持',
    `min_price`       int(10)       DEFAULT NULL COMMENT '最低价格',
    `max_price`       int(10)       DEFAULT NULL COMMENT '最高价格',
    `sale_num`        int(10)       DEFAULT '0' COMMENT '销售数量(所有规格销售总量)',
    `total_num`       int(10)       DEFAULT '0' COMMENT '总销售量=实际销售+虚拟销量',
    `introduce`       longtext COMMENT '商品介绍信息',
    `add_time`        datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`     datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)        DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品信息';


CREATE TABLE `product_sku`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `product_id`  bigint(20)  DEFAULT NULL COMMENT '商品id',
    `title`       varchar(20) DEFAULT NULL COMMENT '规格名称',
    `line_price`  int(10)     DEFAULT NULL COMMENT '划线价',
    `cost_price`  int(10)     DEFAULT '0' COMMENT '成本价',
    `sale_price`  int(10)     DEFAULT '0' COMMENT '销售价',
    `stock`       smallint(4) DEFAULT NULL COMMENT '库存',
    `sale_num`    int(10)     DEFAULT '0' COMMENT '销售量',
    `cover_url`   varchar(200) COMMENT '封面图',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品规格表';

CREATE TABLE `product_shop`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `state`          tinyint(1)     DEFAULT '0' COMMENT '状态 0:待上架 1:待审核 2:已上架',
    `title`          varchar(50)    DEFAULT NULL COMMENT '店铺名称',
    `merchant_id`    bigint(20)     DEFAULT NULL COMMENT '所属商户id',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '封面图',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '营业时间',
    `province_id`    bigint(20)     DEFAULT NULL COMMENT '省id',
    `city_id`        bigint(20)     DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20)     DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `telephone`      varchar(20)    DEFAULT NULL COMMENT '商家电话',
    `introduce`      longtext COMMENT '商家介绍',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='店铺信息表';

CREATE TABLE `merchant`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(30)  DEFAULT NULL COMMENT '商家名称',
    `type`        smallint(4)  DEFAULT NULL COMMENT '商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路',
    `nick_name`   varchar(20)  DEFAULT NULL COMMENT '联系人姓名',
    `mobile`      varchar(20)  DEFAULT NULL COMMENT '联系人电话',
    `user_name`   varchar(30)  DEFAULT NULL COMMENT '账号名称',
    `pwd`         varchar(255) DEFAULT NULL COMMENT '账号密码',
    `init_pwd`    bit(1)       DEFAULT b'1' COMMENT '是否为初始化密码 1:是 0:不是',
    `state`       tinyint(1)   DEFAULT '1' COMMENT '状态 0:锁定 1:正常 2:销户',
    `add_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '是否为删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商家信息表';

CREATE TABLE `merchant_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户ID',
    `role_id`     bigint(20) DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商户与角色关联表';

CREATE TABLE `coupon_config`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(30)  DEFAULT NULL COMMENT '优惠券名称',
    `state`           tinyint(1)   DEFAULT '0' COMMENT '状态 0:未启用 1:启用',
    `stock`           smallint(4)  DEFAULT '1' COMMENT '库存',
    `receive_num`     smallint(6)  DEFAULT '0' COMMENT '已领取数量',
    `use_num`         smallint(6)  DEFAULT '0' COMMENT '已使用数量',
    `max_limit`       smallint(2)  DEFAULT '1' COMMENT '单人领取限制',
    `mode`            tinyint(1)   DEFAULT '1' COMMENT '领取方式 1:页面领取 2: 手动发放',
    `coupon_type`     tinyint(1)   DEFAULT '1' COMMENT '优惠券类型 1: 抵扣券 2: 折扣券',
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


CREATE TABLE `ticket_order`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `scenic_id`         bigint(20)   DEFAULT NULL COMMENT '门票所属景区(冗余字段)',
    `order_no`          varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `effective_date`    datetime     DEFAULT NULL COMMENT '生效日期(包含)',
    `expire_date`       datetime     DEFAULT NULL COMMENT '失效日期(包含)',
    `use_scope`         smallint(3)  DEFAULT NULL COMMENT '使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日',
    `verification_type` tinyint(2)   DEFAULT NULL COMMENT '核销方式 1:手动核销 2:自动核销 (凌晨自动核销)',
    `real_buy`          bit(1)       DEFAULT b'0' COMMENT '是否实名购票 0:不实名 1:实名',
    `introduce`         longtext COMMENT '门票介绍',
    `ticket_id`         bigint(20)   DEFAULT NULL COMMENT '门票id',
    `mobile`            varchar(11)  DEFAULT NULL COMMENT '联系人手机号',
    `use_time`          datetime     DEFAULT NULL COMMENT '使用时间',
    `add_time`          datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           varchar(255) DEFAULT '0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='门票订单表';

CREATE TABLE `coupon_product`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `coupon_config_id` bigint(20)  DEFAULT NULL COMMENT '优惠券配置id',
    `product_type`     varchar(20) DEFAULT NULL COMMENT '商品类型',
    `product_id`       bigint(20)  DEFAULT NULL COMMENT '商品id',
    `add_time`         datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券商品关联表';

CREATE TABLE `user_coupon`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `coupon_config_id` bigint(20) DEFAULT NULL COMMENT '优惠券配置id',
    `user_id`          bigint(20) DEFAULT NULL COMMENT '用户id',
    `state`            tinyint(1) DEFAULT '0' COMMENT '使用状态 0:未使用 1:已使用 2:已过期',
    `receive_time`     datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    `use_time`         datetime   DEFAULT NULL COMMENT '使用时间',
    `add_time`         datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`      datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)     DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户优惠券表';

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
    `add_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='活动信息表';

CREATE TABLE `pay_notify_log`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `trade_type`    varchar(30) DEFAULT NULL COMMENT '交易方式 WECHAT:微信 ALIPAY:支付宝',
    `notify_id`     varchar(50) DEFAULT NULL COMMENT '异步通知唯一ID',
    `notify_type`   varchar(30) DEFAULT NULL COMMENT '通知类型 PAY: 支付异步通知 REFUND:退款异步通知',
    `out_trade_no`  varchar(30) DEFAULT NULL COMMENT '交易流水号',
    `out_refund_no` varchar(30) DEFAULT NULL COMMENT '退款流水号',
    `params`        text COMMENT '退款通知原始参数',
    `add_time`      datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`   datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `notify_id_unique` (`trade_type`, `notify_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='支付异步通知记录表';

CREATE TABLE `order_visitor`
(
    `id`           bigint(20) NOT NULL COMMENT '主键',
    `product_type` varchar(30) DEFAULT NULL COMMENT '商品类型',
    `order_id`     bigint(20)  DEFAULT NULL COMMENT '订单id',
    `user_name`    varchar(20) DEFAULT NULL COMMENT '游客姓名',
    `id_card`      varchar(20) DEFAULT NULL COMMENT '身份证号码',
    `state`        tinyint(1)  DEFAULT '0' COMMENT '状态 0: 待使用 1:已使用 2:已退款',
    `collect_id`   bigint(20)  DEFAULT NULL COMMENT '关联id(退款记录id或核销记录id)',
    `add_time`     datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单游客信息表';

CREATE TABLE `order_refund_log`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`          varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `user_id`           bigint(20)   DEFAULT NULL COMMENT '退款人id',
    `out_refund_no`     varchar(50)  DEFAULT NULL COMMENT '退款流水号',
    `num`               smallint(2)  DEFAULT '1' COMMENT '退款数量',
    `amount`            int(10)      DEFAULT '0' COMMENT '申请退款金额',
    `reason`            varchar(200) DEFAULT NULL COMMENT '退款原因',
    `apply_type`        tinyint(1)   DEFAULT '1' COMMENT '申请方式 1:仅退款 2:退货退款',
    `state`             tinyint(1)   DEFAULT '-1' COMMENT '退款状态 -1:退款申请中 0:退款中 1:退款成功 2:退款失败',
    `audit_state`       tinyint(1)   DEFAULT '0' COMMENT '审核状态 0:待审核 1:审核通过 2:审核拒绝',
    `audit_time`        datetime     DEFAULT NULL COMMENT '退款审核时间',
    `audit_remark`      varchar(255) DEFAULT NULL COMMENT '审批意见或建议',
    `apply_time`        datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '退款申请时间',
    `logistics_company` varchar(50)  DEFAULT NULL COMMENT '物流公司(退货退款)',
    `logistics_no`      varchar(30)  DEFAULT NULL COMMENT '物流单号(退货退款)',
    `create_time`       datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单退款记录表';

CREATE TABLE `verify_log`
(
    `id`       bigint(20)   DEFAULT NULL COMMENT '主键',
    `order_id` bigint(20)   DEFAULT NULL COMMENT '订单id',
    `user_id`  bigint(20)   DEFAULT NULL COMMENT '核销人id',
    `num`      tinyint(2)   DEFAULT '1' COMMENT '核销数量',
    `remark`   varchar(100) DEFAULT NULL COMMENT '核销备注',
    `add_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '核销时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单核销记录表';

CREATE TABLE `homestay_order`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `order_no`    varchar(30)   DEFAULT NULL COMMENT '订单编号',
    `homestay_id` bigint(20)    DEFAULT NULL COMMENT '酒店id(冗余字段)',
    `room_id`     bigint(20)    DEFAULT NULL COMMENT '房型id',
    `start_date`  date          DEFAULT NULL COMMENT '入住开始时间(含)',
    `end_date`    date          DEFAULT NULL COMMENT '入住结束时间(含)',
    `room`        tinyint(1)    DEFAULT '1' COMMENT '几室',
    `hall`        tinyint(1)    DEFAULT '0' COMMENT '几厅',
    `kitchen`     tinyint(1)    DEFAULT '0' COMMENT '几厨',
    `washroom`    tinyint(1)    DEFAULT '1' COMMENT '卫生间数',
    `dimension`   smallint(3)   DEFAULT NULL COMMENT '面积',
    `resident`    tinyint(2)    DEFAULT '2' COMMENT '居住人数',
    `bed`         tinyint(1)    DEFAULT '1' COMMENT '床数',
    `room_type`   tinyint(1)    DEFAULT NULL COMMENT '房型类型 1:整租 2:单间 3:合租',
    `cover_url`   varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `introduce`   longtext COMMENT '详细介绍',
    `mobile`      varchar(11)   DEFAULT NULL COMMENT '联系人手机号',
    `add_time`    datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     varchar(255)  DEFAULT '0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='民宿订单表';

CREATE TABLE `order`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `title`           varchar(50)  DEFAULT NULL COMMENT '商品名称',
    `user_id`         bigint(20)   DEFAULT NULL COMMENT '用户id',
    `pay_type`        varchar(30)  DEFAULT NULL COMMENT '支付方式',
    `order_no`        varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `out_trade_no`    varchar(30)  DEFAULT NULL COMMENT '支付流水号',
    `price`           int(10)      DEFAULT NULL COMMENT '单价',
    `num`             smallint(3)  DEFAULT '1' COMMENT '数量',
    `product_type`    varchar(30)  DEFAULT NULL COMMENT '商品类型',
    `support_refund`  bit(1)       DEFAULT b'0' COMMENT '是否支持退款 0:不支持 1:直接退款 2:审核后退款',
    `refund_describe` varchar(200) DEFAULT NULL COMMENT '退款描述信息',
    `state`           tinyint(2)   DEFAULT '0' COMMENT '订单状态 0:待支付 1:支付处理中 2:支付成功,待使用 3:已使用,待评价 4:已完成 5:已关闭',
    `refund_state`    tinyint(1)   DEFAULT NULL COMMENT '退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功',
    `close_type`      tinyint(1)   DEFAULT NULL COMMENT '关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成',
    `discount_amount` int(10)      DEFAULT '0' COMMENT '优惠金额',
    `pay_amount`      int(10)      DEFAULT '0' COMMENT '付款金额=单价*数量-优惠金额',
    `coupon_id`       bigint(20)   DEFAULT NULL COMMENT '优惠券id',
    `pay_time`        datetime     DEFAULT NULL COMMENT '订单支付时间',
    `complete_time`   datetime     DEFAULT NULL COMMENT '订单完成时间',
    `close_time`      datetime     DEFAULT NULL COMMENT '订单关闭时间',
    `add_time`        datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         varchar(255) DEFAULT '0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单表';

CREATE TABLE `restaurant_order`
(
    `id`            bigint(20) NOT NULL COMMENT '主键',
    `restaurant_id` bigint(20)   DEFAULT NULL COMMENT '餐饮商家id(冗余)',
    `voucher_id`    bigint(20)   DEFAULT NULL COMMENT '餐饮券id',
    `order_no`      varchar(30)  DEFAULT NULL COMMENT '订单编号',
    `cover_url`     varchar(100) DEFAULT NULL COMMENT '封面图片',
    `line_price`    int(10)      DEFAULT NULL COMMENT '划线价',
    `sale_price`    int(10)      DEFAULT NULL COMMENT '销售价',
    `describe`      varchar(200) DEFAULT NULL COMMENT '购买说明',
    `quota`         int(10)      DEFAULT '1' COMMENT '限购数量',
    `valid_days`    smallint(3)  DEFAULT NULL COMMENT '有效期购买之日起',
    `effect_date`   date         DEFAULT NULL COMMENT '生效时间(包含)',
    `expire_date`   date         DEFAULT NULL COMMENT '失效日期(包含)',
    `effect_time`   varchar(10)  DEFAULT NULL COMMENT '使用开始时间',
    `expire_time`   varchar(10)  DEFAULT NULL COMMENT '使用截止时间',
    `introduce`     longtext COMMENT '详细介绍',
    `add_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='餐饮券订单表';

CREATE TABLE `homestay_order_snapshot`
(
    `id`               bigint(20) NOT NULL COMMENT '主键',
    `homestay_room_id` bigint(20)  DEFAULT NULL COMMENT '房型id',
    `order_no`         varchar(30) DEFAULT NULL COMMENT '订单编号',
    `sale_price`       int(10)     DEFAULT '0' COMMENT '销售价',
    `line_price`       int(10)     DEFAULT '0' COMMENT '划线价',
    `config_date`      date        DEFAULT NULL COMMENT '日期',
    `add_time`         datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`      datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='房态快照表';




