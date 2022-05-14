package com.example.demo;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


public class AuthorizationCodeUriExample {
    private static final String clientId = "9c06a16a8f1c4697b70dd07338df8622";
    private static final String clientSecret = "25f810718fcf4167ae4591922eba0810";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code/");

    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .scope("app-remote-control,playlist-read-collaborative,playlist-modify-public,playlist-read-private,playlist-modify-private")
//            .show_dialog(true)
            .build();

    @GetMapping
    public static String authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
        return uri.toString();
    }


    public static void main(String[] args) throws InterruptedException {
        authorizationCodeUri_Sync();
    }
}
