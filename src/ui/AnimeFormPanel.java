package ui;

import exception.AnimeAlreadyExistsException;
import model.*;
import service.AnimeService;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AnimeFormPanel extends JPanel {
    private AnimeService service;
    private JTextField titleField, yearField, studioField, episodesField, extraField;
    private JComboBox<Status> statusBox;
    private JComboBox<String> typeBox;
    private JSlider ratingSlider;
    private JList<Genre> genreList;

    public AnimeFormPanel(AnimeService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Type:"));
        typeBox = new JComboBox<>(new String[]{"Movie", "Show"});
        formPanel.add(typeBox);

        formPanel.add(new JLabel("Release Year:"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("Studio:"));
        studioField = new JTextField();
        formPanel.add(studioField);

        formPanel.add(new JLabel("Episodes:"));
        episodesField = new JTextField();
        formPanel.add(episodesField);

        formPanel.add(new JLabel("Status:"));
        statusBox = new JComboBox<>(Status.values());
        formPanel.add(statusBox);

        formPanel.add(new JLabel("Rating (1-5):"));
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setMajorTickSpacing(1);
        formPanel.add(ratingSlider);

        JLabel extraLabel = new JLabel("Duration (mins):");
        formPanel.add(extraLabel);
        extraField = new JTextField();
        formPanel.add(extraField);

        typeBox.addActionListener(e -> {
            if ("Movie".equals(typeBox.getSelectedItem())) {
                extraLabel.setText("Duration (mins):");
            } else {
                extraLabel.setText("Currently Airing? (true/false):");
            }
        });

        formPanel.add(new JLabel("Genres (Hold Ctrl for multiple):"));
        genreList = new JList<>(Genre.values());
        formPanel.add(new JScrollPane(genreList));

        add(formPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Register Anime");
        add(saveBtn, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> registerAnime());
    }

    private void registerAnime() {
        try {
            String title = titleField.getText();
            String studio = studioField.getText();
            String type = (String) typeBox.getSelectedItem();
            Status status = (Status) statusBox.getSelectedItem();
            int rating = ratingSlider.getValue();
            int year = Integer.parseInt(yearField.getText());
            int episodes = Integer.parseInt(episodesField.getText());

            Set<Genre> selectedGenres = new HashSet<>(genreList.getSelectedValuesList());
            if (selectedGenres.isEmpty()) throw new IllegalArgumentException("Select at least one genre.");

            int duration = 0;
            boolean isAiring = false;

            if ("Movie".equals(type)) {
                duration = Integer.parseInt(extraField.getText());
            } else {
                isAiring = Boolean.parseBoolean(extraField.getText());
            }

            service.registerAnime(title, year, studio, episodes, status, rating, selectedGenres, type, duration, isAiring);
            JOptionPane.showMessageDialog(this, "Anime registered successfully!");
            titleField.setText(""); // Clear minimal fields for UX

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year, Episodes, and Duration must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (AnimeAlreadyExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplicate", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}