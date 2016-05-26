
package strategictoastinsertion;
import org.newdawn.slick.*;
/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
Created: May 18th, 2016
Purpose: player character class for the game "Strategic Toast Insertion"
*/
public class Bird extends STIObject{
    int score;
    String birdShoot;
    public Bird(){
        xPos = 25;
        yPos = 25;
        speed = 3;
        imageString = "null";
        score = 0;
    }
    public Bird(String birdType, String birdShoot){
        this();
        this.imageString = birdType;
        this.birdShoot = birdShoot;
    }
    public Bird(String birdType, String birdShoot, int x, int y){
        this(birdType, birdShoot);
        System.out.println(this.imageString);
        xPos = x;
        yPos = y;
    }  

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBirdShoot() {
        return birdShoot;
    }

    public void setBirdShoot(String birdShoot) {
        this.birdShoot = birdShoot;
    }
    
    public void moveUp(){
        yPos += speed;
    }
    
    public void moveDown(){
        yPos -= speed;
    }
    
    public void shoot(){
        
    }
    
    public void birth() throws SlickException{
        Play.playerIcon = new Image(type);
        Play.playerIcon.draw(xPos, yPos, 100, 100);
    }
}