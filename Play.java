package strategictoastinsertion;
/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: The state for basic gameplay
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.Font;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Play extends BasicGameState {

    public Play(int state) {

    }

    @Override
    public int getID() {
        return 1;
    }
    //Declare variables for later use
    static Music themeSong, menuSong, creditsSong;//music to play throughout game
    Sound fireSound, explosionSound;//sound effects
    static Image bg, playerIcon, dead, pew, explosion;//images
    static Shape skwair, birdRect; //shapes used for display and collision detection
    Shape box;
    Input input; //variable to hold input
    static Bird player; //the player character
    static Animation bird; //animation used to draw the player character to the screen
    static int bgX1, bgX2, bgY;//integers to hold background positions
    static boolean arrayMade = false; //boolean to detect if animation has been created
    static DecimalFormat number; //used in formatting numbers
    static ArrayList<ToastBullet> bullets = new ArrayList();
    boolean fired = false; //boolean to handle whether or not bullet has been fired
    static int bulletHeight = Menu.birdHeight / 2, bulletWidth = Menu.birdWidth / 2, difficulty = 0; //integers used to reference height and width of the bird
    TrueTypeFont ttf, ttfTwo; //fonts used to write text to screen
    static boolean isAlive = true; //boolean to handle if the player is alive
    static ArrayList<ToasterBlock> toasters = new ArrayList(); //an arraylist to hold all of the toasters on the screen
    static ArrayList<Shape> bulletCollision = new ArrayList(); //an arraylist of shapes used in collision detection
    static ArrayList<Shape> toastersCollision = new ArrayList(); //an arraylist of shapes used in collision detection
    int shapeX, shapeY, rndY, rndGen, toasterGen, percentChance = 1; //integer variables used in toaster generation
    static long deathTime = -1, fireTime = -1, stringDisplayTime = -1; //long values used to hold time (to time actions)
    int recentScore, bulletsRemaining = 5;//integers to handle bullet firing
    int explosionX, explosionY, framesToExplode; //ints for information regarding bullet explosion
    static ArrayList<Integer> explosionArray = new ArrayList(); //arraylist to hold information relating to bullet explosion

    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //initializes variables
        menuSong = new Music("res/audio/thiefInTheNight.wav");//initializeds menu music
        themeSong = new Music("res/audio/portent.wav");//initializes game music
        creditsSong = new Music("res/audio/upbeatForever.wav");//initializes credits music
        fireSound = new Sound("res/audio/bulletShot.wav");//initializes shooting sound effect
        explosionSound = new Sound("res/audio/boom.wav");//initializes explosion sound
        explosion = new Image("res/images/explosion.png");//initializes explosion image
        number = new DecimalFormat("###,###");//initializes number format to format large numbers
        Font font = new Font("Palatino Linotype", Font.BOLD, 26);//initializes font for score
        Font fontTwo = new Font("Palatino LinoType", Font.BOLD, 22);//initializes font for everything else
        ttf = new TrueTypeFont(font, true);
        ttfTwo = new TrueTypeFont(fontTwo, true);
        skwair = new Rectangle(SettingUp.width - 260, SettingUp.height - 30, 225, 25);//initializes square to tidy up display
        background();//method to generate a random background
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Credits.creditsSoundPlayed = false;
        if (!isAlive) {//if the player is dead, move them to the bottom of the screen
            player.moveDown();
        }
        bg.draw(bgX1, bgY);//draws the background (loops for scrolling)
        bg.draw(bgX2, bgY);
        if (bgX1 < -Menu.width) {
            bgX1 = Menu.width - 3;
        } else if (bgX2 < -Menu.width) {//moves xposition of background for looping
            bgX2 = Menu.width - 3;
        }
        if (isAlive) {//if the bird is alive
            bird.draw(player.getxPos(), player.getyPos(), Menu.birdHeight, Menu.birdWidth);//draws the bird
            if (Menu.currentTime % 3 == 0) {//accumulates score based on time
                player.score++;
            }
            bgX1 -= 2;//moves background
            bgX2 -= 2;
            
        } else {//if the player is dead, draw the player dead sprite
            dead.draw(player.getxPos(), player.getyPos(), Menu.birdHeight, Menu.birdWidth);
        }
        g.fill(skwair);//draws background box for bullets remaining (shhhhhh)
        for (ToastBullet currentBullet : bullets) {//loops through arraylist of bullets
            pew = new Image(currentBullet.getImageString());//draws bullets
            pew.draw(currentBullet.getxPos(), currentBullet.getyPos(), bulletHeight, bulletWidth);
        }
        for (ToasterBlock toaster : toasters) {//loops through arraylist of toasters
            toaster.toasterImg.draw(toaster.xPos, toaster.yPos, Menu.birdHeight, Menu.birdWidth);//draws toasters
        }
        ttf.drawString(SettingUp.width - 200, 10, "Score: " + number.format(player.score));//draws player score 
        
        //draws bullets remaining
        ttfTwo.drawString(SettingUp.width - 250, SettingUp.height - 30, "Bullets Remaining: " + bulletsRemaining, Color.black);
        if (!(Menu.currentTime > stringDisplayTime + 500)) { //if a toaster has beeen destroyed within a certain amount of time
            ttfTwo.drawString(SettingUp.width - 200, 40, " +" + recentScore);//display their score bonus to the user
        }

        for (int i = 0; i < explosionArray.size(); i += 3) {//loops through arraylist of bullet explosions
            explosionX = explosionArray.get(i);//gets x, y, and frames to explode of an explosion
            explosionY = explosionArray.get(i + 1);
            framesToExplode = explosionArray.get(i + 2);
            if (framesToExplode <= 0) {//removes frames to explode if no explosion neccessary
                explosionArray.remove(i);
                explosionArray.remove(i);
                explosionArray.remove(i);
            } else {//if neccessary, draw an explosion
                explosion.draw(explosionX, explosionY, Menu.birdHeight, Menu.birdWidth);
                framesToExplode--;//decrease frames to explode
                explosionArray.set(i + 2, framesToExplode);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        input = gc.getInput();//get input from game container
        Menu.currentTime = System.currentTimeMillis();//get current time in milliseconds
        if (deathTime != -1) {//if the user is dead 
            if (Menu.currentTime > (deathTime + 3000)) {//after 3 seconds
                themeSong.stop(); //stop the theme music and enter credits
                sbg.enterState(SettingUp.credits);
            }
        }

        if (arrayMade == false) {//if the bird animation has not been created, create the bird animation
            setBirdArray();
        }

        bird.draw(player.getxPos(), player.getyPos());//draws the bird to the screen

       
        if (input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_ESCAPE)) {//if the player wants to pause 
            sbg.enterState(SettingUp.pause);//pause
        }

        if (input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_Z)) {//if the player wants to shoot
            if (isAlive) {//if they're alive
                if (Menu.currentTime > fireTime + 250) {//if enough time has passed since the player last shot
                    if (bullets.size() < 5) {//if the player has bullets to fire
                        bird.setCurrentFrame(1);//enter the shooting animation
                        if (input.isKeyPressed(Input.KEY_SPACE) || input.isKeyPressed(Input.KEY_Z)) {
                            ToastBullet temp = player.shoot();//create a new bullet
                            fireSound.play();//play the shooting sound
                            bullets.add(temp);//add bullet to arraylist
                            box = temp.getShape();
                            bulletCollision.add(box);//adds box for collision detection
                            fireTime = System.currentTimeMillis();//get time of bullet fired
                        }
                    }
                }
            }
        } else {//if not firing
            bird.setCurrentFrame(0);//enter regular animation frame
        }
        bulletsRemaining = 5 - bullets.size();//get bullets remaining
        for (int j = 0; j < bullets.size(); j++) {//loop through bullets
            ToastBullet currentBullet = bullets.get(j);//sets variable (largely for easier reading)
            Shape currentShape = bulletCollision.get(bullets.indexOf(currentBullet));//sets current shape
            if (isAlive) {//if the player is alive
                currentBullet.move();//move the bullets
                currentShape.setLocation(currentBullet.getxPos(), currentBullet.getyPos());//moves the shape
                if (currentBullet.getxPos() > Menu.width) {//if the bullet leaves the screen
                    bullets.remove(currentBullet);//remove the bullet 
                    bulletCollision.remove(currentShape);//remove the shape
                }
            } else {//if the player is dead 
                bullets.remove(currentBullet);//remove bullet
                bulletCollision.remove(currentShape);//remove shape
            }
        }

        if (isAlive) {//if the player is alive
            if (input.isKeyDown(Input.KEY_UP) || (input.isKeyDown(Input.KEY_W))) {//if the player wants to move up,
                player.moveUp();//if they're alive, move up
            } else if (input.isKeyDown(Input.KEY_DOWN) || (input.isKeyDown(Input.KEY_S))) {//if the player wants to move down
                player.moveDown();//if they're alive, move down
            }

            for (int j = 0; j < toasters.size(); j++) {//loop through toaster arraylist
                ToasterBlock currentToaster = toasters.get(j);//get current toaster
                Shape currentShape = toastersCollision.get(toasters.indexOf(currentToaster));//get current shape
                    currentToaster.move();//move toaster
                    currentShape.setLocation(currentToaster.xPos, currentToaster.yPos);//mpve shape to toaster
                if (currentToaster.xPos < 0 - (Menu.birdWidth * 2)) {//if the toaster goes off screen
                    toasters.remove(currentToaster);//remove toaster
                    toastersCollision.remove(currentShape);//remove shape
                }
                if (birdRect.intersects(currentShape)) {//if the bird hits a toaster
                    player.die();//kill the bird
                    Credits.start = true;//initialize for credits music
                    deathTime = System.currentTimeMillis();//get death time
                }
                for (int g = 0; g < bulletCollision.size(); g++) {//loops through arraylist of bullets
                    Shape currentBullet = bulletCollision.get(g);//get current shape
                    if (currentBullet.intersects(currentShape)) {//if the toaster intersects the bullet
                        currentToaster.damage();//damage the toaster
                        bullets.remove(bulletCollision.indexOf(currentBullet));//remove the bullet
                        bulletCollision.remove(currentBullet);//remove bullet shape
                        if (currentToaster.getHealth() <= 0) {//if the toaster is dead
                            recentScore = currentToaster.getScoreToAdd();//get score bonus
                            player.score += recentScore;//add score bonus
                            explosionArray.add(currentToaster.getxPos());//get x for explosion
                            explosionArray.add(currentToaster.getyPos());//get y for explosion
                            explosionArray.add(7);//set frames to explode to 7
                            toasters.remove(currentToaster);//remove toaster
                            toastersCollision.remove(currentShape);//remove shape
                            explosionSound.play();//play explosion sound
                            stringDisplayTime = System.currentTimeMillis();//get time of toaster explosion

                        }

                    }
                }

            }
            rndGen = (int) (Math.random() * 99) + 1;//generate a random number between 1 and 99
            if (rndGen <= percentChance) {
                rndY = (((int) (Math.random() * 6) + 0) * Menu.birdWidth) + 5;
                if (difficulty < 50) {//if difficulty is below a certain amount
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, 1));//create a copper toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                } else if (difficulty > 50 && difficulty < 100) {//if difficulty is between a certain range
                    toasterGen = (int) (Math.random() * 2) + 1;//generate a random number (for either copper or silver toasters)
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create that toaster;
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                } else if (difficulty > 100 && difficulty < 150) {//if difficulty is within a certain range
                    toasterGen = (int) (Math.random() * 3) + 1;//generate a random toaster (gold, silver, or copper)
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create that toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                } else if (difficulty > 150 && difficulty < 200) {//if toaster is within a certain range
                    toasterGen = (int) (Math.random() * 2) + 2;//generate a random number (either gold or silver)
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;
                } else if (difficulty > 200 && difficulty <= 250) {//maximum difficulty
                    toasterGen = (int) (Math.random() * 1) + 3;//generate a gold toaster
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                }else if(difficulty > 250 && difficulty <=300){
                    toasterGen = (int) (Math.random() * 1) + 3;//generate a gold toaster
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                    player.speed = 6;
                }else if(difficulty > 300 && difficulty <=350){
                    toasterGen = (int) (Math.random() * 1) + 3;//generate a gold toaster
                    toasters.add(new ToasterBlock(Menu.width - 10, rndY, toasterGen));//create toaster
                    toastersCollision.add(new Rectangle(Menu.width - 10, rndY, Menu.birdWidth, Menu.birdHeight));
                    difficulty++;//increase difficulty
                    player.speed = 5;
                    percentChance++;
                }
            }
        }
        if (difficulty % 50 == 0) {//if enough toasters have been destroyed
            difficulty++;//increase the difficulty
            ToasterBlock.accelerate();
        }

        if (difficulty % 150 == 0) {//if difficulty reaches 150
            percentChance++;//increase likelihood of toaster spawning
        }

        if (!input.isKeyPressed(Input.KEY_R)) {//if the r key is pressed, generate a random background
        } else {
            background();
        }

        shapeX = player.getxPos();//get xpos of bird
        shapeY = player.getyPos();//get ypos of bird
        birdRect.setLocation(shapeX, shapeY);//move bird shape to bird 
    }

    public void background() throws SlickException {//method for generating random background
        String imagePath = "res/images/cave.png";//default background is cave
        switch (((int) ((Math.random() * 4) - 0))) {//generate a random number between zero and three
            case 0://if random is  0
                imagePath = "res/images/cave.png";//background is cave
                break;
            case 1://if random is 1
                imagePath = "res/images/sunset.png";//background is sunset
                break;
            case 2://if random is 2
                imagePath = "res/images/starry.png";//background is a beautiful, tranquil starry night. you can feel the wind in your hair as you gaze at the stars, appreciating the infinite beauty of our natural universe while simultaneously revelling in sheer existential terror at the size of it 
                break;
            case 3://if random is 3
                imagePath = "res/images/beach.png";//beach
                break;
        }
        bg = new Image(imagePath);//initialize background variables
        bgX1 = 0;
        bgX2 = 1000;
        bgY = 0;
    }

    public void setBirdArray() throws SlickException {//method that sets animation (runs only once per play)
        arrayMade = true;//sets boolean so method will not be called again
        themeSong.loop();//plays theme song
        dead = new Image(player.getBirdDead());//initialize bird death image
        //initialize bird animation
        Image[] images = {new Image(player.getImageString()), new Image(player.getBirdShoot()), new Image(player.getBirdDead())};
        int[] duration = {300, 300, 300};
        bird = new Animation(images, duration, false);
        //create bird shape
        birdRect = new Rectangle(player.getxPos(), player.getyPos(), player.getWidth(), player.getHeight());
    }

}
