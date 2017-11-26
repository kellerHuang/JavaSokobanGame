import java.util.ArrayList;

/**
 *
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

/*
Key for map
0: Empty tile
1: player facing up
2: player facing right
3: player facing down
4: player facing left
5: box that can be pushed
6: wall
7: goal tile
8: goal tile with box on top
9: player on goal facing up
10: player on goal facing right
11: player on goal facing down
12: player on goal facing left
*/
public class Map {
    public static final int EMPTY = 0;
    public static final int MOVE_UP = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_DOWN = 3;
    public static final int MOVE_LEFT = 4;
    public static final int BOX = 5;
    public static final int WALL = 6;
    public static final int GOAL = 7;
    public static final int GOAL_BOX = 8;
    public static final int PLAYER_ON_GOAL_UP = 9;
    public static final int PLAYER_ON_GOAL_RIGHT = 10;
    public static final int PLAYER_ON_GOAL_DOWN = 11;
    public static final int PLAYER_ON_GOAL_LEFT = 12;


    private int[][] locations;
    private int mapSizex;
    private int mapSizey;

    /**
     * initialise map
     * @param input
     * @param mapSizex - horizontal size of map
     * @param mapSizey - vertical size of map
     */
    public Map(int[][] input, int mapSizex, int mapSizey){
        this.mapSizex = mapSizex;
        this.mapSizey = mapSizey;
        locations = new int[mapSizey][mapSizex];
        initLocations(input);
        System.out.println("First: " + mapSizey + " Second: " + mapSizex);
    }


    public int[][] getLocations(){
        return locations;
    }

    /**
     *  a method that takes in an arraylist of x ordinates and y ordinates of tiles
     *that need to be changed and changes them to their respective value stored in changeTo
     * @param xOrdinate - x-coord to be changed
     * @param yOrdinate - y-coord to be changed
     * @param changeTo - value to be changed to
     */
    public void changeTile(int xOrdinate, int yOrdinate, int changeTo){
        System.out.println("Changing " + yOrdinate + " " + xOrdinate + " to " + changeTo);
        locations[yOrdinate][xOrdinate] = changeTo;
    }

    /**
     * Check if player is out of map bounds.
     * @param xOrdinate - current x-coord
     * @param yOrdinate - current y-coord
     * @return -1 if out of bounds, current location otherwise.
     */
    public int checkTile(int xOrdinate, int yOrdinate){
        System.out.println("Checking " + yOrdinate  + " " + xOrdinate);
        if(xOrdinate >= mapSizex || xOrdinate < 0)return -1;
        if(yOrdinate >= mapSizey || yOrdinate < 0)return -1;
        return locations[yOrdinate][xOrdinate];
    }

    /**
     * Check the current location of the player
     * @return arraylist with first value being x value and second being y value,
     *         if player not found returns empty
     */
    public ArrayList<Integer> checkPlayerLocation(){
        ArrayList<Integer> locationArray = new ArrayList<Integer>();
        for(int i = 0; i < mapSizey ; i++){
            for(int m = 0; m < mapSizex; m++){
                if((locations[i][m] >= MOVE_UP && locations[i][m] <= MOVE_LEFT) || (locations[i][m] >= PLAYER_ON_GOAL_UP && locations[i][m] <= PLAYER_ON_GOAL_LEFT)){
                    locationArray.add(new Integer(i));
                    locationArray.add(new Integer(m));
                    return locationArray;
                }
            }
        }
        return locationArray;
    }

    /**
     * Get the size of the map
     * @return ArrayList containing x-size and y-size
     */
    public ArrayList<Integer> getMapSize(){
        ArrayList<Integer> returnArray = new ArrayList<Integer>();
        returnArray.add(mapSizex);
        returnArray.add(mapSizey);
        return returnArray;
    }

    //Creates a deep copy of the matrix.
    private void initLocations(int[][] input) {
        if (input == null) {
            return;
        }

        for (int i = 0; i < mapSizey; i++) {
            for (int j = 0; j < mapSizex; j++) {
                locations[i][j] = input[i][j];
            }
        }
    }
}
