import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class Login
{
	JLabel mainLa, textLa, laId, laPw;
	JPasswordField pw;
	JButton loginB;
	JTextField id;
	JPanel idPanel, pwPanel, loginPanel;
	JPanel upPanel, downPanel;
	JFrame f;
	ImageIcon frameIcon, mainIcon, idIcon, pwIcon, loginIcon;

	static String name, loginid, loginpass;
	static String [] sArray;

	public Login(){


		f = new JFrame();
		frameIcon = new ImageIcon("Tinder Logo.png");
		f.setIconImage(frameIcon.getImage());

		f.setTitle("Tinder Login");
		f.setSize(450,350);
		f.setResizable(false);
		f.setLocation(500,100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainIcon = new ImageIcon("main.png");
		mainLa = new JLabel(mainIcon);
		f.add(mainLa);

		setUI();

		f.getContentPane().add(mainLa, BorderLayout.CENTER);
		f.setVisible(true);
	}
	public void setUI(){

		// �ϴ� �г�
		downPanel = new JPanel();
		downPanel.setLayout(null);
		downPanel.setBackground(new Color(-1));
		downPanel.setBounds(8, 130, 420, 176); // ��ġ��. int x, int y, int width, int height
		downPanel.setBorder(new EtchedBorder());
		mainLa.add(downPanel);

		// �ϴ� �гο� �� ���� (���̵�, �н�����, �α���)
		// 1. ���̵�
		idIcon = new ImageIcon("ID.png");
		laId = new JLabel(idIcon);
		laId.setBounds(100, 36, 80, 30);
		downPanel.add(laId);

		id = new JTextField();
		id.setFont(new Font("����", 0, 14));
		id.setForeground(new Color(-13421773));
		id.setBackground(new Color(-1));
		id.setBounds(194, 36, 124, 30);
		downPanel.add(id);


		// 2. �н�����
		pwIcon = new ImageIcon("PW.png");
		laPw = new JLabel(pwIcon);
		laPw.setBounds(100, 80, 80, 30);
		downPanel.add(laPw);

		pw = new JPasswordField();
		pw.setFont(new Font("����", 0, 14));
		pw.setForeground(new Color(-13421773));
		pw.setBackground(new Color(-1));
		pw.setBounds(194, 80, 124, 30);
		downPanel.add(pw);
		pw.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				loginCheck();
            }
        });

		// 3. �α��� ��ư
		loginIcon = new ImageIcon("Login.png");
		loginB = new JButton(loginIcon);
		loginB.setBounds(173, 135, 80, 30);
		downPanel.add(loginB);
		loginB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				loginCheck();
            }
        });
		// -----------------------   ���� �ɼ� â ------------------------------------------
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int answer = JOptionPane.showConfirmDialog(
					f, "�����ұ��?", "����", JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.YES_OPTION){
					System.exit(-1);
				}else{
					f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}
	// -----------------------   �α��� �� ID / PW Ȯ�� â ------------------------------------------
	public void loginCheck(){
		try{
			BufferedReader br = new BufferedReader(new FileReader("wList.txt"));
			String s;  // txt ���� �б�
			outer:
			for(int i = 0;i<=1;i++){
				while((s=br.readLine())!=null){
					 sArray  = s.split("\\*");  //  * �������� ���� split
					 loginid = String.valueOf(sArray[1]);  // index[1] ID����
					 loginpass = String.valueOf(sArray[2]);  // index[2] PW ����
					 name = String.valueOf(sArray[0]);  // index[0] �̸� ����

					if(id.getText().equals(loginid) && new String (pw.getPassword()).equals(loginpass)){
						JOptionPane.showMessageDialog(null, "�α��� �Ϸ�!");
							 boolean check = true;
							 if(check){
								f.setVisible(false);
								new GUI(name);
								break outer;
							 }
					}
				}if(i==0)br = new BufferedReader(new FileReader("mList.txt"));
				else if(i==1){
					JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���ϼ���.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					pw.setText("");
					id.setText("");
					id.requestFocus();
				}
			}
			}catch(IOException ie){}
	}
	public static void main(String[] args) {
		new Login();
	}
}
