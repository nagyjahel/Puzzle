import Models.Puzzle;

import java.util.ArrayList;

public class Solver {

    private static String inputType;
    private static boolean printWholeSequence;
    private static boolean printCostOfTheSolution;
    private static int heuristicsType;
    private static int sizeOfRandomState;
    private static int numberOfPushings;
    private static ArrayList<Puzzle> openList;
    private static ArrayList<Puzzle> closedList;
    private static Puzzle startNode;
    private static Puzzle goalNode;

    /**
     * Initiates the solver's parameter based on the input arguments
     * */
    private static void initiateSolver(String arguments){
        // Read the initial state from file/console
        // Give values to the parameters
    }

    /**
     * Solves the puzzle
     * */
    public static Puzzle solvePuzzle(String arguments){
        initiateSolver(arguments);
        ArrayList<Puzzle> openList = new ArrayList<>();
        ArrayList<Puzzle> closedList = new ArrayList<>();
        openList.add(startNode);
        while(openList.size() != 0 ){
            Puzzle node = getNodeWithLowestOptimalCost(openList);
            closedList.add(node);
            if(node == goalNode) return node;
            for(Puzzle successor: node.getSuccessors()){
                successor.prepare(node, heuristicsType);
                removeOccurencesFromLists(openList,closedList,successor);
            }
        }

        return null;
    }

    /**
     * Removes the actual element from the open and the closed list, and adds it to the open one if needed.
     * */
    private static void removeOccurencesFromLists(ArrayList<Puzzle> openList, ArrayList<Puzzle> closedList, Puzzle successor){
        Puzzle theSameFromTheOpenList = findElementInTheList(openList,successor);
        if(theSameFromTheOpenList != null && theSameFromTheOpenList.getOptimalCost()> successor.getOptimalCost()){
            removeFromList(openList,theSameFromTheOpenList);
            openList.add(successor);
        }
        Puzzle theSameFromTheClosedList = findElementInTheList(closedList,successor);
        if(theSameFromTheClosedList != null && theSameFromTheClosedList.getOptimalCost()> successor.getOptimalCost()){
            removeFromList(closedList,theSameFromTheOpenList);
        }
    }

    /**
     * Returns the element from a given list which has the lowest optimal cost.
     * */
    private static Puzzle getNodeWithLowestOptimalCost(ArrayList<Puzzle> list){
        return null;
    }

    /**
     * Returns the list element if it is identical with the parameter, null otherwise.
     * */
    private static Puzzle findElementInTheList(ArrayList<Puzzle> list, Puzzle nodeToFind){
        return null;
    }

    /**
     * Removes given element from a given list.
     * */
    private static void removeFromList(ArrayList<Puzzle> list, Puzzle nodeToFind){
    }
}
