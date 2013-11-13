CrawlBBS
========

-- MySQL DB tables creating sentence

DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `ArticleId` int(11)  AUTO_INCREMENT NOT NULL ,
  `ReplyCounts` int(11) DEFAULT NULL,
  `ViewCounts` int(11) DEFAULT NULL,
  `Content` text,
  `Title` varchar(255)  DEFAULT NULL,
  `Url` varchar(255) DEFAULT NULL,
  `AuthorName` varchar(255) DEFAULT NULL,
  `PostTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `Reply`;
CREATE TABLE `Reply` (
  `ReplyId` int(11) NOT NULL AUTO_INCREMENT,
  `ArticleId` int(11) DEFAULT NULL,
  `ReplierName` varchar(255)  DEFAULT NULL,
  `PostTime` varchar(255)  DEFAULT NULL,
  `Content` text,
  PRIMARY KEY (`ReplyId`),
  KEY `FK_Reply_Article` (`ArticleId`),
  CONSTRAINT `FK_Reply_Article` FOREIGN KEY (`ArticleId`) REFERENCES `Article` (`ArticleId`)
  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
