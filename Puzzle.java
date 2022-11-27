//Alex Casper
//Main Class for 8-Puzzle
import java.util.*;
import java.io.*;

public class Puzzle{

private static Scanner in = new Scanner(System.in);

public static void main(String[ ] args){
	//create a 3x3 array for the users original unsolved puzzle
	int originalPuzzle[][] = new int [3][3];
	//then a goal state to compare it to
	int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
	
	  //ask the user which heuristic they would like to use
	  System.out.println("Please pick the number 1 (# of pieces heuristic) or 2 (total distance heuristic): ");
	  int heuristic = in.nextInt();
	  
	  //ask the user for the current state of the puzzle they would like to solve
	  System.out.println("Great! Can you now enter your array for the puzzle: ");
	  for(int i = 0; i <= 2; i++){
		  for(int j = 0; j <= 2; j++){
			  originalPuzzle[i][j] = in.nextInt();
		  }
	  }
	  
	  //call the A* Search
	  //pass the heuristic value as a parameter so it knows which to look at
	  AStarSearch(originalPuzzle, goal, heuristic);
  }
  
  /*////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////*/
  
  //this is the actual implementation of the A* search algorithm
  public static void AStarSearch(int[][] originalPuzzle, int[][] goal, int heuristic){
	  //we will have a list of frontier states sorted by their f(n) values
	  Queue<Node> frontierStates = new PriorityQueue<>();
	  
	  //we have to add the original puzzle (orginalPuzzle) to the priotity queue 
	  //so create the node object...
	  Node originalState = new Node(originalPuzzle, null, 0, 0);
	  
	  //...and put it into the queue
	  frontierStates.add(originalState);
	  
	  //since we need to eventually look at every state we
	  //put in the queue:
	  while (!frontierStates.isEmpty()){
		  //we look at the state with the smallest f(n) value
		  //when it is first ran this will just look at originalState
		  Node currentNode = frontierStates.poll();
		  
		  //printBoard(currentNode.curState);
		  //System.out.println();
		  
		  //are we looking at a goal?
		  if(checkIfGoal(currentNode.curState, goal)){
			  //if yes tell the user you did it
			  System.out.println("We found a solution!");
			  //then print the path we found
			  System.out.println();
			  printPath(currentNode);
			  break;
			  
		  }
		  
		  //if not we need to continue down the tree by looking 
		  //at current's children
		  else{
			  //since it wasn't a goal state we need to remove it from the frontier state queue
			  frontierStates.remove(currentNode);
			  
			  //add the children to the frontier states to search through them
			  frontierStates = createChildren(currentNode, frontierStates, heuristic, goal);
			  
		  }
	  }
	  
  }
  
 /*////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////*/
  
