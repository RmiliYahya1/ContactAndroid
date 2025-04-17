package com.emsi.front_contact.data.datasource;


import com.emsi.front_contact.data.model.ContactDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ContactRemoteDatasource {

    private final ApiService apiService;

    public ContactRemoteDatasource(ApiService apiService){
        this.apiService=apiService;
    }

    public void ajouterContact(com.emsi.front_contact.data.model.ContactDto contact, Callback<Void> callback) {
        Call<Void> call = apiService.addContact(contact);
        call.enqueue(callback);
    }

    public void getAllContacts(Callback<List<ContactDto>> callback) {
        Call<List<ContactDto>> call = apiService.getContacts();
        call.enqueue(callback);
    }

    public void searchContacts(String contenu, Callback<List<ContactDto>> callback) {
        apiService.search(contenu).enqueue(callback);
    }

}
