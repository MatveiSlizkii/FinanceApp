package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

public class OperationRequest {
//    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private Long date ;
    private String description; //по человечески
    private Long value;
    private UUID currency;
    private UUID category;

    private OperationRequest() {
    }

    public OperationRequest(Long date, String description, Long value, UUID currency, UUID category) {
        this.date = date;
        this.description = description;
        this.value = value;
        this.currency = currency;
        this.category = category;
        this.date = LocalDateTime.now().toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(LocalDateTime.now())).toEpochMilli();
    }

    public Long getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Long getValue() {
        return value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public UUID getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "{" +
                "\"date\":" + date +
                ", \"description\": \"" + description +
                "\", \"category\": \"" + category +
                "\", \"value\": " + value +
                ", \"currency\": \"" + currency +
                "\"}";
    }


    public static class Builder {
        private Long date;
        private String description; //по человечески
        private Long value;
        private UUID currency;
        private UUID category;

        private Builder() {
        }


        public Builder setDate(Long date) {
            this.date = date;
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

        public OperationRequest build() {
            return new OperationRequest(date, description, value, currency, category);
        }
    }
}
