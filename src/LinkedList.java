/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 * 
 * @param <T>
 *            data type of the value, set as generic initially
 */
public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    /**
     * Constructer for Linked List class
     */
    public LinkedList() {
        head = new Node<T>(null);
        tail = head;
    }


    /**
     * Inserts data into the linked list
     * 
     * @param record
     *            record to be inserted
     */
    public void insert(T record) {
        Node<T> node = new Node<T>(record);
        Node<T> curr;
        if (head.getNext() == null) {
            head.setNext(node);
            tail = node;
        }
        else {
            curr = tail;
            curr.setNext(node);
            tail = node;
        }
    }


    /**
     * This method returns the number of nodes in the linked list. The count
     * does not include the head node.
     * 
     * @return int length of the linked list
     */
    public int getListLength() {
        int length = 0;
        Node<T> curr = head.getNext();
        while (curr != null) {
            length++;
            curr = curr.getNext();
        }
        return length;
    }


    /**
     * Remove the node from the linked list
     * 
     * @param record
     *            record to be removed
     */
    public void remove(T record) {
        Node<T> curr = head;
        Node<T> temp;
        while (curr.getNext() != null) {
            if (curr.getNext().getValue() == record) {
                if (curr.getNext() == tail) {
                    tail = curr;
                }
                temp = curr.getNext().getNext();
                curr.setNext(temp);
                break;
            }
            curr = curr.getNext();
        }
    }


    /**
     * delete the complete list
     */
    public void deleteList() {
        head.setNext(null);
        tail = head;
    }


    /**
     * Check if the list is empty
     * 
     * @return boolean check for empty list
     */
    public boolean empty() {
        return (head == tail);
    }


    /**
     * Getter function for the head of the list
     * 
     * @return Node<T> returns first node of the Linked list
     */
    public Node<T> getHead() {
        return head;
    }


    /**
     * Getter function for the tail of the list
     * 
     * @return Node<T> returns the last node of Linked List
     */
    public Node<T> getTail() {
        return tail;
    }

}
