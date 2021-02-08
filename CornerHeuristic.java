
//import com.twmacinta.util.MD5;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.NoSuchAlgorithmException;

/*
In this class, I generate all corner states from distance 0 to 10 and store this
in a database. I only need to examine 9 moves if I fix one corner.
I fixed the top front right corner, I therefore examine B, D and L moves to
generate all states.
 */
public class CornerHeuristic extends Cube {
    
    HashSet<String> hash;                       // HashSet stores a Hashcode of the int[][][] cube
    Queue<String> q;                                     // Queue holds turns that lead to states not examined before
    String turns;                                        // Keeps track of turns needed to get to a new state
    String x;
    String hashValue;
    int num = 0;

    
    public CornerHeuristic () throws NoSuchAlgorithmException {
        
        super();

        hash = new HashSet<String>(1000000, 0.7f);       // Creating new HashSap and queue
        q = new LinkedList<String>();

        turns = "0";                                               // Setting turns to string "0" to represent no moves taken place
        hashValue = stateTwoByTwo();
        hash.add(hashValue);
        q.add(turns);
    }
    
    public void searchMoves () {

        int counter = 0;                  // This counter ensure all 18 moves are searched from a given state
        
        Connection con = null;	
        PreparedStatement prstmt = null;
       
       try {
            String host = "jdbc:derby://localhost:1527/Rubik;rewriteBatchedStatements=true";
            String uName = "rafi";
            String uPass = "rafi";
        
            con = DriverManager.getConnection(host, uName, uPass );
            String insert = "INSERT INTO CORNERPOSITIONS VALUES (?, ?)";
            
            prstmt = con.prepareStatement(insert);
            
            prstmt.setString(1, stateTwoByTwo());
            prstmt.setShort(2, (short) 0);
            prstmt.executeUpdate();
               
          while (!q.isEmpty() && q.peek().length() < 10) { 
                                                                      
           counter = counter + 1;

            if (counter == 10) {     // Once counter is equal to 10. The turns string is deleted from the queue for the next queue string to be examined
                reverseMoves();   // This reverses moves to get back to original state
                q.remove(); // Deletes first string in queue
                counter = 1; // Sets counter back to one
            }
            else if (counter != 1) {
                reverseMove(counter-1);
            }
            x = turns;
            turns = null;
            turns = q.peek();   // returns string of turns from top of queue but does not delete it from queue
            if (counter == 1) {
                moveConstructor(turns); // Method below. Performs moves in turns string
            }
            moveGenerator(counter); // Method below. Peforms a move from this state. It can perform moves 1 - 9
            printStringToMoves(turns);
      
            if (turns.length() >= 2 && turns.charAt(turns.length()-1) == turns.charAt(turns.length()-2)){
                // Checks that no move is being done twice in a row as
                // it is not needed to be examined as it can be done with one move.
                // e.g two F moves is equivalent to one F2 move
                continue;
            }
            else if (turns.length() >= 2 && !checkMove(turns)) {   // Checks for other types of moves that are not needed to be checked
                continue;
            }
            else if (hash.contains(this.stateTwoByTwo())){
                continue;
            }
            else {
                    String state = this.stateTwoByTwo();
                    hash.add(state); // Adds new state to hashset
                    if (turns.length() < 10)
                        q.add(turns); // Adds new string of moves to queue
                    prstmt.setString(1, state);
                    prstmt.setShort(2, (short) turns.length());
                    prstmt.addBatch();
                    num++;
            }

            if (num >= 10000) {
                prstmt.executeBatch();
                num = 0;
            } 
  
        }
       }
       catch (SQLException se) {
            int count = 1;
            while (se != null) {    // Code from http://www.java2s.com/Code/JavaAPI/java.sql/SQLExceptiongetNextException.htm
                System.out.println("SQLException " + count);
                System.out.println("Code: " + se.getErrorCode());
                System.out.println("SqlState: " + se.getSQLState());
                System.out.println("Error Message: " + se.getMessage());
                se = se.getNextException();
                count++;
               }
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                prstmt.executeBatch();
                prstmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CornerHeuristic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        /*
    This method uses the string in queue to set up state by performing moves represented in string
     */

    public void moveConstructor (String s) {


        if (!s.equals("0")) {
            char[] array = s.toCharArray();

            for (int i = 0; i < turns.length(); i++) {
                int move = charToMoves(array[i]);
                movePerformer(move);
            }
        }
        else {
            System.out.println("No move constructor");
        }

    }
    
    /*
    This method performs moves for construction of state purpose. It does not add the move to the turn string
    variable like the moveGenerator method as the moves are already in the string.
     */

    public void movePerformer (int i) {


        switch (i) {


            case 1:
                this.B();
                break;
            case 2:
                this.B2();
                break;
            case 3:
                this.BC();
                break;
            case 4:
                this.D();
                break;
            case 5:
                this.D2();
                break;
            case 6:
                this.DC();
                break;
            case 7:
                this.L();
                break;
            case 8:
                this.L2();
                break;
            case 9:
                this.LC();
                break;
            default:
                System.out.println("Invalid method character in move performer of " + i);
                break;

        }
    }
    /*
    This method is used for generate moves from states to reach new states of the cube.
    It concatenates the new move to the turns string to obtain a new turns string value.

    This method overites this method in Cube class. This is because I don't want to examine all moves
    contained in that method, rather only the 9 moves in this method.
     */

    public void moveGenerator (int i) {

        if (turns.equals("0"))
            turns = "";

        switch (i) {

            case 1:
                this.B();
                turns = turns + '1';
                break;
            case 2:
                this.B2();
                turns = turns + '2';
                break;
            case 3:
                this.BC();
                turns = turns + '3';
                break;
            case 4:
                this.D();
                turns = turns + '4';
                break;
            case 5:
                this.D2();
                turns = turns + '5';
                break;
            case 6:
                this.DC();
                turns = turns + '6';
                break;
            case 7:
                this.L();
                turns = turns + '7';
                break;
            case 8:
                this.L2();
                turns = turns + '8';
                break;
            case 9:
                this.LC();
                turns = turns + '9';
                break;
            default:
                System.out.println("Invalid method character in move generator of " + i);
                break;

        }
    }
    

    /*
    This method reverses the move done by moveGenerator.
     */
    public void reverseMoves () {

        char[] array = turns.toCharArray();

            for (int i = 0; i < turns.length(); i++) {
                reverseMove(charToMoves(array[turns.length() - 1 - i]));
            }
    }
    /*
    The method reverse a move
    */
    
    public void reverseMove (int i) {

        switch (i) {
            
            case 1:
                this.BC();
                break;
            case 2:
                this.B2();
                break;
            case 3:
                this.B();
                break;
            case 4:
                this.DC();
                break;
            case 5:
                this.D2();
                break;
            case 6:
                this.D();
                break;
            case 7:
                this.LC();
                break;
            case 8:
                this.L2();
                break;
            case 9:
                this.L();
                break;
            default:
                System.out.println("Invalid method character in move reverser of " + i);
                break;

        }

    }
/*
This method checks for moves sequences that do not need to be tested
 */
    public boolean checkMove (String s) {

        if (s.charAt(s.length()-1) == '3' && s.charAt(s.length()-2) == '1')
            return false;
        else if (s.charAt(s.length()-1) == '1' && s.charAt(s.length()-2) == '3')
            return false;
        else if (s.charAt(s.length()-1) == '6' && s.charAt(s.length()-2) == '4')
            return false;
        else if (s.charAt(s.length()-1) == '4' && s.charAt(s.length()-2) == '6')
            return false;
        else if (s.charAt(s.length()-1) == '9' && s.charAt(s.length()-2) == '7')
            return false;
        else if (s.charAt(s.length()-1) == '7' && s.charAt(s.length()-2) == '9')
            return false;
        else if (s.charAt(s.length()-1) == '1' && s.charAt(s.length()-2) == '2')
            return false;
        else if (s.charAt(s.length()-1) == '2' && s.charAt(s.length()-2) == '1')
            return false;
        else if (s.charAt(s.length()-1) == '3' && s.charAt(s.length()-2) == '2')
            return false;
        else if (s.charAt(s.length()-1) == '2' && s.charAt(s.length()-2) == '3')
            return false;
        else if (s.charAt(s.length()-1) == '4' && s.charAt(s.length()-2) == '5')
            return false;
        else if (s.charAt(s.length()-1) == '5' && s.charAt(s.length()-2) == '4')
            return false;
        else if (s.charAt(s.length()-1) == '5' && s.charAt(s.length()-2) == '6')
            return false;
        else if (s.charAt(s.length()-1) == '6' && s.charAt(s.length()-2) == '5')
            return false;
        else if (s.charAt(s.length()-1) == '7' && s.charAt(s.length()-2) == '8')
            return false;
        else if (s.charAt(s.length()-1) == '8' && s.charAt(s.length()-2) == '7')
            return false;
        else if (s.charAt(s.length()-1) == '8' && s.charAt(s.length()-2) == '9')
            return false;
        else if (s.charAt(s.length()-1) == '9' && s.charAt(s.length()-2) == '8')
            return false;

        return true;
    }
    /*
    This method changes a char to an integer representing a move
    */

    public int charToMoves (char c) {


        if (c == '1')
            return 1;
        else if (c == '2')
            return 2;
        else if (c == '3')
            return 3;
        else if (c == '4')
            return 4;
        else if (c == '5')
            return 5;
        else if (c == '6')
            return 6;
        else if (c == '7')
            return 7;
        else if (c == '8')
            return 8;
        else if (c == '9')
            return 9;
        else
            System.out.println("Invalid character in char to moves of "+c);

        return 0;

    }
    /*
    This method computes the corner hash of the state
    */
    
    public String stateTwoByTwo () throws NoSuchAlgorithmException {
        
        String s = "";
        
        for (int i = 0; i < this.cube.length; i++)
           for (int j = 0; j < this.cube[0].length; j++)
                for (int k = 0; k < this.cube[0][j].length; k++) {
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
    This method prints the moves represented in the string
    */
   
    public void printStringToMoves (String s) {

        char c = 0;

        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '1')
                System.out.print("B ");
            else if (c == '2')
                System.out.print("B2 ");
            else if (c == '3')
                System.out.print("B' ");
            else if (c == '4')
                System.out.print("D ");
            else if (c == '5')
                System.out.print("D2 ");
            else if (c == '6')
                System.out.print("D' ");
            else if (c == '7')
                System.out.print("L ");
            else if (c == '8')
                System.out.print("L2 ");
            else if (c == '9')
                System.out.print("L' ");
            else
                System.out.println("Invalid character int print string to moves of " + c);
        }

        System.out.println();

    }
    
    public static void main (String[] args) throws NoSuchAlgorithmException {
        
        CornerHeuristic build = new CornerHeuristic();
        build.searchMoves();
        
    }
   
    
}
