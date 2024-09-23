package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
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
                           PaycheckMapper paycheckMapper) {
        this.paycheckRepository = paycheckRepository;
        this.paycheckItemRepository = paycheckItemRepository;
        this.paycheckMapper = paycheckMapper;
    }

    // GET METHODS
    public List<Long> getAllPaycheckIds() {
        return paycheckRepository.findAll()
                .stream()
                .map(PaycheckDO::getId)
                .collect(Collectors.toList());
    }

    public List<PaycheckItemDO> getPaycheckItemsByPaycheckId(Long paycheckId){
        return paycheckItemRepository.findByPaycheckId(paycheckId);
    }

    public List<Paycheck> getAllPaychecks() {
        return getAllPaycheckIds()
                .stream()
                .map(this::getPaycheckItemsByPaycheckId)
                .map(paycheckMapper::entityListToModel)
                .map(this::setTakeHomeForPaycheck)
                .collect(Collectors.toList());
    }

    public Optional<Paycheck> getPaycheckById(Long id) {
        List<PaycheckItemDO> paycheckItemDOS = getPaycheckItemsByPaycheckId(id);
        Paycheck paycheck = paycheckMapper.entityListToModel(paycheckItemDOS);
        return Optional.ofNullable(paycheck);
    }

    // TODO getPaycheckItemById(Long)

    // SAVE METHODS

    public Paycheck savePaycheck(Paycheck paycheck) {
        PaycheckDO paycheckDO = paycheckMapper.modelToEntity(paycheck);
        List<PaycheckItemDO> paycheckItemDOList = paycheckMapper.modelToEntityList(paycheck);
        paycheckRepository.save(paycheckDO);
        List<PaycheckItemDO> savedPaycheckItemDOList = paycheckItemDOList.stream()
                .map(paycheckItemRepository::save)
                .toList();
        return paycheckMapper.entityListToModel(savedPaycheckItemDOList);
    }

    // TODO savePaycheckItem(PaycheckItem)

    // UPDATE METHODS

    public Optional<Paycheck> updatePaycheck(
            Long id,
            Paycheck paycheck
    ) {
        // Step 1: Make Sure Paycheck of given ID exists
        if (!paycheckRepository.existsById(id)) {
            return Optional.empty();
        }
        // Step 2: Convert Paycheck into List<PaycheckItemDO>
        List<PaycheckItemDO> paycheckItemDOList = paycheckMapper.modelToEntityList(paycheck);
        // Step 3: Save each PaycheckItemDO into PaycheckItems Table
        List<PaycheckItemDO> savedPaycheckDO = paycheckItemDOList.stream()
                .map(paycheckItemRepository::save)
                .toList();
        // Step 4: Convert list of result PaycheckItemsDO back into model
        return Optional.ofNullable(paycheckMapper.entityListToModel(savedPaycheckDO));
        // Step 5: return
    }

    // TODO updatePaycheckItem(Long, PaycheckItem)


    // DELETE METHODS
    public boolean deletePaycheck(Long id){
        if(paycheckRepository.existsById(id)){
            paycheckRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // TODO deletePaycheckItem(Long)


    // BUSINESS LOGIC METHODS
    public double calculateTakeHome(Paycheck paycheck) {
        double grossPay = paycheck.getGrossPay().getValue();
        double taxes = calculatePaycheckDeduction(paycheck.getTaxes());
        double benefits = calculatePaycheckDeduction(paycheck.getBenefits());
        double retirement = calculatePaycheckDeduction(paycheck.getRetirement());

        return grossPay - taxes - benefits - retirement;
    }

    public double calculatePaycheckDeduction(List<PaycheckItem> itemList){
        return itemList.stream()
                .mapToDouble(PaycheckItem::getValue)
                .sum();
    }

    public Paycheck setTakeHomeForPaycheck(Paycheck paycheck){
        double takeHome = calculateTakeHome(paycheck);
        paycheck.setTakeHome(takeHome);
        return paycheck;
    }
}
