package com.tseste.personal_finance_analytics.database;

import com.tseste.personal_finance_analytics.Utils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseImporter {
    private static final String INSERT_STATEMENT = "INSERT INTO expenses "
        + "(date, amount, category, description) "
        + "VALUES (?, ?, ?, ?);";

    public static void importFromCSV(String filePath) {
        Logger logger = Logger.getLogger(ExpenseImporter.class.getName());
        logger.setLevel(Level.INFO);
        try (
            Connection conn = DatabaseManager.connect();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STATEMENT)
        ) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 4) continue;

                preparedStatement.setString(1, values[0]);
                preparedStatement.setDouble(2, Double.parseDouble(values[1]));
                preparedStatement.setString(3, values[2]);
                preparedStatement.setString(4, values[3]);
                preparedStatement.executeUpdate();
            }
            logger.info("added row: " + preparedStatement.toString());
        } catch (IOException | SQLException e) {
            logger.severe(Utils.ExceptionToString(e));
        }
    }
}
