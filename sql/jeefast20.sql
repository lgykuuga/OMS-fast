/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : jeefast20

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 25/02/2021 15:24:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_combo
-- ----------------------------
DROP TABLE IF EXISTS `base_combo`;
CREATE TABLE `base_combo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '组合商品编码',
  `children` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '明细商品编码',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='组合商品';

-- ----------------------------
-- Table structure for base_commodity
-- ----------------------------
DROP TABLE IF EXISTS `base_commodity`;
CREATE TABLE `base_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `combo` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'N' COMMENT '组合商品',
  `img_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '缩略图',
  `bar_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品条码',
  `brand` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `category` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类别',
  `size` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '尺寸',
  `weight` decimal(20,6) DEFAULT NULL COMMENT '重量',
  `volume` decimal(20,6) DEFAULT NULL COMMENT '体积',
  `owner` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品档案';

-- ----------------------------
-- Table structure for base_logistics
-- ----------------------------
DROP TABLE IF EXISTS `base_logistics`;
CREATE TABLE `base_logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '物流商编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流商名称',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='物流商信息';

-- ----------------------------
-- Table structure for base_owner
-- ----------------------------
DROP TABLE IF EXISTS `base_owner`;
CREATE TABLE `base_owner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '货主编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '货主名称',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='货主档案';

-- ----------------------------
-- Table structure for base_platform
-- ----------------------------
DROP TABLE IF EXISTS `base_platform`;
CREATE TABLE `base_platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '平台编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台名称',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='平台档案';

-- ----------------------------
-- Table structure for base_shop
-- ----------------------------
DROP TABLE IF EXISTS `base_shop`;
CREATE TABLE `base_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '店铺编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `platform` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台编码',
  `owner` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='店铺档案';

-- ----------------------------
-- Table structure for base_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `base_warehouse`;
CREATE TABLE `base_warehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gco` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `gna` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '仓库名称',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gco` (`gco`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='仓库信息';

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) COLLATE utf8mb4_bin DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) COLLATE utf8mb4_bin DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='代码生成业务表';

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) COLLATE utf8mb4_bin DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=990 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='代码生成业务表字段';

-- ----------------------------
-- Table structure for oms_distribution_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_distribution_detail`;
CREATE TABLE `oms_distribution_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `distribution_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '配货单号',
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `source_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '来源编号',
  `row_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '行序号',
  `source_row` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '来源行序号',
  `commodity` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `oid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台子订单编号',
  `refund_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '退款状态',
  `type` tinyint(4) DEFAULT '0' COMMENT '商品类型',
  `pic_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品图片绝对路径',
  `num_iid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品数字ID',
  `outer_iid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商家外部编码',
  `sku_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台skuID',
  `outer_sku_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '外部网店自己定义的Sku编号',
  `price` decimal(20,6) DEFAULT '0.000000' COMMENT '销售单价',
  `total_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '应付金额',
  `payment` decimal(20,6) DEFAULT '0.000000' COMMENT '实付金额',
  `divide_order_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '分摊之后的实付金额',
  `size` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '尺寸',
  `bar_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品条码',
  `brand` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `category` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类别',
  `warehouse` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发货仓库',
  `logistics` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '物流商',
  `express_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '快递单号',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='配货单明细表';

-- ----------------------------
-- Table structure for oms_distribution_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_distribution_order`;
CREATE TABLE `oms_distribution_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `distribution_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '配货单号',
  `order_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单单号',
  `source_id` varchar(200) COLLATE utf8mb4_bin NOT NULL COMMENT '来源单号',
  `shop` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '店铺编码',
  `platform` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '平台编码',
  `owner` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '货主编码',
  `source_type` tinyint(4) DEFAULT '0' COMMENT '来源类型',
  `size_type` tinyint(4) DEFAULT '0' COMMENT '尺码类型',
  `sku_num` int(11) DEFAULT '0' COMMENT 'sku种类数量',
  `qty` int(11) DEFAULT '0' COMMENT '总件数',
  `commodity` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码集合',
  `volume` decimal(20,6) DEFAULT '0.000000' COMMENT '总体积',
  `weight` decimal(20,6) DEFAULT '0.000000' COMMENT '总重量',
  `freight_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '运费',
  `warehouse` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发货仓库编码',
  `logistics` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流商编码',
  `express_number` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `send_time` datetime(3) DEFAULT NULL COMMENT '推送时间',
  `sendout_time` datetime(3) DEFAULT NULL COMMENT '发货时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`,`distribution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='配货单信息表';

