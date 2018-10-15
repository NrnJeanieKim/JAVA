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
  void pick(int s, String gender){
    String genderChecker = gender;
    if(gender.equals("male"))path = "/Users/Sungsu/Desktop/Tinder/list/mList.txt";
    else path = "/Users/Sungsu/Desktop/Tinder/list/wList.txt";
	  System.out.println("한글테스트");
    int num = s;
    int count= 1;
    try{
      br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"euc-kr"));
      String line = "";

      while(true){

        line = br.readLine();
        // if(line==null){
        //   count= 1;
        //   System.out.println("line == null");
        //   pick(num,genderChecker);
        // }
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
  void getInfo(String line){

    String[] info = line.split("\\*");
    for(String i : info)System.out.println(i);
    String index = info[0];
    String id = info[1];
    String pw = info[2];
    ptPath = info[3];
    String age = info[4];
    String itr = info[5];
    profile = id+age+itr;

      
  }
}
