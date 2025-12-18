package ui;

import model.*;
import service.AnimeService;
import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class AnimeListPanel extends JPanel {
    private AnimeService service;
    private AnimeTableModel tableModel;
    private JTextField searchField;
    private JComboBox<Genre> genreFilterBox;
    private JComboBox<String> sortBox;

    public AnimeListPanel(AnimeService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Title:"));
        searchField = new JTextField(10);
        topPanel.add(searchField);

        topPanel.add(new JLabel("Genre:"));
        genreFilterBox = new JComboBox<>(Genre.values());
        genreFilterBox.insertItemAt(null, 0);
        genreFilterBox.setSelectedIndex(0);
        topPanel.add(genreFilterBox);

        JButton filterBtn = new JButton("Apply Filter");
        JButton refreshBtn = new JButton("Reset");
        topPanel.add(filterBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new AnimeTableModel(service.getAllAnimes());
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(new JLabel("Sort By:"));
        sortBox = new JComboBox<>(new String[]{"Title (A-Z)", "Rating (High-Low)", "Year (New-Old)"});
        bottomPanel.add(sortBox);
        JButton sortBtn = new JButton("Sort");
        bottomPanel.add(sortBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // SOLID: OCP - Filter logic defined here, Service just executes it
        filterBtn.addActionListener(e -> {
            String txt = searchField.getText().toLowerCase();
            Genre genre = (Genre) genreFilterBox.getSelectedItem();
            Predicate<Anime> rule = a -> {
                boolean matchTitle = a.getTitle().toLowerCase().contains(txt);
                boolean matchGenre = (genre == null) || a.getGenres().contains(genre);
                return matchTitle && matchGenre;
            };
            tableModel.setAnimeList(service.search(rule));
        });

        refreshBtn.addActionListener(e -> tableModel.setAnimeList(service.getAllAnimes()));

        // GRASP: Low Coupling - Sorting strategy passed to service
        sortBtn.addActionListener(e -> {
            String crit = (String) sortBox.getSelectedItem();
            Comparator<Anime> comp = null;
            if (crit.contains("Title")) comp = Comparator.comparing(Anime::getTitle);
            else if (crit.contains("Rating")) comp = (a1, a2) -> Integer.compare(a2.getRating(), a1.getRating());
            else if (crit.contains("Year")) comp = (a1, a2) -> Integer.compare(a2.getReleaseYear(), a1.getReleaseYear());

            if (comp != null) tableModel.setAnimeList(service.sort(comp));
        });
    }
}