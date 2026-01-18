package com.example.expense_approval.service.sericeImpl;

import com.example.expense_approval.dto.ExpenseAuditDto;
import com.example.expense_approval.dto.ExpenseDto;
import com.example.expense_approval.entity.ExpenseAuditDao;
import com.example.expense_approval.entity.ExpenseDao;
import com.example.expense_approval.enums.ExpenseStatus;
import com.example.expense_approval.mapper.ExpenseMapper;
import com.example.expense_approval.repository.ExpenseAuditRepository;
import com.example.expense_approval.repository.ExpenseRepository;
import com.example.expense_approval.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseAuditRepository auditRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository
                .findAllByOrderByCreatedOnDesc()
                .stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    // ---------------- CREATE DRAFT ----------------
    @Override
    public ExpenseDto createDraft(ExpenseDto dto) {
        ExpenseDao expense = new ExpenseDao();
        expense.setTitle(dto.getTitle());
        expense.setDescription(dto.getDescription());
        expense.setCreatedBy(dto.getCreatedBy());
        expense.setCreatedOn(LocalDateTime.now());
        expense.setCurrentStatus(ExpenseStatus.DRAFT);

        ExpenseDao saved = expenseRepository.save(expense);

        saveAudit(saved.getId(), null, ExpenseStatus.DRAFT,
                dto.getCreatedBy(), "Draft created");

        return expenseMapper.toDto(saved);
    }

    // ---------------- SUBMIT ----------------
    @Override
    public ExpenseDto submit(Long expenseId, Long userId) {
        ExpenseDao expense = getExpense(expenseId);

        ExpenseStatus prevStatus = expense.getCurrentStatus();
        validateStatus(prevStatus, ExpenseStatus.DRAFT);

        expense.setCurrentStatus(ExpenseStatus.SUBMITTED);
        expense.setLastUpdatedBy(userId);
        expense.setLastUpdatedOn(LocalDateTime.now());

        expenseRepository.save(expense);

        saveAudit(expenseId, prevStatus, ExpenseStatus.SUBMITTED,
                userId, "Submitted");

        return expenseMapper.toDto(expense);
    }

    // ---------------- MANAGER APPROVAL ----------------
    @Override
    public ExpenseDto approveByManager(Long expenseId, Long managerId) {
        ExpenseDao expense = getExpense(expenseId);

        validateStatus(expense.getCurrentStatus(), ExpenseStatus.SUBMITTED);

        expense.setCurrentStatus(ExpenseStatus.MANAGER_APPROVED);
        expense.setLastUpdatedBy(managerId);
        expense.setLastUpdatedOn(LocalDateTime.now());

        expenseRepository.save(expense);

        saveAudit(expenseId, ExpenseStatus.SUBMITTED,
                ExpenseStatus.MANAGER_APPROVED,
                managerId, "Approved by Manager");

        return expenseMapper.toDto(expense);
    }

    // ---------------- FINANCE APPROVAL ----------------
    @Override
    public ExpenseDto approveByFinance(Long expenseId, Long financeId) {
        ExpenseDao expense = getExpense(expenseId);

        validateStatus(expense.getCurrentStatus(), ExpenseStatus.MANAGER_APPROVED);

        expense.setCurrentStatus(ExpenseStatus.FINANCE_APPROVED);
        expense.setLastUpdatedBy(financeId);
        expense.setLastUpdatedOn(LocalDateTime.now());

        expenseRepository.save(expense);

        saveAudit(expenseId, ExpenseStatus.MANAGER_APPROVED,
                ExpenseStatus.FINANCE_APPROVED,
                financeId, "Approved by Finance");

        return expenseMapper.toDto(expense);
    }

    // ---------------- MANAGER REJECT ----------------
    @Override
    public ExpenseDto rejectByManager(Long expenseId, Long managerId, String remarks) {
        ExpenseDao expense = getExpense(expenseId);

        validateStatus(expense.getCurrentStatus(), ExpenseStatus.SUBMITTED);

        expense.setCurrentStatus(ExpenseStatus.REJECTED_BY_MANAGER);
        expense.setLastUpdatedBy(managerId);
        expense.setLastUpdatedOn(LocalDateTime.now());

        expenseRepository.save(expense);

        saveAudit(expenseId, ExpenseStatus.SUBMITTED,
                ExpenseStatus.REJECTED_BY_MANAGER,
                managerId, remarks);

        return expenseMapper.toDto(expense);
    }

    // ---------------- FINANCE REJECT ----------------
    @Override
    public ExpenseDto rejectByFinance(Long expenseId, Long financeId, String remarks) {
        ExpenseDao expense = getExpense(expenseId);

        validateStatus(expense.getCurrentStatus(), ExpenseStatus.MANAGER_APPROVED);

        expense.setCurrentStatus(ExpenseStatus.REJECTED_BY_FINANCE);
        expense.setLastUpdatedBy(financeId);
        expense.setLastUpdatedOn(LocalDateTime.now());

        expenseRepository.save(expense);

        saveAudit(expenseId, ExpenseStatus.MANAGER_APPROVED,
                ExpenseStatus.REJECTED_BY_FINANCE,
                financeId, remarks);

        return expenseMapper.toDto(expense);
    }

    // ---------------- AUDIT HISTORY ----------------
    @Override
    public List<ExpenseAuditDto> getAuditHistory(Long expenseId) {
        return auditRepository
                .findByExpenseIdOrderByActionOnDesc(expenseId)
                .stream()
                .map(expenseMapper::toAuditDto)
                .toList();
    }

    // ---------------- HELPERS ----------------
    private ExpenseDao getExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    private void validateStatus(ExpenseStatus actual, ExpenseStatus expected) {
        if (actual != expected) {
            throw new RuntimeException("Invalid state transition");
        }
    }

    private void saveAudit(Long expenseId,
                           ExpenseStatus from,
                           ExpenseStatus to,
                           Long userId,
                           String remarks) {
        ExpenseAuditDao audit = new ExpenseAuditDao();
        audit.setExpenseId(expenseId);
        audit.setFromStatus(from);
        audit.setToStatus(to);
        audit.setActionBy(userId);
        audit.setRemarks(remarks);
        audit.setActionOn(LocalDateTime.now());
        auditRepository.save(audit);
    }
}

