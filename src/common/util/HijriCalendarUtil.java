package common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class HijriCalendarUtil {

	public static Map<String, String> findAllHijriMonths() {
		Map<String, String> Months = new LinkedHashMap<String, String>();
		Months.put("1-\u0645\u062d\u0631\u0645", "01");
		Months.put("2-\u0635\u0641\u0631", "02");
		Months.put("3-\u0631\u0628\u064a\u0639 \u0627\u0644\u0623\u0648\u0644", "03");
		Months.put("4-\u0631\u0628\u064a\u0639 \u0627\u0644\u062b\u0627\u0646\u064a", "04");
		Months.put("5-\u062c\u0645\u0627\u062f\u064a \u0627\u0644\u0623\u0648\u0644", "05");
		Months.put("6-\u062c\u0645\u0627\u062f\u064a \u0627\u0644\u062b\u0627\u0646\u064a", "06");
		Months.put("7-\u0631\u062c\u0628", "07");
		Months.put("8-\u0634\u0639\u0628\u0627\u0646", "08");
		Months.put("9-\u0631\u0645\u0636\u0627\u0646", "09");
		Months.put("10-\u0634\u0648\u0627\u0644", "10");
		Months.put("11-\u0630\u0648 \u0627\u0644\u0642\u0639\u062f\u0629 ", "11");
		Months.put("12-\u0630\u0648 \u0627\u0644\u062d\u062c\u0629", "12");
		return Months;
	}

	public static String ConvertHijriTogeorgianDate(String inputdate) throws ParseException {
		int total = 0;
		String in_date2;
		String in_dat;
		int l_curyear = Integer.parseInt(inputdate.substring(6));
		YearClass currentYear = null;
		int CurrentHYear = Integer.parseInt(inputdate.substring(6));

		for (YearClass item : loadYearsData()) {
			if (item.getYear() == CurrentHYear) {
				currentYear = new YearClass(item.getYear(), item.getMon01(), item.getMon02(), item.getMon03(),
						item.getMon04(), item.getMon05(), item.getMon06(), item.getMon07(), item.getMon08(),
						item.getMon09(), item.getMon10(), item.getMon11(), item.getMon12(), item.getYearStartDate());
			}
		}
		in_date2 = inputdate.replace("/", "");
		in_dat = inputdate.substring(6) + in_date2.substring(2, 4) + in_date2.substring(0, 2);

		for (int y = 1; y <= Integer.parseInt(in_date2.substring(2, 4)); y++) {

			total = Integer.parseInt(in_date2.substring(0, 2));
			if (y == 1) {
				total = total + 0;
				// System.err.println("--s-y ->" +total);
			}
			if (y == 2) {
				total = total + currentYear.getMon01();
				// System.err.println("-e--y ->" +total);
			}
			if (y == 3) {
				total = total + currentYear.getMon01() + currentYear.getMon02();
				// System.err.println("---y ->" +total);
			}
			if (y == 4) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03();
				// System.err.println("---y ->" +total);
			}
			if (y == 5) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04();
				// System.err.println("---y ->" +total);
			}
			if (y == 6) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05();
				// System.err.println("---y ->" +total);
			}
			if (y == 7) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06();
				// System.err.println("---y ->" +total);
			}
			if (y == 8) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06()
						+ currentYear.getMon07();
				// System.err.println("---y ->" +total);
			}
			if (y == 9) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06()
						+ currentYear.getMon07() + currentYear.getMon08();
				// System.err.println("---y ->" +total);
			}
			if (y == 10) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06()
						+ currentYear.getMon07() + currentYear.getMon08() + currentYear.getMon09();
				// System.err.println("---y ->" +total);
			}
			if (y == 11) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06()
						+ currentYear.getMon07() + currentYear.getMon08() + currentYear.getMon09()
						+ currentYear.getMon10();
				// System.err.println("---y ->" +total);
			}
			if (y == 12) {
				total = total + currentYear.getMon01() + currentYear.getMon02() + currentYear.getMon03()
						+ currentYear.getMon04() + currentYear.getMon05() + currentYear.getMon06()
						+ currentYear.getMon07() + currentYear.getMon08() + currentYear.getMon09()
						+ currentYear.getMon10() + currentYear.getMon11();
				// System.err.println("---y ->" +total);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d = sdf.parse(currentYear.getYearStartDate());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, total - 1);
		Date targetDay = c.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

		return format1.format(c.getTime());
	}

	public static String ConvertgeorgianDatetoHijriDate(String inputDate) throws ParseException {
		String OutDate = "";
		int dd = 0;
		int mm = 0;
		int df = 0;
		String mmc;
		String ddc;
		int yyyy = 0;
		YearClass CurrentYear = new YearClass();
		YearClass NextYear = new YearClass();
		List<YearClass> YList = loadYearsData();
		SimpleDateFormat sdfinput = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdfinput.parse(inputDate);
		for (YearClass item : loadYearsData()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date1 = sdf.parse(item.getYearStartDate());
			Calendar c = Calendar.getInstance();
			c.setTime(date1);
			c.add(Calendar.YEAR, 1);
			Date date2 = c.getTime();
			if (date.after(date1) && date.before(date2)) {
				CurrentYear = new YearClass(item.getYear(), item.getMon01(), item.getMon02(), item.getMon03(),
						item.getMon04(), item.getMon05(), item.getMon06(), item.getMon07(), item.getMon08(),
						item.getMon09(), item.getMon10(), item.getMon11(), item.getMon12(), item.getYearStartDate());
			}
		}
		for (YearClass item : loadYearsData()) {
			if (item.getYear() == CurrentYear.getYear() + 1) {
				NextYear = new YearClass(item.getYear(), item.getMon01(), item.getMon02(), item.getMon03(),
						item.getMon04(), item.getMon05(), item.getMon06(), item.getMon07(), item.getMon08(),
						item.getMon09(), item.getMon10(), item.getMon11(), item.getMon12(), item.getYearStartDate());
			}

		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d = sdf.parse(CurrentYear.getYearStartDate());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, 0);
		Date dayAfterstart = c.getTime();
		DateTime dt1 = new DateTime(date);
		DateTime dt2 = new DateTime(dayAfterstart);
		// System.out.print(Days.daysBetween(dt1, dt1).getDays() + 1 + " days,
		// ");
		df = Days.daysBetween(dt2, dt1).getDays() + 1;
		yyyy = CurrentYear.getYear();
		if (df <= CurrentYear.getMon01()) {
			mm = 1;
			dd = df;
		} else {
			df = df - CurrentYear.getMon01();
			if (df <= CurrentYear.getMon02()) {
				mm = 2;
				dd = df;
			} else {
				df = df - CurrentYear.getMon02();
				if (df <= CurrentYear.getMon03()) {
					mm = 3;
					dd = df;
				} else {
					df = df - CurrentYear.getMon03();
					if (df <= CurrentYear.getMon04()) {
						mm = 4;
						dd = df;
					} else {
						df = df - CurrentYear.getMon04();
						if (df <= CurrentYear.getMon05()) {
							mm = 5;
							dd = df;
						} else {
							df = df - CurrentYear.getMon05();
							if (df <= CurrentYear.getMon06()) {
								mm = 6;
								dd = df;
							} else {
								df = df - CurrentYear.getMon06();
								if (df <= CurrentYear.getMon07()) {
									mm = 7;
									dd = df;
								} else {
									df = df - CurrentYear.getMon07();
									if (df <= CurrentYear.getMon08()) {
										mm = 8;
										dd = df;
									} else {
										df = df - CurrentYear.getMon08();
										if (df <= CurrentYear.getMon09()) {
											mm = 9;
											dd = df;
										} else {
											df = df - CurrentYear.getMon09();
											if (df <= CurrentYear.getMon10()) {
												mm = 10;
												dd = df;
											} else {
												df = df - CurrentYear.getMon10();
												if (df <= CurrentYear.getMon11()) {
													mm = 11;
													dd = df;
												} else {
													df = df - CurrentYear.getMon11();
													if (df <= CurrentYear.getMon12()) {
														mm = 12;
														dd = df;
													} else {
														df = df - CurrentYear.getMon12();
														yyyy = NextYear.getYear();
														if (df <= NextYear.getMon01()) {
															mm = 01;
															dd = df;
														}

													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		String x = String.format("%02d", dd);
		if (x.length() < 2) {
			// System.out.println(x + " " + x.length());
			ddc = "X";
		} else {
			ddc = x.trim();
		}
		String y = String.format("%02d", mm);
		if (x.length() < 2) {
			mmc = "X";
		} else {
			mmc = y.trim();
		}
		return ddc + "/" + mmc + "/" + yyyy;
	}


	public static String findCurrentHijriDate() {
		String Outdate = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			String x = ConvertgeorgianDatetoHijriDate(dateFormat.format(date));
			Outdate = x;
		} catch (ParseException e) {
		}

		return Outdate;
	}

	public static String findCurrentGeorgianDate() {
		String Outdate = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			Outdate = dateFormat.format(date);
		} catch (Exception e) {
		}

		return Outdate;
	}

	public static String findCurrentYear() {
		String OutYear = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			String x = ConvertgeorgianDatetoHijriDate(dateFormat.format(date));
			OutYear = x.substring(6);
		} catch (ParseException e) {
		}

		return OutYear;
	}

	public static String findCurrentMonth() {
		String OutMonth = "";
		int CurrentHijriMonth = 0;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			String x = ConvertgeorgianDatetoHijriDate(dateFormat.format(date));
			CurrentHijriMonth = Integer.parseInt(x.substring(3, 5));
		} catch (ParseException e) {
		}
		OutMonth = String.format("%02d", CurrentHijriMonth);
		return OutMonth;
	}

	public static int findFirstMonthDay(String InputDate) {
		int outDayMumber = 0;
		String inDate = "";
		try {
			inDate = ConvertHijriTogeorgianDate("01" + InputDate.substring(2));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(inDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayNumber = c.get(Calendar.DAY_OF_WEEK);
			if (dayNumber == 1) {
				outDayMumber = 2;
			}
			if (dayNumber == 2) {
				outDayMumber = 3;
			}
			if (dayNumber == 3) {
				outDayMumber = 4;
			}
			if (dayNumber == 4) {
				outDayMumber = 5;
			}
			if (dayNumber == 5) {
				outDayMumber = 6;
			}
			if (dayNumber == 6) {
				outDayMumber = 7;
			}
			if (dayNumber == 7) {
				outDayMumber = 1;
			}

			// System.out.println(c.get(Calendar.DAY_OF_WEEK));
		} catch (Exception e) {
		}
		// System.out.println(inDate);
		return outDayMumber;
	}

	public static int findMonthLength(int Year, int Month) {
		int monthLen = 0;
		if (Month > 0 && Month < 13) {
			for (YearClass item : loadYearsData()) {
				if (item.getYear() == Year) {
					if (Month == 1) {
						monthLen = item.getMon01();
					}
					if (Month == 2) {
						monthLen = item.getMon02();
					}
					if (Month == 3) {
						monthLen = item.getMon03();
					}
					if (Month == 4) {
						monthLen = item.getMon04();
					}
					if (Month == 5) {
						monthLen = item.getMon05();
					}
					if (Month == 6) {
						monthLen = item.getMon06();
					}
					if (Month == 7) {
						monthLen = item.getMon07();
					}
					if (Month == 8) {
						monthLen = item.getMon08();
					}
					if (Month == 9) {
						monthLen = item.getMon09();
					}
					if (Month == 10) {
						monthLen = item.getMon10();
					}
					if (Month == 11) {
						monthLen = item.getMon11();
					}
					if (Month == 12) {
						monthLen = item.getMon12();
					}
				}
			}
		}
		return monthLen;
	}

	public static Map<String, String> findAllHijriYears() {
		Map<String, String> x = new LinkedHashMap<String, String>();
		for (YearClass yc : loadYearsData()) {
			x.put(yc.getYear() + "", yc.getYear() + "");
		}
		return x;
	}

	public static String findCurrentHijriWithTimeStamp() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return findCurrentHijriDate() + " " + sdf.format(cal.getTime());

	}

	public static String findCurrentTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());

	}

	public static String addDaysToHijriDate(String DateString, int Days) {
		String retString = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(DateString));
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, Days);
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			retString = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(format1.format(c.getTime()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retString;
	}

	public static String addMonthsToHijriDate(String DateString, int Months) {
		String retString = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(DateString));
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, Months);
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			retString = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(format1.format(c.getTime()));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return retString;
	}

	public static String addYearsToHijriDate(String DateString, int Years) {
		String retString = "";
		retString = DateString.substring(0, 6) + (Integer.parseInt(DateString.substring(6)) + Years);
		return retString;
	}

	public static int findDatesDifferenceInDays(String minHijriDate, String maxHijriDate) {
		int x = 0;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = sdf.parse(minHijriDate);
			Date date2 = sdf.parse(maxHijriDate);
			int diff = Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
			x = diff;
		} catch (ParseException ex) {
			Logger.getLogger(HijriCalendarUtil.class.getName()).log(Level.SEVERE, null, ex);
		}

		return x;
	}

	public static int findDatesDifferenceInMonths(String minHijriDate, String maxHijriDate) {
		int x = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = sdf.parse(minHijriDate);
			Date date2 = sdf.parse(maxHijriDate);
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(date1);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(date2);

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			System.err.println(diffYear);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
			x = diffMonth;
		} catch (Exception e) {
		}
		return x;
	}

	public static int findDatesDifferenceInYears(String minHijriDate, String maxHijriDate) {
		int x = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = sdf.parse(minHijriDate);
			Date date2 = sdf.parse(maxHijriDate);
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(date1);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(date2);

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			x = diffYear;
		} catch (Exception e) {
		}
		return x;
	}

	public static class YearClass {

		private int year;
		private int mon01;
		private int mon02;
		private int mon03;
		private int mon04;
		private int mon05;
		private int mon06;
		private int mon07;
		private int mon08;
		private int mon09;
		private int mon10;
		private int mon11;
		private int mon12;
		private String YearStartDate;

		public YearClass() {
		}

		public YearClass(int year, int mon01, int mon02, int mon03, int mon04, int mon05, int mon06, int mon07,
				int mon08, int mon09, int mon10, int mon11, int mon12, String YearStartDate) {
			super();
			this.year = year;
			this.mon01 = mon01;
			this.mon02 = mon02;
			this.mon03 = mon03;
			this.mon04 = mon04;
			this.mon05 = mon05;
			this.mon06 = mon06;
			this.mon07 = mon07;
			this.mon08 = mon08;
			this.mon09 = mon09;
			this.mon10 = mon10;
			this.mon11 = mon11;
			this.mon12 = mon12;
			this.YearStartDate = YearStartDate;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getYear() {
			return year;
		}

		public void setMon01(int mon01) {
			this.mon01 = mon01;
		}

		public int getMon01() {
			return mon01;
		}

		public void setMon02(int mon02) {
			this.mon02 = mon02;
		}

		public int getMon02() {
			return mon02;
		}

		public void setMon03(int mon03) {
			this.mon03 = mon03;
		}

		public int getMon03() {
			return mon03;
		}

		public void setMon04(int mon04) {
			this.mon04 = mon04;
		}

		public int getMon04() {
			return mon04;
		}

		public void setMon05(int mon05) {
			this.mon05 = mon05;
		}

		public int getMon05() {
			return mon05;
		}

		public void setMon06(int mon06) {
			this.mon06 = mon06;
		}

		public int getMon06() {
			return mon06;
		}

		public void setMon07(int mon07) {
			this.mon07 = mon07;
		}

		public int getMon07() {
			return mon07;
		}

		public void setMon08(int mon08) {
			this.mon08 = mon08;
		}

		public int getMon08() {
			return mon08;
		}

		public void setMon09(int mon09) {
			this.mon09 = mon09;
		}

		public int getMon09() {
			return mon09;
		}

		public void setMon10(int mon10) {
			this.mon10 = mon10;
		}

		public int getMon10() {
			return mon10;
		}

		public void setMon11(int mon11) {
			this.mon11 = mon11;
		}

		public int getMon11() {
			return mon11;
		}

		public void setMon12(int mon12) {
			this.mon12 = mon12;
		}

		public int getMon12() {
			return mon12;
		}

		public void setYearStartDate(String YearStartDate) {
			this.YearStartDate = YearStartDate;
		}

		public String getYearStartDate() {
			return YearStartDate;
		}

	}

	public static List<YearClass> loadYearsData() {
		List<YearClass> yearsList = new ArrayList<YearClass>();

		yearsList.add(new YearClass(1413, 29, 30, 29, 29, 30, 30, 29, 30, 30, 29, 30, 30, "1992/07/02"));
		yearsList.add(new YearClass(1414, 29, 29, 30, 29, 29, 30, 29, 30, 29, 30, 29, 30, "1993/06/22"));
		yearsList.add(new YearClass(1415, 29, 30, 29, 30, 29, 29, 30, 29, 30, 30, 29, 30, "1994/06/10"));
		yearsList.add(new YearClass(1416, 30, 29, 30, 29, 30, 29, 30, 29, 29, 30, 29, 30, "1995/05/30"));
		yearsList.add(new YearClass(1417, 30, 29, 30, 29, 30, 30, 29, 30, 29, 30, 29, 29, "1996/05/18"));
		yearsList.add(new YearClass(1418, 30, 29, 30, 29, 30, 30, 30, 29, 30, 29, 30, 29, "1997/05/07"));
		yearsList.add(new YearClass(1419, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, 30, "1998/04/27"));
		yearsList.add(new YearClass(1420, 29, 30, 29, 29, 30, 29, 30, 30, 30, 30, 29, 30, "1999/04/17"));
		yearsList.add(new YearClass(1421, 29, 29, 30, 29, 29, 29, 30, 30, 30, 30, 29, 30, "2000/04/06"));
		yearsList.add(new YearClass(1422, 30, 29, 29, 30, 29, 29, 29, 30, 30, 30, 29, 30, "2001/03/26"));
		yearsList.add(new YearClass(1400, 29, 29, 29, 29, 29, 30, 30, 30, 30, 30, 30, 30, "1979/11/22"));
		yearsList.add(new YearClass(1401, 29, 30, 29, 29, 30, 30, 29, 30, 29, 30, 29, 30, "1980/11/11"));
		yearsList.add(new YearClass(1402, 29, 29, 29, 30, 30, 30, 29, 29, 30, 29, 30, 29, "1981/10/31"));
		yearsList.add(new YearClass(1403, 30, 30, 29, 29, 30, 30, 29, 30, 29, 30, 29, 30, "1982/10/19"));
		yearsList.add(new YearClass(1404, 30, 29, 29, 29, 29, 30, 30, 30, 30, 30, 29, 30, "1983/10/09"));
		yearsList.add(new YearClass(1405, 29, 30, 30, 29, 30, 29, 30, 30, 29, 30, 29, 30, "1984/09/28"));
		yearsList.add(new YearClass(1406, 29, 29, 30, 29, 30, 29, 30, 29, 30, 29, 29, 30, "1985/09/18"));
		yearsList.add(new YearClass(1407, 29, 29, 29, 30, 30, 30, 29, 30, 29, 29, 30, 29, "1986/09/06"));
		yearsList.add(new YearClass(1408, 30, 29, 30, 29, 30, 29, 30, 29, 29, 30, 29, 30, "1987/08/25"));
		yearsList.add(new YearClass(1409, 30, 29, 30, 30, 29, 30, 29, 30, 29, 29, 30, 29, "1988/08/13"));
		yearsList.add(new YearClass(1410, 30, 29, 30, 30, 30, 29, 30, 29, 30, 29, 29, 30, "1989/08/02"));
		yearsList.add(new YearClass(1411, 29, 30, 29, 30, 30, 29, 30, 30, 29, 30, 29, 30, "1990/07/23"));
		yearsList.add(new YearClass(1412, 30, 30, 29, 30, 29, 30, 30, 29, 30, 29, 30, 29, "1991/07/13"));
		yearsList.add(new YearClass(1423, 30, 29, 30, 29, 30, 29, 29, 30, 29, 30, 29, 30, "2002/03/15"));
		yearsList.add(new YearClass(1424, 30, 29, 30, 30, 29, 30, 29, 29, 30, 29, 30, 29, "2003/03/04"));
		yearsList.add(new YearClass(1425, 30, 29, 30, 30, 29, 30, 29, 30, 30, 29, 30, 29, "2004/02/21"));
		yearsList.add(new YearClass(1399, 30, 30, 30, 29, 29, 30, 29, 29, 29, 30, 30, 30, "1978/12/02"));
		yearsList.add(new YearClass(1398, 30, 29, 29, 30, 30, 29, 30, 29, 30, 29, 30, 29, "1977/12/13"));
		yearsList.add(new YearClass(1397, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1976/12/18"));
		yearsList.add(new YearClass(1396, 30, 30, 30, 30, 29, 29, 29, 29, 29, 30, 30, 30, "1975/12/24"));
		yearsList.add(new YearClass(1395, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1975/01/03"));
		yearsList.add(new YearClass(1394, 30, 30, 29, 29, 29, 29, 29, 29, 29, 29, 30, 30, "1974/01/08"));
		yearsList.add(new YearClass(1393, 30, 30, 29, 29, 29, 29, 29, 29, 29, 29, 30, 30, "1973/01/21"));
		yearsList.add(new YearClass(1392, 30, 29, 29, 29, 29, 29, 29, 29, 29, 30, 30, 30, "1972/02/04"));
		yearsList.add(new YearClass(1391, 30, 30, 29, 29, 29, 29, 29, 29, 30, 30, 30, 30, "1971/02/17"));
		yearsList.add(new YearClass(1390, 30, 30, 29, 29, 29, 29, 29, 30, 30, 30, 30, 30, "1970/02/28"));
		yearsList.add(new YearClass(1389, 30, 30, 29, 30, 30, 29, 29, 29, 29, 30, 30, 30, "1969/03/10"));
		yearsList.add(new YearClass(1388, 30, 29, 29, 30, 30, 30, 30, 30, 29, 30, 30, 29, "1968/03/20"));
		yearsList.add(new YearClass(1387, 30, 30, 30, 29, 29, 29, 29, 29, 30, 30, 30, 30, "1967/03/30"));
		yearsList.add(new YearClass(1386, 30, 30, 30, 29, 29, 29, 29, 29, 30, 30, 30, 30, "1966/04/09"));
		yearsList.add(new YearClass(1385, 30, 30, 29, 29, 29, 29, 29, 30, 30, 30, 30, 30, "1965/04/19"));
		yearsList.add(new YearClass(1384, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1965/04/19"));
		yearsList.add(new YearClass(1383, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1964/04/24"));
		yearsList.add(new YearClass(1350, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1931/10/15"));
		yearsList.add(new YearClass(1351, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1932/10/09"));
		yearsList.add(new YearClass(1352, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1933/10/04"));
		yearsList.add(new YearClass(1353, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1934/09/29"));
		yearsList.add(new YearClass(1354, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1935/09/24"));
		yearsList.add(new YearClass(1355, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1936/09/18"));
		yearsList.add(new YearClass(1356, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1937/09/13"));
		yearsList.add(new YearClass(1357, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1938/09/08"));
		yearsList.add(new YearClass(1358, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1939/09/03"));
		yearsList.add(new YearClass(1359, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1940/08/28"));
		yearsList.add(new YearClass(1360, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1941/08/23"));
		yearsList.add(new YearClass(1361, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1942/08/18"));
		yearsList.add(new YearClass(1362, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1943/08/13"));
		yearsList.add(new YearClass(1363, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1944/08/07"));
		yearsList.add(new YearClass(1364, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1945/08/02"));
		yearsList.add(new YearClass(1365, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1946/07/28"));
		yearsList.add(new YearClass(1366, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1947/07/23"));
		yearsList.add(new YearClass(1367, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1948/07/17"));
		yearsList.add(new YearClass(1368, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1949/07/12"));
		yearsList.add(new YearClass(1369, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1950/07/07"));
		yearsList.add(new YearClass(1370, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1951/07/02"));
		yearsList.add(new YearClass(1371, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1952/06/26"));
		yearsList.add(new YearClass(1372, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1953/06/21"));
		yearsList.add(new YearClass(1373, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1954/06/16"));
		yearsList.add(new YearClass(1374, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1955/06/11"));
		yearsList.add(new YearClass(1375, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1956/06/05"));
		yearsList.add(new YearClass(1376, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1957/05/31"));
		yearsList.add(new YearClass(1377, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1958/05/26"));
		yearsList.add(new YearClass(1378, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1959/05/21"));
		yearsList.add(new YearClass(1379, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1960/05/15"));
		yearsList.add(new YearClass(1380, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1961/05/10"));
		yearsList.add(new YearClass(1381, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1962/05/05"));
		yearsList.add(new YearClass(1382, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, "1963/04/30"));
		yearsList.add(new YearClass(1426, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, 30, "2005/02/10"));
		yearsList.add(new YearClass(1428, 30, 29, 29, 30, 29, 29, 30, 30, 30, 29, 30, 30, "2007/01/20"));
		yearsList.add(new YearClass(1429, 29, 30, 29, 29, 30, 29, 29, 30, 30, 29, 30, 30, "2008/01/10"));
		yearsList.add(new YearClass(1430, 29, 30, 30, 29, 29, 30, 29, 30, 29, 30, 29, 30, "2008/12/29"));
		yearsList.add(new YearClass(1431, 29, 30, 30, 29, 30, 29, 30, 29, 30, 29, 29, 29, "2009/12/18"));
		yearsList.add(new YearClass(1433, 30, 29, 30, 30, 29, 30, 30, 29, 30, 29, 30, 29, "2011/11/26"));
		yearsList.add(new YearClass(1434, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, 29, "2012/11/15"));
		yearsList.add(new YearClass(1435, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30, 29, 30, "2013/11/04"));
		yearsList.add(new YearClass(1436, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, "2014/10/25"));
		yearsList.add(new YearClass(1437, 30, 29, 30, 30, 29, 29, 30, 29, 30, 29, 29, 30, "2015/10/14"));
		yearsList.add(new YearClass(1438, 30, 29, 30, 30, 30, 29, 29, 30, 29, 29, 30, 29, "2016/10/02"));
		yearsList.add(new YearClass(1439, 30, 29, 30, 30, 30, 29, 30, 29, 30, 29, 29, 30, "2017/09/21"));
		yearsList.add(new YearClass(1440, 29, 30, 29, 30, 30, 30, 29, 30, 29, 30, 29, 29, "2018/09/11"));
		yearsList.add(new YearClass(1432, 29, 30, 30, 30, 29, 30, 29, 30, 29, 30, 29, 29, "2010/12/07"));
		yearsList.add(new YearClass(1427, 29, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, "2006/01/31"));
		yearsList.add(new YearClass(1441, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, 30, 29, "2019/08/31"));
		yearsList.add(new YearClass(1442, 29, 30, 29, 30, 29, 30, 29, 30, 30, 29, 30, 29, "2020/08/20"));
		yearsList.add(new YearClass(1443, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30, "2021/08/09"));
		yearsList.add(new YearClass(1444, 29, 30, 29, 30, 30, 29, 29, 30, 29, 30, 29, 30, "2022/07/30"));
		yearsList.add(new YearClass(1445, 29, 30, 30, 30, 29, 30, 29, 29, 30, 29, 29, 30, "2023/07/19"));
		yearsList.add(new YearClass(1446, 29, 30, 30, 29, 29, 30, 30, 29, 29, 30, 29, 29, "2024/07/07"));
		yearsList.add(new YearClass(1447, 30, 29, 30, 30, 30, 29, 30, 29, 30, 29, 30, 29, "2025/06/26"));
		yearsList.add(new YearClass(1448, 29, 30, 29, 30, 30, 29, 30, 30, 29, 30, 29, 29, "2026/06/16"));
		yearsList.add(new YearClass(1449, 29, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, "2027/06/06"));
//		yearsList.add(new YearClass(1450, 29, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, "2028/05/26"));
//		yearsList.add(new YearClass(1451, 29, 29, 30, 29, 30, 29, 30, 30, 29, 30, 30, 29, "2029/05/16"));
		return yearsList;

	}

	public static String findCurrentDay() {
		String OutDay = "";
		int CurrentHijriMonth = 0;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			String x = ConvertgeorgianDatetoHijriDate(dateFormat.format(date));
			CurrentHijriMonth = Integer.parseInt(x.substring(0, 2));
		} catch (ParseException e) {
		}
		OutDay = String.format("%02d", CurrentHijriMonth);
		return OutDay;
	}

	public static Integer findCurrentGeorgianYear() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			String dateFormated = dateFormat.format(date);
			return Integer.parseInt(dateFormated.substring(6));
		} catch (Exception e) {
		}

		return null;
	}
}
