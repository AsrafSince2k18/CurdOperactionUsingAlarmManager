package com.example.interview.notes.data.mapper

import com.example.interview.notes.data.local.BusEntity
import com.example.interview.notes.domain.model.BusModel

fun BusModel.toEntity():BusEntity{
    return BusEntity(
        id = id,
        busName = busName,
        noPassenger=noPassenger,
        time = time,
        oneTimeAlarm = oneTimeAlarm,
        repeatTimeAlarm = repeatTimeAlarm
    )
}

fun BusEntity.toModel():BusModel{
    return BusModel(
        id = id,
        busName = busName,
        noPassenger=noPassenger,
        time = time,
        oneTimeAlarm = oneTimeAlarm,
        repeatTimeAlarm = repeatTimeAlarm
    )
}