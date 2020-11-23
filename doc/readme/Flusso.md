Flusso
======================
Flusso degli eventi nelle classi controllate dal programma:

###Setup non-UI logic
- Spring context
- Application 
- All @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
- A ContextRefreshEvent occurs
  
      when an ApplicationContext gets initialized or refreshed.

- @EventListener
- FlowBoot.onApplicationEvent(ContextRefreshedEvent event)

###Browser call


#Analisi
####Spring context
####Application 
####@EventListener
####FlowBoot
- Inizializzazione di alcuni parametri del database mongoDB
- (?) Riferimento alla sottoclasse specifica per utilizzare il metodo sovrascritto resetPreferenze()
- Inizializzazione dei dati di alcune collections essenziali per la partenza
- Crea le preferenze standard, se non esistono
- Regola alcune variabili generali dell' applicazione al loro valore iniziale di default
- Regolazione delle preferenze standard effettuata nella sottoclasse specifica
- Inizializzazione dei dati di alcune collections sul DB mongo
- Aggiunge le @Route (view) standard






Nell'architettura di SpringBoot, occorre la classe di partenza col metodo `main`.

- La prima classe intercettabile nel flusso è

##Link
- running-code-on-spring-boot-startup [guru](https://springframework.guru/running-code-on-spring-boot-startup/)