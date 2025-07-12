# API Documentation - HappyProgram System

## Tổng quan
API REST cho hệ thống Mentor System với các chức năng đăng ký, đăng nhập và quản lý yêu cầu Mentee.

Base URL: `http://localhost:8080`

## Authentication Endpoints

### 1. Đăng ký (Sign Up)
**Endpoint:** `POST /api/auth/signup`

**Mô tả:** Tạo tài khoản mới cho Mentor hoặc Mentee

**Request Body:**
```json
{
  "username": "string (required, max 20 chars)",
  "email": "string (required, valid email, max 255 chars)",
  "fullname": "string (required, max 50 chars)",
  "password": "string (required, min 6 chars)",
  "roleId": "string (required, R002=Mentor, R003=Mentee)",
  "phone": "string (optional, max 10 chars)",
  "address": "string (optional, max 255 chars)",
  "dob": "date (optional, format: YYYY-MM-DD)",
  "sex": "enum (optional, M/F/O)",
  
  // Chỉ áp dụng cho Mentor (roleId = R002)
  "facebook": "string (optional)",
  "github": "string (optional)",
  "profession": "string (optional)",
  "language": "string (optional)",
  "introduction": "string (optional)",
  "serviceDescription": "string (optional)",
  "achievementDescription": "string (optional)"
}
```

**Ví dụ Request (Mentee):**
```json
{
  "username": "mentee_new",
  "email": "mentee@example.com",
  "fullname": "New Mentee",
  "password": "password123",
  "roleId": "R003",
  "phone": "0909123456",
  "address": "123 Test Street",
  "dob": "1999-05-15",
  "sex": "M"
}
```

**Ví dụ Request (Mentor):**
```json
{
  "username": "mentor_new",
  "email": "mentor@example.com",
  "fullname": "New Mentor",
  "password": "password123",
  "roleId": "R002",
  "phone": "0909123456",
  "address": "456 Mentor Avenue",
  "dob": "1985-10-20",
  "sex": "F",
  "facebook": "https://facebook.com/mentor_new",
  "github": "https://github.com/mentor_new",
  "profession": "Software Engineer",
  "language": "English, Vietnamese",
  "introduction": "Experienced software engineer with 10+ years",
  "serviceDescription": "Mentoring in web development and system design",
  "achievementDescription": "Led 50+ successful projects"
}
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "User registered successfully",
  "data": {
    "userId": "U009",
    "username": "mentee_new",
    "email": "mentee@example.com",
    "fullname": "New Mentee",
    "phone": "0909123456",
    "address": "123 Test Street",
    "dob": "1999-05-15",
    "sex": "M",
    "image": null,
    "roleId": "R003",
    "roleName": "Mentee",
    "token": null,
    "status": true,
    "emailStatus": false
  }
}
```

**Response (Error - 400):**
```json
{
  "status": 400,
  "message": "Username already exists",
  "data": null
}
```

### 2. Đăng nhập (Sign In)
**Endpoint:** `POST /api/auth/signin`

**Mô tả:** Xác thực tài khoản và đăng nhập vào hệ thống

**Request Body:**
```json
{
  "username": "string (required)",
  "password": "string (required)"
}
```

**Ví dụ Request:**
```json
{
  "username": "mentee_new",
  "password": "password123"
}
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Login successful",
  "data": {
    "userId": "U009",
    "username": "mentee_new",
    "email": "mentee@example.com",
    "fullname": "New Mentee",
    "phone": "0909123456",
    "address": "123 Test Street",
    "dob": "1999-05-15",
    "sex": "M",
    "image": null,
    "roleId": "R003",
    "roleName": "Mentee",
    "token": null,
    "status": true,
    "emailStatus": false
  }
}
```

**Response (Error - 400):**
```json
{
  "status": 400,
  "message": "Invalid username or password",
  "data": null
}
```

## Test Endpoints

### 3. Test API Connection
**Endpoint:** `GET /api/auth/test`

