import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class TClient {
	 
	 public static void main(String[] args) {
		InetAddress inetAddress = null;	
		  try{
			  inetAddress = InetAddress.getByName("224.1.1.0"); // 224.0.0.0 to 239.255.255.255 ���� ����ؾ� ��Ƽ���� ��...
		  }catch(UnknownHostException ue){
			  System.out.println("�ùٸ��� ���� ������");}
		  BufferedReader br = null;
		  UDPMultiReceive udpMultiReceive = null;
		  UDPMultiSend udpMultiSend = null;
		  int portNumber = 7120;
		  String strId = "";
	  try {
		// 1. IP �ּ�, port ��ȣ �Է� ���� Ȯ��
		  /*if(args.length != 2) {
			System.out.println("IP, Port �Է� �ٶ��ϴ�.");
			System.exit(0);
		   }else {
			inetAddress = InetAddress.getByName(args[0]); // ������ ip
			portNumber = Integer.parseInt(args[1]); // port
		  }*/
		   // 2. ID �Է�
		   br = new BufferedReader(new InputStreamReader(System.in));
		   System.out.print("id�� �Է��Ͻÿ� ==> ");
		   strId = br.readLine();
		   System.out.println("*********** " + strId + "�� ���� ***********");
		   // 3. ������ �ޱ�
		   udpMultiReceive = new UDPMultiReceive(inetAddress, portNumber);
		   udpMultiReceive.start();
		   // 4. ������ ������
		   udpMultiSend = new UDPMultiSend(inetAddress, portNumber, strId);
		   udpMultiSend.start();
		  } catch (UnknownHostException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
 }
}