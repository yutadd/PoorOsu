package poor.osu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	static JFrame jf=new JFrame("OSU!");
	static JPanel panel=new JPanel();
	static JLabel cursor=new JLabel();
	static ArrayList<Target> targets=new ArrayList<Target>();
	static JLabel score_board=new JLabel("0");
	static JLabel title=new JLabel("-y=x^2+100");
	static JLabel precious=new JLabel("0%");
	static int eliminated_target=0;
	static int hited=0;
	static int score=0;
	public static void main(String[] args) {
		jf.setUndecorated(true);
		jf.setAlwaysOnTop(true);
		jf.setType(Type.UTILITY);
		jf.setBounds(0,0,1920,1080);
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		jf.setCursor(blankCursor);
		jf.setLayout(null);
		panel.setBounds(0,0,1920,1080);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		jf.add(panel);
		cursor.setIcon(new ImageIcon("cursor.png"));
		panel.add(cursor);
		cursor.setBounds(0, 0, 40, 40);
		jf.setBackground(Color.black);
		score_board.setBounds(1270, 5, 700, 100);
		score_board.setFont(new Font("メイリオ",Font.BOLD,80));
		score_board.setText("0");
		score_board.setForeground(Color.white);
		precious.setBounds(1270, 90, 500, 100);
		precious.setFont(new Font("メイリオ",Font.BOLD,70));
		precious.setForeground(Color.white);
		title.setBounds(0, 0, 1000, 100);
		title.setFont(new Font("メイリオ",Font.BOLD,80));
		title.setForeground(Color.white);
		panel.add(title);
		panel.add(score_board);
		panel.add(precious);
		jf.setVisible(true);
		Thread storyboard=new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					for(int i=0;i<20;i++) {
						addtg(i*30+100,i*30+100);
						Thread.sleep(10);
					}
					Thread.sleep(10);
					for(int i=0;i<20;i++) {
						addtg(i*10+600,(20-i)*30+100);
						Thread.sleep(10);
					}
					for(int i=0;i<20;i++) {
						addtg(i*-10+900,i*30+100);
						Thread.sleep(10);
					}
					Thread.sleep(10);
					for(int i=0;i<20;i++) {
						addtg(i+1000,i*30+100);
						Thread.sleep(10);
					}
					Thread.sleep(50);
					for(int i=0;i<100;i++) {
						addtg((int)(Math.sin(i*330)*300)+1400,i*9);
						Thread.sleep(20);
					}
					Thread.sleep(50);
					for(int i=20;i>0;i--) {
						addtg((i*60)+800,i*60);
						Thread.sleep(30);
					}
					for(int i=0;i<80;i++) {
						addtg(i*40,(int)(Math.cos(i*3)*200)+500);
						Thread.sleep(20);
					}
					Thread.sleep(1000);
				}catch(Exception e) {}
			}
		};
		storyboard.start();
		while(true) {
			try {
				long time=System.currentTimeMillis();

				targets.forEach(o->{
					if(time>=o.expire) {
						o.eliminate();
					}
					
				});
				try {
				precious.setText((int)((double)((double)hited/(double)eliminated_target)*100.0)+"% "+hited+"/"+eliminated_target);
				}catch(Exception e) {}
				cursor.setBounds(jf.getMousePosition().x-20,jf.getMousePosition().y-30,60,60);}catch(Exception e) {}
				jf.repaint();
			//Thread.sleep(6);
		}
	}
	static void addtg(int x,int y) {
		Target t=new Target(x,y);
		targets.add(t);
		panel.add(t.label);
	}
}
