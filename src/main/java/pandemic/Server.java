package pandemic;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import pandemic.org.Pandemic;

public class Server
{
    Server()
    {	 
        Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(81);

    SocketIOServer server = new SocketIOServer(config);
    }
}