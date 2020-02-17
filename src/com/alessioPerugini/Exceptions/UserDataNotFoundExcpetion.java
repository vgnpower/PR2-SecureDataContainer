package com.alessioPerugini.Exceptions;

public class UserDataNotFoundExcpetion extends  RuntimeException {
    public UserDataNotFoundExcpetion()
    {

    }
    public UserDataNotFoundExcpetion(String ecc)
    {
        super(ecc);
    }

}
