# to Ask

## sui requisiti

- La deposit action viene chiamata dal WS? Quando si accende viene pilotato dal servizio.
- Cosa si intende per smart device? messaggio
- Per interagire con indoor/containers è necessario che stia in una cella adiacente al muro? si
- il sonar ha dei momenti in cui non deve essere attivato? se no, come fa ad interagire con indoor/container?
- Il service manager, con la gui, svolge solo una funzione di supervisione o deve interagire col sistema? visualizza e basta
- Il waste truck, dopo aver interagito con indoor, manda la richiesta, le informazioni sul materiale le riceve da indoor? il truckLoad è di dimensione fissa?
- per tenere traccia della capienza dei container, usiamo uno stato dentro WS, oppure i container sono entità esterne che comunicano con WS/trolley?

## sul cosa ci fornisce

- Il committente fornisce software relativo al Led ?
- Il committente fornisce software per il Sonar ?
- Il committente fornisce qualche libreria per la costruzione della GUI?
- Il LED può/deve essere connesso allo stesso RaspberryPi del sonar?
- I valori DLIMIT, MAXPB , MAXGB , RD deve essere cablato nel sistema o è bene sia definibile in modo configurabile dall’utente finale? cablati nel sistema

## su HTML

- il sonar è un componente autonomo?
- i due attori: transport_trolley e Led sono controllati da WS?
- Aggiungiamo un errore se si rompe qualcosa nel trolley o nel led? no
- Il Sonar manda continuamente la misura al WS oppure solo quando ci supera DLIMIT?
- applichiamo un approccio divide et impera sulla business logic? --> separiamo i controller

scrivere qualcosa anche capibile dallam macchina

componente : entità esterna al sistema (driver ) deve mandare un messaggio (request) [request_dump] al Waste Service () risponde con [load_accept] e [load_reject].

posizione: indoor, home, "working", container1, container2
