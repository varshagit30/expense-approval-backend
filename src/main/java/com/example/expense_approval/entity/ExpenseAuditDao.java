package com.example.expense_approval.entity;

import com.example.expense_approval.enums.ExpenseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expense_audit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpenseAuditDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long expenseId;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus toStatus;

    private Long actionBy;
    private String remarks;
    private LocalDateTime actionOn;


}
