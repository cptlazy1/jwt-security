package com.retrogj.security.controller;

import com.retrogj.security.dto.GameSystemDto;
import com.retrogj.security.service.GameSystemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class GameSystemController {

    private final GameSystemService gameSystemService;

    // PostMapping to add game system
    @PostMapping("/game-systems")
    public ResponseEntity<GameSystemDto> addGameSystem(@Valid @RequestBody GameSystemDto dto) {
        GameSystemDto gameSystemDto = gameSystemService.addGameSystem(dto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/game-systems/{id}")
                .buildAndExpand(gameSystemDto.getGameSystemID())
                .toUriString());

        return ResponseEntity.created(uri).body(gameSystemDto);
    }

    // PutMapping to update game system
    @PutMapping("/game-systems/{id}")
    public ResponseEntity<GameSystemDto> updateGameSystem(@PathVariable("id") Long gameSystemID, @Valid @RequestBody GameSystemDto dto)  {
        GameSystemDto gameSystemDto = gameSystemService.updateGameSystem(gameSystemID, dto);
        return ResponseEntity.ok().body(gameSystemDto);
    }

    // DeleteMapping to delete game system
    @DeleteMapping("/game-systems/{id}")
    public ResponseEntity<Void> deleteGameSystem(@PathVariable("id") Long gameSystemID) {
        gameSystemService.deleteGameSystem(gameSystemID);
        return ResponseEntity.noContent().build();
    }

    // PutMapping to assign game condition to game system
    @PutMapping("/game-systems/{gameSystemID}/game-system-conditions/{gameSystemConditionID}")
    public ResponseEntity<String> assignGameSystemCondition(@PathVariable("gameSystemID") Long gameSystemID, @PathVariable("gameSystemConditionID") Long gameSystemConditionID)  {
        gameSystemService.assignGameSystemCondition(gameSystemID, gameSystemConditionID);
        return ResponseEntity.ok().body("Game system condition assigned successfully to game system");
    }

}
