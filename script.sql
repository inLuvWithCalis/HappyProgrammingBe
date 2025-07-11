CREATE
DATABASE IF NOT EXISTS MentorSystemDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE
MentorSystemDB;

CREATE TABLE Role
(
    roleId   VARCHAR(20) PRIMARY KEY,
    roleName VARCHAR(255) NOT NULL
);

CREATE TABLE Users
(
    userId      VARCHAR(20) PRIMARY KEY,
    username    VARCHAR(20)  NOT NULL,
    email       VARCHAR(255) NOT NULL,
    roleId      VARCHAR(10)  NOT NULL,
    fullname    VARCHAR(50)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    phone       VARCHAR(10),
    address     VARCHAR(255),
    dob         DATE,
    sex         ENUM('M','F','O'),
    image       VARCHAR(255),
    status      TINYINT(1) NOT NULL DEFAULT 1,
    emailStatus TINYINT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (roleId) REFERENCES Role (roleId)
);

CREATE TABLE MentorDetails
(
    mentorId               VARCHAR(20) PRIMARY KEY,
    facebook               VARCHAR(255),
    github                 VARCHAR(255),
    profession             VARCHAR(255),
    language               VARCHAR(255),
    introduction           VARCHAR(255),
    serviceDescription     VARCHAR(255),
    achievementDescription VARCHAR(255),
    FOREIGN KEY (mentorId) REFERENCES Users (userId)
);

CREATE TABLE Statistic
(
    userId            VARCHAR(20) PRIMARY KEY,
    roleId            VARCHAR(10),
    totalRequests     INT DEFAULT 0,
    completedRequests INT DEFAULT 0,
    canceledRequests  INT DEFAULT 0,
    avgRate DOUBLE DEFAULT 0,
    followerCount     INT DEFAULT 0,
    lastUpdated       DATETIME,
    FOREIGN KEY (userId) REFERENCES Users (userId),
    FOREIGN KEY (roleId) REFERENCES Role (roleId)
);

