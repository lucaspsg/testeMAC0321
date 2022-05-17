package com.example.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.hc.core5.http.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.playlists.*;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
@RestController
public class GerenciadorMusical {
    AuthController authController = new AuthController();
    SpotifyApi spotifyApi = AuthController.spotifyApi;
    String userId= "";

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorMusical.class, args);
    }
    @CrossOrigin
    @GetMapping("home")
    public String getCurrentUsersProfile_Sync() {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                .build();
        System.out.println("banana");
        try {
            final User user = getCurrentUsersProfileRequest.execute();
            userId = user.getId();
            System.out.println(userId);
            System.out.println("Display name: " + user.getDisplayName());
            return userId;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @GetMapping("playlists")
    @CrossOrigin
    public PlaylistSimplified[] getListOfCurrentUsersPlaylists_Sync() {
        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
//          .limit(10)
//          .offset(0)
                .build();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
            return playlistSimplifiedPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new PlaylistSimplified[0];
    }

    @GetMapping("playlist-items/{id}")
    @CrossOrigin
    public PlaylistTrack[] getPlaylistsItems_Sync(@PathVariable("id") String playlistId) {
        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
//          .fields("description")
//          .limit(10)
//          .offset(0)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
                .build();
        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            return playlistTrackPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new PlaylistTrack[0];
    }
    @CrossOrigin
    @GetMapping("create-playlist/{name}")
    public Playlist createPlaylist_Sync(@PathVariable("name") String name) {
        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, name)
//          .collaborative(false)
//          .public_(false)
//          .description("Amazing music.")
                .build();

        try {
            final Playlist playlist = createPlaylistRequest.execute();

            System.out.println("Name: " + playlist.getName());
            return playlist;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    @CrossOrigin(origins="http://localhost:3000/home")
    @GetMapping("add-playlist-items/{playlistId}/{songUri}")

    public void addItemsToPlaylist_Sync(@PathVariable("playlistId") String playlistId, @PathVariable("songUri") String songUris) {
        String[] array = songUris.split(",");
        AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi
                .addItemsToPlaylist(playlistId, array)
//          .position(0)
                .build();

        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();

            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("search/{name}")
    public Track[] searchTracks_Sync(@PathVariable("name") String name) {

        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(name)
//          .market(CountryCode.SE)
          .limit(10)
//          .offset(0)
//          .includeExternal("audio")
                .build();

        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();

            System.out.println("Total: " + trackPaging.getTotal());
            return trackPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    @GetMapping("remove-playlist-items/{playlistId}/{songUri}")
    public void removeItemsFromPlaylist_Sync(@PathVariable("playlistId") String playlistId, @PathVariable("songUri") String songUri) {
        JsonArray  track = JsonParser.parseString("[{\"uri\":\"" + songUri + "\"}]").getAsJsonArray();
        RemoveItemsFromPlaylistRequest removeItemsFromPlaylistRequest = spotifyApi
                .removeItemsFromPlaylist(playlistId, track)
//          .position(0)
                .build();

        try {
            final SnapshotResult snapshotResult = removeItemsFromPlaylistRequest.execute();

            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
