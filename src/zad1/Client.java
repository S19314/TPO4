/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


public class Client {
	private String host,
			id;
	private int port;
	private SocketChannel socket = null;
	// private 
	
	public Client(String host, int port, String id) {
		this.host = host;
		this.id = id;
		this.port = port;
	}
	
	public String getId() {
		return id;
	}
	
	public void connect() {
		try {
			
			socket = SocketChannel.open(new InetSocketAddress(host, port)); //(host, port);
		//	socket.socket().getInputStream();
			
			
		}catch(UnknownHostException unHodtExc) {
			// TODO: ”брать
			unHodtExc.printStackTrace();
			
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
		
		
	}
	
//	private CharBuffer charBuffer = new CharBuffer(); 
	private static Charset charset = Charset.forName("UTF-8");
	private static final int BYTE_BUFFER_SIZE = 1024;
	private ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
	private StringBuffer respondeStringBuffer = new StringBuffer();
	// TODO: не забыть о return "";
	public String send(String req) {
		
		System.out.println("Client Request " + req);
		ByteBuffer bufferRequest = charset.encode(CharBuffer.wrap(req));
		try {
			socket.write(bufferRequest);
			bufferRequest.flip();
		}catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
//		String responde = ""; 
		try {	
			respondeStringBuffer.setLength(0);
			while(true) { // TODO: ¬вести двойную \n\n  в серваке, дл€ правильно коммуникации 
				int ilosZczytanychBitow = socket.read(byteBuffer); // двойной \n дл€ всех сообщений с сервака
				if (ilosZczytanychBitow > 0) {
					byteBuffer.flip();
					CharBuffer charBuffer = charset.decode(byteBuffer);
					while(charBuffer.hasRemaining()) {
						char character = charBuffer.get();
						respondeStringBuffer.append(character);
						if(character == '\r' || character == '\n') {
							character = charBuffer.get();
							if(character == '\r' || character == '\n') {
								return respondeStringBuffer.toString();
							}
						}
						}					}
					//respondeStringBuffer.append(charBuffer.toString());
					
				}
		}catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
	
		
		return "";
	}
	
	
	
	
}
