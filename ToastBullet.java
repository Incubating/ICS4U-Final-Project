package strategictoastinsertion;

/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
Created: May 18th, 2016
Purpose: projectile class for the game "STI"
*/
public class ToastBullet extends STIObject{
//attributes
    int size, X_LIMIT, Y_LOW, Y_HIGH;
//constructors
    public ToastBullet(String t, String i) {
        type = t;
        imageString = i;
        //loadImageFile();
    }
    public ToastBullet(int speed, int size, int xPos, int yPos,String toastType) { 
        this.speed = speed;
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        imageString = "res/" + toastType + ".png";
        this.type = toastType;
    }
//getters and setters for instance variables
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
//getters and setters for class variables
    public int getX_LIMIT() {
        return X_LIMIT;
    }
    public void setX_LIMIT(int X_LIMIT) {
        this.X_LIMIT = X_LIMIT;
    }
    public int getY_LOW() {
        return Y_LOW;
    }
    public void setY_LOW(int Y_LOW) {
        this.Y_LOW = Y_LOW;
    }
    public int getY_HIGH() {
        return Y_HIGH;
    }
    public void setY_HIGH(int Y_HIGH) {
        this.Y_HIGH = Y_HIGH;
    }
//rules for movement boundaries
    public String getScreenRules(){
        return "X Limit: " + X_LIMIT + 
            ", Lowest Y coordinate: " + Y_LOW + 
            ", Highest Y coordinate: " + Y_HIGH;
    }
//movement of toast-bullet
    public void move(){
        
    }
//removal of toast-bullet from game
    public void toast(){
        
    }
}