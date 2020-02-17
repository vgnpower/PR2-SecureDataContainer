package com.alessioPerugini;

import com.alessioPerugini.Exceptions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface SecureDataContainer<E> {
    //Overview Contenitore di oggetti di tipo E dove è possibile accedere al tipo di dati solo se autorizzati
    //Typical Element: <{UserProfile_0, ..., UserProfile_i-1}>
        //Dove UserProfile: è l'insieme della n-upla che contiene username, password, e la lista di Userdata e quella
        //     con gli elementi di tipo E condivisi con lui <{<username, password, {<UserData_0, ..., UserData_i-1>}, {<objE_0, ..., objE_i-1>}>}>
        //Dove UserData: è l'insieme di coppie che contengono un elemento di tipo E ed una lista di utenti con cui è condivisio
        //     {<objE, {<username_0, ..., username_i-1>}>}

    //Crea l’identità un nuovo utente della collezione
    public void createUser(String Id, String passw) throws NullPointerException, UserAlreadyExistExcpetion;
    /**
     * REQUIRES: (Id && passw) != null
     * MODIFIES: this
     * EFFECTS: aggiunge alla collezione un Utente di nome id e password passw
     * THROWS: se Id || passw  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se esiste già un Id lancia un UserAlreadyExistExcpetion() (eccezione non disponibile in java unchecked)
     */

    //Restituisce il numero degli elementi di un utente presenti nella struttura
    public int getSize(String Owner, String passw) throws NullPointerException, UserProfileNotFoundExcpetion;
    /**
     * REQUIRES: (Owner && passw) != null
     * EFFECTS: restituisce il numero di elementi creati dall'utente owner.
     * THROWS: se Owner || passw  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *          (eccezione non disponibile in java unchecked)
     */

    //Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
    public boolean put(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion;
    /**
     * REQUIRES: (username && passw && data) != null
     * MODIFIES: this
     * EFFECTS: Aggiunge l'elemento data di proprietà dell'utente owner se va a buon fine ritorna true false altrimenti
     * THROWS: se Owner || passw || data  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     */

    //Ottiene una copia del valore del dato nella collezione se vengono rispettati i controlli di identità
    public E get(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion;
    /**
     * REQUIRES: (username && passw && data) != null
     * EFFECTS: restutuisce un riferimento al'oggetto data di proprietà di Owner se non esiste l'oggetto cerca il tipo E
     *          nella lista di condivisi se anche questo non viene trovato ritorna null
     * THROWS: se Owner || passw || data  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     */

    //Rimuove il dato nella collezione se vengono rispettati i controlli di identità
    public E remove(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion;
    /**
     * REQUIRES: (username && passw && data) != null
     * MODIFIES: this
     * EFFECTS: rimuove tutte le occorrenze di data di proprietà dell'utente owner restituisce data, se presente,
     *          altrimenti restituisce null
     * THROWS: se Owner || passw || data  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     */

    //Crea una copia del  dato nella collezione se vengono rispettati i controlli di identità
    public void copy(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion, UserDataNotFoundExcpetion;
    /**
     * REQUIRES: (username && passw && data) != null
     * MODIFIES: this
     * EFFECTS: crea un altra copia di data, e la inserisce nell'insieme dei dati creati da Owner solo se vi sono occorrenze
     *          di tipo E che hanno l'insieme delle condivisioni con cardinalità > 0
     * THROWS: se Owner || passw || data  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     */


    //Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException, UserProfileNotFoundExcpetion, CannotShareWithTheSameUser;
    /**
     * REQUIRES: (username && passw && data && Other) != null
     * MODIFIES: this
     * EFFECTS: condivide l'ogetto data con l'utente other.
     * THROWS: se Owner || passw || data || Other == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     *           se Owner == Other lancia una CannotShareWithTheSameUser() (eccezione non disponibile in java unchecked)
     *           se nelle ricerca dell'inseiem dei UserData non trova nulla lancia una UserDataNotFoundExcpetion() (eccezione
     *           non disponibile in java unchecked)
     */

    //restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, UserProfileNotFoundExcpetion, NoSuchElementException;
    /**
     * REQUIRES: (username && passw) != null
     * MODIFIES: this
     * EFFECTS: restituisce un Iterator su tutti i dati creati da Owner e su tutti queli condivisi con lui.
     * THROWS: se Owner || passw == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
     *          se non esiste un profilo che corrisponde ad Owner e passw lancia un UserAlreadyExistExcpetion()
     *           (eccezione non disponibile in java unchecked)
     */

}
