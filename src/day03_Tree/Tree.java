package day03_Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tree {
    /**
     * 给树一个头节点。。
     */
    private TreeNode root = null;
    public Tree(){
        root = new TreeNode(1,"A");
    }

    /**
     * 求这颗树的高度(深度)
     */
    public int getHeight(){
        return getHeight(root);
    }
    public int getHeight(TreeNode root){
        if(root == null){
            return 0;
        }else{
            int i = getHeight(root.leftChild);
            int j = getHeight(root.rigthChild);
            return i>j?i+1:j+1;
        }
    }
    /**
     * 求二叉树的节点数。
     */
    public int getSize(){
        return getSize(root);
    }

    private int getSize(TreeNode root) {
        if(root == null){
            return 0;
        }else{
            return 1+getSize(root.leftChild)+getSize(root.rigthChild);
        }
    }
    /**
     * 前序遍历二叉树
     *    根 左 右
     *    A B D E C F G
     */
    /**
     * 构建一个这样的树。
     *          A
     *     B          C
     *  D     E    F     G
     */
    private void preOrder(TreeNode root){
        if(root == null){
            return;
        }
        System.out.print("data:"+root.getData()+" ");
        preOrder(root.leftChild);
        preOrder(root.rigthChild);
    }
    /**
     * 中序遍历二叉树
     *     左 根 右
     *
     */
    /**
     * 构建一个这样的树。
     *          A
     *     B          C
     *  D     E    F     G
     *    结果：D B E A F C G
     */
    public void middleOrder(TreeNode root){
        if(root == null){
            return;
        }else{
            middleOrder(root.leftChild);
            System.out.print("data:"+root.getData()+" ");
            middleOrder(root.rigthChild);
        }
    }
    /**
     * 后序遍历二叉树
     *     左 右 根
     *
     */
    /**
     * 构建一个这样的树。
     *          A
     *     B          C
     *  D     E    F     G
     *    结果：
     */
    public void backOrder(TreeNode root){
        if(root == null){
            return;
        }else{
            backOrder(root.leftChild);
            backOrder(root.rigthChild);
            System.out.print("data:"+root.getData()+" ");
        }
    }
    /**
     * 根据前序遍历之后的结果来构建原来的树。
     *
     *      * 构建一个这样的树。
     *      *          A
     *      *     B          C
     *      *  D     E    #      F
     *      #    #  #  #      #      #
     *      ABD##E##C#F##
     */
    public TreeNode buildTree(ArrayList<String> data){
        //得到集合中的第一个字符准备创建第一个节点
        String nodeString = data.get(0);
        TreeNode node = null;
        data.remove(nodeString);
        if(nodeString.equals("#")){
            node = null;
            data.remove(nodeString);
        }
        //创建第一个根节点。
        node = new TreeNode(nodeString);
        root = node;
        //利用递归分别创建根节点的左右节点。
        root.leftChild = buildTree(data);
        root.rigthChild = buildTree(data);
        return node;
    }
    /**
     * 构建一个这样的树。
     *          A
     *     B          C
     *  D     E    F     G
     */
    public void createTree(){
        TreeNode nodeB = new TreeNode(2,"B");
        TreeNode nodeC = new TreeNode(3,"C");
        TreeNode nodeD = new TreeNode(4,"D");
        TreeNode nodeE = new TreeNode(5,"E");
        TreeNode nodeF = new TreeNode(6,"F");
        TreeNode nodeG = new TreeNode(7,"G");
        //构建树中的各个节点的关系。
        root.leftChild = nodeB;
        root.rigthChild = nodeC;
        nodeB.leftChild = nodeD;
        nodeB.rigthChild = nodeE;
        nodeC.leftChild = nodeF;
        nodeC.rigthChild = nodeG;
    }
    /**
     * 构造一个节点。
     */
    public class TreeNode{
        private int index;
        private TreeNode leftChild;
        private TreeNode rigthChild;
        private String data;

        public int getIndex() {
            return index;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public TreeNode getRigthChild() {
            return rigthChild;
        }

        public String getData() {
            return data;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public void setRigthChild(TreeNode rigthChild) {
            this.rigthChild = rigthChild;
        }

        public void setData(String data) {
            this.data = data;
        }

        public TreeNode(int index, String data){
            this.data = data;
            this.index = index;
            leftChild = null;
            rigthChild = null;
        }
        public TreeNode(String data){
            this.data = data;
            leftChild = null;
            rigthChild = null;
        }
    }
    /**
     * 执行主函数。
     */
    public static void main(String[] args){
        //创建一棵树
        Tree tree = new Tree();
        //构建树。
        tree.createTree();
        int height = tree.getHeight();
        System.out.println("height:"+height);
        int size = tree.getSize();
        System.out.println("size:" + size);
        System.out.println("前序遍历二叉树的结果：");
        tree.preOrder(tree.root);
        System.out.println();
        System.out.println("中序遍历二叉树的结果：");
        tree.middleOrder(tree.root);
        System.out.println();
        System.out.println("后序遍历二叉树的结果：");
        tree.backOrder(tree.root);
    }
}
