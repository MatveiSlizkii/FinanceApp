package by.it_academy.jd2.hw.example.messenger.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private UUID uuid;
    private String title;
    private String description;
    private String type;
    private UUID currency;
    private Double balance;

    public Account(UUID uuid, String title, String description, String type, UUID currency, Double balance) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public static class Builder {
        private UUID uuid;
        private String title;
        private String description;
        private String type;
        private UUID currency;
        private double balance;

        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setCurrency(UUID currency) {
            this.currency = currency;
            return this;
        }

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Account build() {
            return new Account(uuid, title, description, type, currency, balance);
        }
    }
}