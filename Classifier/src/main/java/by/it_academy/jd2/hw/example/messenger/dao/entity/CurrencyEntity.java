package by.it_academy.jd2.hw.example.messenger.dao.entity;

import by.it_academy.jd2.hw.example.messenger.model.Currency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "currency", schema = "app")
public class CurrencyEntity {
    @Id
    private UUID uuid;
    private LocalDateTime dt_create;
    @Version
    private LocalDateTime dt_update;
    private String title;
    private String description;

    public CurrencyEntity() {
    }

    public CurrencyEntity(UUID uuid, LocalDateTime dt_create,
                          LocalDateTime dt_update, String title, String description) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dt_create;
        private LocalDateTime dt_update;
        private String title;
        private String description;


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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public CurrencyEntity build() {
            return new CurrencyEntity(uuid, dt_create, dt_update, title, description);
        }
    }
}