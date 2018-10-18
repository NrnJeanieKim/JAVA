import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMultiSend extends Thread {
	private InetAddress inetAddress = null;
	private int portNumber = 0;
	private String strId = "";
	private DatagramSocket datagramSocket = null;
	private DatagramPacket datagramPacket = null;
	private BufferedReader br = null;

	public UDPMultiSend(InetAddress tmpInetAddress, int tmpportNumber, String tmpstrId) {
		inetAddress = tmpInetAddress;
		portNumber = tmpportNumber;
		strId = tmpstrId;
	}
	public void run() {
		///////////////////////////////
		// 1. Data를 보낼 버퍼 생성
		///////////////////////////////
		byte[] buffer = new byte[512];
		///////////////////////////////
		try {	// 2. Socket 열기
			datagramSocket = new DatagramSocket();
			br = new BufferedReader(new InputStreamReader(System.in)); // 3. Server로 메시지 전송을 위한 입력 스트림 생성
			while(true) {// 메시지 계속 전송
				System.out.println("메세지를 입력하시오 : ");// 4. 메시지 입력
				String msg = "[" + strId + "]" + br.readLine();
				buffer = msg.getBytes(); // 스트림을 바이트 어레이
				datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, portNumber);// 6. DatagramPacket에 각 정보를 담고 전송
				datagramSocket.send(datagramPacket);
			}
		} catch(IOException ie) {	//System.err.println(ie);
			System.out.println("send 오류");
		} finally {
			datagramSocket.close();
		}
	}
}
