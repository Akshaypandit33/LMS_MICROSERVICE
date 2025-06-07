package org.lms.academicstructureservice.Feign;

import feign.codec.ErrorDecoder;
import org.lms.commonmodule.FeignException.CustomFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
