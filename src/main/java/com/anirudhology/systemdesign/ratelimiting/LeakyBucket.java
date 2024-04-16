package com.anirudhology.systemdesign.ratelimiting;

import java.time.Duration;
import java.time.Instant;

public class LeakyBucket {

    // Maximum number of tokens that a bucket can hold
    private final int capacity;
    // Rate of leaking tokens from the bucket
    private final int leakRate;
    // Current number of tokens in the bucket
    private int tokens;
    // Last time when the tokens were leaked
    private Instant lastLeakTime;

    public LeakyBucket(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        // Initially the bucket is empty
        this.tokens = 0;
        this.lastLeakTime = Instant.now();
    }

    public boolean acceptRequest() {
        // Calculate the number of tokens to leak since the last request
        long elapsedSeconds = Duration.between(lastLeakTime, Instant.now()).getSeconds();
        int tokensToLeak = (int) (elapsedSeconds * leakRate);
        tokens = Math.max(0, tokens - tokensToLeak);
        // If the bucket is not full, leak the token and accept request
        if (tokens < capacity) {
            tokens++;
            lastLeakTime = Instant.now();
            return true;
        }
        // Bucket is full, reject the request
        return false;
    }
}
