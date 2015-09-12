package me.cacto.util.log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import me.cacto.util.io.InputStreamToOutputStream;

public class LogFile {
	private FileOutputStream fos;
	private File file;
	private String filename;
	private String path;
	private LogLevel level;
	private Boolean compress;
	private Long maxSize;
	private Long currentSize = 0l;
	private Long maxLines;
	private Integer currentLine = 1;

	public LogFile(LogLevel level, String outfilename) throws IOException {
		this(level, new File(outfilename));
	}

	public LogFile(LogLevel level, File outfile) throws IOException {
		this.file = outfile;
		this.level = level;

		this.filename = this.file.getName();
		if (!this.filename.contains("."))
			this.filename += ".out";

		this.path = this.file.getParent();

		if (!new File(this.path).exists())
			new File(this.path).mkdirs();

		if (!this.path.endsWith(File.separator))
			this.path += File.separator;

		this.file = new File(this.path + this.filename);
		this.logRotate(true);

		this.fos = new FileOutputStream(this.file, true);
	}

	public Log log(String application, String user) {
		return new Log(application, user, this);
	}

	public Log log(String application) {
		return new Log(application, null, this);
	}

	public Log log() {
		return new Log("*", null, this);
	}

	private synchronized void logRotate(Boolean onLoad) throws IOException {
		this.currentLine = 1;
		this.currentSize = 0l;

		if (this.file.exists()) {
			this.fos = new FileOutputStream(this.file, true);

			if (onLoad) {
				this.write("*\t(LOGFILE)\t[]\t" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()) + "\tWARNING\t\"UNEXPECTED CLOSE\"");
			} else
				this.write("*\t(LOGFILE)\t[]\t" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()) + "\tINFORMATION\t\"CLOSE\"");

			this.fos.flush();
			this.fos.close();

			String dateTime = new SimpleDateFormat("'_'yyyyMMdd'_'HHmmss.SSS").format(new Date());
			String destExt = this.filename.substring(this.filename.lastIndexOf("."));
			String destFilename = this.filename.substring(0, this.filename.lastIndexOf(".")) + dateTime + destExt;

			File dest = new File(this.path + destFilename);
			this.file.renameTo(dest);

			if (this.getCompress()) {
				FileOutputStream fosZip = new FileOutputStream(this.path + destFilename + ".gzip");
				GZIPOutputStream gzip = new GZIPOutputStream(new BufferedOutputStream(fosZip));

				FileInputStream fis = new FileInputStream(dest);
				InputStreamToOutputStream.inputStreamToOutputStream(fis, gzip);
				gzip.finish();
				gzip.close();
				fis.close();
				fosZip.close();

				dest.delete();
			}
		}

		this.fos = null;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public LogLevel getLevel() {
		return this.level;
	}

	synchronized Integer getCurrentLine() {
		this.currentLine++;
		return this.currentLine - 1;
	}

	synchronized void write(String s) {
		try {
			if (this.fos == null)
				this.fos = new FileOutputStream(this.file, true);

			byte[] b = s.getBytes();

			this.fos.write(b);
			this.fos.flush();
			this.currentSize += b.length;
		} catch (IOException e) {
			throw new LogException(e);
		}

		try {
			if (this.currentLine > this.getMaxLines() || this.currentSize >= this.getMaxSize()) {
				this.logRotate(false);
			}
		} catch (IOException e) {
			throw new LogException(e);
		}
	}

	public void close() throws IOException {
		this.logRotate(false);
	}

	//	private void renameAndCompress() throws IOException {
	//		this.close();
	//
	//		String dateTime = new SimpleDateFormat("'_'yyyyMMdd'_'HHmmss.SSS").format(new Date());
	//		String destExt = this.filename.substring(this.filename.lastIndexOf("."));
	//		String destFilename = this.filename.substring(0, this.filename.lastIndexOf(".")) + dateTime + destExt;
	//
	//		File dest = new File(this.path + destFilename);
	//		this.file.renameTo(dest);
	//
	//		if (this.getCompress()) {
	//			FileOutputStream fosZip = new FileOutputStream(this.path + destFilename + ".gzip");
	//			GZIPOutputStream gzip = new GZIPOutputStream(new BufferedOutputStream(fosZip));
	//
	//			FileInputStream fis = new FileInputStream(dest);
	//			InputStreamToOutputStream.inputStreamToOutputStream(fis, gzip);
	//			gzip.finish();
	//			gzip.close();
	//			fis.close();
	//			fosZip.close();
	//
	//			dest.delete();
	//		}
	//	}

	public Boolean getCompress() {
		if (this.compress == null)
			return true;

		return this.compress;
	}

	public void setCompress(Boolean compress) {
		this.compress = compress;
	}

	public Long getMaxLines() {
		if (this.maxLines == null)
			return 32000l;

		return this.maxLines;
	}

	public void setMaxLines(Long maxLines) {
		this.maxLines = maxLines;
	}

	public Long getMaxSize() {
		if (this.maxSize == null)
			return 10485760l;

		return this.maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}
}
