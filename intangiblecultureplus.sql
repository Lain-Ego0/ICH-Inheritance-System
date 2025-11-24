/*
 Navicat Premium Data Transfer

 Source Server         : 系统
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : intangiblecultureplus

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 01/04/2025 21:41:48
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`
(
    `Id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '活动主键',
    `CreationTime`    timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`       int(11) NULL DEFAULT NULL COMMENT '创建人',
    `UserId`          int(11) NULL DEFAULT NULL COMMENT '发起人',
    `ActivityType`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动类型',
    `Location`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地点',
    `EndJoinTime`     datetime(0) NULL DEFAULT NULL COMMENT '截至报名时间',
    `Title`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动标题',
    `BeginJoinTime`   datetime(0) NULL DEFAULT NULL COMMENT '起始报名时间',
    `Content`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '活动内容',
    `BeginActiveTime` datetime(0) NULL DEFAULT NULL COMMENT '开始活动时间',
    `ActivityStatus`  int(11) NULL DEFAULT NULL COMMENT '活动状态枚举枚举',
    `LimitPeople`     int(11) NULL DEFAULT NULL COMMENT '限制人数',
    `Cover`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
    `EndActiveTime`   datetime(0) NULL DEFAULT NULL COMMENT '结束活动时间',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity`
VALUES (1, '2025-04-01 09:15:22', 1, NULL, '传统手工艺', '浙江省杭州市西湖区非遗展示馆', '2025-04-15 18:00:00',
        '西湖绸伞制作技艺传承活动', '2025-04-01 08:00:00',
        '邀请国家级非遗传承人现场教授西湖绸伞制作技艺，体验传统手工艺魅力。', '2025-04-20 09:00:00', 2, 30,
        'http://localhost:7245/1743479838395/9.jpg', '2025-04-20 17:00:00');
INSERT INTO `activity`
VALUES (2, '2025-04-01 10:30:45', 1, NULL, '戏曲表演', '江苏省昆山市周庄古戏台', '2025-05-10 20:00:00',
        '昆曲经典剧目《牡丹亭》展演', '2025-04-10 09:00:00',
        '国家级昆曲表演艺术家带来经典剧目《牡丹亭》全本演出，感受非遗戏曲艺术。', '2025-05-15 19:30:00', 1, 200,
        'http://localhost:7245/1743479834248/3.jpg', '2025-05-15 22:00:00');
INSERT INTO `activity`
VALUES (3, '2025-04-01 11:05:33', 1, NULL, '传统美食', '四川省成都市锦里古街', '2025-06-01 12:00:00',
        '川菜非遗技艺体验课', '2025-05-01 08:00:00', '跟随川菜非遗传承人学习宫保鸡丁、麻婆豆腐等经典川菜制作技艺。',
        '2025-06-08 10:00:00', 1, 20, 'http://localhost:7245/1743479828531/10.jpg', '2025-06-08 15:00:00');
INSERT INTO `activity`
VALUES (4, '2025-04-01 13:22:18', 1, NULL, '民间舞蹈', '云南省大理市古城广场', '2025-04-25 17:00:00',
        '白族三道茶歌舞展演', '2025-04-10 08:00:00', '体验白族传统三道茶礼仪，欣赏白族歌舞表演，感受少数民族非遗文化。',
        '2025-04-28 14:00:00', 1, 100, 'http://localhost:7245/1743479821883/7.jpg', '2025-04-28 17:00:00');
INSERT INTO `activity`
VALUES (5, '2025-04-01 14:40:56', 1, NULL, '传统医药', '北京市东城区同仁堂博物馆', '2025-07-15 18:00:00',
        '中医针灸非遗技艺讲座', '2025-06-01 09:00:00', '国家级非遗传承人讲解中医针灸理论，现场演示传统针灸技法。',
        '2025-07-20 13:30:00', 1, 50, 'http://localhost:7245/1743479817981/2.jpg', '2025-07-20 16:30:00');
INSERT INTO `activity`
VALUES (6, '2025-04-01 15:12:07', 1, NULL, '民间文学', '湖南省湘西土家族苗族自治州', '2025-08-10 20:00:00',
        '苗族古歌传承人讲座', '2025-07-01 08:00:00', '苗族古歌国家级传承人现场演唱并讲解苗族古歌的文化内涵与传承。',
        '2025-08-15 09:30:00', 1, 80, 'http://localhost:7245/1743479810461/14.jpg', '2025-08-15 11:30:00');
INSERT INTO `activity`
VALUES (7, '2025-04-01 16:33:29', 1, NULL, '传统技艺', '江西省景德镇市陶瓷博物馆', '2025-09-05 17:00:00',
        '景德镇手工制瓷技艺体验', '2025-08-01 09:00:00', '在非遗传承人指导下体验拉坯、画坯等景德镇传统手工制瓷工序。',
        '2025-09-10 08:30:00', 1, 15, 'http://localhost:7245/1743479802979/3.jpg', '2025-09-10 16:30:00');
INSERT INTO `activity`
VALUES (8, '2025-04-01 17:45:11', 1, NULL, '民俗活动', '陕西省延安市黄帝陵', '2025-10-01 12:00:00',
        '黄帝陵祭祖大典观摩', '2025-09-01 08:00:00', '组织参观国家级非遗项目黄帝陵祭祖大典，了解传统祭祀文化。',
        '2025-10-04 06:00:00', 1, 150, 'http://localhost:7245/1743479795786/1.jpg', '2025-10-04 12:00:00');
INSERT INTO `activity`
VALUES (9, '2025-04-01 18:20:44', 1, NULL, '传统音乐', '福建省泉州市南音阁', '2025-11-10 20:00:00', '南音古乐欣赏会',
        '2025-10-10 09:00:00', '聆听千年古乐南音表演，了解这一世界级非物质文化遗产的艺术特色。', '2025-11-15 19:00:00', 1,
        120, 'http://localhost:7245/1743479790407/15.jpg', '2025-11-15 21:30:00');
INSERT INTO `activity`
VALUES (10, '2025-04-01 19:05:37', 1, NULL, '传统体育', '河南省登封市少林寺', '2025-12-05 17:00:00', '少林功夫体验营',
        '2025-11-01 08:00:00', '在少林寺武僧指导下学习基础少林功夫，体验禅武合一的文化精髓。', '2025-12-10 07:00:00', 1,
        40, 'http://localhost:7245/1743479783406/14.jpg', '2025-12-10 17:00:00');

-- ----------------------------
-- Table structure for activityrecord
-- ----------------------------
DROP TABLE IF EXISTS `activityrecord`;
CREATE TABLE `activityrecord`
(
    `Id`                  int(11) NOT NULL AUTO_INCREMENT COMMENT '活动登记主键',
    `CreationTime`        timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`           int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CheckTime`           datetime(0) NULL DEFAULT NULL COMMENT '签到时间',
    `JoinUserId`          int(11) NULL DEFAULT NULL COMMENT '报名人',
    `IsCheck`             tinyint(1) NULL DEFAULT NULL COMMENT '是否签到',
    `IsCancel`            tinyint(1) NULL DEFAULT NULL COMMENT '是否取消',
    `ActivityId`          int(11) NULL DEFAULT NULL COMMENT '活动',
    `Comment`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评价',
    `CheckCode`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '验证码',
    `ActivityAuditStatus` int(11) NULL DEFAULT NULL COMMENT '审核状态枚举枚举',
    `CommentScore` double(20, 8) NULL DEFAULT NULL COMMENT '评分',
    `CancelTime`          datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
    `AuditResult`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核结果',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '活动登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activityrecord
-- ----------------------------
INSERT INTO `activityrecord`
VALUES (1, '2025-04-01 21:18:19', 2, NULL, 2, 0, 0, 1, NULL, '695287289', 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for appuser
-- ----------------------------
DROP TABLE IF EXISTS `appuser`;
CREATE TABLE `appuser`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Password`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
    `Email`        varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
    `RoleType`     int(11) NULL DEFAULT NULL COMMENT '角色',
    `PhoneNumber`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
    `ImageUrls`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
    `Birth`        datetime(0) NULL DEFAULT NULL COMMENT '出生年月',
    `Name`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    `UserName`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appuser
-- ----------------------------
INSERT INTO `appuser`
VALUES (1, '2025-03-26 20:05:00', 0, 'admin', NULL, 1, NULL, NULL, NULL, '管理员', 'admin');
INSERT INTO `appuser`
VALUES (2, '2025-03-26 20:55:14', 0, 'keke', '806105498@qq.com', 2, '15616262411', NULL, NULL, '从前那个少年', 'keke');
INSERT INTO `appuser`
VALUES (3, '2025-04-01 12:12:24', 0, 'xinxin', '509217890@qq.com', 2, '15616262422', NULL, NULL, '欣欣', 'xinxin');

-- ----------------------------
-- Table structure for buycard
-- ----------------------------
DROP TABLE IF EXISTS `buycard`;
CREATE TABLE `buycard`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Qty`          int(11) NULL DEFAULT NULL COMMENT '数量',
    `BelongUserId` int(11) NULL DEFAULT NULL COMMENT '所属用户',
    `GoodId`       int(11) NULL DEFAULT NULL COMMENT '商品',
    `ShopId`       int(11) NULL DEFAULT NULL COMMENT '店铺',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of buycard
-- ----------------------------
INSERT INTO `buycard`
VALUES (1, '2025-03-27 09:06:00', 2, 1, 2, 1, 1);
INSERT INTO `buycard`
VALUES (2, '2025-03-27 09:06:03', 2, 1, 2, 3, 1);
INSERT INTO `buycard`
VALUES (3, '2025-04-01 12:11:00', 2, 1, 2, 7, 1);
INSERT INTO `buycard`
VALUES (4, '2025-04-01 12:57:55', 3, 1, 3, 7, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `Id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
    `CreationTime`  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`     int(11) NULL DEFAULT NULL COMMENT '创建人',
    `RelativeId`    int(11) NULL DEFAULT NULL COMMENT '关联id',
    `Type`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
    `NickName`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    `RootCommentId` int(11) NULL DEFAULT NULL COMMENT '根评论',
    `TargetUserId`  int(11) NULL DEFAULT NULL COMMENT '回复用户',
    `HeadImage`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
    `SendUserId`    int(11) NULL DEFAULT NULL COMMENT '发送用户',
    `Content`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment`
VALUES (1, '2025-03-27 09:06:13', 2, 8, 'Topic', '咳咳', 0, NULL, NULL, 2, '测试');

-- ----------------------------
-- Table structure for commentlove
-- ----------------------------
DROP TABLE IF EXISTS `commentlove`;
CREATE TABLE `commentlove`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '评论点赞记录主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Type`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
    `RelativeId`   int(11) NULL DEFAULT NULL COMMENT '关联id',
    `CommentId`    int(11) NULL DEFAULT NULL COMMENT '点赞评论',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '点赞人',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论点赞记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commentlove
-- ----------------------------
INSERT INTO `commentlove`
VALUES (1, '2025-03-27 09:06:14', 2, '0', 8, 1, 2);

-- ----------------------------
-- Table structure for couse
-- ----------------------------
DROP TABLE IF EXISTS `couse`;
CREATE TABLE `couse`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '课程内容',
    `CouseTypeId`  int(11) NULL DEFAULT NULL COMMENT '课程分类',
    `Cover`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
    `IsPutaway`    tinyint(1) NULL DEFAULT NULL COMMENT '是否上架',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名称',
    `Price` double(18, 6) NULL DEFAULT NULL COMMENT '课程价格',
    `IsFree`       bit(1) NULL DEFAULT NULL COMMENT '是否免费',
    `CouseId`      int(11) NULL DEFAULT NULL COMMENT '课程',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of couse
-- ----------------------------
INSERT INTO `couse`
VALUES (1, '2025-04-01 09:15:22', 1,
        '系统学习景德镇传统手工制瓷72道工序，包含拉坯、利坯、画坯、施釉和烧窑等核心技艺。课程由国家级非遗传承人亲自指导。', 1,
        'http://localhost:7245/1743482976264/7.jpg', 1, '景德镇手工制瓷技艺全流程', 599.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (2, '2025-04-01 10:30:45', 1, '昆曲经典剧目《牡丹亭》唱腔与身段教学，包含水磨腔发声技巧和戏曲表演基本功训练。', 2,
        'http://localhost:7245/1743482969037/15.jpg', 1, '昆曲《牡丹亭》表演艺术', 399.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (3, '2025-04-01 11:05:33', 1, '白族传统舞蹈三道茶歌舞系统教学，包含舞蹈基本动作、音乐节奏和茶礼文化内涵解析。', 3,
        'http://localhost:7245/1743482964066/15.jpg', 1, '白族三道茶歌舞传承课程', 0.000000, b'1', NULL);
INSERT INTO `couse`
VALUES (4, '2025-04-01 13:22:18', 1, '中医针灸非遗技艺系统教学，包含经络理论、针刺手法和常见病症的针灸治疗方案。', 4,
        'http://localhost:7245/1743482959050/12.jpg', 1, '传统中医针灸技法精讲', 899.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (5, '2025-04-01 14:40:56', 1, '黄帝陵祭祖大典仪式流程与文化内涵深度解析，包含祭祀礼仪、乐舞表演和服饰文化。', 5,
        'http://localhost:7245/1743482951388/1.jpg', 1, '黄帝陵祭祖大典文化解读', 299.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (6, '2025-04-01 15:12:07', 1, '西湖绸伞制作技艺全流程教学，包含伞骨制作、伞面绘制和组装工艺等32道工序。', 1,
        'http://localhost:7245/1743482939383/16.jpg', 1, '西湖绸伞制作技艺传承班', 499.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (7, '2025-04-01 16:33:29', 1, '南音古乐演奏技法教学，包含洞箫、琵琶等乐器演奏技巧和传统曲目解读。', 2,
        'http://localhost:7245/1743482931623/3.jpg', 1, '泉州南音古乐演奏艺术', 0.000000, b'1', NULL);
INSERT INTO `couse`
VALUES (8, '2025-04-01 17:45:11', 1, '苗族古歌演唱技艺与文化解读，包含发声方法、歌词内涵和民族历史传承。', 3,
        'http://localhost:7245/1743482924279/2.jpg', 1, '苗族古歌传承人课堂', 199.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (9, '2025-04-01 18:20:44', 1, '川菜非遗技艺系统教学，包含宫保鸡丁、麻婆豆腐等24道经典川菜的制作工艺。', 5,
        'http://localhost:7245/1743482919232/12.jpg', 1, '川菜非遗技艺大师课', 699.000000, b'0', NULL);
INSERT INTO `couse`
VALUES (10, '2025-04-01 19:05:37', 1, '少林传统功夫基础训练课程，包含易筋经、罗汉拳等基本功法和禅武文化讲解。', 5,
        'http://localhost:7245/1743482910532/15.jpg', 0, '少林功夫入门精修班', 799.000000, b'0', NULL);

-- ----------------------------
-- Table structure for couseclock
-- ----------------------------
DROP TABLE IF EXISTS `couseclock`;
CREATE TABLE `couseclock`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程打卡主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '用户',
    `CouseId`      int(11) NULL DEFAULT NULL COMMENT '课程打卡',
    `ClockTime`    datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
    `CouseStepId`  int(11) NULL DEFAULT NULL COMMENT '打卡章节',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程打卡表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cousecollect
-- ----------------------------
DROP TABLE IF EXISTS `cousecollect`;
CREATE TABLE `cousecollect`
(
    `Id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '课程收藏主键',
    `CreationTime`  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`     int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CouseId`       int(11) NULL DEFAULT NULL COMMENT '课程',
    `CollectUserId` int(11) NULL DEFAULT NULL COMMENT '收藏人',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cousecollect
-- ----------------------------
INSERT INTO `cousecollect`
VALUES (7, '2025-04-01 12:53:50', 3, 10, 3);
INSERT INTO `cousecollect`
VALUES (8, '2025-04-01 13:09:16', 3, 9, 3);

-- ----------------------------
-- Table structure for cousecomment
-- ----------------------------
DROP TABLE IF EXISTS `cousecomment`;
CREATE TABLE `cousecomment`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程评价主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CouseId`      int(11) NULL DEFAULT NULL COMMENT '课程',
    `FeatureCommentScore` double(20, 8) NULL DEFAULT NULL COMMENT '风格评分',
    `ExpressionCommentScore` double(20, 8) NULL DEFAULT NULL COMMENT '表达评分',
    `QualityCommentScore` double(20, 8) NULL DEFAULT NULL COMMENT '质量评分',
    `Comment`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评语',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '评语人',
    `CouseStepId`  int(11) NULL DEFAULT NULL COMMENT '课程章节',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cousecomment
-- ----------------------------
INSERT INTO `cousecomment`
VALUES (4, '2025-04-01 12:55:14', 3, 10, 5.00000000, 5.00000000, 5.00000000, '真的很不错', 3, 37);

-- ----------------------------
-- Table structure for couseorder
-- ----------------------------
DROP TABLE IF EXISTS `couseorder`;
CREATE TABLE `couseorder`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程订单主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CouseId`      int(11) NULL DEFAULT NULL COMMENT '课程',
    `No`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
    `PayMoney` double(20, 5) NULL DEFAULT NULL COMMENT '支付金额',
    `PayType`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付类型',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '下单人',
    `IsReturn`     tinyint(1) NULL DEFAULT NULL COMMENT '是否退款',
    `ReturnTime`   datetime(0) NULL DEFAULT NULL COMMENT '退款时间',
    `Comment`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评价',
    `CommentScore` double(20, 5) NULL DEFAULT NULL COMMENT '评分',
    `PayTime`      datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
    `OrderStatus`  int(11) NULL DEFAULT NULL COMMENT '订单状态',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of couseorder
-- ----------------------------
INSERT INTO `couseorder`
VALUES (7, '2025-04-01 12:54:49', 3, 10, 'D20250401125448861025', 799.00000, '银行卡支付', 3, 0, NULL, NULL, NULL, NULL,
        2);
INSERT INTO `couseorder`
VALUES (8, '2025-04-01 21:15:52', 2, 9, 'D20250401211552179826', 699.00000, '微信支付', 2, 0, NULL, NULL, NULL, NULL,
        2);
INSERT INTO `couseorder`
VALUES (9, '2025-04-01 21:16:08', 2, 8, 'D20250401211607595767', 199.00000, '银行卡支付', 2, 0, NULL, NULL, NULL, NULL,
        2);

-- ----------------------------
-- Table structure for cousestep
-- ----------------------------
DROP TABLE IF EXISTS `cousestep`;
CREATE TABLE `cousestep`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程章节主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Durtion` double(20, 5) NULL DEFAULT NULL COMMENT '小节时长',
    `VideoUrls`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '小节视频',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程小节',
    `CouseId`      int(11) NULL DEFAULT NULL COMMENT '所属课程',
    `FileUrls`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附件',
    `Cover`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '小节封面',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程章节表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cousestep
-- ----------------------------
INSERT INTO `cousestep`
VALUES (1, '2025-04-01 09:20:00', 1, 45.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '景德镇制瓷历史与文化渊源', 1,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (2, '2025-04-01 09:25:00', 1, 68.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '瓷土选料与精炼工艺', 1,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (3, '2025-04-01 09:30:00', 1, 72.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '传统拉坯成型技法实操', 1,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (4, '2025-04-01 09:35:00', 1, 58.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '青花绘制基础笔法', 1,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (5, '2025-04-01 10:35:00', 1, 52.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '昆曲水磨腔发声原理', 2,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (6, '2025-04-01 10:40:00', 1, 61.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '《游园惊梦》经典唱段解析', 2,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (7, '2025-04-01 10:45:00', 1, 48.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '戏曲身段基本功训练', 2,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (8, '2025-04-01 10:50:00', 1, 55.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '杜丽娘角色塑造技巧', 2,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (9, '2025-04-01 11:10:00', 1, 42.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '三道茶文化内涵解析', 3,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (10, '2025-04-01 11:15:00', 1, 38.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '茶礼仪式基础动作', 3,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (11, '2025-04-01 11:20:00', 1, 50.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '白族歌舞节奏训练', 3,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (12, '2025-04-01 11:25:00', 1, 45.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '完整表演编排实践', 3,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (13, '2025-04-01 13:25:00', 1, 65.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '经络学说基础理论', 4,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (14, '2025-04-01 13:30:00', 1, 70.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '常用穴位定位方法', 4,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (15, '2025-04-01 13:35:00', 1, 55.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '毫针基本操作规范', 4,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (16, '2025-04-01 13:40:00', 1, 62.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '常见病症针灸方案', 4,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (17, '2025-04-01 14:45:00', 1, 40.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '黄帝陵祭祀历史沿革', 5,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (18, '2025-04-01 14:50:00', 1, 35.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '祭典乐舞文化符号', 5,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (19, '2025-04-01 14:55:00', 1, 48.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '传统祭祀礼仪规范', 5,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (20, '2025-04-01 15:00:00', 1, 42.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '现代传承创新实践', 5,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (21, '2025-04-01 15:15:00', 1, 47.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '西湖绸伞艺术特色', 6,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (22, '2025-04-01 15:20:00', 1, 53.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '伞骨选材与加工工艺', 6,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (23, '2025-04-01 15:25:00', 1, 60.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '绸面绘画技法示范', 6,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (24, '2025-04-01 15:30:00', 1, 55.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '成品组装质量检验', 6,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (25, '2025-04-01 16:35:00', 1, 38.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '南音历史与传承谱系', 7,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (26, '2025-04-01 16:40:00', 1, 45.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '洞箫演奏基础指法', 7,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (27, '2025-04-01 16:45:00', 1, 52.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '琵琶弹奏技巧训练', 7,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (28, '2025-04-01 16:50:00', 1, 48.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '传统曲目《梅花操》解析', 7,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (29, '2025-04-01 17:50:00', 1, 42.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '苗族古歌文化价值', 8,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (30, '2025-04-01 17:55:00', 1, 37.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '古歌特殊发声方法', 8,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (31, '2025-04-01 18:00:00', 1, 50.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '《开天辟地》史诗解读', 8,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (32, '2025-04-01 18:05:00', 1, 45.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '口传心授教学实践', 8,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (33, '2025-04-01 18:25:00', 1, 55.00000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '川菜二十四味型解析', 9,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (34, '2025-04-01 18:30:00', 1, 62.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '经典宫保鸡丁制作', 9,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (35, '2025-04-01 18:35:00', 1, 58.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '麻婆豆腐工艺秘诀', 9,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (36, '2025-04-01 18:40:00', 1, 52.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '传统豆瓣酱制作技艺', 9,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (37, '2025-04-01 19:10:00', 1, 33958.33400, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '少林禅武文化渊源', 10,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (38, '2025-04-01 19:15:00', 1, 70.50000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '易筋经十二式教学', 10,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (39, '2025-04-01 19:20:00', 1, 55.25000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '罗汉拳基础套路', 10,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);
INSERT INTO `cousestep`
VALUES (40, '2025-04-01 19:25:00', 1, 62.75000, 'http://localhost:7245/1743483079588/测试内存稳定性.mp4',
        '传统器械基本功法', 10,
        'http://localhost:7245/1743482996318/20250202_143127.mp4,http://localhost:7245/1743483073202/测试内存稳定性.mp4',
        NULL);

-- ----------------------------
-- Table structure for cousestudyschedule
-- ----------------------------
DROP TABLE IF EXISTS `cousestudyschedule`;
CREATE TABLE `cousestudyschedule`
(
    `Id`             int(11) NOT NULL AUTO_INCREMENT COMMENT '课程学习进度主键',
    `CreationTime`   timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`      int(11) NULL DEFAULT NULL COMMENT '创建人',
    `LastUploadTime` datetime(0) NULL DEFAULT NULL COMMENT '最后上传时间',
    `StudyUserId`    int(11) NULL DEFAULT NULL COMMENT '学习人',
    `CouseId`        int(11) NULL DEFAULT NULL COMMENT '所属课程',
    `BeginTime`      datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
    `Durtion` double(20, 5) NULL DEFAULT NULL COMMENT '学习时长',
    `CouseStepId`    int(11) NULL DEFAULT NULL COMMENT '所属章节',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程学习进度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cousestudyschedule
-- ----------------------------
INSERT INTO `cousestudyschedule`
VALUES (3, '2025-04-01 12:56:27', 3, '2025-04-01 12:56:26', 3, 10, '2025-04-01 12:56:26', 8001.00000, 37);

-- ----------------------------
-- Table structure for cousetype
-- ----------------------------
DROP TABLE IF EXISTS `cousetype`;
CREATE TABLE `cousetype`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '课程分类主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cousetype
-- ----------------------------
INSERT INTO `cousetype`
VALUES (1, '2025-04-01 09:15:22', 1, '传统手工艺');
INSERT INTO `cousetype`
VALUES (2, '2025-04-01 10:30:45', 1, '戏曲曲艺');
INSERT INTO `cousetype`
VALUES (3, '2025-04-01 11:05:33', 1, '民间舞蹈');
INSERT INTO `cousetype`
VALUES (4, '2025-04-01 13:22:18', 1, '传统医药');
INSERT INTO `cousetype`
VALUES (5, '2025-04-01 14:40:56', 1, '民俗节庆');

-- ----------------------------
-- Table structure for cultural
-- ----------------------------
DROP TABLE IF EXISTS `cultural`;
CREATE TABLE `cultural`
(
    `Id`               int(11) NOT NULL AUTO_INCREMENT COMMENT '非遗文化主键',
    `CreationTime`     timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`        int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Name`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    `No`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
    `ImageUrls`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
    `RecordDate`       datetime(0) NULL DEFAULT NULL COMMENT '登记日期',
    `PlaceDeclaration` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申报地区',
    `ProtectionUnit`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '保护单位',
    `CulturalTypeId`   int(11) NULL DEFAULT NULL COMMENT '类别',
    `ProviceCityArea`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
    `BatchNo`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '批次',
    `InheritorId`      int(11) NULL DEFAULT NULL COMMENT '传人',
    `Content`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详细内容',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '非遗文化表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cultural
-- ----------------------------
INSERT INTO `cultural`
VALUES (1, '2025-03-26 20:38:18', 1, '古琴艺术', 'FY001', 'http://localhost:7245/1740401517834/0.jpg',
        '2020-05-15 00:00:00', '江苏省', '江苏省文化艺术研究院', 1, '江苏省,苏州市,姑苏区', '第一批', 1,
        '古琴艺术是中国传统音乐的代表，已有三千多年历史，被列入联合国教科文组织人类非物质文化遗产代表作名录。');
INSERT INTO `cultural`
VALUES (2, '2025-03-26 20:38:18', 1, '昆曲', 'FY002', 'http://localhost:7245/1740401523910/1.jpg',
        '2001-05-18 00:00:00', '江苏省', '江苏省昆剧院', 3, '江苏省,苏州市,昆山市', '第一批', 1,
        '昆曲是中国传统戏曲的瑰宝，被誉为“百戏之祖”，已有六百多年历史。');
INSERT INTO `cultural`
VALUES (3, '2025-03-26 20:38:18', 1, '京剧', 'FY003', 'http://localhost:7245/1740401529135/1.jpg',
        '2006-05-20 00:00:00', '北京市', '中国京剧艺术基金会', 3, '北京市,北京市,西城区', '第一批', 1,
        '京剧是中国国粹，融合了唱、念、做、打等多种表演形式，具有极高的艺术价值。');
INSERT INTO `cultural`
VALUES (4, '2025-03-26 20:38:18', 1, '太极拳', 'FY004', 'http://localhost:7245/1740401536771/0.jpg',
        '2020-12-17 00:00:00', '河南省', '河南省太极拳协会', 5, '河南省,焦作市,温县', '第五批', 1,
        '太极拳是中国传统武术的代表，兼具健身、防身和修身养性的功能。');
INSERT INTO `cultural`
VALUES (5, '2025-03-26 20:38:18', 1, '皮影戏', 'FY005', 'http://localhost:7245/1740401541333/0.jpg',
        '2011-11-27 00:00:00', '陕西省', '陕西省皮影艺术协会', 3, '陕西省,渭南市,华州区', '第三批', 1,
        '皮影戏是中国传统戏剧形式之一，通过灯光照射皮影人物进行表演，具有独特的艺术魅力。');
INSERT INTO `cultural`
VALUES (6, '2025-03-26 20:38:18', 1, '苗族古歌', 'FY006', 'http://localhost:7245/1740401546723/2.jpg',
        '2006-05-20 00:00:00', '贵州省', '贵州省苗族文化保护中心', 1, '贵州省,黔东南苗族侗族自治州,凯里市', '第一批', 1,
        '苗族古歌是苗族人民口传心授的古老歌谣，记录了苗族的历史、文化和生活。');
INSERT INTO `cultural`
VALUES (7, '2025-03-26 20:38:18', 1, '木偶戏', 'FY007', 'http://localhost:7245/1740401556939/6.jpg',
        '2006-05-20 00:00:00', '福建省', '福建省木偶艺术剧院', 3, '福建省,泉州市,鲤城区', '第一批', 1,
        '木偶戏是中国传统戏剧形式之一，通过操纵木偶进行表演，具有独特的艺术风格。');
INSERT INTO `cultural`
VALUES (8, '2025-03-26 20:38:18', 1, '端午节', 'FY008', 'http://localhost:7245/1740401564080/7.jpg',
        '2009-09-30 00:00:00', '湖北省', '湖北省非物质文化遗产保护中心', 5, '湖北省,宜昌市,秭归县', '第二批', 1,
        '端午节是中国传统节日，以纪念屈原为核心，包含赛龙舟、吃粽子等习俗。');
INSERT INTO `cultural`
VALUES (9, '2025-03-26 20:38:18', 1, '剪纸', 'FY009', 'http://localhost:7245/1740401568758/1.jpg',
        '2009-09-30 00:00:00', '河北省', '河北省民间文艺家协会', 5, '河北省,张家口市,蔚县', '第二批', 1,
        '剪纸是中国传统民间艺术，通过剪刀或刻刀在纸上创作出各种图案，具有浓厚的民俗特色。');
INSERT INTO `cultural`
VALUES (10, '2025-03-26 20:38:18', 1, '蒙古族长调民歌', 'FY010', 'http://localhost:7245/1740401575131/7.jpg',
        '2005-11-25 00:00:00', '内蒙古自治区', '内蒙古自治区文化艺术研究院', 1, '内蒙古自治区,锡林郭勒盟,锡林浩特市',
        '第一批', 1, '蒙古族长调民歌是蒙古族传统音乐的代表，以其悠扬的旋律和独特的演唱方式闻名。');

-- ----------------------------
-- Table structure for culturaltype
-- ----------------------------
DROP TABLE IF EXISTS `culturaltype`;
CREATE TABLE `culturaltype`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '非遗文化类别主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '非遗文化类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of culturaltype
-- ----------------------------
INSERT INTO `culturaltype`
VALUES (1, '2025-03-26 20:17:12', 1, '传统音乐');
INSERT INTO `culturaltype`
VALUES (2, '2025-03-26 20:17:12', 1, '传统舞蹈');
INSERT INTO `culturaltype`
VALUES (3, '2025-03-26 20:17:12', 1, '传统戏剧');
INSERT INTO `culturaltype`
VALUES (4, '2025-03-26 20:17:12', 1, '曲艺');
INSERT INTO `culturaltype`
VALUES (5, '2025-03-26 20:17:12', 1, '传统体育、游艺与杂技');
INSERT INTO `culturaltype`
VALUES (6, '2025-03-26 20:17:12', 1, '传统美术');
INSERT INTO `culturaltype`
VALUES (7, '2025-03-26 20:17:12', 1, '传统技艺');
INSERT INTO `culturaltype`
VALUES (8, '2025-03-26 20:17:12', 1, '传统医药');
INSERT INTO `culturaltype`
VALUES (9, '2025-03-26 20:17:12', 1, '民俗');
INSERT INTO `culturaltype`
VALUES (10, '2025-03-26 20:17:12', 1, '传统饮食文化');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`
(
    `Id`                 int(11) NOT NULL AUTO_INCREMENT COMMENT '商品主键',
    `CreationTime`       timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`          int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Price` double(20, 5) NULL DEFAULT NULL COMMENT '价格',
    `GoodTypeId`         int(11) NULL DEFAULT NULL COMMENT '分类',
    `Name`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    `Stock` double(20, 5) NULL DEFAULT NULL COMMENT '库存',
    `UserId`             int(11) NULL DEFAULT NULL COMMENT '所属用户',
    `Unit`               varchar(54) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
    `Content`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详情内容',
    `ReplenishmentLimit` int(11) NULL DEFAULT NULL COMMENT '库存下限',
    `ShopGoodTypeId`     int(11) NULL DEFAULT NULL COMMENT '商品店铺分类',
    `Cover`              varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
    `ImageUrls`          varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图',
    `ShopId`             int(11) NULL DEFAULT NULL COMMENT '所属店铺',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good`
VALUES (1, '2025-03-26 20:30:47', 1, 199.99000, 1, '手工刺绣团扇', 149.00000, NULL, '件',
        '精美手工刺绣团扇，采用传统苏绣工艺，图案精美，适合收藏或送礼。', 10, NULL,
        'http://localhost:7245/1740401160323/4.jpg',
        'http://localhost:7245/1740401163666/3.jpg,http://localhost:7245/1740401163667/4.jpg,http://localhost:7245/1740401163667/5.jpg,http://localhost:7245/1740401163667/7.jpg',
        1);
INSERT INTO `good`
VALUES (2, '2025-03-26 20:30:47', 1, 59.99000, 2, '非遗文化图鉴', 100.00000, NULL, '本',
        '详细介绍中国非物质文化遗产的图鉴书籍，图文并茂，内容丰富。', 20, NULL,
        'http://localhost:7245/1740401173571/16.jpg',
        'http://localhost:7245/1740401176276/2.jpg,http://localhost:7245/1740401176277/3.jpg,http://localhost:7245/1740401176281/4.jpg,http://localhost:7245/1740401176281/5.jpg',
        1);
INSERT INTO `good`
VALUES (3, '2025-03-26 20:30:47', 1, 899.99000, 3, '紫檀木古筝', 10.00000, NULL, '台',
        '精选紫檀木制作的传统古筝，音色优美，适合演奏和收藏。', 5, NULL, 'http://localhost:7245/1740401181220/1.jpg',
        'http://localhost:7245/1740401183198/1.jpg,http://localhost:7245/1740401183200/2.jpg,http://localhost:7245/1740401183201/3.jpg,http://localhost:7245/1740401183201/4.jpg,http://localhost:7245/1740401183205/5.jpg',
        1);
INSERT INTO `good`
VALUES (4, '2025-03-26 20:30:47', 1, 299.99000, 4, '苗族手工刺绣服饰', 30.00000, NULL, '套',
        '苗族传统手工刺绣服饰，色彩艳丽，工艺精湛，适合节日穿着。', 10, NULL, 'http://localhost:7245/1740401188893/10.jpg',
        'http://localhost:7245/1740401191339/10.jpg,http://localhost:7245/1740401191340/11.jpg,http://localhost:7245/1740401191339/12.jpg,http://localhost:7245/1740401191344/13.jpg,http://localhost:7245/1740401191343/14.jpg',
        1);
INSERT INTO `good`
VALUES (5, '2025-03-26 20:30:47', 1, 49.99000, 5, '手工桂花糕', 200.00000, NULL, '盒',
        '传统手工制作的桂花糕，口感细腻，甜而不腻，适合茶点。', 50, NULL, 'http://localhost:7245/1740401196901/0.jpg',
        'http://localhost:7245/1740401198879/11.jpg,http://localhost:7245/1740401198880/12.jpg,http://localhost:7245/1740401198880/13.jpg,http://localhost:7245/1740401198880/14.jpg',
        1);
INSERT INTO `good`
VALUES (6, '2025-03-26 20:30:47', 1, 99.99000, 6, '非遗文化纪念徽章', 150.00000, NULL, '枚',
        '非遗文化主题纪念徽章，设计精美，适合收藏或赠送。', 30, NULL, 'http://localhost:7245/1740401206006/0.jpg',
        'http://localhost:7245/1740401208316/0.jpg,http://localhost:7245/1740401208317/1.jpg,http://localhost:7245/1740401208319/2.jpg,http://localhost:7245/1740401208322/3.jpg,http://localhost:7245/1740401208320/4.jpg',
        1);
INSERT INTO `good`
VALUES (7, '2025-03-26 20:30:47', 1, 199.99000, 7, '手工艾草香包', 79.00000, NULL, '个',
        '传统手工制作的艾草香包，具有驱蚊安神的功效，适合居家使用。', 20, NULL,
        'http://localhost:7245/1740401220352/10.jpg',
        'http://localhost:7245/1740401222418/2.jpg,http://localhost:7245/1740401222419/3.jpg,http://localhost:7245/1740401222419/4.jpg',
        1);
INSERT INTO `good`
VALUES (8, '2025-03-26 20:30:47', 1, 399.99000, 8, '手工蜡染围巾', 40.00000, NULL, '条',
        '采用传统蜡染工艺制作的围巾，图案独特，适合秋冬季节佩戴。', 15, NULL, 'http://localhost:7245/1740401228684/11.jpg',
        'http://localhost:7245/1740401230571/12.jpg,http://localhost:7245/1740401230570/13.jpg,http://localhost:7245/1740401230571/14.jpg,http://localhost:7245/1740401230571/15.jpg',
        1);
INSERT INTO `good`
VALUES (9, '2025-03-26 20:30:47', 1, 599.99000, 9, '手工陶瓷茶具', 20.00000, NULL, '套',
        '传统手工制作的陶瓷茶具，造型古朴，适合品茶使用。', 10, NULL, 'http://localhost:7245/1740401236349/4.jpg',
        'http://localhost:7245/1740401238454/3.jpg,http://localhost:7245/1740401238455/4.jpg,http://localhost:7245/1740401238455/5.jpg,http://localhost:7245/1740401238457/6.jpg,http://localhost:7245/1740401238458/7.jpg',
        1);

-- ----------------------------
-- Table structure for goodcollect
-- ----------------------------
DROP TABLE IF EXISTS `goodcollect`;
CREATE TABLE `goodcollect`
(
    `Id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '商品收藏主键',
    `CreationTime`  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`     int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CollectUserId` int(11) NULL DEFAULT NULL COMMENT '收藏人',
    `GoodId`        int(11) NULL DEFAULT NULL COMMENT '商品',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goodprop
-- ----------------------------
DROP TABLE IF EXISTS `goodprop`;
CREATE TABLE `goodprop`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Value`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性值',
    `GoodId`       int(11) NULL DEFAULT NULL COMMENT '商品',
    `Name`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性名称',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodprop
-- ----------------------------
INSERT INTO `goodprop`
VALUES (1, '2025-03-26 20:32:24', 1, '红色', 1, '颜色');
INSERT INTO `goodprop`
VALUES (3, '2025-03-26 20:32:24', 1, '直径30cm', 1, '尺寸');
INSERT INTO `goodprop`
VALUES (5, '2025-03-26 20:32:24', 1, '精装', 2, '装帧');
INSERT INTO `goodprop`
VALUES (6, '2025-03-26 20:32:24', 1, '平装', 2, '装帧');
INSERT INTO `goodprop`
VALUES (7, '2025-03-26 20:32:24', 1, '2023年版', 2, '版本');
INSERT INTO `goodprop`
VALUES (8, '2025-03-26 20:32:24', 1, '2022年版', 2, '版本');
INSERT INTO `goodprop`
VALUES (9, '2025-03-26 20:32:24', 1, '紫檀木', 3, '材质');
INSERT INTO `goodprop`
VALUES (10, '2025-03-26 20:32:24', 1, '21弦', 3, '弦数');
INSERT INTO `goodprop`
VALUES (11, '2025-03-26 20:32:24', 1, 'S', 4, '尺码');
INSERT INTO `goodprop`
VALUES (12, '2025-03-26 20:32:24', 1, 'M', 4, '尺码');
INSERT INTO `goodprop`
VALUES (13, '2025-03-26 20:32:24', 1, 'L', 4, '尺码');
INSERT INTO `goodprop`
VALUES (14, '2025-03-26 20:32:24', 1, '红色', 4, '颜色');
INSERT INTO `goodprop`
VALUES (15, '2025-03-26 20:32:24', 1, '蓝色', 4, '颜色');
INSERT INTO `goodprop`
VALUES (16, '2025-03-26 20:32:24', 1, '500g', 5, '重量');
INSERT INTO `goodprop`
VALUES (17, '2025-03-26 20:32:24', 1, '1000g', 5, '重量');
INSERT INTO `goodprop`
VALUES (18, '2025-03-26 20:32:24', 1, '甜味', 5, '口味');
INSERT INTO `goodprop`
VALUES (19, '2025-03-26 20:32:24', 1, '原味', 5, '口味');
INSERT INTO `goodprop`
VALUES (20, '2025-03-26 20:32:24', 1, '铜质', 6, '材质');
INSERT INTO `goodprop`
VALUES (21, '2025-03-26 20:32:24', 1, '银质', 6, '材质');
INSERT INTO `goodprop`
VALUES (22, '2025-03-26 20:32:24', 1, '圆形', 6, '形状');
INSERT INTO `goodprop`
VALUES (23, '2025-03-26 20:32:24', 1, '方形', 6, '形状');
INSERT INTO `goodprop`
VALUES (24, '2025-03-26 20:32:24', 1, '绿色', 7, '颜色');
INSERT INTO `goodprop`
VALUES (25, '2025-03-26 20:32:24', 1, '蓝色', 7, '颜色');
INSERT INTO `goodprop`
VALUES (26, '2025-03-26 20:32:24', 1, '小号', 7, '尺寸');
INSERT INTO `goodprop`
VALUES (27, '2025-03-26 20:32:24', 1, '大号', 7, '尺寸');
INSERT INTO `goodprop`
VALUES (28, '2025-03-26 20:32:24', 1, '棉质', 8, '材质');
INSERT INTO `goodprop`
VALUES (29, '2025-03-26 20:32:24', 1, '丝绸', 8, '材质');
INSERT INTO `goodprop`
VALUES (30, '2025-03-26 20:32:24', 1, '长款', 8, '款式');
INSERT INTO `goodprop`
VALUES (31, '2025-03-26 20:32:24', 1, '短款', 8, '款式');
INSERT INTO `goodprop`
VALUES (32, '2025-03-26 20:32:24', 1, '白色', 8, '颜色');
INSERT INTO `goodprop`
VALUES (33, '2025-03-26 20:32:24', 1, '黑色', 8, '颜色');
INSERT INTO `goodprop`
VALUES (34, '2025-03-26 20:32:24', 1, '陶瓷', 9, '材质');
INSERT INTO `goodprop`
VALUES (35, '2025-03-26 20:32:24', 1, '4件套', 9, '套装');
INSERT INTO `goodprop`
VALUES (36, '2025-03-26 20:32:24', 1, '6件套', 9, '套装');
INSERT INTO `goodprop`
VALUES (37, '2025-03-26 20:32:24', 1, '青色', 9, '颜色');
INSERT INTO `goodprop`
VALUES (38, '2025-03-26 20:32:24', 1, '棕色', 9, '颜色');
INSERT INTO `goodprop`
VALUES (39, '2025-03-26 20:32:24', 1, '亲子课程', 10, '类型');
INSERT INTO `goodprop`
VALUES (40, '2025-03-26 20:32:24', 1, '团队课程', 10, '类型');
INSERT INTO `goodprop`
VALUES (41, '2025-03-26 20:32:24', 1, '2小时', 10, '时长');
INSERT INTO `goodprop`
VALUES (42, '2025-03-26 20:32:24', 1, '3小时', 10, '时长');
INSERT INTO `goodprop`
VALUES (43, '2025-03-26 20:32:24', 1, '刺绣', 10, '内容');
INSERT INTO `goodprop`
VALUES (44, '2025-03-26 20:32:24', 1, '陶艺', 10, '内容');
INSERT INTO `goodprop`
VALUES (45, '2025-03-26 20:32:24', 1, '剪纸', 10, '内容');
INSERT INTO `goodprop`
VALUES (46, '2025-03-26 20:32:24', 1, '线上', 10, '形式');
INSERT INTO `goodprop`
VALUES (47, '2025-03-26 20:32:24', 1, '线下', 10, '形式');
INSERT INTO `goodprop`
VALUES (48, '2025-03-26 20:32:24', 1, '中文', 10, '语言');
INSERT INTO `goodprop`
VALUES (49, '2025-03-26 20:32:24', 1, '英文', 10, '语言');
INSERT INTO `goodprop`
VALUES (50, '2025-03-26 20:32:24', 1, '周末班', 10, '时间');

-- ----------------------------
-- Table structure for goodstock
-- ----------------------------
DROP TABLE IF EXISTS `goodstock`;
CREATE TABLE `goodstock`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '库存记录主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '调整数量',
    `GoodId`       int(11) NULL DEFAULT NULL COMMENT '商品',
    `Remark`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库存记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodstock
-- ----------------------------
INSERT INTO `goodstock`
VALUES (1, '2025-04-01 12:01:42', 1, 100.00000, 1, '正常');

-- ----------------------------
-- Table structure for goodtype
-- ----------------------------
DROP TABLE IF EXISTS `goodtype`;
CREATE TABLE `goodtype`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '商品分类主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Sort`         int(11) NULL DEFAULT NULL COMMENT '排序',
    `Name`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodtype
-- ----------------------------
INSERT INTO `goodtype`
VALUES (1, '2025-03-26 20:18:35', 1, 1, '传统手工艺品');
INSERT INTO `goodtype`
VALUES (2, '2025-03-26 20:18:35', 1, 2, '非遗文化书籍');
INSERT INTO `goodtype`
VALUES (3, '2025-03-26 20:18:35', 1, 3, '传统乐器');
INSERT INTO `goodtype`
VALUES (4, '2025-03-26 20:18:35', 1, 4, '民族服饰');
INSERT INTO `goodtype`
VALUES (5, '2025-03-26 20:18:35', 1, 5, '传统食品');
INSERT INTO `goodtype`
VALUES (6, '2025-03-26 20:18:35', 1, 6, '非遗文化纪念品');
INSERT INTO `goodtype`
VALUES (7, '2025-03-26 20:18:35', 1, 7, '传统医药产品');
INSERT INTO `goodtype`
VALUES (8, '2025-03-26 20:18:35', 1, 8, '民俗文化用品');
INSERT INTO `goodtype`
VALUES (9, '2025-03-26 20:18:35', 1, 9, '传统美术作品');
INSERT INTO `goodtype`
VALUES (10, '2025-03-26 20:18:35', 1, 10, '非遗文化体验课程');

-- ----------------------------
-- Table structure for inheritor
-- ----------------------------
DROP TABLE IF EXISTS `inheritor`;
CREATE TABLE `inheritor`
(
    `Id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '传人主键',
    `CreationTime`    timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`       int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Name`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
    `ImageUrls`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
    `Content`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
    `Sex`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
    `Nation`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '民族',
    `No`              varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
    `BirthDate`       datetime(0) NULL DEFAULT NULL COMMENT '出生年月',
    `ProviceCityArea` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '传人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inheritor
-- ----------------------------
INSERT INTO `inheritor`
VALUES (1, '2025-03-26 20:35:23', 1, '张三', 'http://localhost:7245/1740409432072/0.jpg',
        '国家级非物质文化遗产传承人，擅长传统刺绣技艺。', '男', '汉族', 'CZ001', '1960-05-12 00:00:00',
        '北京市,北京市,东城区');
INSERT INTO `inheritor`
VALUES (2, '2025-03-26 20:35:23', 1, '李四', 'http://localhost:7245/1740409437105/36.jpg',
        '省级非物质文化遗产传承人，精通传统陶瓷制作。', '女', '苗族', 'CZ002', '1975-08-23 00:00:00',
        '湖南省,湘西土家族苗族自治州,吉首市');
INSERT INTO `inheritor`
VALUES (3, '2025-03-26 20:35:23', 1, '王五', 'http://localhost:7245/1740409449796/111.jpg',
        '国家级非物质文化遗产传承人，擅长古琴演奏。', '男', '汉族', 'CZ003', '1955-11-30 00:00:00',
        '江苏省,苏州市,姑苏区');
INSERT INTO `inheritor`
VALUES (4, '2025-03-26 20:35:23', 1, '赵六', 'http://localhost:7245/1740409454452/70.jpg',
        '市级非物质文化遗产传承人，精通传统木雕技艺。', '男', '土家族', 'CZ004', '1980-03-15 00:00:00',
        '湖北省,恩施土家族苗族自治州,恩施市');
INSERT INTO `inheritor`
VALUES (5, '2025-03-26 20:35:23', 1, '孙七', 'http://localhost:7245/1740409457985/107.jpg',
        '省级非物质文化遗产传承人，擅长传统剪纸艺术。', '女', '汉族', 'CZ005', '1972-07-20 00:00:00',
        '陕西省,延安市,宝塔区');
INSERT INTO `inheritor`
VALUES (6, '2025-03-26 20:35:23', 1, '周八', 'http://localhost:7245/1740409464991/112.jpg',
        '国家级非物质文化遗产传承人，精通传统蜡染技艺。', '女', '苗族', 'CZ006', '1968-09-10 00:00:00',
        '贵州省,黔东南苗族侗族自治州,凯里市');
INSERT INTO `inheritor`
VALUES (7, '2025-03-26 20:35:23', 1, '吴九', 'http://localhost:7245/1740409471689/170.jpg',
        '市级非物质文化遗产传承人，擅长传统竹编技艺。', '男', '汉族', 'CZ007', '1985-12-05 00:00:00',
        '浙江省,杭州市,西湖区');
INSERT INTO `inheritor`
VALUES (8, '2025-03-26 20:35:23', 1, '郑十', 'http://localhost:7245/1740409475051/125.jpg',
        '省级非物质文化遗产传承人，精通传统漆器制作。', '男', '汉族', 'CZ008', '1978-04-18 00:00:00',
        '福建省,福州市,鼓楼区');
INSERT INTO `inheritor`
VALUES (9, '2025-03-26 20:35:23', 1, '王芳', 'http://localhost:7245/1740409479999/193.jpg',
        '国家级非物质文化遗产传承人，擅长传统戏曲表演。', '女', '汉族', 'CZ009', '1963-06-25 00:00:00',
        '安徽省,黄山市,屯溪区');
INSERT INTO `inheritor`
VALUES (10, '2025-03-26 20:35:23', 1, '刘强', 'http://localhost:7245/1740409484005/90.jpg',
        '市级非物质文化遗产传承人，精通传统酿酒技艺。', '男', '汉族', 'CZ010', '1970-02-14 00:00:00',
        '四川省,泸州市,江阳区');

-- ----------------------------
-- Table structure for ordercomment
-- ----------------------------
DROP TABLE IF EXISTS `ordercomment`;
CREATE TABLE `ordercomment`
(
    `Id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '订单评价主键',
    `CreationTime`  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`     int(11) NULL DEFAULT NULL COMMENT '创建人',
    `GoodId`        int(11) NULL DEFAULT NULL COMMENT '商品',
    `CommentUserId` int(11) NULL DEFAULT NULL COMMENT '评论人',
    `OrderId`       int(11) NULL DEFAULT NULL COMMENT '订单',
    `CommentScore` double(20, 5) NULL DEFAULT NULL COMMENT '评分',
    `Comment`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论',
    `ImageUrls`     varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
    `ShopId`        int(11) NULL DEFAULT NULL COMMENT '店铺',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orderdet
-- ----------------------------
DROP TABLE IF EXISTS `orderdet`;
CREATE TABLE `orderdet`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `OrderId`      int(11) NULL DEFAULT NULL COMMENT '订单',
    `Qty`          int(11) NULL DEFAULT NULL COMMENT '数量',
    `Price` double(20, 5) NULL DEFAULT NULL COMMENT '购买价格',
    `GoodId`       int(11) NULL DEFAULT NULL COMMENT '商品',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdet
-- ----------------------------
INSERT INTO `orderdet`
VALUES (1, '2025-03-27 09:05:47', 2, 1, 1, 199.99000, 1);
INSERT INTO `orderdet`
VALUES (2, '2025-04-01 12:11:05', 2, 2, 1, 199.99000, 7);

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo`
(
    `Id`             int(11) NOT NULL AUTO_INCREMENT COMMENT '订单主键',
    `CreationTime`   timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`      int(11) NULL DEFAULT NULL COMMENT '创建人',
    `SelfPickupTime` datetime(0) NULL DEFAULT NULL COMMENT '自提时间',
    `ReceiveAddress` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
    `GetOrderTime`   datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
    `ReceivePhone`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货电话',
    `ShopId`         int(11) NULL DEFAULT NULL COMMENT '店铺',
    `OrderNo`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
    `Remark`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    `DiscountMoney` double(20, 5) NULL DEFAULT NULL COMMENT '折扣金额',
    `ExpireTime`     datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
    `PayOrderNo`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付订单号',
    `ReceiveName`    varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
    `DeliveryTime`   datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
    `UserId`         int(11) NULL DEFAULT NULL COMMENT '用户',
    `TotalQty`       int(11) NULL DEFAULT NULL COMMENT '总数量',
    `PayTime`        datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
    `OrginMoney` double(20, 5) NULL DEFAULT NULL COMMENT '原始金额',
    `IsSelfPickup`   tinyint(1) NULL DEFAULT NULL COMMENT '是否自提',
    `LogisticsNo`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
    `PayType`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付类型',
    `ExpressInfo`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '物流信息',
    `ShopUserId`     int(11) NULL DEFAULT NULL COMMENT '商家',
    `OrderStatus`    int(11) NULL DEFAULT NULL COMMENT '订单状态枚举',
    `TotalMoney` double(20, 5) NULL DEFAULT NULL COMMENT '总金额',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo`
VALUES (2, '2025-04-01 12:11:04', 2, NULL,
        '北京市,市辖区,东城区 北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室[门牌号]:A0006', NULL, '18646215125',
        1, 'D20250401121104837254', NULL, 0.00000, '2025-04-01 12:41:04', 'P20250401121104592711', '李佳奇', NULL, 2, 1,
        '2025-04-01 12:11:04', 199.99000, 0, NULL, '微信支付', NULL, 1, 1, 199.99000);

-- ----------------------------
-- Table structure for orderreturn
-- ----------------------------
DROP TABLE IF EXISTS `orderreturn`;
CREATE TABLE `orderreturn`
(
    `Id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '订单退货主键',
    `CreationTime`    timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`       int(11) NULL DEFAULT NULL COMMENT '创建人',
    `OrderId`         int(11) NULL DEFAULT NULL COMMENT '订单',
    `SellerReply`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '卖家回复',
    `IsDiscard`       tinyint(1) NULL DEFAULT NULL COMMENT '本次申请是否失效',
    `ApplyReason`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '申请原因',
    `IsAggren`        tinyint(1) NULL DEFAULT NULL COMMENT '卖家是否同意',
    `SellerImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卖家拍照',
    `LogisticsNo`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
    `IsApplyCustom`   tinyint(1) NULL DEFAULT NULL COMMENT '是否申请客服介入',
    `UserImageUrls`   varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户拍照',
    `ReturnMoney` double(20, 5) NULL DEFAULT NULL COMMENT '退款金额',
    `OrderReturnType` int(11) NULL DEFAULT NULL COMMENT '退款方式枚举',
    `ShopUserId`      int(11) NULL DEFAULT NULL COMMENT '商家',
    `UserId`          int(11) NULL DEFAULT NULL COMMENT '用户',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单退货表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `LogoCover`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Logo',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '商家',
    `AuditStatus`  int(11) NULL DEFAULT NULL COMMENT '审核状态枚举',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '店铺详情',
    `Email`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺邮箱',
    `Name`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺名称',
    `AuditUserId`  int(11) NULL DEFAULT NULL COMMENT '店铺审核人',
    `Ensure`       varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺保障',
    `Address`      varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺地址',
    `Phone`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
    `AuditReason`  longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '审核原因',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop`
VALUES (1, '2025-03-26 20:22:59', 1, 'http://localhost:7245/1740399713176/鄂尔多斯银行.png', 1, 2,
        '<p>非物质文化遗产官方店是一个致力于传承、推广和保护非物质文化遗产（以下简称“非遗”）的综合性平台。我们以“传承文化，守护匠心”为使命，通过线上线下相结合的方式，向公众展示和销售与非遗相关的各类产品和服务，让更多人了解、体验和参与到非遗文化的保护与传承中来。</p><h3><strong>核心价值</strong></h3><ol><li><strong>文化传承</strong>：我们精选了来自全国各地的非遗项目，涵盖传统手工艺、传统音乐、传统戏剧、民俗文化等多个领域。每一件产品都承载着深厚的历史文化底蕴和匠人的智慧结晶。</li><li><strong>匠心品质</strong>：所有商品均由非遗传承人或经过认证的工匠手工制作，确保每一件产品都符合传统工艺的标准，同时融入现代设计，使其既具有文化价值，又符合当代审美。</li><li><strong>公益支持</strong>：部分收益将用于支持非遗传承人的培养和非遗项目的保护工作，帮助更多濒危的非遗技艺得以延续。</li></ol><h3><strong>产品与服务</strong></h3><ol><li><strong>传统手工艺品</strong>：包括刺绣、陶瓷、木雕、漆器等，每一件作品都是独一无二的艺术品。</li><li><strong>非遗文化书籍</strong>：涵盖非遗历史、技艺介绍、传承人故事等，帮助消费者深入了解非遗文化。</li><li><strong>传统乐器与音乐</strong>：如古琴、二胡、笛子等，附赠教学视频或体验课程，让消费者感受传统音乐的魅力。</li><li><strong>民族服饰与配饰</strong>：融合传统元素与现代设计，适合日常穿着或特殊场合使用。</li><li><strong>传统食品</strong>：如手工制作的糕点、茶叶、酒类等，传承古法工艺，品味历史的味道。</li><li><strong>非遗体验课程</strong>：提供线下或线上的非遗技艺体验课程，如剪纸、扎染、陶艺等，让消费者亲身参与非遗文化的传承。</li></ol><h3><strong>特色亮点</strong></h3><ol><li><strong>官方认证</strong>：所有商品均经过严格筛选和认证，确保其真实性和文化价值。</li><li><strong>文化故事</strong>：每一件产品都附有详细的文化背景和传承人故事，让消费者在购买的同时了解其背后的文化内涵。</li><li><strong>定制服务</strong>：提供个性化定制服务，满足消费者对非遗文化的独特需求。</li><li><strong>线上线下结合</strong>：除了线上商城，我们还设有线下体验店，消费者可以亲自感受非遗文化的魅力。</li></ol><h3><strong>愿景与使命</strong></h3><p>非物质文化遗产官方店不仅是一个购物平台，更是一个文化传播的窗口。我们希望通过我们的努力，让更多人了解非遗、热爱非遗，并参与到非遗的保护与传承中来，共同守护这份珍贵的文化遗产。</p><p>欢迎您来到非物质文化遗产官方店，与我们一同感受传统文化的魅力，守护匠心，传承文明！</p>',
        '125981789@qq.com', '非物质文化遗产官方店', NULL, '正品、文化',
        '北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室', '18646215125', NULL);

-- ----------------------------
-- Table structure for shopcollect
-- ----------------------------
DROP TABLE IF EXISTS `shopcollect`;
CREATE TABLE `shopcollect`
(
    `Id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺收藏主键',
    `CreationTime`  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`     int(11) NULL DEFAULT NULL COMMENT '创建人',
    `ShopId`        int(11) NULL DEFAULT NULL COMMENT '店铺',
    `CollectUserId` int(11) NULL DEFAULT NULL COMMENT '收藏人',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sysnotice
-- ----------------------------
DROP TABLE IF EXISTS `sysnotice`;
CREATE TABLE `sysnotice`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '系统通知主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `IsPutaway`    tinyint(1) NULL DEFAULT NULL COMMENT '是否显示',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '通知内容',
    `Title`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sysnotice
-- ----------------------------
INSERT INTO `sysnotice`
VALUES (1, '2025-03-26 00:00:00', 1, 1, '近期将举办剪纸艺术展览，欢迎各位非遗爱好者前来参观。', '剪纸艺术展览通知');
INSERT INTO `sysnotice`
VALUES (2, '2025-03-26 00:00:00', 1, 0, '皮影戏传承人培训班将于下周开始报名，请感兴趣的朋友留意详情。',
        '皮影戏传承人培训班');
INSERT INTO `sysnotice`
VALUES (3, '2025-03-26 00:00:00', 1, 1, '京剧表演大赛即将启动，现面向全国征集优秀节目。', '京剧表演大赛通知');
INSERT INTO `sysnotice`
VALUES (4, '2025-03-26 00:00:00', 1, 1, '苗族刺绣工艺展将在本月下旬举行，展示独特的民族风情。', '苗族刺绣工艺展');
INSERT INTO `sysnotice`
VALUES (5, '2025-03-26 00:00:00', 1, 0, '藏医药浴法体验活动将在本周末开展，名额有限，报名从速。', '藏医药浴法体验活动');
INSERT INTO `sysnotice`
VALUES (6, '2025-03-26 00:00:00', 1, 1, '木版年画制作工作坊开放预约，适合家庭亲子互动。', '木版年画工作坊');
INSERT INTO `sysnotice`
VALUES (7, '2025-03-26 00:00:00', 1, 1, '蒙古长调民歌演唱会将在本市大剧院举行，门票现已开售。', '蒙古长调民歌演唱会');
INSERT INTO `sysnotice`
VALUES (8, '2025-03-26 00:00:00', 1, 0, '昆曲经典剧目《牡丹亭》将在本周五上演，敬请期待。', '昆曲《牡丹亭》演出通知');
INSERT INTO `sysnotice`
VALUES (9, '2025-03-26 00:00:00', 1, 1, '福建土楼文化研讨会邀请各界专家参与，共同探讨保护与发展。', '福建土楼文化研讨会');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '话题主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `ViewCount`    int(11) NULL DEFAULT NULL COMMENT '浏览次数',
    `ImageUrls`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图',
    `Cover`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
    `TopicTypeId`  int(11) NULL DEFAULT NULL COMMENT '话题类型',
    `IsRecommand`  tinyint(1) NULL DEFAULT NULL COMMENT '是否推荐',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
    `Title`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
    `AuditStatus`  int(11) NULL DEFAULT NULL COMMENT '审核状态枚举',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic`
VALUES (1, '2025-03-28 22:23:36', 2, 1,
        'http://localhost:7245/1740407006598/中式美学杀疯了！这剪纸灯笼美到失语_2_美加艺术_来自小红书网页版.jpg,http://localhost:7245/1740407006599/中式美学杀疯了！这剪纸灯笼美到失语_1_美加艺术_来自小红书网页版.jpg',
        'http://localhost:7245/1740407004335/中式美学杀疯了！这剪纸灯笼美到失语_1_美加艺术_来自小红书网页版.jpg', 1, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 宝子们，谁能扛得住这只剪纸灯笼的魅力啊！ </span></p><p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 大红色超正，🐦儿展翅欲飞，花卉、亭台栩栩如生。这就是美育里的东方浪漫呀，传统艺术的魅力狠狠get！ </span></p><p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 大家还见过啥超美的中式手作？快来唠唠~ </span></p><p><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E4%25B8%25AD%25E5%25BC%258F%25E5%2589%25AA%25E7%25BA%25B8%25E7%2581%25AF%25E7%25AC%25BC&amp;type=54&amp;source=web_note_detail_r10\">#中式剪纸灯笼</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E7%25BE%258E%25E8%2582%25B2&amp;type=54&amp;source=web_note_detail_r10\">#美育</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E4%25BC%25A0%25E7%25BB%259F%25E8%2589%25BA%25E6%259C%25AF&amp;type=54&amp;source=web_note_detail_r10\">#传统艺术</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E4%25B8%259C%25E6%2596%25B9%25E7%25BE%258E%25E5%25AD%25A6&amp;type=54&amp;source=web_note_detail_r10\">#东方美学</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E5%25AE%25A1%25E7%25BE%258E%25E7%25A7%25AF%25E7%25B4%25AF%25E8%2589%25BA%25E6%259C%25AF%25E5%2588%2586%25E4%25BA%25AB&amp;type=54&amp;source=web_note_detail_r10\">#审美积累艺术分享</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E4%25BC%25A0%25E7%25BB%259F%25E6%2589%258B%25E8%2589%25BA%25E8%25BF%2587%25E6%2598%25A5%25E8%258A%2582&amp;type=54&amp;source=web_note_detail_r10\">#传统手艺过春节</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E7%25BE%258E%25E5%258A%25A0%25E7%25BE%258E%25E6%259C%25AF&amp;type=54&amp;source=web_note_detail_r10\">#美加美术</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E9%259D%259E%25E9%2581%2597%25E9%2587%258C%25E7%259A%2584%25E4%25B8%25AD%25E5%259B%25BD%25E5%25B9%25B4&amp;type=54&amp;source=web_note_detail_r10\">#非遗里的中国年</a><a target=\"_blank\" rel=\"noopener noreferrer nofollow\" class=\"tag\" href=\"https://www.xiaohongshu.com/search_result?keyword=%25E5%2585%2583%25E5%25AE%25B5%25E8%258A%25B1%25E7%2581%25AF&amp;type=54&amp;source=web_note_detail_r10\">#元宵花灯</a></p>',
        '中式美学杀疯了！这剪纸灯笼美到失语', 2);
INSERT INTO `topic`
VALUES (2, '2025-03-28 22:23:55', 2, 1,
        'http://localhost:7245/1740407033492/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_2_是个剪纸匠就酱_来自小红书网页版.jpg,http://localhost:7245/1740407033493/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_1_是个剪纸匠就酱_来自小红书网页版.jpg',
        'http://localhost:7245/1740407029877/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_1_是个剪纸匠就酱_来自小红书网页版.jpg',
        1, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">今天做了个哪吒头像，红黑配色真的绝绝子！ 剪完的那一刻，有被治愈到！原来专注做一件事，真的能让人忘记烦恼！💆‍♀ ✂️材料准备： - 红色、黑色折纸，白色可用a4纸（🍑就有，超便宜） - 含酒精的马克笔（用来点缀细节） - 剪刀、刻刀（手残党建议用安全剪刀） - 白乳胶（可以装在针管瓶里使用，用来粘贴多层剪纸） ✂️制作步骤： 1⃣ 画图：先板绘出哪吒的头像，线条简单一点，方便后续剪刻。 2⃣ 装订：将画好的作品打印出来，并附在红色、黑色纸上面装订在一起。（新手建议两张即可，太多层会刻不透） 3️⃣ 剪刻：由内向外剪刻出哪吒的五官、发型以及外轮廓。（单色剪纸到这一步就完成啦） 4️⃣ 粘贴组合：制作笔彩剪纸，还需要将黑色剪纸粘贴在白色纸上。 5️⃣ 点缀色彩：在镂空处点缀颜色，使剪纸作品更加生动。 这次，我做了三种不同风格的哪吒： 🔥红色：代表哪吒的火焰和热血！ 🖤黑色：是一个内心酷酷的小朋友！ 🎨彩色：是一个灵动的哪吒！ 💞小贴士： - 如果你是手残党，可以先从简单的图案开始，慢慢练手～ 🖌️ - 剪纸的时候放点轻音乐，整个人都会沉浸其中，超级解压！ 🎵 - 完成后可以装裱起来，挂在家里当装饰，成就感满满！ 💕 最后想说： 生活有时候真的很累，但找到一件让自己专注的小事，真的能治愈一切～❤ 如果你也喜欢手工，不妨试试这个哪吒剪纸，做完真的会爱上！ 💖</span></p>',
        '治愈系剪纸| 剪一个酷酷的吒儿（附底稿教程）', 2);
INSERT INTO `topic`
VALUES (3, '2025-03-28 22:24:21', 2, 2,
        'http://localhost:7245/1740407060236/剪纸艺术奇幻博物馆_2_颐野设计_来自小红书网页版.jpg',
        'http://localhost:7245/1740407059036/剪纸艺术奇幻博物馆_2_颐野设计_来自小红书网页版.jpg', 1, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">将传统文化剪纸与现代儿童艺术展览结合，是一种创新的尝试，让孩子们在体验和学习中感受中国传统文化的魅力。这种结合方式不仅能够激发孩子们对于中国传统艺术形式的活泼和好奇心，还有助于培育纪律严明的民族文化身份认同。 1.教育意义**：通过互动工作坊或者现场演示等形式，在艺术展览中引入剪纸活动。专业人士可以向孩子介绍剪纸背后丰富多彩、深厚底蕴，并指导他们进行简单的图案制作实践。这种过程帮助儿童理解和欣赏到手工技巧以及其中所承载的历史与情感价值。</span></p>',
        '剪纸艺术奇幻博物馆', 2);
INSERT INTO `topic`
VALUES (4, '2025-03-28 22:25:12', 2, NULL,
        'http://localhost:7245/1740407110633/皮影非遗_1_非遗美学｜吉吉如薏_来自小红书网页版.jpg',
        'http://localhost:7245/1740407109182/皮影非遗_1_非遗美学｜吉吉如薏_来自小红书网页版.jpg', 2, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">皮影戏，又称“影子戏”或“灯影戏”，是一种以兽皮或纸板做成的人物剪影以表演故事的民间戏剧。表演时，艺人们在白色幕布后面，一边操纵影人，一边用当地流行的曲调讲述故事，同时配以打击乐器和弦乐，有浓厚的乡土气息。 每一幅中国皮影画，都是一位工匠心血的结晶。细致的刻刀，勾勒出栩栩如生的人物，仿佛把历史的长河凝固在皮影之上，传达出深厚的传统文化韵味。 在灯光的照射下，色彩在皮影画中交融出无限的幻想。每一幅画都如同一场绚烂的舞台剧，光影的变化为故事增色，将观者引入一个充满奇妙的艺术空间。 皮影戏历史悠久，流行范围广泛，并因各地表现方式不同而形成多种多样的皮影戏。如明快、神气十足的山东皮影、威严、庄重、深沉的河北皮影、是粗犷、豪放、朴实的陕西皮影等。 此外，它的制作过程十分复杂，通常要经过选皮、制皮、画稿、过稿、镂刻、敷彩、发汗、熨平、缀结合成等八道工序、手工雕刻 3000余刀。 中国皮影画不仅传承经典，更注入现代创意。在古老的皮影背后，蕴藏着无穷的创意力量，让这一传统艺术在时光中焕发新的生机。 每一个动作，每一张影子，都是一段悠扬的故事。中国皮影画不仅为观者展现了独特的艺术魅力，更是一次对文化传承的深刻唤醒，让历史在影子中得以重现。 在每个特别的时刻，一副中国皮影画不仅是一份独特的礼物，更是一份对于传统文化的珍视。送上皮影画，仿佛送上一份深远的文化情怀，成为岁月中的珍贵见证。</span></p>',
        '皮影非遗', 2);
INSERT INTO `topic`
VALUES (5, '2025-03-29 22:25:34', 2, NULL,
        'http://localhost:7245/1740407131747/【中华非遗技艺——皮影戏】_1_银川科技学院_来自小红书网页版.jpg',
        'http://localhost:7245/1740407130411/【中华非遗技艺——皮影戏】_1_银川科技学院_来自小红书网页版.jpg', 2, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">【中华非遗技艺——皮影戏】 一、皮影戏的介绍 皮影戏，又称“影子戏”或“灯影💡戏”，是一种以兽皮🫓或纸🧻板做成的人物剪影以表演故事的民间戏剧。表演时，艺人🧔们在白色幕布后面，一边操纵影人，一边用当地流行的曲调讲述故事，同时配以打击乐器和弦乐🎸，有浓厚的乡土气息。其流行范围极为广泛，并因各地所演的声腔不同而形成多种多样的皮影戏。 二、皮影戏的历史 皮影戏作为一种传统戏剧形式，其发展历程悠久且丰富💫多彩。皮影戏起源于‌西汉时期，据传是‌汉武帝为了纪念爱妃‌李夫人而创造的。它最初以兽皮🫓制成人物剪影，在光源照射下通过隔亮布进行表演，逐渐发展成为一种独特的戏曲形式。‌ 三、皮影戏的流派 由于皮影戏在中国的广泛传播，在不同地区的长期演变中形成了不同的流派，常见有四川皮影、湖北皮影、湖南皮影、北京皮影、唐山⛰️皮影、山东皮影、山西皮影、青海皮影、宁夏皮影、陕西皮影，以及川北皮影、陇东皮影等风格各具特色的地方皮影。各地皮影的音乐🎵唱腔风格与韵律都吸收了各自地方戏曲、曲艺、民歌小调🎼音乐🎵体系的精华，从而形成了异彩纷呈的众多流派。 四、皮影戏的发展 皮影戏是中国传统文化的重要组成部分，也是中国人民🧔喜爱的艺术形式之一。随着社会的发展，人们的生活水平不断提高，人们对精神文化生活的需求也越来越高。皮影戏作为传统文化的重要组成部分，其发展十分广阔。随着传统文化保护意识🧠的不断提高，人们对皮影戏这一传统文化的保护和传承也更加重视👏。随着科技的发展，皮影戏也在不断创新，出现了许多新的表现形式，如动漫、影视等。这些新的表现形式不仅为皮影戏带来了新的生命力，也为皮影戏的发展带来了更多的机遇。只要我们不断努力，保护和传承这一传统文化，不断推进创新和发展，皮影戏这一传统文化的未来一定会更加美好。 皮影戏，这一中国独有的民间艺术形式，以其独特的艺术魅力和深厚的历史文化底蕴，赢得了广泛的赞誉</span></p>',
        '【中华非遗技艺——皮影戏】', 2);
INSERT INTO `topic`
VALUES (6, '2025-03-29 22:25:59', 2, NULL,
        'http://localhost:7245/1740407152374/非遗文化 _ 京剧_2_古韵新织_来自小红书网页版.jpg,http://localhost:7245/1740407152376/非遗文化 _ 京剧_1_古韵新织_来自小红书网页版 (1).jpg',
        'http://localhost:7245/1740407150192/【中华非遗技艺——皮影戏】_1_银川科技学院_来自小红书网页版.jpg', 3, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 京剧，又称平剧、京戏等，中国国粹之一，是中国影响力最大的戏曲剧种，分布地以北京为中心，遍及全国各地。 </span></p><p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 2006年5月，京剧被国务院批准列入第一批国家级非物质文化遗产名录。2010年，被列入联合国教科文组织非物质文化遗产名录（名册）人类非物质文化遗产代表作名录。 </span></p><p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 京剧四大名旦是指梅兰芳、程砚秋、尚小云、苟慧生，他们是我国京剧且角行当中四大艺术流派的创始人。他们的优秀艺术，给人留下了不可磨灭的印象。 </span></p><p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\"> 京剧的角色划分是：生、旦、净、丑。京剧表演的四种艺术手法：唱、念、做、打，也是京剧表演四项基本功。京剧伴奏乐器分打击乐器与管弦乐器，有文场和武场之分。</span></p>',
        '非遗文化 | 京剧', 2);
INSERT INTO `topic`
VALUES (7, '2025-03-30 22:26:15', 2, 1, 'http://localhost:7245/1740407168258/至美国粹_2_朝花希拾_来自小红书网页版.jpg',
        'http://localhost:7245/1740407166976/至美国粹_1_朝花希拾_来自小红书网页版.jpg', 3, 0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">京剧，作为四大国粹之一，是中国传统文化的璀璨明珠。它融合了唱、念、做、打，用独特的艺术形式讲述着古老的故事。 而花旦，更是京剧舞台上最灵动的风景。 她们身着华丽的戏服，妆容精致，眼神灵动，每一个动作都带着古典的韵味。花旦们用婉转的唱腔、轻盈的舞步，演绎着从古至今的爱恨情仇。 从《霸王别姬》的虞姬到《贵妃醉酒》的杨玉环，花旦们用她们的才华和魅力，诠释着京剧的美。 京剧是国粹，花旦是京剧的灵魂。让我们一起走</span></p>',
        '至美国粹', 2);
INSERT INTO `topic`
VALUES (8, '2025-03-31 22:26:42', 2, 13,
        'http://localhost:7245/1740407197514/凯里｜没有人能空手走出苗绣集市，太好看了啊_2_白榆_来自小红书网页版.jpg',
        'http://localhost:7245/1740407196157/凯里｜没有人能空手走出苗绣集市，太好看了啊_1_白榆_来自小红书网页版.jpg', 4,
        0,
        '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">【关于导航地址】 📍绣里淘非遗集市（民族风情园） 【关于集市时间】 🕑周五、周六、周日 早10点—下午4点 1、周五最多，平日也有少量出摊，也有一些店面 2、尽量早些去，绣品更多更好 3、爱好者预留4小时左右，拍照打卡2小时左右 【关于出行交通】 🚕市区直接打车10元左右 🚌也可以坐公交18/25/28/5路 🚗自驾出游在旁边就有停车场 【关于苗绣基本信息】 📝苗绣是苗族源远流长的古老艺术，是苗族服饰的主要装饰手段。黔东南苗绣技法众多，主要有锡绣、绉绣、辫绣、堆花绣、平绣、破丝绣、马尾绣、打籽绣等。其色彩鲜艳，构图明朗，朴实大方。图案包含蝶、龙、鸟、牛、蛙、鱼、人物及花草树木等，这与苗族古代生活地域及民间信仰有密切关系。 📝苗族剪纸与苗族刺绣并称为姊妹花，主要作为苗族服饰刺绣纹样稿，是一种与苗族生活紧密相关的民间艺术。 其手法有剪、刻、扎等，题材多为花、鸟、虫、鱼、动物形象和苗族神话传说，反映苗族对远古图腾和自然的崇拜。 【关于绣品价格】 💰吊坠20—40 耳饰20—40 戒指15—35 绣片根据大小绣法不同几百——几千不等 1、绣片可以二次创作，最简单美观可以相框装裱 2、摊主大多都很热情会介绍绣品，但一般价格会喊得比较高，所以请大胆砍价，即使不买也没有关系。 【关于选绣品的建议】 1、有时间可以先去凯里博物馆了解苗绣的相关信息，比如苗绣技法，苗绣图案寓意等，便于选绣品时更好识别。 2、辨识手绣和机绣，机绣价格会便宜很多。手绣因为费时费工所以价格低于100的要小心。不会辨识的最好不要下单价格太高的绣片。 3、一眼心动的绣品，看好图案和配色，一定要多逛多对比，不要冲动消费。因为很多绣娘不会自己设计图案，都是按照模版图案绣的，所以很多摊位会有相同的图案，只是配色会不太一样。 【关于集市其它】 1、集市的摊位非常多，有些摊主看着平平无奇，但她就是苗绣非遗传承人，真的是高手隐于市井。 2、整个集市主要以苗绣为主，还会有一些蜡染扎染，织锦等其它民族工艺品，也可以去淘一淘。</span></p>',
        '凯里｜没有人能空手走出苗绣集市，太好看了啊', 2);

-- ----------------------------
-- Table structure for topiccollect
-- ----------------------------
DROP TABLE IF EXISTS `topiccollect`;
CREATE TABLE `topiccollect`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '话题收藏主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '收藏人',
    `TopicId`      int(11) NULL DEFAULT NULL COMMENT '话题',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topicrecord
-- ----------------------------
DROP TABLE IF EXISTS `topicrecord`;
CREATE TABLE `topicrecord`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '话题浏览记录主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `TopicTypeId`  int(11) NULL DEFAULT NULL COMMENT '话题类型',
    `UserId`       int(11) NULL DEFAULT NULL COMMENT '浏览人',
    `TopicId`      int(11) NULL DEFAULT NULL COMMENT '话题',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题浏览记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topicrecord
-- ----------------------------
INSERT INTO `topicrecord`
VALUES (1, '2025-03-26 22:52:30', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (2, '2025-03-27 09:06:10', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (3, '2025-03-27 09:06:16', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (4, '2025-04-01 11:44:53', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (5, '2025-04-01 11:47:49', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (6, '2025-04-01 11:48:35', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (7, '2025-04-01 11:49:00', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (8, '2025-04-01 11:49:26', 2, 1, 2, 1);
INSERT INTO `topicrecord`
VALUES (9, '2025-04-01 11:49:32', 2, 1, 2, 3);
INSERT INTO `topicrecord`
VALUES (10, '2025-04-01 11:51:58', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (11, '2025-04-01 12:11:08', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (12, '2025-04-01 12:11:14', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (13, '2025-04-01 12:12:49', 3, 3, 3, 7);
INSERT INTO `topicrecord`
VALUES (14, '2025-04-01 12:58:35', 3, 1, 3, 2);
INSERT INTO `topicrecord`
VALUES (15, '2025-04-01 21:15:13', 2, 4, 2, 8);
INSERT INTO `topicrecord`
VALUES (16, '2025-04-01 21:17:20', 2, 1, 2, 3);

-- ----------------------------
-- Table structure for topictype
-- ----------------------------
DROP TABLE IF EXISTS `topictype`;
CREATE TABLE `topictype`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '话题类型主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
    `Cover`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topictype
-- ----------------------------
INSERT INTO `topictype`
VALUES (1, '2025-03-26 00:00:00', 1, '剪纸艺术是中国传统的民间艺术形式，具有浓厚的地方特色和文化内涵。', NULL,
        '剪纸艺术');
INSERT INTO `topictype`
VALUES (2, '2025-03-26 00:00:00', 1, '皮影戏是一种古老的戏剧表演形式，通过灯光和影子展现故事情节。', NULL, '皮影戏');
INSERT INTO `topictype`
VALUES (3, '2025-03-26 00:00:00', 1, '京剧是中国国粹之一，融合了唱、念、做、打等多种表演形式。', NULL, '京剧');
INSERT INTO `topictype`
VALUES (4, '2025-03-26 00:00:00', 1, '苗族刺绣以其独特的图案和色彩闻名，是民族文化的瑰宝。', NULL, '苗族刺绣');
INSERT INTO `topictype`
VALUES (5, '2025-03-26 00:00:00', 1, '藏医药浴法是藏族传统医学的重要组成部分，具有悠久的历史。', NULL, '藏医药浴法');
INSERT INTO `topictype`
VALUES (6, '2025-03-26 00:00:00', 1, '木版年画是一种传统的民间艺术，常用于庆祝春节和其他节日。', NULL, '木版年画');
INSERT INTO `topictype`
VALUES (7, '2025-03-26 00:00:00', 1, '蒙古长调民歌以悠扬的旋律和深情的歌词著称，展现了草原文化的魅力。', NULL,
        '蒙古长调民歌');
INSERT INTO `topictype`
VALUES (8, '2025-03-26 00:00:00', 1, '昆曲是中国最古老的剧种之一，被誉为“百戏之祖”。', NULL, '昆曲');
INSERT INTO `topictype`
VALUES (9, '2025-03-26 00:00:00', 1, '福建土楼是客家文化的象征，体现了古代建筑智慧与防御功能的结合。', NULL, '福建土楼');
INSERT INTO `topictype`
VALUES (10, '2025-03-26 00:00:00', 1, '端午节赛龙舟是一项历史悠久的传统活动，象征着团结和拼搏精神。', NULL,
        '端午节赛龙舟');

-- ----------------------------
-- Table structure for useraddress
-- ----------------------------
DROP TABLE IF EXISTS `useraddress`;
CREATE TABLE `useraddress`
(
    `Id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址主键',
    `CreationTime`    timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`       int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Sex`             varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
    `Phone`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
    `BelongUserId`    int(11) NULL DEFAULT NULL COMMENT '所属用户',
    `Address`         varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
    `ProviceCityArea` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
    `Name`            varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货姓名',
    `HouseNumber`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门牌号',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of useraddress
-- ----------------------------
INSERT INTO `useraddress`
VALUES (1, '2025-03-27 09:04:58', 2, '保密', '18646215125', 2, '北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室',
        '北京市,市辖区,东城区', '李佳奇', 'A0006');

-- ----------------------------
-- Table structure for videoexhibition
-- ----------------------------
DROP TABLE IF EXISTS `videoexhibition`;
CREATE TABLE `videoexhibition`
(
    `Id`                    int(11) NOT NULL AUTO_INCREMENT COMMENT '视频展览主键',
    `CreationTime`          timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`             int(11) NULL DEFAULT NULL COMMENT '创建人',
    `VideoExhibitionTypeId` int(11) NULL DEFAULT NULL COMMENT '分类',
    `VideoUrl`              varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '视频',
    `VideoCover`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '视频封面',
    `ShortDesc`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短描述',
    `IsPutaway`             tinyint(1) NULL DEFAULT NULL COMMENT '是否上架',
    `ProviceCityArea`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '视频展览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videoexhibition
-- ----------------------------
INSERT INTO `videoexhibition`
VALUES (1, '2025-03-26 00:00:00', 1, 1, 'https://example.com/videos/opera1.mp4',
        'http://localhost:7245/1743479961962/1.jpg', '京剧《霸王别姬》经典片段', 1, '北京市,北京市,市辖区');
INSERT INTO `videoexhibition`
VALUES (2, '2025-03-26 00:00:00', 1, 2, 'https://example.com/videos/art1.mp4',
        'http://localhost:7245/1743479976418/1.jpg', '皮影戏表演《西游记》选段', 1, '陕西省,西安市,莲湖区');
INSERT INTO `videoexhibition`
VALUES (3, '2025-03-26 00:00:00', 1, 3, 'https://example.com/videos/craft1.mp4',
        'http://localhost:7245/1743479982661/1.jpg', '苏绣工艺制作过程展示', 1, '江苏省,苏州市,吴中区');
INSERT INTO `videoexhibition`
VALUES (4, '2025-03-26 00:00:00', 1, 4, 'https://example.com/videos/music1.mp4',
        'http://localhost:7245/1743479991759/18.jpg', '蒙古长调民歌演唱', 0, '内蒙古自治区,锡林郭勒盟,二连浩特市');
INSERT INTO `videoexhibition`
VALUES (5, '2025-03-26 00:00:00', 1, 5, 'https://example.com/videos/festival1.mp4',
        'http://localhost:7245/1743479999865/1.jpg', '端午节赛龙舟活动精彩瞬间', 1, '广东省,广州市,海珠区');
INSERT INTO `videoexhibition`
VALUES (6, '2025-03-26 00:00:00', 1, 3, 'https://example.com/videos/craft2.mp4',
        'http://localhost:7245/1743480006184/14.jpg', '景德镇陶瓷烧制技艺', 1, '江西省,景德镇市,珠山区');
INSERT INTO `videoexhibition`
VALUES (7, '2025-03-26 00:00:00', 1, 4, 'https://example.com/videos/music2.mp4',
        'http://localhost:7245/1743480011041/14.jpg', '苗族飞歌现场演唱', 1, '贵州省,黔东南苗族侗族自治州,凯里市');
INSERT INTO `videoexhibition`
VALUES (8, '2025-03-26 00:00:00', 1, 1, 'https://example.com/videos/opera2.mp4',
        'http://localhost:7245/1743480017033/1.jpg', '昆曲《牡丹亭》选段欣赏', 0, '江苏省,苏州市,姑苏区');
INSERT INTO `videoexhibition`
VALUES (9, '2025-03-26 00:00:00', 1, 5, 'https://example.com/videos/festival2.mp4',
        'http://localhost:7245/1743480021935/0.jpg', '春节舞龙舞狮表演', 1, '上海市,上海市,黄浦区');
INSERT INTO `videoexhibition`
VALUES (10, '2025-03-26 00:00:00', 1, 2, 'https://example.com/videos/art2.mp4',
        'http://localhost:7245/1743480029870/18.jpg', '剪纸艺术创作过程', 1, '山东省,济南市,历下区');

-- ----------------------------
-- Table structure for videoexhibitioncollect
-- ----------------------------
DROP TABLE IF EXISTS `videoexhibitioncollect`;
CREATE TABLE `videoexhibitioncollect`
(
    `Id`                int(11) NOT NULL AUTO_INCREMENT COMMENT '视频展览收藏主键',
    `CreationTime`      timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`         int(11) NULL DEFAULT NULL COMMENT '创建人',
    `CoolectUserId`     int(11) NULL DEFAULT NULL COMMENT '收藏人',
    `VideoExhibitionId` int(11) NULL DEFAULT NULL COMMENT '视频展览',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '视频展览收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videoexhibitioncollect
-- ----------------------------
INSERT INTO `videoexhibitioncollect`
VALUES (1, '2025-04-01 21:17:15', 2, 2, 1);

-- ----------------------------
-- Table structure for videoexhibitiontype
-- ----------------------------
DROP TABLE IF EXISTS `videoexhibitiontype`;
CREATE TABLE `videoexhibitiontype`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '视频展览类型主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '视频展览类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videoexhibitiontype
-- ----------------------------
INSERT INTO `videoexhibitiontype`
VALUES (1, '2025-03-26 00:00:00', 1, '传统戏曲');
INSERT INTO `videoexhibitiontype`
VALUES (2, '2025-03-26 00:00:00', 1, '民间艺术');
INSERT INTO `videoexhibitiontype`
VALUES (3, '2025-03-26 00:00:00', 1, '传统手工艺');
INSERT INTO `videoexhibitiontype`
VALUES (4, '2025-03-26 00:00:00', 1, '民族音乐');
INSERT INTO `videoexhibitiontype`
VALUES (5, '2025-03-26 00:00:00', 1, '传统节庆');
INSERT INTO `videoexhibitiontype`
VALUES (6, '2025-03-26 00:00:00', 1, '传统医药');
INSERT INTO `videoexhibitiontype`
VALUES (7, '2025-03-26 00:00:00', 1, '传统舞蹈');
INSERT INTO `videoexhibitiontype`
VALUES (8, '2025-03-26 00:00:00', 1, '传统建筑');
INSERT INTO `videoexhibitiontype`
VALUES (9, '2025-03-26 00:00:00', 1, '传统饮食文化');
INSERT INTO `videoexhibitiontype`
VALUES (10, '2025-03-26 00:00:00', 1, '传统民俗');

-- ----------------------------
-- Table structure for wechatcollection
-- ----------------------------
DROP TABLE IF EXISTS `wechatcollection`;
CREATE TABLE `wechatcollection`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '会话集合主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `LastTime`     datetime(0) NULL DEFAULT NULL COMMENT '最新消息时间',
    `LastMessage`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最新消息',
    `OtherUserId`  int(11) NULL DEFAULT NULL COMMENT '对方用户',
    `SelfUserId`   int(11) NULL DEFAULT NULL COMMENT '本身用户',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话集合表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wechatcollection
-- ----------------------------
INSERT INTO `wechatcollection`
VALUES (1, '2025-03-26 22:46:01', 2, NULL, NULL, 1, 2);
INSERT INTO `wechatcollection`
VALUES (2, '2025-03-26 22:46:01', 2, NULL, NULL, 2, 1);
INSERT INTO `wechatcollection`
VALUES (3, '2025-04-01 12:13:26', 3, NULL, NULL, 1, 3);
INSERT INTO `wechatcollection`
VALUES (4, '2025-04-01 12:13:26', 3, NULL, NULL, 3, 1);

-- ----------------------------
-- Table structure for wechatmessage
-- ----------------------------
DROP TABLE IF EXISTS `wechatmessage`;
CREATE TABLE `wechatmessage`
(
    `Id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '会话消息主键',
    `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `CreatorId`    int(11) NULL DEFAULT NULL COMMENT '创建人',
    `Content`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '消息内容',
    `SelfUserId`   int(11) NULL DEFAULT NULL COMMENT '自身用户',
    `OtherUserId`  int(11) NULL DEFAULT NULL COMMENT '对方用户',
    `SendTime`     datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
    PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话消息表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
