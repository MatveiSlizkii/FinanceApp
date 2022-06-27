package by.it_academy.jd2.hw.example.messenger.model.dto;

import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeDeserializer;
import by.it_academy.jd2.hw.example.messenger.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Schedule {
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime start_time;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime stop_time;
    private Long interval;
    private TimeUnitEnum time_unit;

    public Schedule() {
    }

    public Schedule(LocalDateTime start_time,
                    LocalDateTime stop_time, Long interval,
                    TimeUnitEnum time_unit) {
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.interval = interval;
        this.time_unit = time_unit;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getStop_time() {
        return stop_time;
    }

    public void setStop_time(LocalDateTime stop_time) {
        this.stop_time = stop_time;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public TimeUnitEnum getTime_unit() {
        return time_unit;
    }

    public void setTime_unit(TimeUnitEnum time_unit) {
        this.time_unit = time_unit;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                ", startTime=" + start_time +
                ", stopTime=" + stop_time +
                ", interval=" + interval +
                ", timeUnit=" + time_unit +
                '}';
    }

    public static class Builder {
        private LocalDateTime startTime;
        private LocalDateTime stopTime;
        private Long interval;
        private TimeUnitEnum timeUnit;

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

        public Builder setInterval(Long interval) {
            this.interval = interval;
            return this;
        }

        public Builder setTimeUnit(TimeUnitEnum timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Schedule build() {
            return new Schedule(startTime,
                                stopTime, interval, timeUnit);
        }
    }
}
