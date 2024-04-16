package com.anirudhology.systemdesign.ratelimiting;

import java.time.Instant;

/**
 * Class that implements token bucket rate limiting algorithm
 */
public class TokenBucket {

    // Maximum number of tokens that a bucket can hold
    private final int capacity;
    // Rate at which tokens will be refilled
    private final double refillRate;
    // Current number of tokens in the bucket;
    private int tokens;
    // Time when the bucket was last refilled
    private Instant lastRefillTime;

    public TokenBucket(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        // The bucket is full initially
        this.tokens = capacity;
        this.lastRefillTime = Instant.now();
    }

    /**
     * Acquires token from the bucket to perform operations
     *
     * @param tokensRequired required tokens for an operation
     * @return true, if operation is allowed, false otherwise.
     */
    public boolean acquire(int tokensRequired) {
        synchronized (this) {
            refillBucket();
            if (tokens >= tokensRequired) {
                tokens -= tokensRequired;
                return true;
            }
            return false;
        }
    }

    /**
     * Refill the bucket with tokens based on the elapsed time
     * since the last refill
     */
    private void refillBucket() {
        // Current time
        Instant currentTime = Instant.now();
        // Elapsed time since the last refill
        double elapsedTime = currentTime.toEpochMilli() - lastRefillTime.toEpochMilli();
        // Tokens to add
        double tokensToAdd = elapsedTime * refillRate;
        tokens = (int) Math.min(capacity, tokensToAdd + tokens);
        lastRefillTime = currentTime;
    }
}
