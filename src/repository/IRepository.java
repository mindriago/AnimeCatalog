package repository;

// SOLID: Dependency Inversion Principle (DIP) - Depend on abstractions, not concretions
// GRASP: Protected Variations - Isolates the rest of the app from storage details
public interface IRepository<T> {
    void save(T data);
    T load();
}