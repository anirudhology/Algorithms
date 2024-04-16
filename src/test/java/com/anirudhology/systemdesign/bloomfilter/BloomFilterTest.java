package com.anirudhology.systemdesign.bloomfilter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    @Test
    void testAddAndContains() {
        BloomFilter<String> filter = new BloomFilter<>(100, 3);
        filter.add("apple");
        filter.add("banana");
        filter.add("orange");

        assertTrue(filter.contains("apple"));
        assertTrue(filter.contains("banana"));
        assertTrue(filter.contains("orange"));
        assertFalse(filter.contains("grape"));
    }

    @Test
    void testCalculateFalsePositiveProbability() {
        BloomFilter<String> filter = new BloomFilter<>(100, 3);
        filter.add("apple");
        filter.add("banana");
        filter.add("orange");

        double probability = filter.calculateFalsePositiveProbability();
        assertTrue(probability > 0 && probability <= 1);
    }
}