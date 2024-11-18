package com.damworks.caffeine.core;

import com.damworks.caffeine.tasks.MouseMover;
import com.damworks.caffeine.tasks.ReminderTask;
import com.damworks.caffeine.util.StopListener;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A utility program to prevent system inactivity by periodically moving the mouse.
 * This simulates user activity to avoid sleep mode or screen locking.
 */
public class Caffeine {
    // Interval between mouse movements (in milliseconds)
    private static final long MOVE_INTERVAL_MS = 120_000; // 2 minutes
    // Interval for reminder messages (in milliseconds)
    private static final long REMINDER_INTERVAL_MS = 1_800_000; // 30 minutes

    public static void main(String[] args) {
        System.out.println("Starting Caffeine...");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Initialize the mouse movement task
        try {
            // Schedule tasks
            MouseMover.scheduleMouseMovement(scheduler, MOVE_INTERVAL_MS);
            ReminderTask.scheduleReminder(scheduler, REMINDER_INTERVAL_MS);

            // Listen for exit commands
            StopListener.listenForExitCommand(scheduler);

        } catch (AWTException e) {
            System.err.println("Error initializing the robot: " + e.getMessage());
        }
    }

}
