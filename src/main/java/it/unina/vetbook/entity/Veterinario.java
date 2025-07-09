package it.unina.vetbook.entity;

public class Veterinario extends Utente {

    private final Agenda agenda = Agenda.getInstance();
    private static Veterinario instance;

    public static Veterinario getInstance() {
        if (instance == null) {
            instance = new Veterinario();
        }
        return instance;
    }

    public void registraVisita(Visita v) {
        agenda.getVisite().add(v);
    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}
