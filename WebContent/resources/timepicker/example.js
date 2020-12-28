///* global hljs, TimePicker */
//
//var time2 = document.getElementById('time2');
//var timepicker = new TimePicker(['time2', 'link'], {
//  lang: 'en',
//  theme: 'blue-grey'
//});
//timepicker.on('change', function (evt) {
//	alert(evt.element.id)
//  var value = (evt.hour || '00') + ':' + (evt.minute || '00');
//
//  if (evt.element.id === 'link') {
//    time2.value = value;
//  } else {
//    evt.element.value = value;
//  }
//});
//
//hljs.configure({ tabReplace: '  ' });
//hljs.initHighlightingOnLoad();
var time2 = document.getElementById('time2');
var time3 = document.getElementById('time3');
var timepicker = new TimePicker(['link','link2'], {
  lang: 'ar',
  theme: 'blue-grey'
});
timepicker.on('change', function (evt) {
  var value = (evt.hour || '00') + ':' + (evt.minute || '00');

  if (evt.element.id === 'link') {
    time2.value = value;
  }
  else if(evt.element.id === 'link2'){
	  time3.value = value;
  }
  else {
    evt.element.value = value;
  }
});

