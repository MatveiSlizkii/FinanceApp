package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class Schedule {
    @JsonProperty("start_time")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
    @JsonProperty("stop_time")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime stopTime;
    @JsonProperty("interval")
    private long interval;

    @JsonProperty("time_unit")
    private TimeUnitEnum timeUnit;

    public Schedule() {
    }

    public Schedule(LocalDateTime startTime, LocalDateTime stopTime,
                    long interval, TimeUnitEnum timeUnit) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }



    public TimeUnitEnum getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnitEnum timeUnit) {
        this.timeUnit = timeUnit;
    }

    public static class Builder {
        private LocalDateTime startTime;
        private LocalDateTime stopTime;
        private long interval;

        private TimeUnitEnum time;

        private Builder() {
        }

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setStopTime(LocalDateTime stopTime) {
            this.stopTime = stopTime;
            return this;
        }

        public Builder setInterval(long interval) {
            this.interval = interval;
            return this;
        }



        public Builder setTime(TimeUnitEnum time) {
            this.time = time;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Schedule build() {
            return new Schedule(startTime, stopTime, interval,
                    time);
        }
    }
}