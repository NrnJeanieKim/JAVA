import java.io.*;
import javax.swing.*;

class Load{
  FileReader fr;
  String FName;
  BufferedReader br;
  JFrame jf;
  String ptPath;
  String path;
  String profile;

  Load(){}
  void pick(int s, String gender){////////////남녀를 구분하여 파일을 불러옴
    String genderChecker = gender;
    if(gender.equals("male"))path = "mList.txt";
    else path = "wList.txt";
	  System.out.println("한글테스트");
    int num = s;
    int count= 1;
    try{
      br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"euc-kr"));
      String line = "";
      while(true){
        line = br.readLine();
        if(count==num){
          getInfo(line);
          count++;
          break;
        }
        count++;
      }
    }catch(FileNotFoundException fe){
    }catch(IOException ie){
    }
  }
  String index;
  void getInfo(String line){////해당 유저의 정보를 나눠 저
    String[] info = line.split("\\*");
    for(String i : info)System.out.println(i);
    index = info[0];
    String id = info[1];
    String pw = info[2];
    ptPath = info[3];
    String age = info[4];
    String itr = info[5];
    profile = id+age+itr;

  }
}
