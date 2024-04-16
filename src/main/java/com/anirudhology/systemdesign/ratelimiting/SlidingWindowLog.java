package com.anirudhology.systemdesign.ratelimiting;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowLog {

    // Size of the time window
    private final Duration windowSize;
    // Maximum number of allowed requests in a time window
    private final int maxAllowedRequests;
    // Logs of request timestamps within the window
    private final Deque<Instant> requestLogs;

    public SlidingWindowLog(Duration windowSize, int maxAllowedRequests) {
        this.windowSize = windowSize;
        this.maxAllowedRequests = maxAllowedRequests;
        this.requestLogs = new ArrayDeque<>();
    }

    /**
     * Accept of reject a request based on sliding window log
     * @return true, if request is accepted, false otherwise
     */
    public synchronized boolean acceptRequest() {
        // Current time
        Instant currentTime = Instant.now();
        // Remove timestamps older than current window
        removeOldTimestamps(currentTime);
        // Add current timestamp to the log
        requestLogs.addLast(currentTime);
        // Check if number of timestamps exceeds maximum allowed requests
        return requestLogs.size() <= maxAllowedRequests;
    }

    private void removeOldTimestamps(Instant currentTime) {
        Instant windowStartTime = currentTime.minus(windowSize);
        while (!requestLogs.isEmpty() && requestLogs.peekFirst().isBefore(windowStartTime)) {
            requestLogs.removeFirst();
        }
    }
}
