package com.example.budgetbackend.repository;

import com.example.budgetbackend.entity.TransactionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionDO,Long> {

    // FIXME: Should add index to DB for date and category?
    @Query("SELECT t FROM TransactionDO t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month AND t.category = :category")
    List<TransactionDO> findAllByYearMonthAndCategory(@Param("year") int year, @Param("month") int month, @Param("category") String category);


}
