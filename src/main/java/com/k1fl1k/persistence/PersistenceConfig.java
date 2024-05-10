package com.k1fl1k.persistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.k1fl1k.persistence")
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

}
