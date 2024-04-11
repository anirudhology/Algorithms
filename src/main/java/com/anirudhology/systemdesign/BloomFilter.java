package com.anirudhology.systemdesign;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter<T> {

    // Bit array
    private final BitSet bitSet;
    // Size of bit array
    private final int size;
    // Number of hash functions to be applied
    private final int numberOfHashFunctions;
    // Number of elements in the bloom filter
    private int numberOfElements;

    public BloomFilter(int size, int numberOfHashFunctions) {
        this.size = size;
        this.numberOfHashFunctions = numberOfHashFunctions;
        this.bitSet = new BitSet(this.size);
    }

    public void add(T element) {
        for (int i = 0; i < numberOfHashFunctions; i++) {
            int hash = calculateHash(element, i);
            bitSet.set(hash % size, true);
        }
        numberOfElements++;
    }

    public boolean contains(T element) {
        for (int i = 0; i < numberOfElements; i++) {
            int hash = calculateHash(element, i);
            if (!bitSet.get(hash % size)) {
                return false;
            }
        }
        return true;
    }

    public double calculateFalsePositiveProbability() {
        return Math.pow((1 - Math.exp(-numberOfHashFunctions * (double) numberOfElements / size)), numberOfHashFunctions);
    }

    private int calculateHash(T element, int index) {
        return switch (index) {
            case 0 -> calculateMD5Hash(element);
            case 1 -> calculateSHA256Hash(element);
            case 2 -> calculateFNVHash(element);
            default -> throw new IllegalArgumentException("Invalid hash function index!!!");
        };
    }

    private int calculateMD5Hash(T element) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md5.digest(convertObjectToByteArray(element));
            return Math.abs(new String(hashBytes).hashCode());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateSHA256Hash(T element) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = sha256.digest(convertObjectToByteArray(element));
            return Math.abs(new String(hashBytes).hashCode());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateFNVHash(T element) {
        final int FNV_OFFSET_BASIS_32 = 0x811c9dc5;
        final int FNV_PRIME_32 = 0x01000193;
        int hash = FNV_OFFSET_BASIS_32;
        byte[] bytes = convertObjectToByteArray(element);
        for (byte b : bytes) {
            hash ^= b;
            hash *= FNV_PRIME_32;
        }
        return Math.abs(hash);
    }

    private byte[] convertObjectToByteArray(T element) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(element);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
