package com.k1fl1k.persistence;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for the Spring application context.
 * This class sets up component scanning, property sources, and bean definitions.
 */
@Configuration
@ComponentScan("com.k1fl1k")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    /**
     * Bean definition method for creating a Validator instance.
     * This method creates a Validator using the Jakarta Bean Validation API.
     * @return Validator instance
     */
    @Bean
    Validator validator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