**Mô tả:** Kiểm tra kết nối API

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": "Authentication API is working"
}
```

### 4. Database Status
**Endpoint:** `GET /api/test/database-status`

**Mô tả:** Kiểm tra kết nối database

**Response:**
```json
"Database connected successfully! Users: 8, Roles: 3"
```

### 5. Get All Users (Test)
**Endpoint:** `GET /api/test/users`

**Mô tả:** Lấy danh sách tất cả users (chỉ dùng để test)

### 6. Get All Roles (Test)
**Endpoint:** `GET /api/test/roles`

**Mô tả:** Lấy danh sách tất cả roles

## Phân quyền theo Role

### Role IDs:
- **R001**: Admin
- **R002**: Mentor  
- **R003**: Mentee

### Quy tắc đăng ký:
- Chỉ cho phép đăng ký với roleId = "R002" (Mentor) hoặc "R003" (Mentee)
- Admin (R001) không được phép đăng ký qua API

## Validation Rules

### Username:
- Bắt buộc
- Tối đa 20 ký tự
- Không được trùng với username đã tồn tại

### Email:
- Bắt buộc
- Định dạng email hợp lệ
- Tối đa 255 ký tự
- Không được trùng với email đã tồn tại

### Password:
- Bắt buộc
- Tối thiểu 6 ký tự
- Được mã hóa bằng BCrypt

### Phone:
- Tùy chọn
- Tối đa 10 ký tự

### Fullname:
- Bắt buộc
- Tối đa 50 ký tự

## Error Codes

| Status Code | Mô tả |
|-------------|--------|
| 200 | Thành công |
| 400 | Lỗi validation hoặc business logic |
| 500 | Lỗi server |

## Cách test API

### 1. Sử dụng Postman:
1. Import các endpoint vào Postman
2. Set Content-Type: application/json
3. Test theo thứ tự: test connection → signup → signin

### 2. Sử dụng curl:
```bash
# Test connection
curl -X GET http://localhost:8080/api/auth/test

# Sign up
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com", 
    "fullname": "Test User",
    "password": "password123",
    "roleId": "R003"
  }'

# Sign in
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

## Database Schema

Các bảng chính được sử dụng:
- **Users**: Lưu thông tin người dùng
- **Role**: Lưu thông tin vai trò (Admin, Mentor, Mentee)
- **MentorDetails**: Lưu thông tin chi tiết của Mentor

ID được tự động tăng theo format:
- User ID: U001, U002, U003...
- Mentor Skill ID: MS001, MS002, MS003...

## Mentee Endpoints

### 1. Tạo yêu cầu (Create Request)
**Endpoint:** `POST /api/mentee/{menteeId}/requests`

**Mô tả:** Cho phép Mentee tạo yêu cầu mới

**Path Parameters:**
- `menteeId`: ID của mentee (string, required)

**Request Body:**
```json
{
  "mentorId": "string (required, ID của mentor)",
  "skillId": "string (required, ID của skill)",
  "deadline": "datetime (required, format: YYYY-MM-DDTHH:MM:SS)",
  "title": "string (required, tiêu đề yêu cầu - sử dụng tiếng Anh)",
  "reqContent": "string (required, nội dung yêu cầu - sử dụng tiếng Anh)"
}
```

**Ví dụ Request:**
```json
{
  "mentorId": "U002",
  "skillId": "S001",
  "deadline": "2025-08-15T23:59:59",
  "title": "Learn Java Spring Boot",
  "reqContent": "I want to learn Spring Boot for web application development"
}
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Request created successfully",
  "data": {
    "requestId": "REQ005",
    "menteeId": "U003",
    "menteeName": "Mentee One",
    "mentorId": "U002",
    "mentorName": "Mentor One",
    "skillId": "S001",
    "skillName": "Java",
    "deadline": "2025-08-15T23:59:59",
    "title": "Learn Java Spring Boot",
    "reqContent": "I want to learn Spring Boot for web application development",
    "status": "O",
    "openedTime": "2025-07-12T10:30:00",
    "approvedTime": null,
    "canceledTime": null,
    "closedTime": null
  }
}
```

### 2. Cập nhật yêu cầu (Update Request)
**Endpoint:** `PUT /api/mentee/{menteeId}/requests/{requestId}`

**Mô tả:** Mentee có thể cập nhật các yêu cầu đã tạo (chỉ khi status = 'O' hoặc 'C')

**Path Parameters:**
- `menteeId`: ID của mentee
- `requestId`: ID của request cần cập nhật

**Request Body:** (tương tự Create Request)

### 3. Xóa yêu cầu (Delete Request)
**Endpoint:** `DELETE /api/mentee/{menteeId}/requests/{requestId}`

