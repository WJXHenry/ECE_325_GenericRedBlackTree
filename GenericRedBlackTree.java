/**
 * Lab 4: Generics <br />
 * The {@code GenericRedBlackTree} class <br />
 * Reference: <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">
 *              https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 *            </a>
 */
public class GenericRedBlackTree<K extends Comparable<K>, V> {

    /**
     * Root node of the red black tree
     */
    private Node root = null;

    /**
     * Size of the tree
     */
    private int size = 0;

    /**
     * Search for the node by key, and return the corresponding value
     * @param key       {@code K} the key for searching
     * @return          {@code V} the value of the node, or {@code NULL} if not found
     */
    public V find(K key) {
        // TODO: Lab 4 Part 3-1 -- find an element from the tree
        return find(this.root, key);
    }
    
    // Recursively searches RBT for key
    private V find(Node node, K key) {
    	// Base case (node is null - value does not exist in tree)
    	if (node == null || node.key == null) {
    		return null;
    	}
    	// If the current node is the value searched for
    	if (node.key.compareTo(key) == 0) {
    		return node.value;
    	}
    	// If current node has value less than value searched for (go right)
    	if (node.key.compareTo(key) < 0) {
    		return find(node.rChild, key);
    	}
    	// The current node has value greater than value searched for (go left)
    	return find(node.lChild, key);
    }

    // Searches the tree for the key and returns the node with that key
    // (returns null otherwise)
    private Node findKeyNode(K key) {
    	return findKeyNode(this.root, key);
    }
    
    // Recursively searches RBT for key
    private Node findKeyNode(Node node, K key) {
    	// Base case (node is null - value does not exist in tree)
    	if (node == null || node.key == null) {
    		return null;
    	}
    	// If the current node is the value searched for
    	if (node.key.compareTo(key) == 0) {
    		return node;
    	}
    	// If current node has value less than value searched for (go right)
    	if (node.key.compareTo(key) < 0) {
    		return findKeyNode(node.rChild, key);
    	}
    	// The current node has value greater than value searched for (go left)
    	return findKeyNode(node.lChild, key);
    }
    
    /**
     * Insert an element to the tree
     * @param key       {@code K} the key of the new element
     * @param value     {@code V} the value of the new element
     */
    public void insert(K key, V value) {
        // TODO: Lab 4 Part 3-2 -- insert an element into the tree
    	// Insert the value into the tree
    	loopInsert(key, value);
    	// Fix colour of tree
    	fixInsColour(this.root);
    	// Increment tree size
    	this.size += 1;
    }

 // Insert integer (value) into the tree
    private void loopInsert(K key, V value) {
    	// Init the new node to be added
    	Node newNode = new Node(key, value);
    	newNode.color = Node.RED;
    	// Empty tree
    	if (this.root == null) {
    		this.root = newNode;
    		return;
    	}
    	Node currentNode = this.root;
    	while (currentNode.value != null) {
    		// Go left
    		if (currentNode.key.compareTo(key) > 0) {
    			currentNode = currentNode.lChild;
    		}
    		// Go right
    		else if (currentNode.key.compareTo(key) < 0) {
    			currentNode = currentNode.rChild;
    		}
    		// Value is current value - do not add node
    		else {
    			return;
    		}
    	}
    	// Add new node
    	Node parent = getParent(currentNode);
    	// Assign child node to parent
		if (isLChild(currentNode)) {
			parent.lChild = newNode;
		} else {
			parent.rChild = newNode;
		}
    	// Assign parent to new node
    	newNode.parent = parent;
		// Fix RBT colour property
		fixInsColour(newNode);
    }
    
