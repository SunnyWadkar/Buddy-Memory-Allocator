import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class MemHandleTest extends TestCase {

    /**
     * Test for all methods of MemHandle class
     */
    public void testMemHandle() {
        MemHandle m1 = new MemHandle(0, 32);
        assertEquals(m1.getStartIndx(), 0);
        assertEquals(m1.getSegmentSize(), 32);
        m1.setStartIndx(1);
        assertEquals(m1.getStartIndx(), 1);
        m1.setSegmentSize(64);
        assertEquals(m1.getSegmentSize(), 64);
    }

}
