package com.workintech.s19d1.util;

import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class HollywoodValidation {

    public static void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new ApiException("Id is not valid: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkActor(Actor actor) {
        if (actor == null || actor.getFirstName() == null || actor.getFirstName().isBlank()) {
            throw new ApiException("Actor data is not valid", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkMovie(Movie movie) {
        if (movie == null || movie.getName() == null || movie.getName().isBlank()) {
            throw new ApiException("Movie data is not valid", HttpStatus.BAD_REQUEST);
        }
    }
}