  //This funciton will create the children of the current node (currentNode)
  //and then add them to the queue
  //This creates children by moving the blank space 'up', 'down', 'right', or 'left'
  public static Queue<Node> createChildren(Node currentNode, Queue<Node> frontierStates, int heuristic, int [][] goal){
	  
	  //lets first locate the position of the 0 (blank space) for the current state
	  int zero_row = findNumberRow(currentNode.curState, 0);
	  int zero_col = findNumberCol(currentNode.curState, 0);
	  
	  if(heuristic == 1){
		  
		//If the blank is not in the top row then we know you can move up
		if(zero_row > 0){
		  //before we create a new child we need to create the childs state (board)
		  int [][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the north of it 
		  childState[zero_row][zero_col] = currentNode.curState[(zero_row - 1)][zero_col];
		  //then set the new blank (0) space
		  childState [zero_row - 1][zero_col] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicOne(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		}
	  
		//if the blank is not in column one then we know you can move left
		if (zero_col > 0){
		   //before we create a new child we need to create the childs state (board)
		  int [][]childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the west of it 
		  childState[zero_row][zero_col] = currentNode.curState[zero_row][zero_col - 1];
		  //then set the new blank (0) space
		  childState [zero_row][zero_col - 1] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicOne(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		}
	  
		//if the blank is not in row 2 then we know you can move down
		if(zero_row < 2){
		   //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the south of it 
		  childState[zero_row][zero_col] = currentNode.curState[(zero_row + 1)][zero_col];
		  //then set the new blank (0) space
		  childState [zero_row + 1][zero_col] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicOne(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		  
		}
	  
		//if the blank is not in column 2 then we know it can go right
		if(zero_col < 2){
		  //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the east of it 
		  childState[zero_row][zero_col] = currentNode.curState[zero_row][zero_col + 1];
		  //then set the new blank (0) space
		  childState [zero_row][zero_col +
		  1] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicOne(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		  
		}
	  }
	  
	  //this all happens if we use the 2nd heuristic instead of the 1st
	  //it is literally the same thing but instead of heuristicOne being
	  //called in the creation of the children it is heuristicTwo
	  else{
		  
		  //If the blank is not in the top row then we know you can move up
		if(zero_row > 0){
		  //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the north of it 
		  childState[zero_row][zero_col] = currentNode.curState[(zero_row - 1)][zero_col];
		  //then set the new blank (0) space
		  childState [zero_row - 1][zero_col] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicTwo(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		}
	  
		//if the blank is not in column one then we know you can move left
		if (zero_col > 0){
		   //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the west of it 
		  childState[zero_row][zero_col] = currentNode.curState[zero_row][zero_col - 1];
		  //then set the new blank (0) space
		  childState [zero_row][zero_col - 1] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicTwo(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		}
	  
		//if the blank is not in row 2 then we know you can move down
		if(zero_row < 2){
		   //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the south of it 
		  childState[zero_row][zero_col] = currentNode.curState[(zero_row + 1)][zero_col];
		  //then set the new blank (0) space
		  childState [zero_row + 1][zero_col] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicTwo(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		  
		}
	  
		//if the blank is not in column 2 then we know it can go right
		if(zero_col < 2){
		  //before we create a new child we need to create the childs state (board)
		  int[][] childState = new int [3][3];
		  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				childState[i][j] = currentNode.curState[i][j];
			}
		  }
		  //then we need to motify the childs board before we can add him to the queue
		  //so first move the blank space up 1 by setting it to the value of the piece adjecent to the east of it 
		  childState[zero_row][zero_col] = currentNode.curState[zero_row][zero_col + 1];
		  //then set the new blank (0) space
		  childState [zero_row][zero_col + 1] = 0;
		  
		  //now make it a node...
		  Node child = new Node(childState, currentNode, currentNode.GoN + 1, heuristicTwo(currentNode.curState, goal));
		  //...and add it to the queue
		  frontierStates.add(child);
		  
		} 
	  }
	  
	  return frontierStates;
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
  
  //finds the row on the current board of a passed number 
  public static int findNumberRow(int[][] curState, int num){
	  int num_row = 0;
	  for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				if(curState[i][j] == num){
					num_row = i;
				}
			}
		  } 
	  return num_row;
  }
  
  //finds the column on the current board of a passed number
  public static int findNumberCol(int[][] curState, int num){
	  int num_col = 0;
	  
	 for (int i = 0; i <= 2; i++){
			for (int j = 0; j<= 2; j++){
				if(curState[i][j] == num){
					num_col = j;
				}
			}
		  }
	  return num_col;
  }
  
  
  //this function will calculate h(n) or our guess of the cost from where we are to the goal
  //using the # of pieces out of place heuristic
  public static int heuristicOne(int[][] curState, int[][] goal){
	  int sum = 0;
	  for (int i = 0; i <= 2; i++){
		  for (int j = 0; j <= 2; j++){
			  //look through every piece to see if they match up
			  ///if they don't then we have a misplaced piece so...
			  if(curState[i][j] != goal[i][j]){
				  //...add 1 to the total of misplaced pieces
				  sum++;
			  }
		  }  
	  }
	  return sum;
  }
  
  //this function will calculate h(n) or our guess of the cost from where we are to the goal
  //using the total distance of each piece heuristic
  public static int heuristicTwo(int[][] curState, int[][] goal){
	  int distance = 0;
	  //find the blank and determine the distance from where it should be
	  int zero_row = findNumberRow(curState, 0);
	  int zero_col = findNumberCol(curState, 0);
	  distance = distance + findDistance(zero_row, zero_col, goal, 0);
	  
	  //find the one and determine the distance from where it should be...
	  //and so forth...
	  int one_row = findNumberRow(curState, 1);
	  int one_col = findNumberCol(curState, 1);
	  distance = distance + findDistance(one_row, one_col, goal, 1);
	  
	  int two_row = findNumberRow(curState, 2);
	  int two_col = findNumberCol(curState, 2);
	  distance = distance + findDistance(two_row, two_col, goal, 2);
	  
	  int three_row = findNumberRow(curState, 3);
	  int three_col = findNumberCol(curState, 3);
	  distance = distance + findDistance(three_row, three_col, goal, 3);
	  
	  int four_row = findNumberRow(curState, 4);
	  int four_col = findNumberCol(curState, 4);
	  distance = distance + findDistance(four_row, four_col, goal, 4);
	  
	  int five_row = findNumberRow(curState, 5);
	  int five_col = findNumberCol(curState, 5);
	  distance = distance + findDistance(five_row, five_col, goal, 5);
	  
	  int six_row = findNumberRow(curState, 6);
	  int six_col = findNumberCol(curState, 6);
	  distance = distance + findDistance(six_row, six_col, goal, 6);
	  
	  int seven_row = findNumberRow(curState, 7);
	  int seven_col = findNumberCol(curState, 7);
	  distance = distance + findDistance(seven_row, seven_col, goal, 7);
	  
	  int eight_row = findNumberRow(curState, 8);
	  int eight_col = findNumberCol(curState, 8);
	  distance = distance + findDistance(eight_row, eight_col, goal, 8);
	  
	  //...until we can finally determine the total net distance of each misplaced piece
	  return distance;
	  
  }
  
  //helper function used to help find total distance for heuristic 2
  public static int findDistance(int row, int col, int[][] goal, int num){
	  //find the row and column of where the passed 'num' should actually be
	  int num_row = findNumberRow(goal, num);
	  int num_col = findNumberCol(goal,num);
	  int distance;
	  
	  //find the distance between the two points
	  distance = Math.abs(row - num_row) + Math.abs(col - num_col);
	  
	  return distance;
	  
  }
  
  //funciton that checks to see if we have found a goal state
  public static boolean checkIfGoal(int[][] curState, int[][] goal){
	  for(int i = 0; i <= 2; i++){
		  for(int j = 0; j <= 2; j++){
			  if(curState[i][j] != goal[i][j]){
				  return false;
			  }
		  }
	  }
	  //if we made it outside of the loop then all positions
	  //in each state are the same and we have found a solution
	  return true;
	  
  }
  
 //this will back track the path we took to get here by
 //by putting the goal state's parent into a vector
 //and that parent's parent into a vector and so on.
 //Then it will print it out backwards
  public static void printPath(Node goal){
	  Vector<Node> v = new Vector<Node>();
	  do{
		  v.add(goal);
		  goal = goal.getParent();
	  }while(goal.getParent() != null);
	  
	  //we've exited the do loop so now we need to print the vector
	  for (int i = v.size() - 1; i >= 0; i--){
	  printBoard(v.get(i).curState);
	  System.out.println();
  }
  }
  
  
  //function to print a board
  public static void printBoard(int[][] curBoard){
	  for (int i = 0; i < curBoard.length; i++){
		  for (int j = 0; j < curBoard.length; j++){
			  System.out.print(curBoard[i][j] + " ");
		  }
		  System.out.println();
	  }
  }  
  }