import lib.socketio.client.IO;

public class Server {
    Server() {
        // Do stuff here
        socket = IO.socket("http://localhost");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

          @Override
          public void call(Object... args) {
            socket.emit("message", "hi jamie");
            socket.disconnect();
          }

        }).on("event", new Emitter.Listener() {

          @Override
          public void call(Object... args) {
            socket.emit('message', 'we got a message');
          }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

          @Override
          public void call(Object... args) {
            socket.emit('message', 'client disconnect');
          }

        });
        socket.connect();

    }
}