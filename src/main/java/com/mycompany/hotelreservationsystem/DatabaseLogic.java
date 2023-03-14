/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hotelreservationsystem;
import java.lang.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author RIVERSIDEKOLK-20
 */
public class DatabaseLogic {
    /*------------------ 'test' is the name of the database, 'root' is the default username and we have to put our mySQL password inplace of password ---------------*/
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String user = "root";
    public static String passowrd = "iamAtin@root";
    
    /*------------------ Query to Create Required Tables ---------------*/
    public static final String QUERY1 = "create table IF NOT EXISTS staff ( staff_id int not null, password varchar(50) not null, staff_fname varchar(25), staff_lname varchar(25), address varchar(100), contact varchar(12), gender varchar(10), dob date, post varchar(20), salary int, primary key(staff_id));";
    public static final String QUERY2 = "create table IF NOT EXISTS guest ( user_name varchar(20) not null, password varchar(50) not null, fname varchar(25), lname varchar(25), dob date, address varchar(100), gender varchar(50), email varchar(50), contact varchar(12), primary key(user_name));";
    public static final String QUERY3 = "create table IF NOT EXISTS room_details( room_no varchar(20) not null, room_type varchar(10), no_of_bed int, cost_per_night int, primary key(room_no));";
    public static final String QUERY4 = "create table IF NOT EXISTS payment_details( payment_id int not null auto_increment, payment_amount int, payment_status varchar(10), user_name varchar(20), primary key(payment_id), foreign key(user_name) references guest(user_name));";
    public static final String QUERY5 = "create table IF NOT EXISTS booking_details( booking_id int not null auto_increment, user_name varchar(20), room_no varchar(20), check_in_date date, check_out_date date, fooding varchar(5), primary key(booking_id), foreign key(user_name) references guest(user_name), foreign key(room_no) references room_details(room_no));";
    
    
    /*------------------ Function to connect to Database and perform required Operations ---------------*/
    public static void initDb() throws Exception{
        /*------------------ Loading MySQL Driver ---------------*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);
        System.out.println("Connection Successfull");
        
        /*------------------ Execute Statement for table creation ---------------*/
        Statement stmt = con.createStatement();
        stmt.executeUpdate(QUERY1);
        stmt.executeUpdate(QUERY2);
        stmt.executeUpdate(QUERY3);
        stmt.executeUpdate(QUERY4);
        stmt.executeUpdate(QUERY5);
        
        /*------------------ Check if Data is already present in staff table or not. If data present rs will return 1 else 0 ---------------*/
        ResultSet rs = stmt.executeQuery("select exists(select 1 from staff);");
        rs.next();
        int count = rs.getInt(1);
        
        if (count == 0){
            /*------------------ Execute Statement for data insertion ---------------*/
            stmt.executeUpdate("insert into staff values(101, 'staff101', 'Mohit', 'Agarwal', 'Kundghat, Delhi, India', '9732236574', 'm', '1989-12-04', 'Manager', 80000),(102, 'staff102', 'Priya', 'Singh', 'Tajpur, Hyderabad, India', '6362915089', 'f', '1992-10-04', 'Receptionist', 50000),(103, 'staff103', 'Raju', 'Srivastava', 'Malhari, Andhrapradesh, India', '6297549863', 'm', '1978-09-20', 'Ward Boy', 25000),(104, 'staff104', 'Rehana', 'Poddar', 'Marhat, West Bengal, India', '6290548976', 'f', '1991-10-12', 'Cook', 40000), (105, 'staff105', 'Reema', 'Paul', 'Maber, Delhi, India', '6978456932', 'f', '1979-05-22', 'Receptionist', 45000), (106, 'staff106', 'Nilam', 'Kundu', 'Mehanatganj, Rajasthan, India', '9679548931', 'm', '1982-07-08', 'Cook', 38000);");
        }
        
        /*------------------ Check if Data is already present in room_details table or not. If data present rs will return 1 else 0 ---------------*/
        rs = stmt.executeQuery("select exists(select 1 from room_details);");
        rs.next();
        int count2 = rs.getInt(1);
        
        if (count2 == 0){
            /*------------------ Execute Statement for data insertion ---------------*/
            stmt.executeUpdate("insert into room_details values ('1A','single', 1, 500), ('1B','double', 2, 900), ('2A','single', 1, 600), ('2B','double', 2, 1000), ('3A', 'single', 1, 800), ('3B', 'double', 2, 1400), ('3C', 'triple', 3, 1900), ('4A', 'single', 1, 1100), ('4B', 'double', 2, 1900), ('4C', 'triple', 3, 2800), ('4D', 'quad', 4, 3700);");
        }
        
        stmt.close();
        con.close();
    }
    
