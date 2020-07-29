Grid
======================


##Documentazione
- [Is your Grid too slow?](https://vaadin.com/blog/using-the-right-r)

##Tipo possibili di colonne
In ordine di velocità e difficoltà crescenti:
1) ComponentRenderers
2) TemplateRenderers
3) ValueProvider


    // ComponentRenderer
    grid.addComponentColumn(item -> new Span(item));

    // TemplateRenderer
    grid.addColumn(TemplateRenderer.<String> of("[[item.string]]").withProperty("string", item -> item));

    // ValueProvider
    grid.addColumn(ValueProvider.identity()); // same as item -> item
    
##Soluzioni
1) ValueProvider

- Valori elementari (Text ed Integer)
- Semplicità di realizzazione
- Velocità di esecuzione


    PaginatedGrid grid = new PaginatedGrid<>();
    grid.addColumn(item -> ((Regione)item).ordine).setHeader("#").setSortable(true);

 2) ComponentRenderer
 
 - Massima libertà e flessibilità di composizione
 - Decisamente più lento
 - Più complesso da scrivere
 - Non supporta le colonne sortable
 
 
    PaginatedGrid grid = new PaginatedGrid<>();
    grid.addComponentColumn(item -> new Span(((Regione)item).ordine+"")).setHeader("#");

 
 3) TemplateRenderer
 
 - Richiede utilizzo di HTML
 - Inutilmente complesso e farraginoso
 - Non particolarmente veloce
 
