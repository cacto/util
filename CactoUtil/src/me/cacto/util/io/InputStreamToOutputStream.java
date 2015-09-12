package me.cacto.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class InputStreamToOutputStream {
	public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
		ByteReaderInputStream bris = new ByteReaderInputStream(inputStream);

		while (bris.hasNext())
			outputStream.write(bris.next());
	}
}
