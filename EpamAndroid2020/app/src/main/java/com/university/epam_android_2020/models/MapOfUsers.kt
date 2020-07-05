package com.university.epam_android_2020.models

import java.io.Serializable

data class MapOfUsers(val name: String, val places: List<Place>) : Serializable
