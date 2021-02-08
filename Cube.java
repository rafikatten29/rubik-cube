
import java.util.Random;
import java.util.ArrayList;
/*
This class is the master class for all cube algorithms. It defines a cube and
the moves it can make. It also has useful methods that are need for algorithm
classes that inherit from it
White colour is 1;
Blue colour is 2;
Red colour is 3;
Green colour is 4;
Orange colour is 5;
Yellow colour is 6;
*/
public class Cube {

	/*protected*/ int [][][] cube; // This stores the cube in a 6x3x3 int array   
	ArrayList<String> solution;   // This stores the turns and rotatioms performs in an algorithm
	ArrayList<String> moves;      // This stores on;y the moves of a algorithm 
	ArrayList<String> rotations;  // This stores the rotations of a algorithm
	String [][] edge;             // This stores postions on the cube that correspond to the same edge
	String [][] corner;      // This stores positions on the cube corresponding to same corner
        
	public Cube () {

		solution = new ArrayList<String>();  
		moves = new ArrayList<String>();
		rotations = new ArrayList<String>();

		cube = new int [6][3][3];
	
		for (int i = 0; i < cube.length; i++)
			for (int j = 0; j < cube[i].length; j++)
				for (int k = 0; k < cube[i][j].length; k++)
					cube[i][j][k] = i+1;   // The cube is intialised with white side on cube[0], blue cube[1] and so on

		edge = new String [12][2];

		edge[0][0] = "001";
		edge[0][1] = "201";
		edge[1][0] = "010";
		edge[1][1] = "101";
		edge[2][0] = "021";
		edge[2][1] = "401";
		edge[3][0] = "012";
		edge[3][1] = "301";
		edge[4][0] = "110";
		edge[4][1] = "212";
		edge[5][0] = "210";
		edge[5][1] = "312";
		edge[6][0] = "310";
		edge[6][1] = "412";
		edge[7][0] = "410";
		edge[7][1] = "112";
		edge[8][0] = "501";
		edge[8][1] = "221";
		edge[9][0] = "510";
		edge[9][1] = "321";
		edge[10][0] = "521";
		edge[10][1] = "421";
		edge[11][0] = "512";
		edge[11][1] = "121";

		corner = new String[8][3];

		corner[0][0] = "000";
		corner[0][1] = "100";
		corner[0][2] = "202";
		corner[1][0] = "002";
		corner[1][1] = "200";
		corner[1][2] = "302";
		corner[2][0] = "022";
		corner[2][1] = "300";
		corner[2][2] = "402";
		corner[3][0] = "020";
		corner[3][1] = "400";
		corner[3][2] = "102";
		corner[4][0] = "500";
		corner[4][1] = "322";
		corner[4][2] = "220";
		corner[5][0] = "502";
		corner[5][1] = "222";
		corner[5][2] = "120";
		corner[6][0] = "522";
		corner[6][1] = "122";
		corner[6][2] = "420";
		corner[7][0] = "520";
		corner[7][1] = "422";
		corner[7][2] = "320";

	}
/*
 This method sets cube to be equal to int [][][] array       
 */
	public void setCube (int [][][] cube) {

		for (int i = 0; i < this.cube.length; i++)
			for (int j = 0; j < this.cube[i].length; j++)
				for (int k = 0; k < this.cube[i][j].length; k++) {
					if (cube[i][j][k] >= 1 && cube[i][j][k] <= 6)
						this.cube[i][j][k] = cube[i][j][k];
					else {
						System.out.println("Cannot set colour to be "+cube[i][j][k]+".");
						System.out.println("Colour must be between 1 and 6");
					}
				}
	}

	public int[][][] getCube () {

		return cube;
	}

	public void setSide (int[][] colours, int side) {

	
		for (int j = 0; j < this.cube[side].length; j++)
			for (int k = 0; k < this.cube[side][j].length; k++) {
				if (colours[j][k] >= 1 && colours[j][k] <= 6)
					this.cube[side][j][k] = colours[j][k];
				else {
					System.out.println("Cannot set colour to be "+colours[j][k]+".");
					System.out.println("Colour must be between 1 and 6");
				}
			}

	}

	public int[][] getSide (int side) {

		int [][] colours = new int [3][3];

		for (int j = 0; j < this.cube[side].length; j++)
			for (int k = 0; k < this.cube[side][j].length; k++)
				 colours[j][k] = this.cube[side][j][k];

		return colours;
	}

