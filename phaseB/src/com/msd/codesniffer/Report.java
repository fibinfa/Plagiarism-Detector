package com.msd.codesniffer;

import java.util.HashMap;

/**
 * The type Report.
 *
 * @author sidharththapar
 * @since 2 /11/18.
 */
public class Report {
    /**
     * The Id.
     */
    int id;
    /**
     * The Score.
     */
    int score;
    /**
     * The User i ds.
     */
    int[] userIDs;

    /**
     * returns map with
     * Key: fileName
     * Value: list of startLines and endLine of the matching block
     */
    HashMap<String, int[]> similarity;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get user i ds int [ ].
     *
     * @return the int [ ]
     */
    public int[] getUserIDs() {
        return userIDs;
    }

    /**
     * Sets user i ds.
     *
     * @param userIDs the user i ds
     */
    public void setUserIDs(int[] userIDs) {
        this.userIDs = userIDs;
    }

    /**
     * Gets similarity.
     *
     * @return the similarity
     */
    public HashMap<String, int[]> getSimilarity() {
        return similarity;
    }

    /**
     * Sets similarity.
     *
     * @param similarity the similarity
     */
    public void setSimilarity(HashMap<String, int[]> similarity) {
        this.similarity = similarity;
    }

   
}
