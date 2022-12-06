package com.example.gerenciadordeatividadescomplementares.Authentication;

import com.google.firebase.auth.FirebaseAuth;

public class Authentication {
    private FirebaseAuth auth;

    public Authentication() {
        auth = FirebaseAuth.getInstance();
    }


}
