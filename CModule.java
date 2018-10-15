import java.io.*;
import java.net.*;

class CModule implements Runnable{
	ChatServer gs;
	Socket s;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	String chatId;
	String report = "[ ��Ӿ�, ���� ���� ������ �Ű� ó���Ǿ����ϴ�.\n 5ȸ ������ ������ �����˴ϴ�.\n 3�� �� ��ȭ�� ����˴ϴ�.]";

	CModule(Socket s, ChatServer gs){
		this.s = s;
		this.gs = gs;
		try{
			is = s.getInputStream();
			os = s.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
		}catch(IOException ie){}
	}
	public void run(){
		readWrite();
	}
	void readWrite(){
		try{
			chatId = dis.readUTF();
			broadcast(chatId+"���� �����ϼ̽��ϴ�. (���� ������ "+gs.list.size()+" ��)"); //������ client��
			gs.pln(chatId+"�� ����!! (���� ������ "+gs.list.size()+" ��)");//���� �ܼ�â
			while(true){
				String msg = dis.readUTF();
				if(msg.equals(report)){
					broadcast(msg);
				}else{
					broadcast(chatId+">> "+msg);
					//gs.pln(chatId+">> "+msg); ����â���� �� ���̰� ��.
				}
			}
		}catch(IOException ie){
			gs.list.remove(this);
			broadcast(chatId+"���� ä�ù��� �������ϴ�."); //������ Ŭ���̾�Ʈ��
			gs.pln(chatId+"�� ����!! (���� ������ "+gs.list.size()+" ��)"); //���� �ܼ�â
			if (gs.list.size() == 0){
				gs.pln("ä�� ����� ��� ����. ä�� ������ �����Ͻ÷��� \"/exit\"�� �Է����ּ���.");
			}
		}
	}
	void broadcast(String msg){
		try{
			for(CModule m : gs.list){
				m.dos.writeUTF(msg);
				m.dos.flush();
			}
		}catch(IOException ie){}
	}
}
