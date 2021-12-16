package sample.petushok.file.append;

import java.io.FileWriter;
import java.io.IOException;

public class FileProject {
    public static FileProject fileProject;
    private  FileWriter writer ;
    private FileProject() {}
    public void addDataInFile(String text,int count) throws IOException {
            for(int i=0;i<count;i++){
                writer.write(text);
                writer.append('\n');
                writer.flush();

            }
    }
    public void createFile(){
        try {
            writer = new FileWriter("archive.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
