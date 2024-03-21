package model.wordType;

public class Bonus extends WordType{
    public Bonus(){
        type = "Bonus";
    }
    public boolean instanceOf(Class<?> c) {
        return Bonus.class.equals(c);
    }
}
