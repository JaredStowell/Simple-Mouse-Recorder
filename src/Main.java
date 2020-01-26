import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel {

    static ArrayList<Recording> recordings = new ArrayList<>();
    static ArrayList<String> path = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File mouseFile = new File("mouse.dat");
        FileWriter fw = new FileWriter(mouseFile, true);
        JFrame frame = new JFrame("Mouse Recorder");
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        JPanel rectangle = new JPanel();
        rectangle.setBackground( Color.RED );
        rectangle.setSize(10,10);
        rectangle.setLocation(generateRandom(700), generateRandom(400));
        frame.add(rectangle);
        frame.add(panel);
        frame.setPreferredSize(new Dimension(760, 500));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("Writing to file...");
                for(Recording recording : recordings){
                    try {
                        printPath(recording.getPath(), fw);
                    }catch(Exception e){}
                }
                try {
                    fw.flush();
                    fw.close();
                }catch(Exception e){}
            }
        });

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {
                path.add(System.currentTimeMillis()+":"+e.getX()+":"+e.getY());
            }
        });

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Making a recording with path: "+path);
                recordings.add(new Recording(path));
                path = new ArrayList<>();
                rectangle.setLocation(generateRandom(700), generateRandom(400));
                System.out.println(rectangle.getLocation());
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }

    private static void printPath(ArrayList<String> thePath, FileWriter fw) throws IOException {
        String[] first = null;
        String[] prev = null;
        for(String s : thePath){
            if(prev != null && first != null){
                String[] args = getArgs(s);
                prev = args;
            }else{
                first = getArgs(s);
                prev = first;
            }
        }
        int timeDiff = getDifference(first[0], prev[0]);
        int xdiff = getDifference(first[1], prev[1]);
        int ydiff = getDifference(first[2], prev[2]);
        appendNewData(xdiff, ydiff, timeDiff, thePath, fw);
    }

    private static void appendNewData(int x, int y, int time, ArrayList<String> thePath, FileWriter fw) throws IOException {
        ArrayList<String> newPath = rewritePath(thePath);
        fw.append(String.format("[%1$d:%2$d:%3$d]", x,y,time));
        fw.append(newPath+System.lineSeparator());
    }

    private static ArrayList<String> rewritePath(ArrayList<String> path){
        ArrayList<String> newPath = new ArrayList<>();
        String[] first = null;
        for(String s : path){
            if(first != null){
                String[] args = getArgs(s);
                int xdiff = getDifference(first[1], args[1]);
                int ydiff = getDifference(first[2], args[2]);
                int timeDiff = getDifference(first[0], args[0]);
                newPath.add(String.format("%1$d:%2$d:%3$d", timeDiff, xdiff, ydiff));
            }else{
                first = getArgs(s);
                newPath.add("0:0:0");
            }
        }
        return newPath;
    }

    private static int getDifference(String s1, String s2){
        return (int) (Long.valueOf(s2) - Long.valueOf(s1));
    }
    private static String[] getArgs(String s){
        return s.split(":");
    }

    public static int generateRandom(int bounds){
        return new Random().nextInt(bounds) + 25;
    }

}
