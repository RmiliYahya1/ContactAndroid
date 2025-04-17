package com.emsi.front_contact;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.emsi.front_contact.data.datasource.ApiService;
import com.emsi.front_contact.data.datasource.ContactRemoteDatasource;
import com.emsi.front_contact.data.datasource.RetrofitClient;
import com.emsi.front_contact.data.repository.ContactRepositoryImpl;
import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;
import com.emsi.front_contact.domaine.usecases.AjouterContactUsecase;
import com.emsi.front_contact.domaine.usecases.ChargerContactUsecase;
import com.emsi.front_contact.domaine.usecases.RechercherContactUsecase;
import com.emsi.front_contact.presentation.adapter.ContactAdapter;
import com.emsi.front_contact.presentation.provider.ContactProvider;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 100;
    private ListView listView;
    private EditText editSearch;

    private ContactProvider contactProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listContact);
        editSearch = findViewById(R.id.editSearch);


        ApiService apiService = RetrofitClient.getApiService();
        ContactRemoteDatasource remoteDataSource = new ContactRemoteDatasource(apiService);
        ContactRepository repository = new ContactRepositoryImpl(remoteDataSource);
        AjouterContactUsecase ajouterContactUsecase = new AjouterContactUsecase(repository);
        ChargerContactUsecase chargerContactUsecase = new ChargerContactUsecase(repository);
        RechercherContactUsecase rechercherContactUsecase = new RechercherContactUsecase(repository);
        contactProvider = new ContactProvider(ajouterContactUsecase, chargerContactUsecase, rechercherContactUsecase);


        contactProvider.getContacts().observe(this, contacts -> {
            Log.d("MainActivity", "Nombre de contacts observés : " + contacts.size());
            ContactAdapter adapter = new ContactAdapter(this, contacts);
            listView.setAdapter(adapter);
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            ajouterContactsDepuisTelephone();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Recherche", "Recherche lancée pour : " + s.toString());
                contactProvider.rechercher(s.toString());
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = (Contact) parent.getItemAtPosition(position);

            new AlertDialog.Builder(this)
                    .setTitle("Action sur le contact")
                    .setMessage("Que voulez-vous faire avec " + contact.getNom() + " ?")
                    .setPositiveButton("Appeler", (dialog, which) -> {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + contact.getNum()));
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            startActivity(callIntent);
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.CALL_PHONE}, 200);
                        }
                    })
                    .setNegativeButton("Envoyer SMS", (dialog, which) -> {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setData(Uri.parse("sms:" + contact.getNum()));
                        startActivity(smsIntent);
                    })
                    .setNeutralButton("Annuler", null)
                    .show();
        });


    }

    private List<Contact> getContactFromPhone(){
        List<Contact> contactList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor != null){
            while (cursor.moveToNext()){
                String nom= cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new Contact(nom, numero));
            }
            cursor.close();
        }
        Log.d("test1", "Nombre de contacts récupérés : " + contactList.size());
        return contactList;
    }

    private void ajouterContactsDepuisTelephone() {
        List<Contact> contacts = getContactFromPhone();
        for (Contact c : contacts) {
            contactProvider.ajouterContact(c);
        }
        contactProvider.chargerContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée
                ajouterContactsDepuisTelephone();
            } else {
                // Permission refusée
                Toast.makeText(this, "Permission requise pour charger les contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}