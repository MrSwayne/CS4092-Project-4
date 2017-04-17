import javax.swing.*;
import java.io.*;
import java.util.*;

public class Flight
{
	private String number;
	private String airportDepart;
	private String airportArrival;
	private String timeDepart;
	private String timeArrival;
	private String days;
	private String dateDepart;
	private String dateArrival;

	public Flight(String fn, String ad, String aa, String td, String ta, String d, String dd, String da)
	{
		number = fn;
		airportDepart = ad;
		airportArrival = aa;
		timeDepart = td;
		timeArrival = ta;
		days = d;
		dateDepart = dd;
		dateArrival = da;
	}

	public String number()
	{
		return number;
	}

	public String airportDepart()
	{
		return airportDepart;
	}

	public String airportArrival()
	{
		return airportArrival;
	}

	public String timeDepart()
	{
		return timeDepart;
	}

	public String timeArrival()
	{
		return timeArrival;
	}

	public String days()
	{
		return days;
	}

	public String dateDepart()
	{
		return dateDepart;
	}

	public String dateArrival()
	{
		return dateArrival;
	}


	/*
		@author Adam Swayne

		input:

		Processing:

		output:
	*/

	public static void add(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String flightNum, String airportDepart, String airportArrival, String timeDepart, String timeArrival, String days, String dateStart, String dateEnd)
	{
		boolean dCodeFound = false;
		boolean aCodeFound = false;
		boolean flightExists = false;

		for(int i = 0;i < airportList.size();i++)
		{
			if(airportList.get(i).code().equalsIgnoreCase(airportDepart))
				dCodeFound = true;
			if(airportList.get(i).code().equalsIgnoreCase(airportArrival))
				aCodeFound = true;
		}
		if(!(aCodeFound))	JOptionPane.showMessageDialog(null,"Error, arrival airport not found in Airports.txt");
		if(!(dCodeFound))	JOptionPane.showMessageDialog(null,"Error, Departure airport not found in Airports.txt");
		else
		{

			for(int i = 0;i < flightList.size() && !flightExists;i++)
				if(flightList.get(i).number().equalsIgnoreCase(flightNum))	
					flightExists = true;	
			if(!flightExists)
			{
				JOptionPane.showMessageDialog(null,"Flight " + flightNum + " added successfully");
				flightList.add(new Flight(flightNum, airportDepart, airportArrival, timeDepart, timeArrival, days, dateStart, dateEnd));
			}
			else
				JOptionPane.showMessageDialog(null,"Error, flight " + flightNum + " already exists");
		}
		sortFiles(airportList, flightList);
	}

     /*
		@authors Matthew Blake
	
		Input: Command-line arguments
        
		Output: Error or success message
	*/

	public static void edit(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String flightNum,String flightDays,String startDateFlight,String endDateFlight)
	{
		boolean isFound = false;
		boolean flightToEdit = false;
		int rowFound = 0;
		for (int i = 0; i < flightList.size() && !isFound; i++)
			//checks if flight exists
			if(flightList.get(i).number.equalsIgnoreCase(flightNum))
				isFound = true;
		if(!isFound)
			JOptionPane.showMessageDialog(null,"Error, Flight Number " + flightNum + " not found");

		else
		{
			for (int i = 0; i < flightList.size(); i++)
			{
					if(flightList.get(i).number.equalsIgnoreCase(flightNum))
					{
						rowFound = i;//finds row of flight in .txt file
						flightToEdit = true;	
					}
			}
			
			for (int i = 0; i < flightList.size(); i++)
				if(flightList.get(i).number.equalsIgnoreCase(flightNum))
					isFound = true;
			
			if(flightToEdit)
			{
				flightList.set(rowFound,new Flight(flightNum, flightList.get(rowFound).airportDepart(), flightList.get(rowFound).airportArrival(),
																flightList.get(rowFound).timeDepart, flightList.get(rowFound).timeArrival,
																 flightDays, startDateFlight, endDateFlight));
				JOptionPane.showMessageDialog(null,"Flight successfully edited");
			}
		}
		sortFiles(airportList, flightList);
	}


	/*
		@authors 	Matthew Blake
		
		Input: 			Command-line arguments
        
		Output:			Error or success message
	*/

	public static void delete(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String flightNum)
	{
		boolean isFound = false;
		boolean flightToDelete = false;
		int rowFound = 0;
		for (int i = 0; i < flightList.size(); i++)
			if(flightList.get(i).number.equalsIgnoreCase(flightNum))
				isFound = true;
		
		if(isFound)
		{
			for (int i = 0; i < flightList.size(); i++)
			{
				if(flightList.get(i).number.equalsIgnoreCase(flightNum))
			  	{
					rowFound = i;
					flightList.remove(rowFound);
					JOptionPane.showMessageDialog(null,"Flight " + flightNum + " was successfully deleted");
					flightToDelete = false;
					i--;
			  	}
		  	}
		}
		else
			JOptionPane.showMessageDialog(null,"Error, flight Number " + flightNum + " was not found in flights.txt");
	}

