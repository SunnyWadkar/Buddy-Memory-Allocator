import java.util.*;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 * 
 *          Memory Allocator based on the buddy system
 */
public class MemoryAllocator {

    private class AddressSpace {
        private int startIndx;
        private int endIndx;

        /**
         * Constructor for the AddressSpace class
         * 
         * @param start
         *            start index of AddressSpace
         * @param end
         *            end index of AddressSpace
         */
        private AddressSpace(int start, int end) {
            startIndx = start;
            endIndx = end;
        }


        /**
         * Comparator method for AddressSpace class
         */
        @Override
        public boolean equals(Object otherObj) {
            AddressSpace other = (AddressSpace)otherObj;
            return ((this.startIndx == other.startIndx)
                & (this.endIndx == other.endIndx));
        }
    }

    private byte[] memoryPool; // memory buffer
    private int freeListSize;
    private LinkedList<AddressSpace>[] freeList; // free list for buddy system
    private LinkedList<AddressSpace> allocatedList; // list to keep track of
                                                    // allocated memory spaces

    /**
     * Constructor for memory allocator class
     * 
     * @param poolsize
     *            maximum size of memory buffer
     */
    @SuppressWarnings("unchecked")
    public MemoryAllocator(int poolsize) {
        freeListSize = (int)(Math.ceil(Math.log(poolsize) / Math.log(2)));
        poolsize = 1 << freeListSize;
        memoryPool = new byte[poolsize];
        allocatedList = new LinkedList<AddressSpace>();
        freeList = new LinkedList[freeListSize + 1];
        for (int i = 0; i <= freeListSize; i++) {
            freeList[i] = new LinkedList<AddressSpace>();
        }
        AddressSpace initBlock = new AddressSpace(0, poolsize - 1);
        freeList[freeListSize].insert(initBlock);
    }


    /**
     * This method prints the free list of buddy system
     */
    public void printFreeList() {
        boolean free = false;
        for (int i = 0; i <= freeListSize; i++) {
            int[] elements;
            if (!freeList[i].empty()) {
                free = true;
                System.out.print((1 << i) + ": ");
                elements = new int[freeList[i].getListLength()];
                Node<AddressSpace> temp = freeList[i].getHead().getNext();
                int j = 0;
                while (temp != null) {
                    AddressSpace val = (AddressSpace)temp.getValue();
                    elements[j] = val.startIndx;
                    j++;
                    temp = temp.getNext();
                }
                Arrays.sort(elements);
                for (int k : elements) {
                    System.out.print(k + " ");
                }
                System.out.print("\n");
            }
        }
        if (!free) {
            System.out.println("No free blocks are available.");
        }
    }


    /**
     * Function to return the free address space from the free list
     * 
     * @param freeListIdx
     *            index of free list
     * @return free AddressSpace
     */
    private AddressSpace getFreeBlock(int freeListIdx) {
        AddressSpace retSpace = null;
        Node<AddressSpace> temp = freeList[freeListIdx].getHead().getNext();
        retSpace = temp.getValue();
        temp = temp.getNext();
        while (temp != null) {
            if (temp.getValue().startIndx < retSpace.startIndx) {
                retSpace = temp.getValue();
            }
            temp = temp.getNext();
        }
        freeList[freeListIdx].remove(retSpace);
        return retSpace;
    }


    /**
     * Method to create free address spaces for the given index. Here index is
     * the log of the memory required.
     * 
     * @param idx
     *            index of free block to be created
     * @return operation status. Success : 0 , Failure : -1
     */
    private int createFreeBlocks(int idx) {
        int availableBlock = -1;
        AddressSpace freeBlock;
        for (int i = idx + 1; i <= freeListSize; i++) {
            if (!freeList[i].empty()) {
                availableBlock = i;
                break;
            }
        }
        if (availableBlock > 0) {
            for (int j = availableBlock; j > idx; j--) {
                freeBlock = getFreeBlock(j);
                int halfIndx = freeBlock.startIndx + (freeBlock.endIndx
                    - freeBlock.startIndx) / 2;
                freeList[j - 1].insert(new AddressSpace(freeBlock.startIndx,
                    halfIndx));
                freeList[j - 1].insert(new AddressSpace(halfIndx + 1,
                    freeBlock.endIndx));
            }
            return 0;
        }
        else {
            return -1;
        }
    }


