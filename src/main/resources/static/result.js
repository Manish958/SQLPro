//<script>
//        document.addEventListener("DOMContentLoaded", async function () {
//            try {
//                // Fetch data from backend API
//                const response = await fetch("http://localhost:8090/records");
//                const data = await response.json();
//
//                if (!data.length) {
//                    console.warn("No records found!");
//                    return;
//                }
//
//                // Generate column definitions dynamically
//                const columns = Object.keys(data[0]).map(key => ({
//                    field: key,
//                    headerText: key.toUpperCase(),
//                    width: 120
//                }));
//
//                // Initialize Syncfusion Grid
//                new ej.grids.Grid({
//                    dataSource: data,
//                    allowPaging: true,
//                   // showColumnChooser: true,
//                    toolbar: ['Search', 'ExcelExport', 'CsvExport'],
//                    allowExcelExport: true,
//                    columns: columns
//                }).appendTo("#SyncTable");
//
//            } catch (error) {
//                console.error("Error fetching data:", error);
//            }
//        });
//    </script>

 document.addEventListener("DOMContentLoaded", async function () {
            try {
                // Fetch data from backend
                var data = /*[[${data}]]*/ [];

                if (!Array.isArray(data) || data.length === 0) {
                    console.warn("No records found!");
                    alert("No records found!");
                    return;
                }

                // Generate column definitions dynamically
                const columns = Object.keys(data[0]).map(key => ({
                    field: key,
                    headerText: key.toUpperCase(),
                    width: 120
                }));

                // Initialize Syncfusion Grid
                new ej.grids.Grid({
                    dataSource: data,
                    allowPaging: true,
                    toolbar: ['Search', 'ExcelExport', 'CsvExport'],
                    allowExcelExport: true,
                    columns: columns
                }).appendTo("#SyncTable");

            } catch (error) {
                console.error("Error fetching data:", error);
            }
        });