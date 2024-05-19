package com.yanqun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class JwstWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwstWebManagementApplication.class, args);
    }

}
