package org.kobelig.com.acd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Appliance Control Dictionary Proxy サービスに接続して与えられた家電IDの操作を実行する．
 * @author masa-n
 *
 */
public class ACDproxy {
	private String baseUrl; //ACDproxyのエンドポイント．プロパティファイルから読み込む

	public ACDproxy() {
		Properties prop = new Properties();

		try {
			prop.load(getClass().getResourceAsStream("/META-INF/acdproxy.properties"));
			baseUrl = prop.getProperty("url");
			System.out.println("ACDproxy endpoint: " + baseUrl);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			Logger.getLogger(getClass()).error("Failed to initialize ACD proxy. Aborted.");
			e.printStackTrace();
		}

	}

	/**
	 * ACDProxyサービスに接続して処理を実行する．
	 * @param deviceId 操作するデバイスのID
	 * @param operationId 操作名ID
	 * @throws IOException ACDサービスに正常に接続できない
	 * @throws InterruptedException 待機が失敗した
	 */
	public void exec(String deviceId, String operationId) throws ACDExecException {
		exec(deviceId, operationId, 0);
		//exec2(deviceId, operationId, 0);
	}

	/**
	 * ACDProxyサービスに接続して処理を実行する．実行後，待機時間だけ待機する．
	 * @param deviceId 操作するデバイスのID
	 * @param operationId 操作名ID
	 * @param wait 操作後の待機時間(msec)
	 * @return Result of service
	 * @throws IOException ACDサービスに正常に接続できない
	 * @throws InterruptedException 待機が失敗した
	 */
	public boolean exec(String deviceId, String operationId, int wait) throws ACDExecException {
		boolean cond=false;
		try {
			URL url = new URL(baseUrl + "?deviceId="+deviceId+"&operationId="+operationId);
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			String contents = in.readLine();
			in.close();

			if (contents.indexOf("true") < 0 ) {
				throw new ACDExecException(2, "ACD execution failed. [" + deviceId + ", "+ operationId+ ", "+ wait+ "]" );
			}
			Logger.getLogger(getClass()).info("Execute ACD [" + deviceId + ", "+ operationId+ ", "+ wait+ "]" );
			Thread.sleep(wait);

			return cond;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new ACDExecException(1, wait + " msec Sleep was interrupted." );
		} catch (IOException e) {
			e.printStackTrace();
			throw new ACDExecException(2, "IOException caught with " +deviceId + ", "+ operationId );
		}
	}


}
