import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

class AskYes extends JFrame{ //������ ��ȭ�� �������� ��, ��ȭ�� �ųİ� ����� Ŭ����
	int yesNo;
	AskYes(){
		yesNo = JOptionPane.showConfirmDialog(this, "���ƿ��� ������ ��ȭ ��û�� �ֽ��ϴ�. Ȯ���� �����ø� ��ȭâ���� �̵��մϴ�.", "����", JOptionPane.OK_CANCEL_OPTION);
	}
}