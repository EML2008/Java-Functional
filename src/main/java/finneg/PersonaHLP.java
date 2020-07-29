package finneg;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonaHLP {
    private List<Persona> personas = new ArrayList<>();

    public void init() {
        personas.add(new Persona("Emiliano", "Lancuba", Genero.MASCULINO, 33));
        personas.add(new Persona("Gabriel", "Salvo", Genero.MASCULINO, 26));
        personas.add(new Persona("Aldana", "Rojas", Genero.FEMENINO, 22));
        personas.add(new Persona("Renata", "Rojas", Genero.FEMENINO, 23));
        personas.add(new Persona("Lucia", "Poupour", Genero.FEMENINO, 24));
        personas.add(new Persona("Hernan", "Fernandez", Genero.MASCULINO, 20));
        personas.add(new Persona("Martin", "Castro", Genero.OTRO, 18));
    }

    public void filtrarPersonasGeneroMasculinoMayorQue25(List<Persona> resultado) {
        for (Persona p : this.personas) {
            if (p.getGenero().equals(Genero.MASCULINO) && p.getEdad() > 25)
                resultado.add(p);
        }
    }

    public void filtrarPersonasConInterfaz(List<Persona> resultado, Filtro filtro) {
        for (Persona p : personas) {
            if (filtro.test(p))
                resultado.add(p);
        }
    }

    public List<Persona> filtrarPersonasConStream(Predicate<Persona> p) {
        return personas.stream()
                .filter(p)
                .collect(Collectors.toList());
    }

    public int sumarEdadesPersonasGeneroFemenino() {
        int sum = 0;
        for (Persona p : this.personas) {
            if (p.getGenero().equals(Genero.FEMENINO)) {
                sum += p.getEdad();
            }
        }
        return sum;
    }

    public int sumarEdadesPersonasGeneroFemeninoConStream() {
        return this.personas.stream()
                .filter(persona -> persona.getGenero().equals(Genero.FEMENINO))
                //.mapToInt(Persona::getEdad)
                .mapToInt(persona -> persona.getEdad())
                .sum();
        //Si se recibe por parametro un Predicate<Persona>, el metodo pasaria a ser completamente generico?
    }

    public Map<Genero, Integer> sumarEdadesPorGenero() {
        return this.personas.stream()
                .collect(Collectors.groupingBy(
                        Persona::getGenero,
                        Collectors.summingInt(Persona::getEdad)));
    }

    public Map<Genero, List<Persona>> agruparPersonasPorGenero() {
        return personas.stream().collect(Collectors.groupingBy(Persona::getGenero));
    }

    public void imprimirPersonasPorPantallaConFunciones(Predicate<Persona> filtro, Function<Persona, String> mapper, Consumer<String> consumidor) {
        this.personas.stream()
                .filter(filtro)
                .map(mapper)
                .forEach(consumidor);
    }

    public Optional<Integer> obtenerMayorEdad() {
        BinaryOperator<Integer> b = (i1, i2) -> Integer.max(i1, i2);
        BinaryOperator<Integer> b2 = (i1, i2) -> Integer.max(i1, i2);
        return this.personas.stream().map(p -> p.getEdad()).reduce(b);
    }

    public Optional<Persona> obtenerPersonaConNombreMasCorto() {
        Comparator<Persona> c = Comparator.comparing(persona -> persona.getNombre().length());
        return this.personas.stream().collect(Collectors.minBy(c));
    }

    public Optional<Integer> sumarTodasLasEdades() {
        BinaryOperator<Integer> b = (i1, i2) -> Integer.sum(i1, i2);
        BinaryOperator<Integer> b2 = (i1, i2) -> Integer.max(i1, i2);
        return this.personas.stream()
                .map(p -> p.getEdad())
                .reduce(b);
    }

    public Integer sumarTodasLasEdades2() {
        return this.personas.stream()
                .collect(Collectors.reducing(
                        0,
                        Persona::getEdad,
                        Integer::sum
                ));
    }
}
