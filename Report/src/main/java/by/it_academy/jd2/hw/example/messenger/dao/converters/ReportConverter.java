package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entities.ReportEntity;
import by.it_academy.jd2.hw.example.messenger.model.Report;
import by.it_academy.jd2.hw.example.messenger.model.api.StatusType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ReportConverter implements Converter<ReportEntity, Report> {
    @Override
    public Report convert(ReportEntity source) {

        return Report.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setStatus(StatusType.valueOf(source.getStatus()))
                .setType(source.getType())
                .setDescription(source.getDescription())
                .setParams(source.getParams())
                .setExcelReport(source.getExcelReport())
                .setUser(source.getUser())
                .build();
    }

    @Override
    public <U> Converter<ReportEntity, U> andThen(Converter<? super Report, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