	public void setPosition (int colour, int pos1, int pos2, int pos3) {

		if (colour >=1 && colour <= 6)
			this.cube[pos1][pos2][pos3] = colour;
		else {
			System.out.println("Cannot set colour to be "+colour+".");
			System.out.println("Colour must be between 1 and 6");
		}
	}

	public int getPosition (int pos1, int pos2, int pos3) {

		return 	this.cube[pos1][pos2][pos3];
	}	
/*
        This method checks that there is nor more than 9 colours of each colour on cube
        */
	public boolean checkCube () {

		int [] count = new int [6];
		int colour = 0;
		
		for (int i = 0; i < this.cube.length; i++)
			for (int j = 0; j < this.cube[i].length; j++)
				for (int k = 0; k < this.cube[i][j].length; k++) {
					colour = this.cube[i][j][k];
					if (colour == 1)
						count[0]++;
					else if (colour == 2)
						count[1]++;
					else if (colour == 3)
						count[2]++;
					else if (colour == 4)
						count[3]++;
					else if (colour == 5)
						count[4]++;
					else if (colour == 6)
						count[5]++;
					}

		for (int i = 0; i < count.length; i++)
			if (count[i] != 9)
				return false;

		return true;

	}
/*
        This method prints solution contained in solution variable
        */
	public void printSolution () {

		for (int i = 0; i < solution.size(); i++) {
			System.out.print(solution.get(i) + " ");
		}
		System.out.println();
	}
        
        public String returnSolution () {
            
            String sol = "";
            
            for (int i = 0; i < solution.size(); i++) {
                sol = sol + solution.get(i) + " ";
            }
            
            return sol;
        }
/*
        This method prints moves in moves variable
        */
	public void printMoves () {

		for (int i = 0; i < moves.size(); i++) {
			System.out.print(moves.get(i) + " ");
		}
		System.out.println();
	}

/* We use the following notation
F - Front face
B - Back face
R - Right face
L - Left face
U - Upper face
D - Bottom face
To denote the anti-clockwise 90 degrees rotation I added the letter 'c'.
For example FC is F counter clockwise
*/
	public void F () {
		
		int temp1 = cube[4][0][1];
		int temp2 = cube[4][0][2];

		cube[4][0][2] = cube[4][0][0];
		cube[4][0][1] = cube[4][1][0];
		cube[4][0][0] = cube[4][2][0];
		cube[4][1][0] = cube[4][2][1];
		cube[4][2][0] = cube[4][2][2];
		cube[4][2][1] = cube[4][1][2];
		cube[4][2][2] = temp2;
		cube[4][1][2] = temp1;
		
		temp1 = cube[0][2][0];
		temp2 = cube[0][2][1];
		int temp3 = cube[0][2][2];

		cube[0][2][2] = cube[1][0][2];
		cube[0][2][1] = cube[1][1][2];
		cube[0][2][0] = cube[1][2][2];

		cube[1][0][2] = cube[5][2][2];
		cube[1][1][2] = cube[5][2][1];
		cube[1][2][2] = cube[5][2][0];

		cube[5][2][2] = cube[3][2][0];
		cube[5][2][1] = cube[3][1][0];
		cube[5][2][0] = cube[3][0][0];

		cube[3][2][0] = temp3;
		cube[3][1][0] = temp2;
		cube[3][0][0] = temp1;

	}

	public void F2 () {

		F();
		F();
	}

	public void FC () {

		F();
		F();
		F();
	}

	public void B () {


		int temp1 = cube[2][0][1];
		int temp2 = cube[2][0][2];

		cube[2][0][2] = cube[2][0][0];
		cube[2][0][1] = cube[2][1][0];
		cube[2][0][0] = cube[2][2][0];
		cube[2][1][0] = cube[2][2][1];
		cube[2][2][0] = cube[2][2][2];
		cube[2][2][1] = cube[2][1][2];
		cube[2][2][2] = temp2;
		cube[2][1][2] = temp1;

		
		temp1 = cube[0][0][0];
		temp2 = cube[0][0][1];
		int temp3 = cube[0][0][2];

		cube[0][0][0] = cube[3][0][2];
		cube[0][0][1] = cube[3][1][2];
		cube[0][0][2] = cube[3][2][2];

		cube[3][0][2] = cube[5][0][0];
		cube[3][1][2] = cube[5][0][1];
		cube[3][2][2] = cube[5][0][2];

		cube[5][0][0] = cube[1][2][0];
		cube[5][0][1] = cube[1][1][0];
		cube[5][0][2] = cube[1][0][0];

		cube[1][2][0] = temp1;
		cube[1][1][0] = temp2;
		cube[1][0][0] = temp3;

	}

