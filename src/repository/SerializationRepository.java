package repository;

import exception.AnimePersistenceException;
import model.Anime;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// GRASP: Pure Fabrication - Artificial class created to handle File I/O
public class SerializationRepository implements IRepository<Map<String, Anime>> {
    private String filePath;

    public SerializationRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<String, Anime> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new AnimePersistenceException("Failed to save data", e);
        }
    }

    @Override
    public Map<String, Anime> load() {
        File file = new File(filePath);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<String, Anime>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AnimePersistenceException("Failed to load data", e);
        }
    }
}