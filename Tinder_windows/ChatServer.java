import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ChatServer extends Thread{
	ServerSocket ss;
	int port = 5000;
	ArrayList<CModule> list = new ArrayList<CModule>();
	ChatServer(){
		try{
			ss = new ServerSocket(port);
			pln(port+"�� �������� ��Ƽ ���� �����...");
			start();
			while(true){
				Socket s = ss.accept();
				CModule m = new CModule(s, this);
				Thread threadM = new Thread(m);
				list.add(m);
				threadM.start();
			}
		}catch(IOException ie){
			pln(port+"�� ��Ʈ�� �̹� �����...");
		}
	}
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public void run(){
		speak();
	}
	void speak(){ //������ keyboard-->���ӵ� ���ϵ�
		try{
			String line = "";
			while((line = br.readLine()) != null){
				if(list.size() != 0) list.get(0).broadcast("������>> " + line);
			}
		}catch(IOException ie){}
	}

	void pln(String str){
		System.out.println(str);
	}
	void p(String str){
		System.out.print(str);
	}
	public static void main(String[] args){
		new ChatServer();
	}
}
