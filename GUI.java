import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
class GUI extends JFrame{
  String tinder ="/Users/Sungsu/Desktop/Tinder/";
  JPanel jp1,jp2,jp3,lower,upper;
  JButton leftBt,rightBt;
  JLabel logoLb, photoLb;
  Load l;
  String topBanner = tinder+"TopBanner.jpg";
  String rightImage = tinder+"RightKey.jpg";
  String leftImage = tinder+"LeftKey.jpg";
  int counter = 1;
  ImageIcon img;
  JLabel proLb;
  String gender;

  class RightLeft implements ActionListener{
    RightLeft(){
      leftBt.addActionListener(this);
      rightBt.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
      counter++;
      try{
        l.pick(counter,gender);
        System.out.println(l.ptPath);
        change(l.ptPath);
      }catch(NullPointerException ne){
          System.out.println(" 매칭 할 사람이 없다");
          // jp2.setVisible(false);
          // JLabel noPic = new JLabel();
          // noPic.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"NoOne.jpg"))));
          // JPanel noOne = new JPanel();
          // noOne.add(noPic);
          // jp2.add(noOne);
          change();
      }
    }
  }
  void change(String ptPath){
    try{
      if(gender.equals("female"))photoLb.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+ptPath))));
      else photoLb.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"mPhoto/"+ptPath))));
      proLb.setText(l.profile);
    }catch(IOException ie){
    }
  }
  void change(){
    JLabel noPic = new JLabel();
    try{
      noPic.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"NoOne.jpg"))));
      lower.add(noPic,BorderLayout.CENTER);
    }catch(IOException ie){
    }
  }

  GUI(){
    try{
      gender = "female";
      l = new Load();
      l.pick(counter,gender);
      leftBt = new JButton(new ImageIcon(ImageIO.read(new File(leftImage))));
      rightBt = new JButton(new ImageIcon(ImageIO.read(new File(rightImage))));
      RightLeft rl = new RightLeft();
      logoLb = new JLabel(new ImageIcon(ImageIO.read(new File(topBanner))));
      if(gender.equals("male"))img = new ImageIcon(ImageIO.read(new File(tinder+"mPhoto/"+l.ptPath)));
      else img = new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+l.ptPath)));
      photoLb= new JLabel(img);
      proLb = new JLabel(l.profile);
    }catch(IOException ie){
    }
    ////////////TOP//////////////
    jp1 = new JPanel(new FlowLayout());
    jp1.add(logoLb);
    Choose c = new Choose();
    logoLb.addKeyListener(c.new MyKeyListener());
    ///////////MIDDLE////////////
    upper = new JPanel(new FlowLayout());
    upper.add(proLb);
    jp2 = new JPanel(new GridLayout(2,1));
    jp2.add(photoLb);
    jp2.add(upper);
    ////////////BOTTOM//////////
    jp3 = new JPanel(new FlowLayout());
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
