package com.example.stevenmaccoun.morningmoment.db;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/25/16.
 */
public interface IRepository<TYPE, KEY> {

    ArrayList<TYPE> GetAll(KEY key);
    TYPE GetById(KEY key);
    boolean Save(TYPE obj);
    boolean Update(TYPE obj);
    boolean Delete(KEY key);

}
