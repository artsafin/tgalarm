package com.artsafin.tgalarm.parser;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimeMutator {
    private DayOfWeek weekday;
    private int dayOfMonth;
    private Month month;
    private int year;

    private LocalTime time;

    private List<Duration> intervals = new ArrayList<>();

    public void addInterval(Duration duration) {
        intervals.add(duration);
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    private ZonedDateTime futureOverlap(ZonedDateTime date, ZonedDateTime now, TemporalUnit mutableUnit) {
        if (date.compareTo(now) < 0) {
            return date.plus(1, mutableUnit);
        } else {
            return date;
        }
    }

    public ZonedDateTime build(ZonedDateTime now) {
        ZonedDateTime pivotDate = now;

        if (time != null) {
            pivotDate = futureOverlap(pivotDate.with(time), pivotDate, ChronoUnit.DAYS);
        }

        if (dayOfMonth > 0) {
            pivotDate = futureOverlap(pivotDate.withDayOfMonth(dayOfMonth), pivotDate, ChronoUnit.MONTHS);
        } else if (weekday != null) {
            int weekDayOffset = weekday.getValue() - pivotDate.getDayOfWeek().getValue();
            if (weekDayOffset < 0) {
                weekDayOffset += 7;
            }

            pivotDate = pivotDate.plusDays(weekDayOffset);
        }

        if (month != null) {
            pivotDate = futureOverlap(pivotDate.withMonth(month.getValue()), pivotDate, ChronoUnit.YEARS);
        }

        if (year > 0) {
            pivotDate = pivotDate.withYear(year);
        }

        for (Duration d : intervals) {
            pivotDate = pivotDate.plus(d);
        }

        return pivotDate;
    }
}
