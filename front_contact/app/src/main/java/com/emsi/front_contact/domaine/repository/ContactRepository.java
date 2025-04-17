package com.emsi.front_contact.domaine.repository;

import com.emsi.front_contact.domaine.entities.Contact;

import java.util.List;

public interface ContactRepository {
    void ajouterContact(Contact contact);
    void getContacts(RepositoryCallback<List<Contact>> callback);

    void search(String contenu, RepositoryCallback<List<Contact>> callback);


    interface RepositoryCallback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
}
