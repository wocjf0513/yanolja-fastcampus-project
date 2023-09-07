package searchbook.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import searchbook.dto.BookDto;
import searchbook.entity.Book;

public class BookRepository {
	
	private static BookRepository bookRepository=new BookRepository();
	private final EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("hello");
	
	private BookRepository() {}
	
	public static BookRepository getBookRepository() {
		return bookRepository;
	}
	
	public void saveBook(ArrayList<BookDto> Books) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
		EntityTransaction et=em.getTransaction();
		et.begin();
		for(int i=0; i<Books.size(); i++) {
			if(!validateDuplicate(Books.get(i)))
			em.persist(Books.get(i));
		}
		et.commit();
		}
		catch(Exception e) {
			throw new RuntimeException("데이터베이스 관련 오류");
		}
		finally {
			em.close();
		}
	}
	public boolean validateDuplicate(BookDto book) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
		EntityTransaction et=em.getTransaction();
		et.begin();
		TypedQuery<Book> query=em.createQuery("Select b from Book b WHERE b.isbn='"+book.getIsbn()+"'",Book.class);
		Book retrivedBook=query.getSingleResult();
		et.commit();
		if(retrivedBook!=null) {
			return true;
		}
		else {
			return false;
		}
		
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("데이터베이스 관련 오류");
		}
		finally {
			em.close();
		}
		
	}
	
	public List<BookDto> retrivedBooks(int size) {
		List<BookDto> retrivedBooks=null;
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
		EntityTransaction et=em.getTransaction();
		et.begin();
		TypedQuery<Book> query=em.createQuery("Select b from Book b WHERE ROWNUM<="+size,Book.class);
		retrivedBooks=mapperToBookDto(query.getResultList());
		et.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("데이터베이스 관련 오류");
		}
		finally {
			em.close();
			return retrivedBooks;
		}
		
	}
	public List<BookDto> mapperToBookDto(List<Book> books) {
		List<BookDto> bookDtos=new ArrayList();
		for(int i=0; i<books.size(); i++) {
			bookDtos.add(new BookDto(books.get(i)));
		}
		return bookDtos;
	}
}
