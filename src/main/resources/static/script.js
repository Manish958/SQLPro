document.getElementById("refresh").addEventListener("click",()=>{
			document.getElementById('input').value = "";
		})
        document.getElementById('btn').addEventListener('click',()=>{
              document.body.classList.toggle('dark')
             document.getElementById('input').classList.toggle('dark')
             document.getElementById('refresh').classList.toggle('dark');
			 console.log("refresh clicked")
			
			 
        });
        document.getElementById("export-btn").addEventListener("click", function () {
        let table = document.querySelector("table");
        let rows = table.querySelectorAll("tr");
        let csvContent = "";

        rows.forEach(row => {
            let cols = row.querySelectorAll("th, td");
            let rowData = [];
            cols.forEach(col => rowData.push(col.innerText));
            csvContent += rowData.join(",") + "\n";
        });

        let blob = new Blob([csvContent], { type: "text/csv" });
        let link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "SQL_Results.csv";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });
	document.addEventListener("DOMContentLoaded", function () {
	    document.getElementById("exportBtn").addEventListener("click", function () {
	        exportTableToCSV("query_results.csv");  // HIGHLIGHTED
	    });
	});

	
	