package com.example.happyprogrambe.Controller.Mentee;

import com.example.happyprogrambe.Dto.Request.CreateRequestDto;
import com.example.happyprogrambe.Dto.Request.MentorSearchDto;
import com.example.happyprogrambe.Dto.Request.UpdateRequestDto;
import com.example.happyprogrambe.Dto.Response.*;
import com.example.happyprogrambe.Service.MenteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mentee")
@RequiredArgsConstructor
public class MenteeController {

    private final MenteeService menteeService;

    @PostMapping("/{menteeId}/requests")
    public ResponseEntity<ApiResponse<RequestResponse>> createRequest(
            @PathVariable String menteeId,
            @RequestBody CreateRequestDto dto) {
        try {
            RequestResponse response = menteeService.createRequest(menteeId, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Request created successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @PutMapping("/{menteeId}/requests/{requestId}")
    public ResponseEntity<ApiResponse<RequestResponse>> updateRequest(
            @PathVariable String menteeId,
            @PathVariable String requestId,
            @RequestBody UpdateRequestDto dto) {
        try {
            RequestResponse response = menteeService.updateRequest(menteeId, requestId, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Request updated successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{menteeId}/requests/{requestId}")
    public ResponseEntity<ApiResponse<String>> deleteRequest(
            @PathVariable String menteeId,
            @PathVariable String requestId) {
        try {
            menteeService.deleteRequest(menteeId, requestId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Request deleted successfully", "Deleted"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/{menteeId}/requests")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllRequests(
            @PathVariable String menteeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "openedTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Page<RequestResponse> requestPage = menteeService.getAllRequests(menteeId, page, size, sortBy, sortDir);

            // Convert Page to Map to avoid serialization issues
            Map<String, Object> response = new HashMap<>();
            response.put("content", requestPage.getContent());
            response.put("totalElements", requestPage.getTotalElements());
            response.put("totalPages", requestPage.getTotalPages());
            response.put("size", requestPage.getSize());
            response.put("number", requestPage.getNumber());
            response.put("first", requestPage.isFirst());
            response.put("last", requestPage.isLast());
            response.put("empty", requestPage.isEmpty());

            Map<String, Object> pageable = new HashMap<>();
            pageable.put("pageNumber", requestPage.getPageable().getPageNumber());
            pageable.put("pageSize", requestPage.getPageable().getPageSize());
            pageable.put("offset", requestPage.getPageable().getOffset());
            pageable.put("paged", requestPage.getPageable().isPaged());
            pageable.put("unpaged", requestPage.getPageable().isUnpaged());
            response.put("pageable", pageable);

            Map<String, Object> sort = new HashMap<>();
            sort.put("empty", requestPage.getSort().isEmpty());
            sort.put("sorted", requestPage.getSort().isSorted());
            sort.put("unsorted", requestPage.getSort().isUnsorted());
            response.put("sort", sort);

            return ResponseEntity.ok(new ApiResponse<>(200, "Requests retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/{menteeId}/requests/filter")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRequestsByStatus(
            @PathVariable String menteeId,
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<RequestResponse> requestPage = menteeService.getRequestsByStatus(menteeId, status, page, size);

            // Convert Page to Map to avoid serialization issues
            Map<String, Object> response = new HashMap<>();
            response.put("content", requestPage.getContent());
            response.put("totalElements", requestPage.getTotalElements());
            response.put("totalPages", requestPage.getTotalPages());
            response.put("size", requestPage.getSize());
            response.put("number", requestPage.getNumber());
            response.put("first", requestPage.isFirst());
            response.put("last", requestPage.isLast());
            response.put("empty", requestPage.isEmpty());

            return ResponseEntity.ok(new ApiResponse<>(200, "Filtered requests retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/{menteeId}/requests/{requestId}")
    public ResponseEntity<ApiResponse<RequestResponse>> getRequestById(
            @PathVariable String menteeId,
            @PathVariable String requestId) {
        try {
            RequestResponse response = menteeService.getRequestById(menteeId, requestId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Request retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/{menteeId}/requests/statistics")
    public ResponseEntity<ApiResponse<RequestStatisticResponse>> getRequestStatistics(
            @PathVariable String menteeId) {
        try {
            RequestStatisticResponse response = menteeService.getRequestStatistics(menteeId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Statistics retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @PostMapping("/mentors/search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> searchMentors(
            @RequestBody MentorSearchDto searchDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<MentorSearchResponse> mentorPage = menteeService.searchMentors(searchDto, page, size);

            // Convert Page to Map to avoid serialization issues
            Map<String, Object> response = new HashMap<>();
            response.put("content", mentorPage.getContent());
            response.put("totalElements", mentorPage.getTotalElements());
            response.put("totalPages", mentorPage.getTotalPages());
            response.put("size", mentorPage.getSize());
            response.put("number", mentorPage.getNumber());
            response.put("first", mentorPage.isFirst());
            response.put("last", mentorPage.isLast());
            response.put("empty", mentorPage.isEmpty());

            return ResponseEntity.ok(new ApiResponse<>(200, "Mentors retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }
}
