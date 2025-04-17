package com.emsi.front_contact.presentation.provider;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;
import com.emsi.front_contact.domaine.usecases.AjouterContactUsecase;
import com.emsi.front_contact.domaine.usecases.ChargerContactUsecase;
import com.emsi.front_contact.domaine.usecases.RechercherContactUsecase;

import java.util.List;

public class ContactProvider {
    private final AjouterContactUsecase ajouterContactUsecase;
    private final ChargerContactUsecase chargerContactUsecase;
    private final RechercherContactUsecase rechercherContactUsecase;
    private final MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public ContactProvider(AjouterContactUsecase ajouterContactUsecase, ChargerContactUsecase chargerContactUsecase, RechercherContactUsecase rechercherContactUsecase) {
        this.ajouterContactUsecase = ajouterContactUsecase;
        this.chargerContactUsecase = chargerContactUsecase;
        this.rechercherContactUsecase = rechercherContactUsecase;
    }

    public LiveData<List<Contact>> getContacts() {
        return contactsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void ajouterContact(Contact contact) {
        Log.d("ContactProvider", "Ajout du contact : " + contact.getNom());
        ajouterContactUsecase.execute(contact);
    }

    public void chargerContacts() {
        isLoading.setValue(true);
        chargerContactUsecase.execute(new ContactRepository.RepositoryCallback<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> result) {
                Log.d("ContactProvider", "Nombre de contacts chargés : " + result.size());
                contactsLiveData.postValue(result);
                isLoading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ContactProvider", "Erreur lors du chargement des contacts : " + errorMessage);
                isLoading.postValue(false);
            }
        });
    }

    public void rechercher(String contenu) {
        isLoading.setValue(true);
        rechercherContactUsecase.execute(contenu, new ContactRepository.RepositoryCallback<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> result) {
                Log.d("ContactProvider", "Résultats recherche : " + result.size());
                contactsLiveData.postValue(result);
                isLoading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ContactProvider", "Erreur recherche : " + errorMessage);
                isLoading.postValue(false);
            }
        });
    }
}
