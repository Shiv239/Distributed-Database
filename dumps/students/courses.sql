--
-- Table structure for table `courses`
--
DROP TABLE IF EXISTS `courses`;

CREATE TABLE `courses` ( 
courseId  int NOT NULL  Primary KEY ,
courseName  String NULL  ,
credits  int NULL  ,
ProfessorId int  NULL Foreign  KEY  Professors
);
--
-- Dumping data for table `courses`
--
[SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

INSERT INTO `courses` VALUES(23,SDC,6),(24,DMWA,3),(25,Cloud,3),(26,ASDC,3);
COMMIT;
