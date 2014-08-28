package com.kharkiv.board.dto.schedule;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;
import com.kharkiv.board.dto.BaseEntity;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.CommentQueries;

@Entity
@Table(name = "comments")
@NamedQueries(value = {
        @NamedQuery(name = CommentQueries.GET_4_USER_BY_USER_ID, query = "SELECT c FROM Comment c WHERE c.user.id = :userId"),
        @NamedQuery(name = CommentQueries.GET_4_USER_BY_USER_LOGIN, query = "SELECT c FROM Comment c WHERE c.user.login = :login"),
        @NamedQuery(name = CommentQueries.GET_4_SCHEDULE_BY_SCHEDULE_ID, query = "SELECT c FROM Comment c WHERE c.schedule.id = :scheduleId"),
        @NamedQuery(name = CommentQueries.DELETE_BY_ID, query = "DELETE FROM Comment c WHERE c.id = :id") })
public class Comment extends BaseEntity {

    private static final long serialVersionUID = -6913962017810044373L;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", super.getId()).add("user", user.getLogin())
                .add("schedule", schedule.getDateTime()).toString();
    }
}
