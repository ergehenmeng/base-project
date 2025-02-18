INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1010', '系统管理', 'Vz', 'Setting', '0', '/sys', NULL, '1', '4', b'1', '平台专享', '2', '2018-01-24 08:13:54',
        '2024-09-06 14:55:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101010', '菜单管理', 'EQU', 'Menu', '1010', '/sys/menu', NULL, '1', '1', b'1', '', '2', '2018-01-24 08:14:01',
        '2024-05-28 18:01:30');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101010', '新增', 'nhK0', NULL, '101010', NULL, '/manage/menu/create', '2', '2', b'1', '按钮权限', '2',
        '2019-01-22 06:16:11', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101011', '列表', 'YhK0', NULL, '101010', NULL, '/manage/menu/list', '2', '1', b'1', '列表', '2',
        '2019-01-22 06:19:01', '2024-05-28 14:24:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101012', '编辑', 'uhK0', NULL, '101010', NULL, '/manage/menu/update', '2', '3', b'1', '', '2',
        '2019-09-09 07:59:57', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101013', '删除', 'NhK0', NULL, '101010', NULL, '/manage/menu/delete', '2', '4', b'1', '', '2',
        '2019-09-09 08:01:28', '2023-09-27 17:26:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101014', '排序', 'GhK0', NULL, '101010', NULL, '/manage/menu/sort', '2', '30', b'1', NULL, '2',
        '2024-05-27 17:59:48', '2024-05-27 18:00:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101015', '禁启', 'AhK0', NULL, '101010', NULL, '/manage/menu/updateState', '2', '40', b'1', '禁用或启用',
        '2', '2024-05-28 16:09:37', '2024-05-28 16:10:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101011', '系统参数', 'wQU', 'Tools', '1010', '/sys/config', NULL, '1', '2', b'1', NULL, '2',
        '2018-01-24 08:14:31', '2024-05-28 18:02:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101110', '列表', 'siK0', NULL, '101011', NULL, '/manage/config/listPage', '2', '1', b'1', '列表', '2',
        '2019-09-12 08:13:51', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101111', '编辑', 'PiK0', NULL, '101011', NULL, '/manage/config/update', '2', '2', b'1', '', '2',
        '2019-09-12 08:15:48', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101012', '用户管理', '1QU', 'UserFilled', '1010', '/sys/user', NULL, '1', '3', b'1', NULL, '2',
        '2018-01-24 08:14:40', '2024-05-28 18:02:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101210', '列表', 'XqK0', NULL, '101012', NULL, '/manage/user/listPage', '2', '1', b'1', '列表', '2',
        '2019-09-12 08:21:44', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101211', '编辑', 'iqK0', NULL, '101012', NULL, '/manage/user/update,/manage/dept/list', '2', '2', b'1', '',
        '2', '2019-09-12 08:22:32', '2024-01-09 16:45:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101212', '锁定', 'qqK0', NULL, '101012', NULL, '/manage/user/lock', '2', '3', b'1', '', '2',
        '2019-09-12 08:24:02', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101213', '解锁', '8qK0', NULL, '101012', NULL, '/manage/user/unlock', '2', '4', b'1', '', '2',
        '2019-09-12 08:24:26', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101214', '重置', 'jqK0', NULL, '101012', NULL, '/manage/user/reset', '2', '5', b'1', '', '2',
        '2019-09-12 08:25:03', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101215', '添加', 'gqK0', NULL, '101012', NULL, '/manage/user/create,/manage/dept/list', '2', '6', b'1', '',
        '2', '2019-09-12 08:26:12', '2024-01-09 16:45:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101216', '删除', '2qK0', NULL, '101012', NULL, '/manage/user/delete', '2', '7', b'1', '', '2',
        '2019-09-12 08:34:32', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101217', '详情', 'mqK0', NULL, '101012', NULL, '/manage/user/select', '2', '10', b'1', NULL, '2',
        '2024-03-08 17:22:32', '2024-03-08 17:23:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101013', '角色管理', 'CQU', 'HelpFilled', '1010', '/sys/role', NULL, '1', '4', b'1', NULL, '2',
        '2018-01-24 08:14:56', '2024-05-28 18:03:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101310', '列表', 'JjK0', NULL, '101013', NULL, '/manage/role/listPage', '2', '1', b'1', '', '2',
        '2019-09-12 08:35:33', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101311', '添加', 'KjK0', NULL, '101013', NULL, '/manage/role/create', '2', '2', b'1', '', '2',
        '2019-09-12 08:36:26', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101312', '编辑', '5jK0', NULL, '101013', NULL, '/manage/role/update', '2', '3', b'1', '', '2',
        '2019-09-12 08:36:47', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101313', '授权', 'ZjK0', NULL, '101013', NULL,
        '/manage/role/auth,/manage/role/menu,/manage/menu/systemList,/manage/menu/merchantList', '2', '4', b'1', '',
        '2', '2019-09-12 08:37:20', '2023-09-27 17:21:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101314', '删除', 'UjK0', NULL, '101013', NULL, '/manage/role/delete', '2', '5', b'1', '', '2',
        '2019-09-12 08:37:47', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101014', '图片管理', 'zQU', 'Picture', '1010', '/sys/image', NULL, '1', '5', b'1', NULL, '2',
        '2018-11-27 09:02:36', '2024-05-28 18:03:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101410', '列表', 'ogK0', NULL, '101014', NULL, '/manage/image/listPage', '2', '1', b'1', '', '2',
        '2019-09-12 08:49:04', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101411', '添加', 'vgK0', NULL, '101014', NULL, '/manage/image/create', '2', '2', b'1', '', '2',
        '2019-09-12 08:50:00', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101412', '编辑', 'I2K0', NULL, '101014', NULL, '/manage/image/update', '2', '3', b'1', '', '2',
        '2019-09-12 08:51:26', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101413', '删除', 'F2K0', NULL, '101014', NULL, '/manage/image/delete', '2', '4', b'1', '', '2',
        '2019-09-12 08:51:45', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101015', '数据字典', 'QQU', 'Collection', '1010', '/sys/dict', NULL, '1', '6', b'1', NULL, '2',
        '2019-01-11 09:51:31', '2024-05-28 18:04:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101510', '列表', 'GmK0', NULL, '101015', NULL, '/manage/dict/list', '2', '10', b'1', '', '2',
        '2019-09-12 08:54:05', '2024-06-25 11:47:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101511', '添加', 'AmK0', NULL, '101015', NULL, '/manage/dict/create', '2', '20', b'1', '', '2',
        '2019-09-12 08:55:21', '2023-12-18 16:55:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101512', '编辑', 'rmK0', NULL, '101015', NULL, '/manage/dict/update', '2', '30', b'1', '', '2',
        '2019-09-12 08:55:43', '2023-12-18 16:55:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101513', '删除', 'RmK0', NULL, '101015', NULL, '/manage/dict/delete', '2', '40', b'1', '', '2',
        '2019-09-12 08:56:13', '2023-12-18 16:55:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101514', '子项添加', '9mK0', NULL, '101015', NULL, '/manage/dict/item/create', '2', '50', b'1', NULL, '2',
        '2023-12-18 16:55:17', '2023-12-18 16:57:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101515', '子项编辑', 'amK0', NULL, '101015', NULL, '/manage/dict/item/update', '2', '60', b'1', NULL, '2',
        '2023-12-18 16:55:17', '2023-12-18 16:57:14');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101516', '子项删除', '0mK0', NULL, '101015', NULL, '/manage/dict/item/delete', '2', '70', b'1', NULL, '2',
        '2023-12-18 16:55:17', '2023-12-18 16:57:15');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101016', '缓存管理', 'VQU', 'Cpu', '1010', '/sys/cache', NULL, '1', '7', b'1', NULL, '2',
        '2019-01-14 07:27:58', '2024-05-28 18:04:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101610', '列表', 'E7K0', NULL, '101016', NULL, '/manage/cache/list', '2', '1', b'1', '', '2',
        '2019-09-12 08:58:30', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101611', '清除', 'w7K0', NULL, '101016', NULL, '/manage/cache/clear', '2', '2', b'1', '', '2',
        '2019-09-12 08:59:33', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101018', '部门管理', 'xQU', 'Notification', '1010', '/sys/dept', NULL, '1', '9', b'1', '暂未实现', '2',
        '2019-01-17 10:03:54', '2024-05-28 18:06:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101810', '列表', 'ULK0', NULL, '101018', NULL, '/manage/dept/list', '2', '0', b'1', NULL, '2',
        '2022-05-02 07:55:27', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101811', '添加', 'kLK0', NULL, '101018', NULL, '/manage/dept/create', '2', '10', b'1', NULL, '2',
        '2022-05-02 07:56:05', '2023-12-28 15:07:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101812', '编辑', 'lLK0', NULL, '101018', NULL, '/manage/dept/update', '2', '20', b'1', NULL, '2',
        '2022-05-02 07:56:32', '2023-12-28 15:07:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101813', '删除', 'OLK0', NULL, '101018', NULL, '/manage/dept/delete', '2', '30', b'1', NULL, '2',
        '2023-12-28 15:08:11', '2023-12-29 10:09:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101019', 'ip黑名单', 'tQU', 'SetUp', '1010', '/sys/blackRoster', NULL, '1', '10', b'1', NULL, '2',
        '2023-09-26 15:57:22', '2024-05-28 18:06:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101910', '列表', 'boK0', NULL, '101019', NULL, '/manage/black/roster/listPage', '2', '10', b'1', NULL, '2',
        '2023-09-26 15:57:22', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101911', '新增', 'HoK0', NULL, '101019', NULL, '/manage/black/roster/create', '2', '20', b'1', NULL, '2',
        '2023-09-26 15:57:22', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10101912', '删除', '4oK0', NULL, '101019', NULL, '/manage/black/roster/delete', '2', '30', b'1', NULL, '2',
        '2023-09-26 15:57:22', '2023-09-27 14:39:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101020', '授权管理', 'JQU', 'FolderChecked', '1010', '/sys/auth', NULL, '1', '12', b'1', '第三方授权管理', '2',
        '2023-10-23 14:17:04', '2024-05-28 18:06:59');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10102010', '列表', '9vK0', NULL, '101020', NULL, '/manage/auth/listPage', '2', '10', b'1', NULL, '2',
        '2023-10-23 14:17:48', '2023-10-23 14:21:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10102011', '新增', 'avK0', NULL, '101020', NULL, '/manage/auth/create', '2', '20', b'1', NULL, '2',
        '2023-10-23 14:17:48', '2023-10-23 14:21:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10102012', '编辑', '0vK0', NULL, '101020', NULL, '/manage/auth/update', '2', '30', b'1', NULL, '2',
        '2023-10-23 14:17:48', '2023-10-23 14:21:19');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10102013', '删除', 'dvK0', NULL, '101020', NULL, '/manage/auth/delete', '2', '40', b'1', NULL, '2',
        '2023-10-23 14:18:21', '2023-10-23 14:21:20');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10102014', '重置', 'pvK0', NULL, '101020', NULL, '/manage/auth/reset', '2', '35', b'1', '重置授权秘钥', '2',
        '2024-05-25 22:06:01', '2024-05-25 22:06:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1011', '平台运营', '6z', 'Operation', '0', '/operation', NULL, '1', '20', b'1', '平台运营专享', '2',
        '2019-08-22 03:54:33', '2024-05-22 14:03:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101110', '轮播管理', 'jVU', 'Film', '1011', '/operation/banner', NULL, '1', '1', b'1', '', '2',
        '2019-08-23 03:55:35', '2024-05-28 18:08:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111010', '列表', 'jxU0', NULL, '101110', NULL, '/manage/banner/listPage', '2', '1', b'1', '', '2',
        '2019-09-12 09:03:26', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111011', '添加', 'gxU0', NULL, '101110', NULL, '/manage/banner/create', '2', '2', b'1', '', '2',
        '2019-09-12 09:03:49', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111012', '编辑', '2xU0', NULL, '101110', NULL, '/manage/banner/update', '2', '3', b'1', '', '2',
        '2019-09-12 09:04:18', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111013', '删除', 'mxU0', NULL, '101110', NULL, '/manage/banner/delete', '2', '4', b'1', '', '2',
        '2019-09-12 09:04:45', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111014', '排序', 'BxU0', NULL, '101110', NULL, '/manage/banner/sort', '2', '10', b'1', NULL, '2',
        '2024-05-28 22:49:00', '2024-05-28 22:49:50');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111015', '更新状态', '7xU0', NULL, '101110', NULL, '/manage/banner/updateState', '2', '10', b'1', NULL, '2',
        '2024-05-28 22:49:18', '2024-05-28 22:49:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101111', '公告管理', 'gVU', 'Promotion', '1011', '/operation/notice', NULL, '1', '2', b'1', '', '2',
        '2019-08-23 07:09:57', '2024-05-28 18:09:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111110', '列表', 'UJU0', NULL, '101111', NULL, '/manage/notice/listPage', '2', '1', b'1', '', '2',
        '2019-11-25 07:51:19', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111111', '编辑', 'kJU0', NULL, '101111', NULL, '/manage/notice/update', '2', '3', b'1', '', '2',
        '2019-11-25 07:51:56', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111112', '发布', 'lJU0', NULL, '101111', NULL, '/manage/notice/publish', '2', '4', b'1', '', '2',
        '2019-11-25 07:52:22', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111113', '取消', 'OJU0', NULL, '101111', NULL, '/manage/notice/cancel', '2', '5', b'1', '', '2',
        '2019-11-25 07:52:53', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111114', '删除', 'cJU0', NULL, '101111', NULL, '/manage/notice/delete', '2', '6', b'1', '', '2',
        '2019-11-25 07:53:31', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111115', '添加', 'DJU0', NULL, '101111', NULL, '/manage/notice/create', '2', '2', b'1', '', '2',
        '2019-11-25 08:09:16', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111116', '查看', 'nJU0', NULL, '101111', NULL, '/manage/notice/select', '2', '11', b'1', '', '2',
        '2022-11-08 21:45:00', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101114', '版本管理', 'BVU', 'Files', '1011', '/operation/version', NULL, '1', '5', b'1', '', '2',
        '2019-08-23 07:16:59', '2024-05-28 18:10:38');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111410', '列表', 'zkU0', NULL, '101114', NULL, '/manage/version/listPage', '2', '1', b'1', '', '2',
        '2019-09-15 05:06:36', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111411', '添加', 'QkU0', NULL, '101114', NULL, '/manage/version/create', '2', '2', b'1', '', '2',
        '2019-09-15 05:07:18', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111412', '编辑', 'VkU0', NULL, '101114', NULL, '/manage/version/update', '2', '3', b'1', '暂未实现前端页面',
        '2', '2019-09-15 05:07:51', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111415', '删除', 'tkU0', NULL, '101114', NULL, '/manage/version/delete', '2', '6', b'1', '', '2',
        '2019-09-15 05:09:34', '2024-05-29 16:58:38');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10111416', '上下架', 'JkU0', NULL, '101114', NULL, '/manage/version/updateState', '2', '4', b'1', NULL, '2',
        '2024-05-29 16:58:20', '2024-05-29 16:58:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101124', '活动管理', 'b6U', 'Guide', '1011', '/operation/activity', NULL, '1', '70', b'1', NULL, '2',
        '2022-12-16 15:32:37', '2024-05-28 18:11:01');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112410', '月度活动', '5dU0', NULL, '101124', NULL, '/manage/activity/month', '2', '10', b'1', NULL, '2',
        '2022-12-16 15:32:37', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112411', '配置', 'ZdU0', NULL, '101124', NULL, '/manage/activity/config,/manage/scenic/storeListPage', '2',
        '20', b'1', '配置某一时间段的活动', '2', '2022-12-16 15:32:37', '2024-02-22 14:49:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112412', '新增', 'UdU0', NULL, '101124', NULL, '/manage/activity/create,/manage/scenic/storeListPage', '2',
        '30', b'1', NULL, '2', '2022-12-16 15:32:37', '2024-02-22 14:49:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112413', '编辑', 'kdU0', NULL, '101124', NULL,
        '/manage/activity/update,/manage/activity/select,/manage/scenic/storeListPage', '2', '40', b'1', NULL, '2',
        '2022-12-16 15:32:37', '2024-02-22 14:49:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112414', '详情', 'ldU0', NULL, '101124', NULL, '/manage/activity/select', '2', '50', b'1', NULL, '2',
        '2022-12-16 15:32:38', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10112415', '删除', 'OdU0', NULL, '101124', NULL, '/manage/activity/delete', '2', '60', b'1', NULL, '2',
        '2022-12-16 15:32:38', '2023-09-27 14:59:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101130', '资讯管理', 's6U', 'Postcard', '1011', '/operation/news', NULL, '1', '130', b'1', NULL, '2',
        '2023-12-29 15:58:03', '2024-08-19 15:03:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113010', '列表', '4mU0', NULL, '101130', NULL, '/manage/news/listPage,/manage/news/config/list', '2', '10',
        b'1', NULL, '2', '2023-12-29 15:58:03', '2024-06-03 09:37:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113011', '新增', 'SmU0', NULL, '101130', NULL, '/manage/news/create,/manage/news/config/select', '2', '20',
        b'1', NULL, '2', '2023-12-29 15:58:03', '2024-06-03 09:37:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113012', '编辑', 'ymU0', NULL, '101130', NULL,
        '/manage/news/update,/manage/news/config/select,/manage/news/select', '2', '30', b'1', NULL, '2',
        '2023-12-29 15:58:03', '2024-06-03 11:51:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113013', '删除', 'MmU0', NULL, '101130', NULL, '/manage/news/delete', '2', '40', b'1', NULL, '2',
        '2023-12-29 15:58:03', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113014', '排序', 'smU0', NULL, '101130', NULL, '/manage/news/sort', '2', '50', b'1', NULL, '2',
        '2023-12-29 15:58:03', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113015', '评论', 'PmU0', NULL, '101130', NULL, '/manage/comment/listPage', '2', '60', b'1', NULL, '2',
        '2024-09-26 09:11:49', '2024-09-26 09:14:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101131', '评论管理', 'P6U', 'Postcard', '1011', '/operation/comment', NULL, '1', '140', b'1', NULL, '2',
        '2024-01-17 14:34:58', '2024-05-28 18:11:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113110', '列表', '0BU0', NULL, '101131', NULL, '/manage/comment/listPage', '2', '10', b'1', NULL, '2',
        '2024-01-17 14:34:58', '2024-03-01 17:18:48');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113111', '屏蔽', 'dBU0', NULL, '101131', NULL, '/manage/comment/shield', '2', '20', b'1', NULL, '2',
        '2024-01-17 14:35:27', '2024-03-01 17:18:50');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113112', '举报列表', 'pBU0', NULL, '101131', NULL, '/manage/comment/report/listPage', '2', '30', b'1', NULL,
        '2', '2024-01-17 14:34:58', '2024-03-01 17:18:52');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113113', '置顶', 'hBU0', NULL, '101131', NULL, '/manage/comment/top', '2', '40', b'1', NULL, '2',
        '2024-01-17 16:28:46', '2024-03-01 17:18:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113114', '取消置顶', 'XBU0', NULL, '101131', NULL, '/manage/comment/unTop', '2', '50', b'1', NULL, '2',
        '2024-01-17 16:28:46', '2024-03-01 17:18:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113115', '取消屏蔽', 'iBU0', NULL, '101131', NULL, '/manage/comment/unShield', '2', '25', b'1', NULL, '2',
        '2024-07-26 17:27:09', '2024-07-26 17:27:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101132', '评论举报', 'f6U', 'Warning', '1011', '/operation/commentReport', NULL, '1', '150', b'1', NULL, '2',
        '2024-06-03 15:35:37', '2024-06-03 15:36:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113210', '列表', 'VWU0', NULL, '101132', NULL, '/manage/comment/report/listPage', '2', '10', b'1', NULL, '2',
        '2024-06-03 15:59:35', '2024-06-03 16:00:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101133', '帮助中心', 'KQU', 'Help', '1011', '/sys/help', NULL, '1', '160', b'1', NULL, '2',
        '2024-10-22 14:21:15', '2024-10-22 14:49:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113310', '列表', 'W3U0', NULL, '101133', NULL, '/manage/help/listPage', '2', '10', b'1', NULL, '2',
        '2024-10-22 14:22:17', '2024-10-22 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113311', '新增', '33U0', NULL, '101133', NULL, '/manage/help/create', '2', '20', b'1', NULL, '2',
        '2024-10-22 14:22:18', '2024-10-22 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113312', '编辑', 'L3U0', NULL, '101133', NULL, '/manage/help/update,/manage/help/select', '2', '30', b'1',
        NULL, '2', '2024-10-22 14:22:18', '2024-10-22 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113313', '删除', 'T3U0', NULL, '101133', NULL, '/manage/help/delete', '2', '40', b'1', NULL, '2',
        '2024-10-22 14:22:18', '2024-10-22 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113314', '排序', 'o3U0', NULL, '101133', NULL, '/manage/help/sort', '2', '50', b'1', NULL, '2',
        '2024-10-22 14:22:18', '2024-10-22 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101134', '意见反馈', 'E6U', 'Stamp', '1011', '/sys/feedback', NULL, '1', '170', b'1', NULL, '2',
        '2024-10-22 14:38:38', '2024-10-22 14:49:14');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113410', '列表', 'nTU0', NULL, '101134', NULL, '/manage/feedback/listPage', '2', '10', b'1', NULL, '2',
        '2024-10-22 14:38:38', '2024-10-22 14:41:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113411', '回复', 'YTU0', NULL, '101134', NULL, '/manage/feedback/dispose', '2', '20', b'1', NULL, '2',
        '2024-10-22 14:38:38', '2024-10-22 14:41:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101135', '敏感词', 'w6U', 'Memo', '1011', '/sys/sensitive', NULL, '1', '180', b'1', NULL, '2',
        '2024-10-22 14:40:31', '2024-10-22 14:50:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113510', '列表', 'svU0', NULL, '101135', NULL, '/manage/sensitive/word/listPage', '2', '10', b'1', NULL, '2',
        '2024-10-22 14:40:31', '2024-10-22 14:41:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113511', '删除', 'PvU0', NULL, '101135', NULL, '/manage/sensitive/word/delete', '2', '20', b'1', NULL, '2',
        '2024-10-22 14:40:31', '2024-10-22 14:41:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113512', '新增', 'fvU0', NULL, '101135', NULL, '/manage/sensitive/word/create', '2', '30', b'1', NULL, '2',
        '2024-10-22 14:40:32', '2024-10-22 14:41:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1012', '商品管理', 'xz', 'Goods', '0', '/product', NULL, '1', '30', b'1', '商户及平台', '3',
        '2022-11-25 18:06:53', '2024-05-22 14:04:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101210', '景区管理', 'UxU', 'Bicycle', '1012', '/product/scenic', NULL, '1', '1', b'1', NULL, '3',
        '2022-11-25 18:08:40', '2024-05-28 18:12:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121010', '列表', 'yLl0', NULL, '101210', NULL, '/manage/scenic/listPage', '2', '1', b'1', NULL, '3',
        '2022-11-25 18:10:03', '2022-11-25 18:17:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121011', '新增', 'MLl0', NULL, '101210', NULL, '/manage/scenic/create', '2', '10', b'1', NULL, '1',
        '2022-11-25 18:10:40', '2023-09-27 15:00:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121012', '编辑', 'sLl0', NULL, '101210', NULL, '/manage/scenic/update,/manage/scenic/select', '2', '20',
        b'1', NULL, '1', '2022-11-25 18:11:44', '2023-12-22 14:53:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121013', '详情', 'PLl0', NULL, '101210', NULL, '/manage/scenic/select', '2', '30', b'1', NULL, '3',
        '2022-11-25 18:12:52', '2022-11-25 18:17:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121014', '上架', 'fLl0', NULL, '101210', NULL, '/manage/scenic/shelves', '2', '40', b'1', NULL, '1',
        '2022-11-25 18:13:31', '2023-09-27 15:00:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121015', '下架', 'eLl0', NULL, '101210', NULL, '/manage/scenic/unShelves', '2', '50', b'1', NULL, '1',
        '2022-11-25 18:13:53', '2023-09-27 15:00:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121017', '强制下架', 'wLl0', NULL, '101210', NULL, '/manage/scenic/platformUnShelves', '2', '70', b'1', NULL,
        '2', '2022-11-25 18:15:34', '2023-09-27 15:00:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121018', '删除', '1Ll0', NULL, '101210', NULL, '/manage/scenic/delete', '2', '80', b'1', NULL, '1',
        '2022-11-25 18:16:19', '2023-09-27 15:00:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121019', '导出', 'CLl0', NULL, '101210', NULL, '/manage/scenic/export', '2', '90', b'1', NULL, '3',
        '2024-08-01 17:05:02', '2024-08-01 17:05:15');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101211', '景区门票', 'kxU', 'Ticket', '1012', '/product/ticket', NULL, '1', '10', b'1', NULL, '3',
        '2022-11-26 15:53:22', '2024-05-28 18:12:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121110', '列表', 'pTl0', NULL, '101211', NULL, '/manage/scenic/ticket/listPage,/manage/scenic/list', '2',
        '1', b'1', NULL, '3', '2022-11-26 15:54:29', '2024-06-05 18:18:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121111', '新增', 'hTl0', NULL, '101211', NULL, '/manage/scenic/ticket/create,/manage/scenic/list', '2', '10',
        b'1', NULL, '1', '2022-11-26 16:00:07', '2024-06-05 18:18:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121112', '编辑', 'XTl0', NULL, '101211', NULL,
        '/manage/scenic/ticket/update,/manage/scenic/ticket/select,/manage/scenic/list', '2', '20', b'1', NULL, '1',
        '2022-11-26 16:00:07', '2024-06-05 18:18:50');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121113', '详情', 'iTl0', NULL, '101211', NULL, '/manage/scenic/ticket/select', '2', '30', b'1', NULL, '3',
        '2022-11-26 16:00:07', '2022-11-26 16:08:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121114', '上架', 'qTl0', NULL, '101211', NULL, '/manage/scenic/ticket/shelves', '2', '40', b'1', NULL, '1',
        '2022-11-26 16:00:07', '2023-09-27 15:00:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121115', '下架', '8Tl0', NULL, '101211', NULL, '/manage/scenic/ticket/unShelves', '2', '50', b'1', NULL, '1',
        '2022-11-26 16:00:07', '2023-09-27 15:00:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121117', '强制下架', 'gTl0', NULL, '101211', NULL, '/manage/scenic/ticket/platformUnShelves', '2', '70',
        b'1', NULL, '2', '2022-11-26 18:01:13', '2023-09-27 15:02:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121118', '删除', '2Tl0', NULL, '101211', NULL, '/manage/scenic/ticket/delete', '2', '80', b'1', NULL, '1',
        '2022-11-26 16:10:20', '2023-09-27 15:02:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121119', '导出', 'mTl0', NULL, '101211', NULL, '/manage/scenic/ticket/export', '2', '90', b'1', NULL, '3',
        '2024-08-02 13:46:19', '2024-08-02 13:46:38');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101212', '线路管理', 'lxU', 'Place', '1012', '/product/line', NULL, '1', '20', b'1', NULL, '3',
        '2022-11-26 17:38:18', '2024-05-28 18:13:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121210', '列表', 'xvl0', NULL, '101212', NULL, '/manage/line/listPage,/manage/travel/list', '2', '1', b'1',
        NULL, '3', '2022-11-26 17:38:46', '2024-06-11 09:35:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121211', '新增', 'tvl0', NULL, '101212', NULL, '/manage/line/create,/manage/travel/list', '2', '10', b'1',
        NULL, '1', '2022-11-26 17:38:46', '2024-06-06 15:28:42');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121212', '编辑', 'Jvl0', NULL, '101212', NULL, '/manage/line/update,/manage/line/select,/manage/travel/list',
        '2', '20', b'1', NULL, '1', '2022-11-26 17:38:46', '2024-06-06 15:28:46');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121213', '详情', 'Kvl0', NULL, '101212', NULL, '/manage/line/select', '2', '30', b'1', NULL, '3',
        '2022-11-26 17:38:46', '2022-11-26 17:45:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121214', '上架', '5vl0', NULL, '101212', NULL, '/manage/line/shelves', '2', '40', b'1', NULL, '1',
        '2022-11-26 17:38:46', '2023-09-27 15:02:29');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121215', '下架', 'Zvl0', NULL, '101212', NULL, '/manage/line/unShelves', '2', '50', b'1', NULL, '1',
        '2022-11-26 17:38:46', '2023-09-27 15:02:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121217', '强制下架', 'kvl0', NULL, '101212', NULL, '/manage/line/platformUnShelves', '2', '70', b'1', NULL,
        '2', '2022-11-26 17:38:46', '2023-09-27 15:02:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121218', '删除', 'lvl0', NULL, '101212', NULL, '/manage/line/delete', '2', '80', b'1', NULL, '1',
        '2022-11-26 17:38:46', '2023-09-27 15:02:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121219', '价格详情', 'Ovl0', NULL, '101212', NULL, '/manage/line/config/month', '2', '100', b'1', NULL, '3',
        '2022-12-27 11:26:16', '2022-12-27 11:28:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121220', '价格设置(月)', 'cvl0', NULL, '101212', NULL, '/manage/line/config/setup', '2', '110', b'1', NULL,
        '1', '2022-12-27 11:26:16', '2023-09-27 15:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121221', '价格设置(天)', 'Dvl0', NULL, '101212', NULL, '/manage/line/config/setDay', '2', '120', b'1', NULL,
        '1', '2022-12-27 11:26:16', '2023-09-27 15:02:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121222', '导出', 'nvl0', NULL, '101212', NULL, '/manage/line/export', '2', '140', b'1', NULL, '3',
        '2023-12-09 16:19:53', '2023-12-11 15:30:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101213', '民宿管理', 'OxU', 'School', '1012', '/product/homestay', NULL, '1', '30', b'1', NULL, '3',
        '2022-11-26 18:00:04', '2024-05-28 18:13:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121310', '列表', 'LIO0', NULL, '101213', NULL, '/manage/homestay/listPage', '2', '1', b'1', NULL, '3',
        '2022-11-26 18:01:13', '2022-11-26 18:01:21');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121311', '新增', 'TIO0', NULL, '101213', NULL, '/manage/homestay/create', '2', '10', b'1', NULL, '2',
        '2022-11-26 18:01:13', '2024-02-21 10:28:15');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121312', '编辑', 'oIO0', NULL, '101213', NULL, '/manage/homestay/update,/manage/homestay/select', '2', '20',
        b'1', NULL, '1', '2022-11-26 18:01:13', '2024-03-14 14:48:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121313', '上架', 'vIO0', NULL, '101213', NULL, '/manage/homestay/shelves', '2', '40', b'1', NULL, '1',
        '2022-11-26 18:01:13', '2023-09-27 15:03:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121314', '下架', 'IFO0', NULL, '101213', NULL, '/manage/homestay/unShelves', '2', '50', b'1', NULL, '1',
        '2022-11-26 18:01:13', '2023-09-27 15:03:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121316', '强制下架', 'bFO0', NULL, '101213', NULL, '/manage/homestay/platformUnShelves', '2', '70', b'1',
        NULL, '2', '2022-11-26 18:01:13', '2023-09-27 15:03:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121317', '删除', 'HFO0', NULL, '101213', NULL, '/manage/homestay/delete', '2', '80', b'1', NULL, '2',
        '2022-11-26 18:01:13', '2024-02-22 10:49:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121318', '导出', '4FO0', NULL, '101213', NULL, '/manage/homestay/export', '2', '90', b'1', NULL, '3',
        '2023-12-09 16:11:40', '2023-12-11 15:30:48');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121319', '详情', 'SFO0', NULL, '101213', NULL, '/manage/homestay/select', '2', '100', b'1', NULL, '3',
        '2024-03-14 14:48:12', '2024-03-14 14:48:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101214', '民宿房型', 'cxU', 'Reading', '1012', '/product/room', NULL, '1', '40', b'1', NULL, '3',
        '2022-12-16 14:35:30', '2024-05-28 18:13:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121410', '列表', 'ubO0', NULL, '101214', NULL, '/manage/homestay/room/listPage,/manage/homestay/list', '2',
        '10', b'1', NULL, '3', '2022-12-14 11:32:40', '2024-06-11 09:35:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121411', '新增', 'NbO0', NULL, '101214', NULL, '/manage/homestay/room/create,/manage/homestay/list', '2',
        '20', b'1', NULL, '1', '2022-12-14 11:39:55', '2024-06-11 09:35:52');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121412', '编辑', 'GbO0', NULL, '101214', NULL,
        '/manage/homestay/room/update,/manage/homestay/room/select,/manage/homestay/list', '2', '30', b'1', NULL, '1',
        '2022-12-14 11:40:19', '2024-06-11 09:35:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121413', '详情', 'AbO0', NULL, '101214', NULL, '/manage/homestay/room/select,/manage/homestay/list', '2',
        '40', b'1', NULL, '3', '2022-12-14 11:41:07', '2024-06-11 09:35:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121414', '上架', 'rbO0', NULL, '101214', NULL, '/manage/homestay/room/shelves', '2', '50', b'1', NULL, '1',
        '2022-12-14 11:42:45', '2023-09-27 15:20:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121415', '下架', 'RbO0', NULL, '101214', NULL, '/manage/homestay/room/unShelves', '2', '60', b'1', NULL, '1',
        '2022-12-14 11:43:20', '2023-09-27 15:20:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121417', '强制下架', 'abO0', NULL, '101214', NULL, '/manage/homestay/room/platformUnShelves', '2', '80',
        b'1', NULL, '2', '2022-12-14 11:45:17', '2023-09-27 15:20:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121418', '推荐', '0bO0', NULL, '101214', NULL, '/manage/homestay/room/recommend', '2', '90', b'1', NULL, '2',
        '2023-01-13 17:27:33', '2024-02-19 15:57:52');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121419', '删除', 'dbO0', NULL, '101214', NULL, '/manage/homestay/room/delete', '2', '100', b'1', NULL, '1',
        '2023-01-29 14:51:41', '2023-09-27 15:20:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121420', '批量设置房态', 'pbO0', NULL, '101214', NULL, '/manage/homestay/room/config/setup', '2', '110',
        b'1', NULL, '1', '2023-10-08 16:54:53', '2023-10-08 16:58:32');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121421', '房态查询', 'hbO0', NULL, '101214', NULL, '/manage/homestay/room/config/month', '2', '120', b'1',
        NULL, '3', '2023-10-08 16:55:49', '2023-10-08 16:58:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121422', '设置房态', 'XbO0', NULL, '101214', NULL, '/manage/homestay/room/config/update', '2', '130', b'1',
        NULL, '1', '2023-10-08 16:56:08', '2023-10-08 16:58:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121423', '导出', 'ibO0', NULL, '101214', NULL, '/manage/homestay/room/export', '2', '140', b'1', NULL, '3',
        '2023-12-09 16:15:11', '2023-12-11 15:30:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101215', '零售店铺', 'DxU', 'Money', '1012', '/product/store', NULL, '1', '50', b'1', NULL, '3',
        '2022-12-14 18:45:35', '2024-05-28 18:14:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121510', '列表', 'f4O0', NULL, '101215', NULL, '/manage/item/store/listPage', '2', '10', b'1', NULL, '3',
        '2022-12-14 19:25:22', '2023-09-27 15:15:29');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121511', '新增', 'e4O0', NULL, '101215', NULL,
        '/manage/item/store/create,/manage/merchant/list,/manage/merchant/address/list', '2', '20', b'1', NULL, '2',
        '2022-12-16 11:19:46', '2024-06-21 15:12:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121512', '编辑', 'E4O0', NULL, '101215', NULL,
        '/manage/item/store/update,/manage/item/store/select,/manage/merchant/address/list,/manage/merchant/list', '2',
        '30', b'1', NULL, '1', '2022-12-16 11:20:24', '2024-06-11 09:37:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121513', '上架', 'w4O0', NULL, '101215', NULL, '/manage/item/store/shelves', '2', '40', b'1', NULL, '1',
        '2022-12-16 11:38:52', '2023-09-27 15:21:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121514', '下架', '14O0', NULL, '101215', NULL, '/manage/item/store/unShelves', '2', '50', b'1', NULL, '1',
        '2022-12-16 11:39:14', '2023-09-27 15:21:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121516', '强制下架', 'z4O0', NULL, '101215', NULL, '/manage/item/store/platformUnShelves', '2', '70', b'1',
        NULL, '2', '2022-12-16 11:40:45', '2023-09-27 15:21:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121517', '推荐', 'Q4O0', NULL, '101215', NULL, '/manage/item/store/recommend', '2', '80', b'1', NULL, '2',
        '2023-01-29 14:55:29', '2024-01-31 17:32:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121518', '删除', 'V4O0', NULL, '101215', NULL, '/manage/item/store/delete', '2', '90', b'1', NULL, '2',
        '2024-02-22 10:48:11', '2024-02-22 10:48:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121519', '导出', '64O0', NULL, '101215', NULL, '/manage/item/store/export', '2', '100', b'1', NULL, '3',
        '2024-02-29 13:22:20', '2024-08-01 15:07:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121520', '详情', 'x4O0', NULL, '101215', NULL, '/manage/item/store/select', '2', '110', b'1', NULL, '3',
        '2024-03-14 13:33:09', '2024-03-14 13:33:30');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101216', '零售商品', 'nxU', 'Coffee', '1012', '/product/item', NULL, '1', '60', b'1', NULL, '3',
        '2022-12-14 11:31:13', '2024-05-28 18:15:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121611', '列表', '8SO0', NULL, '101216', NULL, '/manage/item/listPage,/manage/item/store/list', '2', '20',
        b'1', NULL, '3', '2022-12-16 14:35:30', '2024-06-11 09:40:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121612', '新增', 'jSO0', NULL, '101216', NULL, '/manage/item/create,/manage/item/store/list', '2', '30',
        b'1', NULL, '1', '2022-12-16 14:35:30', '2024-06-11 09:40:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121613', '编辑', 'gSO0', NULL, '101216', NULL,
        '/manage/item/update,/manage/item/select,/manage/item/store/list', '2', '40', b'1', NULL, '1',
        '2022-12-16 14:35:30', '2024-06-11 09:40:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121614', '详情', '2SO0', NULL, '101216', NULL, '/manage/item/select,/manage/item/store/list', '2', '50',
        b'1', NULL, '3', '2022-12-16 14:35:31', '2024-06-11 09:40:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121615', '上架', 'mSO0', NULL, '101216', NULL, '/manage/item/shelves', '2', '60', b'1', NULL, '1',
        '2022-12-16 14:35:31', '2023-09-27 15:22:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121616', '下架', 'BSO0', NULL, '101216', NULL, '/manage/item/unShelves', '2', '70', b'1', NULL, '1',
        '2022-12-16 14:35:31', '2023-09-27 15:22:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121618', '强制下架', 'WSO0', NULL, '101216', NULL, '/manage/item/platformUnShelves', '2', '90', b'1', NULL,
        '2', '2022-12-16 15:26:51', '2023-09-27 15:22:29');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121619', '推荐', '3SO0', NULL, '101216', NULL, '/manage/item/recommend', '2', '100', b'1', NULL, '2',
        '2022-12-16 15:26:51', '2023-09-27 15:22:32');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121620', '排序', 'LSO0', NULL, '101216', NULL, '/manage/item/sort', '2', '110', b'1', NULL, '1',
        '2022-12-16 15:26:51', '2024-01-31 17:32:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121621', '删除', 'TSO0', NULL, '101216', NULL, '/manage/item/delete', '2', '120', b'1', NULL, '1',
        '2023-09-28 17:01:44', '2023-09-28 17:03:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121622', '导出', 'oSO0', NULL, '101216', NULL, '/manage/item/export', '2', '140', b'1', NULL, '3',
        '2023-12-09 16:16:33', '2023-12-11 15:30:49');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121623', '生成链接', 'vSO0', NULL, '101216', NULL, '/manage/wechat/shortUrl,/manage/wechat/linkUrl', '2',
        '150', b'1', NULL, '3', '2024-03-26 14:32:06', '2024-03-26 14:32:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121624', '加库存', 'IyO0', NULL, '101216', NULL, '/manage/item/getSku,/manage/item/addStock', '2', '160',
        b'1', NULL, '1', '2024-09-10 14:39:09', '2024-09-10 14:39:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101217', '餐饮商家', 'YxU', 'KnifeFork', '1012', '/product/restaurant', NULL, '1', '80', b'1', NULL, '3',
        '2022-12-16 16:03:39', '2024-05-28 18:15:59');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121710', '列表', '5MO0', NULL, '101217', NULL, '/manage/restaurant/listPage', '2', '10', b'1', NULL, '3',
        '2022-12-16 16:03:39', '2023-09-26 14:28:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121711', '新增', 'ZMO0', NULL, '101217', NULL, '/manage/restaurant/create', '2', '20', b'1', NULL, '2',
        '2022-12-16 16:03:39', '2024-02-22 10:30:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121712', '编辑', 'UMO0', NULL, '101217', NULL, '/manage/restaurant/update,/manage/restaurant/select', '2',
        '30', b'1', NULL, '1', '2022-12-16 16:03:39', '2023-12-22 14:53:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121713', '上架', 'kMO0', NULL, '101217', NULL, '/manage/restaurant/shelves', '2', '40', b'1', NULL, '1',
        '2022-12-16 16:03:39', '2023-09-27 15:22:58');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121714', '下架', 'lMO0', NULL, '101217', NULL, '/manage/restaurant/unShelves', '2', '50', b'1', NULL, '1',
        '2022-12-16 16:03:39', '2023-09-27 15:23:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121716', '强制下架', 'cMO0', NULL, '101217', NULL, '/manage/restaurant/platformUnShelves', '2', '70', b'1',
        NULL, '2', '2022-12-16 16:03:39', '2023-09-27 15:23:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121717', '详情', 'DMO0', NULL, '101217', NULL, '/manage/restaurant/select', '2', '80', b'1', NULL, '3',
        '2022-12-16 16:03:39', '2023-09-26 14:28:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121718', '导出', 'nMO0', NULL, '101217', NULL, '/manage/restaurant/export', '2', '140', b'1', NULL, '3',
        '2023-12-09 16:23:33', '2023-12-11 15:30:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121719', '删除', 'YMO0', NULL, '101217', NULL, '/manage/restaurant/delete', '2', '150', b'1',
        '管理员删除时也需要慎重', '2', '2024-02-22 10:34:07', '2024-02-22 10:35:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101218', '餐饮券', 'uxU', 'Bowl', '1012', '/product/voucher', NULL, '1', '90', b'1', NULL, '3',
        '2022-12-16 16:07:56', '2024-05-28 18:16:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121810', '列表', 'IPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/listPage,/manage/restaurant/list',
        '2', '10', b'1', NULL, '3', '2022-12-16 16:07:56', '2024-06-12 16:47:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121811', '新增', 'FPO0', NULL, '101218', NULL,
        '/manage/restaurant/voucher/create,/manage/restaurant/list,/manage/voucher/tag/list', '2', '20', b'1', NULL,
        '1', '2022-12-16 16:07:56', '2024-10-09 14:55:19');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121812', '编辑', 'bPO0', NULL, '101218', NULL,
        '/manage/restaurant/voucher/update,/manage/restaurant/voucher/select,/manage/restaurant/list,/manage/voucher/tag/list',
        '2', '30', b'1', NULL, '1', '2022-12-16 16:07:56', '2024-10-09 14:55:21');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121813', '上架', 'HPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/shelves', '2', '40', b'1', NULL,
        '1', '2022-12-16 16:07:56', '2023-09-27 15:26:01');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121814', '下架', '4PO0', NULL, '101218', NULL, '/manage/restaurant/voucher/unShelves', '2', '50', b'1', NULL,
        '1', '2022-12-16 16:07:56', '2023-09-27 15:26:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121816', '强制下架', 'yPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/platformUnShelves', '2', '70',
        b'1', NULL, '2', '2022-12-16 16:07:56', '2023-09-27 15:26:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121817', '详情', 'MPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/select,/manage/restaurant/list',
        '2', '80', b'1', NULL, '3', '2022-12-16 16:07:56', '2024-06-12 16:47:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121818', '删除', 'sPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/delete', '2', '90', b'1', NULL,
        '1', '2022-12-16 16:07:56', '2023-09-27 15:26:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121819', '导出', 'PPO0', NULL, '101218', NULL, '/manage/restaurant/voucher/export', '2', '140', b'1', NULL,
        '3', '2023-12-09 16:24:45', '2023-12-11 15:30:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101219', '快递模板', 'NxU', 'Notebook', '1012', '/product/express', NULL, '1', '65', b'1', NULL, '1',
        '2023-09-27 11:08:55', '2024-06-11 10:27:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121910', '列表', 'rfO0', NULL, '101219', NULL, '/manage/express/template/list', '2', '10', b'1', NULL, '1',
        '2023-09-27 11:08:55', '2024-06-11 10:27:49');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121911', '新增', 'RfO0', NULL, '101219', NULL, '/manage/express/template/create', '2', '20', b'1', NULL, '1',
        '2023-09-27 11:08:55', '2023-12-19 14:01:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121912', '编辑', '9fO0', NULL, '101219', NULL,
        '/manage/express/template/update,/manage/express/template/select', '2', '30', b'1', NULL, '1',
        '2023-09-27 11:08:55', '2024-06-12 09:22:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10121913', '删除', 'afO0', NULL, '101219', NULL, '/manage/express/template/delete', '2', '40', b'1', NULL, '1',
        '2023-09-27 11:08:56', '2023-12-19 14:01:30');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101220', '旅行社', 'GxU', 'Van', '1012', '/product/travel', NULL, '1', '19', b'1', NULL, '3',
        '2023-09-27 11:29:35', '2024-06-06 09:36:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122010', '列表', '1EO0', NULL, '101220', NULL, '/manage/travel/listPage', '2', '10', b'1', NULL, '3',
        '2023-09-27 11:29:35', '2023-09-27 11:30:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122011', '新增', 'CEO0', NULL, '101220', NULL, '/manage/travel/create', '2', '20', b'1', NULL, '2',
        '2023-09-27 11:29:35', '2024-03-14 13:53:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122012', '编辑', 'zEO0', NULL, '101220', NULL, '/manage/travel/update', '2', '30', b'1', NULL, '1',
        '2023-09-27 11:29:35', '2024-06-06 11:16:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122013', '上架', 'QEO0', NULL, '101220', NULL, '/manage/travel/shelves', '2', '40', b'1', NULL, '1',
        '2023-09-27 11:29:35', '2023-09-27 15:27:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122014', '下架', 'VEO0', NULL, '101220', NULL, '/manage/travel/unShelves', '2', '50', b'1', NULL, '1',
        '2023-09-27 11:29:35', '2023-09-27 15:27:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122016', '平台下架', 'xEO0', NULL, '101220', NULL, '/manage/travel/platformUnShelves', '2', '70', b'1', NULL,
        '2', '2023-09-27 11:29:35', '2023-09-27 15:27:28');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122017', '详情', 'tEO0', NULL, '101220', NULL, '/manage/travel/select', '2', '80', b'1', NULL, '3',
        '2023-09-27 11:29:35', '2023-09-27 11:34:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122018', '删除', 'JEO0', NULL, '101220', NULL, '/manage/travel/delete', '2', '90', b'1', NULL, '2',
        '2023-09-27 11:29:35', '2024-02-22 10:49:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122019', '导出', 'KEO0', NULL, '101220', NULL, '/manage/travel/export', '2', '100', b'1', NULL, '3',
        '2024-08-01 17:06:11', '2024-08-01 17:06:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101221', '场馆管理', 'AxU', 'Football', '1012', '/product/venue', NULL, '1', '100', b'1', NULL, '3',
        '2024-02-19 14:13:01', '2024-05-28 18:16:22');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122110', '列表', '2wO0', NULL, '101221', NULL, '/manage/venue/listPage', '2', '10', b'1', NULL, '3',
        '2024-02-19 14:13:01', '2024-02-19 14:15:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122111', '新增', 'mwO0', NULL, '101221', NULL, '/manage/venue/create', '2', '20', b'1', NULL, '1',
        '2024-02-19 14:13:01', '2024-02-19 14:17:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122112', '编辑', 'BwO0', NULL, '101221', NULL, '/manage/venue/update', '2', '30', b'1', NULL, '1',
        '2024-02-19 14:13:01', '2024-02-19 14:17:20');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122113', '上架', '7wO0', NULL, '101221', NULL, '/manage/venue/shelves', '2', '40', b'1', NULL, '1',
        '2024-02-19 14:13:01', '2024-02-19 14:17:22');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122114', '下架', 'WwO0', NULL, '101221', NULL, '/manage/venue/unShelves', '2', '50', b'1', NULL, '1',
        '2024-02-19 14:13:01', '2024-02-19 14:17:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122115', '平台下架', '3wO0', NULL, '101221', NULL, '/manage/venue/platformUnShelves', '2', '60', b'1', NULL,
        '2', '2024-02-19 14:13:01', '2024-02-19 14:17:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122116', '详情', 'LwO0', NULL, '101221', NULL, '/manage/venue/select', '2', '70', b'1', NULL, '3',
        '2024-02-19 14:13:01', '2024-02-19 14:16:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122117', '删除', 'TwO0', NULL, '101221', NULL, '/manage/venue/delete', '2', '80', b'1', NULL, '1',
        '2024-02-19 14:13:01', '2024-02-19 14:17:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122118', '导出', 'owO0', NULL, '101221', NULL, '/manage/venue/export', '2', '90', b'1', NULL, '3',
        '2024-02-19 14:13:01', '2024-02-19 14:16:21');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101222', '场地管理', 'rxU', 'Brush', '1012', '/product/site', NULL, '1', '110', b'1', NULL, '3',
        '2024-02-19 14:19:15', '2024-05-28 18:16:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122210', '列表', 'lCO0', NULL, '101222', NULL, '/manage/venue/site/listPage,/manage/venue/list', '2', '10',
        b'1', NULL, '3', '2024-02-19 14:19:15', '2024-06-14 10:38:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122211', '新增', 'OCO0', NULL, '101222', NULL, '/manage/venue/site/create,/manage/venue/list', '2', '20',
        b'1', NULL, '1', '2024-02-19 14:19:15', '2024-06-14 10:38:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122212', '编辑', 'cCO0', NULL, '101222', NULL,
        '/manage/venue/site/update,/manage/venue/site/select,/manage/venue/site/priceList,/manage/venue/list', '2',
        '30', b'1', NULL, '1', '2024-02-19 14:19:15', '2024-06-14 10:38:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122213', '上架', 'DCO0', NULL, '101222', NULL, '/manage/venue/site/shelves', '2', '40', b'1', NULL, '1',
        '2024-02-19 14:19:15', '2024-02-19 14:21:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122214', '下架', 'nCO0', NULL, '101222', NULL, '/manage/venue/site/unShelves', '2', '50', b'1', NULL, '1',
        '2024-02-19 14:19:15', '2024-02-19 14:21:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122215', '平台下架', 'YCO0', NULL, '101222', NULL, '/manage/venue/site/platformUnShelves', '2', '60', b'1',
        NULL, '2', '2024-02-19 14:19:15', '2024-02-19 14:21:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122216', '排序', 'uCO0', NULL, '101222', NULL, '/manage/venue/site/sort', '2', '70', b'1', NULL, '1',
        '2024-02-19 14:19:15', '2024-02-19 14:22:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122217', '详情', 'NCO0', NULL, '101222', NULL, '/manage/venue/site/select,/manage/venue/site/priceList', '2',
        '80', b'1', NULL, '3', '2024-02-19 14:19:15', '2024-02-23 15:28:19');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122218', '删除', 'GCO0', NULL, '101222', NULL, '/manage/venue/site/delete', '2', '90', b'1', NULL, '1',
        '2024-02-19 14:19:15', '2024-02-19 14:22:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122219', '设置价格', 'ACO0', NULL, '101222', NULL, '/manage/venue/site/price/setup', '2', '100', b'1', NULL,
        '1', '2024-02-19 14:24:38', '2024-02-19 14:25:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122220', '更新价格', 'rCO0', NULL, '101222', NULL, '/manage/venue/site/price/update', '2', '110', b'1', NULL,
        '1', '2024-02-19 14:24:38', '2024-02-19 14:25:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122221', '删除价格', 'RCO0', NULL, '101222', NULL, '/manage/venue/site/price/delete', '2', '120', b'1', NULL,
        '1', '2024-02-19 14:24:38', '2024-02-19 14:25:49');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101223', '收货地址', 'RxU', 'DocumentAdd', '1012', '/product/address', NULL, '1', '66', b'1', NULL, '1',
        '2024-03-20 16:24:17', '2024-05-28 18:15:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122310', '列表', '4QO0', NULL, '101223', NULL, '/manage/merchant/address/listPage', '2', '10', b'1', NULL,
        '1', '2024-03-20 16:24:11', '2024-03-20 16:29:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122311', '新增', 'SQO0', NULL, '101223', NULL, '/manage/merchant/address/create', '2', '20', b'1', NULL, '1',
        '2024-03-20 16:24:11', '2024-03-20 16:29:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122312', '编辑', 'yQO0', NULL, '101223', NULL,
        '/manage/merchant/address/update,/manage/merchant/address/select', '2', '30', b'1', NULL, '1',
        '2024-03-20 16:24:12', '2024-06-12 11:40:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122313', '删除', 'MQO0', NULL, '101223', NULL, '/manage/merchant/address/delete', '2', '40', b'1', NULL, '1',
        '2024-03-20 16:24:12', '2024-03-20 16:29:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101224', '餐饮券标签', '9xU', 'PriceTag', '1012', '/product/voucherTag', NULL, '1', '95', b'1', NULL, '1',
        '2024-10-09 11:41:06', '2024-10-09 11:44:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122410', '列表', '0VO0', NULL, '101224', NULL, '/manage/voucher/tag/listPage,/manage/restaurant/list', '2',
        '10', b'1', NULL, '1', '2024-10-09 13:16:57', '2024-10-09 14:25:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122411', '新增', 'dVO0', NULL, '101224', NULL, '/manage/voucher/tag/create,/manage/restaurant/list', '2',
        '20', b'1', NULL, '1', '2024-10-09 13:16:57', '2024-10-09 14:25:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122412', '编辑', 'pVO0', NULL, '101224', NULL, '/manage/voucher/tag/update,/manage/restaurant/list', '2',
        '30', b'1', NULL, '1', '2024-10-09 13:16:57', '2024-10-09 14:25:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122413', '排序', 'hVO0', NULL, '101224', NULL, '/manage/voucher/tag/sort', '2', '40', b'1', NULL, '1',
        '2024-10-09 13:16:58', '2024-10-09 14:23:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10122414', '删除', 'XVO0', NULL, '101224', NULL, '/manage/voucher/tag/delete', '2', '50', b'1', NULL, '1',
        '2024-10-09 13:17:44', '2024-10-09 14:23:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1013', '订单管理', 'tz', 'Collection', '0', '/order', NULL, '1', '40', b'1', '商户及平台', '3',
        '2023-09-26 15:04:49', '2024-05-22 14:04:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101310', '门票订单', 'bJU', 'Tickets', '1013', '/order/ticket', NULL, '1', '10', b'1', NULL, '3',
        '2023-09-26 15:08:11', '2024-06-18 10:26:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131010', '列表', '5YD0', NULL, '101310', NULL, '/manage/ticket/order/listPage', '2', '10', b'1', NULL, '3',
        '2023-09-26 15:08:11', '2023-11-22 09:56:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131011', '线下退款', 'ZYD0', NULL, '101310', NULL, '/manage/ticket/order/offlineRefund', '2', '20', b'1',
        NULL, '1', '2023-09-26 15:08:11', '2023-11-22 09:56:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131013', '详情', 'kYD0', NULL, '101310', NULL, '/manage/ticket/order/detail', '2', '40', b'1', NULL, '3',
        '2023-09-27 15:29:37', '2023-11-22 09:56:59');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131014', '导出', 'lYD0', NULL, '101310', NULL, '/manage/ticket/order/export', '2', '140', b'1', NULL, '3',
        '2023-12-09 16:25:54', '2023-12-11 15:30:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131015', '退款', 'OYD0', NULL, '101310', NULL, '/manage/ticket/order/refund', '2', '30', b'1',
        '商户主动退款', '1', '2024-10-14 13:54:07', '2024-10-14 13:55:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101314', '零售订单', 'yJU', 'TrendCharts', '1013', '/order/item', NULL, '1', '11', b'1', NULL, '3',
        '2023-11-22 10:46:53', '2024-06-18 10:26:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131410', '列表', '2RD0', NULL, '101314', NULL, '/manage/item/order/listPage', '2', '10', b'1', NULL, '3',
        '2023-11-22 10:47:07', '2023-11-22 11:13:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131411', '详情', 'mRD0', NULL, '101314', NULL, '/manage/item/order/detail', '2', '20', b'1', '改价记录', '3',
        '2023-11-22 10:47:08', '2024-04-01 15:40:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131412', '发货', 'BRD0', NULL, '101314', NULL,
        '/manage/item/order/sipping,/manage/item/order/sipping/expressList', '2', '30', b'1', NULL, '1',
        '2023-11-22 10:47:11', '2024-01-10 17:25:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131414', '退款', 'WRD0', NULL, '101314', NULL, '/manage/item/order/refund', '2', '50', b'1',
        '商户主动发起退款', '3', '2023-11-22 10:48:16', '2024-02-19 16:03:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131415', '修改物流', '7RD0', NULL, '101314', NULL, '/manage/item/order/updateExpress', '2', '60', b'1',
        '更新物流单号', '1', '2023-11-22 10:48:16', '2023-12-29 10:11:38');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131416', '导出', 'LRD0', NULL, '101314', NULL, '/manage/item/order/export', '2', '70', b'1', NULL, '3',
        '2023-12-19 15:58:15', '2023-12-19 16:00:22');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131417', '改价', 'TRD0', NULL, '101314', NULL, '/manage/order/adjust/item', '2', '80', b'1', NULL, '1',
        '2024-03-25 17:16:01', '2024-03-25 17:19:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101315', '民宿订单', 'MJU', 'OfficeBuilding', '1013', '/order/homestay', NULL, '1', '12', b'1', NULL, '3',
        '2023-11-22 11:19:29', '2024-06-18 10:26:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131510', '列表', 'laD0', NULL, '101315', NULL, '/manage/homestay/order/listPage', '2', '10', b'1', NULL, '3',
        '2023-11-22 11:19:29', '2023-11-23 16:44:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131511', '详情', 'OaD0', NULL, '101315', NULL, '/manage/homestay/order/detail', '2', '20', b'1', NULL, '3',
        '2023-11-22 11:19:29', '2023-11-23 16:45:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131512', '房型确认', 'caD0', NULL, '101315', NULL, '/manage/homestay/order/confirm', '2', '30', b'1',
        '确认是否有无房型', '1', '2023-11-22 11:19:29', '2023-11-23 16:45:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131513', '导出', 'DaD0', NULL, '101315', NULL, '/manage/homestay/order/export', '2', '40', b'1', NULL, '3',
        '2023-11-22 11:19:29', '2023-12-09 16:14:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131514', '退款', 'naD0', NULL, '101315', NULL, '/manage/homestay/order/refund', '2', '35', b'1', NULL, '1',
        '2023-11-22 11:23:02', '2024-10-14 14:01:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101316', '线路订单', 'sJU', 'Histogram', '1013', '/order/line', NULL, '1', '13', b'1', NULL, '3',
        '2023-11-22 11:24:07', '2024-06-18 10:26:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131610', '列表', '4dD0', NULL, '101316', NULL, '/manage/line/order/listPage', '2', '10', b'1', NULL, '3',
        '2023-11-22 11:24:07', '2023-11-22 11:24:46');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131611', '详情', 'SdD0', NULL, '101316', NULL, '/manage/line/order/detail', '2', '20', b'1', NULL, '3',
        '2023-11-22 11:24:07', '2023-11-22 11:24:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131612', '导出', 'ydD0', NULL, '101316', NULL, '/manage/line/order/export', '2', '30', b'1', NULL, '3',
        '2023-11-22 11:24:07', '2023-12-09 16:21:01');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131613', '退款', 'MdD0', NULL, '101316', NULL, '/manage/line/order/refund', '2', '25', b'1', NULL, '1',
        '2024-10-14 14:05:23', '2024-10-14 14:05:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101317', '餐饮订单', 'PJU', 'DishDot', '1013', '/order/voucher', NULL, '1', '14', b'1', NULL, '3',
        '2023-11-22 11:40:25', '2024-06-18 10:26:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131710', '列表', '0pD0', NULL, '101317', NULL, '/manage/voucher/order/listPage', '2', '10', b'1', NULL, '3',
        '2023-11-22 11:40:25', '2023-12-06 15:03:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131711', '详情', 'dpD0', NULL, '101317', NULL, '/manage/voucher/order/detail', '2', '20', b'1', NULL, '3',
        '2023-11-22 11:40:26', '2023-12-06 15:03:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131712', '导出', 'ppD0', NULL, '101317', NULL, '/manage/voucher/order/export', '2', '30', b'1', NULL, '3',
        '2023-11-22 11:40:26', '2023-12-09 16:27:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10131713', '退款', 'hpD0', NULL, '101317', NULL, '/manage/voucher/order/refund', '2', '25', b'1', NULL, '1',
        '2024-10-14 14:07:54', '2024-10-14 14:08:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101320', '场馆订单', 'EJU', 'Wallet', '1013', '/order/venue', NULL, '1', '15', b'1', NULL, '3',
        '2024-02-19 15:14:47', '2024-06-18 10:26:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10132010', '列表', 'n8D0', NULL, '101320', NULL, '/manage/venue/order/listPage', '2', '10', b'1', NULL, '3',
        '2024-02-19 15:14:47', '2024-02-19 15:15:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10132011', '详情', 'Y8D0', NULL, '101320', NULL, '/manage/venue/order/detail', '2', '20', b'1', NULL, '3',
        '2024-02-19 15:14:47', '2024-02-19 15:15:41');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10132012', '导出', 'u8D0', NULL, '101320', NULL, '/manage/venue/order/export', '2', '30', b'1', NULL, '3',
        '2024-06-18 16:38:12', '2024-06-18 16:38:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10132013', '退款', 'N8D0', NULL, '101320', NULL, '/manage/venue/order/refund', '2', '25', b'1', NULL, '1',
        '2024-10-14 14:10:10', '2024-10-14 14:10:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1014', '商户中心', 'Jz', 'User', '0', NULL, NULL, '1', '50', b'1', '商户专享', '3', '2023-09-27 10:54:29',
        '2024-05-22 10:26:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101410', '商户用户', '9KU', 'UserFilled', '1014', '/merchant/user', NULL, '1', '30', b'1', NULL, '1',
        '2023-09-27 10:56:15', '2024-09-14 14:47:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141010', '列表', '0su0', NULL, '101410', NULL, '/manage/merchant/user/listPage', '2', '10', b'1', NULL, '1',
        '2023-09-27 10:56:15', '2023-09-27 15:34:59');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141011', '新增', 'dsu0', NULL, '101410', NULL, '/manage/merchant/user/create', '2', '20', b'1', NULL, '1',
        '2023-09-27 10:56:15', '2023-09-27 15:34:59');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141012', '编辑', 'psu0', NULL, '101410', NULL, '/manage/merchant/user/update,/manage/merchant/user/roleList',
        '2', '30', b'1', NULL, '1', '2023-09-27 10:56:15', '2024-07-31 11:36:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141013', '锁定', 'hsu0', NULL, '101410', NULL, '/manage/merchant/user/lock', '2', '40', b'1', NULL, '1',
        '2023-09-27 10:56:15', '2023-09-27 15:35:01');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141014', '解锁', 'Xsu0', NULL, '101410', NULL, '/manage/merchant/user/unlock', '2', '50', b'1', NULL, '1',
        '2023-09-27 10:56:15', '2023-09-27 15:35:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141015', '删除', 'isu0', NULL, '101410', NULL, '/manage/merchant/user/delete', '2', '60', b'1', NULL, '1',
        '2023-09-27 10:56:16', '2023-09-27 15:35:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101412', '商户角色', '0KU', 'Avatar', '1014', '/merchant/role', NULL, '1', '20', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-09-14 14:47:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141210', '列表', 'Weu0', NULL, '101412', NULL, '/manage/merchant/role/listPage', '2', '10', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-01-15 16:33:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141211', '新增', '3eu0', NULL, '101412', NULL, '/manage/merchant/role/create', '2', '20', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-01-15 16:33:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141212', '编辑', 'Leu0', NULL, '101412', NULL, '/manage/merchant/role/update', '2', '30', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-01-15 16:33:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141213', '授权', 'Teu0', NULL, '101412', NULL,
        '/manage/merchant/role/auth,/manage/merchant/role/menu,/manage/menu/merchantList', '2', '40', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-07-31 11:06:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141214', '删除', 'oeu0', NULL, '101412', NULL, '/manage/merchant/role/delete', '2', '50', b'1', NULL, '1',
        '2024-01-15 16:31:12', '2024-01-15 16:33:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101413', '商户信息', 'dKU', 'InfoFilled', '1014', '/merchant/detail', NULL, '1', '10', b'1', NULL, '1',
        '2024-01-16 14:00:14', '2024-09-14 14:46:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141310', '详情', 'nwu0', NULL, '101413', NULL, '/manage/merchant/center/detail', '2', '10', b'1', NULL, '1',
        '2024-01-16 14:00:14', '2024-01-16 14:02:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141311', '解绑', 'Ywu0', NULL, '101413', NULL,
        '/manage/merchant/center/sendSms,/manage/merchant/center/unbind', '2', '20', b'1', NULL, '1',
        '2024-01-16 14:00:14', '2024-01-16 14:02:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141312', '绑定手机号', 'uwu0', NULL, '101413', NULL, '/manage/merchant/center/generate', '2', '30', b'1',
        NULL, '1', '2024-01-16 14:00:14', '2024-06-20 16:13:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101414', '资金变动记录', 'pKU', 'TakeawayBox', '1014', '/merchant/accountLog', NULL, '1', '50', b'1', NULL,
        '3', '2024-01-16 17:34:49', '2024-06-20 09:19:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141410', '列表', 'sCu0', NULL, '101414', NULL, '/manage/merchant/account/log/listPage', '2', '10', b'1',
        NULL, '3', '2024-01-16 17:34:49', '2024-02-19 15:01:25');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141411', '导出', 'PCu0', NULL, '101414', NULL, '/manage/merchant/account/log/export', '2', '20', b'1', NULL,
        '3', '2024-01-16 17:34:49', '2024-02-19 15:01:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101415', '资金账户', 'hKU', 'Wallet', '1014', '/merchant/account', NULL, '1', '45', b'1', NULL, '1',
        '2024-02-19 14:48:58', '2024-06-20 09:19:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141510', '详情', 'Xzu0', NULL, '101415', NULL, '/manage/merchant/account', '2', '10', b'1', NULL, '1',
        '2024-02-19 14:48:59', '2024-02-19 14:53:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141511', '提现', 'izu0', NULL, '101415', NULL, '/manage/merchant/withdraw/apply', '2', '20', b'1', NULL, '1',
        '2024-02-19 14:48:59', '2024-02-27 11:27:19');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101416', '积分账户', 'XKU', 'CreditCard', '1014', '/merchant/score', NULL, '1', '60', b'1', NULL, '1',
        '2024-02-19 14:57:48', '2024-06-20 16:53:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141610', '详情', 'JVu0', NULL, '101416', NULL, '/manage/merchant/score/account', '2', '10', b'1', NULL, '1',
        '2024-02-19 14:57:48', '2024-02-19 15:10:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141611', '充值', 'KVu0', NULL, '101416', NULL,
        '/manage/merchant/score/recharge/scan,/manage/merchant/score/recharge/balance,/manage/merchant/score/recharge/detail',
        '2', '20', b'1', NULL, '1', '2024-02-19 14:57:48', '2024-06-20 17:47:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141612', '提现', '5Vu0', NULL, '101416', NULL,
        '/manage/merchant/score/withdraw/apply,/manage/merchant/score/withdraw/detail', '2', '30', b'1', NULL, '1',
        '2024-02-19 14:57:48', '2024-06-21 10:39:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141613', '冻结明细', 'ZVu0', NULL, '101416', NULL,
        '/manage/merchant/account/freeze/listPage,/manage/merchant/account/freeze/export', '2', '40', b'1', NULL, '3',
        '2024-02-28 15:51:42', '2024-02-28 15:54:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101417', '积分变动记录', 'iKU', 'DocumentCopy', '1014', '/merchant/scoreLog', NULL, '1', '70', b'1', NULL, '3',
        '2024-02-19 15:08:10', '2024-06-21 11:24:20');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141710', '列表', 'o6u0', NULL, '101417', NULL, '/manage/score/account/log/listPage', '2', '10', b'1', NULL,
        '3', '2024-02-19 15:08:10', '2024-02-19 15:09:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141711', '导出', 'v6u0', NULL, '101417', NULL, '/manage/score/account/log/export', '2', '20', b'1', NULL,
        '3', '2024-02-19 15:08:10', '2024-02-19 15:09:14');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101418', '提现记录', 'qKU', 'Memo', '1014', '/merchant/withdraw', NULL, '1', '52', b'1', NULL, '3',
        '2024-02-27 11:30:23', '2024-06-20 15:53:29');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141810', '列表', 'Gtu0', NULL, '101418', NULL, '/manage/merchant/withdraw/listPage', '2', '10', b'1', NULL,
        '3', '2024-02-27 11:30:24', '2024-02-27 11:32:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141811', '导出', 'Atu0', NULL, '101418', NULL, '/manage/merchant/withdraw/export', '2', '20', b'1', NULL,
        '3', '2024-02-27 13:08:10', '2024-02-27 13:08:38');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101419', '冻结记录', '8KU', 'Document', '1014', '/merchant/freezeLog', NULL, '1', '53', b'1', NULL, '3',
        '2024-02-28 13:08:11', '2024-06-20 16:36:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141910', '列表', 'EKu0', NULL, '101419', NULL, '/manage/merchant/account/freeze/listPage', '2', '10', b'1',
        NULL, '3', '2024-02-28 13:08:11', '2024-02-28 13:08:49');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10141911', '导出', 'wKu0', NULL, '101419', NULL, '/manage/merchant/account/freeze/export', '2', '20', b'1',
        NULL, '3', '2024-02-28 13:08:11', '2024-02-28 13:08:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1015', '区域导览', 'Kz', 'Location', '0', NULL, NULL, '1', '60', b'1', '平台运营专享', '2',
        '2023-12-22 16:02:36', '2024-05-22 10:26:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101510', 'POI区域', 'zZU', 'Coordinate', '1015', '/poi/area', NULL, '1', '10', b'1', NULL, '2',
        '2023-12-22 16:02:36', '2024-06-25 13:40:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151010', '列表', 'ohG0', NULL, '101510', NULL, '/manage/poi/area/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-22 16:06:05', '2023-12-29 10:03:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151011', '新增', 'vhG0', NULL, '101510', NULL, '/manage/poi/area/create', '2', '20', b'1', NULL, '2',
        '2023-12-22 16:06:05', '2023-12-29 10:03:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151012', '编辑', 'IXG0', NULL, '101510', NULL, '/manage/poi/area/update', '2', '30', b'1', NULL, '2',
        '2023-12-22 16:06:05', '2024-06-26 09:19:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151013', '上下架', 'FXG0', NULL, '101510', NULL, '/manage/poi/area/updateState', '2', '40', b'1', NULL, '2',
        '2023-12-22 16:06:05', '2023-12-29 10:03:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151014', '删除', 'bXG0', NULL, '101510', NULL, '/manage/poi/area/delete', '2', '50', b'1', NULL, '2',
        '2023-12-22 16:06:05', '2023-12-29 10:03:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101511', 'POI类型', 'QZU', 'Grid', '1015', '/poi/type', NULL, '1', '20', b'1', NULL, '2',
        '2023-12-22 16:02:36', '2024-06-25 13:42:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151110', '列表', 'GiG0', NULL, '101511', NULL, '/manage/poi/type/listPage,/manage/poi/area/list', '2', '10',
        b'1', NULL, '2', '2023-12-22 16:19:00', '2024-06-26 09:19:53');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151111', '新增', 'AiG0', NULL, '101511', NULL, '/manage/poi/type/create,/manage/poi/area/list', '2', '20',
        b'1', NULL, '2', '2023-12-22 16:19:00', '2024-01-02 10:29:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151112', '编辑', 'riG0', NULL, '101511', NULL, '/manage/poi/type/update,/manage/poi/area/list', '2', '30',
        b'1', NULL, '2', '2023-12-22 16:19:00', '2024-01-02 10:29:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151113', '删除', 'RiG0', NULL, '101511', NULL, '/manage/poi/type/delete', '2', '40', b'1', NULL, '2',
        '2023-12-22 16:19:00', '2023-12-29 10:03:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101512', 'POI点位', 'VZU', 'MapLocation', '1015', '/poi/point', NULL, '1', '30', b'1', NULL, '2',
        '2023-12-22 16:02:36', '2024-06-25 13:42:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151210', '列表', 'E8G0', NULL, '101512', NULL, '/manage/poi/point/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-22 16:21:07', '2023-12-29 10:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151211', '新增', 'w8G0', NULL, '101512', NULL,
        '/manage/poi/point/create,/manage/poi/area/list,/manage/poi/type/list', '2', '20', b'1', NULL, '2',
        '2023-12-22 16:21:07', '2024-01-02 10:48:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151212', '编辑', '18G0', NULL, '101512', NULL,
        '/manage/poi/point/update,/manage/poi/point/select,/manage/poi/area/list,/manage/poi/type/list', '2', '30',
        b'1', NULL, '2', '2023-12-22 16:21:07', '2024-01-02 10:48:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151213', '详情', 'C8G0', NULL, '101512', NULL, '/manage/poi/point/select', '2', '40', b'1', NULL, '2',
        '2023-12-22 16:21:07', '2023-12-29 10:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151214', '删除', 'z8G0', NULL, '101512', NULL, '/manage/poi/point/delete', '2', '50', b'1', NULL, '2',
        '2023-12-22 16:21:08', '2023-12-29 10:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101513', 'POI线路', '6ZU', 'Guide', '1015', '/poi/line', NULL, '1', '40', b'1', NULL, '2',
        '2023-12-22 16:02:36', '2024-06-25 13:43:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151310', '列表', 'jjG0', NULL, '101513', NULL, '/manage/poi/line/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-22 16:23:50', '2023-12-29 10:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151311', '新增', 'gjG0', NULL, '101513', NULL, '/manage/poi/line/create,/manage/poi/area/list', '2', '20',
        b'1', NULL, '2', '2023-12-22 16:23:50', '2024-01-02 10:29:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151312', '编辑', '2jG0', NULL, '101513', NULL,
        '/manage/poi/line/update,/manage/poi/line/select,/manage/poi/area/list', '2', '30', b'1', NULL, '2',
        '2023-12-22 16:23:50', '2024-01-02 10:29:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151313', '详情', 'mjG0', NULL, '101513', NULL, '/manage/poi/line/select', '2', '40', b'1', NULL, '2',
        '2023-12-22 16:23:50', '2023-12-29 10:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151314', '设置线路', 'BjG0', NULL, '101513', NULL, '/manage/poi/line/bind,/manage/poi/line/bindDetail', '2',
        '50', b'1', NULL, '2', '2023-12-22 16:23:50', '2024-06-26 11:51:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151316', '删除', 'WjG0', NULL, '101513', NULL, '/manage/poi/line/delete', '2', '70', b'1', NULL, '2',
        '2023-12-22 16:23:50', '2023-12-29 10:02:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151317', '上架', '3jG0', NULL, '101513', NULL, '/manage/poi/line/shelves', '2', '62', b'1', NULL, '2',
        '2024-03-08 13:54:12', '2024-03-08 13:55:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10151318', '下架', 'LjG0', NULL, '101513', NULL, '/manage/poi/line/unShelves', '2', '63', b'1', NULL, '2',
        '2024-03-08 13:54:31', '2024-03-08 13:55:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1016', '运营配置', '5z', 'EditPen', '0', NULL, NULL, '1', '70', b'1', '平台运营专享', '2',
        '2023-12-28 15:28:07', '2024-05-22 10:26:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101611', '零售标签', '7UU', 'PriceTag', '1016', '/config/itemTag', NULL, '1', '20', b'1', NULL, '2',
        '2023-12-28 15:28:21', '2024-06-25 10:13:42');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161110', '列表', 'BJR0', NULL, '101611', NULL, '/manage/item/tag/list', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:00:13', '2023-12-28 16:01:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161111', '新增', '7JR0', NULL, '101611', NULL, '/manage/item/tag/create', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:00:13', '2023-12-28 16:01:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161112', '编辑', 'WJR0', NULL, '101611', NULL, '/manage/item/tag/update', '2', '30', b'1', NULL, '2',
        '2023-12-28 16:00:13', '2023-12-28 16:01:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161113', '删除', '3JR0', NULL, '101611', NULL, '/manage/item/tag/delete', '2', '40', b'1', NULL, '2',
        '2023-12-28 16:00:13', '2023-12-28 16:01:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161114', '排序', 'LJR0', NULL, '101611', NULL, '/manage/item/tag/sort', '2', '35', b'1', NULL, '2',
        '2024-10-09 17:16:49', '2024-10-09 17:17:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101612', '站内信模板', 'WUU', 'Flag', '1016', '/config/notice', NULL, '1', '30', b'1', NULL, '2',
        '2023-12-28 15:28:21', '2024-06-24 10:55:14');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161210', '列表', 'c5R0', NULL, '101612', NULL, '/manage/notice/template/listPage', '2', '10', b'1', NULL,
        '2', '2023-12-28 16:02:27', '2023-12-28 16:02:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161211', '编辑', 'D5R0', NULL, '101612', NULL, '/manage/notice/template/update', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:02:27', '2023-12-28 16:03:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101613', '邮件模板', '3UU', 'Message', '1016', '/config/email', NULL, '1', '40', b'1', NULL, '2',
        '2023-12-28 15:28:21', '2024-06-24 10:55:30');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161310', '列表', 'yUR0', NULL, '101613', NULL, '/manage/email/template/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:04:56', '2023-12-28 16:05:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161311', '编辑', 'MUR0', NULL, '101613', NULL, '/manage/email/template/update', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:04:56', '2023-12-28 16:05:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101615', '任务配置', 'TUU', 'Odometer', '1016', '/config/task', NULL, '1', '60', b'1', NULL, '2',
        '2023-12-28 15:36:29', '2024-06-24 10:55:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161510', '列表', 'xOR0', NULL, '101615', NULL, '/manage/task/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:07:45', '2023-12-28 16:08:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161511', '编辑', 'tOR0', NULL, '101615', NULL, '/manage/task/update', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:07:45', '2023-12-28 16:08:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161512', '刷新', 'JOR0', NULL, '101615', NULL, '/manage/task/refresh', '2', '30', b'1', NULL, '2',
        '2023-12-28 16:07:45', '2023-12-28 16:08:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161513', '执行', 'KOR0', NULL, '101615', NULL, '/manage/task/execute', '2', '40', b'1', NULL, '2',
        '2023-12-28 16:07:45', '2023-12-28 16:08:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101616', '资讯配置', 'oUU', 'Operation', '1016', '/config/news', NULL, '1', '70', b'1', NULL, '2',
        '2023-12-29 15:54:06', '2024-05-31 17:20:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161610', '列表', 'LcR0', NULL, '101616', NULL, '/manage/news/config/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-29 15:54:06', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161611', '新增', 'TcR0', NULL, '101616', NULL, '/manage/news/config/create', '2', '20', b'1', NULL, '2',
        '2023-12-29 15:54:06', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161612', '编辑', 'ocR0', NULL, '101616', NULL, '/manage/news/config/update', '2', '30', b'1', NULL, '2',
        '2023-12-29 15:54:06', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161613', '删除', 'vcR0', NULL, '101616', NULL, '/manage/news/config/delete', '2', '40', b'1', NULL, '2',
        '2023-12-29 15:54:06', '2023-12-29 16:31:39');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101617', '会员标签', 'vUU', 'House', '1016', '/config/memberTag', NULL, '1', '80', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-06-24 10:56:15');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161710', '列表', 'unR0', NULL, '101617', NULL, '/manage/member/tag/listPage', '2', '10', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-03-06 17:23:41');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161711', '新增', 'NnR0', NULL, '101617', NULL, '/manage/member/tag/create', '2', '20', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-03-06 17:23:42');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161712', '编辑', 'GnR0', NULL, '101617', NULL, '/manage/member/tag/update', '2', '30', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-03-06 17:23:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161714', '刷新', 'rnR0', NULL, '101617', NULL, '/manage/member/tag/refresh', '2', '50', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-03-06 17:23:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161715', '删除', 'RnR0', NULL, '101617', NULL, '/manage/member/tag/delete', '2', '60', b'1', NULL, '2',
        '2024-03-06 17:20:50', '2024-03-06 17:23:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161716', '会员列表', '9nR0', NULL, '101617', NULL, '/manage/member/tag/memberPage', '2', '70', b'1', NULL,
        '2', '2024-03-06 17:20:50', '2024-03-06 17:23:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161717', '发送通知', 'anR0', NULL, '101617', NULL, '/manage/member/tag/sendNotice', '2', '80', b'1',
        '发送站内信通知', '2', '2024-03-06 17:24:39', '2024-03-08 15:08:15');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161718', '发送短信', '0nR0', NULL, '101617', NULL, '/manage/member/tag/sendSms', '2', '90', b'1', NULL, '2',
        '2024-03-07 15:47:52', '2024-03-07 15:48:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161719', '发放优惠券', 'dnR0', NULL, '101617', NULL, '/manage/coupon/grant,/manage/coupon/listPage', '2',
        '100', b'1', '发放优惠券', '2', '2024-06-30 17:04:50', '2024-06-30 17:04:50');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101618', '支付配置', 'IkU', 'CreditCard', '1016', '/config/pay', '', '1', '5', b'1', '', '2',
        '2024-09-24 15:13:37', '2024-09-24 15:13:48');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161810', '列表', 'fuR0', '', '101618', '', '/manage/pay/config/listPage', '2', '10', b'1', '', '2',
        '2024-09-24 15:17:15', '2024-09-24 15:20:37');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10161811', '编辑', 'euR0', '', '101618', '', '/manage/pay/config/update', '2', '20', b'1', '', '2',
        '2024-09-24 15:17:38', '2024-09-24 15:20:41');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1017', '日志管理', 'Zz', 'Document', '0', NULL, NULL, '1', '80', b'1', '平台专享', '2', '2023-12-28 15:39:23',
        '2024-05-22 10:26:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101710', '短信日志', 'clU', 'ChatDotSquare', '1017', '/log/sms', NULL, '1', '10', b'1', NULL, '2',
        '2023-12-28 15:39:23', '2024-06-24 11:21:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171010', '列表', 'uLa0', NULL, '101710', NULL, '/manage/sms/log/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:09:35', '2023-12-28 16:09:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101711', '任务日志', 'DlU', 'Failed', '1017', '/log/task', NULL, '1', '20', b'1', NULL, '2',
        '2023-12-28 15:39:24', '2024-06-24 11:41:05');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171110', '列表', 'foa0', NULL, '101711', NULL, '/manage/task/log/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:10:50', '2023-12-28 16:11:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171111', '详情', 'eoa0', NULL, '101711', NULL, '/manage/task/log/select', '2', '20', b'1',
        '日志错误详细信息', '2', '2023-12-28 16:10:50', '2023-12-28 16:11:16');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101712', '会员日志', 'nlU', 'Connection', '1017', '/log/member', NULL, '1', '30', b'1', NULL, '2',
        '2023-12-28 15:39:24', '2024-06-24 14:29:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171210', '列表', 'qva0', NULL, '101712', NULL, '/manage/webapp/log/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:12:04', '2023-12-28 16:12:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101713', '支付日志', 'YlU', 'Aim', '1017', '/log/pay', NULL, '1', '40', b'1', NULL, '2', '2023-12-28 15:39:24',
        '2024-06-24 14:32:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171310', '同步', '5F00', NULL, '101713', NULL, '/manage/pay/log/sync/listPage', '2', '10', b'1',
        '支付同步请求的日志列表(含退款)', '2', '2023-12-28 16:13:22', '2023-12-28 16:13:50');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171311', '异步', 'ZF00', NULL, '101713', NULL, '/manage/pay/log/async/listPage', '2', '20', b'1',
        '支付同步回调的日志列表(含退款)', '2', '2023-12-28 16:13:22', '2024-06-24 15:08:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101714', '系统日志', 'ulU', 'DocumentCopy', '1017', '/log/system', NULL, '1', '50', b'1', NULL, '2',
        '2023-12-28 15:49:14', '2024-06-24 14:35:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10171410', '列表', 'IH00', NULL, '101714', NULL, '/manage/log/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:15:13', '2023-12-28 16:15:46');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1018', '商户/会员', 'Uz', 'Notification', '0', NULL, NULL, '1', '90', b'1', '平台运营专享', '2',
        '2023-12-28 15:41:10', '2024-09-24 11:35:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101810', '商户管理', 'ycU', 'Box', '1018', '/user/merchant', NULL, '1', '10', b'1', NULL, '2',
        '2023-12-28 15:41:10', '2024-06-24 16:35:42');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181010', '列表', '2Yp0', NULL, '101810', NULL, '/manage/merchant/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181011', '新增', 'mYp0', NULL, '101810', NULL, '/manage/merchant/create', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181012', '编辑', 'BYp0', NULL, '101810', NULL, '/manage/merchant/update', '2', '30', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181013', '锁定', '7Yp0', NULL, '101810', NULL, '/manage/merchant/lock', '2', '40', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181014', '重置', 'WYp0', NULL, '101810', NULL, '/manage/merchant/resetPwd', '2', '50', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181015', '解锁', '3Yp0', NULL, '101810', NULL, '/manage/merchant/unlock', '2', '60', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181016', '导出', 'LYp0', NULL, '101810', NULL, '/manage/merchant/export', '2', '70', b'1', NULL, '2',
        '2023-12-28 16:16:51', '2023-12-28 16:17:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181017', '详情', 'TYp0', NULL, '101810', NULL, '/manage/merchant/detail', '2', '65', b'1', NULL, '2',
        '2024-01-16 13:45:15', '2024-01-16 13:47:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181018', '解绑', 'oYp0', NULL, '101810', NULL, '/manage/merchant/unbind', '2', '66', b'1', NULL, '2',
        '2024-01-16 13:45:50', '2024-01-16 13:47:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181019', '调整费率', 'vYp0', NULL, '101810', NULL, '/manage/merchant/adjustRate', '2', '67', b'1', NULL, '2',
        '2024-01-22 13:26:11', '2024-01-22 13:27:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181020', '注销', 'Iup0', NULL, '101810', NULL, '/manage/merchant/logout', '2', '80', b'1', NULL, '2',
        '2024-03-08 17:05:28', '2024-03-08 17:06:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101811', '会员管理', 'McU', 'Avatar', '1018', '/user/member', NULL, '1', '20', b'1', NULL, '2',
        '2023-12-28 15:41:10', '2024-06-24 16:36:31');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181110', '列表', 'lNp0', NULL, '101811', NULL, '/manage/member/listPage', '2', '10', b'1', NULL, '2',
        '2023-12-28 16:19:07', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181111', '冻结', 'ONp0', NULL, '101811', NULL, '/manage/member/freeze', '2', '20', b'1', NULL, '2',
        '2023-12-28 16:19:07', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181112', '解冻', 'cNp0', NULL, '101811', NULL, '/manage/member/unfreeze', '2', '30', b'1', NULL, '2',
        '2023-12-28 16:19:08', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181113', '强制下线', 'DNp0', NULL, '101811', NULL, '/manage/member/offline', '2', '40', b'1', NULL, '2',
        '2023-12-28 16:19:08', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181114', '导出', 'nNp0', NULL, '101811', NULL, '/manage/member/export', '2', '50', b'1', NULL, '2',
        '2023-12-28 16:19:08', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181115', '登录日志', 'YNp0', NULL, '101811', NULL, '/manage/member/loginPage', '2', '60', b'1', NULL, '2',
        '2023-12-28 16:19:08', '2023-12-28 16:20:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181116', '发送通知', 'uNp0', NULL, '101811', NULL, '/manage/member/sendNotice', '2', '70', b'1', NULL, '2',
        '2024-03-07 11:48:35', '2024-03-08 15:06:34');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181117', '发送短信', 'NNp0', NULL, '101811', NULL, '/manage/member/sendSms', '2', '80', b'1', NULL, '2',
        '2024-03-08 15:06:12', '2024-03-08 15:08:33');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1019', '营销中心', 'kz', 'Calendar', '0', NULL, NULL, '1', '100', b'1', '商户及平台运营', '3',
        '2023-12-29 10:23:46', '2024-05-22 10:26:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101910', '优惠券', 'pDU', 'Box', '1019', '/marketing/coupon', NULL, '1', '10', b'1', NULL, '3',
        '2023-12-29 10:23:46', '2024-06-27 17:15:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191010', '列表', 'sPi0', NULL, '101910', NULL, '/manage/coupon/listPage', '2', '10', b'1', NULL, '3',
        '2023-12-29 10:27:37', '2023-12-29 10:29:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191011', '新增', 'PPi0', NULL, '101910', NULL,
        '/manage/coupon/create,/manage/scenic/ticket/productListPage,/manage/homestay/room/productListPage,/manage/item/productListPage,/manage/restaurant/voucher/productListPage,/manage/line/productListPage,/manage/venue/site/productListPage,/manage/scenic/list,/manage/homestay/list,/manage/restaurant/list,/manage/item/store/list,/manage/travel/list,/manage/venue/list',
        '2', '20', b'1', NULL, '1', '2023-12-29 10:27:37', '2024-07-31 16:54:52');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191012', '编辑', 'fPi0', NULL, '101910', NULL,
        '/manage/coupon/update,/manage/coupon/select,/manage/scenic/ticket/productListPage,/manage/homestay/room/productListPage,/manage/item/productListPage,/manage/restaurant/voucher/productListPage,/manage/line/productListPage,/manage/venue/site/productListPage,/manage/scenic/list,/manage/homestay/list,/manage/restaurant/list,/manage/item/store/list,/manage/travel/list,/manage/venue/list',
        '2', '30', b'1', NULL, '1', '2023-12-29 10:27:37', '2024-07-31 16:54:55');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191013', '详情', 'ePi0', NULL, '101910', NULL,
        '/manage/coupon/select,/manage/scenic/ticket/productListPage,/manage/homestay/room/productListPage,/manage/item/productListPage,/manage/restaurant/voucher/productListPage,/manage/line/productListPage,/manage/venue/site/productListPage,/manage/scenic/list,/manage/homestay/list,/manage/restaurant/list,/manage/item/store/list,/manage/travel/list,/manage/venue/list',
        '2', '40', b'1', NULL, '3', '2023-12-29 10:27:37', '2024-07-31 16:55:02');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191014', '启用', 'EPi0', NULL, '101910', NULL, '/manage/coupon/open', '2', '50', b'1', NULL, '1',
        '2023-12-29 10:27:37', '2023-12-29 10:29:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191015', '禁用', 'wPi0', NULL, '101910', NULL, '/manage/coupon/close', '2', '60', b'1', NULL, '1',
        '2023-12-29 10:27:37', '2023-12-29 10:29:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191016', '发放', '1Pi0', NULL, '101910', NULL, '/manage/coupon/grant,/manage/member/listPage', '2', '70',
        b'1', NULL, '2', '2023-12-29 10:27:37', '2024-07-29 13:49:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191017', '领取详情', 'CPi0', NULL, '101910', NULL, '/manage/coupon/receivePage', '2', '80', b'1', NULL, '1',
        '2023-12-29 10:27:37', '2024-03-26 14:08:54');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191018', '生成链接', 'zPi0', NULL, '101910', NULL, '/manage/wechat/shortUrl,/manage/wechat/linkUrl', '2',
        '90', b'1', '只有页面领取类型的优惠券才能生成链接', '3', '2024-03-26 14:02:33', '2024-03-26 14:11:10');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191019', '删除', 'QPi0', NULL, '101910', NULL, '/manage/coupon/delete', '2', '100', b'1', NULL, '1',
        '2024-08-05 13:45:13', '2024-08-05 13:45:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101911', '拼团', 'hDU', 'Money', '1019', '/marketing/group', NULL, '1', '20', b'1', NULL, '3',
        '2024-02-19 14:32:50', '2024-07-01 11:36:07');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191110', '列表', 'Xfi0', NULL, '101911', NULL, '/manage/group/booking/listPage', '2', '10', b'1', NULL, '3',
        '2024-02-19 14:32:50', '2024-02-19 14:34:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191111', '新增', 'ifi0', NULL, '101911', NULL, '/manage/group/booking/create,/manage/item/activityList', '2',
        '20', b'1', NULL, '1', '2024-02-19 14:32:50', '2024-02-26 15:32:46');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191112', '编辑', 'qfi0', NULL, '101911', NULL,
        '/manage/group/booking/update,/manage/item/activityList,/manage/group/booking/detail', '2', '30', b'1', NULL,
        '1', '2024-02-19 14:32:50', '2024-02-26 15:36:47');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191113', '详情', '8fi0', NULL, '101911', NULL, '/manage/group/booking/detail,/manage/item/activityList', '2',
        '40', b'1', NULL, '3', '2024-02-19 14:32:50', '2024-08-19 14:24:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191114', '删除', 'jfi0', NULL, '101911', NULL, '/manage/group/booking/delete', '2', '50', b'1', NULL, '1',
        '2024-02-19 14:33:46', '2024-02-19 14:35:13');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101912', '限时购', 'XDU', 'AlarmClock', '1019', '/marketing/limit', NULL, '1', '30', b'1', NULL, '3',
        '2024-02-19 14:36:06', '2024-07-01 11:36:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191210', '列表', 'JEi0', NULL, '101912', NULL, '/manage/limit/purchase/listPage', '2', '10', b'1', NULL, '3',
        '2024-02-19 14:36:06', '2024-02-19 14:36:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191211', '新增', 'KEi0', NULL, '101912', NULL, '/manage/limit/purchase/create,/manage/item/activityList',
        '2', '20', b'1', NULL, '1', '2024-02-19 14:36:06', '2024-02-26 15:36:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191212', '编辑', '5Ei0', NULL, '101912', NULL,
        '/manage/limit/purchase/update,/manage/limit/purchase/detail,/manage/item/activityList', '2', '30', b'1', NULL,
        '1', '2024-02-19 14:36:06', '2024-02-26 15:36:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191213', '详情', 'ZEi0', NULL, '101912', NULL, '/manage/limit/purchase/detail', '2', '40', b'1', NULL, '3',
        '2024-02-19 14:36:06', '2024-02-19 14:37:08');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191214', '删除', 'UEi0', NULL, '101912', NULL, '/manage/limit/purchase/delete', '2', '50', b'1', NULL, '1',
        '2024-02-19 14:36:06', '2024-02-19 14:37:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101913', '抽奖', 'iDU', 'Present', '1019', '/marketing/lottery', NULL, '1', '40', b'1', NULL, '3',
        '2024-02-27 10:54:47', '2024-07-01 11:36:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191310', '列表', 'owi0', NULL, '101913', NULL, '/manage/lottery/listPage', '2', '10', b'1', NULL, '3',
        '2024-02-27 10:54:47', '2024-02-27 10:56:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191311', '新增', 'vwi0', NULL, '101913', NULL,
        '/manage/lottery/create,/manage/store/storeList,/manage/coupon/grantList', '2', '20', b'1', NULL, '1',
        '2024-02-27 10:54:47', '2024-07-17 17:48:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191312', '编辑', 'I1i0', NULL, '101913', NULL,
        '/manage/lottery/update,/manage/store/storeList,/manage/coupon/grantList', '2', '30', b'1', NULL, '1',
        '2024-02-27 10:54:47', '2024-07-17 17:48:48');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191313', '详情', 'F1i0', NULL, '101913', NULL, '/manage/lottery/detail,/manage/store/storeList', '2', '40',
        b'1', NULL, '3', '2024-02-27 10:54:47', '2024-07-11 16:06:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191314', '删除', 'b1i0', NULL, '101913', NULL, '/manage/lottery/delete', '2', '50', b'1', NULL, '1',
        '2024-02-27 10:55:52', '2024-02-27 10:56:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191315', '记录', 'H1i0', NULL, '101913', NULL, '/manage/lottery/logPage', '2', '60', b'1', '抽奖记录', '3',
        '2024-04-11 09:49:07', '2024-04-11 09:49:55');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('101914', '兑换码', 'qDU', 'Magnet', '1019', '/marketing/redeem', NULL, '1', '50', b'1', NULL, '2',
        '2024-02-27 10:58:27', '2024-07-01 11:36:27');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191410', '列表', 'GCi0', NULL, '101914', NULL, '/manage/redeem/code/listPage', '2', '10', b'1', NULL, '2',
        '2024-02-27 10:58:28', '2024-02-27 11:06:14');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191411', '新增', 'ACi0', NULL, '101914', NULL, '/manage/redeem/code/create,/manage/store/storeList', '2',
        '20', b'1', NULL, '2', '2024-02-27 10:58:28', '2024-07-02 09:25:29');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191412', '编辑', 'rCi0', NULL, '101914', NULL,
        '/manage/redeem/code/update,/manage/store/storeList,/manage/redeem/code/detail', '2', '30', b'1', NULL, '2',
        '2024-02-27 10:58:28', '2024-07-02 09:25:42');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191413', '详情', 'RCi0', NULL, '101914', NULL, '/manage/redeem/code/detail,/manage/store/storeList', '2',
        '40', b'1', NULL, '2', '2024-02-27 10:58:28', '2024-07-02 09:25:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191414', '生成', '9Ci0', NULL, '101914', NULL, '/manage/redeem/code/generate', '2', '50', b'1', NULL, '2',
        '2024-02-27 10:58:28', '2024-02-27 11:06:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191415', '删除', 'aCi0', NULL, '101914', NULL, '/manage/redeem/code/delete', '2', '60', b'1', NULL, '2',
        '2024-02-27 10:58:28', '2024-02-27 11:06:17');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191416', '兑换码列表', '0Ci0', NULL, '101914', NULL, '/manage/redeem/code/grant/listPage', '2', '70', b'1',
        NULL, '2', '2024-02-27 10:58:28', '2024-02-27 11:06:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10191417', '兑换码导出', 'dCi0', NULL, '101914', NULL, '/manage/redeem/code/grant/export', '2', '80', b'1',
        NULL, '2', '2024-02-27 10:58:28', '2024-02-27 11:06:19');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201010', '会员分析', 'UX80', NULL, '102010', NULL, '/manage/statistics/sexChannel', '2', '10', b'1', NULL,
        '2', '2024-02-26 17:30:16', '2024-02-26 17:33:43');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201011', '注册统计', 'kX80', NULL, '102010', NULL, '/manage/statistics/dayRegister', '2', '20', b'1', NULL,
        '2', '2024-02-26 17:30:16', '2024-02-26 17:33:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201014', '浏览统计', 'cX80', NULL, '102010', NULL, '/manage/statistics/visit', '2', '50', b'1', NULL, '2',
        '2024-02-26 17:30:17', '2024-02-26 17:33:56');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201015', '收藏统计', 'DX80', NULL, '102010', NULL, '/manage/statistics/collect', '2', '60', b'1', NULL, '2',
        '2024-02-26 17:30:17', '2024-02-26 17:34:00');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201016', '商品新增', 'nX80', NULL, '102010', NULL, '/manage/statistics/append', '2', '70', b'1', NULL, '2',
        '2024-02-26 17:30:17', '2024-02-26 17:34:03');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201017', '零售销售排行', 'YX80', NULL, '102010', NULL, '/manage/statistics/itemSale', '2', '80', b'1', NULL,
        '2', '2024-09-09 17:40:59', '2024-09-09 17:42:23');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201018', '商家销售排行', 'uX80', NULL, '102010', NULL, '/manage/statistics/merchantSale', '2', '90', b'1',
        NULL, '2', '2024-09-09 17:41:00', '2024-09-09 17:42:24');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('1021', '售后管理', 'Oz', 'Service', '0', NULL, NULL, '1', '46', b'1', NULL, '3', '2024-05-22 10:27:30',
        '2024-05-28 17:51:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('102110', '退款订单', 'LuU', 'SoldOut', '1021', '/service/refund', NULL, '1', '10', b'1', NULL, '3',
        '2024-05-22 10:27:30', '2024-07-29 14:48:48');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211010', '列表', 'pt20', NULL, '102110', NULL, '/manage/refund/log/listPage', '2', '10', b'1', NULL, '3',
        '2024-05-22 10:30:48', '2024-05-22 10:34:40');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211011', '审核', 'ht20', NULL, '102110', NULL, '/manage/refund/log/audit', '2', '20', b'1', NULL, '1',
        '2024-05-22 10:30:48', '2024-05-22 10:34:45');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211012', '详情', 'Xt20', NULL, '102110', NULL, '/manage/refund/log/item/detail', '2', '30', b'1', NULL, '3',
        '2024-05-22 10:30:48', '2024-05-22 10:34:51');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('102111', '核销订单', 'TuU', 'Stamp', '1021', '/service/verify', NULL, '1', '20', b'1', NULL, '3',
        '2024-05-22 10:27:30', '2024-07-29 14:45:04');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211110', '列表', 'xK20', NULL, '102111', NULL, '/manage/verify/listPage', '2', '10', b'1', NULL, '3',
        '2024-05-22 10:35:27', '2024-07-29 13:57:11');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211111', '扫码', 'tK20', NULL, '102111', NULL, '/manage/verify/scan', '2', '20', b'1', '移动端使用', '1',
        '2024-05-22 10:35:27', '2024-05-22 10:36:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211112', '核销', 'JK20', NULL, '102111', NULL, '/manage/verify/verify', '2', '30', b'1', '移动端使用', '1',
        '2024-05-22 10:35:27', '2024-05-22 10:36:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211113', '导出', 'KK20', NULL, '102111', NULL, '/manage/verify/export', '2', '40', b'1', NULL, '3',
        '2024-07-29 14:14:02', '2024-07-29 14:14:32');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('102112', '订单评价', 'ouU', 'MoreFilled', '1021', '/service/evaluation', NULL, '1', '30', b'1', NULL, '2',
        '2024-05-22 10:27:30', '2024-07-29 14:48:18');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211210', '列表', 'L520', NULL, '102112', NULL, '/manage/order/evaluation/listPage', '2', '10', b'1', NULL,
        '2', '2024-05-22 10:37:27', '2024-05-22 10:38:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211211', '屏蔽', 'T520', NULL, '102112', NULL, '/manage/order/evaluation/shield', '2', '20', b'1', NULL, '2',
        '2024-05-22 10:37:27', '2024-07-29 16:29:12');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('102113', '购物车', 'vuU', 'ShoppingTrolley', '1021', '/service/cart', NULL, '1', '40', b'1', NULL, '3',
        '2024-05-22 11:02:40', '2024-07-29 14:49:06');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10211310', '列表', 'uU20', NULL, '102113', NULL, '/manage/shopping/cart/listPage', '2', '10', b'1', NULL, '3',
        '2024-05-22 11:03:18', '2024-05-22 11:04:57');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201019', '经营数据', 'NX80', NULL, '102010', NULL, '/manage/statistics/orderDetail', '2', '100', b'1', NULL,
        '3', '2024-10-30 15:40:08', '2024-10-30 15:40:36');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201020', '下单统计', 'GX80', NULL, '102010', NULL, '/manage/statistics/order', '2', '110', b'1', NULL, '3',
        '2024-10-30 15:42:09', '2024-10-30 15:43:26');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201021', '下单统计(天)', 'AX80', NULL, '102010', NULL, '/manage/statistics/dayOrder', '2', '120', b'1', NULL,
        '3', '2024-10-30 15:42:59', '2024-10-30 15:44:09');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10201022', '加购统计', 'rX80', NULL, '102010', NULL, '/manage/statistics/cart', '2', '130', b'1', NULL, '3',
        '2024-10-30 15:44:31', '2024-10-30 15:45:44');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10181118', '更新积分', 'GNp0', NULL, '101811', NULL, '/manage/member/updateScore', '2', '90', b'1', NULL, '2',
        '2024-12-17 16:01:35', '2024-12-17 16:01:35');
INSERT INTO `sys_menu` (`id`, `title`, `code`, `icon`, `pid`, `path`, `sub_path`, `grade`, `sort`, `state`, `remark`,
                        `display_state`, `create_time`, `update_time`)
VALUES ('10113016', '上下架', 'fmU0', '', '101130', '', '/manage/news/updateState', '2', '70', b'1', '', '2',
        '2024-11-29 14:58:57', '2024-11-29 14:59:05');