    /*------------------ Function to check if Staff is valid or not ---------------*/
    public static boolean isValidStaff(int staffId, String pass)throws Exception{
        /*------------------ Loading MySQL Driver ---------------*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);
        
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select exists(select * from staff where staff_id ="+staffId+" and password= '"+pass+"' );");
        rs.next();
        
        int count = rs.getInt(1);
        
        if (count == 1){
            return true;
        }
        
        stmt.close();
        con.close();
        return false;
    }
    
    /*------------------ Function to check if Guest is valid or not ---------------*/
    public static boolean isValidUser(String username, String pass)throws Exception{
        /*------------------ Loading MySQL Driver ---------------*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);
        
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select exists(select * from guest where user_name = '"+username+"' and password= '"+pass+"' );");
        rs.next();
        
        int count = rs.getInt(1);
        
        if (count == 1){
            return true;
        }
        
        stmt.close();
        con.close();
        return false;
    }
    
    /*------------------ Function to check if Guest is Already present in database or not ---------------*/
    public static boolean isUserAlreadyPresent(String fname, String lname, String contact)throws Exception{
        /*------------------ Loading MySQL Driver ---------------*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);
        
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select exists(select * from guest where fname = '"+fname+"' and lname= '"+lname+"'and contact= '"+contact+"' );");
        rs.next();
        
        int count = rs.getInt(1);
        
        if (count == 1){
            return true;
        }
        
        stmt.close();
        con.close();
        return false;
    }
    
    /*------------------ Function to inset data of user into database ---------------*/
    public static boolean enterUsertoDb(String username, String pass, String fname, String lname, String dob, String gender, String email, String address, String contact) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);

        Statement stmt = con.createStatement();
        try{
            //System.err.println(username+" "+pass+" "+fname+" "+lname+" "+dob+" "+gender+" "+email+" "+address+" "+contact);
            stmt.executeUpdate("insert into guest values( '"+username+"', '"+pass+"', '"+fname+"', '"+lname+"', '"+dob+"', '"+address+"', '"+gender+"', '"+email+"', '"+contact+"');");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            stmt.close();
            con.close();
        }    
        
    }
    
    
    public static String getNameOfUser(String username) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);;
        Statement stmt = con.createStatement();
        
        String name;
        try{
            //System.err.println(username+" "+pass+" "+fname+" "+lname+" "+dob+" "+gender+" "+email+" "+address+" "+contact);
            ResultSet rs = stmt.executeQuery("select fname from guest where user_name = '"+username+"';");
            rs.next();
            
            name = rs.getString("fname");
            
            return name;
            
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            stmt.close();
            con.close();
        } 
        
    }
    
    
    public static void returnAvailableRooms() throws Exception{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, passowrd);;
        
        Statement stmt = con.createStatement();
        
        try{
            ResultSet rs = stmt.executeQuery("select * from room_details where room_no not in (select bd.room_no from booking_details bd where check_out_date > curdate());");

            while(rs.next()){
                String roomNo = rs.getString("room_no");
                String roomType = rs.getString("room_type");
                int noOfBed = rs.getInt("no_of_bed");
                int costPerNight = rs.getInt("cost_per_night");
                
                System.out.println(roomNo + " " + roomType + " " + noOfBed +" " + costPerNight+" ");
            }

        }catch(Exception e){
            System.out.println(e);
        }
        stmt.close();
        con.close();
        
    }
}
