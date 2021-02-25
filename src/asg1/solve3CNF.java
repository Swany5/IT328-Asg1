package asg1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class solve3CNF {
	public static int n; //size of the clique being searched for
	public static int dimension;
	public static int kClique;
	public static long ms;

	/*
	 * Default Constructor
	 */
	public solve3CNF() {
	}

	/**
	 * Method used to reduce CNF to a graph.
	 */
	public static int[][] reduce(ArrayList<Integer> cnfTokens, int dimension) {
		int[][] graph = new int[dimension][dimension];
		for(int i = 0; i < dimension; i++) { //current index being compared to all other nodes
			int curr = cnfTokens.get(i); // value of current index
			for(int j = i; j < dimension; j++) { //index that current is being compared to
				int compare = cnfTokens.get(j); //value of that index
		
				if(i/3 != j/3) { 
					if(curr != -compare) { 
						graph[i][j] = 1;
						graph[j][i] = 1;
					}
					else { 
						graph[i][j] = 0;
						graph[j][i] = 0;
					}
				} 
	
				if(i == j) {
					graph[i][j] = 1;
				}
			}
		}
		return graph;

	}


	public static String toString(int[][] graph) {
		String temp = "";
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				temp += graph[i][j] + " ";
			}
			temp += "\n";
		}
		return temp;
	}


	private static boolean checkDuplicates(ArrayList<Integer> temp, int value) {

		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == value) {
				return true;
			}
		}
		return false;
	}

	
	public static void printSolution(int graphNum, int n, int kClique, ArrayList<Integer> currMaxClique, ArrayList<Integer> cnf) {

		ArrayList<Integer> temp = new ArrayList<Integer>();
		boolean duplicate = false;
		for (int i = 0; i < currMaxClique.size(); i++) {
			duplicate = checkDuplicates(temp, cnf.get(currMaxClique.get(i)));
			if (!duplicate) {
				temp.add(cnf.get(currMaxClique.get(i)));
				
			}
		}
		//sort
		int[] sortThese = new int[temp.size()];
		for(int i = 0; i < temp.size(); i++) {
			sortThese[i] = temp.get(i);
		}
		Arrays.sort(sortThese);

		System.out.print("3-CNF No." + graphNum + ": [n=" + n + " k=" + kClique + "] ");

		 // TEST
 
		if (currMaxClique.size() != kClique) {
			System.out.println("No " + kClique + "-clique; no solution");
		}
		else {
			// Print assignments
			System.out.print("Assignments: [ ");
			for(int i = 0; i < sortThese.length; i++) {
		
				System.out.print("A" + (i+1) + "=");
				if(sortThese[i] > 0) {

					System.out.print("T ");
				}
				else if(sortThese[i] < 0) {
					System.out.print("F ");
				}
			}
			System.out.println("] (" + ms + " ms)");
		}

		


		temp.clear(); 

	}

	// Main
	public static void main(String[] args) {
		Clique CNF = new Clique();
		ArrayList<Integer> cliqueList = new ArrayList<Integer>();
		ArrayList<Integer> currMaxClique = new ArrayList<Integer>();
		ArrayList<Integer> cnf = new ArrayList<Integer>();
		// Read in the graph
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("cnfs2021.txt"));
			String line = null;
			n = 0;
			String[] tokens;
			int currNum = 0; //current integer token

			// Create new file to write graph to output.txt
			File file = new File("outputGraph.txt");
			// If file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			int[][] temp;

			int graphNum = 1; // current graph

			while((line = br.readLine()) != null) {
				long startTime = System.currentTimeMillis();
				tokens = line.split(" "); //split line into tokens
				n = Integer.parseInt(tokens[0]); //size of clique being searched for
				if(n != 0) { //read until end of file
					dimension = tokens.length-1; //number of nodes after initial integer
					kClique = dimension / 3;

					// System.out.println("dimension: " + dimension); // TEST
					
					for(int i = 1; i < tokens.length; i++) {
						currNum = Integer.parseInt(tokens[i]);
						cnf.add(currNum);
						// System.out.print(" " + currNum); // TEST
					}
					System.out.println();
					
					// Evaluate and write
					temp = reduce(cnf, dimension);
					// Write to output.txt
					bw.write(dimension + "\n"); // output dimension
					bw.write(toString(temp));
					// System.out.println("Write complete"); // TEST
					//break; //testing first graph

					// Find cliques
					CNF.graph = temp;
					CNF.dimension = dimension;
					currMaxClique = CNF.findMaxClique(cliqueList,0,dimension);

					printSolution(graphNum, n, kClique, currMaxClique, cnf);
					graphNum++;
					long endTime = System.currentTimeMillis();
					ms = endTime - startTime;

					cnf.clear(); // CLEAR THE ARRAYLIST!!!!
				}
			} // endwhile
			bw.write("0");
			br.close();
			bw.close();
		} // endtry
		catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
		} 
		catch (IOException e) {
			System.out.println("Could not read file.");
		}
	} // endmain
}