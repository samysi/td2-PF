package Exo1;

@FunctionalInterface
public interface ToString<T> {
    T convert(T source);
}
