package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings

    final int  originalTileSize = 16; 
    final int scale = 3;
     
    public int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol; // 768
    public final int screenHeight = tileSize*maxScreenRow; // 576

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int WorldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize *maxWorldRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    Thread gameThread;
    public CollisionCheck cCheck = new CollisionCheck(this);
    

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
       
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {


           // UPDATE: update information such as character positions
           update();

           // DRAW: draw the screen with updated information
           repaint();

           
           try {
            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime = remainingTime/1000000;

            if (remainingTime < 0) {
                remainingTime = 0;
            }

            Thread.sleep((long) remainingTime);
            
            nextDrawTime += drawInterval;
           

            } catch (InterruptedException e) {

            e.printStackTrace(); 
            }


        }

    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
