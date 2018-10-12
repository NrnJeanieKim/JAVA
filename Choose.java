import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

class Choose extends JFrame//프로필 띄우고, 키보드 방향키 따라서 like/dislike 리스트 만들어서 저장, 대화하시겠습니까?(팝업창) 
{
	FileReader fr = null; //상대방 성별 리스트 읽기
	String FName = ""; //여자면 mList.txt, 남자면 wList.txt 읽기. 아이디 인덱스 맞춰서...
	//BufferedReader br = null;
	String name = ""; //여자w1~w10, 남자 m1~m10
	final String LIKE = "1";
	final String DISLIKE = "2";
	String answer = ""; //좋으면 1, 싫으면 2
	ArrayList<String> answers;
	//fr = new FileReader(fName);
	//br = new BufferedReader(fr); 로또 참조

	//내가 할 부분 : 키보드 방향키 따라서 like/dislike 리스트 만들어서 저장
	JFrame jr;

	File file = new File(name+"_like.txt");
	FileWriter writer = null;
	
	///////파일에 쓰는게 어느 시점이어야 하지..??마지막에 쓰면 실시간매치가안되고...
	Choose(){
		answers = new ArrayList<String>();
		try{
			writer = new FileWriter(file, true); //기존 내용에 이어서 쓰려고 true..?
			/*for (String answer : answers){
				writer.write(answer);
			}
			writer.flush();*/
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}

	void init(){
		jr = new JFrame();
		addKeyListener(new MyKeyListener()); //리스너 적응을 어디다하지..jr?jPanel??contentpane?
		setUI();
	}

	void setUI(){
		setTitle("Our Tinder");
		setSize(350, 500);
		setLocation(500,300);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

 //내부클래스 KeyListener!!!!!!!
	 class MyKeyListener extends KeyAdapter{ 
		public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT: //싫어요
		try{
			writer.write(DISLIKE);
			writer.flush();
		}catch(IOException ie){} break;
		case KeyEvent.VK_RIGHT: //좋아요
		try{	
			writer.write(LIKE);
			writer.flush();
		}catch(IOException ie){} break;
		}
		}
	  }

	 public static void main(String[] args){
		new Choose().init();
	}
}
	

	
