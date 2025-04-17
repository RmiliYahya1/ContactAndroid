package com.emsi.front_contact.data.repository;

import com.emsi.front_contact.data.datasource.ContactRemoteDatasource;
import com.emsi.front_contact.data.model.ContactDto;
import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepositoryImpl implements ContactRepository {

    private final ContactRemoteDatasource remoteDatasource;

    public ContactRepositoryImpl(ContactRemoteDatasource remoteDatasource) {
        this.remoteDatasource = remoteDatasource;
    }

    @Override
    public void ajouterContact(Contact contact) {
        ContactDto contactDto = new ContactDto(contact.getNom(), contact.getNum());
        remoteDatasource.ajouterContact(contactDto, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getContacts(RepositoryCallback<List<Contact>> callback) {
        remoteDatasource.getAllContacts(new Callback<List<ContactDto>>() {
            @Override
            public void onResponse(Call<List<ContactDto>> call, Response<List<ContactDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Contact> contactList = new ArrayList<>();
                    for (ContactDto contactDto : response.body()) {
                        contactList.add(new Contact(contactDto.getNom(), contactDto.getNumero()));
                    }
                    callback.onSuccess(contactList);
                } else {
                    callback.onError(String.valueOf(new Exception("Erreur de chargement")));
                }
            }

            @Override
            public void onFailure(Call<List<ContactDto>> call, Throwable t) {
                callback.onError(String.valueOf(t));
            }
        });

    }

    @Override
    public void search(String contenu, RepositoryCallback<List<Contact>> callback) {
        remoteDatasource.searchContacts(contenu, new Callback<List<ContactDto>>() {
            @Override
            public void onResponse(Call<List<ContactDto>> call, Response<List<ContactDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Contact> results = new ArrayList<>();
                    for (ContactDto dto : response.body()) {
                        results.add(new Contact(dto.getNom(), dto.getNumero()));
                    }
                    callback.onSuccess(results);
                } else {
                    callback.onError("Réponse vide ou erreur");
                }
            }

            @Override
            public void onFailure(Call<List<ContactDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
