package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ITestStorage extends JpaRepository<TestEntity, UUID> {
}
