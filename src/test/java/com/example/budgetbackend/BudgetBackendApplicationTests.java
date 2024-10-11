package com.example.budgetbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = BudgetBackendApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BudgetBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
