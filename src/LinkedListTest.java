import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */

public class LinkedListTest extends TestCase {

    /**
     * Test for the all the methods of the Linked List class
     */
    public void testLL() {
        LinkedList<String> ll = new LinkedList<String>();
        assertEquals(ll.getListLength(), 0);
        assertNull(ll.getHead().getValue());
        assertEquals(ll.getHead(), ll.getTail());
        ll.insert("Head");
        assertEquals(ll.getTail().getValue(), "Head");
        ll.insert("pop");
        assertEquals(ll.getListLength(), 2);
        ll.remove("Head");
        assertEquals(ll.empty(), false);
        ll.deleteList();
        ll.remove("A");
        assertEquals(ll.empty(), true);
        assertNull(ll.getTail().getValue());

    }
}
