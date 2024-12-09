package zlosnik.jp.lab05.ref;
import javax.swing.*;

public class Glowna {
	
	public static void main(String[] args) {
	        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        MyFrame frame = new MyFrame("MyApplication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
  */     frame.setVisible(true);
	}	
}
