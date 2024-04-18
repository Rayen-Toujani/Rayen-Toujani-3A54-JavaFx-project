package com.visita.controllers;
import java.util.List;
import java.util.Arrays;


public class CommentFilter {

    private static final List<String> badWords = Arrays.asList(
            "arse",
            "arsehead",
            "arsehole",
            "ass",
            "asshole",
            "bastard",
            "bitch",
            "bloody",
            "bollocks",
            "brotherfucker",
            "bugger",
            "bullshit",
            "child-fucker",
            "Christ on a bike",
            "Christ on a cracker",
            "cock",
            "cocksucker",
            "crap",
            "cunt",
            "cyka blyat",
            "damn",
            "damn it",
            "dick",
            "dickhead",
            "dyke",
            "fatherfucker",
            "frigger",
            "fuck",
            "goddamn",
            "godsdamn",
            "hell",
            "holy shit",
            "horseshit",
            "in shit",
            "Jesus Christ",
            "Jesus fuck",
            "Jesus H. Christ",
            "Jesus Harold Christ",
            "Jesus, Mary and Joseph",
            "Jesus wept",
            "kike",
            "motherfucker",
            "nigga",
            "nigra",
            "pigfucker",
            "piss",
            "prick",
            "pussy",
            "shit",
            "shit ass",
            "shite",
            "sisterfucker",
            "slut",
            "son of a whore",
            "son of a bitch",
            "spastic",
            "sweet Jesus",
            "turd",
            "twat",
            "wanker",
            "poop"
    );

    // Define the replacement character for bad words
    private static final String REPLACEMENT = "***";

    // Method to filter comments
    public static String filterComment(String comment) {
        // Iterate through the list of bad words
        for (String badWord : badWords) {
            // Replace bad words with the replacement character
            comment = comment.replaceAll("(?i)" + badWord, REPLACEMENT);
        }
        return comment;
    }
}
