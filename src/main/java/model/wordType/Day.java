package model.wordType;

public class Day extends WordType{
    public Day(){
        type = "Day";
    }
    public boolean instanceOf(Class<?> c) {
        return Day.class.equals(c);
    }
}
