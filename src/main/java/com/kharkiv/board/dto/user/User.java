package com.kharkiv.board.dto.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.schedule.TrainingVisit;
import com.kharkiv.board.util.QueryNamesConstants.UserQueries;

@Entity
@Table(name = "users", indexes = @Index(name = "login_index", columnList = "login"))
@NamedQueries(value = { @NamedQuery(name = UserQueries.GET_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = UserQueries.GET_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = UserQueries.GET_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = UserQueries.DELETE_BY_ID, query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = UserQueries.DELETE_BY_LOGIN, query = "DELETE FROM User u WHERE u.login = :login") })
public class User implements Serializable {

    private static final long serialVersionUID = -8301776059810262474L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", length = 55, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 8, nullable = false)
    private UserRole role;

    @Column(name = "logo", length = 150)
    private String logo;

    @Basic
    @Column(name = "ban", columnDefinition = "BIT DEFAULT 0", length = 1)
    private Boolean ban;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        return Objects.equal(id, ((User) obj).id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("login", login).add("role", role).add("ban", ban).toString();
    }
}
