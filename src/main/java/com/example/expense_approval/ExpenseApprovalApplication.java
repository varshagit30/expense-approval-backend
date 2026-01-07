package com.example.expense_approval;

import com.example.expense_approval.entity.UserDao;
import com.example.expense_approval.enums.UserRole;
import com.example.expense_approval.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseApprovalApplication.class, args);
	}

    @Bean
    CommandLineRunner init(UserRepository repo){
        return args -> {

            repo.save(UserDao.builder()
                    .username("emp")
                    .password("123")
                    .role(UserRole.EMPLOYEE)
                    .build());

            repo.save(UserDao.builder()
                    .username("manager")
                    .password("123")
                    .role(UserRole.MANAGER)
                    .build());

            repo.save(UserDao.builder()
                    .username("finance")
                    .password("123")
                    .role(UserRole.FINANCE)
                    .build());
        };
    }


}