-- ----------------------------
-- Table structure for oms_download_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_download_order`;
CREATE TABLE `oms_download_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `bedt` datetime(3) DEFAULT NULL COMMENT '查单开始时间',
  `endt` datetime(3) DEFAULT NULL COMMENT '查单结束时间',
  `dodt` datetime(3) DEFAULT NULL COMMENT '执行时间',
  `size` int(11) DEFAULT NULL COMMENT '每页数量',
  `page` int(11) DEFAULT NULL COMMENT '页数',
  `resp` text COLLATE utf8_bin COMMENT '请求返回消息',
  `onum` int(11) DEFAULT NULL COMMENT '订单数量',
  `stat` int(11) DEFAULT NULL COMMENT '请求状态（1：成功 0：失败）',
  `snum` int(11) DEFAULT '0' COMMENT '成功下载保存订单数量',
  `unum` int(11) DEFAULT '0' COMMENT '成功下载更新订单数量',
  `fnum` int(11) DEFAULT '0' COMMENT '失败下载保存订单数量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='下载订单';

-- ----------------------------
-- Table structure for oms_mq_error_message
-- ----------------------------
DROP TABLE IF EXISTS `oms_mq_error_message`;
CREATE TABLE `oms_mq_error_message` (
  `id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
  `queue` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '队列名',
  `error` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='mq异常消息';

-- ----------------------------
-- Table structure for oms_order_buyerinfo
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_buyerinfo`;
CREATE TABLE `oms_order_buyerinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编码',
  `source_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '来源单号',
  `buyer_id` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '买家ID',
  `buyer_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家姓名',
  `buyer_phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家电话',
  `buyer_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家邮件地址',
  `buyer_card_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '买家身份证号',
  `consignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人姓名',
  `consignee_mobile` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人移动电话',
  `consignee_telephone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人固定电话',
  `consignee_email` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人邮箱地址',
  `consignee_card_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人身份证号',
  `nation_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国家编码',
  `nation` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人国家',
  `province_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '省份编码',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人省/州',
  `city_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人市',
  `district_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `district` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件人区县',
  `address` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  `zip_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `buyer_message` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '买家留言',
  `seller_message` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卖家留言',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单买家信息表';

-- ----------------------------
-- Table structure for oms_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_detail`;
CREATE TABLE `oms_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `source_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '来源编号',
  `row_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '行序号',
  `source_row` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '来源行序号',
  `commodity` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `distribution_qty` int(11) DEFAULT '0' COMMENT '下发配货单数量',
  `send_qty` int(11) DEFAULT '0' COMMENT '发货完成数量',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `oid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台子订单编号',
  `refund_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '退款状态',
  `type` tinyint(4) DEFAULT '0' COMMENT '商品类型',
  `pic_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品图片绝对路径',
  `num_iid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品数字ID',
  `outer_iid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商家外部编码',
  `sku_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '平台skuID',
  `outer_sku_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '外部网店自己定义的Sku编号',
  `price` decimal(20,6) DEFAULT '0.000000' COMMENT '销售单价',
  `total_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '应付金额',
  `payment` decimal(20,6) DEFAULT '0.000000' COMMENT '实付金额',
  `divide_order_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '分摊之后的实付金额',
  `size` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '尺寸',
  `bar_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品条码',
  `brand` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `category` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类别',
  `active` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '活动编码',
  `warehouse` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发货仓库',
  `logistics` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '物流商',
  `express_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '快递单号',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单明细信息表';

-- ----------------------------
-- Table structure for oms_order_intercept
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_intercept`;
CREATE TABLE `oms_order_intercept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单编码',
  `type` tinyint(4) DEFAULT '0' COMMENT '拦截类型',
  `content` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '拦截内容说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单拦截信息表';

-- ----------------------------
-- Table structure for oms_order_main
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_main`;
CREATE TABLE `oms_order_main` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单流水编号',
  `source_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '来源单号',
  `shop` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '店铺编码',
  `platform` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '平台编码',
  `owner` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '货主编码',
  `frozen` tinyint(4) DEFAULT '1' COMMENT '订单是否冻结',
  `active` tinyint(4) DEFAULT '1' COMMENT '是否参与活动',
  `hand_edit` tinyint(4) DEFAULT '1' COMMENT '是否人工编辑',
  `refund` tinyint(4) DEFAULT '1' COMMENT '是否退款',
  `intercept` tinyint(4) DEFAULT '1' COMMENT '是否拦截',
  `after_sales` tinyint(4) DEFAULT '1' COMMENT '是否售后',
  `invoice` tinyint(4) DEFAULT '1' COMMENT '是否发票',
  `order_lock` tinyint(4) DEFAULT '1' COMMENT '是否用户锁定',
  `lock_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '锁定人编码',
  `lock_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '锁定时间',
  `mark` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单标记',
  `mark_content` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '标记内容',
  `seller_flag` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '卖家备注旗帜',
  `size_type` tinyint(4) DEFAULT '0' COMMENT '尺码类型',
  `sku_num` int(11) DEFAULT '0' COMMENT 'sku种类数量',
  `qty` int(11) DEFAULT '0' COMMENT '总件数',
  `distribution_qty` int(11) DEFAULT '0' COMMENT '生成配货单数量',
  `send_qty` int(11) DEFAULT '0' COMMENT '发货完成数量',
  `commodity` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码集合',
  `volume` decimal(20,6) DEFAULT '0.000000' COMMENT '总体积',
  `weight` decimal(20,6) DEFAULT '0.000000' COMMENT '总重量',
  `warehouse` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发货仓库编码(支持多条)',
  `logistics` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流商编码(支持多条)',
  `express_number` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号(支持多条)',
  `sendout_time` datetime(3) DEFAULT NULL COMMENT '发货时间',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `lack_stock` tinyint(4) DEFAULT '0' COMMENT '缺货标识',
  `lock_stock` tinyint(4) DEFAULT '0' COMMENT '库存占用标识',
  PRIMARY KEY (`id`,`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单主信息表';

-- ----------------------------
-- Table structure for oms_order_payinfo
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_payinfo`;
CREATE TABLE `oms_order_payinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单编码',
  `source_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '来源单号',
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `currency` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `order_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '订单金额',
  `pay_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '支付金额',
  `received_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '实收金额',
  `discount` decimal(20,6) DEFAULT '0.000000' COMMENT '折扣',
  `tax_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '税额',
  `post` decimal(20,6) DEFAULT '0.000000' COMMENT '邮费',
  `freight_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '运费',
  `refund_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '退款金额',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单支付信息表';

-- ----------------------------
-- Table structure for oms_order_status
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_status`;
CREATE TABLE `oms_order_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单编码',
  `source_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '来源编码',
  `flag` tinyint(4) DEFAULT '0' COMMENT '订单状态',
  `merger` tinyint(4) DEFAULT '0' COMMENT '合并状态',
  `split` tinyint(4) DEFAULT '0' COMMENT '拆分状态',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态(0:有效,1:无效)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单状态表';

-- ----------------------------
-- Table structure for oms_order_typeinfo
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_typeinfo`;
CREATE TABLE `oms_order_typeinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单编码',
  `source_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '来源单号',
  `aim_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '目标单号',
  `source_type` tinyint(4) DEFAULT '0' COMMENT '来源类型',
  `order_type` tinyint(4) DEFAULT '0' COMMENT '单据类型(线上订单、线下订单、特殊订单)',
  `delivery_type` tinyint(4) DEFAULT '0' COMMENT '发货类型(正常发货、第三方发货、刷单发货、刷单不发货)',
  `outbound_type` tinyint(4) DEFAULT '0' COMMENT '出库类型(正常出库、补货出库、换货出库)',
  `cod` tinyint(4) DEFAULT '1' COMMENT '货到付款',
  `invoice` tinyint(4) DEFAULT '0' COMMENT '发票申请',
  `level` tinyint(4) DEFAULT '0' COMMENT '发货级别(正常、加急、特急)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单类型信息表';

-- ----------------------------
-- Table structure for oms_shop_commodity
-- ----------------------------
DROP TABLE IF EXISTS `oms_shop_commodity`;
CREATE TABLE `oms_shop_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '店铺编码',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `commodity` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `num_iid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品数字ID',
  `outer_iid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商家外部编码',
  `sku_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '平台skuID',
  `outer_sku_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '外部Sku编号',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='铺货关系';

-- ----------------------------
-- Table structure for oms_shop_interfaces
-- ----------------------------
DROP TABLE IF EXISTS `oms_shop_interfaces`;
CREATE TABLE `oms_shop_interfaces` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '店铺编码',
  `platform` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '平台编码',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `appk` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'appkey',
  `secr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'sercret',
  `toke` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'token',
  `surl` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '请求地址',
  `jklx` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型',
  `zdxz` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否自动下载订单(0是,1否)',
  `xdcl` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '下载订单策略',
  `scjs` datetime(3) DEFAULT NULL COMMENT '上次下载订单结束时间',
  `ycfz` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '延迟抓单分钟数',
  `xdqz` int(5) DEFAULT '0' COMMENT '下单前置分钟数',
  `xdhz` int(5) DEFAULT '0' COMMENT '下单后置分钟数',
  `bczd` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '补偿抓单是否开启(0是,1否)',
  `bcmi` int(11) DEFAULT '0' COMMENT '补偿抓单下载距今多少分钟前的订单',
  `fhhc` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否自动回传发货状态(0是,1否)',
  `odmx` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否回传订单明细(0是,1否)',
  `sckc` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否自动上传库存(0是,1否)',
  `tktz` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否自动下载退货退款通知(0是,1否)',
  `sctk` datetime(3) DEFAULT NULL COMMENT '上次下载退款通知结束时间',
  `tzqz` int(5) DEFAULT '0' COMMENT '下载通知前置分钟数',
  `tzhz` int(5) DEFAULT '0' COMMENT '下载通知后置分钟数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shop` (`shop`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店铺接口设置';

-- ----------------------------
-- Table structure for oms_stock
-- ----------------------------
DROP TABLE IF EXISTS `oms_stock`;
CREATE TABLE `oms_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `warehouse` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库编码',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主编码',
  `commodity` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='库存信息';

-- ----------------------------
-- Table structure for oms_stock_lock
-- ----------------------------
DROP TABLE IF EXISTS `oms_stock_lock`;
CREATE TABLE `oms_stock_lock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `warehouse` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库编码',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主编码',
  `order_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `commodity` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `row_number` bigint(20) DEFAULT NULL COMMENT '商品行序号',
  `module` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '业务模块',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='库存锁定';

-- ----------------------------
-- Table structure for oms_strategy_audit
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit`;
CREATE TABLE `oms_strategy_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略名称',
  `source` tinyint(4) DEFAULT '0' COMMENT '来源单号相同拦截开启',
  `address_valid` tinyint(4) DEFAULT '0' COMMENT '地址有效性校验开启',
  `buy_message` tinyint(4) DEFAULT '0' COMMENT '买家留言拦截开启',
  `seller_message` tinyint(4) DEFAULT '0' COMMENT '卖家留言拦截开启',
  `seller_message_match` tinyint(4) DEFAULT '0' COMMENT '卖家留言匹配开启',
  `cod` tinyint(4) DEFAULT '0' COMMENT '货到付款拦截开启',
  `seller_flag` tinyint(4) DEFAULT '0' COMMENT '卖家备注旗帜拦截开启',
  `refund` tinyint(4) DEFAULT '0' COMMENT '退款拦截开启',
  `mark` varchar(50) COLLATE utf8_bin DEFAULT '0' COMMENT '标记拦截开启(多选)',
  `amount` tinyint(4) DEFAULT '0' COMMENT '金额拦截类型',
  `amount_min` decimal(20,6) DEFAULT NULL COMMENT '金额拦截最小值',
  `amount_max` decimal(20,6) DEFAULT NULL COMMENT '金额拦截最大值',
  `time_range` tinyint(4) DEFAULT '0' COMMENT '时间范围拦截类型拦截',
  `time_start` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '时间拦截起',
  `time_end` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '时间拦截止',
  `number` tinyint(4) DEFAULT '0' COMMENT '数字拦截类型',
  `number_min` decimal(20,6) DEFAULT NULL COMMENT '数字拦截最小值',
  `number_max` decimal(20,6) DEFAULT NULL COMMENT '数字拦截最小值',
  `valid_date_type` tinyint(4) DEFAULT '0' COMMENT '有效期类型拦截',
  `valid_date` int(11) DEFAULT NULL COMMENT '有效期(天)',
  `auto_audit_type` tinyint(4) DEFAULT '0' COMMENT '自动审核等待时间类型',
  `wait_minute` int(11) DEFAULT '0' COMMENT '等待时间(分钟)',
  `profit_value` decimal(20,6) DEFAULT NULL COMMENT '毛利率拦截值',
  `order_intercept` tinyint(4) DEFAULT '1' COMMENT '特定信息拦截是否开启',
  `commodity_intercept` tinyint(4) DEFAULT '1' COMMENT '指定商品拦截是否开启',
  `address_intercept` tinyint(4) DEFAULT '1' COMMENT '特殊地址拦截是否开启',
  `combo_intercept` tinyint(4) DEFAULT '1' COMMENT '组合信息拦截是否开启',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略';

-- ----------------------------
-- Table structure for oms_strategy_audit_combo
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit_combo`;
CREATE TABLE `oms_strategy_audit_combo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `number` int(11) DEFAULT '0' COMMENT '满足条件个数',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略组合信息拦截';

-- ----------------------------
-- Table structure for oms_strategy_audit_combo_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit_combo_detail`;
CREATE TABLE `oms_strategy_audit_combo_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `combo_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应combo表自增id',
  `type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '属性类型(订单主信息/订单用户信息/订单支付信息...)',
  `field` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规则对应的字段',
  `requirement` varchar(24) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '条件(大于/小于/等于/不等于/包含/正则)',
  `value_code` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(多条用英文逗号分隔)',
  `value_name` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(中文含义)',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略组合信息明细';

-- ----------------------------
-- Table structure for oms_strategy_audit_commodity
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit_commodity`;
CREATE TABLE `oms_strategy_audit_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `type` tinyint(4) DEFAULT '0' COMMENT '商品类型',
  `value` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '信息值',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略指定商品拦截';

-- ----------------------------
-- Table structure for oms_strategy_audit_shop
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit_shop`;
CREATE TABLE `oms_strategy_audit_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `shop` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '适用店铺',
  `auto` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '开启自动(0:开启,1:关闭)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略适用店铺';

-- ----------------------------
-- Table structure for oms_strategy_audit_special
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_audit_special`;
CREATE TABLE `oms_strategy_audit_special` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `type` tinyint(4) DEFAULT '0' COMMENT '信息类型',
  `value` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '信息值',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审单策略特定信息拦截';

-- ----------------------------
-- Table structure for oms_strategy_convert
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_convert`;
CREATE TABLE `oms_strategy_convert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略名称',
  `trigger_node` tinyint(4) DEFAULT NULL COMMENT '触发节点',
  `match_commodity` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配商品方式(0:外部编码,1:铺货关系)',
  `process` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '流程方式(0:人工触发,1:无逻辑转发,2:状态机触发,3:调度触发)',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='转单策略';

-- ----------------------------
-- Table structure for oms_strategy_convert_shop
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_convert_shop`;
CREATE TABLE `oms_strategy_convert_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `shop` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '适用店铺',
  `auto` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '开启自动(0:开启,1:关闭)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='转换策略适用店铺';

-- ----------------------------
-- Table structure for oms_strategy_distribution
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution`;
CREATE TABLE `oms_strategy_distribution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '策略名称',
  `pre_execute` tinyint(4) DEFAULT '0' COMMENT '预分配开启',
  `warehouse_sku_split` tinyint(4) DEFAULT '0' COMMENT '仓库商品拆分开启',
  `special_sku_split` tinyint(4) DEFAULT '0' COMMENT '特殊商品拆分开启',
  `category_sku_split` tinyint(4) DEFAULT '0' COMMENT '类别商品拆分开启',
  `stock_split` tinyint(4) DEFAULT '0' COMMENT '库存不足拆分开启',
  `weight_split` tinyint(4) DEFAULT '0' COMMENT '订单重量拆分开启',
  `lock_model` tinyint(4) DEFAULT '0' COMMENT '锁定库存模式',
  `warehouse` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '默认仓库',
  `logistics` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '默认物流商',
  `re_warehouse` tinyint(4) DEFAULT '0' COMMENT '重新分配仓库',
  `re_logistics` tinyint(4) DEFAULT '0' COMMENT '重新分配物流',
  `wait_time` int(11) DEFAULT '0' COMMENT '自动配货等待时间(分钟)',
  `model` tinyint(4) DEFAULT '0' COMMENT '配货模式',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略';

-- ----------------------------
-- Table structure for oms_strategy_distribution_category
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_category`;
CREATE TABLE `oms_strategy_distribution_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略类别商品拆分';

-- ----------------------------
-- Table structure for oms_strategy_distribution_category_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_category_detail`;
CREATE TABLE `oms_strategy_distribution_category_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应category表自增id',
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `category` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '类别编码',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略类别商品拆分明细';

-- ----------------------------
-- Table structure for oms_strategy_distribution_pre
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_pre`;
CREATE TABLE `oms_strategy_distribution_pre` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `number` int(11) DEFAULT '0' COMMENT '满足条件个数',
  `warehouse_sku_split` tinyint(4) DEFAULT '0' COMMENT '参与仓库商品拆分',
  `special_sku_split` tinyint(4) DEFAULT '0' COMMENT '参与特殊商品拆分',
  `category_sku_split` tinyint(4) DEFAULT '0' COMMENT '参与类别商品拆分',
  `stock_split` tinyint(4) DEFAULT '0' COMMENT '参与库存不足拆分',
  `weight_split` tinyint(4) DEFAULT '0' COMMENT '参与订单重量拆分',
  `lock_model` tinyint(4) DEFAULT '0' COMMENT '锁定库存模式',
  `warehouse` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '发货仓库编码',
  `logistics` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '物流商编码',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略预分配信息';

-- ----------------------------
-- Table structure for oms_strategy_distribution_pre_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_pre_detail`;
CREATE TABLE `oms_strategy_distribution_pre_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应combo表自增id',
  `type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '属性类型(订单主信息/订单用户信息/订单支付信息...)',
  `field` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规则对应的字段',
  `requirement` varchar(24) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '条件(大于/小于/等于/不等于/包含/正则)',
  `value_code` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(多条用英文逗号分隔)',
  `value_name` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(中文含义)',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略预分配信息明细';

-- ----------------------------
-- Table structure for oms_strategy_distribution_shop
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_shop`;
CREATE TABLE `oms_strategy_distribution_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `shop` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '适用店铺',
  `auto` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '开启自动(0:开启,1:关闭)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略适用店铺';

-- ----------------------------
-- Table structure for oms_strategy_distribution_special
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_special`;
CREATE TABLE `oms_strategy_distribution_special` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `gna` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略特殊商品拆分';

-- ----------------------------
-- Table structure for oms_strategy_distribution_special_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_special_detail`;
CREATE TABLE `oms_strategy_distribution_special_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `special_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应special表自增id',
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `commodity` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '商品编码',
  `qty` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数量',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略特殊商品拆分明细';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_area
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_area`;
CREATE TABLE `oms_strategy_distribution_warehouse_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `nation` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人国家',
  `province` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人省/州',
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人市',
  `district` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人区县',
  `arrive` tinyint(4) DEFAULT '0' COMMENT '是否到达',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略分仓覆盖区域规则';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_available
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_available`;
CREATE TABLE `oms_strategy_distribution_warehouse_available` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `auto` tinyint(4) DEFAULT '0' COMMENT '自动推送',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略分仓可用仓库';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_logistics
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_logistics`;
CREATE TABLE `oms_strategy_distribution_warehouse_logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `logistics` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '物流编码',
  `arrive` tinyint(4) DEFAULT '0' COMMENT '是否到达',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略分仓物流规则';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_rule
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_rule`;
CREATE TABLE `oms_strategy_distribution_warehouse_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `rule_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '规则编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `must` tinyint(4) DEFAULT '0' COMMENT '是否必须满足',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略分仓规则列表';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_sku
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_sku`;
CREATE TABLE `oms_strategy_distribution_warehouse_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `sku` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'sku编码',
  `split` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否拆分',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略仓库SKU信息';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_special
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_special`;
CREATE TABLE `oms_strategy_distribution_warehouse_special` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '属性类型(订单主信息/订单用户信息/订单支付信息...)',
  `field` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规则对应的字段',
  `requirement` varchar(24) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '条件(大于/小于/等于/不等于/包含/正则)',
  `value_code` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(多条用英文逗号分隔)',
  `value_name` mediumtext COLLATE utf8_bin NOT NULL COMMENT '匹配值(中文含义)',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '规则说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略特定信息分仓';

-- ----------------------------
-- Table structure for oms_strategy_distribution_warehouse_weight
-- ----------------------------
DROP TABLE IF EXISTS `oms_strategy_distribution_warehouse_weight`;
CREATE TABLE `oms_strategy_distribution_warehouse_weight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gco` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略编码',
  `warehouse` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '仓库编码',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `nation` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人国家',
  `province` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人省/州',
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人市',
  `district` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收件人区县',
  `weight_min` decimal(20,6) DEFAULT NULL COMMENT '重量范围最小值',
  `weight_max` decimal(20,6) DEFAULT NULL COMMENT '重量范围最大值',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '状态（0启用 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配货策略分仓重量规则';

-- ----------------------------
-- Table structure for oms_trace_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_trace_log`;
CREATE TABLE `oms_trace_log` (
  `id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单流水编号',
  `module` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块编码',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作类型',
  `level` tinyint(4) DEFAULT '0' COMMENT '日志级别',
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单轨迹日志';

-- ----------------------------
-- Table structure for oms_trade
-- ----------------------------
DROP TABLE IF EXISTS `oms_trade`;
CREATE TABLE `oms_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '平台交易单号',
  `status` tinyint(4) DEFAULT '5' COMMENT '交易状态',
  `modified` datetime(3) DEFAULT NULL COMMENT '交易修改时间',
  `hashcode` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '数据校验字段',
  `response` text COLLATE utf8_bin COMMENT '请求返回消息',
  `shop` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `owner` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '货主编码',
  `flag` tinyint(4) DEFAULT '0' COMMENT '转单状态',
  `frequency` int(5) DEFAULT '0' COMMENT '更新次数',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=978 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易订单';

-- ----------------------------
-- Table structure for oms_trade_standard
-- ----------------------------
DROP TABLE IF EXISTS `oms_trade_standard`;
CREATE TABLE `oms_trade_standard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '平台交易单号',
  `modified` datetime(3) DEFAULT NULL COMMENT '交易修改时间',
  `standard` text COLLATE utf8_bin COMMENT '标准订单报文',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1041 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易订单快照';

-- ----------------------------
-- Table structure for oms_warehouse_interfaces
-- ----------------------------
DROP TABLE IF EXISTS `oms_warehouse_interfaces`;
CREATE TABLE `oms_warehouse_interfaces` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `warehouse` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库编码',
  `encr` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否加密(0：是  1：否)',
  `appk` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'appkey',
  `secr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '加密密串',
  `toke` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'token',
  `surl` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '请求地址',
  `fmat` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据格式(JSON/XML)',
  `jklx` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `warehouse` (`warehouse`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='仓库接口设置';

SET FOREIGN_KEY_CHECKS = 1;
