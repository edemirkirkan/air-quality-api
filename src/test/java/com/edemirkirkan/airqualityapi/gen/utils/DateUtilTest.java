package com.edemirkirkan.airqualityapi.gen.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateUtilTest {

    private static SimpleDateFormat formatterDate;

    @BeforeAll
    public static void setup(){
        formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    void shouldConvertString() {

        Date date = new Date();

        String str = DateUtil.dateToString(date);

        String format = formatterDate.format(date);

        assertEquals(str, format);
    }

    @Test
    void shouldNotConvertToStringWhenTheParameterIsNull() {
        assertThrows(NullPointerException.class, () -> DateUtil.dateToString(null));
    }
}