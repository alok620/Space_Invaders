import javax.swing.JFrame;

public class Runner extends JFrame {

    public Runner()
    {
        add(new World());
        setTitle("Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Runner();
    }
}