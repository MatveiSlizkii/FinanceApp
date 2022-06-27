package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Operation {
    private UUID account;
    private String description; //по человечески
    private Long value;
    private UUID currency;
    private UUID category;
    private Long date;
    @JsonIgnore
    private String login;

    public Operation() {
    }

    public Operation ( UUID account,
                     String description, Long value,
                     UUID currency, UUID category,
                       String login) {

        this.account = account;
        this.description = description;
        this.value = value;
        this.currency = currency;
        this.category = category;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Operation{" +
                ", account=" + account +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", currency=" + currency +
                ", category=" + category +
                '}';
    }

    public static class Builder {
        private UUID account;
        private String description; //по человечески
        private Long value;
        private UUID currency;
        private UUID category;
        private String login;

        private Builder() {
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

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Operation build() {
            return new Operation(account, description, value, currency, category, login);
        }
    }
}
