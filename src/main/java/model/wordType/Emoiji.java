package model.wordType;

public class Emoiji extends WordType {
    private String emojiIcon;

    public Emoiji() {
        type = "Emoji";
        emojiIcon = "";
    }

    /**
     * This updates the emojiIcon to hold an emoji representation
     * 
     * @param emojiPresent the emoji representation value
     */
    private void setEmojiIcon(String emojiPresent) {
        emojiIcon = emojiPresent;
    }

    /**
     * This gives you the emoji representation for a given Word associated
     * with it
     * 
     * @return the emoji representation value
     */
    public String getEmoji() {
        return this.emojiIcon;
    }

    /**
     * This is a public function to update the emoji representation value
     * 
     * @param emojString the new emoji representation to be updated with
     */
    public void updateEmojiIcon(String emojiString) {
        this.setEmojiIcon(emojiString);
    }

    public boolean instanceOf(Class<?> c) {
        return Emoiji.class.equals(c);
    }
}
