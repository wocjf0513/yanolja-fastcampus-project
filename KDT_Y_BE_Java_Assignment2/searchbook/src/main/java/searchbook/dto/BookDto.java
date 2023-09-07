package searchbook.dto;

import searchbook.entity.Book;

public class BookDto {

	private String isbn;
	private String title;
	private String price;
	private String publisher;
	private String salePrice;
	
	public BookDto() {
		
	}
	
	public BookDto(Book book) {
		this.isbn=book.getIsbn();
		this.title=book.getTitle();
		this.price=book.getPrice();
		this.publisher=book.getPublisher();
		this.salePrice=book.getSalePrice();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
