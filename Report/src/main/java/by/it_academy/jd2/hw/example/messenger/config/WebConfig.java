package by.it_academy.jd2.hw.example.messenger.config;


import by.it_academy.jd2.hw.example.messenger.dao.converters.LongToLocalDateTimeConverter;
import by.it_academy.jd2.hw.example.messenger.dao.converters.ReportConverter;
import by.it_academy.jd2.hw.example.messenger.dao.converters.ReportEntityConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ReportConverter());
        registry.addConverter(new ReportEntityConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());
    }
}
