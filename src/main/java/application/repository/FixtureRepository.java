package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Fixture;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {
}