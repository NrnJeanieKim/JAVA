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
  void pick(int s, String gender){////////////���ฦ �����Ͽ� ������ �ҷ���
    String genderChecker = gender;
    if(gender.equals("male"))path = "mList.txt";
    else path = "wList.txt";

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
  void getInfo(String line){////�ش� ������ ������ ���� ��
    String[] info = line.split("\\*");
    //for(String i : info)System.out.println(i);
    index = info[0];
    String id = info[1];
    String pw = info[2];
    ptPath = info[3];
    String age = info[4];
    String itr = info[5];
    profile = id+"\n"+age+"  "+itr;

  }
}
