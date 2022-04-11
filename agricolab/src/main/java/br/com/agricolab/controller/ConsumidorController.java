package br.com.agricolab.controller;

import br.com.agricolab.core.consumidor.dto.ConsumidorDto;
import br.com.agricolab.core.consumidor.mapper.ConsumidorDtoMapper;
import br.com.agricolab.core.consumidor.processors.ConsumidorProcessor;
import br.com.agricolab.domain.Consumidor;
import br.com.agricolab.repository.adapter.ConsumidorRepository;
import br.com.agricolab.repository.mapper.ConsumidorEntityMapper;
import br.com.agricolab.repository.model.ConsumidorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/consumidor")
public class ConsumidorController {

    @Autowired
    private ConsumidorRepository consumidorRepository;

    @Autowired
    private ConsumidorProcessor consumidorProcessor;

    ConsumidorController(ConsumidorRepository consumidorRepository,ConsumidorProcessor consumidorProcessor) {
        this.consumidorRepository = consumidorRepository;
        this.consumidorProcessor = consumidorProcessor;
    }

    @GetMapping(path = "/all")
    public List findAll(){
        return consumidorRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ConsumidorDto findById(@PathVariable Integer id){
        Optional<ConsumidorEntity> consumidorEntity = consumidorRepository.findById(id);

        if(consumidorEntity.isPresent()){
            Consumidor consumidor = ConsumidorEntityMapper.INSTANCE.ConsumidorToEntity(consumidorEntity.get());
            return ConsumidorDtoMapper.INSTANCE.consumidorToDto(consumidor);
        }
        return new ConsumidorDto();
    }

    @PostMapping
    public ConsumidorDto create(@RequestBody ConsumidorDto consumidorRequest){

        Consumidor consumidor = ConsumidorDtoMapper.INSTANCE.consumidorToDto(consumidorRequest);

        consumidor = consumidorProcessor.createConsumidor(consumidor);

        return ConsumidorDtoMapper.INSTANCE.consumidorToDto(consumidor);
    }

    @PatchMapping("/{id}")
    ConsumidorDto modificaConsumidor(@RequestBody ConsumidorDto novoConsumidor, @PathVariable Integer id) {
        Consumidor consumidorRequest = ConsumidorDtoMapper.INSTANCE.consumidorToDto(novoConsumidor);

        Consumidor consumidor = consumidorProcessor.modificaConsumidor(consumidorRequest,id);

        return ConsumidorDtoMapper.INSTANCE.consumidorToDto(consumidor);

    }

    @DeleteMapping("/{id}")
    void deleteProdutor(@PathVariable Integer id) {
        consumidorRepository.deleteById(id);
    }

}
