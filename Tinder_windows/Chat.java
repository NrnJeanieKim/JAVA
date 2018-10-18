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

	Chat(){
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

		try{
			s = new Socket(ip, port);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
			is = s.getInputStream();
			dis = new DataInputStream(is);

			Thread thread = new Thread(this);
			thread.start();
		}catch(UnknownHostException ne){
			pln("[ �ش� ������ ã�� �� �����ϴ�. ]");
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
			ta.append("[ ������ �ٿ�Ǿ����ϴ�. 3�� ��, ���α׷��� �����մϴ�. ]");
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
					//c.ta.append(c.line); //chat ID �տ� ä�� ���� �ʰ�
					c.dos.writeUTF(c.line);
					c.dos.flush();
				}catch(IOException ie){}
			}
		}else if(obj == c.bReport){
			int answer = JOptionPane.showConfirmDialog(null, "�Ű��Ͻðڽ��ϱ�?\n(�Ű�� ��ȭ ������ ����Ʈ�Ǹ�, ��ȭ�� ����˴ϴ�.)",
				"�Ű��ϱ�", JOptionPane.OK_CANCEL_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "�Ű� �Ϸ�Ǿ����ϴ�. ��ȭ�� �����մϴ�.", "�Ű�Ϸ�",
					JOptionPane.INFORMATION_MESSAGE);
				//���� ��ȭâ�� ����
				try{
					c.dos.writeUTF("[ ��Ӿ�, ���� ���� ������ �Ű� ó���Ǿ����ϴ�.\n 5ȸ ������ ������ �����˴ϴ�.\n 3�� �� ��ȭ�� ����˴ϴ�.]");
				}catch(IOException ie){
				}finally{
					 System.exit(0);
				}
			}
		}
	}
}
