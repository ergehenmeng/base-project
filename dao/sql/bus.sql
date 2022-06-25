CREATE TABLE `scenic`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `scenic_name`    varchar(50)    DEFAULT NULL COMMENT '景区名称',
    `level`          tinyint(1) DEFAULT NULL COMMENT '景区等级 5: 5A 4: 4A 3:3A 2:2A 1:A 0:其他',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '景区营业时间',
    `province_id`    bigint(20) DEFAULT NULL COMMENT '省份id',
    `city_id`        bigint(20) DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20) DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(100)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `describe`       varchar(100)   DEFAULT NULL COMMENT '景区描述信息',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '景区图片',
    `introduce`      longtext COMMENT '景区详细介绍信息',
    `add_time`       datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='景区信息表';


CREATE TABLE `scenic_ticket`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `scenic_id`         bigint(20) DEFAULT NULL COMMENT '门票所属景区',
    `title`             varchar(50)  DEFAULT NULL COMMENT '门票名称',
    `category`          tinyint(2) DEFAULT NULL COMMENT '门票种类 1: 成人票 2: 老人票 3:儿童票',
    `cover_url`         varchar(500) DEFAULT NULL COMMENT '门票封面图',
    `line_price`        int(10) DEFAULT '0' COMMENT '划线价',
    `sale_price`        int(10) DEFAULT '0' COMMENT '销售价',
    `cost_price`        int(10) DEFAULT '0' COMMENT '成本价',
    `advance_day`       tinyint(2) unsigned DEFAULT NULL COMMENT '提前多少天购票',
    `quota`             tinyint(2) DEFAULT '1' COMMENT '单次最大购买数量',
    `start_date`        date         DEFAULT NULL COMMENT '开始预订时间',
    `end_date`          date         DEFAULT NULL COMMENT '截止预订时间',
    `stock`             int(10) DEFAULT '0' COMMENT '剩余库存',
    `sale_num`          int(10) DEFAULT '0' COMMENT '真实销售数量',
    `total_num`         int(10) DEFAULT '0' COMMENT '总销量=实际销量+虚拟销量',
    `introduce`         longtext COMMENT '景区介绍',
    `valid_days`        smallint(3) DEFAULT NULL COMMENT '有效期购买之日起',
    `effect_date`       date         DEFAULT NULL COMMENT '生效日期(包含)',
    `expire_date`       date         DEFAULT NULL COMMENT '失效日期(包含)',
    `use_scope`         smallint(3) DEFAULT NULL COMMENT '使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日',
    `verification_type` tinyint(2) DEFAULT NULL COMMENT '核销方式 1:手动核销 2:自动核销 (凌晨自动核销)',
    `support_refund`    bit(1)       DEFAULT b'1' COMMENT '是否支持退款 1:支持 0:不支持',
    `real_buy`          bit(1)       DEFAULT b'0' COMMENT '是否实名购票 0:不实名 1:实名',
    `add_time`          datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)       DEFAULT b'0' COMMENT '是否删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='景区门票信息表';


CREATE TABLE `sys_merchant`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `merchant_name`         varchar(30)  DEFAULT NULL COMMENT '商家名称',
    `type`          smallint(4) DEFAULT NULL COMMENT '商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路',
    `contact_name`  varchar(10)  DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` varchar(20)  DEFAULT NULL COMMENT '联系人电话',
    `user_name`     varchar(30)  DEFAULT NULL COMMENT '账号名称',
    `pwd`           varchar(255) DEFAULT NULL COMMENT '账号密码',
    `init_pwd`      bit(1)       DEFAULT b'1' COMMENT '是否为初始化密码 1:是 0:不是',
    `state`         tinyint(1) DEFAULT '1' COMMENT '状态 0:锁定 1:正常 2:销户',
    `add_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)       DEFAULT b'0' COMMENT '是否为删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家信息表';


alter table scenic
    add column min_price int(10) default 0 comment '景区最低票价';
alter table scenic
    add column max_price int(10) default 0 comment '景区最高票价';
