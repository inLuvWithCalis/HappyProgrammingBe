package com.example.happyprogrambe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class HappyProgramBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HappyProgramBeApplication.class, args);
    }
}
