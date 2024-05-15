package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;
import jdbc.SQL;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable
{
	
	final int originalTileSize = 16;
	
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = maxScreenCol * tileSize;
	public final int screenHeight = maxScreenRow * tileSize;
	
	//WORLD SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	//public final int worldWidth = tileSize * maxWorldCol;
	//public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler(this);
	
	Sound music = new Sound();
	public Sound se = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	
	public SQL sql = new SQL(this);
	
	Thread gameThread;
	
	JFrame window;

	
	
	//ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int leaderboardState = 3;
	public final int endState = 4;
	
	public GamePanel(JFrame window)
	{
		this.window = window;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setUpGame()
	{
		
		aSetter.setObject();
		
		//playMusic(0);
		
		
	}
	
	public void startGameThread()
	{
		
		gameState = titleState;
		gameThread = new Thread(this);
		gameThread.start();
		
	}

	/*
	@Override
	public void run() 
	{
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null)
		{
			
			//UPDATE
			update();
			
			//DRAW
			repaint();
			
			try 
			{
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0)
				{
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	*/
	
	@Override
	public void run()
	{
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		//long timer = 0;
		//int drawCount = 0;
		
		
		while(gameThread != null)
		{
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			//timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			
			if (delta >= 1)
			{
				update();
				repaint();
				delta--;
				//drawCount++;
			}
			/*
			 * if (timer >= 1000000000) { System.out.println("FPS:" + drawCount);
			 * drawCount = 0; timer = 0; }
			 */
		}
		
	}
	
	public void update()
	{
		
		if(gameState == playState)
		{
			player.update();
		}
		
		if(gameState == pauseState)
		{
			//nothing
		}
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true)
		{
			drawStart = System.nanoTime();
		}
		
		
		//TITLE/LEADERBOARD SCREEN
		if(gameState == titleState || gameState == leaderboardState)
		{
			ui.draw(g2);
		}
		
		//OTHERS
		else
		{
			//TILE
			tileM.draw(g2);
			
			//OBJECT
			for(int i = 0; i < obj.length; i++)
			{
					if(obj[i] != null)
				{
						obj[i].draw(g2, this);
				}
			}
			
			//PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);
		}
		
		
		if(keyH.checkDrawTime == true)
		{
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Time Passed: " + passed, 10, 400);
			System.out.println("Time Passed: " + passed);
		}
		
		
		
		g2.dispose();
	}
	
	public void playMusic(int i)
	{
		music.setFIle(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic()
	{
		music.stop();
	}
	
	public void playSE(int i)
	{
		se.setFIle(i);
		se.play();
	}
}
