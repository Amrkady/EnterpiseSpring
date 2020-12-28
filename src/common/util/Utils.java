package common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.userdetails.User;

import com.entities.Attachment;
import com.entities.Users;
import com.models.AttachmentModel;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class Utils {

	public static Map<String, String> daysMap;
	private static Map<String, String> hoursMap;
	private static Map<String, String> minutesMap;
	public static int year = Calendar.getInstance().get(Calendar.YEAR);
	protected static final Logger logger = Logger.getLogger(Utils.class);
	private static Properties prop = new Properties();

	private static Map<String, String> sqlRequests;

	public static String padLeft(String data, int size, char formattingChar) {
		return String.format("%1$" + size + "s", data).replace(' ', formattingChar);
	}

	public static String convertDateToArabic(String hijriDate) {
		if (hijriDate == null) {
			return "";
		}
		String ret = hijriDate;
		if (hijriDate.length() != 10) {
			ret = convertTextWithArNumric(hijriDate) + "*";
		} else {
			String dd = hijriDate.substring(0, 2);
			String mm = hijriDate.substring(3, 5);
			String yy = hijriDate.substring(6, 10);
			ret = convertTextWithArNumric(yy + "/" + mm + "/" + dd);
		}
		return ret;
	}

	public static String convertTextWithArNumric(String txt) {
		String returnText = txt;
		if (returnText != null) {
			returnText = returnText.replaceAll("0", "\\\u0660").replaceAll("1", "\\\u0661").replaceAll("2", "\\\u0662")
					.replaceAll("3", "\\\u0663").replaceAll("4", "\\\u0664").replaceAll("5", "\\\u0665")
					.replaceAll("6", "\\\u0666").replaceAll("7", "\\\u0667").replaceAll("8", "\\\u0668")
					.replaceAll("9", "\\\u0669");
		}
		return returnText;
	}

	public static String convertToEnglishDigits(String value) {
		String newValue = value.replace("Ù¡", "1").replace("Ù¢", "2").replace("Ù£", "3").replace("Ù¤", "4")
				.replace("Ù¥", "5").replace("Ù¦", "6").replace("Ù§", "7").replace("Ù¨", "8")
				.replace("Ù©", "9").replace("Ù ", "0").replace("Û±", "1").replace("Û²", "2")
				.replace("Û³", "3").replace("Û´", "4").replace("Ûµ", "5").replace("Û¶", "6")
				.replace("Û·", "7").replace("Û¸", "8").replace("Û¹", "9").replace("Û°", "0");

		return newValue;
	}

	public static Map<String, String> findDaysMap() {
		Map<String, String> Days = new HashMap<String, String>();
		Days.put("Ø§Ù„Ø³Ø¨Øª", "Ø§Ù„Ø³Ø¨Øª");
		Days.put("Ø§Ù„Ø£Ø­Ø¯", "Ø§Ù„Ø£Ø­Ø¯");
		Days.put("Ø§Ù„Ø£Ø«Ù†ÙŠÙ†", "Ø§Ù„Ø£Ø«Ù†ÙŠÙ†");
		Days.put("Ø§Ù„Ø«Ù„Ø§Ø«Ø§Ø¡", "Ø§Ù„Ø«Ù„Ø§Ø«Ø§Ø¡");
		Days.put("Ø§Ù„Ø£Ø±Ø¨Ø¹Ø§Ø¡", "Ø§Ù„Ø£Ø±Ø¨Ø¹Ø§Ø¡");
		Days.put("Ø§Ù„Ø®Ù…ÙŠØ³", "Ø§Ù„Ø®Ù…ÙŠØ³");
		return Days;
	}

	public static Map<String, String> findMinutesMap() {
		Map<String, String> MinutesMap = new HashMap<String, String>();
		MinutesMap.put("10", "10");
		MinutesMap.put("20", "20");
		MinutesMap.put("30", "30");
		MinutesMap.put("40", "40");
		MinutesMap.put("50", "50");
		return MinutesMap;
	}

	public static Map<String, String> findHoursMap() {
		Map<String, String> HoursMap = new HashMap<String, String>();
		HoursMap.put(" ØµØ¨Ø§Ø­Ø§ 01", "01");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 02", "02");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 03", "03");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 04", "04");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 05", "05");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 06", "06");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 07", "07");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 08", "08");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 09", "09");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 10", "10");
		HoursMap.put("ØµØ¨Ø§Ø­Ø§ 11", "11");
		HoursMap.put("Ø¸Ù‡Ø±Ø§ 12", "12");
		HoursMap.put("Ø¸Ù‡Ø±Ø§ 01", "13");
		HoursMap.put("Ø¸Ù‡Ø±Ø§ 02", "14");
		HoursMap.put("Ø¸Ù‡Ø±Ø§ 03", "15");
		HoursMap.put(" Ù…Ø³Ø§Ø¡Ø§04", "16");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 05", "17");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 06", "18");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 07", "19");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 08", "20");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 09", "21");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 10", "22");
		HoursMap.put("Ù…Ø³Ø§Ø¡Ø§ 11", "23");
		HoursMap.put(" Ù„ÙŠÙ„Ø§ 12", "24");
		return HoursMap;
	}

	public static Map<String, String> getDaysMap() {
		if ((daysMap == null) || (daysMap.isEmpty())) {
			daysMap = findDaysMap();
		}
		return daysMap;
	}

	public static Map<String, String> getHoursMap() {
		if ((hoursMap == null) || (hoursMap.isEmpty())) {
			hoursMap = findHoursMap();
		}
		return hoursMap;
	}

	public static Map<String, String> getMinutesMap() {
		if ((minutesMap == null) || (minutesMap.isEmpty())) {
			minutesMap = findMinutesMap();
		}
		return minutesMap;
	}

	public static void openDialog(String DialogName) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('" + DialogName + "').show()");
	}

	public static void closeDialog(String DialogName) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('" + DialogName + "').hide()");
	}

	public static String convertBytesToString(byte[] inputBytes) {
		return new String(inputBytes);
	}

	public static String loadMessagesFromFile(String key) {
		if (prop.size() == 0) {
			InputStream input = null;
			try {
				String filename = "messages.properties";
				input = Utils.class.getClassLoader().getResourceAsStream(filename);
				// load a properties file from class path, inside static method
				prop.load(input);
				return prop.getProperty(key);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return "";
		} else {
			return prop.getProperty(key);
		}

	}

	public static String generateRandomName() {
		final SecureRandom r = new SecureRandom();
		Date now = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("ssddMMmmSSS");
		String strDate = sdfDate.format(now);
		return new BigInteger(130, r).toString(32) + strDate;
	}

	public static String saveAttachment(InputStream inputStream) {
		String name = generateRandomName();
		String destination = "C:\\tempUploadedFile\\";
		File temp = new File(destination);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		String completeName = destination + name;

		try {
			OutputStream out = new FileOutputStream(new File(destination + name));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			name = "";
			e.printStackTrace();
		}

		/* UPLOADING TO REMOTE SERVER */
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/serverUpload/upload");
			FileBody fileBody = new FileBody(new File(completeName));
			HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("upfile", fileBody).build();
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {

				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuilder result = new StringBuilder();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				}
				EntityUtils.consume(responseEntity);
			} catch (Exception responseEx) {
				name = "";
				responseEx.printStackTrace();
				System.err.println("ERROR!! 010 : " + responseEx.getMessage());
			} finally {
				response.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("ERROR!! 011 : " + ex.getMessage());
		}

		return name;
	}

	public static String convertHijriDateToGregorian(String hijriDate) {
		try {
			return HijriCalendarUtil.ConvertHijriTogeorgianDate(hijriDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean checkSomeDayOfDate(String hijriDate, int day) {
		boolean valid = true;
		try {
			String myDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(hijriDate);
			Calendar cal = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date date = format.parse(myDate);
			cal.setTime(date);
			cal.get(Calendar.DAY_OF_WEEK);
			if (cal.get(Calendar.DAY_OF_WEEK) != day) {

				valid = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return valid;
	}

	public static boolean checkValidTime(int leavingHour, int returnHour, int leavingMinute, int returnMinute) {
		boolean valid = true;
		if ((leavingHour > returnHour) || ((leavingHour == returnHour) && (leavingMinute >= returnMinute))) {
			// MsgEntry.addErrorMessage(MsgEntry.ERROR_INVALID_AUTHORIZATION_TIME);
			valid = false;
		}
		return valid;
	}

	public static boolean checkEcartTime(int leavingHour, int returnHour, int leavingMinute, int returnMinute,
			int ecart) {
		boolean valid = true;
		if (((returnHour - leavingHour) > ecart)
				|| ((returnHour - leavingHour) == ecart) && (leavingMinute < returnMinute)) {
			MsgEntry.addErrorMessage(MsgEntry.ERROR_ECART_AUTHORIZATION_TIME);
			valid = false;
		}
		return valid;
	}

	public static synchronized void saveAttachments(InputStream inputStream, String att_name) throws Exception {
		String destination = "C:\\temp\\";
		File temp = new File(destination);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		String CompleteName = "";
		try {
			OutputStream out = new FileOutputStream(new File(destination + att_name));
			CompleteName = destination + att_name;

			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("ERROR!! 009 : " + e.getMessage());
		}

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/serverUpload/upload");
			FileBody fileBody = new FileBody(new File(CompleteName));
			HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("upfile", fileBody).build();
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {

				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuilder result = new StringBuilder();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				}
				EntityUtils.consume(responseEntity);
			} catch (Exception responseEx) {
				responseEx.printStackTrace();
				System.err.println("ERROR!! 010 : " + responseEx.getMessage());
			} finally {
				response.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("ERROR!! 011 : " + ex.getMessage());
		}

	}

	public static User findLoggedUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (User) context.getExternalContext().getSessionMap().get("logged_user");
	}

	public static Date convertHDateToGDate(String hijriDate) throws Exception {
		try {

			String myDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(hijriDate);

			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date date = format.parse(myDate);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static String getDayForHigriDate(String hijriCalendar) {
		try {
			String day = new String();
			String myDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(hijriCalendar);
			Calendar cal = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date date = format.parse(myDate);
			cal.setTime(date);
			int x = cal.get(Calendar.DAY_OF_WEEK);
			switch (x) {

			case 1:
				day = loadMessagesFromFile("sunday");
				break;
			case 2:
				day = loadMessagesFromFile("monday");

				break;
			case 3:
				day = loadMessagesFromFile("tuesday");
				break;
			case 4:
				day = loadMessagesFromFile("wednesday");
				break;
			case 5:
				day = loadMessagesFromFile("thursday");
				break;

			case 6:
				day = loadMessagesFromFile("friday");
				break;
			case 7:
				day = loadMessagesFromFile("saturday");
				break;

			default:
				break;
			}

			return day;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public static String getDayForCurrentDate() {

		Calendar calendar = Calendar.getInstance();
		Date currentHigriDate = new Date();
		calendar.setTime(currentHigriDate);
		Integer x = calendar.get(Calendar.DAY_OF_WEEK);
		String day = new String();

		switch (x) {

		case 1:
			day = "Ø§Ù„Ø§Ø­Ø¯";
			break;
		case 2:
			day = "Ø§Ù„Ø§Ø«Ù†ÙŠÙ†";

			break;
		case 3:
			day = "Ø§Ù„Ø«Ù„Ø§Ø«Ø§Ø¡";
			break;
		case 4:
			day = "Ø§Ù„Ø§Ø±Ø¨Ø¹Ø§Ø¡";
			break;
		case 5:
			day = "Ø§Ù„Ø®Ù…ÙŠØ³";
			break;
		case 6:
			day = "Ø§Ù„Ø¬Ù…Ø¹Ø©";
			break;
		case 7:
			day = "Ø§Ù„Ø³Ø¨Øª";
			break;

		default:

			break;
		}

		return day;
	}

	public static int reverseDateToNumber(String dateConvert) {
		String dd = dateConvert.substring(0, 2);
		String mm = dateConvert.substring(3, 5);
		String yy = dateConvert.substring(6, 10);
		return Integer.parseInt(yy + mm + dd);

	}

	public static String reverseNumberToDate(String dateNumber) {
		String yy = dateNumber.substring(0, 4);
		String mm = dateNumber.substring(4, 6);
		String dd = dateNumber.substring(6, 8);
		return dd + "/" + mm + "/" + yy;

	}

	public static int getDiffYears(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public static int getDiffDays(String start, String end) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(start);
			d2 = format.parse(end);

			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			int result = Days.daysBetween(dt1, dt2).getDays();
			if (d2.before(d1)) {
				result = result * (-1);
			}
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static double formatDouble(double inputVal) {
		DecimalFormat df = new DecimalFormat("0.##");
		return Double.parseDouble(df.format(inputVal));
	}

	public static String formatTwoDigits(int inputVal) {
		return String.format("%02d", inputVal);
	}

	/**
	 * Calculate the end service date taking a fix 30 days by month
	 * 
	 * @return
	 */
	public static String calculateEndServiceDate(String startDate, int prd) {

		int period = prd * 10 - 1;

		int ddf = Integer.parseInt(startDate.substring(0, 2));
		int mmf = Integer.parseInt(startDate.substring(3, 5));
		int yyf = Integer.parseInt(startDate.substring(6, 10));

		int addY = period / 360;
		int addM = (period - addY * 360) / 30;
		int addD = (period - addY * 360 - addM * 30);
		ddf += addD;
		mmf += addM;
		yyf += addY;

		if (ddf > 30) {
			mmf += 1;
			ddf = ddf - 30;
		}
		if (mmf > 12) {
			yyf += 1;
			mmf = mmf - 12;
		}

		return formatTwoDigits(ddf) + "/" + formatTwoDigits(mmf) + "/" + yyf;

		// return HijriCalendarUtil.addDaysToHijriDate(startDate, period);

	}

	public static boolean IsEmpty(String MyString) {
		if (MyString == null || MyString.isEmpty()) {
			return true;
		}
		return false;
	}

	public static void printPdfReport(String reportName, Map<String, Object> parameters) {
		// get HttpServletResponse
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

		// set correct content type
		response.setContentType("application/pdf");
		Connection connection = null;
		OutputStream stream = null;
		try {
			// get OutputStream
			stream = response.getOutputStream();
			connection = DataBaseConnectionClass.getConnection(); // opens a
			String reportingPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(reportName);
			String jrsFile = reportingPath.replace("jasper", "jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(jrsFile);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, connection);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
			exporter.exportReport();

		} catch (Exception e) {
			throw new RuntimeException("It's not possible to generate the pdf report.", e);
		} finally {
			// it's your responsibility to close the connection, don't forget
			// it!
			try {
				if (stream != null) {
					stream.close();
				}

			} catch (Exception e) {
			}
		}
		// mark response as completed
		context.responseComplete();
	}

	public synchronized static <T> void printPdfReportFromListDataSource(String reportName,
			Map<String, Object> parameters, List<T> dataSourceList) {
		// get HttpServletResponse
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

		// set correct content type
		response.setContentType("application/pdf");
		// Connection connection = null;
		OutputStream stream = null;
		try {
			// get OutputStream
			stream = response.getOutputStream();
			// connection = DataBaseConnectionClass.getConnection(); // opens a
			String reportingPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(reportName);
			String jrsFile = reportingPath.replace("jasper", "jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(jrsFile);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters,
					new JRBeanCollectionDataSource(dataSourceList));
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
			exporter.exportReport();
			stream.close();
		} catch (Exception e) {
			throw new RuntimeException("It's not possible to generate the pdf report.", e);
		} finally {
			// it's your responsibility to close the connection, don't forget
			// it!
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
			}
		}
		// mark response as completed
		context.responseComplete();
	}

	public static double calculateDiffDaysHijriDate(String hijriStart, String hijriEnd) {
		double result = 0;
		double startYear = Integer.parseInt(hijriStart.substring(6, 10));
		double endYear = Integer.parseInt(hijriEnd.substring(6, 10));
		double resYear = endYear - startYear;

		double startMonth = Integer.parseInt(hijriStart.substring(3, 5));
		double endMonth = Integer.parseInt(hijriEnd.substring(3, 5));

		if (startMonth > endMonth) {
			resYear -= 1;
			endMonth += 12;
		}
		double resMonth = endMonth - startMonth;
		double startDay = Integer.parseInt(hijriStart.substring(0, 2));
		double endDay = Integer.parseInt(hijriEnd.substring(0, 2));
		if (startDay > endDay) {
			resMonth -= 1;
			endDay += 30;
		}
		double resDay = endDay - startDay;
		result = resDay + resMonth * 30 + resYear * 360;
		return result;

	}

	public static byte[] getBytes(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}
			buf = bos.toByteArray();
		}
		return buf;
	}

	public static String getFormatKeyCode(String MhlId) {
		if (MhlId != null && MhlId.length() == 12) {
			String sp1 = MhlId.substring(0, 2);
			String sp2 = MhlId.substring(2, 4);
			String sp3 = MhlId.substring(4, 6);
			String sp4 = MhlId.substring(6, 8);
			String sp5 = MhlId.substring(8, 10);
			String sp6 = MhlId.substring(10, 12);
			String keyCode = sp1 + "-" + sp2 + "-" + sp3 + "-" + sp4 + "-" + sp5 + "-" + sp6;
			return keyCode;
		}
		return null;
	}

	public static String readQuery(String QueryId) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "commonQuery.properties";
			input = Utils.class.getClassLoader().getResourceAsStream(filename);
			prop.load(input);
			return prop.getProperty(QueryId);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public static String readSqlRequest(String reqName) throws IOException {
		String sqlLine = null;
		InputStream input = null;
		try {
			if (sqlRequests == null) {
				String filename = "sqlRequests.sql";
				input = Utils.class.getClassLoader().getResourceAsStream(filename);
				Reader targetReader = new InputStreamReader(input);
				BufferedReader br = new BufferedReader(targetReader);
				StringBuffer sb = new StringBuffer();
				while ((sqlLine = br.readLine()) != null) {
					sb.append(sqlLine);
				}
				br.close();
				String[] inst = sb.toString().split(";");
				sqlRequests = new HashMap<>();
				for (String req : inst) {
					String[] reqVal = req.split(":=");
					String key = reqVal[0].trim();
					String value = reqVal[1];
					sqlRequests.put(key, value);
				}
				return sqlRequests.get(reqName);
			} else {
				return sqlRequests.get(reqName);
			}
		} catch (Exception e) {
			logger.error("readSqlRequest :" + reqName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return sqlLine;
	}

	////////////////////// convert Dates /////////////////////////////
	public static String convertDateToString(Date inputDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(inputDate);
	}

	public static String grigDatesConvert(Date calecderDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateHirgi = null;
		if (calecderDate != null) {
			String grigDate = sdf.format(calecderDate);
			System.out.println("calecderDate after formattnig  --> " + grigDate);
			dateHirgi = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(grigDate);
		}
		return dateHirgi;
	}

	public static Date convertGregStringToDate(String gregDate) throws Exception {
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date date = format.parse(gregDate);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static Date getCurrentGrigdate() throws Exception {
		Calendar clndr = Calendar.getInstance();
		Date date = clndr.getTime();
		return date;
	}

	public static Integer getCurrentHijriYear() throws Exception {
		Date date = getCurrentGrigdate();
		String hijri = null;

		try {
			hijri = Utils.grigDatesConvert(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] parts = hijri.split("/");
		Integer dd = Integer.parseInt(parts[0]);
		Integer MM = Integer.parseInt(parts[1]);
		Integer yyyy = Integer.parseInt(parts[2]);
		return yyyy;
	}

	public static String getDayOfWeek(String hijriDate) {
		String day = new String();
		String outDay = new String();
		try {
			// Date date = Utils.convertHDateToGDate(hijriDate);
			Date date = Utils.convertHDateToGDate(hijriDate);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
			String dt = sdf.format(date);
			String[] parts = dt.split("/");
			Integer dd = Integer.parseInt(parts[0]);
			Integer MM = Integer.parseInt(parts[1]);
			Integer yyyy = Integer.parseInt(parts[2]);
			LocalDate localDate = LocalDate.of(yyyy, MM, dd);
			DayOfWeek weekDay = localDate.getDayOfWeek();
			day = weekDay.toString();
			if (day.equals("SUNDAY")) {
				outDay = "الأحد";
			}
			if (day.equals("MONDAY")) {
				outDay = "الإثنين";
			}
			if (day.equals("TUESDAY")) {
				outDay = "الثلاثاء";
			}
			if (day.equals("WEDNESDAY")) {
				outDay = "الأربعاء";
			}
			if (day.equals("THURSDAY")) {
				outDay = "الخميس";
			}
			if (day.equals("FRIDAY")) {
				outDay = "الجمعة";
			}
			if (day.equals("SATURDAY")) {
				outDay = "السبت";
			}
			System.out.println("hijri is -- " + hijriDate + "and greogein is--" + date + " and day is " + weekDay);
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outDay;
	}

	public static String convertTimeHourToString(Date inputDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		return sdf.format(inputDate);
	}

	public static Date convertStringHourToTimeDate(String inputDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		try {
			date = sdf.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static void updateUIComponent(String ComponentName) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.update(ComponentName);

	}

	public static Users findCurrentUser() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			if (request == null) {
				return null;
			}
			HttpSession appsession = request.getSession(true);
			Users user = (Users) appsession.getAttribute("user");
			return user;
		} catch (Exception e) {
//			Users user = new Users();
//			user.setUserId(118);
//			return user;
		}
		return null;

	}

	public static List<Attachment> SaveAttachementsToFtpServer(List<AttachmentModel> attachments) {
		List<Attachment> myAttachs = new ArrayList<Attachment>();
		try {
			boolean resultUpload = uploadAttachedFiles(attachments);
			if (resultUpload) {
				for (AttachmentModel attachment : attachments) {
					Attachment attach = new Attachment();
					attach.setAttName(attachment.getRealName());
					attach.setAttSize(attachment.getAttachSize());
					attach.setRealName(attachment.getAttachRealName());
					myAttachs.add(attach);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return myAttachs;
	}

	public static boolean uploadAttachedFiles(List<AttachmentModel> attachments) {
		try {
			for (AttachmentModel attachment : attachments) {

				FtpTransferFile.uploadFile(attachment.getAttachStream(), attachment.getRealName());
			}
			return true;
		} catch (Exception e) {
			logger.error("uploadAttachedFiles  :" + e.getMessage());
		}
		return false;
	}

	public static String getDayDate(String dateCalendar) {
		try {
			String day = new String();
			// String myDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(hijriCalendar);
			Calendar cal = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date date = format.parse(dateCalendar);
			cal.setTime(date);
			int x = cal.get(Calendar.DAY_OF_WEEK);
			switch (x) {

			case 1:
				day = loadMessagesFromFile("sunday");
				break;
			case 2:
				day = loadMessagesFromFile("monday");

				break;
			case 3:
				day = loadMessagesFromFile("tuesday");
				break;
			case 4:
				day = loadMessagesFromFile("wednesday");
				break;
			case 5:
				day = loadMessagesFromFile("thursday");
				break;

			case 6:
				day = loadMessagesFromFile("friday");
				break;
			case 7:
				day = loadMessagesFromFile("saturday");
				break;
			default:
				break;
			}

			return day;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}
}
