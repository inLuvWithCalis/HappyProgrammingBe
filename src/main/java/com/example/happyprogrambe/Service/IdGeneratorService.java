package com.example.happyprogrambe.Service;

import com.example.happyprogrambe.Repository.UserRepository;
import com.example.happyprogrambe.Repository.MentorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorDetailsRepository mentorDetailsRepository;

    public String generateUserId() {
        // Lấy ID cao nhất hiện tại và tăng lên 1
        String lastUserId = userRepository.findTopByOrderByUserIdDesc()
                .map(user -> user.getUserId())
                .orElse("U000");

        int nextNumber = Integer.parseInt(lastUserId.substring(1)) + 1;
        return String.format("U%03d", nextNumber);
    }

    public String generateMentorDetailsId() {
        String lastId = mentorDetailsRepository.findTopByOrderByMentorIdDesc()
                .map(mentor -> mentor.getMentorId())
                .orElse("U000");

        int nextNumber = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("U%03d", nextNumber);
    }

    public String generateMentorSkillId() {
        // Tạm thời sử dụng timestamp, sẽ cải thiện khi có MentorSkillsRepository
        long count = System.currentTimeMillis() % 1000;
        return String.format("MS%03d", count + 1);
    }

    public String generateRequestId() {
        long count = System.currentTimeMillis() % 1000;
        return String.format("REQ%03d", count + 1);
    }

    public String generateCommentId() {
        long count = System.currentTimeMillis() % 1000;
        return String.format("C%03d", count + 1);
    }

    public String generateFollowId() {
        long count = System.currentTimeMillis() % 1000;
        return String.format("F%03d", count + 1);
    }
}
