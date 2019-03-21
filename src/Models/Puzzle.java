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

    public Puzzle(Puzzle original){
        this.size= original.size;
        this.content = new int[size][size];
        for(int i=0; i<size; ++i){
            for (int j=0; j<size; ++j){
                content[i][j] = original.content[i][j];
            }
        }
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
        if(emptyElementCoordinates.x + 1 < size){
            successors.add(new Puzzle(this).moveEmptyPosition(emptyElementCoordinates,0,1));
        }
        return successors;
    }


    private Puzzle moveEmptyPosition(Coordinate emptyElementCoordinates, int horizontalStep, int verticalStep){
        int i = content[emptyElementCoordinates.x - horizontalStep][emptyElementCoordinates.y - verticalStep];
        content[emptyElementCoordinates.x][emptyElementCoordinates.y] = i;
        content[emptyElementCoordinates.x - horizontalStep][emptyElementCoordinates.y - verticalStep] = 0;
        return this;
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
        int number = 0;
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                if(content[i][j] != i+j){
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
    public void calculateCost() {
        cost = parent.cost + getCostToParent();
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
    public void calculateOptimalCost() {
        optimalCost =  estimatedCost + cost;
    }


    public void setContent(ArrayList<Integer> values){
        int k =0;
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                content[i][j] = values.get(k);
                k++;
            }
        }
    }

    public void print(){
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j){
                System.out.print(content[i][j] + " ");
            }
            System.out.println();
        }
    }

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

    public boolean isEqual(Puzzle nodeToFind) {
        for(int i=0; i<size; ++i){
            for (int j=0; j<size; ++j){
                if(content[i][j] != nodeToFind.content[i][j]){
                    return false;
                }
            }
        }
    return true;
    }

    private class Coordinate{
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
