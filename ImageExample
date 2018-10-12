import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

class C extends JFrame {
	JButton b1, b2, b3, b4;
	ImageIcon ii1, ii2, ii3, ii4;
	Container cp;
	GridLayout gLayout = new GridLayout(2,2);
	JPanel p = new JPanel();//패널이란 접시

	void init(){
		loadImg();
		b1 = new JButton("동");
		b2 = new JButton("서");
		b3 = new JButton("남");
		b4 = new JButton("북");
		cp = getContentPane();
		cp.add(b1, BorderLayout.EAST);
		cp.add(b2, BorderLayout.WEST);
		cp.add(b3, BorderLayout.SOUTH);
		cp.add(b4, BorderLayout.NORTH);
		cp.add(p);
		p.setLayout(gLayout);
		p.add(new JButton(ii1));
		p.add(new JButton(ii2));
		setUI();
	}

	void setUI(){
		setTitle("Image Test Ver 1.0");
		pack();
		setLocation(500, 100);
		setVisible(true);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void loadImg(){
		try{
			ii1 = new ImageIcon(ImageIO.read(new File(ImagePath.PATH1)));
			ii2 = new ImageIcon(ImageIO.read(new File(ImagePath.PATH2)));

		}catch(IOException ie){}
	}
	public static void main(String[] args) 
	{
		C c = new C();
		c.init();
	}
}
