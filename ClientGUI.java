import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ClientGUI extends JFrame implements ActionListener {

private static final long serialVersionUID = 1L;
private JLabel label;
private JTextField tf;
private JPanel chatPanel;
private JScrollPane scroll;

ClientGUI() {
    super("Chat Client");
    label = new JLabel("You can write messages below:", SwingConstants.CENTER);
    chatPanel = new JPanel();

    tf = new JTextField("");
    tf.setBackground(Color.WHITE);
    tf.requestFocus();
    tf.setVisible(true);
    tf.addActionListener(this);

    chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));
    chatPanel.add(Box.createVerticalGlue());
    JPanel centerPanel = new JPanel(new GridLayout(1,1));

    scroll = new JScrollPane();
    scroll.setViewportView(chatPanel);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    centerPanel.add(scroll);

    add(centerPanel, BorderLayout.CENTER);

    JPanel southPanel = new JPanel(new BorderLayout());
    JPanel writeChatPanel = new JPanel(new GridLayout(2,1));
    writeChatPanel.add(label);
    writeChatPanel.add(tf);
    southPanel.add(writeChatPanel, BorderLayout.NORTH);

    add(southPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 600);
    setVisible(true);

    chatPanel.scrollRectToVisible(new Rectangle(chatPanel.getSize()));

}

void append(String str) {
    LeftArrowBubble leftArrowBubble = new LeftArrowBubble();

	final int size = 500;
	leftArrowBubble.setMaximumSize(new Dimension(size, size));

    JLabel tac = new JLabel();

	tac.setMaximumSize(new Dimension(size - 50, size - 50));

	final int maximumSize = 56;
	String textWithSeparators = "";
	final StringTokenizer textTokenizer = new StringTokenizer(str, " \t\n\r", true);

	while(textTokenizer.hasMoreTokens()) {
		final String part = textTokenizer.nextToken();
		for (int beginIndex = 0; beginIndex < part.length();
			 beginIndex += maximumSize)
			textWithSeparators += (beginIndex == 0 ? "" : " ")
				+ part.substring(beginIndex,
								 Math.min(part.length(),
										  beginIndex + maximumSize));
	}
	System.out.println(textWithSeparators);

	tac.setText("<html><body style='width:" + (size - 150) + "px;padding:15px;display:block;'>"
					+ textWithSeparators + "</body></html>");

    tac.setOpaque(false);
    leftArrowBubble.add(tac, BorderLayout.NORTH);

    chatPanel.add(leftArrowBubble);     

    chatPanel.add(Box.createRigidArea(new Dimension(0,5)));
    Rectangle rect = chatPanel.getBounds();
    Rectangle r2 = scroll.getViewport().getVisibleRect();
    chatPanel.scrollRectToVisible(new Rectangle((int) rect.getWidth(), 
            (int) rect.getHeight(), (int) r2.getWidth(), (int) r2.getHeight()));
    revalidate();
    repaint();
}
public void actionPerformed(ActionEvent e) {    
        // just have to send the message
        append(tf.getText());               
        tf.setText("");
        return;
}
public static void main(String[] args) {
    new ClientGUI();
}
}