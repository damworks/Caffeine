package com.damworks.caffeine.util;

import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

public class StopListener {
    /**
     * Listens for a "stop" command in the console to safely terminate the program.
     *
     * @param scheduler The ScheduledExecutorService used for scheduling tasks.
     */
    public static void listenForExitCommand(ScheduledExecutorService scheduler) {
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
