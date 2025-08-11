package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.model.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class AuthorDTOMapper {
    public AuthorDTO toAuthorView(Optional<Author> authorOptional) {
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setName(author.getName());
            authorDTO.setShortBio(author.getShortBio());
            authorDTO.setPhotoURL(author.getPhotoURL());
            return authorDTO;
        } else {
            return null;
        }
    }

    public ArrayList<AuthorDTO> toAuthorView(Iterable<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        ArrayList<AuthorDTO> iterable = new ArrayList<AuthorDTO>();
        for ( Author author : authors ) {
            iterable.add( toAuthorView ( author ) );
        }

        return iterable;
    }


    public AuthorDTO toAuthorView(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorNumber(author.getAuthorNumber());
        authorDTO.setName(author.getName());
        authorDTO.setShortBio(author.getShortBio());
        authorDTO.setPhotoURL(author.getPhotoURL());
        return authorDTO;
    }

}
