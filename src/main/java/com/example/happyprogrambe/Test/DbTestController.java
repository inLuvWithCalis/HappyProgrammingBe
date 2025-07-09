package com.example.happyprogrambe.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class DbTestController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/db-connection")
    public Map<String, Object> testConnection() {
        Map<String, Object> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            response.put("connection", "Success");
            response.put("databaseProduct", connection.getMetaData().getDatabaseProductName());
            response.put("databaseVersion", connection.getMetaData().getDatabaseProductVersion());

            // Test a simple query
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("queryResult", result);

        } catch (Exception e) {
            response.put("connection", "Failed");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
