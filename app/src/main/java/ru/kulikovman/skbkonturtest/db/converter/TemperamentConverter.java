package ru.kulikovman.skbkonturtest.db.converter;

import androidx.room.TypeConverter;
import ru.kulikovman.skbkonturtest.data.Temperament;

public class TemperamentConverter {

    @TypeConverter
    public static Temperament fromString(String data) {
        return Temperament.valueOf(data);
    }

    @TypeConverter
    public static String fromEnum(Temperament temperament) {
        return temperament.name();
    }
}
