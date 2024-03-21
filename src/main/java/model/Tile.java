package model;

import java.util.ArrayList;


public class Tile {
    private String letter;
    private int x;
    private int y;

    /**
     * constructor, makes a new tile object
     * @param letter the letter on the tile
     * @param x the tile's x position in the grid (0-n)
     * @param y the tile's y position in the grid (0-n)
     */
    public Tile(String letter, int x, int y){
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    /**
     * getter for x position
     */
    public int getXPos(){
        return x;
    }

    /**
     * getter for y position
     */
    public int getYPos(){
        return y;
    }

    /**
     * getter for letter, returns String
     */
    public String getLetter(){
        return letter;
    }

    public static ArrayList<ArrayList<Tile>> stringGridToTiles(String gridString){
        
        ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
        String[] rows = gridString.split("\n");

        for(int i=0; i < rows.length; i++){
            String[] columns = rows[i].split("");
            ArrayList<Tile> rowTiles = new ArrayList<>();
            for(int j=0; j < columns.length; j++){
                rowTiles.add(new Tile(columns[j], i, j));
            }
            tiles.add(rowTiles);
        }
        return tiles;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (other.getClass() == String.class){
            String o = (String) other;

            if(!this.letter.equals(o)){
                return false;
            }
            return true;
        }
        Tile o = (Tile) other;

        if(!this.letter.equals(o.letter) || this.x != o.x || this.y!=o.y){
            return false;
        }
        return true;

        

    }
    
}
