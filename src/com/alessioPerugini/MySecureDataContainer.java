package com.alessioPerugini;

import com.alessioPerugini.Exceptions.*;

import java.util.*;


public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //AF(c) = c.lsDati.get(0), ... , c.lsDati.get(i-1) dove 0 <= i < c.lsDati.size()
    /**IR(c) = c.lsDati != null &&
     *         ForAll i . 0 <= i < c.lsDati.size() ==> (UP.username != null &&
     *                                                   UP.password != null &&
     *                                                   (ForAll j . 0 <= j < UP.sharedToMe.size() ==> UP.sharedToMe.get(j) != null) &&
     *                                                   (ForAll k . 0 <= k < UP.owned.size() ==> UP.owned.data != null && (ForAll l . 0 <= l < UP.owned.get(k).sharedList.size() ==> UP.owned.get(k).sharedList.get(l) != null &&
     *                                                                                                                                                          UP.owned.get(k).sharedList.get(l) != UP.owned.get(k).sharedList.get(k))
     *                                                   )&&                                                                                        //Un user profile deve rispettare l'IR di UserData e UserProfile
     *          ForAll i,j . ((i,j = 0, c.lsDati.size()-1) && (i != j) ==> !UP.username.equals(c.lsDati.get(j).username)                            //Utenti tutti distinti
     *          ForAll i . (0 <= i < c.lsDati.size()) ==> ForAll j . (0 <= j < UP.owned.size()) ==> ForAll k . (0 <= k < UP.owned.get(j).size()) ==>
     *                 Exists s . (0 <= s < c.lsDati.size()) && c.lsDati.get(s).username.equals(UP.owned.get(j).sharedList.get(k)) &&               //i nomi contenuti sharedList di UserDati contengono username di utenti registrati
     *          ForAll i (0 <= i < c.lsDati.get(i) ==> ForAll j . (0 <= j < UP.owned.size()) ==> ForAll k . (0 <= k < UP.owned.get(j).sharedList().size()) ==>
     *                  Exists s . (0 <= s < c.lsDati.size()) && Exists t . (0 <= t < UP.owned.size()) && Exists u . (0 <= u < UP.owned.sharedList.size()) &&
     *                          UP.owned.get(j).data.equals(c.lsDati.get(s).owned.get(t).data) &&
     *                          UP.owned.get(j).sharedList.get(k).equals(c.lsDati.get(s).owned.get(t).sharedList.get(u))                        //Quando un dato viene condiviso deve essere presente in tutte le liste
     *                                                   dove UP = c.lsDati.get(i)
    */
    private Vector<UserProfile> lsDati;

    public MySecureDataContainer() {
        lsDati = new Vector<>();
    }

    @Override
    public void createUser(String Id, String passw) throws NullPointerException, UserAlreadyExistExcpetion {
        if (Id == null || passw == null) throw new NullPointerException();

        UserProfile profile = new UserProfile();
        profile.AddCredential(Id, passw);

        if (getProfile(Id, passw, false) != null) throw new UserAlreadyExistExcpetion();
        lsDati.add(profile);
    }

    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, UserProfileNotFoundExcpetion {
        if (Owner == null || passw == null) throw new NullPointerException();

        UserProfile profile = getProfile(Owner, passw, true);

        if (profile == null) throw new UserProfileNotFoundExcpetion();

        return profile.getSizeOwned();
    }

    private UserProfile getProfile(String username, String pw, Boolean usePw) throws NullPointerException {
        /**
         * REQUIRES: (username && passw && usePw) != null
         * MODIFIES: this
         * EFFECTS: se (Owner && passw && usePw) != null e se usePw == false cerca nell'insieme se esiste un ojbect UserProfile
         *          che contiene l'username uguale al parametro username e ne restituisce il puntatore della classe UserProfile
         *          Se usePw == true cerca nell'insieme se esiste un object UserProfile che contine sia username che password uguale a username e pw
         *          (passati per parametro) e restituisce il puntatore della clase UserProfile.
         *          In entrambi i casi se l'utente cercato non viene trovato ritorno null.
         *
         * THROWS: se Owner || passw  == null lancia una NullPointerException() (eccezione disponibile in java unchecked)
         */
        if (username == null || pw == null) throw new NullPointerException();

        int i = 0;
        boolean found = false;
        UserProfile profile = null;

        while (i < lsDati.size() && !found) {
            profile = lsDati.get(i);
            found = (usePw) ? profile.Auth(username, pw) : profile.sameNameAs(username);
            i++;
        }

        return (found) ? profile : null;
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion {
        if (Owner == null || passw == null || data == null) throw new NullPointerException();

        UserProfile profile = getProfile(Owner, passw, true);

        if (profile == null) throw new UserProfileNotFoundExcpetion();

        return profile.AddUserData(data);
    }

    @Override
    public E get(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion {
        if (Owner == null || passw == null) throw new NullPointerException();

        UserProfile profile = getProfile(Owner, passw, true);
        if (profile == null) throw new UserProfileNotFoundExcpetion();

        UserData<E> usrData = profile.getUserDataFromOwned(data);
        E elm = (usrData != null) ? usrData.getData() : null;

        if (elm == null) return elm;

        try {
            elm =  (E)profile.getUserDataFromShaaredToMe(data);
        } catch (Exception Ecc) {
        } finally {
            return elm;
        }
    }

    @Override
    public E remove(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion,UserDataNotFoundExcpetion {
        if (Owner == null || passw == null || data == null) throw new NullPointerException();

        UserProfile profile = getProfile(Owner, passw, true);
        if (profile == null) throw new UserProfileNotFoundExcpetion();

        UserData<E> usrData = profile.RemoveUserData(data);
        if(usrData == null) throw new UserDataNotFoundExcpetion();
        Vector<String> lsSharedUsers = usrData.getSharedList();

        for (int i = 0; i < usrData.getSharedList().size(); i++) {
            String name = lsSharedUsers.get(i);

            UserProfile<E> otherProfile = getProfile(name, passw, false);
            if (otherProfile != null) otherProfile.removeSharedToMeData(data);
        }

        return usrData.getData();
    }

    @Override
    public void copy(String Owner, String passw, E data) throws NullPointerException, UserProfileNotFoundExcpetion, UserDataNotFoundExcpetion {
        if (Owner == null || passw == null || data == null) throw new NullPointerException();

        UserProfile profile = getProfile(Owner, passw, true);
        if (profile == null) throw new UserProfileNotFoundExcpetion();

        UserData<E> usrData = profile.getUserDataFromOwned(data);
        if (usrData == null) throw new UserDataNotFoundExcpetion();

        profile.AddUserData(/*data*/usrData.getData());
    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException, UserProfileNotFoundExcpetion, CannotShareWithTheSameUser, UserDataNotFoundExcpetion {
        if (Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.equals(Other)) throw new CannotShareWithTheSameUser();

        UserProfile<E> profilo = getProfile(Owner, passw, true);
        if (profilo == null) throw new UserProfileNotFoundExcpetion();

        UserData<E> shareResult = profilo.AddSharedElement(Other, data);
        if (shareResult == null) throw new UserDataNotFoundExcpetion();
        else {
            if (!shareResult.getSharedList().contains(Other)) {
                shareResult.share(Other, data);
                UserProfile<E> otherProfile = getProfile(Other, passw, false);
                if (otherProfile != null) otherProfile.AddSharedToMe(shareResult.getData());
            }
        }

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, UserProfileNotFoundExcpetion, NoSuchElementException {
        if (Owner == null || passw == null) throw new NullPointerException();

        UserProfile<E> profilo = getProfile(Owner, passw, true);
        if (profilo == null) throw new UserProfileNotFoundExcpetion();

        Iterator<E> iterator = new Iterator<E>() {
            private int length = 0;
            private int sizeOwned = profilo.getOwned().size();
            private int sizeShared = profilo.getSharedToMe().size();

            @Override
            public boolean hasNext() {
                return length < sizeOwned + sizeShared;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                else return (length < sizeOwned) ? profilo.getOwned().get(length++).getData() : profilo.getSharedToMe().get((length++)-sizeOwned);
            }
        };

        return iterator;
    }
}
