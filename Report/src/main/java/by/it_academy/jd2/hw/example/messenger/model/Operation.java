package by.it_academy.jd2.hw.example.messenger.model;


import java.time.LocalDateTime;

public class Operation {
    private String description;
    private String category;
    private LocalDateTime date;
    private double value;

    private Operation() {
    }

    public Operation(String description, String category, LocalDateTime date, double value) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
        private String category;
        private LocalDateTime date;
        private double value;

        private Builder() {
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategory(String category) {
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

        public Operation build() {
            return new Operation(description, category, date, value);
        }
    }
    }
