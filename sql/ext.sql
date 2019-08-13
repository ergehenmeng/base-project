delete from system_cache where id = 3;

delete from system_cache where id = 5;

INSERT INTO `system_config` (`nid`, `title`, `content`, `classify`, `locked`, `start_time`, `end_time`, `reserve_content`, `remark`, `add_time`, `update_time`) VALUES ('token_expire', 'token过期时间', '864000', '2', b'0', NULL, NULL, NULL, '单位秒', '2019-08-13 15:18:33', '2019-08-13 15:18:33');

INSERT INTO `system_config` (`nid`, `title`, `content`, `classify`, `locked`, `start_time`, `end_time`, `reserve_content`, `remark`, `add_time`, `update_time`) VALUES ('sso_open', '是否开启单设备单点登录', '0', '2', b'0', NULL, NULL, NULL, '0:不开启 1:开启', '2019-08-13 15:45:39', '2019-08-13 15:45:39');
