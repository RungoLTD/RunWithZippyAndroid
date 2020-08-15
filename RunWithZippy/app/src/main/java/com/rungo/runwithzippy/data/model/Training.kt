package com.rungo.runwithzippy.data.model

import org.threeten.bp.LocalDateTime

data class TrainingResponse(
    val success: Boolean,
    val data: List<List<Data>>
)

data class Data(
    val trainProgram: List<TrainProgram>,
    val title: List<Title>,
    val description: Description,
    val full: Full,
    val color: String,
    val options: List<Options>
)

data class TrainProgram(
    val day: Int?,
    val distance: Long?,
    val time: Long?,
    val pace: Int?,
    val type: Int?,
    val completed: Boolean?
)

data class Title(
    val ru: String,
    val en: String
)

data class Description(
    val ru: String,
    val en: String
)

data class Full(
    val ru: String,
    val en: String
)

data class Options(
    val title: Title,
    val options: List<OptionsV2>
)

data class OptionsV2(
    val title: Title,
    val startDate: LocalDateTime,
    val limit: Int?,
    val type: String
)