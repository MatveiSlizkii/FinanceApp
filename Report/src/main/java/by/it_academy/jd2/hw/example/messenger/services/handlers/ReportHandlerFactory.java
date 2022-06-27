package by.it_academy.jd2.hw.example.messenger.services.handlers;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.services.api.IDataReport;
import by.it_academy.jd2.hw.example.messenger.services.handlers.api.IReportHandler;
import org.springframework.stereotype.Service;

@Service
public class ReportHandlerFactory {
    private final IDataReport reportHandler;

    public ReportHandlerFactory(IDataReport reportHandler) {
        this.reportHandler = reportHandler;
    }

    public IReportHandler handler(ReportType reportType) {
        switch (reportType) {
            case BALANCE:
                return new BalanceReportHandler(reportHandler);
            case BY_DATE:
                return new ByDateReportHandler(reportHandler);
            case BY_CATEGORY:
                return new ByCategoryReportHandler(reportHandler);
            default: throw new IllegalStateException("Нет реализации");
        }
    }
}
