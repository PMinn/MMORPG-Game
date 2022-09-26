package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRequest extends Thread {
	private URL url;
	private String method;
	public boolean haveBody = false;
	public String body;
	private StringBuilder result = new StringBuilder();

	public HttpRequest(String path, String method, String... body) throws MalformedURLException {
		url = new URL(path);
		this.method = method;
		if (body.length > 0) {
			this.body = "";
			haveBody = true;
			for (int i = 0; i < body.length; i++) {
				this.body += body[i];
				if (i != body.length - 1) {
					this.body += "&";
				}
			}
		}
	}

	public void run() {
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (haveBody) {
				conn.setDoOutput(true);
			}

			conn.setRequestMethod(method);
			if (haveBody) {
				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
				osw.write(body);
				osw.flush();
				osw.close();
				os.close();
				conn.connect();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line; (line = reader.readLine()) != null;) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return result.toString();
	}

	public static String async(String path, String method, String... body) throws IOException {
		URL url = new URL(path);
		String sendBody = "";
		if (body.length > 0) {
			for (int i = 0; i < body.length; i++) {
				sendBody += body[i];
				if (i != body.length - 1) {
					sendBody += "&";
				}
			}
		}

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (body.length > 0) {
			conn.setDoOutput(true);
		}
		conn.setRequestMethod(method);
		if (body.length > 0) {
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(sendBody);
			osw.flush();
			osw.close();
			os.close(); // don't forget to close the OutputStream
			conn.connect();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuilder result = new StringBuilder();
		for (String line; (line = reader.readLine()) != null;) {
			result.append(line);
		}
		return result.toString();
	}
}