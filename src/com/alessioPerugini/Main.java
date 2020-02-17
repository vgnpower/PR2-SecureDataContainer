package com.alessioPerugini;

import com.alessioPerugini.Exceptions.CannotShareWithTheSameUser;
import com.alessioPerugini.Exceptions.UserAlreadyExistExcpetion;
import com.alessioPerugini.Exceptions.UserDataNotFoundExcpetion;
import java.util.Iterator;

import static java.lang.System.out;

public class Main {

    static SecureDataContainer mySecureData;

    public static void main(String[] args) {

        mySecureData = new MySecureDataContainer();
        Gen();
        out.println();
        out.println("/!\\ Seconda Implementazione /!\\");
        mySecureData = new MyHashSecureDataContainer();
        Gen();
    }

    private  static void Gen()
    {
        mySecureData.createUser("alessio", "ciao");
        mySecureData.createUser("simone", "prova");
        mySecureData.createUser("antonio", "test");

        //provo ad ottenere la dim della lista degli oggetti creati (deve ritornare 0 perchè vuota)
        out.println("Dimensione lista: " + mySecureData.getSize("alessio", "ciao"));
        mySecureData.put("alessio", "ciao", 123);
        mySecureData.put("alessio", "ciao", 777);
        mySecureData.put("alessio", "ciao", 888);
        mySecureData.put("simone", "prova", 567);
        mySecureData.share("simone", "prova", "alessio", 567);
        //Tento di inserire un dato già esistente che non è stato condiviso
        mySecureData.put("alessio", "ciao", 777);
        out.println("Dimensione lista: " + mySecureData.getSize("alessio", "ciao"));

        mySecureData.share("alessio", "ciao", "simone", 777);
        Print("alessio", "ciao");
        Print("simone", "prova");

        //Tento di ricondividere con Simone lo stesso dato
        mySecureData.share("alessio", "ciao", "simone", 777);
        Print("simone", "prova");
        Print("alessio", "ciao");
        mySecureData.remove("alessio", "ciao", 777);
        Print("alessio", "ciao");
        Print("simone", "prova");

        //Tento di copiare un dato non condiviso. Esito: non crea la copia poichè deve essere condivisono con 1 utente
        mySecureData.copy("alessio", "ciao", 123);
        mySecureData.share("alessio", "ciao", "simone", 123);
        //Copio un dato questa volta deve funzionare poichè precedentemente condiviso
        mySecureData.copy("alessio", "ciao", 123);

        Print("alessio", "ciao");
        Print("simone", "prova");
        //rimuovo la prima occorrenza di un dato duplicato
        mySecureData.remove("alessio", "ciao", 123);
        Print("simone", "prova");
        Print("alessio", "ciao");

        //###TEST ERRORI###
        out.println("##TEST ERRORI##");
        //Provo ad ottenere un dato inesistente deve ritornare null
        out.println(mySecureData.get("simone", "prova", 7776));
        try{
            mySecureData.createUser("alessio", "ciao");
        }catch (UserAlreadyExistExcpetion ecc){ out.println("/!\\ L'utente 'Alessio' è già stato creato");}

        //Tento di condividere un dato con me stesso
        try{
            mySecureData.share("alessio", "ciao", "alessio", 123);
        }catch (CannotShareWithTheSameUser ecc) { out.println("/!\\ Non puoi condividere il dato con te stesso");}

        //testo cosa succede se i parametri sono null
        try{
            mySecureData.createUser(null,null);
        }catch (NullPointerException e) {out.println("/!\\ parametri non validi");}
        try{
            mySecureData.share("alessio","ciao", "simone", null);
        }catch (NullPointerException e) {out.println("/!\\ parametri non validi");}
        //Tento di condividere un dato inesistente
        try{
            mySecureData.share("alessio","ciao", "simone", 123);
        }catch (UserDataNotFoundExcpetion e) {out.println("/!\\ dato inesistente");}
        mySecureData.put("alessio", "ciao", 123);
        Print("alessio", "ciao");
        Print("simone", "prova");
        //Provo a condividere più volte lo stesso oggetto con l'utente simone
        mySecureData.share("alessio","ciao", "simone", 123);
        mySecureData.share("alessio","ciao", "simone", 123);
        mySecureData.share("alessio","ciao", "simone", 123);
        Print("simone", "prova");

        //Provo ad eliminare un oggetto non presente
        try {
            mySecureData.remove("alessio", "ciao", 777);
        }catch (UserDataNotFoundExcpetion e){out.println("/!\\  Il dato da eliminare non è presente!");}
        //Provo a creare più copie dello stesso oggetto
        mySecureData.copy("alessio", "ciao", 123);
        mySecureData.copy("alessio", "ciao", 123);
        mySecureData.copy("alessio", "ciao", 123);
        mySecureData.copy("alessio", "ciao", 123);
        mySecureData.copy("alessio", "ciao", 123);

        Print("alessio", "ciao");

    }

    private static void Print(String username, String pw)
    {
        System.out.println("/-----" +username + "------\\");
        Iterator myIterator = mySecureData.getIterator(username, pw);
        int dimOwned = mySecureData.getSize(username, pw);
        out.println(" Owned:");
        for(int i = 0; i < dimOwned; i++) out.println("   " + myIterator.next());
        out.println();
        out.println(" SharedToMe:");

        while(myIterator.hasNext()) {
            out.println("   " + myIterator.next());
        }
        System.out.println("\\------------------/");
        System.out.println();
    }
}
