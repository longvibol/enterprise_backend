package com.pisethjavaschool.accesscontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
		"com.pisethjavaschool.accesscontrol",
		"com.pisethjavaschool.platform.exception",
})
@ConfigurationPropertiesScan
public class AccessControlServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessControlServiceApplication.class, args);
    }
}