CREATE TABLE Skills
(
    skillId   VARCHAR(20) PRIMARY KEY,
    skillName VARCHAR(50) NOT NULL,
    status    TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE MentorSkills
(
    mentorSkillId   VARCHAR(20) PRIMARY KEY,
    mentorId        VARCHAR(8),
    skillId         VARCHAR(8),
    yearsExperience INT DEFAULT 0,
    rate            INT DEFAULT 0,
    FOREIGN KEY (mentorId) REFERENCES MentorDetails (mentorId),
    FOREIGN KEY (skillId) REFERENCES Skills (skillId)
);

CREATE TABLE Request
(
    requestId    VARCHAR(25) PRIMARY KEY,
    menteeId     VARCHAR(8),
    mentorId     VARCHAR(8),
    skillId      VARCHAR(8),
    deadline     DATETIME,
    title        VARCHAR(255),
    reqContent   VARCHAR(255),
    status       CHAR(1),
    openedTime   DATETIME,
    approvedTime DATETIME,
    canceledTime DATETIME,
    closedTime   DATETIME,
    FOREIGN KEY (menteeId) REFERENCES Users (userId),
    FOREIGN KEY (mentorId) REFERENCES MentorDetails (mentorId),
    FOREIGN KEY (skillId) REFERENCES Skills (skillId)
);

CREATE TABLE Comments
(
    commentId VARCHAR(20) PRIMARY KEY,
    menteeId  VARCHAR(8),
    mentorId  VARCHAR(8),
    rate      INT,
    comments  VARCHAR(1500),
    timestamp DATETIME,
    FOREIGN KEY (menteeId) REFERENCES Users (userId),
    FOREIGN KEY (mentorId) REFERENCES MentorDetails (mentorId)
);

CREATE TABLE Followers
(
    followId  VARCHAR(20) PRIMARY KEY,
    menteeId  VARCHAR(8),
    mentorId  VARCHAR(8),
    timestamp DATETIME,
    FOREIGN KEY (menteeId) REFERENCES Users (userId),
    FOREIGN KEY (mentorId) REFERENCES MentorDetails (mentorId)
);


-- Thêm dữ liệu vào bảng Role
INSERT INTO Role (roleId, roleName)
VALUES ('R001', 'Admin'),
       ('R002', 'Mentor'),
       ('R003', 'Mentee');

-- Thêm dữ liệu vào bảng Users
INSERT INTO Users (userId, username, email, roleId, fullname, password, phone, address, dob, sex, image, status,
                   emailStatus)
VALUES ('U001', 'adminuser', 'admin@example.com', 'R001', 'Admin User', 'hashed_password_1', '0123456789',
        '123 Admin St', '1980-01-01', 'M', NULL, 1, 1),
       ('U002', 'mentor1', 'mentor1@example.com', 'R002', 'Mentor One', 'hashed_password_2', '0987654321',
        '456 Mentor Rd', '1990-05-15', 'F', NULL, 1, 1),
       ('U003', 'mentee1', 'mentee1@example.com', 'R003', 'Mentee One', 'hashed_password_3', '0912345678',
        '789 Mentee Ave', '1995-07-20', 'M', NULL, 1, 1);

-- Thêm dữ liệu vào bảng MentorDetails
INSERT INTO MentorDetails (mentorId, facebook, github, profession, language, introduction, serviceDescription,
                           achievementDescription)
VALUES ('U002', 'https://facebook.com/mentor1', 'https://github.com/mentor1', 'Software Engineer',
        'English, Vietnamese', 'Experienced software engineer', 'Mentoring in software development',
        'Completed 100+ projects');

-- Thêm dữ liệu vào bảng Skills
INSERT INTO Skills (skillId, skillName, status)
VALUES ('S001', 'Java', 1),
       ('S002', 'Python', 1),
       ('S003', 'Web Development', 1);

-- Thêm dữ liệu vào bảng MentorSkills
INSERT INTO MentorSkills (mentorSkillId, mentorId, skillId, yearsExperience, rate)
VALUES ('MS001', 'U002', 'S001', 5, 9),
       ('MS002', 'U002', 'S002', 3, 8);

-- Thêm dữ liệu vào bảng Request
INSERT INTO Request (requestId, menteeId, mentorId, skillId, deadline, title, reqContent, status, openedTime,
                     approvedTime, canceledTime, closedTime)
VALUES ('REQ001', 'U003', 'U002', 'S001', '2025-08-01 23:59:59', 'Java Mentoring', 'Need help with Java basics', 'O',
        NOW(), NULL, NULL, NULL);

-- Thêm dữ liệu vào bảng Comments
INSERT INTO Comments (commentId, menteeId, mentorId, rate, comments, timestamp)
VALUES ('C001', 'U003', 'U002', 5, 'Great mentor, very helpful!', NOW());

-- Thêm dữ liệu vào bảng Followers
INSERT INTO Followers (followId, menteeId, mentorId, timestamp)
VALUES ('F001', 'U003', 'U002', NOW());

--------------------
-- Thêm dữ liệu vào bảng Users
INSERT INTO Users (userId, username, email, roleId, fullname, password, phone, address, dob, sex, image, status,
                   emailStatus)
VALUES ('U004', 'mentor2', 'mentor2@example.com', 'R002', 'Mentor Two', 'hashed_password_4', '0909123456',
        '101 Mentor Blvd', '1988-03-12', 'M', NULL, 1, 1),
       ('U005', 'mentee2', 'mentee2@example.com', 'R003', 'Mentee Two', 'hashed_password_5', '0909876543',
        '202 Mentee St', '1997-11-22', 'F', NULL, 1, 1),
       ('U006', 'mentor3', 'mentor3@example.com', 'R002', 'Mentor Three', 'hashed_password_6', '0911223344',
        '303 Mentor Ln', '1985-07-09', 'F', NULL, 1, 1),
       ('U007', 'mentee3', 'mentee3@example.com', 'R003', 'Mentee Three', 'hashed_password_7', '0922334455',
        '404 Mentee Rd', '1998-01-15', 'M', NULL, 1, 1),
       ('U008', 'mentee4', 'mentee4@example.com', 'R003', 'Mentee Four', 'hashed_password_8', '0933445566',
        '505 Mentee Ave', '2000-06-30', 'F', NULL, 1, 1);

-- Thêm dữ liệu vào bảng MentorDetails
INSERT INTO MentorDetails (mentorId, facebook, github, profession, language, introduction, serviceDescription,
                           achievementDescription)
VALUES ('U004', 'https://facebook.com/mentor2', 'https://github.com/mentor2', 'Data Scientist', 'English, French',
        'Passionate about data and AI', 'Helping mentees with data science projects', 'Published 5 research papers'),
       ('U006', 'https://facebook.com/mentor3', 'https://github.com/mentor3', 'UX Designer', 'English',
        'Creative UX designer', 'Mentoring on user experience design', 'Led design for top apps');

-- Thêm dữ liệu vào bảng Skills
INSERT INTO Skills (skillId, skillName, status)
VALUES ('S004', 'Data Science', 1),
       ('S005', 'Machine Learning', 1),
       ('S006', 'UX Design', 1),
       ('S007', 'Project Management', 1);

-- Thêm dữ liệu vào bảng MentorSkills
INSERT INTO MentorSkills (mentorSkillId, mentorId, skillId, yearsExperience, rate)
VALUES ('MS003', 'U004', 'S004', 4, 9),
       ('MS004', 'U004', 'S005', 3, 8),
       ('MS005', 'U006', 'S006', 6, 9),
       ('MS006', 'U006', 'S007', 5, 7);

-- Thêm dữ liệu vào bảng Request
INSERT INTO Request (requestId, menteeId, mentorId, skillId, deadline, title, reqContent, status, openedTime,
                     approvedTime, canceledTime, closedTime)
VALUES ('REQ002', 'U005', 'U004', 'S004', '2025-09-15 23:59:59', 'Data Science Help',
        'Need guidance on data cleaning and visualization', 'O', NOW(), NULL, NULL, NULL),
       ('REQ003', 'U007', 'U006', 'S006', '2025-07-31 23:59:59', 'UX Design Basics',
        'Looking for help with wireframing and prototyping', 'O', NOW(), NULL, NULL, NULL),
       ('REQ004', 'U008', 'U004', 'S005', '2025-10-10 23:59:59', 'ML Project Support',
        'Assistance with machine learning algorithms', 'O', NOW(), NULL, NULL, NULL);

-- Thêm dữ liệu vào bảng Comments
INSERT INTO Comments (commentId, menteeId, mentorId, rate, comments, timestamp)
VALUES ('C002', 'U005', 'U004', 4, 'Very knowledgeable and patient mentor.', NOW()),
       ('C003', 'U007', 'U006', 5, 'Helped me improve my design skills significantly.', NOW());

-- Thêm dữ liệu vào bảng Followers
INSERT INTO Followers (followId, menteeId, mentorId, timestamp)
VALUES ('F002', 'U005', 'U004', NOW()),
       ('F003', 'U007', 'U006', NOW()),
       ('F004', 'U008', 'U004', NOW());


