import Models.Coordinate;
import Models.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Solver {

    private static String inputType = "";
    private static boolean printWholeSequence = false;
    private static boolean printCostOfTheSolution = true;
    private static boolean printVisitedNodeNumber = false;
    private static int heuristicsType;
    private static int sizeOfRandomState;
    private static int numberOfPushings;
    private static ArrayList<Puzzle> openList;
    private static ArrayList<Puzzle> closedList;
    private static Puzzle startNode;
    private static Puzzle goalNode;
    private static int[][] content;
    private static int totalCost = 0;
    private static int numberOfVisitedNodes = 0;
    /**
     * Initiates the solver's parameter based on the input arguments
     */
    private static void initiateSolver(String arguments) {
        String[] argumentList = arguments.split(" ");
        for(int i=0; i< argumentList.length; ++i){
            if(argumentList[i].equals("-input")){
                inputType = argumentList[i+1];
            }
            if(argumentList[i].equals("-h")){
                heuristicsType = Integer.parseInt(argumentList[i+1]);
            }
            if(argumentList[i].equals("-solseq")){
                printWholeSequence = true;
            }
            if(argumentList[i].equals("-pcost")){
                printCostOfTheSolution = true;
            }
            if(argumentList[i].equals("-rand")){
                sizeOfRandomState = Integer.parseInt(argumentList[i+1]);
                numberOfPushings = Integer.parseInt(argumentList[i+2]);
            }

            if(argumentList[i].equals("-nvisited")){
                printVisitedNodeNumber = true;
            }
        }

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

        datas.add(1);
        datas.add(2);
        datas.add(0);
        datas.add(3);
        datas.add(4);
        datas.add(5);
        datas.add(6);
        datas.add(7);
        datas.add(8);


        return datas;
    }

    /**
     * Defines random initial state
     */
    private static ArrayList<Integer> createRandomInitialState(){
        content = createInitialContent(sizeOfRandomState);
        ArrayList<Integer> data;
        int currentX = 0, currentY = 0;
        for(int i = 0; i < numberOfPushings; ++i){
            getRandomPushDirection(currentX, currentY);
            printContent(content, sizeOfRandomState);
        }
        data = createList(content, sizeOfRandomState);
        return data;
    }

    /**
     * Random pushDirection
     */
    private static int[][] getRandomPushDirection(int currentX, int currentY){
        Random r = new Random();
        int pushDirection = r.nextInt((numberOfPushings - 1) + 1);
        System.out.println("Random direction: " + pushDirection);
        switch(pushDirection){
            case 0:
                //up
                if(currentY > 0){
                    currentY--;
                    switchElementsInContent(new Coordinate(currentX, currentY), new Coordinate(currentX, currentY+1), content);
                    printContent(content, sizeOfRandomState);
                }
                else{
                    getRandomPushDirection(currentX, currentY);
                }
                break;
            case 1:
                //right
                if(currentX < sizeOfRandomState) {
                    currentX++;
                    switchElementsInContent(new Coordinate(currentX, currentY), new Coordinate(currentX-1, currentY+1), content);
                }
                else{
                    getRandomPushDirection(currentX, currentY);
                }
                break;
            case 2:
                //down
                if(currentY < sizeOfRandomState){
                    currentY++;
                    switchElementsInContent(new Coordinate(currentX, currentY), new Coordinate(currentX, currentY-1), content);
                }
                else{
                    getRandomPushDirection(currentX, currentY);
                }
                break;
            case 3:
                //left
                if(currentX > 0){
                    currentX--;
                    switchElementsInContent(new Coordinate(currentX, currentY), new Coordinate(currentX+1, currentY+1), content);
                }
                else{
                    getRandomPushDirection(currentX, currentY);
                }
                break;
        }
        return null;
    }

    /**
     * Swithes two values in the initial node
     */
    private static void switchElementsInContent(Coordinate first, Coordinate second, int[][] randomContent){
        Integer aux = randomContent[first.getX()][first.getY()];
        randomContent[first.getX()][first.getY()] = randomContent[second.getX()][second.getY()];
        randomContent[second.getX()][second.getY()] = aux;
    }

    /**
     * Print the content
     */
    private static void printContent(int[][] content, int size){
        for(int i = 0; i < size; ++i){
            for(int j = 0; j < size; ++j){
                System.out.print(content[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Defines the initial content for int[][]
     */
    private static int[][] createInitialContent(int size){
        content = new int[size][size];
        int[][] data = new int[size][size];
        int value = 0;
        for (int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j){
                data[i][j] = value++;
            }
        }
        return data;
    }

    /**
     * Set list
     */
    private static ArrayList<Integer> createList(int[][] content, int size){
        ArrayList<Integer> data = new ArrayList<>(size);
        for(int i = 0; i < size; ++i){
            for(int j = 0; j < size; ++j){
                data.add(content[i][j]);
            }
        }
        return data;
    }

    /**
     * Solves the puzzle
     */
    static Puzzle solvePuzzle(String arguments) {
        initiateSolver(arguments);
        openList.add(startNode);
        startNode.prepare(null, heuristicsType);
        Puzzle lastNode = startNode;

        while (areNotVisitedNodes()) {
            if(printWholeSequence) lastNode.print();
            lastNode.visit();
            totalCost += lastNode.getOptimalCost();
            numberOfVisitedNodes ++;
            closedList.add(lastNode);
            if (lastNode.isEqual(goalNode)) {
                if(printCostOfTheSolution) System.out.println("Total cost: " + totalCost);
                if(printVisitedNodeNumber) System.out.println("Number of visited nodes: " + numberOfVisitedNodes);
                    return lastNode;
            }
            for (Puzzle successor : lastNode.getSuccessors()) {
                successor.prepare(lastNode, heuristicsType);
                removeOccurencesFromLists(successor);
            }
            Puzzle node = getNodeWithLowestOptimalCost(lastNode);
            if(node == null) return null;
            lastNode = node;
        }

        return null;
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
                break;
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
    private static Puzzle getNodeWithLowestOptimalCost(Puzzle parentNode) {

        int index = -1, optimalCost = 1000;
        for (int i = 0; i < openList.size(); ++i) {
            if (openList.get(i).getOptimalCost() <= optimalCost && !openList.get(i).isVisited() && openList.get(i).getParent() != null && parentNode.isEqual(openList.get(i).getParent())) {
                optimalCost = openList.get(i).getOptimalCost();
                index = i;
            }
        }
        if(index == -1) return null;
        return openList.get(index);
    }



}
