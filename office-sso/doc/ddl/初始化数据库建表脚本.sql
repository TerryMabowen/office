DROP TABLE IF EXISTS `oc_sso_users`;
CREATE TABLE `oc_sso_users`
(
    `id`            bigint(20)                      NOT NULL AUTO_INCREMENT,
    `username`      varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
    `password_hash` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '加密密码',
    `status`        int(11)                         NOT NULL,
    `created_time`  datetime                        NOT NULL,
    `updated_time`  datetime                        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户表';

DROP TABLE IF EXISTS `oc_sso_roles`;
CREATE TABLE `oc_sso_roles`
(
    `id`           bigint(20)                      NOT NULL AUTO_INCREMENT,
    `app_id`       bigint(20)                      NOT NULL COMMENT '应用ID',
    `code`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码',
    `name`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
    `status`       int(11)                         NOT NULL,
    `created_time` datetime                        NOT NULL,
    `updated_time` datetime                        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色表';

DROP TABLE IF EXISTS `oc_sso_permissions`;
CREATE TABLE `oc_sso_permissions`
(
    `id`           bigint(20)                      NOT NULL AUTO_INCREMENT,
    `app_id`       bigint(20)                      NOT NULL COMMENT '应用ID',
    `code`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '权限编码',
    `name`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '权限名称',
    `pid`          bigint(11) COLLATE utf8mb4_bin  NOT NULL COMMENT '父权限ID',
    `parent_path`  varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '父路径',
    `status`       int(11)                         NOT NULL,
    `created_time` datetime                        NOT NULL,
    `updated_time` datetime                        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='权限表';

DROP TABLE IF EXISTS `oc_sso_apps`;
CREATE TABLE `oc_sso_apps`
(
    `id`           bigint(20)                      NOT NULL AUTO_INCREMENT,
    `code`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '应用编码',
    `name`         varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '应用名',
    `url`          varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应用链接',
    `version`      varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应用版本',
    `app_secret`   varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '应用秘钥',
    `enable_state` int(11)                         NOT NULL COMMENT '启用状态',
    `status`       int(11)                         NOT NULL,
    `created_time` datetime                        NOT NULL,
    `updated_time` datetime                        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='应用表';

DROP TABLE IF EXISTS `oc_sso_user_app_roles`;
CREATE TABLE `oc_sso_user_app_roles`
(
    `user_id` bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL,
    `app_id`  bigint(20) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户应用角色表';

DROP TABLE IF EXISTS `oc_sso_role_permissions`;
CREATE TABLE `oc_sso_role_permissions`
(
    `app_id`        bigint(20) NOT NULL,
    `role_id`       bigint(20) NOT NULL,
    `permission_id` bigint(20) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色权限表';