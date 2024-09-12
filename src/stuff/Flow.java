package stuff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Flow extends JComponent implements MouseListener, MouseMotionListener{
	static final int NULL = 0, RED = 1, BLUE = 2, GREEN = 3, YELLOW = 4, ORANGE = 5, PINK = 6, CYAN = 7, PURPLE = 8, WHITE = 9, BROWN = 10;
	final Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, new Color(0,100,0), Color.YELLOW, new Color(255,150,0), Color.PINK, Color.CYAN, new Color(100,0,255), Color.WHITE, new Color(110,69,0)};
	
	
	final int WIDTH = 600, HEIGHT = 900;
	final int leftMargin = 42, rightMargin = 42, topMargin = 259, botMargin = 125;
	
	int SIZE, boxWidth, boxHeight;
	int[][] flowSources, flowUsed;
	
	int mouseX = 100000, mouseY = 100000;
	ArrayList<ArrayList<int[]>> paths = new ArrayList<ArrayList<int[]>>();
	int currPath = NULL;
	
	int flows, maxFlows, moves;
	double pipe = 0;
	
	int currLevel = 0;
	
	// { {SIZEX, SIZEY, maxFlows}, ...{...flowSources...} }
	int[][] level1 = {{5,5,5},  {0,0,4,1},{0,2,3,1},{1,2,4,2},{0,4,3,3},{1,4,4,3}};
	int[][] level2 = {{5,5,4},  {0,0,4,3},{2,2,1,3},{0,3,4,4},{0,4,2,3}};
	int[][] level3 = {{6,6,5},  {0,2,2,0},{1,1,4,4},{2,1,5,2},{4,1,0,4},{0,5,1,4}};
	int[][] level4 = {{7,7,7},  {0,0,0,4},{0,1,2,4},{2,0,4,0},{1,4,5,5},{2,1,4,2},{5,1,4,3},{5,0,6,3}};
	int[][] level5 = {{8,8,7},  {1,6,3,4},{2,2,2,4},{2,6,5,5},{3,6,4,1},{4,0,6,0},{5,0,5,3},{6,1,6,3}};
	int[][] level6 = {{9,9,7},  {0,2,2,4},{0,3,8,6},{1,1,4,4},{2,1,6,3},{4,5,8,5},{5,3,5,5},{7,0,8,4}};
	int[][] level7 = {{10,10,9},{1,3,8,3},{1,4,3,8},{1,8,5,4},{1,9,9,7},{2,3,7,2},{2,6,9,9},{3,6,6,4},{4,6,5,5},{8,4,9,8}};
	
	ArrayList<int[][]> levels= new ArrayList<int[][]>();
	
	
	public Flow() {
		//JCompoennt Stuff
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//Add levels to Level list
		levels.add(level1);
		levels.add(level2);
		levels.add(level3);
		levels.add(level4);
		levels.add(level5);
		levels.add(level6);
		levels.add(level7);
		
		newLevel(levels.get(currLevel));
		
		repaint();
	}
	
	public void newLevel(int[][] level) {
		maxFlows = level[0][2];
		SIZE = level[0][0];
		
		boxWidth = (WIDTH - leftMargin - rightMargin)/SIZE;
		boxHeight = (HEIGHT - topMargin - botMargin)/SIZE;
		
		flowSources = new int[SIZE][SIZE];
		flowUsed = new int[SIZE][SIZE];
		for(int i = 1; i <= maxFlows; i++) {
			flowSources[level[i][0]][level[i][1]] = i;
			flowSources[level[i][2]][level[i][3]] = i;
		}
		
		paths.clear();
		for(int i = 0; i < maxFlows; i ++) {
			paths.add(new ArrayList<int[]>());
		}
		
		currPath = NULL;
		flows = 0;
		pipe = 0;
		moves = 0;
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,WIDTH,HEIGHT);
		
		g2.setColor(Color.GREEN);
		g2.setFont(new Font("Arial",Font.PLAIN,40));
		g2.drawString("level " + (currLevel+1), WIDTH/6, 100);
		g2.setColor(Color.GRAY);
		g2.setFont(new Font("Arial",Font.PLAIN,30));
		g2.drawString(SIZE + "x" + SIZE, (int) (WIDTH/2.7), 97);
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial",Font.PLAIN,20));
		g2.drawString("flows: " + flows + "/" + maxFlows, leftMargin, topMargin-10);
		
		g2.drawString("pipe: " + (int)(pipe+0.5) + "%", (int) (WIDTH/1.3), topMargin-10);
		if(moves > maxFlows) g2.setColor(new Color(255,120,0));
		g2.drawString("moves: "+ moves, (int) (leftMargin+WIDTH/3.5), topMargin-10);
		g2.drawString("best: " + maxFlows, (int) (leftMargin+WIDTH/2.15), topMargin-10);
		 
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				g2.setColor(colors[flowUsed[r][c]].darker().darker().darker().darker());
				g2.fill(new Rectangle2D.Double(leftMargin + c*boxWidth, topMargin + r*boxHeight, boxWidth, boxHeight));
				g2.setColor(Color.GREEN);
				g2.draw(new Rectangle2D.Double(leftMargin + c*boxWidth, topMargin + r*boxHeight, boxWidth, boxHeight));
				if(flowSources[r][c] != 0) {
					g2.setColor(colors[flowSources[r][c]]);
					g2.fillOval(leftMargin + c*boxWidth + 10, topMargin + r*boxHeight + 10, boxWidth - 20, boxHeight - 20);
				}
			}
		}

		g2.setStroke(new BasicStroke(boxHeight/4));
		for(int j = 0; j < paths.size(); j++) {
			ArrayList<int[]> curr = paths.get(j);
			g2.setColor(colors[j+1]);
			for (int i = 1; i < curr.size(); i++) {
				int c = leftMargin + curr.get(i)[1]*boxWidth + boxWidth / 2;
				int r = topMargin + curr.get(i)[0]*boxHeight + boxHeight / 2;
				int pc = leftMargin + curr.get(i-1)[1]*boxWidth + boxWidth / 2;
				int pr = topMargin + curr.get(i-1)[0]*boxHeight + boxHeight / 2;
				g2.drawLine(c, r, pc, pr);
			}
		}
		if(currPath > NULL) {
			g2.setColor(new Color(colors[currPath].getRed(),colors[currPath].getGreen(),colors[currPath].getBlue(),150));
			g2.fillOval(mouseX-25, mouseY-25, 50, 50);
		}
		
		//Check Win
		if(flows == maxFlows && (int)(pipe+0.5) == 100) {
			currLevel++;
			newLevel(levels.get(currLevel));
			repaint();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int X = e.getX();
		int Y = e.getY();
		int row = (Y - topMargin)/boxHeight;
		int col = (X - leftMargin)/boxWidth;
		if(!(row < SIZE && col < SIZE && row >=0 && col >=0)) return;
		
		//If clicked on a flow source
		if(flowSources[row][col] > 0) {
			currPath = flowSources[row][col];
			pipe += 100.0/(SIZE*SIZE);
			for(int[] a:paths.get(currPath-1)) {
				flowUsed[a[0]][a[1]] = NULL;
				pipe -= 100.0/(SIZE*SIZE);
			}
			int pathLength = paths.get(currPath-1).size();
			if ( pathLength > 1 && flowSources[paths.get(currPath-1).get(pathLength-1)[0]][paths.get(currPath-1).get(pathLength-1)[1]] > NULL) {
				flows -= 1;
			}
			paths.get(currPath-1).clear();
			int[] pos = {row,col};
			paths.get(currPath-1).add(pos);
			flowUsed[row][col] = currPath;
		}
		//Lets you continue from anywhere in a path where clicked
		else if(flowUsed[row][col] > 0) {
			currPath = flowUsed[row][col];
			int i = paths.get(currPath-1).size()-1;
			if ( i > 0 && flowSources[paths.get(currPath-1).get(i)[0]][paths.get(currPath-1).get(i)[1]] > NULL) {
				flows -= 1;
			}
			while(paths.get(currPath-1).get(i)[0] != row || paths.get(currPath-1).get(i)[1] != col) {
				flowUsed[paths.get(currPath-1).get(i)[0]][paths.get(currPath-1).get(i)[1]] = NULL;
				paths.get(currPath-1).remove(i);
				pipe -= 100.0/(SIZE*SIZE);
				i--;
			}
			
		}
		//If clicked anywhere else
		else {
			currPath = NULL;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseX = 100000;
		mouseY = 100000;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int X = e.getX();
		int Y = e.getY();
		mouseX = X;
		mouseY = Y;
		repaint();

		if (currPath == NULL) return;
		
		int row = (Y - topMargin)/boxHeight;
		int col = (X - leftMargin)/boxWidth;
		
		if(!(row < SIZE && col < SIZE && row >=0 && col >=0)) return;
		
		int[] pos = {row,col};
		
		if(flowSources[row][col] != currPath && flowSources[row][col] != NULL) return;
		
		int pathLength = paths.get(currPath-1).size();
		int pr=0, pc=0;
		if(pathLength > 0) {
			pr = paths.get(currPath-1).get(pathLength-1)[0];
			pc = paths.get(currPath-1).get(pathLength-1)[1];
			if(Math.abs(row-pr)+Math.abs(col-pc) > 1) return;
		}
		
		//Checks if square is empty and if it is it adds a path there
		if( flowUsed[row][col] == NULL ) {
			pipe += 100.0/(SIZE*SIZE);
			paths.get(currPath-1).add(pos);
			flowUsed[row][col] = currPath;
			if(flowSources[row][col] > 0) {
				currPath = NULL;
				flows += 1;
				moves += 1;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
