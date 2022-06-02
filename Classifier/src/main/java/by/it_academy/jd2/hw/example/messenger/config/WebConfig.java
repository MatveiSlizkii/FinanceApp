package by.it_academy.jd2.hw.example.messenger.config;

import by.it_academy.jd2.hw.example.messenger.dao.converters.CurrencyConverter;
import by.it_academy.jd2.hw.example.messenger.dao.converters.CurrencyEntityConverter;
import by.it_academy.jd2.hw.example.messenger.dao.converters.OperationCategoryConverter;
import by.it_academy.jd2.hw.example.messenger.dao.converters.OperationCategoryEntityConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CurrencyConverter());
        registry.addConverter(new CurrencyEntityConverter());
        registry.addConverter(new OperationCategoryConverter());
        registry.addConverter(new OperationCategoryEntityConverter());
    }
}
