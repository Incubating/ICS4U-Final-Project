package strategictoastinsertion;

/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: box class for the game "STI"
 */
public class ToasterBlock extends STIObject {

    int health, CURRENT_SPEED, MAX_SPEED;
//constructors

    public ToasterBlock(String t, String i) {
        type = t;
        imageString = i;
    }

    public ToasterBlock(int xPos, int yPos, String toasterType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = toasterType;
        this.imageString = ("res/" + type + ".png");
        switch (type) {
            case "copper":
                health = 1;
                break;
            case "silver":
                health = 2;
                break;
            case "gold":
                health = 3;
                break;
        }

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
//removal of toaster-block from game

    public void destroy() {

    }
//collision detector for the player character

    public void birdCollide() {

    }
//collision detector for a toast-bullet

    public void toastCollide() {

    }
//movement speed changer

    public void accelerate() {

    }
}
