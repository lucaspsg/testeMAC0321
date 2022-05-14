package com.example.demo;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCodeExample {
    private static final String clientId = "9c06a16a8f1c4697b70dd07338df8622";
    private static final String clientSecret = "25f810718fcf4167ae4591922eba0810";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080");
    private static final String code = "AQAd6rgtJhP_V7g1CTEDrpCluLtKn0KsYRI3d7nkVIXFED1X7D2_LUvJI8A32tEOB4EjmvNLdId86ivYpbKZXNqZb35kCvB4R8GiHoUf_wzMMIiaepX6S75OgWO-9tH0UqcFXx-KJ7iH_dk23mOGkicUoCt3r9K0QjKxYHOlTrWulG_wjk_ifNJd826IffkYuMA_OTE87CJhxLwhokHZlG4vLDarKiWkcL-WLhp-F1nBgPFa9EnXOtjLPHzqSZKhhN2Ja5sTGOKo62a9Ahm7A4tIJ1QTrDgrB0qEhncCZBP5I3D1oFlz9PrqrNcfVvqbGYs";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
            .build();

    public static void authorizationCode_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void authorizationCode_Async() {
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        authorizationCode_Sync();
        authorizationCode_Async();

    }
}
