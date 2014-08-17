package com.kharkiv.board.dto.schedule;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.kharkiv.board.dto.user.User;

@Entity
@Table(name = "training_visitors", indexes = { @Index(name = "visitors_user_index", columnList = "user_id"),
        @Index(name = "visitors_schedule_index", columnList = "schedule_id") })
public class TrainingVisitor implements Serializable {

    private static final long serialVersionUID = -4855027240087372707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TrainingVisitor))
            return false;
        return Objects.equal(id, ((TrainingVisitor) obj).id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("login", user.getLogin())
                .add("schedule", schedule.getDateTime()).toString();
    }
}
