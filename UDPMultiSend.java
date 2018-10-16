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
		// 1. Data�� ���� ���� ����
		///////////////////////////////
		byte[] buffer = new byte[512];
		///////////////////////////////
		try {	// 2. Socket ����
			datagramSocket = new DatagramSocket(); 
			br = new BufferedReader(new InputStreamReader(System.in)); // 3. Server�� �޽��� ������ ���� �Է� ��Ʈ�� ����
			while(true) {// �޽��� ��� ����
				System.out.println("�޼����� �Է��Ͻÿ� : ");// 4. �޽��� �Է�
				String msg = "[" + strId + "]" + br.readLine();
				buffer = msg.getBytes(); // ��Ʈ���� ����Ʈ ���
				datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, portNumber);// 6. DatagramPacket�� �� ������ ��� ����
				datagramSocket.send(datagramPacket);
			}
		} catch(IOException ie) {	//System.err.println(ie);
			System.out.println("send ����");
		} finally {
			datagramSocket.close();
		}
	}
}

