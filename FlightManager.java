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
			if(filesStart())
			{
				programLauncher();
			}
			restart();
	}
	/*
		@authors 		Adam Swayne
		Input: 			No Input
		Processing:		loads files into memory
        Output:			returns true when files have been loaded into memory and validated	
	*/
	public static boolean filesStart()
	{
		try
		{
			File airportsFile    = 	new File("airports.txt");
			File flightsFile     =  new File("flights.txt");
			if(validateFiles(airportsFile) && validateFiles(flightsFile))
			{
				readInFiles();
				return true;
			}
			else return false;
		}
		catch(NumberFormatException e) {JOptionPane.showMessageDialog(null,"Error, text file not found","Error",JOptionPane.PLAIN_MESSAGE);}
		catch(IOException ex){JOptionPane.showMessageDialog(null,"Error, text file not found","Error",JOptionPane.PLAIN_MESSAGE);}
		return false;
	}
	/*
		@authors 		Ian McKay
		Input: 			No Input
		Processing:		Gets input from menus() and then send it to the method chosen by the user
        Output:			No Output		
	*/
	public static void programLauncher()
	{
		String[] args;
		args=menus();
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
			default:            JOptionPane.showMessageDialog(null,"Error, selection not found","Error",JOptionPane.PLAIN_MESSAGE);
		}
	}
	/*
		@authors 		Ian McKay
		Input: 			No Input
		Processing:		Asks user if they want to start the program again
        Output:			No Output		
	*/
	public static void restart()
	{
		int n = JOptionPane.showConfirmDialog(null,"Would you like to use another task.","Flight Manager",JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) programLauncher();
		else System.exit(0);
	}
	public static void exit()
	{
		int n = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?.","Flight Manager",JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) System.exit(0);
		else restart();
	}
	/*
		@authors 		Ian McKay
		Input: 			No Input
		Processing:		Takes input from the user, sends it to validation for each input
        Output:			String array containing all the inputs		
	*/
	public static String[] menus()
	{
		try {
			String[] selection;
			String[] airportCode2;
			String[] airport2;
			String[] choices = { "Add Airport", "Edit Airport", "Delete Airport", "Add Flight", "Edit Flight", "Delete Flight", "Search Flight", "Search Flight by Date" };
			String input = (String) JOptionPane.showInputDialog(null, "Choose a program to launch", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, choices,choices[0]);
			
			ArrayList<String> airports = new ArrayList<String>();
			for (int i=0;i<airport.size();i++) airports.add(airport.get(i).name());
			String[] airportNames= airports.toArray(new String[airports.size()]);
			
			ArrayList<String> airportCodes = new ArrayList<String>();
			for (int i=0;i<airport.size();i++) airportCodes.add(airport.get(i).code()+" ("+airport.get(i).name()+")");
			String[] airportCode= airportCodes.toArray(new String[airportCodes.size()]);
			
			ArrayList<String> flights = new ArrayList<String>();
			for (int i=0;i<flight.size();i++) flights.add("Flight "+flight.get(i).number()+" ("+flight.get(i).airportDepart()+"-"+flight.get(i).airportArrival()+")");
			String[] flightNums= flights.toArray(new String[flights.size()]);
			
			switch(input)
			{
				case "Add Airport":     
					selection = new String[3];
					selection[0]="AA";
					selection[1] = menuInput("Please enter an airport name.",selection,1);
					selection[2] = menuInput("Please enter a three letter airport code.",selection,2);
					break;
					
				case "Edit Airport":
					selection = new String[3];
					selection[0]="EA";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Choose an airport to edit.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportCode,airportCode[0]);
					for(int i=0; i<airport.size();i++)if(selection[1].equals(airportCode[i]))selection[1]=airport.get(i).code();
					selection[2] = menuInput("Please enter new airport name",selection,2);
					break;
				case "Delete Airport":
					selection = new String[2];
					selection[0]="DA";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Choose an airport to delete.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportCode,airportCode[0]);
					for(int i=0; i<airport.size();i++)if(selection[1].equals(airportCode[i]))selection[1]=airport.get(i).code();
					break;

				case "Add Flight":
					selection = new String[9];
					selection[0]="AF";
					selection[1] = menuInput("Please enter flight number.",selection,1);
					selection[2] =(String) JOptionPane.showInputDialog(null, "Please choose the airport of departure.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportCode,airportCode[0]);
					for(int i=0; i<airport.size();i++)if(selection[2].equals(airportCode[i]))selection[2]=airport.get(i).code();
					
					airportCodes.remove(selection[2]);
					airportCode2= airportCodes.toArray(new String[airportCodes.size()]);
					
					selection[3] = (String) JOptionPane.showInputDialog(null, "Please choose the airport of arrival.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportCode2,airportCode2[0]);
					for(int i=0; i<airport.size();i++)if(selection[3].equals(airportCode[i]))selection[3]=airport.get(i).code();
					selection[4] = menuInput("Please enter departing time.",selection,4);
					selection[5] = menuInput("Please enter arival time.",selection,5);
					selection[6] = menuInput("Please enter week schedule.",selection,6);
					selection[7] = menuInput("Please enter starting flight date.",selection,7);
					selection[8] = menuInput("Please enter last flight date.",selection,8);
					break;
				case "Edit Flight":          
					selection = new String[5];
					selection[0]="EF";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Choose a flight to edit.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, flightNums,flightNums[0]);
					for(int i=0; i<flight.size();i++)if(selection[1].equals(flightNums[i]))selection[1]=flight.get(i).number();
					selection[2] = menuInput("Please enter week schedule.",selection,2);
					selection[3] = menuInput("Please enter starting flight date.",selection,3);
					selection[4] = menuInput("Please enter last flight date.",selection,4);
					break;

				case "Delete Flight":        
					selection = new String[2];
					selection[0]="DF";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Choose a flight to edit.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, flightNums,flightNums[0]);
					for(int i=0; i<flight.size();i++)if(selection[1].equals(flightNums[i]))selection[1]=flight.get(i).number();
					break;

				case "Search Flight":      
					selection = new String[3];
					selection[0]="SF";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Please select the airport of departure.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportNames,airportNames[0]);
					
					airports.remove(selection[1]);
					airport2= airports.toArray(new String[airports.size()]);
					
					selection[2] = (String) JOptionPane.showInputDialog(null, "Please select the airport of arrival.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airport2,airport2[0]);
					break;

				case "Search Flight by Date":       
					selection = new String[4];
					selection[0]="SD";
					selection[1] = (String) JOptionPane.showInputDialog(null, "Please select the airport of departure.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airportNames,airportNames[0]);
					
					airports.remove(selection[1]);
					airport2= airports.toArray(new String[airports.size()]);
					
					selection[2] = (String) JOptionPane.showInputDialog(null, "Please select the airport of arrival.", "Flight Manager", JOptionPane.QUESTION_MESSAGE, null, airport2,airport2[0]);
					selection[3] = menuInput("Please enter the date for the flights you wish to search.",selection,3);
					break;

				default:            
				{
					JOptionPane.showMessageDialog(null,"Error, selection does not exist","Error",JOptionPane.PLAIN_MESSAGE);
					selection = new String[0];
				}           
			}
			return selection;
		}
		catch(NullPointerException e) {exit();}
		String[] nothing = new String[0];
		return nothing;
	}
	public static String menuInput(String messege, String[] args, int num)
	{
		try {
			boolean inputRecieved=false;
			args[num]= JOptionPane.showInputDialog(messege);
			while(!inputRecieved){
				if(args[num].equals("") || !validation(args,num))args[num]= JOptionPane.showInputDialog(messege);
				else inputRecieved=true;
			}
			return args[num];
		} 
		catch(NullPointerException e) {restart();}
		return args[num];
	}
	/*
		@authors 		Ian McKay
	
		Input: 			String [] args - String array containing all the elements inputted by the user so far, 
						validationNum- the number that the current input is at, to be tested
	
		Processing:		
	
        Output:			boolean isValid		
*/
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
					System.out.print(args[1]);
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
					isValid=checkDays(args[2]);
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

					if(!(endD.after(startD))) JOptionPane.showMessageDialog(null,"Error, End date appears to be before the start date","Error",JOptionPane.PLAIN_MESSAGE);
					else return true;
				}
				break;
            

            default:            
            JOptionPane.showMessageDialog(null,"Error, invalid command supplied.","Error",JOptionPane.PLAIN_MESSAGE); 
            break;
        }
    return isValid;
    }
	/*
		@authors 		Adam Swayne
	
		Input: 			String of the sequence of days that the flight is functioning
	
		Processing:		checks if the days follow the pattern required
	
        Output:			boolean validation		
*/
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
/*
		@authors 		Adam Swayne && Ian McKay
	
		Input: 			a String of elemtents that contain the date
	
		Processing:		Whether or not the date is valid
	
        Output:			boolean if date is valid		
*/
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
		
		if (date.before(present))
		{
			JOptionPane.showMessageDialog(null,"Error, date cannot be before current date","Error",JOptionPane.PLAIN_MESSAGE);
			return false;
		} 
        if(ddInt == 0 || mmInt == 0 || yyInt == 0)
		{
			JOptionPane.showMessageDialog(null,"Error, niether day, month, or year can be 0.","Error",JOptionPane.PLAIN_MESSAGE);
			dateIsValid =false;
		}
        else if(mmInt > 12)
		{
			JOptionPane.showMessageDialog(null,"Error, month cannot be greater than 12.","Error",JOptionPane.PLAIN_MESSAGE);
			dateIsValid =false;
		}        
        else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))
		{
			dateIsValid =true;
		}        
        else if(ddInt > daysArray[mmInt -1])
		{
			JOptionPane.showMessageDialog(null,"Error, the day cannot be that high of a number.","Error",JOptionPane.PLAIN_MESSAGE);
			dateIsValid =false;
		}
        return dateIsValid;
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
