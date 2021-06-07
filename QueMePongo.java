import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class QueMePongo { 
    static ArrayList<Prenda> prendasCreadas = new ArrayList<>();
    static ArrayList<Uniforme> uniformesSugeridos = new ArrayList<>();
    static List<Usuario> usuariosDelSistema = new ArrayList<>();
    
    static List<Prenda> getPrendasSuperiores() {
        return prendasCreadas.stream().filter(unaPrenda -> unaPrenda.getClass() == PrendaSuperior.class).collect(Collectors.toList());
    }

    static List<Prenda> getPrendasInferiores() {
        return prendasCreadas.stream().filter(unaPrenda -> unaPrenda.getClass() == PrendaInferior.class).collect(Collectors.toList());
    }

    static List<Prenda> getCalzados() {
        return prendasCreadas.stream().filter(unaPrenda -> unaPrenda.getClass() == Calzado.class).collect(Collectors.toList());
    }

    static List<Prenda> getAccesorios() {
        return prendasCreadas.stream().filter(unaPrenda -> unaPrenda.getClass() == Accesorio.class).collect(Collectors.toList());
    }

    static List<Prenda> getPrendasCreadas() {
        return prendasCreadas;
    }

    static List<Uniforme> getUniformesSugeridos() {
        return uniformesSugeridos;
    }

    static void agregarUniforme(Uniforme unUniforme) {
        uniformesSugeridos.add(unUniforme);
    }

    static void agregarPrenda(Prenda unaPrenda) {
        prendasCreadas.add(unaPrenda);
    }

    static List<Prenda> filtrarPorTemperatura(List<Prenda> unaListaDePrendas, Temperatura unaTemperatura) {
        return unaListaDePrendas.stream().filter(unaPrenda -> unaPrenda.getGradosMaximos() < unaTemperatura.getGrados()).collect(Collectors.toList());
    }

    static int numeroRandomSegunLista(int tamanioLista) {
        return new Random().nextInt(tamanioLista);
    }

    static void actualizarSugerenciaDiaria() {
        usuariosDelSistema.stream().filter(unUsuario -> unUsuario.recalculoActivado()).forEach(unUsuario -> unUsuario.recibirSugerenciaDiaria());
    }

    static Atuendo sugerenciaDelDia() {
        List<String> alertas = Meteorologo.obtenerAlertas();
        return recibirSugerenciaSegunAlertas(alertas);
    }

    static Atuendo recibirSugerenciaSegunAlertas(List<String> alertas) {
        // TODO: No se especifica como influye alerta en Prenda
        return null;
    }

    static Atuendo recibirSugerenciaAtuendoPorTemperatura(Temperatura unaTemperatura) {
        List<Accesorio> accesorio = new ArrayList<>();
        accesorio.add(filtrarPorTemperatura(getAccesorios(), unaTemperatura).get(numeroRandomSegunLista(getAccesorios().size())));
        return new Atuendo( (PrendaSuperior) filtrarPorTemperatura(getPrendasSuperiores(), unaTemperatura).get(numeroRandomSegunLista(getPrendasSuperiores().size())),
                            (PrendaInferior) filtrarPorTemperatura(getPrendasInferiores(), unaTemperatura).get(numeroRandomSegunLista(getPrendasInferiores().size())),
                            (Calzado) filtrarPorTemperatura(getCalzados(), unaTemperatura).get(numeroRandomSegunLista(getCalzados().size())), 
                            accesorio);
    }

    static void actualizarAlertas() {
        Meteorologo.notificarAlertas();
    }
}