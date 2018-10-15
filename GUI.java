class AskChat extends JFrame{ //팝업창 띄우기 위한 클래스. Choose외부.
	 Choose cs; //띄우고 나서 cs.ask = false로 만들어줘야 함.
	 JPanel upPanel, middlePanel, downPanel; //매치, 하트, 대화/계속고르기 부착되는 JPanel
	 JLabel match, heart; //"It's Match!", 하트그림
	 JButton sendButton, keepButton; //"send a message", "Keep Playing"
	 Container cp;
	 AskChat(Choose cs){
		 this.cs = cs;
		 init();			  
	  }
	 void init(){
		cp = getContentPane();
		upPanel = new JPanel(); middlePanel = new JPanel(); downPanel = new JPanel();
		cp.add(upPanel, BorderLayout.NORTH);
		cp.add(middlePanel, BorderLayout.CENTER);
		cp.add(downPanel, BorderLayout.SOUTH);
		match = new JLabel("It's a Match!");
		heart = new JLabel("heart"); //하트아이콘 넣기★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		sendButton = new JButton("Send a Message");
		keepButton = new JButton("Keep Playing");
		//패널 내부는 FLOWLAYOUT으로바꿔서해야할듯??
		upPanel.add(match);
		middlePanel.add(heart);
		downPanel.add(sendButton); downPanel.add(keepButton);
		setUI();
	  }
	 void setUI(){
		//setTitle("대화하시겠습니까?");타이틀꼭필요한가???
		setSize(350, 500);
		setLocation(500,300);
		setVisible(true);
		setResizable(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //이 세 버튼은 없앨수없나
	}
	  }
