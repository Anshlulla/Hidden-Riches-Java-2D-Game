package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import main.GamePanel;

public class SQL {
	
	GamePanel gp;
	DecimalFormat decimalFormat = new DecimalFormat("#0.00");
	
	public SQL(GamePanel gp)
	{
		this.gp = gp;
	}
	
	
	public ArrayList<String> viewLeaderboardName()
	{
		
		ArrayList<String> outputName = new ArrayList<>();
		
		try {
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "002cd592");
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from leaderboard order by playerTime asc limit 5");
			
			
			while(rs.next()) 
			{ 
				outputName.add(rs.getString(1));
			}
			
			
			con.close();
			stmt.close();
			rs.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return outputName;
		
	}
	
	public ArrayList<String> viewLeaderboardTime()
	{
		
		ArrayList<String> outputTime = new ArrayList<>();
		
		try {
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "002cd592");
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from leaderboard order by playerTime asc limit 5");
			
			
			while(rs.next()) 
			{ 
				outputTime.add(decimalFormat.format(rs.getDouble(2)));
			}
			
			
			con.close();
			stmt.close();
			rs.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return outputTime;
		
	}
	
	public void updateLeaderboard(String playerName, double playerTime) {
	    try {
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "002cd592");
	        
	        PreparedStatement pstmt = con.prepareStatement("INSERT INTO leaderboard (playerName, playerTime) VALUES (?, ?) ON DUPLICATE KEY UPDATE playerTime = IF(VALUES(playerTime) < playerTime, VALUES(playerTime), playerTime)");
	        
	        pstmt.setString(1, playerName);
	        pstmt.setDouble(2, playerTime);
	        
	        pstmt.executeUpdate();
	        
	        pstmt.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



}
