$.ajax({
	type: "GET",
	dataType: "json",
	url: "http://localhost:8080/persons",
	success: function(data) {
		alter(data);
	}
});


$http.get(url);
$http.post(url, data);
$http.put(url, data);
$http.delete(url);