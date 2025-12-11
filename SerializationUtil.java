// SerializationUtil.java
// Walid Karmid
// OCCC 2025
import java.io.*;
import java.util.ArrayList;

public class SerializationUtil {

    public static void saveToFile(File f, ArrayList<Person> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Person> loadFromFile(File f) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (ArrayList<Person>) obj;
        }
    }
}
