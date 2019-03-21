# Puzzle
We have a grid with different numbers and a single empty position.
Our scope is to resolve this puzzle by placing every element on its corresponding position.

### Examples ###
A possible state of the puzzle may be, where the "-" indicates the empty positions.
A puzzle with three rows and columns is being considered solved if looks as follows:

### Inputs ###
1. –input <FILE>: indicates the file the initial state can be read from. If it's missing, the initial state will be read from the console.
2. –solseq: the program will write the whole sequence of the solution on the standard output
3. –pcost: the program will write the cost of the solution on the standard output
4. –nvisited: the program will write the number of visited nodes on the standard output
5. –h <H>: defines the type of the heuristics. 
          If it's 1, then the estimated cost will be the number of the elements which are not on their right place.
          If it's 2, then the estimated cost will be calculated using the Manhattan heuristics.
6. –rand <N> <M> the program will write a state of size N on the standard output. M defines the number of random element-pushings.

