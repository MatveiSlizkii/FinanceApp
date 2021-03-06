package by.it_academy.jd2.hw.example.messenger.model;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.api.StatusType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Report {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private StatusType status;
    private ReportType type;
    private String description;
    private String params;
    private String excelReport;
    private String user;

    private Report() {
    }

    public Report(UUID uuid, LocalDateTime dtCreate,
                  LocalDateTime dtUpdate, StatusType status,
                  ReportType type, String description,
                  String params, String excelReport, String user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
        this.excelReport = excelReport;
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExcelReport() {
        return excelReport;
    }

    public void setExcelReport(String excelReport) {
        this.excelReport = excelReport;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private StatusType status;
        private ReportType type;
        private String description; //???? ?????????????????????? ???? ??????????????
        private String params;
        private String excelReport;
        private String user;

        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public Builder setStatus(StatusType status) {
            this.status = status;
            return this;
        }

        public Builder setType(ReportType type) {
            this.type = type;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setParams(String params) {
            this.params = params;
            return this;
        }

        public Builder setExcelReport(String excelReport) {
            this.excelReport = excelReport;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Report build() {
            return new Report(uuid, dtCreate, dtUpdate, status, type, description, params, excelReport, user);
        }
    }
}