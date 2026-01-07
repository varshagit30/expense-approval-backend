package com.example.expense_approval.mapper;

import com.example.expense_approval.dto.ExpenseAuditDto;
import com.example.expense_approval.dto.ExpenseDto;
import com.example.expense_approval.entity.ExpenseAuditDao;
import com.example.expense_approval.entity.ExpenseDao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDto toDto(ExpenseDao expense);

    List<ExpenseDto> toDtoList(List<ExpenseDao> expenses);

    // Audit mappings
    ExpenseAuditDto toAuditDto(ExpenseAuditDao audit);

    List<ExpenseAuditDto> toAuditDtoList(List<ExpenseAuditDao> audits);
}
