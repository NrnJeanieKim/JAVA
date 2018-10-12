import java.awt.*; //이부분수정함!!~!
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

class UDPui extends JFrame{
	UDPsc sc;
	//////보내는 창
	JPanel upPanel,receiverPanel, senderPanel, msgPanel; //맨위,받는사람, 보내는사람, 보낼 메세지 패널
	JTextField senderField; //보내는사람 쓰는 곳
	JComboBox receiverBox; //받는사람 고르는 콤보박스
	JTextArea msgArea; //보낼 메세지 쓰는 곳.
	JScrollPane msgScroll; //보낼 메세지 스크롤. msgArea를 이 안에 넣어야함
	JButton receiverB, senderB, sendB, sendMsgB; //받는사람, 보내는사람, 보내기 버튼, 보낼 메세지
	
	
	
	String name, ip; //이름과 아이피
	

	UDPui(UDPsc sc){
		this.sc = sc;
		init();
	}
	void init(){
		ActionListener listener = new uiHandler(this); //★★★★리스너 클래스로 추가하기!!!내부가편할듯?★★★★
		/////생성하기
		upPanel = new JPanel();
		receiverPanel = new JPanel(); senderPanel = new JPanel(); msgPanel = new JPanel();
		receiverBox =  new JComboBox(names); //이름-ip연결.....★★★★★★★★★★★
		senderField = new JTextField();
		msgArea = new JTextArea(); //꾸밀까★★★★★★★★
		msgScroll = new JScrollPane(msgArea); //
		msgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		msgScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//가로 스크롤 안생기고 자동 줄바꿈하게 못하나???★★★★★★★★★★★★
		receiverB = new JButton("받는 사람");
		senderB = new JButton("보내는 사람");
		sendMsgB = new JButton("보낼 메세지");
		sendB = new JButton("보내기");
		try{
			pic = new ImageIcon(ImageIO.read(new File("다운로드.ico")));;
		}catch(IOException ie){pln("이미지파일 오류");}
		picButton = new JButton(pic); 
		gotMsgArea = new JTextArea(); 
		msgOkButton = new JButton("확인");

		/////보내는창 배치하기
		upPanel.setLayout(new FlowLayout());
		upPanel.add(receiverPanel);
		upPanel.add(senderPanel);//upPanel에 받는/보내는사람 붙이기
		add(upPanel, BorderLayout.NORTH); //맨위에 upPanel붙이기.
		receiverPanel.add(receiverBox);
		senderPanel.add(senderField);

		msgPanel.setLayout(new FlowLayout());
		add(msgPanel, BorderLayout.CENTER);
		msgPanel.add(msgArea); //msgPanel에 msgArea붙이기
		msgPanel.add(msgOkButton); //msgPanel에 보내기 버튼 붙이기

		////// 받는창 배치하기
		
		
	}

	void fileRead(){ //이름/ip파일 읽어오기
		
	}
	
	void pln(String str){
		System.out.println(str);
	}
	void p(String str){
		System.out.print(str);
	}

	public static void main(String[] args){
		new UDPui().init();
	}
////내부클래스 리스너
	class uiHandler implements ActionListener{
		UDPui ui;
		uiHandler(UDPui ui){
			this.ui = ui;
		}
		public void actionPerformed(ActionEvent e){
			Object obj = e.getSource();
			//if (obj == cp.in){
		//	}
		}

	}
}

class Textui extends JFrame{
	UDPsc sc;
	Textui(UDPsc sc){
		this.sc = sc;
	}
	//////받는 창
	JButton picButton; // 프로필이미지
	ImageIcon pic; //picButton에 넣을 이미지
	JTextArea gotMsgArea; //받은 메세지 띄우는 Area
	JButton msgOkButton; //받은 메세지 확인 누르면 메세지 창 닫히는 버튼

}
