package shortest_paths;
/**
 * Written by Christina Alexander
 * Student ID W1672077
 * 
 * This code is written for the Algorithms: Theory, Design and Implementation
 * course at the University of Westminster for the Spring semester of 2018.
 */
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JTextField;


public class Run_Shortest_Path {
	int corXStart, corYStart, corXEnd, corYEnd;
	private JButton ManhattanButton = new JButton("Manhattan"),
			ChebyshevButton = new JButton("Chebyshev"), 
			EuclideanButton = new JButton("Euclidean");
	private JTextField firstSetX = new JTextField(2), firstSetY = new JTextField(2),
			endSetX = new JTextField(2), endSetY = new JTextField(2);
	private javax.swing.JTextArea OutputBoxText;
	private javax.swing.JScrollPane OutputBox;
	private Node[][] grid;
	
	Run_Shortest_Path() throws IOException{
		File numberFile = new File("NumberMap.txt");
		//BufferedReader numberFileReader = new BufferedReader(new FileReader(numberFile));
        
		if (numberFile.exists()) {
			Scanner numberFileReader = new Scanner(numberFile);
			grid = new Node[20][20];	
			int x = 0,y = 0,num;
			while(numberFileReader.hasNextInt()) {
				num = numberFileReader.nextInt();
				grid[x][y] = new Node(x,y,num);
				//System.out.println(x + "," + y);
				if (y == 19) {
					y = 0; x++;
				}
				else
					y++;
			}
			numberFileReader.close();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		Run_Shortest_Path run = new Run_Shortest_Path();
		run.buildWindow();
	}
		
	<T> void buildWindow() {
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		GroupLayout g1 = new GroupLayout(contentPane);
		contentPane.setLayout(g1);

		
		g1.setAutoCreateContainerGaps(true);
		g1.setAutoCreateGaps(true);
		
		myListen<T> listener = new myListen<T>();
		
		ManhattanButton.addActionListener(listener);
		ChebyshevButton.addActionListener(listener);
		EuclideanButton.addActionListener(listener);
		
		
		ImageIcon map = new ImageIcon(getClass().getResource("/Grid_Algorithms.PNG"));
		JLabel mapLabel = new JLabel();
		mapLabel.setIcon(map);
		
		JLabel startX = new JLabel("Start X:"), startY = new JLabel("Start Y:"),
				endX = new JLabel("End X:"), endY = new JLabel("End Y:"), 
				messageLabel = new JLabel("Please Enter Start and End Coordinates"
						+ " and Then Select a Metric System");
		
		firstSetX.setEditable(true);	firstSetX.setMaximumSize(new Dimension(20,20));
		firstSetY.setEditable(true);	firstSetY.setMaximumSize(new Dimension(20,20));
		endSetX.setEditable(true);	endSetX.setMaximumSize(new Dimension(20,20));
		endSetY.setEditable(true);	endSetY.setMaximumSize(new Dimension(20,20));
		 
		OutputBox = new javax.swing.JScrollPane();
	    OutputBoxText = new javax.swing.JTextArea();

		OutputBoxText.setColumns(20);
        OutputBoxText.setRows(5);
        OutputBox.setViewportView(OutputBoxText);
		
		g1.setHorizontalGroup(g1.createParallelGroup()
				.addComponent(mapLabel)
				.addGroup(g1.createParallelGroup()
						.addComponent(messageLabel)
				)
				.addGroup(g1.createSequentialGroup()
						.addComponent(startX)
						.addComponent(firstSetX)
						.addComponent(startY)
						.addComponent(firstSetY)
				)
				.addGroup(g1.createSequentialGroup()
						.addComponent(endX)
						.addComponent(endSetX)
						.addComponent(endY)
						.addComponent(endSetY)
				)
				.addGroup(g1.createSequentialGroup()
					.addComponent(ChebyshevButton)
					.addComponent(EuclideanButton)
					.addComponent(ManhattanButton)
				)
				.addComponent(OutputBox)
        );

        g1.setVerticalGroup(g1.createSequentialGroup()
        		.addComponent(mapLabel)
        		.addComponent(messageLabel)
        		.addGroup(g1.createParallelGroup()
        				.addComponent(startX)
        				.addComponent(firstSetX)
        				.addComponent(startY)
						.addComponent(firstSetY)
				)
				.addGroup(g1.createParallelGroup()
						.addComponent(endX)
						.addComponent(endSetX)
						.addComponent(endY)
						.addComponent(endSetY)
				)
        		.addGroup(g1.createParallelGroup()
        			.addComponent(ChebyshevButton)
        			.addComponent(EuclideanButton)
        			.addComponent(ManhattanButton)
        		)
        		.addComponent(OutputBox)
        );
		
		frame.pack();
		frame.setVisible(true);
		
		
	}
	boolean validCoords(){
        if (corXStart > 19 || corXStart < 0 || corYStart > 19 || corYStart < 0 || corXEnd > 19 || corXEnd < 0 || corYEnd > 19 || corYEnd < 0)
            return false;
        return true;
    }
	
	private class myListen<T> implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			getVals();
			if(validCoords()) {
				System.out.println(System.currentTimeMillis());
				List<Node> path = new LinkedList<Node>();
			ShortestPath runPath = null;
			if (e.getSource() == ManhattanButton) 
				runPath = new ShortestPath(grid,"Manhattan");
			else if(e.getSource() == ChebyshevButton) 
				runPath = new ShortestPath(grid,"Chebyshev");
			else if(e.getSource() == EuclideanButton) 
				runPath = new ShortestPath(grid,"Euclidean");
			
			path = runPath.aStarAlgorithm(corXStart, corYStart, corXEnd, corYEnd);
			String pathCoords = new String();
			if (path.isEmpty()) {
				OutputBoxText.setText("No Path Found");
				return;
			}
			for (int i = 0; i < path.size(); ++i)
			{
				Node curr = path.get(i);
				pathCoords += "[" + curr.x + "," + curr.y + "]";
				if (i+1 < path.size()) pathCoords += ",";
				else pathCoords += "\n";
			}
			pathCoords += path.get(path.size()-1).getG();
			OutputBoxText.setText(pathCoords);
			System.out.println(System.currentTimeMillis());
			}
			else
				OutputBoxText.setText("Invalid Coordinates. Please Try again");
		}
		
		void getVals() {
			String curr;
			
			curr = firstSetX.getText();
			if (curr.length() == 0) corXStart = 0;
			else corXStart = Integer.parseInt(curr);
			
			curr = firstSetY.getText();
			if(curr.length() == 0) corYStart = 0;
			else corYStart = Integer.parseInt(curr);
			
			curr = endSetX.getText();
			if (curr.length() == 0) corXEnd = 0;
			else corXEnd = Integer.parseInt(curr);
			
			curr = endSetY.getText();
			if (curr.length() == 0) corYEnd = 0;
			else corYEnd = Integer.parseInt(curr);
		}
		
	}
	

}
