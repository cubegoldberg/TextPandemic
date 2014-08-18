package pandemic;

import com.github.fluffy.socketio.client.Socket;
import com.github.fluffy.socketio.client.IO;
import com.github.fluffy.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
    Server() throws URISyntaxException
    {
	// Do stuff here
	final Socket socket = IO.socket("http://localhost");
	socket.on(Socket.EVENT_CONNECT, new Emitter.Listener()
	{
	    @Override
	    public void call(Object... args)
	    {
		socket.emit("message", "hi jamie");
		socket.disconnect();
	    }
	}).on("event", new Emitter.Listener()
	{
	    @Override
	    public void call(Object... args)
	    {
		socket.emit("message", "we got a message");
	    }
	}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener()
	{
	    @Override
	    public void call(Object... args)
	    {
		socket.emit("message", "client disconnect");
	    }
	});
	socket.connect();

    }
}