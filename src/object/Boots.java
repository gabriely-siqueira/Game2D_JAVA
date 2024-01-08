package object;

import javax.imageio.ImageIO;

public class Boots extends SuperObject{
    public Boots(){
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResource("/resource/objects/boots.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
