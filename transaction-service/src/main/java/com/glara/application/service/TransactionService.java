package com.glara.application.service;

import com.glara.dto.TransactionDTO;
import com.glara.infrastructure.repository.TransactionRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    public Uni<TransactionDTO> findByIdTransaction(UUID id) {
        return transactionRepository.findByIdTransaction(id)
                .onItem().ifNull().failWith(new Exception("Transaction not found"));
    }

    public Uni<List<TransactionDTO>> findAllTransaction(Integer size, Integer page) {
        return transactionRepository.findAllTransaction(size, page)
                .onItem().ifNull().failWith(new Exception("Transactions not found"));
    }

    public Uni<TransactionDTO> createTransaction(TransactionDTO transactionDTO) {
        return transactionRepository.createTransaction(transactionDTO)
                .onItem().ifNull().failWith(new Exception("Transaction not created"));
    }

    public Uni<TransactionDTO> updateTransaction(UUID id, TransactionDTO transactionDTO) {
        return transactionRepository.updateTransaction(id, transactionDTO)
                .onItem().ifNull().failWith(new Exception("Transaction not updated"));
    }

    public Uni<Void> deleteTransaction(UUID id) {
        return transactionRepository.deleteTransaction(id)
                .onItem().ifNull().failWith(new Exception("Transaction not deleted"))
                .onItem().ignore().andContinueWithNull();
    }

}
