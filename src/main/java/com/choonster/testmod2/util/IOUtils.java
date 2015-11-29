package com.choonster.testmod2.util;

import org.apache.commons.io.output.NullWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * I/O utility methods.
 */
public class IOUtils {

	/**
	 * If {@code writeToFile} is {@code true}, returns a {@link BufferedWriter} for the file at {@code path} with the specified {@link OpenOption}s.
	 * <p>
	 * If {@code writeToFile} is {@code false}, returns a {@link NullWriter} that discards everything written to it.
	 *
	 * @param writeToFile Whether the returned {@link Writer} should write to the file at {@code path}
	 * @param path        The path to the file
	 * @param openOptions Options specifying how the file is opened
	 * @return The {@link Writer}
	 * @throws IOException If an I/O error occurs opening or creating the file
	 */
	public static Writer getOptionalWriter(boolean writeToFile, Path path, OpenOption... openOptions) throws IOException {
		if (writeToFile) {
			return Files.newBufferedWriter(path, openOptions);
		} else {
			return new NullWriter();
		}
	}
}
