package by.it_academy.jd2.hw.example.messenger.config;


import by.it_academy.jd2.hw.example.messenger.model.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new LongToLocalDateTimeConverter());
        registry.addConverter(new LocalDateTimeToDateConverter());
        registry.addConverter(new ScheduledReportConverter());
        registry.addConverter(new ScheduledReportEntityConverter());
        registry.addConverter(new LocalDateTimeToLongConverter());
        registry.addConverter(new ReportRequestConverter());

    }
}
