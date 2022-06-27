package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Report {
    @JsonProperty("accounts")
    private UUID[] accounts;
    @JsonProperty("to")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime to;
    @JsonProperty("from")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime from;
    @JsonIgnore
    @JsonProperty("report_type")
    private ReportType reportType;

    private String login;

    public Report() {
    }

    public Report(UUID[] accounts, LocalDateTime to, LocalDateTime from, ReportType reportType, String login) {
        this.accounts = accounts;
        this.to = to;
        this.from = from;
        this.reportType = reportType;
        this.login = login;
    }

    public UUID[] getAccounts() {
        return accounts;
    }

    public void setAccounts(UUID[] accounts) {
        this.accounts = accounts;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public static class Builder {
        private UUID[] accounts;
        private LocalDateTime to;
        private LocalDateTime from;
        private ReportType reportType;
        private String login;

        private Builder() {
        }

        public Builder setAccounts(UUID[] accounts) {
            this.accounts = accounts;
            return this;
        }

        public Builder setTo(LocalDateTime to) {
            this.to = to;
            return this;
        }

        public Builder setFrom(LocalDateTime from) {
            this.from = from;
            return this;
        }

        public Builder setReportType(ReportType reportType) {
            this.reportType = reportType;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Report build() {
            return new Report(accounts, to, from, reportType, login);
        }
    }
}
