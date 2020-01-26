import java.util.ArrayList;

public class Recording {


    private ArrayList<String> path = new ArrayList<>();

    Recording(ArrayList<String> path){
        this.path = path;
    }

    public ArrayList<String> getPath() {
        return path;
    }
}
