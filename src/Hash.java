/**
 * Stub for hash table class
 *
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */

public class Hash {
    private class Record {
        private int recordSize;
        private String recordName;
        private MemHandle recordHandle;

        /**
         * 
         * @param size
         *            size of the record
         * @param name
         *            canonical name of the record
         * @param handle
         *            memory reference to the record
         */
        private Record(int size, String name, MemHandle handle) {
            this.recordSize = size;
            this.recordHandle = handle;
            this.recordName = name;
        }
    }

    private Record[] hashTable;
    private int hashTableSize;
    private MemoryAllocator hashStorage;
    private int allocationCount;
    private double extensionThreshold;

    /**
     * Constructor for Hash Table
     * 
     * @param memory
     *            size of the memory pool
     * @param tableSize
     *            size of hashtable
     * @param load
     *            hashtable resize threshold
     */
    public Hash(int memory, int tableSize, double load) {
        hashTableSize = tableSize;
        allocationCount = 0;
        extensionThreshold = load;
        hashStorage = new MemoryAllocator(memory);
        hashTable = new Record[hashTableSize];
        for (int i = 0; i < hashTableSize; i++) {
            hashTable[i] = null;
        }
    }


    /**
     * Extend hashtable routine. This function extends the hashtable by factor
     * of 2.
     */
    private void extendHashTable() {
        int originalSize = hashTableSize;
        int newSize = hashTableSize * 2;
        hashTableSize = newSize;
        Record[] tmp = new Record[originalSize];
        for (int i = 0; i < originalSize; i++) {
            tmp[i] = hashTable[i];
        }
        hashTable = new Record[newSize];
        for (int j = 0; j < newSize; j++) {
            hashTable[j] = null;
        }
        int idx;
        for (int k = 0; k < originalSize; k++) {
            if (tmp[k] != null) {
                idx = h(tmp[k].recordName, newSize);
                if (hashTable[idx] != null) {
                    idx = resolveCollison(idx);
                }
                hashTable[idx] = tmp[k];
            }
        }
        System.out.println("Name hash table size doubled to " + newSize
            + " slots.");
    }


    /**
     * his function resolves the hash collision and returns a new key. Quadratic
     * probing is used to resolve the hash collision.
     * 
     * @param key
     *            hash collision key to be resolved
     * @return resolved key
     * 
     */
    private int resolveCollison(int key) {
        int keyResolve = -1;
        int newKey;
        for (int i = 1; i < hashTableSize; i++) {
            newKey = (key + (i * i)) % hashTableSize;
            if (hashTable[newKey] == null) {
                keyResolve = newKey;
                break;
            }
        }
        return keyResolve;
    }


    /**
     * his function searches if the record is present and its corresponding key.
     * 
     * @param key
     *            record key to be searched
     * @param recordName
     *            record name to be searched
     * @return resolved key
     * 
     */
    private int findRecordKey(int key, String recordName) {
        int keyResolve = -1;
        boolean isRecordPresent = false;
        Record temp = hashTable[key];
        if ((temp != null) && (temp.recordName.equals(recordName))) {
            isRecordPresent = true;
            keyResolve = key;
        }
        else {
            for (int i = 1; i < hashTableSize; i++) {
                keyResolve = (key + (i * i)) % hashTableSize;
                temp = hashTable[keyResolve];
                if ((temp != null) && (temp.recordName.equals(recordName))) {
                    isRecordPresent = true;
                    break;
                }
            }
        }
        return isRecordPresent ? keyResolve : -1;
    }


    /**
     * Insert method for hash table. This function also invokes hash table
     * extension when the hash capacity passes certain threshold.
     * 
     * @param data
     *            record to be inserted
     * @return operation status
     * 
     */
    public int insert(String data) {
        int key = h(data, hashTableSize);
        if (findRecordKey(key, data) >= 0) {
            System.out.println("|" + data
                + "| duplicates a record already in the Name database.");
            return -1;
        }
        byte[] dataBytes = data.getBytes();
        MemHandle handle = hashStorage.insert(dataBytes, dataBytes.length);
        Record record = new Record(dataBytes.length, data, handle);
        allocationCount++;
        double tableUsage = (double)allocationCount / (double)hashTableSize;
        if (tableUsage > extensionThreshold) {
            extendHashTable();
            key = h(data, hashTableSize);
        }
        if (hashTable[key] != null) {
            key = resolveCollison(key);
        }
        hashTable[key] = record;
        System.out.println("|" + data + "|"
            + " has been added to the Name database.");
        return 0;
    }


    /**
     * The delete routine for the hash table. It sets the hash slot as null to
     * indicate that the hash entry is empty.
     * 
     * @param data
     *            record to be deleted
     * @return in operation status
     */
    public int delete(String data) {
        int key = h(data, hashTableSize);
        key = findRecordKey(key, data);
        if (key < 0) {
            System.out.println("|" + data + "|"
                + " not deleted because it does "
                + "not exist in the Name database.");
            return -1;
        }
        Record record = hashTable[key];
        hashStorage.remove(record.recordHandle);
        hashTable[key] = null;
        System.out.println("|" + data + "|"
            + " has been deleted from the Name database.");
        allocationCount--;
        return 0;
    }


