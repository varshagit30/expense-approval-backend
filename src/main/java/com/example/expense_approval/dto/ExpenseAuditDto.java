package com.example.expense_approval.dto;

import com.example.expense_approval.enums.ExpenseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseAuditDto {
    private ExpenseStatus fromStatus;
    private ExpenseStatus toStatus;
    private Long actionBy;
    private String remarks;
    private LocalDateTime actionOn;

}
