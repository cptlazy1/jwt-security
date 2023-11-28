package com.retrogj.security.controller;

import com.retrogj.security.dto.GameConditionDto;
import com.retrogj.security.service.GameConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class GameConditionController {

    private final GameConditionService gameConditionService;

    @PostMapping("/game-conditions")
    public ResponseEntity<GameConditionDto> addGameCondition(@RequestBody GameConditionDto dto) {
        GameConditionDto gameConditionDto = gameConditionService.addGameCondition(dto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/game-conditions/{id}")
                .buildAndExpand(gameConditionDto.getGameConditionID())
                .toUriString());

        return ResponseEntity.created(uri).body(gameConditionDto);
    }

    @PutMapping("/game-conditions/{id}")
    public ResponseEntity<GameConditionDto> updateGameCondition(@PathVariable("id") Long gameConditionID, @RequestBody GameConditionDto dto) {
        GameConditionDto gameConditionDto = gameConditionService.updateGameCondition(gameConditionID, dto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/game-conditions/{id}")
                .buildAndExpand(gameConditionDto.getGameConditionID())
                .toUriString());

        return ResponseEntity.created(uri).body(gameConditionDto);
    }

}
