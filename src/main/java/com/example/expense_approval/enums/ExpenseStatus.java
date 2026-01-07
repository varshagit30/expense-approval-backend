package com.example.expense_approval.enums;

public enum ExpenseStatus {
    DRAFT,

    SUBMITTED,
    IN_REVIEW,

    MANAGER_APPROVED,
    FINANCE_APPROVED,

    PAID,

    REJECTED_BY_MANAGER,
    REJECTED_BY_FINANCE,

    CANCELLED
}
