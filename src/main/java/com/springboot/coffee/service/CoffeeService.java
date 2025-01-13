package com.springboot.coffee.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.repository.CoffeeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;

import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import com.springboot.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee createCoffee(Coffee coffee) {
        verifyExistCoffeeCode(coffee.getCoffeeCode());
        return coffeeRepository.save(coffee);
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }

    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId ());
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        Optional.ofNullable((coffee.getKorName()))
                .ifPresent(korName -> findCoffee.setKorName(korName));

        Optional.ofNullable((coffee.getEngName()))
                .ifPresent(engName -> findCoffee.setEngName(engName));

        Optional.ofNullable((coffee.getPrice()))
                .ifPresent(price -> findCoffee.setPrice(price));

        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId) {
//        Coffee findCoffee = findVerifiedCoffee (coffeeId);
        return findVerifiedCoffee(coffeeId);
    }

    public List<Coffee> findCoffees() {
        return (List<Coffee>) coffeeRepository.findAll();
    }

    public void deleteCoffee(long coffeeId) {
        Coffee findCoffee = findVerifiedCoffee(coffeeId);
        coffeeRepository.delete(findCoffee);
    }

    // 주문에 해당하는 커피 정보 조회
    public List<Coffee> findOrderedCoffees(Order order) {
        List<Coffee> findCoffees = order.getOrderCoffeees().stream()
                .map(orderCoffee -> findVerifiedCoffee(orderCoffee.getCoffeeId()))
                .collect(Collectors.toList());
        return findCoffees;
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }

    private void verifyExistCoffeeCode(String coffeeCode) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffeeCode(coffeeCode);

        if(optionalCoffee.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }

    private Coffee findVerifiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);

        Coffee findCoffee = optionalCoffee.orElseThrow (() ->
                new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));

        return findCoffee;
    }

//    private Coffee findOrderCoffee(Order order) {
//        Optional<Coffee> optionalCoffee = coffeeRepository.findById (orderCoffee.getCoffeeId ());
//
//        List<Coffee> findCoffee = optionalCoffee.orElseThrow (()->
//                new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
//
//        return findCoffee;
//    }

}
