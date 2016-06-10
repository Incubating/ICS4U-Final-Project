package strategictoastinsertion;
/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: The class that sets up the "game container" which is the basic interface/display of the game
 */
//Imports
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class SettingUp extends StateBasedGame {
 
    static int width = 1000;//width of the screen
    static int height = 600;//height of the screen
   
    static boolean fullscreen = false;//whether or not the screen is in fullscreen mode
    static boolean showFPS = true;//displays the frame per second on the screen
    
    static final String title = "Strategic Toast Insertion (STI)";//title for the program window
    static final String icon = "res/images/16x16.png";//attempt at an icon for the program to display in the taskbar
    static final int fpslimit = 60;//how many frames to cyclce through per second
    static final int menu = 0;//integer to represent the menu state
    static final int play = 1;//integer to represent the play state
    static final int credits = 2;//integer to represent the credits state
    static final int pause = 3;//integer to represent the pause state
    //static final String[] icons = {"res/images/32x32.png", "res/images/24x24.png", "res/images/16x16.png"};//another attempt at icons for the program to display in th taskbar
    static AppGameContainer app;
    
    public SettingUp(String title) {
        super(title);//set the title of the program window
        //create all the states that the game can be in
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.addState(new Credits(credits));
        this.addState(new Pause(pause));
    }
 
     @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        if (app instanceof AppGameContainer) {//check if the app is the correct container to use these sorts of icons
            AppGameContainer appContainer = (AppGameContainer) app;//make an app container to display the icons
            appContainer.setTitle(title);//assign a title to the container
            if (!appContainer.isFullscreen()) {//can't set icons if game is in full screen
                String[] icons = {"res/images/32.png", "res/images/24.png", "res/images/16.png"};//set three images for icon usage
                app.setIcons(icons);//set the icons for the program in the taskbar, window pane, etc
            }
        }
        //initialise all the states that the game can be in
        this.getState(menu).init(gc, this);
        this.getState(play).init(gc, this);
        this.getState(credits).init(gc, this);
        this.getState(pause).init(gc, this);
        this.enterState(menu);//start the game in the menu state
    }
   
    public static void main(String[] args) throws SlickException {
        try{
            app = new AppGameContainer(new SettingUp(title));//creates the window and sets the title
            app.setDisplayMode(width, height, fullscreen);//makes the window a certain height and width, also can set to fullscreen
            app.setSmoothDeltas(true);
            app.setTargetFrameRate(fpslimit);//sets the frame rate
            app.setShowFPS(showFPS);//shows the frame rate on the screen
            app.start();//starts the game
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}