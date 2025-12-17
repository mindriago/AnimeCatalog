package ui;

import model.Anime;
import model.Genre;
import service.AnimeService;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private AnimeService service;
    private JTextArea statsArea;

    public StatsPanel(AnimeService service) {
        this.service = service;
        setLayout(new BorderLayout());

        JButton refreshBtn = new JButton("Refresh Statistics");
        add(refreshBtn, BorderLayout.NORTH);

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> calculateStats());
    }

    private void calculateStats() {
        java.util.List<Anime> all = service.getAllAnimes();
        if (all.isEmpty()) {
            statsArea.setText("No data available.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double avgRating = all.stream().mapToInt(Anime::getRating).average().orElse(0.0);
        sb.append(String.format("Global Rating Average: %.2f\n\n", avgRating));

        sb.append("Top Genres by Count:\n");
        Map<Genre, Long> genreCounts = all.stream()
                .flatMap(a -> a.getGenres().stream())
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        genreCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .forEach(e -> sb.append(String.format("- %s: %d\n", e.getKey(), e.getValue())));

        statsArea.setText(sb.toString());
    }
}