

let dt = new Date();
let month = dt.getMonth();
let year = dt.getFullYear();


function renderDate() {
   dt.setDate(1);
   let day = dt.getDay();
   let today = new Date();
   let endDate = new Date(dt.getFullYear(), dt.getMonth() + 1,0).getDate();
   let prevDate = new Date(dt.getFullYear(), dt.getMonth(), 0).getDate();

   let months = ["Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli",
                  "Augusti", "September", "Oktober", "November", "December"]

   document.getElementById("month").innerHTML = months[dt.getMonth()];
   document.getElementById("date_str").innerHTML = dt.toDateString();

   let cells = "";
   for (x = day; x > 1; x--) {
       cells += "<div class='prev_date'>" + (prevDate - x + 1) + "</div>";
   }

   console.log(day);
   for (i = 1; i <= endDate; i++) {
       if (i == today.getDate() && dt.getMonth() == today.getMonth()) cells += "<div class='today'>" + i + "</div>";
       else
             cells += "<div class='day' id= "+ i +" onclick=availableTimes('"+i+"')>" + i + "</div>";

// under här försökte jag få in länken direkt men det gick inte så vi går via en funktion istället.
//           cells += "<div class='day' id= "+ i +" th:onclick=window.location.href="/?=2018-03-10">" + i + "</div>";


//availableTimes('"+i+"')>" + i + "


   }

   document.getElementsByClassName("days")[0].innerHTML = cells;
}

function moveDate(para) {
   if(para == "prev") {
       dt.setMonth(dt.getMonth() - 1);
       month = dt.getMonth();
   }
   else if(para == 'next') {
       dt.setMonth(dt.getMonth() + 1);
       month = dt.getMonth();
   }
   renderDate();
}

function availableTimes (id){
let bookingDate = year + "-" + (month+1) + "-" + id;
console.log(bookingDate);
window.location.href="http://localhost:8080/?date=" + bookingDate +"";

}


//försökte med en "click- listener" men fick inte det att fungera

//const dateSelected = document.getElementsByClassName('day');
//console.log(dateSelected)
//
//dateSelected.forEach((dateSelected) => {
//    dateSelected.addEventListener('click', (event) => {
//        console.log(event.currentTarget.dataset.date);
//        console.log(dateSelected);
//        console.log("HEJ!")
//    });
//});

