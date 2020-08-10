package ru.otus.spring.services;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.impl.AuthorRepository;
import ru.otus.spring.repositories.impl.BookRepository;
import ru.otus.spring.repositories.impl.GenreRepository;
import ru.otus.spring.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.services.imp.BookService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Book saveBook(long genreId, long authorId, String bookname) throws Exception {
        Author author = authorRepository.findById(authorId);
        Genre genre = genreRepository.findById(genreId);
         if(author == null) {
             throw new Exception(String.format("Автор с id '%s' не найден", authorId));
         } else if(genre == null){
             throw new Exception(String.format("Жанр с id '%s' не найден", genreId));
         } else{
             Book book = new Book();
             book.setGenre(genre);
             book.setAuthor(author);
             book.setBookName(bookname);

             bookRepository.save(book);
             return book;
         }
    }

    @Override
    @Transactional
    public Book removeBook(long id) throws Exception {
        Book book = bookRepository.findById(id);
        if(book == null){
            throw new Exception(String.format("Книга с id '%s' не найдена", id));
        } else {
            bookRepository.remove(book);
            return book;
        }
    }

    @Override
    @Transactional
    public Book findByIdBook(long id) throws Exception {
        Book book = bookRepository.findById(id);

        if(book == null){
            throw new Exception(String.format("Книги с id '%s' нет в базе", id));
        } else {
            return book;
        }
    }

    @Override
    @Transactional
    public List<Book> findByGenreBook(long genreId) throws Exception {
        Genre genre = genreRepository.findById(genreId);

        if(genre.getId() == 0){
            throw new Exception(String.format("Книги жанра с id '%s' не найдены", genreId));
        } else{
            List<Book> resultBooks = new ArrayList<>();
            List<Book> books = bookRepository.findAll();
            for (Book book: books) {
                if(book.getGenre().getId() == genreId){
                    resultBooks.add(book);
                }
            }
            return resultBooks;
        }
    }

    @Override
    @Transactional
    public List<Book> findByAuthorBook(long authorId) throws Exception {
        Author author = authorRepository.findById(authorId);

        if(author.getId() == 0){
            throw new Exception(String.format("Книги с id автора '%s' не найдены", authorId));
        } else {
            List<Book> resultBooks = new ArrayList<>();
            List<Book> books = bookRepository.findAll();
            for (Book book: books) {
                if(book.getAuthor().getId() == authorId){
                    resultBooks.add(book);
                }
            }
            return resultBooks;
        }
    }

    @Override
    @Transactional
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

}
