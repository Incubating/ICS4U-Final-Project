/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
Created: May 18th, 2016
Purpose: abstract class to handle objects for the game "Strategic Toast Insertion"
Version: 1.2
 */
package strategictoastinsertion;

/**
 *
 * @author Seth Thomson
 */
public class STIObject {
   int xPos, yPos, speed;
    String type, imageString;

    public STIObject() {
        xPos = 0;
        yPos = 0;
        speed = 1;
        type = "res/images/oh noes.jpg";
        imageString = "res/images/oh noes.jpg";
    }

    public STIObject(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public STIObject(int xPos, int yPos, int speed) {
        this(xPos, yPos);
        this.speed = speed;
    }

    public STIObject(int xPos, int yPos, int speed, String type) {
        this(xPos, yPos, speed);
        this.type = type;
    }
    

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
    
}
