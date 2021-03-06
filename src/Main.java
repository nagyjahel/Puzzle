import Models.Puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * Reads the arguments from console and invokes the corresponding functions
     * */
    private static String getArguments(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String arguments = reader.readLine();
            return arguments;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Main function
     */
    public static void main(String[] args) {
        System.out.println("PUZZLE");
        String arguments = getArguments();
        Puzzle solution = Solver.solvePuzzle(arguments);
        if(solution == null){
            System.out.println("No solution");
        }
        else{
            System.out.println("Solution");
            solution.print();
        }
    }
}