	public void B2 () {

		B();
		B();
	}

	public void BC () {

		B();
		B();
		B();
	}

	public void U () {

		int temp1 = cube[0][0][1];
		int temp2 = cube[0][0][2];

		cube[0][0][2] = cube[0][0][0];
		cube[0][0][1] = cube[0][1][0];
		cube[0][0][0] = cube[0][2][0];
		cube[0][1][0] = cube[0][2][1];
		cube[0][2][0] = cube[0][2][2];
		cube[0][2][1] = cube[0][1][2];
		cube[0][2][2] = temp2;
		cube[0][1][2] = temp1;

		
		temp1 = cube[2][0][0];
		temp2 = cube[2][0][1];
		int temp3 = cube[2][0][2];

		cube[2][0][0] = cube[1][0][0];
		cube[2][0][1] = cube[1][0][1];
		cube[2][0][2] = cube[1][0][2];

		cube[1][0][0] = cube[4][0][0];
		cube[1][0][1] = cube[4][0][1];
		cube[1][0][2] = cube[4][0][2];

		cube[4][0][0] = cube[3][0][0];
		cube[4][0][1] = cube[3][0][1];
		cube[4][0][2] = cube[3][0][2];

		cube[3][0][0] = temp1;
		cube[3][0][1] = temp2;
		cube[3][0][2] = temp3;

	}

	public void U2 () {

		U();
		U();
	}

	public void UC () {

		U();
		U();
		U();
	}

	public void D () {
		
		int temp1 = cube[5][0][1];
		int temp2 = cube[5][0][2];

		cube[5][0][2] = cube[5][0][0];
		cube[5][0][1] = cube[5][1][0];
		cube[5][0][0] = cube[5][2][0];
		cube[5][1][0] = cube[5][2][1];
		cube[5][2][0] = cube[5][2][2];
		cube[5][2][1] = cube[5][1][2];
		cube[5][2][2] = temp2;
		cube[5][1][2] = temp1;

		temp1 = cube[3][2][0];
		temp2 = cube[3][2][1];
		int temp3 = cube[3][2][2];

		cube[3][2][2] = cube[4][2][2];
		cube[3][2][1] = cube[4][2][1];
		cube[3][2][0] = cube[4][2][0];

		cube[4][2][2] = cube[1][2][2];
		cube[4][2][1] = cube[1][2][1];
		cube[4][2][0] = cube[1][2][0];

		cube[1][2][2] = cube[2][2][2];
		cube[1][2][1] = cube[2][2][1];
		cube[1][2][0] = cube[2][2][0];

		cube[2][2][2] = temp3;
		cube[2][2][1] = temp2;
		cube[2][2][0] = temp1;

	}

	public void D2 () {

		D();
		D();
	}

	public void DC () {

		D();
		D();
		D();
	}

	public void R () {
		
		int temp1 = cube[3][0][1];
		int temp2 = cube[3][0][2];

		cube[3][0][2] = cube[3][0][0];
		cube[3][0][1] = cube[3][1][0];
		cube[3][0][0] = cube[3][2][0];
		cube[3][1][0] = cube[3][2][1];
		cube[3][2][0] = cube[3][2][2];
		cube[3][2][1] = cube[3][1][2];
		cube[3][2][2] = temp2;
		cube[3][1][2] = temp1;

		temp1 = cube[4][2][2];
		temp2 = cube[4][1][2];
		int temp3 = cube[4][0][2];

		cube[4][2][2] = cube[5][0][0];
		cube[4][1][2] = cube[5][1][0];
		cube[4][0][2] = cube[5][2][0];

		cube[5][2][0] = cube[2][2][0];
		cube[5][1][0] = cube[2][1][0];
		cube[5][0][0] = cube[2][0][0];

		cube[2][0][0] = cube[0][2][2];
		cube[2][1][0] = cube[0][1][2];
		cube[2][2][0] = cube[0][0][2];

		cube[0][2][2] = temp1;
		cube[0][1][2] = temp2;
		cube[0][0][2] = temp3;

	}

