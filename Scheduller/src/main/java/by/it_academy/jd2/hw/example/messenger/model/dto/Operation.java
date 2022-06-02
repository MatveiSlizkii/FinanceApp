package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Operation {
    private UUID uuid;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dt_create;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dt_update;
    private UUID account;
    private String description; //по человечески
    private Long value;
    private UUID currency;
    private UUID category;

    public Operation() {
    }

    public Operation(UUID uuid, LocalDateTime dt_create,
                     LocalDateTime dt_update, UUID account,
                     String description, Long value,
                     UUID currency, UUID category) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
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
        return "Operation{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
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

        public Operation build() {
            return new Operation(uuid,dt_create, dt_update, account, description, value, currency, category);
        }
    }
}
