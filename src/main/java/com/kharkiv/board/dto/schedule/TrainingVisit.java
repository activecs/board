package com.kharkiv.board.dto.schedule;

import static com.google.common.base.MoreObjects.toStringHelper;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.kharkiv.board.dto.BaseEntity;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.TrainingVisitsQueris;

@Entity
@Table(name = "training_visits", indexes = { @Index(name = "visits_user_index", columnList = "user_id"),
        @Index(name = "visits_schedule_index", columnList = "schedule_id") })
@NamedQueries(value = {
        @NamedQuery(name = TrainingVisitsQueris.GET_4_USER_BY_USER_ID, query = "SELECT tv FROM TrainingVisit tv WHERE tv.user.id = :userId"),
        @NamedQuery(name = TrainingVisitsQueris.GET_4_SCHEDULE_BY_SCHEDULE_ID, query = "SELECT tv FROM TrainingVisit tv WHERE tv.schedule.id = :scheduleId"),
        @NamedQuery(name = TrainingVisitsQueris.DELETE_BY_ID, query = "DELETE FROM TrainingVisit tv WHERE tv.id = :id") })
public class TrainingVisit extends BaseEntity {

    private static final long serialVersionUID = 514563793854713484L;

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

    @Override
    public String toString() {
        return toStringHelper(this)
        		.add("id", super.getId())
        		.add("username", user.getUsername())
                .add("schedule", schedule.getDateTime())
                .toString();
    }
}
