package com.example.happyprogrambe.Repository;

import com.example.happyprogrambe.Domain.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, String> {

    // Tìm skills theo status
    List<Skills> findByStatus(Boolean status);

    // Tìm skill theo tên
    Skills findBySkillName(String skillName);
}
