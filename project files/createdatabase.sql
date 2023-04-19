DROP TABLE student_course_log;
DROP TABLE student_course;
DROP TABLE lesson;
DROP TABLE IF EXISTS choice_question;
DROP TABLE IF EXISTS completion_question;
DROP TABLE IF EXISTS assignment_mark;
DROP TABLE IF EXISTS assignment;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS SCHOOL;
DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS `exam`;

CREATE TABLE IF NOT EXISTS `school`(
	`schoolId` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (`schoolId`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `department`(
	`departmentId` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (`departmentId`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `student`(
   `username` VARCHAR(30) NOT NULL UNIQUE,
   `studentId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
   `password` VARCHAR(20) NOT NULL,
   `realName` VARCHAR(30),
   `school` SMALLINT UNSIGNED,
   `studentNumber` VARCHAR(10),
   `department` SMALLINT UNSIGNED,
   `schoolClass` VARCHAR(10),
   `phone` CHAR(11),
   `email` VARCHAR(30),
   PRIMARY KEY ( `studentId` ),
   FOREIGN KEY fk_student_school(`school`) REFERENCES school(`schoolId`) ON DELETE SET NULL ON UPDATE CASCADE,
   FOREIGN KEY fk_student_department(`department`) REFERENCES department(`departmentId`) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `teacher`(
   `username` VARCHAR(30) NOT NULL,
   `teacherId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
   `password` VARCHAR(20) NOT NULL,
   `realName` VARCHAR(30),
   `school` SMALLINT UNSIGNED,
   `teacherNumber` VARCHAR(10),
   `department` SMALLINT UNSIGNED,
   `phone` CHAR(11),
   `email` VARCHAR(30),
   `certificated` BOOLEAN DEFAULT FALSE,
   PRIMARY KEY ( `teacherId` ),
   FOREIGN KEY fk_teacher_school(`school`) REFERENCES school(`schoolId`) ON DELETE SET NULL ON UPDATE CASCADE,
   FOREIGN KEY fk_teacher_department(`department`) REFERENCES department(`departmentId`) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `course`(
	`courseName` VARCHAR(30) NOT NULL,
    `teacher` INT UNSIGNED,
    `courseId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `startDate` DATE NOT NULL,
    `endDate` DATE NOT NULL,
    `joinMode` TINYINT NOT NULL DEFAULT 2,
    PRIMARY KEY (`courseId`),
    FOREIGN KEY fk_course_teacher(`teacher`) REFERENCES teacher(`teacherId`) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `lesson`(
	`lessonName` VARCHAR(30) NOT NULL,
    `lessonId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `course` INT UNSIGNED NOT NULL,
    `introduction` VARCHAR(2000),
    `video` VARCHAR(200),
    PRIMARY KEY (`lessonId`),
    FOREIGN KEY fk_lesson_course(`course`) REFERENCES course(`courseId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `assignment`(
	`assignmentId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `course` INT UNSIGNED NOT NULL,
    `assignmentName` VARCHAR(30),
    `weight` DECIMAL(4, 3),
    `startTime` DATETIME,
    `endTime` DATETIME,
    PRIMARY KEY (`assignmentId`),
    FOREIGN KEY fk_assignment_course(`course`) REFERENCES course(`courseId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `choice_question`(
	`questionId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `assignment` INT UNSIGNED NOT NULL,
    `description` VARCHAR(500),
    `choiceA` VARCHAR(200),
    `choiceB` VARCHAR(200),
    `choiceC` VARCHAR(200),
    `choiceD` VARCHAR(200),
    `answer` CHAR(1),
    `mark` INT,
    PRIMARY KEY (`questionID`),
    FOREIGN KEY fk_choice_assignment(`assignment`) REFERENCES assignment(`assignmentId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `completion_question`(
	`questionId` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `assignment` INT UNSIGNED NOT NULL,
    `description` VARCHAR(500),
    `answer` VARCHAR(30),
    `mark` INT,
	PRIMARY KEY (`questionID`),
    FOREIGN KEY fk_completion_assignment(`assignment`) REFERENCES assignment(`assignmentId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `assignment_mark`(
	`assignment` INT UNSIGNED NOT NULL,
    `student` INT UNSIGNED NOT NULL,
    `mark` INT,
    PRIMARY KEY (`assignment`, `student`),
    FOREIGN KEY fk_mark_assignment(`assignment`) REFERENCES assignment(`assignmentId`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fk_mark_student(`student`) REFERENCES student(`studentId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `student_course`(
	`student` INT UNSIGNED NOT NULL,
    `course` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`student`, `course`),
    FOREIGN KEY fk_student_course_log_student(`student`) REFERENCES student(`studentId`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fk_student_course_log_course(`course`) REFERENCES course(`courseId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `student_course_log`(
	`student` INT UNSIGNED NOT NULL,
    `course` INT UNSIGNED NOT NULL,
	`logUrl` VARCHAR(40),
    PRIMARY KEY (`student`, `course`),
    FOREIGN KEY fk_student_course_log_student(`student`) REFERENCES student(`studentId`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fk_student_course_log_course(`course`) REFERENCES course(`courseId`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `exam` (
  `studentNumber` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `courseId` INT UNSIGNED NOT NULL,
  `grade` DOUBLE DEFAULT '0',
  PRIMARY KEY (`studentNumber`,`courseId`),
  KEY `fk_courseId` (`courseId`),
  CONSTRAINT `fk_courseId` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  CONSTRAINT `fk_studentNumber` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO school(NAME) VALUE("大连理工大学");
INSERT INTO department(NAME) VALUE("软件学院");

INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("username1","123456","韩宇",1,"201792026",1,"软1709");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992007","123456","包恩泽",1,"201992007",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992027","123456","杨昊",1,"201992027",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992036","123456","祝鹏辉",1,"201992036",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992055","123456","柴宇航",1,"201992055",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992085","123456","刘洋",1,"201992085",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992117","123456","付政",1,"201992117",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992157","123456","李晓",1,"201992157",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992173","123456","赵晨旭",1,"201992173",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992179","123456","甄雨诺",1,"201992179",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992182","123456","张兆丹",1,"201992182",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992183","123456","赵焓超",1,"201992183",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992220","123456","柯懿星",1,"201992220",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992244","123456","于岱汛",1,"201992244",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992251","123456","刘行舟",1,"201992251",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992262","123456","洪绍洧",1,"201992262",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992283","123456","林双印",1,"201992283",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992296","123456","吴泓翰",1,"201992296",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992301","123456","余叔桐",1,"201992301",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992308","123456","史柯",1,"201992308",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992324","123456","王彦哲",1,"201992324",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992342","123456","李明蒨",1,"201992342",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992383","123456","杨雨霖",1,"201992383",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992397","123456","王子硕",1,"201992397",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992402","123456","曹佳",1,"201992402",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992414","123456","刘旭尧",1,"201992414",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992427","123456","琚子健",1,"201992427",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992444","123456","王晨",1,"201992444",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992460","123456","谢广瑞",1,"201992460",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992486","123456","严拼",1,"201992486",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992487","123456","李瑶",1,"201992487",1,"软1901");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992230","123456","鹿洺栋",1,"201992230",1,"软1902");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201941325","123456","杨岩",1,"201941325",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201981561","123456","黄江煜",1,"201981561",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992030","123456","陈钦禹",1,"201992030",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992034","123456","谭向文",1,"201992034",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992046","123456","王逸风",1,"201992046",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992063","123456","刘梦瑶",1,"201992063",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992071","123456","胡哲琪",1,"201992071",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992087","123456","段一肖",1,"201992087",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992112","123456","钱坤雅",1,"201992112",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992118","123456","周绮",1,"201992118",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992121","123456","刘永凯",1,"201992121",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992159","123456","常天烁",1,"201992159",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992180","123456","高峰",1,"201992180",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992186","123456","刘思诚",1,"201992186",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992209","123456","李津",1,"201992209",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992229","123456","于嘉豪",1,"201992229",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992233","123456","陈帅",1,"201992233",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992252","123456","房可飞",1,"201992252",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992267","123456","姜奇年",1,"201992267",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992298","123456","孔维琛",1,"201992298",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992316","123456","陈维聪",1,"201992316",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992336","123456","林嘉熹",1,"201992336",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992355","123456","李季桥",1,"201992355",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992366","123456","韩肇明",1,"201992366",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992367","123456","吴翊广",1,"201992367",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992382","123456","于家傲",1,"201992382",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992413","123456","张佳卉",1,"201992413",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992426","123456","薛涛",1,"201992426",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992434","123456","金润琦",1,"201992434",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992467","123456","夏逸飞",1,"201992467",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992484","123456","庞兴耀",1,"201992484",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201995036","123456","陈嗣元",1,"201995036",1,"软1904");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992003","123456","莫颖裕",1,"201992003",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992084","123456","琚星浩",1,"201992084",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992100","123456","卜清发",1,"201992100",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992127","123456","江朋",1,"201992127",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992141","123456","王嘉良",1,"201992141",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992197","123456","冯家成",1,"201992197",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992208","123456","孙博",1,"201992208",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992322","123456","钱坤",1,"201992322",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992357","123456","佟昕峰",1,"201992357",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992369","123456","陈英奇",1,"201992369",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992449","123456","武闯",1,"201992449",1,"软1911");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992016","123456","马驰",1,"201992016",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992026","123456","王定章",1,"201992026",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992031","123456","姜成龙",1,"201992031",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992047","123456","徐雯",1,"201992047",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992073","123456","陆瑶",1,"201992073",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992082","123456","闫逸飞",1,"201992082",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992126","123456","张竞驰",1,"201992126",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992138","123456","朱倚岩",1,"201992138",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992214","123456","罗仕杰",1,"201992214",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992290","123456","崔书俊",1,"201992290",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992387","123456","王姝",1,"201992387",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992401","123456","李天傲",1,"201992401",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992410","123456","杜春光",1,"201992410",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992435","123456","丁亚东",1,"201992435",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992438","123456","方陶松",1,"201992438",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992462","123456","师婉婷",1,"201992462",1,"软1912");
INSERT INTO student (username, PASSWORD, realName, school, studentNumber, department, schoolClass) VALUES ("201992479","123456","刘浪沙",1,"201992479",1,"软1912");

INSERT INTO teacher (username, PASSWORD, realName, school, teacherNumber, department) VALUES ("username1","123456","赵婧",1,"1000010000",1);

INSERT INTO course(courseName, teacher, startDate, endDate) VALUES ("设计模式",1,"2021-3-1","2021-6-30"); 
INSERT INTO student_course  SELECT studentId, 1 FROM student WHERE phone IS NULL;

INSERT INTO exam (studentNumber, courseId) VALUES (201992007, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992027, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992036, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992055, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992085, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992117, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992157, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992173, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992179, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992182, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992183, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992220, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992244, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992251, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992262, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992283, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992296, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992301, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992308, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992324, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992342, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992383, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992397, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992402, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992414, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992427, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992444, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992460, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992486, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992487, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992230, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201941325, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201981561, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992030, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992034, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992046, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992063, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992071, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992087, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992112, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992118, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992121, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992159, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992180, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992186, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992209, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992229, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992233, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992252, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992267, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992298, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992316, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992336, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992355, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992366, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992367, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992382, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992413, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992426, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992434, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992467, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992484, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201995036, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992003, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992084, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992100, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992127, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992141, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992197, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992208, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992322, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992357, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992369, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992449, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992016, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992026, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992031, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992047, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992073, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992082, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992126, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992138, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992214, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992290, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992387, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992401, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992410, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992435, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992438, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992462, 1);
INSERT INTO exam (studentNumber, courseId) VALUES (201992479, 1);


