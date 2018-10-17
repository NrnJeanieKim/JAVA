import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;

public class TChat extends JFrame implements Runnable{
	Container cp;
	JPanel pNorth, pCenter, pSouth;
	//JTextArea ta; 이거 이제 안씀
	JPanel chatPanel;/////////////
	JScrollPane scroll;//////////////
	JScrollPane sp;
	JLabel Id;
    JTextField tf;
	JButton bBack, cSend, bReport; //뒤로 가기, 메시지 보내기, 신고 버튼
	ImageIcon ii1, ii2;
	String ip = "127.0.0.1";
	int port = 5000;
	Socket s;
	OutputStream os;
	DataOutputStream dos;
	InputStream is;
    DataInputStream dis;
	String line;
	String report = "[ 비속어, 스팸 등의 이유로 신고 처리되었습니다.\n 5회 누적시 계정이 정지됩니다.\n 3초 후 대화가 종료됩니다.]";
	String myId = "내아이디";
	String yourId = "너의아이디";
	//////////////////
	InetAddress inetAddress;
	DatagramSocket datagramSocket = null;
	DatagramPacket datagramPacket = null;
	DatagramPacket datagramPacket2 = null;
	byte[] buffer = new byte[512];
	MulticastSocket multicastSocket = null;
	//static int first = 1; //static으로 만들면..........상대방한테는안뜨나..????
	static final long serialVersionUID = 1L;//////////////////
	


