package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.responses.ReceiptResponse;
import com.htt.ecourse.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final EnrollmentRepository enrollmentRepository;
    private final ReceiptRepository receiptRepository;
    private final ReceiptDetailRepository receiptDetailRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<Receipt> getAllReceipts(PageRequest pageRequest, String keyword) {
        return receiptRepository
                .searchReceiptsAll(keyword, pageRequest);
    }

    @Override
    public List<Receipt> findByUserId(Long userId) {
        return receiptRepository.findByUserId(userId);
    }

    @Override
    public Receipt getReceipt(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find receipt with id " + id));

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
    public void addReceipt(List<Cart> cartList) throws DataNotFoundException {
        if (cartList != null){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = this.userRepository.getUserByUsername(username).getId();

            for (Cart cart : cartList) {
                Long courseId = cart.getId();

                Optional<Enrollment> enrollments = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
                if (!enrollments.isEmpty()) {
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "This course had in your list course!!");
                }
            }

            Receipt receipt = new Receipt();
            receipt.setUser(this.userRepository.getUserByUsername(username));
            receipt.setOrderDate(new Date());

            float totalPrice = (float) cartList.stream()
                    .mapToDouble(c ->(c.getPrice() * (1 - c.getDiscount() / 100)))
                    .sum();
            receipt.setTotalMoney(totalPrice);

            receiptRepository.save(receipt);

            for (Cart c : cartList) {
                Receiptdetail d = new Receiptdetail();
                d.setPrice(c.getPrice());
                d.setQuantity(1);
                d.setDiscount(c.getDiscount());
                d.setReceipt(receipt);
                d.setCourse(courseRepository.getCourseById(c.getId()));

                receiptDetailRepository.save(d);

                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentDate(new Date());
                enrollment.setUser(this.userRepository.getUserByUsername(username));
                enrollment.setCourse(courseRepository.getCourseById(c.getId()));

                enrollmentRepository.save(enrollment);
            }

        }

    }
}
