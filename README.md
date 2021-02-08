# rubik-cube

This was my university project to make a program to solve a Rubik's cube.

I first created a program to solve the cube using the Beginner's method which is a algorithm to solve any rubik's cube which is used by people to solve the cube.

The other program tries to find the optimum solution. It has been proven that any Rubik's cube can be solved in 20 or less moves.

To speed the search of the tree the program uses alpha beta pruning using a heuristic value of the cube based on the position of the corners and edge pieces. Since every cube that is solved requires the corners and edges to be solved, the number of moves to solved only the corners and edges is a lower bound on the number of moves to solve the entire cube. These heuristic values are stored in an apache database and a lookup function is performed on a cube state which is used to evaluate how preferable that state is. 

I also stored all cube states that can be solved with 5 moves in the database to help speed up the search.

The search is done using an iterative deepening search.



I will go through briefly the classes:

### BeginnersMethod.java
This can be run to test the Beginner's method solution to the Rubik's cube. It implements a simple GUI of the cube.
### RubikSolver.java
This can be run to test the searcher to find the optimum solution. It requires an apache derby database to run to look up the heuristic values and check whether the cube can be solved within 5 moves

### Cube.java
The master java class implementing moves of the cube and other useful methods.
### EdgeHeuristic.java
Class to store the edge heuristic values in the database. Only 6 edges are used to make the heuristic value.
### CornerHeuristic.java
Class to store the corner heuristic values in the database. All 4 corners are used to make the heuristic value.
### RubikDatabaseBuilder.java
Class to store all the cube orientations that can be solved in 5 moves.
