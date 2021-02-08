import java.security.MessageDigest;
//import com.twmacinta.util.MD5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.NoSuchAlgorithmException;
/*
This clase is used to perform an IDA* algorithm to find an optimal solution to
the Rubik's cube.
This class inherits from RubikDatabaseBuilder which inherits from the Cube class,
hence all the methods and variables in these two classes are available in this
class.
*/

public class RubikSolver extends RubikDatabaseBuilder {
    
    boolean solved = false;   // Used to end search if solution is found
    int depthLevel;         // This is the maximum depth a solution can be, paths with estimated cost above this are pruned from search
    String host = "jdbc:derby://localhost:1527/Rubik";
    String uName = "rafi";
    String uPass = "rafi";
    String query1 = "SELECT MOVES FROM CORNERPOSITIONS WHERE POSITION = ?";  // Query to determine how many moves to solve corners from loopup table
    String query2 = "SELECT MOVES FROM EDGEPOSITIONS WHERE POSITION = ?"; // Query to determine how many moves to solve edges from lookup table
    PreparedStatement solvable;
    PreparedStatement statement1;
    PreparedStatement statement2;
    
    public RubikSolver () {
        
        super();
            
    }
    
    /*
    By calling this method, an IDA* search is started on Rubik cube
    */
    
    public void solve (boolean firstIteration) {
        
        if (firstIteration)
            depthLevel = heuristic ();                // The first depthLevel is determined by the heuristic cost of the start state
        
        
        if (isSolved()) {   // Checks if cube is already in solved state
                System.out.println("The cube is already in solved state");
            }
        
        else if (isSolvable()) {            // This checks if a solution is stored in lookup table RubikPositions.
                System.out.println("The Rubik cube has been solved");
                System.out.println("The solution is:");
                this.printStringToMoves(this.turns);
        }
        
        else {
        
            int depth = 0;
        
            int newDepth = searchNodes(depth, depthLevel);  // The newDepth level is returned by the searchNodes method
        
            if (solved) {
                System.out.println("The Rubik cube has been solved");
                System.out.println("The solution is:");
                this.printStringToMoves(this.turns);
            }
            else {
                depthLevel = newDepth;   // depthLevel is changed to minimum path cost of all paths searched
                System.out.println("Depth level is "+depthLevel);
                solve(false);  // This method is called recursively until a solution is found. The parameter 'false' makes sure depthLevel variable does not change
            }    
        }

    }
    /*
    This method searches all nodes at a depth with a cutoff depth
    */
    
    public int searchNodes (int depth, int cutoff) {
      
        int counter = 1; 
        depth++; // Depth is incremened every time this method is called
        int newDepth = 100; // To determine newDepth level is next iteration
        int estimateDepth = 0; // To store estimated cost of a path determined by function f
        
        while (!solved && counter < 19) {
            
            System.out.println(counter);

            if (turns == null)
                    this.turns = "";   // This resets the turns variable once search of all moves is completed
                
            this.moveGenerator(counter);  // This performs moves and adds to turns string
            System.out.println("Searching at cutoff of "+depthLevel);
            this.printStringToMoves(this.turns);
            if (this.turns.length() >= 2 && this.turns.charAt(this.turns.length()-1) == this.turns.charAt(this.turns.length()-2)) {
                this.reverseMove(counter); // This prunes searches away from repeated moves
            }
            else if (this.turns.length() >= 2 && !checkMove(this.turns)) {   // Checks for other types of moves that are not needed to be checked
                this.reverseMove(counter);
            }
            else {
                if (depth >= 6 && isSolvable()) { // Only need to check if it is solvable if depth is greater than 6 as this method
                    solved = true;                // was already called at start state, and isSolvable() determines whether state is within
                    break;                        // 5 moves of a solution
                }
                else {
                    int estimatedCost = functionf(depth);
                    if (estimatedCost <= cutoff) {
                        estimateDepth = depth + searchNodes(depth, cutoff - 1); // Search takes place next depth if it passed cuttoff test
                        this.reverseMove(counter);
                    }
                    else {
                        estimateDepth = depth + estimatedCost;
                        this.reverseMove(counter);
                    }
                }
                if (estimateDepth < newDepth)
                    newDepth = estimateDepth;  // newDepth is determined by the minimum cost of all paths
            }
            x = this.turns;
            this.turns = this.turns.substring(0, this.turns.length() - 1); // Deletes last char from turns string
            counter++;
                
        }
        return newDepth;
   }
    /*
    This method determines the estimated cost (number of moves needsd to solve
    a cube) of a cube state. This is the depth until this state plus
    an heuristic estimate of remaining cost
    */    
    
