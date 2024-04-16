package com.anirudhology.systemdesign.ratelimiting;

import org.junit.jupiter.api.Test;

import java.time.Duration;

class FixedWindowCounterTest {

    @Test
    void acceptRequest() {
        // Window size - 1 second
        // Maximum allowed requests - 5
        FixedWindowCounter fixedWindowCounter = new FixedWindowCounter(Duration.ofSeconds(1), 5);
        for (int i = 1; i <= 20; i++) {
            boolean accepted = fixedWindowCounter.acceptRequest();
            System.out.println("Request " + i + ": " + (accepted ? "Accepted" : "Rejected"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}