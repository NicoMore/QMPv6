import java.security.Guard;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Usuario {
    List<Guardarropa> guardarropas = new ArrayList<>();
    List<Guardarropa> guardarropasDeAmigos = new ArrayList<>();
    List<Prenda> misPrendas = new ArrayList<>();
    List<Sugerencia> sugerenciasRecibidas = new ArrayList<>();
    List<Sugerencia> sugerenciasAceptadas = new ArrayList<>();

    boolean notificacionesActivadas = false;
    boolean mailsActivados = false;
    boolean recalculoActivado = false;

    String mail;

    void activarNotificaciones() {
        notificacionesActivadas = true;
    }

    void activarMails() {
        mailsActivados = true;
    }

    void activarRecalculo() {
        recalculoActivado = true;
    }

    void desactivarNotificaciones() {
        notificacionesActivadas = false;
    }

    void desactivarMails() {
        mailsActivados = false;
    }

    void desactivarRecalculo() {
        recalculoActivado = false;
    }

    void compartirGuardarropas(Guardarropa unGuardarropa, Usuario destinatario) {
        destinatario.recibirGuardarropas(unGuardarropa);
    }

    void recibirGuardarropas(Guardarropa unGuardarropa) {
        guardarropasDeAmigos.add(unGuardarropa);
    }

    void enviarSugerencia(Sugerencia unaSugerencia, Usuario destinatario) {
        unaSugerencia.setDestinatario(destinatario);
        destinatario.recibirSugerencia(unaSugerencia);
    }

    void recibirSugerencia(Sugerencia unaSugerencia) {
        sugerenciasRecibidas.add(unaSugerencia);
    }

    void decidirSobreSugerencias() {
        for(Sugerencia unaSugerencia : sugerenciasRecibidas) {
            mostrar(unaSugerencia);
            decidir(unaSugerencia);
            sugerenciasRecibidas.remove(unaSugerencia);
        }
    }

    void mostrar(Sugerencia unaSugerencia){
        // TODO
    }

    void decidir(Sugerencia unaSugerencia) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Desea aceptar o rechazar la sugerencia?");
        String decision = scan.nextLine();

        if (decision.startsWith("a") || decision.startsWith("A")) {
            aceptarSugerencia(unaSugerencia);
        }
        else {
            rechazarSugerencia(unaSugerencia);
        }

        scan.close();
    }

    void aceptarSugerencia(Sugerencia unaSugerencia) {
        unaSugerencia.aceptar();
        sugerenciasAceptadas.add(unaSugerencia);
    }

    void rechazarSugerencia(Sugerencia unaSugerencia) {
        unaSugerencia.rechazar();
    }

    void revertirSugerencia(Sugerencia unaSugerencia) {
        if (sugerenciasAceptadas.contains(unaSugerencia)) {
            unaSugerencia.revertirEfecto();
            sugerenciasAceptadas.remove(unaSugerencia);
        }
    }

    Atuendo recibirSugerenciaAtuendoPorTemperatura(Temperatura unaTemperatura) {
        return QueMePongo.recibirSugerenciaAtuendoPorTemperatura(unaTemperatura);
    }

    Uniforme recibirSugerenciaUniforme() {
        return QueMePongo.getUniformesSugeridos().get(QueMePongo.numeroRandomSegunLista(QueMePongo.getUniformesSugeridos().size()));
    }

    void iniciarCreacionDePrenda() {
        BuilderPrenda.cargarPrenda();
    }

    void crearPrenda() {
        Prenda prenda = BuilderPrenda.crearPrenda();
        QueMePongo.agregarPrenda(prenda);
        agregarPrenda(prenda);
    }

    void agregarPrenda(Prenda prenda) {
        misPrendas.add(prenda);
    }

    void quitarPrenda(Prenda prenda) {
        misPrendas.remove(prenda);
    }

    void suscribirseAAlertas() {
        Meteorologo.agregarSuscriptor(this);
    }

    void desuscribirseAAlertas() {
        Meteorologo.quitarSubscriptor(this);
    }

    void notificarAlertas(List<String> alertas) {
        String alertasEnFormato;

        for (String unaAlerta : alertas) {
            alertasEnFormato += (unaAlerta + ", ");
        }

        if (notificacionesActivadas) {
            NotificationService.notify("Se recibieron las siguientes alertas: " + alertasEnFormato);
        }

        if (mailsActivados) {
            MailSender.send(mail, "Se recibieron las siguientes alertas: " + alertasEnFormato);
        }
    }

    void recibirSugerenciaParaguas() {
        if (notificacionesActivadas) {
            NotificationService.notify("Debido a la posibilidad de tormentas, se recomienda llevar paraguas!");
        }
    }

    void recibirSugerenciaNoUsarAuto() {
        if (notificacionesActivadas) {
            NotificationService.notify("Debido a la posibilidad de granizo, se recomienda no viajar en auto!");
        }
    }

    public Atuendo recibirSugerenciaDiaria() {
        return QueMePongo.sugerenciaDelDia();
    }

    public boolean recalculoActivado() {
        return recalculoActivado;
    }
}
