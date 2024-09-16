package org.hj.timebean.repository;

import org.hj.timebean.dto.TodoDTO;
import org.hj.timebean.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMemberId(Long memberId);

    List<Todo> findByStatus(boolean status);

    @Query("SELECT new org.hj.timebean.dto.TodoDTO(t.id, t.member.id, t.text, t.completed, t.status, t.recordedDate) FROM Todo t WHERE t.member.id = :memberId AND t.status = :status")
    List<TodoDTO> findByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") boolean status);
}
