2018년 10월 프로젝트\
곽수지, 김나린, 이빛나, 최성수\
Tinder GUI\
★
ChatServer.java서버\
Chat.java대화하는 자바파일, report4.png, back3.png 아이콘파일\
GUI.java고르는(Load.java프로필 차례로 업로드하는)\
Login.java로그인하는 (타이틀:frameIcon.png) (GUI: mainIcon.png, idIcon.png, pwIcon.png, loginIcon.png )\
m1_like.txt 이런식으로 사람들이 할 때마다 파일 만들어져야함.\
★★★로그인창 말고 다른 고르기+It's a Match+대화창 크기는 모두 400*540, 위치는 500,100★★★\
\


수지언니가 만든거 : Chat.java +ChatServer.java\
이중에서 Chat.java에 GUI 들어있음.\

나린 만든 : TClient.java (+UDPMultiReceive.java, UDPMultiSend.java)\
\
=>TClient.java에다가 수지 만든 Chat.java의 GUI를 넣고.\
이벤트리스너로 본인이 말한거 창에다가 띄우는거+\
이벤트리스너로 데이터패킷으로 보내는거.\
스레드 Listen() 데이터패킷으로 받아서 띄우는거\
