package finneg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AppTest {
    private PersonaHLP personaHLP;

    @Before
    public void preparedTests() {
            this.personaHLP = new PersonaHLP();
            this.personaHLP.init();
    }

    @Test
    public void filtrarPersonasGeneroMasculinoMayorQue25() {
        List<Persona> resultado = new ArrayList<>();
        this.personaHLP.filtrarPersonasGeneroMasculinoMayorQue25(resultado);
        Assert.assertEquals(2, resultado.size());
    }

    @Test
    public void filtrarPersonasConInterfaz() {
        ArrayList<Persona> resultado = new ArrayList<>();
        Filtro filtro = new FiltroPersonaGeneroMasculinoMayorQue25();
        this.personaHLP.filtrarPersonasConInterfaz(resultado, filtro);
        Assert.assertEquals(2, resultado.size());
    }

    @Test
    public void filtrarPersonasConClaseAnonima() {
        ArrayList<Persona> resultado = new ArrayList<>();
        Filtro test = new Filtro() {
            @Override
            public boolean test(Persona p) {
                return p.getGenero().equals(Genero.MASCULINO) && p.getEdad() > 25;
            }
        };
        this.personaHLP.filtrarPersonasConInterfaz(resultado, test);
        Assert.assertEquals(2, resultado.size());
    }

    @Test
    public void filtrarPersonaConExpresionLambda() {
        ArrayList<Persona> resultado = new ArrayList<>();
        Filtro filtro = (Persona persona) -> persona.getGenero().equals(Genero.MASCULINO) && persona.getEdad() > 25;
        this.personaHLP.filtrarPersonasConInterfaz(resultado, filtro);
        Assert.assertEquals(2, resultado.size());
    }

    @Test
    public void filtrarPersonaConStream() {
        Predicate<Persona> filtro = (Persona persona) -> persona.getGenero().equals(Genero.MASCULINO) && persona.getEdad() > 25;
        Predicate<Persona> filtro2 = persona -> persona.getGenero().equals(Genero.MASCULINO) && persona.getEdad() > 25;
        Predicate<Persona> filtro3 = persona -> {
            Random r = new Random();
            int n1 = r.nextInt();
            for (int i = 0; ; i++)
                if (n1 == i)
                    break;
            return persona.getGenero().equals(Genero.MASCULINO) && persona.getEdad() > 25;
        };
        List<Persona> resultado;
        resultado = this.personaHLP.filtrarPersonasConStream(filtro);
        Assert.assertEquals(2, resultado.size());
        resultado = this.personaHLP.filtrarPersonasConStream(filtro2);
        Assert.assertEquals(2, resultado.size());
        resultado = this.personaHLP.filtrarPersonasConStream(filtro3);
        Assert.assertEquals(2, resultado.size());
    }

    @Test
    public void sumarEdadesGeneroFeminino() {
        int resultado = this.personaHLP.sumarEdadesPersonasGeneroFemenino();
        Assert.assertEquals(69, resultado);
    }

    @Test
    public void sumarEdadesGeneroFemininoConStream() {
        int resultado = this.personaHLP.sumarEdadesPersonasGeneroFemeninoConStream();
        Assert.assertEquals(69, resultado);
    }

    @Test
    public void agruparPersonasPorGenero() {
        Map<Genero, List<Persona>> map;
        map = this.personaHLP.agruparPersonasPorGenero();
        Assert.assertEquals(3, map.get(Genero.MASCULINO).size());
        Assert.assertEquals(3, map.get(Genero.FEMENINO).size());
        Assert.assertEquals(1, map.get(Genero.OTRO).size());
    }

    @Test
    public void sumarEdadesPorGenero() {
        Map<Genero, Integer> map;
        map = this.personaHLP.sumarEdadesPorGenero();
        Assert.assertEquals(79, map.get(Genero.MASCULINO).intValue());
        Assert.assertEquals(69, map.get(Genero.FEMENINO).intValue());
        Assert.assertEquals(18, map.get(Genero.OTRO).intValue());
    }

    @Test
    public void imprimirPersonasPorPantalla() {
        Predicate<Persona> filtro = persona -> persona.getEdad() > 18;
        Function<Persona, String> mapper = persona -> persona.getApellido().concat(" ").concat(persona.getNombre());
        Consumer<String> consumidor = System.out::println; //Equivalente a (s) -> System.out.println(s);
        this.personaHLP.imprimirPersonasPorPantallaConFunciones(filtro, mapper, consumidor);
    }

    @Test
    public void obtenerMayorEdad() {
        Optional<Integer> op = this.personaHLP.obtenerMayorEdad();
        op.ifPresent(integer -> Assert.assertEquals(33, integer.intValue()));
    }

    @Test
    public void sumarTodasLasEdades() {
        Optional<Integer> res = this.personaHLP.sumarTodasLasEdades();
        res.ifPresent(value -> {
            Assert.assertEquals(166, value.intValue());
        });

        int res2 = this.personaHLP.sumarTodasLasEdades2();
        Assert.assertEquals(166, res2);
    }

    @Test
    public void obtenerPersonaConElNombreMasCorto() {
        Optional<Persona> p = personaHLP.obtenerPersonaConNombreMasCorto();
        p.ifPresent(persona -> {
            Assert.assertEquals("Lucia", persona.getNombre());
        });
    }

    public Supplier<Integer> variableEfectivamenteFinal(Integer a) {
        final Integer b = a;
        Supplier<Integer> ret = () -> b;
        a++;
        return ret;
    }
}