	/*
		@authors			Ian Mckay 
	
		Input:				AirportDepart, airportArrive from user Input 
		
		Proccessing:		Searchs for a flight and lists everything to do with the flight that was searched	
		
		Output:				Details of flight
	*/

	public static void searchFlight(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String airportDepart,String airportArrive)
	{
		boolean departFound =false;
		boolean arriveFound =false;
		String result = "";
		String departCode = "";
		String arriveCode = "";
		String departName = "";
		String arriveName = "";
		ArrayList<Integer> flightsFound = new ArrayList<Integer>();
		
		for (int i = 0; i <airportList.size(); i++)
		{
			if(airportList.get(i).name().equalsIgnoreCase(airportDepart))
			{
				departCode = airportList.get(i).code();
				departName = airportList.get(i).name();
				departFound = true;
			}
			if(airportList.get(i).name().equalsIgnoreCase(airportArrive))
			{
				arriveCode = airportList.get(i).code();
				arriveName = airportList.get(i).name();
				arriveFound = true;
			}
		}

		if(departFound && arriveFound)
		{
			for (int i = 0; i <flightList.size(); i++)
			{
				if(flightList.get(i).airportArrival().equalsIgnoreCase(arriveCode)&& flightList.get(i).airportDepart().equals(departCode))
					flightsFound.add(i);
			}

			if(!(flightsFound.size() == 0))
			{
				JOptionPane.showMessageDialog(null,"The Following Flight(s) depart from " + airportDepart + " and arrive in " + airportArrive + ".");
				for(int i = 0;i < flightsFound.size();i++)
				{
					JOptionPane.showMessageDialog(null,"Flight Number :\t\t" + flightList.get(flightsFound.get(i)).number() + "\nDeparting from :\t" + departName +
					"\t\t At:\t" + flightList.get(flightsFound.get(i)).timeDepart() + "\nArriving to :\t\t" + arriveName +
					"\t\t At:\t" + flightList.get(flightsFound.get(i)).timeArrival() + "\nRunning on :\t\t" + flightList.get(flightsFound.get(i)).days() +
					"\nFrom :\t\t\t" + flightList.get(flightsFound.get(i)).dateDepart() + "\nUntil :\t\t\t" + flightList.get(flightsFound.get(i)).dateArrival());
				}
			}
			else
				JOptionPane.showMessageDialog(null,"No flights departing from " + airportDepart + " and arriving to " + airportArrive + " were found");
		}
		else
		{
			if(!departFound)
				JOptionPane.showMessageDialog(null,"Error, departure Airport not found");
			if(!arriveFound)
				JOptionPane.showMessageDialog(null,"Error, Arrival Airport not found");
		}
		sortFiles(airportList, flightList);
	}

		/*
			@author Adam Swayne
			
			Input:				 airport that the flight is departing from, the airport the flight is arriving to and the date.
			
			processing: 		Checks to see if the airports that were inputted exists, then gets their corresponding airport
									codes from the airports array,  then checks the flights array and checks if there is any flight
									going from that airport to the other airport on that date, it also checks if the date is within the timeframe
									and then checks if the date inputted matches the days that the flight is running on and then outputs
									the list of flights matching that criteria to the user

			Output:				List of flights matching criteria
		*/
        
