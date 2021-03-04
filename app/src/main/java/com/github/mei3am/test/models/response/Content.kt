package com.github.mei3am.test.models.response

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

data class ContentResponse(
    @field:SerializedName("Status")
    val status: Int,
    @field:SerializedName("Message")
    val message: String,
    @field:SerializedName("Result")
    val result: Result
)

data class Result(
    @field:SerializedName("GetContentList")
    val contentList: List<Content>,

    @field:SerializedName("TotalPages")
    val totalPages: Int
)

@Entity(
    indices = [
        Index("contentId")],
    primaryKeys = ["title"]
)
data class Content(
    @field:SerializedName("ContentID")
    val contentId: Int,

    @field:SerializedName("Title")
    val title: String,

    @field:SerializedName("ThumbImage")
    val thumbImage: String,

    @field:SerializedName("ZoneID")
    val zoneId: Int?,

    val favorite: Boolean?,
)