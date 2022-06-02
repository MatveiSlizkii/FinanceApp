package by.it_academy.jd2.hw.example.messenger.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "balance", schema = "app")
public class BalanceEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "value")
    private double value;

    public BalanceEntity() {
    }

    public BalanceEntity(UUID uuid,
                         LocalDateTime dtUpdate, double value) {
        this.uuid = uuid;
        this.dtUpdate = dtUpdate;
        this.value = value;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtUpdate;
        private double value;


        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public Builder setValue(double value) {
            this.value = value;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public BalanceEntity build() {
            return new BalanceEntity(uuid, dtUpdate, value);
        }
    }}
