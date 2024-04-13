package com.anirudhology.systemdesign;

import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashing<T> {

    // Number of virtual nodes per physical node
    private final int virtualNodesPerServer;
    // Hash ring to store the mapping of hash values to nodes
    private final TreeMap<Integer, T> hashRing;

    public ConsistentHashing(int virtualNodesPerServer) {
        this.virtualNodesPerServer = virtualNodesPerServer;
        this.hashRing = new TreeMap<>();
    }

    /**
     * Add node to the hash ring
     * @param node the node to be added
     */
    public void addNode(T node) {
        for (int i = 0; i < virtualNodesPerServer; i++) {
            int hash = calculateHash(node.toString() + i);
            hashRing.put(hash, node);
        }
        rebalance();
    }

    /**
     * Remove node from the hash ring
     * @param node the node to be removed
     */
    public void removeNode(T node) {
        for (int i = 0; i < virtualNodesPerServer; i++) {
            int hash = calculateHash(node.toString() + i);
            hashRing.remove(hash);
        }
        rebalance();
    }

    /**
     * Get the node where the given key is stored
     * @param key key to check
     * @return node where the key is/should be stored
     */
    public T getNodeForKey(String key) {
        if (hashRing.isEmpty()) {
            return null;
        }
        // Get hash of the key
        int keyHash = calculateHash(key);
        // Find the first node with a hash value greater than or equal to the
        // key's hash
        Map.Entry<Integer, T> entry = hashRing.ceilingEntry(keyHash);
        // If no such entry exists, wrap around to the first entry in the hash ring
        if (entry == null) {
            entry = hashRing.firstEntry();
        }
        return entry.getValue();
    }

    private void rebalance() {
        // No need to rebalance if there's only one node or no nodes
        if (hashRing.size() <= 1) {
            return;
        }
        // Create a new hash ring with the same number of virtual nodes
        TreeMap<Integer, T> newHashRing = new TreeMap<>();
        for (Map.Entry<Integer, T> entry : hashRing.entrySet()) {
            int hash = entry.getKey();
            T node = entry.getValue();
            newHashRing.put(hash, node);
        }
        hashRing.clear();
        hashRing.putAll(newHashRing);
    }

    private int calculateHash(String input) {
        return input.hashCode();
    }
}
