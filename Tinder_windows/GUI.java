import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
class GUI extends JFrame {
  String tinder ="";
  JPanel jp1,jp2,jp3,lower,upper;
  JButton leftBt,rightBt;
  JLabel logoLb, photoLb;
  Load l;
  String topBanner = tinder+"TopBanner.jpg";
  String rightImage = tinder+"Like.jpg";
  String leftImage = tinder+"disLike.jpg";
  int counter =1;
  ImageIcon img;
  JLabel proLb;
  Container cp;
  //String FName = ""; //���ڸ� mList.txt, ���ڸ� wList.txt �б�. ���̵� �ε��� ���缭...
	BufferedReader br = null;
	File myFile, yourFile; //�� ��/�� �����ϴ� ����Ʈ, ���� ����Ʈ(���� �ѱ涧���� ���� ���ư�)
	FileReader fr = null; //���� ���� ����Ʈ �б�
	FileWriter writer = null;
	final String LIKE = "1";
	final String DISLIKE = "2";
	String yourName = ""; //�α����� ��� �̸�. ����w1~w10, ���� m1~m10 ���̸����ϴ¹��ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	String myIdx; //�� �ε���. 1~10���� �ϳ��� ������ ����(�α��ν� ��Ī��)
	String yourIdx; //���� ���� ���ƿ�/�Ⱦ�並 �Ǵ��ϴ� ����� �ε���. 1���� �����ؼ� Ű���尪 �Է� �Ҷ����� 1�� ����.
	String myName = ""; //�� ������ �ݴ�Ǵ� ����+yourIdx ���� �̸� ���ϴ� ���ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	String yourAnswer = ""; //������ �����ü
	boolean ask = false; //���ƿ�-���ƿ�� true�� ��->��ȭ ���� �˾� ��
  TChat tc;
  /*class RightLeft implements ActionListener{
    RightLeft(){
      leftBt.addActionListener(this);
      rightBt.addActionListener(this);
    }
    /*public void actionPerformed(ActionEvent ae){
      counter++;
      try{
        l.pick(counter,myName);
        System.out.println(l.ptPath);
        change(l.ptPath);
      }catch(NullPointerException ne){
          System.out.println(" ��Ī �� ����� ����");
          // jp2.setVisible(false);
          // JLabel noPic = new JLabel();
          // noPic.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"NoOne.jpg"))));
          // JPanel noOne = new JPanel();
          // noOne.add(noPic);
          // jp2.add(noOne);
          change();
      }
    }*/
  void change(int counter,String gender){///////������ ���� �� ���� ��ȯ
    try{
      if(gender.equals("female")){
        l.pick(counter,"male");
        photoLb.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"mPhoto/"+l.ptPath))));
      }else {
        l.pick(counter,"female");
        photoLb.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+l.ptPath))));
      }
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
  String fName;
  GUI(){}
  GUI(String name){
    tc = new TChat();
    myName = name;
    fName = name;
    myIdx = myName.substring(1);
    if(myName.contains("w"))myName = "female";
    else myName = "male";
	  myFile = new File(fName+"_like.txt"); //�� ��/�� �����ϴ� ����Ʈ
    try{
	     writer = new FileWriter(myFile, false);
      l = new Load();
      l.pick(Integer.parseInt(myIdx),myName);
      leftBt = new JButton(new ImageIcon(ImageIO.read(new File(leftImage))));
      rightBt = new JButton(new ImageIcon(ImageIO.read(new File(rightImage))));
      logoLb = new JLabel(new ImageIcon(ImageIO.read(new File(topBanner))));
      if(myName.equals("male")){
        l.pick(1,"female");
        img = new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+l.ptPath)));
      }else{
        l.pick(1,"male");
        img = new ImageIcon(ImageIO.read(new File(tinder+"mPhoto/"+l.ptPath)));
      }
      photoLb= new JLabel(img);
      proLb = new JLabel(l.profile);
    }catch(IOException ie){
    }
    ////////////TOP//////////////
    jp1 = new JPanel(new FlowLayout());
    jp1.add(logoLb);
    ///////////MIDDLE////////////
    upper = new JPanel(new FlowLayout());
    upper.add(proLb);
    jp2 = new JPanel();
    photoLb.setPreferredSize(new Dimension(300,310));;
    jp2.add(photoLb,BorderLayout.SOUTH);
    jp2.add(upper,BorderLayout.NORTH);
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
    cp  = getContentPane();
    cp.setFocusable(true);
    cp.requestFocus();
	  cp.addKeyListener(new MyKeyListener());
    setTitle("Tinder");
    setSize(400,540);
    setLocation(500,100);
    setVisible(true);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  //����Ŭ���� KeyListener!!!!!!!
	 class MyKeyListener extends KeyAdapter{
     	  public void keyPressed(KeyEvent e){
     			try{
            counter++;
     				String ptName = myName+myIdx;
     				yourFile = new File(l.index+"_like.txt");
     				fr = new FileReader(yourFile);
     			}catch(FileNotFoundException fe){
          }catch(NullPointerException ne){
              System.out.println(" ��Ī �� ����� ����");
              return;
            }
     			if (yourFile.exists()){
     				br = new BufferedReader(fr);
     			}
     			int key = e.getKeyCode();
     			switch(key){
     				case KeyEvent.VK_LEFT: //�Ⱦ��
     				try{
     					writer.write(DISLIKE+"*");
     					writer.flush();
     				}catch(IOException ie){} break;
     				case KeyEvent.VK_RIGHT: //���ƿ�
     				try{
              writer.write(LIKE+"*");
     					writer.flush();
     					String line = "";//�ε��� Ű������ �����ؾ��ԡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
     					if (yourFile.exists()){
                System.out.println(myIdx+"myIdx");
     					   if((line = br.readLine())!=null){
     							 yourAnswer = line;
                  String[] elements = yourAnswer.split("\\*");
                  System.out.println(elements.length);
                     if (elements[Integer.parseInt(myIdx)-1].equals("1")){ //���� ���� ��� �� �ε����� like�� ��ȭâ ���� �޼ҵ�� �Ѿ.
         					  		ask = true;
         						  	askChat(tc);
         						 }
                   }
     						}
     				   }catch(IOException ie){}
                 catch(ArrayIndexOutOfBoundsException se){break;}
            break;
     			}
          if(!ask)change(counter,myName);
     		}
      }
    Boolean keep =false;
	  void askChat(TChat tc){
    //   TChat ts = tc; /////just
    //   int answer =JOptionPane.showConfirmDialog(this, "���ƿ��� ������ ��ȭ ��û�� �ֽ��ϴ�. Ȯ���� �����ø� ��ȭâ���� �̵��մϴ�.", "����", JOptionPane.OK_CANCEL_OPTION);
    //   if(answer == JOptionPane.YES_OPTION){
    //   tc.pop();
    //   tc.init();
    // }else{
    //   keep = true;
    // //gui.focus();
    // change(counter,myName);
    // System.out.println("Change has made");
    // // dispose();
      if (ask){ //��ȭ�����˾�â����.
        AskChat ac = new AskChat(this,tc);
        if(ac.poped) keep = true;
    }



	  }
    void focus(){
       //requestFocusInWindow();
      //toFront();
       //cp.setFocusable(true);
       cp.requestFocus();
    }



}

