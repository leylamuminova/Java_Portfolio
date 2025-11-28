/**
 * ObjectHashMap.java
 * Homework 6, Part 1-3
 * A simple chaining-based hash map used for TextAnalytics.
 */

import java.util.LinkedList;

public class ObjectHashMap extends AbstractHashMap {

    private LinkedList<Entry>[] table;

    /**
     * Constructor for the hash map.
     * @param maxLoad double, maximum allowed load factor before resizing.
     */
    public ObjectHashMap(double maxLoad) {
        super(maxLoad);

        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<Entry>();
        }
    }

    /**
     * Inserts or updates a key/value pair.
     * Average time: O(1) per insertion when load factor is maintained.
     * Note: resize() operation is O(n) where n is the number of entries in the map.
     */
    @Override
    public void put(Object key, Object value) {

        double load = (double) numKeys / capacity;
        if (load > maxLoad) {
            resize(); // O(n): rehashing all entries requires visiting each entry once
        }

        LinkedList<Entry> bucket = findBucket(key);
        Entry e = findEntry(bucket, key);

        // findEntry() is O(n) in worst case where n is size of bucket (usually small)
        if (e == null) {
            bucket.add(new Entry(key, value));
            numKeys++;
        } else {
            e.value = value;
        }
    }

    /**
     * Finds and returns the value for a given key.
     * @param key Object
     * @return Object value
     * @throws IllegalArgumentException if key not found
     */
    @Override
    public Object find(Object key) {
        LinkedList<Entry> bucket = findBucket(key);
        Entry e = findEntry(bucket, key);

        if (e == null) {
            return null;
        }
        return e.value;
    }

    /**
     * O(n): Resizes hash table by doubling capacity.
     * Rehashing all entries is O(n) where n is the total number of entries in the map.
     * This operation visits every entry and reinserts it into a new larger table.
     */
    @Override
    protected void resize() {

        LinkedList<Entry>[] old = table;
        capacity = capacity * 2;

        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<Entry>();
        }

        numKeys = 0;

        // O(n): reinserting every entry - iterates through all old entries once
        for (LinkedList<Entry> bucket : old) {
            for (Entry e : bucket) {
                put(e.key, e.value);
            }
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return findEntry(findBucket(key), key) != null;
    }

    /**
     * O(n): Collects all entries into an array.
     * This method touches every key once, making it O(n) where n is the number of unique keys.
     * Iterates through all capacity buckets and collects all entries.
     */
    @Override
    public Entry[] getEntries() {
        LinkedList<Entry> all = new LinkedList<>();

        // O(n): scanning all buckets - visits each bucket in the table once
        for (LinkedList<Entry> bucket : table) {
            for (Entry e : bucket) {
                all.add(e);
            }
        }

        Entry[] arr = new Entry[all.size()];
        return all.toArray(arr);
    }

    // Helper methods

    private LinkedList<Entry> findBucket(Object key) {
        int h = hash(key);

        if (h < 0) {
            h = h % capacity;
            if (h < 0) h += capacity;
        }

        return table[h];
    }

    /**
     * Searches for an entry with the given key in the provided bucket.
     * Worst-case O(n) where n is the number of entries in the bucket
     * (typically very small when load factor is controlled).
     */
    private Entry findEntry(LinkedList<Entry> bucket, Object key) {
        // O(n) worst case: bucket contains n entries
        for (Entry e : bucket) {
            if (e.key.equals(key)) return e;
        }
        return null;
    }
}
