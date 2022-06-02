package by.it_academy.jd2.hw.example.messenger.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "scheduled_operation", schema = "app")
public class ScheduledOperationEntity {
    @Id
    private UUID uuid;
    private LocalDateTime dt_create;
    @Version
    private LocalDateTime dt_update;
    private UUID schedule;
    private UUID operation;

    public ScheduledOperationEntity() {
    }

    public ScheduledOperationEntity(UUID uuid, LocalDateTime dt_create,
                                    LocalDateTime dt_update, UUID schedule,
                                    UUID operation) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.schedule = schedule;
        this.operation = operation;
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

    public UUID getSchedule() {
        return schedule;
    }

    public void setSchedule(UUID schedule) {
        this.schedule = schedule;
    }

    public UUID getOperation() {
        return operation;
    }

    public void setOperation(UUID operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "ScheduledOperation{" +
                "uuid=" + uuid +
                ", dtCreate=" + dt_create +
                ", dtUpdate=" + dt_update +
                ", schedule=" + schedule +
                ", operation=" + operation +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dt_create;
        private LocalDateTime dt_update;
        private UUID scheduleEntity;
        private UUID operationEntity;

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

        public Builder setScheduleEntity(UUID scheduleEntity) {
            this.scheduleEntity = scheduleEntity;
            return this;
        }

        public Builder setOperationEntity(UUID operationEntity) {
            this.operationEntity = operationEntity;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ScheduledOperationEntity build() {
            return new ScheduledOperationEntity(uuid, dt_create, dt_update, scheduleEntity, operationEntity);
        }
    }
}
