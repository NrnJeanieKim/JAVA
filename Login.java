import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

class Login 
{
	JFrame f;
	JLabel la,laId,laPw;
	JPasswordField pw;
	JButton b;
	JTextField id;
	JPanel idPanel, pwPanel, loginPanel;
	String loginid, loginpass;

	Login(){
		loginUi();
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
		laId = new JLabel("아이디    ");
		laPw = new JLabel("패스워드");
		id = new JTextField(10);
		pw = new JPasswordField(10);
		idPanel.add(laId);
		idPanel.add(id);
		pwPanel.add(laPw);
		pwPanel.add(pw);
		pw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				loginCheck();    
            }
        });


		loginPanel = new JPanel();
		b = new JButton("로그인");
		loginPanel.add(b);
		b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
    
            }
        });
		

		f.add(idPanel,"North");
		f.add(pwPanel,"Center");
		f.add(loginPanel,"South");

		f.setTitle("Login");
		f.setSize(200,150);
		f.setResizable(false);
		f.setLocation(700,300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		// -----------------------   종료 옵션 창 ------------------------------------------
		f.addWindowListener(new WindowAdapter(){  
			public void windowClosing(WindowEvent e){
				int answer = JOptionPane.showConfirmDialog(
					f, "종료할까요?", "선택", JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.YES_OPTION){
					System.exit(-1);
				}else{
					f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}

	
	// -----------------------   로그인 시 ID / PW 확인 창 ------------------------------------------  
	void loginCheck(){ 
		try{
			BufferedReader br = new BufferedReader(new FileReader("wList.txt"));	
			String s;
				while((s=br.readLine()) != null){
					String [] sArray  = s.split("\\*");
					loginid = String.valueOf(sArray[1]);
					loginpass = String.valueOf(sArray[2]);
				
					if(id.getText().equals(loginid) && new String (pw.getPassword()).equals(loginpass)){
						JOptionPane.showMessageDialog(null, "로그인 완료!");
							 boolean check = true;
							 if(check){
								f.setVisible(false);
								break;
							 }
					}else {
							JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 다시 확인하세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);  
							id.setText("");
							pw.setText("");
							break;
					 }  // 작성한 ID / PW 초기화 ------------------- 근데 커서가 ID로 안가고 PW로 감.. ㅠ
				}
			}catch(IOException ie){}
	}


	public static void main(String[] args) 
	{
		new Login();
	}
}
