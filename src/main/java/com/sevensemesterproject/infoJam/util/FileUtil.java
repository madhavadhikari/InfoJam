package com.sevensemesterproject.infoJam.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class FileUtil {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
	private static final String FILE_DIRECTORY = "infojam";
	private static final String FILE_SEPARATOR = "/";

	
	@Deprecated
	public static File write(String fileName, String location, String value) {
		LOG.info("File Uploading...");
		InputStream is = null;
		OutputStream os = null;
		location += "//" + fileName;
		File file = new File(location);
		File filePath = new File(file.getPath());
		try {
			is = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
			fileName = null;
			value = null;
			os = new FileOutputStream(file);
			int bytesRead = 0;
			int batchLength = 10000000;
			byte[] batch = new byte[batchLength];
			while ((bytesRead = is.read(batch)) > 0) {
				byte[] ba = new byte[bytesRead];

				for (int i = 0; i < ba.length; i++) {
					ba[i] = batch[i];
				}
				ba = Base64.decode(new String(ba));
				os.write(ba, 0, ba.length);
			}
			LOG.info("File uploaded to: " + file.getAbsolutePath());
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
			try {
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
		}
		return filePath;
	}

	public static File write(String fileName, String value) {
		LOG.info("File Uploading...");
		InputStream is = null;
		OutputStream os = null;
		File directory = new File(FILE_DIRECTORY);
		File file = null;
		try {

			if (!directory.exists()) {
				directory.mkdir();
			}
			file = new File(directory.getPath().concat(File.separator).concat(fileName));
			is = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
			fileName = null;
			value = null;
			os = new FileOutputStream(file);
			int bytesRead = 0;
			int batchLength = 10000000;
			byte[] batch = new byte[batchLength];
			while ((bytesRead = is.read(batch)) > 0) {
				byte[] ba = new byte[bytesRead];

				for (int i = 0; i < ba.length; i++) {
					ba[i] = batch[i];
				}
				ba = Base64.decode(new String(ba));
				if (os != null && ba != null) {
					os.write(ba, 0, ba.length);
				}
			}
			LOG.info("File uploaded to: " + file.getAbsolutePath());

		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
			try {
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
		}
		return file;
	}

	@SuppressWarnings("restriction")
	@Deprecated
	public static File write(String fileName, String value, Long userId, Date date) {
		LOG.info("File Uploading...");
		InputStream is = null;
		OutputStream os = null;
		File directory = new File(FILE_DIRECTORY);
		File file = null;
		try {

			if (!directory.exists()) {
				directory.mkdir();
			}

			
			String s2 = "/";
			String custDirectory = FILE_DIRECTORY.concat(s2);
			File directory1 = new File(custDirectory);
			if (!directory1.exists()) {
				directory1.mkdir();
			}

			// Date date; // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			String months = Month.of(month).name();

			s2 = "/";
			String yearDirectory = custDirectory.concat(s2);
			File directory2 = new File(yearDirectory);
			if (!directory2.exists()) {
				directory2.mkdir();
			}

			// s1=Integer.toString(months);
			s2 = "/";
			s2 = s2.concat(months);
			String monthDirectory = yearDirectory.concat(s2);
			File directory3 = new File(monthDirectory);
			if (!directory3.exists()) {
				directory3.mkdir();
			}

			s2 = "/";
			String userRoleDirectory = monthDirectory.concat(s2);
			File directory4 = new File(userRoleDirectory);
			if (!directory4.exists()) {
				directory4.mkdir();
			}

			file = new File(directory4.getPath().concat(File.separator).concat(fileName));
			is = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
			fileName = null;
			value = null;
			os = new FileOutputStream(file);
			int bytesRead = 0;
			int batchLength = 10000000;
			byte[] batch = new byte[batchLength];
			while ((bytesRead = is.read(batch)) > 0) {
				byte[] ba = new byte[bytesRead];

				for (int i = 0; i < ba.length; i++) {
					ba[i] = batch[i];
				}
				ba = Base64.decode(new String(ba));
				if (os != null && ba != null) {
					os.write(ba, 0, ba.length);
				}
			}
			LOG.info("File uploaded to: " + file.getAbsolutePath());

		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
			try {
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
		}
		return file;

	}

	public static String getFileLocation(String environment) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		LOG.debug("Environment {}", environment);
		String finalDirectory = environment.concat(FILE_SEPARATOR)
				.concat(String.valueOf(year)).concat(FILE_SEPARATOR)
				.concat(String.valueOf(month)).concat(FILE_SEPARATOR)
				.concat(UUID.randomUUID().toString());
		LOG.debug("The file directory is {}", finalDirectory);
		return finalDirectory;
	}
	
	public static void deleteFile(String url) {
		File directory = new File(url);
		if (!directory.exists()) {
			LOG.info("----------------->>>>>>>>Sorry no file found");
			return;
		}
		if (directory.delete()) {
			LOG.info("---------->>>>>>>>>>>>>>>Succesfully Deleted");
			return;
		}
		else
			LOG.info("---------->>>>>>>>>>>>>>>Not Deleted");
	}


}