        public static void searchDate(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String airportDepart,String airportArrive, String date)
		{
			boolean stop = false;
			int[] numbers = new int[flightList.size()];
			String airportDepartCode = "", airportArriveCode = "";
			int counter = 0;
			
			//Finds the corresponding airport code to the airports supplied
			for(int i = 0;i < airportList.size() && !stop; i++)
			{
				if (airportDepart.equalsIgnoreCase(airportList.get(i).name()))			airportDepartCode += airportList.get(i).code();
				if(airportArrive.equalsIgnoreCase(airportList.get(i).name()))			airportArriveCode += airportList.get(i).code();	
					
				//once both airports have been found, it should break out of the loop
				if ((!(airportDepartCode.equals(""))) && (!(airportArriveCode.equals(""))))		stop = true;
			}
			
			for(int i = 0;i < flightList.size();i++)
			{
				//go through all of the arrayList and check if the arrival/departing airport and the date matches what's on file, if it does, then the index of that is put into an array so I can call it again later on
				if((flightList.get(i).airportDepart().equalsIgnoreCase(airportDepartCode)) && (flightList.get(i).airportArrival.equalsIgnoreCase(airportArriveCode)) && (checkDate(date.split("/"), flightList.get(i).dateDepart().split("/"), flightList.get(i).dateArrival().split("/"), flightList.get(i).days())))
				{
					numbers[counter] = i;
					counter++;
				}
				
			}
			//Print out how many flights were found corresponding to search criteria
			if(counter == 1)			JOptionPane.showMessageDialog(null,"There was only 1 flight found matching that criteria.");
			else 						JOptionPane.showMessageDialog(null,"There were " + (counter) + " flights found matching that criteria.");
			
			for(int i = 0; i < counter; i++)
			{
				//Output the textfile data to the user
				System.out.println((i + 1) + ".\nFlight Number   :\t"  + flightList.get(numbers[i]).number() +
											"\nleaving \t: \t" + airportDepart + "\t\t at : \t" + flightList.get(numbers[i]).timeDepart().substring(0,2) + ":" + flightList.get(numbers[i]).timeDepart().substring(2,4) +
											"\nArriving to \t:\t" + airportArrive + "\t\t at : \t" + flightList.get(numbers[i]).timeArrival().substring(0,2) + ":" + flightList.get(numbers[i]).timeArrival().substring(2,4) );
			}	
			sortFiles(airportList, flightList);
		}
		
		/*
			@Author 			Adam Swayne
			
			input:				date inputted by user, start and end date in flights.txt and the days the flight is running in flights.txt (from searchDate method)
			
			processing: 		checks the dates of flights and checks if the flight start date is before the input date and that the end date is after the input date, 
									if that passes, then the checkDaysOfWeek method is called which will then check what day of the week is the date that was inputted,
									if that date corresponds to the days of the flights on file as well as the date paramaters, then the method returns true to searchDate method
			
			output:				returns the value of isValid (if the date paramaters AND the day match) to the searchDate method
		*/
		public static boolean checkDate(String[] dateInput, String[] dateStart, String[] dateEnd, String days)
		{
			boolean isValid = false;
			
			String dayOfWeek = "";
			String daysExtended = "";

			//Creates 3 variables of Calendar type
			Calendar inputDate = Calendar.getInstance();
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			
			//the 3 calendars are set to the year, month and day of the input date, the start date of the flight on file, and the end date of the flight on file
			inputDate.set(Integer.parseInt(dateInput[2]), Integer.parseInt(dateInput[1]), Integer.parseInt(dateInput[0]));
			startDate.set(Integer.parseInt(dateStart[2]), Integer.parseInt(dateStart[1]), Integer.parseInt(dateStart[0]));
			endDate.set(Integer.parseInt(dateEnd[2]), Integer.parseInt(dateEnd[1]), Integer.parseInt(dateEnd[0]));

			//checks to see if the start date is before or equal to the current inputted date, and that the end date is after or equal to the current inputted date
			if((startDate.before(inputDate) || startDate.equals(inputDate)) && (endDate.after(inputDate) || endDate.equals(inputDate)))
				isValid = true;
			
			//if the date is valid, then the days in the flights.txt file are analysed to check if the inputted date day is equal to a day that is on file
			if (isValid)	
			{
				try
				{
					//this is so I can differentiate between days starting with the same letter i.e Tuesday and Thursday or Saturday and Sunday
					dayOfWeek = checkDayOfWeek(dateInput);
					if(days.contains("M"))												daysExtended += "mo/";
					if(days.substring(1,2).equalsIgnoreCase("t"))				daysExtended += "tu";
					if(days.contains("W"))												daysExtended += "we";
					if(days.substring(3,4).equalsIgnoreCase("t"))				daysExtended += "th";
					if(days.contains("F"))													daysExtended += "fr";
					if(days.substring(5,6).equalsIgnoreCase("s"))				daysExtended += "sa";
					if(days.substring(6,7).equalsIgnoreCase("s"))				daysExtended += "su";
					
					//checks if the current day from the inputted date matches a day that is in the flights.txt file
					if(daysExtended.contains(dayOfWeek.substring(0,2)))
						isValid = true;
					else
						isValid = false;
				}
				//if invalid args in the flights.txt file, an error is thrown
				catch(StringIndexOutOfBoundsException e)
				{
					JOptionPane.showMessageDialog(null,"Error, Please make sure the days are in the format ------- with each dash representing a day i.e -TWT-S- represents tuesday, wednesday, thursday and saturday");
				}
			}
			return isValid;
		}
		
