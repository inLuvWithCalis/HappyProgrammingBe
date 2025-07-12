package com.example.happyprogrambe.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorSearchResponse {
    private String mentorId;
    private String fullname;
    private String profession;
    private String language;
    private String introduction;
    private String serviceDescription;
    private String achievementDescription;
    private String facebook;
    private String github;
    private List<MentorSkillInfo> skills;
    private Double avgRate;
    private Integer followerCount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MentorSkillInfo {
        private String skillId;
        private String skillName;
        private Integer yearsExperience;
        private Integer rate;
    }
}
