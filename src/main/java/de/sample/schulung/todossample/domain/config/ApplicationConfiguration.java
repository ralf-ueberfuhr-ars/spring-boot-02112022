package de.sample.schulung.todossample.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public @Data class ApplicationConfiguration {

    private boolean initializeSampleDataOnStartup;

}
