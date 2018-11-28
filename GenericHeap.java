import java.util.HashMap;
import java.util.ArrayList;
/**
 * Lab 4: Generics <br />
 * The {@code GenericHeap} class
 */
public class GenericHeap<K extends Comparable<K>, V> {
	
	// Use to map keys to values
	HashMap<K,V> map;
	// Use to sort
	ArrayList<K> heap;
	
	// Constructor
	public GenericHeap() {
		this.map = new HashMap<K, V>();
		this.heap = new ArrayList<K>();
	}
	
	// Heapify down
	private void heapifyDown(ArrayList<K> heap, int currentPoint, int end) {
    	int leftChild = currentPoint*2+1;
    	int rightChild = currentPoint*2+2;

    	int largest = currentPoint;
    	
    	if (leftChild < end && heap.get(leftChild).compareTo(heap.get(largest)) > 0) {
    		largest = leftChild;
    	}
    	
    	if (rightChild < end && heap.get(rightChild).compareTo(heap.get(largest)) > 0) {
    		largest = rightChild;
    	}
    	
    	if (largest != currentPoint) {
    		swap(heap, largest, currentPoint);
    		heapifyDown(heap,largest,end);
    	}
    }
	
	// Heapify up
	private void heapifyUp(ArrayList<K> heap, int currentPoint) {
		int parent = (currentPoint-1)/2;
		if (currentPoint != 0) {
			if (heap.get(currentPoint).compareTo(heap.get(parent)) > 0) {
				swap(heap, currentPoint, parent);
				heapifyUp(heap, parent);
			}
		}
	}
	
	// Swap
	private void swap(ArrayList<K> heap, int i, int j) {
		K temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	
	// toString function
	public String toString() {
		String return_string = "";
		if (!heap.isEmpty()) {
			return_string += this.map.get(this.heap.get(0)).toString();
		}
		for (int i = 1; i < heap.size(); i++) {
			return_string += (" " + this.map.get(this.heap.get(i)).toString());
		}
		return return_string;
	}
	
	// pop function
	public V pop() {
		int size = heap.size();
		// Get the value of the root
		V pop_value = map.get(heap.get(0));
		// Move last node to root and heapify the tree
		swap(this.heap, 0, size-1);
		// Delete the previous root (keep mapping - may have duplicates)
		heap.remove(size-1);
		heapifyDown(this.heap, 0, size-1);
		return pop_value;
	}
	
    /**
     * Insert an new element to the heap
     * @param key       {@code K} the comparable key of the new element
     * @param value     {@code V} the actual value of the new element
     */
    public void insert(K key, V value) {
        // TODO: Lab 4 Part 2-1 -- GenericHeap, add new element
        this.map.put(key, value);
        this.heap.add(key);
        heapifyUp(this.heap, this.heap.size()-1);
    }

    /**
     * The heap sort procedure
     * @param array     {@code <E extends Comparable<E>>[]} the array to be sorted
     * @return          {@code <E extends Comparable<E>>[]} the sorted array
     */
    public static <E extends Comparable<E>> E[] heapSort(E[] array) {
        // TODO: Lab 4 Part 2-4 -- GenericHeap, return a sorted array
        GenericHeap<E, E> heap = new GenericHeap<E, E>();
        // Create a heap to sort
        for (E element : array) {
        	heap.insert(element, element);
        }
        // Largest to smallest
        for (int i = 0; i < array.length; i++) {
        	array[i] = heap.pop();
        }
        // Smallest to largest
        /*
        for (int i = array.length-1; i > -1; i--) {
        	array[i] = heap.pop();
        }
        */
        return array;
    }

    /**
     * Main entry: test the HeapSort
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        // Sort an array of integers
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (int) (Math.random() * 200);
        heapSort(numbers);
        for (int n: numbers)
            System.out.print(n + " ");
        System.out.println();

        // Sort an array of strings
        String[] strs = new String[10];
        for (int i = 0; i < strs.length; i++)
            strs[i] = String.format("%c", (int) (Math.random() * 26 + 65));
        heapSort(strs);
        for (String s: strs)
            System.out.print(s + " ");
        System.out.println();
        
        GenericHeap<Integer, String> heap = new GenericHeap<Integer, String>();
        System.out.println("Hello : 8");
        heap.insert(8, "Hello");
        System.out.println("Bob : 1");
        heap.insert(1, "Bob");
        System.out.println("Alice : 10");
        heap.insert(10, "Alice");
        System.out.println("Eve : 16");
        heap.insert(16, "Eve");
        System.out.println("Fiend : 3");
        heap.insert(3, "Friend");
        System.out.println("Dave : 5");
        heap.insert(5, "Dave");
        System.out.println(heap.toString());
    }

}
