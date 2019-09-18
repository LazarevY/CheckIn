package system;

public class GeneratorID {
    private static long nextID = 0;
    public static long geneateUserID(){
        return nextID++;
    }
}
