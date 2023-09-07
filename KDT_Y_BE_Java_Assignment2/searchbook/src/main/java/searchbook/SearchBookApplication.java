package searchbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import searchbook.sevice.BookService;

public class SearchBookApplication {
	
	final static BookService bookService=new BookService();

	private static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		final CloseableHttpClient chc=HttpClientBuilder.create().build();
		// TODO Auto-generated method stub
		try {
			
		System.out.print("도서를 검색할 제목을 입력하세요:");
		bookService.searchBook(chc,br.readLine());
		bookService.printRecentlySearchedBook();
		System.out.println("데이터베이스에 저장하시겠습니까? Y/N");
			while(true) {
				String input=br.readLine();
				if(input.equals("Y")) {
					bookService.saveBook();
					System.out.println("데이터가 저장되었습니다.");
					break;
				}
				else if(input.equals("N")){
					break;
				}
				else {
					System.out.println("다시 입력해주세요! Y/N");	
				}
			}
			bookService.printRetrivedBook(bookService.retriveBooks(10));
			System.out.println("\n[프로그램 종료]");
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			br.close();
			chc.close();
		}
		
	}

}
