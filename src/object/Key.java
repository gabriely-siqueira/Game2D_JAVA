package object;

import javax.imageio.ImageIO;

public class Key extends SuperObject {
    public Key(){
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResource("/resource/objects/key.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
