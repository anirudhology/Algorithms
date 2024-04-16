package com.anirudhology.systemdesign.ratelimiting;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowCounter {

    // Size of the time window
    private final Duration windowSize;
    // Maximum allowed requests in a time window
    private final int maxAllowedRequests;
    // Map to store requests counts for each time window
    private final Map<Long, Integer> requestCounts;

    public FixedWindowCounter(Duration windowSize, int maxAllowedRequests) {
        this.windowSize = windowSize;
        this.maxAllowedRequests = maxAllowedRequests;
        this.requestCounts = new HashMap<>();
    }

    /**
     * Accept or reject incoming requests based on fixed window counter
     * @return true, if request is allowed, false otherwise
     */
    public synchronized boolean acceptRequest() {
        // Current time
        Instant currentTime = Instant.now();
        // Current time window
        long currentWindow = currentTime.toEpochMilli() / windowSize.toMillis();
        // Increment request count for the current window
        requestCounts.put(currentWindow, requestCounts.getOrDefault(currentWindow, 0) + 1);
        // Check if request count for current window exceeds the max allowed requests
        return requestCounts.getOrDefault(currentWindow, 0) <= maxAllowedRequests;
    }
}
