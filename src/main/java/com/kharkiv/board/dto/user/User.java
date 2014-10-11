package com.kharkiv.board.dto.user;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.DELETE_BY_ID;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.DELETE_BY_LOGIN;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_ALL;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_BY_ID;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_BY_LOGIN;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;

import com.kharkiv.board.dto.BaseEntity;
import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.schedule.TrainingVisit;

@Entity
@DynamicInsert
@Table(name = "users", indexes = @Index(name = "login_index", columnList = "login"))
@NamedQueries(value = { @NamedQuery(name = GET_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = GET_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = GET_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = DELETE_BY_ID, query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = DELETE_BY_LOGIN, query = "DELETE FROM User u WHERE u.login = :login") })
public class User extends BaseEntity {

    private static final long serialVersionUID = -5766469760606469192L;
    
    @NotNull(message = "login:sign.up.requried.field")
    @Size(min = 3, message = "login:sign.up.field.size")
    @Column(length = 55, nullable = false, unique = true)
    private String login;
    
    @Size(min = 6, message = "password:sign.up.field.size")
    @NotNull(message = "password:sign.up.requried.field")
    @Column(length = 65, nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private UserRole role;
    
    @Column(length = 150)
    private String logo;
    
    @Basic
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean ban;
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Schedule> schedules;
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Comment> comments;
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<TrainingVisit> trainigVisited;
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public Boolean getBan() {
        return ban;
    }
    
    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<Comment> getComments() {
        return comments;
    }
    
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<TrainingVisit> getTrainigVisited() {
        return trainigVisited;
    }
    
    public void setTrainigVisited(Set<TrainingVisit> trainigVisited) {
        this.trainigVisited = trainigVisited;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
        		.add("id", super.getId())
        		.add("login", login)
        		.add("role", role)
                .add("ban", ban)
                .toString();
    }
}