alter table scenic_ticket
    add column state tinyint(1) default 0 comment '景区状态 0:待上架 1:已上架';

CREATE TABLE `homestay`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `title`          varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '民宿名称',
    `level`          tinyint(1) DEFAULT NULL COMMENT '星级 5:五星级 4:四星级 3:三星级 0: 其他',
    `state`          tinyint(1) DEFAULT NULL COMMENT '状态 0:待上架  1:已上架',
    `province_id`    bigint(20) DEFAULT NULL COMMENT '省份',
    `city_id`        bigint(20) DEFAULT NULL COMMENT '城市',
    `county_id`      bigint(20) DEFAULT NULL COMMENT '县区',
    `detail_address` varchar(100)                           DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7)                         DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7)                         DEFAULT NULL COMMENT '纬度',
    `describe`       varchar(100)                           DEFAULT NULL COMMENT '描述信息',
    `cover_url`      varchar(100)                           DEFAULT NULL COMMENT '封面图片',
    `introduce`      longtext COMMENT '详细介绍',
    `phone`          varchar(20)                            DEFAULT NULL COMMENT '联系电话',
    `infrastructure` varchar(200)                           DEFAULT NULL COMMENT '基础设施',
    `bathroom`       varchar(200)                           DEFAULT NULL COMMENT '卫浴设施',
    `kitchen`        varchar(200)                           DEFAULT NULL COMMENT '厨房设施',
    `key_service`    varchar(200)                           DEFAULT NULL COMMENT '特色服务',
    `add_time`       datetime                               DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                 DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='民宿信息表';


CREATE TABLE `homestay_room`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `homestay_id`     bigint(20) DEFAULT NULL COMMENT '民宿id',
    `state`           tinyint(1) DEFAULT '0' COMMENT '房型上架状态 0:待上架 1:已上架',
    `room`            tinyint(1) DEFAULT '1' COMMENT '几室',
    `hall`            tinyint(1) DEFAULT '0' COMMENT '几厅',
    `kitchen`         tinyint(1) DEFAULT '0' COMMENT '几厨',
    `washroom`        tinyint(1) DEFAULT '1' COMMENT '卫生间数',
    `dimension`       smallint(3) DEFAULT NULL COMMENT '面积',
    `resident`        tinyint(2) DEFAULT '2' COMMENT '居住人数',
    `bed`             tinyint(1) DEFAULT '1' COMMENT '床数',
    `room_type`       tinyint(1) DEFAULT NULL COMMENT '房型类型 1:整租 2:单间 3:合租',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `introduce`       longtext COMMENT '详细介绍',
    `support_refund`  bit(1)        DEFAULT b'0' COMMENT '是否支持退款 0:不支持 1:支持',
    `refund_describe` varchar(200)  DEFAULT NULL COMMENT '退款描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型信息表';

