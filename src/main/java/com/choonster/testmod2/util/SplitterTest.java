package com.choonster.testmod2.util;

import com.choonster.testmod2.Logger;
import com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.List;

public class SplitterTest {
	// Copied from StringTranslate
	private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);

	public static void splitStrings() {
		splitString("abc.123=ABC 123");
		splitString("abc.123 = ABC 123");
	}

	private static void splitString(String string) {
		List<String> results = equalSignSplitter.splitToList(string);
		Logger.info("\"%s\" splits to %s", string, Arrays.toString(results.toArray()));
	}
}
