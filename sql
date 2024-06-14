CREATE DATABASE IF NOT EXISTS linkedin_db;

USE linkedin_db;

CREATE TABLE IF NOT EXISTS users(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL, #unique 제약조건 추가
password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS posts(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
content TEXT NOT NULL,
image BLOB,
created_at DATETIME
);

CREATE TABLE IF NOT EXISTS comments(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
comment_content VARCHAR(200),
post_id BIGINT,
FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS user_profiles(
id INT AUTO_INCREMENT PRIMARY KEY,
profile_picture BLOB,
profile_background_picture BLOB,
full_name VARCHAR(50) NOT NULL,
introduction TEXT,
bio TEXT,
education VARCHAR(200),
location VARCHAR(200),
certification VARCHAR(200),
user_id BIGINT,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

### 데이터 seed 심는 부분 ###

USE linkedin_db;

INSERT INTO users (email, password) VALUES
('test1@example.com', 'password1'),
('test2@example.com', 'password2'),
('test3@example.com', 'password3');

INSERT INTO posts (content, image, created_at) VALUES
('First post content', NULL , NOW()),
('Second post content', NULL, NOW()),
('Third post content', NULL, NOW());

INSERT INTO comments (comment_content, post_id) VALUES
('첫번째 게시물의 댓글입니다', 1),
('두번째 게시물 댓글', 2),
('세번째 게시물 댓글데스', 3);

INSERT INTO user_profiles (profile_picture, profile_background_picture, full_name, introduction, bio, education, location, certification, user_id) VALUES
('FFD8FFE000104A464946.', '89504E470D0A1A0A0000...', 'jeongmin choi', '제이미의 소개글', NULL, '와플대학 와플학', '인천', NULL, 1),
('89504E470D0A1A0A0000', 'FFD8FFE000104A464946...', 'yeonhee kim', '연희님의 소개글', NULL, '서울대 컴퓨터공학', '서울', NULL, 2),
('FFD8FFE000104A464946', '89504E470D0A1A0A0000...', 'sangyoon kim', '상윤님의 소개글', NULL, '연세대 의예과', '대전', NULL, 3);



//test//test