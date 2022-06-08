import pkg.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Random; 
import java.util.Scanner; 
import java.io.File; 
import java.io.FileNotFoundException;
import java.lang.Thread; 

/*
Wordle but more 

1. Problem - not enough wordle 
2. Outline: 
    1. Array list - words to choose from 
    2. Methods: 
        1. Game: 
			i) boolean ifCorrect(Array) - loop that goes through each element of array 
					       - if statements —> if letter is in word vs in correct space 
			ii) drawGrid() - graphics —> draws boxes based on the length of word
			iii) drawLetter() - graphics —> draw letter of guess and color associated (grey, yellow green)

3. skip a few
4. yay were done?? clean things up
	graphics: 
	-timer for undrawing
	-when you lose - clear canvas--> show correct word and display u lose
5. If i want --> deploy on website???

*/

public class starter implements InputControl, InputKeyControl{
	public String userInput; 
	public static String[] correctWord; 
	public static String[] userWord = new String[5]; 
	public static boolean[] checker = new boolean[5]; 
	public static int row = 0; 
	public static int space = 0; 
	public static int lives = 0; 
	public static int graphicsCounter = 0;  
	public static Text notWord = new Text (0,0,""); 
	public static Text text = new Text (0,0,"");
	public static Text text2 = new Text(0,0,""); 
	public static Text text3 = new Text(0,0, ""); 
	public static Text text4 = new Text(0,0, ""); 
	public static Rectangle rect2;
	public static Line vertGrid; 
	public static Line horzGrid;
	public static boolean match; 
	public static Rectangle rect[][] = new Rectangle[5][5]; 
	public static Rectangle[] top; 
	public static Rectangle [] mid; 
	public static Rectangle [] bot;
	public static Text[] topt; 
	public static Text[] midt;
	public static Text[] bott;
	public static Text[][] display; 
	public static String qp; 
	public static String al; 
	public static String zm;
	public static String key; 
	public static String temp = ""; 
	public static char idk; 
	public static int win = 0; 
	public static ArrayList<String> list = new ArrayList<String>(); 
	public static Random rand = new Random(); 

