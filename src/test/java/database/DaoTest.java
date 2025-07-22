package database;

import it.unina.vetbook.database.*;
import it.unina.vetbook.entity.*;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaoTest {

    private Connection conn;

    @BeforeAll
    void prepareDatabase() throws Exception {
        conn = DriverManager.getConnection(
                "jdbc:h2:mem:vetbook;MODE=MySQL;DB_CLOSE_DELAY=-1");

        try (var in = getClass().getResourceAsStream("/vetbook_schema.sql")) {
            assertNotNull(in, "Impossibile trovare vetbook_schema.sql nel class-path");
            RunScript.execute(conn, new InputStreamReader(in, StandardCharsets.UTF_8));
        }
    }

    @AfterAll
    void closeConnection() throws Exception { conn.close(); }

    @AfterEach
    void cleanTables() throws Exception {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

            st.executeUpdate("TRUNCATE TABLE farmaci_has_visite");
            st.executeUpdate("TRUNCATE TABLE visite");
            st.executeUpdate("TRUNCATE TABLE disponibilita");
            st.executeUpdate("TRUNCATE TABLE prenotazioni");
            st.executeUpdate("TRUNCATE TABLE animali");
            st.executeUpdate("TRUNCATE TABLE farmaci");
            st.executeUpdate("TRUNCATE TABLE utenti");

            st.execute("ALTER TABLE utenti               ALTER COLUMN id RESTART WITH 1");
            st.execute("ALTER TABLE animali              ALTER COLUMN codicechip RESTART WITH 1000000001");
            st.execute("ALTER TABLE disponibilita        ALTER COLUMN id RESTART WITH 1");
            st.execute("ALTER TABLE prenotazioni         ALTER COLUMN id RESTART WITH 1");
            st.execute("ALTER TABLE visite               ALTER COLUMN id RESTART WITH 1");
            st.execute("ALTER TABLE farmaci              ALTER COLUMN id RESTART WITH 1");

            st.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
        }
    }

    @Test
    void utenteCrud_OK() throws Exception {
        UtenteDAO dao = new UtenteDAO(conn);

        Proprietario u = new Proprietario("mrossi","m@x.it","pw123");
        u.setNome("Mario"); u.setCognome("Rossi");

        dao.create(u); 
        assertEquals(1, u.getId());

        Utente db = dao.read(new Integer[]{u.getId()}).orElseThrow();
        assertEquals("mrossi", db.getUsername());

        db.setUsername("nuovoUser");
        dao.update(db);
        assertEquals("nuovoUser",
                dao.read(new Integer[]{1}).orElseThrow().getUsername());

        dao.delete(1);
        assertTrue(dao.read(new Integer[]{1}).isEmpty());
    }

    @Test
    void animaleCrud_OK() throws Exception {
        UtenteDAO utenti = new UtenteDAO(conn);
        Proprietario p = new Proprietario("p1","p@x.it","pw123");
        p.setNome("Paolo"); p.setCognome("Bianchi");
        utenti.create(p);

        AnimaleDomesticoDAO dao = new AnimaleDomesticoDAO(conn);

        AnimaleDomestico a = new AnimaleDomestico(
                1000000001, p.getId(),
                "Fido", "Cane", "Labrador", "Nero",
                LocalDate.of(2020,6,10));

        dao.create(a); 
        assertEquals(1000000001L, a.getCodiceChip());

        AnimaleDomestico db = dao.read(new Integer[]{a.getCodiceChip()}).orElseThrow();
        assertEquals("Fido", db.getNome());

        db.setColore("Miele");
        dao.update(db);
        assertEquals("Miele",
                dao.read(new Integer[]{a.getCodiceChip()}).orElseThrow().getColore());

        dao.delete(a.getCodiceChip());
        assertTrue(dao.read(new Integer[]{a.getCodiceChip()}).isEmpty());
    }
    
    
    @Test
    void farmacoCrud_OK() throws Exception {
        FarmacoDAO dao = new FarmacoDAO(conn);

        Farmaco f = new Farmaco("Antibiocan","VetLife");
        dao.create(f); 
        assertEquals(1, f.getId());

        Farmaco db = dao.read(new Integer[]{1}).orElseThrow(); 
        assertEquals("Antibiocan", db.getNome());

        db.setProduttore("NuovoProd");
        dao.update(db);
        assertEquals("NuovoProd",
                dao.read(new Integer[]{1}).orElseThrow().getProduttore());

        List<Farmaco> all = dao.readAll();
        assertEquals(1, all.size());

        dao.delete(1);
        assertTrue(dao.read(new Integer[]{1}).isEmpty());
    }

    @Test
    void disponibilitaCrud_OK() throws Exception {
        DisponibilitaDAO dao = new DisponibilitaDAO(conn);

        Disponibilita d = new Disponibilita(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0));

        dao.create(d); 
        assertEquals(1, d.getId());

        Disponibilita db = dao.read(new Integer[]{1}).orElseThrow();
        assertEquals(LocalTime.of(10,0), db.getOra());

        db.setOra(LocalTime.of(11,0));
        dao.update(db);
        assertEquals(LocalTime.of(11,0),
                dao.read(new Integer[]{1}).orElseThrow().getOra());

        dao.delete(1);
        assertTrue(dao.read(new Integer[]{1}).isEmpty());
    }


    @Test
    void prenotazioneCrud_OK() throws Exception {
        UtenteDAO utenti = new UtenteDAO(conn);
        Proprietario p = new Proprietario("prop","prop@x.it","pw");
        utenti.create(p);

        AnimaleDomesticoDAO animDao = new AnimaleDomesticoDAO(conn);
        AnimaleDomestico a = new AnimaleDomestico(
                1000000001, p.getId(),
                "Luna","Gatto","Europeo","Bianco",
                LocalDate.of(2021,3,12));
        animDao.create(a);

        PrenotazioneDAO dao = new PrenotazioneDAO(conn);

        Prenotazione pr = new Prenotazione(
                LocalDate.now().plusDays(1),
                LocalTime.of(9,0),
                a);

        dao.create(pr);
        assertEquals(1, pr.getId());

        Prenotazione db = dao.read(new Integer[]{1}).orElseThrow();
        assertEquals(LocalTime.of(9,0), db.getOra());

        dao.delete(1);
        assertTrue(dao.read(new Integer[]{1}).isEmpty());
    }

}
