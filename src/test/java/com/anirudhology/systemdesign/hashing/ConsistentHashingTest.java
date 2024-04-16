package com.anirudhology.systemdesign.hashing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsistentHashingTest {

    @Test
    void test() {
        // Use 3 virtual nodes per physical node
        ConsistentHashing<String> consistentHashing = new ConsistentHashing<>(3);
        // Add nodes to the hash ring
        consistentHashing.addNode("Node1");
        consistentHashing.addNode("Node2");
        consistentHashing.addNode("Node3");
        // Get the node responsible for a given key
        String key = "example_key";
        String node = consistentHashing.getNodeForKey(key);
        assertNotNull(node);

        // Remove a node from the hash ring
        consistentHashing.removeNode(node);
        // Get the node responsible for the same key again
        node = consistentHashing.getNodeForKey(key);
        assertNotNull(node);

        // Remove new node also
        consistentHashing.removeNode(node);
        // Get the node responsible for the same key again
        node = consistentHashing.getNodeForKey(key);
        assertNotNull(node);

        // Remove the new node also
        consistentHashing.removeNode(node);
        // Get the node responsible for the same key again
        node = consistentHashing.getNodeForKey(key);
        assertNull(node);

        consistentHashing.addNode("Node4");
        node = consistentHashing.getNodeForKey(key);
        assertNotNull(node);
        assertEquals("Node4", node);
    }
}