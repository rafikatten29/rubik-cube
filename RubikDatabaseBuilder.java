//import com.twmacinta.util.MD5;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author rafi
 */
public class RubikDatabaseBuilder extends Cube {

    HashSet<String> hash;                             // HashSet stores a Hashcode of the int[][][] cube
    Queue<String> q;                                     // Queue holds turns that lead to states not examined before
    String turns;                                        // Keeps track of turns needed to get to a new state
    String x;
    String hashValue;
    int num;
    Connection con;	
    PreparedStatement prstmt;

    public RubikDatabaseBuilder () {

        super();

        }
    
        public void build () throws NoSuchAlgorithmException {
            
        hash = new HashSet<String>(1000000, 0.7f);       // Creating new HashSet and queue
        q = new LinkedList<String>();

        turns = "0";                                               // Setting turns to string "0" to represent no moves taken place
        hashValue = stateToHash();
        hash.add(hashValue);
        q.add(turns);                                             // Setting turns to string "0" to represent no moves taken place
        searchMoves(); 
   
        }
/*
This method goes through states with a distance of 5 from a solved state.
All unique states are stored in a database
 */

    public void searchMoves () throws NoSuchAlgorithmException {

        int counter = 0;                  // This counter ensure all 18 moves are searched from a given state

        String insert = "INSERT INTO RUBIKPOSITIONS VALUES (?, ?)";
        
        try {
            String host = "jdbc:derby://localhost:1527/Rubik";
            String uName = "rafi";
            String uPass = "rafi";
            
            con = DriverManager.getConnection(host, uName, uPass );
            
            prstmt = con.prepareStatement(insert);
            
            prstmt.setString(1, stateToHash());
            prstmt.setString(2, "");
            prstmt.executeUpdate();
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
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        
        boolean check = !q.isEmpty() && q.peek().length() <= 4;
        
        while (check) { 

            counter = counter + 1;
            System.out.println(counter);
            
            if (counter == 19) {     // Once counter is equal to 19. The turns string is deleted from the queue for the next queue string to be examined
                reverseMoves();   // This reverses moves to get back to original state
                q.remove(); // Deletes first string in queue
                counter = 1; // Sets counter back to one
            }
            else if (counter != 1) {
                reverseMove(counter-1);  // reverse last move
            }
            x = turns;
            turns = null;
            turns = q.peek();   // returns string of turns from top of queue but does not delete it from queue
            
            if (counter == 1) {
                moveConstructor(turns); // Method below. Performs moves in turns string
            }
   
            moveGenerator(counter); // Method below. Peforms a move from this state.

            printStringToMoves(turns);
      
            if (turns.length() >= 2 && turns.charAt(turns.length()-1) == turns.charAt(turns.length()-2)) {
                // Checks that no move is being done 2 times in a row as
                // it is not needed to be examined as it can be done with one move.
                // e.g two F moves is equivalent to one F2 move
                continue;
            }
            else if (turns.length() >= 2 && !checkMove(turns)) {   // Checks for other types of moves that are not needed to be checked
                continue;
            }
            else if (hash.contains(this.stateToHash())) { // Checks for a hash value of a state already seen before.
                continue;
            }
            else {
                String state = stateToHash();
                hash.add(state); // Adds new state to hashset
                if (turns.length() < 5)
                    q.add(turns); // Adds new string of moves to queue
                try {
                    prstmt.setString(1, state);
                    prstmt.setString(2, turns);
                    prstmt.addBatch();   // Dats is insered in batches to better performance
                } catch (SQLException ex) {
                    Logger.getLogger(RubikDatabaseBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }
                num++;
            }

            if (num >= 1000) {
                try {
                    prstmt.executeBatch();
                } catch (SQLException ex) {
                    Logger.getLogger(RubikDatabaseBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }
                num = 0;
            } 

        }
          
        if (!check) {
            try {
                prstmt.executeBatch();
                prstmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RubikDatabaseBuilder.class.getName()).log(Level.SEVERE, null, ex);
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
                this.F();
                break;
            case 2:
                this.F2();
                break;
            case 3:
                this.FC();
                break;
            case 4:
                this.B();
                break;
            case 5:
                this.B2();
                break;
            case 6:
                this.BC();
                break;
            case 7:
                this.U();
                break;
            case 8:
                this.U2();
                break;
            case 9:
                this.UC();
                break;
            case 10:
                this.D();
                break;
            case 11:
                this.D2();
                break;
            case 12:
                this.DC();
                break;
            case 13:
                this.R();
                break;
            case 14:
                this.R2();
                break;
            case 15:
                this.RC();
                break;
            case 16:
                this.L();
                break;
            case 17:
                this.L2();
                break;
            case 18:
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
    contained in that method, rather only the 12 moves in this method.
     */

    public void moveGenerator (int i) {

        if (turns.equals("0"))
            turns = "";

        switch (i) {

            case 1:
                this.F();
                turns = turns + "1";
                break;
            case 2:
                this.F2();
                turns = turns + "2";
                break;
            case 3:
                this.FC();
                turns = turns + "3";
                break;
            case 4:
                this.B();
                turns = turns + "4";
                break;
            case 5:
                this.B2();
                turns = turns + "5";
                break;
            case 6:
                this.BC();
                turns = turns + "6";
                break;
            case 7:
                this.U();
                turns = turns + "7";
                break;
            case 8:
                this.U2();
                turns = turns + "8";
                break;
            case 9:
                this.UC();
                turns = turns + "9";
                break;
            case 10:
                this.D();
                turns = turns + "a";
                break;
            case 11:
                this.D2();
                turns = turns + "b";
                break;
            case 12:
                this.DC();
                turns = turns + "c";
                break;
            case 13:
                this.R();
                turns = turns + "d";
                break;
            case 14:
                this.R2();
                turns = turns + "e";
                break;
            case 15:
                this.RC();
                turns = turns + "f";
                break;
            case 16:
                this.L();
                turns = turns + "g";
                break;
            case 17:
                this.L2();
                turns = turns + "h";
                break;
            case 18:
                this.LC();
                turns = turns + "i";
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
    The method reverses a move
    */
    
    public void reverseMove (int i) {

        switch (i) {

            case 1:
                this.FC();
                break;
            case 2:
                this.F2();
                break;
            case 3:
                this.F();
                break;
            case 4:
                this.BC();
                break;
            case 5:
                this.B2();
                break;    
            case 6:
                this.B();
                break;
            case 7:
                this.UC();
                break;
            case 8:
                this.U2();
                break;
            case 9:
                this.U();
                break;
            case 10:
                this.DC();
                break;
            case 11:
                this.D2();
                break;
            case 12:
                this.D();
                break;
            case 13:
                this.RC();
                break;
            case 14:
                this.R2();
                break;
            case 15:
                this.R();
                break;
            case 16:
                this.LC();
                break;
            case 17:
                this.L2();
                break;
            case 18:
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

        if (s.charAt(s.length()-1) == '1' && s.charAt(s.length()-2) == '3')
            return false;
        else if (s.charAt(s.length()-1) == '3' && s.charAt(s.length()-2) == '1')
            return false;
        else if (s.charAt(s.length()-1) == '4' && s.charAt(s.length()-2) == '6')
            return false;
        else if (s.charAt(s.length()-1) == '6' && s.charAt(s.length()-2) == '4')
            return false;
        else if (s.charAt(s.length()-1) == '7' && s.charAt(s.length()-2) == '9')
            return false;
        else if (s.charAt(s.length()-1) == '9' && s.charAt(s.length()-2) == '7')
            return false;
        else if (s.charAt(s.length()-1) == 'a' && s.charAt(s.length()-2) == 'c')
            return false;
        else if (s.charAt(s.length()-1) == 'c' && s.charAt(s.length()-2) == 'a')
            return false;
        else if (s.charAt(s.length()-1) == 'd' && s.charAt(s.length()-2) == 'f')
            return false;
        else if (s.charAt(s.length()-1) == 'f' && s.charAt(s.length()-2) == 'd')
            return false;
        else if (s.charAt(s.length()-1) == 'g' && s.charAt(s.length()-2) == 'i')
            return false;
        else if (s.charAt(s.length()-1) == 'i' && s.charAt(s.length()-2) == 'g')
            return false;
        else if (s.charAt(s.length()-1) == '1' && s.charAt(s.length()-2) == '2')
            return false;
        else if (s.charAt(s.length()-1) == '2' && s.charAt(s.length()-2) == '1')
            return false;
        else if (s.charAt(s.length()-1) == '2' && s.charAt(s.length()-2) == '3')
            return false;
        else if (s.charAt(s.length()-1) == '3' && s.charAt(s.length()-2) == '2')
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
        else if (s.charAt(s.length()-1) == 'a' && s.charAt(s.length()-2) == 'b')
            return false;
        else if (s.charAt(s.length()-1) == 'b' && s.charAt(s.length()-2) == 'a')
            return false;
        else if (s.charAt(s.length()-1) == 'b' && s.charAt(s.length()-2) == 'c')
            return false;
        else if (s.charAt(s.length()-1) == 'c' && s.charAt(s.length()-2) == 'b')
            return false;
        else if (s.charAt(s.length()-1) == 'd' && s.charAt(s.length()-2) == 'e')
            return false;
        else if (s.charAt(s.length()-1) == 'e' && s.charAt(s.length()-2) == 'd')
            return false;
        else if (s.charAt(s.length()-1) == 'e' && s.charAt(s.length()-2) == 'f')
            return false;
        else if (s.charAt(s.length()-1) == 'f' && s.charAt(s.length()-2) == 'e')
            return false;
        else if (s.charAt(s.length()-1) == 'g' && s.charAt(s.length()-2) == 'h')
            return false;
        else if (s.charAt(s.length()-1) == 'h' && s.charAt(s.length()-2) == 'g')
            return false;
        else if (s.charAt(s.length()-1) == 'h' && s.charAt(s.length()-2) == 'i')
            return false;
        else if (s.charAt(s.length()-1) == 'i' && s.charAt(s.length()-2) == 'h')
            return false;
        else if ((s.charAt(s.length()-1) == '1' || s.charAt(s.length()-1) == '2' ||
                 s.charAt(s.length()-1) == '3') && (s.charAt(s.length()-2) == '4' || 
                 s.charAt(s.length()-2) == '5' || s.charAt(s.length()-2) == '6'))
            return false;
        else if ((s.charAt(s.length()-1) == '7' || s.charAt(s.length()-1) == '8' ||
                 s.charAt(s.length()-1) == '9') && (s.charAt(s.length()-2) == 'a' || 
                 s.charAt(s.length()-2) == 'b' || s.charAt(s.length()-2) == 'c'))
            return false;
        else if ((s.charAt(s.length()-1) == 'd' || s.charAt(s.length()-1) == 'e' ||
                 s.charAt(s.length()-1) == 'f') && (s.charAt(s.length()-2) == 'g' || 
                 s.charAt(s.length()-2) == 'h' || s.charAt(s.length()-2) == 'i'))
            return false;

        return true;
    }
    /*
    This method prints moves from a string representing moves
     */

    public void printStringToMoves (String s) {

        char c = '0';

        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '1')
                System.out.print("F ");
            else if (c == '2')
                System.out.print("F2 ");
            else if (c == '3')
                System.out.print("F' ");
            else if (c == '4')
                System.out.print("B ");
            else if (c == '5')
                System.out.print("B2 ");
            else if (c == '6')
                System.out.print("B' ");
            else if (c == '7')
                System.out.print("U ");
            else if (c == '8')
                System.out.print("U2 ");
            else if (c == '9')
                System.out.print("U' ");
            else if (c == 'a')
                System.out.print("D ");
            else if (c == 'b')
                System.out.print("D2 ");
            else if (c == 'c')
                System.out.print("D' ");
            else if (c == 'd')
                System.out.print("R ");
             else if (c == 'e')
                System.out.print("R2 ");
            else if (c == 'f')
                System.out.print("R' ");
            else if (c == 'g')
                System.out.print("L ");
            else if (c == 'h')
                System.out.print("L2 ");
            else if (c == 'i')
                System.out.print("L' ");
            else
                System.out.println("Invalid character in print string to moves of " + c);
        }

        System.out.println();

    }
    /*
    This method chandes char symbol to move number
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
        else if (c == 'a')
            return 10;
        else if (c == 'b')
            return 11;
        else if (c == 'c')
            return 12;
        else if (c == 'd')
            return 13;
        else if (c == 'e')
            return 14;
        else if (c == 'f')
            return 15;
        else if (c == 'g')
            return 16;
        else if (c == 'h')
            return 17;
        else if (c == 'i')
            return 18;
        else
            System.out.println("Invalid character in char to moves of "+c);

        return 0;

    }
/*
This method returns a hash from a state of a cube.
I used an imported JAR for faster performance of MD5 hash
 */
    public String stateToHash () throws NoSuchAlgorithmException {

        String s = "";

        for (int i = 0; i < this.cube.length; i++)
            for (int j = 0; j < this.cube[i].length; j++)
                for (int k = 0; k < this.cube[i][j].length; k++)
                    s = s + this.cube[i][j][k];

        //mhash = new MD5 ();
        MessageDigest mhash = MessageDigest.getInstance("MD5");
        //mhash.Update(s);
        //byte[] newArray = mhash.Final();
        byte[] newArray = s.getBytes();
        byte [] hash = mhash.digest(newArray);
        String h;
        h = Base64.getEncoder().encodeToString(hash);
        return h;

    }
    
    public static void main (String[] args) throws NoSuchAlgorithmException {
        
        RubikDatabaseBuilder b = new RubikDatabaseBuilder ();
        
        b.build();
               
    }
}
    
