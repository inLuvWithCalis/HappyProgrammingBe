package com.example.happyprogrambe.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponse {
    private String requestId;
    private String menteeId;
    private String menteeName;
    private String mentorId;
    private String mentorName;
    private String skillId;
    private String skillName;
    private LocalDateTime deadline;
    private String title;
    private String reqContent;
    private String status;
    private LocalDateTime openedTime;
    private LocalDateTime approvedTime;
    private LocalDateTime canceledTime;
    private LocalDateTime closedTime;
}
