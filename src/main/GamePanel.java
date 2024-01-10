package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter setter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    // GAME STATE
    public enum GameState {
        PLAY,
        PAUSE
    }

    public GameState gameState = GameState.PLAY;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        setter.setObject();
        setter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = GameState.PLAY;
    }

    private boolean isRunning = false;

    public void startGameThread() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            isRunning = true;
            gameThread.start();
        }
    }

    public void stopGameThread() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == GameState.PLAY) {
            //PLAYER
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i]!=null){
                    npc[i].update();
                }
            }
        }
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTile) {
            drawStart = System.nanoTime();
        }

        // TILE
        tileM.draw(g2);

        // OBJECT
        for (SuperObject anObj : obj) {
            if (anObj != null) {
                anObj.draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // NPC
        for (int i = 0; i < npc.length ; i++) {
            if (npc[i] != null){
                npc[i].draw(g2);
            }
        }

        // UI
        ui.draw(g2);

        // DEBUG
        if (keyH.checkDrawTile) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
            g2.dispose();
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        se.setFile(i);
        se.play();
    }
}
