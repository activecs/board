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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.TrainigVisitsQueris;

@Entity
@Table(name = "training_visits", indexes = { @Index(name = "visits_user_index", columnList = "user_id"),
        @Index(name = "visits_schedule_index", columnList = "schedule_id") })
@NamedQueries(value = {
        @NamedQuery(name = TrainigVisitsQueris.GET_4_USER_BY_USER_ID, query = "SELECT tv FROM TrainingVisit tv WHERE tv.user.id = :userId"),
        @NamedQuery(name = TrainigVisitsQueris.GET_4_SCHEDULE_BY_SCHEDULE_ID, query = "SELECT tv FROM TrainingVisit tv WHERE tv.schedule.id = :scheduleId"),
        @NamedQuery(name = TrainigVisitsQueris.DELETE_BY_ID, query = "DELETE FROM TrainingVisit tv WHERE tv.id = :id") })
public class TrainingVisit implements Serializable {

    private static final long serialVersionUID = 4140929018424276729L;

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

    public void setId(Integer id) {
        this.id = id;
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
        if (!(obj instanceof TrainingVisit))
            return false;
        return Objects.equal(id, ((TrainingVisit) obj).id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("login", user.getLogin())
                .add("schedule", schedule.getDateTime()).toString();
    }
}
