import javax.swing.*;
import java.io.*;
import java.util.*;

public class Flight
{
	String flightNum;
	String airportDepart;
	String airportArrival;
	String timeDepart;
	String timeArrival;
	String days;
	String dateDepart;
	String dateArrival;

	public Flight(String fn, String ad, String aa, String td, String ta, String d, String dd, String da)
	{
		flightNum = fn;
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
		return flightNum;
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

}