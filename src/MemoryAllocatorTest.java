import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class MemoryAllocatorTest extends TestCase {

    /**
     * Test all the MemoryAllocator methods
     */
    public void testMemoryAllocator() {

        MemoryAllocator alloc = new MemoryAllocator(2);
        String string1 = "DBZ";
        byte[] test1 = string1.getBytes();
        int sizeTest1 = test1.length;
        MemHandle handle1 = alloc.insert(test1, sizeTest1);
        assertEquals(0, handle1.getStartIndx());
        assertEquals(4, handle1.getSegmentSize());
        String string2 = "Castle in the Sky";
        byte[] test2 = string2.getBytes();
        int sizeTest2 = test1.length;
        MemHandle handle2 = alloc.insert(test2, sizeTest2);
        assertEquals(4, handle2.getStartIndx());
        assertEquals(4, handle2.getSegmentSize());
        assertEquals(0, alloc.getData(test1, sizeTest1, handle1));
        alloc.remove(handle1);
        assertEquals(-1, alloc.getData(test1, sizeTest1, handle1));
        assertEquals(0, alloc.getData(test2, sizeTest2, handle2));
        alloc.remove(handle2);
        assertEquals(-1, alloc.getData(test2, sizeTest2, handle2));

    }

}
