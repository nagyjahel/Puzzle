package Models;

import java.util.ArrayList;

public class Puzzle {

    int size;
    int [][] content;
    int cost;
    int estimatedCost;
    int optimalCost;

    Puzzle parent;

    /**
     * Constructor of the puzzle entity
     * */
    public Puzzle(int size){
        this.size = size;
        this.content = new int[size][size];
    }

    /**
     * Sets another puzzle as the parent of the current one
     * */
    public void setParent(Puzzle node) {
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
        return null;
    }

    /**
     * Invokes the corresponding heuristics-calculated function based on the given parameter
     * */
    public void estimateDistanceHeuristically(int mode){
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
        setParent(parent);
        estimateDistanceHeuristically(heuristicsType);
        calculateCost();
        calculateOptimalCost();
    }

    /**
     * Calculates the elements estimated distance with the simple method.
     * The calculated value will be the number of the fields which are on wrong place.
     * */
    private void simpleHeuristics(){

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
    public void calculateCost() {
        cost = parent.cost + getCostToParent();
    }


    /**
     * Return the cost of getting to the actual element from the parent
     * */
    private int getCostToParent(){
        return 0;
    }

    /**
     * Calculates the optimal cost f(n') = g(n) + h(n)
     * */
    public void calculateOptimalCost() {
        optimalCost =  estimatedCost + cost;
    }


}
