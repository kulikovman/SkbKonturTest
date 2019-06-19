package ru.kulikovman.skbkonturtest.db.converter;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static long fromDate(Date data) {
        return data.getTime();
    }

    @TypeConverter
    public static Date fromLong(long timestamp) {
        return new Date(timestamp);
    }
}
