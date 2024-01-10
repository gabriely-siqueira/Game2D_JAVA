package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    // DEBUG
    boolean checkDrawTile = false;

    GamePanel gp;

    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_A = KeyEvent.VK_A;
    private static final int KEY_D = KeyEvent.VK_D;
    private static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
    private static final int KEY_T = KeyEvent.VK_T;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não é necessário implementar para esta aplicação
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KEY_W)
            upPressed = true;
        if (code == KEY_S)
            downPressed = true;
        if (code == KEY_A)
            leftPressed = true;
        if (code == KEY_D)
            rightPressed = true;
        if (code == KEY_ESCAPE) {
            toggleGameState();
        }

        // DEBUG
        if (code == KEY_T) {
            toggleDrawTile();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KEY_W)
            upPressed = false;
        if (code == KEY_S)
            downPressed = false;
        if (code == KEY_A)
            leftPressed = false;
        if (code == KEY_D)
            rightPressed = false;
    }

    private void toggleGameState() {
        if (gp.gameState == GamePanel.GameState.PLAY) {
            gp.gameState = GamePanel.GameState.PAUSE;
        } else if (gp.gameState == GamePanel.GameState.PAUSE) {
            gp.gameState = GamePanel.GameState.PLAY;
        }
    }

    // DEBUG
    private void toggleDrawTile() {
        checkDrawTile = !checkDrawTile;
    }
}
