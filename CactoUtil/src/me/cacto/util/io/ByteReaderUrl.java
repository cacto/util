package me.cacto.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class ByteReaderUrl extends ByteReader {
	private URL url;
	private URLConnection urlConnection;

	public ByteReaderUrl(String url) throws MalformedURLException {
		this(new URL(url));
	}

	public ByteReaderUrl(URL url) {
		this.url = url;
	}

	public ByteReaderUrl(URLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public URLConnection getUrlConnection() throws IOException {
		if (this.urlConnection == null) {
			this.urlConnection = this.url.openConnection();
		}

		return this.urlConnection;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (super.inputStream == null) {
			super.inputStream = this.getUrlConnection().getInputStream();
			this.hasNext = Boolean.TRUE;
		}

		return super.inputStream;
	}
}
