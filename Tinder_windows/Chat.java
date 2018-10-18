import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Chat extends JFrame implements Runnable{
	Container cp;
	JPanel pNorth, pSouth;
	JTextArea ta;
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

	Chat(){
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

		try{
			s = new Socket(ip, port);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
			is = s.getInputStream();
			dis = new DataInputStream(is);

			Thread thread = new Thread(this);
			thread.start();
		}catch(UnknownHostException ne){
			pln("[ 해당 서버를 찾을 수 없습니다. ]");
		}catch(IOException ie){
		}
		init();
	}
	void init(){
		setUI();
	}
	void setUI(){
		ActionListener listener = new ChatHandler(this);
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
		listen();
	}
	void listen(){ //Socket --> Moniter
		try{
			while(true){
				String msg = dis.readUTF();
				ta.append(msg+"\n");
				ta.setCaretPosition(ta.getDocument().getLength());

				if(msg.equals(report)){
					try{
						Thread.sleep(3000);
						this.dispose();
					}catch(InterruptedException ie2){}
				}
			}
		}catch(IOException ie){
			ta.append("[ 서버가 다운되었습니다. 3초 후, 프로그램을 종료합니다. ]");
			try{
				Thread.sleep(3000);
				System.exit(0);
			}catch(InterruptedException ie3){}
		}finally{
			closeAll();
		}
	}
	void closeAll(){
		try{
			if(dis != null) dis.close();
			if(dos != null) dos.close();
			if(is != null) is.close();
			if(os != null) os.close();
			if(s != null) s.close();
		}catch(IOException ie){}
	}
	void pln(String str){
		System.out.println(str);
	}
	void p(String str){
		System.out.print(str);
	}
	public static void main(String[] args) {
		Chat c = new Chat();
		c.init();
	}
}

class ChatHandler implements ActionListener {
	Chat c;
	ChatHandler(Chat c){
		this.c = c;
	}
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == c.bBack){
			System.exit(0);
		}else if(obj == c.cSend){
			c.line = c.tf.getText().trim();
			c.tf.setText("");
			if(c.line.length() != 0 && !(c.line.equals(""))){
				try{
					//c.ta.append(c.line); //chat ID 앞에 채팅 뜨지 않게
					c.dos.writeUTF(c.line);
					c.dos.flush();
				}catch(IOException ie){}
			}
		}else if(obj == c.bReport){
			int answer = JOptionPane.showConfirmDialog(null, "신고하시겠습니까?\n(신고시 대화 내용이 리포트되며, 대화가 종료됩니다.)",
				"신고하기", JOptionPane.OK_CANCEL_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "신고가 완료되었습니다. 대화를 종료합니다.", "신고완료",
					JOptionPane.INFORMATION_MESSAGE);
				//상대방 대화창도 종료
				try{
					c.dos.writeUTF("[ 비속어, 스팸 등의 이유로 신고 처리되었습니다.\n 5회 누적시 계정이 정지됩니다.\n 3초 후 대화가 종료됩니다.]");
				}catch(IOException ie){
				}finally{
					 System.exit(0);
				}
			}
		}
	}
}
