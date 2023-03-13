/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hotelreservationsystem;
import java.lang.*;
import java.sql.*;

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
    public static final String QUERY1 = "create table IF NOT EXISTS staff ( staff_id int not null, password varchar(50) not null, staff_fname varchar(25), staff_lname varchar(25), address varchar(100), contact varchar(12), gender varchar(2), dob date, post varchar(20), salary int, primary key(staff_id));";
    public static final String QUERY2 = "create table IF NOT EXISTS guest ( user_name varchar(20) not null, password varchar(50) not null, fname varchar(25), lname varchar(25), dob date, address varchar(100), gender varchar(2), email varchar(50), contact varchar(12), primary key(user_name));";
    public static final String QUERY3 = "create table IF NOT EXISTS room_details( room_no int not null auto_increment, room_type varchar(10), no_of_bed int, cost_per_night int, primary key(room_no));";
    public static final String QUERY4 = "create table IF NOT EXISTS payment_details( payment_id int not null auto_increment, payment_amount int, payment_status varchar(10), user_name varchar(20), primary key(payment_id), foreign key(user_name) references guest(user_name));";
    public static final String QUERY5 = "create table IF NOT EXISTS booking_details( booking_id int not null auto_increment, user_name varchar(20), room_no int, check_in_date date, check_out_date date, fooding varchar(5), payment_id int, primary key(booking_id), foreign key(user_name) references guest(user_name), foreign key(room_no) references room_details(room_no));";
    
    
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
    public static void enterUsertoDb(){
        
    }
}
