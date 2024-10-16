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

    public Paycheck createPaycheck(Paycheck paycheck) {
        PaycheckDO paycheckDO = paycheckMapper.modelToEntity(paycheck);
        paycheckRepository.save(paycheckDO);
        paycheck.setId(paycheckDO.getId());
        List<PaycheckItemDO> paycheckItemDOList = paycheckMapper.modelToEntityList(paycheck);
        List<PaycheckItemDO> savedPaycheckItemDOList = paycheckItemDOList.stream()
                .map(paycheckItemRepository::save)
                .toList();
        return paycheckMapper.entityListToModel(savedPaycheckItemDOList);
    }

    public PaycheckItem createPaycheckItem(Long paycheckID, String category, PaycheckItem paycheckItem) {
        if(category.equals("gross_pay")){
            throw new IllegalArgumentException("Cannot create new gross pay. Please update existing gross pay instead.");
        }
        throwIfPaycheckItemExists(paycheckID,category,paycheckItem);

        PaycheckItemDO paycheckItemDO = paycheckMapper.modelItemToEntityItem(paycheckID, category, paycheckItem);
        PaycheckItemDO savedPaycheckItem = paycheckItemRepository.save(paycheckItemDO);
        return paycheckMapper.entityItemToModelItem(savedPaycheckItem);
    }

    public Optional<Paycheck> updatePaycheck(
            Long id,
            Paycheck paycheck
    ) {
        if (!paycheckRepository.existsById(id)) {
            throw new EntityNotFoundException("Paycheck with ID " + paycheck + " does not exist");
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
        Optional<PaycheckItemDO> curItemDO = paycheckItemRepository.findById(id);
        if (curItemDO.isEmpty()) {
            throw new EntityNotFoundException("Paycheck Item with ID " + id + " does not exist");
        }
        throwIfPaycheckItemBelongsToOtherPaycheck(curItemDO.get(), paycheckId, id);

        PaycheckItemDO paycheckItemDO = paycheckMapper.modelItemToEntityItem(paycheckId, category, paycheckItem);
        PaycheckItemDO savedPaycheckItemDO = paycheckItemRepository.save(paycheckItemDO);
        return Optional.of(paycheckMapper.entityItemToModelItem(savedPaycheckItemDO));
    }



    public boolean deletePaycheck(Long id){
        if(paycheckRepository.existsById(id)){
            paycheckRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deletePaycheckItem(Long id) {
        Optional<PaycheckItemDO> paycheckItem = paycheckItemRepository.findById(id);
        if (paycheckItem.isEmpty()) {
            return false;
        }
        if (paycheckItem.get().getCategory().equals("gross_pay")) {
            throw new UnsupportedOperationException("You cannot delete the gross pay of a paycheck. " +
                    "Update the value, or delete the entire paycheck.");
        }
        paycheckItemRepository.deleteById(id);
        return true;
    }

    private double calculateTakeHome(Paycheck paycheck) {
        double grossPay = paycheck.getGrossPay().getValue();
        double taxes = calculatePaycheckDeduction(paycheck.getTaxes());
        double benefits = calculatePaycheckDeduction(paycheck.getBenefits());
        double retirement = calculatePaycheckDeduction(paycheck.getRetirement());

        return grossPay - taxes - benefits - retirement;
    }

    private double calculatePaycheckDeduction(List<PaycheckItem> itemList){
        return itemList.stream()
                .mapToDouble(PaycheckItem::getValue)
                .sum();
    }

    private Paycheck setTakeHomeForPaycheck(Paycheck paycheck){
        double takeHome = calculateTakeHome(paycheck);
        paycheck.setTakeHome(takeHome);
        return paycheck;
    }

    private void throwIfPaycheckItemExists(Long paycheckID, String category, PaycheckItem paycheckItem) {
        Optional<PaycheckItemDO> existingPaycheckItem = paycheckItemRepository
                .findByPaycheckIdAndCategoryAndLabel(
                        paycheckID,
                        category,
                        paycheckItem.getLabel()
                );
        if(existingPaycheckItem.isPresent()){
            Long id = existingPaycheckItem.get().getId();
            throw new IllegalArgumentException(
                    "A paycheck item with category '" + category + "' and label '" + paycheckItem.getLabel() +
                            "' already exists for paycheck ID: " + paycheckID + " (Paycheck Item ID: " + id +
                            "). Please update the existing item."
            );
        }
    }
    private void throwIfPaycheckItemBelongsToOtherPaycheck(PaycheckItemDO curItemDO, Long paycheckId, Long id) {
        Long curPaycheckId = curItemDO.getPaycheckId();
        if(!paycheckId.equals(curPaycheckId)) {
            throw new IllegalArgumentException("PaycheckItem with ID " + id + " already belongs to Paycheck of ID "
                    + curPaycheckId + ". Cannot moved to another Paycheck");
        }
    }
}
