/*
				CS4092 Java Project 3, group 7
	@authors 	Adam Swayne,Ian Mckay, Matthew Blake, Ibrahim Alaydi
*/


import javax.swing.*;
import java.io.*;
import java.util.*;

public class FlightManager
{
	public static Airport[] airport;
	public static Flight[]	flight;


	public static void main(String[] args) throws IOException
	{
		File airportsFile    = 	new File("airports.txt");
		File flightsFile     =  new File("flights.txt");

		if(validateFiles(airportsFile) && validateFiles(flightsFile))
		{
			readInFiles();

			switch(input[0].toUpperCase())
			{
				case "AA":       System.out.print(airport.add(input[1],input[2])); break;
				//case "EA":       editAirport(input[1],input[2]); break;
				//case "DA":       deleteAirport(input[1]); break;
				//case "EF":       editFlight(input[1],input[2],input[3],input[4]); break;
				//case "DF":       deleteFlight(input[1]); break;
				//case "SF":       searchFlight(input[1],input[2]); break;
				//case "SD":       searchDate(input[1],input[2],input[3]); break;
				//case "help":	 	displayInstructions();
				default:        	System.out.println();			}	
		}
	}

/*
		@authors	Adam Swayne
	
		Input:		Nothing
	
		Processing:	Makes sure the files are present, if they are not, they are created
	
        Output:		Returns true when files are present
*/

	public static boolean validateFiles(File file) throws IOException
	{
		if(!file.exists())	 		file.createNewFile();			
		return true;
	}

/*
		@authors 		Adam Swayne
	
		Input: 			Nothing
	
		Processing:		reads in files from text file and stores in the airports + flights arrays
	
        Output:			Nothing		
*/

	 public static void readInFiles() throws FileNotFoundException, IOException
	{

		FileReader airportFile = new FileReader("airports.txt");
		FileReader flightFile = new FileReader("flights.txt");
			
		ArrayList<ArrayList<String>> airportList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> flightList = new ArrayList<ArrayList<String>>();

		Scanner in = new Scanner(airportFile);

		String[] data;
		int counter = 0;

		while(in.hasNext())
		{
			airportList.add(new ArrayList<String>());
			data = in.nextLine().split(",");

			for(int i=0;i<data.length;i++)				airportList.get(counter).add(data[i]);
			
			counter++;
		}
		
		in = new Scanner(flightFile);
		counter=0;
		
		while(in.hasNext())
		{
			flightList.add(new ArrayList<String>());
			data = in.nextLine().split(",");

			for(int i = 0;i < data.length;i++)				flightList.get(counter).add(data[i]);

			counter++;
		}		

		airport = new Airport[airportList.size()];
		flight = new Flight[flightList.size()];
		
		for(int i = 0;i < airportList.size(); i++)			airport[i] = new Airport(airportList.get(i).get(0), airportList.get(i).get(1));
		for(int i = 0;i < flightList.size(); i++)			flight[i]  = new Flight(flightList.get(i).get(0), flightList.get(i).get(1), 
																	flightList.get(i).get(2), flightList.get(i).get(3), flightList.get(i).get(4), 
																	flightList.get(i).get(5), flightList.get(i).get(6),
																	flightList.get(i).get(7));

		in.close();
		airportFile.close();
		flightFile.close();
	}

}