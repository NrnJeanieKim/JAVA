import java.io.*;
import javax.swing.*;

class Load{
  FileReader fr;
  String FName;
  BufferedReader br;
  JFrame jf;
  String ptPath;
  String path = "/Users/Sungsu/Desktop/Tinder/list/wList.txt";
  String profile;
  Load(){}
  void pick(int s){
    int num = s;
    try{
      fr = new FileReader(path);
      br = new BufferedReader(fr);
      String line = "";
      int count= 1;
      while(line !=null){
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
