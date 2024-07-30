/**
 * Recursive Labyrinth
 * @author Jason Sun
 * @version 1.0 
 * March 2022
 */


import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.Arrays;

public class RecursiveLabyrinth{
    static char[][] labyrinth;
    static boolean[][] visited;
    static int rows;
    static int columns;
    static Visualizer visualizer;
    public static void main(String args[]) throws Exception{
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the number of horizontal alleys of the labryinth: ");
        int horizontalAlleys = keyboard.nextInt();
        rows = horizontalAlleys*2 + 1;
        System.out.println("Enter the number of vertical alleys of the labryinth: ");
        int verticalAlleys = keyboard.nextInt();
        columns = verticalAlleys*2 + 1;
        labyrinth = new char[rows][columns];
        visited = new boolean[rows][columns];
        
        // Generating initial grid system of labyrinth
        for (int i = 0; i<rows; i++){
            if (i%2==0){
                for (int j = 0; j<columns; j++){
                    visited[i][j] = false;
                    labyrinth[i][j] = Const.WALL;
                }
            }
            else if (i%2==1){
                for (int j = 0; j<columns; j++){
                    visited[i][j] = false;
                    if (j%2==0)
                        labyrinth[i][j] = Const.WALL;
                    else if (j%2==1)
                        labyrinth[i][j] = Const.ALLEY;
                }
            }
        } // Grid generator end
        visualizer = new Visualizer(labyrinth);
        Stack<Integer> cellTracker = new Stack<Integer>();
        int initialX = 1;
        int initialY = 1;
        cellTracker.push(initialX);
        cellTracker.push(initialY);
        generateLabyrinth(initialX, initialY, cellTracker);
        
        // Print labyrinth in text file
        File labyrinthFile = new File("labyrinth.txt");
        PrintWriter output = new PrintWriter(labyrinthFile);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++)
                output.print(labyrinth[i][j]);
            output.println();
        }
        output.println();
        output.close();
    } // main method end
    
    // labyrinthGenerator method---------------------------------------------------------------------------------------
    public static void generateLabyrinth(int currentX, int currentY, Stack<Integer> cellTracker){
        try{
            Thread.sleep(Const.DELAY);
        } catch(Exception e){}
        
        // Base case: if maze is complete; no remaining cells to backtrack
        if (cellTracker.isEmpty())
            return;
        
        // Determine all possible and valid moves
        String possibleMoves = "";
        if (currentX-1 != 0 && !visited[currentX-2][currentY])
            possibleMoves = possibleMoves + "L";
        if (currentX+1 != rows-1 && !visited[currentX+2][currentY])
            possibleMoves = possibleMoves + "R";
        if (currentY-1 != 0 && !visited[currentX][currentY-2])
            possibleMoves = possibleMoves + "U";
        if (currentY+1 != columns-1 && !visited[currentX][currentY+2])
            possibleMoves = possibleMoves + "D";
        
        // Base case: if nowhere to go, backtrack to previous cell
        if (possibleMoves == ""){
            // Obtain previous X and Y positions
            int previousY = cellTracker.pop();
            int previousX = cellTracker.pop();
            generateLabyrinth(previousX, previousY, cellTracker);
            return;
        }
        
        // Randomly obtain a valid direction for next move
        char move = possibleMoves.charAt((int)(Math.random()*possibleMoves.length()));
        // Moving left
        if (move == 'L'){
            labyrinth[currentX-1][currentY] = Const.ALLEY;
            visited[currentX-2][currentY] = true;
            visited[currentX-1][currentY] = true;
            currentX = currentX-2;
            cellTracker.push(currentX);
            cellTracker.push(currentY);
            generateLabyrinth(currentX, currentY, cellTracker);
        }
        
        // Moving right
        else if (move == 'R'){
            labyrinth[currentX+1][currentY] = Const.ALLEY;
            visited[currentX+2][currentY] = true;
             visited[currentX+1][currentY] = true;
            currentX = currentX+2;
            cellTracker.push(currentX);
            cellTracker.push(currentY);
            generateLabyrinth(currentX, currentY, cellTracker);
        }
        
        // Moving up
        else if (move == 'U'){
            labyrinth[currentX][currentY-1] = Const.ALLEY;
            visited[currentX][currentY-2] = true;
            visited[currentX][currentY+1] = true;
            currentY = currentY-2;
            cellTracker.push(currentX);
            cellTracker.push(currentY);
            generateLabyrinth(currentX, currentY, cellTracker);
        }
        
        // Moving down
        else if (move == 'D'){
            labyrinth[currentX][currentY+1] = Const.ALLEY;
            visited[currentX][currentY+2] = true;
            visited[currentX][currentY+1] = true;
            currentY = currentY+2;
            cellTracker.push(currentX);
            cellTracker.push(currentY);
            generateLabyrinth(currentX, currentY, cellTracker);
        }    
        
    } // generateLabyrinth method end
} // RecursiveLabyrinth class end