	public static void greyKeyboard(String[] s){
	for (int i = 0; i<s.length; i++){
		String temp = s[i]; 
			if (qp.indexOf(temp) >= 0){
				top[qp.indexOf(temp)].setColor(new Color (128,128,128)); 
				top[qp.indexOf(temp)].fill(); 
			}
			else if (al.indexOf(temp) >= 0){
				mid[al.indexOf(temp)].setColor(new Color (128,128,128)); 
				mid[al.indexOf(temp)].fill(); 
			}
			else {
				bot[zm.indexOf(temp)].setColor(new Color (128,128,128)); 
				bot[zm.indexOf(temp)].fill(); 
			}	
		}
	}
	public static void checkWord(String[] array, int row){
		lives++;
		int temp ; 
		for (int i = 0; i < 5; i++)
			display[row][i].setColor(new Color (192,192,192));
		//checking green:
		for (int i = 0; i < 5; i++){
			if (correctWord[i].equals(array[i])){
				checker[i] = true; 
				display[row][i].setColor(new Color(0,225,0)); 
				display[row][i].draw(); 
				win++;
			}
		}
		//checking yellow: 
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++){
				if (correctWord[x].equals(array[y]) && x!=y && checker[x]==false){
					display[row][y].setColor(new Color(225,225,0)); 
					display[row][y].draw(); 
				}
			}
		}
	}
	public static void drawGrid(){
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++){
				rect[x][y] = new Rectangle ((100+70*y),(130+70*x), 70, 70); 
				rect[x][y].draw(); 
			}
		}
	}
	public static void drawKeyboard(){
		qp = "qwertyuiop"; 
		topt = new Text[10]; 
		top = new Rectangle [10]; 
		for (int i = 0; i < 10; i++){
			top[i] = new Rectangle (135+30*i, 500, 20, 25); 
			topt[i] = new Text (140+30*i, 505, qp.substring(i, i+1)); 
			top[i].draw(); 
			topt[i].draw(); 
		}
		al = "asdfghjkl"; 
		midt = new Text[9]; 
		mid = new Rectangle [9]; 
		for (int i = 0; i < 9; i++){
			mid[i] = new Rectangle (155 + 30*i, 530, 20, 25); 
			midt[i] = new Text (160 + 30*i, 535, al.substring(i,i+1)); 
			mid[i].draw(); 
			midt[i].draw(); 
		}
		zm = "zxcvbnm"; 
		bott = new Text [7]; 
		bot = new Rectangle [7]; 
		for (int i = 0; i < 7; i++){
			bot[i] = new Rectangle (185 + 30*i, 560, 20, 25); 
			bott[i] = new Text (190 + 30*i, 565, zm.substring(i, i+1)); 
			bot[i].draw(); 
			bott[i].draw(); 
		}
	}
	public static void drawLetter(String input, int space, int row){
		display[row][space] = new Text (130+70*space, 165+70*row, input.toUpperCase()); 
		display[row][space].grow(15,20); 
		display[row][space].draw(); 
	}
	public static void pickWord(){
		int chooser = rand.nextInt(list.size()) + 1; 
		String word = list.get(chooser); 
		for (int i = 0; i < 5; i++)
			correctWord[i] = word.substring(i,i+1).toLowerCase(); 
	}
	public static boolean isItaWord(String[] a){
		//converting userWord to a String
		String temp = ""; 
		for (int i = 0; i < 5; i++){
			temp = temp + a[i]; 
		}
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).equals(temp))
				return true; 
		}
		return false; 
	}
	public static void reset(){
		for (int i = 0; i < qp.length(); i++){
			top[i].setColor(new Color (0,0,0));
			top[i].fill(); 
		}
		for (int i = 0; i < al.length(); i++){
			mid[i].setColor(new Color(0,0,0)); 
			top[i].fill(); 
		}
		for (int i = 0; i < zm.length(); i++){
			bot[i].setColor(new Color(0,0,0)); 
			bot[i].fill(); 
		}
		for (int i = 0; i < 5; i++){
			userWord[i] = ""; 
			checker[i] = false; 
		}
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++)
				display[x][y].undraw();
		}
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++){
				display[x][y] = new Text (130+70*x, 70+70*y, " "); 
				display[x][y].draw(); 
			}
		}
			
	}
	public static void clear(){
		for (int i = 0; i < qp.length(); i++){
			top[i].undraw(); 
			topt[i].undraw(); 
		}
		for (int i = 0; i < al.length(); i++){
			mid[i].undraw(); 
			midt[i].undraw(); 
		}
		for (int i = 0; i < zm.length(); i++){
			bot[i].undraw(); 
			bott[i].undraw(); 
		}
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++){
				rect[x][y].undraw(); 
			}
		}
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++)
				display[x][y].undraw();
		}
		text.undraw(); 
		text2.undraw(); 
		text3.undraw(); 
		text4.undraw(); 
	}
	public static void newGame(){
		clear(); 
		drawGrid(); 
		drawKeyboard();
		pickWord();
		temp = ""; 
	}
	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		// please leave following line alone, necessary for keyboard/mouse input
		KeyController kC = new KeyController(Canvas.getInstance(),new starter());
		MouseController mC = new MouseController(Canvas.getInstance(),new starter());
		//picking word - initializing correctWord
		correctWord = new String[5]; 
		File text = new File("wordList.txt"); 
		Scanner sc = new Scanner(text); 
		
		//initializing userWord to empty and check to all false
		for (int c = 0; c < 5; c++){
			userWord[c] = ""; 
			checker[c] = false; 
		}
		//initializing the empty grid of Texts
		display = new Text[5][5]; 
		for (int x = 0; x < 5; x++){
			for (int y = 0; y < 5; y++){
				display[x][y] = new Text (130+70*x, 70+70*y, " "); 
				display[x][y].draw(); 
			}
		}
		//adding txt file to
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			list.add(line); 
		}
		drawGrid(); 
		drawKeyboard(); 
		pickWord();
	}
	public void onMouseClick(double x, double y) {
		// enter code here
		if (x >= 263 && x <= 330 && y >= 385 && y <= 405)
			text.undraw(); 
			text2.undraw();
			text3.undraw(); 
			text4.undraw(); 
			rect2.undraw(); 
			newGame(); 
			space = 0; 
			row = 0; 
			lives = 0; 
	
		if (x >= 485 && x <= 552 && y >= 295 && y <= 310){
			newGame(); 
			space = 0; 
			row = 0; 
			lives = 0; 
		}
	}
	public void keyPress(String s) {
		String abc = "abcdefghijklmnopqrstuvwxyz"; 
		char delete = (char)8; 
		String deletet = Character.toString(delete); 
		char enter = (char)10; 
		String entert = Character.toString(enter); 

		if(s.equals(entert) && space == 5 && isItaWord(userWord)){
			greyKeyboard(userWord);
			checkWord(userWord,row);
				if (win == 5){
					text = new Text(275, 75, "You Won"); 
					text.setColor(new Color(0,225,0)); 
					text.grow(160, 55); 
					text.draw(); 
					text2 = new Text (502, 300, "new game"); 
					text2.grow(15,5); 
					text2.draw(); 
					rect2 = new Rectangle (485,295, 67,20); 
					rect2.draw(); 
				}
				if (win < 5 && row == 4){
					clear(); 
					text = new Text(275,250, "GAME OVER LOSER"); 
					text.setColor(new Color (215, 0, 0)); 
					text.grow(160,55); 
					text.draw(); 
					text2 = new Text(210, 340, "Correct word was:"); 
					text2.grow(80,20); 
					text2.draw(); 
					//turning correctWord[] into string 
					for (int i = 0; i < 5; i++)
						temp = temp + correctWord[i]; 
					text3 = new Text (395, 340, temp); 
					text3.grow(30,10); 
					text3.draw(); 
					text4 = new Text(280, 390, "new game"); 
					text4.grow(15,5); 
					text4.draw(); 
					rect2 = new Rectangle(263,385, 67, 20); 
					rect2.draw(); 
				}
			space = 0; 
			row++; 
			win = 0; 
		}
		if(s.equals(entert) && space == 5 && !isItaWord(userWord)){
			notWord = new Text(250,60, "This is not a word"); 
			notWord.grow(100,32); 
			notWord.draw(); 
		}		
		if (s.equals(deletet) && space == 5)
			notWord.undraw(); 
		if (s.equals(deletet) && space >= 1){
			space--; 
			display[row][space].undraw(); 
		}
		if (abc.indexOf(s) >= 0 && space != 5 && row <= 4){ 
			userWord[space] = s; 
			drawLetter(s,space,row); 
			space++; 	
		}
	}
}
