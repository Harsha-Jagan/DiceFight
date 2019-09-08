/*
 * Name    : Jagannathan Harshavardhan
 * User ID : hxj4805
 * Lab #   : Final exam
 */
package dicefight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.lang.Math;
import java.util.Arrays;

public class MazeGui extends javax.swing.JFrame 
{
    JFrame frame;
    JPanel panel, west;
    JButton[] button;
    JButton dice;
    JTextArea top, westText;
    int numRows, numCols;
    MazeGui thisObject;
    private final int delay = 50;
    private String mazeFile = "maze2.txt";
    private char[][] grid;      // 2D char array
    private int[] start;        // starting square {row, col}
    private int[] exit;         // exiting square {row, col}
    private char wall = 'X';    // character that represents a wall
    char[] icon = {'A', 'B'};
    int click = 0;
    int player = 0;
    int[] cRow = new int[2]; // set current row to starting row
    int[] cCol = new int[2]; // set current col to starting col
    static int flag;
    ActionListener ac;
    
    public MazeGui(int ans) 
    {
        exit = new int[2];      // exit[0]  = row, exit[1]  = col
        readMaze(mazeFile);     // load maze file
        findOpenings();
        createGUI(grid, start, exit, wall);
        flag = ans;
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) 
    {
        boolean pathFound = false;
        
        if (click == 0) 
            return;
        if (cRow[player] == exit[0] && cCol[player] == exit[1]) 
        {
            pathFound = true;
        }
        if(flag==2||player!=1)
        {
            if (evt.getKeyCode() == 38 && !pathFound && cRow[player] > 0 && grid[cRow[player] - 1][cCol[player]] != 'X')//up
            {
                System.out.println("Player "+icon[player]+" moved up");
                grid[cRow[player]][cCol[player]] = ' ';
                cRow[player] = cRow[player] - 1;
                grid[cRow[player]][cCol[player]] = icon[player];
                updateGUI(grid);
                if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
                else if(cRow[0]==exit[0]&&cCol[0]==exit[1]||cRow[1]==exit[0]&&cCol[1]==exit[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
            } 
            else if (evt.getKeyCode() == 40 && !pathFound && cRow[player] < numRows - 1 && grid[cRow[player] + 1][cCol[player]] != 'X')//down
            {
                System.out.println("Player "+icon[player]+" moved down");
                grid[cRow[player]][cCol[player]] = ' ';
                cRow[player] = cRow[player] + 1;
                grid[cRow[player]][cCol[player]] = icon[player];
                updateGUI(grid);
                if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
                else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
            } 
            else if (evt.getKeyCode() == 37 && !pathFound && cCol[player] > 0 && grid[cRow[player]][cCol[player] - 1] != 'X')//left
            {
                System.out.println("Player "+icon[player]+" moved left");
                grid[cRow[player]][cCol[player]] = ' ';
                cCol[player] = cCol[player] - 1;
                grid[cRow[player]][cCol[player]] = icon[player];
                updateGUI(grid);
                if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
                else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
            } 
            else if (evt.getKeyCode() == 39 && !pathFound && cCol[player] < numCols - 1 && grid[cRow[player]][cCol[player] + 1] != 'X')//right
            {
                System.out.println("Player "+icon[player]+" moved right");
                grid[cRow[player]][cCol[player]] = ' ';
                cCol[player] = cCol[player] + 1;
                grid[cRow[player]][cCol[player]] = icon[player];
                updateGUI(grid);
                if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
                else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                {
                    System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                    System.exit(0);
                }
            } 
            else 
            {
                return;
            }
        }
        click--;
        if (click == 0) 
        {
            player ^= 1;
            if(flag==1)
            {
                computer();
                player ^= 1;
            }
            dice.addActionListener(ac);
        }
    }
    private void computer()
    {
        
        int num = (int) (Math.random() * 6 + 1);
        System.out.println("\nIt is Computer's turn.\nLooks like Comp rolled a "+num);
        dice.setText(num + "");
        boolean u=false,d=false,l=false,r=false;
        
        while(num!=0)
        {
            if(cRow[player] > 0 && grid[cRow[player] - 1][cCol[player]] != 'X')
            {
                u=true;
            }
            if(cRow[player] < grid.length-1 && grid[cRow[player] + 1][cCol[player]] != 'X')
            {
                d=true;
            }
            if(cCol[player] < grid[0].length-1 && grid[cRow[player]][cCol[player]+1] != 'X')
            {
                r=true;
            }
            if(cCol[player] > 0 && grid[cRow[player]][cCol[player]-1] != 'X')
            {
                l=true;
            }

            double right, left, up, down;
            right = Math.sqrt(Math.pow( cCol[0] - (cCol[1]+1) , 2) + Math.pow( cRow[0] - cRow[1] , 2));
            left = Math.sqrt(Math.pow( cCol[0] - (cCol[1]-1) , 2) + Math.pow( cRow[0] - cRow[1] , 2));
            up = Math.sqrt(Math.pow( cCol[0] - cCol[1] , 2) + Math.pow( cRow[0] - (cRow[1]-1) , 2));
            down = Math.sqrt(Math.pow( cCol[0] - cCol[1], 2) + Math.pow( cRow[0] - (cRow[1]+1) , 2));
  
            double[] array = {up,down,left,right};
            Arrays.sort(array);
            int track = 0;
            while(track<array.length)
            {
                if(r)
                {
                    if(right==array[track])
                    {
                        System.out.println("Computer moved right");
                        grid[cRow[player]][cCol[player]] = ' ';
                        cCol[player] = cCol[player] + 1;
                        grid[cRow[player]][cCol[player]] = icon[player];
                        updateGUI(grid);
                        if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        break;
                    }
                }
                if(l)
                {
                    if(left==array[track])
                    {
                        System.out.println("Computer moved left");
                        grid[cRow[player]][cCol[player]] = ' ';
                        cCol[player] = cCol[player] - 1;
                        grid[cRow[player]][cCol[player]] = icon[player];
                        updateGUI(grid);
                        if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        break;
                    }
                }
                if(u)
                {
                    if(up==array[track])
                    {
                        System.out.println("Computer moved up");
                        grid[cRow[player]][cCol[player]] = ' ';
                        cRow[player] = cRow[player] - 1;
                        grid[cRow[player]][cCol[player]] = icon[player];
                        updateGUI(grid);
                        if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        else if(cRow[0]==exit[0]&&cCol[0]==exit[1]||cRow[1]==exit[0]&&cCol[1]==exit[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        break;
                    }
                }
                if(d)
                {
                    if(down==array[track])
                    {
                        System.out.println("Computer moved down");
                        grid[cRow[player]][cCol[player]] = ' ';
                        cRow[player] = cRow[player] + 1;
                        grid[cRow[player]][cCol[player]] = icon[player];
                        updateGUI(grid);
                        if(cRow[0]==cRow[1]&&cCol[0]==cCol[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        else if(cRow[0]==exit[0]&&cCol[0]==exit[1])
                        {
                            System.out.println("Xx________________"+icon[player]+" has won"+"________________xX");
                            System.exit(0);
                        }
                        break;
                    }
                }
                track++;
            }
            track=0;
            num--;
            u=false; 
            d=false; 
            r=false; 
            l=false;
        }
    }
    
    private void formKeyTyped(java.awt.event.KeyEvent evt) 
    {
        System.out.println(evt.getKeyChar());
    }

    public final void readMaze(String file) {
        try 
        {
            Scanner read = new Scanner(new File(file));
            int rows = read.nextInt();
            grid = new char[rows][];
            read.nextLine();
            for (int i = 0; i < rows; i++) {
                grid[i] = read.nextLine().toCharArray();
            }
            numRows = grid.length;
            numCols = grid[0].length;
        } catch (FileNotFoundException ex) 
        {
            System.out.println("Error reading file: " + file);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) 
    {
        int num = (int) (Math.random() * 6 + 1);
        click = num;
        System.out.println("\nPlayer "+icon[player]+" rolled a "+num);
        dice.setText(num + "");
        dice.setFocusable(false);
        ac = dice.getActionListeners()[0];
        dice.removeActionListener(ac);
        panel.setFocusable(true);
    }

    public final void createGUI(char[][] grid, int[] start, int[] exit, char c) 
    {
        frame = new JFrame("Maze");
        panel = new JPanel();
        top = new JTextArea("Top test area");
        west = new JPanel();
        west.setLayout(new GridLayout(10, 1));
        westText = new JTextArea("Dice");
        westText.setEditable(false);
        dice = new JButton();
        dice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        west.add(westText);
        west.add(dice);
        panel.setLayout(new GridLayout(numRows, numCols));
        button = new JButton[numRows * numCols];
        for (int i = 0; i < button.length; i++) 
        {
            button[i] = new JButton("");
            button[i].setFont(new Font("Arial", Font.BOLD, 14));
            button[i].setBackground(new Color(255, 200, 150));
            if (grid[i / numCols][i % numCols] == wall) 
            {
                button[i].setBackground(new Color(0, 25, 0));
            }
            button[i].setFocusable(false);
            panel.add(button[i]);
        }
        button[cRow[0] * numCols + cCol[0]].setOpaque(true);
        button[cRow[0] * numCols + cCol[0]].setBackground(new Color(255, 0, 0));
        button[cRow[1] * numCols + cCol[1]].setOpaque(true);
        button[cRow[1] * numCols + cCol[1]].setBackground(new Color(0, 0, 255));
        button[exit[0] * numCols + exit[1]].setOpaque(true);
        button[exit[0] * numCols + exit[1]].setBackground(new Color(0, 255, 0));
        frame.setLayout(new BorderLayout());
        frame.setSize(numCols * 45 + 45, numRows * 45 + 45);
        frame.add(top, BorderLayout.NORTH);
        frame.add(west, BorderLayout.WEST);
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        top.setFocusable(false);
        west.setFocusable(false);
        panel.setFocusable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        frame.addKeyListener(new java.awt.event.KeyAdapter() 
        {
            public void keyPressed(java.awt.event.KeyEvent evt) 
            {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) 
            {
                formKeyTyped(evt);
            }
        });
        frame.setVisible(true);
    }

    public final void updateGUI() 
    {
        for (int row = 0; row < grid.length; row++) 
        {
            for (int col = 0; col < grid[row].length; col++) 
            {
                button[row * numCols + col].setText("" + grid[row][col]);
            }
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
        }
    }

    public static void main(String[] args) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MazeGui(flag).setVisible(true);
            }
        });
    }

    public void findOpenings() 
    {
        for (int row = 0; row < grid.length; row++) 
        {
            for (int col = 0; col < grid[row].length; col++) 
            {
                if (grid[row][col] == 'A') 
                {
                    cRow[0] = row;
                    cCol[0] = col;
                } 
                else if (grid[row][col] == 'B') 
                {
                    cRow[1] = row;
                    cCol[1] = col;
                } 
                else if (grid[row][col] == 'E') 
                {
                    exit[0] = row;
                    exit[1] = col;
                }
            }
        }
    }

    public void updateGUI(char[][] grid) 
    {
        for (int row = 0; row < grid.length; row++) 
        {
            for (int col = 0; col < grid[row].length; col++) 
            {
                if (grid[row][col] == 'x') 
                {
                    button[row * numCols + col].setText("");
                } 
                else 
                {
                    button[row * numCols + col].setText("" + grid[row][col]);
                }
            }
        }
    }
}
