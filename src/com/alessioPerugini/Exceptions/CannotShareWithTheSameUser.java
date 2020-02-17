package com.alessioPerugini.Exceptions;

public class CannotShareWithTheSameUser extends  RuntimeException {
    public CannotShareWithTheSameUser()
    {

    }
    public CannotShareWithTheSameUser(String ecc)
    {
        super(ecc);
    }

}
