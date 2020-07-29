package finneg;

@FunctionalInterface
public interface Filtro {
    boolean test(Persona p);
}