	public void R2 () {

		R();
		R();
	}

	public void RC () {

		R();
		R();
		R();
	}

	public void L () {
		
		int temp1 = cube[1][0][1];
		int temp2 = cube[1][0][2];

		cube[1][0][2] = cube[1][0][0];
		cube[1][0][1] = cube[1][1][0];
		cube[1][0][0] = cube[1][2][0];
		cube[1][1][0] = cube[1][2][1];
		cube[1][2][0] = cube[1][2][2];
		cube[1][2][1] = cube[1][1][2];
		cube[1][2][2] = temp2;
		cube[1][1][2] = temp1;

		temp1 = cube[4][0][0];
		temp2 = cube[4][1][0];
		int temp3 = cube[4][2][0];

		cube[4][0][0] = cube[0][0][0];
		cube[4][1][0] = cube[0][1][0];
		cube[4][2][0] = cube[0][2][0];

		cube[0][0][0] = cube[2][2][2];
		cube[0][1][0] = cube[2][1][2];
		cube[0][2][0] = cube[2][0][2];

		cube[2][2][2] = cube[5][2][2];
		cube[2][1][2] = cube[5][1][2];
		cube[2][0][2] = cube[5][0][2];

		cube[5][2][2] = temp1;
		cube[5][1][2] = temp2;
		cube[5][0][2] = temp3;

	}

	public void L2 () {

		L();
		L();
	}