		/*
			@author			Adam Swayne
			
			input: 				inputDate from checkDate method
			
			processing:		calculates the day of the week using the ZellerCongruenceTest that we had in semester 1
			
			output: 				returns the day of the week to checkDate method
			
		*/
		
		public static String checkDayOfWeek(String[] inputDate)
		{
			int d = Integer.parseInt(inputDate[0]);
			int m = Integer.parseInt(inputDate[1]);
			int y = Integer.parseInt(inputDate[2]);
			
			String result = ""; 
			int a, b, dayOfWeek;
			
			if (m == 1 || m == 2)
			{
				m += 12; 
				y -=  1;
			}
			
			a = y % 100;  
			b = y / 100;
			dayOfWeek = ((d + (((m + 1)*26)/10)+ a + (a/4) + (b/4)) + (5*b)) % 7;
			switch(dayOfWeek)
			{
				case 0: result = "Saturday";  break;  case 1: result = "Sunday";    break;
				case 2: result = "Monday";    break;  case 3: result = "Tuesday";   break;
				case 4: result = "Wednesday"; break;  case 5: result = "Thursday";  break;
				case 6: result = "Friday";    break;
			}	
			return result.toLowerCase();
		}

	/*
		@author 			Adam Swayne
		
		Input:				Nothing 
		
		Processing:		Sorts the airport.txt file by airport, sorts the flights.txt file by flightNumber then calls the writeTo methods.
		
        Output:				Nothing
	*/

    public static void sortFiles(ArrayList<Airport> airportList, ArrayList<Flight> flightList)
	{	
		Airport temp;
		Flight temp2;
		for(int i = 0;i < airportList.size();i++)
		{
			for(int j = i + 1;j < airportList.size();j++)
			{
				//checks if the airport comes alphabetically before the airport after it 
				if(airportList.get(i).name.compareToIgnoreCase(airportList.get(j).name) > 0)
				{
					//Swapping elements 
					temp = new Airport(airportList.get(i).name(), airportList.get(i).code());
					airportList.set(i,airportList.get(j));
					airportList.set(j, temp);
				}
			}	
		}

		for(int i = 0;i < flightList.size();i++)
		{
			for(int j = i + 1;j < flightList.size();j++)
			{
				//checks if the airport comes alphabetically before the airport after it 
				if(flightList.get(i).number.compareToIgnoreCase(flightList.get(j).number) > 0)
				{
					//Swapping elements 
					temp2 = new Flight(flightList.get(i).number(), flightList.get(i).airportDepart(), flightList.get(i).airportArrival(),
									flightList.get(i).timeDepart(), flightList.get(i).timeArrival(), flightList.get(i).days(),
									flightList.get(i).dateDepart(),	flightList.get(i).dateArrival());
					flightList.set(i,flightList.get(j));
					flightList.set(j, temp2);
				}
			}	
		}

		writeToAirports(airportList);
		writeToFlights(flightList);
    }	


     /*
		@authors			Ian McKay
	
		Input:				Nothing
	
		Proccessing:		Writes the Airports arrayList to the airports.txt file
	
        Output:				Nothing
	*/
        
    public static void writeToAirports(ArrayList<Airport> airportList)
	{
        try
    	{
	       PrintWriter outFile = new PrintWriter("Airports.txt");
	       String currentLine = "";
	       for (int i = 0; i <airportList.size(); i++)
	       {
			    currentLine = airportList.get(i).name() + "," + airportList.get(i).code();

		       //prints each line to the text file
		       outFile.println(currentLine);
	       }
	       outFile.close();
		}
		catch(IOException e)
		{
			System.out.print("IOException : " + e.getMessage());
		}
     }

	/*
		@authors				Ian McKay
	
		Input:					Nothing
	
		Proccessing:			Writes the Flight arrayList to the flights.txt file
	
        Output:					Nothing
	*/

    public static void writeToFlights(ArrayList<Flight> flightList)
	{
		try
        	{
		       PrintWriter outFile = new PrintWriter("Flights.txt");
		       String currentLine = "";
		       for (int i = 0; i <flightList.size(); i++)
		       {
				   currentLine = flightList.get(i).number() + "," + flightList.get(i).airportDepart() + "," + flightList.get(i).airportArrival() +
				   				"," + flightList.get(i).timeDepart() + "," + flightList.get(i).timeArrival() +"," + flightList.get(i).days() + 
				   				"," + flightList.get(i).dateDepart() + "," + flightList.get(i).dateArrival();
			       //prints each line to the text file
			       outFile.println(currentLine);
		       }
		       outFile.close();
		}
		catch(IOException e)
        	{
            		System.out.print("IOException : " + e.getMessage());
		}
     }
}
