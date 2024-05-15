package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import object.OBJ_Key;

public class UI 
{
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_60B, arial_30B, arial_40B, arial_25B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	ArrayList<String> outputName, outputTime;
	int leaderboardCounter;
	public int commandNum = 0;
	
	double playTime;
	
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_60B = new Font("Arial", Font.BOLD, 60);
		arial_30B = new Font("Arial", Font.BOLD, 30);
		arial_40B = new Font("Arial", Font.BOLD, 40);
		arial_25B = new Font("Arial", Font.BOLD, 25);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
		
	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2)
	{
		
		this.g2 = g2;
		
		g2.setFont(arial_60B);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState)
		{
			drawTitleScreen();
		}
		
		//PLAY STATE
		if(gp.gameState == gp.playState)
		{
			drawPlayScreen();
		}
		
		//PAUSE STATE
		if(gp.gameState == gp.pauseState)
		{
			drawPauseScreen();
		}
		
		//LEADERBOARD STATE
		if(gp.gameState == gp.leaderboardState)
		{
			drawLeaderboardScreen();
		}
		
		//END STATE
		if(gp.gameState == gp.endState)
		{
			drawEndScreen();
		}
	}
	
	public void drawEndScreen()
	{
		commandNum = 0;
		String text;
		int x;
		int y;
		
		gp.sql.updateLeaderboard(gp.keyH.username, playTime);
		
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		
		g2.setFont(arial_60B);
		g2.setColor(Color.yellow);
		text = "CONGRATULATIONS!";
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - (gp.tileSize * 3);
		g2.drawString(text, x, y);
		
		
		g2.setFont(arial_40B);
		g2.setColor(Color.white);
		text = "Your time : " + dFormat.format(playTime) + "s";
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - (gp.tileSize * 0);
		g2.drawString(text, x, y);
		
		g2.setFont(arial_30B);
		text= "Back to Title";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawPlayScreen()
	{
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		g2.drawImage(keyImage,gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g2.drawString("x " + gp.player.hasKey, 74, 65);
		
		//TIME
		g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize * 11, 65);
		playTime += (double)1/gp.FPS;
		
		
		//MESSAGE
		if(messageOn == true)
		{
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize/2, gp.tileSize * 3);
			
			messageCounter++;
			
			if(messageCounter > 120)
			{
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
	public void drawLeaderboardScreen()
	{
		String text;
		int x;
		int y;
		commandNum = 0;
		
		
		g2.setFont(arial_40B);
		g2.setColor(Color.white);
		text = "LEADERBOARD:";
		x = getXforCenteredText(text);
		y = gp.screenHeight/2 - (gp.tileSize * 3);
		g2.drawString(text, x, y);
		
		
		g2.setFont(arial_30B); g2.setColor(Color.white); 
		text = "NAME";
		x = getXforCenteredText(text) - (gp.tileSize * 2); 
		y = gp.screenHeight/2 - (gp.tileSize * 1);
		g2.drawString(text, x, y);
		leaderboardCounter++;
		
		g2.setFont(arial_30B); g2.setColor(Color.white); 
		text = "TIME";
		x = getXforCenteredText(text) + (gp.tileSize * 2); 
		y = gp.screenHeight/2 - (gp.tileSize * 1);
		g2.drawString(text, x, y);
		leaderboardCounter++;
		
		
		outputName = gp.sql.viewLeaderboardName();
		outputTime = gp.sql.viewLeaderboardTime();
		leaderboardCounter = 0;
		
		for(String str : outputName)
		{
			
			g2.setFont(arial_25B); g2.setColor(Color.white); 
			text = str;
			x = getXforCenteredText(text) - (gp.tileSize * 2); 
			y = gp.screenHeight/2 + (gp.tileSize * (0 + leaderboardCounter));
			g2.drawString(text, x, y);
			leaderboardCounter++;
			
		}
		
		leaderboardCounter = 0;
		
		for(String str : outputTime)
		{
			
			g2.setFont(arial_25B); g2.setColor(Color.white); 
			text = str + "s";
			x = getXforCenteredText(text) + (gp.tileSize * 2); 
			y = gp.screenHeight/2 + (gp.tileSize * (0 + leaderboardCounter));
			g2.drawString(text, x, y);
			leaderboardCounter++;
			
		}
		
		g2.setFont(arial_30B);
		text= "Back";
		x = getXforCenteredText(text);
		y += gp.tileSize * 1;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawTitleScreen()
	{
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(arial_40B);
		String text = "Hidden Riches: Jungle Expedition";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2 - (gp.tileSize * 4);
		
		
		
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 3, y + 3);
		//MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		
		//NEW GAME
		text= "New Game";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		
		//LEADERBOARD
		text= "Leaderboard";
		x = getXforCenteredText(text);
		y += gp.tileSize * 1;
		g2.drawString(text, x, y);
		if(commandNum == 1)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text= "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize * 1;
		g2.drawString(text, x, y);
		if(commandNum == 2)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		
	}
	
	public void drawPauseScreen()
	{
		
		String text = "Paused";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
			
		g2.drawString(text, x, y);
		
	}
	
	public int getXforCenteredText(String text)
	{
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
		
	}
}
