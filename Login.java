import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;

class Login 
{
	JFrame f;
	JLabel la,laId, laPw;
	JPasswordField pw;
	JButton b;
	JTextField id;
	JPanel idPanel, pwPanel, loginPanel;

	Login(){
		loginUi();
		//if fileWm���� �α��� ��
		//if FileMm���� �α��� �� 
	}


	void loginUi(){
		f = new JFrame();
		f.setLayout(new BorderLayout());
		EtchedBorder eb = new EtchedBorder();
		
		la = new JLabel();
		la.setBorder(eb);
		f.add(la);

		idPanel = new JPanel();
		pwPanel = new JPanel();
		laId = new JLabel("���̵�    ");
		laPw = new JLabel("�н�����");
		id = new JTextField(10);
		pw = new JPasswordField(10);
		idPanel.add(laId);
		idPanel.add(id);
		pwPanel.add(laPw);
		pwPanel.add(pw);

		loginPanel = new JPanel();
		b = new JButton("�α���");
		loginPanel.add(b);

		f.add(idPanel,"North");
		f.add(pwPanel,"Center");
		f.add(loginPanel,"South");

		f.setTitle("Login");
		f.setSize(200,150);
		f.setResizable(false);
		f.setLocation(700,300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

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
	public static void main(String[] args) 
	{
		new Login();
	}
}
