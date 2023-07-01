package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 48;

        setDefaultVals();
        getPlayerImage();

    }

    public void setDefaultVals() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 18;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gp.cCheck.checkTile(this);

            // if collision false, player can move
            if (collisionOn == false) {
                switch(direction) {
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
            if(spriteCounter > 12) {
                if(spriteNum == 1 || spriteNum == 0) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2 || spriteNum == 0) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else if (keyH.upPressed == false || keyH.downPressed == false || keyH.leftPressed == false || keyH.rightPressed == false) {
            spriteNum = 0;
        }

    }

    public void draw(Graphics2D g2) {

 //       g2.setColor(Color.white);
 //       g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
        case "up":
        if(spriteNum == 1) {
            image = up1;
        }
        if(spriteNum == 2) {
            image = up2;
        }
        if (spriteNum == 0) {
            image = idle;
        }
            break;
        case "down":
        if(spriteNum == 1) {
            image = down1;
        }
        if(spriteNum == 2) {
            image = down2;
        }
        if (spriteNum == 0) {
            image = idle;
        }
            break;
        case "left":
        if(spriteNum == 1) {
            image = left1;
        }
        if(spriteNum == 2) {
            image = left2;
        }
        if (spriteNum == 0) {
            image = idle;
        }
            break;
        case "right":
        if(spriteNum == 1) {
            image = right1;
        }
        if(spriteNum == 2) {
            image = right2;
        }
        if (spriteNum == 0) {
            image = idle;
        }
            break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-left2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-right2.png"));
            idle = ImageIO.read(getClass().getClassLoader().getResourceAsStream("\\res\\player\\kirby-idle.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
