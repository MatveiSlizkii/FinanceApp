package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduledOperation {
    private UUID uuid;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dt_create;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dt_update;
    private Schedule schedule;
    private Operation operation;
    @JsonIgnore
    private String user;

    public ScheduledOperation() {
    }

    public ScheduledOperation(UUID uuid, LocalDateTime dt_create,
                              LocalDateTime dt_update, Schedule schedule,
                              Operation operation, String user) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.schedule = schedule;
        this.operation = operation;
        this.user = user;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ScheduledOperation{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", schedule=" + schedule +
                ", operation=" + operation +
                ", user='" + user + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dt_create;
        private LocalDateTime dt_update;
        private Schedule schedule;
        private Operation operation;
        private String user;

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

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ScheduledOperation build() {
            return new ScheduledOperation(uuid, dt_create, dt_update, schedule, operation, user);
        }
    }
    }
