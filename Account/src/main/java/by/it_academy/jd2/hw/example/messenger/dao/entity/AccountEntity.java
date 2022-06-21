package by.it_academy.jd2.hw.example.messenger.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "accounts", schema = "app")
public class AccountEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "currency")
    private UUID currency;

    @OneToOne
    @JoinColumn(name = "balance", nullable = false)
    private BalanceEntity balance;
    @JsonIgnore
    @Column (name = "user")
    private String user;

    public AccountEntity() {
    }

    public AccountEntity(UUID uuid, LocalDateTime dtCreate,
                         LocalDateTime dtUpdate,
                         String title, String description,
                         String type, UUID currency,
                         BalanceEntity balance,
                         String user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.description = description;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
        this.user = user;
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

    public BalanceEntity getBalance() {
        return balance;
    }

    public void setBalance(BalanceEntity balance) {
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", user='" + user + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String title;
        private String description;
        private String type;
        private UUID currency;
        private BalanceEntity balance;
        private String user;


        private Builder() {

        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            try {
                this.dtCreate = dtCreate;
            } catch (IllegalArgumentException e){
                this.dtCreate = null;
            }

            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            try {
                this.dtUpdate = dtUpdate;
            } catch (IllegalArgumentException e){
                this.dtUpdate = null;
            }
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

        public Builder setBalance(BalanceEntity balance) {
            this.balance = balance;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public AccountEntity build() {
            return new AccountEntity(uuid, dtCreate, dtUpdate, title, description, type, currency, balance, user);
        }

    }
}

