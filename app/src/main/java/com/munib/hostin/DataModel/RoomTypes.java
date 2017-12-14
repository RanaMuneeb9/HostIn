package com.munib.hostin.DataModel;

import java.io.Serializable;

/**
 * Created by rana_ on 12/13/2017.
 */

public class RoomTypes implements Serializable {

    int type_id,seaters,base_price,price_with_mess;

    public RoomTypes(int type_id,int seaters,int price,int price_with_mess)
    {
        this.type_id=type_id;
        this.seaters=seaters;
        this.base_price=price;
        this.price_with_mess=price_with_mess;

    }
    public int getPrice() {
        return base_price;
    }

    public int getBase_price() {
        return base_price;
    }

    public int getPrice_with_mess() {
        return price_with_mess;
    }

    public int getSeaters() {
        return seaters;
    }

    public int getType_id() {
        return type_id;
    }
}
