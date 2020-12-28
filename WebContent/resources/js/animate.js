    
document.getElementById("about_us").addEventListener("click",()=>{
	alert("1");
	document.getElementById("three-column").style.backgroundColor="yellow";
//	document.getElementById("cntus").style.backgroundColor="white";
})
document.getElementById("contact_us").addEventListener("click",()=>{
//	document.getElementById("cntus").style.backgroundColor="yellow";
	document.getElementById("three-column").style.backgroundColor="white";
})


var slideIndex = 0;
showSlides();

function showSlides() {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  slideIndex++;
  if (slideIndex > slides.length) {slideIndex = 1}
  slides[slideIndex-1].style.display = "block";
  setTimeout(showSlides, 2000); // Change image every 2 seconds
}
