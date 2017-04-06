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

    public boolean add(String airport,String airportCode)
    {
    	return true;
    }
}