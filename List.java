/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }


    
    /** Returns the first element in the list */
    public CharData getFirst() {
        return first.cp;
    }

    /*** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        if (first == null) {
            first = new Node(new CharData(chr));
        } else {
            Node temp = first;
            first = new Node(new CharData(chr));
            first.next = temp;
        }
        size++;
    }
    
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        if (size == 0) {
            return "()";
        }
        Node temp = first;
        StringBuilder stringB = new StringBuilder("(");
        while (temp != null) {
            stringB.append(temp.cp + " ");
            temp = temp.next;
        }
        String re = stringB.substring(0, stringB.length() - 1) + ")";
        return re;
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        Node temp = first;
        int counter = 0;
        while (temp != null) {
            if (temp.cp.equals(chr)) {
                return counter;
            }
            temp = temp.next;
            counter++;
        }
        return -1;
    }


    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
        Node temp = first;
        boolean i = false;
    
        while (temp != null) {
            if (temp.cp.chr == chr) {
                temp.cp.count++;
                i = true;
                break;
            }
            temp = temp.next;}
    
        if (!i) {
            addFirst(chr);
        }
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        Node temp = first;
        boolean i = false;
    
        if (temp != null && temp.equals(chr)) {
            first = temp.next;
            size--;
            i = true;
        } else {
            while (temp != null && temp.next != null) {
                if (temp.next.equals(chr)) {
                    temp.next = temp.next.next;
                    size--;
                    i = true;
                    break;
                }
                temp = temp.next;
            }
        }
        return i;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {    
        Node temp = first;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);}

        else{
        for (int i = 0; i < index; i++) {
            temp = temp.next;
            }
        }
        return temp.cp;
       

    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}
