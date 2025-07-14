package controller;

import it.unina.vetbook.control.AuthController;
import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.entity.*;
import it.unina.vetbook.utils.LoginResult;
import it.unina.vetbook.utils.RegistrationResult;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    AuthController ctrl;
    MockedStatic<Utente> utenteStatic;
    MockedStatic<UtenteFactory> factoryStatic;
    MockedStatic<Agenda> agendaStatic;

    @BeforeEach
    void setUp() {
        utenteStatic = Mockito.mockStatic(Utente.class);
        factoryStatic = Mockito.mockStatic(UtenteFactory.class);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(Mockito.mock(Agenda.class));
        ctrl = AuthController.getInstance();
    }

    @AfterEach
    void tearDown() {
        utenteStatic.close();
        factoryStatic.close();
        agendaStatic.close();  // <--- chiudi anche questo
        resetSingleton();
    }

    private void resetSingleton() {
        try {
            Field f = AuthController.class.getDeclaredField("instance");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void login_shouldFail_whenFieldsAreEmpty() {
        LoginResult result = ctrl.login("", "password");
        assertFalse(result.isSuccess());
        assertEquals("Tutti i campi sono obbligatori.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void login_shouldFail_whenPasswordIsShort() {
        LoginResult result = ctrl.login("username", "123");
        assertFalse(result.isSuccess());
        assertEquals("La password deve contenere almeno 6 caratteri.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void login_shouldFail_whenCredentialsInvalid() {
        utenteStatic.when(() -> Utente.login("username", "password")).thenReturn(null);

        LoginResult result = ctrl.login("username", "password");

        assertFalse(result.isSuccess());
        assertEquals("Credenziali non valide.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void login_shouldSucceed_whenProprietario() {
        Proprietario mockUser = Mockito.mock(Proprietario.class);
        when(mockUser.getRuolo()).thenReturn(UserRole.PROPRIETARIO);

        utenteStatic.when(() -> Utente.login("username", "password")).thenReturn(mockUser);

        LoginResult result = ctrl.login("username", "password");

        assertTrue(result.isSuccess());
        assertNotNull(result.getController());
        assertTrue(result.getController() instanceof ProprietarioController);
    }

    @Test
    void registrati_shouldSucceed_whenValidData() {
        utenteStatic.when(() -> Utente.exists("username")).thenReturn(false);

        // Mock completo del proprietario
        Proprietario mockProprietario = Mockito.mock(Proprietario.class);

        // Mock statico della factory
        factoryStatic.when(() ->
                UtenteFactory.creaProprietario("username", "mail@test.com", "nome", "cognome", "password")
        ).thenReturn(mockProprietario);

        // Mock metodo registrati()
        doNothing().when(mockProprietario).registrati();

        RegistrationResult result = ctrl.registrati("username", "mail@test.com", "nome", "cognome", "password");

        verify(mockProprietario).registrati();
        assertTrue(result.isSuccess());
        assertNotNull(result.getController());
        assertTrue(result.getController() instanceof ProprietarioController);
    }



    @Test
    void registrati_shouldFail_whenFieldsAreEmpty() {
        RegistrationResult result = ctrl.registrati("", "mail@test.com", "nome", "cognome", "password");
        assertFalse(result.isSuccess());
        assertEquals("Tutti i campi sono obbligatori.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void registrati_shouldFail_whenEmailInvalid() {
        RegistrationResult result = ctrl.registrati("username", "emailNonValida", "nome", "cognome", "password");
        assertFalse(result.isSuccess());
        assertEquals("Email non valida.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void registrati_shouldFail_whenPasswordTooShort() {
        RegistrationResult result = ctrl.registrati("username", "mail@test.com", "nome", "cognome", "123");
        assertFalse(result.isSuccess());
        assertEquals("La password deve contenere almeno 6 caratteri.", result.getErrorMessage());
        assertNull(result.getController());
    }

    @Test
    void registrati_shouldFail_whenUsernameExists() {
        utenteStatic.when(() -> Utente.exists("username")).thenReturn(true);

        RegistrationResult result = ctrl.registrati("username", "mail@test.com", "nome", "cognome", "password");

        assertFalse(result.isSuccess());
        assertEquals("Username già presente.", result.getErrorMessage());
        assertNull(result.getController());
    }
}
