# :gb: Ex-01-BusinessLogic-EJB
**Project for the "Distributed Systems" course, Master Degree in Computer Engineering, University of Bologna.**

Develop a J2EE Web Application for managing the warehouse of a computer components shop.

The application must be an e-commerce website and must let the user put the electronic components inside his cart and confirm the order later.

The orders must be persistently saved in a DB by using Hibernate and/or JPA.

Consider the following model in order to develop the application:
1. Products (computer components: CPUs, RAM, graphic cards etc.)
2. Producers (amd, intel, nvidia etc.)
3. Cart (SessionBean waiting to be purchased)
4. Orders (EntityBean to be saved in the database)

Requested tasks:
-  create the database access business logic (DAO pattern) using Enterprise Java Beans 3.0 components, in particular:
   + use the Object-Relational mapping through Entity Beans
   + implement DAO using Session Bean components

-  additionally, extend the described business logic by adding a (potentially) remote logging mechanism:
   + each method expecting to write on the DB (adding new components, producers, orders etc.) must send an JMS message to an appropriate logging component
   + a logging component (implemented with a Message Driven Bean) must write the received message on an appropriate log


# :it: Ex-01-BusinessLogic-EJB
**Progetto per il corso "Sistemi Distribuiti", Ingegneria Informatica Magistrale, Università di Bologna.**

Si sviluppi un applicazione Web J2EE per la gestione del magazzino di un ipotetico negozio di componenti informatiche.

Tale applicazione deve avere l’aspetto di un sito e-commerce e che quindi dia la possibilità ad un utente di mettere le componenti elettroniche nel carrello e successivamente di inviare l’ordine d’acquisto.

Gli ordini poi (mediante Hibernate e/o JPA) dovranno essere salvati permanentemente in un DB.

Per realizzare l’applicazione si consideri il seguente modello:
1. Prodotti (componenti per pc: processori, ram, schede video ecc..)
2. Produttori (amd, intel, nvidia ecc..)
3. Carrello (SessionBean in attesa di essere ordinati)
4. Ordini (EntiyBean che devono essere salvati nel data base)

Si richiede di:
-  realizzare la parte di logica di accesso al database (pattern DAO) mediante componenti Enterprise Java Beans 3.0, in particolare:
   + utilizzare mapping Object-Relational tramite componenti Entity Bean
   + realizzare DAO tramite opportuni componenti Session Bean

-  inoltre, estendere la logica applicativa già descritta, aggiungendo un meccanismo di logging (potenzialmente) remoto:
   + ciascun metodo che preveda scritture su DB (aggiunta di nuovi componenti, produttori, ordini ecc…) deve inviare un messaggio JMS a un opportuno componente di logging
   + componente di logging (realizzato come Message Driven Bean) scrive su opportuno log (anche solo stdout) il messaggio ricevuto
