package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ReceiptDetailDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.pojo.Receiptdetail;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.ReceiptDetailRepository;
import com.htt.ecourse.repository.ReceiptRepository;
import com.htt.ecourse.service.ReceiptDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptDetailServiceImpl implements ReceiptDetailService {
    private final ReceiptDetailRepository receiptDetailRepository;
    private final ReceiptRepository receiptRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Receiptdetail> findByReceiptId(Long receiptId) {
        return receiptDetailRepository.findByReceiptId(receiptId);
    }

    @Override
    public Receiptdetail getReceiptDetail(Long id) {
        return receiptDetailRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Cannot find receipt id "  + id));
    }

    @Override
    public Receiptdetail createReceiptDetail(ReceiptDetailDTO receiptDetailDTO) throws DataNotFoundException {
        // tim xem receipt id co ton tai khong
        Receipt receipt = receiptRepository.findById(receiptDetailDTO.getReceiptId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find receipt id " + receiptDetailDTO.getReceiptId()));
        Course course = courseRepository.findById(receiptDetailDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find course id " + receiptDetailDTO.getCourseId()));
        Receiptdetail receiptDetail = Receiptdetail.builder()
                .receipt(receipt)
                .course(course)
                .price(receiptDetailDTO.getPrice())
                .totalMoney(receiptDetailDTO.getTotalMoney())
                .quantity(receiptDetailDTO.getQuantity())
                .build();
        return receiptDetailRepository.save(receiptDetail);
    }
}