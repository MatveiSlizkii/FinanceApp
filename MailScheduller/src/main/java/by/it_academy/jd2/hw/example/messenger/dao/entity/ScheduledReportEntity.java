package by.it_academy.jd2.hw.example.messenger.dao.entity;

import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "sheduled_report", schema = "app")
public class ScheduledReportEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "from1")
    private LocalDateTime from;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "stop_time")
    private LocalDateTime stopTime;
    @Column(name = "interval")
    private long interval;

    @Column(name = "time")
    private TimeUnitEnum time;

    @Column(name = "accounts")
    private UUID[] accounts;
    @Column(name = "to1")
    private LocalDateTime to;

    @Column(name = "report_type")
    private String reportType;
    @Column(name = "user_login")
    private String login;

    public ScheduledReportEntity() {
    }

    public ScheduledReportEntity(UUID uuid, LocalDateTime dtCreate,
                                 LocalDateTime dtUpdate, LocalDateTime startTime,
                                 LocalDateTime stopTime, long interval,
                                 TimeUnitEnum time, UUID[] accounts, LocalDateTime to,
                                 LocalDateTime from, String reportType, String login) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.time = time;
        this.accounts = accounts;
        this.to = to;
        this.from = from;
        this.reportType = reportType;
        this.login = login;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public TimeUnitEnum getTime() {
        return time;
    }

    public void setTime(TimeUnitEnum time) {
        this.time = time;
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private LocalDateTime startTime;
        private LocalDateTime stopTime;
        private long interval;

        private TimeUnitEnum time;

        private UUID[] accounts;
        private LocalDateTime to;
        private LocalDateTime from;
        private String reportType;

        private String login;

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

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setStopTime(LocalDateTime stopTime) {
            this.stopTime = stopTime;
            return this;
        }

        public Builder setInterval(long interval) {
            this.interval = interval;
            return this;
        }


        public Builder setTime(TimeUnitEnum time) {
            this.time = time;
            return this;
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

        public Builder setReportType(String reportType) {
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

        public ScheduledReportEntity build() {
            return new ScheduledReportEntity(uuid, dtCreate, dtUpdate, startTime, stopTime, interval,
                    time, accounts, to, from, reportType, login);
        }
    }
}