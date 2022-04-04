package ru.vlapin.trainings.springluxoft.config;

import ru.vlapin.trainings.springluxoft.model.JavaConfigBasedSetterPropertiesPlaceholderExample;
import ru.vlapin.trainings.springluxoft.model.JavaConfigBasedSetterPropertiesPlaceholderExampleImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationPropertiesScan("ru.vlapin.trainings.springluxoft")
public class PropertyPlaceholder {

  @Bean
  @ConfigurationProperties("my-properties2")
  JavaConfigBasedSetterPropertiesPlaceholderExample mySetterProperties2() {
    return new JavaConfigBasedSetterPropertiesPlaceholderExampleImpl();
  }
}
