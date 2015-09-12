package me.cacto.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class ByteReaderFile extends ByteReader {
	public ByteReaderFile(String filename) throws FileNotFoundException {
		this(new File(filename));
	}

	public ByteReaderFile(File file) throws FileNotFoundException {
		this.inputStream = new FileInputStream(file);
		this.hasNext = Boolean.TRUE;
	}
}
