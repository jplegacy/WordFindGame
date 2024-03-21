package model.word;

import java.util.ArrayList;

import model.Tile;

public class SequenceWord extends Word{
    private ArrayList<Tile> sequenceTiles;
    public SequenceWord(String word, ArrayList<Tile> tiles){
        super(word);
        sequenceTiles = tiles;
    }

    public ArrayList<Tile> getSequence(){
        return (ArrayList<Tile>) sequenceTiles.clone();
    }
}
