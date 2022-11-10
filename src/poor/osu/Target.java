package poor.osu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Target implements MouseListener{
	int x=0,y=0;
	JLabel label=new JLabel();
	long expire;
	boolean elmed=false;
	
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
		eliminate();
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
		score();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
