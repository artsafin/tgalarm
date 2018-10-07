package com.artsafin.tgalarm.alarm;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Consumer;

public class MemoryAlarmRepository implements AlarmRepository {
    private Map<String, ScheduledAlarm> alarmsById = new HashMap<>();
    private Map<Integer, Map<String, ScheduledAlarm>> alarmsByUser = new HashMap<>();

    @Override
    public boolean addOrUpdate(ScheduledAlarm alarm) {
        alarmsById.put(alarm.id(), alarm);

        if (!alarmsByUser.containsKey(alarm.userId())) {
            alarmsByUser.put(alarm.userId(), new HashMap<>());
        }
        alarmsByUser.get(alarm.userId()).put(alarm.id(), alarm);

        return true;
    }

    @Override
    public Map<String, ScheduledAlarm> getUserAlarms(int userId) {
        return Optional.ofNullable(alarmsByUser.get(userId)).orElse(Collections.emptyMap());
    }

    @Override
    public Optional<ScheduledAlarm> getById(String id) {
        return Optional.ofNullable(alarmsById.get(id));
    }

    @Override
    public boolean has(ScheduledAlarm alarm) {
        return alarmsById.containsKey(alarm.id());
    }

    @Override
    public Iterator<ScheduledAlarm> iterator() {
        return alarmsById.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super ScheduledAlarm> action) {
        alarmsById.values().forEach(action);
    }

    @Override
    public Spliterator<ScheduledAlarm> spliterator() {
        return alarmsById.values().spliterator();
    }
}
