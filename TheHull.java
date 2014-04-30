/*
 * A convex hull solving algorithm Graham Scan
 * Author:  Scott Gramig
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.io.*;

public class TheHull
{
	private static Scanner input;// Scanner object
	public static String[] currLine;//holds the split string
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		Scanner kb = new Scanner(System.in);//input from keyboard
		String line = null;//var to hold input from text file
		String fileName, newFile;//file to read and file to save output
		
		ArrayList<String> allNodes = new ArrayList<String>();//.size() will provide the total number of vertices

		System.out.print("\nEnter the file name to use: ");//my file location - /home/p0wder/workspace/gramigsConvexHull/src/vertices.txt
		fileName =  kb.nextLine();
		System.out.print("\nEnter the desired output file(.txt): ");
		newFile = kb.nextLine();
		PrintWriter pw = new PrintWriter(newFile, "UTF-8");
		input = new Scanner(new File(fileName));//read input file

		HashMap<String, MyPoint> allPoints = new HashMap<String, MyPoint>();
		
		//this while loop puts all input into a HashMap and ArrayList of all nodes
		while(input.hasNext())
		{
			line = input.nextLine();
			currLine = line.split(", ");
			MyPoint currPoint = new MyPoint(currLine[0],Integer.parseInt(currLine[1]), Integer.parseInt(currLine[2]));
			allNodes.add(currLine[0]);
			allPoints.put(currLine[0], currPoint);
		}
		
		//lets find the left most point on the cartesian plane
		String leftMost = allNodes.get(0);
		
		for(int i = 0; i < allNodes.size(); i++)
		{
			String currKey = allNodes.get(i);
			if(allPoints.get(currKey).x < allPoints.get(leftMost).x)
				leftMost = currKey;
		}

		//now lets find the slopes of 2 lines lines and compare them
		MyLine allLines[] = new MyLine[allNodes.size()-1];
		
		int j = 0;//indexing for allLines
		for(int i = 0; i < allNodes.size(); i++)
		{
			if(leftMost == allNodes.get(i))
				continue;
			
			MyPoint fromPoint = allPoints.get(leftMost);
			MyPoint toPoint = allPoints.get(allNodes.get(i));
			
			MyLine currentLine = new MyLine(fromPoint, toPoint);
			
			allLines[j] = currentLine;
			//System.out.println(allLines[j].slope);
			j++;
		}
		
		//sort in acsending order
		Arrays.sort(allLines);

		//this will have the final result for convex hull
		Stack<String> ourStack = new Stack<String>();
		ourStack.push(leftMost);
		String elementBeforeLast = ourStack.peek();
		ourStack.push(allLines[0].point2.name);
		
		for(int i = 0; i < allLines.length-1; i++)
		{
			String lastElementInStack = ourStack.peek();
			
			MyLine line1 = new MyLine(allPoints.get(elementBeforeLast), allPoints.get(lastElementInStack));
			MyLine line2 = new MyLine(allPoints.get(elementBeforeLast), allPoints.get(allLines[i+1].point2.name));
			
			if(!line1.isItClockwise(line2))
			{
				elementBeforeLast = ourStack.peek();
				ourStack.push(allLines[i+1].point2.name);
			}
			else
			{
				ourStack.pop();
				lastElementInStack = ourStack.pop();
				elementBeforeLast = ourStack.pop();
				ourStack.push(elementBeforeLast);
				ourStack.push(lastElementInStack);
				i--;
			}
		}
		while(!ourStack.empty())
		{
			pw.println(ourStack.pop());
		}
		pw.close();
		kb.close();
	}
}



