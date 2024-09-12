package stuff;

import javax.swing.JFrame;

public class FlowViewer {

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.setTitle("Flow Free");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Flow component = new Flow();
		frame.add(component);
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);		
	}

}
