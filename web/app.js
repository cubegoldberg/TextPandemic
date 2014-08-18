var socket = io('http://10.0.1.11/')
// var socket = io('http://localhost');
socket.on('message', function(message) {
  console.log(message);
})
socket.emit('message', {
  foo: 'bar',
  youRock: 'rylo!'
})
