import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This is the driver class for the media database which houses the main method and also houses the methods
 * for parsing and searching the database for the movies and/or tv episodes requested by the user. The program will read the 
 * data by parsing, thus creating an object. This object will be saved into the ArrayList in MovieDatabase or TVSeriesDatabase 
 * and will then be divided up by title, year, format, and roman numerals in the case of a movie, and season title,
 * season year, season number, the year it began, the year it ended, episode title, and episode number though not all will
 * include all bits if info as were mentioned.
 * 
 */
public class MDbDriver {
	
	private static TVSeriesDatabase SeriesDb = new TVSeriesDatabase(); //the object is used to store season's and their episodes
	private static MovieDatabase movDb = new MovieDatabase(); //The object is used to store movies
	private static String userInput;	//This variable holds the users search criteria.
	private static int searchAgain;		//This variable is used to determine if the user wants to search again.
	private static File file; 			//The file of movies that the user wishes to search from.
	private static ArrayList<MovieInformation> movies; //This ArrayList serves as a placeholder of sorts to help aid in the search process.
	private static ArrayList<TVSeriesInformation> tvSeries; 
	
	
	/**
	 * Main method
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Enter name of .txt file consisting of TV series and episodes");
		Scanner keyboard = new Scanner(System.in);
		String userInput = keyboard.next();
		
		File media = new File(userInput);	
		
		parseTVData(media);
	}
	
	
	
	
	/**
	 * This method parses the data in order to break it up to allow the lines of test to be searched (search performed by 
	 * searchMovies() for the individual movies or by searchTVSeries() that are specified by the user.
	 * 
	 * @param infoLine gives the parseData method the info of which needs parsing.
	 * @throws IOException 
	 */
	private static void parseTVData(File file) throws IOException
	{
		File movieList = file;
		
		if (movieList != null)
		{
			//BufferedReader (variable name "br") is used to read each line individually. 
			BufferedReader br = new BufferedReader(new FileReader(movieList));

			String line = br.readLine();

			while (line != null)
			{
				/*
				 * The String delimiter is used to split the line of text into the separate tokens
				 * using the delimiter as the search criteria for the split.
				 */
				
				String delimiter = "[({})]";
				String[] tokens = line.split(delimiter);
		
				if (tokens.length == 11) 
				{
					//if this statement is true, then there is every possible amount of info
					
					//input: "Star Trek: The Continuing Mission" (2007) {Command Decision (#1.5)} {{SUSPENDED}}	2009
					
					//output:
					// 0 - StarTrek: The Continuing Mission 
					// 1 - 2007
					// 2 - 
					// 3 - Command Decision
					// 4 - #1.5
					// 5 -
					// 6 -
					// 7 -
					// 8 - SUSPENDED
					// 9 -
					// 10 - 2009
					
					System.out.println(tokens[0].trim());
					System.out.println(tokens[1].trim());
					System.out.println(tokens[3].trim());
					System.out.println(tokens[4].trim());
					System.out.println(tokens[8].trim());
					System.out.println(tokens[10].trim() + '\n');
					
					
				}
				
				else if (tokens.length == 9)
				{
					//input: "Star Trek Anthology(random parenthesis in title)" (2015) {Another Door Opens (#1.1)}	2015"
					
					//output:
					// 0 - Star Trek Anthology 
					// 1 - (random parenthesis in title)
					// 2 - 
					// 3 - 2015
					// 4 - 
					// 5 - Another Door Opens
					// 6 - #1.1
					// 7 -
					// 8 - SUSPENDED
					
					System.out.println(tokens[0].trim());
					System.out.println(tokens[1].trim());
					System.out.println(tokens[3].trim());
					System.out.println(tokens[5].trim());
					System.out.println(tokens[6].trim());
					System.out.println(tokens[8].trim() + '\n');
					
					
				}
				
				else if (tokens.length == 7) 
				{
					
					if (tokens[4].contains("SUSPENDED"))
					{
						//MUST BE A SUSPENDED SERIES
						
						//input: "Star Trek: The Continuing Mission" (2007) {{SUSPENDED}}	2007-????
						
						//Results:
						// 0 - "Star Trek: The Continuing Mission"
						// 1 - 2007
						// 2 -  
						// 3 - 
						// 4 - SUSPENDED
						// 5 - 
						// 6 - 2007-????
						
						System.out.println(tokens[0].trim());
						System.out.println(tokens[1].trim());
						System.out.println(tokens[4].trim());
						System.out.println(tokens[6].trim() + '\n');
					}
					
					else if (!(tokens[3].equals(""))) 
					{
						
						
						// sample line: Star Trek Anthology (2015) {Another Door Opens (#1.1)}	2015
						//Results:
						// 0 - Star Trek Anthology
						// 1 - 2015
						// 2 -  
						// 3 - Another Door Opens
						// 4 - #1.1
						// 5 - 
						// 6 - 2015
						
						System.out.println(tokens[0].trim());
						System.out.println(tokens[1].trim());
						System.out.println(tokens[3].trim());
						System.out.println(tokens[4].trim());
						System.out.println(tokens[6].trim() + '\n');
						
					}
					
					else {
						
						//if this statement is true, there is no episode title
						
						// sample line: "Star Trek Continues" (2013) {(#1.7)}			2016
						//Results:
						// 0 - Star Trek Continues
						// 1 - 2013
						// 2 -  
						// 3 - 
						// 4 - #1.7
						// 5 - 
						// 6 - 2016
					
						System.out.println(tokens[0].trim());
						System.out.println(tokens[1].trim());
						System.out.println(tokens[4].trim());
						System.out.println(tokens[6].trim() + '\n');
					
					}
				} 
				
				else if (tokens.length == 5) 
				{
				
					//If this statement is true, there is no episode number
					
					// sample line: "Star Trek Cataja" (2013) {Der Tag danach}		2015"
					//Results:
					// 0 - Star Trek Cataja
					// 1 - 2013
					// 2 - 
					// 3 - Der Tag danach
					// 4 - 2015
					
		
					System.out.println(tokens[0].trim());
					System.out.println(tokens[1].trim());
					System.out.println(tokens[3].trim());
					System.out.println(tokens[4].trim() + '\n');
					
		
				}
				
				else if (tokens.length == 3) 
				{
				
					//If this statement is true, it is a series title
					
					// sample line: "Star Trek Begins (2014)				2014-????"
					//Results:
					// 0 - Star Trek Begins
					// 1 - 2014
					// 2 - 2014-????
					
					System.out.println(tokens[0].trim());
					System.out.println(tokens[1].trim());
					System.out.println(tokens[2].trim() + '\n');		
					
				} 
		 
				else 
				{
					//Indicates an issue with the format of the input file.
					JOptionPane.showMessageDialog(null, "Problem with format of input file", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				//reads to make sure there is another existing line to determine if the while loop should be considered.
				line = br.readLine();
			}
			
			br.close();
		}
		else	
		{	
			//When no file is given.
			JOptionPane.showMessageDialog(null, "Could not open file or file doesn't exist", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
		
	
	
	
	/**
	 * This method searches the file given and returns the movie's info based off of the search criteria 
	 * supplied by the user.
	 * 
	 * @param userInput movies search criteria entered by user
	 * @return gives the movie info in the form of a String.
	 */
	public static String searchMovies(String userInput, ArrayList<MovieInformation> movies)
	{
		
		
		return "";
	}

	
	/**
	 * This method searches the file given and returns the movie's info based off of the search criteria 
	 * supplied by the user.
	 * 
	 * @param userInput tvSeries search criteria entered by user
	 * @return gives the series info in the form of a String.
	 */
	public static String searchTVSeries(String userInput, ArrayList<TVSeriesInformation> tvSeries)
	{
		
		
		return "";
	}
	

}
