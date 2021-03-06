package ru.forxy.user.rest.v1.pojo;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.forxy.common.pojo.SimpleJacksonDateDeserializer;
import ru.forxy.common.pojo.SimpleJacksonDateSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document
public class User implements Serializable {

    private static final long serialVersionUID = 4780940749658431820L;

    @Id
    private String email;

    private String password;

    private String login;

    private String firstName;

    private String lastName;

    private Character gender;

    private List<String> roles;

    private Date birthDate;

    private Date updateDate = new Date();

    private String updatedBy;

    private Date createDate = new Date();

    private String createdBy;

    public User() {
    }

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(final Character gender) {
        this.gender = gender;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @JsonSerialize(using = SimpleJacksonDateSerializer.class)
    public Date getBirthDate() {
        return birthDate;
    }

    @JsonDeserialize(using = SimpleJacksonDateDeserializer.class)
    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonSerialize(using = SimpleJacksonDateSerializer.class)
    public Date getUpdateDate() {
        return updateDate;
    }

    @JsonDeserialize(using = SimpleJacksonDateDeserializer.class)
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonSerialize(using = SimpleJacksonDateSerializer.class)
    public Date getCreateDate() {
        return createDate;
    }

    @JsonDeserialize(using = SimpleJacksonDateDeserializer.class)
    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (gender == null) {
            if (other.gender != null) {
                return false;
            }
        } else if (!gender.equals(other.gender)) {
            return false;
        }
        if (roles == null) {
            if (other.roles != null) {
                return false;
            }
        } else if (!roles.equals(other.roles)) {
            return false;
        }
        if (birthDate == null) {
            if (other.birthDate != null) {
                return false;
            }
        } else if (!birthDate.equals(other.birthDate)) {
            return false;
        }
        if (updateDate == null) {
            if (other.updateDate != null) {
                return false;
            }
        } else if (!updateDate.equals(other.updateDate)) {
            return false;
        }
        if (updatedBy == null) {
            if (other.updatedBy != null) {
                return false;
            }
        } else if (!updatedBy.equals(other.updatedBy)) {
            return false;
        }
        if (createDate == null) {
            if (other.createDate != null) {
                return false;
            }
        } else if (!createDate.equals(other.createDate)) {
            return false;
        }
        if (createdBy == null) {
            if (other.createdBy != null) {
                return false;
            }
        } else if (!createdBy.equals(other.createdBy)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("{email=%s, login=%s, firstName=%s, lastName=%s, gender=%c, birthDate=%s, " +
                        "updateDate=%s, updatedBy=%s, createDate=%s, createdBy=%s, roles=%s}",
                email, login, firstName, lastName, gender, birthDate, updateDate, updatedBy, createDate, createdBy, roles);
    }
}
