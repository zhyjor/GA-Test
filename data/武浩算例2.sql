/*
Navicat MySQL Data Transfer

Source Server         : 虚拟计量
Source Server Version : 50627
Source Host           : 10.1.16.50:3306
Source Database       : 武浩算例2

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2018-03-16 16:21:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for CenterStation
-- ----------------------------
DROP TABLE IF EXISTS `CenterStation`;
CREATE TABLE `CenterStation` (
  `SequenceNumber` int(8) NOT NULL AUTO_INCREMENT,
  `Time` datetime DEFAULT NULL,
  `Region` varchar(25) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Q` double(15,4) DEFAULT NULL,
  `Q_shouldbe` float(15,4) DEFAULT NULL COMMENT '统计的流量,m3/d',
  `Opti_Q` float(15,4) DEFAULT NULL,
  `OutgoingP` double(10,4) DEFAULT NULL,
  `OutP_shouldbe` float(10,4) DEFAULT NULL COMMENT '计算后的出站压力,MPa',
  `InletP` double(10,4) DEFAULT NULL,
  `OutgoingT` double(4,1) DEFAULT NULL,
  `InletT` double(4,1) DEFAULT NULL,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CenterStation
-- ----------------------------
INSERT INTO `CenterStation` VALUES ('1', '2017-12-04 19:53:18', '柿庄', 'ZYCLC', null, null, null, '3.0000', null, '0.0200', '26.0', '24.0');

-- ----------------------------
-- Table structure for CoalSeam
-- ----------------------------
DROP TABLE IF EXISTS `CoalSeam`;
CREATE TABLE `CoalSeam` (
  `SequenceNumber` int(5) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `Region` varchar(50) DEFAULT NULL,
  `Time` datetime DEFAULT NULL COMMENT '时间',
  `Name` varchar(25) DEFAULT NULL COMMENT '名称',
  `IFP` double(8,2) DEFAULT NULL COMMENT '原始地层压力，MPa',
  `CoalSeamA` double(8,2) DEFAULT NULL COMMENT '煤层面积， 单位：Km2',
  `Thickness` float(25,2) DEFAULT NULL COMMENT '平均厚度，m',
  `Depth` float(25,2) DEFAULT NULL COMMENT '深度，m',
  `LangmuirP` float(25,2) DEFAULT NULL COMMENT 'Langmuir压力，MPa',
  `LangmuirV` float(25,2) DEFAULT NULL COMMENT 'Langmuir体积,m3/ton',
  `PoreCompressibility` float(25,2) DEFAULT NULL COMMENT '孔隙压缩系数, 10^-9.MPa^-1',
  `ContentofGas` float(25,2) DEFAULT NULL COMMENT '含气量，m3/ton',
  `GasSaturation` float(25,2) DEFAULT NULL,
  `T` double(8,2) DEFAULT NULL COMMENT '煤层温度，℃',
  `InitialPorosity` double(8,2) DEFAULT NULL COMMENT '煤层初始孔隙度（MT）',
  `InitialPermeability` double(8,2) DEFAULT NULL COMMENT '煤层初始渗透率，单位：mD',
  `NowPorosity` double(8,2) DEFAULT NULL COMMENT '目前的煤层孔隙度',
  `NowPermeability` double(8,0) DEFAULT NULL COMMENT '目前煤层渗透率,mD',
  `VolumeModulus` double(8,2) DEFAULT NULL COMMENT '体积弹性模量，GPa',
  `CumulativeWaterQ` double(8,1) DEFAULT NULL COMMENT '累计产水量，m3',
  `CumulativeGasQ` double(8,1) DEFAULT NULL,
  `Information` mediumtext COMMENT ' 其他信息',
  `St` double(25,4) DEFAULT NULL COMMENT '煤岩的抗拉强度，MPa',
  `fai` double(25,4) DEFAULT NULL COMMENT '煤岩内摩擦角',
  `fair` double(25,4) DEFAULT NULL COMMENT '残余内摩擦角，°',
  `Cohesion` double(25,4) DEFAULT NULL COMMENT '岩石粘聚力,MPa',
  `Cohesion_R` double(25,4) DEFAULT NULL COMMENT '残余内聚力，MPa',
  `UCS` double(25,4) DEFAULT NULL COMMENT '岩石单轴实验下的抗压强度，MPa',
  `sigmaUCS` double(25,4) DEFAULT NULL COMMENT '峰后残余强度，MPa',
  `Posong` double(25,4) DEFAULT NULL COMMENT '岩石泊松比',
  `re` double(25,4) DEFAULT NULL COMMENT '渗流半径,m',
  `deltaBcu` float(25,4) DEFAULT NULL COMMENT '单轴时的强度退化量',
  `Xd` float(25,5) DEFAULT NULL COMMENT '峰后强度退化指数',
  `md` float(25,5) DEFAULT NULL COMMENT '常数',
  `CoalSeamDensity` double(10,4) DEFAULT NULL COMMENT '岩层密度，kg/m3',
  `NowP` double(10,4) DEFAULT NULL COMMENT '目前地层压力，MPa',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CoalSeam
-- ----------------------------
INSERT INTO `CoalSeam` VALUES ('1', null, '2016-11-22 14:52:56', '柿庄3#煤层', '3.00', null, '6.00', '839.00', null, null, null, null, null, '24.20', '5.76', '0.28', '1.00', '1', '2.40', null, null, null, '0.5000', null, null, '4.9000', null, null, null, '0.3700', null, null, null, null, null, '3.0800');
INSERT INTO `CoalSeam` VALUES ('2', null, '2016-11-22 14:52:59', '柿庄15#煤层', '6.00', null, '3.00', '949.00', null, null, null, null, null, '25.00', null, '0.28', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `CoalSeam` VALUES ('3', null, '2016-11-22 14:53:02', '例子1', '13.00', '202500.00', '9.00', '472.00', '3.00', '35.00', '4.00', '25.00', '0.00', '23.00', '2.00', '6.00', null, null, '2.73', null, null, null, '0.0000', '36.6000', null, '1.4600', null, '1.4600', '3.5000', '0.3800', null, null, null, null, null, null);
INSERT INTO `CoalSeam` VALUES ('4', null, '2016-11-22 14:53:07', '韩城1', null, null, null, '355.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.2400', '39.7000', '38.9000', '1.3000', '0.6100', '0.0000', '0.6100', null, null, null, null, null, null, '6.3000');
INSERT INTO `CoalSeam` VALUES ('5', null, '2016-11-22 14:53:10', '韩城2', null, null, null, '489.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.2300', '40.8000', '39.1000', '2.4000', '0.6300', '0.0000', '0.6300', null, null, null, null, null, null, '12.4000');
INSERT INTO `CoalSeam` VALUES ('6', null, '2016-12-19 13:36:02', '樟村', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3.62', null, null, null, '0.7400', null, null, '0.0000', null, '14.8500', null, null, null, null, null, null, null, null);
INSERT INTO `CoalSeam` VALUES ('7', null, '2016-12-19 13:36:10', '例子2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `CoalSeam` VALUES ('8', null, '2016-12-19 13:36:10', '例子3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `CoalSeam` VALUES ('9', null, '2016-12-19 13:36:11', '樊庄', null, null, '6.00', '679.00', null, null, null, null, null, '33.50', '2.00', '1.00', null, null, '0.24', null, null, null, '0.5000', null, null, null, null, '4.9000', '0.0000', '0.3700', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for Equipment
-- ----------------------------
DROP TABLE IF EXISTS `Equipment`;
CREATE TABLE `Equipment` (
  `SequenceNumber` int(5) NOT NULL AUTO_INCREMENT,
  `Time` datetime DEFAULT NULL,
  `Region` varchar(75) DEFAULT NULL,
  `Name` varchar(75) DEFAULT NULL,
  `RatedPower` double(8,2) DEFAULT NULL COMMENT '额定功率',
  `RatedSpeed` int(8) DEFAULT NULL COMMENT '额定转速',
  `InletP` double(8,0) DEFAULT NULL,
  `InletT` double(4,1) DEFAULT NULL,
  `OutgoingP` double(8,4) DEFAULT NULL COMMENT '输出压力',
  `OutgoingT` double(4,1) DEFAULT NULL COMMENT '输出温度',
  `Q` double(10,4) DEFAULT NULL,
  `I` double(8,2) DEFAULT NULL,
  `V` double(8,2) DEFAULT NULL,
  `ActualPower` double(8,2) DEFAULT NULL COMMENT '实际功率',
  `Condition` varchar(8) DEFAULT NULL,
  `Information` mediumtext,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Equipment
-- ----------------------------
INSERT INTO `Equipment` VALUES ('1', '2015-10-31 20:17:48', '0', '0', '1.00', '2', '3', '20.0', '20.0000', '20.0', '20.0000', '20.00', '20.00', '20.00', '20', null);

-- ----------------------------
-- Table structure for Pipeline
-- ----------------------------
DROP TABLE IF EXISTS `Pipeline`;
CREATE TABLE `Pipeline` (
  `SequenceNumber` int(5) NOT NULL AUTO_INCREMENT,
  `Region` varchar(25) DEFAULT NULL,
  `Time` datetime DEFAULT NULL,
  `StartPointName` varchar(25) DEFAULT NULL,
  `StartP` double(8,4) DEFAULT NULL COMMENT '起点压力，MPa',
  `Start_P_Shouldbe` double(8,4) DEFAULT NULL COMMENT '根据CL反推算结果，得到的节点应有的压力，MPa',
  `Opti_StartP` double(8,4) DEFAULT NULL COMMENT '根据优化后的流量来反推的节点压力，MPa',
  `StartT` double(4,1) DEFAULT NULL COMMENT '起点温度，℃',
  `EndPointName` varchar(8) DEFAULT NULL,
  `EndP` double(8,4) DEFAULT NULL COMMENT '终点压力，MPa',
  `EndP_shouldbe` double(8,4) DEFAULT NULL,
  `Opti_EndP` double(8,4) DEFAULT NULL COMMENT '根据优化结果得到的终点压力，MPa',
  `EndT` double(4,1) DEFAULT NULL COMMENT '管末温度，摄氏度',
  `Length` double(8,2) DEFAULT NULL COMMENT '管长，m',
  `D` double(8,2) DEFAULT NULL COMMENT '管径，mm',
  `Economical_Speed` double(8,2) DEFAULT NULL COMMENT '管道经济流速，m/s',
  `Ecnomical_Q` double(10,2) DEFAULT NULL,
  `Ke` double(8,5) DEFAULT NULL COMMENT '粗糙度，mm',
  `Thickness` double(4,1) DEFAULT NULL COMMENT '壁厚，mm',
  `TheoreticalQ` double(10,2) DEFAULT NULL COMMENT '理论最大流量，m^3/d',
  `ActualQ` double(10,2) DEFAULT NULL COMMENT '实际流量，m^3/d',
  `Q_shouldbe` double(10,2) DEFAULT NULL,
  `UtilizationEfficiency` double(8,2) DEFAULT NULL COMMENT '管道利用率',
  `TransportEfficiency` double(8,2) DEFAULT NULL COMMENT '输送效率',
  `Opti_Q` float(10,4) DEFAULT NULL COMMENT '优化后的结果,m3/d',
  `Opti_Efficiency` float(6,2) DEFAULT NULL COMMENT '由整体优化结果得到的输送效率',
  `SurroundT` double(8,1) DEFAULT NULL COMMENT '环境温度，度',
  `PrecipitatedWaterQ` double(8,4) DEFAULT NULL COMMENT '析出水量',
  `mozu_para` float(25,4) DEFAULT NULL COMMENT '管道摩阻系数',
  `Real_Pipe_CL` float(25,5) DEFAULT NULL COMMENT '管道的实际CL值，根据实际数据计算',
  `Pipe_CL` float(25,5) DEFAULT NULL COMMENT '优化中的常数,理论值',
  `Holdup` mediumtext,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Pipeline
-- ----------------------------
INSERT INTO `Pipeline` VALUES ('1', null, '2017-12-04 19:53:16', 'A2-001', '0.2056', '0.2056', '0.2159', '25.0', 'FZ-4', '0.2014', '0.2014', '0.2087', '25.0', '265.00', '50.00', '7.00', '2305.51', '0.05000', '5.0', '1500.96', '1500.00', '1500.00', '0.65', '1.00', '2817.4109', '1.22', '25.0', '0.0000', '0.0275', '8458297409536.00000', '8447491833856.00000', '0');
INSERT INTO `Pipeline` VALUES ('2', null, '2017-12-04 19:53:16', 'A2-002', '0.2297', '0.2297', '0.3705', '25.0', 'FZ-1', '0.2123', '0.2123', '0.3135', '25.0', '1550.00', '50.00', '7.00', '2439.01', '0.05000', '5.0', '1299.12', '1300.00', '1300.00', '0.53', '1.00', '4324.9219', '1.77', '25.0', '0.0000', '0.0275', '49342945689600.00000', '49409857421312.00000', '0');
INSERT INTO `Pipeline` VALUES ('3', null, '2017-12-04 19:53:16', 'A2-003', '0.2506', '0.2506', '0.5774', '25.0', 'FZ-1', '0.2123', '0.2123', '0.3135', '25.0', '1350.00', '50.00', '7.00', '2520.59', '0.05000', '5.0', '2098.60', '2100.00', '2100.00', '0.83', '1.00', '8121.0532', '3.14', '25.0', '0.0000', '0.0275', '42976961101824.00000', '43034393706496.00000', '0');
INSERT INTO `Pipeline` VALUES ('4', null, '2017-12-04 19:53:16', 'A2-004', '0.2214', '0.2214', '0.2585', '25.0', 'FZ-1', '0.2123', '0.2123', '0.3135', '25.0', '940.00', '50.00', '7.00', '2407.05', '0.05000', '5.0', '1198.59', '1200.00', '1200.00', '0.50', '1.00', '2780.0959', '1.15', '25.0', '0.0000', '0.0275', '29894417317888.00000', '29964688687104.00000', '0');
INSERT INTO `Pipeline` VALUES ('5', null, '2017-12-04 19:53:16', 'A2-005', '0.2160', '0.2160', '0.2919', '25.0', 'FZ-1', '0.2123', '0.2123', '0.3135', '25.0', '1500.00', '50.00', '7.00', '2386.40', '0.05000', '5.0', '602.44', '600.00', '600.00', '0.25', '1.00', '2958.0610', '1.24', '25.0', '0.0000', '0.0275', '48205186203648.00000', '47815992541184.00000', '0');
INSERT INTO `Pipeline` VALUES ('6', null, '2017-12-04 19:53:16', 'A2-006', '0.2296', '0.2296', '0.2599', '25.0', 'FZ-2', '0.2235', '0.2235', '0.2753', '25.0', '1450.00', '50.00', '10.00', '3543.77', '0.05000', '5.0', '802.13', '800.00', '800.00', '0.23', '1.00', '2004.7159', '0.57', '25.0', '0.0000', '0.0275', '46468324917248.00000', '46222123466752.00000', '0');
INSERT INTO `Pipeline` VALUES ('7', null, '2017-12-04 19:53:16', 'A2-007', '0.2393', '0.2393', '0.2999', '25.0', 'FZ-2', '0.2235', '0.2235', '0.2753', '25.0', '1445.00', '50.00', '10.00', '3596.98', '0.05000', '5.0', '1302.74', '1300.00', '1300.00', '0.36', '1.00', '2993.4270', '0.83', '25.0', '0.0000', '0.0275', '46257309483008.00000', '46062739914752.00000', '0');
INSERT INTO `Pipeline` VALUES ('8', null, '2017-12-04 19:53:16', 'A2-008', '0.2505', '0.2505', '0.3250', '25.0', 'FZ-2', '0.2235', '0.2235', '0.2753', '25.0', '1065.00', '50.00', '10.00', '3659.03', '0.05000', '5.0', '2000.37', '2000.00', '2000.00', '0.55', '1.00', '4087.3230', '1.12', '25.0', '0.0000', '0.0275', '33961835233280.00000', '33949355081728.00000', '0');
INSERT INTO `Pipeline` VALUES ('9', null, '2017-12-04 19:53:16', 'A2-009', '0.2490', '0.2490', '0.2630', '25.0', 'FZ-2', '0.2235', '0.2235', '0.2753', '25.0', '1005.00', '50.00', '10.00', '3650.68', '0.05000', '5.0', '1998.97', '2000.00', '2000.00', '0.55', '1.00', '2512.9441', '0.69', '25.0', '0.0000', '0.0275', '32003682467840.00000', '32036714708992.00000', '0');
INSERT INTO `Pipeline` VALUES ('10', null, '2017-12-04 19:53:16', 'A2-010', '0.3873', '0.3873', '0.5485', '25.0', 'A2-011', '0.3467', '0.3467', '0.6707', '25.0', '1420.00', '50.00', '10.00', '5071.00', '0.05000', '5.0', '2500.72', '2500.00', '2500.00', '0.49', '1.00', '6762.7490', '1.19', '25.0', '0.0000', '0.0275', '45291881365504.00000', '45265805377536.00000', '0');
INSERT INTO `Pipeline` VALUES ('11', null, '2017-12-04 19:53:16', 'A2-011', '0.3467', '0.3467', '0.6707', '25.0', 'FZ-3', '0.2290', '0.2290', '0.2956', '25.0', '1465.00', '50.00', '10.00', '4241.19', '0.05000', '5.0', '3820.23', '3820.00', '3820.00', '0.90', '1.00', '9823.4961', '2.08', '25.0', '0.0000', '0.0275', '46705823186944.00000', '46700286705664.00000', '0');
INSERT INTO `Pipeline` VALUES ('12', null, '2017-12-04 19:53:16', 'A2-012', '0.2375', '0.2375', '0.2567', '25.0', 'FZ-3', '0.2290', '0.2290', '0.2956', '25.0', '1095.00', '50.00', '10.00', '3616.57', '0.05000', '5.0', '1100.72', '1100.00', '1100.00', '0.30', '1.00', '2013.4590', '0.56', '25.0', '0.0000', '0.0275', '34951126843392.00000', '34905673170944.00000', '0');
INSERT INTO `Pipeline` VALUES ('13', null, '2017-12-04 19:53:16', 'A2-013', '0.2451', '0.2451', '0.3118', '25.0', 'FZ-3', '0.2290', '0.2290', '0.2956', '25.0', '1125.00', '50.00', '10.00', '3658.31', '0.05000', '5.0', '1503.04', '1500.00', '1500.00', '0.41', '1.00', '3573.1621', '0.98', '25.0', '0.0000', '0.0275', '36007680409600.00000', '35861995454464.00000', '0');
INSERT INTO `Pipeline` VALUES ('14', null, '2017-12-04 19:53:16', 'A2-014', '0.2600', '0.2600', '0.2866', '25.0', 'FZ-3', '0.2290', '0.2290', '0.2956', '25.0', '945.00', '50.00', '10.00', '3740.98', '0.05000', '5.0', '2300.63', '2300.00', '2300.00', '0.61', '1.00', '3194.2771', '0.85', '25.0', '0.0000', '0.0275', '30140658614272.00000', '30124074336256.00000', '0');
INSERT INTO `Pipeline` VALUES ('15', null, '2017-12-04 19:53:16', 'A2-015', '0.2908', '0.2908', '0.2514', '25.0', 'FZ-5', '0.2042', '0.2042', '0.2196', '25.0', '1565.00', '50.00', '10.00', '3790.53', '0.05000', '5.0', '3001.01', '3000.00', '3000.00', '0.79', '1.00', '2151.6289', '0.57', '25.0', '0.0000', '0.0275', '49921503789056.00000', '49888016465920.00000', '0');
INSERT INTO `Pipeline` VALUES ('16', null, '2017-12-04 19:53:16', 'A2-016', '0.2062', '0.2062', '0.2530', '25.0', 'FZ-5', '0.2042', '0.2042', '0.2196', '25.0', '1115.00', '50.00', '10.00', '3311.99', '0.05000', '5.0', '506.36', '500.00', '500.00', '0.15', '0.99', '2594.4370', '0.78', '25.0', '0.0000', '0.0275', '36452893196288.00000', '35543219961856.00000', '0');
INSERT INTO `Pipeline` VALUES ('17', null, '2017-12-04 19:53:16', 'A2-017', '0.2303', '0.2303', '0.5959', '25.0', 'FZ-5', '0.2042', '0.2042', '0.2196', '25.0', '1515.00', '50.00', '10.00', '3444.69', '0.05000', '5.0', '1599.94', '1600.00', '1600.00', '0.46', '1.00', '7919.4790', '2.26', '25.0', '0.0000', '0.0275', '48290271854592.00000', '48294151585792.00000', '0');
INSERT INTO `Pipeline` VALUES ('18', null, '2017-12-04 19:53:16', 'FZ-1', '0.2123', '0.2123', '0.3135', '25.0', 'FZ-4', '0.2014', '0.2014', '0.2087', '25.0', '1500.00', '90.00', '7.00', '7892.40', '0.05000', '8.2', '5194.64', '5200.00', '5200.00', '0.66', '1.00', '18184.1328', '2.28', '25.0', null, '0.0224', '1846730031104.00000', '1850545274880.00000', null);
INSERT INTO `Pipeline` VALUES ('19', null, '2017-12-04 19:53:16', 'FZ-2', '0.2235', '0.2235', '0.2753', '25.0', 'FZ-4', '0.2014', '0.2014', '0.2087', '25.0', '2250.00', '90.00', '7.00', '8038.94', '0.05000', '8.2', '6094.24', '6100.00', '6100.00', '0.76', '1.00', '11598.4102', '1.44', '25.0', null, '0.0224', '2770579488768.00000', '2775817912320.00000', null);
INSERT INTO `Pipeline` VALUES ('20', null, '2017-12-04 19:53:16', 'FZ-3', '0.2290', '0.2290', '0.2956', '25.0', 'FZ-5', '0.2042', '0.2042', '0.2196', '25.0', '1250.00', '90.00', '7.00', '8146.48', '0.05000', '8.2', '8718.68', '8720.00', '8720.00', '1.07', '1.00', '18604.3945', '2.16', '25.0', null, '0.0224', '1541654708224.00000', '1542121062400.00000', null);
INSERT INTO `Pipeline` VALUES ('21', null, '2017-12-04 19:53:16', 'FZ-4', '0.2014', '0.2014', '0.2087', '25.0', '集气2站', '0.2000', '0.2000', '0.2000', '24.0', '945.00', '160.00', '7.00', '27981.55', '0.05000', '10.0', '12906.98', '12800.00', '12800.00', '0.46', '0.99', '32599.9531', '1.16', '25.0', null, '0.0181', '38361800704.00000', '37728530432.00000', null);
INSERT INTO `Pipeline` VALUES ('22', null, '2017-12-04 19:53:16', 'FZ-5', '0.2042', '0.2042', '0.2196', '25.0', '集气2站', '0.2000', '0.2000', '0.2000', '24.0', '2500.00', '160.00', '10.00', '40160.32', '0.05000', '10.0', '13776.53', '13820.00', '13820.00', '0.34', '1.00', '31269.9395', '0.75', '25.0', null, '0.0181', '99183951872.00000', '99810934784.00000', null);

-- ----------------------------
-- Table structure for Station
-- ----------------------------
DROP TABLE IF EXISTS `Station`;
CREATE TABLE `Station` (
  `SequenceNumber` int(4) NOT NULL AUTO_INCREMENT,
  `Time` datetime DEFAULT NULL,
  `Region` varchar(25) DEFAULT NULL,
  `Name` varchar(75) DEFAULT NULL,
  `Q` double(10,4) DEFAULT NULL,
  `Q_shouldbe` float(10,4) DEFAULT NULL COMMENT '统计得到的流量，m3/d',
  `Opti_Q` double(10,4) DEFAULT NULL,
  `InletP` double(10,4) DEFAULT NULL COMMENT '进站压力，MPa',
  `OutgoingP` double(10,4) DEFAULT NULL COMMENT '出站压力，MPa',
  `OutP_shouldbe` double(10,4) DEFAULT NULL COMMENT '计算后的出站压力，MPa',
  `Opti_OutP` double(10,4) DEFAULT NULL,
  `InletT` double(4,1) DEFAULT NULL COMMENT '进站温度，',
  `OutgoingT` double(4,1) DEFAULT NULL COMMENT '出站温度',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Station
-- ----------------------------
INSERT INTO `Station` VALUES ('1', '2017-12-04 19:53:18', '1', '集气2站', '26620.0000', '26620.0000', '63869.8920', '0.2000', '1.2000', null, null, '24.0', '25.0');

-- ----------------------------
-- Table structure for Valve
-- ----------------------------
DROP TABLE IF EXISTS `Valve`;
CREATE TABLE `Valve` (
  `SequenceNumber` int(5) NOT NULL AUTO_INCREMENT,
  `Time` datetime DEFAULT NULL,
  `Region` varchar(25) DEFAULT NULL,
  `Name` varchar(75) DEFAULT NULL,
  `Q` double(15,4) DEFAULT NULL,
  `Q_shouldbe` float(15,4) DEFAULT NULL COMMENT 'm3/d',
  `Opti_Q` double(15,4) DEFAULT NULL,
  `P_shouldbe` double(10,4) DEFAULT NULL,
  `Opti_P` double(10,4) DEFAULT NULL,
  `P` double(10,4) DEFAULT NULL,
  `T` double(4,1) DEFAULT NULL,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Valve
-- ----------------------------
INSERT INTO `Valve` VALUES ('1', '2017-12-04 19:53:17', '韩城', 'FZ-2', '6100.0000', '6100.0000', '11598.4100', '0.2235', '0.2753', '0.2235', '25.0');
INSERT INTO `Valve` VALUES ('2', '2017-12-04 19:53:17', '韩城', 'FZ-3', '8720.0000', '8720.0000', '18604.3940', '0.2290', '0.2956', '0.2290', '25.0');
INSERT INTO `Valve` VALUES ('3', '2017-12-04 19:53:17', '韩城', 'FZ-1', '5200.0000', '5200.0000', '18184.1320', '0.2123', '0.3135', '0.2123', '25.0');
INSERT INTO `Valve` VALUES ('4', '2017-12-04 19:53:17', '韩城', 'FZ-4', '12800.0000', '12800.0000', '32599.9530', '0.2014', '0.2087', '0.2014', '25.0');
INSERT INTO `Valve` VALUES ('5', '2017-12-04 19:53:17', '韩城', 'FZ-5', '13820.0000', '13820.0000', '31269.9390', '0.2042', '0.2196', '0.2042', '25.0');

-- ----------------------------
-- Table structure for Well
-- ----------------------------
DROP TABLE IF EXISTS `Well`;
CREATE TABLE `Well` (
  `SequenceNumber` int(8) NOT NULL DEFAULT '0' COMMENT '序号',
  `Time` datetime DEFAULT NULL COMMENT '时间，不需要填写',
  `Region` varchar(25) DEFAULT NULL COMMENT '油井所属区块',
  `StratigraphicName` varchar(25) DEFAULT NULL COMMENT '所属地层名称',
  `Name` varchar(25) DEFAULT NULL COMMENT '井的名字',
  `CasingD` float(8,2) DEFAULT NULL COMMENT '套管内径，mm',
  `TubingD` float(8,2) DEFAULT NULL COMMENT '油管直径，mm',
  `FormationDepth` float(8,2) DEFAULT NULL COMMENT '煤层深度,m',
  `Q` double(8,2) DEFAULT NULL COMMENT '产气量，m3/d',
  `CasingP` double(8,4) DEFAULT NULL COMMENT '套压，气嘴前，MPa',
  `BackP` double(8,4) DEFAULT NULL COMMENT '回压，气嘴后压力，MPa',
  `Working_Fluid_Level` double(8,2) DEFAULT NULL COMMENT '动液面深度，m',
  `Well_bore_P` double(8,2) DEFAULT NULL COMMENT '井筒产生的压差，即套压与井底流压的差，MPa',
  `FBHP` double(8,4) DEFAULT NULL COMMENT '井底流压，MPa',
  `ProductivityEquaA` double(25,6) DEFAULT NULL COMMENT '产能二项式系数，',
  `ProductivityEquaB` double(25,11) DEFAULT NULL COMMENT '产能二项式系数,',
  `Pr` float(10,5) DEFAULT NULL COMMENT '地层压力',
  `Pr_shouldbe` float(10,4) DEFAULT NULL COMMENT '根据生产数据以及AB值反推地层压力，MPa',
  `BackP_shouldbe` double(8,4) DEFAULT NULL,
  `EquipmentRunningEfficiency` double(4,2) DEFAULT NULL COMMENT '设备效率',
  `Pwf_max` float(10,4) DEFAULT NULL COMMENT '建议井底流压最大值，MPa',
  `Best_Pwf` float(10,4) DEFAULT NULL,
  `Pwf_min` float(10,4) DEFAULT NULL COMMENT '建议井底流压最小值，MPa',
  `Q_max` float(10,4) DEFAULT NULL COMMENT '建议最大产量',
  `Best_Q` double(10,4) DEFAULT NULL COMMENT '最佳产量，m3/d',
  `Q_min` float(10,4) DEFAULT NULL COMMENT '建议最小产量',
  `Well_Pipenet_Matching` float(6,2) DEFAULT NULL COMMENT '井与管网匹配度，',
  `Well_Matching` float(6,2) DEFAULT NULL COMMENT '井间匹配度',
  `Opti_BackP` double(8,4) DEFAULT NULL COMMENT '优化后的回压，MPa',
  `PFThickness` double(8,4) DEFAULT NULL COMMENT '产层厚度，m',
  `ChockD` double(4,1) DEFAULT NULL COMMENT '油嘴直径，mm',
  `T` double(4,1) DEFAULT NULL COMMENT '温度，摄氏度',
  `Well_individually_product_Q` float(10,4) DEFAULT NULL COMMENT '假设其他井停产时，该井的产量，m3/d',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Well
-- ----------------------------
INSERT INTO `Well` VALUES ('1', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-001', '99.60', '48.30', '381.35', '1500.00', '0.6000', '0.2056', '269.35', '1.14', '1.7200', '0.003206', '-0.00000030900', '2.63010', '2.6594', '0.2056', '0.44', '2.6301', '0.5810', '0.5810', '2817.4119', '2817.4110', '0.0000', '1.00', '1.00', '0.2159', '4.0000', null, '25.0', '3022.3352');
INSERT INTO `Well` VALUES ('2', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-002', '99.60', '48.30', '399.53', '1300.00', '0.5400', '0.2297', '239.53', '1.62', '2.1400', '0.001110', '-0.00000001380', '2.20910', '2.4493', '0.2297', '0.04', '2.2091', '0.5810', '0.5810', '4324.9229', '4324.9220', '0.0000', '1.00', '1.00', '0.3705', '4.0000', null, '25.0', '4486.0820');
INSERT INTO `Well` VALUES ('3', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-003', '99.60', '48.30', '474.14', '2100.00', '0.6500', '0.2506', '220.14', '2.56', '3.1900', '0.001350', '-0.00000001170', '3.24490', '3.5999', '0.2506', '0.02', '3.2449', '0.5810', '0.5810', '8121.0532', '8121.0530', '0.0000', '0.99', '0.97', '0.5774', '4.0000', null, '25.0', '8042.1309');
INSERT INTO `Well` VALUES ('4', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-004', '99.60', '48.30', '359.61', '1200.00', '0.4800', '0.2214', '230.61', '1.31', '1.7700', '0.001620', '-0.00000001590', '2.17220', '2.2481', '0.2214', '0.25', '2.1722', '0.5810', '0.5810', '2780.0964', '2780.0960', '0.0000', '1.00', '1.00', '0.2585', '4.0000', null, '25.0', '2950.4441');
INSERT INTO `Well` VALUES ('5', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-005', '99.60', '48.30', '339.03', '600.00', '0.7900', '0.2160', '196.03', '1.45', '2.2200', '0.002400', '-0.00000004020', '2.66180', '2.5207', '0.2160', '0.21', '2.6618', '0.5810', '0.5810', '2958.0615', '2958.0610', '0.0000', '1.00', '1.00', '0.2919', '4.0000', null, '25.0', '3066.9492');
INSERT INTO `Well` VALUES ('6', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-006', '99.60', '48.30', '310.50', '800.00', '0.6800', '0.2296', '233.50', '0.79', '1.4500', '0.001060', '-0.00000000200', '1.56670', '1.7173', '0.2296', '0.12', '1.5667', '0.5810', '0.5810', '2004.7168', '2004.7160', '0.0000', '1.00', '1.00', '0.2599', '4.0000', null, '25.0', '2255.9407');
INSERT INTO `Well` VALUES ('7', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-007', '99.60', '48.30', '323.77', '1300.00', '0.7600', '0.2393', '245.77', '0.80', '1.5400', '0.001481', '-0.00000001670', '2.14970', '2.0661', '0.2393', '0.39', '2.1497', '0.5810', '0.5810', '2993.4270', '2993.4270', '0.0000', '1.00', '1.00', '0.2999', '4.0000', null, '25.0', '3161.8560');
INSERT INTO `Well` VALUES ('8', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-008', '99.60', '48.30', '285.63', '2000.00', '0.7000', '0.2505', '210.63', '0.77', '1.4500', '0.000960', '-0.00000001560', '2.00020', '1.9900', '0.2505', '0.39', '2.0002', '0.5810', '0.5810', '4087.3230', '4087.3230', '0.0000', '1.00', '1.00', '0.3250', '4.0000', null, '25.0', '4334.7832');
INSERT INTO `Well` VALUES ('9', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-009', '99.60', '48.30', '280.82', '2000.00', '0.6300', '0.2490', '225.82', '0.57', '1.1800', '0.001379', '-0.00000004020', '1.88390', '1.9974', '0.2490', '0.54', '1.8839', '0.5810', '0.5810', '2512.9448', '2512.9440', '0.0000', '1.00', '1.00', '0.2630', '4.0000', null, '25.0', '2737.7839');
INSERT INTO `Well` VALUES ('10', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-010', '99.60', '48.30', '359.19', '2500.00', '0.8900', '0.3873', '237.19', '1.24', '2.1100', '0.001640', '-0.00000006600', '2.90000', '2.8530', '0.3873', '0.41', '2.9000', '0.5810', '0.5810', '6762.7490', '6762.7490', '0.0000', '0.96', '0.89', '0.5485', '4.0000', null, '25.0', '6466.9404');
INSERT INTO `Well` VALUES ('11', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-011', '99.60', '48.30', '347.70', '1320.00', '0.6600', '0.3467', '273.70', '0.76', '1.4000', '0.001375', '-0.00000001650', '2.09560', '1.9355', '0.3467', '0.55', '2.0956', '0.5810', '0.5810', '3060.7473', '3060.7470', '0.0000', '1.00', '0.91', '0.6707', '4.0000', null, '25.0', '3241.3501');
INSERT INTO `Well` VALUES ('12', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-012', '99.60', '48.30', '321.78', '1100.00', '0.5900', '0.2375', '264.78', '0.59', '1.1600', '0.003019', '-0.00000014000', '2.41840', '2.1206', '0.2375', '0.68', '2.4184', '0.5810', '0.5810', '2013.4596', '2013.4590', '0.0000', '1.00', '1.00', '0.2567', '4.0000', null, '25.0', '2126.3757');
INSERT INTO `Well` VALUES ('13', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-013', '99.60', '48.30', '431.61', '1500.00', '0.7600', '0.2451', '288.61', '1.45', '2.1900', '0.002340', '-0.00000001670', '2.91300', '2.8755', '0.2451', '0.31', '2.9130', '0.5810', '0.5810', '3573.1621', '3573.1620', '0.0000', '1.00', '1.00', '0.3118', '4.0000', null, '25.0', '3676.6685');
INSERT INTO `Well` VALUES ('14', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-014', '99.60', '48.30', '445.20', '2300.00', '0.6500', '0.2600', '278.20', '1.69', '2.3200', '0.004554', '-0.00000035000', '3.36350', '3.7423', '0.2600', '0.38', '3.3635', '0.5810', '0.5810', '3194.2771', '3194.2770', '0.0000', '1.00', '1.00', '0.2866', '4.0000', null, '25.0', '3304.4446');
INSERT INTO `Well` VALUES ('15', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-015', '99.60', '48.30', '440.68', '3000.00', '0.4900', '0.2908', '260.00', '1.83', '1.3200', '0.003160', '-0.00000031000', '2.38780', '2.9039', '0.2908', '0.59', '2.3878', '0.5810', '0.5810', '2151.6299', '2151.6290', '0.0000', '1.00', '1.00', '0.2514', '4.0000', null, '25.0', '2298.9038');
INSERT INTO `Well` VALUES ('16', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-016', '99.60', '48.30', '399.53', '500.00', '0.5500', '0.2062', '155.53', '2.46', '2.9900', '0.006033', '-0.00000030900', '3.72960', '3.4466', '0.2062', '0.23', '3.7296', '0.5810', '0.5810', '2594.4375', '2594.4370', '0.0000', '1.00', '1.00', '0.2530', '4.0000', null, '25.0', '2654.2739');
INSERT INTO `Well` VALUES ('17', '2017-12-04 19:53:15', '集气2站', '韩城2', 'A2-017', '99.60', '48.30', '474.14', '1600.00', '0.5600', '0.2303', '201.14', '2.75', '3.2900', '0.001650', '-0.00000001670', '3.51530', '3.6635', '0.2303', '0.08', '3.5153', '0.5810', '0.5810', '7919.4790', '7919.4790', '0.0000', '0.99', '0.98', '0.5959', '4.0000', null, '25.0', '7846.2690');
