package ge.edu.cu.l_lomidze2.sabakalavro.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    Map<String, Integer> cache() {
        return new ConcurrentHashMap<>();
    }
}
