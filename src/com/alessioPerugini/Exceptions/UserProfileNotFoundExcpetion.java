package com.alessioPerugini.Exceptions;

public class UserProfileNotFoundExcpetion extends  RuntimeException {
    public UserProfileNotFoundExcpetion()
    {

    }
    public UserProfileNotFoundExcpetion(String ecc)
    {
        super(ecc);
    }

}
