package com.rungo.runwithzippy.data.model

data class dataFriends(
    val approved: List<Friend>,
    val notApproved: List<Friend>
)

data class Friend(
    val id: Int,
    val name: String,
    val avatar: String,
    val mood: Int
)

data class FriendsResponse(
    val success: Boolean,
    val data: dataFriends
)