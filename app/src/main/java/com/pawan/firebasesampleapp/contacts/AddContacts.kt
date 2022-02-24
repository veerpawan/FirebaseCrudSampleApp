package com.pawan.firebasesampleapp.contacts

class AddContacts {
    var key: String? =null
    private set
    var fname: String? = null
        private set
    var lname: String? = null
        private set
    var email: String? = null
        private set
    var phone: String? = null
        private set

    constructor() {
        //this constructor is required
    }

  /*  constructor(firstName: String?, lastName: String?, email: String?, phone: String?) {
        this.fname = firstName
        this.lname = lastName
        this.email = email
        this.phone = phone
    }*/

    constructor(key: String?, firstName: String?, lastName: String?, email: String?, phone: String?) {
        this.fname = firstName
        this.lname = lastName
        this.email = email
        this.phone = phone
        this.key = key
    }
}