package com.anirudhology.systemdesign.ratelimiting;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounter {

    // Duration of the sliding window
    private final Duration windowSize;
    // Maximum allowed requests in a time window
    private final int maxAllowedRequests;
    // Map to store request counts for each time window
    private final Map<Long, Integer> requestCounts;

    public SlidingWindowCounter(Duration windowSize, int maxAllowedRequests) {
        this.windowSize = windowSize;
        this.maxAllowedRequests = maxAllowedRequests;
        this.requestCounts = new ConcurrentHashMap<>();
    }

    public synchronized boolean acceptRequest() {
        long currentTime = System.currentTimeMillis();
        long currentWindow = currentTime / windowSize.toMillis();
        // Slide window, if necessary
        slideWindow(currentWindow);
        // Increment request for the current window
        int count = requestCounts.getOrDefault(currentWindow, 0) + 1;
        requestCounts.put(currentWindow, count);
        // Check if the request count exceeds the maximum allowed requests
        return count <= maxAllowedRequests;
    }

    private void slideWindow(long currentWindow) {
        long oldestWindow = currentWindow - (maxAllowedRequests - 1);
        requestCounts.keySet().removeIf(window -> window < oldestWindow);
    }
}
