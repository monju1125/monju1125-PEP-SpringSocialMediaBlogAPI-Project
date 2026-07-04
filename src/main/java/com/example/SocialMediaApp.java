package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a class that is used to run your application.
 *
 * DO NOT CHANGE ANYTHING IN THIS CLASS
 *
 */
@SpringBootApplication
public class SocialMediaApp {
    /**
     * Runs the application
     * 
     * @param args The arguments of the program.
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SocialMediaApp.class, args);
    }
}
