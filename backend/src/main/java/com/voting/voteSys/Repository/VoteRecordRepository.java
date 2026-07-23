package com.voting.voteSys.Repository;

import com.voting.voteSys.model.Poll;
import com.voting.voteSys.model.User;
import com.voting.voteSys.model.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    Optional<VoteRecord> findByUserAndPoll(User user, Poll poll);

    boolean existsByUserAndPoll(User user, Poll poll);
}
