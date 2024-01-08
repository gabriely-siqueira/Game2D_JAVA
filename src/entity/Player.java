package entity;

import Main.GamePanel;
import Main.KeyHandler;

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

        solidArea = new Rectangle(7,14,28,28);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getAnimationImages();
    }
    public void getAnimationImages() {
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
                    hasKey++;
                    gp.obj[i]=null;
                    System.out.println("Key:" + hasKey);
                    break;
                case "Door":
                    if (hasKey > 0){
                        gp.obj[i]=null;
                        hasKey--;
                    }
                    System.out.println("Key:" + hasKey);
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
