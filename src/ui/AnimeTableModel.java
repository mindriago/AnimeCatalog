package ui;

import model.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class AnimeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Type", "Year", "Studio", "Status", "Rating", "Genres", "Details"};
    private List<Anime> animeList;

    public AnimeTableModel(List<Anime> animeList) {
        this.animeList = animeList;
    }

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return animeList.size(); }
    @Override
    public int getColumnCount() { return columnNames.length; }
    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Anime anime = animeList.get(rowIndex);
        switch (columnIndex) {
            case 0: return anime.getTitle();
            case 1: return (anime instanceof AnimeMovie) ? "Movie" : "Show"; // Polymorphism check
            case 2: return anime.getReleaseYear();
            case 3: return anime.getStudio();
            case 4: return anime.getStatus();
            case 5: return anime.getRating();
            case 6: return anime.getGenres().stream().map(Enum::name).collect(Collectors.joining(", "));
            case 7:
                // Polymorphic display logic
                if (anime instanceof AnimeMovie) return ((AnimeMovie) anime).getDuration() + " mins";
                if (anime instanceof AnimeShow) return ((AnimeShow) anime).isAiring() ? "Airing" : "Finished";
                return "-";
            default: return null;
        }
    }
}