	public void LC () {

		L();
		L();
		L();
	}
/*
        This method defines a rotation of 90 degrees insame direction as a F turn
        */
	public void rotate90AsF () {

		int temp1 = cube[4][0][1];
		int temp2 = cube[4][0][2];

		cube[4][0][2] = cube[4][0][0];
		cube[4][0][1] = cube[4][1][0];
		cube[4][0][0] = cube[4][2][0];
		cube[4][1][0] = cube[4][2][1];
		cube[4][2][0] = cube[4][2][2];
		cube[4][2][1] = cube[4][1][2];
		cube[4][2][2] = temp2;
		cube[4][1][2] = temp1;

		temp1 = cube[2][0][0];
		temp2 = cube[2][0][1];

		cube[2][0][0] = cube[2][0][2];
		cube[2][0][1] = cube[2][1][2];
		cube[2][0][2] = cube[2][2][2];
		cube[2][1][2] = cube[2][2][1];
		cube[2][2][2] = cube[2][2][0];
		cube[2][2][1] = cube[2][1][0];
		cube[2][2][0] = temp1;
		cube[2][1][0] = temp2;

		temp1 = cube[0][0][0];
		temp2 = cube[0][0][1];
		int temp3 = cube[0][0][2];
		int temp4 = cube[0][1][0];
		int temp5 = cube[0][1][1];
		int temp6 = cube[0][1][2];
		int temp7 = cube[0][2][0];
		int temp8 = cube[0][2][1];
		int temp9 = cube[0][2][2];

		cube[0][0][0] = cube[1][2][0];   // due to shift in where you count positions
		cube[0][0][1] = cube[1][1][0];
		cube[0][0][2] = cube[1][0][0];
		cube[0][1][0] = cube[1][2][1];
		cube[0][1][1] = cube[1][1][1];
		cube[0][1][2] = cube[1][0][1];
		cube[0][2][0] = cube[1][2][2];
		cube[0][2][1] = cube[1][1][2];
		cube[0][2][2] = cube[1][0][2];	

		cube[1][0][0] = cube[5][0][2];
		cube[1][0][1] = cube[5][1][2];
		cube[1][0][2] = cube[5][2][2];
		cube[1][1][0] = cube[5][0][1];
		cube[1][1][1] = cube[5][1][1];
		cube[1][1][2] = cube[5][2][1];
		cube[1][2][0] = cube[5][0][0];
		cube[1][2][1] = cube[5][1][0];
		cube[1][2][2] = cube[5][2][0];

		cube[5][0][0] = cube[3][0][2];
		cube[5][0][1] = cube[3][1][2];
		cube[5][0][2] = cube[3][2][2];
		cube[5][1][0] = cube[3][0][1];
		cube[5][1][1] = cube[3][1][1];
		cube[5][1][2] = cube[3][2][1];
		cube[5][2][0] = cube[3][0][0];
		cube[5][2][1] = cube[3][1][0];
		cube[5][2][2] = cube[3][2][0];	

		cube[3][0][0] = temp7; // cube[0][2][0];
		cube[3][0][1] = temp4; // cube[0][1][0];
		cube[3][0][2] = temp1; // cube[0][0][0];
		cube[3][1][0] = temp8; // cube[0][2][1];
		cube[3][1][1] = temp5; // cube[0][1][1];
		cube[3][1][2] = temp2; // cube[0][0][1];
		cube[3][2][0] = temp9; // cube[0][2][2];
		cube[3][2][1] = temp6; // cube[0][1][2];
		cube[3][2][2] = temp3; // cube[0][0][2];	
				

	}
/*
        This method defines a rotation of 90 degrees insame direction as a U turn
        */
	public void rotate90AsU () {

		int temp1 = cube[0][0][1];
		int temp2 = cube[0][0][2];

		cube[0][0][2] = cube[0][0][0];
		cube[0][0][1] = cube[0][1][0];
		cube[0][0][0] = cube[0][2][0];
		cube[0][1][0] = cube[0][2][1];
		cube[0][2][0] = cube[0][2][2];
		cube[0][2][1] = cube[0][1][2];
		cube[0][2][2] = temp2;
		cube[0][1][2] = temp1;

		temp1 = cube[5][0][0];
		temp2 = cube[5][0][1];

		cube[5][0][0] = cube[5][0][2];
		cube[5][0][1] = cube[5][1][2];
		cube[5][0][2] = cube[5][2][2];
		cube[5][1][2] = cube[5][2][1];
		cube[5][2][2] = cube[5][2][0];
		cube[5][2][1] = cube[5][1][0];
		cube[5][2][0] = temp1;
		cube[5][1][0] = temp2;

		temp1 = cube[4][0][0];
		temp2 = cube[4][0][1];
		int temp3 = cube[4][0][2];
		int temp4 = cube[4][1][0];
		int temp5 = cube[4][1][1];
		int temp6 = cube[4][1][2];
		int temp7 = cube[4][2][0];
		int temp8 = cube[4][2][1];
		int temp9 = cube[4][2][2];

		cube[4][0][0] = cube[3][0][0]; 
		cube[4][0][1] = cube[3][0][1];
		cube[4][0][2] = cube[3][0][2];
		cube[4][1][0] = cube[3][1][0];
		cube[4][1][1] = cube[3][1][1];
		cube[4][1][2] = cube[3][1][2];
		cube[4][2][0] = cube[3][2][0];
		cube[4][2][1] = cube[3][2][1];
		cube[4][2][2] = cube[3][2][2];	

		cube[3][0][0] = cube[2][0][0];
		cube[3][0][1] = cube[2][0][1];
		cube[3][0][2] = cube[2][0][2];
		cube[3][1][0] = cube[2][1][0];
		cube[3][1][1] = cube[2][1][1];
		cube[3][1][2] = cube[2][1][2];
		cube[3][2][0] = cube[2][2][0];
		cube[3][2][1] = cube[2][2][1];
		cube[3][2][2] = cube[2][2][2];	

		cube[2][0][0] = cube[1][0][0];
		cube[2][0][1] = cube[1][0][1];
		cube[2][0][2] = cube[1][0][2];
		cube[2][1][0] = cube[1][1][0];
		cube[2][1][1] = cube[1][1][1];
		cube[2][1][2] = cube[1][1][2];
		cube[2][2][0] = cube[1][2][0];
		cube[2][2][1] = cube[1][2][1];
		cube[2][2][2] = cube[1][2][2];	

		cube[1][0][0] = temp1; // cube[4][0][0]
		cube[1][0][1] = temp2; // cube[4][0][1]
		cube[1][0][2] = temp3; // cube[4][0][2]
		cube[1][1][0] = temp4; // cube[4][1][0]
		cube[1][1][1] = temp5; // cube[4][1][1]
		cube[1][1][2] = temp6; // cube[4][1][2]
		cube[1][2][0] = temp7; // cube[4][2][0]
		cube[1][2][1] = temp8; // cube[4][2][1]
		cube[1][2][2] = temp9; // cube[4][2][2]
				

	}
/*
        This method defines a rotation of 90 degrees insame direction as a R turn
        */
	public void rotate90AsR () {

		int temp1 = cube[3][0][1];
		int temp2 = cube[3][0][2];

		cube[3][0][2] = cube[3][0][0];
		cube[3][0][1] = cube[3][1][0];
		cube[3][0][0] = cube[3][2][0];
		cube[3][1][0] = cube[3][2][1];
		cube[3][2][0] = cube[3][2][2];
		cube[3][2][1] = cube[3][1][2];
		cube[3][2][2] = temp2;
		cube[3][1][2] = temp1;

		temp1 = cube[1][0][0];
		temp2 = cube[1][0][1];

		cube[1][0][0] = cube[1][0][2];
		cube[1][0][1] = cube[1][1][2];
		cube[1][0][2] = cube[1][2][2];
		cube[1][1][2] = cube[1][2][1];
		cube[1][2][2] = cube[1][2][0];
		cube[1][2][1] = cube[1][1][0];
		cube[1][2][0] = temp1;
		cube[1][1][0] = temp2;

		temp1 = cube[0][0][0];
		temp2 = cube[0][0][1];
		int temp3 = cube[0][0][2];
		int temp4 = cube[0][1][0];
		int temp5 = cube[0][1][1];
		int temp6 = cube[0][1][2];
		int temp7 = cube[0][2][0];
		int temp8 = cube[0][2][1];
		int temp9 = cube[0][2][2];

		cube[0][0][0] = cube[4][0][0];
		cube[0][0][1] = cube[4][0][1];
		cube[0][0][2] = cube[4][0][2];
		cube[0][1][0] = cube[4][1][0];
		cube[0][1][1] = cube[4][1][1];
		cube[0][1][2] = cube[4][1][2];
		cube[0][2][0] = cube[4][2][0];
		cube[0][2][1] = cube[4][2][1];
		cube[0][2][2] = cube[4][2][2];	

		cube[4][0][0] = cube[5][2][2];
		cube[4][0][1] = cube[5][2][1];
		cube[4][0][2] = cube[5][2][0];
		cube[4][1][0] = cube[5][1][2];
		cube[4][1][1] = cube[5][1][1];
		cube[4][1][2] = cube[5][1][0];
		cube[4][2][0] = cube[5][0][2];
		cube[4][2][1] = cube[5][0][1];
		cube[4][2][2] = cube[5][0][0];	

		cube[5][0][0] = cube[2][0][0]; 
		cube[5][0][1] = cube[2][0][1];
		cube[5][0][2] = cube[2][0][2];
		cube[5][1][0] = cube[2][1][0];
		cube[5][1][1] = cube[2][1][1];
		cube[5][1][2] = cube[2][1][2];
		cube[5][2][0] = cube[2][2][0];
		cube[5][2][1] = cube[2][2][1];
		cube[5][2][2] = cube[2][2][2];	

		cube[2][0][0] = temp9; // cube[0][2][2]
		cube[2][0][1] = temp8; // cube[0][2][1]
		cube[2][0][2] = temp7; // cube[0][2][0]
		cube[2][1][0] = temp6; // cube[0][1][2]
		cube[2][1][1] = temp5; // cube[0][1][1]
		cube[2][1][2] = temp4; // cube[0][1][0]
		cube[2][2][0] = temp3; // cube[0][0][2]
		cube[2][2][1] = temp2; // cube[0][0][1]
		cube[2][2][2] = temp1; // cube[0][0][0]	
				

	}
/*
        This method makes a random shuffle of the cube.
        */
	public void randomShuffle () {

		int shuffleID = 0;
		int[] x = null;

		Random randomShuffle = new Random ();
		Random shuffle = new Random ();
		int numberOfShuffles = randomShuffle.nextInt(50) + 50;

		while (numberOfShuffles > 0) {
			
			shuffleID = shuffle.nextInt(18) + 1;

			switch (shuffleID) {

				case 1:
					F();
					break;
				case 2:
					F2();
					break;
				case 3:
					FC();
					break;
				case 4:
					B();
					break;
				case 5:
					B2();
					break;
				case 6:
					BC();
					break;
				case 7:
					U();
					break;
				case 8:
					U2();
					break;
				case 9:
					UC();
					break;
				case 10:
					D();
					break;
				case 11:
					D2();
					break;
				case 12:
					DC();
					break;
				case 13:
					R();
					break;
				case 14:
					R2();
					break;
				case 15:
					RC();
					break;
				case 16:
					L();
					break;
				case 17:
					L2();
					break;
				case 18:
					LC();
					break;
			}
			numberOfShuffles--;
		}
	}
/*
        This method returns a string of the colour representec by the int
        parameter. This is used to print the cube to the terminal
        */
	public String numToCol (int i) {

		String colour = "";

		switch (i) {
			case 1:
				colour = "White";
				break;
			case 2:
				colour = "Blue";
				break;
			case 3:
				colour = "Red";
				break;
			case 4:
				colour = "Green";
				break;
			case 5:
				colour = "Orange";
				break;
			case 6:
				colour = "Yellow";
				break;
			default:
				System.out.println("Invalid number. Number cannot be "+i+". Number must be in range of 1 - 6.");
				break;
			}

		return colour;
	}			
/*
        This method prints a face to the termial
        */
	public void printFace (int face) {

		System.out.println("Face number: " + (face+1));
		System.out.println(numToCol(cube[face][0][0])+" "+numToCol(cube[face][0][1])+" "+numToCol(cube[face][0][2]));
		System.out.println(numToCol(cube[face][1][0])+" "+numToCol(cube[face][1][1])+" "+numToCol(cube[face][1][2]));
		System.out.println(numToCol(cube[face][2][0])+" "+numToCol(cube[face][2][1])+" "+numToCol(cube[face][2][2]));
	}
/*
        This method overwrites toString method, to print entire cube to the terminal
        */
        @Override
	public String toString () {

		printFace(0);
		printFace(1);
		printFace(2);
		printFace(3);
		printFace(4);
		printFace(5);
		
		return "The cube is as above";
	}
/*
        This method performs a move and updates the solution, moves and rotation
        variables depending on the parameter number
        */
	public void moveGenerator (int i) {

		switch (i) {
			
			case 1:
				F();
				solution.add("F");
				moves.add("F");
				break;
			case 2:
				F2();
				solution.add("F2");
				moves.add("F2");
				break;	
			case 3:
				FC();
				solution.add("F'");
				moves.add("F'");
				break;
			case 4:
				B();
				solution.add("B");
				moves.add("B");
				break;		
			case 5:
				B2();
				solution.add("B2");
				moves.add("B2");
				break;	
			case 6:
				BC();
				solution.add("B'");
				moves.add("B'");
				break;	
			case 7:
				U();
				solution.add("U");
				moves.add("U");
				break;		
			case 8:
				U2();
				solution.add("U2");
				moves.add("U2");
				break;	
			case 9:
				UC();
				solution.add("U'");
				moves.add("U'");
				break;
			case 10:
				D();
				solution.add("D");
				moves.add("D");
				break;		
			case 11:
				D2();
				solution.add("D2");
				moves.add("D2");
				break;	
			case 12:
				DC();
				solution.add("D'");
				moves.add("D'");
				break;
			case 13:
				R();
				solution.add("R");
				moves.add("R");
				break;		
			case 14:
				R2();
				solution.add("R2");
				moves.add("R2");
				break;	
			case 15:
				RC();
				solution.add("R'");
				moves.add("R'");
				break;	
			case 16:
				L();
				solution.add("L");
				moves.add("L");
				break;		
			case 17:
				L2();
				solution.add("L2");
				moves.add("L2");
				break;	
			case 18:
				LC();
				solution.add("L'");
				moves.add("L'");
				break;	
			case 19:
				rotate90AsF();
				solution.add("f");
				rotations.add("f");
				break;
			case 20:
				rotate90AsU();
				solution.add("u");
				rotations.add("u");
				break;	
			case 21:
				rotate90AsR();
				solution.add("r");
				rotations.add("r");
				break;
			default:
				System.out.println("Invalid method number of "+i);
				break;

			}
	}
/*
        This method detemines whether the state is solved
        */

