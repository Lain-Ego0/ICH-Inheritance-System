/*
 Navicat Premium Data Transfer

 Source Server         : yzx
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : feiyixitong

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 02/01/2026 15:38:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appuser
-- ----------------------------
DROP TABLE IF EXISTS `appuser`;
CREATE TABLE `appuser`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `Email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `RoleType` int NULL DEFAULT NULL COMMENT '角色',
  `PhoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `ImageUrls` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `Birth` datetime NULL DEFAULT NULL COMMENT '出生年月',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of appuser
-- ----------------------------
INSERT INTO `appuser` VALUES (1, '2025-12-26 20:05:00', 0, 'admin', NULL, 1, NULL, NULL, NULL, '管理员', 'admin');
INSERT INTO `appuser` VALUES (2, '2025-12-26 20:55:14', 0, 'keke', '123456@qq.com', 2, '15386751943', 'http://localhost:7245/1766825803374/781c1679-9b4f-4315-b28c-21773d1c6c37.png', '2020-01-06 00:00:00', '胡图图', 'keke');
INSERT INTO `appuser` VALUES (4, '2025-12-26 19:39:58', 0, '1', '1', 2, '13850015754', NULL, NULL, '1', '1');

-- ----------------------------
-- Table structure for buycard
-- ----------------------------
DROP TABLE IF EXISTS `buycard`;
CREATE TABLE `buycard`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '购物车主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Qty` int NULL DEFAULT NULL COMMENT '数量',
  `BelongUserId` int NULL DEFAULT NULL COMMENT '所属用户',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  `ShopId` int NULL DEFAULT NULL COMMENT '店铺',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of buycard
-- ----------------------------
INSERT INTO `buycard` VALUES (1, '2025-03-27 09:06:00', 2, 1, 2, 1, 1);
INSERT INTO `buycard` VALUES (2, '2025-03-27 09:06:03', 2, 1, 2, 3, 1);
INSERT INTO `buycard` VALUES (3, '2025-04-01 12:11:00', 2, 1, 2, 7, 1);
INSERT INTO `buycard` VALUES (4, '2025-04-01 12:57:55', 3, 1, 3, 7, 1);
INSERT INTO `buycard` VALUES (5, '2025-12-26 23:57:01', 4, 1, 4, 1, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '评论主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `RelativeId` int NULL DEFAULT NULL COMMENT '关联id',
  `Type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `NickName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `RootCommentId` int NULL DEFAULT NULL COMMENT '根评论',
  `TargetUserId` int NULL DEFAULT NULL COMMENT '回复用户',
  `HeadImage` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `SendUserId` int NULL DEFAULT NULL COMMENT '发送用户',
  `Content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '2025-12-27 09:06:13', 2, 8, 'Topic', '胡图图', 0, NULL, NULL, 2, '测试');
INSERT INTO `comment` VALUES (2, '2025-12-26 23:20:50', 4, 2, 'Topic', '1', 0, NULL, NULL, 4, '牛逼');

-- ----------------------------
-- Table structure for commentlove
-- ----------------------------
DROP TABLE IF EXISTS `commentlove`;
CREATE TABLE `commentlove`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '评论点赞记录主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `RelativeId` int NULL DEFAULT NULL COMMENT '关联id',
  `CommentId` int NULL DEFAULT NULL COMMENT '点赞评论',
  `UserId` int NULL DEFAULT NULL COMMENT '点赞人',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论点赞记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commentlove
-- ----------------------------
INSERT INTO `commentlove` VALUES (1, '2025-12-27 09:06:14', 2, '0', 8, 1, 2);
INSERT INTO `commentlove` VALUES (2, '2025-12-26 23:20:53', 4, '0', 2, 2, 4);

-- ----------------------------
-- Table structure for cultural
-- ----------------------------
DROP TABLE IF EXISTS `cultural`;
CREATE TABLE `cultural`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '非遗文化主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `No` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
  `ImageUrls` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `RecordDate` datetime NULL DEFAULT NULL COMMENT '登记日期',
  `PlaceDeclaration` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申报地区',
  `ProtectionUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '保护单位',
  `CulturalTypeId` int NULL DEFAULT NULL COMMENT '类别',
  `ProviceCityArea` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
  `BatchNo` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '批次',
  `InheritorId` int NULL DEFAULT NULL COMMENT '传人',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详细内容',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '非遗文化表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cultural
-- ----------------------------
INSERT INTO `cultural` VALUES (1, '2025-12-26 20:30:18', 1, '苗族古歌', 'FY001', 'http://localhost:7245/1766760379653/853bd14b-57bc-4f05-9ce8-1f1813b2187c.png', '2006-12-13 00:00:00', '贵州省台江县', '台江县非物质文化遗产保护中心', 1, '贵州省,黔东南苗族侗族自治州,台江县', '第一批', 1, '苗族分布在我国西南数省区。按方言划分，大致可分为湘西方言区、黔东方言区、川滇黔方言区。黔东南清水江流域一带是全国苗族最大的聚居区，大致包括凯里、剑河、黄平、台江、雷山、丹寨、施秉、黄平、镇远、三穗，以及广西三江和湖南靖县等地。在此广大苗族聚居区普遍流传着一种以创世为主体内容的诗体神话，俗称“古歌”或“古歌古词”。 　　苗族古歌内容包罗万象，从宇宙的诞生、人类和物种的起源、开天辟地、初民时期的滔天洪水，到苗族的大迁徙、苗族的古代社会制度和日常生产生活等，无所不包，成为苗族古代神话的总汇。 　　苗族古歌古词神话大多在鼓社祭、婚丧活动、亲友聚会和节日等场合演唱，演唱者多为中老年人、巫师、歌手等。酒席是演唱古歌的重要场合。苗族的古歌古词神话是一个民族的心灵记忆，是苗族古代社会的百科全书和“经典”，具有史学、民族学、哲学、人类学等多方面价值。今天，这些古歌古词神话还在民间流传唱诵。 　　但由于受到现代文化和市场经济的冲击，苗族古歌已濒临失传。以台江为例，在全县13万苗族同胞中，能唱完整部古歌的已寥寥无几，目前只有二百余人能唱一些不完整的古歌，而且都是中老年人，传承古歌较多的老人年事已高。如不抓紧抢救保护，苗族古歌这一民族瑰宝将最终在世间消失。');
INSERT INTO `cultural` VALUES (2, '2025-12-26 20:35:18', 1, '土族盘秀', 'FY002', 'http://localhost:7245/1766761006886/89e736cd-e22c-40ec-af27-dbd6a042a7f8.png', '2006-07-12 00:00:00', '青海省互助土族自治县', '互助土族自治县文化馆', 2, '青海省,海东市,互助土族自治县', '第一批', 2, '互助土族自治县位于青海省东北部，这里地处祁连山东南麓，为黄土高原与青藏高原交错衔接地带。土族独具特色的刺绣艺术盘绣就产生在这一地区。 　　土族盘绣艺术主要流传在青海互助县东沟、东山、五十、松多、丹麻等乡镇。在青海省都兰县发掘的土族先祖吐谷浑墓葬中，就出土有类似盘绣的绣品，由此可知，4世纪左右盘绣工艺已经出现。盘绣用料考究，加工精细，以黑色纯棉布做底料，再选面料贴上。盘绣是丝线绣，有红、黄、绿、蓝、桂红、紫、白等七色绣线，绣时一般七色俱全，配色协调，鲜艳夺目。盘绣的针法十分独特，操针时同时配两根色彩相同的线，一作盘线，一作缝线。盘绣不用棚架，直接用双手操作，绣者左手拿布料，右手拿针，作盘线的那根线挂在右胸，作缝线的那根线穿在针眼上。上针盘，下针缝，一针二线，虽费工费料，但成品厚实华丽，经久耐用。盘绣的图案构思巧妙，具有浓郁的民族风格，包括法轮（土语称为“扩日洛”）、太极图、五瓣梅、神仙魁子、云纹、菱形、雀儿头、富贵不断头、人物、佛像等几十种样式。一千多年来，盘绣以母女相传为主，亦在姊妹、妯娌、婆媳间传承。现代主要传承人有麻宝青、牛玛索等。 　　有着千年历史的土族盘绣一直传承至今，其文化与艺术的价值不可低估。土族盘绣色彩缤纷，图案逼真，在形、色、质、意等方面体现出本民族的审美态度和价值判断，为民族学、美学等的研究提供了鲜活的材料。 　　1949年以后，特别是近二十几年来，虽然各级政府部门和有关人员对土族盘绣做了一些发掘、抢救、保护工作，但这一古老的民族技艺至今仍面临着不少难以解决的问题。目前老一辈的盘绣能手大多年事已高，还有不少人已经谢世，一些绝技得不到传承就已悄然消亡，而年轻妇女多热衷于较简单的刺绣技术，如剁绣、机扎等，这些状况造成盘绣艺术后继乏人，濒临灭绝，急需调动各方面力量加以抢救、保护。');
INSERT INTO `cultural` VALUES (3, '2025-12-26 23:05:42', 1, '中医传统制剂方法（六神丸制作技艺）', 'FY003', 'http://localhost:7245/1767336959563/807d3429-89e1-464d-a9d9-da43995cd099.png', '2011-12-15 00:00:00', '上海市黄浦区', '上海雷允上药业有限公司', 3, '上海市,市辖区,黄浦区', '第三批', 3, '六神丸肇始于雷氏传人雷子纯。清同治年间，雷氏传人雷滋蕃得六神丸方，扩大生产，声誉日增。现为上海雷允上药业有限公司的著名特色品种，是国家保密品种，也是国家一般中药保护品种，1979年、1984年和1989年三次获得国家金质奖。 　　六神丸应用独特的中医药配伍理论：所用药材不是最常用的植物类药，而是动物、矿物类药，且有一定毒性，只有深谙中医药理论精髓的医家圣手才能开出这样的配方，既保留毒性药材中的有效成分，又降低毒性药材的毒副作用。和其他传统中药比，六神丸具有剂量小、起效快的特点。服用剂量仅以毫克计算；其他传统中药通常一个疗程需用周、月来计，而六神丸服用过后一天半日就可发挥作用。 　　在技艺上，六神丸从选材到“泛丸”（即制丸）由人工完成。其选材十分严格，珍珠选用港濂珠，不用老光珠；麝香必用“当门子”，即是指麝体下腹部腺香囊中成颗粒状者的干燥分泌物，质优价贵。采购蟾酥的时间大都在春秋雨季，直接向乡农收购，由老药工指导刮取蟾酥浆。“泛丸”则是通过成型、起模、打光等纯手工制法，使微丸具有圆整度、光亮度高及崩解速度快的特点。“泛丸”是中药的传统工艺，但要泛制成如六神丸这么小（如芥菜籽）的丸药，一般难以做到，彰显其技艺独特。其制作工序按采购、炮制、选配等步骤分解下去，制药工各司其职，相互间不允许打听。各岗完成原药后，再汇总到传人手中，由传人在一个封闭的房间里完成最后的合成。');

-- ----------------------------
-- Table structure for culturaltype
-- ----------------------------
DROP TABLE IF EXISTS `culturaltype`;
CREATE TABLE `culturaltype`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '非遗文化类别主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '非遗文化类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of culturaltype
-- ----------------------------
INSERT INTO `culturaltype` VALUES (1, '2025-12-26 22:42:38', 1, '民间文学');
INSERT INTO `culturaltype` VALUES (2, '2025-12-26 22:55:19', 1, '传统美术');
INSERT INTO `culturaltype` VALUES (3, '2025-12-26 22:59:59', 1, '传统医药');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Price` double(20, 5) NULL DEFAULT NULL COMMENT '价格',
  `GoodTypeId` int NULL DEFAULT NULL COMMENT '分类',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `Stock` double(20, 5) NULL DEFAULT NULL COMMENT '库存',
  `UserId` int NULL DEFAULT NULL COMMENT '所属用户',
  `Unit` varchar(54) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详情内容',
  `ReplenishmentLimit` int NULL DEFAULT NULL COMMENT '库存下限',
  `ShopGoodTypeId` int NULL DEFAULT NULL COMMENT '商品店铺分类',
  `Cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `ImageUrls` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图',
  `ShopId` int NULL DEFAULT NULL COMMENT '所属店铺',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (1, '2025-12-26 20:30:47', 1, 69.90000, 1, '手工刺绣团扇', 149.00000, NULL, '件', '<p>中式苏绣双面绣刺绣团扇古风汉服道具舞蹈旗袍专用扇古典工艺扇子</p>', 10, NULL, 'http://localhost:7245/1766826349835/b0bd44ac-6ebe-437f-b90c-c3958c5f3942.png', 'http://localhost:7245/1766826384348/83f9c4ac-0155-45ae-8ffe-3c983ad28c6d.png,http://localhost:7245/1766826407712/36f33237-fa0b-4c83-8742-afdc2f98c43a.png,http://localhost:7245/1766826436468/608525ed-c1d3-4e3f-88ff-ab5976e4f403.png,http://localhost:7245/1766834970404/da33f292-3283-40ef-baba-29b1a56580a6.png', 1);
INSERT INTO `good` VALUES (3, '2025-12-26 20:30:47', 1, 4869.89000, 3, '挖筝古筝', 10.00000, NULL, '台', '<p>华鼎古筝挖筝全桐木手工整挖面挖半挖专业演奏收藏热销榜内行大师</p>', 5, NULL, 'http://localhost:7245/1766829460647/f302d5e1-68b8-4f4c-9b1a-58f111c4ef94.png', 'http://localhost:7245/1766829536938/78b5be0e-8e00-480a-bc26-49c50e544ba7.png,http://localhost:7245/1766829600225/f14b9a21-ee50-4bba-bbc0-f06c0c63eae1.png,http://localhost:7245/1766829704695/71d9775e-1722-4a0c-a935-55c2916280ae.png', 1);
INSERT INTO `good` VALUES (7, '2025-12-26 20:30:47', 1, 20.00000, 7, '手工艾草香包', 79.00000, NULL, '个', '<p>好东西</p>', 50, NULL, 'http://localhost:7245/1766829910204/0bd675c4-6f47-46ad-9ae1-328b6476e1d5.png', 'http://localhost:7245/1766829952588/25fe29ce-53b4-43f9-aab9-84184b419cd9.png,http://localhost:7245/1766835014896/4a956b1a-f8f8-4bc7-b9fe-d4d79112b0f6.png', 1);

-- ----------------------------
-- Table structure for goodcollect
-- ----------------------------
DROP TABLE IF EXISTS `goodcollect`;
CREATE TABLE `goodcollect`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '商品收藏主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `CollectUserId` int NULL DEFAULT NULL COMMENT '收藏人',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodcollect
-- ----------------------------

-- ----------------------------
-- Table structure for goodprop
-- ----------------------------
DROP TABLE IF EXISTS `goodprop`;
CREATE TABLE `goodprop`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '商品属性主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性值',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  `Name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性名称',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodprop
-- ----------------------------
INSERT INTO `goodprop` VALUES (1, '2025-12-26 20:32:24', 1, '随机', 1, '颜色');
INSERT INTO `goodprop` VALUES (3, '2025-12-26 20:32:24', 1, '直径30cm', 1, '尺寸');
INSERT INTO `goodprop` VALUES (9, '2025-12-26 20:32:24', 1, '桐木', 3, '材质');
INSERT INTO `goodprop` VALUES (10, '2025-12-26 20:32:24', 1, '21弦', 3, '弦数');
INSERT INTO `goodprop` VALUES (24, '2025-12-26 20:32:24', 1, '特别美丽', 7, '款式');
INSERT INTO `goodprop` VALUES (26, '2025-12-26 20:32:24', 1, '约15cm', 7, '尺寸');

-- ----------------------------
-- Table structure for goodstock
-- ----------------------------
DROP TABLE IF EXISTS `goodstock`;
CREATE TABLE `goodstock`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '库存记录主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '调整数量',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  `Remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库存记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodstock
-- ----------------------------
INSERT INTO `goodstock` VALUES (1, '2025-12-27 12:01:42', 1, 100.00000, 1, '正常');

-- ----------------------------
-- Table structure for goodtype
-- ----------------------------
DROP TABLE IF EXISTS `goodtype`;
CREATE TABLE `goodtype`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '商品分类主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Sort` int NULL DEFAULT NULL COMMENT '排序',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodtype
-- ----------------------------
INSERT INTO `goodtype` VALUES (1, '2025-12-26 20:18:35', 1, 1, '传统手工艺品');
INSERT INTO `goodtype` VALUES (3, '2025-12-26 20:18:35', 1, 2, '传统乐器');
INSERT INTO `goodtype` VALUES (7, '2025-12-26 20:18:35', 1, 3, '传统医药产品');
INSERT INTO `goodtype` VALUES (10, '2025-12-26 20:18:35', 1, 10, '非遗文化体验课程');

-- ----------------------------
-- Table structure for inheritor
-- ----------------------------
DROP TABLE IF EXISTS `inheritor`;
CREATE TABLE `inheritor`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '传人主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `ImageUrls` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `Sex` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `Nation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '民族',
  `No` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
  `BirthDate` datetime NULL DEFAULT NULL COMMENT '出生年月',
  `ProviceCityArea` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '传人表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inheritor
-- ----------------------------
INSERT INTO `inheritor` VALUES (1, '2025-12-26 20:35:23', 1, '龙通珍', 'http://localhost:7245/1766756846382/cc44954e-0a01-46fa-b58c-a2d6aef7d050.png', '女，苗族，1936年生，贵州黄平人。第一批国家级非物质文化遗产项目苗族古歌代表性传承人。苗族古歌是苗族古代先民在长期的生产劳动中创造出来的史诗，内容包罗万象，从宇宙的诞生、人类与万物的起源、初民时期的大洪水，到苗族的大迁徙、苗族的古代社会制度和日常生产生活等，可谓苗族古代神话的总汇。由于苗族没有自己的文字，古歌传唱起到了传承民族历史与文化的功能。演唱古歌有着严格的禁忌，只有在祭祖、婚丧、亲友聚会和节庆等重大场合才能演唱，演唱者多为年长的巫师与歌手。龙通珍从6岁开始跟随祖母、母亲学习苗族古歌，上承龙垢玉、潘巫福等八代约四百余年的传唱历史，20岁后成为歌手，因为技艺熟练且能歌善舞而颇得好评。龙通珍不仅执著于苗族古歌的演唱，还向本村及附近村寨的百余名后辈传授古歌，培养了一批年轻的苗族古歌歌手，对苗族古歌的传承做出了一定的贡献。除了苗族古歌，龙通珍还能演唱大歌、酒歌及旅方歌等百余首。', '女', '苗族', 'CZ001', '1936-04-08 00:00:00', '贵州省,黔东南苗族侗族自治州,黄平县');
INSERT INTO `inheritor` VALUES (2, '2025-12-26 20:35:23', 1, '李发秀', 'http://localhost:7245/1766760807023/e8095384-4964-47fe-b617-f02d2a9fc943.png', '女，土族，1959年生，青海互助土族自治县人。第一批国家级非物质文化遗产项目土族盘绣代表性传承人。13岁起随母亲学绣。盘绣是具有土族特色的刺绣针法，同时运用两根针线，做工精致、复杂、匀称。李发秀利用农闲季节组织当地刺绣爱好者传授盘绣技艺，并且从1996年开始多次在县里少数民族乡镇土族刺绣培训班授课，受训人员多达百人次。', '女', '土族', 'CZ002', '1959-11-11 00:00:00', '青海省,海东市,互助土族自治县');
INSERT INTO `inheritor` VALUES (3, '2025-12-26 23:02:16', 1, '劳三申', 'http://localhost:7245/1766761247547/348acec0-faef-4b5f-9bd0-3b79294b84a1.png', '男，汉族，1946年9月生，浙江省杭州市人。第四批国家级非物质文化遗产项目中医传统制剂方法（六神丸制作技艺）代表性传承人。认真学习六神丸的制作工艺及相关内容，恪守国家保密品种协议，并带徒授艺，传承该项技艺。在任六神房房长期间，六神丸三次获得国家质量金奖，并多次获得“上海市名牌产品”、上海中药行业协会“上海中药行业名优产品”等称号，为企业赢得了良好的声誉。主要负责开发的六神丸衍生产品麝香保心丸获国家经济贸易委员会优秀新产品奖、上海市优秀新产品一等奖，六应丸获国家经济贸易委员会优秀新产品奖、上海市重大科技成果奖三等奖。', '男', '汉族', 'CZ003', '1946-09-18 00:00:00', '上海市,市辖区,黄浦区');

-- ----------------------------
-- Table structure for ordercomment
-- ----------------------------
DROP TABLE IF EXISTS `ordercomment`;
CREATE TABLE `ordercomment`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '订单评价主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  `CommentUserId` int NULL DEFAULT NULL COMMENT '评论人',
  `OrderId` int NULL DEFAULT NULL COMMENT '订单',
  `CommentScore` double(20, 5) NULL DEFAULT NULL COMMENT '评分',
  `Comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论',
  `ImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `ShopId` int NULL DEFAULT NULL COMMENT '店铺',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ordercomment
-- ----------------------------

-- ----------------------------
-- Table structure for orderdet
-- ----------------------------
DROP TABLE IF EXISTS `orderdet`;
CREATE TABLE `orderdet`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '订单明细主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `OrderId` int NULL DEFAULT NULL COMMENT '订单',
  `Qty` int NULL DEFAULT NULL COMMENT '数量',
  `Price` double(20, 5) NULL DEFAULT NULL COMMENT '购买价格',
  `GoodId` int NULL DEFAULT NULL COMMENT '商品',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderdet
-- ----------------------------
INSERT INTO `orderdet` VALUES (1, '2025-12-27 09:05:47', 2, 1, 1, 199.99000, 1);
INSERT INTO `orderdet` VALUES (2, '2025-12-21 12:11:05', 2, 2, 1, 199.99000, 7);

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '订单主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `SelfPickupTime` datetime NULL DEFAULT NULL COMMENT '自提时间',
  `ReceiveAddress` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `GetOrderTime` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `ReceivePhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货电话',
  `ShopId` int NULL DEFAULT NULL COMMENT '店铺',
  `OrderNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `Remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `DiscountMoney` double(20, 5) NULL DEFAULT NULL COMMENT '折扣金额',
  `ExpireTime` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `PayOrderNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付订单号',
  `ReceiveName` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `DeliveryTime` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `UserId` int NULL DEFAULT NULL COMMENT '用户',
  `TotalQty` int NULL DEFAULT NULL COMMENT '总数量',
  `PayTime` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `OrginMoney` double(20, 5) NULL DEFAULT NULL COMMENT '原始金额',
  `IsSelfPickup` tinyint(1) NULL DEFAULT NULL COMMENT '是否自提',
  `LogisticsNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `PayType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付类型',
  `ExpressInfo` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '物流信息',
  `ShopUserId` int NULL DEFAULT NULL COMMENT '商家',
  `OrderStatus` int NULL DEFAULT NULL COMMENT '订单状态枚举',
  `TotalMoney` double(20, 5) NULL DEFAULT NULL COMMENT '总金额',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES (2, '2025-12-21 12:11:04', 2, NULL, '北京市,市辖区,东城区 北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室[门牌号]:A0006', NULL, '18646215125', 1, 'D20250401121104837254', NULL, 0.00000, '2025-04-01 12:41:04', 'P20250401121104592711', '李佳奇', '2025-12-28 19:28:38', 2, 1, '2025-12-01 12:11:04', 199.99000, 0, '123', '微信支付', NULL, 1, 2, 199.99000);

-- ----------------------------
-- Table structure for orderreturn
-- ----------------------------
DROP TABLE IF EXISTS `orderreturn`;
CREATE TABLE `orderreturn`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '订单退货主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `OrderId` int NULL DEFAULT NULL COMMENT '订单',
  `SellerReply` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '卖家回复',
  `IsDiscard` tinyint(1) NULL DEFAULT NULL COMMENT '本次申请是否失效',
  `ApplyReason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '申请原因',
  `IsAggren` tinyint(1) NULL DEFAULT NULL COMMENT '卖家是否同意',
  `SellerImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卖家拍照',
  `LogisticsNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `IsApplyCustom` tinyint(1) NULL DEFAULT NULL COMMENT '是否申请客服介入',
  `UserImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户拍照',
  `ReturnMoney` double(20, 5) NULL DEFAULT NULL COMMENT '退款金额',
  `OrderReturnType` int NULL DEFAULT NULL COMMENT '退款方式枚举',
  `ShopUserId` int NULL DEFAULT NULL COMMENT '商家',
  `UserId` int NULL DEFAULT NULL COMMENT '用户',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单退货表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderreturn
-- ----------------------------

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '店铺主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `LogoCover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Logo',
  `UserId` int NULL DEFAULT NULL COMMENT '商家',
  `AuditStatus` int NULL DEFAULT NULL COMMENT '审核状态枚举',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '店铺详情',
  `Email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺邮箱',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `AuditUserId` int NULL DEFAULT NULL COMMENT '店铺审核人',
  `Ensure` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺保障',
  `Address` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺地址',
  `Phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `AuditReason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '审核原因',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, '2025-12-26 20:22:59', 1, 'http://localhost:7245/1766764010917/5bec20c1-cada-40f0-aff0-ec325877139d.png', 1, 2, '<p>非物质文化遗产官方店是一个致力于传承、推广和保护非物质文化遗产（以下简称“非遗”）的综合性平台。我们以“传承文化，守护匠心”为使命，通过线上线下相结合的方式，向公众展示和销售与非遗相关的各类产品和服务，让更多人了解、体验和参与到非遗文化的保护与传承中来。</p><h3><strong>核心价值</strong></h3><ol><li><strong>文化传承</strong>：我们精选了来自全国各地的非遗项目，涵盖传统手工艺、传统音乐、传统戏剧、民俗文化等多个领域。每一件产品都承载着深厚的历史文化底蕴和匠人的智慧结晶。</li><li><strong>匠心品质</strong>：所有商品均由非遗传承人或经过认证的工匠手工制作，确保每一件产品都符合传统工艺的标准，同时融入现代设计，使其既具有文化价值，又符合当代审美。</li><li><strong>公益支持</strong>：部分收益将用于支持非遗传承人的培养和非遗项目的保护工作，帮助更多濒危的非遗技艺得以延续。</li></ol><h3><strong>产品与服务</strong></h3><ol><li><strong>传统手工艺品</strong>：包括刺绣、陶瓷、木雕、漆器等，每一件作品都是独一无二的艺术品。</li><li><strong>非遗文化书籍</strong>：涵盖非遗历史、技艺介绍、传承人故事等，帮助消费者深入了解非遗文化。</li><li><strong>传统乐器与音乐</strong>：如古琴、二胡、笛子等，附赠教学视频或体验课程，让消费者感受传统音乐的魅力。</li><li><strong>民族服饰与配饰</strong>：融合传统元素与现代设计，适合日常穿着或特殊场合使用。</li><li><strong>传统食品</strong>：如手工制作的糕点、茶叶、酒类等，传承古法工艺，品味历史的味道。</li><li><strong>非遗体验课程</strong>：提供线下或线上的非遗技艺体验课程，如剪纸、扎染、陶艺等，让消费者亲身参与非遗文化的传承。</li></ol><h3><strong>特色亮点</strong></h3><ol><li><strong>官方认证</strong>：所有商品均经过严格筛选和认证，确保其真实性和文化价值。</li><li><strong>文化故事</strong>：每一件产品都附有详细的文化背景和传承人故事，让消费者在购买的同时了解其背后的文化内涵。</li><li><strong>定制服务</strong>：提供个性化定制服务，满足消费者对非遗文化的独特需求。</li><li><strong>线上线下结合</strong>：除了线上商城，我们还设有线下体验店，消费者可以亲自感受非遗文化的魅力。</li></ol><h3><strong>愿景与使命</strong></h3><p>非物质文化遗产官方店不仅是一个购物平台，更是一个文化传播的窗口。我们希望通过我们的努力，让更多人了解非遗、热爱非遗，并参与到非遗的保护与传承中来，共同守护这份珍贵的文化遗产。</p><p>欢迎您来到非物质文化遗产官方店，与我们一同感受传统文化的魅力，守护匠心，传承文明！</p>', '125981789@qq.com', '中国非物质文化遗产官方旗舰店', NULL, '正品、文化', '北京市朝阳区翻斗大街翻斗花园1001室', '18646215125', NULL);

-- ----------------------------
-- Table structure for shopcollect
-- ----------------------------
DROP TABLE IF EXISTS `shopcollect`;
CREATE TABLE `shopcollect`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '店铺收藏主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `ShopId` int NULL DEFAULT NULL COMMENT '店铺',
  `CollectUserId` int NULL DEFAULT NULL COMMENT '收藏人',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopcollect
-- ----------------------------

-- ----------------------------
-- Table structure for sysnotice
-- ----------------------------
DROP TABLE IF EXISTS `sysnotice`;
CREATE TABLE `sysnotice`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '系统通知主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `IsPutaway` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '通知内容',
  `Title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sysnotice
-- ----------------------------
INSERT INTO `sysnotice` VALUES (21, '2025-12-28 19:05:59', 1, 1, '<p>为健全非遗名录体系，保护传承优秀传统文化，现启动区级非物质文化遗产项目申报工作。欢迎符合条件的单位、群体及个人积极申报，详细要求可咨询非遗保护中心，共同助力非遗活态传承。</p>', '区级非物质文化遗产项目申报通知');
INSERT INTO `sysnotice` VALUES (22, '2025-12-28 19:24:32', 1, 1, '<p>为提升非遗传承实践水平，近期将举办传统技艺（汉绣、挑花方向）研修培训班。邀请行业专家与工艺美术大师授课，面向 40 岁以下有基础的从业人员招募，全程免费，结业后可参与作品展览。</p>', '非遗传承人研修培训通知');
INSERT INTO `sysnotice` VALUES (23, '2025-12-28 19:24:55', 1, 1, '<p>非遗大师亲授剪纸、中国结编织技艺！零基础也能上手，亲手制作非遗文创挂件，聆听技艺背后的文化故事。名额有限，欢迎非遗爱好者、亲子家庭报名参与，让传统手艺融入生活。</p>', '“非遗焕新” 手工体验课招募通知');
INSERT INTO `sysnotice` VALUES (24, '2025-12-28 19:25:25', 1, 1, '<p>非遗好物齐聚一堂！近期将举办非遗民俗集市，汇集扎染、手工银饰、传统美食等特色产品，现场还有传承人技艺展演。欢迎市民前来选购非遗好物、体验传统民俗，共赴文化盛宴。</p>', '非遗民俗集市活动通知');
INSERT INTO `sysnotice` VALUES (25, '2025-12-28 19:25:55', 1, 1, '<p>围绕 “融入现代生活 —— 非遗正青春” 主题，近期将举办非遗成果展览。集中展示传统医药、手工技艺、戏曲等特色项目，同步设置互动体验区，诚邀市民游客走进非遗、感受传统文化魅力。</p>', '“文化和自然遗产日” 非遗展览通知');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '话题主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `ViewCount` int NULL DEFAULT NULL COMMENT '浏览次数',
  `ImageUrls` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图',
  `Cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `TopicTypeId` int NULL DEFAULT NULL COMMENT '话题类型',
  `IsRecommand` tinyint(1) NULL DEFAULT NULL COMMENT '是否推荐',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `Title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `AuditStatus` int NULL DEFAULT NULL COMMENT '审核状态枚举',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (2, '2025-12-28 22:23:55', 2, 3, 'http://localhost:7245/1740407033492/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_2_是个剪纸匠就酱_来自小红书网页版.jpg,http://localhost:7245/1740407033493/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_1_是个剪纸匠就酱_来自小红书网页版.jpg', 'http://localhost:7245/1740407029877/治愈系剪纸_ 剪一个酷酷的吒儿（附底稿教程）_1_是个剪纸匠就酱_来自小红书网页版.jpg', 1, 0, '<p><span style=\"font-family: system-ui, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;, -apple-system, &quot;Segoe UI&quot;, Roboto, Ubuntu, Cantarell, &quot;Noto Sans&quot;, sans-serif, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, Arial, &quot;PingFang SC&quot;, &quot;PingFang TC&quot;, &quot;PingFang HK&quot;, &quot;Microsoft Yahei&quot;, &quot;Microsoft JhengHei&quot;; color: rgb(51, 51, 51); font-size: 16px\">今天做了个哪吒头像，红黑配色真的绝绝子！ 剪完的那一刻，有被治愈到！原来专注做一件事，真的能让人忘记烦恼！💆‍♀ ✂️材料准备： - 红色、黑色折纸，白色可用a4纸（🍑就有，超便宜） - 含酒精的马克笔（用来点缀细节） - 剪刀、刻刀（手残党建议用安全剪刀） - 白乳胶（可以装在针管瓶里使用，用来粘贴多层剪纸） ✂️制作步骤： 1⃣ 画图：先板绘出哪吒的头像，线条简单一点，方便后续剪刻。 2⃣ 装订：将画好的作品打印出来，并附在红色、黑色纸上面装订在一起。（新手建议两张即可，太多层会刻不透） 3️⃣ 剪刻：由内向外剪刻出哪吒的五官、发型以及外轮廓。（单色剪纸到这一步就完成啦） 4️⃣ 粘贴组合：制作笔彩剪纸，还需要将黑色剪纸粘贴在白色纸上。 5️⃣ 点缀色彩：在镂空处点缀颜色，使剪纸作品更加生动。 这次，我做了三种不同风格的哪吒： 🔥红色：代表哪吒的火焰和热血！ 🖤黑色：是一个内心酷酷的小朋友！ 🎨彩色：是一个灵动的哪吒！ 💞小贴士： - 如果你是手残党，可以先从简单的图案开始，慢慢练手～ 🖌️ - 剪纸的时候放点轻音乐，整个人都会沉浸其中，超级解压！ 🎵 - 完成后可以装裱起来，挂在家里当装饰，成就感满满！ 💕 最后想说： 生活有时候真的很累，但找到一件让自己专注的小事，真的能治愈一切～❤ 如果你也喜欢手工，不妨试试这个哪吒剪纸，做完真的会爱上！ 💖</span></p>', '治愈系剪纸| 剪一个酷酷的吒儿（附底稿教程）', 2);
INSERT INTO `topic` VALUES (5, '2025-12-29 22:25:34', 2, 1, 'http://localhost:7245/1766844702503/f547b452-2647-4846-aefd-6bd127c200db.png', 'http://localhost:7245/1766844671871/42aee960-0f6b-453f-91fd-e7c539b90be5.png', 2, 0, '<p>藏在西安书院门的老巷子里，有个能触摸光影的宝藏地 ——<strong>关中皮影老市集</strong>，青石板路两旁挂着泛黄的皮影影人，一到傍晚，煤油灯亮起，皮影在幕布上晃出千年故事，沉浸式体验老关中的皮影风情，这份攻略赶紧码住！</p><p>【导航 &amp; 时间】📍定位搜 “西安书院门・关中皮影老市集”（藏在书院门文化街里，靠近宝庆寺塔的巷弄里，跟着 “咚咚锵” 的皮影戏鼓点走就对了）🕑出摊节奏：每周六、周日 早 10 点 — 晚 7 点；端午、中秋、春节等节假日加开周五场，晚 6 点后有 “煤油灯皮影夜场”，幕布光影更有老味道；建议留 3 小时逛玩，想体验皮影雕刻、亲手演皮影的话，预留 4 小时足够。</p><p>【交通怎么去】🚇地铁最便捷：坐 2 号线到 “南门站”，从 B 口出，步行 10 分钟穿过书院门步行街，拐进巷弄就到；🚌公交直达：坐 18 路、203 路、213 路到 “南门里” 站，下车步行 5 分钟；🚗自驾：停在南门广场地下停车场，步行 15 分钟进书院门（巷弄里不能开车，步行逛更有味道）；🚶步行：书院门步行街本身就好逛，从南门里慢慢逛进去，沿途能看笔墨纸砚，顺路找到市集。</p><p>【市集里的皮影，是活的光影非遗】这里没有冰冷的陈列，全是能看、能摸、能玩的皮影烟火气：老艺人坐在幕布后，手里提着驴皮影人，竹棍一挑，穆桂英、孙悟空就活了过来，鼓点一响，唱腔苍劲，幕布上的影人刀光剑影、眉眼翻飞，连衣褶都跟着动；摊位上摆满了各式皮影，有巴掌大的卡通影人挂件，有半米高的传统戏曲角色影人，还有未上色的空白皮影坯子，摸起来薄如蝉翼，是用老驴皮、牛皮经十几道工序泡制、雕刻而成的。老艺人会现场演示皮影雕刻，刻刀在驴皮上翻飞，一会儿就刻出一朵牡丹纹，他说 “关中皮影讲究‘刀工要利，纹样要活’，龙凤纹保吉祥，花鸟纹藏烟火，每道刻痕都是老祖宗的讲究”；市集中央的小幕布前，随时能凑过去看一段《三打白骨精》《白蛇传》的皮影片段，旁边有阿姨拿着小本子，轻声讲影人的故事。</p><p>【市集价格 &amp; 砍价小技巧】💰小物件超划算：卡通皮影挂件 25-45 元，皮影书签 15-25 元，迷你影人钥匙扣 10-20 元，买回去挂在墙上、当伴手礼都好看；💸影人和体验项目：巴掌大的手工皮影影人（上色款）80-200 元，半米高的传统角色影人 300-800 元；体验项目：亲手操作皮影演戏 30 元 / 15 分钟，跟着老艺人学刻简单皮影（含材料）50-100 元；⚠️砍价攻略：关中摊主实在，别漫天砍价，比如 180 元的皮影影人，说 “150 元我带回去给娃讲皮影故事”，大多会答应；买影人时可以问摊主 “能不能送根小竹棍”，一般都会免费送，方便自己把玩。</p><p>【逛市集避坑指南】</p><ol class=\"auto-hide-last-sibling-br\"><li><p>辨手工 / 机器皮影：手工皮影刻痕有深浅变化，边缘略带毛边，颜色晕染自然；机器皮影线条死板，颜色均匀无层次，手工款低于 50 元基本没有，别踩坑；</p></li><li><p>选影人看材质：老驴皮、牛皮皮影摸起来柔韧，透光性好；塑料仿制品轻飘飘的，没有质感，别买错；</p></li><li><p>体验皮影操作别用力：影人的关节靠丝线连接，操作时竹棍要轻挑，别使劲扯，容易断丝线；</p></li><li><p>夜场必来：傍晚煤油灯亮起时，幕布上的皮影光影更柔和，拍照超出片，还能听老艺人唱老腔皮影戏。</p></li></ol><p>【市集隐藏彩蛋】巷弄口摆小摊的白发爷爷，是关中皮影非遗传承人，家里三代做皮影，他手里的老皮影，有些是几十年前的老物件，能讲出背后的戏曲故事；逛累了可以去巷子里的老茶摊歇脚，点一杯茯茶（15 元 / 杯），能免费听爷爷唱一段皮影小调；要是运气好，能遇到皮影戏团来市集演出，完整的《封神榜》片段，看得人挪不开眼；买空白皮影坯子，老艺人会免费帮你刻上名字，独一份的纪念。</p><p>我最后体验了皮影雕刻，选了个小蝴蝶纹样，老艺人手把手教我握刻刀，还说 “刻皮影要沉下心，慢工出细活”，刻好后自己上色，装了相框带走，现在摆在书架上，每次看都能想起市集里的鼓点和光影 —— 这哪是逛市集，是把关中皮影的千年韵味，揣进了口袋里！</p><p>你们有没有看过皮影戏？或者逛过光影类的非遗市集？评论区晒一晒你的体验～</p>', '巷弄里的光影传奇：西安书院门藏着关中皮影老市集', 2);
INSERT INTO `topic` VALUES (7, '2025-12-30 22:26:15', 2, 1, 'http://localhost:7245/1766844444182/ea6f1520-0948-4456-a521-647dd5308254.png', 'http://localhost:7245/1766844357920/82ddc6ef-da40-444b-81ad-27162565ca80.png', 3, 0, '<p>终于在京城挖到藏着京剧魂的宝藏地 ——<strong>长安大戏院旁・京剧雅集市集</strong>，藏在东单的老胡同里，青灰瓦檐下挂着大红戏服纹样的灯笼，一脚踏进就像闯进了活的梨园秘境，这份沉浸式京剧体验攻略收好了！</p><p>【导航 &amp; 时间】📍定位搜 “长安大戏院・京剧雅集市集”（东单三条胡同里，大戏院东侧 50 米，跟着戏韵调子走准能找着）🕑出摊节奏记好：每周日、周六 早 10 点 — 晚 6 点；节假日（国庆、春节、中秋）加开周五场，还有夜场（晚 7 点 —9 点），夜场有露天京剧片段表演，氛围感拉满；建议留 3 小时慢慢逛，想体验戏服试穿、脸谱绘制的话，预留 4-5 小时更充裕。</p><p>【交通怎么去】🚇地铁超方便：坐 2 号线 / 5 号线到 “东单站”，从 A 口出，步行 8 分钟穿胡同就到；🚌公交直达：坐 1 路、52 路、120 路到 “东单路口北” 站，下车走 3 分钟；🚗自驾：附近有东单体育中心停车场，步行 10 分钟可达，胡同里不能开车，建议停好车步行逛。</p><p>【市集里的京剧，是活的梨园风情】这里没有刻板的博物馆陈列，全是能摸、能玩、能体验的京剧烟火气：老艺人坐在竹椅上，手里握着油彩笔绘脸谱，朱砂、石青、藤黄的颜料在脸上勾勒出关羽的红脸、包拯的黑脸，一笔一画都是讲究；摊位上挂着缩小版的戏服，绣着团龙、牡丹纹样的水袖书签、脸谱挂件堆得满满当当，连钥匙扣都是生旦净丑的卡通造型。市集中央的小戏台子，时不时有非遗传承人即兴表演：老生的唱腔苍劲有力，花旦的水袖翻飞如蝶，连念白都字正腔圆，旁边会有阿姨拿着小册子，轻声给你讲 “苏三起解”“霸王别姬” 的故事。还有京剧周边超戳人：手工绣制的脸谱团扇、印着戏服纹样的帆布包、能贴在手机上的脸谱贴纸，每一样都把京剧的雅韵藏在了小细节里。</p><p>【市集价格 &amp; 体验收费】💰小物件性价比超高：脸谱挂件 20-50 元，水袖书签 15-30 元，戏服纹样钥匙扣 10-25 元，当个伴手礼刚好；💸体验项目收费合理：脸谱绘制（含空白脸谱、颜料、工具）30-80 元（大小不同价格不同），戏服试穿（含简单妆造）50-100 元（儿童款更便宜），老艺人教念白 / 台步体验 40 元 / 半小时；⚠️砍价小技巧：小物件可适当抹零，体验项目价格固定，但可以问摊主 “能不能多教一个简单台步”，大多会爽快答应；买得多的话，摊主会送小贴纸、书签之类的小礼物。</p><p>【逛市集 &amp; 体验避坑指南】</p><ol class=\"auto-hide-last-sibling-br\"><li><p>先听后体验：刚到市集可以先在小戏台旁听一段京剧片段，了解基本的生旦净丑角色，再去体验脸谱绘制，能更精准选自己喜欢的角色纹样；</p></li><li><p>辨手工 / 印刷脸谱：手工绘制的脸谱颜色有晕染感，线条有粗细变化，印刷的颜色均匀但死板，手工款一般 50 元以上，低于 30 元的基本是印刷品；</p></li><li><p>戏服试穿选对尺码：戏服偏大，试穿时一定要让摊主帮忙调整腰带、袖口，不然拍照会显拖沓；儿童试穿建议提前问清有没有合适的尺码，避免跑空；</p></li><li><p>夜场别错过：节假日夜场有露天表演，灯笼亮起时，戏服的刺绣在灯光下超有质感，拍照超出片。</p></li></ol><p>【市集隐藏彩蛋】别小看胡同口摆小摊的老爷爷，说不定是退休的京剧团老艺人，他手里的脸谱纹样，是从师父那辈传下来的老样式；逛累了可以去胡同里的老茶馆歇脚，点一杯茉莉花茶（20 元 / 杯），能免费听茶馆里的艺人唱段京剧小调；要是运气好，还能遇到京剧演员来市集采风，说不定能合张影。</p><p>我最后体验了脸谱绘制，选了穆桂英的旦角脸谱，老艺人手把手教我勾线条，还说 “这眉眼要画得俏，才像穆桂英的英气”，画好后装了相框带走，现在摆在书桌前，每次看都能想起市集里的戏韵茶香 —— 这哪是逛市集，是把京剧的雅韵揣进了心里！</p><p>你们有没有逛过京剧相关的非遗市集？或者有没有喜欢的京剧角色？评论区聊一聊～</p>', '京城梨园雅集：藏在长安大戏院旁的京剧市集，一秒入戏', 2);
INSERT INTO `topic` VALUES (8, '2025-12-31 22:26:42', 2, 18, 'http://localhost:7245/1766842821171/4908c069-739b-4864-9be3-c7b2a0faf6ee.png,http://localhost:7245/1766842901281/0e593e29-0884-4555-9819-baf28372725f.png', 'http://localhost:7245/1766842785367/54bc04f9-0548-40bb-9192-4f22722777dd.png', 4, 0, '<p>上周冲了湘西吉首乾州古城里的<strong>苗绣织梦市集</strong>，一脚踏进就撞进了针尖裹满烟火气的苗绣世界 —— 这市集藏在古城北门的老院子片区里，连石板路都沾着靛蓝染布的味儿，全是和之前不一样的新鲜体验！</p><p>【导航 &amp; 时间】📍定位搜 “湘西吉首乾州古城・苗绣织梦市集”（就在古城北门 “三门开” 老院子里，跟着苗歌的调子走准能找着）🕑出摊节奏是：每周六、周日早 9 点 — 晚 5 点，要是赶在湘西赶秋节期间，周五还会加场；建议赶早去，赶秋节限定的 “稻穗纹绣品” 只有早场能抢着，想体验绣娘教学的话，下午 2 点后老院子里会摆绣架。</p><p>【交通怎么去】✈️外地来的话，飞张家界荷花机场 / 铜仁凤凰机场，转高铁到吉首东站，出站坐旅游接驳车（10 块钱，15 分钟直达古城）；🚌本地坐吉首公交 6 路、12 路到 “乾州古城站”，下车走 5 分钟进北门；🚶古城里只能步行，市集藏在老院子的青石板巷里，别着急，闻着靛蓝染布的淡香就能摸到。</p><p>【这市集里的苗绣，是湘西独一份的烈】湘西苗绣的针脚比黔东南更 “烈”：朱砂红配深靛蓝的配色，像极了湘西的山和霞；针法里的 “堆绣” 要把丝线一层叠一层绣，摸起来像绒绒的云朵，绣娘说这是 “把赶秋节的稻穗堆在布上”—— 赶秋节是湘西苗家庆丰收的节，所以市集里的堆绣全是稻穗、谷粒纹，摸上去暖乎乎的。还有湘西独有的 “盘瓠纹”：绣片上的犬形纹样，是湘西苗族的盘瓠图腾（老辈说苗族先祖是盘瓠所化），绣娘会指着绣片笑 “这纹样缝在背扇上，娃能跟着先祖的脚步行路”。连小香囊都有讲究：靛蓝布裹着打籽绣的艾草纹，挂在包上是 “赶湿寒” 的，湘西的山雾重，这是苗家人的小智慧。</p><p>【绣品价格 &amp; 砍价小技巧】💰小物件接地气：绣制艾草香囊 15-30 块，苗银配绣的发簪 30-50 块，挂在衣襟上的小绣牌 20-40 块；💸绣片看针法：堆绣绣片（A4 大小）要 300-800 块，盘瓠纹的大件绣片（半米长）得 1000+，但能让绣娘在边角绣上你的名字缩写（这是市集限定福利）；⚠️砍价要先聊家常：湘西摊主不爱直接砍价，先问 “这绣片绣了多久呀”，绣娘会跟你说 “熬了三个赶秋夜”，再顺着说 “您手真巧，少个零头我揣走当伴手礼”，十有八九能成。</p><p>【选绣品的湘西专属 Tips】</p><ol class=\"auto-hide-last-sibling-br\"><li><p><strong>赶秋限定别错过</strong>：市集里有赶秋节专属的 “稻穗堆绣胸针”，只有节期才有，别在外套上衬得气色都亮；</p></li><li><p><strong>辨手绣看 “毛边”</strong>：湘西手绣的针脚边缘会带点绒绒的毛边（是丝线搓皱的质感），机绣则是死板的齐边，100 块以下的手绣基本没有，别踩坑；</p></li><li><p><strong>现场学绣更值</strong>：老院子里的绣娘会教 “基础打籽绣”，花 20 块买块小布片，跟着绣个小蝴蝶，绣好能直接带走当书签。</p></li></ol><p>【市集藏的软彩蛋】逛累了坐老院子的石凳上，会有苗家阿婆端来糯米茶，边喝边听她唱苗歌 —— 歌里唱的就是绣片上的盘瓠传说；要是赶上赶秋节加场，市集尽头会摆长桌宴，绣娘会把刚绣好的稻穗纹帕子铺在桌布上，连吃饭都裹着苗绣的味儿。</p><p>我最后带了个稻穗堆绣胸针走，现在别在帆布包上，每次摸到绒绒的针脚，都能想起阿婆说 “这是赶秋节的稻穗，揣着走年年都有好收成”—— 这哪是买绣品，是把湘西的秋意裹兜里啦～</p><p>你们逛过带 “节气限定” 的非遗市集吗？评论区晒点你们的淘货，让我下次冲新地方！</p>', '湘西乾州古城藏了个苗绣市集：针尖裹着赶秋节的风', 2);

-- ----------------------------
-- Table structure for topiccollect
-- ----------------------------
DROP TABLE IF EXISTS `topiccollect`;
CREATE TABLE `topiccollect`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '话题收藏主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `UserId` int NULL DEFAULT NULL COMMENT '收藏人',
  `TopicId` int NULL DEFAULT NULL COMMENT '话题',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topiccollect
-- ----------------------------

-- ----------------------------
-- Table structure for topicrecord
-- ----------------------------
DROP TABLE IF EXISTS `topicrecord`;
CREATE TABLE `topicrecord`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '话题浏览记录主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `TopicTypeId` int NULL DEFAULT NULL COMMENT '话题类型',
  `UserId` int NULL DEFAULT NULL COMMENT '浏览人',
  `TopicId` int NULL DEFAULT NULL COMMENT '话题',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题浏览记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topicrecord
-- ----------------------------
INSERT INTO `topicrecord` VALUES (1, '2025-12-26 22:52:30', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (2, '2025-12-27 09:06:10', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (3, '2025-12-27 09:06:16', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (4, '2025-12-21 11:44:53', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (5, '2025-12-21 11:47:49', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (6, '2025-12-21 11:48:35', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (7, '2025-12-21 11:49:00', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (10, '2025-12-21 11:51:58', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (11, '2025-12-21 12:11:08', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (12, '2025-12-21 12:11:14', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (13, '2025-12-21 12:12:49', 3, 3, 3, 7);
INSERT INTO `topicrecord` VALUES (14, '2025-12-21 12:58:35', 3, 1, 3, 2);
INSERT INTO `topicrecord` VALUES (15, '2025-12-21 21:15:13', 2, 4, 2, 8);
INSERT INTO `topicrecord` VALUES (17, '2025-12-26 19:40:33', 4, 2, 4, 5);
INSERT INTO `topicrecord` VALUES (19, '2025-12-26 23:19:15', 4, 4, 4, 8);
INSERT INTO `topicrecord` VALUES (21, '2025-12-26 23:20:00', 4, 1, 4, 2);
INSERT INTO `topicrecord` VALUES (22, '2025-12-27 18:55:31', 4, 4, 4, 8);
INSERT INTO `topicrecord` VALUES (24, '2025-12-27 21:55:26', 4, 4, 4, 8);
INSERT INTO `topicrecord` VALUES (25, '2025-12-27 21:56:54', 4, 4, 4, 8);
INSERT INTO `topicrecord` VALUES (26, '2026-01-02 12:35:10', 4, 4, 4, 8);
INSERT INTO `topicrecord` VALUES (27, '2026-01-02 15:00:12', 4, 1, 4, 2);

-- ----------------------------
-- Table structure for topictype
-- ----------------------------
DROP TABLE IF EXISTS `topictype`;
CREATE TABLE `topictype`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '话题类型主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `Cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '话题类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topictype
-- ----------------------------
INSERT INTO `topictype` VALUES (1, '2025-12-26 00:00:00', 1, '作为国家级非物质文化遗产的剪纸，以纸张为载体，凭剪刻技艺塑就精巧纹样，是承载民俗意趣与美好祈愿的传统手工艺术。', NULL, '剪纸');
INSERT INTO `topictype` VALUES (2, '2025-12-26 00:00:00', 1, '皮影戏是中国传统的民间戏剧艺术形式，以兽皮剪影配唱腔演绎故事，兼具独特的表演趣味与民俗文化内涵。', NULL, '皮影戏');
INSERT INTO `topictype` VALUES (3, '2025-12-26 00:00:00', 1, '京剧是中国经典的戏曲艺术形式，以 “唱念做打” 的综合表演彰显国粹魅力，具有深厚的艺术价值与文化内涵。', NULL, '京剧');
INSERT INTO `topictype` VALUES (4, '2025-12-26 00:00:00', 1, '苗族刺绣是中国苗族传统的民间手工艺形式，以精美纹样承载民族文化记忆，具有浓郁的地域特色与艺术内涵。', NULL, '苗族刺绣');
INSERT INTO `topictype` VALUES (9, '2025-12-26 00:00:00', 1, '福建土楼是中国传统的民居建筑形式，以独特的夯土聚落结构展现营造智慧，具有鲜明的地域特色与文化内涵。', NULL, '福建土楼');
INSERT INTO `topictype` VALUES (10, '2025-03-26 00:00:00', 1, '端午节赛龙舟是中国传统的民俗活动形式，以龙舟竞渡寄托端午祈愿，具有浓厚的节日氛围与文化内涵。', NULL, '端午节赛龙舟');

-- ----------------------------
-- Table structure for useraddress
-- ----------------------------
DROP TABLE IF EXISTS `useraddress`;
CREATE TABLE `useraddress`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '收货地址主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Sex` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `Phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `BelongUserId` int NULL DEFAULT NULL COMMENT '所属用户',
  `Address` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `ProviceCityArea` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
  `Name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货姓名',
  `HouseNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门牌号',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of useraddress
-- ----------------------------
INSERT INTO `useraddress` VALUES (1, '2025-12-27 09:04:58', 2, '保密', '18646215125', 2, '北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室', '北京市,市辖区,东城区', '李佳奇', 'A0006');

-- ----------------------------
-- Table structure for wechatcollection
-- ----------------------------
DROP TABLE IF EXISTS `wechatcollection`;
CREATE TABLE `wechatcollection`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '会话集合主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `LastTime` datetime NULL DEFAULT NULL COMMENT '最新消息时间',
  `LastMessage` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最新消息',
  `OtherUserId` int NULL DEFAULT NULL COMMENT '对方用户',
  `SelfUserId` int NULL DEFAULT NULL COMMENT '本身用户',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话集合表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wechatcollection
-- ----------------------------
INSERT INTO `wechatcollection` VALUES (1, '2025-12-26 23:55:46', 4, '2025-12-26 23:59:24', '想屁吃', 1, 4);
INSERT INTO `wechatcollection` VALUES (2, '2025-12-26 23:55:46', 4, '2025-12-26 23:59:24', '想屁吃', 4, 1);

-- ----------------------------
-- Table structure for wechatmessage
-- ----------------------------
DROP TABLE IF EXISTS `wechatmessage`;
CREATE TABLE `wechatmessage`  (
  `Id` int NOT NULL AUTO_INCREMENT COMMENT '会话消息主键',
  `CreationTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int NULL DEFAULT NULL COMMENT '创建人',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '消息内容',
  `SelfUserId` int NULL DEFAULT NULL COMMENT '自身用户',
  `OtherUserId` int NULL DEFAULT NULL COMMENT '对方用户',
  `SendTime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wechatmessage
-- ----------------------------
INSERT INTO `wechatmessage` VALUES (1, '2025-12-26 23:58:34', 4, '不下单能发货吗\n', 4, 1, '2025-12-26 23:58:34');
INSERT INTO `wechatmessage` VALUES (2, '2025-12-26 23:59:24', 1, '想屁吃', 1, 4, '2025-12-26 23:59:24');

SET FOREIGN_KEY_CHECKS = 1;
