package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOperationCategoryStorage extends JpaRepository<OperationCategoryEntity, UUID> {
    Optional<OperationCategoryEntity> findByTitle(String title);
}
