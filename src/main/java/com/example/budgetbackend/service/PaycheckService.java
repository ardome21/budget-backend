package com.example.budgetbackend.service;

import com.example.budgetbackend.entity.PaycheckDO;
import com.example.budgetbackend.entity.PaycheckItemDO;
import com.example.budgetbackend.mapper.PaycheckMapper;
import com.example.budgetbackend.model.Paycheck;
import com.example.budgetbackend.model.PaycheckItem;
import com.example.budgetbackend.repository.PaycheckItemRepository;
import com.example.budgetbackend.repository.PaycheckRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Optional<PaycheckItem> getPaycheckItemById(Long id) {
        return paycheckItemRepository.findById(id)
                .map(paycheckMapper::entityItemToModelItem);
    }

    // SAVE METHODS
    public Paycheck savePaycheck(Paycheck paycheck) {
        PaycheckDO paycheckDO = paycheckMapper.modelToEntity(paycheck);
        paycheckRepository.save(paycheckDO);
        paycheck.setId(paycheckDO.getId());
        List<PaycheckItemDO> paycheckItemDOList = paycheckMapper.modelToEntityList(paycheck);
        List<PaycheckItemDO> savedPaycheckItemDOList = paycheckItemDOList.stream()
                .map(paycheckItemRepository::save)
                .toList();
        return paycheckMapper.entityListToModel(savedPaycheckItemDOList);
    }

    public PaycheckItem savePaycheckItem(Long paycheckID, String category, PaycheckItem paycheckItem) {
        // TODO: Add restrictions
        // 1. category can't be gross_pay -> Give error to update, not save
        // 2. If entry of matching paycheckId, category, item.label exist -> Give error to update, not save
        PaycheckItemDO paycheckItemDO = paycheckMapper.modelItemToEntityItem(paycheckID, category, paycheckItem);
        PaycheckItemDO savedPaycheckItem = paycheckItemRepository.save(paycheckItemDO);
        return paycheckMapper.entityItemToModelItem(savedPaycheckItem);
    }

    // UPDATE METHODS

    // TODO: Utilize
    public Optional<Paycheck> updatePaycheck(
            Long id,
            Paycheck paycheck
    ) {
        if (!paycheckRepository.existsById(id)) {
            return Optional.empty();
        }
        List<PaycheckItemDO> paycheckItemDOList = paycheckMapper.modelToEntityList(paycheck);
        List<PaycheckItemDO> savedPaycheckDO = paycheckItemDOList.stream()
                .map(paycheckItemRepository::save)
                .toList();
        return Optional.ofNullable(paycheckMapper.entityListToModel(savedPaycheckDO));
    }

    public Optional<PaycheckItem> updatePaycheckItem(
            Long id,
            Long paycheckId,
            String category,
            PaycheckItem paycheckItem
    ) {
        if (!paycheckRepository.existsById(paycheckId)) {
            throw new EntityNotFoundException("Paycheck with ID " + paycheckId + " does not exist");
        }
        if (paycheckItemRepository.existsById(id)) {
            return Optional.empty();
        }
        PaycheckItemDO paycheckItemDO = paycheckMapper.modelItemToEntityItem(paycheckId, category, paycheckItem);
        PaycheckItemDO savedPaycheckItemDO = paycheckItemRepository.save(paycheckItemDO);
        return Optional.of(paycheckMapper.entityItemToModelItem(savedPaycheckItemDO));
    }

    // DELETE METHODS
    public boolean deletePaycheck(Long id){
        if(paycheckRepository.existsById(id)){
            paycheckRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public boolean deletePaycheckItem(Long id) {
        if(paycheckItemRepository.existsById(id)){
            paycheckItemRepository.deleteById(id);
            return true;
        }
        return false;
    }


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
