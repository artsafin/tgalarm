package com.artsafin.tgalarm.alarm;

import java.util.Map;
import java.util.Optional;

public interface AlarmRepository extends Iterable<ScheduledAlarm> {
    boolean addOrUpdate(ScheduledAlarm alarm);

    Map<String, ScheduledAlarm> getUserAlarms(int userId);

    Optional<ScheduledAlarm> getById(String id);

    boolean has(ScheduledAlarm alarm);
}
