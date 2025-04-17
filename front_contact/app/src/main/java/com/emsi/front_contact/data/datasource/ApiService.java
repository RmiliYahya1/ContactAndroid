package com.emsi.front_contact.data.datasource;



import com.emsi.front_contact.data.model.ContactDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/contact/all")
    Call<List<ContactDto>> getContacts();
    @POST("/contact/ajouter")
    Call<Void> addContact(@Body ContactDto contact);

    @GET("/contact/search")
    Call<List<ContactDto>> search(@Query("contenu") String contenu);
}
