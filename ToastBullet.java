package strategictoastinsertion;

import org.newdawn.slick.geom.Rectangle;

/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: the bullet class that objectifies projectiles in the gamea
*/
public class ToastBullet extends STIObject{
//attributes
    static int size, X_LIMIT, Y_LOW, Y_HIGH;//integers to handle limitations
//constructors
    public ToastBullet(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.imageString = generateToast();
        this.speed = 5;
        this.size = 10;
    }
//getters and setters for instance variables
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
//getters and setters for class variables
    public int getX_LIMIT() {//return x limit
        return X_LIMIT;
    }
    public void setX_LIMIT(int X_LIMIT) {//set x limit
        this.X_LIMIT = X_LIMIT;
    }
    public int getY_LOW() {//return lower y limit
        return Y_LOW;
    }
    public void setY_LOW(int Y_LOW) {//set lower y limit
        this.Y_LOW = Y_LOW;
    }
    public int getY_HIGH() {//return higher y limit
        return Y_HIGH;
    }
    public void setY_HIGH(int Y_HIGH) {//set higher y limit
        this.Y_HIGH = Y_HIGH;
    }
//rules for movement boundaries
    public String getScreenRules(){//return rules for screen boundaries
        return "X Limit: " + X_LIMIT + 
            ", Lowest Y coordinate: " + Y_LOW + 
            ", Highest Y coordinate: " + Y_HIGH;
    }
//movement of toast-bullet
    public void move(){
        if(xPos < (SettingUp.width+10)){//if x position of toast bullet is within the boundaries
                xPos+=speed;//move toast bullet
        }
    }
    
    public Rectangle getShape(){//generates a new rectangle around the bullet
        Rectangle bosch = new Rectangle(xPos, yPos, Play.bulletWidth, Play.bulletHeight);
        return bosch;//returns the rectangles
    }
    
    
    public String generateToast(){//generates a random toast
        String file;//string to hold filepath
        int random = (int)((Math.random()*4)+0);//generates a random number between 0 and 3
        switch(random){
            case 0: //if the number is 0
                file = "res/images/bread.png";//toast is bread
                break;
            case 1://if the number is 1
                file = "res/images/waffle.png";//toast is waffle
                break;
            case 2://if the number is 2
                file = "res/images/fork.png";//toast is a fork
                break;
            case 3://if the number is 3
                file = "res/images/pepper.png";//toast is a delicious green bell pepper
                break;
            default://default case: toast is bread
                file = "res/images/bread.png";
                break;
        }
        return file;//returns the file path
    }
}