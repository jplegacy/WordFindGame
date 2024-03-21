package model.wordType;

public class Invalid extends WordType{
    public Invalid(){
        type = "Invalid";
    }
    public boolean instanceOf(Class<?> c) {
        return Invalid.class.equals(c);
    }
}
