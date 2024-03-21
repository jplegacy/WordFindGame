package model.wordType;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

import model.word.SequenceWord;


public class WordTypeFactory {

    private static final int BONUS_LENGTH = 5;

    /**
     * Does special word assignments with respect to other Words
     * @param wordMap a dictionary of Word objects
     */
    public static void specialAssignments(Map<String, SequenceWord> wordMap){
        wordOfDayAssignment(wordMap);
    }

    
    private static void wordOfDayAssignment(Map<String, SequenceWord> wordMap){
        ZoneId zoneId = ZoneId.of( "America/Montreal" );
        LocalDate date = LocalDate.now( zoneId );

        ZonedDateTime todayStart = date.atStartOfDay( zoneId );

        Random generator = new Random(todayStart.toEpochSecond());
        int seed = 0;
        if(wordMap.keySet().size() != 0){
            seed = generator.nextInt(0, wordMap.keySet().size());
        }
        List<String> wordList = new ArrayList<String>(wordMap.keySet());
        Collections.sort(wordList);
        
        String wordOfDayString = wordList.get(seed);
        wordMap.get(wordOfDayString).addTypes(new Day());
    }


    /**
     * Returns wordTypes associated to a specified word 
     * @param word of interest
     * @return types associated to word
     */
    public static Set<WordType> getTypes(String word){
        Set<WordType> types = new HashSet<>();
        if(word.length() < 3){
            types.add(new Invalid());
        } else if (word.length() >= BONUS_LENGTH) {
            types.add(new Bonus());
            types.add(new Valid());
            types.add(new Required());
        } else{        
            types.add(new Valid());
            types.add(new Required());
        }

        // this checks if a word is associated to an emoji based on their tag or alias,
        // if it contains any of them,
        // classify the word as a an EmojiType
        if (EmojiManager.getForTag(word) != null) {
            Emoiji newEmoji = new Emoiji();
            Set<Emoji> emojiSet = EmojiManager.getForTag(word);
            ArrayList<Emoji> emojiList = new ArrayList<Emoji>();
            for (Emoji emoji : emojiSet) {
                emojiList.add(emoji);
            }
            Emoji emojiChoice = emojiList.get(0);
            String emojiIcon = emojiChoice.getUnicode();
            newEmoji.updateEmojiIcon(emojiIcon);
            types.add(newEmoji);
        } else if (EmojiManager.getForAlias(word) != null) {
            Emoiji newEmoji = new Emoiji();
            Emoji emojiChoice = EmojiManager.getForAlias(word);
            String emojiIcon = emojiChoice.getUnicode();
            newEmoji.updateEmojiIcon(emojiIcon);
            types.add(newEmoji);
        }


        return types;
    }
}
