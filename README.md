# PROGRAMMAZIONE	II	 - A.A.	201 8	 – 	19	

# Primo	Progetto-sessione	estiva-autunnale

Il	progetto	ha	l’obiettivo	di	applicare	i	concetti	e	le	tecniche	di	programmazione	Object-Oriented	esaminate	
durante	il	corso.	Lo	scopo	del	progetto	è	lo	sviluppo	di	un	componente	software	di	supporto	alla	gestione	di	
insiemi di	file.	

Si	 richiede	 di	 progettare,	 realizzare	 e	 documentare	 la	 collezione SecureFileContainer<E>.	
SecureFileContainer<E>	è	un	contenitore	di	 oggetti	di	tipo	E.	Intuitivamente	la collezione	si	comporta	come	
una	 specie	File Storage	 per	 la	 memorizzazione	 e	 condivisione	 di	file.	La	 collezione	 deve	 garantire	 un	
meccanismo	di	sicurezza	dei	file fornendo	un	proprio	meccanismo	di	gestione	delle	identità	degli	utenti.	
Ogni	file	ha	un	proprietario	che ha	diritto	a	leggere,	scrivere	e	fare	una	copia. La	collezione	deve,	inoltre,
fornire	 un	 meccanismo	 di	 controllo	 degli	 accessi	 che	 permette	 al	 proprietario	 del	file di	 eseguire	 una	
restrizione	selettiva	dell'accesso	ai	suoi	file inseriti	nella	collezione.	Alcuni	utenti	possono	essere	autorizzati	
dal	 proprietario	 ad	 accedere	ai	suoi	file	(in	 solo lettura	 o	 anche	 scrittura)	 mentre	 altri	non	 possono	
accedervi	senza	autorizzazione. Ma	l’utente	deve	accettare	la	condivisione previa	autenticazione.

Le	principali operazioni della	collezione sono	definite	descritte	di	seguito.

public interface SecureFileContainer<E>{

// Crea l’identità di un nuovo utente della collezione

public void createUser(String Id, String passw);

// Restituisce il numero dei file di un utente presenti nella

// collezione

public int getSize(String Owner, String passw);

// Inserisce il il file nella collezione

// se vengono rispettati i controlli di identità

public boolean put(String Owner, String passw, E file);

// Ottiene una copia del file nella collezione

// se vengono rispettati i controlli di identità

public E get(String Owner, String passw, E file);

// Rimuove il file dalla collezione

// se vengono rispettati i controlli di identità

public E remove(String Owner, String passw, E file);

// Crea una copia del file nella collezione

// se vengono rispettati i controlli di identità

public void copy(String Owner, String passw, E file);

// Condivide in lettura il file nella collezione con un altro utente

// se vengono rispettati i controlli di identità

public void shareR(String Owner, String passw, String Other, E file);


(^2)
// Condivide in lettura e scrittura il file nella collezione con un altro
// utente se vengono rispettati i controlli di identità
public void shareW(String Owner, String passw, String Other, E file);
// restituisce un iteratore (senza remove) che genera tutti i file
//dell’utente in ordine arbitrario
// se vengono rispettati i controlli di identità
public Iterator<E> getIterator(String Owner, String passw);
// ... altre operazione da definire a scelta
}

1. Si	definisca la	specifica	completa	come	interfaccia	Java del	tipo	di	dato	SecureFileContainer<E>	,	
    fornendo le	motivazioni delle	scelte	effettuate.
2. Si definisca l’implementazione	 del	 tipo	 di	 SecureFIleContainer<E>	 fornendo almeno	 due	
    implementazioni	 che	 utilizzano	 differenti	 strutture	 di	 supporto.	In	 entrambi	 i	 casi	 si devono
    comunque	descrivere	sia	la	funzione	di	astrazione	sia	l’invariante	di	rappresentazione.	 Si	discutano
    le	caratteristiche	delle	due	implementazioni.

**Parte	**

Per	valutare	 il	 comportamento	 dell’implementazioni proposte si	 realizzi una	batteria	di	test	in	grado	di	
operare,	senza	modifiche	specifiche,	su	entrambe	le	implementazioni	proposte.

**Modalità	di	consegna**

- Il	 progetto	 deve	 essere	 svolto	 e	 discusso	 col	 docente	 individualmente.	 Il	 confronto	 con	 colleghi	
    mirante	a	valutare soluzioni	alternative	durante	la	fase	di	progetto	è	incoraggiato.
- Il	progetto	deve	essere	costituito	da
    o i file	sorgente	contenenti	il	codice	sviluppato	e	la	batteria di	test,	ove tutto	il	codice	deve	
       essere	adeguatamente	commentato;
    o una	 relazione	 di	 massimo	due	 pagine,	 che	 descrive	 le	 principali	 scelte	 progettuali	 ed	
       eventuali	istruzioni	per	eseguire	il	codice.
- La	consegna	va	fatta	inviando	per	email	al	Prof.	Ferrari	o	alla Prof.	ssa	Levi con	oggetto	“[PR2A]	
    Consegna	Progetto	1”		e“[PR2B]	Consegna	Progetto	1”,	rispettivamente.
- Il	progetto	deve	essere	consegnato	entro	il	21	Giugno	2019	(per	il	primo	appello	estivo)	ed	il	11	
    Luglio	 2019	 (per	 il	 secondo	 appello	 estivo)	 mentre	 entro	 il	 25	 Agosto	 2019	 per	 la	 sessione	
    autunnale.
- **Altre	 informazioni	** Per	 quanto	 riguarda	 il	 progetto,	i docenti	 risponderanno	 solo	 a	 eventuali	
    domande	 riguardanti	 l’interpretazione	 del	 testo,	 e	 non	 commenteranno	 soluzioni	 parziali	 prima	
    della	consegna.


