
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("export-btn").addEventListener("click", async function () {
        let table = document.querySelector("table");
        if (!table) {
            alert("No table found to export.");
            return;
        }

        let rows = table.querySelectorAll("tr");
        let csvContent = [];

        rows.forEach(row => {
            let cols = row.querySelectorAll("th, td");
            let rowData = Array.from(cols).map(col => `"${col.innerText.replace(/"/g, '""')}"`);
            csvContent.push(rowData.join(","));
        });

        let csvData = csvContent.join("\n");
        let csvBlob = new Blob([csvData], { type: "text/csv" });

        // Get email input from the UI
        let emailInput = document.getElementById("email").value;
        if (!emailInput) {
            alert("Please enter a recipient email.");
            return;
        }

        // ✅ Step 1: Download CSV locally
        let downloadLink = document.createElement("a");
        downloadLink.href = URL.createObjectURL(csvBlob);
        downloadLink.download = "SQL_Results.csv";
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);

        // ✅ Step 2: Send CSV via Email
        let formData = new FormData();
        formData.append("file", csvBlob, "SQL_Results.csv");
        formData.append("email", emailInput);  // Add email to request

        try {
            let response = await fetch("http://localhost:8090/api/email/send", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("CSV downloaded & Email Sent Successfully!");
            } else {
                let errorText = await response.text();
                alert(`Error sending email: ${errorText}`);
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while sending the email.");
        }
    });
});

