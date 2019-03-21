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
    }

    private static ArrayList<Integer> createGoalNode(){
        ArrayList <Integer> data = new ArrayList<>();
        for(int i=0; i<createDummyData().size(); ++i){
            data.add(i);
        }
        return data;
    }
    private static ArrayList<Integer> createDummyData(){
        ArrayList <Integer> datas = new ArrayList<>();
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
     * */
    public static Puzzle solvePuzzle(String arguments){
        initiateSolver(arguments);
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        openList.add(startNode);
        startNode.print();

        while(openList.size() != 0 ){
            Puzzle node = getNodeWithLowestOptimalCost(openList);
            closedList.add(node);
            if(node == goalNode) return node;
            printSuccessors(node.getSuccessors());
            for(Puzzle successor: node.getSuccessors()){
                successor.print();
                successor.prepare(node, heuristicsType);
                removeOccurencesFromLists(openList,closedList,successor);
            }
        }

        return null;
    }

    private static void printSuccessors(ArrayList<Puzzle> successors) {
        for(int i=0; i<successors.size(); ++i){
            System.out.println("SUCCESSOR " + i + ": ");
            successors.get(i).print();
        }

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
        int index =0, optimalCost = 1000;
        for(int i=0; i<list.size(); ++i){
            if(list.get(i).getOptimalCost() < optimalCost){
                optimalCost = list.get(i).getOptimalCost();
                index = i;
            }
        }
        return list.get(index);
    }

    /**
     * Returns the list element if it is identical with the parameter, null otherwise.
     * */
    private static Puzzle findElementInTheList(ArrayList<Puzzle> list, Puzzle nodeToFind){
        for(Puzzle puzzle: list){
            if(puzzle == nodeToFind){
                return puzzle;
            }
        }
        return null;
    }

    /**
     * Removes given element from a given list.
     * */
    private static void removeFromList(ArrayList<Puzzle> list, Puzzle nodeToFind){
        for(int i=0; i<list.size(); ++i){
            if(list.get(i).isEqual(nodeToFind)){
                list.remove(i);
            }
        }
    }
}
