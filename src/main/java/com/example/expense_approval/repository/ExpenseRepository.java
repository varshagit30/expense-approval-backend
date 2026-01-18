package com.example.expense_approval.repository;

//import com.example.expense_approval.domain.entity.ExpenseAudit;
import com.example.expense_approval.entity.ExpenseDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseDao, Long> {

//    List<ExpenseDao> findByExpenseIdOrderByActionAtAsc(Long expenseId);
    List<ExpenseDao> findAllByOrderByCreatedOnDesc();
}
