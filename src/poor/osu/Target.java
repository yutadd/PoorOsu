package poor.osu;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Target implements MouseListener{
	int x=0,y=0;
	JLabel label=new JLabel();
	long expire;
	boolean elmed=false;
	boolean dieing=false;
	@Override
	public boolean equals(Object obj) {
		if(((Target)obj).expire==expire)return true;
		return false;
	}
	public Target(int x,int y) {
		expire=System.currentTimeMillis()+600;
		this.x=x;
		this.y=y;
		label.setIcon(new ImageIcon("target.png"));
		label.setBounds(x,y,80*2,80*2);
		label.addMouseListener(this);
	}
	public void eliminate() {
		label.setVisible(false);
		Main.eliminated_target++;
		Main.removeList.add(this);
	}
	public void score() {
		Main.score_board.setText(Main.score+300+"");
		Main.score+=300;
		Main.hited++;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(!dieing) {
			score();
			dieing=true;
			Thread disappere=new Thread() {
				@Override
				public void run() {
					float alpha=0f;
					for(int i=0;i<15;i++) {
						Graphics2D g2=(Graphics2D)label.getGraphics();
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
						alpha+=0.05;
						try {
							Thread.sleep(5);
						}catch(Exception e) {e.printStackTrace();}
					}
					eliminate();
				}
			};
			disappere.start();
		}

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
