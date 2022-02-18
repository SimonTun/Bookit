let dt = new Date();
let month = dt.getMonth();
let year = dt.getFullYear();
let currentId = sessionStorage.getItem("sessionId")
console.log(currentId)


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

//loopar igenom kalenderdagarna och anger dagens datum
   for (i = 1; i <= endDate; i++) {
       if (i == today.getDate() && dt.getMonth() == today.getMonth()) cells += "<div class='today' id= "+ i +
                                                                    " onclick=availableTimes('"+i+"')>" + i + "</div>";

        else if (i == currentId) cells += "<div class='clicked' id= "+ i +
        " onclick=availableTimes('"+i+"')>" + i + "</div>";

        else if (i < today.getDate() && dt.getMonth() == today.getMonth()) cells += "<div class='prev_date'>" + i + "</div>";

       else
             cells += "<div class='day' id= "+ i +" onclick=availableTimes('"+i+"')>" + i + "</div>";
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
window.location.href="http://localhost:8080/bookIt/?date=" + bookingDate +"";

sessionStorage.setItem("sessionId",id)



}

