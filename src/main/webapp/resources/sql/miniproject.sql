
-- 회원 테이블 생성
CREATE TABLE `cbj`.`member` (
  `userId` VARCHAR(8) NOT NULL,
  `userPwd` VARCHAR(200) NOT NULL,
  `userName` VARCHAR(12) NULL,
  `mobile` VARCHAR(13) NULL,
  `email` VARCHAR(50) NULL,
  `registerDate` DATETIME NULL DEFAULT now(),
  `userImg` VARCHAR(50) NOT NULL DEFAULT 'avatar.png',
  `userPoint` INT NULL DEFAULT '100',
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

-- 계층형 게시판 생성
CREATE TABLE `cbj`.`hboard` (
  `boardNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` VARCHAR(2000) NULL,
  `writer` VARCHAR(8) NULL,
  `postDate` DATETIME NULL DEFAULT now(),
  `readCount` INT NULL DEFAULT 0,
  `ref` INT NULL DEFAULT 0,
  `step` INT NULL DEFAULT 0,
  `refOrder` INT NULL DEFAULT 0,
  PRIMARY KEY (`boardNo`),
  INDEX `hboard_member_fk_idx` (`writer` ASC) VISIBLE,
  CONSTRAINT `hboard_member_fk`
    FOREIGN KEY (`writer`)
    REFERENCES `cbj`.`member` (`userId`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
COMMENT = '계층형게시판';

-- 회원 가입
insert into member(userId, userPwd, userName, mobile, email)
values('tosimi', sha1(md5('1234')), '토시미', '010-2222-6666', 'tosimi@abc.com');

insert into member(userId, userPwd, userName, mobile, email)
values('tomoong', sha1(md5('1234')), '토뭉이', '010-2222-8888', 'tomoong@abc.com');

select * from member;

-- 게시판에 게시글을 등록
insert into hboard (title, content, writer)
values ('아싸~~ 1등이다', '내용 무', 'tosimi');

insert into hboard (title, content, writer)
values ('에이~', '1등 놓쳤네', 'tomoong');

select * from hboard order by boardNo desc;

-- insert into hboard (title, content, writer)
-- values (#{title}, #{content}, #{writer});



