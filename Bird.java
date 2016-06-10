package strategictoastinsertion;


import org.newdawn.slick.*;
/*
Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: the bird class that objectifies the player
 */

public class Bird extends STIObject {

    static int score;//int to hold players score
    int width, height;//ints for birds width, birds height
    String birdShoot, birdDead = "res/images/hiyoko.png";//strings for bird animation
    static String playerName = "GADFREY";//string for player name (default is GADFREY)
    Sound screech;//sound to play when player dies
    
    //Constructors
    public Bird() {
        //initialization of variables
        xPos = 25;
        yPos = 100;
        speed = 7;
        imageString = "res/images/oh noes.jpg";
        score = 0;
        width = 75;
        height = 75;
    }

    public Bird(String birdImage, String birdShoot, String birdScreech) {
        this();//call constructor
        this.imageString = birdImage;//set image string to image passed into constructor
        this.birdShoot = birdShoot;//set image string to image passed into 
        try {//try/catch for audio: catch slickexception
            screech = new Sound(birdScreech);//nitialize bird screech
        } catch (SlickException ex) {
            System.out.println("Audio Error");
        }
    }
    
    public Bird(String birdType, String birdShoot, String birdScreech, int x, int y, int width, int height) {
        this(birdType, birdShoot, birdScreech);//constructor call
        xPos = x;//init x
        yPos = y;//init y
        this.width = width;//init birdwidth
        this.height = height;//init birdheight
    }

    //Getters and setters
    public int getScore() {//return score
        return score;
    }

    public void setScore(int score) {//set score
        this.score = score;
    }

    public String getBirdShoot() {//return shooting string
        return birdShoot;
    }

    public void setBirdShoot(String birdShoot) {//set shooting string
        this.birdShoot = birdShoot;
    }

    public int getWidth() {//return width
        return width;
    }

    public void setWidth(int width) {//set width
        this.width = width;
    }

    public int getHeight() {//return height
        return height;
    }

    public void setHeight(int height) {//set height
        this.height = height;
    }

    public String getBirdDead() {//return bird dead string
        return birdDead;
    }

    public void setBirdDead(String birdDead) {//set bird dead string
        this.birdDead = birdDead;
    }

    public static String getPlayerName() {//return players name
        return playerName;
    }

    public static void setPlayerName(String playerName) {//set players name
        Bird.playerName = playerName;
    }

    public void moveUp() {//move up
        if (yPos > 0) {//if y position is lower than max
            yPos -= speed;//move up
        }
    }

    public void moveDown() {//move down
        if (yPos < Menu.height - height) {//if y position is higher than min
            yPos += speed;//move down
        }
    }

    public void die() {//die
        screech.play();//play bird screech
        Play.isAlive = false;//tell game that bird is dead
    }

    public ToastBullet shoot() throws SlickException {//shoot
        ToastBullet bullet = new ToastBullet(xPos + Menu.birdWidth + 1, yPos + 5);//create a new bullet
        
        return bullet;//return it 
    }
}
