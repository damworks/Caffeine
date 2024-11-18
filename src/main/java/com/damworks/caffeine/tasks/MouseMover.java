package com.damworks.caffeine.tasks;

import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MouseMover {
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

    public static void scheduleMouseMovement(ScheduledExecutorService scheduler, long interval) throws AWTException {
        Robot robot = new Robot();
        scheduler.scheduleAtFixedRate(() -> moveMouse(robot), 0, interval, TimeUnit.MILLISECONDS);
    }
}
