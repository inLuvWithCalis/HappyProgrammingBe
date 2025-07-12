package com.example.happyprogrambe.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorSearchDto {
    private String profession;
    private String skillId;
    private String language;
    private Integer minRate;
    private Integer minYearsExperience;
}
