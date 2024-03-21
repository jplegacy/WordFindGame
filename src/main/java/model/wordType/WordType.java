package model.wordType;

public abstract class WordType {
    protected String type;
    
    public abstract boolean instanceOf(Class<?> c);

    public String getType(){
        return type;
    };
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        WordType o = (WordType) other;

        if(!this.type.equals(o.type)){
            return false;
        }
        return true;
    }


}