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
			for(int i = 0;i < args.length;i++)
				args[i] = args[i].toUpperCase();

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
		}
	}


	public static boolean validation(String[] args)
	{
		boolean validFlightNumber = false;
		boolean validAirportCode = false;
		boolean isValid = false;
		boolean sameAirport = false;
		boolean codeFound = false;
		boolean validDays = false;
		String input = "";
		boolean validStartDate = false;
		boolean validEndDate = false;
		boolean validTime = false;
		String startDate = "";
		String endDate = "";
		boolean validDate = false;

		boolean needValidateCode = false, needValidateFlightNum = false, needValidateDays = false, needValidateDate = false;

		if((args.length < 6 && args.length > 1) || args.length == 9)
		{
			if(args[0].equals("AA") || args[0].equals("EA") || args[0].equals("DA"))
				needValidateCode = true;

			if(args[0].equals("AF"))
			{
				needValidateCode = true;
				needValidateFlightNum = true;
				needValidateDays = true;
				needValidateDate = true;
			}
			if(args[0].equals("EF"))
			{
				needValidateFlightNum = true;
				needValidateDays = true;
				needValidateDate = true;
			}
			if(args[0].equals("DF"))
				needValidateFlightNum = true;
			if(args[0].equals("SD"))
				needValidateDate = true;

			//Validate flight number
			if(needValidateFlightNum)
			{
				if(args[0].equals("AF") || args[0].equals("EF") || args[0].equals("DF"))
				{
					if(args[1].length() < 3 || !(args[1].substring(0,2).matches("[a-zA-Z]{2}") && args[1].substring(2,args[1].length()).matches("\\d{1,}")) )
						System.out.println("Error, flight number should be 2 Letters followed by a string of numbers e.g AE1240");
					else if(args[1].length() > 2)
						validFlightNumber = true;
				}
			}

			//validate AirportCode
			if(needValidateCode)
			{
				if(args[0].equals("AA") || args[0].equals("EA") || args[0].equals("DA") || args[0].equals("AF"))
				{
					ArrayList<String> airports = new ArrayList<String>();
					ArrayList<String> airportsIn = new ArrayList<String>();

					if(args[0].equals("AA"))
						{
						if(args[2].matches("[a-zA-Z]{3}"))
							validAirportCode = true;
							airportsIn.add(args[2]);
						}

					else if(args[0].equals("EA") || args[0].equals("DA"))
						{
						if(args[1].matches("[a-zA-Z]{3}"))
							validAirportCode = true;
							airportsIn.add(args[1]);
						}
					else if(args[0].equals("AF"))
						{
						if(args[2].matches("[a-zA-Z]{3}") && args[3].matches("[a-zA-Z]{3}"))
							if(!(args[2].equals(args[3])))
							{
								validAirportCode = true;
								airportsIn.add(args[2]);
								airportsIn.add(args[3]);
							}
							else
							{
								System.out.println("Error, departure airport and arrival airport cannot be the same");
								sameAirport = true;
							}
						}
					if(!validAirportCode && !sameAirport)
					System.out.println("Error, invalid airport code, Airport codes should be 3 alphabetic characters long");
				}
			}

			//Validate days

			if(needValidateDays)
			{
				if(args[0].equals("AF") || args[0].equals("EF"))
				{
					if(args[0].equals("AF"))	input = args[6];
					else						input = args[2];
					String days = "MTWTFSS";
					String dash = "-";
					int countInOrder=0;
					int matchCount=0;
					boolean matchThisPos=false;
					if(input.length() == 7)
					{
						for(int i = 0; i < input.length(); i++)
						{
							for(int j = countInOrder; j < days.length(); j++)
								if((input.charAt(i)==days.charAt(j) || input.charAt(i) == dash.charAt(0)) && !matchThisPos)
								{
									matchCount++;
									countInOrder= j+1;
									matchThisPos=true;
								}
							matchThisPos=false;
						}
						if(matchCount==input.length())
							validDays = true;
					}
				}
				if(!validDays)
					System.out.println("Error, invalid days inputted");
				if(input.equals("-------"))
				{
					validDays = false;
					System.out.println("Error, flight days cannot be empty i.e '-------'");
				}
			}


			//validate date
			if(needValidateDate)
			{
				if(args[0].equals("AF") || args[0].equals("EF") || args[0].equals("SD"))
				{
					String[] startDates = new String[3];
					String[] endDates = new String[3];
					boolean twoDates = true;

					if(args[0].equals("AF"))	
					{
						startDates = args[7].split("/");
							validStartDate = checkDate(startDates);
						endDates = args[8].split("/");
							validEndDate = checkDate(endDates);
					}
					else if(args[0].equals("EF"))
					{
						startDates = args[3].split("/");
							validStartDate = checkDate(startDates);
						endDates = args[4].split("/");
							validEndDate = checkDate(endDates);

					}
					else
					{
						startDates = args[3].split("/");
							validStartDate = checkDate(startDates);
						twoDates = false;
						validEndDate = true;
					}		

					if(!validStartDate)
						System.out.println("Error, invalid start date");
					else if (!validEndDate)
						System.out.println("Error, invalid end date");
					if(validStartDate && validEndDate)
					{
						if(!(args[0].equals("SD")))
						{
							Calendar startD = Calendar.getInstance();
							Calendar endD = Calendar.getInstance();

							startD.set(Integer.parseInt(startDates[2]), Integer.parseInt(startDates[1]), Integer.parseInt(startDates[0]));
							endD.set(Integer.parseInt(endDates[2]), Integer.parseInt(endDates[1]), Integer.parseInt(endDates[0]));

							if(!(endD.after(startD)))
								System.out.println("Error, End date appears to be before the start date");
							else
								validEndDate = false;
						}
					}
					if(validStartDate && validEndDate)
						validDate = true;
				}
			}
		}

		else
			System.out.println("Error, invalid command-line arguments supplied.");

		if(needValidateDate && validDate)					isValid = true;
		if(needValidateDays && validDays)					isValid = true;
		if(needValidateFlightNum && validFlightNumber )		isValid = true;
		if(needValidateCode && validAirportCode)			isValid = true;

		return isValid;
	}

	public static boolean checkDate(String[] dateElements)
	{
		int ddInt, mmInt, yyInt;
		int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
		boolean dateIsValid = true;
		
		ddInt= Integer.parseInt(dateElements[0]);
		mmInt= Integer.parseInt(dateElements[1]);
		yyInt= Integer.parseInt(dateElements[2]);
		
		if(ddInt == 0 || mmInt == 0 || yyInt == 0)																dateIsValid =false;
		
		else if(mmInt > 12)																						dateIsValid =false;
		
		else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))		dateIsValid =true;
		
		else if(ddInt > daysArray[mmInt -1])																	dateIsValid =false;

		return dateIsValid;
	}




	public static void displayInstructions() 
	{ 
		System.out.println(""); 
		System.out.println("************************ Assistance *****************************************************");
		System.out.println("Add new airport               e.g. java FlightManager AA Lisbon LIS"); 
		System.out.println("Edit airport                  e.g. java FlightManager EA BHD Belfast");
		System.out.println("Delete airport                e.g. java FlightManager DA SNN"); 
		System.out.println("Add new flight                e.g. java FlightManager AF ER1230 DUB SNN 1310 1425 -TWTF-- 10/10/2017 15/11/2017");
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