class AskChat extends JDialog implements ActionListener{ //�˾�â ���� ���� Ŭ����. Choose�ܺ�.
	 GUI gui; //���� ���� cs.ask = false�� �������� ��.
	 JPanel upPanel, middlePanel, downPanel; //��ġ, ��Ʈ, ��ȭ/��Ӱ��� �����Ǵ� JPanel
	 JLabel match, heart; //"It's Match!", ��Ʈ�׸�
	 JButton sendButton, keepButton; //"send a message", "Keep Playing"
	 Container cp;
   Boolean poped=false;
   TChat tc;
   AskChat(){}
	 AskChat(GUI gui,TChat tc){
		 this.gui = gui;
     this.tc = tc;
     setModal(true);
		  init();




	  }
   public void actionPerformed(ActionEvent ae){/////////////////////////sendButton ���ý� ���濡�� �˾� �������.//////////////////////
     Object o = ae.getSource();
     if(o==sendButton){
       this.setVisible(false);
      gui.setVisible(false);
      tc.pop();
      tc.init();
     }else {
      this.setVisible(false);
      poped = true;
      // gui.focus();
      gui.change(gui.counter,gui.myName);
      System.out.println("Change has made");
      dispose();
      // return;
    }
     }

	 void init(){
		cp = getContentPane();
		upPanel = new JPanel(); middlePanel = new JPanel(); downPanel = new JPanel();
		cp.add(upPanel, BorderLayout.NORTH);
		cp.add(middlePanel, BorderLayout.CENTER);
		cp.add(downPanel, BorderLayout.SOUTH);
		match = new JLabel("It's a Match!");
		heart = new JLabel("heart"); //��Ʈ������ �ֱ�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		sendButton = new JButton("Send a Message");
    sendButton.addActionListener(this);
		keepButton = new JButton("Keep Playing");
    keepButton.addActionListener(this);
		//�г� ���δ� FLOWLAYOUT���ιٲ㼭�ؾ��ҵ�??
		upPanel.add(match);
		middlePanel.add(heart);
		downPanel.add(sendButton); downPanel.add(keepButton);
		setUI();
	  }
	 void setUI(){
		setSize(350, 500);
		setLocation(500,300);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //�� �� ��ư�� ���ټ�����
	}
	  }
