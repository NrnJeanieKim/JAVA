import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
class GUI extends JFrame {
  String tinder ="/Users/Sungsu/Desktop/Tinder/";
  Container ct;
  JPanel jp1,jp2,jp3,lower;
  JButton leftBt,rightBt;
  JLabel logoLb, photoLb;
  Load l;
  String topBanner = tinder+"TopBanner.jpg";
  String rightImage = tinder+"RightKey.jpg";
  String leftImage = tinder+"LeftKey.jpg";
  int counter = 1;
  ImageIcon img;
  TextArea proTA;

  class RightLeft implements ActionListener{
    RightLeft(){
      leftBt.addActionListener(this);
      rightBt.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
      counter++;
      l.pick(counter);
      System.out.println(l.ptPath);
      change(l.ptPath);
    }
  }
  void change(String ptPath){
    try{
      photoLb.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+ptPath))));
      proTA.setText(l.profile);
    }catch(IOException ie){
    }
  }

  GUI(){
    try{

      l = new Load();
      l.pick(counter);

      // addKeyListener(new Choose().new MyKeyListener());
      leftBt = new JButton(new ImageIcon(ImageIO.read(new File(leftImage))));
      rightBt = new JButton(new ImageIcon(ImageIO.read(new File(rightImage))));
      RightLeft rl = new RightLeft();
      logoLb = new JLabel(new ImageIcon(ImageIO.read(new File(topBanner))));
      img = new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+l.ptPath)));
      photoLb= new JLabel(img);
      proTA = new TextArea(l.profile);
    }catch(IOException ie){
    }
    ////////////TOP//////////////
    jp1 = new JPanel();
    jp1.setLayout(new FlowLayout());
    jp1.add(logoLb);
    ///////////MIDDLE////////////
    jp2 = new JPanel();
    jp2.setLayout(new GridLayout(2,1));
    jp2.add(photoLb);
    jp2.add(proTA);
    ////////////BOTTOM//////////
    jp3 = new JPanel();
    jp3.setLayout(new FlowLayout());
    jp3.add(leftBt);
    jp3.add(rightBt);
    /////////////////////////////
    lower = new JPanel();
    lower.setLayout(new BorderLayout());
    lower.add(jp1,BorderLayout.NORTH);
    lower.add(jp2,BorderLayout.CENTER);
    lower.add(jp3,BorderLayout.SOUTH);
    add(lower);
    init();
  }

  void init(){
    setTitle("Tinder");
    setSize(800,500);
    setLocation(400,400);
    setVisible(true);
  }
  public static void main(String[] args) {
    GUI g = new GUI();
  }
}
