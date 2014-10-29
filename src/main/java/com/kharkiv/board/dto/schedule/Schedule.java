package com.kharkiv.board.dto.schedule;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import com.google.common.base.MoreObjects;
import com.kharkiv.board.dto.BaseEntity;
import com.kharkiv.board.dto.user.User;
import com.kharkiv.board.util.QueryNamesConstants.ScheduleQueries;

@Entity
@DynamicInsert
@Table(name = "schedules", indexes = @Index(name = "schedule_user_index", columnList = "user_id"))
@NamedQueries(value = {
        @NamedQuery(name = ScheduleQueries.GET_ALL, query = "SELECT s FROM Schedule s"),
        @NamedQuery(name = ScheduleQueries.GET_BY_ID, query = "SELECT s FROM Schedule s WHERE s.id = :id"),
        @NamedQuery(name = ScheduleQueries.GET_4_USER_BY_USER_ID, query = "SELECT s FROM Schedule s WHERE s.user.id = :userId"),
        @NamedQuery(name = ScheduleQueries.GET_4_USER_BY_USERNAME, query = "SELECT s FROM Schedule s WHERE s.user.username = :username"),
        @NamedQuery(name = ScheduleQueries.DELETE_BY_ID, query = "DELETE FROM Schedule s WHERE s.id = :id") })
public class Schedule extends BaseEntity {

    private static final long serialVersionUID = 2420156575897057076L;

    @Column(name = "dateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;

    @Column(name = "place", length = 150, nullable = false)
    private String place;
    
    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TrainingVisit> visits;

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.setSchedule(this);
        comments.add(comment);
    }

    public Set<TrainingVisit> getVisits() {
        return visits;
    }

    public void setVisits(Set<TrainingVisit> visits) {
        this.visits = visits;
    }

    public void addVisitor(TrainingVisit visitor) {
        visitor.setSchedule(this);
        visits.add(visitor);
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", super.getId()).add("title", title).toString();
    }
}
