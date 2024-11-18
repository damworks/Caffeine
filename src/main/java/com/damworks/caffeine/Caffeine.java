package com.damworks.caffeine;

import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Initialize the mouse movement task
        try {
            Robot robot = new Robot();

            // Schedule the mouse movement task at a fixed rate
            scheduler.scheduleAtFixedRate(() -> moveMouse(robot), 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);

            // Schedule a reminder message every 30 minutes
            scheduler.scheduleAtFixedRate(Caffeine::sendReminder, REMINDER_INTERVAL_MS, REMINDER_INTERVAL_MS, TimeUnit.MILLISECONDS);


            // Start a thread to listen for a "stop" command
            listenForExitCommand(scheduler);

        } catch (AWTException e) {
            System.err.println("Error initializing the robot: " + e.getMessage());
        }
    }

    /**
     * Moves the mouse slightly to simulate user activity.
     *
     * @param robot The Robot instance to control mouse movements.
     */
    private static void moveMouse(Robot robot) {
        try {
            // Get the current mouse position
            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            // Move the mouse slightly and return to the original position
            robot.mouseMove(currentPosition.x + 1, currentPosition.y);
            robot.mouseMove(currentPosition.x, currentPosition.y);

            // Optional: Perform a left-click to further simulate activity (uncomment if needed)
            // robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            // robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            System.out.println("Got a coffee! " + new java.util.Date());
        } catch (Exception e) {
            System.err.println("Error moving the mouse: " + e.getMessage());
        }
    }

    /**
     * Sends a reminder to the user on how to terminate the program.
     */
    private static void sendReminder() {
        System.out.println("Reminder: Type 'stop' and press Enter to terminate the program.");
    }

    /**
     * Listens for a "stop" command in the console to safely terminate the program.
     *
     * @param scheduler The ScheduledExecutorService used for scheduling tasks.
     */
    private static void listenForExitCommand(ScheduledExecutorService scheduler) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type 'stop' and press Enter to terminate the program.");
            while (true) {
                String input = scanner.nextLine();
                if ("stop".equalsIgnoreCase(input)) {
                    System.out.println("Shutting down Caffeine...");
                    scheduler.shutdown();
                    System.exit(0);
                }
            }
        }).start();
    }
}
