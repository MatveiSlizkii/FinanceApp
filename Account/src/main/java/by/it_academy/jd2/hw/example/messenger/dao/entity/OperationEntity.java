package by.it_academy.jd2.hw.example.messenger.dao.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operations", schema = "app")
public class OperationEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private UUID category;
    @Column(name = "value")
    private Double value;
    @Column(name = "currency")
    private UUID currency;
    @Column(name = "uuidAccount")
    private UUID uuidAccount;

    public OperationEntity() {
    }

    public OperationEntity(UUID uuid, LocalDateTime dtCreate,
                           LocalDateTime dtUpdate, LocalDateTime date,
                           String description, UUID category,
                           Double value, UUID currency, UUID uuidAccount) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.date = date;
        this.description = description;
        this.category = category;
        this.value = value;
        this.currency = currency;
        this.uuidAccount = uuidAccount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public UUID getUuidAccount() {
        return uuidAccount;
    }

    public void setUuidAccount(UUID uuidAccount) {
        this.uuidAccount = uuidAccount;
    }

    @Override
    public String toString() {
        return "OperationEntity{" +
                "uuid=" + uuid +
                ", dt_create=" + dtCreate +
                ", dt_update=" + dtUpdate +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", value=" + value +
                ", currency=" + currency +
                ", uuidAccount=" + uuidAccount +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private LocalDateTime date;
        private String description;
        private UUID category;
        private Double value;
        private UUID currency;
        private UUID uuidAccount;

        private Builder() {
        }


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            try {
                this.dtCreate = dtCreate;
            } catch (NullPointerException e){
                this.dtCreate = null;
            }
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            try {
                this.dtUpdate = dtUpdate;
            } catch (NullPointerException e){
                this.dtUpdate = null;
            }
            return this;
        }

        public Builder setDate(LocalDateTime date) {
            try {
                this.date = date;
            } catch (NullPointerException e){
                this.date = null;
            }
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategory(UUID category) {
            this.category = category;
            return this;
        }

        public Builder setValue(Double value) {
            this.value = value;
            return this;
        }

        public Builder setCurrency(UUID currency) {
            this.currency = currency;
            return this;
        }

        public Builder setUuidAccount(UUID uuidAccount) {
            this.uuidAccount = uuidAccount;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public OperationEntity build() {
            return new OperationEntity(uuid, dtCreate, dtUpdate, date, description, category, value, currency,uuidAccount);
        }

    }

}
