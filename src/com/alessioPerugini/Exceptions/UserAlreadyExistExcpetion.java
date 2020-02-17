package com.alessioPerugini.Exceptions;

public class UserAlreadyExistExcpetion extends  RuntimeException {
    public UserAlreadyExistExcpetion()
    {

    }
    public UserAlreadyExistExcpetion(String ecc)
    {
        super(ecc);
    }

}
