package service;

import exception.AnimeAlreadyExistsException;
import model.*;
import repository.IRepository;
import java.util.*;
import java.util.function.Predicate;

// GRASP: Controller - Facade for UI operations
// GRASP: Creator - Handles creation of Anime instances
public class AnimeService {
    private Map<String, Anime> animeRepository;
    private IRepository<Map<String, Anime>> persistenceMechanism;

    // SOLID: Dependency Inversion Principle (DIP) - Constructor Injection
    public AnimeService(IRepository<Map<String, Anime>> repository) {
        this.persistenceMechanism = repository;
        try {
            this.animeRepository = persistenceMechanism.load();
        } catch (Exception e) {
            this.animeRepository = new HashMap<>();
        }
    }

    // GRASP: Information Expert - Service holds the list, so it checks for duplicates
    public void registerAnime(String title, int year, String studio, int episodes, Status status,
                              int rating, Set<Genre> genres, String type, int duration, boolean isAiring) {

        if (animeRepository.containsKey(title)) {
            throw new AnimeAlreadyExistsException("Anime '" + title + "' already exists.");
        }

        // Factory Logic
        Anime anime;
        if ("Movie".equalsIgnoreCase(type)) {
            anime = new AnimeMovie(title, year, studio, episodes, status, rating, genres, duration);
        } else {
            anime = new AnimeShow(title, year, studio, episodes, status, rating, genres, isAiring);
        }
        animeRepository.put(title, anime);
    }

    // SOLID: Open-Closed Principle (OCP) - Logic is open to extension via Predicate
    public List<Anime> search(Predicate<Anime> criteria) {
        List<Anime> results = new ArrayList<>();
        for (Anime a : animeRepository.values()) {
            if (criteria.test(a)) results.add(a);
        }
        return results;
    }

    // GRASP: Low Coupling - Sorting logic passed as Comparator
    public List<Anime> sort(Comparator<Anime> sorter) {
        List<Anime> list = new ArrayList<>(animeRepository.values());
        list.sort(sorter);
        return list;
    }

    public List<Anime> getAllAnimes() {
        return new ArrayList<>(animeRepository.values());
    }

    public void saveData() {
        persistenceMechanism.save(animeRepository);
    }
}