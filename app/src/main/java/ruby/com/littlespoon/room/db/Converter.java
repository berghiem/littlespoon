package ruby.com.littlespoon.room.db;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;

public class Converter {
        @TypeConverter
        public static Timestamp fromTimestamp(Long value) {
            return value == null ? null : new Timestamp(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Timestamp time) {
            return time == null ? null : time.getTime();
        }

}
