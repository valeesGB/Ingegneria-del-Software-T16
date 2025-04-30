# Ingegneria-del-Software-T16
## T16: Prenotazione vaccinazioni
Si vuole realizzare un sistema software per una farmacia per la prenotazione degli appuntamenti per 
le vaccinazioni. 
La Società che richiede il software gestisce una piccola catena di n ${\color{blue}farmacie}$ in diversi ${\color{green}quartieri}$ della 
${\color{green}città}$. In ciascuna farmacia si possono prenotare i ${\color{blue}vaccini}$ ${\color{green}Pfischio, \space Antiqua \space e \space AsperaZenzero}$. Il 
${\color{red}Dirigente}$ della Società ${\color{yellow}registra \space nel \space sistema \space i}$ ${\color{red}CapiFarmacia}$, ${\color{yellow}uno \space per \space ciascuna \space farmacia}$, addetti alla 
gestione delle prenotazioni, i quali per effetto della registrazione da parte del Dirigente ${\color{yellow}ricevono \space per \space posta \space elettronica \space le}$ ${\color{green}credenziali \space di \space accesso}$ al sistema. A loro volta, i CapiFarmacia ${\color{yellow}registrano \space i}$ 
${\color{red}Farmacisti}$, ${\color{yellow}che \space per \space effetto \space della \space registrazione \space ricevono \space per \space posta \space elettronica \space le \space credenziali \space di \space accesso}$ 
al sistema. Tutti i giorni della settimana, sabato e domenica compresi, ci sono due ${\color{green}turni}$ per il servizio 
di vaccinazione, uno dalle 8 alle 14, l’altro dalle 14 alle 20. ${\color{yellow}Il \space sistema \space deve \space effettuare \space le \space prenotazioni \space
distanziando \space gli \space appuntamenti \space di \space 15 \space minuti \space l’uno \space dall’altro}$. I CapiFarmacia ${\color{yellow}inseriscono \space nel \space sistema \space i \space 
turni \space dei \space Farmacisti \space per \space la \space settimana \space successiva}$ (un Farmacista per turno). 
Tramite il sistema, un ${\color{red}cliente}$ ${\color{yellow}può \space prenotare \space un}$ ${\color{blue}\space appuntamento}$ ${\color{yellow}per una vaccinazione}$, selezionando la 
farmacia, il ${\color{green}giorno}$ e il ${\color{green}vaccino}$, e fornendo il proprio ${\color{green}nome, \space cognome, \space indirizzo \space di \space posta \space elettronica \space e \space
descrivendo, \space in \space un \space campo \space di \space testo, \space eventuali \space allergie \space a \space farmaci}$. ${\color{yellow}Il \space cliente \space non \space sceglie \space l’ora \space 
dell’appuntamento, \space ma \space il \space sistema \space assegna \space il \space primo \space orario \space disponibile \space nel \space giorno \space scelto, \space}$ ${\color{yellow}altrimenti \space 
segnala \space l’indisponibilità \space per \space quel \space giorno. \space A \space prenotazione \space effettuata, \space il \space sistema \space invia \space al \space cliente \space per \space 
posta \space elettronica \space}$ ${\color{yellow}un \space messaggio \space di \space riepilogo \space della \space prenotazione}$, contenente l’indirizzo della Farmacia, 
il nome del vaccino, il giorno e l’ora dell’appuntamento, e l’indirizzo di una casella di posta 
elettronica (letta dal CapoFarmacia) cui inviare un messaggio per eventualmente disdire 
l’appuntamento prima del giorno stabilito. ${\color{yellow}Quando \space un \space CapoFarmacia \space riceve \space un \space messaggio \space di \space disdetta, \space 
accede \space al \space sistema \space per \space cancellare \space l’appuntamento.}$  
Ad inizio turno, il Farmacista ${\color{yellow}consulta \space il \space sistema \space per \space stampare \space gli \space appuntamenti}$ del turno. Ad ogni 
appuntamento, il Farmacista ${\color{yellow}utilizza \space il \space sistema \space per \space registrare\space (in \space un \space campo \space di \space testo) \space i \space dati \space anamnestici \space
del \space paziente}$ prima di effettuare la vaccinazione, ${\color{yellow}e \space l’esito \space della \space vaccinazione}$ (effettuata/non effettuata, 
e, se non effettuata, il Farmacista inserisce in un campo di testo la motivazione).  
Alla fine di ogni giornata, ${\color{yellow}settimanalmente \space e \space mensilmente \space il \space sistema \space invia \space per \space posta \space elettronica \space al \space
Dirigente \space dei \space report \space statistici \space sul \space numero \space di \space prenotazioni, \space di \space}$ ${\color{yellow}annullamenti \space e \space di \space vaccini \space effettuati \space in \space
ogni \space farmacia \space della \space catena. \space Mensilmente \space il \space sistema \space invia \space inoltre \space ad \space un \space}$ ${\color{red}impiegato \space Amministrativo \space}$ 
${\color{yellow}l’elenco \space dei \space turni \space svolti \space da \space ciascun \space Farmacista, \space per \space la \space liquidazione \space dei \space compensi \space nel \space caso \space dei \space}$
${\color{pink}Farmacisti \space non \space già \space dipendenti}$ della Società.