	public boolean isSolved () {

		for (int i = 0; i < cube.length; i++)
			for (int j = 0; j < cube[i].length; j++)
				for (int k = 0; k < cube[i][j].length; k++)
					if (cube[i][j][k] != cube[i][1][1]) {
						return false;
					}

		return true;

	}
/*
        This method returns the edge piece containing x and y colours in position of x
        */
	public int [] edge (int x, int y) {                    

		int [] edgePostion = {-1, -1, -1};	
		String position1 = "";
		String position2 = "";
		String[] strArray1 = null;
		String[] strArray2 = null;
		int num1 = -1;
		int num2 = -1;
		int num3 = -1;
		int num4 = -1;
		int num5 = -1;
		int num6 = -1;

		for (int i = 0; i < edge.length; i++) {
			position1 = edge[i][0];
			strArray1 = position1.split("");
			num1 = Integer.parseInt(strArray1[0]);
			num2 = Integer.parseInt(strArray1[1]);
			num3 = Integer.parseInt(strArray1[2]);
			if (this.cube[num1][num2][num3] == x) {
				position2 = edge[i][1];
				strArray2 = position2.split("");
				num4 = Integer.parseInt(strArray2[0]);
				num5 = Integer.parseInt(strArray2[1]);
				num6 = Integer.parseInt(strArray2[2]);
				if (this.cube[num4][num5][num6] == y) {
					edgePostion[0] = num1;
					edgePostion[1] = num2;
					edgePostion[2] = num3;
					return edgePostion;
				}
			}
			else if (this.cube[num1][num2][num3] == y) {
				position2 = edge[i][1];
				strArray2 = position2.split("");
				num4 = Integer.parseInt(strArray2[0]);
				num5 = Integer.parseInt(strArray2[1]);
				num6 = Integer.parseInt(strArray2[2]);
				if (this.cube[num4][num5][num6] == x) {
					edgePostion[0] = num4;
					edgePostion[1] = num5;
					edgePostion[2] = num6;
					return edgePostion;
				}
			}
		}

		return edgePostion;

	}
/*
        This method searches the edge and corner arrays to find a piece
        */
	public int returnPiece (boolean cornerPiece, int piece, int face) {

		int colour = 0;
		String position = "";
		String [] strArray = null;
		int num1 = -1;
		int num2 = -1;
		int num3 = -1;

		if (cornerPiece) {
			position = corner[piece][face];
			strArray = position.split("");
			num1 = Integer.parseInt(strArray[0]);
			num2 = Integer.parseInt(strArray[1]);
			num3 = Integer.parseInt(strArray[2]);
		}
		else {
			position = edge[piece][face];
			strArray = position.split("");
			num1 = Integer.parseInt(strArray[0]);
			num2 = Integer.parseInt(strArray[1]);
			num3 = Integer.parseInt(strArray[2]);	
		}
			
		colour = cube[num1][num2][num3];
	
		return colour;
	}	
	/*
        The method searches for a corner piece containing x, y and z colours in position of x
        */	
	public int [] corner (int x, int y, int z) {          

		int [] cornerPostion = {-1, -1, -1};
		String position = "";
		String [] strArray = null;
		int num1 = -1;
		int num2 = -1;
		int num3 = -1;	

		for (int i = 0; i < corner.length; i++) {

			if (returnPiece(true,i,0) == x && ((returnPiece(true,i,1) == y && returnPiece(true,i,2) == z) || (returnPiece(true,i,1) == z && returnPiece(true,i,2) == y))) {
				position = corner[i][0];
				strArray = position.split("");
				num1 = Integer.parseInt(strArray[0]);
				num2 = Integer.parseInt(strArray[1]);
				num3 = Integer.parseInt(strArray[2]);    
			}
			else if (((returnPiece(true,i,0) == y && returnPiece(true,i,2) == z) || (returnPiece(true,i,0) == z && returnPiece(true,i,2) == y)) && returnPiece(true,i,1) == x) {
				position = corner[i][1];
				strArray = position.split("");
				num1 = Integer.parseInt(strArray[0]);
				num2 = Integer.parseInt(strArray[1]);
				num3 = Integer.parseInt(strArray[2]);    
			}
			else if (((returnPiece(true,i,0) == y && returnPiece(true,i,1) == z) || (returnPiece(true,i,0) == z && returnPiece(true,i,1) == y)) && returnPiece(true,i,2) == x) {
				position = corner[i][2];
				strArray = position.split("");
				num1 = Integer.parseInt(strArray[0]);
				num2 = Integer.parseInt(strArray[1]);
				num3 = Integer.parseInt(strArray[2]);    
			}
		}

		cornerPostion[0] = num1;
		cornerPostion[1] = num2;
		cornerPostion[2] = num3;

		return cornerPostion;


        }

}
							
				
	
