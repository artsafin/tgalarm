package com.artsafin.tgalarm.alarm;

public class AlarmService {
    private AlarmRepository alarms;


    public AlarmService(AlarmRepository alarms) {
        this.alarms = alarms;
    }

    public void addOrUpdate(ScheduledAlarm alarm) {
    }
}
