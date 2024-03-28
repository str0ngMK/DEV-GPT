function send(){
	let message = document.getElementById('message').value;
	const data = {
		"message": message
	}
	fetch('http://localhost:80/ai/send/dev', {
		method:'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
	.then(response=> {
		return response.json()
	})
	.then(data => {
		
	})
}