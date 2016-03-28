#SuperMarket

# Pagine dell'applicazione

## Main Activity

In questa activity l'utente può scorrere l'elenco delle spese effettuate tramite
la `listview` dedicata. Il tap su uno degli elementi della listview porta
all'apertura della spesa stessa nell'activity (o comunque nel fragment)
associato al dettaglio della spesa.

Con un long-tap, viene aperta una schermata di dialogo in cui vengono mostrati i
valori di testata della spesa, cioè *data* e *negozio*. In particolare, la
seconda `TextView` è una `AutoCompleteTextView`, i cui valori possibili proposti
come suggerimenti, sono le descrizioni dei negozi inseriti nel database interno
all'applicazione. E' presente anche un flag, che indica se procedere con
l'apertura della spesa quando verrà confermata l'operazione.
La schermata di dialogo dovrà avere tre pulsanti:

- `Ok`: procede alla modifica della spesa, risettando data e negozio a quanto
  indicato dall'utente;

- `Exit`: annulla l'operazione di modifica della spesa;

- `Delete`: cancella la spesa dal database, rimuovendo anche tutti gli articoli
  di spesa ad essa associati.

## Detail Activity

In questa activity saranno presenti due view principali:

- la prima con le informazioni di testata della spesa, in cui viene riportato,
  man mano che vengono aggiunti articoli, il costo parziale totale;

- la seconda contenente la lista degli articoli di spesa, su cui è possibile
  eseguire una ricerca tramite la `SearchView` dedicata.

Sulla lista di articoli dovrà essere attiva la funzionalità di
`SwipeToDismiss`, che consente all'utente di rimuovere un articolo in modo
rapido. Da valutare l'introduzione di un meccanismo di long-tap che metta a
video i suggerimenti per la rimozione e la modifica:

- Slide dell'elemento verso sinistra indica la rimozione dell'elemento dalla
  lista;
- Slide dell'elemento verso destra indica che si vuole "entrare"
  nell'elemento, ergo viene proposta la schermata di dialogo di modifica dati.

Un'alternativa alla schermata di dialogo della modifica dati è quella di
presentare una nuova activity (o grazie al material design, una schermata di
dettaglio) in cui vengono presentati i dettagli del prodotto:

- dati del prodotto (articolo, categoria, marca);
- dati dell'acquisto (importo unitario, numero pezzi, importo totale);
- andamento dei prezzi sulle varie spese (per questo creare una vista ad hoc);
- andamento dei prezzi sui vari negozi (come sopra);

In questo caso, il salvataggio avverrebbe tramite pressione del tasto apposito
sulla actionbar o con la pressione del back per tornare alla pagina precedente.

Per quanto riguarda l'activity di dettaglio spesa, la action bar conterrebbe i
tasti per le funzionalità di undo/redo, limitati ai soli inserimenti/rimozioni
degli articoli nella lista, e un tasto per salvare la spesa.
In ogni caso, la pressione del tasto architetturale `back` genererebbe
l'evento
di salvataggio spesa, di cui verrà chiesta conferma tramite schermata di
dialogo.

# Database

## Database standard via `SQL`

Verranno sfruttate le funzionalità messe a disposizione dal `ContentProvider`
di Android, e verranno implementati i metodi `query`, `update`, `insert` e
`delete`.

### Definizione degli `Uri` e degli indicatori

I riferimenti sono stati definiti nella classe `DBConstants` tramite stringhe
relative agli Uri, gli uri veri e propri e gli indicatori. Abbiamo tre
tipologie:

- Specifici di tabella:


### Metodo `query`

Il metodo

## Database noSQL
Tbd

# ToDos

- id: ` [ ADD_EXPENSE_ARTICLE ] `: valutare inserimento di logica di merge per
  articoli uguali (i.e. stessa marca, categoria e descrizione) in caso di
  doppio inserimento. Il metodo `add` di `AdapterExpenseArticles` potrebbe
  essere wrappato da metodo che controlla esistenza doppioni in lista e propone
  il merge tramite `SMDoubleExpenseArticleException`;
  **UPDATE**: inserita questa logica sul metodo dell'interfaccia. Controllare
  la possibilità di eseguire lo scrolling della listview fino ad arrivare all'
  elemento desiderato (se esiste ho già la sua posizione) e implementare una
  piccola animazione per evidenziare la posizione all'utente.

- id: ` [ RETRIEVE_OBJECTS ] `: rivedere il processo di recupero dei vari
  oggetti (articoli, brand, categorie e negozi). Al momento vengono recuperate
  solo le loro descrizioni e in caso di aggiunta/rimozione di oggetti a db non
  c'e' un meccanismo di refresh delle liste.
  Valutare l'inserimento di una sorta di binding tra la tabella db e le le liste
  di oggetti, intesi stavolta come `BaseSMBean` e aggiungere il relativo oggetto
  come tag alla view della lista di autocompletamento.

- id: ` [ SWIPE_TO_DISMISS ] `: al momento in ogni direzione dello slide, viene
  rimosso l'item. Valutare di inserire un meccanismo di long tap sull'item che
  mostri le opzioni di rimozione e di modifica (leggero scroll verso sx e due
  icone una sopra l'altra con bidone e freccia verso sx [ rimozione ] e matita
  con freccia verso dx [ modifica ] ).

- id: ` [ MATERIAL_DESIGN ] `: modificare l'app per evitare le diverse activity
  ed utilizzare il paradigma material design.

- id: ` [ NAVIGATION_BAR ] `: inserire una navigation bar sulla action bar.
# Riferimenti

- ` [ ADD_EXPENSE_ARTICLE ] `:
    - http://stackoverflow.com/questions/14479078/smoothscrolltopositionfromtop-is-not-always-working-like-it-should/20997828#20997828
    - ...
- ` [ SWIPE_TO_DISMISS ] `:
    - http://stackoverflow.com/a/2564751

- ` [ MATERIAL_DESIGN ] `:
    - http://www.google.com/design/spec/material-design/introduction.html#introduction-goals
    - http://developer.android.com/training/material/theme.html
