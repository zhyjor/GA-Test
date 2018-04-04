/*
Navicat MySQL Data Transfer

Source Server         : 虚拟计量
Source Server Version : 50627
Source Host           : 10.1.16.50:3306
Source Database       : 临兴区块

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2018-04-04 17:26:50
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
  `Q_shouldbe` float(15,4) DEFAULT NULL COMMENT '统计的流量，m3/d',
  `Opti_Q` float(15,4) DEFAULT NULL,
  `OutgoingP` double(10,4) DEFAULT NULL,
  `OutP_shouldbe` float(10,4) DEFAULT NULL COMMENT '计算后的出站压力，MPa',
  `InletP` double(10,4) DEFAULT NULL,
  `OutgoingT` double(4,1) DEFAULT NULL,
  `InletT` double(4,1) DEFAULT NULL,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CenterStation
-- ----------------------------
INSERT INTO `CenterStation` VALUES ('1', '2018-04-02 14:13:14', '临兴区块', 'ZYCLC', '910000.0000', null, null, '3.0000', null, '2.0000', '26.0', '0.1');

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
  `CoalSeamA` double(8,2) DEFAULT NULL COMMENT '煤层面积，KM2',
  `Thickness` float(25,2) DEFAULT NULL COMMENT '平均厚度，m',
  `Depth` float(25,2) DEFAULT NULL COMMENT '深度，m',
  `LangmuirP` float(25,2) DEFAULT NULL COMMENT 'Langmuir压力，MPa',
  `LangmuirV` float(25,2) DEFAULT NULL COMMENT 'Langmuir体积，m3/ton',
  `PoreCompressibility` float(25,2) DEFAULT NULL COMMENT '孔隙压缩系数，10^-9MPa^-1',
  `ContentofGas` float(25,2) DEFAULT NULL COMMENT '含气量,m3/ton',
  `GasSaturation` float(25,2) DEFAULT NULL,
  `T` double(8,2) DEFAULT NULL COMMENT '煤层温度,摄氏度',
  `InitialPorosity` double(8,2) DEFAULT NULL COMMENT '煤层初始孔隙度（MT）',
  `InitialPermeability` double(8,2) DEFAULT NULL COMMENT '煤层初始渗透率，单位；mD',
  `NowPorosity` double(8,2) DEFAULT NULL COMMENT '目前的煤层孔隙度',
  `NowPermeability` double(8,2) DEFAULT NULL COMMENT '目前的煤层渗透率',
  `VolumeModulus` double(8,2) DEFAULT NULL,
  `CumulativeWaterQ` double(8,1) DEFAULT NULL COMMENT '累计产水量，m3',
  `CumulativeGasQ` double(8,1) DEFAULT NULL,
  `Information` mediumtext COMMENT '其他信息',
  `St` double(25,4) DEFAULT NULL COMMENT '煤岩的抗拉强度,MPa',
  `fai` double(25,4) DEFAULT NULL COMMENT '煤岩内摩擦角',
  `fair` double(25,4) DEFAULT NULL COMMENT '残余内摩擦角，°',
  `Cohesion` double(25,4) DEFAULT NULL COMMENT '岩石内聚力，MPa',
  `Cohesion_R` double(25,4) DEFAULT NULL COMMENT '残余内聚力，MPa',
  `UCS` double(25,4) DEFAULT NULL COMMENT '岩石单轴实验下的抗压强度，MPa',
  `sigmaUCS` double(25,4) DEFAULT NULL COMMENT '峰后残余强度，MPa',
  `Posong` double(25,4) DEFAULT NULL COMMENT '岩石泊松比',
  `re` double(25,4) DEFAULT NULL COMMENT '渗流半径，m',
  `deltaBcu` float(25,4) DEFAULT NULL COMMENT '单轴时的强度退化量',
  `Xd` float(25,5) DEFAULT NULL COMMENT '峰后强度退化指数',
  `md` float(25,5) DEFAULT NULL COMMENT '常数',
  `CoalSeamDensity` double(10,4) DEFAULT NULL COMMENT '岩层密度，kg/m3',
  `NowP` double(10,4) DEFAULT NULL COMMENT '目前的地层压力，MPa',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CoalSeam
-- ----------------------------
INSERT INTO `CoalSeam` VALUES ('1', null, '2017-09-15 08:36:10', '太2', '3.00', null, '6.00', '839.00', null, null, null, null, null, '25.00', null, '0.59', '1.00', '1.00', '2.40', null, null, null, '0.2400', '36.6000', '38.9000', '4.9000', '0.6100', '0.0000', '0.6000', null, null, null, null, null, null, '3.0500');
INSERT INTO `CoalSeam` VALUES ('2', null, '2017-09-15 08:36:10', '盒8', '6.00', null, '3.00', '949.00', null, null, null, null, null, '25.00', null, '0.28', null, null, null, null, null, null, '0.2300', '40.8000', '39.1000', '2.4000', '0.6300', '0.0000', '0.6100', null, null, null, null, null, null, '6.3000');
INSERT INTO `CoalSeam` VALUES ('3', null, '2017-09-29 08:51:34', '韩城2', null, null, null, '472.00', '3.00', '35.00', '4.00', '25.00', '0.00', '23.00', '2.00', null, null, null, null, null, null, null, '0.2400', '40.8000', '39.1000', '2.4000', '0.6300', '0.0000', '0.6100', null, null, null, null, null, null, '12.4000');
INSERT INTO `CoalSeam` VALUES ('4', null, '2017-09-29 08:51:38', '樊庄', null, null, null, '355.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.2400', '36.6000', '39.1000', '2.4000', '0.6300', '0.0000', '0.6100', null, null, null, null, null, null, '12.4000');
INSERT INTO `CoalSeam` VALUES ('5', null, '2017-09-29 08:51:41', '例子1', '13.00', null, '9.00', '489.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.2400', '36.6000', '39.1000', '2.4000', '0.6300', '0.0000', '0.6100', null, null, null, null, null, null, '12.4000');

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
  `Information` longblob,
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
  `StartP` double(8,4) DEFAULT NULL COMMENT '起点压力,MPa',
  `Start_P_Shouldbe` double(8,4) DEFAULT NULL COMMENT '根据CL反推算结果，得到的节点应有的压力，MPa',
  `Opti_StartP` double(8,4) DEFAULT NULL COMMENT '根据优化后的流量来反推的节点压力，MPa',
  `StartT` double(8,4) DEFAULT NULL COMMENT '起点温度，摄氏度',
  `EndPointName` varchar(8) DEFAULT NULL,
  `EndP` double(8,4) DEFAULT NULL COMMENT '终点压力，MPa',
  `EndP_shouldbe` double(8,4) DEFAULT NULL,
  `Opti_EndP` double(8,4) DEFAULT NULL COMMENT '根据优化结果得到的终点压力,MPa',
  `EndT` double(8,4) DEFAULT NULL COMMENT '管末温度，摄氏度',
  `Length` double(8,2) DEFAULT NULL COMMENT '管长,m ',
  `D` double(8,2) DEFAULT NULL COMMENT '管径，mm',
  `Economical_Speed` double(8,2) DEFAULT NULL COMMENT '经济流速，m/s',
  `Ecnomical_Q` double(10,2) DEFAULT NULL,
  `Ke` double(8,5) DEFAULT NULL COMMENT '粗糙度，mm',
  `Thickness` double(4,1) DEFAULT NULL COMMENT '壁厚,mm',
  `TheoreticalQ` double(10,2) DEFAULT NULL COMMENT '理论最大流量,m3/d',
  `ActualQ` double(10,2) DEFAULT NULL COMMENT '实际流量，m3/d',
  `Q_shouldbe` double(10,2) DEFAULT NULL,
  `UtilizationEfficiency` double(8,2) DEFAULT NULL COMMENT '管道利用率',
  `TransportEfficiency` double(8,2) DEFAULT NULL COMMENT '输送效率',
  `Opti_Q` float(10,4) DEFAULT NULL COMMENT '优化后的结果，m3/d',
  `Opti_Efficiency` float(6,2) DEFAULT NULL COMMENT '由整体优化结果得到的输送效率',
  `SurroundT` double(8,1) DEFAULT NULL COMMENT '环境温度，度',
  `PrecipitatedWaterQ` double(8,4) DEFAULT NULL COMMENT '析出水量',
  `mozu_para` float(25,4) DEFAULT NULL COMMENT '管道摩阻系数',
  `Real_Pipe_CL` float(25,5) DEFAULT NULL COMMENT '管道的实际CL值，根据实际数据计算',
  `Pipe_CL` float(25,5) DEFAULT NULL COMMENT '优化中的常数理论值',
  `Holdup` mediumtext,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Pipeline
-- ----------------------------
INSERT INTO `Pipeline` VALUES ('1', null, '2018-04-02 14:13:13', 'LX-101-1H', '2.6000', '0.6772', '0.6772', '25.0000', 'FZ-1', '2.1050', '0.6772', '0.2706', '5.5000', '1.00', '103.30', '7.00', '99532.68', '0.04570', '5.5', '22538865.03', '10002.62', '10003.00', '0.62', '0.99', '2817.4109', '0.52', '25.0', '0.0000', '0.0208', '382019389358080.00000', '35678584.00000', '0');
INSERT INTO `Pipeline` VALUES ('2', null, '2018-04-02 14:13:13', 'LX-101-4H', '2.3700', '0.6772', '0.6772', '25.0000', 'FZ-1', '2.1050', '0.6772', '0.2706', '5.5000', '1.00', '103.30', '7.00', '94645.80', '0.04570', '5.5', '16099918.67', '15962.73', '15963.00', '0.50', '0.99', '4324.9219', '0.82', '25.0', '0.0000', '0.0208', '243089528586240.00000', '35678584.00000', '0');
INSERT INTO `Pipeline` VALUES ('3', null, '2018-04-02 14:13:13', 'LX-101-5H', '2.7700', '0.6772', '0.6772', '25.0000', 'FZ-1', '2.1050', '0.6772', '0.2706', '5.5000', '1.00', '103.30', '7.00', '103221.32', '0.04570', '5.5', '26572889.62', '24331.81', '24332.00', '0.82', '0.98', '8121.0532', '0.51', '25.0', '0.0000', '0.0208', '387421887791104.00000', '35678584.00000', '0');
INSERT INTO `Pipeline` VALUES ('4', null, '2018-04-02 14:13:13', 'FZ-1', '2.1050', '0.6772', '0.2706', '5.5000', '临时集气站', '0.2000', '2.0000', '2.0000', '24.0000', '4200.00', '103.30', '7.00', '60425.68', '0.04570', '5.5', '153250.16', '50297.16', '50298.00', '0.50', '0.52', '15263.3857', '0.57', '25.0', '0.0000', '0.0208', '79862400286720.00000', '1516800049152.00000', '0');
INSERT INTO `Pipeline` VALUES ('5', null, '2018-04-02 14:13:13', 'LX-102', '2.1000', '0.3227', '0.3227', '25.0000', 'FZ-2', '2.0350', '0.3227', '0.2412', '9.1000', '1.00', '78.90', '10.00', '69793.26', '0.04570', '5.0', '3510033.92', '2275.81', '2276.00', '0.22', '0.98', '2780.0959', '0.54', '25.0', '0.0000', '0.0229', '485253290393600.00000', '170729008.00000', '0');
INSERT INTO `Pipeline` VALUES ('6', null, '2018-04-02 14:13:13', 'LX-102-2D', '2.6000', '0.3227', '0.3227', '25.0000', 'FZ-2', '2.0350', '0.3227', '0.2412', '9.1000', '1.00', '78.90', '10.00', '78191.70', '0.04570', '5.0', '10929053.38', '1053.71', '1054.00', '0.23', '0.99', '2958.0610', '0.78', '25.0', '0.0000', '0.0229', '65027393999339520.00000', '170729008.00000', '0');
INSERT INTO `Pipeline` VALUES ('7', null, '2018-04-02 14:13:13', 'LX-102-3H', '2.2000', '0.3228', '0.3227', '25.0000', 'FZ-2', '2.0350', '0.3227', '0.2412', '9.1000', '1.00', '78.90', '10.00', '71430.85', '0.04570', '5.0', '5656514.02', '12555.43', '12555.00', '0.35', '0.92', '2004.7159', '0.21', '25.0', '0.0000', '0.0229', '505906110398464.00000', '170729008.00000', '0');
INSERT INTO `Pipeline` VALUES ('8', null, '2018-04-02 14:13:13', 'FZ-2', '2.0350', '0.3227', '0.2412', '9.1000', '临时集气站', '0.2000', '2.0000', '2.0000', '24.0000', '1600.00', '154.30', '5.00', '96656.15', '0.04570', '7.0', '732782.95', '103253.28', '133819.00', '0.55', '0.86', '56525.9844', '0.45', '25.0', '0.0000', '0.0181', '1710479769600.00000', '62117191680.00000', '0');
INSERT INTO `Pipeline` VALUES ('9', null, '2018-04-02 14:13:13', 'LX-104', '2.2000', '0.4666', '0.4666', '25.0000', 'FZ-4', '2.0560', '0.4666', '0.3721', '2.5000', '1.00', '103.30', '7.00', '90146.98', '0.04570', '5.5', '11617057.19', '2212.00', '2212.00', '0.55', '0.98', '3060.7471', '0.47', '25.0', '0.0000', '0.0208', '1847921087610880.00000', '35493016.00000', '0');
INSERT INTO `Pipeline` VALUES ('10', null, '2018-04-02 14:13:13', 'LX-104-1H', '2.4000', '0.4666', '0.4666', '25.0000', 'FZ-4', '2.0560', '0.4666', '0.3721', '2.5000', '1.00', '103.30', '7.00', '94331.63', '0.04570', '5.5', '18353884.62', '5305.10', '5305.00', '0.49', '0.98', '2013.4590', '0.48', '25.0', '0.0000', '0.0208', '132007547895808.00000', '35493016.00000', '0');
INSERT INTO `Pipeline` VALUES ('11', null, '2018-04-02 14:13:13', 'LX-104-3D', '2.4000', '0.4666', '0.4666', '25.0000', 'FZ-4', '2.0560', '0.4666', '0.3721', '2.5000', '1.00', '103.30', '7.00', '94331.63', '0.04570', '5.5', '18353884.62', '7227.43', '7227.00', '0.92', '0.97', '3573.1621', '0.62', '25.0', '0.0000', '0.0208', '908874202742784.00000', '35493016.00000', '0');
INSERT INTO `Pipeline` VALUES ('12', null, '2018-04-02 14:13:13', 'LX-104-7D', '2.7000', '0.4666', '0.4666', '25.0000', 'FZ-4', '2.0560', '0.4666', '0.3721', '2.5000', '1.00', '103.30', '7.00', '100791.45', '0.04570', '5.5', '25909013.22', '15821.71', '15822.00', '0.33', '0.97', '3194.2771', '0.18', '25.0', '0.0000', '0.0208', '157074352766976.00000', '35493016.00000', '0');
INSERT INTO `Pipeline` VALUES ('13', null, '2018-04-02 14:13:13', 'FZ-4', '2.0560', '0.4666', '0.3721', '2.5000', 'FZ-6', '2.0390', '0.3541', '0.3282', '0.3000', '2700.00', '103.30', '7.00', '86860.09', '0.04570', '5.5', '24379.29', '30566.24', '30566.00', '0.41', '0.52', '11841.6445', '0.33', '25.0', '0.0000', '0.0208', '743811776512.00000', '917059600384.00000', '0');
INSERT INTO `Pipeline` VALUES ('14', null, '2018-04-02 14:13:13', 'FZ-6', '2.0390', '0.3541', '0.3282', '0.3000', 'FZ-2', '2.0350', '0.3227', '0.2412', '9.1000', '400.00', '154.30', '5.00', '142650.53', '0.04570', '7.0', '93000.88', '117934.57', '117934.00', '0.63', '0.55', '48783.1133', '0.29', '25.0', '0.0000', '0.0181', '7137649664.00000', '14755293184.00000', '0');
INSERT INTO `Pipeline` VALUES ('15', null, '2018-04-02 14:13:13', 'LX-103', '2.6000', '0.3741', '0.3741', '25.0000', 'FZ-3', '2.0420', '0.3741', '0.3741', '2.8000', '1.00', '154.30', '5.00', '162323.67', '0.04570', '7.0', '72790922.39', '1916.52', '1917.00', '0.75', '0.72', '2993.4270', '1.08', '25.0', '0.0000', '0.0181', '364368315285504.00000', '3806554.25000', '0');
INSERT INTO `Pipeline` VALUES ('16', null, '2018-04-02 14:13:13', 'LX-103-1D', '2.7000', '0.3741', '0.3741', '25.0000', 'FZ-3', '2.0420', '0.3741', '0.3741', '2.8000', '1.00', '154.30', '5.00', '165920.58', '0.04570', '7.0', '79856826.61', '3200.00', '3200.00', '0.15', '0.68', '4087.3230', '0.28', '25.0', '0.0000', '0.0181', '1637701228953600.00000', '3806554.25000', '0');
INSERT INTO `Pipeline` VALUES ('17', null, '2018-04-02 14:13:13', 'LX-103-3H', '4.5000', '0.3741', '0.3741', '25.0000', 'FZ-3', '2.0420', '0.3741', '0.3741', '2.8000', '1.00', '154.30', '5.00', '234993.24', '0.04570', '7.0', '22027.52', '22027.52', '22028.00', '0.46', '0.45', '2512.9441', '0.32', '25.0', '0.0000', '0.0181', '102367861145600.00000', '3806554.25000', '0');
INSERT INTO `Pipeline` VALUES ('18', null, '2018-04-02 14:13:13', 'LX-103-4D', '2.3000', '0.3741', '0.3741', '25.0000', 'FZ-3', '2.0420', '0.3741', '0.3741', '2.8000', '1.00', '154.30', '5.00', '151758.40', '0.04570', '7.0', '1138.43', '1138.43', '1138.00', '0.66', '0.47', '6762.7490', '0.41', '25.0', '0.0000', '0.0181', '16688495934308352.00000', '3806554.25000', '0');
INSERT INTO `Pipeline` VALUES ('19', null, '2018-04-02 14:13:13', 'FZ-3', '2.0420', '0.3741', '0.3741', '2.8000', 'FZ-8', '2.0420', '0.3741', '0.3577', '0.9000', '1.00', '154.30', '5.00', '142984.25', '0.04570', '7.0', '28282.47', '28282.47', '28283.00', '0.76', '0.21', '16356.4434', '0.31', '25.0', '0.0000', '0.0181', '0.00000', '3646759.75000', '0');
INSERT INTO `Pipeline` VALUES ('20', null, '2018-04-02 14:13:13', 'FZ-8', '2.0420', '0.3741', '0.3577', '0.9000', 'FZ-6', '2.0390', '0.3541', '0.3282', '0.3000', '500.00', '154.30', '5.00', '142884.15', '0.04570', '7.0', '92189.08', '87368.33', '87368.00', '1.09', '0.32', '36941.4688', '0.28', '25.0', '0.0000', '0.0181', '8429558272.00000', '18150918144.00000', '0');
INSERT INTO `Pipeline` VALUES ('21', null, '2018-04-02 14:13:13', 'FZ-7', '2.0470', '0.4037', '0.3778', '1.2000', 'FZ-8', '2.0420', '0.3741', '0.3577', '0.9000', '1700.00', '154.30', '5.00', '143151.20', '0.04570', '7.0', '57376.29', '59085.86', '59085.00', '0.42', '0.68', '20585.0234', '0.26', '25.0', '0.0000', '0.0181', '44663169024.00000', '61814566912.00000', '0');
INSERT INTO `Pipeline` VALUES ('22', null, '2018-04-02 14:13:13', 'LX-4', '5.0000', '0.4037', '0.4037', '25.0000', 'FZ-7', '2.0470', '0.4037', '0.3778', '1.2000', '1.00', '78.90', '10.00', '123124.68', '0.04570', '5.0', '12354.33', '12354.33', '12354.00', '0.26', '1.32', '7919.4790', '0.51', '25.0', '0.0000', '0.0229', '8861132248842240.00000', '168405168.00000', '0');
INSERT INTO `Pipeline` VALUES ('23', null, '2018-04-02 14:13:13', 'LX-105-1H', '5.0000', '0.5517', '0.5517', '25.0000', 'FZ-5', '2.0830', '0.5517', '0.4160', '5.4000', '1.00', '103.30', '7.00', '155163.08', '0.04570', '5.5', '19534.81', '19534.81', '19535.00', '0.72', '2.32', '2151.6289', '0.50', '25.0', '0.0000', '0.0208', '131534522679296.00000', '35672400.00000', '0');
INSERT INTO `Pipeline` VALUES ('24', null, '2018-04-02 14:13:13', 'LX-105-2D', '2.2000', '0.5517', '0.5517', '25.0000', 'FZ-5', '2.0830', '0.5517', '0.4160', '5.4000', '1.00', '103.30', '7.00', '90682.23', '0.04570', '5.5', '2832.29', '2832.29', '2832.00', '0.18', '2.20', '2594.4370', '0.22', '25.0', '0.0000', '0.0208', '244715844796416.00000', '35672400.00000', '0');
INSERT INTO `Pipeline` VALUES ('25', null, '2018-04-02 14:13:13', 'LX-105-3D', '2.3000', '0.5517', '0.5517', '25.0000', 'FZ-5', '2.0830', '0.5517', '0.4160', '5.4000', '1.00', '103.30', '7.00', '92753.25', '0.04570', '5.5', '24364.43', '24364.43', '24364.00', '0.79', '1.30', '7919.4790', '0.85', '25.0', '0.0000', '0.0208', '25993433579520.00000', '35672400.00000', '0');
INSERT INTO `Pipeline` VALUES ('26', null, '2018-04-02 14:13:13', 'FZ-5', '2.0830', '0.5517', '0.4160', '5.4000', 'FZ-7', '2.0470', '0.4037', '0.3778', '1.2000', '1710.00', '103.30', '7.00', '87569.48', '0.04570', '5.5', '59335.32', '46731.53', '46731.00', '0.32', '1.00', '12665.5449', '0.36', '25.0', '0.0000', '0.0208', '376104976384.00000', '584823799808.00000', '0');

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
  `Q_shouldbe` float(10,4) DEFAULT NULL COMMENT '统计得到的流量,m3/d',
  `Opti_Q` double(10,4) DEFAULT NULL,
  `InletP` double(10,4) DEFAULT NULL COMMENT '进站压力，MPa',
  `OutgoingP` double(10,4) DEFAULT NULL COMMENT '出站压力,MPa',
  `OutP_shouldbe` double(10,4) DEFAULT NULL COMMENT '计算后的出站压力,MPa',
  `Opti_OutP` double(10,4) DEFAULT NULL,
  `InletT` double(4,1) DEFAULT NULL COMMENT '进站温度',
  `OutgoingT` double(4,1) DEFAULT NULL COMMENT '出站温度',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Station
-- ----------------------------
INSERT INTO `Station` VALUES ('1', '2018-04-02 14:13:14', '1', '临时集气站', '910000.0000', '184117.0000', '71789.3710', '0.2000', '1.2000', null, null, '24.0', '25.0');

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
  `Q_shouldbe` float(15,4) DEFAULT NULL,
  `Opti_Q` double(15,4) DEFAULT NULL,
  `P_shouldbe` double(10,4) DEFAULT NULL,
  `Opti_P` double(10,4) DEFAULT NULL,
  `P` double(10,4) DEFAULT NULL,
  `T` double(4,1) DEFAULT NULL,
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Valve
-- ----------------------------
INSERT INTO `Valve` VALUES ('1', '2018-04-02 14:13:14', '临兴区块', 'FZ-1', '50297.1600', '50298.0000', '15263.3860', '0.6772', '0.2706', '2.1050', '5.5');
INSERT INTO `Valve` VALUES ('2', '2018-04-02 14:13:14', '临兴区块', 'FZ-2', '103253.2800', '133819.0000', '56525.9850', '0.3227', '0.2412', '2.0350', '9.1');
INSERT INTO `Valve` VALUES ('3', '2018-04-02 14:13:14', '临兴区块', 'FZ-3', '28282.4700', '28283.0000', '16356.4430', '0.3741', '0.3741', '2.0420', '2.8');
INSERT INTO `Valve` VALUES ('4', '2018-04-02 14:13:14', '临兴区块', 'FZ-4', '30566.2400', '30566.0000', '11841.6450', '0.4666', '0.3721', '2.0560', '2.5');
INSERT INTO `Valve` VALUES ('5', '2018-04-02 14:13:14', '临兴区块', 'FZ-5', '46731.5300', '46731.0000', '12665.5450', '0.5517', '0.4160', '2.0830', '5.4');
INSERT INTO `Valve` VALUES ('6', '2018-04-02 14:13:14', '临兴区块', 'FZ-6', '117934.5700', '117934.0000', '48783.1120', '0.3541', '0.3282', '2.0390', '0.3');
INSERT INTO `Valve` VALUES ('7', '2018-04-02 14:13:14', '临兴区块', 'FZ-7', '59085.8600', '59085.0000', '20585.0240', '0.4037', '0.3778', '2.0470', '1.2');
INSERT INTO `Valve` VALUES ('8', '2018-04-02 14:13:14', '临兴区块', 'FZ-8', '87368.3300', '87368.0000', '36941.4670', '0.3741', '0.3577', '2.0420', '0.9');

-- ----------------------------
-- Table structure for Well
-- ----------------------------
DROP TABLE IF EXISTS `Well`;
CREATE TABLE `Well` (
  `SequenceNumber` int(8) NOT NULL DEFAULT '0' COMMENT '序号',
  `Time` datetime DEFAULT NULL COMMENT '时间，不需要填写',
  `Region` varchar(25) DEFAULT NULL COMMENT '油井所属区块',
  `StratigraphicName` varchar(25) DEFAULT NULL COMMENT '所属地层名字',
  `Name` varchar(25) DEFAULT NULL COMMENT '井的名字',
  `CasingD` float(8,2) NOT NULL COMMENT '套管内径，mm',
  `TubingD` float(8,2) DEFAULT NULL COMMENT '油管直径，mm',
  `FormationDepth` float(8,2) DEFAULT NULL COMMENT '煤层深度，m',
  `Q` double(8,2) DEFAULT NULL COMMENT '产气量，m3/d',
  `CasingP` double(8,4) DEFAULT NULL COMMENT '套压，气嘴前，MPa',
  `BackP` double(8,4) DEFAULT NULL COMMENT '回压，气嘴后压力,MPa',
  `Working_Fluid_Level` double(8,2) DEFAULT NULL COMMENT '动液面深度，m',
  `Well_bore_P` double(8,2) DEFAULT NULL COMMENT '井筒产生的压差，即套压与井底流压的差,MPa',
  `FBHP` double(8,4) DEFAULT NULL COMMENT '井底流压,MPa',
  `ProductivityEquaA` double(25,6) DEFAULT NULL COMMENT '产能二项式系数，',
  `ProductivityEquaB` double(25,11) DEFAULT NULL COMMENT '产能二项式系数',
  `Pr` float(10,5) DEFAULT NULL COMMENT '地层压力',
  `Pr_shouldbe` float(10,4) DEFAULT NULL COMMENT '根据生产数据以及A,B值反推地层压力,MPa',
  `BackP_shouldbe` double(8,4) DEFAULT NULL,
  `EquipmentRunningEfficiency` double(4,2) DEFAULT NULL COMMENT '设备效率',
  `Pwf_max` float(10,4) DEFAULT NULL COMMENT '建议井底最大流压，MPa',
  `Best_Pwf` float(10,4) DEFAULT NULL,
  `Pwf_min` float(10,4) DEFAULT NULL COMMENT '建议井底流压最小值，MPa',
  `Q_max` float(10,4) DEFAULT NULL COMMENT '建议最大产量',
  `Best_Q` float(10,4) DEFAULT NULL COMMENT '最佳产量，m3/d',
  `Q_min` float(10,4) DEFAULT NULL COMMENT '建议最小产量',
  `Well_Pipenet_Matching` float(6,2) DEFAULT NULL COMMENT '井与管网匹配度，',
  `Well_Matching` float(6,2) DEFAULT NULL COMMENT '井间匹配度',
  `Opti_BackP` double(8,4) DEFAULT NULL COMMENT '优化后的回压,MPa',
  `PFThickness` double(8,4) DEFAULT NULL COMMENT '产层深度，m',
  `ChockD` double(4,1) DEFAULT NULL COMMENT '油嘴直径，mm',
  `T` double(4,1) DEFAULT NULL COMMENT '温度，摄氏度',
  `Well_individually_product_Q` float(10,4) DEFAULT NULL COMMENT '假设其他井停产时，该井的产量，m3/d',
  PRIMARY KEY (`SequenceNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Well
-- ----------------------------
INSERT INTO `Well` VALUES ('1', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-101-1H', '177.80', '88.90', '1063.00', '10003.00', '6.0000', '2.6000', '940.55', '1.25', '5.9800', '0.003206', '-0.00000030900', '14.00000', '6.5714', '0.6772', '0.00', '2.6301', '13.7630', '0.5810', '2817.4119', '2817.4109', '0.0000', '1.00', '1.00', '0.6772', '4.9000', null, '25.0', '3022.3352');
INSERT INTO `Well` VALUES ('2', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-101-4H', '177.80', '88.90', '707.70', '15963.00', '4.4000', '2.3700', '604.64', '1.08', '5.3900', '0.001110', '-0.00000001380', '14.20000', '5.9476', '0.6772', '0.00', '2.2091', '14.0391', '0.5810', '4324.9229', '4324.9219', '0.0000', '1.00', '1.00', '0.6772', '10.0000', null, '25.0', '4486.0820');
INSERT INTO `Well` VALUES ('3', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-101-5H', '177.80', '88.90', '796.00', '24332.00', '6.0000', '2.7700', '794.98', '0.04', '3.1900', '0.001350', '-0.00000001170', '14.00000', '4.5057', '0.6772', '0.20', '3.2449', '13.6311', '0.5810', '8121.0532', '8121.0532', '0.0000', '0.99', '1.00', '0.6772', '5.8000', null, '25.0', '8042.1309');
INSERT INTO `Well` VALUES ('4', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-102', '139.70', '73.00', '1319.60', '2276.00', '0.8000', '2.1000', '856.33', '4.66', '1.7700', '0.001620', '-0.00000001590', '11.10000', '2.5371', '0.3227', '0.12', '2.1722', '10.9009', '0.5810', '2780.0964', '2780.0959', '0.0000', '1.00', '1.00', '0.3227', '5.9000', null, '25.0', '2950.4441');
INSERT INTO `Well` VALUES ('5', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-102-2D', '139.70', '73.00', '1687.30', '1054.00', '3.0000', '2.6000', '1319.55', '3.71', '2.2200', '0.002400', '-0.00000000200', '15.90000', '2.5044', '0.3227', '0.11', '2.6618', '15.6757', '0.5810', '2958.0615', '2958.0610', '0.0000', '1.00', '1.00', '0.3227', '5.8000', null, '25.0', '116603.9688');
INSERT INTO `Well` VALUES ('6', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-102-3H', '139.70', '73.00', '977.00', '12555.00', '3.0000', '2.2000', '313.73', '6.66', '1.4500', '0.001060', '-0.00000001670', '15.90000', '2.3249', '0.3228', '0.06', '1.5667', '15.8352', '0.5810', '2004.7168', '2004.7159', '0.0000', '1.00', '1.00', '0.3227', '6.1000', null, '25.0', '43282.7188');
INSERT INTO `Well` VALUES ('7', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-103', '139.70', '73.00', '1728.90', '1917.00', '4.1000', '2.6000', '1560.53', '1.74', '1.5400', '0.001481', '-0.00000001560', '16.20000', '3.5393', '0.3741', '0.06', '2.1497', '16.0669', '0.5810', '2993.4270', '2993.4270', '0.0000', '1.00', '1.00', '0.3741', '11.5000', null, '25.0', '3161.8560');
INSERT INTO `Well` VALUES ('8', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-103-1D', '139.70', '73.00', '1729.90', '3200.00', '5.0000', '2.7000', '1481.94', '2.49', '1.4500', '0.000960', '-0.00000004020', '16.30000', '2.2809', '0.3741', '0.06', '2.0002', '16.1999', '0.5810', '4087.3230', '4087.3230', '0.0000', '1.00', '1.00', '0.3741', '2.7000', null, '25.0', '4334.7832');
INSERT INTO `Well` VALUES ('9', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-103-3H', '177.80', '88.90', '660.50', '22028.00', '5.0000', '4.5000', '348.70', '3.15', '1.1800', '0.001379', '-0.00000006600', '16.30000', '0.0000', '0.3741', '0.04', '1.8839', '16.2062', '0.5810', '2512.9448', '2512.9441', '0.0000', '1.00', '1.00', '0.3741', '6.5000', null, '25.0', '2737.7839');
INSERT INTO `Well` VALUES ('10', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-103-4D', '139.70', '73.00', '1605.60', '1138.00', '3.2000', '2.3000', '1036.21', '5.72', '2.1100', '0.001640', '-0.00000001650', '16.30000', '2.3729', '0.3741', '0.10', '2.9000', '15.9798', '0.5810', '6762.7490', '6762.7490', '0.0000', '0.96', '1.00', '0.3741', '5.2000', null, '25.0', '6466.9404');
INSERT INTO `Well` VALUES ('11', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-104', '139.70', '73.00', '1606.30', '2212.00', '5.5000', '2.2000', '1573.65', '0.35', '1.4000', '0.001375', '-0.00000004000', '15.10000', '2.0174', '0.4666', '0.06', '2.0956', '14.9725', '0.5810', '3060.7473', '3060.7471', '0.0000', '1.00', '1.00', '0.4666', '4.7000', null, '25.0', '3241.3501');
INSERT INTO `Well` VALUES ('12', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-104-1H', '177.80', '88.90', '735.10', '5305.00', '0.5900', '2.4000', '231.02', '5.07', '1.1600', '0.003019', '-0.00000001670', '15.20000', '5.3446', '0.4666', '0.04', '2.4184', '15.0010', '0.5810', '2013.4596', '2013.4590', '0.0000', '1.00', '1.00', '0.4666', '5.4000', null, '25.0', '2126.3757');
INSERT INTO `Well` VALUES ('13', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-104-3D', '139.70', '73.00', '1615.50', '7227.00', '6.1000', '2.4000', '1588.97', '0.34', '2.1900', '0.002340', '-0.00000035000', '15.20000', '2.9460', '0.4666', '0.11', '2.9130', '15.0714', '0.5810', '3573.1621', '3573.1621', '0.0000', '1.00', '0.95', '0.4666', '14.7000', null, '25.0', '3676.6685');
INSERT INTO `Well` VALUES ('14', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-104-7D', '139.70', '73.00', '1558.90', '15822.00', '6.0000', '2.7000', '1506.86', '0.54', '2.3200', '0.004554', '-0.00000031000', '14.70000', '3.8010', '0.4666', '0.13', '3.3635', '14.3076', '0.5810', '3194.2771', '3194.2771', '0.0000', '1.00', '1.00', '0.4666', '3.9000', null, '25.0', '3304.4446');
INSERT INTO `Well` VALUES ('15', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-105-1H', '177.80', '88.90', '857.00', '19535.00', '6.0000', '5.0000', '189.94', '6.70', '1.3200', '0.003160', '-0.00000030900', '15.90000', '0.0000', '0.5517', '0.05', '2.3878', '15.7303', '0.5810', '2151.6299', '2151.6289', '0.0000', '1.00', '1.00', '0.5517', '6.2000', null, '25.0', '2298.9038');
INSERT INTO `Well` VALUES ('16', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-105-2D', '139.70', '73.00', '1820.20', '2832.00', '3.0000', '2.2000', '1241.63', '5.81', '2.9900', '0.006033', '-0.00000030900', '16.70000', '5.3036', '0.5517', '0.15', '3.7296', '16.2886', '0.5810', '2594.4375', '2594.4370', '0.0000', '1.00', '1.00', '0.5517', '5.1000', null, '25.0', '2654.2739');
INSERT INTO `Well` VALUES ('17', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-105-3D', '139.70', '73.00', '1940.80', '24364.00', '3.0000', '2.3000', '1353.05', '5.91', '3.2900', '0.005602', '-0.00000001670', '18.30000', '10.0363', '0.5517', '0.16', '3.5153', '17.0755', '0.5810', '7919.4790', '7919.4790', '0.0000', '0.99', '1.00', '0.5517', '7.2000', null, '25.0', '7846.2690');
INSERT INTO `Well` VALUES ('18', '2018-04-02 14:13:06', '临时集气站', '临兴', 'LX-4', '139.70', '73.00', '1798.60', '12354.00', '5.0000', '5.0000', '1701.66', '1.02', '3.2900', '0.001650', '-0.00000001670', '16.20000', '4.1868', '0.4037', '0.18', '3.5153', '15.8247', '0.5810', '7919.4790', '7919.4790', '0.0000', '1.00', '1.00', '0.4037', '10.3000', null, '25.0', '0.0000');
