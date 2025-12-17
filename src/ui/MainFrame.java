package ui;

import service.AnimeService;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame(AnimeService service) {
        setTitle("Anime Catalog Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Save data when window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                service.saveData();
            }
        });

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Catalog / Search", new AnimeListPanel(service));
        tabs.addTab("Register New", new AnimeFormPanel(service));
        tabs.addTab("Statistics", new StatsPanel(service));
        add(tabs);
    }
}