import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.lang.Math;

public class Save {
	
	public void saveGame(String matrixString, int slot) {

		Properties prop = new Properties();
		OutputStream output = null;

		try {

			File f = new File("save.properties");
			
			if(f.exists() && !f.isDirectory()) {
				prop.load(new FileInputStream("save.properties"));
			}
			
			output = new FileOutputStream("save.properties");
			
			// set the properties value
			if (slot == 1) {
				prop.setProperty("save1", matrixString);
			} else if (slot == 2) {
				prop.setProperty("save2", matrixString);
			} else if (slot == 3) {
				prop.setProperty("save3", matrixString);
			}

			// save properties to root folder
			prop.store(output, null);

		} catch (IOException io) {
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}

		}
	}
	
	public String matrixToString(Map m) {
		
		int i = 0, j = 0, mapX = m.getMapSize().get(0), mapY = m.getMapSize().get(1);
		StringBuilder string = new StringBuilder();
		
		
		for (i = 0; i < mapY; i++) {
			for (j = 0; j < mapX; j++) {
				string.append(Integer.toString(m.getLocations()[i][j]));
			}
		}
		return string.toString();
	}
	
	public boolean loadGame(int slot, GameEngine currGame) {
		Properties prop = new Properties();
		InputStream input = null;
		String s = null;
		Boolean b = true;

		try {

			File f = new File("save.properties");
			
			if(f.exists() && !f.isDirectory()) {
				input = new FileInputStream("save.properties");				

				// load a properties file
				prop.load(input);

				// get the property value and print it out
				if (slot == 1) {
					s = prop.getProperty("save1");
				} else if (slot == 2) {
					s = prop.getProperty("save2");
				} else if (slot == 3) {
					s = prop.getProperty("save3");
				}
				
				if (s == null) {
					b = false;
				} else {
					GameState gS = new GameState(stringToMap(s), 0, 0);
					currGame.setState(gS);
				}
			} else {
				b = false;
			}
		} catch (IOException ex) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		return b;
	}
	
	private Map stringToMap (String s) {
		
		int strlen = (int)Math.sqrt(s.length());		
		int i = 0, j = 0, index = 0;
		int[][] matrix = new int[strlen][strlen];
		for (i = 0; i < strlen; i++) {
			for (j = 0; j < strlen; j++) {
				matrix[i][j] = Character.getNumericValue(s.charAt(index));
				index++;
			}
		}
		
		return new Map(matrix, strlen, strlen);
	}
	
}
