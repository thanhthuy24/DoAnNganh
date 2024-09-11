package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.pojo.Cart;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.ReceiptRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.ReceiptListResponse;
import com.htt.ecourse.responses.ReceiptResponse;
import com.htt.ecourse.service.ReceipService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceipService {
    private final EnrollmentRepository enrollmentRepository;
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ReceiptResponse> getAllReceipts(PageRequest pageRequest) {
        return receiptRepository
                .findAll(pageRequest)
                .map(ReceiptResponse::fromReceipt);
    }

    @Override
    public List<Receipt> findByUserId(Long userId) {
        return receiptRepository.findByUserId(userId);
    }

    @Override
    public Receipt getReceipt(Long id) {
        return receiptRepository.findById(id).orElseThrow(() -> new DateTimeException("Can not find receipt with id " + id));

    }

    @Override
    public Receipt createReceipt(ReceiptDTO receiptDTO) {
        //convert ReceiptDTO -> Receipt in database
        // tao luong bang anh xa rieng de kiem soat viec anh xa
        modelMapper.typeMap(ReceiptDTO.class, Receipt.class)
                .addMappings(mapper -> mapper.skip(Receipt::setId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

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

    @Override
    public void addReceipt(List<Cart> cartList){
        if (cartList != null && cartList.size() > 0){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = this.userRepository.getUserByUsername(username).getId();

            for (Cart cart : cartList){
                Long courseId = cart.getId();

                List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
            }

        }
    }
}
