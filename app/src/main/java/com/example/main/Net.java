package com.example.main;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net {
    private static Net ourInstance = new Net();
    public static Net getInstance(){
        return ourInstance;
    }
    private Net(){}
    private Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://mjckjs.gabia.io/whispering/php/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api;

    public API getApi() {
        if(api==null){
            api=retrofit.create(API.class);
        }
        return api;
    }
}
