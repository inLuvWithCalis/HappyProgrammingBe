CREATE DATABASE IF NOT EXISTS MentorSystemDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE MentorSystemDB;

CREATE TABLE Role (
    roleId VARCHAR(10) PRIMARY KEY,
    roleName VARCHAR(255)
);

CREATE TABLE Users (
    userID VARCHAR(8) PRIMARY KEY,
    username VARCHAR(20),
    email VARCHAR(255),
    fullname VARCHAR(50),
    password VARCHAR(20),
    phone VARCHAR(10),
    address VARCHAR(255),
    dob DATE,
    sex VARCHAR(1),
    image VARCHAR(255),
    status BIT,
    emailStatus BIT
);

CREATE TABLE mentorDetails (
    mentorID VARCHAR(8) PRIMARY KEY,
    facebook VARCHAR(255),
    github VARCHAR(255),
    profession NVARCHAR(255),
    language NVARCHAR(255),
    introduction NVARCHAR(255),
    serviceDescription NVARCHAR(255),
    achievementDescription NVARCHAR(255),
    FOREIGN KEY (mentorID) REFERENCES Users(userID)
);

CREATE TABLE Statistic (
    userId VARCHAR(25) PRIMARY KEY,
    role VARCHAR(20),
    totalRequests INT,
    completedRequests INT,
    canceledRequests INT,
    avgRate DOUBLE,
    followerCount INT,
    lastUpdated DATETIME,
    FOREIGN KEY (userId) REFERENCES Users(userID)
);

CREATE TABLE skills (
    skillID VARCHAR(8) PRIMARY KEY,
    skillName VARCHAR(50),
    status BIT
);

CREATE TABLE mentorSkills (
    mentorSkillID VARCHAR(8) PRIMARY KEY,
    mentorID VARCHAR(8),
    skillID VARCHAR(8),
    yearsExperience INT,
    rate INT,
    FOREIGN KEY (mentorID) REFERENCES mentorDetails(mentorID),
    FOREIGN KEY (skillID) REFERENCES skills(skillID)
);

CREATE TABLE Request (
    requestId VARCHAR(25) PRIMARY KEY,
    menteeId VARCHAR(20),
    mentorId VARCHAR(255),
    skillsId VARCHAR(50),
    deadline DATETIME,
    title VARCHAR(255),
    reqContent NVARCHAR(255),
    status VARCHAR(1),
    openedTime DATETIME,
    approvedTime DATETIME,
    canceledTime DATETIME,
    closedTime DATETIME,
    FOREIGN KEY (menteeId) REFERENCES Users(userID)
    -- Có thể bổ sung FOREIGN KEY cho mentorId nếu cần liên kết tới mentorDetails
);

CREATE TABLE Comments (
    commentID VARCHAR(10) PRIMARY KEY,
    menteeID VARCHAR(8),
    mentorID VARCHAR(8),
    rate INT,
    comments NVARCHAR(1500),
    timestamp DATETIME,
    FOREIGN KEY (menteeID) REFERENCES Users(userID),
    FOREIGN KEY (mentorID) REFERENCES mentorDetails(mentorID)
);

CREATE TABLE followers (
    followID VARCHAR(10) PRIMARY KEY,
    menteeID VARCHAR(8),
    mentorID VARCHAR(8),
    timestamp DATETIME,
    FOREIGN KEY (menteeID) REFERENCES Users(userID),
    FOREIGN KEY (mentorID) REFERENCES mentorDetails(mentorID)
);



-- Thêm dữ liệu mẫu cho bảng Role
INSERT INTO Role (roleId, roleName) VALUES
('R1', 'Mentor'),
('R2', 'Mentee'),
('R3', 'Admin');

