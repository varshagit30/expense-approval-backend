package com.example.expense_approval.controller;

import com.example.expense_approval.dto.ExpenseAuditDto;
import com.example.expense_approval.dto.ExpenseDto;
import com.example.expense_approval.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;

    // LIST OF EXPENSES //
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // ---------- CREATE DRAFT ----------
    @PostMapping
    public ResponseEntity<ExpenseDto> createDraft(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto created = expenseService.createDraft(expenseDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ---------- SUBMIT ----------
    @PostMapping("/{id}/submit")
    public ResponseEntity<ExpenseDto> submit(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        ExpenseDto updated = expenseService.submit(id, userId);
        return ResponseEntity.ok(updated);
    }

    // ---------- MANAGER APPROVES ----------
    @PostMapping("/{id}/approve/manager")
    public ResponseEntity<ExpenseDto> approveByManager(
            @PathVariable Long id,
            @RequestParam Long managerId
    ) {
        ExpenseDto updated = expenseService.approveByManager(id, managerId);
        return ResponseEntity.ok(updated);
    }

    // ---------- FINANCE APPROVES ----------
    @PostMapping("/{id}/approve/finance")
    public ResponseEntity<ExpenseDto> approveByFinance(
            @PathVariable Long id,
            @RequestParam Long financeId
    ) {
        ExpenseDto updated = expenseService.approveByFinance(id, financeId);
        return ResponseEntity.ok(updated);
    }

    // ---------- MANAGER REJECT ----------
    @PostMapping("/{id}/reject/manager")
    public ResponseEntity<ExpenseDto> rejectByManager(
            @PathVariable Long id,
            @RequestParam Long managerId,
            @RequestParam String remarks
    ) {
        ExpenseDto updated = expenseService.rejectByManager(id, managerId, remarks);
        return ResponseEntity.ok(updated);
    }

    // ---------- FINANCE REJECT ----------
    @PostMapping("/{id}/reject/finance")
    public ResponseEntity<ExpenseDto> rejectByFinance(
            @PathVariable Long id,
            @RequestParam Long financeId,
            @RequestParam String remarks
    ) {
        ExpenseDto updated = expenseService.rejectByFinance(id, financeId, remarks);
        return ResponseEntity.ok(updated);
    }

    // ---------- AUDIT HISTORY ----------
    @GetMapping("/{id}/audit")
    public ResponseEntity<List<ExpenseAuditDto>> getAuditHistory(
            @PathVariable Long id
    ) {
        List<ExpenseAuditDto> audits = expenseService.getAuditHistory(id);
        return ResponseEntity.ok(audits);
    }
}
