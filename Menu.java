package strategictoastinsertion;

//Imports
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Jonah Monaghan & Matthew Godfrey & Seth Thompson
 * @version 1.20
 */
public class Menu extends BasicGameState {

    //Constructor
    public Menu(int state) {

    }

    //Variable declarations
    //Input variables
    Input input; //input [pretty self explanitory]

    //Image variables / arrays
    Image baseMenu, options, optionsHover, playNow, playNowHover, selectedCircle; //Menu pics
    Image birds[] = new Image[7]; //All birds
    Image birdsHover[] = new Image[7]; //All birds after mouse interaction
    String birdScreech[] = new String[7];//Death cry of all birds

    //Integers
    static int width = SettingUp.width; //width of screen
    static int height = SettingUp.height; //height of screen
    int centerX = width / 2;
    int centerY = height / 2;

    int mouseX;
    int mouseY;

    static int birdWidth = width / 10;
    static int birdHeight = height / 6;

    static long startTime;
    static long currentTime;
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        //Assign images
        baseMenu = new Image("res/images/menu.png");
        options = new Image("res/images/options.png");
        optionsHover = new Image("res/images/optionsHighlighted.png");
        playNow = new Image("res/images/play.png");
        playNowHover = new Image("res/images/playHighlighted.png");
        selectedCircle = new Image("res/images/selectedButton.png");
        
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
    float[][] coords = new float[7][7];

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
    }
    boolean draw = false;
    float circleX, circleY;
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        getCoordinates(); //Grab bird coordinates
        baseMenu.draw(0, 0, width, height); //Draw the menu img
        playNow.drawCentered(width / 2, height / 2); //Draw the "Play Now" button in the middle
        options.draw(0, height / 30);
        if ((mouseX < 200) && (mouseY <= 100) ){
                options = optionsHover;
        } else {
            options = new Image("res/images/options.png");
        }
        if(draw == true){
            selectedCircle.draw(circleX, circleY);
        }
        
        for (int j = 0; j < 7; j++) {
            if (((mouseX > coords[j][0]) && (mouseX < (coords[j][0] + birdWidth)))
                    && (((mouseY > coords[j][1]) && (mouseY < (coords[j][1] + birdHeight))))) {
                birdsHover[j].draw(coords[j][0], coords[j][1], birdWidth, birdHeight);
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) {
                    draw = true;
                    circleX = coords[j][0];
                    circleY = coords[j][1];
                    Play.player = new Bird(birds[j].getResourceReference(), birdsHover[j].getResourceReference(), birdScreech[j]);
                }
                
            } else {
                birds[j].draw(coords[j][0], coords[j][1], birdWidth, birdHeight);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        playNow = new Image("res/images/play.png");
        if ((mouseX >= ((width / 2) - (birdWidth/2))) && (mouseX <= ((width / 2) + (birdWidth/2)))) {
            if ((mouseY >= ((height / 2) - (birdHeight/2))) && (mouseY <= ((height / 2) + (birdHeight/2)))) {
                playNow = playNowHover;
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) {
                    if(draw == false){
                        startTime = System.currentTimeMillis();
                        Play.player = new Bird(birds[1].getResourceReference(), birdsHover[1].getResourceReference(), birdScreech[1]);
                    }
                    sbg.enterState(SettingUp.play);
                }
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}