package com.example.budgetbackend.controller;

import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
import com.example.budgetbackend.service.PaycheckService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paychecks")
public class PaycheckController {

    private final PaycheckService paycheckService;

    public PaycheckController(PaycheckService paycheckService){
        this.paycheckService = paycheckService;
    }

    @GetMapping
    public ResponseEntity<List<Paycheck>> getAllPaycheck(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paycheckService.getAllPaychecks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paycheck> getPaycheckById(@PathVariable long id){
        Optional<Paycheck> paycheck = paycheckService.getPaycheckById(id);
        return paycheck.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<PaycheckItem> getPaycheckItemById(@PathVariable long id){
        Optional<PaycheckItem> paycheckItem = paycheckService.getPaycheckItemById(id);
        return paycheckItem.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Paycheck> createPaycheck(@RequestBody Paycheck paycheck){
        Paycheck savedPaycheck = paycheckService.createPaycheck(paycheck);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedPaycheck);
    }

    @PostMapping("/items")
    public ResponseEntity<PaycheckItem> createPaycheckItem(@RequestParam Long paycheckId,
                                                           @RequestParam String category,
                                                           @RequestBody PaycheckItem paycheckItem
    ){
        PaycheckItem savedPaycheckItem = paycheckService.createPaycheckItem(paycheckId, category ,paycheckItem);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedPaycheckItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paycheck> updatePaycheckItem(@PathVariable long id,
                                                       @RequestBody Paycheck paycheck
    ){
        Optional<Paycheck> updatedPaycheck = paycheckService.updatePaycheck(id, paycheck);
        return updatedPaycheck.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<PaycheckItem> updatePaycheckItem(@PathVariable long id,
                                                           @RequestParam long paycheckId,
                                                           @RequestParam String category,
                                                           @RequestBody PaycheckItem paycheckItem
    ) {
        Optional<PaycheckItem> updatedPaycheckItem =
                paycheckService.updatePaycheckItem(id, paycheckId, category, paycheckItem);
        return updatedPaycheckItem.map(value -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Paycheck> DeletePaycheck(@PathVariable long id){
        if(paycheckService.deletePaycheck(id)){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<PaycheckItem> deletePaycheckItem(@PathVariable long id){
        if(paycheckService.deletePaycheckItem(id)){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