    /**
     * Fix the colour properties of the RBT after insertion
     * @param node		{@code Node}
     */
    private void fixInsColour(Node node) {
    	// Case 1
    	if (getParent(node) == null) {
    		node.color = Node.BLACK;
    		return;
    	}
    	// Case 2: do nothing if valid
    	else if (getParent(node).color == Node.BLACK || getUncle(node) == null) {
    		return;
    	}
    	// Case 3
    	else if (getParent(node).color == Node.RED && getUncle(node).color == Node.RED) {
    		getParent(node).color = Node.BLACK;
    		getUncle(node).color = Node.BLACK;
    		getGrandparent(node).color = Node.RED;
    		fixInsColour(getGrandparent(node));
    		return;
    	}
    	// Case 4
    	else if (getParent(node).color == Node.RED && getUncle(node).color == Node.BLACK) {
    		if (isRChild(node) && isLChild(getParent(node))) {
    			rotateLeft(node);
    			node = node.lChild;
    		} else if (isLChild(node) && isRChild(getParent(node))) {
    			rotateRight(node);
    			node = node.rChild;
    		}
    		// Case 5
    		getParent(node).color = Node.BLACK;
    		getGrandparent(node).color = Node.RED;
    		if (isLChild(node)) {
    			rotateRight(getParent(node));
    			return;
    		} else {
    			rotateLeft(getParent(node));
    			return;
    		}
    	}
    	// Recurse through rest of the tree
    	if (getParent(node) != null) {
    		fixInsColour(getParent(node));
    	}
    }
    
    // Left rotation function to be done on node
    private void rotateLeft(Node node) {
    	Node parent = getParent(node);
    	Node grandparent = getGrandparent(node);
    	parent.rChild = node.lChild;
    	if (node.lChild != null) {
    		node.lChild.parent = parent;
    	}
    	if (grandparent != null) {
    		node.parent = grandparent;
        	if (isLChild(parent)) {
        		grandparent.lChild = node;
        	} else {
        		grandparent.rChild = node;
        	}
    	} else {
    		// Case when node becomes the root (no grandparent)
    		node.parent = null;
    		this.root = node;
    	}
    	node.lChild = parent;
    	parent.parent = node;
    }
    
    // Right rotation function to be done on node
    private void rotateRight(Node node) {
    	Node parent = getParent(node);
    	Node grandparent = getGrandparent(node);
    	parent.lChild = node.rChild;
    	if (node.rChild != null) {
    		node.rChild.parent = parent;
    	}
    	if (grandparent != null) {
    		node.parent = grandparent;
        	if (isLChild(parent)) {
        		grandparent.lChild = node;
        	} else {
        		grandparent.rChild = node;
        	}
    	} else {
    		// Case when node becomes the root (no grandparent)
    		node.parent = null;
    		this.root = node;
    	}
    	node.rChild = parent;
    	parent.parent = node;
    }
    
