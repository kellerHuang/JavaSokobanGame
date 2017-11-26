import java.util.ArrayList;

/**
 * Created by mw on 9/05/2017.
 */
public class GameState {
    private int moves;
    //Time can be set to a different type later
    private int time;
    //Hints if we have a solve working
    //private int Hints;
    Map currMap;

    public GameState(Map currMap, int currMoves, int time){
        this.currMap = currMap;
        moves = currMoves;
        this.time = time;
        printMap();
    }

    public int getMoves(){
        return moves;
    }

    public int getTime(){
        return time;
    }

    public Map getCurrMap(){
        return currMap;
    }

    public void printMap(){
        int[][] map = currMap.getLocations();
        ArrayList<Integer> mapsize = currMap.getMapSize();
        for(int i = 0; i < mapsize.get(1); i++){
            for(int m = 0; m < mapsize.get(0); m++){
                System.out.printf("[" + map[i][m] + "]");
            }
            System.out.println();
        }
    }
}
