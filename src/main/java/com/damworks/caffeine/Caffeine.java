package com.damworks.caffeine;

import java.awt.*;
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

    public static void main(String[] args) {
        System.out.println("Starting Caffeine...");

        // Initialize the mouse movement task
        try {
            Robot robot = new Robot();
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            // Schedule the mouse movement task at a fixed rate
            scheduler.scheduleAtFixedRate(() -> moveMouse(robot), 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);

            // Add shutdown hook to gracefully terminate the scheduler
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down Caffeine...");
                scheduler.shutdown();
            }));

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

            System.out.println("Mouse moved at " + new java.util.Date());
        } catch (Exception e) {
            System.err.println("Error moving the mouse: " + e.getMessage());
        }
    }
}
