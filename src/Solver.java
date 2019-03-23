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
     */
    private static void initiateSolver(String arguments) {
        inputType = "input.txt";
        printWholeSequence = true;
        printCostOfTheSolution = true;
        heuristicsType = 1;
        sizeOfRandomState = 3;
        numberOfPushings = 4;
        startNode = new Puzzle(3);
        startNode.setContent(createDummyData());
        goalNode = new Puzzle(3);
        goalNode.setContent(createGoalNode());
        openList = new ArrayList<>();
        closedList = new ArrayList<>();

    }

    /**
     * Defines the goal node
     * */
    private static ArrayList<Integer> createGoalNode() {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < createDummyData().size(); ++i) {
            data.add(i);
        }
        return data;
    }

    /**
     * Helper function (may be deleted) - creates dummy data
     * */
    private static ArrayList<Integer> createDummyData() {
        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(2);
        datas.add(3);
        datas.add(5);
        datas.add(8);
        datas.add(0);
        datas.add(1);
        datas.add(7);
        datas.add(6);
        datas.add(4);
        return datas;
    }

    /**
     * Solves the puzzle
     */
    public static Puzzle solvePuzzle(String arguments) {
        initiateSolver(arguments);
        openList.add(startNode);
        startNode.prepare(null, heuristicsType);

        while (areNotVisitedNodes()) {
            Puzzle node = getNodeWithLowestOptimalCost();
            node.visit();
            closedList.add(node);
            if (node.isEqual(goalNode)) return node;
            for (Puzzle successor : node.getSuccessors()) {
                successor.prepare(node, heuristicsType);
                removeOccurencesFromLists(successor);
            }
        }

        return null;
    }

    /**
     * Helper function (may be deleted) - prints all the successors of a node
     * */
    private static void printSuccessors(ArrayList<Puzzle> successors) {
        for (int i = 0; i < successors.size(); ++i) {
            System.out.println("SUCCESSOR " + i + ": ");
            successors.get(i).print();
        }

    }

    private static boolean areNotVisitedNodes(){
        for(Puzzle puzzle: openList){
            if(!puzzle.isVisited()){
                return true;
            }
        }
        return false;
    }
    /**
     * Removes the actual element from the open and the closed list, and adds it to the open one if needed.
     */
    private static void removeOccurencesFromLists(Puzzle successor) {
        Puzzle theSameFromTheOpenedList = findElementInList(openList, successor);
        if (theSameFromTheOpenedList != null) {
            if (theSameFromTheOpenedList.getOptimalCost() > successor.getOptimalCost()) {
                removeFromList(openList, theSameFromTheOpenedList);
                addToList(openList, successor);
            }
        } else {
            addToList(openList, successor);
        }

        Puzzle theSameFromTheClosedList = findElementInList(closedList, successor);
        if (theSameFromTheClosedList != null) {
            if (theSameFromTheClosedList.getOptimalCost() > successor.getOptimalCost()) {
                removeFromList(closedList, theSameFromTheClosedList);
            }
        }

    }

    /**
     * Removes a defined element from a given list
     * */
    private static void removeFromList(ArrayList<Puzzle> list, Puzzle puzzleToRemove) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isIdentical(puzzleToRemove)) {
                list.remove(i);
            }
        }
    }

    /**
     * Prints a list
     * */
    private static void printList(ArrayList<Puzzle> list, int listType){
        if(listType == 0) {
            System.out.println("OPEN LIST ");
        }
        else {
            System.out.println("CLOSE LIST ");

        }
            for(Puzzle puzzle: list){
                puzzle.print();
            }
            System.out.println("--------------------------------------------");

    }

    /**
     * Adds an element to a list in case that element doesn't is part of that list yet
     * */
    private static void addToList(ArrayList<Puzzle> list, Puzzle puzzleToAdd) {
        for (Puzzle puzzle : list) {
            if (puzzle.isIdentical(puzzleToAdd)) {
                return;
            }
        }
        list.add(puzzleToAdd);
    }


    /**
     * Finds a the equal pair of an element in a list
     * */
    private static Puzzle findElementInList(ArrayList<Puzzle> list, Puzzle puzzleToFind) {
        for (Puzzle puzzle : list) {
            if (puzzle.isEqual(puzzleToFind) && !puzzle.isIdentical(puzzleToFind)) {
                return puzzle;
            }
        }
        return null;
    }


    /**
     * Returns the element from a given list which has the lowest optimal cost.
     */
    private static Puzzle getNodeWithLowestOptimalCost() {
        int index = 0, optimalCost = 1000;
        for (int i = 0; i < openList.size(); ++i) {
            if (openList.get(i).getOptimalCost() < optimalCost && !openList.get(i).isVisited()) {
                optimalCost = openList.get(i).getOptimalCost();
                index = i;
            }
        }
        return openList.get(index);
    }



}
