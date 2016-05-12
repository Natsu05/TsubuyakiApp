package com.example.tomigaya.tsubuyakiapp;

import com.orm.SugarRecord;

/**
 * Created by tomigaya on 2016/05/11.
 */
public class Tsubuyaki extends SugarRecord {

    //id(連番)
    public long id;

    //コメント
    public String comment;

    public Tsubuyaki(){}

    public  Tsubuyaki(long id,String comment){
        this.id = id;
        this.comment = comment;
    }
    }

