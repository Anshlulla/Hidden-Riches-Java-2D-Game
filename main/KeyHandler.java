package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JOptionPane;

public class KeyHandler implements KeyListener
{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean takingUsername = false;
	
	//DEBUG
	public boolean checkDrawTime = false;
	
	String username;
	
	
	public KeyHandler(GamePanel gp)
	{
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int code  = e.getKeyCode();
		
		//END STATE
		if(gp.gameState == gp.endState)
		{
			upPressed = false;
			downPressed = false;
			leftPressed = false;
			rightPressed = false;
			if(code == KeyEvent.VK_ESCAPE)
			{
				
				gp.window.dispose();
				try 
				{
		            Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
		            mainMethod.setAccessible(true);
		            mainMethod.invoke(null, (Object) new String[]{});
		        } 
				catch (Exception e2) 
				{
		            e2.printStackTrace();
		        }
		        
			}
		}
		
		//LEADERBOARD STATE
		if(gp.gameState == gp.leaderboardState)
		{
			if(code == KeyEvent.VK_ESCAPE) 
			{
				gp.gameState = gp.titleState;
			}
		}
		
		//TITLE STATE
		
		if(gp.gameState == gp.titleState)
		{
			if(code == KeyEvent.VK_W)
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S)
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2)
				{
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_UP)
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_DOWN)
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2)
				{
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER)
			{
				if(gp.ui.commandNum == 0)
				{
					gp.keyH.takeUsernameInput();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 1)
				{
					gp.gameState = gp.leaderboardState;
				}
				if(gp.ui.commandNum == 2)
				{
					System.exit(0);
				}
				
			}
		}
		
		
		//PLAY/PAUSE STATE
		if(gp.gameState == gp.playState || gp.gameState == gp.pauseState)
		{
			if(code == KeyEvent.VK_W)
			{
				upPressed = true;
			}
			if(code == KeyEvent.VK_S)
			{
				downPressed = true;
			}
			if(code == KeyEvent.VK_A)
			{
				leftPressed = true;
			}
			if(code == KeyEvent.VK_D)
			{
				rightPressed = true;
			}
			
			if(code == KeyEvent.VK_UP)
			{
				upPressed = true;
			}
			if(code == KeyEvent.VK_DOWN)
			{
				downPressed = true;
			}
			if(code == KeyEvent.VK_LEFT)
			{
				leftPressed = true;
			}
			if(code == KeyEvent.VK_RIGHT)
			{
				rightPressed = true;
			}
			if(code == KeyEvent.VK_P)
			{
				if(gp.gameState == gp.playState)
				{
					gp.gameState = gp.pauseState;
				}
				else if (gp.gameState == gp.pauseState)
				{
					gp.gameState = gp.playState;
				}
			}
			
			if(code == KeyEvent.VK_R) 
			{ 
				if(gp.gameState == gp.playState) 
				{ 
					gp.window.dispose();
					try 
					{
			            Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
			            mainMethod.setAccessible(true);
			            mainMethod.invoke(null, (Object) new String[]{});
			        } 
					catch (Exception e2) 
					{
			            e2.printStackTrace();
			        }
				} 
			}
			
			
			//DEBUG
			if(code == KeyEvent.VK_T)
			{
				if(checkDrawTime == true)
				{
					checkDrawTime = false;
				}
				else if(checkDrawTime == false)
				{
					checkDrawTime = true;
				}
				
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int code  = e.getKeyCode();	
		
		//PLAY STATE
		
		if(gp.gameState == gp.playState)
		{
			if(code == KeyEvent.VK_W)
			{
				upPressed = false;
			}
			if(code == KeyEvent.VK_S)
			{
				downPressed = false;
			}
			if(code == KeyEvent.VK_A)
			{
				leftPressed = false;
			}
			if(code == KeyEvent.VK_D)
			{
				rightPressed = false;
			}
			
			if(code == KeyEvent.VK_UP)
			{
				upPressed = false;
			}
			if(code == KeyEvent.VK_DOWN)
			{
				downPressed = false;
			}
			if(code == KeyEvent.VK_LEFT)
			{
				leftPressed = false;
			}
			if(code == KeyEvent.VK_RIGHT)
			{
				rightPressed = false;
			}
		}
		
	}
	
	public void takeUsernameInput() 
	{
		
        username = JOptionPane.showInputDialog("Enter your username:");
        takingUsername = false;
        
    }

}