    /**
     * This method prints out the hashtable. The format is |<Record Name>| hash
     * key
     */
    public void printHashTable() {
        int numRecords = 0;
        Record record;
        for (int i = 0; i < hashTableSize; i++) {
            if (hashTable[i] != null) {
                numRecords++;
                record = hashTable[i];
                byte[] data = new byte[record.recordSize];
                hashStorage.getData(data, record.recordSize,
                    record.recordHandle);
                String recordName = new String(data);
                String[] out = recordName.split("<SEP>");
                System.out.println("|" + out[0] + "| " + i);
            }
        }
        System.out.println("Total records: " + numRecords);
    }


    /**
     * update add routine for hash table. This routine checks if the record is
     * present and updates the fields of record.
     * The record is first deleted and then replaces with the updated record.
     * The fields are separated by <SEP>.
     * 
     * @param recordName
     *            name of the record to be updated
     * @param data
     *            key-value pair for the records separated by <SEP>
     * @return int operation status
     */
    public int addRecordFields(String recordName, String data) {
        Record record;
        int key = h(recordName, hashTableSize);
        key = findRecordKey(key, recordName);
        if (key < 0) {
            System.out.println("|" + recordName + "|"
                + " not updated because it does "
                + "not exist in the Name database.");
            return -1;
        }
        record = hashTable[key];
        byte[] storedData = new byte[record.recordSize];
        hashStorage.getData(storedData, record.recordSize, record.recordHandle);
        String str = new String(storedData);
        String[] strContents = str.split("<SEP>");
        String[] fieldsToAdd = data.split("<SEP>");
        for (int i = 0; i < fieldsToAdd.length; i = i + 2) {
            for (int j = 1; j < strContents.length; j = j + 2) {
                if (fieldsToAdd[i].equals(strContents[j])) {
                    strContents[j] = null;
                    strContents[j + 1] = null;
                    break;
                }
            }
        }
        String newStr = strContents[0];
        for (int k = 1; k < strContents.length; k++) {
            if (strContents[k] != null) {
                newStr = newStr + "<SEP>" + strContents[k];
            }
        }
        for (int l = 0; l < fieldsToAdd.length; l++) {
            if (fieldsToAdd[l] != null) {
                newStr = newStr + "<SEP>" + fieldsToAdd[l];
            }
        }
        hashStorage.remove(record.recordHandle);
        byte[] dataBytes = newStr.getBytes();
        MemHandle handle = hashStorage.insert(dataBytes, dataBytes.length);
        record = new Record(dataBytes.length, recordName, handle);
        hashTable[key] = record;
        System.out.println("Updated Record: |" + newStr + "|");
        return 0;
    }


    /**
     * update delete routine for hash table. This routine checks if the record
     * is present and deletes the fields of record.
     * The record is first deleted and then replaces with the updated record.
     * The fields are separated by <SEP>.
     * 
     * @param recordName
     *            name of the record to be updated
     * @param data
     *            keys for the records separated by <SEP>
     * @return int operation status
     */
    public int deleteRecordFields(String recordName, String data) {
        Record record;
        int key = h(recordName, hashTableSize);
        key = findRecordKey(key, recordName);
        if (key < 0) {
            System.out.println("|" + recordName + "|"
                + " not updated because it does "
                + "not exist in the Name database.");
            return -1;
        }
        record = hashTable[key];
        byte[] storedData = new byte[record.recordSize];
        hashStorage.getData(storedData, record.recordSize, record.recordHandle);
        String str = new String(storedData);
        String[] strContents = str.split("<SEP>");
        String[] fieldsToDelete = data.split("<SEP>");
        boolean found = false;
        for (int i = 0; i < fieldsToDelete.length; i++) {
            for (int j = 1; j < strContents.length; j = j + 2) {
                if (fieldsToDelete[i].equals(strContents[j])) {
                    strContents[j] = null;
                    strContents[j + 1] = null;
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("|" + recordName
                + "| not updated because the field |" + data
                + "| does not exist");
            return -2;
        }
        String newStr = strContents[0];
        for (int k = 1; k < strContents.length; k++) {
            if (strContents[k] != null) {
                newStr = newStr + "<SEP>" + strContents[k];
            }
        }
        hashStorage.remove(record.recordHandle);
        byte[] dataBytes = newStr.getBytes();
        MemHandle handle = hashStorage.insert(dataBytes, dataBytes.length);
        record = new Record(dataBytes.length, recordName, handle);
        hashTable[key] = record;
        System.out.println("Updated Record: |" + newStr + "|");
        return 0;
    }


    /**
     * Returns the key for the string data.
     * 
     * @param data
     *            input record
     * @return int hash of the record
     */
    public int getHashKey(String data) {
        return h(data, hashTableSize);
    }


    /**
     * This method prints the free list of associated memory allocator.
     */
    public void printFreeMemory() {
        hashStorage.printFreeList();
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    // You can make this private in your project.
    // This is public for distributing the hash function in a way
    // that will pass milestone 1 without change.
    private int h(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % m);
    }
}
