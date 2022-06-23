package by.it_academy.jd2.hw.example.messenger.config;


import by.it_academy.jd2.hw.example.messenger.model.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ScheduledOperationConverter());
        registry.addConverter(new ScheduledOperationEntityConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());
        registry.addConverter(new LocalDateTimeToDateConverter());
        registry.addConverter(new OperationRequestConverter());

    }
}
