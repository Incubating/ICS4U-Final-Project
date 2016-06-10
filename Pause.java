package strategictoastinsertion;

/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: The state entered when the game is paused
 */
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;

/**
 *
 * @author Jonah Monaghan
 */
public class Pause extends BasicGameState {

    Input input;//variable to store input
    float alpha = 0;//variable to store alpha composition

    public Pause(int State) {

    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Play.bg.draw(Play.bgX1, Play.bgY);//draw backgrounds
        Play.bg.draw(Play.bgX2, Play.bgY);
        
        //draw bird
        Play.bird.draw(Play.player.getxPos(), Play.player.getyPos(), Menu.birdHeight, Menu.birdWidth);

        //draw toast bullets
        for (ToastBullet currentBullet : Play.bullets) {//loop through arraylist of bullets
            Play.pew = new Image(currentBullet.getImageString());
            //draw bullets
            Play.pew.draw(currentBullet.getxPos(), currentBullet.getyPos(), Play.bulletHeight, Play.bulletWidth);
        }

        //draw toasters
        for (ToasterBlock toaster : Play.toasters) {//loop through arraylist of toasters
            //draw toasters
            toaster.toasterImg.draw(toaster.xPos, toaster.yPos, Menu.birdHeight, Menu.birdWidth);
        }

        Rectangle rect = new Rectangle(0, 0, Menu.width, Menu.height);//create a new rectangle
        g.setColor(new Color(0.2f, 0.2f, 0.2f, alpha));//create a new colour at that alpha
        g.fill(rect);//draw that rectangle
        if (alpha < 0.5f) {//increase alpha gradually
            alpha += 0.01f;
        }
    }

  
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        input = gc.getInput();//get input
        if (input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_ESCAPE)) {//if paused key pressed
            alpha = 0;//reset alpha
            sbg.enterState(SettingUp.play);//return to play
        }
    }

}