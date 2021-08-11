package com.example.sakatap.repository;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.Models.ImageURL;
import com.example.sakatap.Models.Souvenir;
import com.example.sakatap.Models.UserSouvenir;
import com.example.sakatap.Models.Users;
import com.example.sakatap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class repository {
    private Application application;
    private MutableLiveData<Bangunan> data;
    private MutableLiveData<Integer> datapoin;
    private MutableLiveData<Users> datauser;
    private MutableLiveData<Uri> Imageurl;
    private MutableLiveData<Uri> audiourl;
    private MutableLiveData<List<String>> listimageurl;
    private MutableLiveData<Boolean> callback;
    private MutableLiveData<Boolean> check;
    private MutableLiveData<Integer> audioposition;
    private MutableLiveData<List<Souvenir>> souvenir;
    private MutableLiveData<UserSouvenir> usersouvenir;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private MediaPlayer mediaPlayer;


    private Boolean hasil = null;
    private Boolean verifikasi;

    public repository(Application application){
        this.application = application;
        data = new MutableLiveData<>();
        datapoin = new MutableLiveData<>();
        datauser = new MutableLiveData<>();
        Imageurl = new MutableLiveData<>();
        callback = new MutableLiveData<>();
        check = new MutableLiveData<>();
        audioposition = new MutableLiveData<>(-1);
        souvenir = new MutableLiveData<>();
        usersouvenir = new MutableLiveData<>();
        listimageurl = new MutableLiveData<>();
        audiourl = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public void ReadNarasi(String documentid) {
        db.collection("Bangunan").document(documentid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Bangunan hasil = documentSnapshot.toObject(Bangunan.class);
                data.postValue(hasil);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.d("NARASI", "Error mengambil narasi", e);
            }
        });

    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult.getUser() != null) {
                    callback.postValue(true);
                }
                else {
                    callback.postValue(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET", "Error login", e);
                callback.postValue(false);
            }
        });

    }

    public void signup(String email, String password, String username, String nama, String kelamin, String TTL) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult.getUser() != null) {
                    Users user = new Users(username, email, nama, kelamin, TTL, 0);
                    adduserdata(user);
                    UserSouvenir userSouvenir = new UserSouvenir(false, false, false);
                    addUserSouvenir(userSouvenir);
                    callback.postValue(true);
                }
                else {
                    callback.postValue(false);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET", "Error membuat akun", e);
                callback.postValue(false);
            }
        });

    }

    public void check(String email, String password, String username, String nama, String kelamin, String TTL) {
        if(email == "" && password == "" && username == "" && nama == "" && kelamin == "" && TTL == "" && password.length() < 8) {
            check.postValue(false);
        }
        else if(email != "" && password != "" && username != "" && nama != "" && kelamin != "" && TTL != ""  && password.length() > 8) {
            signup(email, password, username, nama, kelamin, TTL);
        }

    }

    public void lupapassword(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    callback.postValue(true);
                }

                else {
                    callback.postValue(false);
                    Log.d("GET", "Pengiriman link reset password", task.getException());
                }
            }
        });
    }

    public void adduserdata(Users user) {
        FirebaseUser users = auth.getCurrentUser();
        db.collection("Users").document(users.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("USERDATA", "Penambahan data users berhasil");
                }

                else {
                    Log.d("USERDATA", "Error menambahkan data users", task.getException());
                }
            }
        });
    }

    public void getuserdata() {
        FirebaseUser user = auth.getCurrentUser();
        db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users hasil = documentSnapshot.toObject(Users.class);
                datauser.postValue(hasil);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET", "Error mengambil data users", e);
            }
        });
    }

    public void updatepassword(String oldpass, String newpass ) {
        FirebaseUser users = auth.getCurrentUser();
        String email = users.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);
        users.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    users.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(application, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(application, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(application, "Autentifikasi gagal", Toast.LENGTH_SHORT).show();
                    Log.d("PASSWORD", "Error Autentifikasi", task.getException());
                }
            }
        });

    }

    public void sendemailverification() {
        FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                }

                else {
                    Toast.makeText(application, "Gagal mengirim link verifikasi", Toast.LENGTH_SHORT).show();
                    Log.d("GET", "mengirim link verifikasi", task.getException());
                }
            }
        });
    }

    public void checkemailverified() {
        FirebaseUser user = auth.getCurrentUser();
        if(user.isEmailVerified()) {
            callback.postValue(false);
        }

        else {
            callback.postValue(true);
            sendemailverification();
        }

    }

    public void logout() {
        auth.signOut();
    }

    public void addpoin(int Poin, int mode) {
        FirebaseUser user = auth.getCurrentUser();
        db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);
                int jumlah = users.getPoin();
                if(mode == 1){
                    db.collection("Users").document(user.getUid()).update("poin", jumlah + Poin);
                }
                else if(mode == 0) {
                    db.collection("Users").document(user.getUid()).update("poin", jumlah - Poin);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("SET", "Error menambahkan poin", e);
            }
        });


    }

    public void getpoin() {
        FirebaseUser user = auth.getCurrentUser();
        db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);
                datapoin.postValue(users.getPoin());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET", "Error pengambilan poin", e);
            }
        });
    }

    public void getsouvenir() {
        db.collection("Souvenir").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Souvenir> list = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Souvenir souvenir = document.toObject(Souvenir.class);
                        list.add(souvenir);
                    }
                    souvenir.postValue(list);
                }
                else {
                    Log.d("SOUVENIR", "Error pengambilan Souvenir", task.getException());
                }
            }
        });
    }

    public void addUserSouvenir(UserSouvenir userSouvenir) {
        FirebaseUser users = auth.getCurrentUser();
        db.collection("UserSouvenir").document(users.getUid()).set(userSouvenir).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("USERSOUVENIR", "Penambahan data users souvenir berhasil");
                }

                else {
                    Log.d("USERSOUVENIR", "Error menambahkan data users souvenir", task.getException());
                }
            }
        });
    }

    public void updateUserSouvenir(String souvenirid) {
        FirebaseUser users = auth.getCurrentUser();
        db.collection("UserSouvenir").document(users.getUid()).update(souvenirid, true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("USERSOUVENIR", "Update data users souvenir berhasil");
                    callback.postValue(true);
                }

                else {
                    Log.d("USERSOUVENIR", "Error saat update users souvenir", task.getException());
                    callback.postValue(false);
                }
            }
        });
    }

    public void getusersouvenir() {
        FirebaseUser users = auth.getCurrentUser();
        db.collection("UserSouvenir").document(users.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserSouvenir hasil = documentSnapshot.toObject(UserSouvenir.class);
                usersouvenir.postValue(hasil);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("USERSOUVENIR", "Error mendapatkan users souvenir", e);
            }
        });
    }

    public void ambil(String Souvenirid, int harga) {
        FirebaseUser user = auth.getCurrentUser();
        db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);
                int Poin = users.getPoin();
                if(Poin >= harga) {
                    updateUserSouvenir(Souvenirid);
                    addpoin(harga, 0);
                }
                else {
                    Toast.makeText(application, "Poin tidak mencukupi", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GET", "Error pengambilan poin", e);
            }
        });


    }

    public void getimageurl(String name) {
        storage.getReference().child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Imageurl.postValue(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("IMAGEURL", "Error Mendapatkan image URL", e);
            }
        });
    }

    public void getlistimageurl(String path) {
        db.collection(path).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        ImageURL img = document.toObject(ImageURL.class);
                        list.add(img.getUrl());
                    }
                    listimageurl.postValue(list);
                }
                else {
                    Log.d("IMAGEURL", "Error Mendapatkan List image URL", task.getException());
                }
            }
        });
    }

    public void getAudiourluri(String name) {
        storage.getReference().child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                audiourl.postValue(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AUDIOURL", "Error Mendapatkan audio URL", e);
            }
        });
    }

    public MutableLiveData<Integer> getDatapoin() {
        return datapoin;
    }

    public MutableLiveData<Users> getDatauser() {
        return datauser;
    }

    public MutableLiveData<Bangunan> getData() {
        return data;
    }

    public MutableLiveData<Uri> getImageurl() {
        return Imageurl;
    }

    public MutableLiveData<Boolean> getCallback() {
        return callback;
    }

    public MutableLiveData<Integer> getAudioposition() {
        return audioposition;
    }

    public MutableLiveData<List<Souvenir>> getSouvenir() {
        return souvenir;
    }

    public MutableLiveData<UserSouvenir> getUsersouvenir() {
        return usersouvenir;
    }

    public MutableLiveData<Boolean> getCheck() {
        return check;
    }

    public MutableLiveData<List<String>> getListimageurl() {
        return listimageurl;
    }

    public MutableLiveData<Uri> getAudiourl() {
        return audiourl;
    }
}
