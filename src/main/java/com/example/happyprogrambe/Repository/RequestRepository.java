package com.example.happyprogrambe.Repository;

import com.example.happyprogrambe.Domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

    // Tìm tất cả yêu cầu của một mentee
    List<Request> findByMentee_UserId(String menteeId);

    // Tìm yêu cầu theo mentee với phân trang
    Page<Request> findByMentee_UserId(String menteeId, Pageable pageable);

    // Tìm yêu cầu theo mentee và status
    Page<Request> findByMentee_UserIdAndStatus(String menteeId, String status, Pageable pageable);

    // Đếm yêu cầu theo mentee và status
    int countByMentee_UserIdAndStatus(String menteeId, String status);

    // Đếm tổng yêu cầu của mentee
    int countByMentee_UserId(String menteeId);

    // Tìm yêu cầu theo mentee và có thể chỉnh sửa (chưa được approve)
    @Query("SELECT r FROM Request r WHERE r.mentee.userId = :menteeId AND r.status IN ('O', 'C')")
    List<Request> findEditableRequestsByMentee(@Param("menteeId") String menteeId);

    // Tìm yêu cầu theo mentor
    List<Request> findByMentor_MentorId(String mentorId);

    // Tìm request với ID cao nhất để generate ID mới
    Optional<Request> findTopByOrderByRequestIdDesc();
}