**Mô tả:** Cho phép Mentee xóa yêu cầu (chỉ khi status = 'O')

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Request deleted successfully",
  "data": "Deleted"
}
```

### 4. Danh sách tất cả yêu cầu (List All Requests)
**Endpoint:** `GET /api/mentee/{menteeId}/requests`

**Mô tả:** Mentee có thể xem tất cả các yêu cầu đã tạo với phân trang

**Path Parameters:**
- `menteeId`: ID của mentee (string, required)

**Query Parameters:**
- `page`: Số trang (default: 0) - Bắt đầu từ 0
- `size`: Số items per page (default: 10) - Tối đa 50
- `sortBy`: Trường để sort (default: "openedTime")
  - Các giá trị hợp lệ: "openedTime", "deadline", "title", "status"
- `sortDir`: Hướng sort (default: "desc")
  - "asc": Tăng dần
  - "desc": Giảm dần

**Ví dụ các URL test:**

1. **Lấy trang đầu tiên (mặc định):**
```
GET http://localhost:8080/api/mentee/U003/requests
```

2. **Lấy 5 requests đầu tiên:**
```
GET http://localhost:8080/api/mentee/U003/requests?size=5
```

3. **Lấy trang thứ 2, mỗi trang 3 items:**
```
GET http://localhost:8080/api/mentee/U003/requests?page=1&size=3
```

4. **Sắp xếp theo deadline tăng dần:**
```
GET http://localhost:8080/api/mentee/U003/requests?sortBy=deadline&sortDir=asc
```

5. **Sắp xếp theo title từ A-Z:**
```
GET http://localhost:8080/api/mentee/U003/requests?sortBy=title&sortDir=asc
```

6. **Kết hợp nhiều tham số:**
```
GET http://localhost:8080/api/mentee/U003/requests?page=0&size=5&sortBy=openedTime&sortDir=desc
```

**Ví dụ với curl:**
```bash
# Test cơ bản
curl -X GET "http://localhost:8080/api/mentee/U003/requests"

# Test với pagination
curl -X GET "http://localhost:8080/api/mentee/U003/requests?page=0&size=5"

# Test với sorting
curl -X GET "http://localhost:8080/api/mentee/U003/requests?sortBy=deadline&sortDir=asc"

# Test tổng hợp
curl -X GET "http://localhost:8080/api/mentee/U003/requests?page=0&size=3&sortBy=title&sortDir=asc"
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Requests retrieved successfully",
  "data": {
    "content": [
      {
        "requestId": "REQ004",
        "menteeId": "U003",
        "menteeName": "Mentee One",
        "mentorId": "U002",
        "mentorName": "Mentor One",
        "skillId": "S001",
        "skillName": "Java",
        "deadline": "2025-08-15T23:59:59",
        "title": "Java Spring Boot",
        "reqContent": "Learning java spring boot to enhance web application",
        "status": "O",
        "openedTime": "2025-07-12T17:05:14",
        "approvedTime": null,
        "canceledTime": null,
        "closedTime": null
      },
      {
        "requestId": "REQ001",
        "menteeId": "U003",
        "menteeName": "Mentee One",
        "mentorId": "U002",
        "mentorName": "Mentor One",
        "skillId": "S001",
        "skillName": "Java",
        "deadline": "2025-08-01T23:59:59",
        "title": "Java Mentoring",
        "reqContent": "Need help with Java basics",
        "status": "O",
        "openedTime": "2025-07-11T23:14:02",
        "approvedTime": null,
        "canceledTime": null,
        "closedTime": null
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10,
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
  }
}
```

**Response (Error - 400):**
```json
{
  "status": 400,
  "message": "You don't have permission to view these requests",
  "data": null
}
```

**Response (Empty List - 200):**
```json
{
  "status": 200,
  "message": "Requests retrieved successfully",
  "data": {
    "content": [],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10
    },
    "totalElements": 0,
    "totalPages": 0,
    "size": 10,
    "number": 0,
    "first": true,
    "last": true,
    "numberOfElements": 0,
    "empty": true
  }
}
```

### 5. Lọc yêu cầu theo trạng thái (Filter Request)
**Endpoint:** `GET /api/mentee/{menteeId}/requests/filter`

**Mô tả:** Mentee có thể lọc yêu cầu theo trạng thái

**Query Parameters:**
- `status`: Trạng thái yêu cầu (required)
  - "O": Open (Mở)
  - "A": Approved (Đã duyệt)
  - "C": Canceled (Đã hủy)
  - "F": Finished (Đã hoàn thành)
- `page`: Số trang (default: 0)
- `size`: Số items per page (default: 10)

### 6. Xem chi tiết yêu cầu (View Request)
**Endpoint:** `GET /api/mentee/{menteeId}/requests/{requestId}`

**Mô tả:** Cho phép Mentee xem chi tiết của một yêu cầu cụ thể

### 7. Thống kê yêu cầu (Request Statistics)
**Endpoint:** `GET /api/mentee/{menteeId}/requests/statistics`

**Mô tả:** Mentee có thể xem thống kê các yêu cầu của họ

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Statistics retrieved successfully",
  "data": {
    "totalRequests": 5,
    "openRequests": 2,
    "approvedRequests": 2,
    "canceledRequests": 1,
    "closedRequests": 0
  }
}
```

### 8. Tìm kiếm Mentor (Search Mentors)
**Endpoint:** `POST /api/mentee/mentors/search`

**Mô tả:** Cho phép Mentee tìm kiếm mentor phù hợp

**Query Parameters:**
- `page`: Số trang (default: 0)
- `size`: Số items per page (default: 10)

