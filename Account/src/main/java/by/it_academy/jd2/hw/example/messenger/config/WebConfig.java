package by.it_academy.jd2.hw.example.messenger.config;

import by.it_academy.jd2.hw.example.messenger.dao.api.IBalanceStorage;
import by.it_academy.jd2.hw.example.messenger.dao.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private  final IBalanceStorage balanceStorage;

    public WebConfig(IBalanceStorage balanceStorage) {
        this.balanceStorage = balanceStorage;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AccountConverter());
        registry.addConverter(new AccountEntityConverter(balanceStorage));
        registry.addConverter(new OperationConverter());
        registry.addConverter(new OperationEntityConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());

    }
}
