package strategictoastinsertion;

//Imports
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import javax.swing.JOptionPane;

/**
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: The state for the main menu
 */
public class Menu extends BasicGameState {

    //Constructor
    public Menu(int state) {

    }

    //Variable declarations
    //Input variables
    Input input; //input [pretty self explanitory]

    //Image variables / arrays
    Image baseMenu, exit, playNow, playNowHover, selectedCircle, selectedSquare; //Menu pics
    Image birds[] = new Image[7]; //All birds
    Image birdsHover[] = new Image[7]; //All birds after mouse interaction
    String birdScreech[] = new String[7];//Death cry of all birds

    //Integers
    static int width = SettingUp.width; //width of screen
    static int height = SettingUp.height; //height of screen
    int centerX = width / 2; //Center of screen on X
    int centerY = height / 2; //Center of screen on 

    static boolean menuSoundPlayed = false; //Music played boolean

    //Mouse X & Y position
    int mouseX; 
    int mouseY;

    //The size of the bird
    static int birdWidth = width / 10;
    static int birdHeight = height / 6;

    //Timer variables
    static long startTime;
    static long currentTime;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        //Assign images
        baseMenu = new Image("res/images/menu.png");
        exit = new Image("res/images/exit.png");
        playNow = new Image("res/images/play.png");
        playNowHover = new Image("res/images/playHighlighted.png");
        selectedCircle = new Image("res/images/selectedButton.png");
        selectedSquare = new Image("res/images/resolutionHighlight.png");
        
        //Read in the bird image locations from a file
        try {
            
            FileReader fr = new FileReader("res/birdFiles.txt");
            BufferedReader br = new BufferedReader(fr);
            
            for (int l = 0; l < 7; l++) {
                
                birds[l] = new Image(br.readLine());
                birdsHover[l] = new Image(br.readLine());
                birdScreech[l] = br.readLine();
            
            }
        
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Array of coordinates for birds
    float[][] coords = new float[9][2];

    //A method setting the coordinates for the birds
    public void getCoordinates() {
        //Flamingo
        coords[0][0] = (float) (centerX - (width / 5));
        coords[0][1] = centerY - (height / 10);

        //Hummer
        coords[1][0] = centerX + (width / 10);
        coords[1][1] = centerY - (height / 10);

        //Parrot
        coords[2][0] = centerX + (width / 15);
        coords[2][1] = centerY - (height / 4);

        //Owl
        coords[3][0] = centerX - (width / 7);
        coords[3][1] = centerY - (height / 4);

        //Pigeon
        coords[4][0] = centerX - (width / 22);
        coords[4][1] = (float) (centerY - (height / 3.4));

        //Tweeter
        coords[5][0] = (float) (centerX - (width / 6.5));
        coords[5][1] = centerY + (height / 20);

        //Pelican
        coords[6][0] = centerX + (width / 20);
        coords[6][1] = centerY + (height / 17);

        //Options
        coords[7][0] = centerX - (width / 10);
        coords[7][1] = centerY + (height / 4);
        coords[8][0] = coords[7][0] + 200;
        coords[8][1] = coords[7][1] + 100;
    }
    
    //Variables for selected circle
    boolean draw = false;
    float circleX, circleY;

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        getCoordinates(); //Grab bird coordinates
        baseMenu.draw(0, 0, width, height); //Draw the menu img
        playNow.drawCentered(width / 2, height / 2); //Draw the "Play Now" button in the middle
        exit.draw(coords[7][0], coords[7][1]); //Draw the exit button
        
        //If the mouse is on the exit button
        if (((mouseX > coords[7][0]) && (mouseX <= coords[8][0])) && ((mouseY > coords[7][1]) && (mouseY <= coords[8][1]))) {
            selectedSquare.draw(coords[7][0], coords[7][1]);
            
            //If the left click has been pressed
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true){
                SettingUp.app.destroy();   
            }
        }
        
        //When draw is true [When a bird is selected]
        if (draw == true) {
            selectedCircle.draw(circleX, circleY); //Draw the circle on the selected bird
        }

        for (int j = 0; j < 7; j++) { //Scroll through all bird coordinates
            
            //If the mouse is on a bird
            if (((mouseX > coords[j][0]) && (mouseX < (coords[j][0] + birdWidth)))
                    && (((mouseY > coords[j][1]) && (mouseY < (coords[j][1] + birdHeight))))) {
                
                birdsHover[j].draw(coords[j][0], coords[j][1], birdWidth, birdHeight);
                
                //If the mouse is clickced
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) {
                    
                    draw = true; //Set draw to true
                    circleX = coords[j][0]; //Set the x position of the bird to circleX
                    circleY = coords[j][1]; //Set the y position of the bird to circleY
                    Play.player = new Bird(birds[j].getResourceReference(), birdsHover[j].getResourceReference(), birdScreech[j]); //Generate selected bird
                
                }

            } else { //Otherwise
                birds[j].draw(coords[j][0], coords[j][1], birdWidth, birdHeight); //Just draw the bird
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
        //Input variables
        input = gc.getInput(); //All input in gc 
        mouseX = input.getMouseX(); //Mouse's x position
        mouseY = input.getMouseY(); //Mouse's y position
        
        if (!menuSoundPlayed) { //If the music hasn't started
            playSound(); //Get the beats going
        }
        
        playNow = new Image("res/images/play.png"); //Re assign play now button
        
        //If the mouse is over the play now button X value
        if ((mouseX >= ((width / 2) - (birdWidth / 2))) && (mouseX <= ((width / 2) + (birdWidth / 2)))) {
            
            //If the mouse is over the play now Y value
            if ((mouseY >= ((height / 2) - (birdHeight / 2))) && (mouseY <= ((height / 2) + (birdHeight / 2)))) {
                
                playNow = playNowHover; //Change the play now button to seem selected
                
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) { //If play now is clicked
                    
                    Play.player.playerName = JOptionPane.showInputDialog("Please enter your name"); //Ask for name 
                    
                    if(Play.player.playerName.length() < 1){ //If a name is not given                      
                        
                        Play.player.playerName = "Rad Brad"; //Set name to Rad Brad 
                    
                    }
                    
                    if (draw == false) { //If a bird has not been selected
                        
                        startTime = System.currentTimeMillis(); //Start the timer 
                        
                        //Set default bird (pterodactyl) to the one and only EARMUFFS 
                        Play.player = new Bird("res/images/earmuffs.png", "res/images/earmuffsShooting.png", "res/audio/earmuffsScreech.wav");

                    }
                    
                    sbg.enterState(SettingUp.play); //Enter the Play State (i.e the game)
                
                }
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }

    public void playSound() { //Play them sick beats
        Play.menuSong.loop(); //ON LOOP
        menuSoundPlayed = true; //And make sure it isn't messed with
    }

}
