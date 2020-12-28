/* global hljs, TimePicker */

var time3 = document.getElementById('time3');
var timepicker1 = new TimePicker(['time3', 'test'], {
  lang: 'en',
  theme: 'blue-grey'
});
timepicker1.on('change', function (evt) {
	alert("sdas"+evt.element.id);
  var value = (evt.hour || '00') + ':' + (evt.minute || '00');

  if (evt.element.id === 'test') {
	  time3.value = value;
  } else {
    evt.element.value = value;
  }
});

