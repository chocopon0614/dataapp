function constructTable(selector1, selector2, list) {
	// Getting the all column names
	var cols = Headers(list, selector1);

	// Traversing the JSON data
	for (var i = 0; i < list.length; i++) {
		var row = $('<tr/>');
		
		row.append($('<th scope="row"/>').html(i+1));

		for (var colIndex = 0; colIndex < cols.length  ; colIndex++) {
			var val = list[i][cols[colIndex]];

			// If there is any key, which is matching
			// with the column name
			if (val == null)
				val = "";
			row.append($('<td/>').html(val));
		}

		// Adding each row to the table
		$(selector2).append(row);
	}
}

function Headers(list, selector1) {
	var columns = [];
	var header = $('<tr/>');

	header.append($('<th scope="col"/>').html('#'));

	for (var i = 0; i < list.length; i++) {
		var row = list[i];

		for ( var k in row) {
			if ($.inArray(k, columns) == -1) {
				columns.push(k);

				// Creating the header
				header.append($('<th scope="col"/>').html(k));
			}
		}
	}

	// Appending the header to the table
	$(selector1).append(header);
	return columns;
}
