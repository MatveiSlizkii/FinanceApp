package by.it_academy.jd2.hw.example.messenger.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scheduled_operation", schema = "app")
public class ScheduledOperationEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dt_create;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dt_update;
    @Column(name = "user_login")
    private String user;
    //scheduler
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Long interval;
    private String time_unit;
    //operation
    private UUID account;
    private String description; //по человечески
    private Long value;
    private UUID currency;
    private UUID category;

    public ScheduledOperationEntity() {
    }

    public ScheduledOperationEntity(UUID uuid, LocalDateTime dt_create,
                                    LocalDateTime dt_update, String user, LocalDateTime startTime,
                                    LocalDateTime stopTime, Long interval, String time_unit,
                                    UUID account, String description, Long value, UUID currency,
                                    UUID category) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;

        this.user = user;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.time_unit = time_unit;
        this.account = account;
        this.description = description;
        this.value = value;
        this.currency = currency;
        this.category = category;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ScheduledOperationEntity{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", user='" + user + '\'' +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", interval=" + interval +
                ", time_unit='" + time_unit + '\'' +
                ", account=" + account +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", currency=" + currency +
                ", category=" + category +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dt_create;
        private LocalDateTime dt_update;
        private String user;

        //scheduler
        private LocalDateTime startTime;
        private LocalDateTime stopTime;
        private Long interval;
        private String time_unit;
        //operation
        private UUID account;
        private String description; //по человечески
        private Long value;
        private UUID currency;
        private UUID category;

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


        public Builder setUser(String user) {
            this.user = user;
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

        public Builder setTime_unit(String time_unit) {
            this.time_unit = time_unit;
            return this;
        }

        public Builder setAccount(UUID account) {
            this.account = account;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setValue(Long value) {
            this.value = value;
            return this;
        }

        public Builder setCurrency(UUID currency) {
            this.currency = currency;
            return this;
        }

        public Builder setCategory(UUID category) {
            this.category = category;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ScheduledOperationEntity build() {
            return new ScheduledOperationEntity(uuid, dt_create, dt_update, user,
                    startTime, stopTime, interval, time_unit,
                    account, description, value, currency, category);
        }
    }
}


