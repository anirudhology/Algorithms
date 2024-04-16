package com.anirudhology.systemdesign.ratelimiting;

import org.junit.jupiter.api.Test;

import java.time.Duration;

class SlidingWindowCounterTest {

    @Test
    void acceptRequest() {
        // Window size - 1
        // Max allowed requests - 5
        SlidingWindowCounter slidingWindowCounter = new SlidingWindowCounter(Duration.ofSeconds(1), 5);
        for (int i = 1; i <= 20; i++) {
            boolean accepted = slidingWindowCounter.acceptRequest();
            System.out.println("Request " + i + ": " + (accepted ? "Accepted" : "Rejected"));
            try {
                Thread.sleep(100); // Simulate request interval
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}