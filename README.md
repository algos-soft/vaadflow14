##Valore risorse
- Scrivere una coppia di tag=valore nel file application.resources
- Es: algos.framework.version=0.27
- Nella classe java in cui si vuole usare il valore, iniettare Environment

  
    @Autowired
    private Environment env;

- Richiamare il valore con env.getProperty("xxx");


    String database = env.getProperty("spring.data.mongodb.database");
    Double version = env.getProperty("algos.framework.version");
    String mail = env.getProperty("spring.mail.host");
    
##Cartelle
Cartelle a livello di /root del progetto
####algos
- Link e snippets vari
###build
- Gestita in automatico da Idea
####config
- File di risorse di testo e immagini
###documentation
- Scripts e sorgenti per i logs
###frontend
- Risorse css, js, ts
###log
- Uscita dei files di log
###node-modules
- Gestita in automatico da Vaadin
