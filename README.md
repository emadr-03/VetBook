# VetBook  
Progetto di Ingegneria del Software – A.A. 2024/2025

## Descrizione del progetto
VetBook è un'applicazione gestionale progettata per supportare le attività di un ambulatorio veterinario.  

## Requisiti tecnici

- **Java Runtime Environment**: JRE 21 o superiore
- **Database Management System**: MySQL

## Tipologie di utenti

- **Proprietario di animali**  
  Può registrarsi, accedere al proprio profilo, modificare i dati personali e gestire i dati degli animali. È abilitato a prenotare visite scegliendo tra le date e ore disponibili.

- **Veterinario**  
  Può visualizzare l’elenco delle prenotazioni giornaliere, registrare visite effettuate, specificare tipologia, descrizione, costo e farmaci prescritti.

- **Amministratore**  
  Inserisce date e orari di disponibilità per le visite, visualizza le visite della giornata e l’incasso totale, nonché la lista degli animali che non vengono vaccinati da oltre un anno.

## Documentazione

La documentazione completa è disponibile in due formati nella root del progetto:
- `VetBook.pdf`
- `VetBook.docx`

## Setup del database

Per effettuare un testing completo dell’applicazione è necessario:

1. Installare MySQL (si veda la guida `mysql_tutorial.pdf` presente nella cartella `dump/`);
2. Eseguire nell’ordine i seguenti script SQL, sempre nella cartella `dump/`:
   - `schema.sql`: crea la struttura del database;
   - `popolamento.sql`: popola le tabelle con dati mock.

> Il database da creare deve essere mysql, esposto su porto 3306 e deve chiamarsi `vetbook`, lo username è `studente`, la password è `studente`. In alternativa per usare credenziali diverse è necessario modificare la classe DBManager. (`jdbc:mysql://localhost:3306/vetbook`)

## Utenti mock già presenti nel sistema

| Ruolo         | Username     | Password          |
|---------------|--------------|-------------------|
| Proprietario  | `mrossi`     | `proprietario123` |
| Veterinario   | `drroberto`  | `vet123`          |
| Amministratore| `admin1`     | `admin123`        |

## Avvio dell'applicazione

L'applicazione può essere avviata in due modi alternativi:

1. **Da codice**  
   Eseguire il metodo `main()` della classe `AppLauncher`.

2. **Da file eseguibile**  
   Utilizzare il file `.jar` precompilato presente nella cartella `out/artifcats/vetbook_original_jar`.

L'interfaccia utente è sviluppata in Java Swing e adotta il tema grafico FlatLaf per offrire una visualizzazione moderna e coerente su sistemi desktop.

## Note
Da UI è possibile interagire con le seguenti feature ma i dati visualizzati sono mockati (a livello di controller), dunque non c'è persistenza nel database:
### Proprietario
1) Gestione profilo
2) Aggiorna (Immagine profilo)
3) Carica (Immagine profilo)
3) Inserisci Nuovo Animale
4) Modifica (Animale)
5) Elimina (Animale)
### Amministratore
6) Animali Non Vaccinati

Le seguenti feature sono funzionanti e seguono il flusso completo boundary->control->entity->database
### Proprietario
1) Visualizza i Tuoi Animali
2) Prenota Visita
### Amministratore
1) Inserisci Disponibilità
2) Incassi Giornalieri
### Veterinario
1) Visualizza Prenotazioni
2) Registra Visita