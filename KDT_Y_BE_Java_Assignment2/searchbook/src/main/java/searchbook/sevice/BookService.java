package searchbook.sevice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import searchbook.dto.BookDto;
import searchbook.entity.Book;
import searchbook.repository.BookRepository;

public class BookService {
	
	
	private final BookRepository bookRepository=BookRepository.getBookRepository();
	private static ArrayList<BookDto> searchedBooks;
	private static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	
	public BookService() {}
	
	public static void printRecentlySearchedBook() throws IOException {
		bw.append("[검색 결과]\n");
		bw.append("-----------------------------------------------------------------------------------------------------\n");		
		bw.append("    도서 제목    |    가격    |    출판사    |    작가    |    할인 가격    |    ISBN\n");
		bw.append("-----------------------------------------------------------------------------------------------------\n");
		for(int i=0; i<searchedBooks.size(); i++) {
			bw.append(searchedBooks.get(i).toString()+"\n");
		}
		bw.flush();
	}
	public static void printRetrivedBook(List<BookDto> retrivedBooks) throws IOException {
		bw.append("[TABLE LIST]\n");
		bw.append("-----------------------------------------------------------------------------------------------------\n");		
		bw.append("    도서 제목    |    가격    |    출판사    |    작가    |    할인 가격    |    ISBN\n");
		bw.append("-----------------------------------------------------------------------------------------------------\n");
		for(int i=0; i<retrivedBooks.size(); i++) {
			bw.append(retrivedBooks.get(i).toString()+"\n");
		}
		bw.flush();
	}
	
	public static void searchBook(CloseableHttpClient chc,String book) throws ClientProtocolException, IOException, URISyntaxException, ClassNotFoundException {
		searchedBooks=new ArrayList(); //새로 검색 결과가 남기 위해 초기화
		
		URIBuilder uri=new URIBuilder("https://dapi.kakao.com/v3/search/book");
		uri.addParameter("query", book);
		HttpGet hg=new HttpGet(uri.build());
		hg.setHeader("Authorization","KakaoAK a28a87fbccabd3c51aee6b20a54ab7f8");
		
		
		CloseableHttpResponse chr=chc.execute(hg);
		int reponseStatusCode=chr.getStatusLine().getStatusCode();
		if(reponseStatusCode==200) {
			String entity=EntityUtils.toString(chr.getEntity());
			if(entity!=null) {
				JSONArray jsonEntityArray=new JSONObject(entity).optJSONArray("documents");
				if(jsonEntityArray!=null) {
					
					for(int i=0; i<jsonEntityArray.length(); i++) {
						BookDto searchBook=new BookDto();
						
						JSONObject jsonEntity=jsonEntityArray.getJSONObject(i);
						
							searchBook.setTitle(jsonEntity.optString("title"));
							searchBook.setPrice(jsonEntity.optString("price"));
							searchBook.setPublisher(jsonEntity.optString("publisher"));
							searchBook.setSalePrice(jsonEntity.optString("sale_price"));
							searchBook.setIsbn(jsonEntity.optString("isbn"));
						
						
						searchedBooks.add(searchBook);
						
					}
				}
				else {
					throw new RuntimeException("해당 요청에 대한 결과가 없습니다.");
				}
			}
			else {
				throw new RuntimeException("응답 메시지가 없습니다.");
			}
		}
		else {
			throw new RuntimeException("네트워크 통신 오류["+reponseStatusCode+"]");
		}
		
		
	}
	
	public void saveBook() {
		bookRepository.saveBook(searchedBooks);
	}
	
	public List<BookDto> retriveBooks(int size){
		return bookRepository.retrivedBooks(size);
		
		
	}
	

	

}
