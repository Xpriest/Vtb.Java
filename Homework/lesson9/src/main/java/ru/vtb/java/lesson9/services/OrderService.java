package ru.vtb.java.lesson9.services;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vtb.java.lesson9.models.Order;
import ru.vtb.java.lesson9.repositories.OrderRepository;
import ru.vtb.java.lesson9.repositories.entities.OrderEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository ordersRepository;

    @Autowired
    public OrderService(OrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Order> getAll() {
        var result = new ArrayList<Order>();
        var ordersEntities = ordersRepository.getAll();

        for (var singleOrderEntity: ordersEntities) {
            result.add(MapFromEntityToDto(singleOrderEntity));
        }

        return result;
    }

    public Order get(Long id){
        var orderEntity = ordersRepository.get(id);

        var result = MapFromEntityToDto(orderEntity);
        return result;
    }

    public void insert(Order inputOrder) {
        ordersRepository.insert(MapFromDtoToEntity(inputOrder));
    }

    public void update(Order inputOrder) {
        ordersRepository.update(MapFromDtoToEntity(inputOrder));
    }

    // Наш автомапер=)
    private Order MapFromEntityToDto(OrderEntity inputOrderEntity) {
        if (inputOrderEntity == null)
            return null;

        var result = new Order();
        result.setTitle(inputOrderEntity.getTitle());
        result.setId(inputOrderEntity.getId());

        return result;
    }

    private OrderEntity MapFromDtoToEntity(Order inputOrder) {
        if (inputOrder == null)
            return null;

        var result = new OrderEntity();
        result.setTitle(inputOrder.getTitle());
        result.setId(inputOrder.getId());

        return result;
    }
}
