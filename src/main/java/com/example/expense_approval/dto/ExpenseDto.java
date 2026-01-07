package com.example.expense_approval.dto;

import com.example.expense_approval.enums.ExpenseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto {
    private Long id;
    private String title;
    private String description;
    private ExpenseStatus currentStatus;
    private Long createdBy;
}
