/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientTask  implements Runnable {
		
		private Client client;
		private List<String> requestList;
		private boolean showSendResults;
		private String clientLog = "";
	
		/*
		private Future<String> task;
		
		public ClientTask(Future<String> task) {
			this.task = task;
		}
	
		public String get() throws ExecutionException, InterruptedException {
			return task.get();
		}
		*/
		
		
		public ClientTask(Client client, List<String> requests, boolean showSendResults) {
			this.client = client;
			requestList = requests;
			this.showSendResults = showSendResults;
			
		}
	
	   public static ClientTask create(Client c, List<String> reqs, boolean showSendRes) {
		   		   return new ClientTask(c, reqs, showSendRes);
//	   	return ;
	}

		@Override
		public void run() {
		   client.connect();
		   client.send("login " + client.getId());
	        for(String req : requestList) {
	          String res = client.send(req);
	          if (showSendResults) System.out.println(res);
	        }
	        clientLog = client.send("bye and log transfer");
	        // System.out.println(clog);
		   // return clog;
	   }
		
		public String get() throws ExecutionException, InterruptedException {
			return clientLog;
		}
}

   