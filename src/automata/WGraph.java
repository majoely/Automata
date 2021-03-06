package automata;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class implements the weighted directed graph ADT and a simple GUI for visualising a weighted directed graph
 * The data structure used for implementing a digraph is the weighted adjacency matrix.
 * The nodes are labeled by Integers
 *
 * The GUI should allow the user to add/remove nodes/edges, as well as performing simple updates and queries on the graph
 * Control to the data structure is done by both the mouse and through a textfield at the bottom of the window
 *
 *
 * @Author:
 */
public class WGraph extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

    private int barb; //size of an arrow edge
    private double phi; //angle of an arrow edge
    private String moveNode; //the node the user is moving on the GUI
    private String selectedNode; // the node selected
    public final static int CIRCLEDIAMETER = 40; //Diameter of the nodes
    
    private static Color colorLine = Color.black;
    private static Color colorArrow = Color.black;
    
    private boolean stopDrawing = false;

    // This is the adjacency list representation of the digraph
    // The nodes are denoted here as Integers
    // Each node is associated with a list of Integers, which indicates its out-neighbours
    private HashMap<String, HashMap<String,String>> data;

    private HashMap<String, Node> nodeList;

    // The collection of node in the graph
    // This set is the key set of data
    private Set<String> nodeSet;

    // The textfield used for user to specify commands
    private JTextField tf;

    // The Constructor
    public WGraph() {
        data = new HashMap<String, HashMap<String, String>>();
        nodeList = new HashMap<String, Node>();
        nodeSet=data.keySet();

        JPanel panel = new JPanel();
        barb = 10;                          // barb length
        phi = Math.PI / 12;                 // 30 degrees barb angle
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);
        tf = new JTextField();
        tf.addActionListener(this);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(tf, BorderLayout.SOUTH);
        moveNode = null; 			// Initial values of moveNode is -1
        selectedNode = null;			// Initial values of moveNode is -1
    }

    public WGraph(int width, int height) {
        this();
        JFrame frame = new JFrame("Weighted Graph Implementation");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.getContentPane().add(this);
        frame.setVisible(true);
    }
    
    public void pauseDrawing() {
        stopDrawing = true;
    }
    
    public void startDrawing() {
        stopDrawing = false;
    }

    /**
      * The method adds a node to the diagraph, labeled by the int value node
      */
    public void add(String node){
        // If the label node is already in the digraph, do nothing and return
        if(data.containsKey(node)) {
            System.err.println("Node not added i:" + node);
            return;
        }
        // Create a new linked list
        HashMap<String,String> list = new HashMap<String,String>();
        // Add a new entry to the adjacency list
        data.put(node, list);

        // Create a new node in the GUI
        // Set a random initial position
        // Link the new node with the corresponding node in the GUI
        Node nodeVisual = new Node(node);
        nodeVisual.xpos = 50 +(int) (Math.random() * 320);
        nodeVisual.ypos = 50 +(int) (Math.random() * 320);
        nodeList.put(node, nodeVisual);
    }
    
    private boolean conatainNodes(String node1, String node2) {
        if (!data.containsKey(node1)) {
            System.err.println("Node:"+node1+" doesn't exist");
            return false;
        }
        if (!data.containsKey(node2)) {
            System.err.println("Node:"+node2+" doesn't exist");
            return false;
        }
        return true;
    }


    /**
      * The method adds an edge to the weighted diagraph
      * 
      * @param source of the edge is labeled node1
      * @param target of the edge is labeled node2
      * @param label of the edge is weight
      */
    public boolean addEdge(String source, String target, String label){
        if (!conatainNodes(source, target)) {
            return false;
        }
        HashMap<String,String> list = data.get(source);
        if(list.containsKey(target)) {
            return false;
        } else {
            list.put(target, label);
            return true;
        }
    }
    
    public void addEdge(String node1, String node2, double weight){
        addEdge(node1, node2, "" + weight);
    }


    /**
      * The method loads a weighted digraph stored in the file fileName in adjacency matrix representation
      * The top line of the file contains the number n of nodes in the graph
      * The nodes in the graph are then given the indices 0,...,n-1
      *
      */
    public void load(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numNodes = Integer.parseInt(br.readLine());
            int pos = 0;
            String output;
            for(int i=0;i<numNodes;i++)
            {
                add("" + i);
            }
            int col = 0;
            String st="";
            for(int i=0; i<numNodes;i++) {
                output = br.readLine();
               if(output!=null) {
                   StringTokenizer token = new StringTokenizer(output);
                   while (token.hasMoreTokens()) {
                        st = token.nextToken();
                        if (!st.equals("#"))
                            addEdge("" + i,"" + col,st);
                        col++;
                   }
                   col=0;
               }
            }
            br.close();
        }catch(FileNotFoundException e) {
            System.out.println("File can't be found.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
      * The method removes an edge from the digraph
      * The source of the edge is labeled node1
      * The target of the edge is labeled node2
      *
      */
    public void removeEdge(String node1, String node2){
            if (!data.containsKey(node1) || !data.containsKey(node2)) {
                return;
            }
            HashMap<String,String> list = data.get(node1);
            if(list.containsKey(node2)) {
                list.remove(node2);
            }
    }


    /**
      * The methods changes the weight of the edges from node1 to node2 to weight
      * Do nothing if the edge does not exist in the graph
      *
      */
    public void changeWeight(String node1, String node2, double weight) {
        if (!conatainNodes(node1, node2)) {
            return;
        }
        HashMap<String,String> list = data.get(node1);
        if(list.containsKey(node2)) {
            list.put(node2, "" + weight);
        }
    }
    
    /**
      * The methods changes the weight of the edges from node1 to node2 to weight
      * Do nothing if the edge does not exist in the graph
      *
      */
    public void apendLabel(String source, String target, String label) {
        if (!conatainNodes(source, target)) {
            return;
        }
        HashMap<String,String> list = data.get(source);
        if(list.containsKey(target)) {
            String current = list.get(target);
            list.put(target, current + "," + label);
        }
    }

    /**
      * The method removes a node from the digraph
      * You need to complete this method
      * It should do nothing if the node is not contained in the digraph
      */
    public void remove(String node){
        if (!data.containsKey(node)) {
            return;
        }
        HashMap<String,String> list;
        for (String name: nodeSet) {
            list = data.get(name);
            if(list.containsKey(node)) {
                list.remove(node);
            }
        }
        data.remove(node);
        nodeList.remove(node);
    }

    /**
      * This method computes and returns the order (number of nodes) of the graph
      */
    public int graphOrder(){
        return data.size();
    }


    /**
      * This method prints out the adjacency matrix of the graph
      * You need to complete this method
      *
      * The method computes the following data structure:
      * 1. a HashMap labels which associates each number between 0 and n-1 a unique node label
      * 2. a boolean nxn matrix adjMatrix storing the adjacency matrix where:
      *		 the ith row/column  corresponds to the node with label labels.get(i)
      *
      * The method then prints out the adjacency matrix
      * To the left and on top of the matrix, the method also prints out
      * the node label which corresponds to each row and column
      */
    public void printMatrix() {
        // n is the order of the graph
        int n=graphOrder();

        // the HashMap associates an index in [0..n-1] with a node label
        HashMap<Integer,String> labels = new HashMap<Integer,String>();

        // the adjacency matrix of the digraph, where the node indices in
        // the matrix are indicated by the labels HashMap
        String[][] adjMatrix = new String[n][n];

        HashMap<String,String> list;
        int row=0;
        int column=0;
        int index=0;

        for (String name: nodeSet){
            labels.put(index, name);
            index++;
        }
        for (int i=0;i<n;i++){
            list = data.get(labels.get(i));
            for (int j=0; j<n; j++){
                if(list.containsKey(labels.get(j)))
                    adjMatrix[i][j]=""+list.get(labels.get(j));
                else
                    adjMatrix[i][j]="#";
            }
        }

        for (int i=0;i<=8*n;i++){
            System.out.print("-");
        }
        System.out.print('\n');

        System.out.print(""+'\t');
        for (int i=0;i<n;i++){
            System.out.print(""+labels.get(i)+'\t');
        }
        System.out.print(""+'\n');


        for (int i=0; i<n;i++){
            System.out.print(""+labels.get(i));
            for (int j=0; j<n;j++){
                System.out.print('\t'+adjMatrix[i][j]);
            }
            System.out.print(""+'\n');
        }
        for (int i=0;i<=8*n;i++){
            System.out.print("-");
        }
        System.out.print('\n');
    }

    /**
      * Clear the digraph
      *
      */
    public void clear(){
        data.clear();
        nodeList.clear();
    }

    /**
      * This method specifies how the digraph may be controled by the user
      * by inputing commands in the TextField
      *
      */
    public void actionPerformed(ActionEvent evt) {
        String command = tf.getText();

        StringTokenizer st = new StringTokenizer(command);

        String token, opt;
        String node1, node2;
        if(st.hasMoreTokens()) {
            token = st.nextToken();
            token=token.toLowerCase();
            switch(token){

                case "load":
                    try{
                        clear();
                        opt=st.nextToken();
                        load(opt);
                    }catch(Exception e){
                        System.out.println("Invalid command");
                    }
                    break;

                case "add":
                    try{
                        opt=st.nextToken();
                        opt=opt.toLowerCase();
                        if(opt.equals("edge")){
                            node1 = st.nextToken();
                            node2 = st.nextToken();
                            double weight = Double.parseDouble(st.nextToken());
                            if(st.hasMoreTokens()) break;
                            addEdge(node1,node2,weight);
                        }
                        else if(opt.equals("node")){
                            node1 = st.nextToken();
                            if(st.hasMoreTokens()) break;
                            add(node1);
                        }
                    }catch(Exception e){
                        System.out.println("Invalid command");
                    }
                    break;

                case "remove":
                    try{
                        opt=st.nextToken();
                        opt=opt.toLowerCase();
                        if(opt.equals("edge")){
                            node1 = st.nextToken();
                            node2 = st.nextToken();
                            if(st.hasMoreTokens()) break;
                            removeEdge(node1,node2);
                        }
                        else if(opt.equals("node")){
                            node1 = st.nextToken();
                            if(st.hasMoreTokens()) break;
                            remove(node1);
                        }
                    }catch(Exception e){System.out.println("Invalid command");}
                    break;

                case "change":
                    try{
                        opt=st.nextToken();
                        opt=opt.toLowerCase();
                        if(opt.equals("weight")){
                            node1 = st.nextToken();
                            node2 = st.nextToken();
                            double w = Double.parseDouble(st.nextToken());
                            if (st.hasMoreTokens()) break;
                            changeWeight(node1,node2,w);
                        }
                    }catch(Exception e){
                            System.out.println("Invalid command");
                    }
                    break;

                case "print":
                    try{
                        opt=st.nextToken();
                        opt=opt.toLowerCase();
                        if(st.hasMoreTokens()) {
                            System.out.println("Invalid command");
                            break;
                        }
                        switch(opt) {
                            case "order":
                                System.out.println("Order of the digraph: "+graphOrder());
                                break;
                            case "matrix":
                                printMatrix();
                                break;
                            default:
                                System.out.println("Invalid command");
                                break;
                        }
                    } catch(Exception e) {
                        System.out.println("Invalid command");
                    }
                    break;

                case "clear":
                    if (!st.hasMoreTokens()) {
                        clear();
                    } else {
                        System.out.println("Invalid command");
                    }
                    break;

                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
        repaint();
    }

    /**
     * Paint the diagraph to the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!stopDrawing) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            //clear the previous screen
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 1500, 1500);
            g2d.setColor(Color.BLACK);

            HashMap<String,String> outNeighbours;

            for (String node1 : nodeSet){
                if(selectedNode==node1) {
                    nodeList.get(node1).draw(g, Color.RED);
                } else {
                    nodeList.get(node1).draw(g, Color.BLACK);
                }
                outNeighbours = data.get(node1);
                for (String node2 : nodeSet){
                    if(outNeighbours.containsKey(node2)) {
                        if (node1 == node2) {
                            drawLoop(node2, g, outNeighbours.get(node2));
                        } else {
                            drawEdge(node1,node2,g,outNeighbours.get(node2));
                        }
                    }
                }
            }
        }
    }
    
    public void drawLoop(String node, Graphics g, String label) {
        
        float radius = (float)CIRCLEDIAMETER/2.0f;
        int delta = (int)Math.sqrt((radius*radius)/2);
        
        System.out.println("Delta: " + delta);
        
        int startX = nodeList.get(node).xpos - delta;
        int startY = nodeList.get(node).ypos - delta;

        int destX = nodeList.get(node).xpos + delta;
        int destY = nodeList.get(node).ypos - delta;
        
        drawCurvedLine(g, startX, startY, destX, destY, (int)radius, -(CIRCLEDIAMETER + (int)radius), label);
    }
    

   /**
    * Draw a directed edge between 2 nodes with the specific color for the line and the arrow.
    */
    public void drawEdge(String node1, String node2, Graphics g, String label) {

        int startX = nodeList.get(node1).getEdgeX(nodeList.get(node2));
        int startY = nodeList.get(node1).getEdgeY(nodeList.get(node2));

        int destX = nodeList.get(node2).getEdgeX(nodeList.get(node1));
        int destY = nodeList.get(node2).getEdgeY(nodeList.get(node1));
        
        drawCurvedLine(g, startX, startY, destX, destY, 0, 0, label);
    }
    
    private void drawCurvedLine(Graphics g, int startX, int startY, int destX, int destY, int deltaX, int deltaY, String label) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(2));
        g2.setColor(colorLine);

        QuadCurve2D q = new QuadCurve2D.Float();
        // draw QuadCurve2D.Float with set coordinates
        int ctrlx=calcTextPosX(startX, destX, startY, destY) + deltaX;
        int ctrly=calcTextPosY(startX, destX, startY, destY) + deltaY;
        q.setCurve(startX, startY, ctrlx, ctrly, destX, destY);
        g2.draw(q);
        
        g2.drawString(label, ctrlx - deltaX, ctrly - (deltaY/2));

        double theta, x, y;
        g2.setPaint(colorArrow);
        theta = Math.atan2(destY - ctrly, destX - ctrlx);
        drawArrow(g2, theta, destX, destY);
    }

    public int calcTextPosX(int x1, int x2, int y1, int y2) {
        if (x1<x2) {
            if(y1<y2) {
                return (x1+x2)/2+20;
            } else {
                return (x1+x2)/2-24;
            }
        } else {
            if(y1<y2) {
                return (x1+x2)/2+20;
            } else {
                return (x1+x2)/2-24;
            }
        }
    }

    public int calcTextPosY(int x1, int x2, int y1, int y2){
            if (x1<x2) {
                return (y1+y2)/2-10;
            } else {
                return (y1+y2)/2+10;
            }
	}

    //draws the arrows on the edges
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0) {
        g2.setStroke(new BasicStroke(3));
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }
    
    // Mouse Actions:
    // Moving a node: The user may drag and drop a node to any position
    // Add a node: The user may add a node by clicking a white area of the frame
    //				The newly added node will be automatically selected
    // Selecting a node: The user may select a node by click on a node
    // Add an edge: Once a node is selected, the user may add an outgoing edge
    //				to the selected node by clicking another node
    @Override
    public void mouseDragged(MouseEvent e) {
        if (moveNode != null) {
            Node node = nodeList.get(moveNode);
            node.xpos = e.getPoint().x;
            node.ypos = e.getPoint().y;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Node node;
        boolean onNode=false;
        String clicked = null;
        for (String name: nodeSet) {
            node=nodeList.get(name);
            //Calculate the distance to the center of a node
            double distance = Math.sqrt(Math.pow((e.getX()-node.xpos), 2)+Math.pow((e.getY()-node.ypos), 2));
            if (distance<=(1.0*CIRCLEDIAMETER/2)) {
                onNode=true;
                clicked = name;
            }
        }
        if (onNode){
            if (selectedNode == null) {
                    selectedNode = clicked;
            } else {
                if (clicked.equals(selectedNode)){
                    selectedNode = null;
                } else {
                    addEdge(selectedNode , clicked , 1);
                    selectedNode = null;
                }
            }
        }
        if (!onNode) {
            int newNode = 0;
            while(nodeSet.contains(newNode)) {
                newNode++;
            }
            add("" + newNode);
            selectedNode= "" + newNode;
        }
      	repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Node node;
        for (String name: nodeSet) {
            node = nodeList.get(name);
            //Calculate the distance to the center of a node
            double distance=Math.sqrt(Math.pow((e.getX()-node.xpos), 2)+Math.pow((e.getY()-node.ypos), 2));
            if (distance<=(1.0*CIRCLEDIAMETER/2)) {
                moveNode = name;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        moveNode = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    // An inner class storing information regarding the visualisation of a node
    private class Node {

        public int xpos;
        public int ypos;
        public String nodeName;
        public int inEdges, arraySpot;

        int dirX, dirY;

        public Node(String name) {
            xpos = 0;
            ypos = 0;
            nodeName = name;
            inEdges = 0;
            arraySpot = 0;
            
            double rand = Math.random();

            if(rand<0.25){
                dirX=1;
                dirY=1;
            } else if(rand<0.5){
                dirX=-1;
                dirY=1;
            } else if(rand<0.75){
                dirX=1;
                dirY=-1;
            } else {
                dirX=-1;
                dirY=-1;
            }
        }

        // returns the label of the node
        public String label(){
            return nodeName;
        }

        // compute the x coordinate of the source of the edge from this to the specified node
        public int getEdgeX(Node node) {
            double direction = 1.0;
            if (node.xpos < xpos) {
                direction = -1.0;
            }
            double x2subx1sqr = Math.pow((node.xpos - xpos), 2);
            double y2suby1sqr = Math.pow((node.ypos - ypos), 2);
            double rsqr = Math.pow(CIRCLEDIAMETER*1.0/2, 2);
            double x = Math.sqrt((x2subx1sqr * rsqr / (x2subx1sqr + y2suby1sqr))) * direction + xpos;
            return (int) Math.round(x);
        }

        // compute the y coordinate of the source of the edge from this to the specified node
        public int getEdgeY(Node node) {
            double direction = 1.0;
            if (node.ypos < ypos) {
                direction = -1.0;
            }
            double x2subx1sqr = Math.pow((node.xpos - xpos), 2);
            double y2suby1sqr = Math.pow((node.ypos - ypos), 2);
            double rsqr = Math.pow(CIRCLEDIAMETER*1.0/2, 2);//Square root of radius
            double y = Math.sqrt((y2suby1sqr * rsqr / (x2subx1sqr + y2suby1sqr))) * direction + ypos;
            return (int) Math.round(y);
        }

		// draw the node
        public void draw(Graphics g, Color color) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color);
            g2d.drawOval(xpos-(CIRCLEDIAMETER / 2), ypos-(CIRCLEDIAMETER / 2), CIRCLEDIAMETER, CIRCLEDIAMETER);
            g2d.setColor(Color.BLUE);
            g2d.drawString("" + nodeName, xpos-3, ypos+4 );
            g2d.setColor(Color.BLACK);
        }

        public void move() {
            xpos = xpos -2*dirX;
            ypos = ypos -2*dirY;
            if (xpos<50 || xpos>360) dirX=(-1)*dirX;
            if (ypos<50 || ypos>360) dirY=(-1)*dirY;
        }
    }

    // The main method builds a digraph and add three initial nodes to the digraph labeled 0,1,2
    // and adds some edges among these nodes
    public static void main(String[] args) {
        WGraph g = new WGraph(450, 450);

        // Expected Print Output:
        /**
         * Node 0 indegree: 0 outdegree: 2
         * Node 1 indegree: 1 outdegree: 1
         * Node 2 indegree: 2 outdegree: 0
         * Size of the digraph: 3
         * Order of the digraph: 3
         * -------------------------
         *         0       1       2
         * 0       0       1       1
         * 1       0       0       1
         * 2       0       0       0
         * -------------------------
         * ---------
         * 0:  1 2
         * 1:  2
         * 2:
         * ---------
         * Universal Source found: 0
         * Universal Sink found: 2
         * Transpose
         * -------------------------
         *         0       1       2
         * 0       0       0       0
         * 1       1       0       0
         * 2       1       1       0
         * -------------------------
         * Underlying Graph
         * -------------------------
         *         0       1       2
         * 0       0       1       1
         * 1       1       0       1
         * 2       1       1       0
         * -------------------------
         */

    }



}