package com.retrogj.security.service;

import com.retrogj.security.dto.GameSystemDto;
import com.retrogj.security.exception.BadRequestException;
import com.retrogj.security.exception.RecordNotFoundException;
import com.retrogj.security.model.GameSystem;
import com.retrogj.security.model.GameSystemCondition;
import com.retrogj.security.repository.GameSystemConditionRepository;
import com.retrogj.security.repository.GameSystemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSystemService {

    private final GameSystemRepository gameSystemRepository;

    private final GameSystemConditionRepository gameSystemConditionRepository;

    // Method to add game system with null check for the game system name
    public GameSystemDto addGameSystem(GameSystemDto gameSystemDto) throws BadRequestException {
        if (gameSystemDto.getGameSystemName() == null) {
            throw new BadRequestException("Game system name cannot be null");
        } else {
            GameSystem gameSystem = convertToGameSystem(gameSystemDto);
            GameSystem savedGameSystem = gameSystemRepository.save(gameSystem);
            return convertToGameSystemDto(savedGameSystem);
        }

    }

    // Method to update game system by gameSystemID
    public GameSystemDto updateGameSystem(Long gameSystemID, GameSystemDto gameSystemDto) {

        Optional<GameSystem> gameSystemOptional = gameSystemRepository.findById(gameSystemID);

        if (gameSystemOptional.isPresent()) {
            GameSystem gameSystem = gameSystemOptional.get();

            // Manual null check for each field
            if (gameSystemDto.getGameSystemName() != null) {
                gameSystem.setGameSystemName(gameSystemDto.getGameSystemName());
            }
            if (gameSystemDto.getGameSystemReview() != null) {
                gameSystem.setGameSystemReview(gameSystemDto.getGameSystemReview());
            }
            if (gameSystemDto.getGameSystemRating() != null) {
                gameSystem.setGameSystemRating(gameSystemDto.getGameSystemRating());
            }

            GameSystem updatedGameSystem = gameSystemRepository.save(gameSystem);
            return convertToGameSystemDto(updatedGameSystem);
        } else {
            throw new RecordNotFoundException("No game system record exists for given gameSystemID");
        }
    }

    // Method to delete game system by gameSystemID
    public void deleteGameSystem(Long gameSystemID) {
        Optional<GameSystem> gameSystemOptional = gameSystemRepository.findById(gameSystemID);
        if (gameSystemOptional.isPresent()) {
            gameSystemRepository.deleteById(gameSystemID);
        } else {
            throw new RecordNotFoundException("No game system record exists for given gameSystemID");
        }
    }


    // Method to assign game system condition to game system
    public void assignGameSystemCondition(Long gameSystemID, Long gameSystemConditionID) {

        Optional<GameSystem> gameSystemOptional = gameSystemRepository.findById(gameSystemID);
        Optional<GameSystemCondition> gameSystemConditionOptional = gameSystemConditionRepository.findById(gameSystemConditionID);

        if (gameSystemOptional.isPresent() && gameSystemConditionOptional.isPresent()) {
            GameSystem gameSystem = gameSystemOptional.get();
            GameSystemCondition gameSystemCondition = gameSystemConditionOptional.get();
            gameSystem.setGameSystemCondition(gameSystemCondition);
            gameSystemRepository.save(gameSystem);
        } else {
            throw new RecordNotFoundException("No game system record exists for given gameSystemID");
        }
    }



    // Method to convert GameSystemDto to GameSystem with ModelMapper
    public GameSystem convertToGameSystem(GameSystemDto gameSystemDto) {
        ModelMapper modelMapper = new ModelMapper();
        GameSystem gameSystem = modelMapper.map(gameSystemDto, GameSystem.class);
        return gameSystem;
    }

    // Method to convert GameSystem to GameSystemDto with ModelMapper
    public GameSystemDto convertToGameSystemDto(GameSystem gameSystem) {
        ModelMapper modelMapper = new ModelMapper();
        GameSystemDto gameSystemDto = modelMapper.map(gameSystem, GameSystemDto.class);
        return gameSystemDto;
    }

}
