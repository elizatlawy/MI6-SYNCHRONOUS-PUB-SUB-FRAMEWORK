package bgu.spl.mics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Printer {
    public static <T extends Serializable> void Print(T Obj, String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream object = new ObjectOutputStream(file);
            object.writeObject(Obj);
            object.close();
            file.close();
        } catch (IOException e) {
        }
    }
}
