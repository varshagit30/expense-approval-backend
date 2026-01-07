package com.example.expense_approval.entity;

import com.example.expense_approval.enums.ExpenseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpenseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Long createdBy;
    private LocalDateTime createdOn;

    private Long lastUpdatedBy;
    private LocalDateTime lastUpdatedOn;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus currentStatus;
}
