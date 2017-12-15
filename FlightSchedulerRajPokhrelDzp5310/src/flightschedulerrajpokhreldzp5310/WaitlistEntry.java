/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Raj
 */
public class WaitlistEntry 
{
    private String flight;
    private String customer;
    private String date;
    private Date timestamp;
    
    /**
     * 
     * @param flight
     * @param customer
     * @param date
     * @param timestamp 
     */
    public WaitlistEntry(String flight, String customer, String date, Date timestamp) 
    {
        this.flight = flight;
        this.customer = customer;
        this.date = date;
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return 
     */
    public String getFlight() 
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
     * @return 
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
    /**
     * 
     * @return 
     */
    public String getDate()
    {
        return date;
    }

    /**
     * 
     * @param date 
     */
    public void setDate(String date) 
    {
        this.date = date;
    }

    /**
     * 
     * @return 
     */
    public Date getTimestamp()
    {
        return timestamp;
    }

    /**
     * 
     * @param timestamp 
     */
    public void setTimestamp(Time timestamp)
    {
        this.timestamp = timestamp;
    }
}
