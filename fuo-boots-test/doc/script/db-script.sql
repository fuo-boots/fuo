
###
# 用户表
###
DROP TABLE  IF EXISTS `fuo_test_main`.`t_test_user`;
CREATE TABLE `fuo_test_main`.`t_test_user` (
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
`name` varchar(100) NULL COMMENT '用户名称',
`age` TINYINT null COMMENT '年龄',
`create_time` datetime NOT NULL COMMENT '创建日期',
`creator` varchar(100) NUll COMMENT '创建人',
`modify_time` datetime NOT NULL COMMENT '修改日期',
`modifier` varchar(100) NULL  COMMENT '修改人',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT= 1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';