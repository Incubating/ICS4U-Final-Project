package strategictoastinsertion;

//Imports
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * 
 * @author Jonah Monaghan
 * @version 1.20
 */
public class SettingUp extends StateBasedGame {
 
    static int width = 1000;
    static int height = 600;
   
    static boolean fullscreen = false;
    static boolean showFPS = true;
    
    static final String title = "Strategic Toast Insertion (STI)";
    static final String icon = "res/images/oh noes.jpg";
    static final int fpslimit = 60;
    static final int menu = 0;
    static final int play = 1;
    static final int credits = 2;
    
    public SettingUp(String title) {
        super(title);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.addState(new Credits(credits));
    }
 
     @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(menu).init(gc, this);
        this.getState(play).init(gc, this);
        this.getState(credits).init(gc, this);
        this.enterState(menu);
    }
   
    public static void main(String[] args) throws SlickException {
      
        AppGameContainer app; 
        try{
            app = new AppGameContainer(new SettingUp(title));
            app.setDisplayMode(width, height, fullscreen);
            app.setSmoothDeltas(true);
            app.setTargetFrameRate(fpslimit);
            app.setShowFPS(showFPS);
            app.setIcon(icon);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
   
}