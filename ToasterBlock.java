package strategictoastinsertion;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: the block class that objectifies the game's obstacles
 */
public class ToasterBlock extends STIObject {

    int health, scoreToAdd;//integers to handle health and speet
    static int CURRENT_SPEED = 2, MAX_SPEED = 10;//integers to handle speed limitations
    Image toasterImg; //image for toaster
    
    //Constructors
    public ToasterBlock(int h) throws SlickException{
        health = h;//sets health
        if(h == 1){//if health is one, toaster is a copper toaster
            imageString = "res/images/copper.png";//copper display string
            scoreToAdd = 100;//copper score
        }else if(h == 2){//if health is two, toaster is silver
            imageString = "res/images/silver.png";//silver display string
            scoreToAdd = 200;//silver score
        }else if(h == 3){//if health is three, toaster is gold
            imageString = "res/images/gold.png";//gold display string
            scoreToAdd = 300;//gold score
        }
        
        toasterImg = new Image(imageString);//sets image to displaystring
        xPos = Menu.width - 100;//sets xposition
        yPos = 50;//sets yposition
    }

    public ToasterBlock(int xPos, int yPos, int h) throws SlickException {
        this(h);//constructor call
        this.xPos = xPos;//set xposition
        this.yPos = yPos;//set yposition
    }
//getters and setters for instance variables

    public int getScoreToAdd() {//returns score
        return scoreToAdd;
    }

    public void setScoreToAdd(int scoreToAdd) {//sets score
        this.scoreToAdd = scoreToAdd;
    }

    public Image getToasterImg() {//returns toaster image
        return toasterImg;
    }

    public void setToasterImg(Image toasterImg) {//sets toaster image
        this.toasterImg = toasterImg;
    }

    public int getHealth() {//returns health
        return health;
    }

    public void setHealth(int health) {//sets health
        this.health = health;
    }
//getters and setters for class variables

    public int getCURRENT_SPEED() {//returns current speed
        return CURRENT_SPEED;
    }

    public void setCURRENT_SPEED(int CURRENT_SPEED) {//sets current speed
        this.CURRENT_SPEED = CURRENT_SPEED;
    }

    public int getMAX_SPEED() {//returns max speed
        return MAX_SPEED;
    }

    public void setMAX_SPEED(int MAX_SPEED) {//sets max speed
        this.MAX_SPEED = MAX_SPEED;
    }
    
    public Image getImage(){//returns image
        return toasterImg;
    }
    
    public void setImage(Image newImg){//sets image
        this.toasterImg = newImg;
    }
    
    public void move(){//moves toaster based on speed
        xPos -= CURRENT_SPEED;
    }
    public void damage(){//damages toaster 
        health -= 1;
    }
 
    //movement speed changer
    public static void accelerate() {//increases speed of toaster
        if(CURRENT_SPEED <= 10){
            CURRENT_SPEED++;
        }
    }
    
}