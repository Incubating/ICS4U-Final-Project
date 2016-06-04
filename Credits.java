package strategictoastinsertion;

//Imports
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Jonah Monaghan
 * @version 1.00
 */
public class Credits extends BasicGameState {

    public Credits(int state){
        
    }
    
    
    @Override
    public int getID() {
        return 2;
    }
    Image bg, backButton, hover;
    String prevScore; 
    int savesCounter = 0, mouseX, mouseY, scoreSearch = 0;
    Input input;
    static ArrayList<String> names = new ArrayList();
    static ArrayList<Integer> scores = new ArrayList();
    boolean start = false;
    static int linearCounter = 0;
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("res/images/creditsScreen.png");
        backButton = new Image("res/images/back.png");
        hover = new Image("res/images/resolutionHighlight.png"); 
        start = true;
        Play.player.playerName = "GADFREY";
    }
    //Font font = new Font("Palatino Linotype", Font.BOLD, 32);
    //TrueTypeFont ttf = new TrueTypeFont(font,true);
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        bg.draw(0,0);
        //ttf.drawString(32.0f, 32.0f, (Play.player.playerName + ": " + Play.number.format(Play.player.score)), Color.white);
        g.drawString( Play.player.playerName+Play.player.score, SettingUp.width/10, SettingUp.height-(2*(SettingUp.height/6))+5);
        for(int ss = 0; ss < 5; ss++){
            g.drawString( (names.get(ss)+scores.get(ss)), SettingUp.width/10, ((SettingUp.height/6)+(ss*50)) );
            
        }
        //g.drawString(prevScore, SettingUp.width/10, SettingUp.height-(SettingUp.height/6));
        
        if(mouseX < 200 && mouseY < 100){
            backButton.draw(0,0);
            hover.draw(0,0);
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) {
                //rewrite();
                sbg.enterState(SettingUp.menu);
                Play.isAlive = true;
                Play.deathTime = -1;
                Play.arrayMade = false;
                Play.toasters = new ArrayList();
                Play.player.playerName = "GADFREY";
                start = true;
            }
        }else{ 
            backButton.draw(0,0);
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(start){
            loadSaves();
            start = false;
        }
        
        input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        
    }
    
    public static int linearSearch(ArrayList<String> A, String x){
        for (int i = 0; i < A.size(); i++){
            linearCounter++;
            if(((A.get(i)).equals(x))) return i;
        }
        return -1;
    }
    public void loadSaves(){
        boolean eof = false;
        String n = "", s = "";
        try{
            FileReader file = new FileReader("res/save.txt");
            BufferedReader buff = new BufferedReader(file);
            while(!eof){
                n = buff.readLine();
                if(n == null){
                    eof = true;
                }else{
                    n += ": ";
                    s = buff.readLine();
                    names.add(n);
                    scores.add(Integer.parseInt(s));
                    savesCounter++;
                }
                
            }
        }catch(IOException e){
            System.out.println(e);
        }
        Play.player.playerName+=": ";
        names.add(Play.player.playerName); 
        scores.add(Play.player.score);
        savesCounter++;
        scoreSearch = linearSearch(names, Play.player.playerName);
        if(scoreSearch == -1){
            prevScore = "No previous scores found";
        }else{
            prevScore = "Previous Score: " + scores.get(scoreSearch);
        }
        System.out.println(prevScore);
        AscendingSorter.quickSort(scores);
        scores = AscendingSorter.getArray();
    }
    public void rewrite(){
        Writer writer = null;
        try {
        writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream("res/save.txt"), "utf-8"));
            for(int f = 0; f < savesCounter; f++){
                writer.write(names.get(f));
                writer.write(scores.get(f));
                System.out.println(names.get(f)+scores.get(f));
            }
        } catch (IOException ex) {} finally {try {writer.close();} catch (Exception ex){}}
    }
}