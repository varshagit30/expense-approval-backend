package com.example.expense_approval.repository;

import com.example.expense_approval.entity.ExpenseAuditDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseAuditRepository extends JpaRepository<ExpenseAuditDao, Long> {

    // Get audit trail for a specific expense
    List<ExpenseAuditDao> findByExpenseIdOrderByActionOnDesc(Long expenseId);

    // Optional: get audits done by a user
    List<ExpenseAuditDao> findByActionBy(Long actionBy);

    // Optional: latest audit for an expense
    ExpenseAuditDao findTopByExpenseIdOrderByActionOnDesc(Long expenseId);
}