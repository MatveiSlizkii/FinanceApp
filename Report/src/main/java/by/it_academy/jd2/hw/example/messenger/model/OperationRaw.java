package by.it_academy.jd2.hw.example.messenger.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class OperationRaw {
    private String description;
    private UUID category;
    private LocalDateTime date;
    private double value;

    private OperationRaw() {
    }

    public OperationRaw(String description, UUID category, LocalDateTime date, double value) {
        this.description = description;
        this.category = category;
        this.date = date;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public static class Builder {
        private String description;
        private UUID category;
        private LocalDateTime date;
        private double value;

    private Builder() {
    }

    public Builder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Builder setCategory(UUID category) {
        this.category = category;
        return this;
    }

    public Builder setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Builder setValue(double value) {
        this.value = value;
        return this;
    }
    public static Builder createBuilder() {
        return new Builder();
    }

    public OperationRaw build() {
        return new OperationRaw(description, category, date, value);
    }
}
}
