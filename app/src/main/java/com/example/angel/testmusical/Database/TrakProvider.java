package com.example.angel.testmusical.Database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = TrakProvider.AUTHORITY, database = TrakDatabase.class)
public class TrakProvider {

    public static final String AUTHORITY = "com.example.angel.testmusical.traksProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    private static final String pathNtraks = "traks/#";
    private static final String pathtraks = "traks";


    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }


    @TableEndpoint(table = TrakDatabase.traks)
    public static class Traks {

        @ContentUri(
                path = pathtraks,
                type = "vnd.android.cursor.dir/list",
                defaultSort = "RANDOM()")
        public static final Uri TRAKS = Uri.parse("content://" + AUTHORITY + "/" + pathtraks);


        @InexactContentUri(
                path = pathNtraks,
                name = "LIST_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = TrakContract.Id,
                limit = "?",
                pathSegment = 1)
        public static Uri withNtracks(int n) {
            return buildUri(pathtraks, String.valueOf(n));
        }


    }
}