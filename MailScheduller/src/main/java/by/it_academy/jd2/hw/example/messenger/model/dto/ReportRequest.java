package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class ReportRequest {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("accounts")
    private UUID[] accounts;
    @JsonProperty("to")

    private Long to;
    @JsonProperty("from")

    private Long from;


    public ReportRequest() {
    }

    public ReportRequest(UUID[] accounts, Long to, Long from, UUID uuid) {
        this.accounts = accounts;
        this.to = to;
        this.from = from;
        this.uuid = uuid;
    }

    public UUID[] getAccounts() {
        return accounts;
    }

    public void setAccounts(UUID[] accounts) {
        this.accounts = accounts;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "uuid=" + uuid +
                ", accounts=" + Arrays.toString(accounts) +
                ", to=" + to +
                ", from=" + from +
                '}';
    }

    public static class Builder {
        private UUID[] accounts;
        private Long to;
        private Long from;
        private UUID uuid;

        private Builder() {
        }

        public Builder setAccounts(UUID[] accounts) {
            this.accounts = accounts;
            return this;
        }

        public Builder setTo(Long to) {
            this.to = to;
            return this;
        }

        public Builder setFrom(Long from) {
            this.from = from;
            return this;
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ReportRequest build() {
            return new ReportRequest(accounts, to, from, uuid);
        }
    }
}
