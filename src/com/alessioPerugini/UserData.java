package com.alessioPerugini;

import java.util.Vector;

public class UserData<E> {
    //Overview: Descrive il dato di un utente ed i nomi con cui è condiviso
    //Typical Element: <data, {<username_0>,...,<username_n-1>}> dove data è di tipo E
    //AF(c) = <data,{c.sharedList.get(i)}> dove 0 <= i < c.sharedList.size()
    //IR(c) = data != null &&
    //        ForAll i. 0 <= i < c.sharedList.size() ==> c.sharedList.get(i) != (null && "")
    //        ForAll i,j / (0 <= i < j <c.sharedList.size()) == > c.sharedList.get(i) != c.sharedList.get(j)

    public E getData() {
        /**
         * EFFECTS: ritorna obj di tipo E
         */
        return data;
    }

    private E data;

    public Vector<String> getSharedList() {
        /**
         * EFFECTS: restituisce il vector sharedList
         */
        return sharedList;
    }

    private Vector<String> sharedList;

    public UserData(E data) throws NullPointerException
    {
        /**
         * REQUIRES: data != null
         * MODIFIES: this
         * EFFECTS: Se data != null assegna il nome alla variabile data ed inizializza il vettore delle condivisioni
         * THROWS: data == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if(data == null) throw new NullPointerException();

        this.data = data;
        sharedList = new Vector<>();
    }

    public E share(String other, E data)
    {
        /**
         * REQUIRES: (other && data) != null
         * MODIFIES: this
         * EFFECTS: Aggiunge il nome alla collezione se (other && data) != null
         * THROWS: (other && data) == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */

        if(data == null || other == null) throw new NullPointerException();

        if( this.data.equals(data) && !sharedList.contains(other))
        {
            this.sharedList.add(other);
            return data;
        }

        return  null;
    }

    @Override
    public boolean equals(Object object)
    {        /**
     * REQUIRES: (object) != null
     * EFFECTS: Controlla che le variabili globali dell'oggetto this siano uguali a quelli passate dall'oggetto
     *          per parametro chiamato "object"
     */
        boolean same = false;

        if (object != null && object instanceof UserData) {
            same = this.data == ((UserData) object).getData();
            same = (same) ? this.sharedList.equals(((UserData) object).getSharedList()) : false;
        }

        return same;
    }

}
