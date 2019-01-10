/*
 * Jeremy Hudson
 */
package avl_tree;

public class AVL_Tree {

    Node root;

    //simply returns height value of a node if it is not a null node 
    static int height(Node treeNode) {

        if (treeNode == null) {
            return 0;
        }

        return treeNode.height;
    }

    Node insert(Node treeNode, int value) {

        //inserts node in the correct location according to it's value
        if (treeNode == null) {
            return (new Node(value));
        }

        if (value < treeNode.key) {

            treeNode.left = insert(treeNode.left, value);
        } else if (value > treeNode.key) {
            treeNode.right = insert(treeNode.right, value);
        } //duplicate node value case
        else {
            return treeNode;
        }

        //update height of parent node after insertion
        treeNode.height = 1 + Math.max(height(treeNode.left),
                height(treeNode.right));

        //check if insertion of new node caused an imbalance for the parent node
        int balance = balanceFactor(treeNode);

        //Left left rotation 
        if (balance > 1 && value < treeNode.left.key) {
            return rotateRight(treeNode);
        }

        //Left right rotation
        if (balance > 1 && value > treeNode.left.key) {
            treeNode.left = rotateLeft(treeNode.left);
            return rotateRight(treeNode);
        }

        //Right left rotation 
        if (balance < -1 && value < treeNode.right.key) {
            treeNode.right = rotateRight(treeNode.right);
            return rotateLeft(treeNode);
        }

        //Right right rotation
        if (balance < -1 && value > treeNode.right.key) {
            return rotateLeft(treeNode);
        }

        return treeNode;
    }

    Node rotateLeft(Node treeNode) {

        Node rightNode = treeNode.right;
        Node leftNodeOfRight = rightNode.left;

        //perform rotation
        rightNode.left = treeNode;
        treeNode.right = leftNodeOfRight;

        if (treeNode.right != null) {
            treeNode.right.parent = treeNode;
        }

        rightNode.left = treeNode;

        if (rightNode.parent != null) {
            treeNode.parent = rightNode;

            if (rightNode.parent.right == treeNode) {
                rightNode.parent.right = rightNode;
            } else {
                rightNode.parent.left = rightNode;
            }
        }
        
        //calculate the new height of the nodes after rotation
        treeNode.height
                = Math.max(height(treeNode.left), height(treeNode.right)) + 1;

        rightNode.height
                = Math.max(height(rightNode.left), height(rightNode.right)) + 1;

        return rightNode;
    }

    Node rotateRight(Node treeNode) {

        Node leftNode = treeNode.left;
        Node rightNodeOfLeft = leftNode.right;

        //perform rotation
        leftNode.right = treeNode;
        treeNode.left = rightNodeOfLeft;

        if (treeNode.left != null) {
            treeNode.left.parent = treeNode;
        }

        leftNode.right = treeNode;

        if (leftNode.parent != null) {
            treeNode.parent = leftNode;

            if (leftNode.parent.right == treeNode) {
                leftNode.parent.right = leftNode;
            } else {
                leftNode.parent.left = leftNode;
            }
        }

        //calculate the new height of the nodes after rotation
        treeNode.height
                = Math.max(height(treeNode.left), height(treeNode.right)) + 1;

        leftNode.height
                = Math.max(height(leftNode.left), height(leftNode.right)) + 1;

        return leftNode;
    }

    //returns balance factor of a given node 
    int balanceFactor(Node treeNode) {

        if (treeNode == null) {
            return 0;
        }

        return height(treeNode.left) - height(treeNode.right);
    }

    //prints tree in an inOrder traversal 
    void printInOrder(Node treeNode) {
        if (treeNode != null) {

            printInOrder(treeNode.left);
            System.out.print(treeNode.key);

            System.out.println("\t\t\t " + (balanceFactor(treeNode))
                    + "\t\t\t " + (treeNode.height - 1));

            System.out.println("\n");
            printInOrder(treeNode.right);

        }
    }

    //class with necessary data for nodes and their properties
    public class Node {

        int data;
        int key;
        int height;

        Node left;
        Node right;
        Node parent;

        Node(int data) {
            key = data;
            height = 1;
        }
    }

    public static void main(String[] args) {

        AVL_Tree tree = new AVL_Tree();

        tree.root = tree.insert(tree.root, 11);
        tree.root = tree.insert(tree.root, 1);
        tree.root = tree.insert(tree.root, 14);
        tree.root = tree.insert(tree.root, 2);
        tree.root = tree.insert(tree.root, 7);
        tree.root = tree.insert(tree.root, 15);
        tree.root = tree.insert(tree.root, 5);
        tree.root = tree.insert(tree.root, 8);
        tree.root = tree.insert(tree.root, 4);

        System.out.println("Node" + "\t\t\t Balance Factor \t Height\n");

        tree.printInOrder(tree.root);

    }
}
