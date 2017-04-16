import javax.swing.*;
import java.io.*;
import java.util.*;

public class Airport
{
     String name;
     String code;

    public Airport(String n, String c)
    {
    	name = n;
    	code = c;
    }

    public String name()
    {
    	return name;
    }

    public String code()
    {
    	return code;
    }  

     /*
		@authors 		Ibrahim Alaydi
	
		Input: 			command-line arguments supplied
    
		Output:			validating airportname and code and checking of it exists, also allowing user to add a new airport
	*/	
	
    public static void add(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String airport,String airportCodeIn)
    {
		boolean alreadyExists=false;
		
		//if both return true, loop through array list and check if airport or airportcode already exists
		for(int i=0;i<airportList.size();i++) //looping through the airport list 
			if(airportList.get(i).name.equalsIgnoreCase(airport) || (airportList.get(i).code.equalsIgnoreCase(airportCodeIn))) 
				alreadyExists=true;
			
		if(!alreadyExists)
		{
			airportList.add(new Airport(airport, airportCodeIn));
			JOptionPane.showMessageDialog(null,"The airport was added successfully");
		}
		else
			JOptionPane.showMessageDialog(null,"Error, airport/airport code already exists");
		sortFiles(airportList, flightList);
    }
	
	/*
		@authors 		Ibrahim Alaydi
	
		Input: 			command-line arguments
    
		Output: 		error messages or new airport is written to the file
	*/
	
	
	public static void edit(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String airportCode,String airport)
	{
		boolean isFound = false;

		for(int i=0;i<airportList.size() && !isFound;i++) //loops through airportList
		{
			if(airportList.get(i).name.equalsIgnoreCase(airport) && !(airportList.get(i).code.equalsIgnoreCase(airportCode)))
			{
				airportList.remove(i);
			}
			if(airportList.get(i).code.equalsIgnoreCase(airportCode))
			{
				airportList.set(i, new Airport(airport, airportCode));
				JOptionPane.showMessageDialog(null,"Airport successfully edited");
			}		
		}
		sortFiles(airportList, flightList);
	}

     /*
		@authors 		Matthew Blake
		
		Input: 				airport code
		
		Proccessing:		checks the arrayList and removes the airport corresponding to the code entered, then deletes all flights that is related to that airport
		
		Output:				Nothing
	*/	

	public static void delete(ArrayList<Airport> airportList, ArrayList<Flight> flightList, String airportCode)
	{
		
		boolean isFound =false;
		boolean flightToDelete=false;
		int rowFound=0;

		for(int i = 0;i < airportList.size();i++)
		{
			if(airportList.get(i).code.equalsIgnoreCase(airportCode))
			{
				isFound=true;
				rowFound=i;
			}
		}
		if(isFound)
		{
			airportList.remove(rowFound);
			for (int i = 0; i <flightList.size(); i++)
			{
				if(flightList.get(i).airportDepart().equalsIgnoreCase(airportCode) || flightList.get(i).airportArrival().equalsIgnoreCase(airportCode))
				{
					rowFound=i;
					flightToDelete=true;
				}
				if(flightToDelete)
				{
					flightList.remove(rowFound);
					flightToDelete=false;
					i--;
				}
			}
			JOptionPane.showMessageDialog(null,"Airport " + airportCode + " deleted successfully");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Error, Airport Code " + airportCode + " not found in Airports.txt");
		}
		sortFiles(airportList, flightList);
	}

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
				if(flightList.get(i).number().compareToIgnoreCase(flightList.get(j).number()) > 0)
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
			JOptionPane.showMessageDialog(null,"IOException : " + e.getMessage());
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
            		JOptionPane.showMessageDialog(null,"IOException : " + e.getMessage());
		}
     }
}



