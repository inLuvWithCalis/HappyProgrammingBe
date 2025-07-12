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
  
  "Chỉ áp dụng cho Mentor (roleId = R002)": "bên dưới",
  
  
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

### 3. Đăng xuất (Logout)
**Endpoint:** `POST /api/auth/logout`

**Mô tả:** Đăng xuất người dùng khỏi hệ thống và invalidate session

**Request Body:**
```json
{
  "userId": "string (required, ID của user cần logout)"
}
```

**Ví dụ Request:**
```json
{
  "userId": "U003"
}
```

**Response (Success - 200):**
```json
{
  "status": 200,
  "message": "Logout successful",
  "data": {
    "userId": "U003",
    "username": "mentee1",
    "message": "Logout successful",
    "logoutTime": "2025-07-12T17:30:45.123456"
  }
}
```

**Response (Error - 400):**
```json
{
  "status": 400,
  "message": "User not found",
  "data": null
}
```

**Response (Error - 500):**
```json
{
  "status": 500,
  "message": "Internal server error",
  "data": null
}
```

### 4. Test API Connection
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

### 5. Database Status
**Endpoint:** `GET /api/test/database-status`

**Mô tả:** Kiểm tra kết nối database

**Response:**
```json
"Database connected successfully! Users: 8, Roles: 3"
```

### 6. Get All Users (Test)
**Endpoint:** `GET /api/test/users`

**Mô tả:** Lấy danh sách tất cả users (chỉ dùng để test)

### 7. Get All Roles (Test)
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

### 3. Sử dụng curl cho Authentication flow hoàn chỉnh:
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

# Logout (sử dụng userId từ response của sign in)
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "U009"
  }'
```

## Authentication Flow

### Quy trình hoàn chỉnh:
1. **Sign Up** → Tạo tài khoản mới
2. **Sign In** → Đăng nhập và nhận thông tin user (bao gồm userId)
3. **Sử dụng các API khác** → Thực hiện các chức năng (Mentee, Mentor, etc.)
4. **Logout** → Đăng xuất và invalidate session

### Lưu ý quan trọng:
- **userId**: Cần lưu từ response của sign in để sử dụng cho logout
- **Session**: Logout sẽ invalidate HTTP session hiện tại
- **Security**: Logout chỉ yêu cầu userId, không cần password
- **Error Handling**: Logout sẽ báo lỗi nếu userId không tồn tại

