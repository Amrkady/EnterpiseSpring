package common.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpTransferFile {
	static String user = "thorapetbnzena@thorapetrol.com";
	static String pass = "01143832045Ff";
	static String host = "thorapetrol.com";
	static int port = 21;

	public static String uploadFile(InputStream inputStream, String uploadPath) {
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(host, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(uploadPath, inputStream);
			inputStream.close();

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		return uploadPath;
	}

	public static InputStream getFileStream(String fileName) throws IOException {
		InputStream inputStream = null;

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(host, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			inputStream = ftpClient.retrieveFileStream(fileName);

			boolean success = ftpClient.completePendingCommand();
			if (success) {
				System.out.println("File #2 has been downloaded successfully.");
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return inputStream;

	}
}
