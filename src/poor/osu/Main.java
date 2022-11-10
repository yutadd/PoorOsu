package poor.osu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

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
	static JLabel title=new JLabel("carlito!");
	static JLabel precious=new JLabel("0%");
	static ConcurrentLinkedQueue<Long> target_time_queue=new ConcurrentLinkedQueue<>();
	static ConcurrentLinkedQueue<Targetv2> target_obj_queue = new ConcurrentLinkedQueue<>();
	static int eliminated_target=0;
	static int hited=0;
	static ArrayList<Target> removeList=new ArrayList<>();
	static int score=0;
	public static void main(String[] args) {
		jf.setUndecorated(true);
		jf.setAlwaysOnTop(true);
		//jf.setType(Type.UTILITY);
		jf.setBounds(0,0,1433,1080);
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		jf.setCursor(blankCursor);
		jf.setLayout(null);
		panel.setBounds(0,0,1433,1080);

		panel.setBackground(Color.black);
		panel.setLayout(null);
		jf.add(panel);
		cursor.setIcon(new ImageIcon("cursor.png"));
		panel.add(cursor);
		cursor.setBounds(0, 0, 40, 40);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBackground(Color.black);
		score_board.setBounds(840, 5, 700, 100);
		score_board.setFont(new Font("メイリオ",Font.BOLD,80));
		score_board.setText("0");
		score_board.setForeground(Color.white);
		precious.setBounds(840, 90, 500, 100);
		precious.setFont(new Font("メイリオ",Font.BOLD,70));
		precious.setForeground(Color.white);
		title.setBounds(0, 0, 1000, 100);
		title.setFont(new Font("メイリオ",Font.BOLD,80));
		title.setForeground(Color.white);
		panel.add(title);
		panel.add(score_board);
		panel.add(precious);
		jf.setVisible(true);
		try {
			boolean begin_reading=false;
			BufferedReader bsr=new BufferedReader(new FileReader(new File("carlito.osu")));
			String str;
			while((str=bsr.readLine())!=null) {
				if(!begin_reading) {
					if(str.equals("[HitObjects]")) {
						begin_reading=true;
					}
				}else {
					String[] objects=str.split(",");
					if(objects[5].startsWith("0")) {
						target_time_queue.add(Long.parseLong(objects[2]));
						target_obj_queue.add(new Targetv2(Integer.parseInt(objects[2]),Integer.parseInt(objects[0]),Integer.parseInt(objects[1])));
						System.out.println("ヒットオブジェクトの読み込み。"+Integer.parseInt(objects[2])+"\t"+Integer.parseInt(objects[0])+"\t"+Integer.parseInt(objects[1]));
					}else {
						String[] params=objects[5].split(Pattern.quote("|"));
						if(params[0].equals("L")) {
							int sx=Integer.parseInt(objects[0]),sy=Integer.parseInt(objects[1]);

							System.out.println(params[1]);
							int fx=Integer.parseInt(params[1].split(":")[0]),fy=Integer.parseInt(params[1].split(":")[1]);
							double x_di=((double)(fx-sx)/(double)10);
							double y_di=((double)(fy-sy)/(double)10);
							System.out.println("direction : "+x_di+"\t"+y_di);
							for(int i=0;i<10;i++) {
								target_time_queue.add(Long.parseLong(objects[2])+i);
								target_obj_queue.add(new Targetv2(Long.parseLong(objects[2])+i,(int)(sx+(x_di*i)), (int)(sy+(y_di*i))));
							}
						}
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Thread th_cursor=new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						cursor.setBounds(jf.getMousePosition().x-20,jf.getMousePosition().y-30,60,60);
					}catch(Exception e) {}
				}
			}
		};
		th_cursor.start();
		int offset=10000;
		long startTime=System.currentTimeMillis()-offset;
		while(true) {//一ミリ病以内に描画できないと飛ばされて描画されない可能性がある。。
			try {
				long currenttime=System.currentTimeMillis();
				try {
					targets.forEach(o->{
						if(currenttime>=o.expire) {
							o.eliminate();
						}
					});
					removeList.forEach(object->targets.remove(object));
					removeList=new ArrayList<>();
				}catch(Exception e) {e.printStackTrace();}
				if(target_time_queue.element()<=(currenttime-startTime)) {
					Targetv2 target=target_obj_queue.remove();
					long time=target_time_queue.remove();
					System.out.print("ヒットオブジェクトの追加前。"+target.x+"\t"+target.y);
					System.out.println("ヒットオブジェクトの描画。"+target.x+"\t"+target.y);
					Target t=new Target((int)((target.x*1.4)+200),(int)((target.y*1.4)+100));
					panel.add(t.label);
					targets.add(t);
				}
				precious.setText((int)((double)((double)hited/(double)eliminated_target)*100.0)+"% "+hited+"/"+eliminated_target);
			}catch(Exception e) {e.printStackTrace();}
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
