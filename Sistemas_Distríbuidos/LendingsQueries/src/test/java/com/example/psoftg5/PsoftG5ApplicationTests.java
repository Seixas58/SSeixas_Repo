package com.example.psoftg5;

import com.example.psoftg5.authormanagement.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PsoftG5ApplicationTests {

	@Test
	void contextLoads() {
	}

}
@SpringBootTest
class AuthorTests {

	@Test
	void testAuthorCreation() {
		String name = "Aberto Caeiro";
		String shortBio = "Um dos heterónimos de Fernando Pessoa.";

		Author author = new Author(name, shortBio);

		assertEquals(name, author.getName(), "Author name should be 'Aberto Caeiro'");
		assertEquals(shortBio, author.getShortBio(), "Author short bio should be 'Um dos heterónimos de Fernando Pessoa.'");
	}

	@Test
	void testAuthorCreationFailsDueToInvalidName() {
		String invalidName = "";
		String shortBio = "Um dos heterónimos de Fernando Pessoa.";

		assertThrows(IllegalArgumentException.class, () -> {
			new Author(invalidName, shortBio);
		}, "Expected creation to fail due to invalid name");
	}

	@Test
	void testAuthorCreationFailsDueToInvalidShortBio() {
		String name = "Alberto Caeiro";
		String invalidShortBio = "";

		assertThrows(IllegalArgumentException.class, () -> {
			new Author(name, invalidShortBio);
		}, "Expected creation to fail due to invalid short bio");
	}
}
