package com.liveCodeTodoList.liveCodeTodo.utils.specification;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserTodoSpecification {

    public static Specification<Todo> getSpecification(User user, TaskStatus status, String sortBy, String order) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user"), user));

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            query.where(predicates.toArray(new Predicate[0]));

            if (sortBy != null && !sortBy.isEmpty()) {
                System.out.println("lojhdbjsnjhujbn");
                if (Objects.equals(order, "asc")) {
                    Order descAsc =  criteriaBuilder.asc(root.get(sortBy));
                    query.orderBy(descAsc);
                } else {
                    Order descAsc = criteriaBuilder.desc(root.get(sortBy));
                    query.orderBy(descAsc);
                }
            }

            return query.getRestriction();
        };
    }

}
