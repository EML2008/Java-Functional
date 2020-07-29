package finneg;

public class FiltroPersonaGeneroMasculinoMayorQue25 implements Filtro {
    @Override
    public boolean test(Persona p) {
        return p.getGenero().equals(Genero.MASCULINO) && p.getEdad() > 25;
    }
}
