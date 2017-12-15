/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Raj
 */
public class BookingEntry 
{
    private String flight;
    private String day;
    private String customer;
    
    /**
     * 
     * @param customer
     * @param day
     * @param flight 
     */
    public BookingEntry(String customer, String day, String flight) 
    {
        this.flight = flight;
        this.day = day;
        this.customer = customer;
    }

    /**
     * 
     * @return flightentry
     */
    public  String getFlight()
    {
        return flight;
    }

    /**
     * 
     * @param flight 
     */
    public void setFlight(String flight) 
    {
        this.flight = flight;
    }

    /**
     * 
     * @return day for booking entry
     */
    public String getDay()
    {
        return day;
    }

    /**
     * 
     * @param day 
     */
    public void setDay(String day) 
    {
        this.day = day;
    }

    /**
     * 
     * @return customer for bookingEntry
     */
    public String getCustomer() 
    {
        return customer;
    }

    /**
     * 
     * @param customer 
     */
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
}
