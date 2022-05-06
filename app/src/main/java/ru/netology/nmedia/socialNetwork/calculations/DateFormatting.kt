package ru.netology.nmedia.socialNetwork.calculations

import java.text.SimpleDateFormat

fun dateFormatting(dateInMs: Long):String {
    val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm")
    return sdf.format(dateInMs)
}