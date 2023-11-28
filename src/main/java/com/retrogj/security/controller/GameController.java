package com.retrogj.security.controller;

import com.retrogj.security.dto.GameDto;

import com.retrogj.security.repository.GameRepository;
import com.retrogj.security.service.GameService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;

    // PostMapping to add game
    @PostMapping("/games")
    public ResponseEntity<GameDto> addGame(@Valid @RequestBody GameDto dto) {
        GameDto gameDto = gameService.addGame(dto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/games/{id}")
                .buildAndExpand(gameDto.getGameID())
                .toUriString());

        return ResponseEntity.created(uri).body(gameDto);
    }


    @PutMapping("/games/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable("id") Long gameID, @Valid @RequestBody GameDto dto)  {
        GameDto gameDto = gameService.updateGame(gameID, dto);
        return ResponseEntity.ok().body(gameDto);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long gameID) {
        gameService.deleteGame(gameID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/games/{gameID}/condition/{gameConditionID}")
    public ResponseEntity<String> assignGameCondition(@PathVariable("gameID") Long gameID, @PathVariable("gameConditionID") Long gameConditionID)  {
        gameService.assignGameCondition(gameID, gameConditionID);
        return ResponseEntity.ok().body("Game condition assigned successfully to game");
    }



}
