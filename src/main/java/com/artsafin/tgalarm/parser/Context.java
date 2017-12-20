package com.artsafin.tgalarm.parser;

import java.time.*;
import java.util.List;

public class Context {
    private boolean canHavePrefix = true;

    private ZonedDateTime now;
    private int dayOfMonth;
    private Month month;
    private int year;
    private DayOfWeek weekday;
    private LocalTime time;
    private List<Duration> intervals;
    private StringBuilder prefixMessage = new StringBuilder();
    private StringBuilder suffixMessage = new StringBuilder();

    public Context(ZonedDateTime now) {
        this.now = now;
    }

    public void addInterval(Duration duration) {
        intervals.add(duration);
        markMutated();
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        markMutated();
    }

    public void setMonth(Month month) {
        this.month = month;
        markMutated();
    }

    public void setYear(int year) {
        this.year = year;
        markMutated();
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
        markMutated();
    }

    public void setTime(LocalTime time) {
        this.time = time;
        markMutated();
    }

    public void setHourAm(int hourAm) {
        setHour(hourAm == 12 ? 0 : hourAm);
    }

    public void setHourPm(int hourPm) {
        setHour(hourPm == 12 ? 12 : hourPm + 12);
    }

    public void setHour(int hour) {
        setTime(LocalTime.of(hour, 0));
    }

    private void markMutated() {
        canHavePrefix = false;

        if (suffixMessage.length() > 0) {
            suffixMessage = new StringBuilder();
        }
    }

    public void addMessage(String value) {
        if (canHavePrefix) {
            prefixMessage.append(" ").append(value);
        } else {
            suffixMessage.append(" ").append(value);
        }
    }
}
