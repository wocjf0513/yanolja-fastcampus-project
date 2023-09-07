package findPharmacy;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	static String restApiKey;
	static CloseableHttpClient client=null;
	
	public static class Point{
		private final String x;
		private final String y;
		
		public Point(String x, String y) {
			this.x=x;
			this.y=y;
		}
		
		public String getX() {
			return x;
		}
	
		public String getY() {
			return y;
		}
	
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Properties properties=readProperties();
		restApiKey=(String)properties.get("rest.api.key");
		client=HttpClients.createDefault();
		// 입력 처리 
		System.out.print("위치 키워드를 입력하세요:");
		String keyword=br.readLine();
		Point searchedPoint=findByKeyword(keyword);
		System.out.print("검색 반경을 입력하세요(1000:1km):");
		String radius=br.readLine();
		JSONArray jsonArray=findByXandYandRadiousandCategory(searchedPoint.getX(), searchedPoint.getY(), radius, "PM9");
		
		System.out.println("입력한 위치 키워드:"+keyword);
		System.out.println("검색 반경:"+radius);
		
		System.out.println("** 약국 검색 결과**");
		
		for(int i=0; i<jsonArray.length() && i<10; i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			System.out.println("------------"+(i+1)+"번째 검색 결과--------------");
			System.out.println("장소 URL(지도 위치): "+jsonObject.optString("place_url"));
			System.out.println("상호명: "+jsonObject.optString("place_name"));
			System.out.println("주소: "+jsonObject.optString("road_address_name"));
			System.out.println("전화번호: "+jsonObject.optString("phone"));
			System.out.printf("거리(km): %.3f\n",jsonObject.optFloat("distance")*0.001);
		}
		if(jsonArray.length()==0) {
			System.out.println("검색 결과가 없습니다.");
		}
		else {
			System.out.println("------------------------------------");
			while(true) {
			System.out.print("\nkakaomap URL(장소 URL):");
			String input=br.readLine();
				if(input.equals("exit") || input.equals("EXIT")) {
					System.out.println("프로그램 종료");
					return;
				}
				else {
					try {
					Desktop.getDesktop().browse(new URI(input));
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
			
			return;
		
	

	}
	public static Properties readProperties() {
		FileInputStream fis=null;
		Properties properties=null;
		
		try {
			File file=new File("src/mai"
					+ "n/resources/applicaion.properties");
			
			if(!file.exists()) {
				System.err.println("File not found:"+file.getAbsolutePath());
				return null;
			}
			
			fis=new FileInputStream(file);
			properties=new Properties();
			properties.load(fis);
			fis.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return properties;
	}
	
	public static Point findByKeyword(String keyword) throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder builder=new URIBuilder("https://dapi.kakao.com/v2/local/search/keyword.json");
		builder.setParameter("query", keyword);
		HttpGet httpGet=new HttpGet(builder.build());
		httpGet.addHeader("Authorization", "KakaoAK "+restApiKey);
	
		CloseableHttpResponse httpResponse=client.execute(httpGet);
		
		if(httpResponse.getStatusLine().getStatusCode()==200) {
			String entity=EntityUtils.toString(httpResponse.getEntity());
			if(entity!=null) {
			JSONArray jsonArray=new JSONObject(entity).optJSONArray("documents");
				if(jsonArray!=null) {
					String x=null;
					String y=null;
					for(int i=0; i<jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.optJSONObject(i);
						if(jsonObject.has("x")){
							x=(String)jsonObject.get("x");
						}
						if(jsonObject.has("y")){
							y=(String)jsonObject.get("y");
						}
					}
					if(x==null || y==null) {
						throw new RuntimeException("해당 키워드 관련 장소가 없습니다.");
					}
					else {
						return new Point(x,y);
					}
				}
				else {
					throw new RuntimeException("해당 키워드 관련 장소가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("해당 키워드 관련 장소가 없습니다.");
			}
		}
		else {
			throw new RuntimeException("잘못된 요청");
		}
	}
	public static JSONArray findByXandYandRadiousandCategory(String x,String y, String radius, String category) throws URISyntaxException, ClientProtocolException, IOException {
		
		URIBuilder builder=new URIBuilder("https://dapi.kakao.com/v2/local/search/category.json");
		builder.setParameter("category_group_code", category);
		builder.setParameter("x", x);
		builder.setParameter("y", y);
		builder.setParameter("radius", radius);
		HttpGet httpGet=new HttpGet(builder.build());
		httpGet.addHeader("Authorization", "KakaoAK "+restApiKey);
	
		CloseableHttpResponse httpResponse=client.execute(httpGet);
		
		if(httpResponse.getStatusLine().getStatusCode()==200) {
			String entity=EntityUtils.toString(httpResponse.getEntity());
			if(entity!=null) {
			JSONArray jsonArray=new JSONObject(entity).optJSONArray("documents");
				if(jsonArray!=null) {
					return jsonArray;
				}
				else {
					throw new RuntimeException("해당 키워드 관련 장소가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("해당 키워드 관련 장소가 없습니다.");
			}
		}
		else {
			throw new RuntimeException("잘못된 요청");
		}
	}
}
