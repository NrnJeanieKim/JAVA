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
	JPanel jp1,jp2,jp3,lower,upper,downP;
	JLabel leftL,rightL;
	JLabel logoLb, photoLb;
	Load l;
	int counter =1;
	ImageIcon img, mainI,frameIc,likeI,disLikeI;
	JLabel proLb,mainL;
	Container cp;
		
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
		myFile = new File(fName+"_like.txt"); //�� ��/�� �����ϴ� ����Ʈ
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

			proLb = new JLabel(l.profile);
			proLb.setLayout(null);
			proLb.setBounds(47,340,400,100);
			proLb.setFont(new Font("����", 0, 10));
			proLb.setHorizontalTextPosition(SwingConstants.CENTER);
			proLb.setVerticalAlignment(SwingConstants.CENTER);
			mainL.add(proLb);

		}catch(IOException ie){}

		// �ϴ� �г� 
		downP = new JPanel();
		downP.setLayout(null);
		downP.setBounds(0,425,400,109);
		downP.setBackground(new Color(-1));
		mainL.add(downP);

		rightL = new JLabel(likeI);
		rightL.setBounds(118, 11, 50, 50);
		downP.add(rightL);

		leftL = new JLabel(disLikeI);
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
