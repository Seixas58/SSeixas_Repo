package com.example.psoftg5.bootstrapping;

import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.repositories.AuthorDBRepository;
import com.example.psoftg5.bookmanagement.repositories.BookDBRepository;
import com.example.psoftg5.bookmanagement.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookBootstrapper implements CommandLineRunner {

    private final BookDBRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorDBRepository authorRepository;

    // Injeção da propriedade personalizada
    @Value("${book.bootstrap.enabled}")
    private boolean bootstrapEnabled;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o bootstrap está habilitado
        if (!bootstrapEnabled) {
            //System.out.println("Bootstrap desativado. Não serão adicionados livros.");
            return; // Sai da execução do bootstrap se a flag estiver desativada
        }

        //Author author1 = new Author("Mateus","Natural de Montalegre","http://example.com/mateus-seixas.jpg");
        //Author author2 = new Author("Miguel Spiritze","Natural Alijo","");

        //authorRepository.saveAll(Arrays.asList(author1, author2));

        Genre fiction = new Genre("Fiction");
        Genre nonFiction = new Genre("Non-Fiction");

        if (genreRepository.findById("Fiction").isEmpty()){
            genreRepository.save(fiction);
        }

        if (genreRepository.findById("Non-Fiction").isEmpty()){
            genreRepository.save(nonFiction);
        }

        /*Set<Author> authors = new HashSet<>();
        authors.add(authorRepository.findAuthorsByName("Max").get(0));

        Set<Author> authors2 = new HashSet<>();
        for(Author obj : authorRepository.findAll()) {
            authors2.add(obj);
        }*/

        String coverPhotoUrl1 = "http://example.com/to-kill-a-mockingbird.jpg";
        String coverPhotoUrl2 = "http://example.com/the-catcher-in-the-rye.jpg";
        String coverPhotoUrl3 = "http://example.com/1984.jpg";
        String coverPhotoUrl4 = "http://example.com/animal-farm.jpg";
        String coverPhotoUrl5 = "http://example.com/lord-of-the-flies.jpg";
        String coverPhotoUrl6 = "http://example.com/montalegre.jpg";

        /*Book book1 = new Book("978-0-446-50469-1", "To Kill a Mockingbird", "A novel by Harper Lee", fiction, authors, coverPhotoUrl1);
        Book book2 = new Book("978-0-06-112008-4", "The Catcher in the Rye", "A novel by J.D. Salinger", fiction, authors2, coverPhotoUrl2);
        Book book3 = new Book("978-0-385-14711-9", "1984", "A dystopian novel by George Orwell", fiction, authors, coverPhotoUrl3);
        Book book4 = new Book("978-0-679-60176-1", "Animal Farm", "A novel by George Orwell", fiction, authors2, coverPhotoUrl4);
        Book book5 = new Book("978-0-140-81718-1", "Lord of the Flies", "A novel by William Golding", fiction, authors, coverPhotoUrl5);
        Book book6 = new Book("978-0-808-81818-1", "Montalegre", "Montalege grande", nonFiction, authors2, coverPhotoUrl6);

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6));*/
    }
}
