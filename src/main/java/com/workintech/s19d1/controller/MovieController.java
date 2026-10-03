package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.MovieRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.service.MovieService;
import com.workintech.s19d1.util.HollywoodValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;

    @Autowired
    public MovieController(MovieService movieService, ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {
        HollywoodValidation.checkId(id);
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody MovieRequest movieRequest) {
        Movie movie = movieRequest.getMovie();
        HollywoodValidation.checkMovie(movie);
        Movie savedMovie = movieService.save(movie);
        if (movieRequest.getActors() != null) {
            for (Actor actor : movieRequest.getActors()) {
                actor.addMovie(savedMovie);
                savedMovie.addActor(actor);
                actorService.save(actor);
            }
        }
        return savedMovie;
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie movie) {
        HollywoodValidation.checkId(id);
        Movie existing = movieService.findById(id);
        existing.setName(movie.getName());
        existing.setDirectorName(movie.getDirectorName());
        existing.setRating(movie.getRating());
        existing.setReleaseDate(movie.getReleaseDate());
        return movieService.save(existing);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id) {
        HollywoodValidation.checkId(id);
        Movie movie = movieService.findById(id);
        if (movie.getActors() != null) {
            for (Actor actor : movie.getActors()) {
                actor.getMovies().remove(movie);
            }
        }
        movieService.delete(movie);
        return movie;
    }
}
