/*
				CS4092 Java Project 3, group 7
	@authors 	Adam Swayne,Ian Mckay, Matthew Blake, Ibrahim Alaydi
*/


import javax.swing.*;
import java.io.*;
import java.util.*;

public class FlightManager
{
	public static ArrayList<Airport> airport = new ArrayList<Airport>();
	public static ArrayList<Flight>	 flight = new ArrayList<Flight>();


	public static void main(String[] args) throws IOException
	{
		File airportsFile    = 	new File("airports.txt");
		File flightsFile     =  new File("flights.txt");

		if(validateFiles(airportsFile) && validateFiles(flightsFile))
		{
			readInFiles();
			
			if(validation(args))
			{
				switch(args[0].toUpperCase())
				{
					case "AA":       	Airport.add(airport, flight, args[1],args[2]); break;
					case "EA":       	Airport.edit(airport, flight, args[1],args[2]); break;
					case "DA":       	Airport.delete(airport, flight, args[1]); break;
					case "EF":       	Flight.edit(airport, flight, args[1], args[2], args[3], args[4]); break;
					case "DF":       	Flight.delete(airport, flight, args[1]); break;
					case "SF":       	Flight.searchFlight(airport, flight, args[1], args[2]); break;
					case "SD":      	Flight.searchDate(airport, flight, args[1], args[2], args[3]); break;
					default:        	
					{
						System.out.println("Error, invalid command supplied.");	
						displayInstructions();
					}			
				}
			}
			else
				System.out.println("Error, invalid command arguments supplied");
		}
	}


	public static boolean validation(String[] args)
	{
		boolean isValid = false;
		
		return isValid;
	}

	public static void displayInstructions() 
	{ 
		System.out.println(""); 
		System.out.println("************************ Assistance *****************************************************");
		System.out.println("Add new airport               e.g. java FlightManager AA Lisbon LIS"); 
		System.out.println("Edit airport                  e.g. java FlightManager EA BHD Belfast");
		System.out.println("Delete airport                e.g. java FlightManager DA SNN"); 
		System.out.println("Edit flight                   e.g. java FlightManager EF EF3240 MTW-F-- 1/5/2017 31/10/2017"); 
		System.out.println("Delete flight                 e.g. java FlightManager DF EH3240"); 
		System.out.println("Search for flights            e.g. java FlightManager SF Dublin Shannon"); 
		System.out.println("Search for flights on date    e.g. java FlightManager SD Dublin Shannon 1/12/2017"); 
		System.out.println("*****************************************************************************************");
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
		Scanner in = new Scanner(airportFile);

		String[] data;

		while(in.hasNext())
		{
			data = in.nextLine().split(",");
			airport.add(new Airport(data[0], data[1]));
		}
		
		in = new Scanner(flightFile);
		
		while(in.hasNext())
		{
			data = in.nextLine().split(",");
			flight.add(new Flight(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
		}		

		in.close();
		airportFile.close();
		flightFile.close();
	}
}