package object;

import javax.imageio.ImageIO;

public class Chest extends SuperObject {
    public Chest(){
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResource("/resource/objects/chest.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
