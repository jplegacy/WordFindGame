package model;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class WordDatabase {
    private Set<String> inMemoryCorpus;

    /**
     * Constructor for an in house corpus database where files are parsed from a txt file
     * where every line contains a single word
     * 
     * @param corpusPath file to corpus file
     * @throws IOException if next line in file cannot be read
     */
    public WordDatabase(String corpusPath) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(corpusPath));
        inMemoryCorpus = new HashSet<>(allLines);

    }
    public WordDatabase(Set<String> words) {
        inMemoryCorpus = new HashSet<>(words); // Copies set

    }
    /**
     * Checks if word is in corpus or not
     * @param word to check in corpus
     * @return if word is contained in the corpus
     */
    public boolean contains(String word){
        // Add Policies on words here

        if(word.length() <= 3){
            return false;
        }

        return inMemoryCorpus.contains(word);
    }

    }

    

