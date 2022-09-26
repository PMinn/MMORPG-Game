package Server;

import java.io.IOException;

import GUI.Dialog;

public class Host {
	public static String url;

	public Host() {
		try {
			HttpRequest url = new HttpRequest(
					"https://script.google.com/macros/s/AKfycbwUblnxwQpG7VejPhF8Z9vpVwqsriPw_zNZpiLXjg780V7HiIF1/exec",
					"GET");
			url.start();
			synchronized (url) {
				url.wait();
			}
			Host.url = url.toString();
		} catch (IOException e) {
			Dialog dialog = new Dialog();
			dialog.show("錯誤", "無法取得伺服器位置");
		} catch (InterruptedException e) {
			Dialog dialog = new Dialog();
			dialog.show("錯誤", "取得伺服器位置執行緒失敗");
		}
	}
}
