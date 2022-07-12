import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		ImageIcon icon = new ImageIcon("icon/banded_krait.jpg");
		setIconImage(icon.getImage());
		
	}

	
	
}
