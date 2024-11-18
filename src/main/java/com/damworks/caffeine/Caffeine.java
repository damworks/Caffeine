package com.damworks.caffeine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // Check if SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.err.println("System tray is not supported on this system.");
            return;
        }

        System.out.println("Starting Caffeine...");

        // Initialize the mouse movement task
        try {
            Robot robot = new Robot();
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            // Initialize and add tray icon
            setupTrayIcon(scheduler);

            // Schedule the mouse movement task at a fixed rate
            scheduler.scheduleAtFixedRate(() -> moveMouse(robot), 0, MOVE_INTERVAL_MS, TimeUnit.MILLISECONDS);

        } catch (AWTException e) {
            System.err.println("Error initializing the robot or tray icon: " + e.getMessage());
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

    /**
     * Sets up the tray icon for the program.
     *
     * @param scheduler The ScheduledExecutorService to shut down when the program exits.
     */
    private static void setupTrayIcon(ScheduledExecutorService scheduler) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        // Load an icon for the tray
        Image trayIconImage = Toolkit.getDefaultToolkit().createImage("coffee_icon.png");
        if (trayIconImage == null) {
            System.err.println("Failed to load tray icon image.");
            return;
        }

        // Create a popup menu
        PopupMenu popup = new PopupMenu();

        // Add an "Exit" menu item
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Shutting down Caffeine...");
                scheduler.shutdown();
                tray.remove(tray.getTrayIcons()[0]);
                System.exit(0);
            }
        });
        popup.add(exitItem);

        // Create the tray icon
        TrayIcon trayIcon = new TrayIcon(trayIconImage, "Caffeine - Prevent Inactivity", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> trayIcon.displayMessage("Caffeine", "Caffeine is running!", TrayIcon.MessageType.INFO));

        // Add the icon to the system tray
        tray.add(trayIcon);
    }
}
