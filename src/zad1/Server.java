/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class Server {
	
	private String host = null;
	private int port = 9999;
	// Мб. Изменю на boolean isRunThreadForClientConnection;
	private Thread serverThread;
	private Map<String, StringBuffer> logClients = new HashMap<String, StringBuffer>(); 
	private StringBuffer logServer = new StringBuffer();
	private boolean serverIsRunning = true;
	
	
	public Server(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	//  uruchamia server w odrębnym wątku,
	public void startServer() {
		serverThread =  new Thread( () ->  
		{
			try {
				ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
				serverSocketChannel.bind(new InetSocketAddress(host, port));
				
				serverSocketChannel.configureBlocking(false);
				
				Selector selector = Selector.open();
				
				SelectionKey serverSelectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				
				
				// Часть оброботки
//				boolean serverIsRunning = true;
				
				while(serverIsRunning) {
					try {
							selector.select();
						
						Set selectedKeys = selector.selectedKeys(); 
					 
						
						Iterator iterator = selectedKeys.iterator();
						while(iterator.hasNext()) {
							SelectionKey selectionKeyFromIteration = (SelectionKey)iterator.next();
							iterator.remove();
							if(selectionKeyFromIteration.isAcceptable()) {
								SocketChannel clientSocketChannel = serverSocketChannel.accept();  
								
								clientSocketChannel.configureBlocking(false);
								clientSocketChannel.register(selector, SelectionKey.OP_READ);
								continue;
							}
							
							if(selectionKeyFromIteration.isReadable()) {
								SocketChannel keySocketChannel = (SocketChannel)selectionKeyFromIteration.channel(); 
								// Обслуживание запроса
								serviceRequest(keySocketChannel);
								continue;
							}
						}
				
					}catch(Exception exception) {
					exception.printStackTrace();
					continue;
					
					}
				}
				
				
			}catch(IOException ioException){
				
				// Убрать
				ioException.printStackTrace();
			}
		});

		serverThread.start();
		
		
		
	}
	
	
	
	private static Charset charset = Charset.forName("UTF-8");
	private static final int BYTE_BUFFER_SIZE = 1024;
	

	 private ByteBuffer byteByffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);

	  private StringBuffer requestStringBuffer = new StringBuffer();
	  
	private void serviceRequest(SocketChannel socketChannel) {
		if(!socketChannel.isOpen()) return;
//		socketChannel.socket().
		requestStringBuffer.setLength(0);
		
		byteByffer.clear();
		
	  try {
		while(serverIsRunning) {//true) {
			int iloscZczytanychBitow = socketChannel.read(byteByffer);
			if(iloscZczytanychBitow > 0) {
				byteByffer.flip();
				CharBuffer charByffer = charset.decode(byteByffer);
				while(charByffer.hasRemaining()) {
					char readedCharakter = charByffer.get();
					if(readedCharakter == '\r' || readedCharakter == '\n') break;
					requestStringBuffer.append(readedCharakter);
				}
				
			}
			
			
			// String[] requestParameters = requestStringBuffer.toString().split(" ", 4);
			// System.out.println("Server requestStringBuffer " + requestStringBuffer.toString());
					  String[] parameters = requestStringBuffer.toString().trim().split(" ");
					  String idClienta = parameters[0],
							  command = "";
			// System.out.println("First patramter " + parameters[0] );
			
					  for(int i = 1; i < parameters.length; i++)
						  command = command + " " + parameters[i]; 
					  
					  // requestParameters[0]; 
		  Socket clientSocket = socketChannel.socket(); 
			String iP = clientSocket.getInetAddress().toString(),
					port = "" + clientSocket.getPort();
			
					  
			if(command.startsWith("bye")) {
				
				writeClientLog(iP, port, idClienta, "logged out\n=== " + idClienta + " log end ===");
				
				if(command.equals("bye and log transfer")) {
					String clientLog = getClientLog(iP, port, idClienta);
					writeResponde(socketChannel, clientLog); 
					//"Ты забыл отправить Log'i ЭТОГО клиента"); // Отправляет его log клиенту
				}
				
				
				writeIntoServerLog(idClienta + " logged out at " + LocalTime.now());
				
				// Напсать ответ клиенту
				writeResponde(socketChannel, "logged out");
				
				socketChannel.close();
				socketChannel.socket().close();
			}else if (command.startsWith("login")) {
				// TYT Записи в Log
				// ответОчка клиенту "logged in"
				System.out.println("Loggged in");
				writeIntoServerLog(idClienta + " logged in at " + LocalTime.now());
				writeClientLog(iP, port, idClienta, "logged in\n=== " + idClienta + " log end ===");
					
				writeResponde(socketChannel, "logged in");
			}else if(command.charAt(0) >= '0' && command.charAt(0) <= '9' ) {
				// TYT Записи в Log
				writeIntoServerLog(idClienta + " request at " + LocalTime.now() + " " + command);
				
//				writeClient(iP, port, idClienta, "logged in\n=== " + idClienta + " log end ===");
				writeIntoLogClientRequest(iP, port, idClienta, command);
				
				String data[] = command.split(" ", 2); 
				
				String timePassed = Time.passed(data[0], data[1]);
				// отправить ответОчку Клиенту
				writeResponde(socketChannel, timePassed	);
				
			} /// else  writeResponde(socketChannel, "Unknown command");// ответOчка об не известной комманде
		}
		}catch(Exception exc) {
			exc.printStackTrace();
			try {
				socketChannel.close();
				socketChannel.socket().close();
			}catch (Exception e) {}
			
		}	
	  	
	}
	
	
	private StringBuffer respondeMessage = new StringBuffer();
	
	private void writeResponde(SocketChannel socketChannel, String message) throws IOException {
		requestStringBuffer.setLength(0);
		requestStringBuffer.append(message);
		requestStringBuffer.append("\n\n");
		
		ByteBuffer messageByteBuffer = charset.encode(CharBuffer.wrap(requestStringBuffer));
		socketChannel.write(messageByteBuffer);
	}
	// private void serviceConnections() {} 
	
	private void writeRequestIntoServerLog(String idClient, String argumentOfRequest) {
		writeIntoServerLog(idClient + " request at " + LocalTime.now() + ": \"" + argumentOfRequest +"\"");
		
	}
	
	private void writeIntoServerLog(String newLog) {
		logServer.append(newLog + "\n");		
	}
	
	
	
/* TODO: ПРИ ВОЗМОЖНОЙ ОШИБКИ/НЕДОЧЁТА С ТОЧНОСТЬЮ ЗАПИСЕЙ "Responde " клиента НУЖНО ИЗМЕНИТЬ 
	"Responde:\n " на "Responde:\r\n "; 
*/  
	private void writeIntoLogClientResponde(String iP, String port, String idClienta, String log) {
		writeClientLog(iP, port, idClienta,"Responde:\n " + log);
		
	}
	
	private void writeIntoLogClientRequest(String iP, String port, String idClienta, String log) {
		writeClientLog(iP, port, idClienta,"Request: " + log);
		
	}
	private void writeClientLog(String iP, String port, String idClienta, String log) {
// Opcyonalno [ 
		/*
				iP = iP.trim();
				port = port.trim();
				idClienta = idClienta.trim();
		*/
//			  ]
				
		String idClientaWithInetAdress = createKeyForClientsLogHashMap(iP, port, idClienta); 
		if(!logClients.containsKey(idClientaWithInetAdress)) {
			logClients.put(idClientaWithInetAdress, new StringBuffer(log));
		}else {
			StringBuffer logStringBuffer = logClients.get(idClientaWithInetAdress);
			log.concat("\r\n");
			logStringBuffer.append(log);
			logClients.replace(idClientaWithInetAdress, logStringBuffer);
		}
	}
	
	private String createKeyForClientsLogHashMap(String iP, String port, String idClienta) {
		return iP + " " + port + " " + idClienta;
	}
	
	private String getClientLog(String iP, String port, String idClienta) {
		String key = iP + " " + port + " " + idClienta;
		
		return logClients.get(key).toString();
	}
	
	// TODO: Не уверен в правильности написания окончания действия Сервера.
	// zatrzymuje działanie serwera i wątku w którym działa
	public void stopServer() {
		//serverThread.interrupt();
		serverIsRunning = false;
	}
	
	public String getServerLog() {
		
		return logServer.toString();
	}
	
	
	
}
