/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
Created: May 18th, 2016
Purpose: the abstract class that handles all basic objects
 */
package strategictoastinsertion;

/**
 *
 * @author Seth Thomson
 */
public class STIObject {
   int xPos, yPos, speed;//integers to handle player movement
    String  imageString;//strings to handle type

    //Constructors
    public STIObject() {
        //initialize variables
        xPos = 0;
        yPos = 0;
        speed = 1;
        imageString = "res/images/oh noes.jpg";
    }

    public STIObject(int xPos, int yPos) {
        this();//constructor call
        this.xPos = xPos;//initialize xposition
        this.yPos = yPos;//initialize yposition
    }

    public STIObject(int xPos, int yPos, int speed) {
        this(xPos, yPos);//constructor call
        this.speed = speed;//initialize speed
    }
    
    //getters and setters
    public int getxPos() {//return xposition
        return xPos;
    }

    public void setxPos(int xPos) {//set xposition
        this.xPos = xPos;
    }

    public int getyPos() {//return yposition
        return yPos;
    }

    public void setyPos(int yPos) {//set yposition
        this.yPos = yPos;
    }

    public int getSpeed() {//return speed
        return speed;
    }

    public void setSpeed(int speed) {//set speed
        this.speed = speed;
    }

    public String getImageString() {//return image string
        return imageString;
    }

    public void setImageString(String imageString) {//set image string
        this.imageString = imageString;
    }
    
}
