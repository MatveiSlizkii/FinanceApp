package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entities.ReportEntity;
import by.it_academy.jd2.hw.example.messenger.model.Report;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ReportEntityConverter implements Converter<Report, ReportEntity> {
    @Override
    public ReportEntity convert(Report source) {

        return ReportEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setStatus(source.getStatus().name())
                .setType(source.getType())
                .setDescription(source.getDescription())
                .setParams(source.getParams())
                .setExcelReport(source.getExcelReport())
                .setUser(source.getUser())
                .build();
    }

    @Override
    public <U> Converter<Report, U> andThen(Converter<? super ReportEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
