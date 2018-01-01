package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MousePixInfo3 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	static JLabel value_x = null;
	static JLabel value_y = null;
	static JLabel myPixColorHex = null;
	static Robot myRobot;
	static Timer timer = new Timer();
	JLabel lblx = new JLabel("X :");
	JLabel lbly = new JLabel("Y :");
	static boolean isRunning = true;
	JLabel note1;
	JLabel note2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			myRobot = new Robot();
			MousePixInfo3 info_frame = new MousePixInfo3();
			info_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			info_frame.setVisible(true);
			info_frame.setAlwaysOnTop(true);
			info_frame.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						System.exit(0);
					} else if (e.getKeyChar() == ' ') {
						if (isRunning) {
							timer.cancel();
							isRunning = false;
						} else {
							run();
							isRunning = true;
						}
					} else if (e.getKeyChar() == 'l') {
						setSysClipboardText(value_x.getText() + "," + value_y.getText());
					} else if (e.getKeyChar() == 'c') {
						setSysClipboardText(myPixColorHex.getText());

					}
				}
			});
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public static void setSysClipboardText(String writeMe) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}

	public static void run() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
				Color myPixColor = myRobot.getPixelColor(point.x, point.y);
				String r = Integer.toHexString(myPixColor.getRed());
				String g = Integer.toHexString(myPixColor.getGreen());
				String b = Integer.toHexString(myPixColor.getBlue());

				value_x.setText("" + point.x); // 双引号""把int转成string
				value_y.setText("" + point.y);
				myPixColorHex.setText("#" + r + g + b);
			}
		}, 100, 50);// 设置100ms后开始,每50ms检测一次坐标
	}

	public MousePixInfo3() {
		timer = new Timer();
		setTitle("像素采集神器");
		setBounds(100, 100, 317, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lblx.setFont(new Font("宋体", Font.PLAIN, 15));
		lblx.setBounds(22, 27, 66, 31);
		contentPanel.add(lblx);

		lbly.setFont(new Font("宋体", Font.PLAIN, 15));
		lbly.setBounds(22, 68, 66, 31);
		contentPanel.add(lbly);

		value_x = new JLabel("0");
		value_x.setForeground(Color.BLUE);
		value_x.setFont(new Font("宋体", Font.PLAIN, 20));
		value_x.setBounds(64, 27, 66, 31);
		contentPanel.add(value_x);

		value_y = new JLabel("0");
		value_y.setForeground(Color.BLUE);
		value_y.setFont(new Font("宋体", Font.PLAIN, 20));
		value_y.setBounds(64, 68, 66, 31);
		contentPanel.add(value_y);

		myPixColorHex = new JLabel("0");
		myPixColorHex.setForeground(Color.RED);
		myPixColorHex.setFont(new Font("宋体", Font.PLAIN, 32));
		myPixColorHex.setBounds(142, 48, 128, 31);
		contentPanel.add(myPixColorHex);

		note1 = new JLabel("esc退出;Space暂停/继续", JLabel.CENTER);
		note1.setFont(new Font("幼圆", Font.PLAIN, 16));
		getContentPane().add(note1, BorderLayout.SOUTH);

		note2 = new JLabel("'l'复制location;'c'复制color", JLabel.CENTER);
		note2.setFont(new Font("幼圆", Font.PLAIN, 16));
		getContentPane().add(note2, BorderLayout.NORTH);

	}
}