package com.damworks.caffeine.tasks;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Sends a reminder to the user on how to terminate the program.
 */
public class ReminderTask {
    public static void scheduleReminder(ScheduledExecutorService scheduler, long interval) {
        scheduler.scheduleAtFixedRate(ReminderTask::sendReminder, interval, interval, TimeUnit.MILLISECONDS);
    }

    private static void sendReminder() {
        System.out.println("Reminder: Type 'stop' and press Enter to terminate the program.");
    }
}
