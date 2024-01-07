package object;

import javax.imageio.ImageIO;

public class Door extends SuperObject{
    public Door(){
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResource("/resource/objects/door.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
