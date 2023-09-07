package searchbook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	private String isbn;
	private String title;
	private String price;
	private String publisher;
	private String salePrice;
	
	public Book() {
		
	}
	
	public Book(String isbn, String title, String price, String publisher, String salePrice) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.price = price;
		this.publisher = publisher;
		this.salePrice = salePrice;
	}

	public String getTitle() {
		return title;
	}

	public String getPrice() {
		return price;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public String toString() {
		return title + " | " + price + " | " + publisher + " | " 
				+ salePrice+ " | " + isbn;
	}
	
	
	
	

}
