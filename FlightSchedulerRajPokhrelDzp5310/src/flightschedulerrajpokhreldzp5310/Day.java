/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raj
 */
public class Day 
{ 
    /**
     * 
     * @param date 
     */
    public static void addDate(String date)
    {
         PreparedStatement SQLStmt;
        try
            {
                SQLStmt = DataBase.getConnection().prepareStatement("INSERT INTO DATES VALUES(?)");
                SQLStmt.setString(1,date);
                SQLStmt.executeUpdate();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            } 
    }
 
    /**
     * 
     * @return 
     */
    public static ArrayList<String> getdates() 
    {
         ArrayList<String> dates = new ArrayList();
          try
        {        
            String sql ="SELECT * FROM DATES";
            PreparedStatement stat = DataBase.getConnection().prepareStatement(sql);
            ResultSet set = stat.executeQuery();
           
           while(set.next())
           {
              dates.add(set.getString("DAY"));
           }                 
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return dates;
    }
}
