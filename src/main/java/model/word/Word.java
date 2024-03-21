package model.word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.Tile;
import model.wordType.WordType;
import model.wordType.WordTypeFactory;

public class Word {
    private String word;
    private Set<WordType> types;
    // public HashMap<String, String> qualities;
    private ArrayList<Tile> sequenceTiles;


    public Word(String word){
        this.word = word.toLowerCase();
        this.types = WordTypeFactory.getTypes(word);
    }

    public String getWord() {
        return word;
    }
    public String toString(){
        return word;
    }

    public Set<WordType> getTypes(){
        return new HashSet<>(types);
    }
    public void addTypes(WordType newType){
        types.add(newType);
    }

    /**
     * this gets a specific type found within a word object, if it finds it, it
     * returns
     * it, if it does not find it, returns null
     * 
     * @param type the specific WordType you want to see if the Word has an
     *             assocition with it
     * @return the WordType specified found within the Word object, otherwise,
     *         return null
     */
    public WordType getType(Class<?> typeTarget) {
        for (WordType type : types) {
            if (type.getClass().equals(typeTarget)) {
                return type;
            }
        }
        return null;
    }

    public boolean containsInstanceOf(Class<?> c) {
        for (WordType type : types) {
            if (type.instanceOf(c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Word o = (Word) other;


        if(!this.word.equals(o.toString())){
            return false;
        }
        return true;
    }
}
