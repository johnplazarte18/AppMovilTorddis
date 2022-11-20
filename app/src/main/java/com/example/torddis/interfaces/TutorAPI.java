package com.example.torddis.interfaces;


import com.example.torddis.models.Tutor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TutorAPI {
    @Headers("Content-Type: application/json")
    @POST("persona/autenticacion/")
    Call <Tutor> iniciarSesion(@Body String body);
}