-- Thêm dữ liệu mẫu cho bảng Users
INSERT INTO Users (userID, username, email, fullname, password, phone, address, dob, sex, image, status, emailStatus) VALUES
('U001', 'mentor01', 'mentor01@email.com', 'Nguyen Van A', '123456', '0909123456', 'Ha Noi', '1990-05-10', 'M', NULL, 1, 1),
('U002', 'mentee01', 'mentee01@email.com', 'Tran Thi B', '654321', '0912345678', 'Ho Chi Minh', '1999-09-15', 'F', NULL, 1, 1),
('U003', 'mentor02', 'mentor02@email.com', 'Le Van C', 'abcdef', '0922334455', 'Da Nang', '1988-03-20', 'M', NULL, 1, 1);

-- Thêm dữ liệu mẫu cho bảng mentorDetails
INSERT INTO mentorDetails (mentorID, facebook, github, profession, language, introduction, serviceDescription, achievementDescription) VALUES
('U001', 'fb.com/nguyenvana', 'github.com/nguyenvana', N'IT Lecturer', N'English, Vietnamese', N'Tôi là mentor nhiều kinh nghiệm.', N'Tư vấn lập trình và định hướng nghề nghiệp.', N'Giải thưởng Olympic Tin học SV.'),
('U003', 'fb.com/levanc', 'github.com/levanc', N'Developer', N'English', N'5 năm kinh nghiệm lập trình.', N'Hỗ trợ mentee về project thực tế.', N'Best Employee 2021');

-- Thêm dữ liệu mẫu cho bảng Statistic
INSERT INTO Statistic (userId, role, totalRequests, completedRequests, canceledRequests, avgRate, followerCount, lastUpdated) VALUES
('U001', 'Mentor', 15, 13, 2, 4.8, 5, '2024-07-01 12:00:00'),
('U002', 'Mentee', 7, 6, 1, 0, 2, '2024-07-01 12:00:00'),
('U003', 'Mentor', 10, 9, 1, 4.5, 3, '2024-07-01 12:00:00');

-- Thêm dữ liệu mẫu cho bảng skills
INSERT INTO skills (skillID, skillName, status) VALUES
('S001', N'Java', 1),
('S002', N'Python', 1),
('S003', N'Database', 1),
('S004', N'JavaScript', 1);

-- Thêm dữ liệu mẫu cho bảng mentorSkills
INSERT INTO mentorSkills (mentorSkillID, mentorID, skillID, yearsExperience, rate) VALUES
('MS01', 'U001', 'S001', 7, 5),
('MS02', 'U001', 'S003', 5, 4),
('MS03', 'U003', 'S002', 4, 5);

-- Thêm dữ liệu mẫu cho bảng Request
INSERT INTO Request (requestId, menteeId, mentorId, skillsId, deadline, title, reqContent, status, openedTime, approvedTime, canceledTime, closedTime) VALUES
('RQ01', 'U002', 'U001', 'S001', '2024-08-01 17:00:00', N'Học Java căn bản', N'Em muốn học lập trình Java.', '1', '2024-07-05 08:00:00', '2024-07-05 09:00:00', NULL, NULL),
('RQ02', 'U002', 'U003', 'S002', '2024-08-10 17:00:00', N'Làm quen Python', N'Em cần hướng dẫn project Python.', '1', '2024-07-06 08:00:00', '2024-07-06 09:00:00', NULL, NULL);

-- Thêm dữ liệu mẫu cho bảng Comments
INSERT INTO Comments (commentID, menteeID, mentorID, rate, comments, timestamp) VALUES
('CMT01', 'U002', 'U001', 5, N'Mentor hướng dẫn rất nhiệt tình.', '2024-07-10 14:30:00'),
('CMT02', 'U002', 'U003', 4, N'Bài học bổ ích, dễ hiểu.', '2024-07-12 15:00:00');

-- Thêm dữ liệu mẫu cho bảng followers
INSERT INTO followers (followID, menteeID, mentorID, timestamp) VALUES
('F01', 'U002', 'U001', '2024-07-01 10:00:00'),
('F02', 'U002', 'U003', '2024-07-02 10:30:00');
