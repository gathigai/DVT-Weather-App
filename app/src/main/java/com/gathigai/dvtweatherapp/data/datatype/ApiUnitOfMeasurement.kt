package com.gathigai.dvtweatherapp.data.datatype

enum class ApiUnitOfMeasurement(val value: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial");

    override fun toString(): String {
        return this.value;
    }
}