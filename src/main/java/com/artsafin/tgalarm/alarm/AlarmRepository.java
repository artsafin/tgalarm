package com.artsafin.tgalarm.alarm;

public interface AlarmRepository extends Iterable<ScheduledAlarm> {
    boolean add(ScheduledAlarm alarm);
}
