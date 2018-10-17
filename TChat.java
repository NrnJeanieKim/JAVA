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

public class TChat extends JFrame implements Runnable{
	Container cp;
	JPanel pNorth, pSouth;
	JTextArea ta;
	JScrollPane sp;
	JLabel Id;
    JTextField tf;
	JButton bBack, cSend, bReport; //�ڷ� ����, �޽��� ������, �Ű� ��ư
	ImageIcon ii1, ii2;
	String ip = "127.0.0.1";
	int port = 5000;
	Socket s;
	OutputStream os;
	DataOutputStream dos;
	InputStream is;
    DataInputStream dis;
	String line;
	String report = "[ ��Ӿ�, ���� ���� ������ �Ű� ó���Ǿ����ϴ�.\n 5ȸ ������ ������ �����˴ϴ�.\n 3�� �� ��ȭ�� ����˴ϴ�.]";
	String myId = "�����̵�";
	String yourId = "���Ǿ��̵�";
	//////////////////
	InetAddress inetAddress;
	DatagramSocket datagramSocket = null;
	DatagramPacket datagramPacket = null;
	DatagramPacket datagramPacket2 = null;
	byte[] buffer = new byte[512];
	MulticastSocket multicastSocket = null;
	static int first = 1; //static���� �����..........�������״¾ȶ߳�..????

	TChat(){
		//Login in = new Login();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ID�� �Է��ϼ��� : ");
		try{
			myId = br.readLine();
		}catch(IOException ie){}
		init();
		inetAddress = null;	
		
		  try{
			  inetAddress = InetAddress.getByName("224.1.1.0"); // 224.0.0.0 to 239.255.255.255 ���� ����ؾ� ��Ƽ���� ��...
		  }catch(UnknownHostException ue){
			  System.out.println("�ùٸ��� ���� ������");}
		 
		 // UDPMultiReceive udpMultiReceive = null;
		 // UDPMultiSend udpMultiSend = null;
		 try {
			 //Socket ����
				datagramSocket = new DatagramSocket(); 
		   System.out.println("*********** " + myId + "�� ���� ***********");
		   // 3. ������ �ޱ�
		   //udpMultiReceive = new UDPMultiReceive(inetAddress, portNumber);
		  // udpMultiReceive.start();
		   // 4. ������ ������
		 //  udpMultiSend = new UDPMultiSend(inetAddress, portNumber, strId);
		//   udpMultiSend.start();
			
			//System.out.println("name : "+in.name+", loginid : "+in.loginid+", loginpassword : "+in.loginpass);
			 ////////////new GUI();
		 // } catch (UnknownHostException e) {
	  // e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	  Thread th = new Thread(this);
	  th.start();
	}
	void init(){
		pNorth = new JPanel();
		loadImageIcon();
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 74, 3));
		
		bBack = new JButton(ii1);
		getContentPane().add(bBack);
		
		//��ȭ�ϴ� ���� ���̵� �߰� �ؾ� ��! (���� �� ��)
		Id = new JLabel("Chat ID");
		getContentPane().add(Id);
		
		bReport = new JButton(ii2);
		getContentPane().add(bReport);
		
		ta = new JTextArea(22, 34);
		getContentPane().add(ta);
		getContentPane().setVisible(true);
		ta.setEditable(false);
		ta.setEnabled(true);
		ta.setLineWrap(true);

		sp = new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().setVisible(true);
		getContentPane().add(sp);

		tf = new JTextField();
		tf.setColumns(34);
		getContentPane().add(tf);
		getContentPane().setVisible(true);
		tf.setEnabled(true);
		
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

	public void run(){
		byte[] buffer2 = new byte[512];
		try{
			//datagramSocket = new DatagramSocket(port); �̰ɷξȵ�.
			multicastSocket = new MulticastSocket(port);// 2. DatagramPacket�� �ޱ� ���� Socket ����
			multicastSocket.joinGroup(inetAddress);// 3. �׷� ��� - ��� �����ϰ� ��
			while(true) {	// �޽��� ��� ����
	// 4. Data�� ���� Packet ����
				datagramPacket = new DatagramPacket(buffer2, buffer2.length);
	// 5. ��Ƽĳ��Ʈ�� �����ϴ� �޽��� ����
				//datagramSocket.receive(datagramPacket2);
				multicastSocket.receive(datagramPacket);
	// 6. ���ŵ� �޽��� ���
				if (first ==1)	{
					new AskYes();
				}
				String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				if (msg.startsWith(myId)){
				}else{
					ta.append(msg+"\n");
					first++;
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

	 class ChatHandler implements ActionListener { ///����Ŭ������ ������ ����
		 public void actionPerformed(ActionEvent e){
			Object obj = e.getSource();
			if(obj == bBack){
				System.exit(0);
			}else if(obj == cSend){
				line = tf.getText().trim();
				tf.setText("");
				if(line.length() != 0 && !(line.equals(""))){
					try{
						ta.append(myId+" : "+line+"\n"); //chat ID �տ� ä�� ���� �ʰ�
						buffer = line.getBytes();
						datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
						datagramSocket.send(datagramPacket);
						//dos.writeUTF(c.line);
						//dos.flush();
					}catch(IOException ie){System.out.println("send ����");}
				}
			}else if(obj == bReport){
				int answer = JOptionPane.showConfirmDialog(null, "�Ű��Ͻðڽ��ϱ�?\n(�Ű�� ��ȭ ������ ����Ʈ�Ǹ�, ��ȭ�� ����˴ϴ�.)",
					"�Ű��ϱ�", JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.YES_OPTION){
					JOptionPane.showMessageDialog(null, "�Ű� �Ϸ�Ǿ����ϴ�. ��ȭ�� �����մϴ�.", "�Ű�Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
					//���� ��ȭâ�� ����
					try{
						dos.writeUTF("[ ��Ӿ�, ���� ���� ������ �Ű� ó���Ǿ����ϴ�.\n 5ȸ ������ ������ �����˴ϴ�.\n 3�� �� ��ȭ�� ����˴ϴ�.]");
					}catch(IOException ie){
					}finally{
						 System.exit(0);
					}
				}
			}
		 }
		 
	 }
}
////��𼱰� datagramSocket.close();�������.�ٺ��������ݾƾ���...