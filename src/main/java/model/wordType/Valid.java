package model.wordType;

public class Valid extends WordType{
    public Valid(){
        type = "Valid";
    }
    
    public boolean instanceOf(Class<?> c) {
        return Valid.class.equals(c);
    }
}
