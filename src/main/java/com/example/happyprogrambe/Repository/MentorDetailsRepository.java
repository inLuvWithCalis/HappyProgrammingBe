package com.example.happyprogrambe.Repository;

import com.example.happyprogrambe.Domain.MentorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorDetailsRepository extends JpaRepository<MentorDetails, String> {
    Optional<MentorDetails> findTopByOrderByMentorIdDesc();
}
