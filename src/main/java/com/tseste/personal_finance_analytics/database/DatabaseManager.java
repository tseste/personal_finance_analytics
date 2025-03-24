package com.tseste.personal_finance_analytics.database;

import com.tseste.personal_finance_analytics.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final String URL = "jdbc:sqlite:pfa.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String createExpensesTable = """
            CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL,
                amount REAL NOT NULL,
                category TEXT NOT NULL,
                description TEXT
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createExpensesTable);
            logger.log(Level.INFO, "Database created");
        } catch (SQLException e) {
            logger.severe(Utils.ExceptionToString(e));
        }
    }

    public static List<Object> getFirstExpense() {
        String statement = "SELECT * FROM expenses ORDER BY id ASC LIMIT 1;";
        List<Object> resultList = new ArrayList<Object>();
        try (
                Connection conn = connect();
                PreparedStatement preparedStatement = conn.prepareStatement(statement);
                ResultSet resultSet = preparedStatement.executeQuery()
        ){
            if (resultSet.next()) {
                resultList.add(resultSet.getInt("id"));
                resultList.add(resultSet.getString("date"));
                resultList.add(resultSet.getDouble("amount"));
                resultList.add(resultSet.getString("category"));
                resultList.add(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, Utils.ExceptionToString(e));
        }
        return resultList;
    }
}
