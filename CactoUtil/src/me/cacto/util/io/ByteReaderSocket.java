package me.cacto.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Daniel Farias
 * @version 1.0.0
 * @since 1.0.0
 */
public class ByteReaderSocket extends ByteReader {
	private final Socket socket;

	public ByteReaderSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (super.inputStream == null) {
			super.inputStream = this.socket.getInputStream();
			this.hasNext = Boolean.TRUE;
		}

		return super.inputStream;
	}
}