    public int functionf (int depth) {
        
        int f = depth + heuristic();
        
        return f;
    }
    /*
    This method determines heuristic estimate of state.
    */
    public int heuristic () {
        
        int c = 0;
        int e1 = 0;
        
        try {
            con = DriverManager.getConnection(host, uName, uPass );
            statement1 = con.prepareStatement(query1);
            statement2 = con.prepareStatement(query2);
            c = cornerHeuristic();
            if (c >= 9) {
                statement1.close();
                statement2.close();
                con.close();
                return c;    // The maximum edge heuristic can be is 9, hence if corner heuristic is 9, this can be returned 
            }
            e1 = edgeHeuristic();
      
        } catch (SQLException ex) {
            Logger.getLogger(RubikSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                statement1.close();
                statement2.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RubikSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
            return Math.max(c, e1); // The heurstic estimate is the maximum of the two heuristics
        

    }
    /*
    The method checks whether a solution to the state exists on the database.
    The database 'RUBIKPOSITIONS' contains all states with a solution within 
    5 moves to solved state
    */

    public boolean isSolvable() {
        
        
       try {
            String host = "jdbc:derby://localhost:1527/Rubik";
            String uName = "rafi";
            String uPass = "rafi";
        
            con = DriverManager.getConnection(host, uName, uPass );
            String query = "SELECT MOVES FROM RUBIKPOSITIONS WHERE POSITION = ?";
        
            solvable = con.prepareStatement(query);
            solvable.setString(1, this.stateToHash()); // This searches for hashed state in database

            
            ResultSet r = solvable.executeQuery();
            
            if (r.next()) {
                String moves = r.getString(1);
                String reverse = reverseMoves(moves); // The moves are reversed as database stores moves from solved state to
                if (turns == null)                   // current state and we want to perform moves from current state to solved state.
                    this.turns = ""; 
                for (int i = 0; i < reverse.length(); i++)
                    moveGenerator(charToMoves(reverse.charAt(i)));
                return true;
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(CornerHeuristic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                solvable.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CornerHeuristic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
       return false;
    }
    /*
    This method reverses the moves in the string
    */
    
    public String reverseMoves (String s) {
        
        String reverse = "";
        
        for (int i = 0; i < s.length(); i++)
            reverse = reverse + opposite(s.charAt(s.length()-1 -i));
        
        return reverse;
        
    }
    /*
    This move returns the opposite move. For example, the opposite of the F
    move is F'
    */
    public char opposite (char c) {
        
        switch (c) {
            case '1':
                return '3';
            case '2':
                return '2';
            case '3':
                return '1';
            case '4':
                return '6';
            case '5':
                return '5';
            case '6':
                return '4';
            case '7':
                return '9';
            case '8':
                return '8';
            case '9':
                return '7';
            case 'a':
                return 'c';
            case 'b':
                return 'b';
            case 'c':
                return 'a';
            case 'd':
                return 'f';
            case 'e':
                return 'e';
            case 'f':
                return 'd';
            case 'g':
                return 'i';
            case 'h':
                return 'h';
            case 'i':
                return 'g';
            default:
                return '0';
        }
        
    }
    /*
    This method returns an heurstic estimate for the number of moves to solve the
    corners
    */

    public int cornerHeuristic() {
        
        int counter = 0;
       
       try {
            
             int [] fixedCorner = this.corner(1, 4, 5);
             
             rotateCube1(fixedCorner); // This rotates the cube so that the white green orange corner is in the top right hand corner as all states stored in database have this corner fixed
             String hash = hashCorner(counter);
             rotateCube2(fixedCorner); // This rotates the cube back to the original state
             statement1.setString(1, hash);
            
             ResultSet r = statement1.executeQuery();
            
             if (r.next()) {
                    int moves = r.getShort(1);
                    return moves;
                }
             
            counter++;
        
        } catch (SQLException ex) {
            Logger.getLogger(CornerHeuristic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        
       
       return 11; // Corners can be solved in a maximum of 11 moves. Database only contains states
    }            // that can be solved in 10 moves. So if state is not in database, estimate is 11
    /*
    This method returns an heuristic estimate for the number of moves to solve 6 of the edges
    */
    public int edgeHeuristic() {
        
       String hash = "";
       
       try {
       
            hash = hashEdge();
        
            statement2.setString(1, hash);
            
            ResultSet r = statement2.executeQuery();
            
            if (r.next()) {
                int moves = r.getInt(1);
                return moves;
            }
 
        } catch (SQLException ex) {
            Logger.getLogger(CornerHeuristic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return 9; // Database only stores states within 8 moves. So heursistic estimate is 9 if state is not found in database
    }
    /*
    This method rotates the cube from one of 24 positions (8 corner positions x 3 orientations) so that white green orange peices
    is in top right front corner with white colour on top and orange colour on front face.
    */
    public void rotateCube1 (int[] position) {
        
        if (position[0] == 0) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else
                    this.rotate90AsU();
            }
            else if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
            } 
        }
        else if (position[0] == 1) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsF();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsF();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsF();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsR();
                    this.rotate90AsF();
                }
            }
        } 
        else if (position[0] == 3) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                }   
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }  
            }
        }
        else if (position[0] == 3) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                }
            }
        }
        else if (position[0] == 4) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsU();
                    this.rotate90AsF();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else
                    this.rotate90AsR();
            }
        }
        else {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                }              
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
            }
        }    
    }
    /*
    This method rotate the cube back to original position
    */
    public void rotateCube2 (int[] position) {
        
        if (position[0] == 0) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
                else
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
            }
            else if (position[2] == 0) {
                    this.rotate90AsU();
            } 
        }
        else if (position[0] == 1) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
            }
        } 
        else if (position[0] == 3) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsR();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                }   
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsR();
                }  
            }
        }
        else if (position[0] == 3) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsF();
                }
                else {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsF();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsF();
                }
                else {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsF();
                }
            }
        }
        else if (position[0] == 4) {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsF();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                }
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
                else
                    this.rotate90AsR();
                    this.rotate90AsR();
                    this.rotate90AsR();
            }
        }
        else {
            if (position[1] == 0) {
                if (position[2] == 0) {
                    this.rotate90AsR();
                    this.rotate90AsR();
                }
                else {
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsU();
                    this.rotate90AsF();
                    this.rotate90AsF();              
                }              
            }
            else {
                if (position[2] == 0) {
                    this.rotate90AsU();
                    this.rotate90AsF();
                    this.rotate90AsF(); 
                }
                else {
                    this.rotate90AsF();
                    this.rotate90AsF();
                }
            }
        }    
    }
    /*
    This method works out the corner hash state in order to check whether the corner state
    is in the corner database
    */
    public String hashCorner(int num) throws NoSuchAlgorithmException {
  
        
        String s = "";
        
        for (int i = 0; i < this.cube.length; i++)
           for (int j = 0; j < this.cube[0].length; j++)
                for (int k = 0; k < this.cube[0][0].length; k++) {
                    if ((j == 0 && k == 0) || (j == 0 && k == 2) || 
                            (j == 2 && k == 0) || (j == 2 && k == 2))
                                s = s + Integer.toString(this.cube[i][j][k]);
                } 
        
        //MD5 mhash = new MD5 ();
        
        MessageDigest mhash = MessageDigest.getInstance("MD5");
        //mhash.Update(s);
        //byte[] newArray = mhash.Final();
        byte[] newArray = s.getBytes();
        byte [] hash = mhash.digest(newArray);
        String h;
        h = Base64.getEncoder().encodeToString(hash);
        
        return h;

    }
    /*
    This method works out the edge hash state in order to check whether the edge state
    is in the edge database
    */
    public String hashEdge () throws NoSuchAlgorithmException {
        
        String s = "";
        
        for (int j = 0; j < this.cube[0].length; j++)
            for (int k = 0; k < this.cube[0][j].length; k++) {
                if ((j == 0 && k == 1) || (j == 1 && k == 0) || 
                    (j == 1 && k == 2) || (j == 2 && k == 1))
                       s = s + Integer.toString(this.cube[0][j][k]);
                }
        for (int i = 1; i < 5; i++)
            s = s + Integer.toString(this.cube[i][0][1]);
               
        s = s + Integer.toString(this.cube[4][0][1]); // This adds the hash of the two middle edges on the front face
        s = s + Integer.toString(this.cube[1][2][1]);
        s = s + Integer.toString(this.cube[4][2][1]);
        s = s + Integer.toString(this.cube[3][0][1]);
        
        //MD5 mhash = new MD5 ();
        
        MessageDigest mhash = MessageDigest.getInstance("MD5");
        //mhash.Update(s);
        //byte[] newArray = mhash.Final();
        byte[] newArray = s.getBytes();
        byte [] hash = mhash.digest(newArray);
        String h;
        h = Base64.getEncoder().encodeToString(hash);
        return h;
         
    }
    
   
    public static void main (String[] args) {
        
        RubikSolver test = new RubikSolver ();
        test.R();
        test.U2();
        test.F();
        test.B2();
        test.L();
        test.FC();
        System.out.println(test);
        test.solve(true);
        System.out.println(test);
    }
    
}
