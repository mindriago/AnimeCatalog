package ui;

import repository.SerializationRepository;
import service.AnimeService;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create Repository (Persistence Layer)
            SerializationRepository repo = new SerializationRepository("anime_catalog.ser");

            // 2. Create Service (Logic Layer) - Injecting Repo
            AnimeService service = new AnimeService(repo);

            // 3. Create UI - Injecting Service
            MainFrame frame = new MainFrame(service);
            frame.setVisible(true);
        });
    }
}