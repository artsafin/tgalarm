package com.artsafin.tgalarm.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AlarmService {
    private AlarmRepository alarms;

    private HashMap<String, List<ScheduledAlarm>> alarmsByUser = new HashMap<>();

    public AlarmService(AlarmRepository alarms) {
        this.alarms = alarms;
    }

    public void add(ScheduledAlarm alarm) {
        alarms.add(alarm);

        if (!alarmsByUser.containsKey(alarm.getUserName())) {
            alarmsByUser.put(alarm.getUserName(), new ArrayList<>());
        }
        alarmsByUser.get(alarm.getUserName()).add(alarm);
    }

    public Optional<List<ScheduledAlarm>> getUserAlerts(String username) {
        return Optional.ofNullable(alarmsByUser.get(username));
    }
}