CREATE TABLE `homestay_room_config`
(
    `id`               bigint(20) NOT NULL COMMENT '主键',
    `homestay_room_id` bigint(20) DEFAULT NULL COMMENT '房型id',
    `config_date`      date     DEFAULT NULL COMMENT '日期',
    `line_price`       int(10) DEFAULT NULL COMMENT '划线机',
    `sale_price`       int(10) DEFAULT NULL COMMENT '销售价',
    `stock`            smallint(4) DEFAULT NULL COMMENT '剩余库存',
    `sale_num`         smallint(4) DEFAULT NULL COMMENT '已预订数量',
    `add_time`         datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`      datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间价格配置表';

alter table homestay
    add column merchant_id bigint(20) comment '所属商户' AFTER title;

CREATE TABLE `restaurant`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `title`       varchar(50)    DEFAULT NULL COMMENT '商家名称',
    `merchant_id` bigint(20) DEFAULT NULL COMMENT '所属商户',
    `state`       tinyint(1) DEFAULT '0' COMMENT '状态 0:待上架 1:已上架',
    `cover_url`   varchar(1000)  DEFAULT NULL COMMENT '商家封面',
    `open_time`   varchar(100)   DEFAULT NULL COMMENT '营业时间',
    `province_id` bigint(20) DEFAULT NULL COMMENT '省份',
    `city_id`     bigint(20) DEFAULT NULL COMMENT '城市id',
    `county_id`   bigint(20) DEFAULT NULL COMMENT '县区id',
    `longitude`   decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`    decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `phone`       varchar(20)    DEFAULT NULL COMMENT '商家热线',
    `introduce`   longtext COMMENT '商家介绍',
    `add_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='餐饮商家信息表';

CREATE TABLE `restaurant_voucher`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `restaurant_id`  bigint(20) DEFAULT NULL COMMENT '餐饮商家id',
    `title`          varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
    `cover_url`      varchar(100)                           DEFAULT NULL COMMENT '封面图片',
    `line_price`     int(10) DEFAULT NULL COMMENT '划线价',
    `sale_price`     int(10) DEFAULT NULL COMMENT '销售价',
    `stock`          int(10) DEFAULT NULL COMMENT '剩余库存',
    `sale_num`       int(10) DEFAULT '0' COMMENT '销售数量',
    `total_num`      int(10) DEFAULT '0' COMMENT '总销量=虚拟销量+真实销量',
    `describe`       varchar(200)                           DEFAULT NULL COMMENT '购买说明',
    `quota`          int(10) DEFAULT '1' COMMENT '限购数量',
    `valid_days`     smallint(3) DEFAULT NULL COMMENT '有效期购买之日起',
    `effect_date`    date                                   DEFAULT NULL COMMENT '生效开始时间(包含)',
    `expire_date`    date                                   DEFAULT NULL COMMENT '失效日期',
    `effect_time`    varchar(10)                            DEFAULT NULL COMMENT '使用开始时间',
    `expire_time`    varchar(10)                            DEFAULT NULL COMMENT '使用截止时间',
    `support_refund` bit(1)                                 DEFAULT b'1' COMMENT '是否支持退款 0:不支持 1:支持',
    `introduce`      longtext COMMENT '详细介绍',
    `add_time`       datetime                               DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                 DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='餐饮代金券';

CREATE TABLE `specialty_store`
(
    `id`             bigint(20) NOT NULL COMMENT '主键',
    `state`          tinyint(1) DEFAULT NULL COMMENT '状态 0:未商家 1:已上架',
    `merchant_id`    bigint(20) DEFAULT NULL COMMENT '所属商户id',
    `cover_url`      varchar(1000)  DEFAULT NULL COMMENT '封面图',
    `open_time`      varchar(100)   DEFAULT NULL COMMENT '营业时间',
    `province_id`    bigint(20) DEFAULT NULL COMMENT '省id',
    `city_id`        bigint(20) DEFAULT NULL COMMENT '城市id',
    `county_id`      bigint(20) DEFAULT NULL COMMENT '县区id',
    `detail_address` varchar(200)   DEFAULT NULL COMMENT '详细地址',
    `longitude`      decimal(10, 7) DEFAULT NULL COMMENT '经度',
    `latitude`       decimal(10, 7) DEFAULT NULL COMMENT '纬度',
    `telephone`      varchar(20)    DEFAULT NULL COMMENT '商家电话',
    `introduce`      longtext COMMENT '商家介绍',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特产店信息表';

CREATE TABLE `specialty_product`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `store_id`        bigint(20) DEFAULT NULL COMMENT '所属特产店',
    `state`           tinyint(1) DEFAULT NULL COMMENT '商品状态 0:待下架 1:已上架',
    `title`           varchar(50)   DEFAULT NULL COMMENT '商品名称',
    `cover_url`       varchar(1000) DEFAULT NULL COMMENT '封面图',
    `purchase_notes`  varchar(200)  DEFAULT NULL COMMENT '购买须知',
    `quota`           smallint(3) DEFAULT '1' COMMENT '限购数量',
    `delivery_method` tinyint(1) DEFAULT NULL COMMENT '交付方式 1:门店自提 2:快递包邮',
    `support_refund`  bit(1)        DEFAULT b'0' COMMENT '是否支持退款 true:支持 false:不支持',
    `min_price`       int(10) DEFAULT NULL COMMENT '最低价格',
    `max_price`       int(10) DEFAULT NULL COMMENT '最高价格',
    `sale_num`        int(10) DEFAULT '0' COMMENT '销售数量(所有规格销售总量)',
    `total_num`       int(10) DEFAULT '0' COMMENT '总销售量=实际销售+虚拟销量',
    `introduce`       longtext COMMENT '商品介绍信息',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特产商品信息';

CREATE TABLE `specialty_product_sku`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `product_id`  bigint(20) DEFAULT NULL COMMENT '特产商品id',
    `title`       varchar(20) DEFAULT NULL COMMENT '规格名称',
    `line_price`  int(10) DEFAULT NULL COMMENT '划线价',
    `cost_price`  int(10) DEFAULT '0' COMMENT '成本价',
    `sale_price`  int(10) DEFAULT '0' COMMENT '销售价',
    `stock`       smallint(4) DEFAULT NULL COMMENT '库存',
    `sale_num`    int(10) DEFAULT '0' COMMENT '销售量',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      DEFAULT b'0' COMMENT '删除状态 0:未删除 1:已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特产商品规格表';

CREATE TABLE `line_product`
(
    `id`                bigint(20) NOT NULL COMMENT '主键',
    `travel_agency_id`  varchar(255)  DEFAULT NULL COMMENT '所属旅行社id',
    `title`             varchar(50)   DEFAULT NULL COMMENT '线路名称',
    `start_province_id` bigint(20) DEFAULT NULL COMMENT '出发省份id',
    `start_city_id`     bigint(20) DEFAULT NULL COMMENT '出发城市id',
    `cover_url`         varchar(1000) DEFAULT NULL COMMENT '封面图片',
    `sale_num`          int(10) DEFAULT '0' COMMENT '销售量',
    `total_num`         int(10) DEFAULT '0' COMMENT '总销量=实际销售+虚拟销量',
    `duration`          tinyint(2) DEFAULT NULL COMMENT '几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游',
    `advance_day`       tinyint(2) DEFAULT NULL COMMENT '提前天数',
    `support_refund`    bit(1)        DEFAULT NULL COMMENT '是否支持退款 0:不支持 1:支持',
    `introduce`         longtext COMMENT '商品介绍',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线路商品信息表';

CREATE TABLE `line_product_config`
(
    `id`              bigint(20) NOT NULL COMMENT '主键',
    `line_product_id` bigint(20) DEFAULT NULL COMMENT '线路商品id',
    `config_date`     date     DEFAULT NULL COMMENT '配置日期',
    `stock`           smallint(4) DEFAULT '0' COMMENT '总库存',
    `sale_price`      int(10) DEFAULT '0' COMMENT '销售价格',
    `sale_num`        int(10) DEFAULT '0' COMMENT '销售数量',
    `create_time`     datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线路商品配置表';

alter table scenic add column tag varchar(300) comment '景区标签,逗号分隔' after open_time;
alter table scenic add column merchant_id bigint(20) comment '景区所属商家(为空则表示系统自营)' after tag;
alter table scenic add column state tinyint(2) default 0 comment '景区状态 0:待上架 1:已上架' AFTER merchant_id;
alter table scenic add column sort smallint(3) default 999 comment '景区排序(小<->大)' AFTER state;

alter table sys_merchant change contact_name  nick_name varchar(20) comment '联系人姓名';
alter table sys_merchant change contact_phone mobile varchar(20) comment '联系人电话';

CREATE TABLE `sys_merchant_role` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`merchant_id` bigint(20) DEFAULT NULL COMMENT '商户ID',
`role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户与角色关联表';

alter table homestay_room_config add column deleted bit(1) default 0 comment '删除状态 0:未删除 1:已删除';
alter table homestay add column tags varchar(200) comment '标签';