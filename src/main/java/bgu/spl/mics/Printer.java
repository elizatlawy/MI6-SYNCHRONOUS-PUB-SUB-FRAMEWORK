package bgu.spl.mics;

import java.io.*;

public class Printer {
    public static <T extends Serializable> void Print(T toWrite, String path) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(fileOutputStream);
            obj.writeObject(toWrite);
            obj.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
//        try {
//            FileOutputStream file = new FileOutputStream(filename);
//            ObjectOutputStream object = new ObjectOutputStream(file);
//            object.writeObject(Obj);
//            object.close();
//            file.close();
//        } catch (IOException e) {
//        }
    }
}
