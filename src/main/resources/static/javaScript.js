

let dt = new Date();

function renderDate() {
   dt.setDate(1);
   let day = dt.getDay();
   let today = new Date();
   let endDate = new Date(dt.getFullYear(), dt.getMonth() + 1,0).getDate();
   let prevDate = new Date(dt.getFullYear(), dt.getMonth(), 0).getDate();

   let months = ["January", "February", "March", "April", "May", "June", "July",
               "August", "September", "October", "November","December"]

   document.getElementById("month").innerHTML = months[dt.getMonth()];
   document.getElementById("date_str").innerHTML = dt.toDateString();

   let cells = "";
   for (x = day; x > 0; x--) {
       cells += "<div class='prev_date'>" + (prevDate - x + 1) + "</div>";
   }

   console.log(day);
   for (i = 1; i <= endDate; i++) {
       if (i == today.getDate() && dt.getMonth() == today.getMonth()) cells += "<div class='today'>" + i + "</div>";
       else
           cells += "<div>" + i + "</div>";
   }

   document.getElementsByClassName("days")[0].innerHTML = cells;
}

function moveDate(para) {
   if(para == "prev") {
       dt.setMonth(dt.getMonth() - 1);
   }
   else if(para == 'next') {
       dt.setMonth(dt.getMonth() + 1);
   }
   renderDate();
}

