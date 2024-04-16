package com.anirudhology.systemdesign.ratelimiting;

import org.junit.jupiter.api.Test;

class LeakyBucketTest {

    @Test
    void acceptRequest() {
        // Bucket capacity - 10
        // Tokens to leak per second - 2
        LeakyBucket leakyBucket = new LeakyBucket(10, 2);
        for (int i = 1; i <= 20; i++) {
            boolean accepted = leakyBucket.acceptRequest();
            System.out.println("Request " + i + ": " + accepted);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}