package org.lms.collegeservice.FeignClient.Config;

import feign.codec.ErrorDecoder;

import org.lms.collegeservice.FeignClient.ExceptionHandler.CustomFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}

