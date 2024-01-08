package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2- (gp.tileSize/2);

        solidArea = new Rectangle(8,16,31,31);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImages();
    }
    public void getPlayerImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resource/player/boy_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        up1 = setup("boy_up1");
        up2 = setup("boy_up2");
        down1 = setup("boy_down1");
        down2 = setup("boy_down2");
        left1 = setup("boy_left1");
        left2 = setup("boy_left2");
        right1 = setup("boy_right1");
        right2 = setup("boy_right2");


    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resource/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void setDefaultValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
    }
    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed==true|| keyH.rightPressed== true){
            if (keyH.upPressed) {
                direction = "up";

            } else if (keyH.downPressed) {
                direction = "down";

            }

            if (keyH.leftPressed) {
                direction = "left";

            } else if (keyH.rightPressed) {
                direction = "right";

            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.checker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.checker.checkObject(this,true);
            pickUpObject(objIndex);


            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if(spriteNum == 2){
                    spriteNum = 1;
                }spriteCounter = 0;
        }

        }
    }

    public void pickUpObject(int i){
        if (i != -1){
            String objectName = gp.obj[i].name;
            switch (objectName){
                case "Key":
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.obj[i]=null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0){
                        gp.playSoundEffect(3);
                        gp.obj[i]=null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door");
                    }else {
                        gp.ui.showMessage("You need a key");
                    }
                    break;
                case "Boots":
                    gp.ui.showMessage("Speed up!");
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.obj[i]= null;
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSoundEffect(4);
                    break;
            }
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if (spriteNum== 2){
                    image=up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if (spriteNum== 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if (spriteNum== 2){
                    image = left2;
                }

                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if (spriteNum== 2){
                    image = right2;
                }
                break;
        }

        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
