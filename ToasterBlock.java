package strategictoastinsertion;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: box class for the game "STI"
 */
public class ToasterBlock extends STIObject {

    int health;
    static int CURRENT_SPEED = 2, MAX_SPEED = 10;
    Image toasterImg;
    //constructors

    public ToasterBlock(int h) throws SlickException{
        health = h;
        if(h == 1){
            imageString = "res/images/copper.png";
        }else if(h == 2){
            imageString = "res/images/silver.png";
        }else if(h == 3){
            imageString = "res/images/gold.png";
        }
        
        toasterImg = new Image(imageString);
        xPos = Menu.width - 100;
        yPos = 50;
    }

    public ToasterBlock(int xPos, int yPos, int h) throws SlickException {
        this(h);
        this.xPos = xPos;
        this.yPos = yPos;
    }
//getters and setters for instance variables

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
//getters and setters for class variables

    public int getCURRENT_SPEED() {
        return CURRENT_SPEED;
    }

    public void setCURRENT_SPEED(int CURRENT_SPEED) {
        this.CURRENT_SPEED = CURRENT_SPEED;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public void setMAX_SPEED(int MAX_SPEED) {
        this.MAX_SPEED = MAX_SPEED;
    }
    
    public Image getImage(){
        return toasterImg;
    }
    
    public void setImage(Image newImg){
        this.toasterImg = newImg;
    }
//removal of toaster-block from game

    public void destroy() {
        //Animation
    }
    
    //collision detector for the player character
    public void birdCollide() {
    }
    
    //collision detector for a toast-bullet
    public void toastCollide() {

    }
    
    public void move(){
        xPos -= CURRENT_SPEED;
    }
 
    //movement speed changer
    public static void accelerate() {
        if(CURRENT_SPEED <= 10){
            CURRENT_SPEED++;
        }
    }
    
}