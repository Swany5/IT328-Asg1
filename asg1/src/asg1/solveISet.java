package asg1;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class solveISet {

	public static int[][] graph;
	public static int dimension, numGraphs, numEdges, row;
	public static long ms;


	public solveISet() {
		graph = new int[60][60];
		dimension = 0;
		numGraphs = 0;
		numEdges = 0;
		row = 0;
		ms = 0;
	}


	public ArrayList<Integer> findMaxIndependentSet(ArrayList<Integer> aClique, int row, int aDimension) {
		long startTime = System.currentTimeMillis();

		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		maxClique = aClique;

		for (int i = row; i < dimension; i++) {
			boolean isAClique = true;
			for (int j = 0; j < aClique.size(); j++) {
				if (graph[aClique.get(j)][i] != 1) {
					isAClique = false;
				}
			}

			if (isAClique) {
				ArrayList<Integer> currentClique = new ArrayList<Integer>(aClique);
				currentClique.add(i);
				temp = findMaxIndependentSet(currentClique, i+1, dimension);

				if (temp.size() > maxClique.size()) {
					maxClique = temp;
				}
			}
		} // end outer
		long endTime = System.currentTimeMillis();
		ms = endTime - startTime;
		return maxClique;
	}

	
	private static int countEdges() {
		numEdges = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = i+1; j < dimension; j++) {
				if (graph[i][j] == 1) {
					numEdges++;
				}
			}
		}
		return numEdges / 2 + (dimension / 2);
	}


	private static int getNext(BufferedReader br) throws IOException {
		char c = (char) br.read();
		int x = Character.getNumericValue(c);
		return x;
	}


	private static void printGraphSolution(ArrayList<Integer> aClique) {
		int size = aClique.size();

		System.out.println("G" + numGraphs + " (" + dimension + ", " + numEdges + ") " +
			aClique.toString() + "(size=" + aClique.size() + ", " + ms + " ms)");
	}


	private static void printGraph() {
		System.out.println("dimension = " + dimension);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}


	public static void main(String[] args) {
		solveISet indSet = new solveISet();

		System.out.println("* Max Independent Set in graphs in graphs.txt\n");
		System.out.println("(|V|,|E|) Independent Sets (size, ms used)");


		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("graphs2021.txt"));
			String line = null;
			while((line = br.readLine()) != null) {
				dimension = Integer.parseInt(line);

				if (dimension != 0) {
					numGraphs++;


					for(int i = 0; i < dimension; i++) {
						for(int j = 0; j < dimension; j++) {
							graph[i][j] = getNext(br);

							countEdges(); 
							if (i != j) {
								if (graph[i][j] == 1) {
									graph[i][j] = 0;
								} else {
									graph[i][j] = 1;
								}
							} 

							br.read(); 
						} 
						br.read();
						br.read();
					} 

		
					ArrayList<Integer> cliqueList = new ArrayList<Integer>();
					cliqueList = indSet.findMaxIndependentSet(cliqueList, row, dimension);
					printGraphSolution(cliqueList);
		
				}
			} 
			br.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
		} 
		catch (IOException e) {
			System.out.println("Could not read file.");
		}
	} 
}