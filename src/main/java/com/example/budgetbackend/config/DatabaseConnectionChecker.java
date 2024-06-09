package com.example.budgetbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Component
public class DatabaseConnectionChecker implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionChecker.class);

    private final DataSource dataSource;

    @Autowired
    public DatabaseConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                String dbName = metaData.getDatabaseProductName();
                String dbVersion = metaData.getDatabaseProductVersion();
                String dbUrl = metaData.getURL();
                String dbUser = metaData.getUserName();

                logger.info("Database connection established successfully!");
                logger.info("Connected to database: {} (version: {})", dbName, dbVersion);
                logger.info("Database URL: {}", dbUrl);
                logger.info("Database user: {}", dbUser);
            } else {
                logger.error("Failed to establish database connection: Connection is null");
            }
        } catch (SQLException e) {
            logger.error("Failed to establish database connection", e);
        }
    }

}
