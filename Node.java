//Alex Casper
//Creation of node class for the 8-Puzzle problem
//implements 'Comparable' interface to decide on what 
//basis our nodes need to be ordered in our priority queue
import java.util.*;
import java.io.*;

public class Node implements Comparable<Node>{

	int[][] curState;
	Node parent;
	int GoN; //g(n)
	int HoN; //h(n)
	int FoN; //f(n)
	
	//Constructor that handles a new state being made (mostly children)
	//state = the current state of the board we are looking at
	//newParent = the parent of the current state (the previous state before we moved the blank space)
	//newGoN = the new g(n) (actual cost) of from the start to where we current are (n)
	//newHoN = the new h(n) (our guess based on the huristic we're using) from where we are (n) to a goal
	public Node(int[][] state, Node newParent, int newGoN, int newHoN) {
        curState = state;
		parent = newParent;
		GoN = newGoN;
		HoN = newHoN;
		FoN = newGoN + newHoN; 
    }
	
	//method to get the parent of the node
	public Node getParent(){
		return parent;
	}
	
	//as a note I got this information from the following URL:
	//https://www.freecodecamp.org/news/priority-queue-implementation-in-java/
	//have to override the compareTo function to compare state's f(n) values
	//lower f(n) values will take priority
	public int compareTo(Node n){
		if(this.FoN > n.FoN){
			return 1;
		}
		
		else if(this.FoN < n.FoN){
			return - 1;
		}
		
		else{
			return 0;
		}
	}
}