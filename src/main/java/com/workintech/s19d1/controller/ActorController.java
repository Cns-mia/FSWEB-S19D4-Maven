package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.util.HollywoodValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable Long id) {
        HollywoodValidation.checkId(id);
        return actorService.findById(id);
    }

    @PostMapping
    public Actor save(@RequestBody ActorRequest actorRequest) {
        Actor actor = actorRequest.getActor();
        HollywoodValidation.checkActor(actor);
        actor.setMovies(new java.util.ArrayList<>());
        if (actorRequest.getMovies() != null) {
            for (Movie movie : actorRequest.getMovies()) {
                actor.addMovie(movie);
                movie.addActor(actor);
            }
        }
        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(@PathVariable Long id, @RequestBody Actor actor) {
        HollywoodValidation.checkId(id);
        Actor existing = actorService.findById(id);
        existing.setFirstName(actor.getFirstName());
        existing.setLastName(actor.getLastName());
        existing.setGender(actor.getGender());
        existing.setBirthDate(actor.getBirthDate());
        return actorService.save(existing);
    }

    @DeleteMapping("/{id}")
    public Actor delete(@PathVariable Long id) {
        HollywoodValidation.checkId(id);
        Actor actor = actorService.findById(id);
        actorService.delete(actor);
        return actor;
    }
}
