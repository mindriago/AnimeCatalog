package ui;

import model.Anime;
import model.Genre;
import service.AnimeService;
import service.RecommendationStrategy;
import service.TopByGenreStrategy;
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

        // Update button text to reflect new functionality
        JButton refreshBtn = new JButton("Refresh Statistics & Recommendations");
        add(refreshBtn, BorderLayout.NORTH);

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        // Use monospaced font for cleaner alignment
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> calculateStats());
    }

    private void calculateStats() {
        java.util.List<Anime> all = service.getAllAnimes();
        if (all.isEmpty()) {
            statsArea.setText("No data available. Please register some animes first.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        // --- PART 1: GENERAL STATISTICS ---
        sb.append("=== GENERAL STATISTICS ===\n");
        double avgRating = all.stream().mapToInt(Anime::getRating).average().orElse(0.0);
        sb.append(String.format("Global Rating Average: %.2f / 5.00\n\n", avgRating));

        sb.append("Top Genres by Count:\n");
        Map<Genre, Long> genreCounts = all.stream()
                .flatMap(a -> a.getGenres().stream())
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        genreCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .forEach(e -> sb.append(String.format("- %s: %d animes\n", e.getKey(), e.getValue())));

        sb.append("\n--------------------------------------------------\n\n");

        // --- PART 2: RECOMMENDATIONS (Strategy Pattern) ---
        // This fulfills FR4 part (b)
        sb.append("=== RECOMMENDATION SYSTEM (STRATEGY PATTERN) ===\n");
        sb.append("Strategy Applied: 'Top 5 SHONEN Animes'\n\n");

        // 1. Instantiate the Concrete Strategy (Hardcoded to SHONEN for this demo)
        RecommendationStrategy strategy = new TopByGenreStrategy(Genre.SHONEN);

        // 2. Execute via Service (Polymorphism)
        // NOTE: Ensure your AnimeService has the getRecommendations() method!
        java.util.List<Anime> recs = service.getRecommendations(strategy);

        if (recs.isEmpty()) {
            sb.append("(No 'SHONEN' animes found to recommend. Try adding some!)\n");
        } else {
            for (Anime a : recs) {
                sb.append(String.format("* [Rating: %d] %s (%s)\n",
                        a.getRating(), a.getTitle(), a.getStudio()));
            }
        }

        statsArea.setText(sb.toString());
    }
}