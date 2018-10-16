import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMultiReceive extends Thread {
	private InetAddress inetAddress = null;
	private int portNumber = 0;
	private MulticastSocket multicastSocket = null;
	private DatagramPacket datagramPacket = null;

	public UDPMultiReceive(InetAddress tmpInetAddress, int tmpportNumber) {
		inetAddress = tmpInetAddress;
		portNumber = tmpportNumber;
	}
	public void run() {
		byte[] buffer = new byte[512];// 1. Data�� ���� ���� ����
		try {
			multicastSocket = new MulticastSocket(portNumber);// 2. DatagramPacket�� �ޱ� ���� Socket ����
			multicastSocket.joinGroup(inetAddress);// 3. �׷� ��� - ��� �����ϰ� ��
			while(true) {	// �޽��� ��� ����
	// 4. Data�� ���� Packet ����
				datagramPacket = new DatagramPacket(buffer, buffer.length);
	// 5. ��Ƽĳ��Ʈ�� �����ϴ� �޽��� ����
				multicastSocket.receive(datagramPacket);
	// 6. ���ŵ� �޽��� ���
				String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				System.out.println("���� �޽��� ==> " + msg);
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(0);
			} finally {
				multicastSocket.close();
			}
		}
}