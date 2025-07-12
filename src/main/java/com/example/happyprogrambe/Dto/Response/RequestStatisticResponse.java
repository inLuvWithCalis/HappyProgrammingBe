package com.example.happyprogrambe.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatisticResponse {
    private int totalRequests;
    private int openRequests;
    private int approvedRequests;
    private int canceledRequests;
    private int closedRequests;
}
