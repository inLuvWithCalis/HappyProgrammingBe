package com.example.happyprogrambe.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutResponse {
    private String userId;
    private String username;
    private String message;
    private String logoutTime;
}
