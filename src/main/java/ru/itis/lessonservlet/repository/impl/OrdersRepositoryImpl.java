package ru.itis.lessonservlet.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.lessonservlet.mapper.OrderMapper;
import ru.itis.lessonservlet.mapper.impl.OrderMapperImpl;
import ru.itis.lessonservlet.model.OrdersEntity;
import ru.itis.lessonservlet.repository.OrdersRepository;


import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_INTO_ORDERS = "insert into orders (user_id, product_id, order_date, status_code) values (?, ?, ?, ?)";

    private final static String SELECT_ORDERS_BY_ID = "SELECT * FROM orders WHERE user_id = ?";

    private final static String UPDATE_STATUS_CODE_BY_ID_ORDERS = "UPDATE orders SET status_code = ? WHERE id = ?";

    private static final String DELETE_ORDERS_BY_ID = "DELETE FROM orders WHERE id = ?";

    private final OrderMapper orderMapper;

    @Override
    public void save(OrdersEntity order) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_INTO_ORDERS, new String[]{"id"});
            ps.setLong(1, order.getUserId());
            ps.setLong(2, order.getProductId());
            ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            ps.setString(4, order.getStatusCode());
            return ps;
        }, holder);
    }

    @Override
    public List<OrdersEntity> findAllByUserId(Long userId) {
        return jdbcTemplate.query(SELECT_ORDERS_BY_ID, orderMapper, userId);
    }

    @Override
    public void updateStatus(Long orderId, String statusCode) {
        jdbcTemplate.update(UPDATE_STATUS_CODE_BY_ID_ORDERS, orderId, statusCode);
    }

    @Override
    public void delete(Long orderId) {
        jdbcTemplate.query(DELETE_ORDERS_BY_ID, orderMapper, orderId);
    }
}
