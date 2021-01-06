/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 *
 * @param <T>
 *            data type of the value, set as generic initially
 */
public class Node<T> {
    private T value;
    private Node<T> next;

    /**
     * Constructor for Node class
     * 
     * @param record
     *            record to be inserted
     */
    public Node(T record) {
        this.value = record;
        this.next = null;
    }


    /**
     * Getter for Node Value
     * 
     * @return T retrieves record saved in the node
     */
    public T getValue() {
        return this.value;
    }


    /**
     * Setter for node value
     * 
     * @param value
     *            setter for record to be saved in the node
     */
    public void setValue(T value) {
        this.value = value;
    }


    /**
     * Getter for next field of node
     * 
     * @return Node<T> retreives the next node
     */
    public Node<T> getNext() {
        return this.next;
    }


    /**
     * Setter for next field of node
     * 
     * @param next
     *            setter for the next node
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
