package service;

import model.Anime;
import java.util.List;

// SOLID: Strategy Pattern & OCP - Allows interchangeable recommendation algorithms
public interface RecommendationStrategy {
    List<Anime> getRecommendations(List<Anime> allAnimes);
}