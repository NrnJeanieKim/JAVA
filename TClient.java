import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TClient {
	TClient(){
		InetAddress inetAddress = null;	
		Login in = new Login();
		  try{
			  inetAddress = InetAddress.getByName("224.1.1.0"); // 224.0.0.0 to 239.255.255.255 범위 사용해야 멀티소켓 됨...
		  }catch(UnknownHostException ue){
			  System.out.println("올바르지 않은 아이피");}
		  BufferedReader br = null;
		  UDPMultiReceive udpMultiReceive = null;
		  UDPMultiSend udpMultiSend = null;
		  int portNumber = 7120;
		  String strId = "";
	  try {
		// 1. IP 주소, port 번호 입력 사항 확인
		  /*if(args.length != 2) {
			System.out.println("IP, Port 입력 바랍니다.");
			System.exit(0);
		   }else {
			inetAddress = InetAddress.getByName(args[0]); // 접속할 ip
			portNumber = Integer.parseInt(args[1]); // port
		  }*/
		   // 2. ID 입력
		   br = new BufferedReader(new InputStreamReader(System.in));
		   System.out.print("id를 입력하시오 ==> ");
		   strId = br.readLine();
		   System.out.println("*********** " + strId + "님 접속 ***********");
		   // 3. 데이터 받기
		   udpMultiReceive = new UDPMultiReceive(inetAddress, portNumber);
		   udpMultiReceive.start();
		   // 4. 데이터 보내기
		   udpMultiSend = new UDPMultiSend(inetAddress, portNumber, strId);
		   udpMultiSend.start();
			
			System.out.println("name : "+in.name+", loginid : "+in.loginid+", loginpassword : "+in.loginpass);
			 ////////////new GUI();

		  } catch (UnknownHostException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	}
	 public static void main(String[] args) {
		 new TClient();
	 }
}
