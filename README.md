# Ingegneria-del-Software-T16
T16: Prenotazione vaccinazioni 
Si vuole realizzare un sistema software per una farmacia per la prenotazione degli appuntamenti per 
le vaccinazioni. 
La Società che richiede il software gestisce una piccola catena di n farmacie in diversi quartieri della 
città. In ciascuna farmacia si possono prenotare i vaccini Pfischio, Antiqua e AsperaZenzero. Il 
Dirigente della Società registra nel sistema i CapiFarmacia, uno per ciascuna farmacia, addetti alla 
gestione delle prenotazioni, i quali per effetto della registrazione da parte del Dirigente ricevono per 
posta elettronica le credenziali di accesso al sistema. A loro volta, i CapiFarmacia registrano i 
Farmacisti, che per effetto della registrazione ricevono per posta elettronica le credenziali di accesso 
al sistema. Tutti i giorni della settimana, sabato e domenica compresi, ci sono due turni per il servizio 
di vaccinazione, uno dalle 8 alle 14, l’altro dalle 14 alle 20. Il sistema deve effettuare le prenotazioni 
distanziando gli appuntamenti di 15 minuti l’uno dall’altro. I CapiFarmacia inseriscono nel sistema i 
turni dei Farmacisti per la settimana successiva (un Farmacista per turno). 
Tramite il sistema, un cliente può prenotare un appuntamento per una vaccinazione, selezionando la 
farmacia, il giorno e il vaccino, e fornendo il proprio nome, cognome, indirizzo di posta elettronica e 
descrivendo, in un campo di testo, eventuali allergie a farmaci. Il cliente non sceglie l’ora 
dell’appuntamento, ma il sistema assegna il primo orario disponibile nel giorno scelto, altrimenti 
segnala l’indisponibilità per quel giorno. A prenotazione effettuata, il sistema invia al cliente per 
posta elettronica un messaggio di riepilogo della prenotazione, contenente l’indirizzo della Farmacia, 
il nome del vaccino, il giorno e l’ora dell’appuntamento, e l’indirizzo di una casella di posta 
elettronica (letta dal CapoFarmacia) cui inviare un messaggio per eventualmente disdire 
l’appuntamento prima del giorno stabilito. Quando un CapoFarmacia riceve un messaggio di disdetta, 
accede al sistema per cancellare l’appuntamento.  
Ad inizio turno, il Farmacista consulta il sistema per stampare gli appuntamenti del turno. Ad ogni 
appuntamento, il Farmacista utilizza il sistema per registrare (in un campo di testo) i dati anamnestici 
del paziente prima di effettuare la vaccinazione, e l’esito della vaccinazione (effettuata/non effettuata, 
e, se non effettuata, il Farmacista inserisce in un campo di testo la motivazione).  
Alla fine di ogni giornata, settimanalmente e mensilmente il sistema invia per posta elettronica al 
Dirigente dei report statistici sul numero di prenotazioni, di annullamenti e di vaccini effettuati in 
ogni farmacia della catena. Mensilmente il sistema invia inoltre ad un impiegato Amministrativo 
l’elenco dei turni svolti da ciascun Farmacista, per la liquidazione dei compensi nel caso dei 
Farmacisti non già dipendenti della Società.
