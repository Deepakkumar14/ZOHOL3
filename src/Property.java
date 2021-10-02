import java.io.*;
import java.util.Properties;

public class Property {
    public static void main(String[] args) throws IOException {
        String path=System.getProperty("user.dir");
        File fr=new File(path+File.separator+"src"+File.separator+"file.properties");
        Properties p=new Properties();
        p.setProperty("Name","Deepak");
        p.setProperty("Name1","Dhatchu");
        p.setProperty("sasd","dwewe");
        p.setProperty("Deepak","2345") ;
        try {
            fr.createNewFile();
            FileWriter file=new FileWriter(fr);
            p.store(file,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileReader file2=new FileReader(fr);

       // FileInputStream fInputStream1 = new FileInputStream(fr);
        p.load(file2);
        System.out.println(p.getProperty("value","Not present"));

    }
}
