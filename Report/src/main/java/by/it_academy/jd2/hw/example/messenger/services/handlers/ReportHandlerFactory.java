package by.it_academy.jd2.hw.example.messenger.services.handlers;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.services.handlers.api.IReportHandler;
import org.springframework.stereotype.Service;

@Service
public class ReportHandlerFactory {
    public IReportHandler handler(ReportType reportType) {
        switch (reportType) {
            case BALANCE:
                return new BalanceReportHandler();
            case BY_DATE:
                return new ByDateReportHandler();
            case BY_CATEGORY:
                return new ByCategoryReportHandler();
            default: throw new IllegalStateException("Нет реализации");
        }
    }
}
