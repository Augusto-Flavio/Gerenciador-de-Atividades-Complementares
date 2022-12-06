package com.example.gerenciadordeatividadescomplementares.Storage;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Storage {
    FirebaseStorage storage;
    StorageReference storageRef;
    public Storage() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

}
