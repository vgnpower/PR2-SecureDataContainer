package com.alessioPerugini;

import java.util.Vector;


public class UserProfile<E> {
    //Overview: tipo dei profili utente modificabili che hanno username, pw ed 2 collezioni
    //          per descrivere gli object di tipo E posseduti e per descrivere con chi sono condivisi
    //TypicalElement: <{<username, password, {<UserData_0, ..., UserData_i-1>}, {<objE_0, ..., objE_i-1>}>}>
    //dove UserData: {<objE, {<username_0, ..., username_i-1>}>}
    //AF(c) = <c.username, c.password,
    //                          {c.sharedList.get(i), c.sharedList.get(i).getSharedList(),
    //                           c.sharedList.get(i).share(other, data), c.sharedList.get(i).getData()},
    //                           {c.sharedToMe.get(j)}> dove 0 <= i < c.sharedList.size() && 0 <= j < c.sharedToMe.size()
    //                                                       && other != null && data != null
    //IR(c) = c.username != null && c.password != null && c.owned != null && c.sharedToMe != null &&
    //        ForAll i. 0 <= i < c.sharedToMe.size() ==> c.sharedToMe.get(i) != null &&
    //        ForAll i . (0 <= i < c.owned.size()) ==> c.owned.get(i) != null &&
    //        ForAll j. 0 <= j < c.owned.size() ==> c.owned.data != null && (for all k . 0 <= k < c.owned.get(j).sharedList().size() ==> c.owned.get(j).sharedList.get(k) != null
    //                                                                                                              && c.owned.get(j).sharedList.get(k) != c.owned.get(j).sharedList().get(j)) //Deve rispettare l'inv di sharedata
    //        ForAll i . (0 <= i <.owned.size()) ==> Exists j . 0 <= j < c.owned.get(i).sharedList.size() && c.owned.get(i).sharedList.get(j).equals(c.username)

    private String username;
    private String password;

    public Vector<UserData<E>> getOwned() {
        //EFFECTS ritorna il vettore di tipo UserData<E>
        return owned;
    }

    private Vector<UserData<E>> owned;

    public Vector<E> getSharedToMe() {
        return sharedToMe;
    }

    private Vector<E> sharedToMe;

    public UserProfile() {
        //MODIFIES this
        //EFFECTS inizializza i vettori owned e sharedToMe
        owned = new Vector<>();
        sharedToMe = new Vector<>();
    }

    public void AddCredential(String usern, String pw) throws NullPointerException {
        //MODIFIES: this
        //EFFECTS: se (usern && String pw) != null associa i valori di usernamme e password
        //          con quelli passati come parametro
        //THROWS: (usern && String pw) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)

        if(usern == null || pw == null) throw new NullPointerException();
        this.username = usern;
        this.password = pw;
    }

    public boolean AddUserData(E data)  throws NullPointerException{
        /**MODIFIES: this
         * EFFECTS: se data != null creo un ogetto UserData passandogli la variabile data e se il dato viene creato
         *          aggiunge al vettore owned l'ogetto UserData solo se non esistono altre occorrenze che hanno lo stesso
         *          vettore di condivisioni
         * THROWS: se data == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */

        if(data == null) throw  new NullPointerException();
        boolean errore = false;

        try {
            UserData usrData = new UserData(data);
            UserData<E> usrDat = getUserDataFromOwned(data);
            boolean test = owned.contains(usrData);
            if(usrDat == null || (usrDat != null && usrDat.getSharedList().size() > 0 && !test)) this.owned.add(usrData);
            else errore = true;
        } catch (Exception e) {
            errore = true;
        }

        return errore;
    }

    public boolean Auth(String usern, String pw) throws NullPointerException {
        /** EFFECTS: se (usern && pw) != null ritorna true se usern == this.username && pw == this.password, false else
         *  THROWS: (usern && pw) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (usern == null || pw == null) throw new NullPointerException();

        return (usern.equals(this.username) && pw.equals(this.password));
    }

    public void removeSharedToMeData(E data) throws NullPointerException{
        /** EFFECTS: se data != null rimuovo (se presente) l'ogetto data dal vettore sharedToMe
         *  THROWS: data == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        sharedToMe.remove(data);
    }

    public UserData<E> RemoveUserData(E data) throws NullPointerException{
        /** EFFECTS: se data != null rimuovo(se presente) l'ogetto data contenuto nella classe UserData presente nel vettore
         *           owned e ritorno il puntatore alla classe UserData, se non è presente ritorno null
         *  THROWS: data == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        UserData<E> usrData = getUserDataFromOwned(data);
        if(usrData != null) owned.remove(usrData);

        return (usrData != null) ? usrData : null;
    }

    public boolean sameNameAs(String Other) throws NullPointerException{
        /** EFFECTS: se Other != null e ritorno il risultato di this.username.equals(Other) true se uguale false else
         *  THROWS: Other == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (Other == null) throw new NullPointerException();

        return (this.username.equals(Other));
    }

    public UserData<E> AddSharedElement(String other, E data) throws NullPointerException {
        /** EFFECTS: se (Other, data) != null cerco nella lista owned la presenda di data se c'è ritorno il puntatore
         *           alla classe UserData contenente quell'oggetto altrimenti null
         *  THROWS: (Other, data) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        UserData<E> usrData = getUserDataFromOwned(data);

        return usrData;
    }

    public void AddSharedToMe(E data) {
        /** EFFECTS: se (data) != null aggiungo al vettore sharedToMe l'object data
         *  THROWS: (data) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        this.sharedToMe.add(data);
    }

    public int getSizeOwned() {
        //EFFECTS: ritorna la cardinalità del vettore owned
        return this.owned.size();
    }

    public UserData<E> getUserDataFromOwned(E data)throws NullPointerException {
        /** EFFECTS: se (data) != null cerco nella lista owned un ogetto di tipo UserData e controllo che l'object E
         *           interno sia uguale a data se si ritorno UserData corrispondente all'ogetto uguale a data altrimenti
         *           ritorno null
         *  THROWS: (data) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        int i = 0;
        boolean found = false;
        UserData<E> elm = null;
        //Cerco il dato
        while (i < getSizeOwned() && !found) {
            elm = owned.elementAt(i);

            found = elm.getData().equals(data);
            i++;
        }

        return (found) ? elm : null;
    }

    public E getUserDataFromShaaredToMe(E data) throws NullPointerException{
        /** EFFECTS: se (data) != null ricerco nella lista sharedToMe un ogetto di tipo E e ritorno la prima occorrenza
         *           uguale a data, null altrimenti
         *  THROWS: (data) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (data == null) throw new NullPointerException();

        int index = sharedToMe.indexOf(data);

        return (index != -1) ? sharedToMe.get(index) : null;
    }
}
