package org.backend.digitalbankingbackendimpl.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {

    private String AccountId;
    private double Balance;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    private List<AccountOperationDto> accountOperationDtos;
}
