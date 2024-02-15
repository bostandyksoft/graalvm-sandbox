package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {

	static String JS_CODE = "(function myFun(param){return 'date '+ new Date(param);})";

	@Test
	void dateTest() {
		try (Context context = Context.create()) {
			Value value = context.eval("js", JS_CODE);
			Value result = value.execute(LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0));
			String strResult = result.asString();
			assertEquals("date Wed Feb 14 2024", strResult.substring(0, Math.min(20, strResult.length())));
		}
	}
}
