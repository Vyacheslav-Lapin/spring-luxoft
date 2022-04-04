package ru.vlapin.trainings.springluxoft;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(includeFilters = @Filter(Aspect.class))
public class AspectConfig {
}
