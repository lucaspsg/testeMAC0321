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
    private static final String code = "AQBsqoKfflXJpeBBYGVZTvF48__JhbYR9afKbwPOqeact5GVSAoanE4M3DxVqllTasIeTl1VdO-cCRaGfZfvk2S0GSjxWl3yzUM5jpr74w9ordeJYNaCe-0_nP3JkYvc5Fkr4QjZ4eyXYgeJmuRA6b52A0E6cY-v4GZFdSTqwqRMdKvb79NaJNNy-qMKXZLgBqMuJ2YTejvoDmRyxgSWcvbXnb_rhi7hqCs3RHGg8fHeUl3tuYsr_ka9FZM1ShzgLiVf14kNQhRRKb8XVm9wb7YVmK1bKxontAdc7W1x2Vzkn195c-StwkDBdXXI8PTtiQs";

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
