package by.it_academy.jd2.hw.example.messenger.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "schedule", schema = "app")
public class ScheduleEntity {
    @Id
    private UUID uuid;
    private LocalDateTime dt_create;
    @Version
    private LocalDateTime dt_update;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Long interval;
    private String time_unit;

    public ScheduleEntity() {
    }

    public ScheduleEntity(UUID uuid, LocalDateTime dt_create,
                          LocalDateTime dt_update, LocalDateTime startTime,
                          LocalDateTime stopTime, Long interval, String time_unit) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.time_unit = time_unit;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
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

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public String getTime_unit() {
        return time_unit;
    }

    public void setTime_unit(String time_unit) {
        this.time_unit = time_unit;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", interval=" + interval +
                ", timeUnit='" + time_unit + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dt_create;
        private LocalDateTime dt_update;
        private LocalDateTime startTime;
        private LocalDateTime stopTime;
        private Long interval;
        private String timeUnit;

        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDt_create(LocalDateTime dt_create) {
            this.dt_create = dt_create;
            return this;
        }

        public Builder setDt_update(LocalDateTime dt_update) {
            this.dt_update = dt_update;
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

        public Builder setInterval(Long interval) {
            this.interval = interval;
            return this;
        }

        public Builder setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ScheduleEntity build() {
            return new ScheduleEntity(uuid,dt_create,dt_update, startTime, stopTime, interval, timeUnit);
        }
    }
}
