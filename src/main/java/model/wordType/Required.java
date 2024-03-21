package model.wordType;

public class Required extends WordType{
    public Required(){
        type = "Required";
    }
    public boolean instanceOf(Class<?> c) {
        return Required.class.equals(c);
    }
}
