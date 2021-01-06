import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 *
 *          Test for the Node Class
 */
public class NodeTest extends TestCase {

    /**
     * test for all the methods of the node class
     */
    public void testNode() {
        Node<Integer> node = new Node<Integer>(2);
        assertEquals((int)node.getValue(), 2);
        assertNull(node.getNext());
        Node<Integer> node2 = new Node<Integer>(8);
        node.setNext(node2);
        assertNotNull(node.getNext());
        node.setValue(5);
        assertEquals((int)node.getValue(), 5);
        node = node.getNext();
        assertEquals((int)node.getValue(), 8);

    }

}
