/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;



public class Server {
	
	private String host = null;
	private int port = 9999;
	// Мб. Изменю на boolean isRunThreadForClientConnection;
	private Thread serverThread;
	// private StringBuffer logsStringBuffer; 
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
				try {
				boolean serverIsRunning = true;
				
				while(serverIsRunning) {
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
							SocketChannel keySocketChannel = (SocketChannel)key.channel(); 
							// Обслуживание запроса
							serviceRequest(keySocketChannel);
							continue;
						}
					}
				}
				}catch(Exception exception) {
					exception.printStackTrace();
					continue;
					
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
		
		requestStringBuffer.setLength(0);
		
		byteByffer.clear();
		
	  try {
		while(true) {
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
			  String command = requestStringBuffer.toString().trim(); 
					  // requestParameters[0]; 
				
			if(command.startsWith("bye")) {
				if(command.equals("bye and log transfer")) writeResponde(socketChannel, "Ты забыл отправить Log'i ЭТОГО клиента");; // Отправляет его log клиенту
				
				// Напсать ответ клиенту
				writeResponde(socketChannel, "logged out");
				
				socketChannel.close();
				socketChannel.socket().close();
			}else if (command.startsWith("login")) {
				// ответОчка клиенту "logged in"
				writeResponde(socketChannel, "logged in");
			}else if(command.charAt(0) >= '0' && command.charAt(0) <= '9' ) {
				String data[] = command.split(" ", 2); 
				String timePassed = Time.passed(data[0], data[1]);
				// отправить ответОчку Клиенту
				writeResponde(socketChannel, timePassed	);
				
			}  else  writeResponde(socketChannel, "Unknown command");// ответOчка об не известной комманде
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
		requestStringBuffer.append('\n');
		
		ByteBuffer messageByteBuffer = charset.encode(CharBuffer.wrap(requestStringBuffer));
		socketChannel.write(messageByteBuffer);
	}
	// private void serviceConnections() {} 
	
	
	// zatrzymuje działanie serwera i wątku w którym działa
	public void stopServer() {
		serverThread.interrupt();
		
	}
	
	public String getServerLog() {
		
		return "";
	}
	
	
	
}
