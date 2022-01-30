package net.j7.ebook.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class JacksonConfiguration extends WebMvcConfigurationSupport {

    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(myConverter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter myConverter() {
        return new MappingJackson2HttpMessageConverter(customObjectMapper());
    }

    @Bean
    public ObjectMapper customObjectMapper() {
        return new ObjectMapper().registerModule(blackbirdModule());
    }

    @Bean
    public BlackbirdModule blackbirdModule() {
        return new BlackbirdModule();
    }

}
