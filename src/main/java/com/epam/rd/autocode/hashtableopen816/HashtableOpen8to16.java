package com.epam.rd.autocode.hashtableopen816;

public interface HashtableOpen8to16 {
    void insert(int key, Object value);
    Object search(int key);
    void remove(int key);
    int size();
    int[] keys();

    static HashtableOpen8to16 getInstance() {
        return new HashtableOpen8to16Impl();
    }
}

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private static final int INITIAL_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.25;

    private int[] keys;
    private Object[] values;
    private int size;
    private int capacity;

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
                throw new IllegalStateException("Maximum capacity reached");
            }
            resize();
        }
        int index = getIndex(key);
        while (keys[index] != 0 && keys[index] != key) {
            index = (index + 1) % capacity;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    @Override
    public Object search(int key) {
        int index = getIndex(key);
        while (keys[index] != 0) {
            if (keys[index] == key) {
                return values[index];
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int index = getIndex(key);
        while (keys[index] != 0) {
            if (keys[index] == key) {
                keys[index] = 0;
                values[index] = null;
                size--;
                resizeIfNeeded();
                return;
            }
            index = (index + 1) % capacity;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int[] allKeys = new int[size];
        int index = 0;
        for (int key : keys) {
            if (key != 0) {
                allKeys[index++] = key;
            }
        }
        return allKeys;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        int[] newKeys = new int[newCapacity];
        Object[] newValues = new Object[newCapacity];
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != 0) {
                int newIndex = getIndex(keys[i], newCapacity);
                while (newKeys[newIndex] != 0) {
                    newIndex = (newIndex + 1) % newCapacity;
                }
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
            }
        }
        keys = newKeys;
        values = newValues;
        capacity = newCapacity;
    }

    private void resizeIfNeeded() {
        if (size != 0 && size * 4 <= capacity) {
            int newCapacity = Math.max(INITIAL_CAPACITY, capacity / 2);
            if (newCapacity != capacity) {
                int[] newKeys = new int[newCapacity];
                Object[] newValues = new Object[newCapacity];
                for (int i = 0; i < capacity; i++) {
                    if (keys[i] != 0) {
                        int newIndex = getIndex(keys[i], newCapacity);
                        while (newKeys[newIndex] != 0) {
                            newIndex = (newIndex + 1) % newCapacity;
                        }
                        newKeys[newIndex] = keys[i];
                        newValues[newIndex] = values[i];
                    }
                }
                keys = newKeys;
                values = newValues;
                capacity = newCapacity;
            }
        }
    }

    private int getIndex(int key) {
        return getIndex(key, capacity);
    }

    private int getIndex(int key, int tableSize) {
        return Math.abs(key) % tableSize;
    }
}
