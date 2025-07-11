# API Documentation - HappyProgram Authentication

## Tổng quan
API REST cho hệ thống Mentor System với các chức năng đăng ký và đăng nhập.

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
