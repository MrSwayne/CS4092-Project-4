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
		try
		{
			File airportsFile    = 	new File("airports.txt");
			File flightsFile     =  new File("flights.txt");

			if(validateFiles(airportsFile) && validateFiles(flightsFile))
			{
				for(int i = 0;i < args.length;i++)
					args[i] = args[i].toUpperCase();

				readInFiles();
				args=menus();
				if(validation(args))
				{
					switch(args[0].toUpperCase())
					{
						case "AA":       	Airport.add(airport, flight, args[1],args[2]); break;
						case "EA":       	Airport.edit(airport, flight, args[1],args[2]); break;
						case "DA":       	Airport.delete(airport, flight, args[1]); break;
						case "AF":			Flight.add(airport, flight, args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]); break;
						case "EF":       	Flight.edit(airport, flight, args[1], args[2], args[3], args[4]); break;
						case "DF":       	Flight.delete(airport, flight, args[1]); break;
						case "SF":       	Flight.searchFlight(airport, flight, args[1], args[2]); break;
						case "SD":      	Flight.searchDate(airport, flight, args[1], args[2], args[3]); break;
						default:        	
						{
							System.out.println("Error, invalid command supplied.");	
						}			
					}
				}
			}

		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Error, invalid command supplied.");
			displayInstructions();
		}
		catch(NumberFormatException e)
		{

		}

	}
	public static String[] menus()
	{
		String[] choices = { "Add Airport", "Edit Airport", "Delete Airport", "Add Flight", "Edit Flight", "Delete Flight", "Search Flight", "Search Flight by Date" };
		String input = (String) JOptionPane.showInputDialog(null, "Choose a program to launch", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, choices,choices[1]);
		String[] selection;
		System.out.println(input);
		switch(input)
		{
			case "Add Airport":     
				selection = new String[3];
				selection[0]="AA";
				selection[1] = menuInput("Please enter an airport name");
				selection[2] = menuInput("Please enter a three letter airport code");
				break;
				
			case "Edit Airport":
				selection = new String[3];
				selection[0]="EA";
				selection[1] = menuInput("Please select an airport code");
				selection[2] = menuInput("Please enter new airport name");
				break;
			case "Delete Airpor":
				selection = new String[2];
				selection[0]="DA";
				selection[1] = menuInput("Please the airport code to be deleted");
				break;

			case "Add Flight":
				selection = new String[9];
				selection[0]="AF";
				selection[1] = menuInput("Please enter flight number");
				selection[2] = menuInput("Please enter departing airport");
				selection[3] = menuInput("Please enter arrival airport");
				selection[4] = menuInput("Please enter departing time");
				selection[5] = menuInput("Please enter arival time");
				selection[6] = menuInput("Please enter week schedule");
				selection[7] = menuInput("Please enter starting flight date");
				selection[8] = menuInput("Please enter last flight date");
				break;


			case "Edit Flight":          
				selection = new String[5];
				selection[0]="EF";
				selection[1] = menuInput("Please enter flight number");
				selection[2] = menuInput("Please enter week schedule");
				selection[3] = menuInput("Please enter starting flight date");
				selection[4] = menuInput("Please enter last flight date");
				break;


			case "Delete Flight":        
				selection = new String[2];
				selection[0]="DF";
				selection[1] = menuInput("Please enter flight number");
				break;

			case "Search Flight":      
				selection = new String[3];
				selection[0]="SF";
				selection[1] = menuInput("Please enter departing airport");
				selection[2] = menuInput("Please enter arival time");
				break;

			case "Search Flight by Date":       
				selection = new String[4];
				selection[0]="SD";
				selection[1] = menuInput("Please enter departing airport");
				selection[2] = menuInput("Please enter arival time");
				selection[3] = menuInput("Please enter starting flight date");
				break;

			default:            
			{
				System.out.println("Error, invalid command supplied."); 
				selection = new String[0];
			}           
		}
		return selection;
	}
	public static String menuInput(String messege)
	{
		String input;
		boolean inputRecieved=false;
		input= JOptionPane.showInputDialog(messege);
		while(!inputRecieved){
			if(input.equals(null) || input.equals(""))input= JOptionPane.showInputDialog(messege);
			else inputRecieved=true;
		}
		return input;
	}
	public static boolean validation(String[] args, int validationNum)
    {
        
        boolean isValid = false;
        String input;
        int airportSize = airport.size();
        boolean found=false;
		String[] startDates = new String[3];
		String[] endDates = new String[3];
        String pattern="\\d\\d\\d\\d";
        
        switch(validationNum)
        {
            case 0: return true;
            case 1:

            if (args[0].equals("AA"))
            {               
                found=false;
                
                for (int i=0;i<airportSize;i++)
                {
                    if(airport.get(i).name.equalsIgnoreCase(args[1]))
                    {
                        found=true;
                    }
                }
                if (found) 
                {
                    JOptionPane.showMessageDialog(null,"Airport name already exists, try again","Error",JOptionPane.PLAIN_MESSAGE);
                    return false;
                }
                else return true;
            }
			
            else if(args[0].equals("AF"))
            {
                if(args[1].length() < 3 || !(args[1].substring(0,2).matches("[a-zA-Z]{2}") && args[1].substring(2,args[1].length()).matches("\\d{1,}")) )
                    JOptionPane.showMessageDialog(null,"Error, flight number should be 2 Letters followed by a string of numbers e.g AE1240","Error",JOptionPane.PLAIN_MESSAGE);
                else if(args[1].length() > 2)
                    return true;
            }
			
            else return true;
			
            break;

            
            case 2:  
                if(args[0].equals("AA"))
                {
                    if(args[2].matches("[a-zA-Z]{3}"))
                    {
                        
                        for(int i=0;i<airportSize;i++)
                        {                           //looping through the airport list 
                            if(airport.get(i).code.equalsIgnoreCase(args[2])){
                            JOptionPane.showMessageDialog(null,"Airport code already exists, try again","Error",JOptionPane.PLAIN_MESSAGE);
                            return false;
                            }
                        }
                        return true;
                    }
                    System.out.println(args[2]);
                    return false;
                }
				else if (args[0].equals("EA"))
				{               
					found=false;
					for (int i=0;i<airportSize;i++)		if(airport.get(i).name.equalsIgnoreCase(args[2]))	found=true;
					if (found) 
					{
						JOptionPane.showMessageDialog(null,"Airport name already registered, try again","Error",JOptionPane.PLAIN_MESSAGE);
						return false;
					}
					else return true;
				}
				else if (args[0].equals("EF"))
				{
					return checkDays(args[2]);
				}
                break;
                
            case 3:
				if (args[0].equals("EF")||(args[0].equals("SD")))
				{
					startDates = args[3].split("/");
                     return checkDate(startDates);
				}	
				break;
				
				
			case 4: 
				if (args[0].equals("AF"))
				{
					input = args[4];
					
					if(input != null && input.length() != 0)
					{
						if(input.matches(pattern))
						{
							isValid = true;
						}
					}
					int inputNum=Integer.parseInt(input);
					if(inputNum>=2400 ||inputNum%100>=60)
					{
						isValid =false;
					}
					if (isValid) return true;
				}
				else if (args[0].equals("EF"))
				{
					
					startDates = args[3].split("/");
					endDates = args[4].split("/");
					
                    if(checkDate(endDates))
					{
						
						Calendar startD = Calendar.getInstance();
						Calendar endD = Calendar.getInstance();

						startD.set(Integer.parseInt(startDates[2]), Integer.parseInt(startDates[1]), Integer.parseInt(startDates[0]));
						endD.set(Integer.parseInt(endDates[2]), Integer.parseInt(endDates[1]), Integer.parseInt(endDates[0]));

						if(!(endD.after(startD)))
							JOptionPane.showMessageDialog(null,"Error, End date appears to be before the start date","Error",JOptionPane.PLAIN_MESSAGE);
						else
							return true;
					}
				}
				break;
			
			case 5: 
				input = args[4];
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				int inputNum=Integer.parseInt(input);
				if(inputNum>=2400 ||inputNum%100>=60)
				{
					isValid = false;
				}
			
				break;
			
			case 6: 
				return checkDays(args[6]);
				
			case 7: 
				startDates = args[3].split("/");
                return checkDate(startDates);
				
			case 8: 
			
				startDates = args[7].split("/");
				endDates = args[8].split("/");
				
				if(checkDate(endDates))
				{
					
					Calendar startD = Calendar.getInstance();
					Calendar endD = Calendar.getInstance();

					startD.set(Integer.parseInt(startDates[2]), Integer.parseInt(startDates[1]), Integer.parseInt(startDates[0]));
					endD.set(Integer.parseInt(endDates[2]), Integer.parseInt(endDates[1]), Integer.parseInt(endDates[0]));

					if(!(endD.after(startD)))
						JOptionPane.showMessageDialog(null,"Error, End date appears to be before the start date","Error",JOptionPane.PLAIN_MESSAGE);
					else
						return true;
				}
				break;
            

            default:            
            JOptionPane.showMessageDialog(null,"Error, invalid command supplied.","Error",JOptionPane.PLAIN_MESSAGE); 
            break;
        }
    return isValid;
    }
	
	public static boolean checkDays(String input)
	{
		String days = "MTWTFSS";
		String dash = "-";
		int countInOrder=0;
		int matchCount=0;
		boolean matchThisPos=false;
		boolean validDays=false;
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
			if(matchCount==input.length()) validDays= true;
		}
		if(!validDays) JOptionPane.showMessageDialog(null,"Error, invalid days inputted","Error",JOptionPane.PLAIN_MESSAGE);
		else if(input.equals("-------"))
		{
			validDays = false;
			JOptionPane.showMessageDialog(null,"Error, flight days cannot be empty i.e '-------'","Error",JOptionPane.PLAIN_MESSAGE);
		} 
		else return true;
		return false;
	}

    public static boolean checkDate(String[] dateElements)
    {

        int ddInt, mmInt, yyInt;
        int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
        boolean dateIsValid = true;
        
        ddInt= Integer.parseInt(dateElements[0]);
        mmInt= Integer.parseInt(dateElements[1]);
        yyInt= Integer.parseInt(dateElements[2]);
		
		Calendar present = Calendar.getInstance();
		Calendar date = Calendar.getInstance();
		date.set(yyInt,mmInt,ddInt);
		
		if (date.before(present)) return false;
        
        if(ddInt == 0 || mmInt == 0 || yyInt == 0)                                                              dateIsValid =false;
        
        else if(mmInt > 12)                                                                                     dateIsValid =false;
        
        else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))      dateIsValid =true;
        
        else if(ddInt > daysArray[mmInt -1])                                                                    dateIsValid =false;


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
