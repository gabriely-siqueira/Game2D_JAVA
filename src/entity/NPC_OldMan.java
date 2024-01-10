package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity {
    private int actionLockCounter = 0;

    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "right";
        speed = 1;
        getPlayerImages();
    }

    public void getPlayerImages() {
        up1 = setup("npc/oldman_up_1");
        up2 = setup("npc/oldman_up_2");
        down1 = setup("npc/oldman_down_1");
        down2 = setup("npc/oldman_down_2");
        left1 = setup("npc/oldman_left_1");
        left2 = setup("npc/oldman_left_2");
        right1 = setup("npc/oldman_right_1");
        right2 = setup("npc/oldman_right_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resource/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(4); // pick up a number from 0 to 3
           if (i==0) {
               direction = "up";
           }else if (i == 1) {
               direction = "down";
           } else if (i==2) {
               direction = "left";
           } else if (i==3) {
               direction = "right";
           }
            actionLockCounter = 0;
        }
    }
}