    /**
     * Method to merge the adjacent free blocks. The blocks which are formed
     * from dividing the larger memory block can only be merged
     * 
     * @param idx
     *            freelist index
     */
    private void mergeFreeBlocks(int idx) {
        for (int i = idx; i < freeListSize; i++) {
            if (!freeList[i].empty()) {
                int segmentSize = (1 << i);
                AddressSpace[] nodes = new AddressSpace[freeList[i]
                    .getListLength()];
                int j = 0;
                Node<AddressSpace> temp = freeList[i].getHead().getNext();
                while (temp != null) {
                    nodes[j] = (AddressSpace)temp.getValue();
                    temp = temp.getNext();
                    j++;
                }
                int adjacentSpace;
                for (int k = 0; k < nodes.length; k++) {
                    if ((nodes[k].startIndx / segmentSize) % 2 == 0) {
                        adjacentSpace = nodes[k].startIndx + segmentSize;
                    }
                    else {
                        adjacentSpace = nodes[k].startIndx - segmentSize;
                    }
                    for (int l = 0; l < nodes.length; l++) {
                        if ((nodes[k].startIndx < nodes[l].startIndx)
                            & (nodes[l].startIndx == adjacentSpace)) {
                            freeList[i].remove(nodes[k]);
                            freeList[i].remove(nodes[l]);
                            AddressSpace newNode = new AddressSpace(
                                nodes[k].startIndx, nodes[l].endIndx);
                            // System.out.println("Adding Memory from: "+
                            // newNode.startIndx +" to "+ newNode.endIndx +" to
                            // index "+(i+1));
                            freeList[i + 1].insert(newNode);
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * Method to extend the memory buffer by a factor of 2.
     */
    @SuppressWarnings("unchecked")
    private void extendMemory() {
        int originalSize = memoryPool.length;
        int newSize = originalSize * 2;
        byte[] tempStore = new byte[originalSize];
        System.arraycopy(memoryPool, 0, tempStore, 0, originalSize);
        memoryPool = new byte[newSize];
        System.arraycopy(tempStore, 0, memoryPool, 0, originalSize);
        LinkedList<AddressSpace>[] tempFreeList = new LinkedList[freeListSize
            + 1];
        System.arraycopy(freeList, 0, tempFreeList, 0, freeList.length);
        freeListSize = (int)(Math.ceil(Math.log(newSize) / Math.log(2)));
        freeList = new LinkedList[freeListSize + 1];
        for (int i = 0; i <= freeListSize; i++) {
            freeList[i] = new LinkedList<AddressSpace>();
        }
        System.arraycopy(tempFreeList, 0, freeList, 0, tempFreeList.length);
        AddressSpace spaceBlock = new AddressSpace(originalSize, newSize - 1);
        freeList[freeListSize - 1].insert(spaceBlock);
        mergeFreeBlocks(freeListSize - 1);
        System.out.println("Memory pool expanded to be " + memoryPool.length
            + " bytes.");
    }


    /**
     * Insert function for the memory buffer. If required memory is not
     * available, then the memory is extended.
     * 
     * @param space
     *            data buffer to be inserted. A byte array is expected.
     * @param size
     *            size of data buffer
     * @return MemHandle reference to allocated space
     */
    public MemHandle insert(byte[] space, int size) {
        MemHandle handle = null;
        int requiredBlock = (int)Math.ceil((Math.log(size) / Math.log(2)));
        AddressSpace allocatedSpace;
        while (requiredBlock > freeListSize) {
            extendMemory();
        }
        while (freeList[requiredBlock].empty()) {
            if (createFreeBlocks(requiredBlock) != 0) {
                extendMemory();
            }
        }
        allocatedSpace = getFreeBlock(requiredBlock);
        // System.out.println("Allocated Memory from: "+
        // allocatedSpace.startIndx +" to "+ allocatedSpace.endIndx);
        allocatedList.insert(allocatedSpace);
        System.arraycopy(space, 0, memoryPool, allocatedSpace.startIndx, size);
        handle = new MemHandle(allocatedSpace.startIndx, (1 << requiredBlock));
        return handle;
    }


    /**
     * Remove method for memory allocator. This method frees the memory blocks
     * and merges them back into free list.
     * 
     * @param handle
     *            reference to memory to be deallocated
     */
    public void remove(MemHandle handle) {
        Node<AddressSpace> temp = allocatedList.getHead().getNext();
        boolean deleteRecord = false;
        AddressSpace curr = null;
        AddressSpace addr = new AddressSpace(handle.getStartIndx(), (handle
            .getStartIndx() + handle.getSegmentSize() - 1));
        while (temp != null) {
            curr = temp.getValue();
            if (curr.equals(addr)) {
                deleteRecord = true;
                break;
            }
            temp = temp.getNext();
        }
        if (deleteRecord) {
            int blockSize = (curr.endIndx - curr.startIndx) + 1;
            int blockIdx = (int)(Math.log(blockSize) / Math.log(2));
            freeList[blockIdx].insert(curr);
            allocatedList.remove(curr);
            // System.out.println("Deallocated Memory from: "+ curr.startIndx +"
            // to "+ curr.endIndx);
            mergeFreeBlocks(blockIdx);
        }
    }


    /**
     * Data retrieval method for memory pool. This function copies the stored
     * data associated with the passed handle, into user space buffer.
     * 
     * @param space
     *            data buffer
     * @param size
     *            size of data buffer
     * @param handle
     *            reference to memory
     * @return int status of operation. Success : 0, Failure : -1
     */
    public int getData(byte[] space, int size, MemHandle handle) {
        Node<AddressSpace> temp = allocatedList.getHead().getNext();
        boolean found = false;
        AddressSpace curr = null;
        AddressSpace addr = new AddressSpace(handle.getStartIndx(), (handle
            .getStartIndx() + handle.getSegmentSize() - 1));
        while (temp != null) {
            curr = temp.getValue();
            if (curr.equals(addr)) {
                found = true;
                break;
            }
            temp = temp.getNext();
        }
        if (found) {
            System.arraycopy(memoryPool, curr.startIndx, space, 0, size);
            return 0;
        }
        else {
            return -1;
        }
    }

}
