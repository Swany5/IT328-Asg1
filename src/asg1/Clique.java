/**
 * solveClique.java
 * Java class that will find the maximum clique of a graph from the given input file
 */

package project1_IT328;

import java.util.*;
import java.io.*;



public class solveClique 
{
	
	public static int[][] graph;
	public static int dimension, graphs, edges, row;
	public static long ms;
	
	// Driver Program
	public static void main(String[] args) 
	{
		solveClique clique = new solveClique();

		System.out.println("* Max Cliques in graphs in graphsDense.txt *");
		System.out.println("(|V|,|E|) Cliques (size, ms used)");

		// BufferedReader to read graph data from input file	
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader("graphs2021.txt"));
			String line = null;
				
			while((line = br.readLine()) != null) 
			{
				edges = 0;
				dimension = Integer.parseInt(line);

				if (dimension != 0) 
				{
					graphs++;

		
					for(int i = 0; i < dimension; i++) 
					{
						for(int j = 0; j < dimension; j++) 
						{
							graph[i][j] = getNext(br);
							countEdges(); 
							br.read(); 
						}
							
						br.read();
						br.read();
					}

				
					ArrayList<Integer> cliqueList = new ArrayList<Integer>();
					cliqueList = clique.maxClique(cliqueList, row, dimension);
					printGraph(cliqueList);

				}
			} 
				
			br.close();
		} 
			
		catch (FileNotFoundException e) 
		{
				System.out.println("Unable to find input file: graphs2021.txt");
		} 
			
		catch (IOException e) 
		{
				System.out.println("Unable to read input file: graphs2021.txt");
		}
	} 

	// Default Constructor
	public solveClique() 
	{
		graph = new int[60][60];			// 2D Array to store graph from input file
		dimension = 0;						// Dimensions of the graph
		graphs = 0;							// Number of graphs in the input file
		edges = 0;							// Number of edges in the current graph
		row = 0;							// Number of rows
		ms = 0;								// Time used to calculate max clique within a given graph
	}


	// maxClique method that will find the maximum clique in the graph and calculate total time taken to find max clique
	public ArrayList<Integer> maxClique(ArrayList<Integer> aClique, int row, int aDimension) 
	{
		long startTime = System.currentTimeMillis();

		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		maxClique = aClique;

		for (int i = row; i < dimension; i++) 
		{
			boolean isAClique = true;
			
			for (int j = 0; j < aClique.size(); j++) 
			{
				if (graph[aClique.get(j)][i] != 1) 
				{
					isAClique = false;
				}
			}

			if (isAClique) 
			{
				ArrayList<Integer> currentClique = new ArrayList<Integer>(aClique);
				currentClique.add(i);
				temp = maxClique(currentClique, i+1, dimension);

				if (temp.size() > maxClique.size()) 
				{
					maxClique = temp;
				}
			}
		} 
		
		long endTime = System.currentTimeMillis();
		ms = endTime - startTime;
		return maxClique;
	}

	
	// Method to find number of edges in the graph
	private static int countEdges() 
	{
		// Initialize edges to 0
		edges = 0;
		
		for (int i = 0; i < dimension; i++) 
		{
			for (int j = i; j < dimension; j++) 
			{
				if (graph[i][j] == 1) 
				{
					edges++;
				}
			}
		}
		
		return edges;
	}

	private static int getNext(BufferedReader br) throws IOException 
	{
		char c = (char) br.read();
		int n = Character.getNumericValue(c);
		return n;
	}

	
	// Print method that outputs solution in given format per assignment sheet
	private static void printGraph(ArrayList<Integer> aClique) 
	{
		System.out.println("G" + graphs + " (" + dimension + ", " + edges + ") " +
			aClique.toString() + "(size=" + aClique.size() + ", " + ms + " ms)");
	}
}
