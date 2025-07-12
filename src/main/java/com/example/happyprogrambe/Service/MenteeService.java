package com.example.happyprogrambe.Service;

import com.example.happyprogrambe.Domain.*;
import com.example.happyprogrambe.Dto.Request.CreateRequestDto;
import com.example.happyprogrambe.Dto.Request.MentorSearchDto;
import com.example.happyprogrambe.Dto.Request.UpdateRequestDto;
import com.example.happyprogrambe.Dto.Response.MentorSearchResponse;
import com.example.happyprogrambe.Dto.Response.RequestResponse;
import com.example.happyprogrambe.Dto.Response.RequestStatisticResponse;
import com.example.happyprogrambe.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenteeService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final MentorDetailsRepository mentorDetailsRepository;
    private final SkillsRepository skillsRepository;
    private final IdGeneratorService idGeneratorService;

    @Transactional
    public RequestResponse createRequest(String menteeId, CreateRequestDto dto) {
        try {
            // Debug logging
            System.out.println("Creating request for menteeId: " + menteeId);
            System.out.println("DTO: mentorId=" + dto.getMentorId() + ", skillId=" + dto.getSkillId());

            // Validate mentee exists
            User mentee = userRepository.findById(menteeId)
                    .orElseThrow(() -> new RuntimeException("Mentee not found with ID: " + menteeId));
            System.out.println("Found mentee: " + mentee.getFullname());

            // Validate mentor exists
            MentorDetails mentor = mentorDetailsRepository.findById(dto.getMentorId())
                    .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + dto.getMentorId()));
            System.out.println("Found mentor: " + mentor.getMentorId());

            // Validate skill exists
            Skills skill = skillsRepository.findById(dto.getSkillId())
                    .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + dto.getSkillId()));
            System.out.println("Found skill: " + skill.getSkillName());

            // Generate request ID using IdGeneratorService for sequential IDs
            String requestId = idGeneratorService.generateRequestId();
            System.out.println("Generated requestId: " + requestId);

            // Create request with explicit field setting
            Request request = new Request();
            request.setRequestId(requestId);
            request.setMentee(mentee);
            request.setMentor(mentor);
            request.setSkill(skill);
            request.setDeadline(dto.getDeadline());
            request.setTitle(dto.getTitle());
            request.setReqContent(dto.getReqContent());
            request.setStatus("O"); // O = Open
            request.setOpenedTime(LocalDateTime.now());

            // Set null for optional time fields
            request.setApprovedTime(null);
            request.setCanceledTime(null);
            request.setClosedTime(null);

            System.out.println("About to save request...");
            Request savedRequest = requestRepository.save(request);
            System.out.println("Request saved successfully with ID: " + savedRequest.getRequestId());

            return convertToRequestResponse(savedRequest);

        } catch (Exception e) {
            System.err.println("Error creating request: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating request: " + e.getMessage(), e);
        }
    }

    @Transactional
    public RequestResponse updateRequest(String menteeId, String requestId, UpdateRequestDto dto) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        // Check if mentee owns this request
        if (!request.getMentee().getUserId().equals(menteeId)) {
            throw new RuntimeException("You don't have permission to update this request");
        }

        // Check if request can be updated (only Open or Canceled requests)
        if (!request.getStatus().equals("O") && !request.getStatus().equals("C")) {
            throw new RuntimeException("Cannot update request that is already approved or closed");
        }

        // Validate and update mentor if changed
        if (dto.getMentorId() != null && !dto.getMentorId().equals(request.getMentor().getMentorId())) {
            MentorDetails mentor = mentorDetailsRepository.findById(dto.getMentorId()).orElseThrow(() -> new RuntimeException("Mentor not found"));
            request.setMentor(mentor);
        }

        // Validate and update skill if changed
        if (dto.getSkillId() != null && !dto.getSkillId().equals(request.getSkill().getSkillId())) {
            Skills skill = skillsRepository.findById(dto.getSkillId()).orElseThrow(() -> new RuntimeException("Skill not found"));
            request.setSkill(skill);
        }

        // Update other fields
        if (dto.getDeadline() != null) request.setDeadline(dto.getDeadline());
        if (dto.getTitle() != null) request.setTitle(dto.getTitle());
        if (dto.getReqContent() != null) request.setReqContent(dto.getReqContent());

        Request updatedRequest = requestRepository.save(request);
        return convertToRequestResponse(updatedRequest);
    }

    @Transactional
    public void deleteRequest(String menteeId, String requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        // Check if mentee owns this request
        if (!request.getMentee().getUserId().equals(menteeId)) {
            throw new RuntimeException("You don't have permission to delete this request");
        }

        // Check if request can be deleted (only Open requests)
        if (!request.getStatus().equals("O")) {
            throw new RuntimeException("Cannot delete request that is not in Open status");
        }

        requestRepository.delete(request);
    }

    public Page<RequestResponse> getAllRequests(String menteeId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Request> requestPage = requestRepository.findByMentee_UserId(menteeId, pageable);

        return requestPage.map(this::convertToRequestResponse);
    }

    public Page<RequestResponse> getRequestsByStatus(String menteeId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("openedTime").descending());
        Page<Request> requestPage = requestRepository.findByMentee_UserIdAndStatus(menteeId, status, pageable);

        return requestPage.map(this::convertToRequestResponse);
    }

    public RequestResponse getRequestById(String menteeId, String requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        // Check if mentee owns this request
        if (!request.getMentee().getUserId().equals(menteeId)) {
            throw new RuntimeException("You don't have permission to view this request");
        }

        return convertToRequestResponse(request);
    }

    public RequestStatisticResponse getRequestStatistics(String menteeId) {
        int totalRequests = requestRepository.countByMentee_UserId(menteeId);
        int openRequests = requestRepository.countByMentee_UserIdAndStatus(menteeId, "O");
        int approvedRequests = requestRepository.countByMentee_UserIdAndStatus(menteeId, "A");
        int canceledRequests = requestRepository.countByMentee_UserIdAndStatus(menteeId, "C");
        int closedRequests = requestRepository.countByMentee_UserIdAndStatus(menteeId, "F");

        return new RequestStatisticResponse(totalRequests, openRequests, approvedRequests, canceledRequests, closedRequests);
    }

    public Page<MentorSearchResponse> searchMentors(MentorSearchDto searchDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MentorDetails> mentorPage = mentorDetailsRepository.searchMentors(searchDto.getProfession(), searchDto.getSkillId(), searchDto.getLanguage(), searchDto.getMinRate(), searchDto.getMinYearsExperience(), pageable);

        return mentorPage.map(this::convertToMentorSearchResponse);
    }

    private RequestResponse convertToRequestResponse(Request request) {
        RequestResponse response = new RequestResponse();
        response.setRequestId(request.getRequestId());

        // Safe access to mentee information
        if (request.getMentee() != null) {
            response.setMenteeId(request.getMentee().getUserId());
            response.setMenteeName(request.getMentee().getFullname());
        }

        // Safe access to mentor information
        if (request.getMentor() != null) {
            response.setMentorId(request.getMentor().getMentorId());
            if (request.getMentor().getMentor() != null) {
                response.setMentorName(request.getMentor().getMentor().getFullname());
            }
        }

        // Safe access to skill information
        if (request.getSkill() != null) {
            response.setSkillId(request.getSkill().getSkillId());
            response.setSkillName(request.getSkill().getSkillName());
        }

        response.setDeadline(request.getDeadline());
        response.setTitle(request.getTitle());
        response.setReqContent(request.getReqContent());
        response.setStatus(request.getStatus());
        response.setOpenedTime(request.getOpenedTime());
        response.setApprovedTime(request.getApprovedTime());
        response.setCanceledTime(request.getCanceledTime());
        response.setClosedTime(request.getClosedTime());
        return response;
    }

    private MentorSearchResponse convertToMentorSearchResponse(MentorDetails mentor) {
        MentorSearchResponse response = new MentorSearchResponse();
        response.setMentorId(mentor.getMentorId());
        response.setFullname(mentor.getMentor().getFullname());
        response.setProfession(mentor.getProfession());
        response.setLanguage(mentor.getLanguage());
        response.setIntroduction(mentor.getIntroduction());
        response.setServiceDescription(mentor.getServiceDescription());
        response.setAchievementDescription(mentor.getAchievementDescription());
        response.setFacebook(mentor.getFacebook());
        response.setGithub(mentor.getGithub());

        // Convert skills
        if (mentor.getMentorSkills() != null) {
            List<MentorSearchResponse.MentorSkillInfo> skills = mentor.getMentorSkills().stream().map(ms -> new MentorSearchResponse.MentorSkillInfo(ms.getSkill().getSkillId(), ms.getSkill().getSkillName(), ms.getYearsExperience(), ms.getRate())).collect(Collectors.toList());
            response.setSkills(skills);
        }

        // Get statistics from user's statistic
        if (mentor.getMentor().getStatistic() != null) {
            response.setAvgRate(mentor.getMentor().getStatistic().getAvgRate());
            response.setFollowerCount(mentor.getMentor().getStatistic().getFollowerCount());
        }

        return response;
    }
}
