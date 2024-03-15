package com.epam.rd.autocode.hashtableopen816;

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private static final int INITIAL_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private Entry[] table;
    private int size;

    public HashtableOpen8to16Impl() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void insert(int key, Object value) {
        if (size == MAX_CAPACITY) {
            throw new IllegalStateException("Hashtable is full");
        }
        int index = key % table.length;
        while (table[index] != null && table[index].key != key) {
            index = (index + 1) % table.length;
        }
        table[index] = new Entry(key, value);
        size++;
        resizeIfNeeded();
    }

    @Override
    public Object search(int key) {
        int index = key % table.length;
        while (table[index] != null) {
            if (table[index].key == key) {
                return table[index].value;
            }
            index = (index + 1) % table.length;
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int index = key % table.length;
        while (table[index] != null) {
            if (table[index].key == key) {
                table[index] = null;
                size--;
                break;
            }
            index = (index + 1) % table.length;
        }
        resizeIfNeeded();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int[] keys = new int[size];
        int keysIndex = 0;
        for (Entry entry : table) {
            if (entry != null) {
                keys[keysIndex++] = entry.key;
            }
        }
        return keys;
    }

    private void resizeIfNeeded() {
        if (size == table.length && table.length < MAX_CAPACITY) {
            resize(table.length * 2);
        } else if (size > 0 && size <= table.length / 4 && table.length > INITIAL_CAPACITY) {
            resize(table.length / 2);
        }
    }

    private void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        for (Entry entry : table) {
            if (entry != null) {
                int index = entry.key % newTable.length;
                while (newTable[index] != null) {
                    index = (index + 1) % newTable.length;
                }
                newTable[index] = entry;
            }
        }
        table = newTable;
    }

    private static class Entry {
        final int key;
        Object value;

        Entry(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static HashtableOpen8to16 getInstance() {
        return new HashtableOpen8to16Impl();
    }
}
