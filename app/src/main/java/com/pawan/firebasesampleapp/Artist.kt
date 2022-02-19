package com.pawan.firebasesampleapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Artist {
    var artistId: String? = null
        private set
    var artistName: String? = null
        private set
    var artistGenre: String? = null
        private set

    constructor() {
        //this constructor is required
    }

    constructor(artistId: String?, artistName: String?, artistGenre: String?) {
        this.artistId = artistId
        this.artistName = artistName
        this.artistGenre = artistGenre
    }
}