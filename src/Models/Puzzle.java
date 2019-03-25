package Models;

import java.util.ArrayList;

public class Puzzle {

    private int size;
    private int [][] content;
    private int cost;
    private int estimatedCost;
    private int optimalCost;
    private boolean isVisited = false;

    private Puzzle parent;

    /**
     * Constructor of the puzzle entity
     * */
    public Puzzle(){

    }
    public Puzzle(int size){
        this.size = size;
        this.content = new int[size][size];
    }

    public Puzzle(Puzzle original){
        this.parent = original;
        this.isVisited = false;
        this.estimatedCost = 0;
        this.cost = 0;
        this.optimalCost = 0;
        this.size= original.size;
        this.content = new int[size][size];
        for(int i=0; i<size; ++i){
            for (int j=0; j<size; ++j) {
                content[i][j] = original.content[i][j];
            }
        }
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void visit() {
        isVisited = true;
    }

    public Puzzle getParent() {
        return parent;
    }

    /**
     * Sets another puzzle as the parent of the current one
     * */
    private void setParent(Puzzle node) {
        this.parent = node;
    }

    /**
     * Returns the optimal cost
     * */
    public int getOptimalCost(){
        return this.optimalCost;
    }

    /**
     * Gets all the successors of an element.
     * For example: if the empty field is on [0,0] position, its successors will be two puzzles:
     *              1. one with the empty field on [0,1]
     *              2. one with the empty field on [1,0]
     * */
    public ArrayList<Puzzle> getSuccessors() {
        ArrayList<Puzzle> successors = new ArrayList<>();

        Coordinate emptyElementCoordinates = getEmptyElementPosition();
        if(emptyElementCoordinates.x - 1 >= 0 ){
            successors.add(new Puzzle(this).moveEmptyPosition(emptyElementCoordinates,-1,0));
        }
        if(emptyElementCoordinates.y - 1 >= 0 ){
            successors.add(new Puzzle(this).moveEmptyPosition(emptyElementCoordinates,0,-1));
        }
        if(emptyElementCoordinates.x + 1 < size){
            successors.add(new Puzzle(this).moveEmptyPosition(emptyElementCoordinates,1,0));
        }
        if(emptyElementCoordinates.y + 1 < size){
            successors.add(new Puzzle(this).moveEmptyPosition(emptyElementCoordinates,0,1));
        }
        return successors;
    }


    private Puzzle moveEmptyPosition(Coordinate emptyElementCoordinates, int horizontalStep, int verticalStep){
        int i = content[emptyElementCoordinates.x + horizontalStep][emptyElementCoordinates.y + verticalStep];
        content[emptyElementCoordinates.x][emptyElementCoordinates.y] = i;
        content[emptyElementCoordinates.x + horizontalStep][emptyElementCoordinates.y + verticalStep] = 0;
        return this;
    }
    /**
     * Invokes the corresponding heuristics-calculated function based on the given parameter
     * */
    private void estimateDistanceHeuristically(int mode){
        if(mode == 1){
            simpleHeuristics();
        }
        else{
            manhattanHeuristics();
        }
    }

    /**
     * Prepares an element for further the algorithm
     * */
    public  void prepare(Puzzle parent, int heuristicsType){
        this.setParent(parent);
        estimateDistanceHeuristically(heuristicsType);
        calculateCost();
        calculateOptimalCost();
    }

    /**
     * Calculates the elements estimated distance with the simple method.
     * The calculated value will be the number of the fields which are on wrong place.
     * */
    private void simpleHeuristics(){
        int number = 0;
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                if(content[i][j] != i * size + j){
                    number ++;
                }
            }
        }
        this.estimatedCost = number;
    }

    /**
     * Calculates the elements estimated distance with the manhattan method.
     * The calculated value will be the manhattan distance between this element and the goal element.
     * */
    private void manhattanHeuristics(){

    }

    /**
     * Calculates the cost g(n') = g(n) + getCostToParent()
     * */
    private void calculateCost() {
        if(parent != null){
            cost = parent.cost + getCostToParent();
        }
        else{
            cost = estimatedCost;
        }
    }


    /**
     * Return the cost of getting to the actual element from the parent
     * */
    private int getCostToParent(){
        return 1;
    }

    /**
     * Calculates the optimal cost f(n') = g(n) + h(n)
     * */
    private void calculateOptimalCost() {
        optimalCost =  estimatedCost + cost;
    }


    /**
     * Build the matrix of the puzzle based on an arraylist
     * */
    public void setContent(ArrayList<Integer> values){
        int k =0;
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                content[i][j] = values.get(k);
                k++;
            }
        }
    }

    /**
     * Print the content of the puzzle (the matrix)
     * */
    public void print(){
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                System.out.print(content[i][j] + " ");
                if(content[i][j]< 10){
                    System.out.print(" ");

                }

            }
            System.out.println();
        }
        System.out.println("----------");

    }

    /**
     * Get the position of the empty element from the matrix
     * */
    private Coordinate getEmptyElementPosition(){
        for(int i=0; i<size; ++i){
            for (int j=0; j<size; ++j){
                if(content[i][j] == 0){
                    return new Coordinate(i,j);
                }
            }
        }
        return null;
    }

    /**
     * Are two puzzles equal? (Their matrix is equal)
     * */
    public boolean isEqual(Puzzle nodeToFind) {
        for(int i=0; i<size; ++i){
            for (int j=0; j<size; ++j){
                if(this.content[i][j] != nodeToFind.content[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Are two puzzles identical? (The same puzzles)
     * */
    public boolean isIdentical(Puzzle nodeToFind){
        return (isEqual(nodeToFind) && this.cost == nodeToFind.cost && this.optimalCost == nodeToFind.optimalCost && this.estimatedCost == nodeToFind.estimatedCost);
    }

    public void setSize(int size) {
        this.size = size;
    }
}
