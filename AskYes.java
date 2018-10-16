import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

class AskYes extends JFrame{ //상대방이 대화를 시작했을 때, 대화할 거냐고 물어보는 클래스
	int yesNo;
	AskYes(){
		yesNo = JOptionPane.showConfirmDialog(this, "좋아요한 상대방의 대화 요청이 있습니다. 확인을 누르시면 대화창으로 이동합니다.", "선택", JOptionPane.OK_CANCEL_OPTION);
	}
}