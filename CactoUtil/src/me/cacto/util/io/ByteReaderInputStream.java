package me.cacto.util.io;

import java.io.InputStream;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class ByteReaderInputStream extends ByteReader {
	public ByteReaderInputStream(InputStream inputStream) {
		super.inputStream = inputStream;
		if (inputStream != null)
			super.hasNext = Boolean.TRUE;
	}
}
