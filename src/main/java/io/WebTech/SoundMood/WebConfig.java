package io.WebTech.SoundMood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:5175", "https://frontend-soundmood.onrender.com")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowCredentials(true);
    }
}
