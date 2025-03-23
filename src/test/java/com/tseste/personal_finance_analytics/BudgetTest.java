package com.tseste.personal_finance_analytics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {
    @Test
    void testShowBudget() {
        Budget budget = new Budget();
        String result = budget.showBudget();
        assertEquals("Budget Details", result,  "Budget output should match expected text");
    }
}
