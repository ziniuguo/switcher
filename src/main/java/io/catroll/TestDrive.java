package io.catroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestDrive implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestDrive.class, args);
    }
    @Autowired TestThread testThread;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("running...");
        testThread.start();
    }
}