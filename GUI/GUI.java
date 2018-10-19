import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.ArrayList;


class GUI{
	JFrame jf;
	String tinder ="";
	JPanel jp1,jp2,jp3,lower,downP;
	JLabel leftL,rightL;
	JLabel logoLb, photoLb,mainL;
	JTextArea proLb;
	Load l;
	int counter =1;
	ImageIcon img, mainI,frameIc,likeI,disLikeI;
	Container cp;
		
	BufferedReader br = null;
	File myFile, yourFile; //내 좋/싫 저장하는 리스트, 상대방 리스트(사진 넘길때마다 새로 돌아감)
	FileReader fr = null; //상대방 성별 리스트 읽기
	FileWriter writer = null;
	final String LIKE = "1";
	final String DISLIKE = "2";
	String yourName = ""; //로그인한 사람 이름. 여자w1~w10, 남자 m1~m10 내이름정하는법★★★★★★★★★★★★★★★★★★★★
	String myIdx; //내 인덱스. 1~10번중 하나로 정해져 있음(로그인시 매칭됨)
	String yourIdx; //내가 지금 좋아요/싫어요를 판단하는 상대의 인덱스. 1에서 시작해서 키보드값 입력 할때마다 1씩 증가.
	String myName = ""; //내 성별과 반대되는 성별+yourIdx 상대방 이름 정하는 법★★★★★★★★★★★★★★★★★★★
	String yourAnswer = ""; //상대방의 대답전체
	boolean ask = false; //좋아요-좋아요면 true가 됨->대화 묻는 팝업 뜸
	TChat tc;

	void change(int counter,String gender){///////프로필 사진 및 내용 전환
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
		  noPic.setIcon(new ImageIcon(ImageIO.read(new File(tinder+"imgs/NoOne.jpg"))));
		  lower.add(noPic,BorderLayout.CENTER);
		}catch(IOException ie){
		}
	  }
	  String fName;
	  GUI(){}
	  GUI(String name){
		jf = new JFrame();
		frameIc = new ImageIcon("imgs/Tinder Logo.png");
		jf.setIconImage(frameIc.getImage());
		
		mainI = new ImageIcon("imgs/Tinder Choose.png");
		mainL = new JLabel(mainI);
		jf.add(mainL);

		tc = new TChat();
		myName = name;
		fName = name;
		myIdx = myName.substring(1);
		if(myName.contains("w"))myName = "female";
		else myName = "male";
		myFile = new File(fName+"_like.txt"); //내 좋/싫 저장하는 리스트
		try{
			writer = new FileWriter(myFile, false);
			l = new Load();
			l.pick(Integer.parseInt(myIdx),myName);

			likeI = new ImageIcon("imgs/Like.png");
			disLikeI = new ImageIcon("imgs/disLike.png");

			if(myName.equals("male")){
				l.pick(1,"female");
				img = new ImageIcon(ImageIO.read(new File(tinder+"wPhoto/"+l.ptPath)));
			}else{
				l.pick(1,"male");
				img = new ImageIcon(ImageIO.read(new File(tinder+"mPhoto/"+l.ptPath)));
			}
			photoLb= new JLabel(img);
			photoLb.setLayout(null);
			photoLb.setBounds(100,84,200,267);
			mainL.add(photoLb);

			proLb = new JTextArea(l.profile);
			proLb.setLayout(new BorderLayout());
			proLb.setBounds(70,360,290,65);
			proLb.setLineWrap(true);
			proLb.setEditable(false);
			proLb.setFocusable(false);
			proLb.setFont(new Font("굴림", 0, 12));
			mainL.add(proLb);

		}catch(IOException ie){}

		// 하단 패널 
		downP = new JPanel();
		downP.setLayout(null);
		downP.setBounds(0,425,400,109);
		downP.setBackground(new Color(-1));
		mainL.add(downP);

		rightL = new JLabel(disLikeI);
		rightL.setBounds(118, 11, 50, 50);
		downP.add(rightL);

		leftL = new JLabel(likeI);
		leftL.setBounds(220, 11, 50, 50);
		downP.add(leftL);
		init();
	  }
	void init(){
		jf.addKeyListener(new MyKeyListener());
		jf.setTitle("Tinder");
		jf.setSize(400,540);
		jf.setLocation(500,100);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.getContentPane().add(mainL, BorderLayout.CENTER);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		class MyKeyListener extends KeyAdapter{
			public void keyPressed(KeyEvent e){
				try{
					counter++;
					String ptName = myName+myIdx;
					yourFile = new File(l.index+"_like.txt");
					fr = new FileReader(yourFile);
				}catch(FileNotFoundException fe){
				}catch(NullPointerException ne){
					System.out.println(" 매칭 할 사람이 없다");
					return;
				}
					if (yourFile.exists()){
					br = new BufferedReader(fr);
					}	
					int key = e.getKeyCode();
					switch(key){
						case KeyEvent.VK_LEFT: //싫어요
						try{
							writer.write(DISLIKE+"*");
							writer.flush();
						}catch(IOException ie){} break;
						case KeyEvent.VK_RIGHT: //좋아요
						try{
						  writer.write(LIKE+"*");
						writer.flush();
						String line = "";//인덱스 키워가며 접근해야함★★★★★★★★★★★★★★★★★★★★★★★★★★
						if (yourFile.exists()){
						System.out.println(myIdx+"myIdx");
							if((line = br.readLine())!=null){
								yourAnswer = line;
								String[] elements = yourAnswer.split("\\*");
								System.out.println(elements.length);
								if (elements[Integer.parseInt(myIdx)-1].equals("1")){ //상대방 파일 열어서 내 인덱스가 like면 대화창 여는 메소드로 넘어감.
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
		//   int answer =JOptionPane.showConfirmDialog(this, "좋아요한 상대방의 대화 요청이 있습니다. 확인을 누르시면 대화창으로 이동합니다.", "선택", JOptionPane.OK_CANCEL_OPTION);
		//   if(answer == JOptionPane.YES_OPTION){
		//   tc.pop();
		//   tc.init();
		// }else{
		//   keep = true;
		// //gui.focus();
		// change(counter,myName);
		// System.out.println("Change has made");
		// // dispose();
		if (ask){ //대화묻는팝업창띄우기.
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
