package com.anirudhology.systemdesign.ratelimiting;

import org.junit.jupiter.api.Test;

class TokenBucketTest {

    @Test
    void testAcquire() {
        // Rate limiter with 10 tokens and refill rate of 0.1 tokens/millisecond
        TokenBucket tokenBucket = new TokenBucket(10, 0.1);
        // Process 20 requests
        for (int i = 1; i <= 20; i++) {
            System.out.println("Request " + i + ": " + tokenBucket.acquire(1));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}