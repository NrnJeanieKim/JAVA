import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
class GUI extends JFrame{
  String tinder ="";
  JFrame jr;
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
  /////////////
  //String FName = ""; //여자면 mList.txt, 남자면 wList.txt 읽기. 아이디 인덱스 맞춰서...
	BufferedReader br = null;
	File myFile, yourFile; //내 좋/싫 저장하는 리스트, 상대방 리스트(사진 넘길때마다 새로 돌아감)
	FileReader fr = null; //상대방 성별 리스트 읽기
	FileWriter writer = null;

	final String LIKE = "1";
	final String DISLIKE = "2";
	String answer = ""; //좋으면 1, 싫으면 2
	ArrayList<String> answers;
	String myName = "w1"; //로그인한 사람 이름. 여자w1~w10, 남자 m1~m10 내이름정하는법★★★★★★★★★★★★★★★★★★★★
	int myIdx; //내 인덱스. 1~10번중 하나로 정해져 있음(로그인시 매칭됨)
	int yourIdx = 1; //내가 지금 좋아요/싫어요를 판단하는 상대의 인덱스. 1에서 시작해서 키보드값 입력 할때마다 1씩 증가.
	String yourName = "m"; //내 성별과 반대되는 성별+yourIdx 상대방 이름 정하는 법★★★★★★★★★★★★★★★★★★★
	String yourAnswer = ""; //상대방의 대답전체
	boolean ask = false; //좋아요-좋아요면 true가 됨->대화 묻는 팝업 뜸

  /*class RightLeft implements ActionListener{
    RightLeft(){
      leftBt.addActionListener(this);
      rightBt.addActionListener(this);
    }
    /*public void actionPerformed(ActionEvent ae){
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
    }*/



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
	myFile = new File(myName+"_like.txt"); //내 좋/싫 저장하는 리스트
	answers = new ArrayList<String>();
    try{
	  writer = new FileWriter(myFile, true);
      gender = "female";
      l = new Load();
      l.pick(counter,gender);
      leftBt = new JButton(new ImageIcon(ImageIO.read(new File(leftImage))));
      rightBt = new JButton(new ImageIcon(ImageIO.read(new File(rightImage))));
      //RightLeft rl = new RightLeft();
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
    Container cp  = getContentPane();
    cp.setFocusable(true);
    cp.requestFocus();
	  cp.addKeyListener(new MyKeyListener());
    setTitle("Tinder");
    setSize(800,500);
    setLocation(400,400);
    setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  //내부클래스 KeyListener!!!!!!!
	 class MyKeyListener extends KeyAdapter{
     	  public void keyPressed(KeyEvent e){
          System.out.println("key pressed");
     			try{
            counter++;
              l.pick(counter,gender);
              System.out.println(l.ptPath);
              change(l.ptPath);

     				yourName = yourName+Integer.toString(yourIdx);
     				yourFile = new File(yourName+"_like.txt");
     				fr = new FileReader(yourFile);
     			}catch(FileNotFoundException fe){
          }catch(NullPointerException ne){
              System.out.println(" 매칭 할 사람이 없다");
            }

     			if (yourFile.exists()){
     				br = new BufferedReader(fr);
     			}
     			int key = e.getKeyCode();
     			switch(key){
     				case KeyEvent.VK_LEFT: //싫어요
     				try{
     					answers.add(DISLIKE);
     					writer.write(DISLIKE);
     					writer.flush();
     				}catch(IOException ie){} break;
     				case KeyEvent.VK_RIGHT: //좋아요
     				try{
     					String line = "";//인덱스 키워가며 접근해야함★★★★★★★★★★★★★★★★★★★★★★★★★★
     					if (yourFile.exists()){
     						while((line = br.readLine())!=null){
     							yourAnswer = line;
     						}
     						if (yourAnswer.charAt(myIdx)=='1'){ //상대방 파일 열어서 내 인덱스가 like면 대화창 여는 메소드로 넘어감.
     							ask = true;
     							askChat();
     						}
     					}
     					answers.add(LIKE);
     					writer.write(LIKE);
     					writer.flush();
     				}catch(IOException ie){} break;
     			}
     			yourIdx+=1; //상대방 인덱스 하나 늘리기.
     		}
       }

	  void askChat(){
		  if (ask){ //대화묻는팝업창띄우기.
			new AskChat(this);
		  }
	  }
  public static void main(String[] args) {
     GUI g = new GUI();
  }
}


class AskChat extends JFrame{ //팝업창 띄우기 위한 클래스. Choose외부.
	 GUI gui; //띄우고 나서 cs.ask = false로 만들어줘야 함.
	 JPanel upPanel, middlePanel, downPanel; //매치, 하트, 대화/계속고르기 부착되는 JPanel
	 JLabel match, heart; //"It's Match!", 하트그림
	 JButton sendButton, keepButton; //"send a message", "Keep Playing"
	 Container cp;
	 AskChat(GUI gui){
		 this.gui = gui;
		 init();
	  }
	 void init(){
		cp = getContentPane();
		upPanel = new JPanel(); middlePanel = new JPanel(); downPanel = new JPanel();
		cp.add(upPanel, BorderLayout.NORTH);
		cp.add(middlePanel, BorderLayout.CENTER);
		cp.add(downPanel, BorderLayout.SOUTH);
		match = new JLabel("It's a Match!");
		heart = new JLabel("heart"); //하트아이콘 넣기★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		sendButton = new JButton("Send a Message");
		keepButton = new JButton("Keep Playing");
		//패널 내부는 FLOWLAYOUT으로바꿔서해야할듯??
		upPanel.add(match);
		middlePanel.add(heart);
		downPanel.add(sendButton); downPanel.add(keepButton);
		setUI();
	  }
	 void setUI(){
		//setTitle("대화하시겠습니까?");타이틀꼭필요한가???
		setSize(350, 500);
		setLocation(500,300);
		setVisible(true);
		setResizable(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //이 세 버튼은 없앨수없나
	}
	  }
