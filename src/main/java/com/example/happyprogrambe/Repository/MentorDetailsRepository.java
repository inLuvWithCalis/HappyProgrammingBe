package com.example.happyprogrambe.Repository;

import com.example.happyprogrambe.Domain.MentorDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorDetailsRepository extends JpaRepository<MentorDetails, String> {
    Optional<MentorDetails> findTopByOrderByMentorIdDesc();

    @Query("SELECT DISTINCT m FROM MentorDetails m " +
           "LEFT JOIN m.mentorSkills ms " +
           "LEFT JOIN m.mentor.statistic s " +
           "WHERE (:profession IS NULL OR m.profession LIKE %:profession%) " +
           "AND (:skillId IS NULL OR ms.skill.skillId = :skillId) " +
           "AND (:language IS NULL OR m.language LIKE %:language%) " +
           "AND (:minRate IS NULL OR ms.rate >= :minRate) " +
           "AND (:minYearsExperience IS NULL OR ms.yearsExperience >= :minYearsExperience) " +
           "AND m.mentor.status = true")
    Page<MentorDetails> searchMentors(
            @Param("profession") String profession,
            @Param("skillId") String skillId,
            @Param("language") String language,
            @Param("minRate") Integer minRate,
            @Param("minYearsExperience") Integer minYearsExperience,
            Pageable pageable
    );
}
