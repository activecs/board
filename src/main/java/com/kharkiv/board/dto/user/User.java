package com.kharkiv.board.dto.user;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.DELETE_BY_ID;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.DELETE_BY_USERNAME;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_ALL;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_BY_ID;
import static com.kharkiv.board.util.QueryNamesConstants.UserQueries.GET_BY_USERNAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kharkiv.board.dto.BaseEntity;
import com.kharkiv.board.dto.schedule.Comment;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.dto.schedule.TrainingVisit;

@Entity
@DynamicInsert
@Table(name = "users", indexes = @Index(name = "username_index", columnList = "username"))
@NamedQueries(value = { @NamedQuery(name = GET_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = GET_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = GET_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = DELETE_BY_ID, query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = DELETE_BY_USERNAME, query = "DELETE FROM User u WHERE u.username = :username") })
public class User extends BaseEntity implements UserDetails{

    private static final long serialVersionUID = -5766469760606469192L;
    
    @NotNull(message = "login:sign.up.requried.field")
    @Size(min = 3, message = "login:sign.up.field.size")
    @Column(length = 55, nullable = false, unique = true)
    private String username;
    
    @Size(min = 6, message = "password:sign.up.field.size")
    @NotNull(message = "password:sign.up.requried.field")
    @Column(length = 65, nullable = false)
    private String password;
    
    @ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Role> roles;
    
    @Column(length = 150)
    private String logo;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Schedule> schedules;
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Comment> comments;
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<TrainingVisit> trainigVisited;
    
    @Basic
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private boolean isAccountNonExpired;
    
    @Basic
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private boolean isAccountNonLocked;
    
    @Basic
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private boolean isCredentialsNonExpired;
    
    @Basic
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private boolean isEnabled;
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
		}
		return authorities;
	}

    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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
        		.add("username", username)
                .toString();
    }
}
