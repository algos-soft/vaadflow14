package it.algos.simple.backend.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 07-dic-2020
 * Time: 09:23
 */
public abstract class SimpleCost {

    public static final String TAG_SIMPLE_DATA = "simpleData";

    public static final String PREF_DATA = "data";

    public static final String PREF_DATA_TIME = "dataTime";

    public static final String PREF_TIME = "time";

    public static final LocalDate DATA = LocalDate.of(2020, 3, 25);

    public static final LocalDateTime DATA_TIME = LocalDateTime.of(2020, 10, 28, 14, 35);

    public static final LocalTime TIME = LocalTime.of(8, 47);

}