**Request Body:**
```json
{
  "profession": "string (optional, nghề nghiệp)",
  "skillId": "string (optional, ID skill)",
  "language": "string (optional, ngôn ngữ)",
  "minRate": "integer (optional, điểm đánh giá tối thiểu)",
  "minYearsExperience": "integer (optional, số năm kinh nghiệm tối thiểu)"
}
```

**Ví dụ Request:**
```json
{
  "profession": "Software Engineer",
  "skillId": "S001",
  "language": "English",
  "minRate": 8,
  "minYearsExperience": 3
}
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Mentors retrieved successfully",
  "data": {
    "content": [
      {
        "mentorId": "U002",
        "fullname": "Mentor One",
        "profession": "Software Engineer",
        "language": "English, Vietnamese",
        "introduction": "Experienced software engineer",
        "serviceDescription": "Mentoring in software development",
        "achievementDescription": "Completed 100+ projects",
        "facebook": "https://facebook.com/mentor1",
        "github": "https://github.com/mentor1",
        "skills": [
          {
            "skillId": "S001",
            "skillName": "Java",
            "yearsExperience": 5,
            "rate": 9
          }
        ],
        "avgRate": 4.5,
        "followerCount": 10
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10
    },
    "totalElements": 1,
    "totalPages": 1
  }
}
```

## Status Codes cho Request

| Status | Mô tả |
|--------|-------|
| O | Open - Yêu cầu mới tạo, chờ mentor duyệt |
| A | Approved - Đã được mentor chấp nhận |
| C | Canceled - Đã bị hủy |
| F | Finished - Đã hoàn thành |

## Business Rules

### Quyền thao tác Request:
1. **Tạo**: Mentee có thể tạo yêu cầu bất kỳ lúc nào
2. **Cập nhật**: Chỉ được phép khi status = 'O' (Open) hoặc 'C' (Canceled)
3. **Xóa**: Chỉ được phép khi status = 'O' (Open)
4. **Xem**: Mentee chỉ xem được yêu cầu của chính mình

### Validation Rules:
- `mentorId`: Phải tồn tại trong hệ thống
- `skillId`: Phải tồn tại và active
- `deadline`: Phải là thời gian trong tương lai
- `title`: Bắt buộc, không được rỗng, **sử dụng tiếng Anh**
- `reqContent`: Bắt buộc, không được rỗng, **sử dụng tiếng Anh**

## Cách test API Mentee

### 1. Test flow hoàn chỉnh:
```bash
# 1. Tạo request mới (sử dụng tiếng Anh)
curl -X POST http://localhost:8080/api/mentee/U003/requests \
  -H "Content-Type: application/json" \
  -d '{
    "mentorId": "U002",
    "skillId": "S001", 
    "deadline": "2025-08-15T23:59:59",
    "title": "Learn Java Spring Boot",
    "reqContent": "I want to learn Spring Boot for web development"
  }'

# 2. Xem danh sách requests
curl -X GET "http://localhost:8080/api/mentee/U003/requests?page=0&size=5"

# 3. Lọc theo status
curl -X GET "http://localhost:8080/api/mentee/U003/requests/filter?status=O"

# 4. Xem thống kê
curl -X GET http://localhost:8080/api/mentee/U003/requests/statistics

# 5. Tìm kiếm mentor
curl -X POST http://localhost:8080/api/mentee/mentors/search \
  -H "Content-Type: application/json" \
  -d '{
    "profession": "Software Engineer",
    "minRate": 8
  }'
```

## Lưu ý quan trọng về nội dung

### Ngôn ngữ sử dụng:
- **Title và reqContent**: Sử dụng tiếng Anh để tránh vấn đề encoding UTF-8
- **API responses**: Có thể chứa dữ liệu tiếng Anh từ database hiện tại
- **Error messages**: Vẫn sử dụng tiếng Anh hoặc tiếng Việt tùy context

### Ví dụ nội dung tiếng Anh thay thế:

| Tiếng Việt (tránh dùng) | Tiếng Anh (khuyến khích) |
|-------------------------|---------------------------|
| "Học Java Spring Boot" | "Learn Java Spring Boot" |
| "Tôi muốn học về Spring Boot" | "I want to learn Spring Boot" |
| "Cần hỗ trợ về lập trình" | "Need programming assistance" |
| "Hướng dẫn thiết kế website" | "Website design guidance" |
| "Học machine learning cơ bản" | "Learn basic machine learning" |

### Lợi ích của cách tiếp cận này:
- ✅ **Không cần sửa database schema**
- ✅ **Tránh lỗi UTF-8 encoding**
- ✅ **Tương thích với database hiện tại**
- ✅ **Dễ dàng testing và debugging**
- ✅ **Phù hợp với môi trường international**
