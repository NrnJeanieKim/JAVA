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
	String report = "[ 비속어, 스팸 등의 이유로 신고 처리되었습니다.\n 5회 누적시 계정이 정지됩니다.\n 3초 후 대화가 종료됩니다.]";

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
			broadcast(chatId+"님이 입장하셨습니다. (현재 접속자 "+gs.list.size()+" 명)"); //접속한 client들
			gs.pln(chatId+"님 입장!! (현재 접속자 "+gs.list.size()+" 명)");//서버 콘솔창
			while(true){
				String msg = dis.readUTF();
				if(msg.equals(report)){
					broadcast(msg);
				}else{
					broadcast(chatId+">> "+msg);
					//gs.pln(chatId+">> "+msg); 서버창에는 안 보이게 함.
				}
			}
		}catch(IOException ie){
			gs.list.remove(this);
			broadcast(chatId+"님이 채팅방을 나갔습니다."); //접속한 클라이언트들
			gs.pln(chatId+"님 퇴장!! (현재 접속자 "+gs.list.size()+" 명)"); //서버 콘솔창
			if (gs.list.size() == 0){
				gs.pln("채팅 사용자 모두 퇴장. 채팅 서버를 종료하시려면 \"/exit\"을 입력해주세요.");
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
