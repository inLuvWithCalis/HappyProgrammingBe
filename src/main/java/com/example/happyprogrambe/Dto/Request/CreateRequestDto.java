package com.example.happyprogrambe.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDto {
    private String mentorId;
    private String skillId;
    private LocalDateTime deadline;
    private String title;
    private String reqContent;
}
