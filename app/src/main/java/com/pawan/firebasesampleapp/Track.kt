package com.pawan.firebasesampleapp

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Belal on 2/26/2017.
 */
@IgnoreExtraProperties
class Track {
    private var id: String? = null
    var trackName: String? = null
        private set
    var rating = 0
        private set

    constructor() {}
    constructor(id: String?, trackName: String?, rating: Int) {
        this.trackName = trackName
        this.rating = rating
        this.id = id
    }
}