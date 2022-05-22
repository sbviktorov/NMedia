package ru.netology.nmedia.socialNetwork.calculations

fun activitiesCountFormat(count: Int): String {

    when (count) {
        0 -> return "0"
        in 1..999 -> return count.toString()
        in 1_000..9_999 -> {
            return if ((count % 1000) < 100) {
                (count / 1000).toString() + "K"
            } else {
                (count / 1000).toString() + "." + ((count % 1000) / 100).toString() + "K"
            }
        }
        in 10_000..999_999 -> {
            return (count / 1000).toString() + "K"
        }
        else -> {
            return if ((count % 1_000_000) < 100_000) {
                (count / 1_000_000).toString() + "M"
            } else {
                (count / 1_000_000).toString() + "." + ((count % 1_000_000) / 100_000).toString() + "M"
            }
        }
    }

}
