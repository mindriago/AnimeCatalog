package service;

import model.Anime;
import model.Genre;
import java.util.List;
import java.util.stream.Collectors;

public class TopByGenreStrategy implements RecommendationStrategy {
    private Genre targetGenre;

    public TopByGenreStrategy(Genre targetGenre) {
        this.targetGenre = targetGenre;
    }

    @Override
    public List<Anime> getRecommendations(List<Anime> allAnimes) {
        return allAnimes.stream()
                // 1. Filter: Keep only animes of the specific genre
                .filter(a -> a.getGenres().contains(targetGenre))
                // 2. Sort: Compare ratings (Desc: High to Low)
                .sorted((a1, a2) -> Integer.compare(a2.getRating(), a1.getRating()))
                // 3. Limit: Take top 5
                .limit(5)
                .collect(Collectors.toList());
    }
}