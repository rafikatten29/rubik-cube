
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class BeginnersMethod extends Cube {

        private final int WINDOW_WIDTH = 1200;
        private final int WINDOW_HEIGHT = 800;
        private JFrame frame;
        private JPanel topRubik;
        private JPanel bottomRubik;
        private JPanel frontRubik;
        private JPanel backRubik;
        private JPanel leftRubik;
        private JPanel rightRubik;
        private JPanel colours;
        private JPanel buttons;
        private JPanel empty1;
        private JPanel empty2;
        private JPanel empty3;
        private JPanel empty4;
        private JButton[][][] cubeButtons = new JButton [6][3][3];
        private JRadioButton white;
        private JRadioButton blue;
        private JRadioButton red;
        private JRadioButton green;
        private JRadioButton orange;
        private JRadioButton yellow;
        private ButtonGroup buttonGroup;
        private JButton random;
        private JButton answer;
        private JTextArea display;
        JScrollPane scrollPane;
        
	public BeginnersMethod () {

	super();
        

	}
        
        public void buildInterface () {
            
           frame = new JFrame ();
            frame.setTitle("Beginners Method");
            frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GridLayout grid = new GridLayout(3, 4, 30 , 30);
            frame.setLayout(grid);
        
            topRubik = new JPanel();
            bottomRubik = new JPanel();
            frontRubik = new JPanel();
            backRubik = new JPanel();
            leftRubik = new JPanel();
            rightRubik = new JPanel();
            colours = new JPanel();
            buttons = new JPanel();
            empty1 = new JPanel ();
            empty2 = new JPanel ();
            empty3 = new JPanel ();
            empty4 = new JPanel ();
        
            topRubik.setLayout(new GridLayout(3,3));
            bottomRubik.setLayout(new GridLayout(3,3));
            frontRubik.setLayout(new GridLayout(3,3));
            backRubik.setLayout(new GridLayout(3,3));
            leftRubik.setLayout(new GridLayout(3,3));
            rightRubik.setLayout(new GridLayout(3,3));
            colours.setLayout(new GridLayout(3,2));
        
        
            for (int i = 0; i < cube.length; i++)
		for (int j = 0; j < cube[i].length; j++)
                    for (int k = 0; k < cube[i][j].length; k++)
                        cubeButtons[i][j][k] = new JButton ();
        
            cubeButtons[0][1][1].setBackground(Color.white);
            cubeButtons[1][1][1].setBackground(Color.blue);
            cubeButtons[2][1][1].setBackground(Color.red);
            cubeButtons[3][1][1].setBackground(Color.green);
            cubeButtons[4][1][1].setBackground(Color.orange);
            cubeButtons[5][1][1].setBackground(Color.yellow);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    topRubik.add(cubeButtons[0][j][k]);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    bottomRubik.add(cubeButtons[5][j][k]);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    frontRubik.add(cubeButtons[4][j][k]);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    backRubik.add(cubeButtons[2][j][k]);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    leftRubik.add(cubeButtons[1][j][k]);
        
            for (int j = 0; j < cube[0].length; j++)
		for (int k = 0; k < cube[0][j].length; k++)
                    rightRubik.add(cubeButtons[3][j][k]);
        
            white = new JRadioButton("White");
            blue = new JRadioButton("Blue");
            red = new JRadioButton("Red");
            green = new JRadioButton("Green");
            orange = new JRadioButton("Orange");
            yellow = new JRadioButton("Yellow");
            buttonGroup = new ButtonGroup();
            buttonGroup.add(white);
            buttonGroup.add(blue);
            buttonGroup.add(red);
            buttonGroup.add(green);
            buttonGroup.add(orange);
            buttonGroup.add(yellow);
            colours.add(white);
            colours.add(blue);
            colours.add(red);
            colours.add(green);
            colours.add(orange);
            colours.add(yellow);
        
            random = new JButton("Random");
            answer = new JButton("Solve");
            buttons.add(random);
            buttons.add(answer);
            
            Font font = new Font("Arial", Font.PLAIN, 12);
        
            display = new JTextArea("Solution:", 10, 20);
            display.setFont(font);
            display.setLineWrap(true);
            display.setWrapStyleWord(true);
            scrollPane = new JScrollPane(display);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            empty2.add(scrollPane);
        
            for (int i = 0; i < cube.length; i++)
                for (int j = 0; j < cube[i].length; j++)
                    for (int k = 0; k < cube[i][j].length; k++)
                        if (j != 1 || k != 1)
                            cubeButtons[i][j][k].addActionListener(new ButtonListener ());
         
            random.addActionListener(new ButtonListener ());
            answer.addActionListener(new ButtonListener ());
        
            frame.add(empty1);
            frame.add(topRubik);
            frame.add(empty2);
            frame.add(empty3);
            frame.add(leftRubik);
            frame.add(frontRubik);
            frame.add(rightRubik);
            frame.add(backRubik);
            frame.add(empty4);
            frame.add(bottomRubik);
            frame.add(colours);
            frame.add(buttons);
        
            frame.pack();
            frame.setVisible(true);
        
        }
/*
        This method is called to start search
        */
	public void solve () {
            
            
            if (this.checkCube()) {
                

            layerOne();

            layerTwo();

	    layerThree();
            }

	}
        /*
        This method solves first layer
        */

	public void layerOne () {

		whiteCross();
		whiteCorners();


	}	
/*
        This method solves white cross
        */
	public void whiteCross () {

		int [] whiteBlue = edge(1,2);
		whiteBlue(whiteBlue);
		int [] whiteRed = edge(1,3);
		moveGenerator(20);
		moveGenerator(20);
		moveGenerator(20);
		whiteRed = edge(1,3);
		whiteRed(whiteRed);
		moveGenerator(20);
		moveGenerator(20);
		moveGenerator(20);
		int [] whiteGreen = edge(1,4);
		whiteGreen(whiteGreen);
		moveGenerator(20);
		moveGenerator(20);
		moveGenerator(20);
		int [] whiteOrange = edge(1,5);
		whiteOrange(whiteOrange);

	}
/*
        This method places white blue edge in correct position
        */
	
	public void whiteBlue (int [] position) {
	
		if (position[0] != 0 || position[1] != 1 || position[2] != 0) {

			if (position[0] == 0) {
				if (position[2] == 2)
					moveGenerator(8);
				else if (position[1] == 0)
					moveGenerator(9);
				else
					moveGenerator(7);
			}

			else if (position[0] == 5) {
				if (position[2] == 2)
					moveGenerator(17);
				else if (position[1] == 0) {
					moveGenerator(10);
					moveGenerator(17);
				} else if (position[1] == 2) {
					moveGenerator(12);
					moveGenerator(17);
				} else {
					moveGenerator(11);
					moveGenerator(17);
				}
			}
			else if (position[0] == 4 && position[2] == 0)
				moveGenerator(18);
			else if (position[0] == 2 && position[2] == 2)
				moveGenerator(16);
			else if (position[0] == 4) {
				if (position[1] == 0) {
					moveGenerator(3);
					moveGenerator(18);
				} else if (position[1] == 2) {
					moveGenerator(1);
					moveGenerator(18);
				} else {
					moveGenerator(2);
					moveGenerator(18);
				}
			} else if (position[0] == 2) {
				if (position[1] == 0) {
					moveGenerator(4);
					moveGenerator(16);
				} else if (position[1] == 2) {
					moveGenerator(6);
					moveGenerator(16);
				} else {
					moveGenerator(5);
					moveGenerator(16);
				}
			} else if (position[0] == 3) {
				if (position[1] == 0) {
					moveGenerator(15);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(17);
				}
				else if (position[1] == 2) {
					moveGenerator(13);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(17);
				}
				else if (position[2] == 2) {
					moveGenerator(14);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(17);
				}
				else if (position[2] == 0) {
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(17);
				}
			} else {
				if (position[1] == 0) {
					moveGenerator(16);
					moveGenerator(3);
					moveGenerator(12);
				}
				else if (position[1] == 2) {
					moveGenerator(16);
					moveGenerator(4);
					moveGenerator(10);
				}
				else if (position[2] == 0) {
					moveGenerator(4);
					moveGenerator(10);
				} else if (position[2] == 2) {
					moveGenerator(3);
					moveGenerator(12);
				}
				moveGenerator(17);
			}
		}

	}
        /*
       This method places white red edge in correct position
        */
	public void whiteRed (int [] position) {


		if (position[0] != 0 || position[1] != 1 || position[2] != 0) {

			if (position[0] == 0) {
				if (position[1] == 0) {
					moveGenerator(5);
					moveGenerator(10);
					moveGenerator(17);
				}
				else {
					moveGenerator(14);
					moveGenerator(11);
					moveGenerator(17);
				}
			}

			else if (position[0] == 5) {
				if (position[2] == 2)
					moveGenerator(17);
				else if (position[1] == 0) {
					moveGenerator(10);
					moveGenerator(17);
				} else if (position[1] == 2) {
					moveGenerator(12);
					moveGenerator(17);
				} else {
					moveGenerator(11);
					moveGenerator(17);
				}
			}
			else if (position[0] == 4 && position[2] == 0)
				moveGenerator(18);
			else if (position[0] == 2 && position[2] == 2)
				moveGenerator(16);
			else if (position[0] == 4) {
				if (position[1] == 2) {
					moveGenerator(1);
					moveGenerator(18);
					moveGenerator(3);
				} else {
					moveGenerator(2);
					moveGenerator(18);
					moveGenerator(2);
				}
			} else if (position[0] == 2) {
				if (position[1] == 0) {
					moveGenerator(4);
					moveGenerator(16);
				} else if (position[1] == 2) {
					moveGenerator(6);
					moveGenerator(16);
				} else {
					moveGenerator(5);
					moveGenerator(16);
				}
			} else if (position[0] == 3) {
				if (position[1] == 0) {
					moveGenerator(15);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
					moveGenerator(17);
				}
				else if (position[1] == 2) {
					moveGenerator(13);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
					moveGenerator(17);
				}
				else if (position[2] == 2) {
					moveGenerator(14);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
					moveGenerator(17);
				}
				else if (position[2] == 0) {
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
					moveGenerator(17);
				}
			} else {
				if (position[1] == 0) {
					moveGenerator(16);
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				else if (position[1] == 2) {
					moveGenerator(16);
					moveGenerator(4);
					moveGenerator(10);
				}
				else if (position[2] == 0) {
					moveGenerator(4);
					moveGenerator(10);
				} else if (position[2] == 2) {
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				moveGenerator(17);
			}
		}


	}
/*
        This method places white green edge in correct position
        */
	public void whiteGreen (int [] position) {

		if (position[0] != 0 || position[1] != 1 || position[2] != 0) {

			if (position[0] == 0) {
					moveGenerator(5);
					moveGenerator(10);
					moveGenerator(17);
			}

			else if (position[0] == 5) {
				if (position[2] == 2)
					moveGenerator(17);
				else if (position[1] == 0) {
					moveGenerator(10);
					moveGenerator(17);
				} else if (position[1] == 2) {
					moveGenerator(12);
					moveGenerator(17);
				} else {
					moveGenerator(11);
					moveGenerator(17);
				}
			}
			else if (position[0] == 4 && position[2] == 0)
				moveGenerator(18);
			else if (position[0] == 2 && position[2] == 2)
				moveGenerator(16);
			else if (position[0] == 4) {
				if (position[1] == 2) {
					moveGenerator(1);
					moveGenerator(18);
					moveGenerator(3);
				} else {
					moveGenerator(2);
					moveGenerator(18);
					moveGenerator(2);
				}
			} else if (position[0] == 2) {
				if (position[1] == 0) {
					moveGenerator(4);
					moveGenerator(16);
				} else if (position[1] == 2) {
					moveGenerator(6);
					moveGenerator(16);
				} else {
					moveGenerator(5);
					moveGenerator(16);
				}
			} else if (position[0] == 3) {
				if (position[1] == 2) {
					moveGenerator(15);
					moveGenerator(6);
					moveGenerator(13);
					moveGenerator(10);
					moveGenerator(17);
				}
				else if (position[2] == 2) {
					moveGenerator(6);
					moveGenerator(10);
					moveGenerator(17);
				}
				else if (position[2] == 0) {
					moveGenerator(14);
					moveGenerator(6);
					moveGenerator(14);
					moveGenerator(10);
					moveGenerator(17);
				}
			} else {
				if (position[1] == 0) {
					moveGenerator(16);
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				else if (position[1] == 2) {
					moveGenerator(16);
					moveGenerator(4);
					moveGenerator(10);
				}
				else if (position[2] == 0) {
					moveGenerator(4);
					moveGenerator(10);
				} else if (position[2] == 2) {
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				moveGenerator(17);
			}
		}

	}
/*
        This method places white orange edge in correct position
        */
	public void whiteOrange (int [] position) {

		if (position[0] != 0 || position[1] != 1 || position[2] != 0) {

			if (position[0] == 5) {
				if (position[2] == 2)
					moveGenerator(17);
				else if (position[1] == 0) {
					moveGenerator(10);
					moveGenerator(17);
				} else if (position[1] == 2) {
					moveGenerator(12);
					moveGenerator(17);
				} else {
					moveGenerator(11);
					moveGenerator(17);
				}
			}
			else if (position[0] == 4 && position[2] == 0)
				moveGenerator(18);
			else if (position[0] == 2 && position[2] == 2)
				moveGenerator(16);
			else if (position[0] == 4) {
				if (position[1] == 2) {
					moveGenerator(1);
					moveGenerator(18);
					moveGenerator(3);
				} else {
					moveGenerator(2);
					moveGenerator(18);
					moveGenerator(2);
				}
			} else if (position[0] == 2) {
				if (position[1] == 0) {
					moveGenerator(4);
					moveGenerator(16);
					moveGenerator(6);
				} else if (position[1] == 2) {
					moveGenerator(6);
					moveGenerator(16);
					moveGenerator(4);
				} else {
					moveGenerator(5);
					moveGenerator(16);
					moveGenerator(5);
				}
			} else if (position[0] == 3) {
				if (position[1] == 2) {
					moveGenerator(15);
					moveGenerator(6);
					moveGenerator(10);
					moveGenerator(17);
					moveGenerator(4);
					moveGenerator(13);
				}
				else if (position[2] == 2) {
					moveGenerator(6);
					moveGenerator(10);
					moveGenerator(4);
					moveGenerator(17);
				}
				else if (position[2] == 0) {
					moveGenerator(14);
					moveGenerator(6);
					moveGenerator(10);
					moveGenerator(17);
					moveGenerator(12);
					moveGenerator(4);
					moveGenerator(14);
				}
			} else {
				if (position[1] == 0) {
					moveGenerator(16);
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				else if (position[1] == 2) {
					moveGenerator(16);
					moveGenerator(4);
					moveGenerator(10);
					moveGenerator(6);
				}
				else if (position[2] == 0) {
					moveGenerator(4);
					moveGenerator(10);
					moveGenerator(6);
				} else if (position[2] == 2) {
					moveGenerator(3);
					moveGenerator(12);
					moveGenerator(1);
				}
				moveGenerator(17);
			}
		}

	}
	/*
        This method solves the white corners
        */
	public void whiteCorners () {

		int [] whiteGreenRed = corner(1,4,3);
		if (whiteGreenRed[0] == 0 || (whiteGreenRed[0] != 5 && whiteGreenRed[1] == 0))
			bringDownCorner(whiteGreenRed, 1);
		else if (whiteGreenRed[0] == 5)
			bringUpCorner(whiteGreenRed, 1);
		else
			rightTopCorner(whiteGreenRed);

		moveGenerator(20);
		int [] whiteRedBlue = corner(1,3,2);
		if (whiteRedBlue[0] == 0 || (whiteRedBlue[0] != 5 && whiteRedBlue[1] == 0))
			bringDownCorner(whiteRedBlue, 2);
		else if (whiteRedBlue[0] == 5)
			bringUpCorner(whiteRedBlue, 2);
		else
			rightTopCorner(whiteRedBlue);

		moveGenerator(20);
		int [] whiteBlueOrange = corner(1,2,5);
		if (whiteBlueOrange[0] == 0 || (whiteBlueOrange[0] != 5 && whiteBlueOrange[1] == 0))
			bringDownCorner(whiteBlueOrange, 3);
		else if (whiteBlueOrange[0] == 5)
			bringUpCorner(whiteBlueOrange, 3);
		else
			rightTopCorner(whiteBlueOrange);

		moveGenerator(20);
		int [] whiteOrangeGreen = corner(1,5,4);
		if (whiteOrangeGreen[0] == 0 || (whiteOrangeGreen[0] != 5 && whiteOrangeGreen[1] == 0))
			bringDownCorner(whiteOrangeGreen, 4);
		else if (whiteOrangeGreen[0] == 5)
			bringUpCorner(whiteOrangeGreen, 4);
		else
			rightTopCorner(whiteOrangeGreen);

	}
/*
        This method brings down a white corner to the bottom layer
        */
	public void bringDownCorner (int [] position, int cornerNum) {



		if (position[0] != 0 || position[1] != 2 || position[2] != 2) {

			if (position[0] == 0) {
				if (position[1] == 0 && position[2] == 0) {
					moveGenerator(18);
					moveGenerator(10);
					moveGenerator(16);
				} else if (position[1] == 0 && position[2] == 2) {
					moveGenerator(13);
					moveGenerator(12);
					moveGenerator(15);
				} else if (position[1] == 2 && position[2] == 0) {
					moveGenerator(16);
					moveGenerator(10);
					moveGenerator(18);
				}
			} else {
				if (position[0] == 1 && position[1] == 0 && position[2] == 0) {
					moveGenerator(18);
					moveGenerator(12);
					moveGenerator(16);
				} else if (position[0] == 1 && position[1] == 0 && position[2] == 2) {
					moveGenerator(16);
					moveGenerator(11);
					moveGenerator(18);
				} else if (position[0] == 2 && position[1] == 0 && position[2] == 0) {
					moveGenerator(6);
					moveGenerator(12);
					moveGenerator(4);
				} else if (position[0] == 2 && position[1] == 0 && position[2] == 2) {
					moveGenerator(4);
					moveGenerator(10);
					moveGenerator(6);
				} else if (position[0] == 3 && position[1] == 0 && position[2] == 0) {
					moveGenerator(15);
					moveGenerator(12);
					moveGenerator(13);
				} else if (position[0] == 3 && position[1] == 0 && position[2] == 2) {
					moveGenerator(13);
					moveGenerator(11);
					moveGenerator(15);
				} else if (position[0] == 4 && position[1] == 0 && position[2] == 0) {
					moveGenerator(3);
					moveGenerator(11);
					moveGenerator(1);
				} else {
					moveGenerator(1);
					moveGenerator(10);
					moveGenerator(3);
				}
			}
			if (cornerNum == 1) {
				int[] whiteGreenRed = corner(1, 4, 3);
				rightTopCorner(whiteGreenRed);
			} else if (cornerNum == 2) {
				int[] whiteRedBlue = corner(1, 3, 2);
				rightTopCorner(whiteRedBlue);
			} else if (cornerNum == 3) {
				int[] whiteBlueOrange = corner(1, 2, 5);
				rightTopCorner(whiteBlueOrange);
			} else if (cornerNum == 4) {
				int[] whiteOrangeGreen = corner(1, 5, 4);
				rightTopCorner(whiteOrangeGreen);
			}
		}
	}
/*
        This method brings up a white corner piece that is on the yellow side
        facing downwards so that the white face in on another cube face
        */
	public void bringUpCorner (int [] position, int cornerNum) {

		if (position[1] == 0 && position[2] == 0) {
			moveGenerator(12);
			moveGenerator(15);
			moveGenerator(10);
			moveGenerator(13);
		}
		else if  (position[1] == 0 && position[2] == 2) {
			moveGenerator(11);
			moveGenerator(15);
			moveGenerator(10);
			moveGenerator(13);
		}
		else if  (position[1] == 2 && position[2] == 0) {
			moveGenerator(15);
			moveGenerator(10);
			moveGenerator(13);
		}
		else {
			moveGenerator(10);
			moveGenerator(15);
			moveGenerator(10);
			moveGenerator(13);
		}

		if (cornerNum == 1) {
			int [] whiteGreenRed = corner(1,4,3);
			rightTopCorner(whiteGreenRed);
		}
		else if (cornerNum == 2) {
			int [] whiteRedBlue = corner(1,3,2);
			rightTopCorner(whiteRedBlue);
		}
		else if (cornerNum == 3) {
			int[] whiteBlueOrange = corner(1, 2, 5);
			rightTopCorner(whiteBlueOrange);
		}
		else if (cornerNum == 4) {
			int[] whiteOrangeGreen = corner(1, 5, 4);
			rightTopCorner(whiteOrangeGreen);
		}
	}
/*
       This method insets white corner piece on top right hand corner
        */
	public void rightTopCorner (int [] position) {

			if (position[2] == 0) {
				if (position[0] == 1) {
					moveGenerator(12);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
				}
				else if (position[0] == 2) {
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
				}
				else if (position[0] == 3) {
					moveGenerator(10);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
				}
				else {
					moveGenerator(11);
					moveGenerator(1);
					moveGenerator(12);
					moveGenerator(3);
				}
			}
			else if (position[2] == 2){
				if (position[0] == 1) {
					moveGenerator(15);
					moveGenerator(10);
					moveGenerator(13);
				}
				else if (position[0] == 2) {
					moveGenerator(10);
					moveGenerator(15);
					moveGenerator(10);
					moveGenerator(13);
				}
				else if (position[0] == 3) {
					moveGenerator(11);
					moveGenerator(15);
					moveGenerator(10);
					moveGenerator(13);
				}
				else {
					moveGenerator(12);
					moveGenerator(15);
					moveGenerator(10);
					moveGenerator(13);
				}

			}

	}
/*
        This method solves the second layer
        */
	public void layerTwo () {

		System.out.println(checkCube());
		moveGenerator(19);
		moveGenerator(19);    // The cube should have yellow side facing up and orange side in front
		System.out.println(checkCube());
		layerTwoPieces();

	}
/*
        This method iterates through the layer two pieces trying to find a piece
        that is in the correct postion for the algorithm. If no pieces are found
        but the layer two has not been solved then it deals with the piece which
        has a wrong orientation.
        */
	public void layerTwoPieces () {

		int[] orangeGreen = null;
		int[] orangeBlue = null;
		int[] redBlue = null;
		int[] redGreen = null;
		int count = 0;
		int corrections = 0;
		boolean og = false;
		boolean ob = false;
		boolean rb = false;
		boolean rg = false;

		while (count < 4 && (!og || !ob || !rb || !rg)) {

			orangeGreen = edge(5, 4);
			if (layerTwoCorrectPositon(orangeGreen)) {
				System.out.println("Testing orange green.");
				System.out.println("Orange green start position is "+orangeGreen[0]+""+orangeGreen[1]+orangeGreen[2]);
				layerTwoAlgorithm(orangeGreen, false, 1);
				orangeGreen = edge(5, 4);
				System.out.println("Orange green end position is "+orangeGreen[0]+""+orangeGreen[1]+orangeGreen[2]);
				og = true;
				corrections++;
			}

			orangeBlue = edge(5, 2);
			if (layerTwoCorrectPositon(orangeBlue)) {
				System.out.println("Testing orange blue.");
				System.out.println("Orange blue start position is "+orangeBlue[0]+""+orangeBlue[1]+orangeBlue[2]);
				layerTwoAlgorithm(orangeBlue, true, 2);
				orangeBlue = edge(5, 2);
				System.out.println("Orange blue end position is "+orangeBlue[0]+""+orangeBlue[1]+orangeBlue[2]);
				ob = true;
				corrections++;
			}
			moveGenerator(20);
			moveGenerator(20);
			redBlue = edge(3, 2);
			if (layerTwoCorrectPositon(redBlue)) {
				System.out.println("Testing red blue.");
				System.out.println("Red blue start position is "+redBlue[0]+""+redBlue[1]+redBlue[2]);
				layerTwoAlgorithm(redBlue, false, 3);
				redBlue = edge(3, 2);
				System.out.println("Red blue end position is "+redBlue[0]+""+redBlue[1]+redBlue[2]);
				rb = true;
				corrections++;
			}

			redGreen = edge(3, 4);
			if (layerTwoCorrectPositon(redGreen)) {
				System.out.println("Testing red green.");
				System.out.println("Red green start position is "+redGreen[0]+""+redGreen[1]+redGreen[2]);
				layerTwoAlgorithm(redGreen, true, 4);
				redGreen = edge(3, 4);
				System.out.println("Red green end position is "+redGreen[0]+""+redGreen[1]+redGreen[2]);
				rg = true;
				corrections++;
			}
			moveGenerator(20);
			moveGenerator(20);
			if (corrections > 0) {
				count++;
				System.out.println("The count is "+count);
				corrections = 0;
			}
			else {
				System.out.println("Entered the replacement section");
				int[] replacement = {4, 0, 1};
				if (!og) {
					layerTwoAlgorithm(replacement, false, 1);
				} else if (!ob) {
					layerTwoAlgorithm(replacement, true, 2);
				} else if (!rb) {
					moveGenerator(20);
					moveGenerator(20);
					layerTwoAlgorithm(replacement, false, 3);
					moveGenerator(20);
					moveGenerator(20);
				} else if (!rg) {
					moveGenerator(20);
					moveGenerator(20);
					layerTwoAlgorithm(replacement, true, 4);
					moveGenerator(20);
					moveGenerator(20);
				}
			}
		}


	}
/*
        This method checks that the layer two piece is in the correct position
        for the algorithm
        */
	public boolean layerTwoCorrectPositon (int [] position) {

		if (position[0] == 0)
				return true;
		else if (position[1] == 0) {
				return true;
		}

		return false;
	}
/*
        This method peforms the algorithm for the layer two piece
        */
	public void layerTwoAlgorithm (int [] position, boolean right, int side) {

		int [] colourPosition = null;

		if (!right) {

			if (position[0] != 0) {
				if (position[0] == 4) {
					moveGenerator(9);
				}
				else if (position[0] == 1) {
					moveGenerator(8);
				}
				else if (position[0] == 2) {
					moveGenerator(7);
				}
				moveGenerator(18);
				moveGenerator(7);
				moveGenerator(16);
				moveGenerator(7);
				moveGenerator(1);
				moveGenerator(9);
				moveGenerator(3);
			}
			else if(position[0] == 0) {
				if (side == 1) {
					moveGenerator(20);
					moveGenerator(20);
					moveGenerator(20);  // Turn so that green side is facing at front
					colourPosition = edge(4, 5);  // switch which colour is searched for in edge
					layerTwoAlgorithm(colourPosition, true, 1);
					moveGenerator(20);
				}
				else if (side == 3) {
					moveGenerator(20);
					moveGenerator(20);
					moveGenerator(20);  // Turn so that blue side is facing at front
					colourPosition = edge(2, 3);
					layerTwoAlgorithm(colourPosition, true, 3);
					moveGenerator(20);
				}
			}
		}
		else if (right) {
			if (position[0] != 0) {
				if (position[0] == 4) {
					moveGenerator(7);
				} else if (position[0] == 3) {
					moveGenerator(8);
				} else if (position[0] == 2) {
					moveGenerator(9);
				}
				moveGenerator(13);
				moveGenerator(9);
				moveGenerator(15);
				moveGenerator(9);
				moveGenerator(3);
				moveGenerator(7);
				moveGenerator(1);
			} else if (position[0] == 0) {
				if (side == 2) {
					moveGenerator(20);
					colourPosition = edge(2, 5);
					layerTwoAlgorithm(colourPosition, false, 2);
					moveGenerator(20);
					moveGenerator(20);
					moveGenerator(20);
				} else if (side == 4) {
					moveGenerator(20);
					colourPosition = edge(4, 3);
					layerTwoAlgorithm(colourPosition, false, 4);
					moveGenerator(20);
					moveGenerator(20);
					moveGenerator(20);
				}
			}
		}
	}
/*
        This method performs solves the third layer
        */
	public void layerThree () {

		yellowCross();
		yellowCorners();
		orientCorners();
		orientCross();
	}
/*
        This method solves the yellow cross
        */
	public void yellowCross () {


		int state = analysisYellow();     // Dot is state 1, L shape is, rod is state 3, and cross is state 4

		if (state == 1) {
			yellowCrossAlgorithm2();
			yellowCross();
		}
		else if (state == 2) {
			yellowCrossAlgorithm1();
			yellowCross();
		}
		else if (state == 3) {
			yellowCrossAlgorithm2();
		}

	}
/*
        This method returns an integer indicating what is on the yellow side
        1 - yellow dot
        2 - yellow 'L' shape
        3 = yellow rod
        4 - yellow cross
        The algorithm depends on this analsis. See Ruwix website for
        explanation of how the algorithm works
        */
	public int analysisYellow () {

		if (cube[0][1][0] != 6 && cube[0][0][1] != 6 && cube[0][1][2] != 6 && cube[0][2][1] != 6) {
			return 1;
		}
		else if (cube[0][1][0] == 6 && cube[0][0][1] == 6 && cube[0][1][2] != 6 && cube[0][2][1] != 6) {
			return 2;
		}
		else if (cube[0][1][0] != 6 && cube[0][0][1] == 6 && cube[0][1][2] == 6 && cube[0][2][1] != 6) {
			moveGenerator(20);
			moveGenerator(20);
			moveGenerator(20);
			return 2;
		}
		else if (cube[0][1][0] != 6 && cube[0][0][1] != 6 && cube[0][1][2] == 6 && cube[0][2][1] == 6) {
			moveGenerator(20);
			moveGenerator(20);
			return 2;
		}
		else if (cube[0][1][0] == 6 && cube[0][0][1] != 6 && cube[0][1][2] != 6 && cube[0][2][1] == 6) {
			moveGenerator(20);
			return 2;
		}
		else if (cube[0][1][0] == 6 && cube[0][1][2] == 6 && (cube[0][0][1] != 6 || cube[0][2][1] != 6)) {
			return 3;
		}
		else if (cube[0][0][1] == 6 && cube[0][2][1] == 6 && (cube[0][1][0] != 6 || cube[0][1][2] != 6 )) {
			moveGenerator(20);
			return 3;
		}
		else if (cube[0][1][0] == 6 && cube[0][0][1] == 6 && cube[0][1][2] == 6 && cube[0][2][1] == 6) {
			return 4;
		}
		return 0;
	}
/*
       This performs the first algorithm of the yellow cross
        */
	public void yellowCrossAlgorithm1 () {

		moveGenerator(1);
		moveGenerator(7);
		moveGenerator(13);
		moveGenerator(9);
		moveGenerator(15);
		moveGenerator(3);
	}
/*
        This performs the second algorithm of the yellow cross
        */
	public void yellowCrossAlgorithm2 () {

		moveGenerator(1);
		moveGenerator(13);
		moveGenerator(7);
		moveGenerator(15);
		moveGenerator(9);
		moveGenerator(3);
	}
/*
        This method places the yellow corners in
        */
	public void yellowCorners () {


		if (!yellowCornerFinished()) {
			rotateCubeForYellowCorners();
			moveGenerator(13);
			moveGenerator(7);
			moveGenerator(15);
			moveGenerator(7);
			moveGenerator(13);
			moveGenerator(7);
			moveGenerator(7);
			moveGenerator(15);
			yellowCorners();
		}

	}
/*
        This method detemines that placement stage has finished
        */
	public boolean yellowCornerFinished () {

		for (int i = 0; i < cube[0].length; i++)
			for (int j = 0; j < cube[0][i].length; j++) {
				if (cube[0][i][j] != 6)
					return false;
			}

		return true;
	}
/*
       This method rotates the cube for place yellow corners in
        */
	public void rotateCubeForYellowCorners () {

		if (cube[0][0][0] != 6 && cube[0][0][2] != 6 && cube[0][2][0] != 6 && cube[0][2][2] != 6) {
			if (cube[1][0][2] == 6) {
			}
			else if (cube[4][0][2] == 6) {
				moveGenerator(20);
			}
			else if (cube[3][0][2] == 6) {
				moveGenerator(20);
				moveGenerator(20);
			}
			else if (cube[2][0][2] == 6) {
				moveGenerator(20);
				moveGenerator(20);
				moveGenerator(20);
			}
		}
		else if (cube[0][0][0] != 6 && cube[0][0][2] != 6 && cube[0][2][0] == 6 && cube[0][2][2] != 6) {
		}
		else if (cube[0][0][0] != 6 && cube[0][0][2] != 6 && cube[0][2][0] != 6 && cube[0][2][2] == 6) {
			moveGenerator(20);
		}
		else if (cube[0][0][0] != 6 && cube[0][0][2] == 6 && cube[0][2][0] != 6 && cube[0][2][2] != 6) {
			moveGenerator(20);
			moveGenerator(20);
		}
		else if (cube[0][0][0] == 6 && cube[0][0][2] != 6 && cube[0][2][0] != 6 && cube[0][2][2] != 6) {
			moveGenerator(20);
			moveGenerator(20);
			moveGenerator(20);
		}
		else {
			if (cube[4][0][0] == 6) {
			}
			else if (cube[3][0][0] == 6) {
				moveGenerator(20);
			}
			else if (cube[2][0][0] == 6) {
				moveGenerator(20);
				moveGenerator(20);
			}
			else if (cube[1][0][0] == 6) {
				moveGenerator(20);
				moveGenerator(20);
				moveGenerator(20);
			}
		}

	}
/*
        This method orients the corners of yellow side
        */
	public void orientCorners () {

		boolean orient = positionCubeForOrientCorners();

		if (!orient) {
			moveGenerator(15);
			moveGenerator(1);
			moveGenerator(15);
			moveGenerator(5);
			moveGenerator(13);
			moveGenerator(3);
			moveGenerator(15);
			moveGenerator(5);
			moveGenerator(14);
			moveGenerator(9);
			orientCorners();
		}

	}
/*
        This methof psotions the cube in right position before orientCorners()
        method eccecutes
        */
	public boolean positionCubeForOrientCorners () {

		boolean check = checkYellowCorners();

		if (check)
			return true;

		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;

		count1 = countCorners();
		moveGenerator(7);
		count2 = countCorners();
		moveGenerator(7);
		count3 = countCorners();
		moveGenerator(7);
		count4 = countCorners();
		moveGenerator(7);

		if (count1 == 4) {
		}
		else if (count2 == 4) {
			moveGenerator(7);
		}
		else if (count3 == 4) {
			moveGenerator(8);
		}
		else if (count4 == 4) {
			moveGenerator(9);
		}
		else if (count1 >= count2 && count1 >= count3 && count1 >= count4) {
			rotateCubeForOrientCorners();
		}
		else if (count2 >= count1 && count2 >= count3 && count2 >= count4) {
			moveGenerator(7);
			rotateCubeForOrientCorners();
		}
		else if (count3 >= count1 && count3 >= count2 && count3 >= count4) {
			moveGenerator(8);
			rotateCubeForOrientCorners();
		}
		else if (count4 >= count1 && count4 >= count2 && count4 >= count3) {
			moveGenerator(9);
			rotateCubeForOrientCorners();
		}

		if (count1 == 4 || count2 == 4 || count3 == 4 || count4 == 4)
			return true;

		return false;
	}
/*
        This method chechs if yellow corner are oriented correctly
        */
	public boolean checkYellowCorners () {

		if (cube[1][0][0] == cube [1][1][1] && cube[2][0][2] == cube[2][1][1] &&
				cube[2][0][0] == cube [2][1][1] && cube[3][0][2] == cube[3][1][1] &&
				cube[4][0][0] == cube [4][1][1] && cube[1][0][2] == cube[1][1][1] &&
				cube[3][0][0] == cube [3][1][1] && cube[4][0][2] == cube[4][1][1])
					return true;
		return false;

	}
/*
        This method counts the number of yellow corners in the right orientation
        */
	public int countCorners () {

		int count = 0;

		if (cube[4][0][0] == cube [4][1][1] && cube[1][0][2] == cube[1][1][1])
			count++;
		if (cube[1][0][0] == cube [1][1][1] && cube[2][0][2] == cube[2][1][1])
			count++;
		if (cube[2][0][0] == cube [2][1][1] && cube[3][0][2] == cube[3][1][1])
			count++;
		if (cube[3][0][0] == cube [3][1][1] && cube[4][0][2] == cube[4][1][1])
			count++;

		return count;
	}
/*
        This methods rotates the cube before the corners are oriented
        */
	public void rotateCubeForOrientCorners () {

		if (cube[1][0][0] == cube [1][1][1] && cube[2][0][2] == cube[2][1][1] &&
				cube[2][0][0] == cube [2][1][1] && cube[3][0][2] == cube[3][1][1]) {
		}
		else if (cube[4][0][0] == cube [4][1][1] && cube[1][0][2] == cube[1][1][1] &&
				cube[1][0][0] == cube [1][1][1] && cube[2][0][2] == cube[2][1][1]) {
			moveGenerator(20);
		}
		else if (cube[4][0][0] == cube [4][1][1] && cube[1][0][2] == cube[1][1][1] &&
				cube[3][0][0] == cube [3][1][1] && cube[4][0][2] == cube[4][1][1]) {
			moveGenerator(20);
			moveGenerator(20);
		}
		else if (cube[2][0][0] == cube [2][1][1] && cube[3][0][2] == cube[3][1][1] &&
				cube[3][0][0] == cube [3][1][1] && cube[4][0][2] == cube[4][1][1]) {
			moveGenerator(20);
			moveGenerator(20);
			moveGenerator(20);
		}

	}
/*
        This method orients the yellow cross
        */
	public void orientCross () {

		boolean check = checkYellowCross();

		if (!check) {
			boolean orient = positionCubeForOrientCross();
			if (orient) {
				yellowCrossClockwise();
				orientCross();
			}
			else {
				if (cube[4][0][1] == cube[1][1][1])
					yellowCrossClockwise();
				else
					yellowCrossCounterClockwise();
			}
		}

	}
/*
        This method checks the the yellow cross is oriented correctly
        */
	public boolean checkYellowCross () {

		if (cube[4][0][1] == cube[4][1][1] && cube[1][0][1] == cube[1][1][1] &&
				cube[2][0][1] == cube[2][1][1] && cube[3][0][1] == cube[3][1][1])
					return true;

		return false;

	}
/*
        This method places the cube in the correct position before the orient
        cross algorithm can take place
        */
	public boolean positionCubeForOrientCross () {

		if (cube[4][0][1] != cube[4][1][1] && cube[1][0][1] != cube[1][1][1] &&
				cube[2][0][1] != cube[2][1][1] && cube[3][0][1] != cube[3][1][1])
					return true;   // This indicates that all 4 sides pieces are incorect as oppose to 3
		else if (cube[2][0][1] == cube[2][1][1]) {          // If one side is correct need to make sure it is on
		}                                                   // back side before algorithm can be applied
		else if (cube[1][0][1] == cube[1][1][1]) {
			moveGenerator(20);
		}
		else if (cube[4][0][1] == cube[4][1][1]) {
			moveGenerator(20);
			moveGenerator(20);
		}
		else if (cube[3][0][1] == cube[3][1][1]) {
			moveGenerator(20);
			moveGenerator(20);
			moveGenerator(20);
		}

		return false;
	}
/*
        This method rotstes three yellow edge pieces in a clockwise direction
        */
	public void yellowCrossClockwise () {

		moveGenerator(2);
		moveGenerator(7);
		moveGenerator(16);
		moveGenerator(15);
		moveGenerator(2);
		moveGenerator(18);
		moveGenerator(13);
		moveGenerator(7);
		moveGenerator(2);

	}
/*
        This method rotstes three yellow edge pieces in an anticlockwise direction
        */
	public void yellowCrossCounterClockwise (){

		moveGenerator(2);
		moveGenerator(9);
		moveGenerator(16);
		moveGenerator(15);
		moveGenerator(2);
		moveGenerator(18);
		moveGenerator(13);
		moveGenerator(9);
		moveGenerator(2);

	}
        
        private class ButtonListener extends BeginnersMethod implements ActionListener {
            
            public void actionPerformed(ActionEvent e) {
            
            for (int i = 0; i < cube.length; i++)
		for (int j = 0; j < cube[i].length; j++)
                    for (int k = 0; k < cube[i][j].length; k++) {
                        if (e.getSource() == cubeButtons[i][j][k]) {
                            if (white.isSelected()) {
                                cubeButtons[i][j][k].setBackground(Color.white);
                            }
                            else if (blue.isSelected()) {
                                cubeButtons[i][j][k].setBackground(Color.blue);
                            }
                            else if (red.isSelected()) {
                                cubeButtons[i][j][k].setBackground(Color.red);
                            }
                            else if (green.isSelected()) {
                                cubeButtons[i][j][k].setBackground(Color.green);
                            }
                            else if (orange.isSelected()) {
                                cubeButtons[i][j][k].setBackground(Color.orange);
                            }
                            else if (yellow.isSelected()){
                                cubeButtons[i][j][k].setBackground(Color.yellow);
                            }
                        }
                    }
            
            if (e.getSource() == random) {
                this.randomShuffle();
                for (int i = 0; i < cube.length; i++)
                    for (int j = 0; j < cube[i].length; j++)
                        for (int k = 0; k < cube[i][j].length; k++) {
                            int value = this.getPosition(i, j, k);
                            cubeButtons[i][j][k].setBackground(numToColor(value));
                        }     
            }
            else if (e.getSource() == answer) {
                display.setText(null);
                this.solution.clear();
                this.moves.clear();
                this.rotations.clear();
                colorToNum();
                this.solve();
                if (this.checkCube())
                    display.setText("Solution: "+this.returnSolution()+
                            "\n\nThe number of moves is "+moves.size()+
                            "\n\nThe number of rotations is "+rotations.size());
                else
                    display.setText("Please check cube: a colour is on more than 9 places");
            }
        }
            
        public Color numToColor  (int i) {
                
                switch (i) {
                    case 1:
                        return Color.white;
                    case 2:
                        return Color.blue;
                    case 3:
                        return Color.red;
                    case 4:
                        return Color.green;
                    case 5:
                        return Color.orange;
                    case 6:
                        return Color.yellow;
                    default:
                        return Color.gray;
                }       
            }
        
        public void colorToNum () {
            
            for (int i = 0; i < cube.length; i++)
                    for (int j = 0; j < cube[i].length; j++)
                        for (int k = 0; k < cube[i][j].length; k++) {
                            if (cubeButtons[i][j][k].getBackground() == Color.white)
                                this.setPosition(1,i,j,k);
                            else if (cubeButtons[i][j][k].getBackground() == Color.blue)
                                this.setPosition(2,i,j,k);
                            else if (cubeButtons[i][j][k].getBackground() == Color.red)
                                this.setPosition(3,i,j,k);
                            else if (cubeButtons[i][j][k].getBackground() == Color.green)
                                this.setPosition(4,i,j,k);
                            else if (cubeButtons[i][j][k].getBackground() == Color.orange)
                                this.setPosition(5,i,j,k);
                            else if (cubeButtons[i][j][k].getBackground() == Color.yellow)
                                this.setPosition(6,i,j,k);
                            else
                                this.setPosition(0,i,j,k);
                        }
        }
        }
        public static void main (String [] args) {
            
            BeginnersMethod test = new BeginnersMethod ();
            test.buildInterface();
            
        }
}


