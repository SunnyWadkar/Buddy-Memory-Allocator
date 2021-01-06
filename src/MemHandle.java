/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 * 
 *          Memory handle class for the data stored by memory allocator
 */
public class MemHandle {
    private int startIndx;
    private int segmentSize;

    /**
     * Constructor for Memory Handle
     * 
     * @param index
     *            starting index of allocated memory
     * @param size
     *            allocated memory size
     */
    public MemHandle(int index, int size) {
        startIndx = index;
        segmentSize = size;
    }


    /**
     * Getter for start index of allocated memory
     * 
     * @return int starting index of allocated memory
     */
    public int getStartIndx() {
        return startIndx;
    }


    /**
     * Setter for start index of allocated memory
     * 
     * @param startIndx
     *            starting index of allocated memory
     */
    public void setStartIndx(int startIndx) {
        this.startIndx = startIndx;
    }


    /**
     * Getter for segment size of the allocated memory
     * 
     * @return int retrieve allocated memory size
     */
    public int getSegmentSize() {
        return segmentSize;
    }


    /**
     * Setter for the segment size of the allocated memory
     * 
     * @param segmentSize
     *            setter for allocated memory size
     */
    public void setSegmentSize(int segmentSize) {
        this.segmentSize = segmentSize;
    }
}
