package com.example.lab17

data class MyObject(
    val result: Result
) {
    data class Result(
        val records: List<Record>
    ) {
        data class Record(
            val SiteName: String,
            val Status: String,
        )
    }
}