    // Check if the node is a left child
    private boolean isLChild(Node node) {
    	// If there is no parent, not a child
    	if (getParent(node) == null) {
    		return false;
    	}
    	if (node == getParent(node).lChild) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // Check if the node is a right child
    private boolean isRChild(Node node) {
    	// If there is no parent, not a child
    	if (getParent(node) == null) {
    		return false;
    	}
    	if (node == getParent(node).rChild) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // Returns the parent of the specified node or null if not exist
    private Node getParent(Node node) {
    	return node.parent;
    } 
    
    // Returns the grandparent of the specified node or null if not exist
    private Node getGrandparent(Node node) {
    	Node parent = getParent(node);
    	// Return null
    	if (parent == null) {
    		return null;
    	}
    	// Return parent of parent (grandparent)
    	return getParent(parent);
    }
    
    // Returns the sibling of the node or null if not exist
    private Node getSibling(Node node) {
    	Node parent = getParent(node);
    	// No parent = no sibling
    	if (parent == null) {
    		return null;
    	}
    	if (node == parent.lChild) {
    		return parent.rChild;
    	} else {
    		return parent.lChild;
    	}
    }
    
    // Returns the uncle of the specified node or null if not exist
    private Node getUncle(Node node) {
    	Node parent = getParent(node);
    	Node grandparent = getGrandparent(node);
    	// No grandparent = no uncle
    	if (grandparent == null) {
    		return null;
    	}
    	return getSibling(parent);
    }
    
    // Swap two nodes' values with each other
    private void swap(Node node1, Node node2) {
    	K temp_key = node1.key;
    	V temp_value = node1.value;
    	node1.key = node2.key;
    	node1.value = node2.value;
    	node2.key = temp_key;
    	node2.value = temp_value;
    }
    
    // Find the smallest node in the subtree with node as root
    private Node findSmallest(Node node) {
    	while (node.lChild.key != null) {
    		node = node.lChild;
    	}
    	return node;
    }
    
    /**
     * Fix the colour properties of the RBT after deletion
     * @param node		{@code Node}
     */
    private void fixDelColour(Node node) {
    	Node parent = getParent(node);
    	Node sibling = getSibling(node);
    	// Case 1 node is the root
    	if (parent == null) {
    		return;
    	}
    	// Case 2
    	if (sibling.color == Node.RED) {
    		parent.color = Node.RED;
    		sibling.color = Node.BLACK;
    		if (isLChild(node)) {
    			rotateLeft(sibling);
    		} else {
    			rotateRight(sibling);
    		}
    		// Update sibling
    		sibling = getSibling(node);
    	}
    	// Case 3
    	if (parent.color == Node.BLACK
    			&& sibling.key != null
    			&& sibling.color == Node.BLACK
    			&& sibling.lChild.color == Node.BLACK
    			&& sibling.rChild.color == Node.BLACK) {
    		sibling.color = Node.RED;
    		fixDelColour(parent);
    	}
    	// Case 4
    	if (parent.color == Node.RED
    			&& sibling.key != null
    			&& sibling.color == Node.BLACK
    			&& sibling.lChild.color == Node.BLACK
    			&& sibling.rChild.color == Node.BLACK) {
    		sibling.color = Node.RED;
    		parent.color = Node.BLACK;
    		return;
    	}
    	// Case 5
    	if (sibling.key != null && sibling.color == Node.BLACK) {
    		sibling.color = Node.RED;
    		if (isLChild(node)
    				&& sibling.lChild.color == Node.RED
    				&& sibling.rChild.color == Node.BLACK) {
    			sibling.lChild.color = Node.BLACK;
    			rotateRight(sibling.lChild);
    			// Update sibling
    			sibling = getSibling(node);
    		} else if (isRChild(node)
    				&& sibling.lChild.color == Node.BLACK
    				&& sibling.rChild.color == Node.RED) {
    			sibling.rChild.color = Node.BLACK;
    			rotateLeft(sibling.rChild);
    			// Update sibling
    			sibling = getSibling(node);
    		}
    	}
    	// Case 6
    	if (sibling.key != null) {
    		// Edge case (if only 2 nodes left)
    		if (this.size <= 2) {
    			return;
    		}
    		sibling.color = parent.color;
        	parent.color = Node.BLACK;
        	if (isLChild(node)) {
        		sibling.rChild.color = Node.BLACK;
        		rotateLeft(sibling);
        	} else {
        		sibling.lChild.color = Node.BLACK;
        		rotateRight(sibling);
        	}
    	}
    }
    
    /**
     * Remove an element from the tree
     * @param key       {@code K} the key of the element
     * @return          {@code V} the value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 4 Part 3-3 -- remove an element from the tree
    	// Find the node to delete
    	Node node = findKeyNode(key);
    	if (node == null) {
    		return null;
    	}
    	// Reduce size of the tree
    	this.size -= 1;
    	boolean del_node_colour = node.color;
		// 1. Case when the node has two non-NIL children
		if (node.lChild.key != null && node.rChild.key != null) {
			// The next largest node
			Node larger = findSmallest(node.rChild);
			// Swap the two nodes' values and change node pointer to larger
			swap(node, larger);
			node = larger;
			// Node should now have at least one NIL child
		}
		// 2. Case when node has at least one NIL child
		// Set child reference node as the NIL node's sibling
		Node child;
		if (node.lChild.key != null) {
			child = node.lChild;
		} else {
			child = node.rChild;
		}
		// 3. Node is not the root
		if (node.parent != null) {
			Node parent = getParent(node);
			// Link the child reference node to the node's parent
			if (isLChild(node)) {
				parent.lChild = child;
			} else {
				parent.rChild = child;
			}
			child.parent = parent;
		} else {
			if (child.key == null) {
				// The tree is now empty since it only contained the node
				this.root = null;
			} else {
				// The child becomes the new root
				child.parent = null;
				this.root = child;
			}
		}
		// 4. If node's colour was black
		if (del_node_colour == Node.BLACK) {
			// If the child's colour is red, simply change to black
			if (child.color == Node.RED) {
				child.color = Node.BLACK;
			} else {
				fixDelColour(child);
			}
		}
		// Return deleted node's value
		return node.value;
    }

    /**
     * Get the size of the tree
     * @return          {@code int} size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Cast the tree into a string
     * @return          {@code String} Printed format of the tree
     */
    @Override public String toString() {
        // TODO: Lab 4 Part 3-4 -- print the tree, where each node contains both value and color
        // You can print it by in-order traversal
    	// If the tree is empty, return an empty String
    	if (this.root == null) {
    		return "";
    	}
    	return inOrderRec(this.root);
    }

    private String inOrderRec(Node node) {
    	String desc = "";
    	if (node.key != null) {
    		desc += inOrderRec(node.lChild);
    		desc += node.toString();
    		desc += inOrderRec(node.rChild);
    	}
    	return desc;
    }
    
    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        GenericRedBlackTree<Integer, String> rbt = new GenericRedBlackTree<Integer, String>();
        int[] keys = new int[10];
        for (int i = 0; i < 10; i++) {
            keys[i] = (int) (Math.random() * 200);
            System.out.println(String.format("%2d Insert: %-3d ", i+1, keys[i]));
            rbt.insert(keys[i], "\"" + keys[i] + "\"");
        } // for (int i = 0; i < 10; i++)

        assert rbt.root.color == GenericRedBlackTree.Node.BLACK;

        GenericRedBlackTree<Integer, String> rbt1 = new GenericRedBlackTree<Integer, String>();
        rbt1.insert(192, "192");
        rbt1.insert(180, "180");
        rbt1.insert(177, "177");
        rbt1.insert(110, "110");
        rbt1.insert(88, "88");
        rbt1.insert(101, "101");
        rbt1.insert(18, "18");
        rbt1.insert(54, "54");
        rbt1.insert(130, "130");
        rbt1.insert(35, "35");
        
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(192);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(180);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(177);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(110);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(88);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(101);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(18);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(54);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(130);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        rbt1.remove(35);
        System.out.println(rbt1.root);
        System.out.println(rbt1);
        
        
        System.out.println(rbt.root);                   // This helps to figure out the tree structure
        System.out.println(rbt);

        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("%2d Delete: %3d(%s)", i+1, keys[i], rbt.remove(keys[i])));
            if ((i + 1) % 5 == 0) {
            	System.out.println(rbt.root);
                System.out.println(rbt);
            } // if ((i + 1) % 5 == 0)
        } // for (int i = 0; i < 10; i++)
        

    }


    /**
     * The {@code Node} class for {@code GenericRedBlackTree}
     */
    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public K key;
        public V value;
        public boolean color = BLACK;
        public Node parent = null, lChild = null, rChild = null;

        public Node(K key, V value) {                   // By default, a new node is black with two NIL children
        	this.key = key;
            this.value = value;
            if (value != null) {
                lChild = new Node(null, null);          // And the NIL children are both black
                lChild.parent = this;
                rChild = new Node(null, null);
                rChild.parent = this;
            }
        }

        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + "(" + key + ")>" : "[" + value + "(" + key + ")]";
        }
    }

}
