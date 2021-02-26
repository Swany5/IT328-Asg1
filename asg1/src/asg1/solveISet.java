/**
 * solveISet.java
 * Java class that will find the maximum independent set of a graph from the given input file
 */

package project1_IT328;

import java.util.*;
import java.io.*;


public class solveISet 
{

	public static int[][] graph;
	public static int dimension, graphs, edges, row;
	public static long ms;
	
	// Driver Program 
	public static void main(String[] args) 
	{
		solveISet indSet = new solveISet();

		System.out.println("* Max Independent Set in graphs in graphs2021.txt: (reduced to K-Clique) *");
		System.out.println("(|V|,|E|) Independent Sets (size, ms used)");


		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader("graphs2021.txt"));
			String line = null;
			
			while((line = br.readLine()) != null) 
			{
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
							if (i != j) 
							{
								if (graph[i][j] == 1) 
								{
									graph[i][j] = 0;
								} 
								
								else 
								{
									graph[i][j] = 1;
								}
							} 

							br.read(); 
						}
						
						br.read();
						br.read();
					} 

		
					ArrayList<Integer> ISetList = new ArrayList<Integer>();
					ISetList = indSet.maxISet(ISetList, row, dimension);
					printGraph(ISetList);
		
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
	public solveISet() 
	{
		graph = new int[60][60];			// 2D Array to store graph from input file
		dimension = 0;						// Dimensions of the graph
		graphs = 0;							// Number of graphs in the input file
		edges = 0;							// Number of edges in the current graph
		row = 0;							// Number of rows
		ms = 0;								// Time used to calculate max clique within a given graph
	}


	public ArrayList<Integer> maxISet(ArrayList<Integer> aISet, int row, int aDimension) 
	{
		long startTime = System.currentTimeMillis();

		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> maxISet = new ArrayList<Integer>();
		maxISet = aISet;

		for (int i = row; i < dimension; i++) 
		{
			boolean isAnISet = true;
			
			for (int j = 0; j < aISet.size(); j++) 
			{
				if (graph[aISet.get(j)][i] != 1) 
				{
					isAnISet = false;
				}
			}

			if (isAnISet) 
			{
				ArrayList<Integer> currentISet = new ArrayList<Integer>(aISet);
				currentISet.add(i);
				temp = maxISet(currentISet, i+1, dimension);

				if (temp.size() > maxISet.size()) {
					maxISet = temp;
				}
			}
		} 
		
		long endTime = System.currentTimeMillis();
		ms = endTime - startTime;
		return maxISet;
	}

	
	private static int countEdges() 
	{
		edges = 0;
		for (int i = 0; i < dimension; i++) 
		{
			for (int j = i+1; j < dimension; j++) 
			{
				if (graph[i][j] == 1) 
				{
					edges++;
				}
			}
		}
		
		return edges / 2 + (dimension / 2);
	}


	private static int getNext(BufferedReader br) throws IOException 
	{
		char c = (char) br.read();
		int n = Character.getNumericValue(c);
		return n;
	}


	private static void printGraph(ArrayList<Integer> aISet) 
	{
		System.out.println("G" + graphs + " (" + dimension + ", " + edges + ") " +
			aISet.toString() + "(size=" + aISet.size() + ", " + ms + " ms)");
	} 
}
