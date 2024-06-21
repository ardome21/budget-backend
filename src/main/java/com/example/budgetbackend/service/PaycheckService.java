package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.repository.PaycheckItemRepository;
import com.example.budgetbackend.repository.PaycheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaycheckService {
    private final PaycheckRepository paycheckRepository;

    private final PaycheckItemRepository paycheckItemRepository;

    private final PaycheckMapper paycheckMapper;

    @Autowired
    public PaycheckService(PaycheckRepository paycheckRepository,
                           PaycheckItemRepository paycheckItemRepository,
                           PaycheckMapper paycheckMapper){
        this.paycheckRepository = paycheckRepository;
        this.paycheckItemRepository = paycheckItemRepository;
        this.paycheckMapper = paycheckMapper;
    }

    // Get all paycheck ids
    public List<Long> getAllPaycheckIds(){
        return paycheckRepository.findAll()
                .stream()
                .map(PaycheckDO::getId)
                .collect(Collectors.toList());
    }

    public List<Paycheck> getAllPaychecks(){
        return getAllPaycheckIds()
                .stream()
                .map(this::getPaycheckItemsByPaycheckId)
                .map(paycheckMapper::entityListToModel)
                .collect(Collectors.toList());
    }

    public Optional<Paycheck> getPaycheckById(Long id){
         List<PaycheckItemDO> paycheckItemDOS = getPaycheckItemsByPaycheckId(id);
         Paycheck paycheck = paycheckMapper.entityListToModel(paycheckItemDOS);
         return Optional.ofNullable(paycheck);
    }

    public Paycheck savePaycheck(Paycheck paycheck){
        // Step 1: Convert Paycheck into PaycheckDO and List<PaycheckItemDO>
        // Step 2: Save PaycheckDO into Paycheck Table
        // Step 3: Save each PaycheckItemDO into PaycheckItems table
        // Step 4: Convert list of PaycheckItemDOs back into model
        // Step 5: return
        return null;
    }

    public Optional<Paycheck> updatePaycheck(
            Long id,
            Paycheck paycheck
    ){
        // Step 1: Make Sure Paycheck of given ID exists
        // Step 2: Convert Paycheck into List<PaycheckItemDO>
        // Step 3: Save each PaycheckItemDO into PaycheckItems Table
        // Step 4: Convert list of result PaycheckItemsDO back into model
        // Step 5: return

        return Optional.empty();
    }

    public boolean deletePaycheck(Long id){
        if(paycheckRepository.existsById(id)){
            paycheckRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PaycheckItemDO> getPaycheckItemsByPaycheckId(Long paycheckId){
        return paycheckItemRepository.findByPaycheckId(paycheckId);
    }

}
