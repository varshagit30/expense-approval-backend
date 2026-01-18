package com.example.expense_approval.service;

import com.example.expense_approval.dto.ExpenseAuditDto;
import com.example.expense_approval.dto.ExpenseDto;

import java.util.List;

public interface ExpenseService {
    ExpenseDto createDraft(ExpenseDto expenseDto);

    ExpenseDto submit(Long expenseId, Long userId);

    ExpenseDto approveByManager(Long expenseId, Long managerId);

    ExpenseDto approveByFinance(Long expenseId, Long financeId);

    ExpenseDto rejectByManager(Long expenseId, Long managerId, String remarks);

    ExpenseDto rejectByFinance(Long expenseId, Long financeId, String remarks);

    List<ExpenseAuditDto> getAuditHistory(Long expenseId);

    List<ExpenseDto> getAllExpenses();
}
