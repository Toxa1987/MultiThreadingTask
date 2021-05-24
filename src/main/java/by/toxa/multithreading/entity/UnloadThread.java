package by.toxa.multithreading.entity;

import java.util.concurrent.TimeUnit;

public class UnloadThread extends Thread {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Daemon starts work");
        Ferry ferry = Ferry.getInstance();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ferry.runToUnload()) {
                System.out.println("Ferry is unloading");
            }
        }
    }
}
