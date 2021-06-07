import java.util.ArrayList;
import java.util.List;

class Meteorologo {
    static Servicio servicio;
    static List<Usuario> subscriptores = new ArrayList<>();

    Servicio getServicio() {
        return servicio;
    }

    static void agregarSuscriptor(Usuario unUsuario) {
        subscriptores.add(unUsuario);
    }

    static void quitarSubscriptor(Usuario unUsuario) {
        subscriptores.remove(unUsuario);
    }

    static void setServicio(Servicio unServicio) {
        servicio = unServicio;
    }

    static Clima obtenerClimaActual() {
        return new Clima(servicio.obtenerTemperatura(), servicio.obtenerProbabilidadDeLluvia(), servicio.obtenerCondiciones(), servicio.obtenerFecha());
    }

    static List<String> obtenerAlertas() {
        return servicio.obtenerAlertas();
    }

    static void notificarAlertas() {
        List<String> alertas = servicio.obtenerAlertas();
        subscriptores.forEach(unUsuario -> unUsuario.notificarAlertas(alertas));
        QueMePongo.actualizarSugerenciaDiaria();

        if (alertas.contains("STORM")) {
            subscriptores.forEach(unUsuario -> unUsuario.recibirSugerenciaParaguas());
        }

        if (alertas.contains("SNOW")) {
            subscriptores.forEach(unUsuario -> unUsuario.recibirSugerenciaNoUsarAuto());
        }
    }
}