package com.np.optimagrowth.license;

import com.np.optimagrowth.license.config.ServiceConfig;
import com.np.optimagrowth.license.model.License;
import com.np.optimagrowth.license.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@RefreshScope
public class LicensingServiceApplication implements ApplicationRunner {
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        var license = License.builder()
                .licenseId(UUID.randomUUID().toString())
                .description("Software Product")
                .organizationId("optimaGrowth")
                .productName("Ostock")
                .licenseType("full")
                .comment(serviceConfig.getProperty())
                .build();
        licenseRepository.save(license);
    }
}
