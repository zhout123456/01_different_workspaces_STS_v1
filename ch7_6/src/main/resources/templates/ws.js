var stompClient = null;
	
	function setConnected(connected) {
		document.getElementById("connect").disabled = connected;
		document.getElementById("disconnect").disabled = !connected;
		document.getElementById("conversationDiv").style.visibility = connected? "visible" : "hidden";
		
		$("#response").html();
	}
	
	function connect() {
		var socket = new SockJS("/endpointWisely");
		stompClient = Stomp.over(socket);
		
		stomClient.connect({}, function(frame) {
			setConnected(true);
			console.log("Connected:" +frame);
			stompClient.subscribe("/topic/getResponse", function(response) {
				showResponse(JSON.prase(response.body).responseMessage);
			})；
		});
	}
	
	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		
		setConnected(false);
		console.log("Disconnected");
	}
	
	function sendName() {
		var name = $("#name").val();
		stompClient.send("/welcome", {}, 
			JSON.stringify(
				{'name' : name}
			)
		);
	}
	
	function showResponse(message) {
		var response = $("#response");
		response.html(message);
	}