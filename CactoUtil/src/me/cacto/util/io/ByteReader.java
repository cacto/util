package me.cacto.util.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class ByteReader {
	public static ByteReaderFile byteReader(File file) throws FileNotFoundException {
		return new ByteReaderFile(file);
	}

	public static ByteReaderInputStream byteReader(InputStream inputStream) {
		return new ByteReaderInputStream(inputStream);
	}

	public static ByteReaderUrl byteReader(URL url) {
		return new ByteReaderUrl(url);
	}

	public static ByteReaderUrl byteReader(URLConnection urlConnection) {
		return new ByteReaderUrl(urlConnection);
	}

	///
	private Integer buffer = 4096;
	protected InputStream inputStream;
	protected Boolean hasNext = Boolean.FALSE;
	protected Boolean hasStarted = Boolean.FALSE;

	public InputStream getInputStream() throws IOException {
		return this.inputStream;
	}

	public Integer getBuffer() {
		return this.buffer;
	}

	public void setBuffer(Integer buffer) {
		this.buffer = buffer;
	}

	public Boolean hasNext() {
		return this.hasNext;
	}

	public byte[] next() throws IOException {
		return this.next(this.buffer);
	}

	public byte[] next(int buffer) throws IOException {
		try {
			if (this.hasStarted && !this.hasNext)
				return new byte[0];

			this.hasStarted = Boolean.TRUE;

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bytes = new byte[this.buffer];
			int bytesRead = this.getInputStream().read(bytes);

			if (bytesRead == -1) {
				this.hasNext = Boolean.FALSE;
				return new byte[0];
			}

			baos.write(bytes, 0, bytesRead);
			baos.flush();
			return baos.toByteArray();
		} catch (IOException ex) {
			this.close();
			throw ex;
		}
	}

	public byte[] readFully() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = this.next();

		while (this.hasNext()) {
			baos.write(b);
			b = this.next();
		}

		return baos.toByteArray();
	}

	public void close() {
		try {
			this.getInputStream().close();
		} catch (IOException e) {
			this.inputStream = null;
		}

		this.hasNext = Boolean.FALSE;
	}
}
