package gmc.project.student.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.student.models.StudentEntity;

public interface StudentDao extends JpaRepository<StudentEntity, Long> {
	Optional<StudentEntity> findByStudentId(String studentId);
}
