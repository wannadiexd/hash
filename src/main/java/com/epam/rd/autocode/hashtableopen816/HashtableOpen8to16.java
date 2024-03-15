package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private static final int INITIAL_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.25;

    private int capacity;
    private int size;
    private int[] keys;
    private Object[] values;

    public HashtableOpen8to16Impl() {
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
        this.keys = new int[capacity];
        this.values = new Object[capacity];
    }

    @Override
    public void insert(int key, Object value) {
        if (size == capacity) {
            if (capacity == MAX_CAPACITY) {
                throw new IllegalStateException("Hashtable capacity reached maximum limit");
            }
            resize();
        }

        int index = findIndex(key);
        if (keys[index] == key) {
            values[index] = value; // Update existing value
        } else {
            keys[index] = key;
            values[index] = value;
            size++;
        }
    }

    @Override
    public Object search(int key) {
        int index = findIndex(key);
        return keys[index] == key ? values[index] : null;
    }

    @Override
    public void remove(int key) {
        int index = findIndex(key);
        if (keys[index] == key) {
            keys[index] = 0;
            values[index] = null;
            size--;

            if (size != 0 && size <= capacity * LOAD_FACTOR_THRESHOLD) {
                resize();
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int[] nonNullKeys = new int[size];
        int index = 0;
        for (int key : keys) {
            if (key != 0) {
                nonNullKeys[index++] = key;
            }
        }
        return Arrays.copyOf(nonNullKeys, index);
    }

    private void resize() {
        int newCapacity = capacity * 2;
        if (size <= capacity / 4 && newCapacity >= INITIAL_CAPACITY * 2) {
            newCapacity = capacity / 2;
        }

        int[] newKeys = new int[newCapacity];
        Object[] newValues = new Object[newCapacity];

        for (int i = 0; i < capacity; i++) {
            if (keys[i] != 0) {
                int newIndex = findIndex(keys[i], newCapacity);
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
            }
        }

        capacity = newCapacity;
        keys = newKeys;
        values = newValues;
    }

    private int findIndex(int key) {
        return findIndex(key, capacity);
    }

    private int findIndex(int key, int capacity) {
        int index = key % capacity;
        while (keys[index] != 0 && keys[index] != key) {
            index = (index + 1) % capacity;
        }
        return index;
    }

    public static HashtableOpen8to16 getInstance() {
        return new HashtableOpen8to16Impl();
    }
}
