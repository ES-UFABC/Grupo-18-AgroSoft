package br.com.agricolab.controller;


import br.com.agricolab.repository.adapter.ConsumidorRepository;
import br.com.agricolab.repository.adapter.ProdutorRepository;
import br.com.agricolab.repository.model.ConsumidorEntity;
import br.com.agricolab.repository.model.ProdutorEntity;
import br.com.agricolab.service.ConsumidorService;
import br.com.agricolab.service.ProdutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/agricolab/v1")
public class AgricolabController {

    @Autowired
    ConsumidorService consumidorService;

    @Autowired
    ProdutorService produtorService;

    @Autowired
    ConsumidorRepository consumidorRepository;

    @Autowired
    ProdutorRepository produtorRepository;

    @PostMapping(path ="/login")
    public ResponseEntity<ConsumidorEntity> loginUser(@RequestBody ConsumidorEntity request) throws Exception {
        String email = request.getEmailConsumidor();
        String password = request.getSenhaConsumidor();

        if (email != null && password != null) {
            return ResponseEntity.ok(consumidorService.login(email, password));
        }
        return null;
    }

    @PostMapping(path ="produtor/login")
    public ResponseEntity<ProdutorEntity> loginProdutor(@RequestBody ProdutorEntity request) throws Exception {
        String email = request.getEmailProdutor();
        String password = request.getSenhaProdutor();

        if (email != null && password != null) {
            return ResponseEntity.ok(produtorService.login(email, password));
        }
        return null;
    }


    @GetMapping("validacao")
    public boolean validacaoEmail(String email) {
       if(consumidorRepository.findByEmailConsumidor(email) || produtorRepository.findByEmailProdutor(email) ){
           return true;
       }
       return false;

    }

}
