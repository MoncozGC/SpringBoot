-- 用户权限认证
CREATE TABLE `user_info`
(
    `UI_ID`          int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号，主键自增',
    `UI_USER_NAME`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `UI_PASSWORD`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
    `UI_STATUS`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'O' COMMENT 'O：正常，D：已删除',
    `UI_CREATE_TIME` bigint(12) NULL DEFAULT NULL COMMENT '用户创建时间',
    `UI_ROLES`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`UI_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;