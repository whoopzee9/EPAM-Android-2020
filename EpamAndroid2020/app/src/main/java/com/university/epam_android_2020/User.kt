package com.university.epam_android_2020

public class User {
    val id:String?
    val name:String
    val secondName:String
    val email:String

    constructor(id: String?, name: String, secondName: String, email: String) {
        this.id = id
        this.name = name
        this.secondName = secondName
        this.email = email
    }
}