package com.carDealer.config;

import com.carDealer.utils.FileIOUtil;
import com.carDealer.utils.FileIOUtilImpl;
import com.carDealer.utils.ValidationUtil;
import com.carDealer.utils.ValidationUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
