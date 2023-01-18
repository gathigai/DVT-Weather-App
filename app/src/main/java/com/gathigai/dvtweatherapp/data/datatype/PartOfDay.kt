package com.gathigai.dvtweatherapp.data.datatype

enum class PartOfDay(val value: String) {
    DAY("d"),
    NIGHT("n");

    override fun toString(): String {
        return this.value;
    }
}