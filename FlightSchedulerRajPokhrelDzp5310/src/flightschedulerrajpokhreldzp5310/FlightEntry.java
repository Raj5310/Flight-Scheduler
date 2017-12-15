/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.util.Objects;
/**
 *
 * @author Raj
 */
public class FlightEntry 
{
     //PROPERTIES
    private String name;
    private int numberSeats;

    /**
     * 
     * @param name
     * @param numberSeats 
     */
    public FlightEntry(String name, int numberSeats) 
    {
        this.name = name;
        this.numberSeats = numberSeats;
    }
    
    public String getName()
    {
        return name;
    }
    
    /**
     * 
     * @param name 
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */
    public int getNumberSeats() 
    {
        return numberSeats;
    }
    
    /**
     * 
     * @param numberSeats 
     */
    public void setNumberSeats(int numberSeats) 
    {
        this.numberSeats = numberSeats;
    }   
}
