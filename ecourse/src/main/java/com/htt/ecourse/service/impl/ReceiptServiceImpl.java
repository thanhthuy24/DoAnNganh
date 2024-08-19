package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.ReceiptRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.ReceipService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceipService {
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Receipt> getAllReceipts() {

        return List.of();
    }

    @Override
    public List<Receipt> findByUserId(Long userId) {
        return receiptRepository.findByUserId(userId);
    }

    @Override
    public Receipt getReceipt(Long id) {
        return receiptRepository.findById(id).orElse(null);

    }

    @Override
    public Receipt createReceipt(ReceiptDTO receiptDTO) {
        User user = userRepository.findById(receiptDTO.getUserId())
                .orElseThrow(() -> new DateTimeException("Cannot find user with id " + receiptDTO.getUserId()));
        //convert ReceiptDTO -> Receipt in database
        // tao luong bang anh xa rieng de kiem soat viec anh xa
        modelMapper.typeMap(ReceiptDTO.class, Receipt.class)
                .addMappings(mapper -> mapper.skip(Receipt::setId));

        //cap nhat cac truong cua don hang tu ReceiptDTO
        Receipt receipt = new Receipt();
        modelMapper.map(receiptDTO, receipt);
        receipt.setUser(user);
        receipt.setOrderDate(new Date());
        receiptRepository.save(receipt);
        return receipt;
    }

    @Override
    public Receipt updateReceipt(Long receiptId, ReceiptDTO receiptDTO) {
        return null;
    }

    @Override
    public void deleteReceipt(Long id) {

    }
}