	TChat(){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ID를 입력하세요 : ");
		try{
			myId = br.readLine();
		}catch(IOException ie){}
		init();
		inetAddress = null;	
		  try{
			  inetAddress = InetAddress.getByName("224.1.1.0"); // 224.0.0.0 to 239.255.255.255 범위 사용해야 멀티소켓 됨...
		  }catch(UnknownHostException ue){
			  System.out.println("올바르지 않은 아이피");}
		 try {
			 //Socket 열기
				datagramSocket = new DatagramSocket(); 
		   System.out.println("*********** " + myId + "님 접속 ***********");

	   } catch (IOException e) {
		e.printStackTrace();
	  }
	  Thread th = new Thread(this);
	  th.start();
	}
	void init(){
		chatPanel = new JPanel(); /////
		pNorth = new JPanel();
		loadImageIcon();
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 74, 3));
		
		bBack = new JButton(ii1);
		getContentPane().add(bBack);
		
		//대화하는 상대방 아이디가 뜨게 해야 함! (구현 안 됨)
		Id = new JLabel("Chat ID");
		getContentPane().add(Id);
		
		bReport = new JButton(ii2);
		getContentPane().add(bReport);
		
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));
		chatPanel.add(Box.createVerticalGlue());
		//ta = new JTextArea(22, 34);
		getContentPane().add(chatPanel);
		getContentPane().setVisible(true);
		//chatPanel.setEdichatPanelble(false);
		chatPanel.setEnabled(true);
		//chatPanel.setLineWrap(true);
		scroll = new JScrollPane();
		scroll.setViewportView(chatPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll);


		sp = new JScrollPane(chatPanel);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setVisible(true);
		getContentPane().add(sp);

		tf = new JTextField();
		tf.setColumns(34);
		getContentPane().add(tf);
		getContentPane().setVisible(true);
		tf.setEnabled(true);
		tf.requestFocus();////////
		
		cSend = new JButton("Send");
		getContentPane().add(cSend);

		setUI();
	}
	void setUI(){
		ActionListener listener = new ChatHandler();
		bBack.addActionListener(listener);
		bReport.addActionListener(listener);
		cSend.addActionListener(listener);
		tf.addActionListener(listener);
		always();
	}
	void always(){
		setTitle("Tinder? Tinder!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(400, 540);
		setLocation(500, 100);
		setVisible(true);
	}
	void loadImageIcon(){
		try{
			BufferedImage bi = ImageIO.read(new File("imgs/back3.png"));
			ii1 = new ImageIcon(bi);
			BufferedImage bi2 = ImageIO.read(new File("imgs/report4.png"));
			ii2 = new ImageIcon(bi2);
		}catch(IOException ie){
		}
	}

	void append(String str) {
		LeftArrowBubble leftArrowBubble = new LeftArrowBubble();

		final int size = 500;
		leftArrowBubble.setMaximumSize(new Dimension(size, size));

		JLabel tac = new JLabel();

		tac.setMaximumSize(new Dimension(size - 50, size - 50));

		final int maximumSize = 56;
		String textWithSeparators = "";
		final StringTokenizer textTokenizer = new StringTokenizer(str, " \t\n\r", true);

		while(textTokenizer.hasMoreTokens()) {
			final String part = textTokenizer.nextToken();
			for (int beginIndex = 0; beginIndex < part.length();
				 beginIndex += maximumSize)
				textWithSeparators += (beginIndex == 0 ? "" : " ")
					+ part.substring(beginIndex,
									 Math.min(part.length(),
											  beginIndex + maximumSize));
		}
		System.out.println(textWithSeparators);

		tac.setText("<html><body style='width:" + (size - 150) + "px;padding:15px;display:block;'>"
						+ textWithSeparators + "</body></html>");

		tac.setOpaque(false);
		leftArrowBubble.add(tac, BorderLayout.NORTH);

		chatPanel.add(leftArrowBubble);     

		chatPanel.add(Box.createRigidArea(new Dimension(0,5)));
		Rectangle rect = chatPanel.getBounds();
		Rectangle r2 = scroll.getViewport().getVisibleRect();
		chatPanel.scrollRectToVisible(new Rectangle((int) rect.getWidth(), 
				(int) rect.getHeight(), (int) r2.getWidth(), (int) r2.getHeight()));
		revalidate();
		repaint();
	}

	public void run(){
		byte[] buffer2 = new byte[512];
		try{
			multicastSocket = new MulticastSocket(port);// 2. DatagramPacket을 받기 위한 Socket 생성
			multicastSocket.joinGroup(inetAddress);// 3. 그룹 등록 - 통신 가능하게 함
			while(true) {	// 메시지 계속 받음
	// 4. Data를 받을 Packet 생성
				datagramPacket = new DatagramPacket(buffer2, buffer2.length);
	// 5. 멀티캐스트에 존재하는 메시지 받음
				//datagramSocket.receive(datagramPacket2);
				multicastSocket.receive(datagramPacket);
	// 6. 수신된 메시지 출력
				/*if (first ==1)	{
					new AskYes();
				}*/
				String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				if (msg.startsWith(myId)){
				}else if(msg.equals(report)){
					try{
						//ta.append("[ 비속어, 스팸 등의 이유로 신고 처리되었습니다.\n 5회 누적시 계정이 정지됩니다.\n 3초 후 대화가 종료됩니다.]");
						append("[ 비속어, 스팸 등의 이유로 신고 처리되었습니다.\n 5회 누적시 계정이 정지됩니다.\n 3초 후 대화가 종료됩니다.]");
						Thread.sleep(3000);
						System.exit(0);
					}catch(InterruptedException ie2){}
				}else{
					append(msg);
					//first++;
				}
			}
		}catch (IOException e) {
			System.err.println(e);
			System.exit(0);
			} finally {
				multicastSocket.close();
			}
	}

	 public static void main(String[] args) {
		 new TChat();
	 }

	 class ChatHandler implements ActionListener { ///내부클래스로 리스너 구현
		 public void actionPerformed(ActionEvent e){
			Object obj = e.getSource();
			if(obj == bBack){
				System.exit(0);
			}else if(obj == cSend){
				line = tf.getText().trim();
				tf.setText("");
				if(line.length() != 0 && !(line.equals(""))){
					try{
						append(myId+" : "+line+"\n"); //chat ID 앞에 채팅 뜨지 않게
						line = myId+" : "+line;
						buffer = line.getBytes();
						datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
						datagramSocket.send(datagramPacket);
					}catch(IOException ie){System.out.println("send 오류");}
				}
			}else if (obj == tf){
				line = tf.getText().trim();
				tf.setText("");
				if(line.length() != 0 && !(line.equals(""))){
					try{
						append(myId+" : "+line+"\n"); //chat ID 앞에 채팅 뜨지 않게
						line = myId+" : "+line;
						buffer = line.getBytes();
						datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
						datagramSocket.send(datagramPacket);
					}catch(IOException ie){System.out.println("send 오류");}
				}
			}
			else if(obj == bReport){
				int answer = JOptionPane.showConfirmDialog(null, "신고하시겠습니까?\n(신고시 대화 내용이 리포트되며, 대화가 종료됩니다.)",
					"신고하기", JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.YES_OPTION){
					JOptionPane.showMessageDialog(null, "신고가 완료되었습니다. 대화를 종료합니다.", "신고완료",
						JOptionPane.INFORMATION_MESSAGE);
					//상대방 대화창도 종료
					try{
						buffer = report.getBytes();
						datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
						datagramSocket.send(datagramPacket);
						}catch(IOException ie){
					}finally{
						 System.exit(0);
					}
				}
			}
		 }
		 
	 }
}
////어디선가 datagramSocket.close();해줘야함.다보내고나서닫아야함...
