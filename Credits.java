package strategictoastinsertion;
/*
 Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: The state for post-gameplay credits and score/highscore display
 */
//Imports
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;

public class Credits extends BasicGameState {
    //constructor
    public Credits(int state) {

    }
    //state checker
    @Override
    public int getID() {
        return 2;
    }
    
    Image bg, backButton, hover;//back button, background, and hover for the back button Images
    String prevScore;//holds the highest of the user's previous scores, if one exists
    int savesCounter = 0, mouseX, mouseY, scoreSearch = 0, buttonHeight = SettingUp.height/6, buttonWidth = SettingUp.width/5;//numbers for mouse and button coordinates, and score saves
    Input input;//gets data for the mouse coordinates
    static ArrayList<String> names = new ArrayList();//holds all the names of previous users
    static ArrayList<Integer> scores = new ArrayList();//holds all the scores of previous users
    static boolean start = false, creditsSoundPlayed = false;//check for actions occuring
    static int linearCounter = 0;//counts how many scores have been searched through so far
    static File file;//holds the file that names and scores are read from
    TrueTypeFont ttf;//font for drawing names and scores on screen

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        file = new File("res/save.txt");//save file that holds user's names and scores
        bg = new Image("res/images/creditsScreen.png");//background screen
        backButton = new Image("res/images/back.png");//back button that takes user to the main menu
        hover = new Image("res/images/resolutionHighlight.png");//appears when user hovers the mouse over the back button
        Font font = new Font("Palatino Linotype", Font.PLAIN , 28);//creates a nice font to draw the names and scores in
        ttf = new TrueTypeFont(font, true);//makes the font applicable to the screen
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        bg.draw(0, 0);//draws the background from the top corner inwards
        ttf.drawString((SettingUp.width / 5), (SettingUp.height - (2 * (SettingUp.height / 6))), (Play.player.playerName + ": " + Play.number.format(Play.player.score)), Color.white);//draws the user's name and score on the dark bar on the screen
        for (int ss = 0; ss < 5; ss++) {
            ttf.drawString(SettingUp.width / 5, ((SettingUp.height / 6) + (ss * 55)), (names.get(ss) + ": " + Play.number.format(scores.get(ss))), Color.white);//draw the five highest scores in descending order above the isers scores just to make the user feel bad
        }
        ttf.drawString(SettingUp.width / 5, SettingUp.height - (SettingUp.height / 6), prevScore, Color.white);//draw the highest previous score that the user might have had, depencding on the search results o the name the user gave at the start
        if (mouseX < buttonWidth && mouseY < buttonHeight) {//if the mouse is over the back button
            backButton.draw(0, 0);//draw the button
            hover.draw(0, 0);//and also draw the rectangle that shows the user has their mouse above the 
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) == true) {//if the user clicks over the back button
                file.delete();//delete the current file
                reload();//remake the file with the user's data
                Menu.menuSoundPlayed = false;//restart the music in the menu
                sbg.enterState(SettingUp.menu);//enter the menu state
            }
        } else {
            backButton.draw(0, 0);//draw only the back button because the mouse isn't over the back button
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
         if (!creditsSoundPlayed) {//if the program hasn't started the credits screen music
            playSound();//play the credits music
        }
        if (start) {//a boolean to make sure the following code is only run once
            loadSaves();//calls a method to read all the data from the save file
            scoreSearch = linearSearch(names, Play.player.playerName);//calls a method to search for the user's name in the save file
            names.add(Play.player.playerName);//adds the user's name to the arraylist containing the names
            scores.add(Play.player.score);//adds the user's score to the arraylist containing the scores
            savesCounter++;//adds one to the number of saves
            if (scoreSearch == -1) {//the linear search returns -1 if the search comes up empty
                prevScore = "No previous scores found";//this will display on the screen
            } else {
                prevScore = "Previous Score: " + scores.get(scoreSearch);//this will display the highest previous score under the user's name
            }
            AscendingSorter.quickSort(scores);//sorts the scores into descending order, highest number first
            scores = AscendingSorter.getArray();//rearranges the scores into the sorted formation
            start = false;//makes it so that the above code will only run once
        }
        input = gc.getInput();//collects input data
        mouseX = input.getMouseX();//gets the mouse X coordinate from the input data
        mouseY = input.getMouseY();//gets the mouse Y cooordinate from the input data

    }

    public void reload() {
        rewrite();//write the names and scores, including the current user's name and score, to a save file
        //reset all the variables that have been changed to a post-play state
        Play.isAlive = true;
        Play.deathTime = -1;
        Play.arrayMade = false;
        Play.toasters = new ArrayList();
        Play.difficulty = 0;
        Play.player.score = 0;
        Play.player.setxPos(25);
        Play.player.setyPos(100);
        ToasterBlock.CURRENT_SPEED = 2;
        names = new ArrayList();
        scores = new ArrayList();
        start = true;
    }

    public static int linearSearch(ArrayList<String> A, String x) {
        for (int i = 0; i < A.size(); i++) {//search throught the full length of the arraylist
            linearCounter++;//count the number of elements searched through
            if (((A.get(i)).equals(x))) {//if the current element is the same as the user's name
                return i;//return the index of the matching elemeent
            }
        }
        return -1;
    }

    public void loadSaves() {
        boolean eof = false;//a boolean that trcks whether or not the file has reached an end
        String n, s;//temporary strings used for transferring names and scores into their arraylists
        try {
            //BufferedReader reader = new BufferedReader(new InputStreamReader(strategictoastinsertion.Credits.getResourceAsStream("save.txt")));
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new FileReader("res/save.txt"));//make a reader to read data from the save file
            while (!eof) {//keeps reading until the file has ended
                n = reader.readLine();//read the first field
                if (n == null) {
                    eof = true;//if the first field is null, notify the loop of the file end
                } else {
                    s = reader.readLine();//read the second field
                    names.add(n);//add the first field to the names arraylist
                    scores.add(Integer.parseInt(s));//add the second field to the scores arraylist
                    savesCounter++;//increases the counting of saves by one
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void rewrite() {
        try (PrintWriter writer = new PrintWriter(file)) {//opens up a writer
            for (int wr = 0; wr < names.size(); wr++) {//loop for the length of the names arraylist
                writer.println(names.get(wr));//write the current name to the new file
                writer.println(scores.get(wr));//write the current score to the new file
            }
            writer.close();//close up the writer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        Play.creditsSong.loop();//play the credits screen music in a loop
        creditsSoundPlayed = true;//set a boolean so this code only executes once
    }

}