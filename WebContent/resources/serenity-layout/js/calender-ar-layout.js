function loadCalendar() {
	
	$(".form-controlCal").focus(function() {
	$('.calendars-cmd-today ').html('اليوم');
	$('.calendars-cmd-prev').html('السابق >');
	$('.calendars-cmd-next').html(' > اللاحق ');
	$('.calendars-ctrl').css('display', 'none');
	$('.calendars-month').css('width', '244px');
	$('.calendars-month').css('float', 'right');
	$('.calendars').css('width', '245px');

});

$(".form-controlCal").calendarsPicker({
	calendar : $.calendars.instance('ummalqura'),
	language : 'ar'

});

$(".form-controlCal").calendarsPicker({
	calendar : $.calendars.instance('ummalqura'),
	language : 'ar'

});
}
$(".form-controlCal").calendarsPicker({
	calendar : $.calendars.instance('ummalqura'),
	language : 'ar'

});

$(".form-controlCal").calendarsPicker({
	calendar : $.calendars.instance('ummalqura'),
	language : 'ar'

});
PrimeFaces.locales['ar'] = {
	closeText : 'إغلق',
	prevText : 'إلى الخلف',
	nextText : 'إلى الأمام',
	currentText : 'بداية',
	monthNames : [ 'يناير', 'فبراير', 'مارس', 'ابريل', 'مايو', 'يونيو',
			'يوليو', 'أغسطس', 'سبتمبر', 'أكتوبر', 'نوفمبر', 'ديسمبر' ],
	monthNamesShort : [ 'يناير', 'فبراير', 'مارس', 'ابريل', 'مايو', 'يونيو',
			'يوليو', 'أغسطس', 'سبتمبر', 'أكتوبر', 'نوفمبر', 'ديسمبر' ],
	dayNames : [ 'يوم الأحد‎', 'يوم الإثنين‎', 'يوم الثلاثاء‎',
			'‏يوم الأَرْبعاء‎', '‏يوم الخَمِيس‎', 'يوم الجُمْعَة‎‎',
			'يوم السَّبْت' ],
	dayNamesShort : [ 'أحد‎', 'إثنين‎', 'ثلاثاء‎', 'أَرْبعاء‎', 'خَمِيس‎',
			'جُمْعَة‎‎', 'سَّبْت' ],
	dayNamesMin : [ 'أحد‎', 'إثنين‎', 'ثلاثاء‎', 'أَرْبعاء‎', 'خَمِيس‎',
			'جُمْعَة‎‎', 'سَّبْت' ],
	weekHeader : 'الأسبوع',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'الوقت فقط',
	timeText : 'الوقت',
	hourText : 'ساعة',
	minuteText : 'دقيقة',
	secondText : 'ثانية',
	ampm : false,
	month : 'الشهر',
	week : 'الأسبوع',
	day : 'اليوم',
	allDayText : 'سا ئراليوم'
};
function openPopup() {
	document.getElementById('time2').value = null
	document.getElementById('time3').value = null
	$('#myScaleModal').modal('show');
}
function closePopup() {
	$('#myScaleModal').modal('hide');
}

$(".form-controlCal").focus(function() {
	$('.calendars-cmd-today ').html('اليوم');
	$('.calendars-cmd-prev').html('السابق >');
	$('.calendars-cmd-next').html(' > اللاحق ');
	$('.calendars-ctrl').css('display', 'none');
	$('.calendars-month').css('width', '244px');
	$('.calendars-month').css('float', 'right');
	$('.calendars').css('width', '245px');

});
