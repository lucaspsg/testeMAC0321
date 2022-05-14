package com.example.demo;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetCurrentUsersProfileExample {
    private static final String accessToken = "AQCsKG8xdJfPmQ8l6_ffGi9fGqHza0NXz9Ruw98v8iLqQ4TUpJMM7twyo0B2yOI2WwQ9vLvUh6Q6GRflhfICDepn3e6DRGZcGW0LFkJ9zBzSpHccH638eN2ve93ggi-GQfbCO0_JgolpLIxFmY-SLPsQ-39C8nE5D6s2AswqP2hLdpMGhErf_Y4cVRExikOVddf7zW1a3XNW8Ajy4WW-dBjNMkPHQcYKnPVqHPuSHzTlpW1xV72kZ2aAL6ytuax7uA9v2OIwj00PMqlKmC9uCKHXQdu-9uZ2Qd3ZUOtjwwsHHuIVDmLa4aFI7SXE_9f19i2y5ZXFVqT-P5ce1xGpiQ";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
            .build();

    public static void getCurrentUsersProfile_Sync() {
        try {
            final User user = getCurrentUsersProfileRequest.execute();

            System.out.println("Display name: " + user.getDisplayName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getCurrentUsersProfile_Async() {
        try {
            final CompletableFuture<User> userFuture = getCurrentUsersProfileRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final User user = userFuture.join();

            System.out.println("Display name: " + user.getDisplayName());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getCurrentUsersProfile_Sync();
        getCurrentUsersProfile_Async();
    }
}
